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
import java.util.Collection;
import java.util.List;
import net.sf.orcc.backends.c.NetworkPrinter;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Type;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * generates top Network testbench
 * 
 * @author Khaled Jerbi and Mariem Abid
 */
@SuppressWarnings("all")
public class NetworkTestBenchPrinter extends NetworkPrinter {
  @Override
  public CharSequence getNetworkFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("LIBRARY ieee;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("USE ieee.std_logic_1164.ALL;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("USE ieee.std_logic_unsigned.all;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("USE ieee.numeric_std.ALL;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("USE std.textio.all;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("LIBRARY work;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("USE work.sim_package.all;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ENTITY testbench IS");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("END testbench;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ARCHITECTURE behavior OF testbench IS");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- Component Declaration");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("COMPONENT TopDesign");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("PORT(");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_clk : IN STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_rst : IN STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_start : IN STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_done : OUT STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_idle : OUT STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_ready : OUT STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
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
        {
          EList<Port> _inputs = instance.getActor().getInputs();
          for(final Port port : _inputs) {
            _builder.append("\t");
            final Connection connection = instance.getIncomingPortMap().get(port);
            _builder.newLineIfNotEmpty();
            {
              if ((connection != null)) {
                {
                  Port _sourcePort = connection.getSourcePort();
                  boolean _tripleEquals = (_sourcePort == null);
                  if (_tripleEquals) {
                    _builder.append("\t");
                    CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection);
                    _builder.append(_castfifoNameWrite, "\t");
                    _builder.append("_V_dout   : IN STD_LOGIC_VECTOR (");
                    int _sizeInBits = this.fifoTypeOut(connection).getSizeInBits();
                    int _minus = (_sizeInBits - 1);
                    _builder.append(_minus, "\t");
                    _builder.append(" downto 0); ");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(connection);
                    _builder.append(_castfifoNameWrite_1, "\t");
                    _builder.append("_V_empty_n : IN STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(connection);
                    _builder.append(_castfifoNameWrite_2, "\t");
                    _builder.append("_V_read    : OUT STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                  }
                }
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
          for(final Port portout : _filter_1) {
            {
              List<Connection> _get = instance.getOutgoingPortMap().get(portout);
              for(final Connection connection_1 : _get) {
                {
                  Port _targetPort = connection_1.getTargetPort();
                  boolean _tripleEquals_1 = (_targetPort == null);
                  if (_tripleEquals_1) {
                    _builder.append("\t");
                    CharSequence _castfifoNameRead = this.castfifoNameRead(connection_1);
                    _builder.append(_castfifoNameRead, "\t");
                    _builder.append("_V_din    : OUT STD_LOGIC_VECTOR (");
                    int _sizeInBits_1 = this.fifoTypeOut(connection_1).getSizeInBits();
                    int _minus_1 = (_sizeInBits_1 - 1);
                    _builder.append(_minus_1, "\t");
                    _builder.append(" downto 0); ");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connection_1);
                    _builder.append(_castfifoNameRead_1, "\t");
                    _builder.append("_V_full_n : IN STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connection_1);
                    _builder.append(_castfifoNameRead_2, "\t");
                    _builder.append("_V_write  : OUT STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_return : OUT STD_LOGIC_VECTOR (31 downto 0)");
    _builder.newLine();
    _builder.append(" ");
    _builder.append(");");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("END COMPONENT;\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal ap_clk :  STD_LOGIC:= \'0\';");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal ap_rst : STD_LOGIC:= \'0\';");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal ap_start : STD_LOGIC:= \'0\';");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal ap_done :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal ap_idle :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal ap_ready :  STD_LOGIC;");
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
        {
          EList<Port> _inputs_1 = instance_1.getActor().getInputs();
          for(final Port port_1 : _inputs_1) {
            _builder.append("\t");
            final Connection connection_2 = instance_1.getIncomingPortMap().get(port_1);
            _builder.newLineIfNotEmpty();
            {
              if ((connection_2 != null)) {
                {
                  Port _sourcePort_1 = connection_2.getSourcePort();
                  boolean _tripleEquals_2 = (_sourcePort_1 == null);
                  if (_tripleEquals_2) {
                    _builder.append("\t");
                    _builder.append("signal ");
                    CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(connection_2);
                    _builder.append(_castfifoNameWrite_3, "\t");
                    _builder.append("_V_dout   :  STD_LOGIC_VECTOR (");
                    int _sizeInBits_2 = this.fifoTypeOut(connection_2).getSizeInBits();
                    int _minus_2 = (_sizeInBits_2 - 1);
                    _builder.append(_minus_2, "\t");
                    _builder.append(" downto 0); ");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("signal ");
                    CharSequence _castfifoNameWrite_4 = this.castfifoNameWrite(connection_2);
                    _builder.append(_castfifoNameWrite_4, "\t");
                    _builder.append("_V_empty_n :  STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("signal ");
                    CharSequence _castfifoNameWrite_5 = this.castfifoNameWrite(connection_2);
                    _builder.append(_castfifoNameWrite_5, "\t");
                    _builder.append("_V_read    :  STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                  }
                }
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
          for(final Port portout_1 : _filter_3) {
            {
              List<Connection> _get_1 = instance_1.getOutgoingPortMap().get(portout_1);
              for(final Connection connection_3 : _get_1) {
                {
                  Port _targetPort_1 = connection_3.getTargetPort();
                  boolean _tripleEquals_3 = (_targetPort_1 == null);
                  if (_tripleEquals_3) {
                    _builder.append("\t");
                    _builder.append("signal ");
                    CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connection_3);
                    _builder.append(_castfifoNameRead_3, "\t");
                    _builder.append("_V_din    : STD_LOGIC_VECTOR (");
                    int _sizeInBits_3 = this.fifoTypeOut(connection_3).getSizeInBits();
                    int _minus_3 = (_sizeInBits_3 - 1);
                    _builder.append(_minus_3, "\t");
                    _builder.append(" downto 0); ");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("signal ");
                    CharSequence _castfifoNameRead_4 = this.castfifoNameRead(connection_3);
                    _builder.append(_castfifoNameRead_4, "\t");
                    _builder.append("_V_full_n : STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("signal ");
                    CharSequence _castfifoNameRead_5 = this.castfifoNameRead(connection_3);
                    _builder.append(_castfifoNameRead_5, "\t");
                    _builder.append("_V_write  :  STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("signal ap_return :  STD_LOGIC_VECTOR (31 downto 0):= (others => \'0\');");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- Configuration");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal count       : integer range 255 downto 0 := 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("constant PERIOD : time := 20 ns;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("constant DUTY_CYCLE : real := 0.5;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("constant OFFSET : time := 100 ns;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("type severity_level is (note, warning, error, failure);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("type tb_type is (after_reset, read_file, CheckRead);");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("-- Input and Output files");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal tb_FSM_bits  : tb_type;");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function_4 = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          return Boolean.valueOf(it.isActor());
        }
      };
      Iterable<Instance> _filter_4 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_4);
      for(final Instance instance_2 : _filter_4) {
        _builder.append("\t");
        CharSequence _assignFifoFile = this.assignFifoFile(instance_2);
        _builder.append(_assignFifoFile, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("begin");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("uut : TopDesign port map (");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_clk => ap_clk,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_rst => ap_rst,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_start => ap_start,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_done => ap_done,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_idle => ap_idle,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_ready =>ap_ready,");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function_5 = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          return Boolean.valueOf(it.isActor());
        }
      };
      Iterable<Instance> _filter_5 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_5);
      for(final Instance instance_3 : _filter_5) {
        _builder.append("\t");
        CharSequence _mappingFifoSignal = this.mappingFifoSignal(instance_3);
        _builder.append(_mappingFifoSignal, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("ap_return => ap_return);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("clockProcess : process");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("begin");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("wait for OFFSET;");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("clock_LOOP : loop");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("ap_clk <= \'0\';");
    _builder.newLine();
    _builder.append("\t          ");
    _builder.append("wait for (PERIOD - (PERIOD * DUTY_CYCLE));");
    _builder.newLine();
    _builder.append("\t          ");
    _builder.append("ap_clk <= \'1\';");
    _builder.newLine();
    _builder.append("\t          ");
    _builder.append("wait for (PERIOD * DUTY_CYCLE);");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("end loop clock_LOOP;");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("end process;");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("resetProcess : process");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("begin                ");
    _builder.newLine();
    _builder.append("\t      ");
    _builder.append("wait for OFFSET;");
    _builder.newLine();
    _builder.append("\t      ");
    _builder.append("-- reset state for 100 ns.");
    _builder.newLine();
    _builder.append("\t      ");
    _builder.append("ap_rst <= \'1\';");
    _builder.newLine();
    _builder.append("\t      ");
    _builder.append("wait for 100 ns;");
    _builder.newLine();
    _builder.append("\t      ");
    _builder.append("ap_rst <= \'0\';        ");
    _builder.newLine();
    _builder.append("\t      ");
    _builder.append("wait;");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("end process;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _isEmpty = this.network.getInputs().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("WaveGen_Proc_In : process (ap_clk)");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("variable Input_bit   : integer range 2147483647 downto - 2147483648;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("variable line_number : line;");
        _builder.newLine();
        {
          final Function1<Instance, Boolean> _function_6 = new Function1<Instance, Boolean>() {
            @Override
            public Boolean apply(final Instance it) {
              return Boolean.valueOf(it.isActor());
            }
          };
          Iterable<Instance> _filter_6 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_6);
          for(final Instance instance_4 : _filter_6) {
            {
              EList<Port> _inputs_2 = instance_4.getActor().getInputs();
              for(final Port port_2 : _inputs_2) {
                _builder.append("\t");
                final Connection connection_4 = instance_4.getIncomingPortMap().get(port_2);
                _builder.newLineIfNotEmpty();
                {
                  if ((connection_4 != null)) {
                    {
                      Port _sourcePort_2 = connection_4.getSourcePort();
                      boolean _tripleEquals_4 = (_sourcePort_2 == null);
                      if (_tripleEquals_4) {
                        _builder.append("\t");
                        _builder.append("variable count");
                        CharSequence _castfifoNameWrite_6 = this.castfifoNameWrite(connection_4);
                        _builder.append(_castfifoNameWrite_6, "\t");
                        _builder.append(": integer:= 0;");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("begin");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t  ");
        _builder.append("if rising_edge(ap_clk) then");
        _builder.newLine();
        {
          final Function1<Instance, Boolean> _function_7 = new Function1<Instance, Boolean>() {
            @Override
            public Boolean apply(final Instance it) {
              return Boolean.valueOf(it.isActor());
            }
          };
          Iterable<Instance> _filter_7 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_7);
          for(final Instance instance_5 : _filter_7) {
            _builder.append("\t");
            _builder.append("\t");
            CharSequence _waveGenInputs = this.waveGenInputs(instance_5);
            _builder.append(_waveGenInputs, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("end if;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("end process WaveGen_Proc_In;");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    {
      boolean _isEmpty_1 = this.network.getOutputs().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("\t\t");
        _builder.append("WaveGen_Proc_Out : process (ap_clk)");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("variable Input_bit   : integer range 2147483647 downto - 2147483648;");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("variable line_number : line;");
        _builder.newLine();
        {
          final Function1<Instance, Boolean> _function_8 = new Function1<Instance, Boolean>() {
            @Override
            public Boolean apply(final Instance it) {
              return Boolean.valueOf(it.isActor());
            }
          };
          Iterable<Instance> _filter_8 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_8);
          for(final Instance instance_6 : _filter_8) {
            {
              final Function1<Port, Boolean> _function_9 = new Function1<Port, Boolean>() {
                @Override
                public Boolean apply(final Port it) {
                  boolean _isNative = it.isNative();
                  return Boolean.valueOf((!_isNative));
                }
              };
              Iterable<Port> _filter_9 = IterableExtensions.<Port>filter(instance_6.getActor().getOutputs(), _function_9);
              for(final Port portout_2 : _filter_9) {
                {
                  List<Connection> _get_2 = instance_6.getOutgoingPortMap().get(portout_2);
                  for(final Connection connection_5 : _get_2) {
                    {
                      Port _targetPort_2 = connection_5.getTargetPort();
                      boolean _tripleEquals_5 = (_targetPort_2 == null);
                      if (_tripleEquals_5) {
                        _builder.append("\t\t");
                        _builder.append("variable count");
                        CharSequence _castfifoNameRead_6 = this.castfifoNameRead(connection_5);
                        _builder.append(_castfifoNameRead_6, "\t\t");
                        _builder.append(": integer:= 0;");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
          }
        }
        _builder.append("\t\t");
        _builder.append("begin");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("if (rising_edge(ap_clk)) then");
        _builder.newLine();
        {
          final Function1<Instance, Boolean> _function_10 = new Function1<Instance, Boolean>() {
            @Override
            public Boolean apply(final Instance it) {
              return Boolean.valueOf(it.isActor());
            }
          };
          Iterable<Instance> _filter_10 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_10);
          for(final Instance instance_7 : _filter_10) {
            _builder.append("\t\t");
            CharSequence _waveGenOutputs = this.waveGenOutputs(instance_7);
            _builder.append(_waveGenOutputs, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t\t");
        _builder.append("end if;");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("end process WaveGen_Proc_Out;");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function_11 = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          return Boolean.valueOf(it.isActor());
        }
      };
      Iterable<Instance> _filter_11 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_11);
      for(final Instance instance_8 : _filter_11) {
        _builder.append("\t\t");
        CharSequence _initOutputs = this.initOutputs(instance_8);
        _builder.append(_initOutputs, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    {
      boolean _isEmpty_2 = this.network.getInputs().isEmpty();
      if (_isEmpty_2) {
        _builder.append("\t\t");
        _builder.append("ap_start <= \'1\';");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.append("END;");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence assignFifoFile(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Collection<List<Connection>> _values = instance.getOutgoingPortMap().values();
      for(final List<Connection> connList : _values) {
        {
          if (((!(IterableExtensions.<Connection>head(connList).getSource() instanceof Port)) && (IterableExtensions.<Connection>head(connList).getTarget() instanceof Port))) {
            _builder.append("file sim_file_");
            String _name = instance.getName();
            _builder.append(_name);
            _builder.append("_");
            String _name_1 = IterableExtensions.<Connection>head(connList).getSourcePort().getName();
            _builder.append(_name_1);
            _builder.append("  : text is \"");
            String _name_2 = instance.getName();
            _builder.append(_name_2);
            _builder.append("_");
            String _name_3 = IterableExtensions.<Connection>head(connList).getSourcePort().getName();
            _builder.append(_name_3);
            _builder.append(".txt\";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      Collection<Connection> _values_1 = instance.getIncomingPortMap().values();
      for(final Connection connList_1 : _values_1) {
        {
          if (((connList_1.getSource() instanceof Port) && (!(connList_1.getTarget() instanceof Port)))) {
            _builder.append("file sim_file_");
            String _name_4 = instance.getName();
            _builder.append(_name_4);
            _builder.append("_");
            String _name_5 = connList_1.getTargetPort().getName();
            _builder.append(_name_5);
            _builder.append("  : text is \"");
            String _name_6 = instance.getName();
            _builder.append(_name_6);
            _builder.append("_");
            String _name_7 = connList_1.getTargetPort().getName();
            _builder.append(_name_7);
            _builder.append(".txt\";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence mappingFifoSignal(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Collection<List<Connection>> _values = instance.getOutgoingPortMap().values();
      for(final List<Connection> connList : _values) {
        {
          if (((!(IterableExtensions.<Connection>head(connList).getSource() instanceof Port)) && (IterableExtensions.<Connection>head(connList).getTarget() instanceof Port))) {
            CharSequence _printOutputFifoMappingHLS = this.printOutputFifoMappingHLS(IterableExtensions.<Connection>head(connList));
            _builder.append(_printOutputFifoMappingHLS);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      Collection<Connection> _values_1 = instance.getIncomingPortMap().values();
      for(final Connection connList_1 : _values_1) {
        {
          if (((connList_1.getSource() instanceof Port) && (!(connList_1.getTarget() instanceof Port)))) {
            CharSequence _printInputFifoMappingHLS = this.printInputFifoMappingHLS(connList_1);
            _builder.append(_printInputFifoMappingHLS);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence printOutputFifoMappingHLS(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _castfifoNameRead = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead);
    _builder.append("_V_din    => ");
    CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_1);
    _builder.append("_V_din,");
    _builder.newLineIfNotEmpty();
    CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_2);
    _builder.append("_V_full_n => ");
    CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_3);
    _builder.append("_V_full_n,");
    _builder.newLineIfNotEmpty();
    CharSequence _castfifoNameRead_4 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_4);
    _builder.append("_V_write  => ");
    CharSequence _castfifoNameRead_5 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_5);
    _builder.append("_V_write,");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence printInputFifoMappingHLS(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite);
    _builder.append("_V_dout    => ");
    CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_1);
    _builder.append("_V_dout,");
    _builder.newLineIfNotEmpty();
    CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_2);
    _builder.append("_V_empty_n => ");
    CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_3);
    _builder.append("_V_empty_n,");
    _builder.newLineIfNotEmpty();
    CharSequence _castfifoNameWrite_4 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_4);
    _builder.append("_V_read    => ");
    CharSequence _castfifoNameWrite_5 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_5);
    _builder.append("_V_read,");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence waveGenInputs(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _inputs = instance.getActor().getInputs();
      for(final Port port : _inputs) {
        final Connection connection = instance.getIncomingPortMap().get(port);
        _builder.newLineIfNotEmpty();
        {
          if ((connection != null)) {
            {
              Port _sourcePort = connection.getSourcePort();
              boolean _tripleEquals = (_sourcePort == null);
              if (_tripleEquals) {
                Vertex _target = connection.getTarget();
                CharSequence _printInputWaveGen = this.printInputWaveGen(((Instance) _target), connection);
                _builder.append(_printInputWaveGen);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence waveGenOutputs(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter = IterableExtensions.<Port>filter(instance.getActor().getOutputs(), _function);
      for(final Port portout : _filter) {
        {
          List<Connection> _get = instance.getOutgoingPortMap().get(portout);
          for(final Connection connection : _get) {
            {
              Port _targetPort = connection.getTargetPort();
              boolean _tripleEquals = (_targetPort == null);
              if (_tripleEquals) {
                Vertex _source = connection.getSource();
                CharSequence _printOutputWaveGen = this.printOutputWaveGen(((Instance) _source), connection);
                _builder.append(_printOutputWaveGen);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence printInputWaveGen(final Instance instance, final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("case tb_FSM_bits is");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("when after_reset =>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("count <= count + 1;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (count = 15) then");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("tb_FSM_bits <= read_file;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("count           <= 0;");
    _builder.newLine();
    _builder.append("end if;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("when read_file =>");
    _builder.newLine();
    _builder.append("if (not endfile (sim_file_");
    String _name = instance.getName();
    _builder.append(_name);
    _builder.append("_");
    String _name_1 = connection.getTargetPort().getName();
    _builder.append(_name_1);
    _builder.append(")) then");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("readline(sim_file_");
    String _name_2 = instance.getName();
    _builder.append(_name_2, "\t");
    _builder.append("_");
    String _name_3 = connection.getTargetPort().getName();
    _builder.append(_name_3, "\t");
    _builder.append(", line_number);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("if (line_number\'length > 0 and line_number(1) /= \'/\') then");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("read(line_number, input_bit);");
    _builder.newLine();
    {
      boolean _isInt = this.fifoTypeIn(connection).isInt();
      if (_isInt) {
        _builder.append("\t\t");
        CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection);
        _builder.append(_castfifoNameWrite, "\t\t");
        _builder.append("_V_dout  <= std_logic_vector(to_signed(input_bit, ");
        int _sizeInBits = this.fifoTypeIn(connection).getSizeInBits();
        _builder.append(_sizeInBits, "\t\t");
        _builder.append("));");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isUint = this.fifoTypeIn(connection).isUint();
      if (_isUint) {
        _builder.append("\t\t");
        CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(connection);
        _builder.append(_castfifoNameWrite_1, "\t\t");
        _builder.append("_V_dout  <= std_logic_vector(to_unsigned(input_bit, ");
        int _sizeInBits_1 = this.fifoTypeIn(connection).getSizeInBits();
        _builder.append(_sizeInBits_1, "\t\t");
        _builder.append("));");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isBool = this.fifoTypeIn(connection).isBool();
      if (_isBool) {
        _builder.append("\t\t");
        _builder.append("if (input_bit = 1) then ");
        _builder.newLine();
        _builder.append("\t\t");
        CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(connection);
        _builder.append(_castfifoNameWrite_2, "\t\t");
        _builder.append("_V_dout  <= \"1\";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("else");
        _builder.newLine();
        _builder.append("\t\t");
        CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(connection);
        _builder.append(_castfifoNameWrite_3, "\t\t");
        _builder.append("_V_dout  <= \"0\";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("end if;");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    CharSequence _castfifoNameWrite_4 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_4, "\t\t");
    _builder.append("_V_empty_n <= \'1\';");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("ap_start <= \'1\';    ");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("tb_FSM_bits <= CheckRead;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.append("end if;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("when CheckRead =>");
    _builder.newLine();
    _builder.append("if (not endfile (sim_file_");
    String _name_4 = instance.getName();
    _builder.append(_name_4);
    _builder.append("_");
    String _name_5 = connection.getTargetPort().getName();
    _builder.append(_name_5);
    _builder.append(")) and ");
    CharSequence _castfifoNameWrite_5 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_5);
    _builder.append("_V_read = \'1\' then");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("count");
    CharSequence _castfifoNameWrite_6 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_6, " ");
    _builder.append(" := count");
    CharSequence _castfifoNameWrite_7 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_7, " ");
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("report \"Number of inputs");
    CharSequence _castfifoNameWrite_8 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_8, " ");
    _builder.append(" = \" & integer\'image(count");
    CharSequence _castfifoNameWrite_9 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_9, " ");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    CharSequence _castfifoNameWrite_10 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_10, " ");
    _builder.append("_V_empty_n <= \'0\';");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("readline(sim_file_");
    String _name_6 = instance.getName();
    _builder.append(_name_6, " ");
    _builder.append("_");
    String _name_7 = connection.getTargetPort().getName();
    _builder.append(_name_7, " ");
    _builder.append(", line_number);");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("if (line_number\'length > 0 and line_number(1) /= \'/\') then");
    _builder.newLine();
    _builder.append(" \t");
    _builder.append("read(line_number, input_bit);");
    _builder.newLine();
    {
      boolean _isInt_1 = this.fifoTypeIn(connection).isInt();
      if (_isInt_1) {
        _builder.append(" \t");
        CharSequence _castfifoNameWrite_11 = this.castfifoNameWrite(connection);
        _builder.append(_castfifoNameWrite_11, " \t");
        _builder.append("_V_dout  <= std_logic_vector(to_signed(input_bit, ");
        int _sizeInBits_2 = this.fifoTypeIn(connection).getSizeInBits();
        _builder.append(_sizeInBits_2, " \t");
        _builder.append("));");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isUint_1 = this.fifoTypeIn(connection).isUint();
      if (_isUint_1) {
        _builder.append(" \t");
        CharSequence _castfifoNameWrite_12 = this.castfifoNameWrite(connection);
        _builder.append(_castfifoNameWrite_12, " \t");
        _builder.append("_V_dout  <= std_logic_vector(to_unsigned(input_bit, ");
        int _sizeInBits_3 = this.fifoTypeIn(connection).getSizeInBits();
        _builder.append(_sizeInBits_3, " \t");
        _builder.append("));");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append(" \t");
    CharSequence _castfifoNameWrite_13 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_13, " \t");
    _builder.append("_V_empty_n <= \'1\';");
    _builder.newLineIfNotEmpty();
    {
      boolean _isBool_1 = this.fifoTypeIn(connection).isBool();
      if (_isBool_1) {
        _builder.append(" \t");
        _builder.append("if (input_bit = 1) then ");
        _builder.newLine();
        _builder.append(" \t");
        CharSequence _castfifoNameWrite_14 = this.castfifoNameWrite(connection);
        _builder.append(_castfifoNameWrite_14, " \t");
        _builder.append("_V_dout  <= \"1\";");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("else");
        _builder.newLine();
        _builder.append(" \t");
        CharSequence _castfifoNameWrite_15 = this.castfifoNameWrite(connection);
        _builder.append(_castfifoNameWrite_15, " \t");
        _builder.append("_V_dout  <= \"0\";");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("end if;");
        _builder.newLine();
      }
    }
    _builder.append(" \t");
    _builder.append("ap_start <= \'1\';      ");
    _builder.newLine();
    _builder.append("end if;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("elsif (endfile (sim_file_");
    String _name_8 = instance.getName();
    _builder.append(_name_8, "\t");
    _builder.append("_");
    String _name_9 = connection.getTargetPort().getName();
    _builder.append(_name_9, "\t");
    _builder.append(")) then");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("ap_start <= \'1\';");
    _builder.newLine();
    _builder.append("\t\t");
    CharSequence _castfifoNameWrite_16 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_16, "\t\t");
    _builder.append("_V_empty_n <= \'0\';");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.append("when others => null;");
    _builder.newLine();
    _builder.append("end case;");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printOutputWaveGen(final Instance vertex, final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (not endfile (sim_file_");
    String _name = vertex.getName();
    _builder.append(_name);
    _builder.append("_");
    String _name_1 = connection.getSourcePort().getName();
    _builder.append(_name_1);
    _builder.append(") and ");
    CharSequence _castfifoNameRead = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead);
    _builder.append("_V_write = \'1\') then");
    _builder.newLineIfNotEmpty();
    _builder.append("count");
    CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_1);
    _builder.append(" := count");
    CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_2);
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("report \"Number of outputs");
    CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_3, " ");
    _builder.append(" = \" & integer\'image(count");
    CharSequence _castfifoNameRead_4 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_4, " ");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("readline(sim_file_");
    String _name_2 = vertex.getName();
    _builder.append(_name_2, " ");
    _builder.append("_");
    String _name_3 = connection.getSourcePort().getName();
    _builder.append(_name_3, " ");
    _builder.append(", line_number);");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("if (line_number\'length > 0 and line_number(1) /= \'/\') then");
    _builder.newLine();
    _builder.append(" \t");
    _builder.append("read(line_number, input_bit);");
    _builder.newLine();
    {
      boolean _isInt = this.fifoTypeOut(connection).isInt();
      if (_isInt) {
        _builder.append(" \t");
        _builder.append("assert (");
        CharSequence _castfifoNameRead_5 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_5, " \t");
        _builder.append("_V_din  = std_logic_vector(to_signed(input_bit, ");
        int _sizeInBits = this.fifoTypeOut(connection).getSizeInBits();
        _builder.append(_sizeInBits, " \t");
        _builder.append(")))");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("-- report \"on ");
        CharSequence _castfifoNameRead_6 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_6, " \t");
        _builder.append(" incorrectly value computed : \" & to_string(to_integer(to_signed(");
        CharSequence _castfifoNameRead_7 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_7, " \t");
        _builder.append("_V_din))) & \" instead of :\" & to_string(input_bit)");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("report \"on port ");
        CharSequence _castfifoNameRead_8 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_8, " \t");
        _builder.append(" incorrectly value computed : \" & str(to_integer(signed(");
        CharSequence _castfifoNameRead_9 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_9, " \t");
        _builder.append("_V_din))) & \" instead of :\" & str(input_bit)");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("severity error;");
        _builder.newLine();
      }
    }
    {
      boolean _isUint = this.fifoTypeOut(connection).isUint();
      if (_isUint) {
        _builder.append(" \t");
        _builder.append("assert (");
        CharSequence _castfifoNameRead_10 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_10, " \t");
        _builder.append("_V_din  = std_logic_vector(to_unsigned(input_bit, ");
        int _sizeInBits_1 = this.fifoTypeOut(connection).getSizeInBits();
        _builder.append(_sizeInBits_1, " \t");
        _builder.append(")))");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("-- report \"on ");
        CharSequence _castfifoNameRead_11 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_11, " \t");
        _builder.append(" incorrectly value computed : \" & to_string(to_integer(to_unsigned(");
        CharSequence _castfifoNameRead_12 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_12, " \t");
        _builder.append("_V_din))) & \" instead of :\" & to_string(input_bit)");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("report \"on port ");
        CharSequence _castfifoNameRead_13 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_13, " \t");
        _builder.append(" incorrectly value computed : \" & str(to_integer(unsigned(");
        CharSequence _castfifoNameRead_14 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_14, " \t");
        _builder.append("_V_din))) & \" instead of :\" & str(input_bit)");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("severity error;");
        _builder.newLine();
      }
    }
    {
      boolean _isBool = this.fifoTypeOut(connection).isBool();
      if (_isBool) {
        _builder.append(" \t");
        _builder.append("if (input_bit = 1) then");
        _builder.newLine();
        _builder.append(" \t");
        _builder.append("\t");
        _builder.append("assert (");
        CharSequence _castfifoNameRead_15 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_15, " \t\t");
        _builder.append("_V_din  = \"1\")");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("\t");
        _builder.append("report \"on port ");
        CharSequence _castfifoNameRead_16 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_16, " \t\t");
        _builder.append(" 0 instead of 1\"");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("\t");
        _builder.append("severity error;");
        _builder.newLine();
        _builder.append(" \t");
        _builder.append("else");
        _builder.newLine();
        _builder.append(" \t");
        _builder.append("\t");
        _builder.append("assert (");
        CharSequence _castfifoNameRead_17 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_17, " \t\t");
        _builder.append("_V_din  = \"0\")");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("\t");
        _builder.append("report \"on port ");
        CharSequence _castfifoNameRead_18 = this.castfifoNameRead(connection);
        _builder.append(_castfifoNameRead_18, " \t\t");
        _builder.append(" 1 instead of 0\"");
        _builder.newLineIfNotEmpty();
        _builder.append(" \t");
        _builder.append("\t");
        _builder.append("severity error;");
        _builder.newLine();
        _builder.append(" \t");
        _builder.append("end if;");
        _builder.newLine();
      }
    }
    _builder.append(" \t");
    _builder.newLine();
    _builder.append(" ");
    _builder.newLine();
    _builder.append(" \t");
    _builder.append("--assert (");
    CharSequence _castfifoNameRead_19 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_19, " \t");
    _builder.append("_V_din /= std_logic_vector(to_signed(input_bit, ");
    int _sizeInBits_2 = this.fifoTypeOut(connection).getSizeInBits();
    _builder.append(_sizeInBits_2, " \t");
    _builder.append(")))");
    _builder.newLineIfNotEmpty();
    _builder.append(" \t");
    _builder.append("--report \"on port ");
    CharSequence _castfifoNameRead_20 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_20, " \t");
    _builder.append(" correct value computed : \" & str(to_integer(signed(");
    CharSequence _castfifoNameRead_21 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_21, " \t");
    _builder.append("_V_din))) & \" equals :\" & str(input_bit)");
    _builder.newLineIfNotEmpty();
    _builder.append(" \t");
    _builder.append("--severity note;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.append("end if;");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence initOutputs(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter = IterableExtensions.<Port>filter(instance.getActor().getOutputs(), _function);
      for(final Port portout : _filter) {
        {
          List<Connection> _get = instance.getOutgoingPortMap().get(portout);
          for(final Connection connection : _get) {
            {
              Port _targetPort = connection.getTargetPort();
              boolean _tripleEquals = (_targetPort == null);
              if (_tripleEquals) {
                CharSequence _castfifoNameRead = this.castfifoNameRead(connection);
                _builder.append(_castfifoNameRead);
                _builder.append("_V_full_n <= \'1\';");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
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
  
  public Type fifoTypeOut(final Connection connection) {
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
  
  public Type fifoTypeIn(final Connection connection) {
    Type _xifexpression = null;
    Port _targetPort = connection.getTargetPort();
    boolean _tripleEquals = (_targetPort == null);
    if (_tripleEquals) {
      _xifexpression = connection.getSourcePort().getType();
    } else {
      _xifexpression = connection.getTargetPort().getType();
    }
    return _xifexpression;
  }
}
