/**
 * Copyright (c) 2012, IETR/INSA of Rennes
 * Copyright (c) 2014, Heriot-Watt University
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
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Port;
import net.sf.orcc.util.OrccAttributes;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Bach command for each actor
 * 
 * @author Rob Stewart and Khaled Jerbi and Mariem Abid
 */
@SuppressWarnings("all")
public class UnitaryBatchCommandPrinterLinux extends InstancePrinter {
  public CharSequence getFileContentBatch() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Two additions to your ~/.bash_profile or ~/.profile must be made,");
    _builder.newLine();
    _builder.append("# to modify the $XILINXD_LICENSE_FILE and $PATH environment variables");
    _builder.newLine();
    _builder.append("# ");
    _builder.newLine();
    _builder.append("# Step 1. The $XILINXD_LICENSE_FILE environment variable must be set, e.g.");
    _builder.newLine();
    _builder.append("# export XILINXD_LICENSE_FILE=\"<path>/Xilinx.lic\"");
    _builder.newLine();
    _builder.append("#");
    _builder.newLine();
    _builder.append("# Step 2. The $PATH environment variable must include the Vivado HLS bin/ e.g.");
    _builder.newLine();
    _builder.append("# export PATH=~/path/to/vivado-hls/bin:$PATH");
    _builder.newLine();
    _builder.newLine();
    _builder.append("cd ..");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("vivado_hls -f script_");
    _builder.append(this.entityName);
    _builder.append(".tcl");
    _builder.newLineIfNotEmpty();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        {
          if ((connection != null)) {
            _builder.append("vivado_hls -f script_cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name = connection.getTargetPort().getName();
            _builder.append(_name);
            _builder.append("_write.tcl");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
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
            _builder.append("vivado_hls -f script_cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name_1 = connection_1.getSourcePort().getName();
            _builder.append(_name_1);
            _builder.append("_read.tcl\t\t\t\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _hasAttribute = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute) {
        {
          EList<Action> _actions = this.actor.getActions();
          for(final Action action : _actions) {
            _builder.append("vivado_hls -f script_cast_");
            _builder.append(this.entityName);
            _builder.append("_tab_");
            String _name_2 = action.getName();
            _builder.append(_name_2);
            _builder.append("_read.tcl");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("cp subProject_");
    _builder.append(this.entityName);
    _builder.append("/solution1/syn/vhdl/* ");
    _builder.append(this.entityName);
    _builder.append("TopVHDL/");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Port> _inputs_1 = this.actor.getInputs();
      for(final Port port_2 : _inputs_1) {
        final Connection connection_2 = this.incomingPortMap.get(port_2);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_2 != null)) {
            _builder.append("cp subProject_cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name_3 = connection_2.getTargetPort().getName();
            _builder.append(_name_3);
            _builder.append("_write/solution1/syn/vhdl/* ");
            _builder.append(this.entityName);
            _builder.append("TopVHDL/");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      final Function1<Port, Boolean> _function_1 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter_1 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_1);
      for(final Port port_3 : _filter_1) {
        {
          List<Connection> _get_1 = this.outgoingPortMap.get(port_3);
          for(final Connection connection_3 : _get_1) {
            _builder.append("cp subProject_cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name_4 = connection_3.getSourcePort().getName();
            _builder.append(_name_4);
            _builder.append("_read/solution1/syn/vhdl/* ");
            _builder.append(this.entityName);
            _builder.append("TopVHDL/");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _hasAttribute_1 = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute_1) {
        {
          EList<Action> _actions_1 = this.actor.getActions();
          for(final Action action_1 : _actions_1) {
            _builder.append("cp subProject_cast_");
            _builder.append(this.entityName);
            _builder.append("_tab_");
            String _name_5 = action_1.getName();
            _builder.append(_name_5);
            _builder.append("_read/solution1/syn/vhdl/* ");
            _builder.append(this.entityName);
            _builder.append("TopVHDL/");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
}
