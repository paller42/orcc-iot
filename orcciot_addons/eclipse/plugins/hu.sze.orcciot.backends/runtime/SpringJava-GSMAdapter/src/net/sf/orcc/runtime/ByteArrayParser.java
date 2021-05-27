/*
 * @author Paller Gábor  	
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
package net.sf.orcc.runtime;

import java.io.EOFException;

public class ByteArrayParser {
	byte[] byteArray;
	int pos;
	
	public ByteArrayParser( byte[] byteArray ) {
		this.byteArray = byteArray;
		pos = 0;
	}
	

	public long readUnsignedInt() throws EOFException {
		long l = 0;
		if( ( pos +4 ) >= byteArray.length )
			throw new EOFException();
		l |= byteArray[pos++] & 0xFF;
		l <<= 8;
		l |= byteArray[pos++] & 0xFF;
		l <<= 8;
		l |= byteArray[pos++] & 0xFF;
		l <<= 8;
		l |= byteArray[pos++] & 0xFF;
		return l;
	}

	public String readString() throws EOFException {
		StringBuffer buf = new StringBuffer();
		if( pos >= byteArray.length )
			throw new EOFException();
		while( pos < byteArray.length ) {
			byte b = byteArray[pos++];
			if( b == 0 )
                break;
			buf.append( (char)b );
		}
		return new String( buf );
	}
}
