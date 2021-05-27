package hu.sze.orcciot.backends.c.STM32F4

import java.util.HashSet
import java.util.Map
import java.util.Properties
import java.util.List
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Entity
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.graph.Vertex
import net.sf.orcc.ir.Var

import static net.sf.orcc.backends.BackendsConstants.*

/**
 * Generate and print network source file for C backend.
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends CTemplate {
	
	protected var Network network;
	
	protected var boolean profile = false	
	protected var boolean newSchedul = false
	var boolean papify = false
	var boolean papifyMultiplex = false

	var boolean genWeights = false
	var int genWeightsActionDataCounter = 0
	var int genWeightsSchedulerDataCounter = 0

	var boolean linkNativeLib
	var String linkNativeLibHeaders
	var int stm32f4_server_port
	var String stm32f4_server_address
	var String stm32f4_nodeId
	var String stm32f4_network_apn
	var List<String> stm32f4_native_lib_headers
		
	def setNetwork(Network network) {
		this.network = network
	}

	override setOptions(Map<String, Object> options) {
		super.setOptions(options)
		if(options.containsKey(PROFILE)){
			profile = options.get(PROFILE) as Boolean
		}
		if (options.containsKey(NEW_SCHEDULER)) {
			newSchedul = options.get(NEW_SCHEDULER) as Boolean
		}
		if(options.containsKey(PAPIFY)){
			papify = options.get(PAPIFY) as Boolean;
			if(options.containsKey(PAPIFY_MULTIPLEX)){
				papifyMultiplex = options.get(PAPIFY_MULTIPLEX) as Boolean;
			}
		}
				
		if(options.containsKey(GEN_WEIGHTS)) {
			genWeights = options.get(GEN_WEIGHTS) as Boolean;
			genWeightsActionDataCounter = 0;
			genWeightsSchedulerDataCounter = 0;
		}

		if(options.containsKey(LINK_NATIVE_LIBRARY)) {
			linkNativeLib = options.get(LINK_NATIVE_LIBRARY) as Boolean;
			linkNativeLibHeaders = options.get(LINK_NATIVE_LIBRARY_HEADERS) as String;
		}
	}
	
	def setStm32f4Properties(Properties stm32f4Props) {
		stm32f4_server_address = stm32f4Props.getProperty( "stm32f4.server.address" )
		stm32f4_server_port = Integer.parseInt( stm32f4Props.getProperty( "stm32f4.server.port" ) )
		stm32f4_nodeId = stm32f4Props.getProperty( "stm32f4.nodeId" )
		stm32f4_network_apn = stm32f4Props.getProperty( "stm32f4.network.apn" )
	}

	def setNativeHeaders( List<String> h ) {
		stm32f4_native_lib_headers = h
	}
	
	def protected getNetworkFileContent() 
	'''
// Generated from «network.name»

#include <locale.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
«printAdditionalIncludes»

#include "types.h"
#include "fifo.h"
#include "util.h"
#include "dataflow.h"
#include "options.h"
#include "scheduler.h"
#include "hwsupport.h"
#include "modem.h"
«IF stm32f4_native_lib_headers != null»
«FOR header : stm32f4_native_lib_headers»
#include "«header»"
«ENDFOR»
«ENDIF»

// Server address
#define SERVER_ADDRESS "«stm32f4_server_address»"
#define SERVER_ADDRESS_BUFLEN	128
char server_address[SERVER_ADDRESS_BUFLEN+1];
#define DEFAULT_SERVER_PORT	«stm32f4_server_port»
int server_port;

int modem_initialized = 0;
char node_id[] = "«stm32f4_nodeId»";
char network_apn[] = "«stm32f4_network_apn»";

«IF linkNativeLib && linkNativeLibHeaders != ""»
«printNativeLibHeaders(linkNativeLibHeaders)»

«ENDIF»
/////////////////////////////////////////////////
// FIFO allocation
«FOR child : network.inputs»
	«child.allocateFifos»
«ENDFOR»
		
«FOR child : network.children»
	«child.allocateFifos»
«ENDFOR»

/////////////////////////////////////////////////
// FIFO pointer assignments
«FOR child : network.inputs»
	«child.assignFifo»
«ENDFOR»
		
«FOR child : network.children»
	«child.assignFifo»
«ENDFOR»

«IF profile»
/////////////////////////////////////////////////
// Declaration of the actions
«FOR child : network.children»
	«FOR action : child.getAdapter(typeof(Actor)).actions»
		action_t action_«child.label»_«action.name» = {"«action.name»", 0, 0, -1, -1, -1, 0, 0};
	«ENDFOR»
«ENDFOR»
«FOR child : network.children»
	action_t *«child.label»_actions[] = {
	«FOR action : child.getAdapter(typeof(Actor)).actions SEPARATOR ","»
			&action_«child.label»_«action.name»
	«ENDFOR»
	};
				
«ENDFOR»
«ENDIF»
«IF genWeights»
/////////////////////////////////////////////////
// Declare rdtsc_data for the actors/actions
«FOR child : network.children»
	«child.allocateGenWeightsActionData»
«ENDFOR»

// Declare rdtsc_data for the actors/scheduler
«FOR child : network.children»
	«child.allocateGenWeightsSchedulerData»
«ENDFOR»

«ENDIF»
«additionalDeclarations»

/////////////////////////////////////////////////
// Actor functions
«FOR child : network.children»
	extern void «child.label»_initialize(schedinfo_t *si);
	extern void «child.label»_scheduler(schedinfo_t *si);
«ENDFOR»
«FOR child : network.inputs»
	extern void «child.name»_initialize(schedinfo_t *si);
	extern void «child.name»_scheduler(schedinfo_t *si);
«ENDFOR»
«FOR child : network.outputs»
	extern void «child.name»_initialize(schedinfo_t *si);
	extern void «child.name»_scheduler(schedinfo_t *si);
«ENDFOR»

/////////////////////////////////////////////////
// Declaration of the actors array
«FOR child : network.children»
	«IF profile»
		actor_t «child.label» = {"«child.label»", «child.label»_initialize, «child.label»_scheduler, 0, 0, 0, 0, NULL, -1, «network.children.indexOf(child)», 0, 1, 0, 0, 0, «child.label»_actions, «child.getAdapter(typeof(Actor)).actions.size», 0, "«child.getAdapter(typeof(Actor)).getFile().getProjectRelativePath().removeFirstSegments(1).removeFileExtension().toString().replace("/", ".")»", 0, 0, 0};
	«ELSE»
		actor_t «child.label» = {"«child.label»", «child.label»_initialize, «child.label»_scheduler, 0, 0, 0, 0, NULL, -1, «network.children.indexOf(child)», 0, 1, 0, 0, 0, NULL, 0, 0, "", 0, 0, 0};
	«ENDIF»						
«ENDFOR»
«FOR child : network.inputs»
	actor_t «child.name» = {"«child.name»", «child.name»_initialize, «child.name»_scheduler, 0, 0, 0, 0, NULL, -1, 0, 0, 1, 0, 0, 0, NULL, 0, 0, "", 0, 0, 0};
«ENDFOR»
«FOR child : network.outputs»
	actor_t «child.name» = {"«child.name»", «child.name»_initialize, «child.name»_scheduler, 0, 0, 0, 0, NULL, -1, 0, 0, 1, 0, 0, 0, NULL, 0, 0, "", 0, 0, 0};
«ENDFOR»

actor_t *actors[] = {
«FOR child : network.children SEPARATOR ","»
	&«child.label»
«ENDFOR»
«IF network.outputs.size>0»
	,
	«FOR child : network.outputs SEPARATOR ","»
		&«child.name»
	«ENDFOR»
«ENDIF»
«IF network.inputs.size>0»
	,
	«FOR child : network.inputs SEPARATOR ","»
		&«child.name»
	«ENDFOR»
«ENDIF»
};

/////////////////////////////////////////////////
// Declaration of the connections array
«FOR connection : network.connections»
	connection_t connection_«connection.source.label»_«connection.target.label» = {&«connection.source.label», &«connection.target.label», 0, 0};
«ENDFOR»

connection_t *connections[] = {
«FOR connection : network.connections SEPARATOR ","»
	&connection_«connection.source.label»_«connection.target.label»
«ENDFOR»
};

/////////////////////////////////////////////////
// Declaration of the network
network_t network = {"«network.name»", actors, connections, «network.allActors.size+network.outputs.size+network.inputs.size», «network.connections.size»};
		
«IF network.hasAttribute("network_shared_variables")»
/////////////////////////////////////////////////
// Shared Variables
«FOR v : network.getAttribute("network_shared_variables").objectValue as HashSet<Var>»
	«v.type.doSwitch» «v.name»«FOR dim : v.type.dimensions»[«dim»]«ENDFOR»;
«ENDFOR»

«ENDIF»

/////////////////////////////////////////////////
// Network server to connect to
void init_server_parameters() {
// Parameters generated by Orcc
	strcpy( server_address,SERVER_ADDRESS );
	server_port = DEFAULT_SERVER_PORT;
}

////////////////////////////////////////////////////////////////////////////////
// Main
int main() {
	«beforeMain»
	conf_hw();
	init_server_parameters();
	setvbuf(stdin, NULL, _IONBF, 0);
	setvbuf(stdout, NULL, _IONBF, 0);
	setvbuf(stderr, NULL, _IONBF, 0);

	options_t *opt = init_orcc();
	set_scheduling_strategy(«IF !newSchedul»"RR"«ELSE»"DD"«ENDIF», opt);
	launcher(opt, &network);
	«afterMain»
	return compareErrors;
}
	'''
	
	def protected assignFifo(Vertex vertex) '''
		«IF vertex instanceof Actor»
			«FOR connList : vertex.getAdapter(typeof(Entity)).outgoingPortMap.values»
				«printFifoAssign(connList.head.source.label, connList.head.sourcePort, connList.head.<Integer>getValueAsObject("idNoBcast"))»
				«FOR conn : connList»
					«IF conn.targetPort === null && conn.target instanceof Port»
						«printOutputPortFifoAssign(conn.target as Port, conn.<Integer>getValueAsObject("idNoBcast"))»
					«ELSE»
						«printFifoAssign(conn.target.label, conn.targetPort, conn.<Integer>getValueAsObject("idNoBcast"))»
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
		«ELSE»
			«IF (vertex as Port).outgoing.size>0»
				«printInputPortFifoAssign(vertex as Port, (vertex as Port).outgoing.get(0).<Integer>getValueAsObject("idNoBcast"))»
				«FOR conn : (vertex as Port).outgoing»
					«printFifoAssign((conn as Connection).target.label, (conn as Connection).targetPort, conn.<Integer>getValueAsObject("idNoBcast"))»
				«ENDFOR»
			«ELSE»
				«printOutputPortFifoAssign(vertex as Port, (vertex as Port).outgoing.get(0).<Integer>getValueAsObject("idNoBcast"))»
			«ENDIF»
		«ENDIF»
	'''
	
	def protected printFifoAssign(String name, Port port, int fifoIndex) '''
		fifo_«port.type.doSwitch»_t *«name»_«port.name» = &fifo_«fifoIndex»;
	'''

	def protected printInputPortFifoAssign(Port port, int fifoIndex) '''
		fifo_«port.type.doSwitch»_t *«port.name»_O = &fifo_«fifoIndex»;
	'''
	
	def protected printOutputPortFifoAssign(Port port, int fifoIndex) '''
		fifo_«port.type.doSwitch»_t *«port.name»_I = &fifo_«fifoIndex»;
	'''

	def protected allocateFifos(Vertex vertex) '''
		«FOR connectionList : vertex.getAdapter(typeof(Entity)).outgoingPortMap.values»
			«allocateFifo(connectionList.get(0), connectionList.size)»
		«ENDFOR»
	'''
	
	def protected allocateFifos(Port port) '''
		«FOR connectionList : port.outgoing»
			«allocateFifo((connectionList as Connection),  port.outgoing.size)»
		«ENDFOR»
	'''
	
	def protected allocateFifo(Connection conn, int nbReaders) '''
		«IF conn.source instanceof Port»
			DECLARE_FIFO(«(conn.source as Port).type.doSwitch», «if (conn.size !== null) conn.size else fifoSize», «conn.<Object>getValueAsObject("idNoBcast")», «nbReaders»)
		«ELSE»
			DECLARE_FIFO(«conn.sourcePort.type.doSwitch», «if (conn.size !== null) conn.size else fifoSize», «conn.<Object>getValueAsObject("idNoBcast")», «nbReaders»)
		«ENDIF»
	'''
	
	def protected allocateGenWeightsActionData(Vertex vertex) '''
		«FOR action : vertex.getAdapter(typeof(Actor)).actions»
			DECLARE_ACTION_PROFILING_DATA(«genWeightsActionDataCounter»)
			rdtsc_data_t *profDataAction_«vertex.label»_«action.name» = &profDataAction_«genWeightsActionDataCounter»;
			«incGenWeightsActionDataCounter»
		«ENDFOR»
	'''
	
	def protected allocateGenWeightsSchedulerData(Vertex vertex) '''
		DECLARE_SCHEDULER_PROFILING_DATA(«genWeightsSchedulerDataCounter», «vertex.getAdapter(typeof(Actor)).actions.length+1», «vertex.getAdapter(typeof(Actor)).actions.length»+1)
		rdtsc_scheduler_map_t *profDataScheduler_«vertex.label» = &profDataScheduler_«genWeightsSchedulerDataCounter»;
		«incGenWeightsSchedulerDataCounter»
	'''
	
	private def incGenWeightsActionDataCounter() {
		genWeightsActionDataCounter++ ''''''
	}
	
	private def incGenWeightsSchedulerDataCounter() {
		genWeightsSchedulerDataCounter++ ''''''
	}
	
	// This method can be override by other backends to print additional includes
	def protected printAdditionalIncludes() ''''''
	
	// This method can be override by other backends to print additional declarations 
	def protected additionalDeclarations() ''''''
	
	// This method can be override by other backends to print additional statements
	// when the program is terminating
	def protected additionalAtExitActions()''''''
	// This method can be override by other backends in case of calling additional 
	// functions before and after the Main function
	def protected afterMain() ''''''
	def protected beforeMain() '''
	«IF papify»
		«IF papifyMultiplex»
			event_init_multiplex();
		«ELSE»
			event_init();	
		«ENDIF»
	«ENDIF»
	'''
}
