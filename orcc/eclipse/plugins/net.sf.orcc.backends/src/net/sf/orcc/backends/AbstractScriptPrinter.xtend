 /*
 * Copyright (c) 2012, 
 * Nebojša Taušan, Gabor Paller, Gabor Farkas, Endri Bezati 
 * All rights reserved.
 * 
 */

package net.sf.orcc.backends;

import net.sf.orcc.df.Network;

/**
	 * following script generators are implemented:
	 * 
	 * getProjectFileContent()    		creates the .project file which enable the import of generated sources in Eclipse IDE
	 * getClasspathFileContent()  		creates the .classpath file which enable the import of generated sources in Eclipse IDE
	 */


abstract class AbstractScriptPrinter {

	protected Network network;
	
	def setNetwork(Network network) {
		this.network = network
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
			<classpathentry kind="src" path="src"/>
			<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
			<classpathentry kind="output" path="target"/>
		</classpath>
	'''
	
	def getPomXMLContent() '''
	
	'''
	
	def getSpringStarterFileContent() '''

	'''
	
	def getQueueConfig()'''

	''' 
	
}