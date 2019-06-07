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

import com.google.common.collect.Iterables;
import java.util.List;
import net.sf.orcc.backends.c.NetworkPrinter;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Batch Command for the network
 * 
 * @author Khaled Jerbi and Mariem Abid
 */
@SuppressWarnings("all")
public class BatchCommandPrinter extends NetworkPrinter {
  @Override
  public CharSequence getNetworkFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(":: The path variable must be set system wide to include vivado_hls and msys binaries, e.g.");
    _builder.newLine();
    _builder.append(":: PATH=D:\\Users\\JoeBloggs\\2013.4\\Xilinx\\Vivado_HLS\\2013.4\\bin;%PATH%;D:\\Users\\JoeBloggs\\2013.4\\Xilinx\\Vivado_HLS\\2013.4\\msys\\bin");
    _builder.newLine();
    _builder.append("::");
    _builder.newLine();
    _builder.append(":: Two environment variables must be set system wide to include vivado_hls , e.g.");
    _builder.newLine();
    _builder.append(":: set AUTOESL_HOME=D:\\Users\\JoeBloggs\\2013.4\\Xilinx\\Vivado_HLS\\2013.4\\bin");
    _builder.newLine();
    _builder.append(":: set VIVADO_HLS_HOME=D:\\Users\\JoeBloggs\\2013.4\\Xilinx\\Vivado_HLS\\2013.4\\bin");
    _builder.newLine();
    _builder.newLine();
    _builder.append("if not \"x%PROCESSOR_ARCHITECTURE%\" == \"xAMD64\" goto _NotX64");
    _builder.newLine();
    _builder.append("set COMSPEC=%WINDIR%\\SysWOW64\\cmd.exe");
    _builder.newLine();
    _builder.append("goto START");
    _builder.newLine();
    _builder.append(":_NotX64");
    _builder.newLine();
    _builder.append("set COMSPEC=%WINDIR%\\System32\\cmd.exe");
    _builder.newLine();
    _builder.append(":START");
    _builder.newLine();
    _builder.append("cd ..");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          return Boolean.valueOf(it.isActor());
        }
      };
      Iterable<Instance> _filter = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function);
      for(final Instance instance : _filter) {
        _builder.newLine();
        _builder.append("%COMSPEC% /C vivado_hls -f script_");
        String _name = instance.getName();
        _builder.append(_name);
        _builder.append(".tcl");
        _builder.newLineIfNotEmpty();
        {
          EList<Port> _inputs = instance.getActor().getInputs();
          for(final Port port : _inputs) {
            final Connection connection = instance.getIncomingPortMap().get(port);
            _builder.newLineIfNotEmpty();
            {
              if (((connection != null) && (connection.getSourcePort() == null))) {
                _builder.append("%COMSPEC% /C vivado_hls -f script_cast_");
                String _name_1 = instance.getName();
                _builder.append(_name_1);
                _builder.append("_");
                String _name_2 = connection.getTargetPort().getName();
                _builder.append(_name_2);
                _builder.append("_write.tcl");
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
          Iterable<Port> _filter_1 = IterableExtensions.<Port>filter(instance.getActor().getOutputs(), _function_1);
          for(final Port port_1 : _filter_1) {
            {
              List<Connection> _get = instance.getOutgoingPortMap().get(port_1);
              for(final Connection connection_1 : _get) {
                {
                  Port _targetPort = connection_1.getTargetPort();
                  boolean _tripleEquals = (_targetPort == null);
                  if (_tripleEquals) {
                    _builder.append("%COMSPEC% /C vivado_hls -f script_cast_");
                    String _name_3 = instance.getName();
                    _builder.append(_name_3);
                    _builder.append("_");
                    String _name_4 = connection_1.getSourcePort().getName();
                    _builder.append(_name_4);
                    _builder.append("_read.tcl\t\t\t\t\t");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("copy %cd%\\sim_package.vhd %cd%\\TopVHDL");
    _builder.newLine();
    _builder.append("copy %cd%\\TopVHDL\\ram_tab.vhd %cd%\\TopVHDL");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function_2 = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          return Boolean.valueOf(it.isActor());
        }
      };
      Iterable<Instance> _filter_2 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_2);
      for(final Instance instance_1 : _filter_2) {
        _builder.append("copy %cd%\\subProject_");
        String _name_5 = instance_1.getName();
        _builder.append(_name_5);
        _builder.append("\\solution1\\syn\\vhdl %cd%\\TopVHDL");
        _builder.newLineIfNotEmpty();
        {
          EList<Port> _inputs_1 = instance_1.getActor().getInputs();
          for(final Port port_2 : _inputs_1) {
            final Connection connection_2 = instance_1.getIncomingPortMap().get(port_2);
            _builder.newLineIfNotEmpty();
            {
              if (((connection_2 != null) && (connection_2.getSourcePort() == null))) {
                _builder.append("copy %cd%\\subProject_cast_");
                String _name_6 = instance_1.getName();
                _builder.append(_name_6);
                _builder.append("_");
                String _name_7 = connection_2.getTargetPort().getName();
                _builder.append(_name_7);
                _builder.append("_write\\solution1\\syn\\vhdl %cd%\\TopVHDL");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        {
          final Function1<Port, Boolean> _function_3 = new Function1<Port, Boolean>() {
            @Override
            public Boolean apply(final Port it) {
              boolean _isNative = it.isNative();
              return Boolean.valueOf((!_isNative));
            }
          };
          Iterable<Port> _filter_3 = IterableExtensions.<Port>filter(instance_1.getActor().getOutputs(), _function_3);
          for(final Port port_3 : _filter_3) {
            {
              List<Connection> _get_1 = instance_1.getOutgoingPortMap().get(port_3);
              for(final Connection connection_3 : _get_1) {
                {
                  Port _targetPort_1 = connection_3.getTargetPort();
                  boolean _tripleEquals_1 = (_targetPort_1 == null);
                  if (_tripleEquals_1) {
                    _builder.append("copy %cd%\\subProject_cast_");
                    String _name_8 = instance_1.getName();
                    _builder.append(_name_8);
                    _builder.append("_");
                    String _name_9 = connection_3.getSourcePort().getName();
                    _builder.append(_name_9);
                    _builder.append("_read\\solution1\\syn\\vhdl %cd%\\TopVHDL");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
      }
    }
    return _builder;
  }
}
