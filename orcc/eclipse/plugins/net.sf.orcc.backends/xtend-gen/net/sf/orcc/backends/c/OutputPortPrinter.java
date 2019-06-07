/**
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
package net.sf.orcc.backends.c;

import java.util.Map;
import net.sf.orcc.OrccLaunchConstants;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.BackendsConstants;
import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.util.OrccLogger;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Generate and print output port source file for C backend.
 * 
 * @author Gabor Paller
 */
@SuppressWarnings("all")
public class OutputPortPrinter extends CTemplate {
  protected Port port;
  
  protected String portName;
  
  protected String connectedPortName;
  
  protected String nativePortType;
  
  protected String javaPortType;
  
  private boolean profile = false;
  
  private boolean inlineActors = false;
  
  private boolean inlineActions = false;
  
  private boolean checkArrayInbounds = false;
  
  private boolean newSchedul = false;
  
  private boolean enableTrace = false;
  
  private String traceFolder;
  
  private boolean isActionAligned = false;
  
  private boolean debugActor = false;
  
  private boolean debugAction = false;
  
  private boolean linkNativeLib;
  
  private String linkNativeLibHeaders;
  
  @Override
  public void setOptions(final Map<String, Object> options) {
    super.setOptions(options);
    boolean _containsKey = options.containsKey(BackendsConstants.PROFILE);
    if (_containsKey) {
      Object _get = options.get(BackendsConstants.PROFILE);
      this.profile = (((Boolean) _get)).booleanValue();
    }
    boolean _containsKey_1 = options.containsKey(BackendsConstants.CHECK_ARRAY_INBOUNDS);
    if (_containsKey_1) {
      Object _get_1 = options.get(BackendsConstants.CHECK_ARRAY_INBOUNDS);
      this.checkArrayInbounds = (((Boolean) _get_1)).booleanValue();
    }
    boolean _containsKey_2 = options.containsKey(BackendsConstants.NEW_SCHEDULER);
    if (_containsKey_2) {
      Object _get_2 = options.get(BackendsConstants.NEW_SCHEDULER);
      this.newSchedul = (((Boolean) _get_2)).booleanValue();
    }
    boolean _containsKey_3 = options.containsKey(OrccLaunchConstants.ENABLE_TRACES);
    if (_containsKey_3) {
      Object _get_3 = options.get(OrccLaunchConstants.ENABLE_TRACES);
      this.enableTrace = (((Boolean) _get_3)).booleanValue();
      Object _get_4 = options.get(OrccLaunchConstants.TRACES_FOLDER);
      String _replace = null;
      if (((String) _get_4)!=null) {
        _replace=((String) _get_4).replace("\\", "\\\\");
      }
      this.traceFolder = _replace;
    }
    boolean _containsKey_4 = options.containsKey(BackendsConstants.INLINE);
    if (_containsKey_4) {
      Object _get_5 = options.get(BackendsConstants.INLINE);
      this.inlineActors = (((Boolean) _get_5)).booleanValue();
      boolean _containsKey_5 = options.containsKey(BackendsConstants.INLINE_NOTACTIONS);
      if (_containsKey_5) {
        Object _get_6 = options.get(BackendsConstants.INLINE_NOTACTIONS);
        boolean _not = (!(((Boolean) _get_6)).booleanValue());
        this.inlineActions = _not;
      }
    }
    boolean _containsKey_6 = options.containsKey(BackendsConstants.LINK_NATIVE_LIBRARY);
    if (_containsKey_6) {
      Object _get_7 = options.get(BackendsConstants.LINK_NATIVE_LIBRARY);
      this.linkNativeLib = (((Boolean) _get_7)).booleanValue();
      Object _get_8 = options.get(BackendsConstants.LINK_NATIVE_LIBRARY_HEADERS);
      this.linkNativeLibHeaders = ((String) _get_8);
    }
  }
  
  public CharSequence getPortContent(final Port port) {
    CharSequence _xblockexpression = null;
    {
      this.setPort(port);
      _xblockexpression = this.getFileContent();
    }
    return _xblockexpression;
  }
  
  public String setPort(final Port port) {
    String _xtrycatchfinallyexpression = null;
    try {
      String _xblockexpression = null;
      {
        this.port = port;
        this.portName = port.getName();
        boolean _startsWith = this.portName.startsWith("i");
        if (_startsWith) {
          this.connectedPortName = "o";
        } else {
          this.connectedPortName = "i";
        }
        String _substring = this.portName.substring(2);
        String _plus = ((this.connectedPortName + "_") + _substring);
        this.connectedPortName = _plus;
        this.setDebug();
        this.getNativePortType();
        _xblockexpression = this.getJavaPortType();
      }
      _xtrycatchfinallyexpression = _xblockexpression;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception ex = (Exception)_t;
        OrccLogger.severeln(("Output setPort: " + ex));
        throw new OrccRuntimeException("Output setPort error");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  protected CharSequence getFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// Source file is \"");
    _builder.append(this.portName);
    _builder.append(".c\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include <libwebsockets.h>");
    _builder.newLine();
    _builder.append("#include <string.h>");
    _builder.newLine();
    _builder.append("#include <stdio.h>");
    _builder.newLine();
    _builder.append("#include <stdlib.h>");
    _builder.newLine();
    _builder.append("#include \"orcc_config.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"types.h\"");
    _builder.newLine();
    _builder.append("#include \"fifo.h\"");
    _builder.newLine();
    _builder.append("#include \"util.h\"");
    _builder.newLine();
    _builder.append("#include \"scheduler.h\"");
    _builder.newLine();
    _builder.append("#include \"dataflow.h\"");
    _builder.newLine();
    _builder.append("#include \"cycle.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Instance");
    _builder.newLine();
    _builder.append("extern actor_t ");
    _builder.append(this.portName);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Shared Variables");
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Input FIFOs");
    _builder.newLine();
    _builder.append("extern fifo_");
    _builder.append(this.nativePortType);
    _builder.append("_t *");
    _builder.append(this.portName);
    _builder.append("_I;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Output Fifo control variables");
    _builder.newLine();
    _builder.append("static unsigned int index_I;");
    _builder.newLine();
    _builder.append("static unsigned int numTokens_I;");
    _builder.newLine();
    _builder.append("#define NUM_READERS_O 1");
    _builder.newLine();
    _builder.append("#define SIZE_I ");
    _builder.append(this.fifoSize);
    _builder.newLineIfNotEmpty();
    _builder.append("#define tokens_I ");
    _builder.append(this.portName);
    _builder.append("_I->contents");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("///////////////////////////////////////////////6");
    _builder.newLine();
    _builder.append("// Server address");
    _builder.newLine();
    _builder.append("extern char server_address[];");
    _builder.newLine();
    _builder.append("extern int server_port;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Websocket functions");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static struct lws_context *context;");
    _builder.newLine();
    _builder.append("static struct lws *client_wsi;");
    _builder.newLine();
    _builder.append("static int ssl_connection = 0;");
    _builder.newLine();
    _builder.append("static int writeable = 0;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static int connect_client() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("struct lws_client_connect_info i;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("memset(&i, 0, sizeof(i));");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.context = context;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.port = server_port;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.address = server_address;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.path = \"/iopod\";");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.host = i.address;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.origin = i.address;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.ssl_connection = ssl_connection;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.protocol = NULL;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.local_protocol_name = \"iopod\";");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i.pwsi = &client_wsi;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("writeable = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return !lws_client_connect_via_info(&i);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static int callback_o_port_broker(struct lws *wsi, enum lws_callback_reasons reason,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void *user, void *in, size_t len) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int n,m;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("unsigned long ts;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("switch (reason) {");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("case LWS_CALLBACK_PROTOCOL_INIT:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("goto try;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("case LWS_CALLBACK_CLIENT_CONNECTION_ERROR:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lwsl_err(\"CLIENT_CONNECTION_ERROR: %s\\n\",");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("in ? (char *)in : \"(null)\");");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("client_wsi = NULL;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lws_timed_callback_vh_protocol(lws_get_vhost(wsi),");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("lws_get_protocol(wsi), LWS_CALLBACK_USER, 1);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("break;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* --- client callbacks --- */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("case LWS_CALLBACK_CLIENT_ESTABLISHED:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lwsl_user(\"%s: established\\n\", __func__);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lws_set_timer_usecs(wsi, 5 * LWS_USEC_PER_SEC);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("break;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("case LWS_CALLBACK_CLIENT_WRITEABLE:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printf( \"writeable:1\\n\");");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("writeable = 1;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("client_wsi = wsi;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("break;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("case LWS_CALLBACK_WS_CLIENT_DROP_PROTOCOL:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("client_wsi = NULL;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lws_timed_callback_vh_protocol(lws_get_vhost(wsi),");
    _builder.newLine();
    _builder.append("\t\t\t\t\t       ");
    _builder.append("lws_get_protocol(wsi),");
    _builder.newLine();
    _builder.append("\t\t\t\t\t       ");
    _builder.append("LWS_CALLBACK_USER, 1);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("break;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("case LWS_CALLBACK_TIMER:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lws_callback_on_writable(wsi);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lws_set_timer_usecs(wsi, 5 * LWS_USEC_PER_SEC);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("break;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("/* rate-limited client connect retries */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("case LWS_CALLBACK_USER:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lwsl_notice(\"%s: LWS_CALLBACK_USER\\n\", __func__);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("try:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (connect_client())");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("lws_timed_callback_vh_protocol(lws_get_vhost(wsi),");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t       ");
    _builder.append("lws_get_protocol(wsi),");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t       ");
    _builder.append("LWS_CALLBACK_USER, 1);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("break;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("break;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return lws_callback_http_dummy(wsi, reason, user, in, len);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static const struct lws_protocols protocols[] = {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"lws-orcc-o_port\",");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("callback_o_port_broker,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("0,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("0,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("{ NULL, NULL, 0, 0 }");
    _builder.newLine();
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Token functions");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("static void read_I() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("index_I = ");
    _builder.append(this.portName, "\t");
    _builder.append("_I->read_inds[0];");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("numTokens_I = index_I + fifo_");
    _builder.append(this.nativePortType, "\t");
    _builder.append("_get_num_tokens(");
    _builder.append(this.portName, "\t");
    _builder.append("_I, 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static void read_end_I() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(this.portName, "\t");
    _builder.append("_I->read_inds[0] = index_I;");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Functions/procedures");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Actions");
    _builder.newLine();
    _builder.append("static i32 isSchedulable_p() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("i32 result;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("result = writeable;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return result;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static void p() {");
    _builder.newLine();
    {
      boolean _equals = this.nativePortType.equals("string");
      if (_equals) {
        _builder.append("\t");
        _builder.append("string vs;");
        _builder.newLine();
      }
    }
    {
      boolean _equals_1 = this.nativePortType.equals("i32");
      if (_equals_1) {
        _builder.append("\t");
        _builder.append("i32 v1;");
        _builder.newLine();
      }
    }
    {
      boolean _equals_2 = this.nativePortType.equals("u32");
      if (_equals_2) {
        _builder.append("\t");
        _builder.append("u32 vu;");
        _builder.newLine();
      }
    }
    {
      boolean _equals_3 = this.nativePortType.equals("float");
      if (_equals_3) {
        _builder.append("\t");
        _builder.append("float vf;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("int m,n;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("uint8_t msg[LWS_PRE + 125];");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("unsigned long ts;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ts = ((unsigned long)time( NULL )) * 1000L;");
    _builder.newLine();
    {
      boolean _equals_4 = this.nativePortType.equals("string");
      if (_equals_4) {
        _builder.append("\t");
        _builder.append("strcpy( vs, tokens_I[(index_I + (0)) % SIZE_I] );");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"");
        _builder.append(this.portName, "\t");
        _builder.append(": %s\\n\", vs);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("n = lws_snprintf((char *)msg + LWS_PRE, 125,");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("\"{\\\"value\\\":\\\"%s\\\",\\\"timeStamp\\\":%lu,\\\"pod\\\":\\\"%s\\\",\\\"type\\\":\\\"String\\\"}\",vs,ts,\"");
        _builder.append(this.connectedPortName, "\t\t");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _equals_5 = this.nativePortType.equals("i32");
      if (_equals_5) {
        _builder.append("\t");
        _builder.append("v1 = tokens_I[(index_I + (0)) % SIZE_I];");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"");
        _builder.append(this.portName, "\t");
        _builder.append(": %d\\n\", v1);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("n = lws_snprintf((char *)msg + LWS_PRE, 125,");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("\"{\\\"value\\\":\\\"%d\\\",\\\"timeStamp\\\":%lu,\\\"pod\\\":\\\"%s\\\",\\\"type\\\":\\\"Integer\\\"}\",v1,ts,\"");
        _builder.append(this.connectedPortName, "\t\t");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _equals_6 = this.nativePortType.equals("u32");
      if (_equals_6) {
        _builder.append("\t");
        _builder.append("vu = tokens_I[(index_I + (0)) % SIZE_I];");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"");
        _builder.append(this.portName, "\t");
        _builder.append(": %u\\n\", vu);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("n = lws_snprintf((char *)msg + LWS_PRE, 125,");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("\"{\\\"value\\\":\\\"%u\\\",\\\"timeStamp\\\":%lu,\\\"pod\\\":\\\"%s\\\",\\\"type\\\":\\\"Integer\\\"}\",vu,ts,\"");
        _builder.append(this.connectedPortName, "\t\t");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _equals_7 = this.nativePortType.equals("float");
      if (_equals_7) {
        _builder.append("\t");
        _builder.append("vf = tokens_I[(index_I + (0)) % SIZE_I];");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"");
        _builder.append(this.portName, "\t");
        _builder.append(": %f\\n\", vf);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("n = lws_snprintf((char *)msg + LWS_PRE, 125,");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("\"{\\\"value\\\":\\\"%f\\\",\\\"timeStamp\\\":%lu,\\\"pod\\\":\\\"%s\\\",\\\"type\\\":\\\"Float\\\"}\",vf,ts,\"");
        _builder.append(this.connectedPortName, "\t\t");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("lwsl_user(\"Sending data...\\n\");");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if( client_wsi != NULL ) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("m = lws_write(client_wsi, msg + LWS_PRE, n, LWS_WRITE_TEXT);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lws_callback_on_writable(client_wsi);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("writeable = 0;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printf( \"writeable:0\\n\");");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} else");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("m = 0;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (m < n)");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("lwsl_err(\"sending message failed: %d\\n\", m);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Update ports indexes");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("index_I += 1;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Initializes");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    _builder.append(this.portName);
    _builder.append("_initialize(schedinfo_t *si) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("struct lws_context_creation_info info;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("const char *p;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int n = 0, logs = LLL_USER | LLL_ERR | LLL_WARN | LLL_NOTICE");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* for LLL_ verbosity above NOTICE to be built into lws,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("* lws must have been configured and built with");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("* -DCMAKE_BUILD_TYPE=DEBUG instead of =RELEASE */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* | LLL_INFO */ /* | LLL_PARSER */ /* | LLL_HEADER */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* | LLL_EXT */ /* | LLL_CLIENT */ /* | LLL_LATENCY */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* | LLL_DEBUG */;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("memset(&info, 0, sizeof info); /* otherwise uninitialized garbage */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/*\tinfo.options = LWS_SERVER_OPTION_DO_SSL_GLOBAL_INIT; */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("info.port = CONTEXT_PORT_NO_LISTEN; /* we do not run any server */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("info.protocols = protocols;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("context = lws_create_context(&info);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (!context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printf(\"lws init failed\\n\");");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} else");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printf( \"");
    _builder.append(this.portName, "\t\t");
    _builder.append(" initialized\\n\" );");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Action scheduler");
    _builder.newLine();
    _builder.append("void ");
    _builder.append(this.portName);
    _builder.append("_scheduler(schedinfo_t *si) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("int i = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("si->ports = 0;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("read_I();");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("while (1) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("lws_service(context, 0);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if ( ( numTokens_I - index_I >= 1 ) && isSchedulable_p()) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("p();");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("printf( \"");
    _builder.append(this.portName, "\t\t\t");
    _builder.append("_scheduler after p()\\n\" );");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("i++;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("si->num_firings = i;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("si->reason = starved;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("finished:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("read_end_I();");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private Object setDebug() {
    return null;
  }
  
  private String getNativePortType() {
    String _xblockexpression = null;
    {
      Type _type = this.port.getType();
      String _plus = ((("Output port: " + this.port) + "; type: ") + _type);
      OrccLogger.warnln(_plus);
      this.nativePortType = "i32";
      String _xifexpression = null;
      boolean _isString = this.port.getType().isString();
      if (_isString) {
        _xifexpression = this.nativePortType = "string";
      } else {
        String _xifexpression_1 = null;
        boolean _isInt = this.port.getType().isInt();
        if (_isInt) {
          _xifexpression_1 = this.nativePortType = "i32";
        } else {
          String _xifexpression_2 = null;
          boolean _isUint = this.port.getType().isUint();
          if (_isUint) {
            _xifexpression_2 = this.nativePortType = "u32";
          } else {
            String _xifexpression_3 = null;
            boolean _isFloat = this.port.getType().isFloat();
            if (_isFloat) {
              _xifexpression_3 = this.nativePortType = "float";
            }
            _xifexpression_2 = _xifexpression_3;
          }
          _xifexpression_1 = _xifexpression_2;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private String getJavaPortType() {
    String _xblockexpression = null;
    {
      this.javaPortType = "Integer";
      String _xifexpression = null;
      boolean _isString = this.port.getType().isString();
      if (_isString) {
        _xifexpression = this.javaPortType = "String";
      } else {
        String _xifexpression_1 = null;
        boolean _isFloat = this.port.getType().isFloat();
        if (_isFloat) {
          _xifexpression_1 = this.javaPortType = "Float";
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
