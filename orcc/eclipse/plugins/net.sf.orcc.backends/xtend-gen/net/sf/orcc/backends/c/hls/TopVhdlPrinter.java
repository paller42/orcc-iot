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
import net.sf.orcc.OrccLaunchConstants;
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
 * Top network VHDL Code
 * 
 * @author Khaled Jerbi and Mariem Abid
 */
@SuppressWarnings("all")
public class TopVhdlPrinter extends NetworkPrinter {
  @Override
  public CharSequence getNetworkFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("library ieee;");
    _builder.newLine();
    _builder.append("use ieee.std_logic_1164.all;");
    _builder.newLine();
    _builder.append("use ieee.std_logic_arith.all;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("entity TopDesign is");
    _builder.newLine();
    _builder.append("port(");
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
              if (((connection != null) && (connection.getSourcePort() == null))) {
                _builder.append("\t");
                CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection);
                _builder.append(_castfifoNameWrite, "\t");
                _builder.append("_V_dout   : IN STD_LOGIC_VECTOR (");
                int _sizeInBits = this.fifoType(connection).getSizeInBits();
                int _minus = (_sizeInBits - 1);
                _builder.append(_minus, "\t");
                _builder.append(" downto 0);");
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
                  boolean _tripleEquals = (_targetPort == null);
                  if (_tripleEquals) {
                    _builder.append("\t");
                    CharSequence _castfifoNameRead = this.castfifoNameRead(connection_1);
                    _builder.append(_castfifoNameRead, "\t");
                    _builder.append("_V_din    : OUT STD_LOGIC_VECTOR (");
                    int _sizeInBits_1 = this.fifoType(connection_1).getSizeInBits();
                    int _minus_1 = (_sizeInBits_1 - 1);
                    _builder.append(_minus_1, "\t");
                    _builder.append(" downto 0);");
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
    _builder.append("ap_return : OUT STD_LOGIC_VECTOR (31 downto 0));");
    _builder.newLine();
    _builder.append("end entity TopDesign;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- Architecture Declaration");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.newLine();
    _builder.append("architecture rtl of TopDesign is");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- Signal Instantiation");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal top_ap_clk :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal top_ap_rst :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal top_ap_start :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal top_ap_done :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal top_ap_idle :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal top_ap_ready :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- FIFO Instantiation");
    _builder.newLine();
    _builder.append("\t");
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
        _builder.append("\t");
        CharSequence _assignFifoSignal = this.assignFifoSignal(instance_1);
        _builder.append(_assignFifoSignal, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Components of the Network");
    _builder.newLine();
    _builder.append("-- ---------------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function_3 = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          return Boolean.valueOf(it.isActor());
        }
      };
      Iterable<Instance> _filter_3 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_3);
      for(final Instance instance_2 : _filter_3) {
        _builder.append("\t");
        CharSequence _declareComponentSignal = this.declareComponentSignal(instance_2);
        _builder.append(_declareComponentSignal, "\t");
        _builder.newLineIfNotEmpty();
        {
          EList<Port> _inputs_1 = instance_2.getActor().getInputs();
          for(final Port port_1 : _inputs_1) {
            _builder.append("\t");
            final Connection connection_2 = instance_2.getIncomingPortMap().get(port_1);
            _builder.newLineIfNotEmpty();
            {
              if (((connection_2 != null) && (connection_2.getSourcePort() == null))) {
                _builder.append("\t");
                _builder.append("component cast_");
                String _name = instance_2.getName();
                _builder.append(_name, "\t");
                _builder.append("_");
                String _name_1 = connection_2.getTargetPort().getName();
                _builder.append(_name_1, "\t");
                _builder.append("_write_scheduler IS");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("port (");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _ramName = this.ramName(connection_2);
                _builder.append(_ramName, "\t\t\t");
                _builder.append("_address0    : OUT  STD_LOGIC_VECTOR (");
                int _closestLog_2 = this.closestLog_2(this.safeSize(connection_2));
                _builder.append(_closestLog_2, "\t\t\t");
                _builder.append("-1 downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _ramName_1 = this.ramName(connection_2);
                _builder.append(_ramName_1, "\t\t\t");
                _builder.append("_ce0 : OUT STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _ramName_2 = this.ramName(connection_2);
                _builder.append(_ramName_2, "\t\t\t");
                _builder.append("_we0  : OUT STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _ramName_3 = this.ramName(connection_2);
                _builder.append(_ramName_3, "\t\t\t");
                _builder.append("_d0  : OUT STD_LOGIC_VECTOR (");
                int _sizeInBits_2 = this.fifoType(connection_2).getSizeInBits();
                int _minus_2 = (_sizeInBits_2 - 1);
                _builder.append(_minus_2, "\t\t\t");
                _builder.append("  downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _wName = this.wName(connection_2);
                _builder.append(_wName, "\t\t\t");
                _builder.append("_address0    :  OUT STD_LOGIC_VECTOR (0 downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _wName_1 = this.wName(connection_2);
                _builder.append(_wName_1, "\t\t\t");
                _builder.append("_ce0 :  OUT STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _wName_2 = this.wName(connection_2);
                _builder.append(_wName_2, "\t\t\t");
                _builder.append("_we0  : OUT STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _wName_3 = this.wName(connection_2);
                _builder.append(_wName_3, "\t\t\t");
                _builder.append("_d0  :  OUT STD_LOGIC_VECTOR (31  downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _rName = this.rName(connection_2);
                _builder.append(_rName, "\t\t\t");
                _builder.append("_address0    : OUT STD_LOGIC_VECTOR (0 downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _rName_1 = this.rName(connection_2);
                _builder.append(_rName_1, "\t\t\t");
                _builder.append("_ce0 : OUT STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _rName_2 = this.rName(connection_2);
                _builder.append(_rName_2, "\t\t\t");
                _builder.append("_q0  : IN  STD_LOGIC_VECTOR (31  downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t\t\t");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(connection_2);
                _builder.append(_castfifoNameWrite_3, "\t\t\t");
                _builder.append("_V_dout   : IN STD_LOGIC_VECTOR (");
                int _sizeInBits_3 = this.fifoType(connection_2).getSizeInBits();
                int _minus_3 = (_sizeInBits_3 - 1);
                _builder.append(_minus_3, "\t\t\t");
                _builder.append(" downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _castfifoNameWrite_4 = this.castfifoNameWrite(connection_2);
                _builder.append(_castfifoNameWrite_4, "\t\t\t");
                _builder.append("_V_empty_n : IN STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                CharSequence _castfifoNameWrite_5 = this.castfifoNameWrite(connection_2);
                _builder.append(_castfifoNameWrite_5, "\t\t\t");
                _builder.append("_V_read    : OUT STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t\t");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                _builder.append("ap_clk : IN STD_LOGIC;");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                _builder.append("ap_rst : IN STD_LOGIC;");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                _builder.append("ap_start : IN STD_LOGIC;");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                _builder.append("ap_done : OUT STD_LOGIC;");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                _builder.append("ap_idle : OUT STD_LOGIC;");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t\t");
                _builder.append("ap_ready : OUT STD_LOGIC);");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("end component;");
                _builder.newLine();
              }
            }
          }
        }
        _builder.append("\t");
        _builder.newLine();
        {
          final Function1<Port, Boolean> _function_4 = new Function1<Port, Boolean>() {
            @Override
            public Boolean apply(final Port it) {
              boolean _isNative = it.isNative();
              return Boolean.valueOf((!_isNative));
            }
          };
          Iterable<Port> _filter_4 = IterableExtensions.<Port>filter(instance_2.getActor().getOutputs(), _function_4);
          for(final Port port_2 : _filter_4) {
            {
              List<Connection> _get_1 = instance_2.getOutgoingPortMap().get(port_2);
              for(final Connection connection_3 : _get_1) {
                {
                  Port _targetPort_1 = connection_3.getTargetPort();
                  boolean _tripleEquals_1 = (_targetPort_1 == null);
                  if (_tripleEquals_1) {
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("component cast_");
                    String _name_2 = instance_2.getName();
                    _builder.append(_name_2, "\t\t");
                    _builder.append("_");
                    String _name_3 = IterableExtensions.<Connection>head(instance_2.getOutgoingPortMap().get(port_2)).getSourcePort().getName();
                    _builder.append(_name_3, "\t\t");
                    _builder.append("_read_scheduler IS");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("port (");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _ramName_4 = this.ramName(connection_3);
                    _builder.append(_ramName_4, "\t\t");
                    _builder.append("_address0    : OUT STD_LOGIC_VECTOR (");
                    int _closestLog_2_1 = this.closestLog_2(this.safeSize(connection_3));
                    _builder.append(_closestLog_2_1, "\t\t");
                    _builder.append("-1 downto 0);");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _ramName_5 = this.ramName(connection_3);
                    _builder.append(_ramName_5, "\t\t");
                    _builder.append("_ce0 : OUT STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _ramName_6 = this.ramName(connection_3);
                    _builder.append(_ramName_6, "\t\t");
                    _builder.append("_q0  :  IN STD_LOGIC_VECTOR (");
                    int _sizeInBits_4 = this.fifoType(connection_3).getSizeInBits();
                    int _minus_4 = (_sizeInBits_4 - 1);
                    _builder.append(_minus_4, "\t\t");
                    _builder.append("  downto 0);");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _wName_4 = this.wName(connection_3);
                    _builder.append(_wName_4, "\t\t");
                    _builder.append("_address0    : OUT STD_LOGIC_VECTOR (0 downto 0);");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _wName_5 = this.wName(connection_3);
                    _builder.append(_wName_5, "\t\t");
                    _builder.append("_ce0 : OUT STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _wName_6 = this.wName(connection_3);
                    _builder.append(_wName_6, "\t\t");
                    _builder.append("_q0  : IN  STD_LOGIC_VECTOR (31  downto 0);");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _rName_3 = this.rName(connection_3);
                    _builder.append(_rName_3, "\t\t");
                    _builder.append("_address0    :OUT  STD_LOGIC_VECTOR (0 downto 0);");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _rName_4 = this.rName(connection_3);
                    _builder.append(_rName_4, "\t\t");
                    _builder.append("_ce0 : OUT STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _rName_5 = this.rName(connection_3);
                    _builder.append(_rName_5, "\t\t");
                    _builder.append("_we0  : OUT STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _rName_6 = this.rName(connection_3);
                    _builder.append(_rName_6, "\t\t");
                    _builder.append("_d0  : OUT  STD_LOGIC_VECTOR (31  downto 0);");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connection_3);
                    _builder.append(_castfifoNameRead_3, "\t\t");
                    _builder.append("_V_din    : OUT STD_LOGIC_VECTOR (");
                    int _sizeInBits_5 = this.fifoType(connection_3).getSizeInBits();
                    int _minus_5 = (_sizeInBits_5 - 1);
                    _builder.append(_minus_5, "\t\t");
                    _builder.append(" downto 0);");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _castfifoNameRead_4 = this.castfifoNameRead(connection_3);
                    _builder.append(_castfifoNameRead_4, "\t\t");
                    _builder.append("_V_full_n : IN STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _castfifoNameRead_5 = this.castfifoNameRead(connection_3);
                    _builder.append(_castfifoNameRead_5, "\t\t");
                    _builder.append("_V_write  : OUT STD_LOGIC;");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("ap_clk : IN STD_LOGIC;");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("ap_rst : IN STD_LOGIC;");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("ap_start : IN STD_LOGIC;");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("ap_done : OUT STD_LOGIC;");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("ap_idle : OUT STD_LOGIC;");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("ap_ready : OUT STD_LOGIC);");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("end component;");
                    _builder.newLine();
                  }
                }
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("component ram_tab is ");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("generic(");
    _builder.newLine();
    _builder.append("\t         ");
    _builder.append("dwidth     : INTEGER; ");
    _builder.newLine();
    _builder.append("\t         ");
    _builder.append("awidth     : INTEGER;  ");
    _builder.newLine();
    _builder.append("\t         ");
    _builder.append("mem_size    : INTEGER ");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("); ");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("port (");
    _builder.newLine();
    _builder.append("\t       ");
    _builder.append("addr0     : in std_logic_vector(awidth-1 downto 0); ");
    _builder.newLine();
    _builder.append("\t       ");
    _builder.append("ce0       : in std_logic; ");
    _builder.newLine();
    _builder.append("\t       ");
    _builder.append("q0        : out std_logic_vector(dwidth-1 downto 0);");
    _builder.newLine();
    _builder.append("\t       ");
    _builder.append("addr1     : in std_logic_vector(awidth-1 downto 0); ");
    _builder.newLine();
    _builder.append("\t       ");
    _builder.append("ce1       : in std_logic; ");
    _builder.newLine();
    _builder.append("\t       ");
    _builder.append("d1        : in std_logic_vector(dwidth-1 downto 0); ");
    _builder.newLine();
    _builder.append("\t       ");
    _builder.append("we1       : in std_logic; ");
    _builder.newLine();
    _builder.append("\t       ");
    _builder.append("clk        : in std_logic ");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("); ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("end component; ");
    _builder.newLine();
    _builder.append("begin");
    _builder.newLine();
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
        CharSequence _mappingComponentFifoSignal = this.mappingComponentFifoSignal(instance_3);
        _builder.append(_mappingComponentFifoSignal, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
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
        _builder.append("\t");
        CharSequence _mappingComponentSignal = this.mappingComponentSignal(instance_4);
        _builder.append(_mappingComponentSignal, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Network Ports Instantiation ");
    _builder.newLine();
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("\t");
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
        CharSequence _assignNetworkPorts = this.assignNetworkPorts(instance_5);
        _builder.append(_assignNetworkPorts, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.append("top_ap_start <= ap_start;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("top_ap_clk <= ap_clk;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("top_ap_rst <= ap_rst;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("end architecture rtl;");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence assignNetworkPorts(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _inputs = instance.getActor().getInputs();
      for(final Port port : _inputs) {
        final Connection connection = instance.getIncomingPortMap().get(port);
        _builder.newLineIfNotEmpty();
        {
          if (((connection != null) && (connection.getSourcePort() == null))) {
            _builder.append("top_");
            CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite);
            _builder.append("_V_dout <= ");
            CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite_1);
            _builder.append("_V_dout;");
            _builder.newLineIfNotEmpty();
            _builder.append("top_");
            CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite_2);
            _builder.append("_V_empty_n <= ");
            CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite_3);
            _builder.append("_V_empty_n;");
            _builder.newLineIfNotEmpty();
            CharSequence _castfifoNameWrite_4 = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite_4);
            _builder.append("_V_read <= top_");
            CharSequence _castfifoNameWrite_5 = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite_5);
            _builder.append("_V_read;\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
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
          for(final Connection connection_1 : _get) {
            {
              Port _targetPort = connection_1.getTargetPort();
              boolean _tripleEquals = (_targetPort == null);
              if (_tripleEquals) {
                CharSequence _castfifoNameRead = this.castfifoNameRead(connection_1);
                _builder.append(_castfifoNameRead);
                _builder.append("_V_din    <= top_");
                CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connection_1);
                _builder.append(_castfifoNameRead_1);
                _builder.append("_V_din;");
                _builder.newLineIfNotEmpty();
                _builder.append("top_");
                CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connection_1);
                _builder.append(_castfifoNameRead_2);
                _builder.append("_V_full_n <= ");
                CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connection_1);
                _builder.append(_castfifoNameRead_3);
                _builder.append("_V_full_n;");
                _builder.newLineIfNotEmpty();
                CharSequence _castfifoNameRead_4 = this.castfifoNameRead(connection_1);
                _builder.append(_castfifoNameRead_4);
                _builder.append("_V_write <= top_");
                CharSequence _castfifoNameRead_5 = this.castfifoNameRead(connection_1);
                _builder.append(_castfifoNameRead_5);
                _builder.append("_V_write;");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence mappingComponentSignal(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("call_");
    String _name = instance.getName();
    _builder.append(_name);
    _builder.append("_scheduler : component ");
    String _name_1 = instance.getName();
    _builder.append(_name_1);
    _builder.append("_scheduler");
    _builder.newLineIfNotEmpty();
    _builder.append("port map(");
    _builder.newLine();
    {
      Collection<List<Connection>> _values = instance.getOutgoingPortMap().values();
      for(final List<Connection> connList : _values) {
        _builder.append("\t");
        CharSequence _ramName = this.ramName(IterableExtensions.<Connection>head(connList));
        _builder.append(_ramName, "\t");
        _builder.append("_address0 => top_");
        CharSequence _ramName_1 = this.ramName(IterableExtensions.<Connection>head(connList));
        _builder.append(_ramName_1, "\t");
        _builder.append("_address1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _ramName_2 = this.ramName(IterableExtensions.<Connection>head(connList));
        _builder.append(_ramName_2, "\t");
        _builder.append("_ce0 => top_");
        CharSequence _ramName_3 = this.ramName(IterableExtensions.<Connection>head(connList));
        _builder.append(_ramName_3, "\t");
        _builder.append("_ce1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _ramName_4 = this.ramName(IterableExtensions.<Connection>head(connList));
        _builder.append(_ramName_4, "\t");
        _builder.append("_we0 => top_");
        CharSequence _ramName_5 = this.ramName(IterableExtensions.<Connection>head(connList));
        _builder.append(_ramName_5, "\t");
        _builder.append("_we1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _ramName_6 = this.ramName(IterableExtensions.<Connection>head(connList));
        _builder.append(_ramName_6, "\t");
        _builder.append("_d0 => top_");
        CharSequence _ramName_7 = this.ramName(IterableExtensions.<Connection>head(connList));
        _builder.append(_ramName_7, "\t");
        _builder.append("_d1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _wName = this.wName(IterableExtensions.<Connection>head(connList));
        _builder.append(_wName, "\t");
        _builder.append("_address0 => top_");
        CharSequence _wName_1 = this.wName(IterableExtensions.<Connection>head(connList));
        _builder.append(_wName_1, "\t");
        _builder.append("_address1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _wName_2 = this.wName(IterableExtensions.<Connection>head(connList));
        _builder.append(_wName_2, "\t");
        _builder.append("_ce0 => top_");
        CharSequence _wName_3 = this.wName(IterableExtensions.<Connection>head(connList));
        _builder.append(_wName_3, "\t");
        _builder.append("_ce1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _wName_4 = this.wName(IterableExtensions.<Connection>head(connList));
        _builder.append(_wName_4, "\t");
        _builder.append("_we0 => top_");
        CharSequence _wName_5 = this.wName(IterableExtensions.<Connection>head(connList));
        _builder.append(_wName_5, "\t");
        _builder.append("_we1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _wName_6 = this.wName(IterableExtensions.<Connection>head(connList));
        _builder.append(_wName_6, "\t");
        _builder.append("_d0 => top_");
        CharSequence _wName_7 = this.wName(IterableExtensions.<Connection>head(connList));
        _builder.append(_wName_7, "\t");
        _builder.append("_d1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _rName = this.rName(IterableExtensions.<Connection>head(connList));
        _builder.append(_rName, "\t");
        _builder.append("_address0 => top_");
        CharSequence _rName_1 = this.rName(IterableExtensions.<Connection>head(connList));
        _builder.append(_rName_1, "\t");
        _builder.append("_address1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _rName_2 = this.rName(IterableExtensions.<Connection>head(connList));
        _builder.append(_rName_2, "\t");
        _builder.append("_ce0 => top_");
        CharSequence _rName_3 = this.rName(IterableExtensions.<Connection>head(connList));
        _builder.append(_rName_3, "\t");
        _builder.append("_ce1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _rName_4 = this.rName(IterableExtensions.<Connection>head(connList));
        _builder.append(_rName_4, "\t");
        _builder.append("_q0 => top_");
        CharSequence _rName_5 = this.rName(IterableExtensions.<Connection>head(connList));
        _builder.append(_rName_5, "\t");
        _builder.append("_q1,\t\t\t\t\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Collection<Connection> _values_1 = instance.getIncomingPortMap().values();
      for(final Connection connList_1 : _values_1) {
        _builder.append("\t");
        CharSequence _ramName_8 = this.ramName(connList_1);
        _builder.append(_ramName_8, "\t");
        _builder.append("_address0 => top_");
        CharSequence _ramName_9 = this.ramName(connList_1);
        _builder.append(_ramName_9, "\t");
        _builder.append("_address0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _ramName_10 = this.ramName(connList_1);
        _builder.append(_ramName_10, "\t");
        _builder.append("_ce0 => top_");
        CharSequence _ramName_11 = this.ramName(connList_1);
        _builder.append(_ramName_11, "\t");
        _builder.append("_ce0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _ramName_12 = this.ramName(connList_1);
        _builder.append(_ramName_12, "\t");
        _builder.append("_q0 => top_");
        CharSequence _ramName_13 = this.ramName(connList_1);
        _builder.append(_ramName_13, "\t");
        _builder.append("_q0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _wName_8 = this.wName(connList_1);
        _builder.append(_wName_8, "\t");
        _builder.append("_address0 => top_");
        CharSequence _wName_9 = this.wName(connList_1);
        _builder.append(_wName_9, "\t");
        _builder.append("_address0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _wName_10 = this.wName(connList_1);
        _builder.append(_wName_10, "\t");
        _builder.append("_ce0 => top_");
        CharSequence _wName_11 = this.wName(connList_1);
        _builder.append(_wName_11, "\t");
        _builder.append("_ce0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _wName_12 = this.wName(connList_1);
        _builder.append(_wName_12, "\t");
        _builder.append("_q0 => top_");
        CharSequence _wName_13 = this.wName(connList_1);
        _builder.append(_wName_13, "\t");
        _builder.append("_q0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _rName_6 = this.rName(connList_1);
        _builder.append(_rName_6, "\t");
        _builder.append("_address0 => top_");
        CharSequence _rName_7 = this.rName(connList_1);
        _builder.append(_rName_7, "\t");
        _builder.append("_address0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _rName_8 = this.rName(connList_1);
        _builder.append(_rName_8, "\t");
        _builder.append("_ce0 => top_");
        CharSequence _rName_9 = this.rName(connList_1);
        _builder.append(_rName_9, "\t");
        _builder.append("_ce0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _rName_10 = this.rName(connList_1);
        _builder.append(_rName_10, "\t");
        _builder.append("_we0 => top_");
        CharSequence _rName_11 = this.rName(connList_1);
        _builder.append(_rName_11, "\t");
        _builder.append("_we0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _rName_12 = this.rName(connList_1);
        _builder.append(_rName_12, "\t");
        _builder.append("_d0 => top_");
        CharSequence _rName_13 = this.rName(connList_1);
        _builder.append(_rName_13, "\t");
        _builder.append("_d0,\t\t\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_start => top_ap_start,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_clk => top_ap_clk,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_rst => top_ap_rst,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_done => top_ap_done,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_idle => top_ap_idle,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_ready => top_ap_ready");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(");");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter = IterableExtensions.<Port>filter(instance.getActor().getOutputs(), _function);
      for(final Port port : _filter) {
        {
          List<Connection> _get = instance.getOutgoingPortMap().get(port);
          for(final Connection connection : _get) {
            {
              Port _targetPort = connection.getTargetPort();
              boolean _tripleEquals = (_targetPort == null);
              if (_tripleEquals) {
                _builder.append("call_cast_");
                String _name_2 = instance.getName();
                _builder.append(_name_2);
                _builder.append("_");
                String _name_3 = connection.getSourcePort().getName();
                _builder.append(_name_3);
                _builder.append("_read_scheduler : component cast_");
                String _name_4 = instance.getName();
                _builder.append(_name_4);
                _builder.append("_");
                String _name_5 = connection.getSourcePort().getName();
                _builder.append(_name_5);
                _builder.append("_read_scheduler");
                _builder.newLineIfNotEmpty();
                _builder.append("port map(");
                _builder.newLine();
                CharSequence _ramName_14 = this.ramName(connection);
                _builder.append(_ramName_14);
                _builder.append("_address0 => top_");
                CharSequence _ramName_15 = this.ramName(connection);
                _builder.append(_ramName_15);
                _builder.append("_address0, ");
                _builder.newLineIfNotEmpty();
                CharSequence _ramName_16 = this.ramName(connection);
                _builder.append(_ramName_16);
                _builder.append("_ce0 => top_");
                CharSequence _ramName_17 = this.ramName(connection);
                _builder.append(_ramName_17);
                _builder.append("_ce0,");
                _builder.newLineIfNotEmpty();
                CharSequence _ramName_18 = this.ramName(connection);
                _builder.append(_ramName_18);
                _builder.append("_q0  => top_");
                CharSequence _ramName_19 = this.ramName(connection);
                _builder.append(_ramName_19);
                _builder.append("_q0,");
                _builder.newLineIfNotEmpty();
                _builder.newLine();
                CharSequence _wName_14 = this.wName(connection);
                _builder.append(_wName_14);
                _builder.append("_address0 => top_");
                CharSequence _wName_15 = this.wName(connection);
                _builder.append(_wName_15);
                _builder.append("_address0,");
                _builder.newLineIfNotEmpty();
                CharSequence _wName_16 = this.wName(connection);
                _builder.append(_wName_16);
                _builder.append("_ce0 => top_");
                CharSequence _wName_17 = this.wName(connection);
                _builder.append(_wName_17);
                _builder.append("_ce0,");
                _builder.newLineIfNotEmpty();
                CharSequence _wName_18 = this.wName(connection);
                _builder.append(_wName_18);
                _builder.append("_q0  => top_");
                CharSequence _wName_19 = this.wName(connection);
                _builder.append(_wName_19);
                _builder.append("_q0,");
                _builder.newLineIfNotEmpty();
                _builder.newLine();
                CharSequence _rName_14 = this.rName(connection);
                _builder.append(_rName_14);
                _builder.append("_address0 => top_");
                CharSequence _rName_15 = this.rName(connection);
                _builder.append(_rName_15);
                _builder.append("_address0,");
                _builder.newLineIfNotEmpty();
                CharSequence _rName_16 = this.rName(connection);
                _builder.append(_rName_16);
                _builder.append("_ce0 => top_");
                CharSequence _rName_17 = this.rName(connection);
                _builder.append(_rName_17);
                _builder.append("_ce0,");
                _builder.newLineIfNotEmpty();
                CharSequence _rName_18 = this.rName(connection);
                _builder.append(_rName_18);
                _builder.append("_we0  => top_");
                CharSequence _rName_19 = this.rName(connection);
                _builder.append(_rName_19);
                _builder.append("_we0,");
                _builder.newLineIfNotEmpty();
                CharSequence _rName_20 = this.rName(connection);
                _builder.append(_rName_20);
                _builder.append("_d0  => top_");
                CharSequence _rName_21 = this.rName(connection);
                _builder.append(_rName_21);
                _builder.append("_d0,");
                _builder.newLineIfNotEmpty();
                _builder.newLine();
                CharSequence _castfifoNameRead = this.castfifoNameRead(connection);
                _builder.append(_castfifoNameRead);
                _builder.append("_V_din    => top_");
                CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connection);
                _builder.append(_castfifoNameRead_1);
                _builder.append("_V_din,");
                _builder.newLineIfNotEmpty();
                CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connection);
                _builder.append(_castfifoNameRead_2);
                _builder.append("_V_full_n => top_");
                CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connection);
                _builder.append(_castfifoNameRead_3);
                _builder.append("_V_full_n,");
                _builder.newLineIfNotEmpty();
                CharSequence _castfifoNameRead_4 = this.castfifoNameRead(connection);
                _builder.append(_castfifoNameRead_4);
                _builder.append("_V_write  => top_");
                CharSequence _castfifoNameRead_5 = this.castfifoNameRead(connection);
                _builder.append(_castfifoNameRead_5);
                _builder.append("_V_write,");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("ap_start => top_ap_start,");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("ap_clk => top_ap_clk,");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("ap_rst => top_ap_rst,");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("ap_done => top_ap_done,");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("ap_idle => top_ap_idle,");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("ap_ready => top_ap_ready);");
                _builder.newLine();
                _builder.newLine();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    {
      EList<Port> _inputs = instance.getActor().getInputs();
      for(final Port port_1 : _inputs) {
        final Connection connection_1 = instance.getIncomingPortMap().get(port_1);
        _builder.newLineIfNotEmpty();
        {
          if (((connection_1 != null) && (connection_1.getSourcePort() == null))) {
            _builder.append("call_cast_");
            String _name_6 = instance.getName();
            _builder.append(_name_6);
            _builder.append("_");
            String _name_7 = connection_1.getTargetPort().getName();
            _builder.append(_name_7);
            _builder.append("_write_scheduler :component cast_");
            String _name_8 = instance.getName();
            _builder.append(_name_8);
            _builder.append("_");
            String _name_9 = connection_1.getTargetPort().getName();
            _builder.append(_name_9);
            _builder.append("_write_scheduler");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("port map(");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _ramName_20 = this.ramName(connection_1);
            _builder.append(_ramName_20, "\t\t");
            _builder.append("_address0   => top_");
            CharSequence _ramName_21 = this.ramName(connection_1);
            _builder.append(_ramName_21, "\t\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _ramName_22 = this.ramName(connection_1);
            _builder.append(_ramName_22, "\t\t");
            _builder.append("_ce0 => top_");
            CharSequence _ramName_23 = this.ramName(connection_1);
            _builder.append(_ramName_23, "\t\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _ramName_24 = this.ramName(connection_1);
            _builder.append(_ramName_24, "\t\t");
            _builder.append("_we0 => top_");
            CharSequence _ramName_25 = this.ramName(connection_1);
            _builder.append(_ramName_25, "\t\t");
            _builder.append("_we1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _ramName_26 = this.ramName(connection_1);
            _builder.append(_ramName_26, "\t\t");
            _builder.append("_d0  => top_");
            CharSequence _ramName_27 = this.ramName(connection_1);
            _builder.append(_ramName_27, "\t\t");
            _builder.append("_d1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _wName_20 = this.wName(connection_1);
            _builder.append(_wName_20, "\t\t");
            _builder.append("_address0  => top_");
            CharSequence _wName_21 = this.wName(connection_1);
            _builder.append(_wName_21, "\t\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _wName_22 = this.wName(connection_1);
            _builder.append(_wName_22, "\t\t");
            _builder.append("_ce0 => top_");
            CharSequence _wName_23 = this.wName(connection_1);
            _builder.append(_wName_23, "\t\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _wName_24 = this.wName(connection_1);
            _builder.append(_wName_24, "\t\t");
            _builder.append("_we0  => top_");
            CharSequence _wName_25 = this.wName(connection_1);
            _builder.append(_wName_25, "\t\t");
            _builder.append("_we1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _wName_26 = this.wName(connection_1);
            _builder.append(_wName_26, "\t\t");
            _builder.append("_d0  => top_");
            CharSequence _wName_27 = this.wName(connection_1);
            _builder.append(_wName_27, "\t\t");
            _builder.append("_d1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _rName_22 = this.rName(connection_1);
            _builder.append(_rName_22, "\t\t");
            _builder.append("_address0   => top_");
            CharSequence _rName_23 = this.rName(connection_1);
            _builder.append(_rName_23, "\t\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _rName_24 = this.rName(connection_1);
            _builder.append(_rName_24, "\t\t");
            _builder.append("_ce0 => top_");
            CharSequence _rName_25 = this.rName(connection_1);
            _builder.append(_rName_25, "\t\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _rName_26 = this.rName(connection_1);
            _builder.append(_rName_26, "\t\t");
            _builder.append("_q0  => top_");
            CharSequence _rName_27 = this.rName(connection_1);
            _builder.append(_rName_27, "\t\t");
            _builder.append("_q1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite, "\t\t");
            _builder.append("_V_dout   => top_");
            CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_1, "\t\t");
            _builder.append("_V_dout,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_2, "\t\t");
            _builder.append("_V_empty_n => top_");
            CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_3, "\t\t");
            _builder.append("_V_empty_n,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameWrite_4 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_4, "\t\t");
            _builder.append("_V_read    => top_");
            CharSequence _castfifoNameWrite_5 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_5, "\t\t");
            _builder.append("_V_read,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("ap_start => top_ap_start,");
            _builder.newLine();
            _builder.append("\t\t\t\t");
            _builder.append("ap_clk => top_ap_clk,");
            _builder.newLine();
            _builder.append("\t\t\t\t");
            _builder.append("ap_rst => top_ap_rst,");
            _builder.newLine();
            _builder.append("\t\t\t\t");
            _builder.append("ap_done => top_ap_done,");
            _builder.newLine();
            _builder.append("\t\t\t\t");
            _builder.append("ap_idle => top_ap_idle,");
            _builder.newLine();
            _builder.append("\t\t\t\t");
            _builder.append("ap_ready => top_ap_ready);");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence mappingComponentFifoSignal(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    {
      Collection<Connection> _values = instance.getIncomingPortMap().values();
      for(final Connection connection : _values) {
        CharSequence _printFifoMapping = this.printFifoMapping(connection);
        _builder.append(_printFifoMapping);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter = IterableExtensions.<Port>filter(instance.getActor().getOutputs(), _function);
      for(final Port port : _filter) {
        {
          List<Connection> _get = instance.getOutgoingPortMap().get(port);
          for(final Connection connection_1 : _get) {
            {
              Port _targetPort = connection_1.getTargetPort();
              boolean _tripleEquals = (_targetPort == null);
              if (_tripleEquals) {
                CharSequence _printFifoMapping_1 = this.printFifoMapping(connection_1);
                _builder.append(_printFifoMapping_1);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence printFifoMapping(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    CharSequence _ramName = this.ramName(connection);
    _builder.append(_ramName);
    _builder.append(" : ram_tab");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("generic map (dwidth     => ");
    int _sizeInBits = this.fifoType(connection).getSizeInBits();
    _builder.append(_sizeInBits, "\t\t");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t       ");
    _builder.append("awidth     => ");
    int _closestLog_2 = this.closestLog_2(this.safeSize(connection));
    _builder.append(_closestLog_2, "\t\t       ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t       ");
    _builder.append("mem_size   => ");
    int _safeSize = this.safeSize(connection);
    _builder.append(_safeSize, "\t\t       ");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("port map (");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("clk => top_ap_clk,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("addr0 => top_");
    CharSequence _ramName_1 = this.ramName(connection);
    _builder.append(_ramName_1, "\t");
    _builder.append("_address0,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ce0 => top_");
    CharSequence _ramName_2 = this.ramName(connection);
    _builder.append(_ramName_2, "\t");
    _builder.append("_ce0,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("q0 => top_");
    CharSequence _ramName_3 = this.ramName(connection);
    _builder.append(_ramName_3, "\t");
    _builder.append("_q0,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("addr1 => top_");
    CharSequence _ramName_4 = this.ramName(connection);
    _builder.append(_ramName_4, "\t");
    _builder.append("_address1,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ce1 => top_");
    CharSequence _ramName_5 = this.ramName(connection);
    _builder.append(_ramName_5, "\t");
    _builder.append("_ce1,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("we1 => top_");
    CharSequence _ramName_6 = this.ramName(connection);
    _builder.append(_ramName_6, "\t");
    _builder.append("_we1,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("d1 => top_");
    CharSequence _ramName_7 = this.ramName(connection);
    _builder.append(_ramName_7, "\t");
    _builder.append("_d1");
    _builder.newLineIfNotEmpty();
    _builder.append(");");
    _builder.newLine();
    CharSequence _rName = this.rName(connection);
    _builder.append(_rName);
    _builder.append(" : ram_tab");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("generic map (dwidth     => 32,");
    _builder.newLine();
    _builder.append("\t\t       ");
    _builder.append("awidth     => 1,");
    _builder.newLine();
    _builder.append("\t\t       ");
    _builder.append("mem_size   => 1)");
    _builder.newLine();
    _builder.append("port map (");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("clk => top_ap_clk,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("addr0 => top_");
    CharSequence _rName_1 = this.rName(connection);
    _builder.append(_rName_1, "\t");
    _builder.append("_address1,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ce0 => top_");
    CharSequence _rName_2 = this.rName(connection);
    _builder.append(_rName_2, "\t");
    _builder.append("_ce1,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("q0 => top_");
    CharSequence _rName_3 = this.rName(connection);
    _builder.append(_rName_3, "\t");
    _builder.append("_q1,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("addr1 => top_");
    CharSequence _rName_4 = this.rName(connection);
    _builder.append(_rName_4, "\t");
    _builder.append("_address0,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ce1 => top_");
    CharSequence _rName_5 = this.rName(connection);
    _builder.append(_rName_5, "\t");
    _builder.append("_ce0,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("we1 => top_");
    CharSequence _rName_6 = this.rName(connection);
    _builder.append(_rName_6, "\t");
    _builder.append("_we0,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("d1 => top_");
    CharSequence _rName_7 = this.rName(connection);
    _builder.append(_rName_7, "\t");
    _builder.append("_d0");
    _builder.newLineIfNotEmpty();
    _builder.append(");");
    _builder.newLine();
    CharSequence _wName = this.wName(connection);
    _builder.append(_wName);
    _builder.append(" : ram_tab");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("generic map (dwidth     => 32,");
    _builder.newLine();
    _builder.append("\t\t       ");
    _builder.append("awidth     => 1,");
    _builder.newLine();
    _builder.append("\t\t       ");
    _builder.append("mem_size   => 1)");
    _builder.newLine();
    _builder.append("port map (");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("clk => top_ap_clk,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("addr0 => top_");
    CharSequence _wName_1 = this.wName(connection);
    _builder.append(_wName_1, "\t");
    _builder.append("_address0,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ce0 => top_");
    CharSequence _wName_2 = this.wName(connection);
    _builder.append(_wName_2, "\t");
    _builder.append("_ce0,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("q0 => top_");
    CharSequence _wName_3 = this.wName(connection);
    _builder.append(_wName_3, "\t");
    _builder.append("_q0,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("addr1 => top_");
    CharSequence _wName_4 = this.wName(connection);
    _builder.append(_wName_4, "\t");
    _builder.append("_address1,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ce1 => top_");
    CharSequence _wName_5 = this.wName(connection);
    _builder.append(_wName_5, "\t");
    _builder.append("_ce1,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("we1 => top_");
    CharSequence _wName_6 = this.wName(connection);
    _builder.append(_wName_6, "\t");
    _builder.append("_we1,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("d1 => top_");
    CharSequence _wName_7 = this.wName(connection);
    _builder.append(_wName_7, "\t");
    _builder.append("_d1");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append(");");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence assignFifoSignal(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Collection<List<Connection>> _values = instance.getOutgoingPortMap().values();
      for(final List<Connection> connList : _values) {
        {
          Vertex _target = IterableExtensions.<Connection>head(connList).getTarget();
          if ((_target instanceof Port)) {
            CharSequence _printOutputFifoSignalAssignHLS = this.printOutputFifoSignalAssignHLS(IterableExtensions.<Connection>head(connList));
            _builder.append(_printOutputFifoSignalAssignHLS);
            _builder.newLineIfNotEmpty();
            CharSequence _printInputRAMSignalAssignHLS = this.printInputRAMSignalAssignHLS(IterableExtensions.<Connection>head(connList));
            _builder.append(_printInputRAMSignalAssignHLS);
            _builder.newLineIfNotEmpty();
          }
        }
        CharSequence _printOutputRamSignalAssignHLS = this.printOutputRamSignalAssignHLS(IterableExtensions.<Connection>head(connList));
        _builder.append(_printOutputRamSignalAssignHLS);
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.newLine();
      }
    }
    {
      Collection<Connection> _values_1 = instance.getIncomingPortMap().values();
      for(final Connection connection : _values_1) {
        {
          Vertex _source = connection.getSource();
          if ((_source instanceof Port)) {
            CharSequence _printInputFifoSignalAssignHLS = this.printInputFifoSignalAssignHLS(connection);
            _builder.append(_printInputFifoSignalAssignHLS);
            _builder.newLineIfNotEmpty();
            CharSequence _printOutputRamSignalAssignHLS_1 = this.printOutputRamSignalAssignHLS(connection);
            _builder.append(_printOutputRamSignalAssignHLS_1);
            _builder.newLineIfNotEmpty();
          }
        }
        CharSequence _printInputRAMSignalAssignHLS_1 = this.printInputRAMSignalAssignHLS(connection);
        _builder.append(_printInputRAMSignalAssignHLS_1);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence printOutputRamSignalAssignHLS(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("signal top_");
    CharSequence _ramName = this.ramName(connection);
    _builder.append(_ramName);
    _builder.append("_address1    :  STD_LOGIC_VECTOR (");
    int _closestLog_2 = this.closestLog_2(this.safeSize(connection));
    _builder.append(_closestLog_2);
    _builder.append("-1 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _ramName_1 = this.ramName(connection);
    _builder.append(_ramName_1);
    _builder.append("_ce1 :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _ramName_2 = this.ramName(connection);
    _builder.append(_ramName_2);
    _builder.append("_we1  :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _ramName_3 = this.ramName(connection);
    _builder.append(_ramName_3);
    _builder.append("_d1  :   STD_LOGIC_VECTOR (");
    int _sizeInBits = this.fifoType(connection).getSizeInBits();
    int _minus = (_sizeInBits - 1);
    _builder.append(_minus);
    _builder.append("  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("signal top_");
    CharSequence _wName = this.wName(connection);
    _builder.append(_wName);
    _builder.append("_address1    :  STD_LOGIC_VECTOR (0 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _wName_1 = this.wName(connection);
    _builder.append(_wName_1);
    _builder.append("_ce1 :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _wName_2 = this.wName(connection);
    _builder.append(_wName_2);
    _builder.append("_we1  :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _wName_3 = this.wName(connection);
    _builder.append(_wName_3);
    _builder.append("_d1  :   STD_LOGIC_VECTOR (31  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("signal top_");
    CharSequence _rName = this.rName(connection);
    _builder.append(_rName);
    _builder.append("_address1    :  STD_LOGIC_VECTOR (0 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _rName_1 = this.rName(connection);
    _builder.append(_rName_1);
    _builder.append("_ce1 :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _rName_2 = this.rName(connection);
    _builder.append(_rName_2);
    _builder.append("_q1  :   STD_LOGIC_VECTOR (31  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printInputRAMSignalAssignHLS(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("signal top_");
    CharSequence _ramName = this.ramName(connection);
    _builder.append(_ramName);
    _builder.append("_address0    :  STD_LOGIC_VECTOR (");
    int _closestLog_2 = this.closestLog_2(this.safeSize(connection));
    _builder.append(_closestLog_2);
    _builder.append("-1 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _ramName_1 = this.ramName(connection);
    _builder.append(_ramName_1);
    _builder.append("_ce0 :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _ramName_2 = this.ramName(connection);
    _builder.append(_ramName_2);
    _builder.append("_q0  :   STD_LOGIC_VECTOR (");
    int _sizeInBits = this.fifoType(connection).getSizeInBits();
    int _minus = (_sizeInBits - 1);
    _builder.append(_minus);
    _builder.append("  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("signal top_");
    CharSequence _wName = this.wName(connection);
    _builder.append(_wName);
    _builder.append("_address0    :  STD_LOGIC_VECTOR (0 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _wName_1 = this.wName(connection);
    _builder.append(_wName_1);
    _builder.append("_ce0 :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _wName_2 = this.wName(connection);
    _builder.append(_wName_2);
    _builder.append("_q0  :   STD_LOGIC_VECTOR (31  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("signal top_");
    CharSequence _rName = this.rName(connection);
    _builder.append(_rName);
    _builder.append("_address0    :  STD_LOGIC_VECTOR (0 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _rName_1 = this.rName(connection);
    _builder.append(_rName_1);
    _builder.append("_ce0 :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _rName_2 = this.rName(connection);
    _builder.append(_rName_2);
    _builder.append("_we0  :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _rName_3 = this.rName(connection);
    _builder.append(_rName_3);
    _builder.append("_d0  :   STD_LOGIC_VECTOR (31  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printOutputRamAssignHLS(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _ramName = this.ramName(connection);
    _builder.append(_ramName);
    _builder.append("_address0    : OUT  STD_LOGIC_VECTOR (");
    int _closestLog_2 = this.closestLog_2(this.safeSize(connection));
    _builder.append(_closestLog_2);
    _builder.append("-1 downto 0);");
    _builder.newLineIfNotEmpty();
    CharSequence _ramName_1 = this.ramName(connection);
    _builder.append(_ramName_1);
    _builder.append("_ce0 : OUT STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    CharSequence _ramName_2 = this.ramName(connection);
    _builder.append(_ramName_2);
    _builder.append("_we0  : OUT STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    CharSequence _ramName_3 = this.ramName(connection);
    _builder.append(_ramName_3);
    _builder.append("_d0  : OUT STD_LOGIC_VECTOR (");
    int _sizeInBits = this.fifoType(connection).getSizeInBits();
    int _minus = (_sizeInBits - 1);
    _builder.append(_minus);
    _builder.append("  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _wName = this.wName(connection);
    _builder.append(_wName);
    _builder.append("_address0    :  OUT STD_LOGIC_VECTOR (0 downto 0);");
    _builder.newLineIfNotEmpty();
    CharSequence _wName_1 = this.wName(connection);
    _builder.append(_wName_1);
    _builder.append("_ce0 :  OUT STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    CharSequence _wName_2 = this.wName(connection);
    _builder.append(_wName_2);
    _builder.append("_we0  : OUT STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    CharSequence _wName_3 = this.wName(connection);
    _builder.append(_wName_3);
    _builder.append("_d0  :  OUT STD_LOGIC_VECTOR (31  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _rName = this.rName(connection);
    _builder.append(_rName);
    _builder.append("_address0    : OUT STD_LOGIC_VECTOR (0 downto 0);");
    _builder.newLineIfNotEmpty();
    CharSequence _rName_1 = this.rName(connection);
    _builder.append(_rName_1);
    _builder.append("_ce0 : OUT STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    CharSequence _rName_2 = this.rName(connection);
    _builder.append(_rName_2);
    _builder.append("_q0  : IN  STD_LOGIC_VECTOR (31  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printInputRAMAssignHLS(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _ramName = this.ramName(connection);
    _builder.append(_ramName);
    _builder.append("_address0    : OUT STD_LOGIC_VECTOR (");
    int _closestLog_2 = this.closestLog_2(this.safeSize(connection));
    _builder.append(_closestLog_2);
    _builder.append("-1 downto 0);");
    _builder.newLineIfNotEmpty();
    CharSequence _ramName_1 = this.ramName(connection);
    _builder.append(_ramName_1);
    _builder.append("_ce0 : OUT STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    CharSequence _ramName_2 = this.ramName(connection);
    _builder.append(_ramName_2);
    _builder.append("_q0  :  IN STD_LOGIC_VECTOR (");
    int _sizeInBits = this.fifoType(connection).getSizeInBits();
    int _minus = (_sizeInBits - 1);
    _builder.append(_minus);
    _builder.append("  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _wName = this.wName(connection);
    _builder.append(_wName);
    _builder.append("_address0    : OUT STD_LOGIC_VECTOR (0 downto 0);");
    _builder.newLineIfNotEmpty();
    CharSequence _wName_1 = this.wName(connection);
    _builder.append(_wName_1);
    _builder.append("_ce0 : OUT STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    CharSequence _wName_2 = this.wName(connection);
    _builder.append(_wName_2);
    _builder.append("_q0  : IN  STD_LOGIC_VECTOR (31  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _rName = this.rName(connection);
    _builder.append(_rName);
    _builder.append("_address0    :OUT  STD_LOGIC_VECTOR (0 downto 0);");
    _builder.newLineIfNotEmpty();
    CharSequence _rName_1 = this.rName(connection);
    _builder.append(_rName_1);
    _builder.append("_ce0 : OUT STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    CharSequence _rName_2 = this.rName(connection);
    _builder.append(_rName_2);
    _builder.append("_we0  : OUT STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    CharSequence _rName_3 = this.rName(connection);
    _builder.append(_rName_3);
    _builder.append("_d0  : OUT  STD_LOGIC_VECTOR (31  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printOutputFifoSignalAssignHLS(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("signal top_");
    CharSequence _castfifoNameRead = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead);
    _builder.append("_V_din    :  STD_LOGIC_VECTOR (");
    int _sizeInBits = this.fifoType(connection).getSizeInBits();
    int _minus = (_sizeInBits - 1);
    _builder.append(_minus);
    _builder.append("  downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_1);
    _builder.append("_V_full_n :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connection);
    _builder.append(_castfifoNameRead_2);
    _builder.append("_V_write  :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence printInputFifoSignalAssignHLS(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("signal top_");
    CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite);
    _builder.append("_V_dout   :  STD_LOGIC_VECTOR (");
    int _sizeInBits = this.fifoType(connection).getSizeInBits();
    int _minus = (_sizeInBits - 1);
    _builder.append(_minus);
    _builder.append(" downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_1);
    _builder.append("_V_empty_n :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal top_");
    CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(connection);
    _builder.append(_castfifoNameWrite_2);
    _builder.append("_V_read    :  STD_LOGIC;");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence declareComponentSignal(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("component ");
    String _name = instance.getName();
    _builder.append(_name);
    _builder.append("_scheduler IS");
    _builder.newLineIfNotEmpty();
    _builder.append("port (");
    _builder.newLine();
    {
      Collection<List<Connection>> _values = instance.getOutgoingPortMap().values();
      for(final List<Connection> connList : _values) {
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        CharSequence _printOutputRamAssignHLS = this.printOutputRamAssignHLS(IterableExtensions.<Connection>head(connList));
        _builder.append(_printOutputRamAssignHLS, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Collection<Connection> _values_1 = instance.getIncomingPortMap().values();
      for(final Connection connList_1 : _values_1) {
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        CharSequence _printInputRAMAssignHLS = this.printInputRAMAssignHLS(connList_1);
        _builder.append(_printInputRAMAssignHLS, "\t\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_clk : IN STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_rst : IN STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_start : IN STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_done : OUT STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_idle : OUT STD_LOGIC;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_ready : OUT STD_LOGIC);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("end component;");
    _builder.newLine();
    _builder.append("\t");
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
  
  public int closestLog_2(final Integer x) {
    int _xifexpression = (int) 0;
    if ((x == null)) {
      _xifexpression = this.closestLog_2(OrccLaunchConstants.DEFAULT_FIFO_SIZE);
    } else {
      _xifexpression = this.closestLog_2(x.intValue());
    }
    return _xifexpression;
  }
  
  public int closestLog_2(final int x) {
    int p = 1;
    int r = 0;
    while ((p < x)) {
      {
        p = (p * 2);
        r = (r + 1);
      }
    }
    return r;
  }
}
