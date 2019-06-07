/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * about
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
package net.sf.orcc.backends.javaspring

import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;

import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.Result;
import java.util.HashMap;
import net.sf.orcc.df.Actor
import java.util.ArrayList


/*
 * Compile Top_network Java source code 
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends JavaSpringTemplate {
	
	Network network;
	String networkSimpleName;
	String topPath;
	Result result;
	JavaSpringScriptPrinter javaScriptsPrinter;
	HashMap<String,String> mapOfPortNames = new HashMap();
	
	def setNetwork(Network network, String path, Result result,JavaSpringScriptPrinter printer) {
		this.network = network
		this.topPath = path;
		this.result = result;
		this.javaScriptsPrinter = printer;
		this.networkSimpleName = network.getSimpleName().replaceAll("[^A-Za-z0-9]", "");
	}
	
	def setNetwork(Network network,JavaSpringScriptPrinter printer) {
		this.network = network;
		this.networkSimpleName = network.getSimpleName().replaceAll("[^A-Za-z0-9]", "");
	}
	
	def HashMap<String,String> getMapOfPortNames(){
		return mapOfPortNames;
	}

	def getNetworkFileContent() '''
		// File generated from «network.fileName»
		package «networkSimpleName»;
		
		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;
		
		import net.sf.orcc.runtime.*;
		import net.sf.orcc.runtime.actors.*;
		import net.sf.orcc.runtime.source.GenericSource;
		
		import org.springframework.boot.CommandLineRunner;
		import org.springframework.stereotype.Component;
		import org.springframework.beans.factory.annotation.Autowired;
		«FOR Actor act : network.allActors»	
			«IF act.native»
				import «act.name»;
			«ENDIF»
		«ENDFOR»
		
		@Component
		public class «networkSimpleName» implements IScheduler, CommandLineRunner {
			private static final Logger LOGGER = LoggerFactory.getLogger(«networkSimpleName».class);			
			
		public «networkSimpleName»(){ 
			LOGGER.info("Object of Class «networkSimpleName» is created: " + this);
			//initialize();
			//schedule();
			}
			
			// Declare actors objects
			«FOR instance : network.children.filter(typeof(Instance)).filter[!broadcast]»
				private IActor «instance.getName().replaceAll("[^A-Za-z0-9]", "")»;
			«ENDFOR»

«««			Clears the list of ports before using the list. Othervise, the list is populated throught consequtive compilations 
			«mapOfPortNames.clear» 
			
			// Declare input fifos
			«FOR port : network.inputs»
				private Fifo inFifo_«port.name»;
				«System.out.println(mapOfPortNames.put(port.name, port.type.doSwitch.toString))»
			«ENDFOR»
			
			// Declare output fifos 
			«FOR port : network.outputs»
				private Fifo outFifo_«port.name»;
				«System.out.println(mapOfPortNames.put(port.name, port.type.doSwitch.toString))»
			«ENDFOR»			

			// Declare broadcast
			«FOR instance : network.children.filter(typeof(Instance)).filter[broadcast]»
				private Broadcast<«instance.getActor.getInput("input").type.doSwitch»> «instance.getName().replaceAll("[^A-Za-z0-9]", "")»;
			«ENDFOR»
			
			
			@Override
			@SuppressWarnings("unchecked")
			public void initialize() {
				
				// Instantiate actors
				«FOR instance : network.children.filter(typeof(Instance)).filter[!broadcast]»
					
					«IF !instance.getActor.native»
						«instance.getName().replaceAll("[^A-Za-z0-9]", "")» = new «instance.getSimpleName().replaceAll("[^A-Za-z0-9]", "")»(«printArguments(instance.getActor.parameters, instance.arguments)»);
					«ELSE»
						«instance.getName().replaceAll("[^A-Za-z0-9]", "")» = new «instance.getActor.simpleName.replaceAll("[^A-Za-z0-9]", "")»(«printArguments(instance.getActor.parameters, instance.arguments)»);
					«ENDIF»				
				«ENDFOR»
				
				// Instantiate broadcast
				«FOR instance : network.children.filter(typeof(Instance)).filter[broadcast]»
					«instance.getName().replaceAll("[^A-Za-z0-9]", "")» = new Broadcast<«instance.getActor.getInput("input").type.doSwitch»>(«instance.outgoing.size»);
				«ENDFOR»
				
				@SuppressWarnings("rawtypes")
				Fifo f;
				«FOR connection : network.connections»
					«IF (connection.source instanceof Port)»
						inFifo_«(connection.source as Port).name» = new Fifo<«(connection.source as Port).type.doSwitch»>(«if (connection.size !== null) connection.size else fifoSize»);
						«(connection.target as Instance).getName().replaceAll("[^A-Za-z0-9]", "")».setFifo("«connection.targetPort.name»", inFifo_«(connection.source as Port).name»);

					«ELSEIF (connection.target instanceof Port)»
						outFifo_«(connection.target as Port).name» = new Fifo<«(connection.target as Port).type.doSwitch»>(«if (connection.size !== null) connection.size else fifoSize»);
						«(connection.source as Instance).getName().replaceAll("[^A-Za-z0-9]", "")».setFifo("«connection.sourcePort.name»", outFifo_«(connection.target as Port).name»);					
						
					«ELSE»
						f = new Fifo<«connection.targetPort.type.doSwitch»>(«if (connection.size !== null) connection.size else fifoSize»);
						«(connection.source as Instance).getName().replaceAll("[^A-Za-z0-9]", "")».setFifo("«connection.sourcePort.name»", f);
						«(connection.target as Instance).getName().replaceAll("[^A-Za-z0-9]", "")».setFifo("«connection.targetPort.name»", f);					
					«ENDIF»
				«ENDFOR»
			}
			
			«FOR port : network.inputs»
				//@Override // needed if you are using interface for this...
				public void receive_«port.name»_«port.type.doSwitch.toString»Token( String pod, «port.type.doSwitch.toString» v ) {
				LOGGER.info("Class: «network.name», Port: «port.name»Token: pod: " + pod + " v: " + v);
					//if( "input".equals( pod ) ) {
					put«port.name»_«port.type.doSwitch.toString»TokenIntoFifo( inFifo_«port.name»,v );
					//}
				}		
				
				private void put«port.name»_«port.type.doSwitch.toString»TokenIntoFifo( Fifo<«port.type.doSwitch.toString»> f, «port.type.doSwitch.toString» v ) {
					LOGGER.info( "Class: «network.name». put«port.type.doSwitch»TokenIntoFifo: f: " + f + " value: " + v );
					if( f.hasRoom(1) ) {
						f.write( v );
						schedule();
						}
					}
				
				«System.out.println(result.merge(FilesManager.writeFile(javaScriptsPrinter.getQueueConfig(networkSimpleName, port.name,"input"), topPath, "QueueCfg_" + port.name + ".java")))»
				«System.out.println(result.merge(FilesManager.writeFile(javaScriptsPrinter.getQueueFileContent(networkSimpleName, port.name, port.type.doSwitch.toString,"input"),topPath,"Queue_"+ port.name +".java")))»
			«ENDFOR»
			
			«FOR port : network.outputs»
				@Autowired
				Queue_«port.name»  queue_«port.name»; // Autowire with senders, since this object sends messages to queue
				private int scheduleOutputFifo_«port.name» ( String podName, Fifo<«port.type.doSwitch.toString»> f ) {
				boolean res = false;
				if( f.hasTokens(1) ){
					res = true;
					«port.type.doSwitch.toString» v = f.read();
					LOGGER.info("Sending from: " + this + " value: " + v + " using object: " + queue_«port.name» );
					queue_«port.name».send«port.type.doSwitch»ToQueue(podName, v );
					}
				return res ? 1 : 0;
				}
				
				«System.out.println(result.merge(FilesManager.writeFile(javaScriptsPrinter.getQueueConfig(networkSimpleName, port.name, "output"), topPath, "QueueCfg_" + port.name + ".java")))»
				«System.out.println(result.merge(FilesManager.writeFile(javaScriptsPrinter.getQueueFileContent(networkSimpleName, port.name, port.type.doSwitch.toString,"output"),topPath,"Queue_"+ port.name +".java")))»
			«ENDFOR»
			
			«System.out.println(result.merge(FilesManager.writeFile(javaScriptsPrinter.getDBRepository(networkSimpleName),topPath,"DBRepository.java")))»
			«System.out.println(result.merge(FilesManager.writeFile(javaScriptsPrinter.getIDBRepository(networkSimpleName),topPath,"IDBRepository.java")))»
		
			@Override
			public void schedule() {
				«FOR instance : network.children.filter(typeof(Instance))»
					«IF ! instance.getActor?.initializes.empty»
						«instance.name».initialize();
					«ENDIF»
				«ENDFOR»
				
				int i;
				do {
					i = 0;
					«FOR port : network.outputs»
						i += scheduleOutputFifo_«port.name»("«port.name»", outFifo_«port.name»);
					«ENDFOR»
					«FOR instance : network.children.filter(typeof(Instance))»
						i += «instance.getName().replaceAll("[^A-Za-z0-9]", "")».schedule();
					«ENDFOR»
					«FOR port : network.inputs»
					//	i += scheduleInputFifo_«port.name»("«port.name»", inFifo_«port.name»); //not necessary for inputs
					«ENDFOR»					
					
				} while (i > 0);
				
				System.out.println("No more action to launch, listening.....");
				//System.exit(0);
			}
			
			@Override
			public void run(String[] args) {
				LOGGER.info("----------------------Run function started in «networkSimpleName»-------------------");
				CLIParameters.getInstance().setArguments(args);
				GenericSource.setFileName(CLIParameters.getInstance().getSourceFile());
				//IScheduler scheduler = new «networkSimpleName»();
				IScheduler scheduler = this;
				scheduler.initialize();
				scheduler.schedule();
				//initialize();
				//schedule();
				LOGGER.info("----------------------Run function complete----------------------------------------");
			}
		}
	'''
	
		
	/**
	 * TODO : replace this awful wart by a great way to detect an instance is broadcast
	 * If an actors has 1 in port called "input" and a list of
	 * out ports called "output_x", it is a Broadcast actor
	 */
	def isBroadcast(Instance instance) {
		if (instance.getActor.getInput("input") === null) {
			return false
		}
		for(port : instance.getActor.outputs) {
			if ( ! port.name.startsWith("output_")) {
				return false
			}
		}
		return true
	}
	
}