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
 

package hu.sze.orcciot.backends.javaspring

import java.util.Properties;
import net.sf.orcc.df.Network;
import java.util.HashMap;
import net.sf.orcc.df.Actor
import java.util.HashSet

/**
	 * following script generators are implemented:
	 * 
	 * getPomXMLContent() generates the pom.xml which enables the compilation of generated Java Spring sources with Maven
	 * getSpringStarterFileContent() 	generates the content of the Spring starter class
	 * getQueueConfig()  generates configuration file for Spring boot queue class
	 * getQueueFileContent() generates the queue class for each port in the network
	 * getAzureProperties() generates the azure.properties file that is referenced from pom.xml
	 * getJMSProperties() generates the JMS configuration file 
	 * getSSHDConfig()     generates configuration script for SSHD for docker image 
	 * getInit()		generates initialization script that will start the application in cloud
	 * getDockerFile(String jarName) generates the Dockerfile for building docker image
	 * getWebSocketConfig(String networkName) generates the Web socket configuration class
	 * getSocketHandler(String networkName) generates the socket handler class
	 * getIDBRepository(String networkName)	generates the Interface class for Mongo DB
	 * getDBRepository(String networkName)	generates the repository class for Mongo DB
	 * getSpringAppProperties()	generates the Spring app configuration file
	 */

class JavaSpringScriptPrinter extends JavaScriptPrinter{
	String azure_resource_group
	String azure_eventhub_namespace
	String azure_acr_username
	String azure_acr_password
	String azure_amqp_uri
	String azure_db_name
	String azure_db_URI
	String networkSimpleName
	HashSet <String> nativeActSet = new HashSet <String>()
	
	override setNetwork(Network network) {
		this.networkSimpleName = network.getSimpleName().replaceAll("[^A-Za-z0-9]", "")
		this.network = network
		this.hasNative = false;
		for (i : 0 ..< network.allActors.size) 
			if (network.allActors.get(i).native) 
				hasNative = true;			
	}
	
	// getPomXMLContent() creates the pom.xml which enables the compilation of generated Java Spring sources with Maven
	def getPomXMLContent(HashMap<String, String> portNames, String projectName) '''
	<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
		  <modelVersion>4.0.0</modelVersion>

		 <groupId>net.sf.orcc</groupId>
		 <artifactId>«projectName + "_" + networkSimpleName»</artifactId>
		 <version>1.0-SNAPSHOT</version>
		 <packaging>jar</packaging>

		 <name>«projectName + "_" + networkSimpleName»</name>
		 <description>Spring Boot app for actors that are part of «networkSimpleName» network.</description>

		<profiles>
			<profile>
				<id>install</id>
				<properties>
					<azure.install>true</azure.install>
					<azure.uninstall>false</azure.uninstall>
				</properties>
				<activation>
					<activeByDefault>true</activeByDefault>
				</activation>
				</profile>
				<profile>
					<id>uninstall</id>
					<properties>
						<azure.install>false</azure.install>
						<azure.uninstall>true</azure.uninstall>
					</properties>
				</profile>
			</profiles>

		<parent>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-parent</artifactId>
			<version>2.1.0.RELEASE</version>
			<relativePath/> <!-- lookup parent from repository -->
		</parent>

		 <properties>
		  	<instance.name>«projectName.toLowerCase().replaceAll("[^A-Za-z0-9]", "") + networkSimpleName.toLowerCase().replaceAll("[^A-Za-z0-9]", "")»</instance.name>
		  	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		  	<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		    <maven.compiler.source>1.8</maven.compiler.source>
		    <maven.compiler.target>1.8</maven.compiler.target>
		    <java.version>1.8</java.version>
		 </properties>
		«IF hasNative »
		  <repositories>
		 	<repository>
		 		<id>repos-local</id>
		 		<name>repository</name>
		 		<url>file://${project.basedir}\repos</url>
		  	</repository>
		 </repositories>
		«ENDIF» 

		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-web</artifactId>
			</dependency>

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-websocket</artifactId>
			</dependency>

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-test</artifactId>
				<scope>test</scope>
			</dependency>

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-activemq</artifactId>
			</dependency>

			<dependency>
				<groupId>org.apache.geronimo.specs</groupId>
				<artifactId>geronimo-jms_1.1_spec</artifactId>
				<version>1.1.1</version>
			</dependency>
			
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-data-mongodb</artifactId>
			</dependency>

			<dependency>
				<groupId>org.apache.qpid</groupId>
				<artifactId>qpid-amqp-1-0-client</artifactId>
				<version>0.32</version>
			</dependency>

			<dependency>
				<groupId>org.apache.qpid</groupId>
				<artifactId>qpid-amqp-1-0-client-jms</artifactId>
				<version>0.32</version>
			</dependency>

			<dependency>
				<groupId>org.apache.qpid</groupId>
				<artifactId>qpid-amqp-1-0-common</artifactId>
				<version>0.32</version>
			</dependency>
		«FOR Actor act : network.allActors»
		«IF act.native»
			«IF nativeActSet.add(act.package + act.getAttribute("jar").getAttribute("artifactid").getStringValue())»   
			<dependency>
				<groupId>«act.package»</groupId>
				<artifactId>«act.getAttribute("jar").getAttribute("artifactid").getStringValue()»</artifactId>
				<version>«act.getAttribute("jar").getAttribute("jarversion").getStringValue()»</version>
			</dependency>
			«ENDIF»
			«ENDIF»
		«ENDFOR»
		«nativeActSet.clear»
		</dependencies>		  
		  
		<build>
			<plugins>
				<plugin>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-maven-plugin</artifactId>
				</plugin>
				<plugin>
					<groupId>org.codehaus.mojo</groupId>
					<artifactId>properties-maven-plugin</artifactId>
					<version>1.0-alpha-1</version>
					<executions>
						<execution>
							<phase>initialize</phase>
							<goals>
								<goal>read-project-properties</goal>
							</goals>
							<configuration>
								<files>
									<file>azure.properties</file>
								</files>
							</configuration>
						</execution>
					</executions>
				</plugin>
				<plugin>
					<artifactId>exec-maven-plugin</artifactId>
					<version>1.6.0</version>
					<groupId>org.codehaus.mojo</groupId>
					<executions>
						«FOR String pn : portNames.keySet»
						<execution>
							<id>Creating port: «pn» .... </id>
							<phase>install</phase>
							<goals>
								<goal>exec</goal>
							</goals>
							<configuration>
								<executable>az</executable>
								<commandlineArgs>eventhubs eventhub create --resource-group ${azure.resource_group} --namespace-name ${azure.eventhub_namespace} --name «pn» --message-retention 1 --partition-count 2</commandlineArgs>
								<skip>${azure.uninstall}</skip>
							</configuration>
						</execution>						
						«ENDFOR»
						<execution>
							<id>Creating docker build</id>
							<phase>install</phase>
							<goals>
								<goal>exec</goal>
							</goals>
							<configuration>
								<executable>docker</executable>
								<commandlineArgs>build -t ${instance.name}/${instance.name} .</commandlineArgs>
								<skip>${azure.uninstall}</skip>
							</configuration>
						</execution>
						<execution>
							<id>Tagging docker build</id>
							<phase>install</phase>
							<goals>
								<goal>exec</goal>
							</goals>
							<configuration>
								<executable>docker</executable>
								<commandlineArgs>tag ${instance.name}/${instance.name} ${azure.acr_username}.azurecr.io/${instance.name}</commandlineArgs>
								<skip>${azure.uninstall}</skip>
							</configuration>
						</execution>
						<execution>
							<id>Logging in to the registry</id>
							<phase>install</phase>
							<goals>
								<goal>exec</goal>
							</goals>
							<configuration>
								<executable>az</executable>
								<commandlineArgs>acr login --name ${azure.acr_username}</commandlineArgs>
								<skip>${azure.uninstall}</skip>
							</configuration>
						</execution>
						<execution>
							<id>Pushing image</id>
							<phase>install</phase>
							<goals>
								<goal>exec</goal>
							</goals>
							<configuration>
								<executable>docker</executable>
								<commandlineArgs>push ${azure.acr_username}.azurecr.io/${instance.name}</commandlineArgs>
								<skip>${azure.uninstall}</skip>
							</configuration>
						</execution>
						<execution>
							<id>Creating instance</id>
							<phase>install</phase>
							<goals>
								<goal>exec</goal>
							</goals>
							<configuration>
								<executable>az</executable>
								<commandlineArgs>container create --name ${instance.name} --ports 8080 --image ${azure.acr_username}.azurecr.io/${instance.name}:latest --cpu 1 --memory 2 --ip-address public -g ${azure.resource_group} --registry-username ${azure.acr_username} --registry-password ${azure.acr_password}</commandlineArgs>
								<skip>${azure.uninstall}</skip>
							</configuration>
						</execution>
						<execution>
							<id>Deleting instance ${instance.name} ..... </id>
							<phase>install</phase>
							<goals>
								<goal>exec</goal>
							</goals>
							<configuration>
								<executable>az</executable>
								<commandlineArgs>container delete --name ${instance.name} --resource-group ${azure.resource_group} --yes</commandlineArgs>
								<skip>${azure.install}</skip>
							</configuration>
						</execution>
						«FOR String pn : portNames.keySet»
						<execution>
							<id>Deleting port: «pn» .... </id>
							<phase>install</phase>
							<goals>
								<goal>exec</goal>
							</goals>
							<configuration>
								<executable>az</executable>
								<commandlineArgs>eventhubs eventhub delete --resource-group ${azure.resource_group} --namespace-name ${azure.eventhub_namespace} --name «pn»</commandlineArgs>
								<skip>${azure.install}</skip>
							</configuration>
						</execution>						
						«ENDFOR»
					</executions>
				</plugin>
			</plugins>
		</build> 
	</project>
	'''
	
		// Returns the content of the Spring starter class...
	override getSpringStarterFileContent() '''
		package «networkSimpleName»;

		import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;

		@SpringBootApplication(scanBasePackages={"net.sf.orcc.runtime","«networkSimpleName»"})
		@EnableMongoRepositories(basePackages = { "net.sf.orcc.runtime" } )
		public class Starter {
			public static void main(String[] args) {
				SpringApplication.run(Starter.class, args);
			}
		}
	'''

	// 	generates configuration file for Spring boot queue class
	def getQueueConfig(String networkName,String portName, String io) '''
	package «networkName»;

	import java.util.Hashtable;
	import javax.jms.ConnectionFactory;
	import javax.naming.Context;
	import javax.naming.InitialContext;
	import javax.naming.NamingException;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.boot.context.event.ApplicationReadyEvent;
	import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.context.annotation.PropertySource;
	import org.springframework.context.event.EventListener;
	import org.springframework.jms.annotation.JmsListener;
	import org.springframework.jms.annotation.EnableJms;
	import org.springframework.jms.core.JmsTemplate;
	import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
	import org.springframework.jms.config.JmsListenerContainerFactory;
	import org.springframework.stereotype.Component;


	@Configuration
	@EnableJms
	@PropertySource("classpath:jms.properties")
	public class QueueCfg_«portName» {
	private static final Logger LOGGER = LoggerFactory.getLogger( QueueCfg_«portName».class );

		@Value("${amqp_uri}")
		private String amqp_uri;

		@Bean
		public ConnectionFactory connectionFactory_«portName»() {
			ConnectionFactory cf = null;
			try {
				Hashtable<String, String> env = new Hashtable<String, String>();
				env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.amqp_1_0.jms.jndi.PropertiesFileInitialContextFactory");
				env.put( "connectionfactory.SBCF", amqp_uri );
				Context context = new InitialContext(env);
				cf = (ConnectionFactory) context.lookup("SBCF");
			} catch( NamingException ex ) {
				LOGGER.info( "NamingException: "+ex.getMessage() );
			}
			return cf;
		}

		@Bean
		public JmsListenerContainerFactory<?> jsaFactory_«portName»(ConnectionFactory connectionFactory_«portName»,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
			DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
			factory.setConnectionFactory(connectionFactory_«portName»);
			factory.setConcurrency("1-1");
			factory.setPubSubDomain(true);
			factory.setSubscriptionDurable(true);
			factory.setAutoStartup(false);
			return factory;
		}

		@Bean
		@Qualifier("«portName»")
		public JmsTemplate jmsTemplate_«portName»(){
			JmsTemplate template = new JmsTemplate();
			template.setConnectionFactory(connectionFactory_«portName»());
			return template;
		}
	}
	'''	
	
	// generates the queue class for each port in the network
	def getQueueFileContent(String networkName,String portName, String tokenType) '''
	package «networkName»;

	import java.io.IOException;
	import java.util.HashMap;
	import java.util.Map;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;		
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.jms.annotation.JmsListener;
	import org.springframework.jms.core.JmsTemplate;
	import org.springframework.jms.support.JmsHeaders;		
	import org.springframework.messaging.handler.annotation.Header;
	import org.springframework.stereotype.Component;		
	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.fasterxml.jackson.databind.node.ObjectNode;
	import com.fasterxml.jackson.core.JsonProcessingException;
	import net.sf.orcc.runtime.*;
	
	@Component
	public class Queue_«portName» {
		private static final Logger LOGGER = LoggerFactory.getLogger( Queue_«portName».class );
		
		
		public Queue_«portName»(){ 
			LOGGER.info("Object of Class Queue_«portName» is created: " + this);
			//initialize();
			//schedule();
		}			

		@Autowired
		«networkName» tokenReceiver;
		
		@JmsListener(
			id = "«portName»_1",
			destination = "«portName»/ConsumerGroups/$Default/Partitions/0", 
			containerFactory="jsaFactory_«portName»")
		public void inputPodReceive0(String msg, @Header(JmsHeaders.TIMESTAMP) long timeStamp){
			inputPodAction( msg,timeStamp );
		}

		@JmsListener(
			id = "«portName»_2",
			destination = "«portName»/ConsumerGroups/$Default/Partitions/1", 
			containerFactory="jsaFactory_«portName»")
		public void inputPodReceive1(String msg, @Header(JmsHeaders.TIMESTAMP) long timeStamp) {
			inputPodAction( msg, timeStamp );
		}

		@Autowired
		IDBRepository dbRepository;
		
		private void inputPodAction( String msg, long timeStamp ) {
			//LOGGER.info("Queue_i_port1   inputPodAction msg: " + msg + "receive and invoke put to fifo function");
			ObjectMapper mapper = new ObjectMapper();
			try {
				«tokenType»JSONToken token = mapper.readValue( msg, «tokenType»JSONToken.class );
				LOGGER.info("Token receiver object is" + tokenReceiver + " sender is " + this);
				tokenReceiver.receive_«portName»_«tokenType»Token( token );
				dbRepository.save(new IOData<«tokenType»>(
						"I",
						System.currentTimeMillis(),token.value, 
						token.timeStamp, 
						token.nodeId, 
						token.pod,
						token.type)
				);
			} catch( JsonProcessingException ex ) {
				ex.printStackTrace();
			} catch( IOException ex ) {
				ex.printStackTrace();
			}
		}
		
	}
	'''

	// generates the azure.properties file that is referenced from pom.xml
	def getAzureProperties() '''
		// Azure resource group
		azure.resource_group=«azure_resource_group»

		// Azure event hub namespace
		azure.eventhub_namespace=«azure_eventhub_namespace»

		// The Azure Container Registry name to use
		azure.acr_username = "«azure_acr_username»"

		// ACR password. azure_create_acr.sh script produces this password
		azure.acr_password = "«azure_acr_password»"
	'''

	// Sets the Azure properties necessary to generate the deployment files
	def setAzureProperties(Properties azureCredsProps) {
		azure_resource_group = azureCredsProps.getProperty( "azure.resource.group" )
		azure_eventhub_namespace = azureCredsProps.getProperty( "azure.eventhub.namespace" )
		azure_acr_username = azureCredsProps.getProperty( "azure.acr.username" )
		azure_acr_password = azureCredsProps.getProperty( "azure.acr.password" )
		azure_amqp_uri = azureCredsProps.getProperty( "azure.amqp.uri" )
		azure_db_name = azureCredsProps.getProperty( "azure.db.name" )
		azure_db_URI = azureCredsProps.getProperty("azure.db.URI")
	}

	
	// JMS config file with the AMQP URI in it
	def getJMSProperties()'''
		amqp_uri=«azure_amqp_uri»
	'''
	
	
	//config file for sshd deployed to cloud where the application is executing.  
	def getSSHDConfig()'''
		#
		# /etc/ssh/sshd_config
		#
		
		Port 2222
		ListenAddress 0.0.0.0
		LoginGraceTime 180
		X11Forwarding yes
		Ciphers aes128-cbc,3des-cbc,aes256-cbc
		MACs hmac-sha1,hmac-sha1-96
		StrictModes yes
		SyslogFacility DAEMON
		PrintMotd no
		IgnoreRhosts no
		#deprecated option 
		#RhostsAuthentication no
		RhostsRSAAuthentication yes
		RSAAuthentication no 
		PasswordAuthentication yes
		PermitEmptyPasswords no
		PermitRootLogin yes
	'''
	
	// Init file is executed once the application is deployed to cloud. It starts the app...
	// do not forget to execute dos2unix command on this file if you work on Windows OS. 
	//It wont work without it once deployed to cloud...
	def getInit(String jarName) '''  
		#!/bin/bash
		set -e
		echo "Starting SSH ..."
		service ssh start
		java -jar /usr/src/«jarName»
	'''

	//generates the dockerfile for building image
	def getDockerFile(String jarName) '''
		FROM openjdk:8-jre
		# ssh
		ENV SSH_PASSWD "root:Docker!"
		RUN apt-get update \
		&& apt-get install -y --no-install-recommends dialog \
		&& apt-get update \
		&& apt-get install -y --no-install-recommends openssh-server \
		&& echo "$SSH_PASSWD" | chpasswd 

		COPY scripts/sshd_config /etc/ssh/
		COPY scripts/init.sh /usr/local/bin/

		RUN chmod a+x /usr/local/bin/init.sh
		EXPOSE 8080 2222
		COPY  target/«jarName» /usr/src/«jarName»
		RUN chmod a+x /usr/src/«jarName»
		ENTRYPOINT ["init.sh"]
	'''	
			
	def getSpringAppProperties() '''
	spring.data.mongodb.database=«azure_db_name»
	spring.data.mongodb.uri=«azure_db_URI»
	'''
	
	def getInputHttpAdapter( String networkName,String portName, String tokenType ) '''
	package «networkName»;

	import java.util.ArrayList;
	import java.io.EOFException;
	import java.io.PrintWriter;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.ServletInputStream;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.jms.core.JmsTemplate;
	import org.springframework.stereotype.Component; 
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.http.HttpEntity;
	import org.springframework.http.ResponseEntity;
	import org.springframework.http.HttpStatus;
	import net.sf.orcc.runtime.*;
	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.fasterxml.jackson.core.JsonProcessingException;
	
	@Controller
	public class HttpEndpoint_«portName» {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpEndpoint_«portName».class);			

	@Autowired
	@Qualifier("«portName»")
	JmsTemplate «portName»_template;

	private void process( byte[] body ) throws EOFException {
		dumpArray( body );
		ByteArrayParser bp = new ByteArrayParser( body );
		String nodeId = bp.readString();
		LOGGER.info( "nodeId: "+nodeId );
		int objctr = 0;
		try {
			while( true ) {
				ObjectMapper mapper = new ObjectMapper();
				long ts = bp.readUnsignedInt();
				String value = bp.readString();
				«tokenType»JSONToken token = new «tokenType»JSONToken( nodeId, "«portName»", ts, value );
				try {
					LOGGER.info( "«portName»: "+token );
					«portName»_template.convertAndSend( "«portName»",mapper.writeValueAsString( token ) );
					++objctr;
				} catch( JsonProcessingException ex ) {
					LOGGER.error( "«portName»_template.convertAndSend",ex );
				}
			}
		} catch( EOFException ex ) {}
		if( objctr == 0 )
			throw new EOFException( "no objects in body (nodeId read successfully)" );
	}

	private void dumpArray( byte[] array ) {
	StringBuffer sb = new StringBuffer();
	int col = 0;
	for( int i = 0 ; i < array.length ; ++i ) {
		if( col > 0 )
			sb.append( " " );
		sb.append( String.format("%02X", array[i]) );
		if( ++col >= 8 ) {
			LOGGER.info( "dumpArray: "+ sb );
			sb = new StringBuffer();
			col = 0;
		}
	}
	if( col > 0 )
		LOGGER.info( "dumpArray: " + sb );
	}

	@RequestMapping(method = RequestMethod.POST, value = "/«portName»", consumes = "application/octet-stream")
	public void acceptData( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		StringBuffer b = new StringBuffer();
		ServletInputStream is = request.getInputStream();
		do {
			int c = is.read();
			if( c < 0 )
				break;
			b.append( (char)c );
		} while( true );
		try {
			byte body[] = b.toString().getBytes();
			process(body);
			PrintWriter writer = response.getWriter();
			writer.print( "OK" );
			response.setStatus( HttpServletResponse.SC_OK );
			writer.close();
		} catch( EOFException ex ) {
			LOGGER.error( "HttpEndpoint_«portName»: "+ex );
			PrintWriter writer = response.getWriter();
			writer.print( "Invalid request body" );
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			writer.close();
		}
	}

	}
	'''
	
	def getOutputHttpAdapter( String networkName,String portName, String tokenType ) '''
	package «networkName»;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.jms.core.JmsTemplate;
	import org.springframework.stereotype.Component; 
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.http.HttpEntity;
	import org.springframework.http.ResponseEntity;
	import org.springframework.http.HttpStatus;
	import java.util.List;
	import net.sf.orcc.runtime.*;

	@Controller
	public class HttpEndpoint_«portName» {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpEndpoint_«portName».class);			

	@Autowired
	IDBRepository dbRepository;

	private byte[] process( String nodeId ) {
		long baseTs = System.currentTimeMillis();
		List<IOData> items = dbRepository.findByPodNameAndLessThanTimeReceived( baseTs, "«portName»" , nodeId);
		ByteArrayGenerator bog = new ByteArrayGenerator();
		for( int i = 0 ; i < items.size() ; ++i ) {
			IOData<String> item = (IOData<String>)items.get(i);
			bog.writeUnsignedInt( item.tokenTStamp );
			bog.writeString( item.tokenValue );
		}
		dbRepository.deleteByPodNameAndLessThanTimeReceived( baseTs, "«portName»", nodeId );
		return bog.getByteArray();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/«portName»")
	public ResponseEntity<byte[]> requestData( @RequestParam String nodeId ) {
		ResponseEntity result = null;
		try {
			byte[] response = process( nodeId );
			result = ResponseEntity.status(HttpStatus.OK)
				.body(response);
		} catch( Exception ex ) {
			byte rsp[] = new byte[5];
			for( int i = 0 ; i < 5 ; ++i )
				rsp[i] = 0;
			LOGGER.error( "HttpEndpoint_«portName»", ex );
			result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body( rsp );
		}
		return result;
	}
	}
	'''

}
