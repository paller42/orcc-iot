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
package net.sf.orcc.backends.java

import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.df.Actor

/*
 * Compile Top_network Java source code 
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends JavaTemplate {
	
	Network network;
	
	def setNetwork(Network network) {
		this.network = network
	}

	def getNetworkFileContent() '''
		// File generated from «network.fileName»
		package «network.simpleName»;
		
		import java.net.URI;
		import java.net.URISyntaxException;
		import net.sf.orcc.runtime.*;
		import net.sf.orcc.runtime.actors.*;
		import net.sf.orcc.runtime.source.GenericSource;
		«FOR Actor act : network.allActors»	
			«IF act.native»
				import «act.name»;
			«ENDIF»
		«ENDFOR»	
		

		public class «network.simpleName» implements IScheduler {
			
			// Declare actors objects
			«FOR instance : network.children.filter(typeof(Instance)).filter[!broadcast]»
				private IActor «instance.name»;
			«ENDFOR»
			
			// Declare broadcast
			«FOR instance : network.children.filter(typeof(Instance)).filter[broadcast]»
				private Broadcast<«instance.getActor.getInput("input").type.doSwitch»> «instance.name»;
			«ENDFOR»
			
			
			@Override
			@SuppressWarnings("unchecked")
			public void initialize( URI serverURI ) {
				
				// Instantiate actors
				«FOR instance : network.children.filter(typeof(Instance)).filter[!broadcast]»
					«IF !instance.getActor.native»
						«instance.name» = new «instance.simpleName»(«printArguments(instance.getActor.parameters, instance.arguments)»);
					«ELSE»
						«instance.name» = new «instance.getActor.simpleName»(«printArguments(instance.getActor.parameters, instance.arguments)»);
					«ENDIF»
				«ENDFOR»
				
				// Instantiate broadcast
				«FOR instance : network.children.filter(typeof(Instance)).filter[broadcast]»
					«instance.name» = new Broadcast<«instance.getActor.getInput("input").type.doSwitch»>(«instance.outgoing.size»);
				«ENDFOR»
				
				@SuppressWarnings("rawtypes")
				Fifo f;
				WebSocketFifo wf;
				«FOR connection : network.connections»					
					
					«IF connection.source instanceof Port »
						/**
						 * Input port «(connection.source as Port).getName()» connected here
						 */
						wf = new  WebSocketFifo<«(connection.source as Port).type.doSwitch»>(
							this,
							"o_"+"«connection.source.label»".substring(2),
							true,
							serverURI,
							«if (connection.size !== null) connection.size else fifoSize»,
							"«(connection.source as Port).type.doSwitch»"
						);
						«(connection.target as Instance).name».setFifo("«connection.targetPort.name»", wf);
						wf.connect("«(connection.source as Port).type.doSwitch»");
					«ELSEIF connection.target instanceof Port »
						/**
						 * Output port «(connection.target as Port).getName()» connected here
						 */
						wf = new  WebSocketFifo<«(connection.target as Port).type.doSwitch»>(
							this,
							"i_"+"«connection.target.label»".substring(2),
							false,
							serverURI,
							1,
							"«(connection.target as Port).type.doSwitch»"
						);
						«(connection.source as Instance).name».setFifo("«connection.sourcePort.name»", wf);
						wf.connect("«(connection.target as Port).type.doSwitch»");
					«ELSE»
						f = new Fifo<« if (connection.target instanceof Instance) connection.targetPort.type.doSwitch else (connection.target as Port).type.doSwitch» >(«if (connection.size !== null) connection.size else fifoSize»);
						«(connection.source as Instance).name».setFifo("«connection.sourcePort.name»", f);
						«(connection.target as Instance).name».setFifo("«connection.targetPort.name»", f);
					«ENDIF»
				«ENDFOR»
				
			}
		
			@Override
			public void schedule() {
				«FOR instance : network.children.filter(typeof(Instance))»
					«IF ! instance.getActor?.initializes.empty »
					«instance.name».initialize();
					«ENDIF»
				«ENDFOR»
				
				int i;
				do {
					i = 0;
					«FOR instance : network.children.filter(typeof(Instance))»
					i += «instance.name».schedule();
					«ENDFOR»
				} while (i > 0);
				
				System.out.println("No more action to launch");
			}
			
			public static void main(String[] args) {
				if( args.length < 1 ) {
					System.out.println( "Usage: java «network.simpleName».«network.simpleName» ws://<server address>:8080/iopod" );
					System.exit( 1 );
				}
				String serverURI = args[0];
				try {
					URI uri = new URI( serverURI );
					CLIParameters.getInstance().setArguments(args);
					GenericSource.setFileName(CLIParameters.getInstance().getSourceFile());
					IScheduler scheduler = new «network.simpleName»();
					scheduler.initialize( uri );
					while( true ) {
						scheduler.schedule();
						try {
							Thread.sleep( 1000L );
						} catch( InterruptedException ex ) {}
					}
				} catch( URISyntaxException ex ) {
					System.out.println( "Invalid server URI: "+serverURI );
					System.exit( 1 );
				}
			}
		}
	'''
	
		
	/**
	 * TODO : replace this awful wart by a great way to detect an instance is  broadcast
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