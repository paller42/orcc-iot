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
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF YUSE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
 package net.sf.orcc.backends.c

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
 * Generate and print output port source file for C backend.
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

#include <libwebsockets.h>
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



////////////////////////////////////////////////////////////////////////////////
// Instance
extern actor_t «portName»;

////////////////////////////////////////////////////////////////////////////////
// Shared Variables

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs
extern fifo_«nativePortType»_t *«portName»_I;

////////////////////////////////////////////////////////////////////////////////
// Output Fifo control variables
static unsigned int index_I;
static unsigned int numTokens_I;
#define NUM_READERS_O 1
#define SIZE_I «fifoSize»
#define tokens_I «portName»_I->contents

///////////////////////////////////////////////6
// Server address
extern char server_address[];
extern int server_port;

////////////////////////////////////////////////////////////////////////////////
// Websocket functions

static struct lws_context *context;
static struct lws *client_wsi;
static int ssl_connection = 0;
static int writeable = 0;

static int connect_client() {
	struct lws_client_connect_info i;

	memset(&i, 0, sizeof(i));

	i.context = context;
	i.port = server_port;
	i.address = server_address;
	i.path = "/iopod";
	i.host = i.address;
	i.origin = i.address;
	i.ssl_connection = ssl_connection;
	i.protocol = NULL;
	i.local_protocol_name = "iopod";
	i.pwsi = &client_wsi;

	writeable = 0;
	
	return !lws_client_connect_via_info(&i);
}

static int callback_o_port_broker(struct lws *wsi, enum lws_callback_reasons reason,
	void *user, void *in, size_t len) {
	int n,m;
	unsigned long ts;

	switch (reason) {

	case LWS_CALLBACK_PROTOCOL_INIT:
		goto try;

	case LWS_CALLBACK_CLIENT_CONNECTION_ERROR:
		lwsl_err("CLIENT_CONNECTION_ERROR: %s\n",
					in ? (char *)in : "(null)");
		client_wsi = NULL;
		lws_timed_callback_vh_protocol(lws_get_vhost(wsi),
					lws_get_protocol(wsi), LWS_CALLBACK_USER, 1);
		break;

	/* --- client callbacks --- */

	case LWS_CALLBACK_CLIENT_ESTABLISHED:
		lwsl_user("%s: established\n", __func__);
		lws_set_timer_usecs(wsi, 5 * LWS_USEC_PER_SEC);
		break;

	case LWS_CALLBACK_CLIENT_WRITEABLE:
		printf( "writeable:1\n");
		writeable = 1;
		client_wsi = wsi;
		break;

	case LWS_CALLBACK_WS_CLIENT_DROP_PROTOCOL:
		client_wsi = NULL;
		lws_timed_callback_vh_protocol(lws_get_vhost(wsi),
					       lws_get_protocol(wsi),
					       LWS_CALLBACK_USER, 1);
		break;

	case LWS_CALLBACK_TIMER:
		lws_callback_on_writable(wsi);
		lws_set_timer_usecs(wsi, 5 * LWS_USEC_PER_SEC);
		break;

		/* rate-limited client connect retries */

	case LWS_CALLBACK_USER:
		lwsl_notice("%s: LWS_CALLBACK_USER\n", __func__);
	try:
		if (connect_client())
			lws_timed_callback_vh_protocol(lws_get_vhost(wsi),
						       lws_get_protocol(wsi),
						       LWS_CALLBACK_USER, 1);
		break;

	default:
		break;
	}

	return lws_callback_http_dummy(wsi, reason, user, in, len);
}

static const struct lws_protocols protocols[] = {
	{
		"lws-orcc-o_port",
		callback_o_port_broker,
		0,
		0,
	},
	{ NULL, NULL, 0, 0 }
};

////////////////////////////////////////////////////////////////////////////////
// Token functions


static void read_I() {
	index_I = «portName»_I->read_inds[0];
	numTokens_I = index_I + fifo_«nativePortType»_get_num_tokens(«portName»_I, 0);
}

static void read_end_I() {
	«portName»_I->read_inds[0] = index_I;
}

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions
static i32 isSchedulable_p() {
	i32 result;

	result = writeable;
	return result;
}

static void p() {
	«IF nativePortType.equals("string")»        
	string vs;
	«ENDIF»
	«IF nativePortType.equals("i32")»        
	i32 v1;
	«ENDIF»
	«IF nativePortType.equals("u32")»        
	u32 vu;
	«ENDIF»
	«IF nativePortType.equals("float")»        
	float vf;
	«ENDIF»
	int m,n;
	uint8_t msg[LWS_PRE + 125];
	unsigned long ts;
	
	ts = ((unsigned long)time( NULL )) * 1000L;
	«IF nativePortType.equals("string")»        
	strcpy( vs, tokens_I[(index_I + (0)) % SIZE_I] );
	printf("«portName»: %s\n", vs);
	n = lws_snprintf((char *)msg + LWS_PRE, 125,
		"{\"value\":\"%s\",\"timeStamp\":%lu,\"pod\":\"%s\",\"type\":\"String\"}",vs,ts,"«connectedPortName»");
	«ENDIF»
	«IF nativePortType.equals("i32")»        
	v1 = tokens_I[(index_I + (0)) % SIZE_I];
	printf("«portName»: %d\n", v1);
	n = lws_snprintf((char *)msg + LWS_PRE, 125,
		"{\"value\":\"%d\",\"timeStamp\":%lu,\"pod\":\"%s\",\"type\":\"Integer\"}",v1,ts,"«connectedPortName»");
	«ENDIF»
	«IF nativePortType.equals("u32")»        
	vu = tokens_I[(index_I + (0)) % SIZE_I];
	printf("«portName»: %u\n", vu);
	n = lws_snprintf((char *)msg + LWS_PRE, 125,
		"{\"value\":\"%u\",\"timeStamp\":%lu,\"pod\":\"%s\",\"type\":\"Integer\"}",vu,ts,"«connectedPortName»");
	«ENDIF»
	«IF nativePortType.equals("float")»        
	vf = tokens_I[(index_I + (0)) % SIZE_I];
	printf("«portName»: %f\n", vf);
	n = lws_snprintf((char *)msg + LWS_PRE, 125,
		"{\"value\":\"%f\",\"timeStamp\":%lu,\"pod\":\"%s\",\"type\":\"Float\"}",vf,ts,"«connectedPortName»");
	«ENDIF»
	lwsl_user("Sending data...\n");

	if( client_wsi != NULL ) {
		m = lws_write(client_wsi, msg + LWS_PRE, n, LWS_WRITE_TEXT);
		lws_callback_on_writable(client_wsi);
		writeable = 0;
		printf( "writeable:0\n");
	} else
		m = 0;
		if (m < n)
			lwsl_err("sending message failed: %d\n", m);

	
	// Update ports indexes
		index_I += 1;
	}

////////////////////////////////////////////////////////////////////////////////
// Initializes

void «portName»_initialize(schedinfo_t *si) {
	struct lws_context_creation_info info;
	const char *p;
	int n = 0, logs = LLL_USER | LLL_ERR | LLL_WARN | LLL_NOTICE
	/* for LLL_ verbosity above NOTICE to be built into lws,
	* lws must have been configured and built with
	* -DCMAKE_BUILD_TYPE=DEBUG instead of =RELEASE */
	/* | LLL_INFO */ /* | LLL_PARSER */ /* | LLL_HEADER */
	/* | LLL_EXT */ /* | LLL_CLIENT */ /* | LLL_LATENCY */
	/* | LLL_DEBUG */;
	memset(&info, 0, sizeof info); /* otherwise uninitialized garbage */
	/*	info.options = LWS_SERVER_OPTION_DO_SSL_GLOBAL_INIT; */
	info.port = CONTEXT_PORT_NO_LISTEN; /* we do not run any server */
	info.protocols = protocols;
	context = lws_create_context(&info);
	if (!context) {
		printf("lws init failed\n");
	} else
		printf( "«portName» initialized\n" );
}

////////////////////////////////////////////////////////////////////////////////
// Action scheduler
void «portName»_scheduler(schedinfo_t *si) {
	int i = 0;
	si->ports = 0;

	read_I();

	while (1) {
		lws_service(context, 0);
		if ( ( numTokens_I - index_I >= 1 ) && isSchedulable_p()) {
			p();
			printf( "«portName»_scheduler after p()\n" );
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
