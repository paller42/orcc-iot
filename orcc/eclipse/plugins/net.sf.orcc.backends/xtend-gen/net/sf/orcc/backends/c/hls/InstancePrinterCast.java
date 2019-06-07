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
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.c.hls;

import net.sf.orcc.backends.c.InstancePrinter;
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Type;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Add instances for each port in case of actor debug
 * 
 * @author Antoine Lorence, Khaled Jerbi and Mariem Abid
 */
@SuppressWarnings("all")
public class InstancePrinterCast extends InstancePrinter {
  private FPGA fpga = FPGA.builder("Virtex7 (xc7v2000t)");
  
  public CharSequence getFileContentWrite(final Connection conn) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <hls_stream.h>");
    _builder.newLine();
    _builder.append("using namespace hls;");
    _builder.newLine();
    _builder.append("#include <stdio.h>");
    _builder.newLine();
    _builder.append("#include <stdlib.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("typedef signed char i8;");
    _builder.newLine();
    _builder.append("typedef short i16;");
    _builder.newLine();
    _builder.append("typedef int i32;");
    _builder.newLine();
    _builder.append("typedef long long int i64;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("typedef unsigned char u8;");
    _builder.newLine();
    _builder.append("typedef unsigned short u16;");
    _builder.newLine();
    _builder.append("typedef unsigned int u32;");
    _builder.newLine();
    _builder.append("typedef unsigned long long int u64;");
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    {
      boolean _isBool = this.fifoType(conn).isBool();
      if (_isBool) {
        _builder.append("// Input FIFOs");
        _builder.newLine();
        _builder.append("extern stream<");
        Type _fifoType = this.fifoType(conn);
        _builder.append(_fifoType);
        _builder.append("> ");
        CharSequence _castfifoNameWrite = this.castfifoNameWrite(conn);
        _builder.append(_castfifoNameWrite);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("// Output FIFOS");
        _builder.newLine();
        _builder.append("extern ");
        Type _fifoType_1 = this.fifoType(conn);
        _builder.append(_fifoType_1);
        _builder.append("\t");
        CharSequence _ramName = this.ramName(conn);
        _builder.append(_ramName);
        _builder.append("[");
        int _safeSize = this.safeSize(conn);
        _builder.append(_safeSize);
        _builder.append("];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern unsigned int\t");
        CharSequence _wName = this.wName(conn);
        _builder.append(_wName);
        _builder.append("[1];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern unsigned int\t");
        CharSequence _rName = this.rName(conn);
        _builder.append(_rName);
        _builder.append("[1];");
        _builder.newLineIfNotEmpty();
        _builder.append("unsigned int ");
        CharSequence _localwName = this.localwName(conn);
        _builder.append(_localwName);
        _builder.append("=0;\t\t\t\t\t");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("// Input FIFOs");
        _builder.newLine();
        _builder.append("extern stream<");
        CharSequence _doSwitch = this.doSwitch(this.fifoType(conn));
        _builder.append(_doSwitch);
        _builder.append("> ");
        CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(conn);
        _builder.append(_castfifoNameWrite_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("// Output FIFOS");
        _builder.newLine();
        _builder.append("extern ");
        CharSequence _doSwitch_1 = this.doSwitch(this.fifoType(conn));
        _builder.append(_doSwitch_1);
        _builder.append("\t");
        CharSequence _ramName_1 = this.ramName(conn);
        _builder.append(_ramName_1);
        _builder.append("[");
        int _safeSize_1 = this.safeSize(conn);
        _builder.append(_safeSize_1);
        _builder.append("];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern unsigned int\t");
        CharSequence _wName_1 = this.wName(conn);
        _builder.append(_wName_1);
        _builder.append("[1];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern unsigned int\t");
        CharSequence _rName_1 = this.rName(conn);
        _builder.append(_rName_1);
        _builder.append("[1];");
        _builder.newLineIfNotEmpty();
        _builder.append("unsigned int ");
        CharSequence _localwName_1 = this.localwName(conn);
        _builder.append(_localwName_1);
        _builder.append("=0;\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Actions");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static void cast_");
    _builder.append(this.entityName);
    _builder.append("_");
    String _name = conn.getTargetPort().getName();
    _builder.append(_name);
    _builder.append("_write_untagged_0() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("i32 ");
    CharSequence _maskName = this.maskName(conn);
    _builder.append(_maskName, "\t");
    _builder.append(" = ");
    CharSequence _localwName_2 = this.localwName(conn);
    _builder.append(_localwName_2, "\t");
    _builder.append(" & (");
    int _safeSize_2 = this.safeSize(conn);
    int _minus = (_safeSize_2 - 1);
    _builder.append(_minus, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    {
      boolean _isBool_1 = this.fifoType(conn).isBool();
      if (_isBool_1) {
        _builder.append("\t");
        Type _fifoType_2 = this.fifoType(conn);
        _builder.append(_fifoType_2, "\t");
        _builder.append(" tmp_");
        String _name_1 = conn.getTargetPort().getName();
        _builder.append(_name_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        CharSequence _doSwitch_2 = this.doSwitch(this.fifoType(conn));
        _builder.append(_doSwitch_2, "\t");
        _builder.append(" tmp_");
        String _name_2 = conn.getTargetPort().getName();
        _builder.append(_name_2, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(conn);
    _builder.append(_castfifoNameWrite_2, "\t");
    _builder.append(".read_nb(tmp_");
    String _name_3 = conn.getTargetPort().getName();
    _builder.append(_name_3, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _ramName_2 = this.ramName(conn);
    _builder.append(_ramName_2, "\t");
    _builder.append("[");
    CharSequence _maskName_1 = this.maskName(conn);
    _builder.append(_maskName_1, "\t");
    _builder.append("]=tmp_");
    String _name_4 = conn.getTargetPort().getName();
    _builder.append(_name_4, "\t");
    _builder.append(" ;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _localwName_3 = this.localwName(conn);
    _builder.append(_localwName_3, "\t");
    _builder.append(" = ");
    CharSequence _localwName_4 = this.localwName(conn);
    _builder.append(_localwName_4, "\t");
    _builder.append(" +1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _wName_2 = this.wName(conn);
    _builder.append(_wName_2, "\t");
    _builder.append("[0] = ");
    CharSequence _localwName_5 = this.localwName(conn);
    _builder.append(_localwName_5, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static bool isSchedulable_untagged_0() {");
    _builder.newLine();
    _builder.append("bool result;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("result = 1;");
    _builder.newLine();
    _builder.append("return result;");
    _builder.newLine();
    _builder.append("}\t\t");
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Action scheduler");
    _builder.newLine();
    _builder.append("void cast_");
    _builder.append(this.entityName);
    _builder.append("_");
    String _name_5 = conn.getTargetPort().getName();
    _builder.append(_name_5);
    _builder.append("_write_scheduler() {\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("if (!");
    CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(conn);
    _builder.append(_castfifoNameWrite_3, "\t");
    _builder.append(".empty() && isSchedulable_untagged_0()) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("if(1 && (");
    int _safeSize_3 = this.safeSize(conn);
    _builder.append(_safeSize_3, "\t\t");
    _builder.append(" - ");
    CharSequence _localwName_6 = this.localwName(conn);
    _builder.append(_localwName_6, "\t\t");
    _builder.append(" + ");
    CharSequence _rName_2 = this.rName(conn);
    _builder.append(_rName_2, "\t\t");
    _builder.append("[0] >= 1)) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("cast_");
    _builder.append(this.entityName, "\t\t\t");
    _builder.append("_");
    String _name_6 = conn.getTargetPort().getName();
    _builder.append(_name_6, "\t\t\t");
    _builder.append("_write_untagged_0();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("finished:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getFileContentReadDebug(final String ActionName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <hls_stream.h>");
    _builder.newLine();
    _builder.append("using namespace hls;");
    _builder.newLine();
    _builder.append("#include <stdio.h>");
    _builder.newLine();
    _builder.append("#include <stdlib.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("typedef signed char i8;");
    _builder.newLine();
    _builder.append("typedef short i16;");
    _builder.newLine();
    _builder.append("typedef int i32;");
    _builder.newLine();
    _builder.append("typedef long long int i64;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("typedef unsigned char u8;");
    _builder.newLine();
    _builder.append("typedef unsigned short u16;");
    _builder.newLine();
    _builder.append("typedef unsigned int u32;");
    _builder.newLine();
    _builder.append("typedef unsigned long long int u64;");
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Input FIFOS");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("extern u8 tab_");
    _builder.append(ActionName, "\t");
    _builder.append("[16384];");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("extern unsigned int writeIdx_");
    _builder.append(ActionName, "\t");
    _builder.append("[1];");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("extern unsigned int readIdx_");
    _builder.append(ActionName, "\t");
    _builder.append("[1];");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("unsigned int rIdx_");
    _builder.append(ActionName, "\t");
    _builder.append("=0;\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("// Output FIFOs");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("extern stream<u8> myStream_cast_tab_");
    _builder.append(ActionName, "\t");
    _builder.append("_read;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Actions");
    _builder.newLine();
    _builder.append("static void cast_");
    _builder.append(this.entityName);
    _builder.append("_tab_");
    _builder.append(ActionName);
    _builder.append("_read_untagged_0() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("i32 mask_");
    _builder.append(ActionName, "\t");
    _builder.append(" = rIdx_");
    _builder.append(ActionName, "\t");
    _builder.append(" & (16383 );");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("u8 tmp_tab_");
    _builder.append(ActionName, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("tmp_tab_");
    _builder.append(ActionName, "\t");
    _builder.append(" = tab_");
    _builder.append(ActionName, "\t");
    _builder.append("[mask_");
    _builder.append(ActionName, "\t");
    _builder.append("];");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("myStream_cast_tab_");
    _builder.append(ActionName, "\t");
    _builder.append("_read.write_nb(tmp_tab_");
    _builder.append(ActionName, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("rIdx_");
    _builder.append(ActionName, "\t");
    _builder.append(" = rIdx_");
    _builder.append(ActionName, "\t");
    _builder.append(" +1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("readIdx_");
    _builder.append(ActionName, "\t");
    _builder.append("[0] = rIdx_");
    _builder.append(ActionName, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static bool isSchedulable_untagged_0() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("bool result;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("result = 1;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return result;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Action scheduler");
    _builder.newLine();
    _builder.append("void cast_");
    _builder.append(this.entityName);
    _builder.append("_tab_");
    _builder.append(ActionName);
    _builder.append("_read_scheduler() {\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (writeIdx_");
    _builder.append(ActionName, "\t");
    _builder.append("[0] - rIdx_");
    _builder.append(ActionName, "\t");
    _builder.append(" >= 1  && isSchedulable_untagged_0()) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("if(1 && (!myStream_cast_tab_");
    _builder.append(ActionName, "\t\t");
    _builder.append("_read.full())) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("cast_");
    _builder.append(this.entityName, "\t\t\t");
    _builder.append("_tab_");
    _builder.append(ActionName, "\t\t\t");
    _builder.append("_read_untagged_0();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("finished:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getFileContentRead(final Connection connOut) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("#include <hls_stream.h>");
    _builder.newLine();
    _builder.append("using namespace hls;");
    _builder.newLine();
    _builder.append("#include <stdio.h>");
    _builder.newLine();
    _builder.append("#include <stdlib.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("typedef signed char i8;");
    _builder.newLine();
    _builder.append("typedef short i16;");
    _builder.newLine();
    _builder.append("typedef int i32;");
    _builder.newLine();
    _builder.append("typedef long long int i64;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("typedef unsigned char u8;");
    _builder.newLine();
    _builder.append("typedef unsigned short u16;");
    _builder.newLine();
    _builder.append("typedef unsigned int u32;");
    _builder.newLine();
    _builder.append("typedef unsigned long long int u64;");
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.newLine();
    {
      boolean _isBool = this.fifoType(connOut).isBool();
      if (_isBool) {
        _builder.append("// Input FIFOS");
        _builder.newLine();
        _builder.append("extern ");
        Type _fifoType = this.fifoType(connOut);
        _builder.append(_fifoType);
        _builder.append(" ");
        CharSequence _ramName = this.ramName(connOut);
        _builder.append(_ramName);
        _builder.append("[");
        int _safeSize = this.safeSize(connOut);
        _builder.append(_safeSize);
        _builder.append("];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern unsigned int ");
        CharSequence _wName = this.wName(connOut);
        _builder.append(_wName);
        _builder.append("[1];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern unsigned int ");
        CharSequence _rName = this.rName(connOut);
        _builder.append(_rName);
        _builder.append("[1];");
        _builder.newLineIfNotEmpty();
        _builder.append("unsigned int ");
        CharSequence _localrName = this.localrName(connOut);
        _builder.append(_localrName);
        _builder.append("=0;");
        _builder.newLineIfNotEmpty();
        _builder.append("// Output FIFOs");
        _builder.newLine();
        _builder.append("extern stream<");
        Type _fifoType_1 = this.fifoType(connOut);
        _builder.append(_fifoType_1);
        _builder.append("> ");
        CharSequence _castfifoNameRead = this.castfifoNameRead(connOut);
        _builder.append(_castfifoNameRead);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("// Input FIFOS");
        _builder.newLine();
        _builder.append("extern ");
        CharSequence _doSwitch = this.doSwitch(this.fifoType(connOut));
        _builder.append(_doSwitch);
        _builder.append(" ");
        CharSequence _ramName_1 = this.ramName(connOut);
        _builder.append(_ramName_1);
        _builder.append("[");
        int _safeSize_1 = this.safeSize(connOut);
        _builder.append(_safeSize_1);
        _builder.append("];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern unsigned int ");
        CharSequence _wName_1 = this.wName(connOut);
        _builder.append(_wName_1);
        _builder.append("[1];");
        _builder.newLineIfNotEmpty();
        _builder.append("extern unsigned int ");
        CharSequence _rName_1 = this.rName(connOut);
        _builder.append(_rName_1);
        _builder.append("[1];");
        _builder.newLineIfNotEmpty();
        _builder.append("unsigned int ");
        CharSequence _localrName_1 = this.localrName(connOut);
        _builder.append(_localrName_1);
        _builder.append("=0;");
        _builder.newLineIfNotEmpty();
        _builder.append("// Output FIFOs");
        _builder.newLine();
        _builder.append("extern stream<");
        CharSequence _doSwitch_1 = this.doSwitch(this.fifoType(connOut));
        _builder.append(_doSwitch_1);
        _builder.append("> ");
        CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connOut);
        _builder.append(_castfifoNameRead_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Actions");
    _builder.newLine();
    _builder.append("static void cast_");
    _builder.append(this.entityName);
    _builder.append("_");
    String _name = connOut.getSourcePort().getName();
    _builder.append(_name);
    _builder.append("_read_untagged_0() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("i32 ");
    CharSequence _maskName = this.maskName(connOut);
    _builder.append(_maskName, "\t");
    _builder.append(" = ");
    CharSequence _localrName_2 = this.localrName(connOut);
    _builder.append(_localrName_2, "\t");
    _builder.append(" & (");
    int _safeSize_2 = this.safeSize(connOut);
    int _minus = (_safeSize_2 - 1);
    _builder.append(_minus, "\t");
    _builder.append(" );");
    _builder.newLineIfNotEmpty();
    {
      boolean _isBool_1 = this.fifoType(connOut).isBool();
      if (_isBool_1) {
        {
          Port _targetPort = connOut.getTargetPort();
          boolean _tripleNotEquals = (_targetPort != null);
          if (_tripleNotEquals) {
            _builder.append("\t");
            Type _fifoType_2 = this.fifoType(connOut);
            _builder.append(_fifoType_2, "\t");
            _builder.append(" tmp_");
            String _name_1 = connOut.getTargetPort().getName();
            _builder.append(_name_1, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t");
            Type _fifoType_3 = this.fifoType(connOut);
            _builder.append(_fifoType_3, "\t");
            _builder.append(" tmp_");
            String _name_2 = connOut.getSourcePort().getName();
            _builder.append(_name_2, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      } else {
        _builder.append("\t");
        CharSequence _doSwitch_2 = this.doSwitch(this.fifoType(connOut));
        _builder.append(_doSwitch_2, "\t");
        _builder.append(" tmp_");
        String _name_3 = connOut.getSourcePort().getName();
        _builder.append(_name_3, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("tmp_");
    String _name_4 = connOut.getSourcePort().getName();
    _builder.append(_name_4, "\t");
    _builder.append(" = ");
    CharSequence _ramName_2 = this.ramName(connOut);
    _builder.append(_ramName_2, "\t");
    _builder.append("[");
    CharSequence _maskName_1 = this.maskName(connOut);
    _builder.append(_maskName_1, "\t");
    _builder.append("];");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connOut);
    _builder.append(_castfifoNameRead_2, "\t");
    _builder.append(".write_nb(tmp_");
    String _name_5 = connOut.getSourcePort().getName();
    _builder.append(_name_5, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _localrName_3 = this.localrName(connOut);
    _builder.append(_localrName_3, "\t");
    _builder.append(" = ");
    CharSequence _localrName_4 = this.localrName(connOut);
    _builder.append(_localrName_4, "\t");
    _builder.append(" +1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _rName_2 = this.rName(connOut);
    _builder.append(_rName_2, "\t");
    _builder.append("[0] = ");
    CharSequence _localrName_5 = this.localrName(connOut);
    _builder.append(_localrName_5, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static bool isSchedulable_untagged_0() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("bool result;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("result = 1;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return result;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Action scheduler");
    _builder.newLine();
    _builder.append("void cast_");
    _builder.append(this.entityName);
    _builder.append("_");
    String _name_6 = connOut.getSourcePort().getName();
    _builder.append(_name_6);
    _builder.append("_read_scheduler() {\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("if (");
    CharSequence _wName_2 = this.wName(connOut);
    _builder.append(_wName_2, "\t");
    _builder.append("[0] - ");
    CharSequence _localrName_6 = this.localrName(connOut);
    _builder.append(_localrName_6, "\t");
    _builder.append(" >= 1  && isSchedulable_untagged_0()) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("if(1 && (!");
    CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connOut);
    _builder.append(_castfifoNameRead_3, "\t\t");
    _builder.append(".full())) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("cast_");
    _builder.append(this.entityName, "\t\t\t");
    _builder.append("_");
    String _name_7 = connOut.getSourcePort().getName();
    _builder.append(_name_7, "\t\t\t");
    _builder.append("_read_untagged_0();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("finished:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence castfifoNameWrite(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection != null)) {
        _builder.append("myStream_cast_");
        Object _objectValue = connection.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
        _builder.append("_write");
      }
    }
    return _builder;
  }
  
  public CharSequence castfifoNameRead(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection != null)) {
        _builder.append("myStream_cast_");
        Object _objectValue = connection.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
        _builder.append("_read");
      }
    }
    return _builder;
  }
  
  public CharSequence ramName(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection != null)) {
        _builder.append("tab_");
        Object _objectValue = connection.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
      }
    }
    return _builder;
  }
  
  public CharSequence wName(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection != null)) {
        _builder.append("writeIdx_");
        Object _objectValue = connection.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
      }
    }
    return _builder;
  }
  
  public CharSequence localwName(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection != null)) {
        _builder.append("wIdx_");
        Object _objectValue = connection.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
      }
    }
    return _builder;
  }
  
  public CharSequence localrName(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection != null)) {
        _builder.append("rIdx_");
        Object _objectValue = connection.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
      }
    }
    return _builder;
  }
  
  public CharSequence rName(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection != null)) {
        _builder.append("readIdx_");
        Object _objectValue = connection.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
      }
    }
    return _builder;
  }
  
  public CharSequence maskName(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection != null)) {
        _builder.append("mask_");
        Object _objectValue = connection.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
      }
    }
    return _builder;
  }
  
  public Type fifoType(final Connection connection) {
    Type _xifexpression = null;
    Port _sourcePort = connection.getSourcePort();
    boolean _tripleNotEquals = (_sourcePort != null);
    if (_tripleNotEquals) {
      _xifexpression = connection.getSourcePort().getType();
    } else {
      _xifexpression = connection.getTargetPort().getType();
    }
    return _xifexpression;
  }
  
  public CharSequence script(final String path, final String Instname) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("open_project -reset subProject_");
    _builder.append(Instname);
    _builder.newLineIfNotEmpty();
    _builder.append("set_top ");
    _builder.append(Instname);
    _builder.append("_scheduler");
    _builder.newLineIfNotEmpty();
    _builder.append("add_files ");
    _builder.append(Instname);
    _builder.append(".cpp");
    _builder.newLineIfNotEmpty();
    _builder.append("add_files -tb ");
    _builder.append(Instname);
    _builder.append("TestBench.cpp");
    _builder.newLineIfNotEmpty();
    _builder.append("open_solution -reset \"solution1\"");
    _builder.newLine();
    _builder.append("set_part  {");
    String _device = this.fpga.getDevice();
    _builder.append(_device);
    String _package = this.fpga.getPackage();
    _builder.append(_package);
    String _version = this.fpga.getVersion();
    _builder.append(_version);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("create_clock -period 20");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("csynth_design");
    _builder.newLine();
    _builder.append("exit");
    _builder.newLine();
    return _builder;
  }
}
