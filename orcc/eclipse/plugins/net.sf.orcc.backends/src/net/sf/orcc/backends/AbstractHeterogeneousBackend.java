package net.sf.orcc.backends;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Result;

import static net.sf.orcc.backends.BackendsConstants.HETEROGENEOUS_MODE;
import static net.sf.orcc.backends.BackendsConstants.PARTITIONED_NETWORK;
import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;
import static net.sf.orcc.backends.BackendsConstants.HETEROGENEOUS_BACKEND;

/**
 * Abstract Heterogeneous BackendO
 * 
 * @author endrix
 *
 */
public abstract class AbstractHeterogeneousBackend extends AbstractBackend {

	protected boolean heterogeneousMode;

	@Override
	public void compile(IProgressMonitor progressMonitor) {
		heterogeneousMode = getOption(HETEROGENEOUS_MODE, false);

		if (heterogeneousMode) {
			String backendName = getOption(HETEROGENEOUS_BACKEND, "<unknown>");
			currentResourceSet = new ResourceSetImpl();
			monitor = progressMonitor;
			OrccLogger.traceln("* Backend: " + backendName);
			Network network = null;
			if (getOptions().containsKey(PARTITIONED_NETWORK)) {
				network = (Network) getOptions().get(PARTITIONED_NETWORK);
			}
			if (network == null) {
				throw new OrccRuntimeException("Unable to load partioned network");
			}

			beforeTransformations(network);
			if (!networkTransfos.isEmpty()) {
				stopIfRequested();
				OrccLogger.traceln("** Network transformations");
				final long t0 = System.currentTimeMillis();
				applyTransformations(network, networkTransfos, debug);
				OrccLogger.traceln("** Done in " + getDuration(t0) + "s");
			}
			stopIfRequested();
			beforeGeneration(network);

			stopIfRequested();
			OrccLogger.traceln("** Network validation");
			doValidate(network);

			stopIfRequested();
			OrccLogger.traceln("** Network generation");
			long t0 = System.currentTimeMillis();
			Result result = doGenerateNetwork(network);
			result.merge(doAdditionalGeneration(network));
			OrccLogger.traceln("** Done in " + getDuration(t0) + "s. " + result);

			if (!childrenTransfos.isEmpty()) {
				stopIfRequested();
				OrccLogger.traceln("** Children transformations");
				t0 = System.currentTimeMillis();
				applyTransformations(network.getAllActors(), childrenTransfos, debug);
				OrccLogger.traceln("** Done in " + getDuration(t0) + "s");
			}

			stopIfRequested();
			OrccLogger.traceln("** Children generation");
			t0 = System.currentTimeMillis();
			result = Result.newInstance();
			for (final Vertex vertex : network.getChildren()) {
				stopIfRequested();
				final Instance instance = vertex.getAdapter(Instance.class);
				final Actor actor = vertex.getAdapter(Actor.class);
				if (instance != null) {
					beforeGeneration(instance);
					result.merge(doGenerateInstance(instance));
					result.merge(doAdditionalGeneration(instance));
				} else if (actor != null) {
					beforeGeneration(actor);
					result.merge(doGenerateActor(actor));
					result.merge(doAdditionalGeneration(actor));
				}
			}
			
			// If user checked the option "Don't export library", the method
			// extractLibraries() must not be called
			if (!getOption(NO_LIBRARY_EXPORT, false)) {
				stopIfRequested();
				t0 = System.currentTimeMillis();
				result = doLibrariesExtraction();
				if (!result.isEmpty()) {
					OrccLogger.traceln("Library export done in " + getDuration(t0) + "s");
				}
			}			

			OrccLogger.traceln("** Done in " + getDuration(t0) + "s. " + result);

			afterGeneration(network);

			OrccLogger.traceln("** " + backendName + " done.");
		} else {
			super.compile(progressMonitor);
		}

	}

}
