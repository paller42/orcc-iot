/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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
package hu.sze.orcciot.backends.javaspring;

import static net.sf.orcc.backends.BackendsConstants.BXDF_FILE;
import static net.sf.orcc.backends.BackendsConstants.IMPORT_BXDF;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;

import net.sf.orcc.backends.AbstractHeterogeneousBackend;
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.util.NetworkValidator;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.tools.mapping.XmlBufferSizeConfiguration;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Result;

/**
 * Java back-end.
 * 
 */

public class JavaBackend extends AbstractHeterogeneousBackend {

	protected String srcPath;
	protected String runtimePath;
	protected String topPath;
	protected String binPath;
	protected String repoPath; // path to maven repository folder.  
	//protected String nativePath; // Native actor jars will be stored here.
	
	private final NetworkPrinter networkPrinter;
	private final InstancePrinter instancePrinter;
	private final JavaScriptPrinter javaScriptsPrinter;

	public JavaBackend() {
		networkPrinter = new NetworkPrinter();
		instancePrinter = new InstancePrinter();
		javaScriptsPrinter = new JavaScriptPrinter();
	}

	@Override
	protected void doInitializeOptions() {
		// -- Create directories
		
		binPath = outputPath + File.separator + "bin";
		createDirectory(binPath);

		srcPath = outputPath + File.separator + "src";
		createDirectory(srcPath);

		runtimePath = outputPath + File.separator + "runtime";
		//createDirectory(runtimePath);

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
	public void compile(IProgressMonitor progressMonitor) {
		try {
			super.compile( progressMonitor );
		} catch( Exception ex ) {
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream( bs );
			ex.printStackTrace( ps );
			OrccLogger.severeln( "compile: "+bs.toString() );
		}
	}

	@Override
	protected Result doLibrariesExtraction() {
		final Result result = Result.newInstance();
		result.merge(FilesManager.extract("/runtime/Java/src/ch", srcPath));
		result.merge(FilesManager.extract("/runtime/Java/src/net", srcPath));
		result.merge(FilesManager.extract("/runtime/Java/src/std", srcPath));
		OrccLogger.traceln("Export runtime into " + srcPath + "... ");
		return result;
	}

	@Override
	protected void beforeGeneration(Network network) {
		// -- Create TopPath
		topPath = srcPath + File.separator + network.getSimpleName();
		createDirectory(topPath);
		network.computeTemplateMaps();

		// if required, load the buffer size from the mapping file
		if (getOption(IMPORT_BXDF, false)) {
			File f = new File(getOption(BXDF_FILE, ""));
			new XmlBufferSizeConfiguration(true, true).load(f, network);
		}

		if (network.getVertex(network.getSimpleName()) != null) {
			final StringBuilder warnMsg = new StringBuilder();
			warnMsg.append('"').append(network.getSimpleName()).append('"');
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
		networkPrinter.setNetwork(network);
		result.merge(FilesManager.writeFile(networkPrinter.getNetworkFileContent(), topPath, network.getSimpleName() + ".java"));

		return result;
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		final Result result = Result.newInstance();
		javaScriptsPrinter.setNetwork(network);
		
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getProjectFileContent(), outputPath, ".project"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getClasspathFileContent(), outputPath, ".classpath"));
		result.merge(FilesManager.writeFile(javaScriptsPrinter.getPomXMLContent(), outputPath, "pom.xml"));
		return result;
	}

	@Override
	protected Result doGenerateInstance(Instance instance) {
		if (!instance.getActor().isNative()) {
			instancePrinter.setInstance(instance);
			return FilesManager.writeFile(instancePrinter.getFileContent(), topPath, instance.getSimpleName() + ".java");
		} else {				
			return Result.newInstance();
		}
	}
	
	protected void doValidate(Network network) {
		for (Vertex vertex : network.getChildren()) {
			//Instance instance = vertex.getAdapter(Instance.class);
			OrccLogger.traceln("vertex: " + vertex + "; class: " + vertex.getClass().toString());
		}
		for (Connection c : network.getConnections()) {
			String sourceName = c.getSource() == null ? "null" : c.getSource().getLabel();
			String targetName = c.getTarget() == null ? "null" : c.getTarget().getLabel();
			String sourcePortName = c.getSourcePort() == null ? "null" : c.getSourcePort().getName();
			String targetPortName = c.getTargetPort() == null ? "null" : c.getTargetPort().getName();
			OrccLogger.traceln("from: "+sourceName+"."+sourcePortName+" to "+targetName+"."+targetPortName);
		}

		Validator.checkMinimalFifoSize(network, fifoSize);

		new NetworkValidator().doSwitch(network);
	}

	
}
