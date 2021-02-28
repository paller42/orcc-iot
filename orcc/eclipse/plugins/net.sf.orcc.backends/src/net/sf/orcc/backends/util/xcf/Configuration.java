package net.sf.orcc.backends.util.xcf;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Network">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="file-format" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="qualified-id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Partitioning">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Partition" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Instance" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="pe" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="backend-id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="host" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Backends">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Backend" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="Parameter">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="backend" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Media">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Interface">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Parameter">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="medium" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Connections">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Fifo-Connection" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="src" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="src-port" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="dst" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="dst-port" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *                           &lt;attribute name="medium-id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="dstport" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class Configuration {

    protected Configuration.Network network;
    protected Configuration.Partitioning partitioning;
    protected Configuration.Backends backends;
    protected Configuration.Media media;
    protected Configuration.Connections connections;

    /**
     * Gets the value of the network property.
     * 
     * @return
     *     possible object is
     *     {@link Configuration.Network }
     *     
     */
    public Configuration.Network getNetwork() {
        return network;
    }

    /**
     * Sets the value of the network property.
     * 
     * @param value
     *     allowed object is
     *     {@link Configuration.Network }
     *     
     */
    public void setNetwork(Configuration.Network value) {
        this.network = value;
    }

    /**
     * Gets the value of the partitioning property.
     * 
     * @return
     *     possible object is
     *     {@link Configuration.Partitioning }
     *     
     */
    public Configuration.Partitioning getPartitioning() {
        return partitioning;
    }

    /**
     * Sets the value of the partitioning property.
     * 
     * @param value
     *     allowed object is
     *     {@link Configuration.Partitioning }
     *     
     */
    public void setPartitioning(Configuration.Partitioning value) {
        this.partitioning = value;
    }

    /**
     * Gets the value of the backends property.
     * 
     * @return
     *     possible object is
     *     {@link Configuration.Backends }
     *     
     */
    public Configuration.Backends getBackends() {
        return backends;
    }

    /**
     * Sets the value of the backends property.
     * 
     * @param value
     *     allowed object is
     *     {@link Configuration.Backends }
     *     
     */
    public void setBackends(Configuration.Backends value) {
        this.backends = value;
    }

    /**
     * Gets the value of the media property.
     * 
     * @return
     *     possible object is
     *     {@link Configuration.Media }
     *     
     */
    public Configuration.Media getMedia() {
        return media;
    }

    /**
     * Sets the value of the media property.
     * 
     * @param value
     *     allowed object is
     *     {@link Configuration.Media }
     *     
     */
    public void setMedia(Configuration.Media value) {
        this.media = value;
    }

    /**
     * Gets the value of the connections property.
     * 
     * @return
     *     possible object is
     *     {@link Configuration.Connections }
     *     
     */
    public Configuration.Connections getConnections() {
        return connections;
    }

    /**
     * Sets the value of the connections property.
     * 
     * @param value
     *     allowed object is
     *     {@link Configuration.Connections }
     *     
     */
    public void setConnections(Configuration.Connections value) {
        this.connections = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Backend" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence minOccurs="0">
     *                   &lt;element name="Parameter">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="backend" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class Backends {

        protected List<Configuration.Backends.Backend> backend;

        /**
         * Gets the value of the backend property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the backend property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBackend().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Configuration.Backends.Backend }
         * 
         * 
         */
        public List<Configuration.Backends.Backend> getBackend() {
            if (backend == null) {
                backend = new ArrayList<Configuration.Backends.Backend>();
            }
            return this.backend;
        }

        public void setBackend( List<Configuration.Backends.Backend> backend ) {
            this.backend = backend;
        }

        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence minOccurs="0">
         *         &lt;element name="Parameter">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="backend" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        public static class Backend {

            protected Configuration.Backends.Backend.Parameter parameter;
            protected String id;
            protected String backend;

            /**
             * Gets the value of the parameter property.
             * 
             * @return
             *     possible object is
             *     {@link Configuration.Backends.Backend.Parameter }
             *     
             */
            public Configuration.Backends.Backend.Parameter getParameter() {
                return parameter;
            }

            /**
             * Sets the value of the parameter property.
             * 
             * @param value
             *     allowed object is
             *     {@link Configuration.Backends.Backend.Parameter }
             *     
             */
            public void setParameter(Configuration.Backends.Backend.Parameter value) {
                this.parameter = value;
            }

            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setId(String value) {
                this.id = value;
            }

            /**
             * Gets the value of the backend property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBackend() {
                return backend;
            }

            /**
             * Sets the value of the backend property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBackend(String value) {
                this.backend = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            public static class Parameter {

                protected String key;
                protected String value;

                /**
                 * Gets the value of the key property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKey() {
                    return key;
                }

                /**
                 * Sets the value of the key property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKey(String value) {
                    this.key = value;
                }

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Fifo-Connection" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="src" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="src-port" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="dst" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="dst-port" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
     *                 &lt;attribute name="medium-id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="dstport" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class Connections {

        protected List<Configuration.Connections.FifoConnection> fifoConnection;

        /**
         * Gets the value of the fifoConnection property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the fifoConnection property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFifoConnection().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Configuration.Connections.FifoConnection }
         * 
         * 
         */
        public List<Configuration.Connections.FifoConnection> getFifoConnection() {
            if (fifoConnection == null) {
                fifoConnection = new ArrayList<Configuration.Connections.FifoConnection>();
            }
            return this.fifoConnection;
        }

        public void setFifoConnection( List<Configuration.Connections.FifoConnection> fifoConnection ) {
            this.fifoConnection = fifoConnection;
        }

        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="src" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="src-port" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="dst" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="dst-port" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
         *       &lt;attribute name="medium-id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="dstport" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        public static class FifoConnection {

            protected String src;
            protected String srcPort;
            protected String dst;
            protected String dstPort;
            protected int size;
            protected String mediumId;
            protected String dstport;

            /**
             * Gets the value of the src property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSrc() {
                return src;
            }

            /**
             * Sets the value of the src property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSrc(String value) {
                this.src = value;
            }

            /**
             * Gets the value of the srcPort property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSrcPort() {
                return srcPort;
            }

            /**
             * Sets the value of the srcPort property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSrcPort(String value) {
                this.srcPort = value;
            }

            /**
             * Gets the value of the dst property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDst() {
                return dst;
            }

            /**
             * Sets the value of the dst property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDst(String value) {
                this.dst = value;
            }

            /**
             * Gets the value of the dstPort property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDstPort() {
                return dstPort;
            }

            /**
             * Sets the value of the dstPort property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDstPort(String value) {
                this.dstPort = value;
            }

            /**
             * Gets the value of the size property.
             * 
             */
            public int getSize() {
                return size;
            }

            /**
             * Sets the value of the size property.
             * 
             */
            public void setSize(int value) {
                this.size = value;
            }

            /**
             * Gets the value of the mediumId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMediumId() {
                return mediumId;
            }

            /**
             * Sets the value of the mediumId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMediumId(String value) {
                this.mediumId = value;
            }

            /**
             * Gets the value of the dstport property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDstport() {
                return dstport;
            }

            /**
             * Sets the value of the dstport property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDstport(String value) {
                this.dstport = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Interface">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Parameter">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="medium" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class Media {

        protected List<Configuration.Media.Interface> _interface;

        /**
         * Gets the value of the interface property.
         * 
         * @return
         *     possible object is
         *     {@link Configuration.Media.Interface }
         *     
         */
        public List<Configuration.Media.Interface> getInterface() {
            return _interface;
        }

        /**
         * Sets the value of the interface property.
         * 
         * @param value
         *     allowed object is
         *     {@link Configuration.Media.Interface }
         *     
         */
        public void setInterface(List<Configuration.Media.Interface> value) {
            this._interface = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Parameter">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="medium" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        public static class Interface {

            protected Configuration.Media.Interface.Parameter parameter;
            protected String id;
            protected String medium;

            /**
             * Gets the value of the parameter property.
             * 
             * @return
             *     possible object is
             *     {@link Configuration.Media.Interface.Parameter }
             *     
             */
            public Configuration.Media.Interface.Parameter getParameter() {
                return parameter;
            }

            /**
             * Sets the value of the parameter property.
             * 
             * @param value
             *     allowed object is
             *     {@link Configuration.Media.Interface.Parameter }
             *     
             */
            public void setParameter(Configuration.Media.Interface.Parameter value) {
                this.parameter = value;
            }

            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setId(String value) {
                this.id = value;
            }

            /**
             * Gets the value of the medium property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMedium() {
                return medium;
            }

            /**
             * Sets the value of the medium property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMedium(String value) {
                this.medium = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            public static class Parameter {

                protected String key;
                protected String value;

                /**
                 * Gets the value of the key property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKey() {
                    return key;
                }

                /**
                 * Sets the value of the key property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKey(String value) {
                    this.key = value;
                }

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="file-format" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="qualified-id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class Network {

        protected String fileFormat;
        protected String qualifiedId;

        /**
         * Gets the value of the fileFormat property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFileFormat() {
            return fileFormat;
        }

        /**
         * Sets the value of the fileFormat property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFileFormat(String value) {
            this.fileFormat = value;
        }

        /**
         * Gets the value of the qualifiedId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQualifiedId() {
            return qualifiedId;
        }

        /**
         * Sets the value of the qualifiedId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQualifiedId(String value) {
            this.qualifiedId = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Partition" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Instance" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="pe" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="backend-id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="host" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class Partitioning {

        protected List<Configuration.Partitioning.Partition> partition;

        /**
         * Gets the value of the partition property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the partition property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPartition().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Configuration.Partitioning.Partition }
         * 
         * 
         */
        public List<Configuration.Partitioning.Partition> getPartition() {
            if (partition == null) {
                partition = new ArrayList<Configuration.Partitioning.Partition>();
            }
            return this.partition;
        }

        public void setPartition( List<Configuration.Partitioning.Partition> partition ) {
            this.partition = partition;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Instance" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="pe" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="backend-id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="host" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        public static class Partition {

            protected List<Configuration.Partitioning.Partition.Instance> instance;
            protected String id;
            protected String pe;
            protected String backendId;
            protected String host;

            /**
             * Gets the value of the instance property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the instance property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getInstance().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Configuration.Partitioning.Partition.Instance }
             * 
             * 
             */
            public List<Configuration.Partitioning.Partition.Instance> getInstance() {
                if (instance == null) {
                    instance = new ArrayList<Configuration.Partitioning.Partition.Instance>();
                }
                return this.instance;
            }

            
            public void setInstance( List<Configuration.Partitioning.Partition.Instance> instance ) {
                this.instance = instance;
            }
            
            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setId(String value) {
                this.id = value;
            }

            /**
             * Gets the value of the pe property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPe() {
                return pe;
            }

            /**
             * Sets the value of the pe property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPe(String value) {
                this.pe = value;
            }

            /**
             * Gets the value of the backendId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBackendId() {
                return backendId;
            }

            /**
             * Sets the value of the backendId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBackendId(String value) {
                this.backendId = value;
            }

            /**
             * Gets the value of the host property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHost() {
                return host;
            }

            /**
             * Sets the value of the host property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHost(String value) {
                this.host = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            public static class Instance {

                protected String id;

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setId(String value) {
                    this.id = value;
                }

            }

        }

    }

}
