/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.orcc.backends.util.xcf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sf.orcc.backends.util.xcf.Configuration.Backends.Backend;
import net.sf.orcc.backends.util.xcf.Configuration.Partitioning.Partition;

/**
 *
 * @author Gabor Farkas
 * @author Endri Bezati
 * @author Gabor Paller
 */
public class XCFManager {

	private File file;
	private Configuration configuration;
	private Map<net.sf.orcc.backends.Backend, Map<String, Object>> backendOptions;

	public XCFManager(File file) throws Exception {
		this.file = file;
		this.backendOptions = new HashMap<net.sf.orcc.backends.Backend, Map<String, Object>>();
		read();
	}

	private void read() throws Exception {
		JAXBContext context = JAXBContext.newInstance(Configuration.class);
		Unmarshaller unmsarshaller = context.createUnmarshaller();
		configuration = (Configuration) unmsarshaller.unmarshal(file);
	}

	public void write(File out) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Configuration.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(configuration, out);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Returns the first partition from the specified {@code conf} configuration
	 * which represents a specified {@code instanceId}.
	 *
	 * @param conf       the specified configuration
	 * @param instanceId the specified instance id
	 * @return the first partition from the specified {@code conf} configuration
	 *         which represents a specified {@code instanceId} or {@code null} if
	 *         not found.
	 */
	public Partition getPartitionByInstanceId(String instanceId) {
		for (Partition p : configuration.getPartitioning().getPartition()) {
			for (Partition.Instance ins : p.getInstance()) {
				if (ins.getId().equals(instanceId)) {
					return p;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the list of partitions from the specified {@code conf} configuration
	 * which represents a specified {@code instanceId}.
	 *
	 * @param conf       the specified configuration
	 * @param instanceId the specified instance id
	 * @return list of partitions from the specified {@code conf} configuration
	 *         which represents a specified {@code instanceId} or {@code null} if
	 *         not found.
	 */
	public List<Partition> getPartitionsByInstanceId(String instanceId) {
		ArrayList<Partition> result = new ArrayList<Partition>();
		for (Partition p : configuration.getPartitioning().getPartition()) {
			for (Partition.Instance ins : p.getInstance()) {
				if (ins.getId().equals(instanceId)) {
					result.add( p );
				}
			}
		}
		return result;
	}

	/**
	 * Returns the first backend which represents the specified {@code p} partition.
	 *
	 * @param conf the configurations
	 * @param p    the specified partition
	 * @return the first backend which represents the specified {@code p} partition
	 *         or {@code null} if not found.
	 */
	public Backend getBackendByPartition(Partition p) {
		for (Backend b : configuration.getBackends().getBackend()) {
			if (b.getId().equals(p.getBackendId())) {
				return b;
			}
		}
		return null;
	}

	/**
	 * Returns the fifo connections which src represents the specified
	 * {@code instanceId}.
	 *
	 * @param conf       the input configuration
	 * @param instanceId the specified instance id
	 * @return the fifo connections which src represents the specified
	 *         {@code instanceId}. If no fifo connections returns empty list.
	 */
	public List<Configuration.Connections.FifoConnection> getConnectionByInstanceId(String instanceId) {
		List out = new ArrayList();
		for (Configuration.Connections.FifoConnection f : configuration.getConnections().getFifoConnection()) {
			if (f.getSrc().equals(instanceId)) {
				out.add(f);
			}
		}
		return out;
	}
}
