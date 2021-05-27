package hu.sze.orcciot.backends.c.STM32F4;

import static net.sf.orcc.OrccLaunchConstants.ENABLE_TRACES;
import static net.sf.orcc.backends.BackendsConstants.ADDITIONAL_TRANSFOS;
import static net.sf.orcc.backends.BackendsConstants.BXDF_FILE;
import static net.sf.orcc.backends.BackendsConstants.HETEROGENEOUS_MODE;
import static net.sf.orcc.backends.BackendsConstants.IMPORT_BXDF;

import java.io.File;
import java.io.IOException;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.core.runtime.Platform;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.AbstractHeterogeneousBackend;
import net.sf.orcc.backends.c.transform.CBroadcastAdder;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.DeadVariableRemoval;
import net.sf.orcc.backends.transform.DivisionSubstitution;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.Inliner;
import net.sf.orcc.backends.transform.InlinerByAnnotation;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.InstTernaryAdder;
import net.sf.orcc.backends.transform.ListFlattener;
import net.sf.orcc.backends.transform.LoopUnrolling;
import net.sf.orcc.backends.transform.Multi2MonoToken;
import net.sf.orcc.backends.transform.ParameterImporter;
import net.sf.orcc.backends.transform.StoreOnceTransformation;
import net.sf.orcc.backends.util.Alignable;
import net.sf.orcc.backends.util.BroadcastMapper;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.ArgumentEvaluator;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.BroadcastRemover;
import net.sf.orcc.df.transform.FifoSizePropagator;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.SharedVarsDetection;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.df.util.NetworkValidator;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.transform.BlockCombine;
import net.sf.orcc.ir.transform.ControlFlowAnalyzer;
import net.sf.orcc.ir.transform.DeadCodeElimination;
import net.sf.orcc.ir.transform.DeadGlobalElimination;
import net.sf.orcc.ir.transform.PhiRemoval;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.ir.transform.SSATransformation;
import net.sf.orcc.ir.transform.SSAVariableRenamer;
import net.sf.orcc.ir.transform.TacTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.mapping.XmlBufferSizeConfiguration;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.tools.stats.StatisticsPrinter;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Result;
import net.sf.orcc.util.Void;
import net.sf.orcc.util.OrccUtil;

/**
 * STM32F4 C back-end.
 * 
 * @author Gabor Paller
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @author Antoine Lorence
 * 
 */
public class STM32F4CBackend extends AbstractHeterogeneousBackend {

	/**
	 * Path to target "src" folder
	 */
	protected String srcPath;

	private final NetworkPrinter networkPrinter;
	private final CMakePrinter cmakePrinter;
	private final InstancePrinter instancePrinter;
	private final OutputPortPrinter outputPortPrinter;
	private final InputPortPrinter inputPortPrinter;

	private final TracesPrinter tracesPrinter;
	private final StatisticsPrinter statsPrinter;
	private String stm32f4_fwpath;
	private String stm32f4_newlibpath;
	private String stm32f4_server_address;
	private int stm32f4_server_port;
	private String stm32f4_nodeId;
	private String stm32f4_network_apn;
	private String stm32f4_native_lib_dir;
	private String stm32f4_native_lib_name;
	private List<String> stm32f4_native_lib_headers;
	
	public STM32F4CBackend() {
		networkPrinter = new NetworkPrinter();
		cmakePrinter = new CMakePrinter();
		instancePrinter = new InstancePrinter();
		outputPortPrinter = new OutputPortPrinter();
		inputPortPrinter = new InputPortPrinter();
		tracesPrinter = new TracesPrinter();
		statsPrinter = new StatisticsPrinter();
	}

	@Override
	protected void doInitializeOptions() {
		// Configure paths
		srcPath = outputPath + File.separator + "src";

		final IFile stm32f4File = OrccUtil.getFile(project, "stm32f4", OrccUtil.PROPS_SUFFIX);
		if ( stm32f4File == null)
				throw new OrccRuntimeException(
					"Unable to find stm32f4.properties");
		Properties stm32f4Props = new Properties();
		try {
			stm32f4Props.load( stm32f4File.getContents() );
		} catch( IOException ex ) {
				throw new OrccRuntimeException(
					"Unable to load stm32f4.properties");
		} catch( CoreException ex ) {
				throw new OrccRuntimeException(
					"Unable to load stm32f4.properties");
		}
		stm32f4_fwpath = stm32f4Props.getProperty( "stm32f4.fw.path" );
		if( stm32f4_fwpath == null )
				throw new OrccRuntimeException(
					"stm32f4.fw.path property missing");

		stm32f4_newlibpath = stm32f4Props.getProperty( "stm32f4.newlib.path" );
		if( stm32f4_newlibpath == null )
				throw new OrccRuntimeException(
					"stm32f4.newlib.path property missing");

		stm32f4_server_address = stm32f4Props.getProperty( "stm32f4.server.address" );
		if( stm32f4_server_address == null )
				throw new OrccRuntimeException(
					"stm32f4.server.address property missing");
		String s;
		s = stm32f4Props.getProperty( "stm32f4.server.port" );
		if( s == null )
				throw new OrccRuntimeException(
					"stm32f4.server.port property missing");
		try {
			stm32f4_server_port = Integer.parseInt( s );
		} catch( NumberFormatException ex ) {
				throw new OrccRuntimeException(
					"stm32f4.server.port property is not an integer");
		}
		stm32f4_nodeId = stm32f4Props.getProperty( "stm32f4.nodeId" );
		if( stm32f4_nodeId == null ) {
			stm32f4_nodeId = randomString( 16 );
			stm32f4Props.setProperty( "stm32f4.nodeId", stm32f4_nodeId );
		}
		
		stm32f4_network_apn = stm32f4Props.getProperty( "stm32f4.network.apn" );
		if( stm32f4_network_apn == null )
				throw new OrccRuntimeException(
					"stm32f4.network.apn property missing");

		stm32f4_native_lib_dir = stm32f4Props.getProperty( "stm32f4.native.lib.dir" );
		stm32f4_native_lib_name = stm32f4Props.getProperty( "stm32f4.native.lib.name" );
		if( stm32f4_native_lib_dir != null ) 
			stm32f4_native_lib_headers = listNativeHeaders( stm32f4_native_lib_dir );
		
		// Load options map into code generator instances
		networkPrinter.setOptions(getOptions());
		networkPrinter.setNativeHeaders( stm32f4_native_lib_headers ); 
		instancePrinter.setOptions(getOptions());
		instancePrinter.setNativeHeaders( stm32f4_native_lib_headers ); 
		tracesPrinter.setOptions(getOptions());
		outputPortPrinter.setOptions(getOptions());
		cmakePrinter.setStm32f4Properties(stm32f4Props);
		networkPrinter.setStm32f4Properties(stm32f4Props);

		// -----------------------------------------------------
		// Transformations that will be applied on the Network
		// -----------------------------------------------------
		if (mergeActors) {
			networkTransfos.add(new FifoSizePropagator(fifoSize));
			networkTransfos.add(new BroadcastAdder());
		} else {
			networkTransfos.add(new FifoSizePropagator(fifoSize, true));
		}
		networkTransfos.add(new Instantiator(true));
		networkTransfos.add(new NetworkFlattener());
		networkTransfos.add(new UnitImporter());
		//networkTransfos.add(new DisconnectedOutputPortRemoval());
		if (classify) {
			networkTransfos.add(new Classifier());
		}
		if (mergeActors) {
			networkTransfos.add(new ActorMerger());
		} else {
			networkTransfos.add(new CBroadcastAdder());
		}
		if (mergeActors) {
			networkTransfos.add(new BroadcastRemover());
		}
		networkTransfos.add(new ArgumentEvaluator());
		networkTransfos.add(new TypeResizer(true, false, true, false));
		networkTransfos.add(new RenameTransformation(getRenameMap()));
		networkTransfos.add(new SharedVarsDetection());

		// -------------------------------------------------------------------
		// Transformations that will be applied on children (instances/actors)
		// -------------------------------------------------------------------
		if (mergeActions) {
			childrenTransfos.add(new ActionMerger());
		}
		if (convertMulti2Mono) {
			childrenTransfos.add(new Multi2MonoToken());
		}
		childrenTransfos.add(new DfVisitor<Void>(new InlinerByAnnotation()));
		childrenTransfos.add(new DfVisitor<Void>(new LoopUnrolling()));

		// If "-t" option is passed to command line, apply additional
		// transformations
		if (getOption(ADDITIONAL_TRANSFOS, false)) {
			childrenTransfos.add(new StoreOnceTransformation());
			childrenTransfos.add(new DfVisitor<Void>(new SSATransformation()));
			childrenTransfos.add(new DfVisitor<Void>(new PhiRemoval()));
			childrenTransfos.add(new Multi2MonoToken());
			childrenTransfos.add(new DivisionSubstitution());
			childrenTransfos.add(new ParameterImporter());
			childrenTransfos.add(new DfVisitor<Void>(new Inliner(true, true)));

			// transformations.add(new UnaryListRemoval());
			// transformations.add(new GlobalArrayInitializer(true));

			childrenTransfos.add(new DfVisitor<Void>(new InstTernaryAdder()));
			childrenTransfos.add(new DeadGlobalElimination());

			childrenTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
			childrenTransfos.add(new DfVisitor<Void>(new DeadCodeElimination()));
			childrenTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
			childrenTransfos.add(new DfVisitor<Void>(new ListFlattener()));
			childrenTransfos.add(new DfVisitor<Expression>(new TacTransformation()));
			childrenTransfos.add(new DfVisitor<CfgNode>(new ControlFlowAnalyzer()));
			childrenTransfos.add(new DfVisitor<Void>(new InstPhiTransformation()));
			childrenTransfos.add(new DfVisitor<Void>(new EmptyBlockRemover()));
			childrenTransfos.add(new DfVisitor<Void>(new BlockCombine()));

			childrenTransfos.add(new DfVisitor<Expression>(new CastAdder(true, true)));
			childrenTransfos.add(new DfVisitor<Void>(new SSAVariableRenamer()));
		}
	}

	protected Map<String, String> getRenameMap() {
		Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_replaced");
		renameMap.put("getw", "getw_replaced");
		renameMap.put("exit", "exit_replaced");
		renameMap.put("index", "index_replaced");
		renameMap.put("log2", "log2_replaced");
		renameMap.put("max", "max_replaced");
		renameMap.put("min", "min_replaced");
		renameMap.put("select", "select_replaced");
		renameMap.put("OUT", "OUT_REPLACED");
		renameMap.put("IN", "IN_REPLACED");
		renameMap.put("SIZE", "SIZE_REPLACED");

		return renameMap;
	}
	
	/*
	* Don't do top level check (see also AbstractBackend::doValidate)
	*/
	protected void doValidate(Network network) {
		Validator.checkMinimalFifoSize(network, fifoSize);
		new NetworkValidator().doSwitch(network);
	}


	@Override
	protected Result doLibrariesExtraction() {
		final Result result = Result.newInstance();
		result.merge(FilesManager.extract("/runtime/STM32F4/libs", outputPath));
		return result;
	}

	@Override
	protected void beforeGeneration(Network network) {
		new File(outputPath + File.separator + "build").mkdirs();
		new File(outputPath + File.separator + "bin").mkdirs();

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
		EList<Port> inputPorts = network.getInputs();
		for( Port port : inputPorts ) {
			try {
				OrccLogger.traceln("** input port: "+port.getName());
				inputPortPrinter.setPort(port);
				result.merge(FilesManager.writeFile(inputPortPrinter.getFileContent(), srcPath, port.getName() + ".c"));
			} catch( Exception ex ) {
				OrccLogger.severeln("input port error: "+ex);
				throw new OrccRuntimeException( "C backend error" ); 
			}
		}
		EList<Port> outputPorts = network.getOutputs();
		for( Port port : outputPorts ) {
			try {
				OrccLogger.traceln("** output port: "+port.getName());
				outputPortPrinter.setPort(port);
				result.merge(FilesManager.writeFile(outputPortPrinter.getFileContent(), srcPath, port.getName() + ".c"));
			} catch( Exception ex ) {
				OrccLogger.severeln("output port error: "+ex);
				throw new OrccRuntimeException( "C backend error" ); 
			}
		}
		networkPrinter.setNetwork(network);
		result.merge(FilesManager.writeFile(networkPrinter.getNetworkFileContent(), srcPath, network.getSimpleName() + ".c"));
		return result;
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		cmakePrinter.setNetwork(network);
		cmakePrinter.setOptions(getOptions());
		final Result result = Result.newInstance();
		result.merge(FilesManager.writeFile(
				cmakePrinter.rootMakefileContent(), 
				outputPath, "Makefile"));
		result.merge(FilesManager.writeFile(
				cmakePrinter.rootLinkerScriptContent(), 
				outputPath, "stm32_flash.ld"));

		if (getOption(ENABLE_TRACES, true)) {
			result.merge(FilesManager.writeFile(tracesPrinter.getTracesFileContent(network), srcPath, "traces.txt"));
		}

		if (getOption(HETEROGENEOUS_MODE, false) == false) {
			result.merge(FilesManager.writeFile(statsPrinter.getContent(network), srcPath,
					network.getSimpleName() + ".csv"));
		}
		if (mergeActors) {
			BroadcastMapper broadcastMapper = new BroadcastMapper();
			broadcastMapper.prepareBroadcastMapping(network);
		}

		final Mapping mapper = new Mapping(network, mapping);
		result.merge(FilesManager.writeFile(mapper.getContentFile(), srcPath, network.getSimpleName() + ".xcf"));

		return result;
	}

	@Override
	protected void beforeGeneration(Instance instance) {
		// update "vectorizable" information
		Alignable.setAlignability(instance.getActor());
	}

	@Override
	protected Result doGenerateInstance(Instance instance) {
		instancePrinter.setInstance(instance);
		OrccLogger.traceln("** doGenerateInstance: "+instance.getSimpleName());
		return FilesManager.writeFile(instancePrinter.getFileContent(), srcPath, instance.getSimpleName() + ".c");
	}

	@Override
	protected void beforeGeneration(Actor actor) {
		// update "vectorizable" information
		Alignable.setAlignability(actor);
	}

	@Override
	protected Result doGenerateActor(Actor actor) {
		instancePrinter.setActor(actor);
		OrccLogger.traceln("** doGenerateActor: "+actor.getSimpleName());
		return FilesManager.writeFile(instancePrinter.getFileContent(), srcPath, actor.getSimpleName() + ".c");
	}
	
	private String randomString( int strlen ) {
		StringBuffer b = new StringBuffer();
		Random rnd = new Random();
		for( int i = 0 ; i < strlen ; ++i ) {
			int r = rnd.nextInt( 16 );
			char c = ' ';
			if( r < 10 )
				c = (char)( '0' + r );
			else
				c = (char)( 'A' + r - 10 );
			b.append( c );
		}
		return new String( b );
	}
	
	private List<String> listNativeHeaders( String stm32f4_native_lib_dir ) {
		File f = new File( stm32f4_native_lib_dir, "public-include" );
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File f, String name) {
				return name.endsWith(".h");
			}
		};
		return Arrays.asList( f.list(filter) );
	}
}
