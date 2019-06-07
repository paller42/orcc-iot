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
 * Top VHDL for actor debug
 * 
 * @author Khaled Jerbi and Mariem Abid
 */
@SuppressWarnings("all")
public class ActorTopVhdlPrinter extends InstancePrinter {
  public CharSequence ActorTopFileContent() {
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
    _builder.append("ap_clk : IN STD_LOGIC;");
    _builder.newLine();
    _builder.append("ap_rst : IN STD_LOGIC;");
    _builder.newLine();
    _builder.append("ap_start : IN STD_LOGIC;");
    _builder.newLine();
    _builder.append("ap_done : OUT STD_LOGIC;");
    _builder.newLine();
    _builder.append("ap_idle : OUT STD_LOGIC;");
    _builder.newLine();
    _builder.append("ap_ready : OUT STD_LOGIC;");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        {
          if ((connection != null)) {
            CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite);
            _builder.append("_V_dout   : IN STD_LOGIC_VECTOR (");
            int _sizeInBits = this.fifoType(connection).getSizeInBits();
            int _minus = (_sizeInBits - 1);
            _builder.append(_minus);
            _builder.append(" downto 0);");
            _builder.newLineIfNotEmpty();
            CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite_1);
            _builder.append("_V_empty_n : IN STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(connection);
            _builder.append(_castfifoNameWrite_2);
            _builder.append("_V_read    : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
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
      for(final Port portout : _filter) {
        {
          List<Connection> _get = this.outgoingPortMap.get(portout);
          for(final Connection connection_1 : _get) {
            {
              if ((connection_1 != null)) {
                CharSequence _castfifoNameRead = this.castfifoNameRead(connection_1);
                _builder.append(_castfifoNameRead);
                _builder.append("_V_din    : OUT STD_LOGIC_VECTOR (");
                int _sizeInBits_1 = this.fifoType(connection_1).getSizeInBits();
                int _minus_1 = (_sizeInBits_1 - 1);
                _builder.append(_minus_1);
                _builder.append(" downto 0);");
                _builder.newLineIfNotEmpty();
                CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connection_1);
                _builder.append(_castfifoNameRead_1);
                _builder.append("_V_full_n : IN STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connection_1);
                _builder.append(_castfifoNameRead_2);
                _builder.append("_V_write  : OUT STD_LOGIC;");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.newLine();
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
            _builder.append("myStream_cast_tab_");
            String _name = action.getName();
            _builder.append(_name);
            _builder.append("_read_V_din    : OUT STD_LOGIC_VECTOR (7 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("myStream_cast_tab_");
            String _name_1 = action.getName();
            _builder.append(_name_1);
            _builder.append("_read_V_full_n : IN STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("myStream_cast_tab_");
            String _name_2 = action.getName();
            _builder.append(_name_2);
            _builder.append("_read_V_write  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.newLine();
    _builder.append("ap_return : OUT STD_LOGIC_VECTOR (31 downto 0));");
    _builder.newLine();
    _builder.append("end entity TopDesign;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Architecture Declaration");
    _builder.newLine();
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.newLine();
    _builder.append("architecture rtl of TopDesign is");
    _builder.newLine();
    _builder.newLine();
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Signal Instantiation");
    _builder.newLine();
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.newLine();
    _builder.append("signal top_ap_clk :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("signal top_ap_rst :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("signal top_ap_start :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("signal top_ap_done :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("signal top_ap_idle :  STD_LOGIC;");
    _builder.newLine();
    _builder.append("signal top_ap_ready :  STD_LOGIC;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("-- FIFO Instantiation");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Port> _inputs_1 = this.actor.getInputs();
      for(final Port port_1 : _inputs_1) {
        final Connection connection_2 = this.incomingPortMap.get(port_1);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_2 != null)) {
            _builder.append("signal top_");
            CharSequence _ramName = this.ramName(connection_2);
            _builder.append(_ramName);
            _builder.append("_address0    :  STD_LOGIC_VECTOR (");
            int _closestLog_2 = this.closestLog_2(this.safeSize(connection_2));
            _builder.append(_closestLog_2);
            _builder.append("-1 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _ramName_1 = this.ramName(connection_2);
            _builder.append(_ramName_1);
            _builder.append("_ce0 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _ramName_2 = this.ramName(connection_2);
            _builder.append(_ramName_2);
            _builder.append("_q0  :   STD_LOGIC_VECTOR (");
            int _sizeInBits_2 = this.fifoType(connection_2).getSizeInBits();
            int _minus_2 = (_sizeInBits_2 - 1);
            _builder.append(_minus_2);
            _builder.append("  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_");
            CharSequence _wName = this.wName(connection_2);
            _builder.append(_wName);
            _builder.append("_address0    :  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _wName_1 = this.wName(connection_2);
            _builder.append(_wName_1);
            _builder.append("_ce0 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _wName_2 = this.wName(connection_2);
            _builder.append(_wName_2);
            _builder.append("_q0  :   STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_");
            CharSequence _rName = this.rName(connection_2);
            _builder.append(_rName);
            _builder.append("_address0    :  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _rName_1 = this.rName(connection_2);
            _builder.append(_rName_1);
            _builder.append("_ce0 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _rName_2 = this.rName(connection_2);
            _builder.append(_rName_2);
            _builder.append("_we0  :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _rName_3 = this.rName(connection_2);
            _builder.append(_rName_3);
            _builder.append("_d0  :   STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(connection_2);
            _builder.append(_castfifoNameWrite_3);
            _builder.append("_V_dout   :  STD_LOGIC_VECTOR (");
            int _sizeInBits_3 = this.fifoType(connection_2).getSizeInBits();
            int _minus_3 = (_sizeInBits_3 - 1);
            _builder.append(_minus_3);
            _builder.append("  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _castfifoNameWrite_4 = this.castfifoNameWrite(connection_2);
            _builder.append(_castfifoNameWrite_4);
            _builder.append("_V_empty_n :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _castfifoNameWrite_5 = this.castfifoNameWrite(connection_2);
            _builder.append(_castfifoNameWrite_5);
            _builder.append("_V_read    :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_");
            CharSequence _ramName_3 = this.ramName(connection_2);
            _builder.append(_ramName_3);
            _builder.append("_address1    :  STD_LOGIC_VECTOR (");
            int _closestLog_2_1 = this.closestLog_2(this.safeSize(connection_2));
            _builder.append(_closestLog_2_1);
            _builder.append("-1 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _ramName_4 = this.ramName(connection_2);
            _builder.append(_ramName_4);
            _builder.append("_ce1 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _ramName_5 = this.ramName(connection_2);
            _builder.append(_ramName_5);
            _builder.append("_we1  :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _ramName_6 = this.ramName(connection_2);
            _builder.append(_ramName_6);
            _builder.append("_d1  :   STD_LOGIC_VECTOR (");
            int _sizeInBits_4 = this.fifoType(connection_2).getSizeInBits();
            int _minus_4 = (_sizeInBits_4 - 1);
            _builder.append(_minus_4);
            _builder.append("  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_");
            CharSequence _wName_3 = this.wName(connection_2);
            _builder.append(_wName_3);
            _builder.append("_address1    :  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _wName_4 = this.wName(connection_2);
            _builder.append(_wName_4);
            _builder.append("_ce1 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _wName_5 = this.wName(connection_2);
            _builder.append(_wName_5);
            _builder.append("_we1  :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _wName_6 = this.wName(connection_2);
            _builder.append(_wName_6);
            _builder.append("_d1  :   STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_");
            CharSequence _rName_4 = this.rName(connection_2);
            _builder.append(_rName_4);
            _builder.append("_address1    :  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _rName_5 = this.rName(connection_2);
            _builder.append(_rName_5);
            _builder.append("_ce1 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_");
            CharSequence _rName_6 = this.rName(connection_2);
            _builder.append(_rName_6);
            _builder.append("_q1  :   STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
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
            {
              if ((connection_3 != null)) {
                _builder.append("signal top_");
                CharSequence _ramName_7 = this.ramName(connection_3);
                _builder.append(_ramName_7);
                _builder.append("_address1    :  STD_LOGIC_VECTOR (");
                int _closestLog_2_2 = this.closestLog_2(this.safeSize(connection_3));
                _builder.append(_closestLog_2_2);
                _builder.append("-1 downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _ramName_8 = this.ramName(connection_3);
                _builder.append(_ramName_8);
                _builder.append("_ce1 :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _ramName_9 = this.ramName(connection_3);
                _builder.append(_ramName_9);
                _builder.append("_we1  :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _ramName_10 = this.ramName(connection_3);
                _builder.append(_ramName_10);
                _builder.append("_d1  :   STD_LOGIC_VECTOR (");
                int _sizeInBits_5 = this.fifoType(connection_3).getSizeInBits();
                int _minus_5 = (_sizeInBits_5 - 1);
                _builder.append(_minus_5);
                _builder.append("  downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.newLine();
                _builder.append("signal top_");
                CharSequence _wName_7 = this.wName(connection_3);
                _builder.append(_wName_7);
                _builder.append("_address1    :  STD_LOGIC_VECTOR (0 downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _wName_8 = this.wName(connection_3);
                _builder.append(_wName_8);
                _builder.append("_ce1 :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _wName_9 = this.wName(connection_3);
                _builder.append(_wName_9);
                _builder.append("_we1  :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _wName_10 = this.wName(connection_3);
                _builder.append(_wName_10);
                _builder.append("_d1  :   STD_LOGIC_VECTOR (31  downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.newLine();
                _builder.append("signal top_");
                CharSequence _rName_7 = this.rName(connection_3);
                _builder.append(_rName_7);
                _builder.append("_address1    :  STD_LOGIC_VECTOR (0 downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _rName_8 = this.rName(connection_3);
                _builder.append(_rName_8);
                _builder.append("_ce1 :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _rName_9 = this.rName(connection_3);
                _builder.append(_rName_9);
                _builder.append("_q1  :   STD_LOGIC_VECTOR (31  downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.newLine();
                _builder.append("signal top_");
                CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connection_3);
                _builder.append(_castfifoNameRead_3);
                _builder.append("_V_din    :  STD_LOGIC_VECTOR (");
                int _sizeInBits_6 = this.fifoType(connection_3).getSizeInBits();
                int _minus_6 = (_sizeInBits_6 - 1);
                _builder.append(_minus_6);
                _builder.append("  downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _castfifoNameRead_4 = this.castfifoNameRead(connection_3);
                _builder.append(_castfifoNameRead_4);
                _builder.append("_V_full_n :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _castfifoNameRead_5 = this.castfifoNameRead(connection_3);
                _builder.append(_castfifoNameRead_5);
                _builder.append("_V_write  :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.newLine();
                _builder.append("signal top_");
                CharSequence _ramName_11 = this.ramName(connection_3);
                _builder.append(_ramName_11);
                _builder.append("_address0    :  STD_LOGIC_VECTOR (");
                int _closestLog_2_3 = this.closestLog_2(this.safeSize(connection_3));
                _builder.append(_closestLog_2_3);
                _builder.append("-1 downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _ramName_12 = this.ramName(connection_3);
                _builder.append(_ramName_12);
                _builder.append("_ce0 :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _ramName_13 = this.ramName(connection_3);
                _builder.append(_ramName_13);
                _builder.append("_q0  :   STD_LOGIC_VECTOR (");
                int _sizeInBits_7 = this.fifoType(connection_3).getSizeInBits();
                int _minus_7 = (_sizeInBits_7 - 1);
                _builder.append(_minus_7);
                _builder.append("  downto 0);\t");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.newLine();
                _builder.append("signal top_");
                CharSequence _wName_11 = this.wName(connection_3);
                _builder.append(_wName_11);
                _builder.append("_address0    :  STD_LOGIC_VECTOR (0 downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _wName_12 = this.wName(connection_3);
                _builder.append(_wName_12);
                _builder.append("_ce0 :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _wName_13 = this.wName(connection_3);
                _builder.append(_wName_13);
                _builder.append("_q0  :   STD_LOGIC_VECTOR (31  downto 0);\t\t");
                _builder.newLineIfNotEmpty();
                _builder.newLine();
                _builder.append("signal top_");
                CharSequence _rName_10 = this.rName(connection_3);
                _builder.append(_rName_10);
                _builder.append("_address0    :  STD_LOGIC_VECTOR (0 downto 0);");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _rName_11 = this.rName(connection_3);
                _builder.append(_rName_11);
                _builder.append("_ce0 :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _rName_12 = this.rName(connection_3);
                _builder.append(_rName_12);
                _builder.append("_we0  :  STD_LOGIC;");
                _builder.newLineIfNotEmpty();
                _builder.append("signal top_");
                CharSequence _rName_13 = this.rName(connection_3);
                _builder.append(_rName_13);
                _builder.append("_d0  :   STD_LOGIC_VECTOR (31  downto 0);");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.newLine();
          }
        }
      }
    }
    _builder.newLine();
    {
      boolean _hasAttribute_1 = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute_1) {
        {
          EList<Action> _actions_1 = this.actor.getActions();
          for(final Action action_1 : _actions_1) {
            _builder.append("signal top_tab_");
            String _name_3 = action_1.getName();
            _builder.append(_name_3);
            _builder.append("_address1    :  STD_LOGIC_VECTOR (14-1 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_tab_");
            String _name_4 = action_1.getName();
            _builder.append(_name_4);
            _builder.append("_ce1 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_tab_");
            String _name_5 = action_1.getName();
            _builder.append(_name_5);
            _builder.append("_we1  :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_tab_");
            String _name_6 = action_1.getName();
            _builder.append(_name_6);
            _builder.append("_d1  :   STD_LOGIC_VECTOR (7  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_writeIdx_");
            String _name_7 = action_1.getName();
            _builder.append(_name_7);
            _builder.append("_address1    :  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_writeIdx_");
            String _name_8 = action_1.getName();
            _builder.append(_name_8);
            _builder.append("_ce1 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_writeIdx_");
            String _name_9 = action_1.getName();
            _builder.append(_name_9);
            _builder.append("_we1  :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_writeIdx_");
            String _name_10 = action_1.getName();
            _builder.append(_name_10);
            _builder.append("_d1  :   STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_readIdx_");
            String _name_11 = action_1.getName();
            _builder.append(_name_11);
            _builder.append("_address1    :  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_readIdx_");
            String _name_12 = action_1.getName();
            _builder.append(_name_12);
            _builder.append("_ce1 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_readIdx_");
            String _name_13 = action_1.getName();
            _builder.append(_name_13);
            _builder.append("_q1  :   STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_myStream_cast_tab_");
            String _name_14 = action_1.getName();
            _builder.append(_name_14);
            _builder.append("_read_V_din    :  STD_LOGIC_VECTOR (7  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_myStream_cast_tab_");
            String _name_15 = action_1.getName();
            _builder.append(_name_15);
            _builder.append("_read_V_full_n :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_myStream_cast_tab_");
            String _name_16 = action_1.getName();
            _builder.append(_name_16);
            _builder.append("_read_V_write  :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_tab_");
            String _name_17 = action_1.getName();
            _builder.append(_name_17);
            _builder.append("_address0    :  STD_LOGIC_VECTOR (14-1 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_tab_");
            String _name_18 = action_1.getName();
            _builder.append(_name_18);
            _builder.append("_ce0 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_tab_");
            String _name_19 = action_1.getName();
            _builder.append(_name_19);
            _builder.append("_q0  :   STD_LOGIC_VECTOR (7  downto 0);\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("signal top_writeIdx_");
            String _name_20 = action_1.getName();
            _builder.append(_name_20);
            _builder.append("_address0    :  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_writeIdx_");
            String _name_21 = action_1.getName();
            _builder.append(_name_21);
            _builder.append("_ce0 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_writeIdx_");
            String _name_22 = action_1.getName();
            _builder.append(_name_22);
            _builder.append("_q0  :   STD_LOGIC_VECTOR (31  downto 0);\t\t");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("signal top_readIdx_");
            String _name_23 = action_1.getName();
            _builder.append(_name_23);
            _builder.append("_address0    :  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_readIdx_");
            String _name_24 = action_1.getName();
            _builder.append(_name_24);
            _builder.append("_ce0 :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_readIdx_");
            String _name_25 = action_1.getName();
            _builder.append(_name_25);
            _builder.append("_we0  :  STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("signal top_readIdx_");
            String _name_26 = action_1.getName();
            _builder.append(_name_26);
            _builder.append("_d0  :   STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("-- ----------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Components of the Network");
    _builder.newLine();
    _builder.append("-- ---------------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    CharSequence _declareComponentSignal = this.declareComponentSignal();
    _builder.append(_declareComponentSignal, "\t");
    _builder.append("\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.append("component ram_tab is ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("generic(");
    _builder.newLine();
    _builder.append("         ");
    _builder.append("dwidth     : INTEGER; ");
    _builder.newLine();
    _builder.append("         ");
    _builder.append("awidth     : INTEGER;  ");
    _builder.newLine();
    _builder.append("         ");
    _builder.append("mem_size    : INTEGER ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("); ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("port (");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("addr0     : in std_logic_vector(awidth-1 downto 0); ");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("ce0       : in std_logic; ");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("q0        : out std_logic_vector(dwidth-1 downto 0);");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("addr1     : in std_logic_vector(awidth-1 downto 0); ");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("ce1       : in std_logic; ");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("d1        : in std_logic_vector(dwidth-1 downto 0); ");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("we1       : in std_logic; ");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("clk        : in std_logic ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("); ");
    _builder.newLine();
    _builder.append("end component; ");
    _builder.newLine();
    _builder.newLine();
    _builder.append("begin");
    _builder.newLine();
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
      for(final Port port_2 : _filter_2) {
        {
          Iterable<Connection> _filterNull = IterableExtensions.<Connection>filterNull(this.outgoingPortMap.get(port_2));
          for(final Connection connection_4 : _filterNull) {
            _builder.append("\t");
            CharSequence _printFifoMapping = this.printFifoMapping(connection_4);
            _builder.append(_printFifoMapping, "\t");
            _builder.append("\t\t\t\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      final Function1<Port, Boolean> _function_3 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          Connection _get = ActorTopVhdlPrinter.this.incomingPortMap.get(it);
          return Boolean.valueOf((_get != null));
        }
      };
      Iterable<Port> _filter_3 = IterableExtensions.<Port>filter(this.actor.getInputs(), _function_3);
      for(final Port port_3 : _filter_3) {
        _builder.append("\t");
        final Connection connection_5 = this.incomingPortMap.get(port_3);
        _builder.append("\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        CharSequence _printFifoMapping_1 = this.printFifoMapping(connection_5);
        _builder.append(_printFifoMapping_1, "\t\t");
        _builder.append("\t\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _hasAttribute_2 = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute_2) {
        {
          EList<Action> _actions_2 = this.actor.getActions();
          for(final Action action_2 : _actions_2) {
            _builder.append("\t");
            _builder.append("tab_");
            String _name_27 = action_2.getName();
            _builder.append(_name_27, "\t");
            _builder.append(" : ram_tab");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("generic map (");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("dwidth     => 8,");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("awidth     => 14,");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("mem_size   => 16384");
            _builder.newLine();
            _builder.append("\t");
            _builder.append(")");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("port map (");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("clk => top_ap_clk,");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("addr0 => top_tab_");
            String _name_28 = action_2.getName();
            _builder.append(_name_28, "\t\t");
            _builder.append("_address0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("ce0 => top_tab_");
            String _name_29 = action_2.getName();
            _builder.append(_name_29, "\t\t");
            _builder.append("_ce0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("q0 => top_tab_");
            String _name_30 = action_2.getName();
            _builder.append(_name_30, "\t\t");
            _builder.append("_q0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("addr1 => top_tab_");
            String _name_31 = action_2.getName();
            _builder.append(_name_31, "\t\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("ce1 => top_tab_");
            String _name_32 = action_2.getName();
            _builder.append(_name_32, "\t\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("we1 => top_tab_");
            String _name_33 = action_2.getName();
            _builder.append(_name_33, "\t\t");
            _builder.append("_we1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("d1 => top_tab_");
            String _name_34 = action_2.getName();
            _builder.append(_name_34, "\t\t");
            _builder.append("_d1");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(");");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("readIdx_");
            String _name_35 = action_2.getName();
            _builder.append(_name_35, "\t");
            _builder.append(" : ram_tab");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("generic map (");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("dwidth     => 32,");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("awidth     => 1,");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("mem_size   => 1");
            _builder.newLine();
            _builder.append("\t");
            _builder.append(")");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("port map (");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("clk => top_ap_clk,");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("addr0 => top_readIdx_");
            String _name_36 = action_2.getName();
            _builder.append(_name_36, "\t\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("ce0 => top_readIdx_");
            String _name_37 = action_2.getName();
            _builder.append(_name_37, "\t\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("q0 => top_readIdx_");
            String _name_38 = action_2.getName();
            _builder.append(_name_38, "\t\t");
            _builder.append("_q1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("addr1 => top_readIdx_");
            String _name_39 = action_2.getName();
            _builder.append(_name_39, "\t\t");
            _builder.append("_address0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("ce1 => top_readIdx_");
            String _name_40 = action_2.getName();
            _builder.append(_name_40, "\t\t");
            _builder.append("_ce0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("we1 => top_readIdx_");
            String _name_41 = action_2.getName();
            _builder.append(_name_41, "\t\t");
            _builder.append("_we0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("d1 => top_readIdx_");
            String _name_42 = action_2.getName();
            _builder.append(_name_42, "\t\t");
            _builder.append("_d0");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(");");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("writeIdx_");
            String _name_43 = action_2.getName();
            _builder.append(_name_43, "\t");
            _builder.append(" : ram_tab");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("generic map (");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("dwidth     => 32,");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("    ");
            _builder.append("awidth     => 1,");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("    ");
            _builder.append("mem_size   => 1");
            _builder.newLine();
            _builder.append("\t");
            _builder.append(")");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("port map (");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("clk => top_ap_clk,");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("addr0 => top_writeIdx_");
            String _name_44 = action_2.getName();
            _builder.append(_name_44, "\t\t");
            _builder.append("_address0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("ce0 => top_writeIdx_");
            String _name_45 = action_2.getName();
            _builder.append(_name_45, "\t\t");
            _builder.append("_ce0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("q0 => top_writeIdx_");
            String _name_46 = action_2.getName();
            _builder.append(_name_46, "\t\t");
            _builder.append("_q0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("addr1 => top_writeIdx_");
            String _name_47 = action_2.getName();
            _builder.append(_name_47, "\t\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("ce1 => top_writeIdx_");
            String _name_48 = action_2.getName();
            _builder.append(_name_48, "\t\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("we1 => top_writeIdx_");
            String _name_49 = action_2.getName();
            _builder.append(_name_49, "\t\t");
            _builder.append("_we1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("d1 => top_writeIdx_");
            String _name_50 = action_2.getName();
            _builder.append(_name_50, "\t\t");
            _builder.append("_d1");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(");");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _mappingComponentSignal = this.mappingComponentSignal();
    _builder.append(_mappingComponentSignal, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("-- Network Ports Instantiation ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.newLine();
    {
      EList<Port> _inputs_2 = this.actor.getInputs();
      for(final Port port_4 : _inputs_2) {
        _builder.append("\t");
        final Connection connection_6 = this.incomingPortMap.get(port_4);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_6 != null)) {
            _builder.append("\t");
            _builder.append("top_");
            CharSequence _castfifoNameWrite_6 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_6, "\t");
            _builder.append("_V_dout <= ");
            CharSequence _castfifoNameWrite_7 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_7, "\t");
            _builder.append("_V_dout;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("top_");
            CharSequence _castfifoNameWrite_8 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_8, "\t");
            _builder.append("_V_empty_n <= ");
            CharSequence _castfifoNameWrite_9 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_9, "\t");
            _builder.append("_V_empty_n;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _castfifoNameWrite_10 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_10, "\t");
            _builder.append("_V_read <= top_");
            CharSequence _castfifoNameWrite_11 = this.castfifoNameWrite(connection_6);
            _builder.append(_castfifoNameWrite_11, "\t");
            _builder.append("_V_read;\t\t\t\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
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
      for(final Port portout_2 : _filter_4) {
        {
          List<Connection> _get_2 = this.outgoingPortMap.get(portout_2);
          for(final Connection connection_7 : _get_2) {
            _builder.append("\t");
            CharSequence _castfifoNameRead_6 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_6, "\t");
            _builder.append("_V_din <= top_");
            CharSequence _castfifoNameRead_7 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_7, "\t");
            _builder.append("_V_din;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("top_");
            CharSequence _castfifoNameRead_8 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_8, "\t");
            _builder.append("_V_full_n <= ");
            CharSequence _castfifoNameRead_9 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_9, "\t");
            _builder.append("_V_full_n;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _castfifoNameRead_10 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_10, "\t");
            _builder.append("_V_write <= top_");
            CharSequence _castfifoNameRead_11 = this.castfifoNameRead(connection_7);
            _builder.append(_castfifoNameRead_11, "\t");
            _builder.append("_V_write;\t\t\t\t");
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
            _builder.append("\t");
            _builder.append("myStream_cast_tab_");
            String _name_51 = action_3.getName();
            _builder.append(_name_51, "\t");
            _builder.append("_read_V_din <= top_myStream_cast_tab_");
            String _name_52 = action_3.getName();
            _builder.append(_name_52, "\t");
            _builder.append("_read_V_din;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("top_myStream_cast_tab_");
            String _name_53 = action_3.getName();
            _builder.append(_name_53, "\t");
            _builder.append("_read_V_full_n <= myStream_cast_tab_");
            String _name_54 = action_3.getName();
            _builder.append(_name_54, "\t");
            _builder.append("_read_V_full_n;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("myStream_cast_tab_");
            String _name_55 = action_3.getName();
            _builder.append(_name_55, "\t");
            _builder.append("_read_V_write <= top_myStream_cast_tab_");
            String _name_56 = action_3.getName();
            _builder.append(_name_56, "\t");
            _builder.append("_read_V_write;\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("top_ap_start <= ap_start;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("top_ap_clk <= ap_clk;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("top_ap_rst <= ap_rst;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("end architecture rtl;");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence mappingComponentSignal() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("call_");
    _builder.append(this.entityName);
    _builder.append("_scheduler : component ");
    _builder.append(this.entityName);
    _builder.append("_scheduler");
    _builder.newLineIfNotEmpty();
    _builder.append("port map(");
    _builder.newLine();
    {
      Collection<List<Connection>> _values = this.outgoingPortMap.values();
      for(final List<Connection> connList : _values) {
        _builder.append("\t");
        _builder.newLine();
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
        _builder.append("_q1,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.newLine();
      }
    }
    {
      Collection<Connection> _values_1 = this.incomingPortMap.values();
      for(final Connection connList_1 : _values_1) {
        _builder.append("\t");
        _builder.newLine();
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
        _builder.append("_d0,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
      }
    }
    {
      boolean _hasAttribute = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute) {
        {
          EList<Action> _actions = this.actor.getActions();
          for(final Action action : _actions) {
            _builder.append("\t");
            _builder.append("tab_");
            String _name = action.getName();
            _builder.append(_name, "\t");
            _builder.append("_address0 => top_tab_");
            String _name_1 = action.getName();
            _builder.append(_name_1, "\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("tab_");
            String _name_2 = action.getName();
            _builder.append(_name_2, "\t");
            _builder.append("_ce0 => top_tab_");
            String _name_3 = action.getName();
            _builder.append(_name_3, "\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("tab_");
            String _name_4 = action.getName();
            _builder.append(_name_4, "\t");
            _builder.append("_we0 => top_tab_");
            String _name_5 = action.getName();
            _builder.append(_name_5, "\t");
            _builder.append("_we1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("tab_");
            String _name_6 = action.getName();
            _builder.append(_name_6, "\t");
            _builder.append("_d0 => top_tab_");
            String _name_7 = action.getName();
            _builder.append(_name_7, "\t");
            _builder.append("_d1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("writeIdx_");
            String _name_8 = action.getName();
            _builder.append(_name_8, "\t");
            _builder.append("_address0 => top_writeIdx_");
            String _name_9 = action.getName();
            _builder.append(_name_9, "\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("writeIdx_");
            String _name_10 = action.getName();
            _builder.append(_name_10, "\t");
            _builder.append("_ce0 => top_writeIdx_");
            String _name_11 = action.getName();
            _builder.append(_name_11, "\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("writeIdx_");
            String _name_12 = action.getName();
            _builder.append(_name_12, "\t");
            _builder.append("_we0 => top_writeIdx_");
            String _name_13 = action.getName();
            _builder.append(_name_13, "\t");
            _builder.append("_we1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("writeIdx_");
            String _name_14 = action.getName();
            _builder.append(_name_14, "\t");
            _builder.append("_d0 => top_writeIdx_");
            String _name_15 = action.getName();
            _builder.append(_name_15, "\t");
            _builder.append("_d1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("readIdx_");
            String _name_16 = action.getName();
            _builder.append(_name_16, "\t");
            _builder.append("_address0 => top_readIdx_");
            String _name_17 = action.getName();
            _builder.append(_name_17, "\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("readIdx_");
            String _name_18 = action.getName();
            _builder.append(_name_18, "\t");
            _builder.append("_ce0 => top_readIdx_");
            String _name_19 = action.getName();
            _builder.append(_name_19, "\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("readIdx_");
            String _name_20 = action.getName();
            _builder.append(_name_20, "\t");
            _builder.append("_q0 => top_readIdx_");
            String _name_21 = action.getName();
            _builder.append(_name_21, "\t");
            _builder.append("_q1,");
            _builder.newLineIfNotEmpty();
          }
        }
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
    _builder.append(");");
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
      for(final Port port : _filter) {
        {
          List<Connection> _get = this.outgoingPortMap.get(port);
          for(final Connection connection : _get) {
            _builder.newLine();
            _builder.append("call_cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name_22 = connection.getSourcePort().getName();
            _builder.append(_name_22);
            _builder.append("_read_scheduler : component cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name_23 = connection.getSourcePort().getName();
            _builder.append(_name_23);
            _builder.append("_read_scheduler");
            _builder.newLineIfNotEmpty();
            _builder.append("port map(");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _ramName_14 = this.ramName(connection);
            _builder.append(_ramName_14, "\t");
            _builder.append("_address0 => top_");
            CharSequence _ramName_15 = this.ramName(connection);
            _builder.append(_ramName_15, "\t");
            _builder.append("_address0, ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _ramName_16 = this.ramName(connection);
            _builder.append(_ramName_16, "\t");
            _builder.append("_ce0 => top_");
            CharSequence _ramName_17 = this.ramName(connection);
            _builder.append(_ramName_17, "\t");
            _builder.append("_ce0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _ramName_18 = this.ramName(connection);
            _builder.append(_ramName_18, "\t");
            _builder.append("_q0  => top_");
            CharSequence _ramName_19 = this.ramName(connection);
            _builder.append(_ramName_19, "\t");
            _builder.append("_q0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _wName_14 = this.wName(connection);
            _builder.append(_wName_14, "\t");
            _builder.append("_address0 => top_");
            CharSequence _wName_15 = this.wName(connection);
            _builder.append(_wName_15, "\t");
            _builder.append("_address0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _wName_16 = this.wName(connection);
            _builder.append(_wName_16, "\t");
            _builder.append("_ce0 => top_");
            CharSequence _wName_17 = this.wName(connection);
            _builder.append(_wName_17, "\t");
            _builder.append("_ce0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _wName_18 = this.wName(connection);
            _builder.append(_wName_18, "\t");
            _builder.append("_q0  => top_");
            CharSequence _wName_19 = this.wName(connection);
            _builder.append(_wName_19, "\t");
            _builder.append("_q0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _rName_14 = this.rName(connection);
            _builder.append(_rName_14, "\t");
            _builder.append("_address0 => top_");
            CharSequence _rName_15 = this.rName(connection);
            _builder.append(_rName_15, "\t");
            _builder.append("_address0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _rName_16 = this.rName(connection);
            _builder.append(_rName_16, "\t");
            _builder.append("_ce0 => top_");
            CharSequence _rName_17 = this.rName(connection);
            _builder.append(_rName_17, "\t");
            _builder.append("_ce0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _rName_18 = this.rName(connection);
            _builder.append(_rName_18, "\t");
            _builder.append("_we0  => top_");
            CharSequence _rName_19 = this.rName(connection);
            _builder.append(_rName_19, "\t");
            _builder.append("_we0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _rName_20 = this.rName(connection);
            _builder.append(_rName_20, "\t");
            _builder.append("_d0  => top_");
            CharSequence _rName_21 = this.rName(connection);
            _builder.append(_rName_21, "\t");
            _builder.append("_d0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _castfifoNameRead = this.castfifoNameRead(connection);
            _builder.append(_castfifoNameRead, "\t");
            _builder.append("_V_din    => top_");
            CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connection);
            _builder.append(_castfifoNameRead_1, "\t");
            _builder.append("_V_din,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connection);
            _builder.append(_castfifoNameRead_2, "\t");
            _builder.append("_V_full_n => top_");
            CharSequence _castfifoNameRead_3 = this.castfifoNameRead(connection);
            _builder.append(_castfifoNameRead_3, "\t");
            _builder.append("_V_full_n,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _castfifoNameRead_4 = this.castfifoNameRead(connection);
            _builder.append(_castfifoNameRead_4, "\t");
            _builder.append("_V_write  => top_");
            CharSequence _castfifoNameRead_5 = this.castfifoNameRead(connection);
            _builder.append(_castfifoNameRead_5, "\t");
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
            _builder.append("ap_ready => top_ap_ready");
            _builder.newLine();
            _builder.append(");");
            _builder.newLine();
            _builder.newLine();
          }
        }
      }
    }
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port_1 : _inputs) {
        final Connection connection_1 = this.incomingPortMap.get(port_1);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_1 != null)) {
            _builder.append("call_cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name_24 = connection_1.getTargetPort().getName();
            _builder.append(_name_24);
            _builder.append("_write_scheduler :component cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name_25 = connection_1.getTargetPort().getName();
            _builder.append(_name_25);
            _builder.append("_write_scheduler");
            _builder.newLineIfNotEmpty();
            _builder.append("port map(");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _ramName_20 = this.ramName(connection_1);
            _builder.append(_ramName_20, "\t");
            _builder.append("_address0   => top_");
            CharSequence _ramName_21 = this.ramName(connection_1);
            _builder.append(_ramName_21, "\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _ramName_22 = this.ramName(connection_1);
            _builder.append(_ramName_22, "\t");
            _builder.append("_ce0 => top_");
            CharSequence _ramName_23 = this.ramName(connection_1);
            _builder.append(_ramName_23, "\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _ramName_24 = this.ramName(connection_1);
            _builder.append(_ramName_24, "\t");
            _builder.append("_we0 => top_");
            CharSequence _ramName_25 = this.ramName(connection_1);
            _builder.append(_ramName_25, "\t");
            _builder.append("_we1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _ramName_26 = this.ramName(connection_1);
            _builder.append(_ramName_26, "\t");
            _builder.append("_d0  => top_");
            CharSequence _ramName_27 = this.ramName(connection_1);
            _builder.append(_ramName_27, "\t");
            _builder.append("_d1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _wName_20 = this.wName(connection_1);
            _builder.append(_wName_20, "\t");
            _builder.append("_address0  => top_");
            CharSequence _wName_21 = this.wName(connection_1);
            _builder.append(_wName_21, "\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _wName_22 = this.wName(connection_1);
            _builder.append(_wName_22, "\t");
            _builder.append("_ce0 => top_");
            CharSequence _wName_23 = this.wName(connection_1);
            _builder.append(_wName_23, "\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _wName_24 = this.wName(connection_1);
            _builder.append(_wName_24, "\t");
            _builder.append("_we0  => top_");
            CharSequence _wName_25 = this.wName(connection_1);
            _builder.append(_wName_25, "\t");
            _builder.append("_we1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _wName_26 = this.wName(connection_1);
            _builder.append(_wName_26, "\t");
            _builder.append("_d0  => top_");
            CharSequence _wName_27 = this.wName(connection_1);
            _builder.append(_wName_27, "\t");
            _builder.append("_d1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _rName_22 = this.rName(connection_1);
            _builder.append(_rName_22, "\t");
            _builder.append("_address0   => top_");
            CharSequence _rName_23 = this.rName(connection_1);
            _builder.append(_rName_23, "\t");
            _builder.append("_address1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _rName_24 = this.rName(connection_1);
            _builder.append(_rName_24, "\t");
            _builder.append("_ce0 => top_");
            CharSequence _rName_25 = this.rName(connection_1);
            _builder.append(_rName_25, "\t");
            _builder.append("_ce1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _rName_26 = this.rName(connection_1);
            _builder.append(_rName_26, "\t");
            _builder.append("_q0  => top_");
            CharSequence _rName_27 = this.rName(connection_1);
            _builder.append(_rName_27, "\t");
            _builder.append("_q1,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite, "\t");
            _builder.append("_V_dout   => top_");
            CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_1, "\t");
            _builder.append("_V_dout,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_2, "\t");
            _builder.append("_V_empty_n => top_");
            CharSequence _castfifoNameWrite_3 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_3, "\t");
            _builder.append("_V_empty_n,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _castfifoNameWrite_4 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_4, "\t");
            _builder.append("_V_read    => top_");
            CharSequence _castfifoNameWrite_5 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_5, "\t");
            _builder.append("_V_read,");
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
            _builder.append("ap_ready => top_ap_ready");
            _builder.newLine();
            _builder.append(");");
            _builder.newLine();
          }
        }
      }
    }
    _builder.newLine();
    {
      boolean _hasAttribute_1 = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute_1) {
        {
          EList<Action> _actions_1 = this.actor.getActions();
          for(final Action action_1 : _actions_1) {
            _builder.append("call_cast_");
            _builder.append(this.entityName);
            _builder.append("_tab_");
            String _name_26 = action_1.getName();
            _builder.append(_name_26);
            _builder.append("_read_scheduler : component cast_");
            _builder.append(this.entityName);
            _builder.append("_tab_");
            String _name_27 = action_1.getName();
            _builder.append(_name_27);
            _builder.append("_read_scheduler");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("port map(");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("tab_");
            String _name_28 = action_1.getName();
            _builder.append(_name_28, "\t\t");
            _builder.append("_address0 => top_tab_");
            String _name_29 = action_1.getName();
            _builder.append(_name_29, "\t\t");
            _builder.append("_address0, ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("tab_");
            String _name_30 = action_1.getName();
            _builder.append(_name_30, "\t\t");
            _builder.append("_ce0 => top_tab_");
            String _name_31 = action_1.getName();
            _builder.append(_name_31, "\t\t");
            _builder.append("_ce0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("tab_");
            String _name_32 = action_1.getName();
            _builder.append(_name_32, "\t\t");
            _builder.append("_q0  => top_tab_");
            String _name_33 = action_1.getName();
            _builder.append(_name_33, "\t\t");
            _builder.append("_q0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("writeIdx_");
            String _name_34 = action_1.getName();
            _builder.append(_name_34, "\t\t");
            _builder.append("_address0 => top_writeIdx_");
            String _name_35 = action_1.getName();
            _builder.append(_name_35, "\t\t");
            _builder.append("_address0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("writeIdx_");
            String _name_36 = action_1.getName();
            _builder.append(_name_36, "\t\t");
            _builder.append("_ce0 => top_writeIdx_");
            String _name_37 = action_1.getName();
            _builder.append(_name_37, "\t\t");
            _builder.append("_ce0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("writeIdx_");
            String _name_38 = action_1.getName();
            _builder.append(_name_38, "\t\t");
            _builder.append("_q0  => top_writeIdx_");
            String _name_39 = action_1.getName();
            _builder.append(_name_39, "\t\t");
            _builder.append("_q0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("readIdx_");
            String _name_40 = action_1.getName();
            _builder.append(_name_40, "\t\t");
            _builder.append("_address0 => top_readIdx_");
            String _name_41 = action_1.getName();
            _builder.append(_name_41, "\t\t");
            _builder.append("_address0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("readIdx_");
            String _name_42 = action_1.getName();
            _builder.append(_name_42, "\t\t");
            _builder.append("_ce0 => top_readIdx_");
            String _name_43 = action_1.getName();
            _builder.append(_name_43, "\t\t");
            _builder.append("_ce0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("readIdx_");
            String _name_44 = action_1.getName();
            _builder.append(_name_44, "\t\t");
            _builder.append("_we0  => top_readIdx_");
            String _name_45 = action_1.getName();
            _builder.append(_name_45, "\t\t");
            _builder.append("_we0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("readIdx_");
            String _name_46 = action_1.getName();
            _builder.append(_name_46, "\t\t");
            _builder.append("_d0  => top_readIdx_");
            String _name_47 = action_1.getName();
            _builder.append(_name_47, "\t\t");
            _builder.append("_d0,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_48 = action_1.getName();
            _builder.append(_name_48, "\t\t");
            _builder.append("_read_V_din    => top_myStream_cast_tab_");
            String _name_49 = action_1.getName();
            _builder.append(_name_49, "\t\t");
            _builder.append("_read_V_din,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_50 = action_1.getName();
            _builder.append(_name_50, "\t\t");
            _builder.append("_read_V_full_n => top_myStream_cast_tab_");
            String _name_51 = action_1.getName();
            _builder.append(_name_51, "\t\t");
            _builder.append("_read_V_full_n,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_52 = action_1.getName();
            _builder.append(_name_52, "\t\t");
            _builder.append("_read_V_write  => top_myStream_cast_tab_");
            String _name_53 = action_1.getName();
            _builder.append(_name_53, "\t\t");
            _builder.append("_read_V_write,");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("ap_start => top_ap_start,");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("ap_clk => top_ap_clk,");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("ap_rst => top_ap_rst,");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("ap_done => top_ap_done,");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("ap_idle => top_ap_idle,");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("ap_ready => top_ap_ready");
            _builder.newLine();
            _builder.append("\t");
            _builder.append(");\t");
            _builder.newLine();
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
    _builder.append("generic map (");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("dwidth     => ");
    int _sizeInBits = this.fifoType(connection).getSizeInBits();
    _builder.append(_sizeInBits, "\t");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("awidth     => ");
    int _closestLog_2 = this.closestLog_2(this.safeSize(connection));
    _builder.append(_closestLog_2, "\t");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("mem_size   => ");
    int _safeSize = this.safeSize(connection);
    _builder.append(_safeSize, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append(")");
    _builder.newLine();
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
    _builder.append("generic map (");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("dwidth     => 32,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("awidth     => 1,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("mem_size   => 1");
    _builder.newLine();
    _builder.append(")");
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
    _builder.append("generic map (");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("dwidth     => 32,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("awidth     => 1,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("mem_size   => 1");
    _builder.newLine();
    _builder.append(")");
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
    _builder.append(");");
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
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence declareComponentSignal() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("component ");
    _builder.append(this.entityName);
    _builder.append("_scheduler IS");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("port (");
    _builder.newLine();
    {
      Collection<List<Connection>> _values = this.outgoingPortMap.values();
      for(final List<Connection> connList : _values) {
        _builder.append("\t\t");
        CharSequence _printOutputRamAssignHLS = this.printOutputRamAssignHLS(IterableExtensions.<Connection>head(connList));
        _builder.append(_printOutputRamAssignHLS, "\t\t");
        _builder.append("\t\t\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Collection<Connection> _values_1 = this.incomingPortMap.values();
      for(final Connection connList_1 : _values_1) {
        _builder.append("\t\t");
        CharSequence _printInputRAMAssignHLS = this.printInputRAMAssignHLS(connList_1);
        _builder.append(_printInputRAMAssignHLS, "\t\t");
        _builder.append("\t\t\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _hasAttribute = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute) {
        {
          EList<Action> _actions = this.actor.getActions();
          for(final Action action : _actions) {
            _builder.append("\t\t");
            _builder.append("tab_");
            String _name = action.getName();
            _builder.append(_name, "\t\t");
            _builder.append("_address0    : OUT  STD_LOGIC_VECTOR (14-1 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("tab_");
            String _name_1 = action.getName();
            _builder.append(_name_1, "\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("tab_");
            String _name_2 = action.getName();
            _builder.append(_name_2, "\t\t");
            _builder.append("_we0  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("tab_");
            String _name_3 = action.getName();
            _builder.append(_name_3, "\t\t");
            _builder.append("_d0  : OUT STD_LOGIC_VECTOR (7  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("writeIdx_");
            String _name_4 = action.getName();
            _builder.append(_name_4, "\t\t");
            _builder.append("_address0    :  OUT STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("writeIdx_");
            String _name_5 = action.getName();
            _builder.append(_name_5, "\t\t");
            _builder.append("_ce0 :  OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("writeIdx_");
            String _name_6 = action.getName();
            _builder.append(_name_6, "\t\t");
            _builder.append("_we0  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("writeIdx_");
            String _name_7 = action.getName();
            _builder.append(_name_7, "\t\t");
            _builder.append("_d0  :  OUT STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("readIdx_");
            String _name_8 = action.getName();
            _builder.append(_name_8, "\t\t");
            _builder.append("_address0    : OUT STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("readIdx_");
            String _name_9 = action.getName();
            _builder.append(_name_9, "\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("readIdx_");
            String _name_10 = action.getName();
            _builder.append(_name_10, "\t\t");
            _builder.append("_q0  : IN  STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
          }
        }
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
    _builder.append("ap_ready : OUT STD_LOGIC");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(");");
    _builder.newLine();
    _builder.append("end component;");
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
      for(final Port port : _filter) {
        {
          List<Connection> _get = this.outgoingPortMap.get(port);
          for(final Connection connection : _get) {
            _builder.newLine();
            _builder.append("component cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name_11 = connection.getSourcePort().getName();
            _builder.append(_name_11);
            _builder.append("_read_scheduler IS");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("port (");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _ramName = this.ramName(connection);
            _builder.append(_ramName, "\t\t");
            _builder.append("_address0    : OUT STD_LOGIC_VECTOR (");
            int _closestLog_2 = this.closestLog_2(this.safeSize(connection));
            _builder.append(_closestLog_2, "\t\t");
            _builder.append("-1 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _ramName_1 = this.ramName(connection);
            _builder.append(_ramName_1, "\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _ramName_2 = this.ramName(connection);
            _builder.append(_ramName_2, "\t\t");
            _builder.append("_q0  :  IN STD_LOGIC_VECTOR (");
            int _sizeInBits = this.fifoType(connection).getSizeInBits();
            int _minus = (_sizeInBits - 1);
            _builder.append(_minus, "\t\t");
            _builder.append("  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _wName = this.wName(connection);
            _builder.append(_wName, "\t\t");
            _builder.append("_address0    : OUT STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _wName_1 = this.wName(connection);
            _builder.append(_wName_1, "\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _wName_2 = this.wName(connection);
            _builder.append(_wName_2, "\t\t");
            _builder.append("_q0  : IN  STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _rName = this.rName(connection);
            _builder.append(_rName, "\t\t");
            _builder.append("_address0    :OUT  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _rName_1 = this.rName(connection);
            _builder.append(_rName_1, "\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _rName_2 = this.rName(connection);
            _builder.append(_rName_2, "\t\t");
            _builder.append("_we0  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _rName_3 = this.rName(connection);
            _builder.append(_rName_3, "\t\t");
            _builder.append("_d0  : OUT  STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _castfifoNameRead = this.castfifoNameRead(connection);
            _builder.append(_castfifoNameRead, "\t\t");
            _builder.append("_V_din    : OUT STD_LOGIC_VECTOR (");
            int _sizeInBits_1 = this.fifoType(connection).getSizeInBits();
            int _minus_1 = (_sizeInBits_1 - 1);
            _builder.append(_minus_1, "\t\t");
            _builder.append(" downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameRead_1 = this.castfifoNameRead(connection);
            _builder.append(_castfifoNameRead_1, "\t\t");
            _builder.append("_V_full_n : IN STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameRead_2 = this.castfifoNameRead(connection);
            _builder.append(_castfifoNameRead_2, "\t\t");
            _builder.append("_V_write  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
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
            _builder.append("ap_ready : OUT STD_LOGIC");
            _builder.newLine();
            _builder.append("\t");
            _builder.append(");");
            _builder.newLine();
            _builder.append("end component;");
            _builder.newLine();
            _builder.newLine();
          }
        }
      }
    }
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port_1 : _inputs) {
        final Connection connection_1 = this.incomingPortMap.get(port_1);
        _builder.newLineIfNotEmpty();
        {
          if ((connection_1 != null)) {
            _builder.append("component cast_");
            _builder.append(this.entityName);
            _builder.append("_");
            String _name_12 = connection_1.getTargetPort().getName();
            _builder.append(_name_12);
            _builder.append("_write_scheduler IS");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("port (");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _ramName_3 = this.ramName(connection_1);
            _builder.append(_ramName_3, "\t\t");
            _builder.append("_address0    : OUT  STD_LOGIC_VECTOR (");
            int _closestLog_2_1 = this.closestLog_2(this.safeSize(connection_1));
            _builder.append(_closestLog_2_1, "\t\t");
            _builder.append("-1 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _ramName_4 = this.ramName(connection_1);
            _builder.append(_ramName_4, "\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _ramName_5 = this.ramName(connection_1);
            _builder.append(_ramName_5, "\t\t");
            _builder.append("_we0  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _ramName_6 = this.ramName(connection_1);
            _builder.append(_ramName_6, "\t\t");
            _builder.append("_d0  : OUT STD_LOGIC_VECTOR (");
            int _sizeInBits_2 = this.fifoType(connection_1).getSizeInBits();
            int _minus_2 = (_sizeInBits_2 - 1);
            _builder.append(_minus_2, "\t\t");
            _builder.append("  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _wName_3 = this.wName(connection_1);
            _builder.append(_wName_3, "\t\t");
            _builder.append("_address0    :  OUT STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _wName_4 = this.wName(connection_1);
            _builder.append(_wName_4, "\t\t");
            _builder.append("_ce0 :  OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _wName_5 = this.wName(connection_1);
            _builder.append(_wName_5, "\t\t");
            _builder.append("_we0  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _wName_6 = this.wName(connection_1);
            _builder.append(_wName_6, "\t\t");
            _builder.append("_d0  :  OUT STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _rName_4 = this.rName(connection_1);
            _builder.append(_rName_4, "\t\t");
            _builder.append("_address0    : OUT STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _rName_5 = this.rName(connection_1);
            _builder.append(_rName_5, "\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _rName_6 = this.rName(connection_1);
            _builder.append(_rName_6, "\t\t");
            _builder.append("_q0  : IN  STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            CharSequence _castfifoNameWrite = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite, "\t\t");
            _builder.append("_V_dout   : IN STD_LOGIC_VECTOR (");
            int _sizeInBits_3 = this.fifoType(connection_1).getSizeInBits();
            int _minus_3 = (_sizeInBits_3 - 1);
            _builder.append(_minus_3, "\t\t");
            _builder.append(" downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameWrite_1 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_1, "\t\t");
            _builder.append("_V_empty_n : IN STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            CharSequence _castfifoNameWrite_2 = this.castfifoNameWrite(connection_1);
            _builder.append(_castfifoNameWrite_2, "\t\t");
            _builder.append("_V_read    : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
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
            _builder.append("ap_ready : OUT STD_LOGIC");
            _builder.newLine();
            _builder.append("\t");
            _builder.append(");");
            _builder.newLine();
            _builder.append("end component;");
            _builder.newLine();
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
            _builder.append("component cast_");
            _builder.append(this.entityName);
            _builder.append("_tab_");
            String _name_13 = action_1.getName();
            _builder.append(_name_13);
            _builder.append("_read_scheduler IS");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("port (");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("tab_");
            String _name_14 = action_1.getName();
            _builder.append(_name_14, "\t\t\t");
            _builder.append("_address0    : OUT STD_LOGIC_VECTOR (14-1 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("tab_");
            String _name_15 = action_1.getName();
            _builder.append(_name_15, "\t\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("tab_");
            String _name_16 = action_1.getName();
            _builder.append(_name_16, "\t\t\t");
            _builder.append("_q0  :  IN STD_LOGIC_VECTOR (7  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("writeIdx_");
            String _name_17 = action_1.getName();
            _builder.append(_name_17, "\t\t\t");
            _builder.append("_address0    : OUT STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("writeIdx_");
            String _name_18 = action_1.getName();
            _builder.append(_name_18, "\t\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("writeIdx_");
            String _name_19 = action_1.getName();
            _builder.append(_name_19, "\t\t\t");
            _builder.append("_q0  : IN  STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("readIdx_");
            String _name_20 = action_1.getName();
            _builder.append(_name_20, "\t\t\t");
            _builder.append("_address0    :OUT  STD_LOGIC_VECTOR (0 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("readIdx_");
            String _name_21 = action_1.getName();
            _builder.append(_name_21, "\t\t\t");
            _builder.append("_ce0 : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("readIdx_");
            String _name_22 = action_1.getName();
            _builder.append(_name_22, "\t\t\t");
            _builder.append("_we0  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("readIdx_");
            String _name_23 = action_1.getName();
            _builder.append(_name_23, "\t\t\t");
            _builder.append("_d0  : OUT  STD_LOGIC_VECTOR (31  downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_24 = action_1.getName();
            _builder.append(_name_24, "\t\t\t");
            _builder.append("_read_V_din    : OUT STD_LOGIC_VECTOR (7 downto 0);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_25 = action_1.getName();
            _builder.append(_name_25, "\t\t\t");
            _builder.append("_read_V_full_n : IN STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("myStream_cast_tab_");
            String _name_26 = action_1.getName();
            _builder.append(_name_26, "\t\t\t");
            _builder.append("_read_V_write  : OUT STD_LOGIC;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("ap_clk : IN STD_LOGIC;");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("ap_rst : IN STD_LOGIC;");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("ap_start : IN STD_LOGIC;");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("ap_done : OUT STD_LOGIC;");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("ap_idle : OUT STD_LOGIC;");
            _builder.newLine();
            _builder.append("\t\t\t");
            _builder.append("ap_ready : OUT STD_LOGIC");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(");");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("end component;");
            _builder.newLine();
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
