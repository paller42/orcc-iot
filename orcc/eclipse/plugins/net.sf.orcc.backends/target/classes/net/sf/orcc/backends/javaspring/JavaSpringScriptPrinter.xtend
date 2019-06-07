 /*
 * Copyright (c) 2012, 
 * Nebojša Taušan, Gabor Paller, Gabor Farkas, Endri Bezati 
 * All rights reserved.
 * 
 */

package net.sf.orcc.backends.javaspring

import net.sf.orcc.backends.java.JavaScriptPrinter;
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
	 * getJSONtokenFileContent()  generates the JSON representation of the token. // Not used any more, java files are in runtime folder
	 * getQueueConfig()  generates configuration file for Spring boot queue class
	 * getQueueFileContent() generates the queue class for each port in the network
	 * getAzureProperties() generates the azure.properties file that is referenced from pom.xml
	 * getSSHDConfig()     generates configuration script for SSHD for docker image 
	 * getInit()		generates initialization script that will start the application in cloud
	 * getDockerFile(String jarName) generates the Dockerfile for building docker image
	 * getWebSocketConfig(String networkName) generates the Web socket configuration class
	 * getSocketHandler(String networkName) generates the socket handler class
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

		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;

		@SpringBootApplication
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
	
«««		«IF io.equals("input")» 
			@Bean
			public JmsListenerContainerFactory<?> jsaFactory_«portName»(ConnectionFactory connectionFactory_«portName»,
				DefaultJmsListenerContainerFactoryConfigurer configurer) {
				DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
				factory.setConnectionFactory(connectionFactory_«portName»);
				factory.setConcurrency("1-1");
				factory.setPubSubDomain(true);
				factory.setSubscriptionDurable(true);
				//factory.setClientId("«networkName»");
				return factory;
			}
«««		«ENDIF»
	
«««		«IF io.equals("output")»
			@Bean
			@Qualifier("«portName»")
			public JmsTemplate jmsTemplate_«portName»(){
				JmsTemplate template = new JmsTemplate();
				template.setConnectionFactory(connectionFactory_«portName»());
				return template;
			}
«««		«ENDIF»	
		}
	'''	
	
	// generates the queue class for each port in the network
	def getQueueFileContent(String networkName,String portName, String tokenType, String io) '''
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

			«IF io.equals("input")»        
			@Autowired
			«networkName» tokenReceiver;	
			@JmsListener(destination = "«portName»/ConsumerGroups/$Default/Partitions/0", containerFactory="jsaFactory_«portName»")
			public void inputPodReceive0(String msg, @Header(JmsHeaders.TIMESTAMP) long timeStamp){
				inputPodAction( msg,timeStamp );
			}

			@JmsListener(destination = "«portName»/ConsumerGroups/$Default/Partitions/1", containerFactory="jsaFactory_«portName»")
			public void inputPodReceive1(String msg, @Header(JmsHeaders.TIMESTAMP) long timeStamp) {
				inputPodAction( msg, timeStamp );
			}

			@Autowired
			IDBRepository dbRepository;
			private void inputPodAction( String msg, long timeStamp ) {
				//LOGGER.info("Queue_«portName»   inputPodAction msg: " + msg + "receive and invoke put to fifo function");
				ObjectMapper mapper = new ObjectMapper();
				try {
					«tokenType»JSONToken token = mapper.readValue( msg, «tokenType»JSONToken.class );
					LOGGER.info("Token receiver object is" + tokenReceiver + " sender is " + this);
					tokenReceiver.receive_«portName»_«tokenType»Token( token.pod,token.value );
					dbRepository.save(new DBRepository<«tokenType»>("receiving","«portName»",System.currentTimeMillis() ,token.value, token.timeStamp, token.pod,token.type));
				} catch( JsonProcessingException ex ) {
					ex.printStackTrace();
				} catch( IOException ex ) {
					ex.printStackTrace();
				}
			}
		«ENDIF»
			
		«IF io.equals("output")»
			@Autowired
			@Qualifier("«portName»")
			JmsTemplate template;
			
			@Autowired
			IDBRepository dbRepository;
			
				public void send«tokenType»ToQueue( String podName, «tokenType» v ) {
					LOGGER.info( "Queue_«portName»  send«tokenType»ToOutputPod: podName: "+podName+"; value: "+v + "sending to queue");
					ObjectMapper mapper = new ObjectMapper();
					«tokenType»JSONToken token = new «tokenType»JSONToken( podName, System.currentTimeMillis() , v );
					try {
						String on_s = mapper.writeValueAsString(token);
						template.convertAndSend( podName, on_s);
						dbRepository.save(new DBRepository<«tokenType.toFirstUpper»>("sending","«portName»",System.currentTimeMillis(), token.value, token.timeStamp, token.pod,token.type));
					} catch( JsonProcessingException ex ) {
						ex.printStackTrace();
					}
				}
		«ENDIF»
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

	// generates the Web socket configuration class
	def getWebSocketConfig(String networkName) '''
		package «networkName»;

		import org.springframework.context.annotation.Configuration;
		import org.springframework.web.socket.config.annotation.EnableWebSocket;
		import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
		import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
		import org.springframework.beans.factory.annotation.Autowired;

		@Configuration
		@EnableWebSocket 
		public class WebSocketConfig implements WebSocketConfigurer { 
			@Autowired
			SocketHandler socketHandler;

			public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) { 
				registry.addHandler(socketHandler, "/iopod");
			}
		}
	'''
	
	
		// generates the socket handler class
	def getSocketHandler(String networkName, HashMap<String,String> portNames) '''
	package «networkName»;

	import net.sf.orcc.runtime.*;
	
	import org.springframework.stereotype.Component; 
	import org.springframework.web.socket.TextMessage; 
	import org.springframework.web.socket.WebSocketSession; 
	import org.springframework.web.socket.CloseStatus;
	import org.springframework.web.socket.handler.TextWebSocketHandler; 
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.jms.core.JmsTemplate;
	import org.springframework.jms.listener.MessageListenerContainer;
	import org.springframework.jms.listener.DefaultMessageListenerContainer;

	import java.util.concurrent.CopyOnWriteArrayList;
	import java.util.List;
	import java.util.HashMap;
	import java.util.logging.Logger;
	import java.util.logging.Level;
	import java.io.IOException;
	import javax.jms.MessageListener;
	import javax.jms.Message;
	import javax.jms.JMSException;

	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.fasterxml.jackson.databind.node.ObjectNode;
	import com.fasterxml.jackson.core.JsonProcessingException;
	import com.fasterxml.jackson.databind.JsonNode;

	@Component 
	public class SocketHandler extends TextWebSocketHandler {
		«FOR String pn : portNames.keySet»
			@Autowired
			@Qualifier("«pn»")
			JmsTemplate «pn»_template;
					
		«ENDFOR»		
		HashMap<String,WebSocketSession> podToWssMap = new HashMap<String,WebSocketSession>();
		HashMap<WebSocketSession,DataPerWss> wssToPodMap = new HashMap<WebSocketSession,DataPerWss>();
		List<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();
		private static final Logger LOGGER = Logger.getLogger( SocketHandler.class.getName() );

		@Override
		public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
			LOGGER.info( "«portNames.keySet.toString»" );
			String ioPod = null;
			String on_s = null;
			long tStamp = 0L;
			
			try {
				ObjectMapper mapper = new ObjectMapper(); 
				JsonNode root = mapper.readTree(message.getPayload());
				switch (root.path("type").textValue()){
					case "Integer": IntegerJSONToken tokenI = mapper.readValue( message.getPayload(), IntegerJSONToken.class );
					on_s = mapper.writeValueAsString( tokenI );
					ioPod = tokenI.pod;
					tStamp = tokenI.timeStamp;
					LOGGER.info("TokenInt received by the websocket interface: " + tokenI);
					break;
					case "String" : StringJSONToken tokenS = mapper.readValue( message.getPayload(), StringJSONToken.class );
					on_s = mapper.writeValueAsString( tokenS );
					ioPod = tokenS.pod;
					tStamp = tokenS.timeStamp;
					LOGGER.info("TokenString received by the websocket interface: " + tokenS);
					break;
					case "Float"  : FloatJSONToken tokenF = mapper.readValue( message.getPayload(), FloatJSONToken.class );
					on_s = mapper.writeValueAsString( tokenF );
					ioPod = tokenF.pod;
					tStamp = tokenF.timeStamp;
					LOGGER.info("TokenFloat received by the websocket interface: " + tokenF);
					break;
				}
						
				if( ioPod != null ) {
				// This is a subscription token, the sender wants to receive messages going out to the indicated pod
					if( tStamp == 0L ) {
						subscribeSession( ioPod, session );
					} else {
						«FOR String pn : portNames.keySet»
						«IF pn.startsWith("i")»
						if( ioPod.equals( "«pn»" ) )
							«pn»_template.convertAndSend( ioPod, on_s);	
						«ENDIF»
						«ENDFOR»
						//TO-DO awful solution, starts wiht i is terrible				
					}
				}
			} catch( JsonProcessingException ex ) {
				LOGGER.log( Level.SEVERE,"handleTextMessage",ex );
			} catch( IOException ex ) {
				LOGGER.log( Level.SEVERE,"handleTextMessage",ex );
			}
		}
		
		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			sessions.add(session);
		}

		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
			//the messages will be broadcasted to all users.
			sessions.remove(session);
			unsubscribeSession( session );
		}
		
		private void subscribeSession( String pod, WebSocketSession session ) {
			OutputPodMessageListener listener = new OutputPodMessageListener( session );
			DataPerWss dataItem = new DataPerWss( pod, listener );
			WebSocketSession oldSession = null;
			synchronized( podToWssMap ) {
				oldSession = podToWssMap.get( pod );
			}
			if( oldSession != null )
				unsubscribeSession( oldSession );
			
			synchronized( podToWssMap ) {
				podToWssMap.put( pod, session );
				wssToPodMap.put( session, dataItem );
			}
			«FOR String pn : portNames.keySet»
				«IF pn.startsWith("o")» 
					if( pod.equals( "«pn»" ) )  //TO-DO this is awful solution, starts wiht o is bad... 
					startListener( listener, «pn»_template, "«pn»",dataItem );

				«ENDIF»
			«ENDFOR»
		}

		private void unsubscribeSession( WebSocketSession session ) {
			MessageListenerContainer container1 = null;
			MessageListenerContainer container2 = null;
			synchronized( podToWssMap ) {
				DataPerWss dataItem = wssToPodMap.get( session );
				String podName = dataItem.podName;
				container1 = dataItem.container1;			
				container2 = dataItem.container2;			
				podToWssMap.remove( podName );
				wssToPodMap.remove( session );
			}
			if( container1 != null )
				container1.stop();
			if( container2 != null )
				container2.stop();
		}
		
		private void startListener( OutputPodMessageListener listener, JmsTemplate template, String queueName, DataPerWss item ) {
			DefaultMessageListenerContainer messageListener1 = new DefaultMessageListenerContainer();
			messageListener1.setDestinationName( queueName+"/ConsumerGroups/$Default/Partitions/0" );
			messageListener1.setConnectionFactory( template.getConnectionFactory());
			messageListener1.setMessageListener( listener );
			messageListener1.setPubSubDomain(false);
			messageListener1.initialize();
			messageListener1.start();
			
			DefaultMessageListenerContainer messageListener2 = new DefaultMessageListenerContainer();
			messageListener2.setDestinationName( queueName+"/ConsumerGroups/$Default/Partitions/1" );
			messageListener2.setConnectionFactory( template.getConnectionFactory());
			messageListener2.setMessageListener( listener );
			messageListener2.setPubSubDomain(false);
			messageListener2.initialize();
			messageListener2.start();
			item.setContainers( messageListener1, messageListener2 );
		}	
		
		class DataPerWss {
			public String podName;
			public OutputPodMessageListener listener;
			public MessageListenerContainer container1;
			public MessageListenerContainer container2;
			
			public DataPerWss( String podName, OutputPodMessageListener listener ) {
				this.podName = podName;
				this.listener = listener;
			}
			
			public void setContainers( MessageListenerContainer container1, MessageListenerContainer container2 ) {
				this.container1 = container1;
				this.container2 = container2;
			}
		}
	
		class OutputPodMessageListener implements MessageListener {
			public OutputPodMessageListener( WebSocketSession session ) {
				this.session = session;
			}
	
			public void onMessage(Message message) {
				try {
					LOGGER.info( "OutputPodMessageListener: "+message );
					if( message instanceof javax.jms.TextMessage ) {
						String payload = ((javax.jms.TextMessage)message).getText();
						LOGGER.info( "payload: "+payload );
						TextMessage wssMsg = new TextMessage( payload.getBytes() );
						boolean repeat = true;
						while( repeat ) {
							try {
								session.sendMessage( wssMsg );
								repeat = false;
							} catch( IllegalStateException ex ) {
								LOGGER.info( "*** IllegalStateException when sending" );
								try {
									Thread.sleep( 1000L );
								} catch( InterruptedException ex1 ) {}
							}
						}
					}
				} catch( IOException ex ) {
					LOGGER.log( Level.SEVERE,"onMessage",ex );
				} catch( JMSException ex ) {
					LOGGER.log( Level.SEVERE,"onMessage",ex );
				}
			}
			
			WebSocketSession session;
		}
	}
	'''
	
	def getIDBRepository(String networkName) '''
	package «networkName»;	
	
	import org.springframework.stereotype.Component;
	import org.springframework.data.mongodb.repository.MongoRepository;
	import java.util.List;
	
	@Component
	public interface IDBRepository extends MongoRepository<DBRepository, String> {
	
	    public List<DBRepository> findByportName(String portName);
	    public List<DBRepository> findBytokenType(String type);
	
	}
	
	'''

	def getDBRepository(String networkName) '''
	package «networkName»;	
	
	import org.springframework.data.annotation.Id;
	
	public class DBRepository<T> {
		@Id
	    public String id;
		
		public String IO;
		public String portName;
		public long timeReceived;

		public T tokenValue;
		public long tokenTStamp;
		public String tokenPod;
		public String tokenType;
		
		public DBRepository (String io, String pn, long tr, T v, long ts, String p, String t) {
			this.IO = io;
			this.portName = pn;
			this.timeReceived = tr;
			
			this.tokenValue = v;
			this.tokenTStamp = ts;
			this.tokenPod = p;
			this.tokenType = t;
		}
	}
	
	'''
	
	def getSpringAppProperties() '''
	spring.data.mongodb.database=«azure_db_name»
	spring.data.mongodb.uri=«azure_db_URI»
	'''
}
