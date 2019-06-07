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

import java.util.List;
import net.sf.orcc.backends.c.InstancePrinter;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * generates testbench for for vivado co-simulation
 * 
 * @author Khaled Jerbi
 */
@SuppressWarnings("all")
public class InstanceCosimPrinter extends InstancePrinter {
  @Override
  public CharSequence getFileContent() {
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
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Input FIFOS");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        {
          if ((connection != null)) {
            _builder.append("stream<");
            CharSequence _doSwitch = this.doSwitch(this.fifoType(connection));
            _builder.append(_doSwitch);
            _builder.append(">\t");
            CharSequence _fifoName = this.fifoName(connection);
            _builder.append(_fifoName);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("int counter_");
            CharSequence _fifoName_1 = this.fifoName(connection);
            _builder.append(_fifoName_1);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            CharSequence _doSwitch_1 = this.doSwitch(this.fifoType(connection));
            _builder.append(_doSwitch_1);
            _builder.append(" tab_");
            CharSequence _fifoName_2 = this.fifoName(connection);
            _builder.append(_fifoName_2);
            _builder.append("[1000];");
            _builder.newLineIfNotEmpty();
            CharSequence _doSwitch_2 = this.doSwitch(this.fifoType(connection));
            _builder.append(_doSwitch_2);
            _builder.append(" tmp_");
            CharSequence _fifoName_3 = this.fifoName(connection);
            _builder.append(_fifoName_3);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Output FIFOs");
    _builder.newLine();
    _builder.newLine();
    {
      final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function);
      for(final Port port_1 : _filter) {
        {
          List<Connection> _get = this.outgoingPortMap.get(port_1);
          for(final Connection connection_1 : _get) {
            _builder.append("stream<");
            CharSequence _doSwitch_3 = this.doSwitch(this.fifoType(connection_1));
            _builder.append(_doSwitch_3);
            _builder.append("> ");
            CharSequence _fifoName_4 = this.fifoName(connection_1);
            _builder.append(_fifoName_4);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("int counter_");
            CharSequence _fifoName_5 = this.fifoName(connection_1);
            _builder.append(_fifoName_5);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            CharSequence _doSwitch_4 = this.doSwitch(this.fifoType(connection_1));
            _builder.append(_doSwitch_4);
            _builder.append(" tab_");
            CharSequence _fifoName_6 = this.fifoName(connection_1);
            _builder.append(_fifoName_6);
            _builder.append(" [1000];");
            _builder.newLineIfNotEmpty();
            CharSequence _doSwitch_5 = this.doSwitch(this.fifoType(connection_1));
            _builder.append(_doSwitch_5);
            _builder.append(" tmp_");
            CharSequence _fifoName_7 = this.fifoName(connection_1);
            _builder.append(_fifoName_7);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// functions definition");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    _builder.append(this.entityName);
    _builder.append("_scheduler();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.newLine();
    _builder.append("int main (){");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("FILE *fp;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int i;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int retval = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// read data");
    _builder.newLine();
    {
      EList<Port> _inputs_1 = this.actor.getInputs();
      for(final Port port_2 : _inputs_1) {
        _builder.append("\t");
        final Connection connection_2 = this.incomingPortMap.get(port_2);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_2 != null)) {
            _builder.append("\t");
            _builder.append("fp=fopen(\"");
            _builder.append(this.entityName, "\t");
            _builder.append("_");
            String _name = port_2.getName();
            _builder.append(_name, "\t");
            _builder.append(".txt\",\"r\");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("for (i=0 ; i<1000 ; i++){");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("fscanf(fp, \"%d\", &tmp_");
            CharSequence _fifoName_8 = this.fifoName(connection_2);
            _builder.append(_fifoName_8, "\t\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("tab_");
            CharSequence _fifoName_9 = this.fifoName(connection_2);
            _builder.append(_fifoName_9, "\t\t");
            _builder.append("[i]=tmp_");
            CharSequence _fifoName_10 = this.fifoName(connection_2);
            _builder.append(_fifoName_10, "\t\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("fclose(fp);");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// scheduler execution");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<Port> _inputs_2 = this.actor.getInputs();
      for(final Port port_3 : _inputs_2) {
        _builder.append("\t\t");
        final Connection connection_3 = this.incomingPortMap.get(port_3);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_3 != null)) {
            _builder.append("\t\t");
            _builder.append("for (i=0 ; i<1000 ; i++){");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("if(!");
            CharSequence _fifoName_11 = this.fifoName(connection_3);
            _builder.append(_fifoName_11, "\t\t\t");
            _builder.append(".full()){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            CharSequence _fifoName_12 = this.fifoName(connection_3);
            _builder.append(_fifoName_12, "\t\t\t\t");
            _builder.append(".write(tab_");
            CharSequence _fifoName_13 = this.fifoName(connection_3);
            _builder.append(_fifoName_13, "\t\t\t\t");
            _builder.append("[counter_");
            CharSequence _fifoName_14 = this.fifoName(connection_3);
            _builder.append(_fifoName_14, "\t\t\t\t");
            _builder.append("]);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            _builder.append("counter_");
            CharSequence _fifoName_15 = this.fifoName(connection_3);
            _builder.append(_fifoName_15, "\t\t\t\t");
            _builder.append(" ++;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("for (i=0 ; i<1000 ; i++){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(this.entityName, "\t\t");
    _builder.append("_scheduler();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    {
      final Function1<Port, Boolean> _function_1 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter_1 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_1);
      for(final Port port_4 : _filter_1) {
        {
          List<Connection> _get_1 = this.outgoingPortMap.get(port_4);
          for(final Connection connection_4 : _get_1) {
            _builder.append("\t\t");
            _builder.append("if(!");
            CharSequence _fifoName_16 = this.fifoName(connection_4);
            _builder.append(_fifoName_16, "\t\t");
            _builder.append(".empty()){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            CharSequence _fifoName_17 = this.fifoName(connection_4);
            _builder.append(_fifoName_17, "\t\t\t");
            _builder.append(".read(tab_");
            CharSequence _fifoName_18 = this.fifoName(connection_4);
            _builder.append(_fifoName_18, "\t\t\t");
            _builder.append("[counter_");
            CharSequence _fifoName_19 = this.fifoName(connection_4);
            _builder.append(_fifoName_19, "\t\t\t");
            _builder.append("]);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("counter_");
            CharSequence _fifoName_20 = this.fifoName(connection_4);
            _builder.append(_fifoName_20, "\t\t\t");
            _builder.append(" ++;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// write results\t");
    _builder.newLine();
    {
      final Function1<Port, Boolean> _function_2 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter_2 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_2);
      for(final Port port_5 : _filter_2) {
        {
          List<Connection> _get_2 = this.outgoingPortMap.get(port_5);
          for(final Connection connection_5 : _get_2) {
            _builder.append("\t");
            _builder.append("fp=fopen(\"gold_");
            _builder.append(this.entityName, "\t");
            _builder.append("_");
            String _name_1 = port_5.getName();
            _builder.append(_name_1, "\t");
            _builder.append(".txt\",\"w\");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("for (i=0 ; i<1000 ; i++){");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("tmp_");
            CharSequence _fifoName_21 = this.fifoName(connection_5);
            _builder.append(_fifoName_21, "\t\t");
            _builder.append("=tab_");
            CharSequence _fifoName_22 = this.fifoName(connection_5);
            _builder.append(_fifoName_22, "\t\t");
            _builder.append("[i];");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("fprintf(fp, \"%d \\n\", tmp_");
            CharSequence _fifoName_23 = this.fifoName(connection_5);
            _builder.append(_fifoName_23, "\t\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("fclose(fp);");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// comparison with reference (gold) files");
    _builder.newLine();
    {
      final Function1<Port, Boolean> _function_3 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter_3 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_3);
      for(final Port port_6 : _filter_3) {
        {
          List<Connection> _get_3 = this.outgoingPortMap.get(port_6);
          for(final Connection connection_6 : _get_3) {
            _builder.append("\t");
            _builder.append("retval += system(\"diff --brief -w ");
            _builder.append(this.entityName, "\t");
            _builder.append("_");
            String _name_2 = port_6.getName();
            _builder.append(_name_2, "\t");
            _builder.append(".txt gold_");
            _builder.append(this.entityName, "\t");
            _builder.append("_");
            String _name_3 = port_6.getName();
            _builder.append(_name_3, "\t");
            _builder.append(".txt\");");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (retval != 0) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printf(\"Test failed !!!\\n\");");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("retval=1;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printf(\"Test passed !\\n\");");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Return 0 if the test is true");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return retval;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  public Type fifoType(final Connection connection) {
    Type _xifexpression = null;
    Port _sourcePort = connection.getSourcePort();
    boolean _tripleEquals = (_sourcePort == null);
    if (_tripleEquals) {
      _xifexpression = connection.getTargetPort().getType();
    } else {
      _xifexpression = connection.getSourcePort().getType();
    }
    return _xifexpression;
  }
  
  public CharSequence fifoName(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection != null)) {
        _builder.append("myStream_");
        Object _objectValue = connection.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("bool");
    return _builder;
  }
}
