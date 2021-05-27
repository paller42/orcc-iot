/*
 * Copyright (c) 2021 Gabor Paller
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

#include "hwsupport.h"
#include "modem.h"
#include <string.h>
#include <stdio.h>

int ok_response() {
	char modem_resp[64];

	while( 1 ) {
		if( !USART2_getstring( modem_resp,63 ) )
			return -1;
		if( strcmp( modem_resp,"" ) == 0 )
			continue;
		if( strcmp( modem_resp,"OK" ) == 0 )
			return 1;
		if( strstr( modem_resp,"ERROR" ) != NULL )
			return 0;
	}
}

int any_response() {
	char modem_resp[64];

	while( 1 ) {
		if( !USART2_getstring( modem_resp,63 ) )
			return -1;
		if( strcmp( modem_resp,"" ) == 0 )
			continue;
		if( strcmp( modem_resp,"OK" ) == 0 )
			return 1;
		if( strstr( modem_resp,"ERROR" ) != NULL )
			return 1;
	}
}

int is_registered() {
	char modem_resp[64];
	int registered = 0;
    
	USART2_sendstring( "AT+CGREG?\x0D" );
	while( 1 ) {
		if( !USART2_getstring( modem_resp,63 ) )
			return -1;
		if( strcmp( modem_resp,"" ) == 0 )
			continue;
		if( strncmp( modem_resp,"+CGREG: ",8 ) == 0 ) {
			if( ( strlen( modem_resp ) >= 11 ) && ( modem_resp[9] == ',' ) && ( modem_resp[10] == '1' ) )
				registered = 1;
		} else
		if( strcmp( modem_resp,"OK" ) == 0 )
			return registered;
		if( strstr( modem_resp,"ERROR" ) != NULL )
			return 0;
	}
}

void init_modem() {
	char modem_string[64];

	USART2_resetrcvbuf();
// Check if the modem is registered
	if( is_registered() != 1 ) {
			printf( "init_modem: TE is not registered on the mobile network\n\r" );
			return;
	}
	USART2_sendstring( "AT+CGATT=1\x0D" );
	if( ok_response() != 1 )
		return;
	USART2_sendstring( "AT+SAPBR=0,1\x0D" );
	if( any_response() != 1 )
		return;
	sprintf( modem_string,"AT+SAPBR=3,1,\"APN\",\"%s\"\x0D",network_apn );
	USART2_sendstring( modem_string );
	if( ok_response() != 1 )
		return;
	USART2_sendstring( "AT+SAPBR=1,1\x0D" );
	if( ok_response() != 1 )
		return;
	modem_initialized = 1;
}

int upload_data( int items, int payload_length, fifo_ptrs *datasource ) {
	char modem_string[64];
	unsigned char *item_ptr;
	int item_len;
	uint32_t	utime;
	int32_t base;
	
	sprintf( modem_string, "AT+HTTPDATA=%u,10000\x0D",payload_length );
	USART2_sendstring( modem_string );
	while( 1 ) {
		if( !USART2_getstring( modem_string,63 ) )
			return -1;
		if( strcmp( modem_string,"" ) == 0 )
			continue;
		if( strcmp( modem_string,"DOWNLOAD" ) == 0 )
			break;
		else
		if( strstr( modem_string,"ERROR" ) != NULL )
			return 0;
	}
	base = TIM2_base();
	while( TIM2_diff( base ) < 2 );
// Send the node ID
	item_ptr = (unsigned char*)node_id;
	for( int i = 0 ; i < strlen( node_id ) ; ++i ) 
			USART2_sendbyte( *(item_ptr++) );
	USART2_sendbyte( 0 );
// Send the data items
	for( int i = 0 ; i < items ; ++i ) {
// 4 bytes of timestamp
		utime = (uint32_t)TIM2_base();
		USART2_sendbyte( (unsigned char)( ( utime & 0xFF000000 ) > 24 ) );
		USART2_sendbyte( (unsigned char)( ( utime & 0xFF0000 ) > 16 ) );
		USART2_sendbyte( (unsigned char)( ( utime & 0xFF00 ) > 8 ) );
		USART2_sendbyte( (unsigned char)( utime & 0xFF ) );
// Then the payload
		datasource->payload_data( i, (void *)&item_ptr , &item_len );
		for( int n = 0 ; n < item_len ; ++n )
			USART2_sendbyte( *(item_ptr++) );
	}
	while( 1 ) {
		if( !USART2_getstring( modem_string,63 ) )
			return -1;
		if( strcmp( modem_string,"" ) == 0 )
			continue;
		if( strcmp( modem_string,"OK" ) == 0 )
			break;
		else
		if( strstr( modem_string,"ERROR" ) != NULL )
			return 0;
	}
	return 1;
}

int do_post() {
	char modem_resp[64];
	int rsp = 0;
	int32_t base;

	USART2_sendstring( "AT+HTTPACTION=1\x0D" );
	base = TIM2_base();
	while( 1 ) {
// Give it up after 1 minute anyway
		if( TIM2_diff( base ) > 60 )
			return -1;
		if( !USART2_getstring( modem_resp,63 ) )
			continue;
		if( strcmp( modem_resp,"" ) == 0 )
			continue;
// We expect unsolicited success/failure response
		if( ( strncmp( modem_resp,"+HTTPACTION: 1,",15 ) == 0 ) && 
			( strlen( modem_resp ) >= 18  ) ) {
			rsp = ( strncmp( modem_resp+15,"200",3 ) == 0 );
			break;
		} else
		if( strstr( modem_resp,"ERROR" ) != NULL )
			break;
	}
	return rsp;
}

int send_data( int items, int payload_length, fifo_ptrs *datasource, char *port ) {
	char modemcmd[80];
	int retval = 0;
	if( !modem_initialized )
		init_modem();
	if( modem_initialized ) {
		printf( "Sending %u items\r\n",items );
		while( 1 ) {
			USART2_sendstring( "AT+HTTPTERM\x0D" );
			if( any_response() != 1 ) {
				retval = 0;
				break;
			}
			USART2_sendstring( "AT+HTTPINIT\x0D" );
			if( ok_response() != 1 ) {
				retval = 0;
				break;
			}
			USART2_sendstring( "AT+HTTPPARA=\"CID\",1\x0D" );
			if( ok_response() != 1 ) {
				retval = 0;
				break;
			}
			sprintf( modemcmd, "AT+HTTPPARA=\"URL\",\"http://%s:%d/%s\"\x0D",server_address,server_port,port );
			USART2_sendstring( modemcmd );
			if( ok_response() != 1 ) {
				retval = 0;
				break;
			}
			sprintf( modemcmd, "AT+HTTPPARA=\"CONTENT\",\"application/octet-stream\"\x0D" );
			USART2_sendstring( modemcmd );
			if( ok_response() != 1 ) {
				retval = 0;
				break;
			}
			if( upload_data( items, payload_length, datasource ) != 1 ) {
				retval = 0;
				break;
			}
			if( do_post() != 1 ) {
				retval = 0;
				break;
			}
			retval = 1;
			break;
		}
		USART2_sendstring( "AT+HTTPTERM\x0D" );
		if( ok_response() != 1  )
			retval = 0;
		if( retval == 1 )
			printf( "Sending successful\r\n" );
		else 
			printf( "Error sending data\n" );
	} else
		printf( "send_data: Modem is NOT initialized\n\r" );
	return retval;
}
