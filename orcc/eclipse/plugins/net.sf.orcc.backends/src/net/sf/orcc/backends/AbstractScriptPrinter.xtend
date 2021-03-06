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

package net.sf.orcc.backends;

import net.sf.orcc.df.Network;

/**
	 * Abstract class for generating various scripts. 
	 * 
	 * In this class, the following script generators are implemented:
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