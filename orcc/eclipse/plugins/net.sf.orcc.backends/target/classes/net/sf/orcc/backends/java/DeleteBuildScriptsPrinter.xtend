/*
 * Copyright (c) 2012, Gang of four... 
 * All rights reserved.
 * 
 */

package net.sf.orcc.backends.java

import net.sf.orcc.df.Network
//import java.util.ArrayList
import net.sf.orcc.df.Actor

class DeleteBuildScriptsPrinter extends JavaTemplate {
	
	Network network;
	protected boolean hasNative = false;
	
	def setNetwork(Network network) {
		this.network = network;
		this.hasNative = false;
		for (i : 0 ..< network.allActors.size) 
			if (network.allActors.get(i).native) hasNative = true;		
	}	
		
	//getProjectFileContent() creates the .project file which enable the import of generated sources in Eclipse IDE
	def getProjectFileContent() '''
		<?xml version="1.0" encoding="UTF-8"?>
		<projectDescription>
			<name>«network.simpleName»</name>
			<comment></comment>
			<projects>
			</projects>
			<buildSpec>
				<buildCommand>
					<name>org.eclipse.jdt.core.javabuilder</name>
					<arguments>
					</arguments>
				</buildCommand>
			</buildSpec>
			<natures>
				<nature>org.eclipse.jdt.core.javanature</nature>
			</natures>
		</projectDescription>
	'''
	
	//getClasspathFileContent() creates the .classpath file which enable the import of generated sources in Eclipse IDE
	def getClasspathFileContent() '''
		<?xml version="1.0" encoding="UTF-8"?>
		<classpath>
			<classpathentry kind="src" path="runtime"/>
			<classpathentry kind="src" path="src"/>
			<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
			<classpathentry kind="output" path="bin"/>
		</classpath>
	'''
	
	// getPomXMLContent() creates the pom.xml which enables the compilation of generated sources wiht maven
	def getPomXMLContent() '''
		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
		  <modelVersion>4.0.0</modelVersion>
		  	
		 <groupId>net.sf.orcc</groupId>
		 <artifactId>«network.simpleName»</artifactId>
		 <version>1.0-SNAPSHOT</version>
		 
		  <properties>
		  	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		  	<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>		  
		    <maven.compiler.source>1.8</maven.compiler.source>
		    <maven.compiler.target>1.8</maven.compiler.target>
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
				<groupId>org.java-websocket</groupId>
				<artifactId>Java-WebSocket</artifactId>
				<version>1.3.9</version>
			</dependency>
			<dependency>
				<groupId>com.fasterxml.jackson.core</groupId>
				<artifactId>jackson-databind</artifactId>
				<version>2.9.8</version>
			</dependency>
		«FOR Actor act : network.allActors»
			<dependency>
				<groupId>«act.package»</groupId>
				<artifactId>«act.simpleName»</artifactId>
				<version>1.0-SNAPSHOT</version>
			</dependency>
		«ENDFOR»
		</dependencies>
		  
		<build>
			<directory>bin</directory>
			<sourceDirectory>src</sourceDirectory>
			<plugins>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-assembly-plugin</artifactId>
					<executions>
						<execution>
							<phase>package</phase>
							<goals>
								<goal>single</goal>
							</goals>
							<configuration>
								<archive>
									<manifest>
										<mainClass>
											«network.simpleName».«network.simpleName»
										</mainClass>
									</manifest>
								</archive>
								<descriptorRefs>
									<descriptorRef>jar-with-dependencies</descriptorRef>
								</descriptorRefs>
							</configuration>
						</execution>
					</executions>
				</plugin>
			</plugins>
		</build>
		 
		</project>
	'''
}