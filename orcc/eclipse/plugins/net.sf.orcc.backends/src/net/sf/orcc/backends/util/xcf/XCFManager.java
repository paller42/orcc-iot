package net.sf.orcc.backends.util.xcf;

import org.w3c.dom.*;
import javax.xml.*;
import javax.xml.parsers.*;

import javax.xml.validation.*;
import java.io.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import net.sf.orcc.backends.util.xcf.Configuration;

public class XCFManager {
    Document document;
    File file;
    Configuration configuration;
    static final boolean DEBUG = false;

    private Map<net.sf.orcc.backends.Backend, Map<String, Object>> backendOptions;

	public XCFManager(File file) throws Exception {
		this.file = file;
		this.backendOptions = new HashMap<net.sf.orcc.backends.Backend, Map<String, Object>>();
		read();
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
	public Configuration.Partitioning.Partition getPartitionByInstanceId(String instanceId) {
		for (Configuration.Partitioning.Partition p : configuration.getPartitioning().getPartition()) {
			for (Configuration.Partitioning.Partition.Instance ins : p.getInstance()) {
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
	public List<Configuration.Partitioning.Partition> getPartitionsByInstanceId(String instanceId) {
		ArrayList<Configuration.Partitioning.Partition> result = new ArrayList<Configuration.Partitioning.Partition>();
		for (Configuration.Partitioning.Partition p : configuration.getPartitioning().getPartition()) {
			for (Configuration.Partitioning.Partition.Instance ins : p.getInstance()) {
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
	public Configuration.Backends.Backend getBackendByPartition(Configuration.Partitioning.Partition p) {
		for (Configuration.Backends.Backend b : configuration.getBackends().getBackend()) {
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
	
	public Element getTag( Element root, String tagName ) throws SAXException {
		NodeList l = root.getElementsByTagName( tagName );
		if( l.getLength() < 1 )
			throw new SAXException( "no '"+tagName+"' element in "+root.getNodeName() );
		return (Element)l.item(0);
	}

	public void buildConfiguration() throws SAXException {
		Element root = document.getDocumentElement();
		configuration = new Configuration();
		Element networkTag = getTag( root, "Network" );
		String fileFormat = networkTag.getAttribute( "file-format" );
		String qualifiedId = networkTag.getAttribute( "qualified-id" );
		Configuration.Network network = new Configuration.Network();
		network.setFileFormat( fileFormat );
		network.setQualifiedId( qualifiedId );
		configuration.setNetwork( network );

// <Partitioning>		
		Element partitioningTag = getTag( root,"Partitioning" );
		ArrayList<Configuration.Partitioning.Partition> partitions = new ArrayList<Configuration.Partitioning.Partition>();
		NodeList l = partitioningTag.getElementsByTagName( "Partition" );
		for( int i = 0 ; i < l.getLength() ; ++i ) {
			Element partitionTag = (Element)l.item(i);
			String id = partitionTag.getAttribute("id");
			String pe = partitionTag.getAttribute("pe");
			String backendId = partitionTag.getAttribute("backend-id");
			String host = partitionTag.getAttribute("host");
			NodeList l1 = partitionTag.getElementsByTagName( "Instance" );
			ArrayList<Configuration.Partitioning.Partition.Instance> instances = 
						new ArrayList<Configuration.Partitioning.Partition.Instance>();
			for( int n = 0 ; n < l1.getLength() ; ++n ) {
				Element instanceTag = (Element)l1.item(n);
				String iid = instanceTag.getAttribute("id");
				Configuration.Partitioning.Partition.Instance instance = new Configuration.Partitioning.Partition.Instance();
				instance.setId( iid );
				if( DEBUG )
                    System.out.println( "Instance id: "+iid );
				instances.add( instance );
			}
			Configuration.Partitioning.Partition partition = new Configuration.Partitioning.Partition();
			partition.setInstance( instances );
			partition.setId( id );
			partition.setPe( pe );
			partition.setBackendId( backendId );
			partition.setHost( host );
			if( DEBUG )
                System.out.println( "Partition id: "+id+"; pe: "+pe+"; backendId: "+backendId+"; host: "+host );
			partitions.add( partition );
		}
		Configuration.Partitioning partitioning = new Configuration.Partitioning();
		partitioning.setPartition( partitions );
		configuration.setPartitioning( partitioning );
		
// <Backends>
		Element backendsTag = getTag( root,"Backends" );
		ArrayList<Configuration.Backends.Backend> backendList = new ArrayList<Configuration.Backends.Backend>();
		l = backendsTag.getElementsByTagName( "Backend" );
		for( int i = 0 ; i < l.getLength() ; ++i ) {
			Element backendTag = (Element)l.item(i);
			String id = backendTag.getAttribute("id");
			String backendParm = backendTag.getAttribute("backend");
			NodeList l1 = backendTag.getElementsByTagName( "Parameter" );
			Configuration.Backends.Backend backend = new Configuration.Backends.Backend();
			backend.setId( id );
			backend.setBackend( backendParm );
			if( l1.getLength() > 0 ) {
				Element parameterTag = (Element)l1.item(0);
				String key = parameterTag.getAttribute("key");
				String value = parameterTag.getAttribute("value");
				Configuration.Backends.Backend.Parameter parameter = new Configuration.Backends.Backend.Parameter();
				parameter.setKey( key );
				parameter.setValue( value );
				if( DEBUG )
                    System.out.println( "Parameter key: "+key+"; value: "+value );
				backend.setParameter( parameter );
			}
			if( DEBUG )
                System.out.println( "Backend id: "+id+"; backend: "+backendParm );
			backendList.add( backend );
		}
		Configuration.Backends backends = new Configuration.Backends();
		backends.setBackend( backendList );
		configuration.setBackends( backends );

// <Media>
		Element mediaTag = getTag( root,"Media" );
		ArrayList<Configuration.Media.Interface> interfaceList = new ArrayList<Configuration.Media.Interface>();
		l = mediaTag.getElementsByTagName( "Interface" );
		for( int i = 0 ; i < l.getLength() ; ++i ) {
			Element interfaceTag = (Element)l.item(i);
			String id = interfaceTag.getAttribute("id");
			String medium = interfaceTag.getAttribute("medium");
			NodeList l1 = interfaceTag.getElementsByTagName( "Parameter" );
			Configuration.Media.Interface _interface = new Configuration.Media.Interface();
			_interface.setId( id );
			_interface.setMedium( medium );
			if( l1.getLength() > 0 ) { 
				Element parameterTag = (Element)l1.item(0);
				String key = parameterTag.getAttribute("key");
				String value = parameterTag.getAttribute("value");
				Configuration.Media.Interface.Parameter parameter = new Configuration.Media.Interface.Parameter();
				parameter.setKey( key );
				parameter.setValue( value );
				if( DEBUG )
                    System.out.println( "Parameter key: "+key+"; value: "+value );
				_interface.setParameter( parameter );
			}
			if( DEBUG )
                System.out.println( "Interface id: "+id+"; medium: "+medium );
			interfaceList.add( _interface );
		}
		Configuration.Media media = new Configuration.Media();
		media.setInterface( interfaceList );
		configuration.setMedia( media );

// <Connections>
		Element connectionsTag = getTag( root,"Connections" );
		ArrayList<Configuration.Connections.FifoConnection> fifoList = 
				new ArrayList<Configuration.Connections.FifoConnection>();
		l = connectionsTag.getElementsByTagName( "Fifo-Connection" );
		for( int i = 0 ; i < l.getLength() ; ++i ) {
			Element fifoTag = (Element)l.item(i);
			String src = fifoTag.getAttribute("src");
			String srcPort = fifoTag.getAttribute("src-port");
			String dst = fifoTag.getAttribute("dst");
			String dstPort = fifoTag.getAttribute("dst-port");
			int size = 0;
			try {
				size = Integer.parseInt( fifoTag.getAttribute("size") );
			} catch( NumberFormatException ex ) {
				System.err.println( fifoTag.getAttribute("size")+" cannot be converted to integer" );
			}
			String mediumId = fifoTag.getAttribute("medium-id");
			Configuration.Connections.FifoConnection fifo = new Configuration.Connections.FifoConnection();
			fifo.setSrc( src );
			fifo.setSrcPort( srcPort );
			fifo.setDst( dst );
			fifo.setDstPort( dstPort );
			fifo.setSize( size );
			fifo.setMediumId( mediumId );
			if( DEBUG )
                System.out.println( "Fifo src: "+src+"; srcPort: "+srcPort+
					"; dst: "+dst+"; dstPort: "+dstPort+"; size: "+size+"; mediumId: "+mediumId );
			fifoList.add( fifo );
		}
		Configuration.Connections connections = new Configuration.Connections();
		connections.setFifoConnection( fifoList );
		configuration.setConnections( connections );

	}
	
	private void testAPI() throws SAXException {
		Configuration.Partitioning.Partition p = getPartitionByInstanceId("outdoor_air_temperature");
		if( p == null )
			throw new SAXException( "Partition==null" );
		if( !p.getId().equals( "air_temp_1" ) )
			throw new SAXException( "Partition id!");
		if( !p.getPe().equals( "ARM" ) )
			throw new SAXException( "Partition pe!");
		if( !p.getBackendId().equals( "Java 0" ) )
			throw new SAXException( "Partition backend id!");
		Configuration.Backends.Backend b = getBackendByPartition(p);
		if( b == null )
			throw new SAXException( "Backend==null" );
		if( !b.getBackend().equals( "Java Vanilla" ) )
			throw new SAXException( "Backend backend!");
		Configuration.Backends.Backend.Parameter parm = b.getParameter();
		if( !parm.getKey().equals( "Board" ) )
			throw new SAXException( "Parameter key!");
		if( !parm.getValue().equals( "XX" ) )
			throw new SAXException( "Parameter value!");
		List<Configuration.Partitioning.Partition> l = getPartitionsByInstanceId("pid_controller");
		if( l.size() != 2 )
			throw new SAXException( "partitions size: "+Integer.toString( l.size() ) );
		for( int i = 0 ; i < l.size() ; ++i ) {
			Configuration.Partitioning.Partition p1 = l.get(i);
			if( !p1.getId().equals("server_0") && !p1.getId().equals("server_1") )
				throw new SAXException("Partitions id: "+p1.getId() );
		}
		List<Configuration.Connections.FifoConnection> l2 = getConnectionByInstanceId("outdoor_air_temperature");
		if( l2.size() != 1 )
			throw new SAXException( "connections size: "+Integer.toString( l2.size() ) );
		Configuration.Connections.FifoConnection fc = l2.get(0);
		if( !fc.getDst().equals("weighted_average") )
			throw new SAXException( "connections dst: "+fc.getDst());
	}
	
    public void read() throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse( file );
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory schemaFactory = SchemaFactory.newInstance(language);
            Schema schema = schemaFactory.newSchema(new StreamSource(new StringReader(xcfSchema)));
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));
            if( DEBUG )
                System.out.println( "Validated successfully");
        } catch( SAXParseException e ) {
            System.err.println( "Parse/validation error: "+e.getMessage() );
            throw e;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        try {
			buildConfiguration();
        } catch( SAXException ex ) {
			System.err.println( "SAXException: "+ex.getMessage());
			throw ex;
        }
    }

	private String xcfSchema =
"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+
"<xs:schema attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n"+
"  <xs:element name=\"Configuration\">\n"+
"    <xs:complexType>\n"+
"      <xs:sequence>\n"+
"        <xs:element name=\"Network\">\n"+
"          <xs:complexType>\n"+
"            <xs:attribute name=\"file-format\" type=\"xs:string\" use=\"required\" />\n"+
"            <xs:attribute name=\"qualified-id\" type=\"xs:string\" use=\"required\" />\n"+
"          </xs:complexType>\n"+
"        </xs:element>\n"+
"        <xs:element name=\"Partitioning\">\n"+
"          <xs:complexType>\n"+
"            <xs:sequence>\n"+
"              <xs:element maxOccurs=\"unbounded\" name=\"Partition\">\n"+
"                <xs:complexType>\n"+
"                  <xs:sequence>\n"+
"                    <xs:element maxOccurs=\"unbounded\" name=\"Instance\">\n"+
"                      <xs:complexType>\n"+
"                        <xs:attribute name=\"id\" type=\"xs:string\" use=\"required\" />\n"+
"                      </xs:complexType>\n"+
"                    </xs:element>\n"+
"                  </xs:sequence>\n"+
"                  <xs:attribute name=\"id\" type=\"xs:string\" use=\"required\" />\n"+
"                  <xs:attribute name=\"pe\" type=\"xs:string\" use=\"required\" />\n"+
"                  <xs:attribute name=\"backend-id\" type=\"xs:string\" use=\"required\" />\n"+
"                  <xs:attribute name=\"host\" type=\"xs:string\" use=\"optional\" />\n"+
"                </xs:complexType>\n"+
"              </xs:element>\n"+
"            </xs:sequence>\n"+
"          </xs:complexType>\n"+
"        </xs:element>\n"+
"        <xs:element name=\"Backends\">\n"+
"          <xs:complexType>\n"+
"            <xs:sequence>\n"+
"              <xs:element maxOccurs=\"unbounded\" name=\"Backend\">\n"+
"                <xs:complexType>\n"+
"                  <xs:sequence minOccurs=\"0\">\n"+
"                    <xs:element name=\"Parameter\">\n"+
"                      <xs:complexType>\n"+
"                        <xs:attribute name=\"key\" type=\"xs:string\" use=\"required\" />\n"+
"                        <xs:attribute name=\"value\" type=\"xs:string\" use=\"required\" />\n"+
"                      </xs:complexType>\n"+
"                    </xs:element>\n"+
"                  </xs:sequence>\n"+
"                  <xs:attribute name=\"id\" type=\"xs:string\" use=\"required\" />\n"+
"                  <xs:attribute name=\"backend\" type=\"xs:string\" use=\"required\" />\n"+
"                </xs:complexType>\n"+
"              </xs:element>\n"+
"            </xs:sequence>\n"+
"          </xs:complexType>\n"+
"        </xs:element>\n"+
"        <xs:element name=\"Media\">\n"+
"          <xs:complexType>\n"+
"            <xs:sequence>\n"+
"              <xs:element maxOccurs=\"unbounded\" name=\"Interface\">\n"+
"                <xs:complexType>\n"+
"                  <xs:sequence>\n"+
"                    <xs:element name=\"Parameter\">\n"+
"                      <xs:complexType>\n"+
"                        <xs:attribute name=\"key\" type=\"xs:string\" use=\"required\" />\n"+
"                        <xs:attribute name=\"value\" type=\"xs:string\" use=\"required\" />\n"+
"                      </xs:complexType>\n"+
"                    </xs:element>\n"+
"                  </xs:sequence>\n"+
"                  <xs:attribute name=\"id\" type=\"xs:string\" use=\"required\" />\n"+
"                  <xs:attribute name=\"medium\" type=\"xs:string\" use=\"required\" />\n"+
"                </xs:complexType>\n"+
"              </xs:element>\n"+
"            </xs:sequence>\n"+
"          </xs:complexType>\n"+
"        </xs:element>\n"+
"        <xs:element name=\"Connections\">\n"+
"          <xs:complexType>\n"+
"            <xs:sequence>\n"+
"              <xs:element maxOccurs=\"unbounded\" name=\"Fifo-Connection\">\n"+
"                <xs:complexType>\n"+
"                  <xs:attribute name=\"src\" type=\"xs:string\" use=\"required\" />\n"+
"                  <xs:attribute name=\"src-port\" type=\"xs:string\" use=\"required\" />\n"+
"                  <xs:attribute name=\"dst\" type=\"xs:string\" use=\"required\" />\n"+
"                  <xs:attribute name=\"dst-port\" type=\"xs:string\" use=\"optional\" />\n"+
"                  <xs:attribute name=\"size\" type=\"xs:unsignedShort\" use=\"required\" />\n"+
"                  <xs:attribute name=\"medium-id\" type=\"xs:string\" use=\"required\" />\n"+
"                  <xs:attribute name=\"dstport\" type=\"xs:string\" use=\"optional\" />\n"+
"                </xs:complexType>\n"+
"              </xs:element>\n"+
"            </xs:sequence>\n"+
"          </xs:complexType>\n"+
"        </xs:element>\n"+
"      </xs:sequence>\n"+
"    </xs:complexType>\n"+
"  </xs:element>\n"+
"</xs:schema>";
}
