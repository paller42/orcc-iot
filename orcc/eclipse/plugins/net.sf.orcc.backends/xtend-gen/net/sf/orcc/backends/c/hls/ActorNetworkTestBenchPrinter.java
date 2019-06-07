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

import java.util.Collection;
import java.util.List;
import net.sf.orcc.backends.c.InstancePrinter;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.util.OrccAttributes;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Top VHDL testbench for actor debug
 * 
 * @author Khaled Jerbi and Mariem Abid
 */
@SuppressWarnings("all")
public class ActorNetworkTestBenchPrinter extends InstancePrinter {
  public CharSequence actorNetworkFileContent() {
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
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        _builder.append("\t");
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        {
          if ((connection != null)) {
            _builder.append("\t");
            CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite, "\t");
            _builder.append("_V_dout   : IN STD_LOGIC_VECTOR (");
            int _sizeInBits = this.fifoTypeIn(connection).getSizeInBits();
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
      Iterable<Port> _filter = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function);
      for(final Port portout : _filter) {
        {
          List<Connection> _get = this.outgoingPortMap.get(portout);
          for(final Connection connection_1 : _get) {
            _builder.append("\t");
            CharSequence _castfifoNameRead = this.castfifoNameRead(connection_1);
            _builder.append(_castfifoNameRead, "\t");
            _builder.append("_V_din    : OUT STD_LOGIC_VECTOR (");
            int _sizeInBits_1 = this.fifoTypeOut(connection_1).getSizeInBits();
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
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _hasAttribute = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute) {
        {
          EList<Action> _actions = this.actor.getActions();
          for(final Action action : _actions) {
            _builder.append("\t");
            _builder.append("myStream_cast_tab_");
            String _name = action.getName();
            _builder.append(_name, "\t");
            _builder.append("_read_V_din    : OUT STD_LOGIC_VECTOR (7 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("myStream_cast_tab_");
            String _name_1 = action.getName();
            _builder.append(_name_1, "\t");
            _builder.append("_read_V_full_n : IN STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("myStream_cast_tab_");
            String _name_2 = action.getName();
            _builder.append(_name_2, "\t");
            _builder.append("_read_V_write  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ap_return : OUT STD_LOGIC_VECTOR (31 downto 0)");
    _builder.newLine();
    _builder.append("\t");
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
    _builder.append("\t");
    _builder.newLine();
    {
      EList<Port> _inputs_1 = this.actor.getInputs();
      for(final Port port_1 : _inputs_1) {
        _builder.append("\t");
        final Connection connection_2 = this.incomingPortMap.get(port_1);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_2 != null)) {
            _builder.append("\t");
            _builder.append("Signal ");
            CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(connection_2);
            _builder.append(_castfifoNameWrite_3, "\t");
            _builder.append("_V_dout   :  STD_LOGIC_VECTOR (");
            int _sizeInBits_2 = this.fifoTypeIn(connection_2).getSizeInBits();
            int _minus_2 = (_sizeInBits_2 - 1);
            _builder.append(_minus_2, "\t");
            _builder.append(" downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("Signal ");
            CharSequence _castfifoNameWrite_4 = this.castfifoNameWrite(connection_2);
            _builder.append(_castfifoNameWrite_4, "\t");
            _builder.append("_V_empty_n :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("Signal ");
            CharSequence _castfifoNameWrite_5 = this.castfifoNameWrite(connection_2);
            _builder.append(_castfifoNameWrite_5, "\t");
            _builder.append("_V_read    :  STD_LOGIC;\t\t\t\t");
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
      for(final Port portout_1 : _filter_1) {
        {
          List<Connection> _get_1 = this.outgoingPortMap.get(portout_1);
          for(final Connection connection_3 : _get_1) {
            _builder.append("\t");
            _builder.append("Signal ");
            CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connection_3);
            _builder.append(_castfifoNameRead_3, "\t");
            _builder.append("_V_din    :  STD_LOGIC_VECTOR (");
            int _sizeInBits_3 = this.fifoTypeOut(connection_3).getSizeInBits();
            int _minus_3 = (_sizeInBits_3 - 1);
            _builder.append(_minus_3, "\t");
            _builder.append(" downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("Signal ");
            CharSequence _castfifoNameRead_4 = this.castfifoNameRead(connection_3);
            _builder.append(_castfifoNameRead_4, "\t");
            _builder.append("_V_full_n :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("Signal ");
            CharSequence _castfifoNameRead_5 = this.castfifoNameRead(connection_3);
            _builder.append(_castfifoNameRead_5, "\t");
            _builder.append("_V_write  :  STD_LOGIC;\t\t\t\t");
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
            _builder.append("\t");
            _builder.append("Signal myStream_cast_tab_");
            String _name_3 = action_1.getName();
            _builder.append(_name_3, "\t");
            _builder.append("_read_V_din    :  STD_LOGIC_VECTOR (7 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("Signal myStream_cast_tab_");
            String _name_4 = action_1.getName();
            _builder.append(_name_4, "\t");
            _builder.append("_read_V_full_n : STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("Signal myStream_cast_tab_");
            String _name_5 = action_1.getName();
            _builder.append(_name_5, "\t");
            _builder.append("_read_V_write  :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
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
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("type severity_level is (note, warning, error, failure);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("type tb_type is (after_reset, read_file, CheckRead);");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- Input and Output files");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("signal tb_FSM_bits  : tb_type;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      Collection<List<Connection>> _values = this.outgoingPortMap.values();
      for(final List<Connection> connection_4 : _values) {
        _builder.append("\t");
        _builder.append("file sim_file_");
        _builder.append(this.entityName, "\t");
        _builder.append("_");
        String _name_6 = IterableExtensions.<Connection>head(connection_4).getSourcePort().getName();
        _builder.append(_name_6, "\t");
        _builder.append("  : text is \"");
        _builder.append(this.entityName, "\t");
        _builder.append("_");
        String _name_7 = IterableExtensions.<Connection>head(connection_4).getSourcePort().getName();
        _builder.append(_name_7, "\t");
        _builder.append(".txt\";");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Collection<Connection> _values_1 = this.incomingPortMap.values();
      for(final Connection connection_5 : _values_1) {
        _builder.append("\t");
        _builder.append("file sim_file_");
        _builder.append(this.entityName, "\t");
        _builder.append("_");
        String _name_8 = connection_5.getTargetPort().getName();
        _builder.append(_name_8, "\t");
        _builder.append("  : text is \"");
        _builder.append(this.entityName, "\t");
        _builder.append("_");
        String _name_9 = connection_5.getTargetPort().getName();
        _builder.append(_name_9, "\t");
        _builder.append(".txt\";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("begin");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("uut : TopDesign port map (");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_clk => ap_clk,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_rst => ap_rst,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_start => ap_start,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_done => ap_done,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_idle => ap_idle,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ap_ready =>ap_ready,");
    _builder.newLine();
    {
      EList<Port> _inputs_2 = this.actor.getInputs();
      for(final Port port_2 : _inputs_2) {
        _builder.append("\t\t");
        final Connection connection_6 = this.incomingPortMap.get(port_2);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_6 != null)) {
            _builder.append("\t\t");
            CharSequence _castfifoNameWrite_6 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_6, "\t\t");
            _builder.append("_V_dout   => ");
            CharSequence _castfifoNameWrite_7 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_7, "\t\t");
            _builder.append("_V_dout,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameWrite_8 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_8, "\t\t");
            _builder.append("_V_empty_n => ");
            CharSequence _castfifoNameWrite_9 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_9, "\t\t");
            _builder.append("_V_empty_n,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameWrite_10 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_10, "\t\t");
            _builder.append("_V_read    => ");
            CharSequence _castfifoNameWrite_11 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_11, "\t\t");
            _builder.append("_V_read,");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      final Function1<Port, Boolean> _function_2 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter_2 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_2);
      for(final Port portout_2 : _filter_2) {
        {
          List<Connection> _get_2 = this.outgoingPortMap.get(portout_2);
          for(final Connection connection_7 : _get_2) {
            _builder.append("\t\t");
            CharSequence _castfifoNameRead_6 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_6, "\t\t");
            _builder.append("_V_din    => ");
            CharSequence _castfifoNameRead_7 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_7, "\t\t");
            _builder.append("_V_din,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameRead_8 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_8, "\t\t");
            _builder.append("_V_full_n => ");
            CharSequence _castfifoNameRead_9 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_9, "\t\t");
            _builder.append("_V_full_n,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameRead_10 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_10, "\t\t");
            _builder.append("_V_write  => ");
            CharSequence _castfifoNameRead_11 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_11, "\t\t");
            _builder.append("_V_write,");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _hasAttribute_2 = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute_2) {
        {
          EList<Action> _actions_2 = this.actor.getActions();
          for(final Action action_2 : _actions_2) {
            _builder.append("\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_10 = action_2.getName();
            _builder.append(_name_10, "\t\t");
            _builder.append("_read_V_din    => myStream_cast_tab_");
            String _name_11 = action_2.getName();
            _builder.append(_name_11, "\t\t");
            _builder.append("_read_V_din,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_12 = action_2.getName();
            _builder.append(_name_12, "\t\t");
            _builder.append("_read_V_full_n => myStream_cast_tab_");
            String _name_13 = action_2.getName();
            _builder.append(_name_13, "\t\t");
            _builder.append("_read_V_full_n,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_14 = action_2.getName();
            _builder.append(_name_14, "\t\t");
            _builder.append("_read_V_write  => myStream_cast_tab_");
            String _name_15 = action_2.getName();
            _builder.append(_name_15, "\t\t");
            _builder.append("_read_V_write,");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.append("ap_return => ap_return");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(");");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("clockProcess : process");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("begin");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("wait for OFFSET;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("clock_LOOP : loop");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("ap_clk <= \'0\';");
    _builder.newLine();
    _builder.append("\t\t\t\t      ");
    _builder.append("wait for (PERIOD - (PERIOD * DUTY_CYCLE));");
    _builder.newLine();
    _builder.append("\t\t\t\t      ");
    _builder.append("ap_clk <= \'1\';");
    _builder.newLine();
    _builder.append("\t\t\t\t      ");
    _builder.append("wait for (PERIOD * DUTY_CYCLE);");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("end loop clock_LOOP;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("end process;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("resetProcess : process");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("begin                ");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("wait for OFFSET;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("-- reset state for 100 ns.");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("ap_rst <= \'1\';");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("wait for 100 ns;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("ap_rst <= \'0\';");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("wait;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("end process;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("WaveGen_Proc_In : process (ap_clk)");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("variable Input_bit   : integer range 2147483647 downto - 2147483648;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("variable line_number : line;");
    _builder.newLine();
    {
      EList<Port> _inputs_3 = this.actor.getInputs();
      for(final Port port_3 : _inputs_3) {
        _builder.append("\t\t\t");
        final Connection connection_8 = this.incomingPortMap.get(port_3);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_8 != null)) {
            _builder.append("\t\t\t");
            _builder.append("variable count");
            CharSequence _castfifoNameWrite_12 = this.castfifoNameWrite(connection_8);
            _builder.append(_castfifoNameWrite_12, "\t\t\t");
            _builder.append(": integer:= 0;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("begin");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("if rising_edge(ap_clk) then");
    _builder.newLine();
    {
      EList<Port> _inputs_4 = this.actor.getInputs();
      for(final Port port_4 : _inputs_4) {
        _builder.append("\t\t\t");
        final Connection connection_9 = this.incomingPortMap.get(port_4);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_9 != null)) {
            _builder.append("\t\t\t");
            CharSequence _printInputWaveGen = this.printInputWaveGen(connection_9, this.castfifoNameWrite(connection_9));
            _builder.append(_printInputWaveGen, "\t\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t\t\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("end process WaveGen_Proc_In;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("WaveGen_Proc_Out : process (ap_clk)");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("variable Input_bit   : integer range 2147483647 downto - 2147483648;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("variable line_number : line;");
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
      for(final Port port_5 : _filter_3) {
        {
          List<Connection> _get_3 = this.outgoingPortMap.get(port_5);
          for(final Connection connection_10 : _get_3) {
            _builder.append("\t\t\t");
            _builder.append("variable count");
            CharSequence _castfifoNameRead_12 = this.castfifoNameRead(connection_10);
            _builder.append(_castfifoNameRead_12, "\t\t\t");
            _builder.append(": integer:= 0;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _hasAttribute_3 = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute_3) {
        {
          EList<Action> _actions_3 = this.actor.getActions();
          for(final Action action_3 : _actions_3) {
            _builder.append("\t\t\t");
            _builder.append("variable count_myStream_cast_tab_");
            String _name_16 = action_3.getName();
            _builder.append(_name_16, "\t\t\t");
            _builder.append("_read: integer:= 0;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("begin");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("if (rising_edge(ap_clk)) then");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    {
      final Function1<Port, Boolean> _function_4 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter_4 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_4);
      for(final Port port_6 : _filter_4) {
        {
          List<Connection> _get_4 = this.outgoingPortMap.get(port_6);
          for(final Connection connection_11 : _get_4) {
            _builder.append("\t\t\t\t");
            CharSequence _printOutputWaveGen = this.printOutputWaveGen(connection_11, this.castfifoNameRead(connection_11));
            _builder.append(_printOutputWaveGen, "\t\t\t\t");
            _builder.append("\t\t\t\t\t\t\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _hasAttribute_4 = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute_4) {
        {
          EList<Action> _actions_4 = this.actor.getActions();
          for(final Action action_4 : _actions_4) {
            _builder.append("\t\t\t\t");
            _builder.append("if ( myStream_cast_tab_");
            String _name_17 = action_4.getName();
            _builder.append(_name_17, "\t\t\t\t");
            _builder.append("_read_V_write = \'1\') then");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t\t");
            _builder.append("count_myStream_cast_tab_");
            String _name_18 = action_4.getName();
            _builder.append(_name_18, "\t\t\t\t");
            _builder.append("_read := count_myStream_cast_tab_");
            String _name_19 = action_4.getName();
            _builder.append(_name_19, "\t\t\t\t");
            _builder.append("_read + 1;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t\t");
            _builder.append("report \"Number of myStream_cast_tab_");
            String _name_20 = action_4.getName();
            _builder.append(_name_20, "\t\t\t\t");
            _builder.append("_read = \" & integer\'image(count_myStream_cast_tab_");
            String _name_21 = action_4.getName();
            _builder.append(_name_21, "\t\t\t\t");
            _builder.append("_read);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t\t");
            _builder.append("end if;");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("end process WaveGen_Proc_Out;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.newLine();
    {
      final Function1<Port, Boolean> _function_5 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter_5 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_5);
      for(final Port portout_3 : _filter_5) {
        {
          List<Connection> _get_5 = this.outgoingPortMap.get(portout_3);
          for(final Connection connection_12 : _get_5) {
            _builder.append("\t\t");
            CharSequence _castfifoNameRead_13 = this.castfifoNameRead(connection_12);
            _builder.append(_castfifoNameRead_13, "\t\t");
            _builder.append("_V_full_n <= \'1\';\t\t\t\t\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _hasAttribute_5 = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute_5) {
        {
          EList<Action> _actions_5 = this.actor.getActions();
          for(final Action action_5 : _actions_5) {
            _builder.append("\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_22 = action_5.getName();
            _builder.append(_name_22, "\t\t");
            _builder.append("_read_V_full_n <= \'1\';");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("END;");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printInputWaveGen(final Connection connection, final CharSequence Fname) {
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
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (count = 15) then");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("tb_FSM_bits <= read_file;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("count           <= 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("when read_file =>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (not endfile (sim_file_");
    _builder.append(this.entityName, "\t");
    _builder.append("_");
    String _name = connection.getTargetPort().getName();
    _builder.append(_name, "\t");
    _builder.append(")) then");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("readline(sim_file_");
    _builder.append(this.entityName, "\t\t");
    _builder.append("_");
    String _name_1 = connection.getTargetPort().getName();
    _builder.append(_name_1, "\t\t");
    _builder.append(", line_number);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("if (line_number\'length > 0 and line_number(1) /= \'/\') then");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("read(line_number, input_bit);");
    _builder.newLine();
    {
      boolean _isInt = this.fifoTypeIn(connection).isInt();
      if (_isInt) {
        _builder.append("\t\t\t");
        _builder.append(Fname, "\t\t\t");
        _builder.append("_V_dout  <= std_logic_vector(to_signed(input_bit, ");
        int _sizeInBits = this.fifoTypeIn(connection).getSizeInBits();
        _builder.append(_sizeInBits, "\t\t\t");
        _builder.append("));");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isUint = this.fifoTypeIn(connection).isUint();
      if (_isUint) {
        _builder.append("\t\t\t");
        _builder.append(Fname, "\t\t\t");
        _builder.append("_V_dout  <= std_logic_vector(to_unsigned(input_bit, ");
        int _sizeInBits_1 = this.fifoTypeIn(connection).getSizeInBits();
        _builder.append(_sizeInBits_1, "\t\t\t");
        _builder.append("));");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isBool = this.fifoTypeIn(connection).isBool();
      if (_isBool) {
        _builder.append("\t\t\t");
        _builder.append("if (input_bit = 1) then ");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append(Fname, "\t\t\t");
        _builder.append("_V_dout  <= \"1\";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t");
        _builder.append("else");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append(Fname, "\t\t\t");
        _builder.append("_V_dout  <= \"0\";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t");
        _builder.append("end if;");
        _builder.newLine();
      }
    }
    _builder.append("\t\t\t");
    _builder.append(Fname, "\t\t\t");
    _builder.append("_V_empty_n <= \'1\';");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("ap_start <= \'1\';    ");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("tb_FSM_bits <= CheckRead;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("when CheckRead =>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (not endfile (sim_file_");
    _builder.append(this.entityName, "\t");
    _builder.append("_");
    String _name_2 = connection.getTargetPort().getName();
    _builder.append(_name_2, "\t");
    _builder.append(")) and ");
    _builder.append(Fname, "\t");
    _builder.append("_V_read = \'1\' then");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("count");
    _builder.append(Fname, "\t");
    _builder.append(" := count");
    _builder.append(Fname, "\t");
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("report \"Number of inputs");
    _builder.append(Fname, "\t");
    _builder.append(" = \" & integer\'image(count");
    _builder.append(Fname, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append(Fname, "\t");
    _builder.append("_V_empty_n <= \'0\';");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("readline(sim_file_");
    _builder.append(this.entityName, "\t");
    _builder.append("_");
    String _name_3 = connection.getTargetPort().getName();
    _builder.append(_name_3, "\t");
    _builder.append(", line_number);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("if (line_number\'length > 0 and line_number(1) /= \'/\') then");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("read(line_number, input_bit);");
    _builder.newLine();
    {
      boolean _isInt_1 = this.fifoTypeIn(connection).isInt();
      if (_isInt_1) {
        _builder.append("\t\t\t\t");
        _builder.append(Fname, "\t\t\t\t");
        _builder.append("_V_dout  <= std_logic_vector(to_signed(input_bit, ");
        int _sizeInBits_2 = this.fifoTypeIn(connection).getSizeInBits();
        _builder.append(_sizeInBits_2, "\t\t\t\t");
        _builder.append("));");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isUint_1 = this.fifoTypeIn(connection).isUint();
      if (_isUint_1) {
        _builder.append("\t\t\t\t");
        _builder.append(Fname, "\t\t\t\t");
        _builder.append("_V_dout  <= std_logic_vector(to_unsigned(input_bit, ");
        int _sizeInBits_3 = this.fifoTypeIn(connection).getSizeInBits();
        _builder.append(_sizeInBits_3, "\t\t\t\t");
        _builder.append("));");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t\t\t");
    _builder.append(Fname, "\t\t\t\t");
    _builder.append("_V_empty_n <= \'1\';");
    _builder.newLineIfNotEmpty();
    {
      boolean _isBool_1 = this.fifoTypeIn(connection).isBool();
      if (_isBool_1) {
        _builder.append("\t\t\t\t");
        _builder.append("if (input_bit = 1) then ");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append(Fname, "\t\t\t\t");
        _builder.append("_V_dout  <= \"1\";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t\t");
        _builder.append("else");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append(Fname, "\t\t\t\t");
        _builder.append("_V_dout  <= \"0\";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t\t");
        _builder.append("end if;");
        _builder.newLine();
      }
    }
    _builder.append("\t\t\t\t");
    _builder.append("ap_start <= \'1\';      ");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("elsif (endfile (sim_file_");
    _builder.append(this.entityName, "\t");
    _builder.append("_");
    String _name_4 = connection.getTargetPort().getName();
    _builder.append(_name_4, "\t");
    _builder.append(")) then");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("ap_start <= \'1\';");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(Fname, "\t\t");
    _builder.append("_V_empty_n <= \'0\';");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("when others => null;");
    _builder.newLine();
    _builder.append("end case;");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printOutputWaveGen(final Connection connection, final CharSequence Fname) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("if (not endfile (sim_file_");
    _builder.append(this.entityName, "\t");
    _builder.append("_");
    String _name = connection.getSourcePort().getName();
    _builder.append(_name, "\t");
    _builder.append(") and ");
    _builder.append(Fname, "\t");
    _builder.append("_V_write = \'1\') then");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("count");
    _builder.append(Fname, "\t");
    _builder.append(" := count");
    _builder.append(Fname, "\t");
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("report \"Number of outputs");
    _builder.append(Fname, "\t");
    _builder.append(" = \" & integer\'image(count");
    _builder.append(Fname, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("readline(sim_file_");
    _builder.append(this.entityName, "\t");
    _builder.append("_");
    String _name_1 = connection.getSourcePort().getName();
    _builder.append(_name_1, "\t");
    _builder.append(", line_number);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("if (line_number\'length > 0 and line_number(1) /= \'/\') then");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("read(line_number, input_bit);");
    _builder.newLine();
    {
      boolean _isInt = this.fifoTypeOut(connection).isInt();
      if (_isInt) {
        _builder.append("\t\t");
        _builder.append("assert (");
        _builder.append(Fname, "\t\t");
        _builder.append("_V_din  = std_logic_vector(to_signed(input_bit, ");
        int _sizeInBits = this.fifoTypeOut(connection).getSizeInBits();
        _builder.append(_sizeInBits, "\t\t");
        _builder.append(")))");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("-- report \"on ");
        _builder.append(Fname, "\t\t");
        _builder.append(" incorrectly value computed : \" & to_string(to_integer(to_signed(");
        _builder.append(Fname, "\t\t");
        _builder.append("_V_din))) & \" instead of :\" & to_string(input_bit)");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("report \"on port ");
        _builder.append(Fname, "\t\t");
        _builder.append(" incorrectly value computed : \" & str(to_integer(signed(");
        _builder.append(Fname, "\t\t");
        _builder.append("_V_din))) & \" instead of :\" & str(input_bit)");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("severity error;");
        _builder.newLine();
      }
    }
    {
      boolean _isUint = this.fifoTypeOut(connection).isUint();
      if (_isUint) {
        _builder.append("\t\t");
        _builder.append("assert (");
        _builder.append(Fname, "\t\t");
        _builder.append("_V_din  = std_logic_vector(to_unsigned(input_bit, ");
        int _sizeInBits_1 = this.fifoTypeOut(connection).getSizeInBits();
        _builder.append(_sizeInBits_1, "\t\t");
        _builder.append(")))");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("-- report \"on ");
        _builder.append(Fname, "\t\t");
        _builder.append(" incorrectly value computed : \" & to_string(to_integer(to_unsigned(");
        _builder.append(Fname, "\t\t");
        _builder.append("_V_din))) & \" instead of :\" & to_string(input_bit)");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("report \"on port ");
        _builder.append(Fname, "\t\t");
        _builder.append(" incorrectly value computed : \" & str(to_integer(unsigned(");
        _builder.append(Fname, "\t\t");
        _builder.append("_V_din))) & \" instead of :\" & str(input_bit)");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("severity error;");
        _builder.newLine();
      }
    }
    {
      boolean _isBool = this.fifoTypeOut(connection).isBool();
      if (_isBool) {
        _builder.append("\t\t");
        _builder.append("if (input_bit = 1) then");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("assert (");
        _builder.append(Fname, "\t\t\t");
        _builder.append("_V_din  = \"1\")");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("report \"on port ");
        _builder.append(Fname, "\t\t\t");
        _builder.append(" 0 instead of 1\"");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("severity error;");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("else");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("assert (");
        _builder.append(Fname, "\t\t\t");
        _builder.append("_V_din  = \"0\")");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("report \"on port ");
        _builder.append(Fname, "\t\t\t");
        _builder.append(" 1 instead of 0\"");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("severity error;");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("end if;");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t\t \t");
    _builder.append("--assert (");
    _builder.append(Fname, "\t\t \t");
    _builder.append("_V_din /= std_logic_vector(to_signed(input_bit, ");
    int _sizeInBits_2 = this.fifoTypeOut(connection).getSizeInBits();
    _builder.append(_sizeInBits_2, "\t\t \t");
    _builder.append(")))");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t \t");
    _builder.append("--report \"on port ");
    _builder.append(Fname, "\t\t \t");
    _builder.append(" correct value computed : \" & str(to_integer(signed(");
    _builder.append(Fname, "\t\t \t");
    _builder.append("_V_din))) & \" equals :\" & str(input_bit)");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t \t");
    _builder.append("--severity note;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("end if;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("end if;");
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
