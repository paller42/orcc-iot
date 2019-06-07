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
 * Generate and print input port source file for C backend.
 *  
 * @author Gabor Paller
 * 
 */
class InputPortPrinter extends CTemplate {

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
			this.port = port;
			this.portName = port.getName();
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
			OrccLogger::severeln( "Input setPort error: "+ex )
			throw new OrccRuntimeException( "Input setPort error" )
		}
	}
	
	def protected getFileContent()
	'''
// Source file is «portName».c

#include <libwebsockets.h>
#include <cjson/cJSON.h>
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
extern fifo_«nativePortType»_t *«portName»_O;

////////////////////////////////////////////////////////////////////////////////
// Output Fifo control variables
static unsigned int index_O;
static unsigned int numTokens_O;
#define NUM_READERS_O 1
#define SIZE_O «maxFifoSize()»
#define tokens_O «portName»_O->contents

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

static int subscribe_required = 0;
static int data_available = 0;
static int connected = 0;

struct json_inp {
	char *port;
	double value;
	char *string_value;
	double timestamp;
	char *type;
};

static int connect_client() {
	struct lws_client_connect_info i;

	printf( "connect_client\n" );
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
	subscribe_required = 0;
	connected = 0;
	data_available = 0;
	
	return !lws_client_connect_via_info(&i);
}

#define RECEIVE_BUFLEN 128

static void traverse_json( cJSON *jp ) {
	if( jp == NULL )
		return;
	while( jp != NULL ) {
		if( cJSON_IsObject( jp ) ) {
				printf( " ( " );
				traverse_json( jp->child );
				printf( " )\n " );
		} else {
			printf( "name: %s",jp->string );
			if( cJSON_IsString( jp ) )
				printf( "; string: %s\n",jp->valuestring );
			else
			if( cJSON_IsNumber( jp ) )
				printf( "; number: %f\n",jp->valuedouble );
			else
				printf( "; unknown type\n" );
		}
		jp = jp->next;
	}
}

////////////////////////////////////////////////////////////////////////////////
// Token functions

static void write_O() {
	index_O = «portName»_O->write_ind;
}

static void write_end_O() {
	«portName»_O->write_ind = index_O;
}

static int extract_json_inp( cJSON *jp, struct json_inp *j ) {
	int value_set = 0;
	int timestamp_set = 0;
	int port_set = 0;
	int type_set = 0;
	
	if( jp->child == NULL )
		return 0;
	jp = jp->child;
	while( jp != NULL ) {
		if( strcmp( jp->string,"value" ) == 0 ) {
			j->value = jp->valuedouble;
			j->string_value = jp->valuestring;
			value_set = 1;
		} else
		if( strcmp( jp->string,"timeStamp" ) == 0 ) {
			j->timestamp = jp->valuedouble;
			timestamp_set = 1;
		} else
		if( strcmp( jp->string,"pod" ) == 0 ) {
			j->port = jp->valuestring;
			port_set = 1;
		} else
		if( strcmp( jp->string,"type" ) == 0 ) {
			j->type = jp->valuestring;
			type_set = 1;
		}
		jp = jp->next;
	}
	return value_set && timestamp_set && port_set && type_set;
}

static int callback_i_port_broker(struct lws *wsi, enum lws_callback_reasons reason,
			void *user, void *in, size_t len) {
	int n,m;
	unsigned long ts;
	char recv_buf[RECEIVE_BUFLEN+1];
	cJSON *json;
	char *json_printable;
	struct json_inp inp;
	
	switch (reason) {

	case LWS_CALLBACK_PROTOCOL_INIT:
		printf( "LWS_CALLBACK_PROTOCOL_INIT\n" );
		goto try;

	case LWS_CALLBACK_CLIENT_CONNECTION_ERROR:
		printf("CLIENT_CONNECTION_ERROR: %s\n",
			 in ? (char *)in : "(null)");
		client_wsi = NULL;
		lws_timed_callback_vh_protocol(lws_get_vhost(wsi),
				lws_get_protocol(wsi), LWS_CALLBACK_USER, 1);
		break;

	/* --- client callbacks --- */

	case LWS_CALLBACK_CLIENT_ESTABLISHED:
		printf("LWS_CALLBACK_CLIENT_ESTABLISHED\n" );
		lws_set_timer_usecs(wsi, 5 * LWS_USEC_PER_SEC);
		connected = 1;
		subscribe_required = 1;
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
		
	case LWS_CALLBACK_CLOSED:
		connected = 0;
		break;

	case LWS_CALLBACK_TIMER:
		lws_callback_on_writable(wsi);
		lws_set_timer_usecs(wsi, 5 * LWS_USEC_PER_SEC);
		break;

	case LWS_CALLBACK_CLIENT_RECEIVE:
		n = len < RECEIVE_BUFLEN ? len : RECEIVE_BUFLEN;
		strncpy( recv_buf,in,n );
		recv_buf[n] = 0;
		printf( "RECEIVE: '%s'\n",recv_buf );
		json = cJSON_Parse(recv_buf);
		if( extract_json_inp( json, &inp ) ) {
			printf( "value:     %f\n",inp.value );
			printf( "timestamp: %f\n",inp.timestamp );
			printf( "port:      %s\n",inp.port );
			printf( "type:      %s\n",inp.type );
		} else
			printf( "extract_json_inp failed\n" );
		if( strcmp( inp.port,"«connectedPortName»" ) == 0 ) {
			«IF nativePortType.equals("i32") || nativePortType.equals("u32")»        
			if( strcmp( inp.type,"Integer" ) == 0 ) {
// This is possible because the websocket library delivers events in the context of lws_service
// call so there's no race condition here
				write_O();
				tokens_O[(index_O + (0)) % SIZE_O] = inp.value;
				index_O += 1;
				write_end_O();
			}
			«ENDIF»
			«IF nativePortType.equals("float")»        
			if( strcmp( inp.type,"Float" ) == 0 ) {
// This is possible because the websocket library delivers events in the context of lws_service
// call so there's no race condition here
				write_O();
				tokens_O[(index_O + (0)) % SIZE_O] = inp.value;
				index_O += 1;
				write_end_O();
			}
			«ENDIF»
			«IF nativePortType.equals("string")»        
			if( strcmp( inp.type,"String" ) == 0 ) {
// This is possible because the websocket library delivers events in the context of lws_service
// call so there's no race condition here
				write_O();
				strcpy( tokens_O[(index_O + (0)) % SIZE_O], inp.string_value );
				index_O += 1;
				write_end_O();
			}
			«ENDIF»
		}
		cJSON_Delete(json);
		break;
		
	/* rate-limited client connect retries */
	case LWS_CALLBACK_USER:
		printf("%s: LWS_CALLBACK_USER\n", __func__);
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
		"lws-orcc-i_port",
		callback_i_port_broker,
		0,
		0,
	},
	{ NULL, NULL, 0, 0 }
};

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions
static i32 isSchedulable_p() {
	i32 result;
	int m,n;
	uint8_t msg[LWS_PRE + 125];

	if( subscribe_required ) {
		n = lws_snprintf((char *)msg + LWS_PRE, 125,
					"{\"value\":%d,\"timeStamp\":%lu,\"pod\":\"%s\",\"type\":\"Integer\"}",0,0L,"«connectedPortName»");
		printf("Subscribing to %s\n","«connectedPortName»");

		if( client_wsi != NULL ) {
			m = lws_write(client_wsi, msg + LWS_PRE, n, LWS_WRITE_TEXT);
			lws_callback_on_writable(client_wsi);
			writeable = 0;
			printf( "writeable:0\n");
			subscribe_required = 0;
		} else
			m = 0;
		if (m < n)
			printf("sending subscription message failed: %s,%d\n", "«portName»", m);
	}
	result = data_available;
	return result;
}

static void p() {
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
	if (!context)
		printf("lws init failed\n");
	else
		printf( "«portName» initialized\n" );
	write_O();
	write_end_O();
}

////////////////////////////////////////////////////////////////////////////////
// Action scheduler
void «portName»_scheduler(schedinfo_t *si) {
	int i = 0;
	si->ports = 0;


	while (1) {
		lws_service(context, 0);
		if (isSchedulable_p()) {
			int stop = 0;
			write_O();
			if (1 > SIZE_O - index_O + «portName»_O->read_inds[0]) {
				stop = 1;
			}
			if (stop != 0) {
				si->num_firings = i;
				si->reason = full;
				goto finished;
			}
			p();
			i++;
			write_end_O();
		} else {
			si->num_firings = i;
			si->reason = starved;
			goto finished;
		}
	}
finished:
	return;
}
	'''
	
	def private setDebug() {
	}

	def private maxFifoSize() {
		var mf = fifoSize
		for( edge : this.port.getOutgoing() ) {
			var c = (edge as Connection)
			if( c.getSize() > mf )
				mf = c.getSize()
		}
		return mf
	}
	
	def private getNativePortType() {
		OrccLogger::warnln( "Input port: "+this.port+"; type: "+this.port.type )
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
