package net.sf.orcc.backends.iot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import net.sf.orcc.backends.util.xcf.Configuration;
import net.sf.orcc.backends.util.xcf.Configuration.Partitioning.Partition;
import net.sf.orcc.backends.util.xcf.XCFManager;
//import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.graph.util.Dota;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.util.OrccLogger;

public class Partitioner {

	private Network network;
	private XCFManager manager;

	HashMap<Partition, List<Vertex>> instancesByPartition;
	HashMap<Partition, Network> networksByPartition;
	private int nextPortID;
	private HashSet<EdgeCacheKey> edgeCache;
	private HashMap<EdgeCacheKey, Integer> portsByEdge;
	private String graphPath;

	public Partitioner(Network network, XCFManager manager) {
		this.network = network;
		this.manager = manager;
	}

	public Partitioner(Network network, XCFManager manager, String graphPath) {
		this.network = network;
		this.manager = manager;
		this.graphPath = graphPath;
	}

	public void partitionNetwork() throws IOException {
		instancesByPartition = new HashMap<Partition, List<Vertex>>();
		networksByPartition = new HashMap<Partition, Network>();

		portsByEdge = new HashMap<EdgeCacheKey, Integer>();
		edgeCache = new HashSet<EdgeCacheKey>();
		OrccLogger.traceln("*** getting all nodes");
//First sort the nodes into partitions and create the subnetworks. Create clones of the nodes into each subnetwork. For the moment these clones
//are not connected, we will deal with it later
		int networkCtr = 1;
		nextPortID = 1;
		for (Vertex vertex : network.getChildren()) {
			Instance instance = vertex.getAdapter(Instance.class);
			// INFO: You do not need that here, Instance contains an actor
			// Actor actor = vertex.getAdapter(Actor.class);
			OrccLogger.traceln("vertex: " + vertex + "; class: " + vertex.getClass().toString());
// Check out, which partitions are associated with an instance. Multi-instance actors may exist in more than one partitions
			List<Partition> partitions = manager.getPartitionsByInstanceId(instance.getName());
			if (partitions.isEmpty())
				OrccLogger.traceln("Instance: " + instance.getName() + " is not allocated to any partition");
			else {
// Create a clone of the instance in every partition it exists in
				for( Partition partition: partitions ) {
					List<Vertex> vertexList = instancesByPartition.get(partition);
//vertexList contains the initial nodes. They are still needed for their connections
					if (vertexList == null) {
						vertexList = new ArrayList<Vertex>();
						instancesByPartition.put(partition, vertexList);
					}
					vertexList.add(vertex);
					OrccLogger.traceln("Instance: " + instance.getName() + " is allocated to partition " + partition);
//Subnetwork objects store the cloned instances. For the moment, these cloned instances are left unconnected.
					Network networkForPartition = networksByPartition.get(partition);
					if (networkForPartition == null) {
						networkForPartition = DfFactory.eINSTANCE.createNetwork();
						networkForPartition.setName("subnetwork" + networkCtr);  // NTa removed "_" from "subnetwork" maven complains
						networksByPartition.put(partition, networkForPartition);
						++networkCtr;
					}
//Clone the instance and add it to the subnetwork
					Instance clonedInstance = EcoreUtil.copy(instance);
					networkForPartition.add(clonedInstance);
				}
			}
		}
//Iterate through the partitions and connect the nodes inside the subnetworks 
		for (Iterator<Partition> it = instancesByPartition.keySet().iterator(); it.hasNext();) {
			Partition partition = it.next();
			Network networkForPartition = networksByPartition.get(partition);
			if (networkForPartition == null) {
				OrccLogger.severeln("Error: subnetwork was not created for partition " + partition);
				break;
			}
			Configuration.Backends.Backend backend = manager.getBackendByPartition(partition);
			OrccLogger.traceln(networkForPartition.getName() + ", backend id:" + backend.getId() + ", backend name: "
					+ backend.getBackend());
//Now take all the vertices from the original graph and connect the corresponding cloned vertices in the subnetworks too
			List<Vertex> instanceList = instancesByPartition.get(partition);
			for (Iterator<Vertex> it2 = instanceList.iterator(); it2.hasNext();) {
				Vertex vertex = it2.next();
				Instance instance = vertex.getAdapter(Instance.class);
				String instanceName = instance.getName();
				OrccLogger.traceln("  instance: " + instance.getName()+" in network "+networkForPartition.getName());
				Instance clonedInstance = (Instance) networkForPartition.getChild(instanceName);
				if (clonedInstance == null) {
					OrccLogger.severeln("Error: cloned instance is not found in subnetwork: " + instanceName);
					break;
				}
// Check the edges incoming to the actor whether they are from the same partition. If the edge is from an actor in the same partition, create the same edge 
// into the subnetwork. If not, create an input port.
				EList<Edge> connecting = vertex.getConnecting();
				for (Iterator<Edge> it3 = connecting.iterator(); it3.hasNext();) {
					Connection e = (Connection)it3.next();
					Vertex iv = e.getSource();
// Funnily the input network has edges that point back to the instance itself. Don't clone those
					if( iv == vertex )
						continue;
					Instance incomingInstance = iv.getAdapter(Instance.class);
					String incomingInstanceName = incomingInstance.getName();
					for( Network incomingInstanceNetwork : getNetworksForInstanceID( incomingInstanceName ) ) {
						if ( edgeVisited(e,incomingInstanceNetwork,networkForPartition))
							continue;
//If the connecting vertex is also in the same subnetwork, leave the edge as it is
						if ( networkForPartition == incomingInstanceNetwork ) {
							Instance clonedIncomingInstance = (Instance) networkForPartition.getChild(incomingInstanceName);
							if (clonedIncomingInstance == null) {
								OrccLogger.severeln("Error: cloned incoming instance is not found in subnetwork: "
									+ incomingInstanceName);
								break;
							}
							OrccLogger.traceln(
								"    creating connection from " + incomingInstanceName + 
								"." + e.getSourcePort().getName() +
								" to " + instanceName+"."+e.getTargetPort().getName());
							Connection clonedConnection = 
								DfFactory.eINSTANCE.createConnection(clonedIncomingInstance, e.getSourcePort(),
									clonedInstance, e.getTargetPort());
							networkForPartition.add(clonedConnection);
// Else connect it to an input port
						} else {
//							Type typePortIN = IrFactory.eINSTANCE.createTypeInt(32);
							int portID = getPortIDByEdge(e,incomingInstanceNetwork,networkForPartition);
// The created input port has the same type as the actor's port it is connected to
							Type typePortIN = cloneType( e.getTargetPort().getType() );
							OrccLogger.traceln( "Creating input port with type: "+typePortIN );
							Port p = DfFactory.eINSTANCE.createPort(
								typePortIN, 
								"i_port" + Integer.toString(portID));
							networkForPartition.addInput(p);
							Connection portConnection = DfFactory.eINSTANCE.createConnection(p,null,
								clonedInstance, e.getTargetPort());
							networkForPartition.add(portConnection);
							OrccLogger.traceln("    incoming connection from: " + 
								incomingInstanceName + "." + e.getSourcePort().getName() +
								" to "+instanceName+"."+e.getTargetPort().getName()+
								" in network "+incomingInstanceNetwork.getName()+
								" was replaced by port " + p.getName() );
						}
					}
				}
//Check the edges incoming to the actor whether they are from the same partition. If the edge is from an actor in the same partition, copy the
//edge into the subnetwork. If not, create an input port.
				EList<Edge> outgoing = vertex.getOutgoing();
				for (Iterator<Edge> it3 = outgoing.iterator(); it3.hasNext();) {
					Connection e = (Connection)it3.next();
					Vertex ov = e.getTarget();
					if( ov == vertex) 
						continue;
					Instance outgoingInstance = ov.getAdapter(Instance.class);
					String outgoingInstanceName = outgoingInstance.getName();
					for( Network outgoingInstanceNetwork : getNetworksForInstanceID( outgoingInstanceName ) ) {
						if ( edgeVisited(e,networkForPartition,outgoingInstanceNetwork))
							continue;
//If the connecting vertex is also in the same subnetwork, leave the edge as it is
						if ( networkForPartition == outgoingInstanceNetwork ) {
							Instance clonedOutgoingInstance = (Instance) networkForPartition.getChild(outgoingInstanceName);
							if (clonedOutgoingInstance == null) {
								OrccLogger.severeln("Error: cloned outgoing instance is not found in subnetwork: "
									+ outgoingInstanceName);
								break;
							}
							OrccLogger.traceln(
								"    creating connection from " + instanceName + "."+e.getSourcePort().getName()+
								" to " + outgoingInstanceName+"."+e.getTargetPort().getName());
							Connection clonedConnection = DfFactory.eINSTANCE.createConnection(
									clonedInstance, e.getSourcePort(),
									clonedOutgoingInstance, e.getTargetPort());
							networkForPartition.add(clonedConnection);
// Else connect it to an output port
						} else {
//							Type typePortIN = IrFactory.eINSTANCE.createTypeInt(32);
							int portID = getPortIDByEdge(e,networkForPartition,outgoingInstanceNetwork);
// The created output port has the same type as the actor's port it is connected to
							Type typePortOUT = cloneType( e.getSourcePort().getType() );
							OrccLogger.traceln( "Creating output port with type: "+typePortOUT );
							Port p = DfFactory.eINSTANCE.createPort( 
								typePortOUT, 
								"o_port" + Integer.toString(portID));
							networkForPartition.addOutput(p);
							Connection portConnection = DfFactory.eINSTANCE.createConnection(
									clonedInstance, e.getSourcePort(),
									p,null);
							networkForPartition.add(portConnection);
							OrccLogger.traceln("    outgoing connection from: " + instanceName + 
										"."+e.getSourcePort().getName()+
										" to " + outgoingInstanceName+"."+e.getTargetPort().getName()+
										" in network "+outgoingInstanceNetwork.getName()+
										" was replaced by port " + p.getName());
						}
					}
				}
			}
		}
//print out the subnetworks
		networkCtr = 1;
		for (Iterator<Partition> it = networksByPartition.keySet().iterator(); it.hasNext();) {
			Partition partition = it.next();
			Network subNetwork = networksByPartition.get(partition);
			dumpNetwork(subNetwork, "subnetwork" + Integer.toString(networkCtr) + ".dot", graphPath);
			++networkCtr;
		}

	}

	/**
	 * Gets the port ID associated to an edge. If the edge already has a mapping,
	 * return that mapped ID. If the edge does not yet have a mapping, a new ID is
	 * allocated for the edge, the ID is stored in the mapping table and then
	 * returned to the caller.
	 */
	private int getPortIDByEdge(Edge e,Network iNetwork,Network oNetwork) {
		EdgeCacheKey key = new EdgeCacheKey( e,iNetwork,oNetwork );
		Integer id = portsByEdge.get(key);
		if (id == null) {
			id = new Integer(nextPortID++);
			portsByEdge.put(key, id);
		}
		return id.intValue();
	}

	public static void dumpNetwork(Network subNetwork, String networkName, String graphPath) throws IOException {
		File gf = new File(graphPath, networkName);
		PrintWriter pw = new PrintWriter(gf);
		Dota dot = new Dota();
		pw.println(dot.dot(subNetwork));
		pw.close();
	}

	private boolean edgeVisited(Edge edge, Network iNetwork, Network oNetwork ) {
		EdgeCacheKey key = new EdgeCacheKey( edge, iNetwork, oNetwork );
		// If the edge traverses a partition boundary (hence has ports), connections
		// must always be created
		if (portsByEdge.containsKey(key))
			return false;
		// Otherwise if connection for the edge has already been created, skip it
		if (edgeCache.contains(key))
			return true;
		// New edge, connection will be created, put it into the cache
		edgeCache.add(key);
		return false;
	}
	
// Returns all the subnetworks in which the instance with the given instance ID is present. Multi-instance actors may have instances in multiple subnetworks.
	private List<Network> getNetworksForInstanceID( String instanceID ) {
		ArrayList<Network> result = new ArrayList<Network>();
		for (Iterator<Partition> it = networksByPartition.keySet().iterator(); it.hasNext();) {
			Partition partition = it.next();
			Network networkForPartition = getSubNetwork(partition);
			if( networkForPartition.getChild( instanceID ) != null )
				result.add( networkForPartition );
		}
		return result;
	}
	
	private Type cloneType( Type orig ) {
		Type rt = null;
		if( orig.isInt() )
			rt = IrFactory.eINSTANCE.createTypeInt(((TypeInt)orig).getSize());
		else
		if( orig.isFloat() )
			rt = IrFactory.eINSTANCE.createTypeFloat(((TypeFloat)orig).getSize());
		else
		if( orig.isString() )
			rt = IrFactory.eINSTANCE.createTypeString();
		else
			rt = IrFactory.eINSTANCE.createTypeVoid();
		return rt;
	}
	
	public Network getSubNetwork(Partition partition) {
		return networksByPartition.get(partition);
	}
	
	public Map<Partition, Network> getNetworksByPartition(){
		return networksByPartition;
	}
	
	
	
	class EdgeCacheKey {
		public EdgeCacheKey( Edge edge, Network iNetwork, Network oNetwork ) {
			this.edge = edge;
			this.iNetwork = iNetwork;
			this.oNetwork = oNetwork;
		}
		
		public boolean equals( Object other ) {
			if( other instanceof EdgeCacheKey ) {
				EdgeCacheKey otherKey = (EdgeCacheKey)other;
				boolean result =  ( getEdge().toString().equals( otherKey.getEdge().toString() ) ) && 
					( iNetwork.getName().equals( otherKey.getINetwork().getName() ) ) &&
					( oNetwork.getName().equals( otherKey.getONetwork().getName() ) );
				return result;
			}
			return false;
		}
		
		public int hashCode() {
			return getEdge().toString().hashCode() * 
					iNetwork.getName().toString().hashCode() * 
					oNetwork.getName().toString().hashCode();
		}
		
		public Edge getEdge() {
			return edge;
		}
		
		public Network getINetwork() {
			return iNetwork;
		}
		
		public Network getONetwork() {
			return oNetwork;
		}
		
		public String toString() {
			return "Edge: "+edge+"; iNetwork: "+iNetwork.getName()+"; oNetwork: "+oNetwork.getName(); 
		}
		
		private Edge edge;
		private Network iNetwork;
		private Network oNetwork;
	}

	
}
