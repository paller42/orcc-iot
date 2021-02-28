/*
 * Copyright (c) 2019, IoT Researchers 
 * @author Bezati Endri	
 * @author Paller Gábor  	
 * @author Taušan Nebojša
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the names of the IoT Researchers nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.javaspring;

import static net.sf.orcc.backends.BackendsConstants.BXDF_FILE;
import static net.sf.orcc.backends.BackendsConstants.IMPORT_BXDF;


import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.java.JavaBackend;
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.tools.mapping.XmlBufferSizeConfiguration;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Result;
import net.sf.orcc.util.OrccUtil;

/**
 * Java Spring back-end.
 * 
 */
public class JavaSpringBackend extends JavaBackend {

	/**
	 * Path to target "src" folder 
	 */
	protected String srcPath;
	protected String runtimePath;
	protected String topPath;
	protected String binPath;
	protected String resourcePath;
	protected String scriptPath;

	private final NetworkPrinter networkPrinter;
	private final InstancePrinter instancePrinter;
	private final JavaSpringScriptPrinter javaScriptsPrinter;
	private Properties azureCredsProps;
	private String azure_resource_group;
	private String azure_eventhub_namespace;
	private String azure_acr_username;
	private String azure_acr_password;
	private String azure_amqp_uri;
	private String azure_db_name;
	private String azure_db_URI;
	
	public JavaSpringBackend() {
		networkPrinter = new NetworkPrinter();
		instancePrinter = new InstancePrinter();
		javaScriptsPrinter = new JavaSpringScriptPrinter();
	}

	@Override
	protected void doInitializeOptions() {
		// -- Create directories
		
		binPath = outputPath + File.separator + "target";
		createDirectory(binPath);

		srcPath = outputPath + File.separator + "src" + File.separator + "main" + File.separator + "java";
		createDirectory(srcPath);
		
		resourcePath = outputPath + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		createDirectory(resourcePath);

		runtimePath = outputPath + File.separator + "runtime";
		//createDirectory(runtimePath);
		
		scriptPath  = outputPath + File.separator + "scripts";
		createDirectory(scriptPath);
		
		// maven repository folder, also configured in pom.xml (e.g. for Native Actor .jars)
		repoPath = outputPath + File.separator + "repos" ; 
		createDirectory(repoPath);
		
		final IFile azureCredsFile = OrccUtil.getFile(project, "azurecreds", OrccUtil.PROPS_SUFFIX);
		if ( azureCredsFile == null)
				throw new OrccRuntimeException(
					"Unable to find azurecreds.properties");
		azureCredsProps = new Properties();
		try {
			azureCredsProps.load( azureCredsFile.getContents() );
		} catch( IOException ex ) {
				throw new OrccRuntimeException(
					"Unable to load azurecreds.properties");
		} catch( CoreException ex ) {
				throw new OrccRuntimeException(
					"Unable to load azurecreds.properties");
		}
		azure_resource_group = azureCredsProps.getProperty( "azure.resource.group" );
		if( azure_resource_group == null )
				throw new OrccRuntimeException(
					"azure_resource_group property missing");

		azure_eventhub_namespace = azureCredsProps.getProperty( "azure.eventhub.namespace" );
		if( azure_eventhub_namespace == null )
				throw new OrccRuntimeException(
					"azure.eventhub.namespace property missing");
		
		azure_acr_username = azureCredsProps.getProperty( "azure.acr.username" );
		if( azure_acr_username == null )
				throw new OrccRuntimeException(
					"azure.acr.username property missing");

		azure_acr_password = azureCredsProps.getProperty( "azure.acr.password" );
		if( azure_acr_password == null )
				throw new OrccRuntimeException(
					"azure.acr.password property missing");
		
		azure_amqp_uri = azureCredsProps.getProperty( "azure.amqp.uri" );
		if( azure_amqp_uri == null )
				throw new OrccRuntimeException(
					"azure_amqp_uri property missing");
		
		azure_db_name= azureCredsProps.getProperty( "azure.db.name" );
		if( azure_db_name == null )
			throw new OrccRuntimeException(
				"azure_db_name property missing");
		
		azure_db_URI= azureCredsProps.getProperty( "azure.db.URI" );
		if( azure_db_URI == null )
			throw new OrccRuntimeException(
				"azure_db_URI property missing");
		
		// -- Replacement Map
		Map<String, String> replacementMap = new HashMap<String, String>();
		replacementMap.put("abs", "abs_replaced");
		replacementMap.put("getw", "getw_replaced");
		replacementMap.put("exit", "exit_replaced");
		replacementMap.put("index", "index_replaced");
		replacementMap.put("log2", "log2_replaced");
		replacementMap.put("max", "max_replaced");
		replacementMap.put("min", "min_replaced");
		replacementMap.put("select", "select_replaced");
		replacementMap.put("OUT", "OUT_REPLACED");
		replacementMap.put("IN", "IN_REPLACED");
		replacementMap.put("SIZE", "SIZE_REPLACED");

		// Load options map into code generator instances
		networkPrinter.setOptions(getOptions());
		instancePrinter.setOptions(getOptions());
		javaScriptsPrinter.setAzureProperties( azureCredsProps );
		
		networkTransfos.add(new BroadcastAdder());
		networkTransfos.add(new Instantiator(false));
		networkTransfos.add(new NetworkFlattener());

		networkTransfos.add(new UnitImporter());
		networkTransfos.add(new DisconnectedOutputPortRemoval());

		childrenTransfos.add(new TypeResizer(true, false, true, false));
		childrenTransfos.add(new RenameTransformation(replacementMap));

	}

	protected Map<String, String> getRenameMap() {
		return null;
	}

	@Override
	protected Result doLibrariesExtraction() {
		final Result result = Result.newInstance();

		result.merge(FilesManager.extract("/runtime/SpringJava/src/ch", srcPath));
		result.merge(FilesManager.extract("/runtime/SpringJava/src/net", srcPath));
		result.merge(FilesManager.extract("/runtime/SpringJava/src/std", srcPath));

		OrccLogger.traceln("Export runtime into " + srcPath + "... ");
		return result;
	}

	@Override
	protected void beforeGeneration(Network network) {
		// -- Create TopPath
		String networkSimpleName = network.getSimpleName().replaceAll("[^A-Za-z0-9]", "");
		topPath = srcPath + File.separator + networkSimpleName;
		createDirectory(topPath);

		network.computeTemplateMaps();

		// if required, load the buffer size from the mapping file
		if (getOption(IMPORT_BXDF, false)) {
			File f = new File(getOption(BXDF_FILE, ""));
			new XmlBufferSizeConfiguration(true, true).load(f, network);
		}

		if (network.getVertex(network.getSimpleName()) != null) {
			final StringBuilder warnMsg = new StringBuilder();
			warnMsg.append('"').append(networkSimpleName).append('"');
			warnMsg.append(" is the name of both the network you want to generate");
			warnMsg.append(" and a vertex in this network.").append('\n');
			warnMsg.append("The 2 entities will be generated");
			warnMsg.append(" in the same file. Please rename one of these elements to prevent");
			warnMsg.append(" unwanted overwriting.");
			OrccLogger.warnln(warnMsg.toString());
		}
	}

	@Override
	protected Result doGenerateNetwork(Network network) {
		final Result result = Result.newInstance();
		try {
			String networkSimpleName = network.getSimpleName().replaceAll("[^A-Za-z0-9]", "");
			networkPrinter.setNetwork(network, topPath, result, javaScriptsPrinter);
			result.merge(FilesManager.writeFile(networkPrinter.getNetworkFileContent(), topPath, networkSimpleName + ".java"));
		} catch( Exception ex ) {
			OrccLogger.severeln( ex );
		}
		return result;
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		final Result result = Result.newInstance();
		javaScriptsPrinter.setNetwork(network);
		String networkSimpleName = network.getSimpleName().replaceAll("[^A-Za-z0-9]", "");
		
		//top path
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getSpringStarterFileContent(), topPath, "Starter.java"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getWebSocketConfig(networkSimpleName), topPath, "WebSocketConfig.java"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getSocketHandler(networkSimpleName,networkPrinter.getMapOfPortNames() ), topPath, "SocketHandler.java"));
		
		//output path
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getProjectFileContent(), outputPath, ".project"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getClasspathFileContent(), outputPath, ".classpath"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getPomXMLContent(networkPrinter.getMapOfPortNames(), project.getName()), outputPath, "pom.xml"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getAzureProperties(), outputPath, "azure.properties"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getDockerFile(project.getName() +"_"+ networkSimpleName+"-1.0-SNAPSHOT.jar"), outputPath, "Dockerfile"));
		
		//script path
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getSSHDConfig(), scriptPath, "sshd_config"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getInit(project.getName() +"_"+ networkSimpleName+"-1.0-SNAPSHOT.jar"), scriptPath, "init.sh"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getJMSProperties(), resourcePath, "jms.properties"));
		
		//resource path
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getSpringAppProperties(), resourcePath, "application.properties"));
		return result;
	}

	@Override
	protected Result doGenerateInstance(Instance instance) {
		if (!instance.getActor().isNative()) {
			instancePrinter.setInstance(instance);
			String instanceSimpleName = instance.getSimpleName().replaceAll("[^A-Za-z0-9]", "");
			return FilesManager.writeFile(instancePrinter.getFileContent(), topPath, instanceSimpleName + ".java");
		} else {
			// collect annotations from .cal file where the native actor is defined....
			String bckEnd =     instance.getActor().getAttribute("backend").getAttribute("id").getStringValue();
			String artId =    instance.getActor().getAttribute("jar").getAttribute("artifactid").getStringValue();
			String jarVersion = instance.getActor().getAttribute("jar").getAttribute("jarversion").getStringValue();
			
			// check if this backend matches to the backend specified in .cal
			if (!bckEnd.equals("java-spring")) {
				OrccLogger.severeln("Native actor " + instance.getName() +  " requires " + bckEnd +" backend, not java-string backedn");
				stop(); // terminates the compilation...
			}
			
			// create the folder structure for .jars
			String packagePath = repoPath
					+ File.separator + instance.getActor().getPackage().replace('.', File.separatorChar)
					+ File.separator + artId 
					+ File.separator + jarVersion; 
			createDirectory(packagePath);

			// copy .jar file of the native actor into repository...
			Path src = new File(instance.getActor().getFile().getProject().getFile("jars" + File.separator + artId + "-" + jarVersion + ".jar").getLocation().toString()).toPath();
			Path dest = new File(packagePath).toPath();		
			try {
				Files.copy(src, dest.resolve(src.getFileName()), REPLACE_EXISTING );
			} catch (IOException e) {
				System.out.println("Unable to copy .jar file for native actor to current project...");
				e.printStackTrace();
			}
			
			//Checks the actors' parameters
			//instance.getActor().getStateVar("")
			//if ()
		
			return Result.newInstance();
		}
	}

}
