package hu.sze.orcciot.backends.c.STM32F4

import java.io.File
import java.util.HashMap
import java.util.List
import java.util.Map
import net.sf.orcc.OrccRuntimeException
import net.sf.orcc.backends.ir.BlockFor
import net.sf.orcc.backends.ir.InstTernary
import net.sf.orcc.df.Action
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.DfFactory
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.Port
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.Arg
import net.sf.orcc.ir.ArgByRef
import net.sf.orcc.ir.ArgByVal
import net.sf.orcc.ir.BlockBasic
import net.sf.orcc.ir.BlockIf
import net.sf.orcc.ir.BlockWhile
import net.sf.orcc.ir.ExprVar
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Param
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Var
import net.sf.orcc.util.Attributable
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.util.util.EcoreHelper

import static net.sf.orcc.OrccLaunchConstants.*
import static net.sf.orcc.backends.BackendsConstants.*
import static net.sf.orcc.util.OrccAttributes.*
import net.sf.orcc.df.Network
import java.util.HashSet

/**
 * Generate and print output port source file for the STM32F4 backend.
 *  
 * @author Gabor Paller
 * 
 */
class OutputPortPrinter extends CTemplate {

	protected var Port port

	protected var String portName
	protected var String connectedPortName
	protected var String nativePortType
	protected var String javaPortType

	var boolean profile = false
	
	var boolean inlineActors = false
	var boolean inlineActions = false
	
	var boolean checkArrayInbounds = false
	
	var boolean newSchedul = false
	
	var boolean enableTrace = false
	var String traceFolder
	
	var boolean isActionAligned = false
	
	var boolean debugActor = false
	var boolean debugAction = false
			
	var boolean linkNativeLib
	var String linkNativeLibHeaders

	override setOptions(Map<String, Object> options) {
		super.setOptions(options)
		if(options.containsKey(PROFILE)){
			profile = options.get(PROFILE) as Boolean
		}
		if (options.containsKey(CHECK_ARRAY_INBOUNDS)) {
			checkArrayInbounds = options.get(CHECK_ARRAY_INBOUNDS) as Boolean
		}

		if (options.containsKey(NEW_SCHEDULER)) {
			newSchedul = options.get(NEW_SCHEDULER) as Boolean
		}
		if (options.containsKey(ENABLE_TRACES)) {
			enableTrace = options.get(ENABLE_TRACES) as Boolean
			traceFolder = (options.get(TRACES_FOLDER) as String)?.replace('\\', "\\\\")
		}
		if(options.containsKey(INLINE)){
			inlineActors = options.get(INLINE) as Boolean
			if(options.containsKey(INLINE_NOTACTIONS)){
				inlineActions = !options.get(INLINE_NOTACTIONS) as Boolean
			}
		}

		if(options.containsKey(LINK_NATIVE_LIBRARY)) {
			linkNativeLib = options.get(LINK_NATIVE_LIBRARY) as Boolean;
			linkNativeLibHeaders = options.get(LINK_NATIVE_LIBRARY_HEADERS) as String;
		}
	}

	def getPortContent(Port port) {
		setPort(port)
		fileContent
	}

	def setPort(Port port) {
		try {
			this.port = port
			this.portName = port.getName()
			if( this.portName.startsWith("i") ) {
				this.connectedPortName = "o";
			} else {
				this.connectedPortName = "i";
			}
			this.connectedPortName = this.connectedPortName + "_" + this.portName.substring(2);
			setDebug();
			getNativePortType();
			getJavaPortType();
		} catch( Exception ex ) {
			OrccLogger::severeln( "Output setPort: "+ex )
			throw new OrccRuntimeException( "Output setPort error" )
		}
	}
	
	def protected getFileContent()
		'''
// Source file is "«portName».c"

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "orcc_config.h"

#include "types.h"
#include "fifo.h"
#include "util.h"
#include "scheduler.h"
#include "dataflow.h"
#include "cycle.h"
#include "modem.h"

#define BATCH_SIZE  32

////////////////////////////////////////////////////////////////////////////////
// Instance
extern actor_t «portName»;

////////////////////////////////////////////////////////////////////////////////
// Shared Variables

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs
extern fifo_string_t *«portName»_I;

////////////////////////////////////////////////////////////////////////////////
// Input Fifo control variables
static unsigned int index_I;
static unsigned int numTokens_I;
#define NUM_READERS_O 1
#define SIZE_I «maxFifoSize()»
#define tokens_I «portName»_I->contents

///////////////////////////////////////////////6
// Server address
extern char server_address[];
extern int server_port;

////////////////////////////////////////////////////////////////////////////////
// Token functions


static void read_I() {
	index_I = «portName»_I->read_inds[0];
	numTokens_I = index_I + fifo_string_get_num_tokens(«portName»_I, 0);
}

static void read_end_I() {
	«portName»_I->read_inds[0] = index_I;
}

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int payload_length( int num_tokens ) {
    int len = strlen( node_id ) + 1;    // node id string + its trailing 0
    for( int i = 0 ; i < num_tokens ; ++i ) {
        int idx = ( index_I + i ) % SIZE_I;
        len += strlen( tokens_I[ idx ] ) + 1;   // +1 for trailing 0
    }
    return len;
}

static void payload_data( int payload_idx, void **payload_ptr, int *payload_size ) {
    char *p = tokens_I[ ( index_I + payload_idx ) % SIZE_I];
    int size = strlen( p ) + 1 + 4; // 4 byte timestamp, 1 byte trailing 0
    *payload_ptr = p;
    *payload_size = size;
}


////////////////////////////////////////////////////////////////////////////////
// Actions
static i32 isSchedulable_p() {
	return 1;
}

static void p() {
    fifo_ptrs ptrs;
    int pl;
    
    int num_tokens = numTokens_I - index_I;
    num_tokens = num_tokens > BATCH_SIZE ? BATCH_SIZE : num_tokens;
    pl = payload_length( num_tokens );
	ptrs.payload_data = &payload_data;
    if( send_data( num_tokens, pl, &ptrs,"«connectedPortName»" ) != 1 )
        printf( "Error sending data\n" );
    else
        printf("«portName»: %u tokens out\n", num_tokens);
    index_I += num_tokens;
}

////////////////////////////////////////////////////////////////////////////////
// Initializes

void «portName»_initialize(schedinfo_t *si) {
}

////////////////////////////////////////////////////////////////////////////////
// Action scheduler
void «portName»_scheduler(schedinfo_t *si) {
	int i = 0;
	si->ports = 0;

	read_I();

	while (1) {
		if ( ( numTokens_I - index_I >= 1 ) && isSchedulable_p()) {
			p();
			printf( "«portName»_scheduler after p()\n\r" );
			i++;
		} else {
			si->num_firings = i;
			si->reason = starved;
			goto finished;
		}
	}
finished:
	read_end_I();
}

'''
	
	def private setDebug() {
	}

    def private maxFifoSize() {
		var mf = 0
		for( edge : this.port.getIncoming() ) {
			var c = (edge as Connection)
			if( c.getSize() > mf )
				mf = c.getSize()
		}
		if( mf == 0 )
            mf = fifoSize
		return mf
	}

	def private getNativePortType() {
		OrccLogger::warnln( "Output port: "+this.port+"; type: "+this.port.type )
		this.nativePortType = "i32"
		if(this.port.type.string)
			this.nativePortType = "string"
		else
		if(this.port.type.int )
			this.nativePortType = "i32"
		else
		if(this.port.type.uint)
			this.nativePortType = "u32"
		else
		if(this.port.type.float)
			this.nativePortType = "float"
	}
	
	def private getJavaPortType() {
		this.javaPortType = "Integer"
		if(this.port.type.string)
			this.javaPortType = "String"
		else
		if(this.port.type.float)
			this.javaPortType = "Float"
	}

}
