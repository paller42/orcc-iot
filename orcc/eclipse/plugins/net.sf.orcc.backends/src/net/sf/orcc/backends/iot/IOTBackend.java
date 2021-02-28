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

package net.sf.orcc.backends.iot;

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_DEBUG;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.OUTPUT_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.XCF_FILE;
import static net.sf.orcc.backends.BackendsConstants.BXDF_FILE;
import static net.sf.orcc.backends.BackendsConstants.HETEROGENEOUS_BACKEND;
import static net.sf.orcc.backends.BackendsConstants.HETEROGENEOUS_MODE;
import static net.sf.orcc.backends.BackendsConstants.IMPORT_BXDF;
import static net.sf.orcc.backends.BackendsConstants.IMPORT_XCF;
import static net.sf.orcc.backends.BackendsConstants.PARTITIONED_NETWORK;
import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;
import static net.sf.orcc.OrccLaunchConstants.ENABLE_TRACES;
import static net.sf.orcc.util.OrccUtil.getFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.AbstractHeterogeneousBackend;
import net.sf.orcc.backends.Backend;
import net.sf.orcc.backends.BackendFactory;
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
import net.sf.orcc.backends.util.xcf.Configuration;
import net.sf.orcc.backends.util.xcf.Configuration.Partitioning.Partition;
import net.sf.orcc.backends.util.xcf.XCFManager;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.util.Dota;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.tools.mapping.XmlBufferSizeConfiguration;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.Result;
import net.sf.orcc.util.util.EcoreHelper;

/**
 * Java back-end.
 * 
 */
public class IOTBackend extends AbstractHeterogeneousBackend {

	/**
	 * Path to target "src" folder
	 */
	protected String srcPath;
	protected String runtimePath;
	protected String topPath;
	protected String binPath;
	protected String graphPath;

	/**
	 * Defined backends in XCF
	 */
	//private Map<String, Backend> backends;

	private int nextPortID;
	private HashSet<Edge> edgeCache;
	private HashMap<Edge, Integer> portsByEdge;

	public IOTBackend() {
	}

	@Override
	protected void doInitializeOptions() {
		// -- Create directories

		binPath = outputPath + File.separator + "bin";
		createDirectory(binPath);

		graphPath = outputPath + File.separator + "graph";
		createDirectory(graphPath);

		
		srcPath = outputPath + File.separator + "src";

		// createDirectory(srcPath);

		runtimePath = outputPath + File.separator + "runtime";
		// createDirectory(runtimePath);

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
		// -- New ResourceSet for a new compilation
			currentResourceSet = new ResourceSetImpl();
			XCFManager manager = null;
			
		// -- Initialize the monitor. Can be used to stop the back-end
		// execution and provide feedback to user
			monitor = progressMonitor;

		// -- Initialize backends
			//backends = new HashMap<String, Backend>();

		// -- Get Xml Configuration File
			String xcfName = getOption(XCF_FILE, "");
			OrccLogger.traceln("XCF file name: "+xcfName);
			final IFile xcfFile = getFile(project, xcfName, OrccUtil.MAPPING_SUFFIX);
			Configuration xcf = null;
			try {
				manager = new XCFManager(xcfFile.getRawLocation().makeAbsolute().toFile());
				xcf = manager.getConfiguration();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new OrccRuntimeException( "XCFManager: "+e.getMessage() );
			}
		
			OrccLogger.traceln("XCF loaded");
			String networkName = xcf.getNetwork().getQualifiedId();
			OrccLogger.traceln("Network name: "+networkName);
			final Network network;
			final IFile xdfFile = OrccUtil.getFile(project, networkName, OrccUtil.NETWORK_SUFFIX);
			if (xdfFile == null) {
				throw new OrccRuntimeException(
					"Unable to find the XDF file " + "corresponding to the network " + networkName + ".");
			} else {
				network = EcoreHelper.getEObject(currentResourceSet, xdfFile);
			}
			OrccLogger.traceln("*** generate dot dump of the input network" ); 
			dumpNetwork( network,"full.dot" );
			
			Partitioner partitioner = new Partitioner(network, manager,graphPath);
			partitioner.partitionNetwork();

			// -- Backend Options
			Map<String, Backend> backends = new HashMap<String, Backend>();
			
			// -- Create orcc backends
			for( net.sf.orcc.backends.util.xcf.Configuration.Backends.Backend b : xcf.getBackends().getBackend()) {
				String backendId = b.getId();
				String backendName = b.getBackend();
				net.sf.orcc.backends.Backend backend = BackendFactory.getInstance().getBackend(backendName);
				if (backend == null) {
					throw new OrccRuntimeException("Unable to find backend : " + backendName);
				}
				Map<String, Object> options = new HashMap<String, Object>();
				// -- Basic backend options
				options.put(PROJECT, getOption(PROJECT, ""));
				options.put(FIFO_SIZE, getOption(FIFO_SIZE, DEFAULT_FIFO_SIZE));
				options.put(DEBUG_MODE, getOption(DEBUG_MODE, DEFAULT_DEBUG));
				options.put(NO_LIBRARY_EXPORT, false);
				options.put(ENABLE_TRACES, false);
				
				// -- heterogeneous backend options
				options.put(HETEROGENEOUS_BACKEND, backendName);
				options.put(HETEROGENEOUS_MODE, true);
				
				// -- Set the basic options to the backend
				backend.setOptions(options);
				
				// -- Parse other options from XCF
				// TODO : for the Java backends you need to parse the options, but first you need to create them for the java backend for example
				
				
				// -- Put the backend to the map of backends
				backends.put(backendId, backend);
			}
			
			// -- Get from every partition the the subnetworks and attribute them to the backend options
			// -- Network by partition
			Map<Partition, Network> networksbyPartition = partitioner.getNetworksByPartition();
			for(Partition partition : networksbyPartition.keySet()) {
				String partitionId = partition.getId();
				String backendId = partition.getBackendId();

				Network suNetwork = partitioner.getSubNetwork(partition);
				if( suNetwork == null ) {
					OrccLogger.traceln(
						"*********************************************" + "************************************");
					OrccLogger.traceln("* Partition: " + partitionId+" ("+partition+")");
					OrccLogger.traceln("* Backend ID: " + backendId);
					OrccLogger.traceln("!!! no node was allocated to this partition, skipping" );
				} else {
					Backend backend = backends.get(backendId);
					String outputFolder = outputPath + File.separator + "partitions" + File.separator + partitionId;
					Map<String, Object> options = backend.getOptions();
					options.put(OUTPUT_FOLDER, outputFolder);
					options.put(PARTITIONED_NETWORK,suNetwork);
					backend.setOptions(options);				
					OrccLogger.traceln(
						"*********************************************" + "************************************");
					OrccLogger.traceln("* Partition: " + partitionId+" ("+partition+")");
					OrccLogger.traceln("* Backend ID: " + backendId);
				
				// -- Compile backend
					backend.compile(progressMonitor);
				}
				
			}
			
			
		
		} catch( Exception ex ) {
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream( bs );
			ex.printStackTrace( ps );
			OrccLogger.severeln( "compile: "+bs.toString() );
		}
	}

	@Override
	protected Result doLibrariesExtraction() {
		OrccLogger.traceln("doLibrariesExtraction");
		final Result result = Result.newInstance();
		OrccLogger.traceln("Export runtime into " + srcPath + "... ");
		return result;
	}

	@Override
	protected void beforeGeneration(Network network) {
		OrccLogger.traceln("beforeGeneration: "+network);
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
		OrccLogger.traceln("doGenerateNetwork: "+network);
		List<Actor> actors = network.getAllActors();
		for( int i = 0 ; i < actors.size() ; ++i ) {
			Actor a = actors.get(i);
			OrccLogger.traceln("Actor: "+a.getName());
		}
		XCFManager manager;
		try {
			if (getOption(IMPORT_XCF, false)) {
				File f = new File(getOption(XCF_FILE, ""));
				manager = new XCFManager(f);
				Configuration conf = manager.getConfiguration();
				StringBuilder sb = new StringBuilder();
				for (Configuration.Backends.Backend backend : conf.getBackends().getBackend()) {
					sb.append("id: " + backend.getId());
					sb.append(", val: " + backend.getBackend());
					sb.append(",param: " + backend.getParameter().getKey());
					sb.append(" - " + backend.getParameter().getValue());
					sb.append("\r\n");
				}
				OrccLogger.warnln(sb.toString());
			}
		} catch (Exception ex) {
			// handle error...
		}
		// process conf...
		return Result.newOkInstance();
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		OrccLogger.traceln("doAdditionalGeneration: "+network);
		return Result.newOkInstance();
	}

	@Override
	protected Result doGenerateInstance(Instance instance) {
		OrccLogger.traceln("doGenerateInstance: "+instance);
		return Result.newOkInstance();// FilesManager.writeFile(instancePrinter.getFileContent(), topPath,
										// instance.getSimpleName() + ".java");
	}
	
/**
* Gets the port ID associated to an edge. If the edge already has a mapping, return that mapped ID. If the edge does not yet have a mapping,
* a new ID is allocated for the edge, the ID is stored in the mapping table and then returned to the caller.
*/
	private int getPortIDByEdge( Edge e ) {
		Integer id = portsByEdge.get( e );
		if( id == null ) {
			id = new Integer( nextPortID++ );
			portsByEdge.put( e,id );
		}
		return id.intValue();
	}
	
	private void dumpNetwork( Network subNetwork, String networkName ) throws IOException {
		File gf = new File( graphPath,networkName  );
		PrintWriter pw = new PrintWriter( gf );
		Dota dot = new Dota();
		pw.println( dot.dot( subNetwork) );
		pw.close();
	}
	
	private boolean edgeVisited( Edge e ) {
// If the edge traverses a partition boundary (hence has ports), connections must always be created
		if( portsByEdge.containsKey( e ) )
			return false;
// Otherwise if connection for the edge has already been created, skip it
		if( edgeCache.contains( e ) )
			return true;
// New edge, connection will be created, put it into the cache
		edgeCache.add( e );
		return false;
	}
}
