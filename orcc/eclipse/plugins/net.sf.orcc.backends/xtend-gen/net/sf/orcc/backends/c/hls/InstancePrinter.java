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
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrccAttributes;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Compile Instance c source code
 * 
 * @author Antoine Lorence, Khaled Jerbi and Mariem Abid
 */
@SuppressWarnings("all")
public class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {
  private FPGA fpga = FPGA.builder("Virtex7 (xc7v2000t)");
  
  @Override
  public CharSequence getFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// Source file is \"");
    IFile _file = this.actor.getFile();
    _builder.append(_file);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
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
    _builder.newLine();
    _builder.newLine();
    {
      if ((this.instance != null)) {
        _builder.append("// Parameter values of the instance");
        _builder.newLine();
        {
          EList<Argument> _arguments = this.instance.getArguments();
          for(final Argument arg : _arguments) {
            {
              boolean _isExprList = arg.getValue().isExprList();
              if (_isExprList) {
                _builder.append("static ");
                {
                  Type _type = arg.getValue().getType();
                  boolean _isUint = ((TypeList) _type).getInnermostType().isUint();
                  if (_isUint) {
                    _builder.append("unsigned ");
                  }
                }
                _builder.append("int ");
                String _name = arg.getVariable().getName();
                _builder.append(_name);
                String _printArrayIndexes = this.printArrayIndexes(arg.getValue().getType().getDimensionsExpr());
                _builder.append(_printArrayIndexes);
                _builder.append(" = ");
                CharSequence _doSwitch = this.doSwitch(arg.getValue());
                _builder.append(_doSwitch);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("#define ");
                String _name_1 = arg.getVariable().getName();
                _builder.append(_name_1);
                _builder.append(" ");
                CharSequence _doSwitch_1 = this.doSwitch(arg.getValue());
                _builder.append(_doSwitch_1);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Input FIFOS");
    _builder.newLine();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        {
          if ((connection != null)) {
            _builder.append("extern ");
            CharSequence _doSwitch_2 = this.doSwitch(this.fifoTypeIn(connection));
            _builder.append(_doSwitch_2);
            _builder.append("\t");
            CharSequence _ramName = this.ramName(connection);
            _builder.append(_ramName);
            _builder.append("[");
            int _safeSize = this.safeSize(connection);
            _builder.append(_safeSize);
            _builder.append("];");
            _builder.newLineIfNotEmpty();
            _builder.append("extern unsigned int\t");
            CharSequence _wName = this.wName(connection);
            _builder.append(_wName);
            _builder.append("[1];");
            _builder.newLineIfNotEmpty();
            _builder.append("extern unsigned int\t");
            CharSequence _rName = this.rName(connection);
            _builder.append(_rName);
            _builder.append("[1];");
            _builder.newLineIfNotEmpty();
            _builder.append("unsigned int ");
            CharSequence _localrName = this.localrName(connection);
            _builder.append(_localrName);
            _builder.append("=0;");
            _builder.newLineIfNotEmpty();
            _builder.append("#define SIZE_");
            String _name_2 = port.getName();
            _builder.append(_name_2);
            _builder.append(" ");
            int _safeSize_1 = this.safeSize(connection);
            int _minus = (_safeSize_1 - 1);
            _builder.append(_minus);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Output FIFOs");
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
            _builder.append("extern ");
            CharSequence _doSwitch_3 = this.doSwitch(this.fifoTypeOut(connection_1));
            _builder.append(_doSwitch_3);
            _builder.append(" ");
            CharSequence _ramName_1 = this.ramName(connection_1);
            _builder.append(_ramName_1);
            _builder.append("[");
            int _safeSize_2 = this.safeSize(connection_1);
            _builder.append(_safeSize_2);
            _builder.append("];");
            _builder.newLineIfNotEmpty();
            _builder.append("extern unsigned int ");
            CharSequence _wName_1 = this.wName(connection_1);
            _builder.append(_wName_1);
            _builder.append("[1];");
            _builder.newLineIfNotEmpty();
            _builder.append("extern unsigned int ");
            CharSequence _rName_1 = this.rName(connection_1);
            _builder.append(_rName_1);
            _builder.append("[1];");
            _builder.newLineIfNotEmpty();
            _builder.append("unsigned int ");
            CharSequence _localwName = this.localwName(connection_1);
            _builder.append(_localwName);
            _builder.append("=0;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("/*IF DEBUG ACTION */");
    _builder.newLine();
    {
      boolean _hasAttribute = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute) {
        {
          EList<Action> _actions = this.actor.getActions();
          for(final Action action : _actions) {
            _builder.append("extern u8 tab_");
            String _name_3 = action.getBody().getName();
            _builder.append(_name_3);
            _builder.append("[16384];");
            _builder.newLineIfNotEmpty();
            _builder.append("extern unsigned int writeIdx_");
            String _name_4 = action.getBody().getName();
            _builder.append(_name_4);
            _builder.append("[1];");
            _builder.newLineIfNotEmpty();
            _builder.append("extern unsigned int readIdx_");
            String _name_5 = action.getBody().getName();
            _builder.append(_name_5);
            _builder.append("[1];");
            _builder.newLineIfNotEmpty();
            _builder.append("unsigned int wIdx_");
            String _name_6 = action.getBody().getName();
            _builder.append(_name_6);
            _builder.append("=0;\t");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          boolean _isEmpty = this.actor.getInitializes().isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            {
              EList<Action> _initializes = this.actor.getInitializes();
              for(final Action init : _initializes) {
                _builder.append("extern u8 tab_");
                String _name_7 = init.getBody().getName();
                _builder.append(_name_7);
                _builder.append("[16384];");
                _builder.newLineIfNotEmpty();
                _builder.append("extern unsigned int writeIdx_");
                String _name_8 = init.getBody().getName();
                _builder.append(_name_8);
                _builder.append("[1];");
                _builder.newLineIfNotEmpty();
                _builder.append("extern unsigned int readIdx_");
                String _name_9 = init.getBody().getName();
                _builder.append(_name_9);
                _builder.append("[1];");
                _builder.newLineIfNotEmpty();
                _builder.append("unsigned int wIdx_");
                String _name_10 = init.getBody().getName();
                _builder.append(_name_10);
                _builder.append("=0;\t");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.append("/******************/");
    _builder.newLine();
    {
      boolean _isEmpty_1 = this.actor.getOutputs().isEmpty();
      if (_isEmpty_1) {
        _builder.append("extern stream<int> outFIFO_");
        _builder.append(this.entityName);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// State variables of the actor");
    _builder.newLine();
    {
      EList<Var> _stateVars = this.actor.getStateVars();
      for(final Var variable : _stateVars) {
        CharSequence _declare = this.declare(variable);
        _builder.append(_declare);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Initial FSM state of the actor");
        _builder.newLine();
        _builder.append("enum states {");
        _builder.newLine();
        {
          EList<State> _states = this.actor.getFsm().getStates();
          boolean _hasElements = false;
          for(final State state : _states) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "\t");
            }
            _builder.append("\t");
            _builder.append("my_state_");
            String _name_11 = state.getName();
            _builder.append(_name_11, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("};");
        _builder.newLine();
        _builder.newLine();
        _builder.append("static enum states _FSM_state = my_state_");
        String _name_12 = this.actor.getFsm().getInitialState().getName();
        _builder.append(_name_12);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Functions/procedures");
    _builder.newLine();
    {
      EList<Procedure> _procs = this.actor.getProcs();
      for(final Procedure proc : _procs) {
        {
          boolean _isNative = proc.isNative();
          if (_isNative) {
            _builder.append("extern");
          } else {
            _builder.append("static");
          }
        }
        _builder.append(" ");
        CharSequence _doSwitch_4 = this.doSwitch(proc.getReturnType());
        _builder.append(_doSwitch_4);
        _builder.append(" ");
        String _name_13 = proc.getName();
        _builder.append(_name_13);
        _builder.append("(");
        final Function1<Param, CharSequence> _function_1 = new Function1<Param, CharSequence>() {
          @Override
          public CharSequence apply(final Param it) {
            return InstancePrinter.this.declare(it.getVariable());
          }
        };
        String _join = IterableExtensions.<Param>join(proc.getParameters(), ", ", _function_1);
        _builder.append(_join);
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      final Function1<Procedure, Boolean> _function_2 = new Function1<Procedure, Boolean>() {
        @Override
        public Boolean apply(final Procedure it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Procedure> _filter_1 = IterableExtensions.<Procedure>filter(this.actor.getProcs(), _function_2);
      for(final Procedure proc_1 : _filter_1) {
        CharSequence _print = this.print(proc_1);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Actions");
    _builder.newLine();
    {
      EList<Action> _actions_1 = this.actor.getActions();
      for(final Action action_1 : _actions_1) {
        CharSequence _print_1 = this.print(action_1);
        _builder.append(_print_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Initializes");
    _builder.newLine();
    CharSequence _initializeFunction = this.initializeFunction();
    _builder.append(_initializeFunction);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Action scheduler");
    _builder.newLine();
    {
      boolean _hasFsm_1 = this.actor.hasFsm();
      if (_hasFsm_1) {
        CharSequence _printFsm = this.printFsm();
        _builder.append(_printFsm);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("void ");
        _builder.append(this.entityName);
        _builder.append("_scheduler() {\t\t");
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty_2 = this.actor.getOutputs().isEmpty();
          if (_isEmpty_2) {
            _builder.append("if (! outFIFO_");
            _builder.append(this.entityName);
            _builder.append(".full()){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("outFIFO_");
            _builder.append(this.entityName, "\t");
            _builder.append(".write(0);");
            _builder.newLineIfNotEmpty();
            _builder.append("}");
            _builder.newLine();
          }
        }
        CharSequence _printActionSchedulingLoop = this.printActionSchedulingLoop(this.actor.getActionsOutsideFsm());
        _builder.append(_printActionSchedulingLoop);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("finished:");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("return;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence printFsm() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("void ");
        _builder.append(this.entityName);
        _builder.append("_outside_FSM_scheduler() {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _printActionSchedulingLoop = this.printActionSchedulingLoop(this.actor.getActionsOutsideFsm());
        _builder.append(_printActionSchedulingLoop, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("finished:");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("return;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("void ");
    _builder.append(this.entityName);
    _builder.append("_scheduler() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("// jump to FSM state ");
    _builder.newLine();
    {
      boolean _isEmpty_1 = this.actor.getOutputs().isEmpty();
      if (_isEmpty_1) {
        _builder.append("\t");
        _builder.append("if (! outFIFO_");
        _builder.append(this.entityName, "\t");
        _builder.append(".full()){");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("outFIFO_");
        _builder.append(this.entityName, "\t\t");
        _builder.append(".write(0);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("switch (_FSM_state) {");
    _builder.newLine();
    {
      EList<State> _states = this.actor.getFsm().getStates();
      for(final State state : _states) {
        _builder.append("\t\t");
        _builder.append("case my_state_");
        String _name = state.getName();
        _builder.append(_name, "\t\t");
        _builder.append(":");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("goto l_");
        String _name_1 = state.getName();
        _builder.append(_name_1, "\t\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// FSM transitions");
    _builder.newLine();
    {
      EList<State> _states_1 = this.actor.getFsm().getStates();
      for(final State state_1 : _states_1) {
        CharSequence _printStateLabel = this.printStateLabel(state_1);
        _builder.append(_printStateLabel);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("finished:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence printStateLabel(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("l_");
    String _name = state.getName();
    _builder.append(_name);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append(this.entityName, "\t");
        _builder.append("_outside_FSM_scheduler();");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty_1 = state.getOutgoing().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("\t");
        CharSequence _printStateTransitions = this.printStateTransitions(state);
        _builder.append(_printStateTransitions, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence printOutputPattern(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _ports = pattern.getPorts();
      for(final Port port : _ports) {
        CharSequence _printOutputPatternsPort = this.printOutputPatternsPort(pattern, port);
        _builder.append(_printOutputPatternsPort);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence printOutputPatternsPort(final Pattern pattern, final Port port) {
    CharSequence _xblockexpression = null;
    {
      int i = (-1);
      StringConcatenation _builder = new StringConcatenation();
      {
        List<Connection> _get = this.outgoingPortMap.get(port);
        for(final Connection connection : _get) {
          CharSequence _printOutputPatternPort = this.printOutputPatternPort(pattern, port, connection, i = (i + 1));
          _builder.append(_printOutputPatternPort);
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public CharSequence printOutputPatternPort(final Pattern pattern, final Port port, final Connection successor, final int id) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("&& (");
    int _safeSize = this.safeSize(successor);
    _builder.append(_safeSize, "\t");
    _builder.append(" - ");
    CharSequence _localwName = this.localwName(IterableExtensions.<Connection>head(this.outgoingPortMap.get(port)));
    _builder.append(_localwName, "\t");
    _builder.append(" + ");
    CharSequence _rName = this.rName(IterableExtensions.<Connection>head(this.outgoingPortMap.get(port)));
    _builder.append(_rName, "\t");
    _builder.append("[0] >= ");
    int _numTokens = pattern.getNumTokens(port);
    _builder.append(_numTokens, "\t");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence checkInputPattern(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _ports = pattern.getPorts();
      for(final Port port : _ports) {
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        {
          if ((connection != null)) {
            CharSequence _wName = this.wName(connection);
            _builder.append(_wName);
            _builder.append("[0] - ");
            CharSequence _localrName = this.localrName(connection);
            _builder.append(_localrName);
            _builder.append(" >= ");
            int _numTokens = pattern.getNumTokens(port);
            _builder.append(_numTokens);
            _builder.append("  &&");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence print(final Action action) {
    this.currentAction = action;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static void ");
    _builder.append(this.entityName);
    _builder.append("_");
    String _name = action.getBody().getName();
    _builder.append(_name);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Var> _locals = action.getBody().getLocals();
      for(final Var variable : _locals) {
        _builder.append("\t");
        CharSequence _declare = this.declare(variable);
        _builder.append(_declare, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<Block> _blocks = action.getBody().getBlocks();
      for(final Block block : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch = this.doSwitch(block);
        _builder.append(_doSwitch, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("/*IF DEBUG ACTION */");
    _builder.newLine();
    {
      boolean _hasAttribute = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      if (_hasAttribute) {
        _builder.append("\t");
        _builder.append("tab_");
        String _name_1 = action.getName();
        _builder.append(_name_1, "\t");
        _builder.append("[wIdx_");
        String _name_2 = action.getName();
        _builder.append(_name_2, "\t");
        _builder.append(" & 16383]=1;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("wIdx_");
        String _name_3 = action.getName();
        _builder.append(_name_3, "\t");
        _builder.append(" = wIdx_");
        String _name_4 = action.getName();
        _builder.append(_name_4, "\t");
        _builder.append(" + 1;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("writeIdx_");
        String _name_5 = action.getName();
        _builder.append(_name_5, "\t");
        _builder.append("[0] = wIdx_");
        String _name_6 = action.getName();
        _builder.append(_name_6, "\t");
        _builder.append(";\t\t\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("/******************/");
    _builder.newLine();
    {
      EList<Port> _ports = action.getInputPattern().getPorts();
      for(final Port port : _ports) {
        _builder.append("\t");
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        {
          if ((connection != null)) {
            _builder.append("\t");
            CharSequence _localrName = this.localrName(connection);
            _builder.append(_localrName, "\t");
            _builder.append(" = ");
            CharSequence _localrName_1 = this.localrName(connection);
            _builder.append(_localrName_1, "\t");
            _builder.append("+");
            int _numTokens = action.getInputPattern().getNumTokens(port);
            _builder.append(_numTokens, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _rName = this.rName(connection);
            _builder.append(_rName, "\t");
            _builder.append("[0] = ");
            CharSequence _localrName_2 = this.localrName(connection);
            _builder.append(_localrName_2, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      EList<Port> _ports_1 = action.getOutputPattern().getPorts();
      for(final Port port_1 : _ports_1) {
        {
          List<Connection> _get = this.outgoingPortMap.get(port_1);
          for(final Connection connection_1 : _get) {
            _builder.append("\t");
            CharSequence _localwName = this.localwName(connection_1);
            _builder.append(_localwName, "\t");
            _builder.append(" = ");
            CharSequence _localwName_1 = this.localwName(connection_1);
            _builder.append(_localwName_1, "\t");
            _builder.append(" + ");
            int _numTokens_1 = action.getOutputPattern().getNumTokens(port_1);
            _builder.append(_numTokens_1, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            CharSequence _wName = this.wName(connection_1);
            _builder.append(_wName, "\t");
            _builder.append("[0] = ");
            CharSequence _localwName_2 = this.localwName(connection_1);
            _builder.append(_localwName_2, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    CharSequence _print = this.print(action.getScheduler());
    _builder.append(_print);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    final String output = _builder.toString();
    this.currentAction = null;
    return output;
  }
  
  @Override
  public CharSequence caseInstLoad(final InstLoad load) {
    CharSequence _xifexpression = null;
    EObject _eContainer = load.eContainer();
    boolean _tripleNotEquals = (_eContainer != null);
    if (_tripleNotEquals) {
      CharSequence _xblockexpression = null;
      {
        final Port srcPort = this.getPort(load.getSource().getVariable());
        StringConcatenation _builder = new StringConcatenation();
        {
          if ((srcPort != null)) {
            String _name = load.getTarget().getVariable().getName();
            _builder.append(_name);
            _builder.append(" = ");
            CharSequence _ramName = this.ramName(this.incomingPortMap.get(srcPort));
            _builder.append(_ramName);
            _builder.append("[((");
            CharSequence _localrName = this.localrName(this.incomingPortMap.get(srcPort));
            _builder.append(_localrName);
            _builder.append(" & SIZE_");
            String _name_1 = srcPort.getName();
            _builder.append(_name_1);
            _builder.append(")  + (");
            CharSequence _doSwitch = this.doSwitch(IterableExtensions.<Expression>head(load.getIndexes()));
            _builder.append(_doSwitch);
            _builder.append("))];");
            _builder.newLineIfNotEmpty();
          } else {
            String _name_2 = load.getTarget().getVariable().getName();
            _builder.append(_name_2);
            _builder.append(" = ");
            String _name_3 = load.getSource().getVariable().getName();
            _builder.append(_name_3);
            String _printArrayIndexes = this.printArrayIndexes(load.getIndexes());
            _builder.append(_printArrayIndexes);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _xblockexpression = _builder;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseInstStore(final InstStore store) {
    CharSequence _xblockexpression = null;
    {
      final Port trgtPort = this.getPort(store.getTarget().getVariable());
      StringConcatenation _builder = new StringConcatenation();
      {
        if ((trgtPort != null)) {
          CharSequence _ramName = this.ramName(IterableExtensions.<Connection>head(this.outgoingPortMap.get(trgtPort)));
          _builder.append(_ramName);
          _builder.append("[((");
          CharSequence _localwName = this.localwName(IterableExtensions.<Connection>head(this.outgoingPortMap.get(trgtPort)));
          _builder.append(_localwName);
          _builder.append(" & (");
          int _safeSize = this.safeSize(IterableExtensions.<Connection>head(this.outgoingPortMap.get(trgtPort)));
          int _minus = (_safeSize - 1);
          _builder.append(_minus);
          _builder.append(" )) + (");
          CharSequence _doSwitch = this.doSwitch(IterableExtensions.<Expression>head(store.getIndexes()));
          _builder.append(_doSwitch);
          _builder.append("))]=");
          CharSequence _doSwitch_1 = this.doSwitch(store.getValue());
          _builder.append(_doSwitch_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        } else {
          String _name = store.getTarget().getVariable().getName();
          _builder.append(_name);
          String _printArrayIndexes = this.printArrayIndexes(store.getIndexes());
          _builder.append(_printArrayIndexes);
          _builder.append(" = ");
          CharSequence _doSwitch_2 = this.doSwitch(store.getValue());
          _builder.append(_doSwitch_2);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence printActionSchedulingLoop(final List<Action> actions) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printActionsScheduling = this.printActionsScheduling(actions);
    _builder.append(_printActionsScheduling);
    _builder.newLineIfNotEmpty();
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
  
  public CharSequence initializeFunction() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.actor.getInitializes().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          EList<Action> _initializes = this.actor.getInitializes();
          for(final Action init : _initializes) {
            CharSequence _print = this.print(init);
            _builder.append(_print);
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("static void initialize() {");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printActionsScheduling = this.printActionsScheduling(this.actor.getInitializes());
        _builder.append(_printActionsScheduling, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("finished:");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("// no read_end/write_end here!");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("return;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence printActionsScheduling(final Iterable<Action> actions) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final Action action : actions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" else ", "");
        }
        _builder.append("if (");
        CharSequence _checkInputPattern = this.checkInputPattern(action.getInputPattern());
        _builder.append(_checkInputPattern);
        _builder.append("isSchedulable_");
        String _name = action.getName();
        _builder.append(_name);
        _builder.append("()) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("if(1");
        _builder.newLine();
        {
          Pattern _outputPattern = action.getOutputPattern();
          boolean _tripleNotEquals = (_outputPattern != null);
          if (_tripleNotEquals) {
            _builder.append("\t");
            CharSequence _printOutputPattern = this.printOutputPattern(action.getOutputPattern());
            _builder.append(_printOutputPattern, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t\t");
          }
        }
        _builder.append(" ");
        {
          boolean _hasAttribute = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
          if (_hasAttribute) {
            _builder.append(" && (16384 - wIdx_");
            String _name_1 = action.getName();
            _builder.append(_name_1, "\t");
            _builder.append(" + readIdx_");
            String _name_2 = action.getName();
            _builder.append(_name_2, "\t");
            _builder.append("[0] >= 1) ");
          }
        }
        _builder.append("){");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append(this.entityName, "\t\t");
        _builder.append("_");
        String _name_3 = action.getBody().getName();
        _builder.append(_name_3, "\t\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("}");
      }
    }
    _builder.append(" else {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence printStateTransitions(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<Edge, Transition> _function = new Function1<Edge, Transition>() {
        @Override
        public Transition apply(final Edge it) {
          return ((Transition) it);
        }
      };
      List<Transition> _map = ListExtensions.<Edge, Transition>map(state.getOutgoing(), _function);
      boolean _hasElements = false;
      for(final Transition transitions : _map) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" else ", "");
        }
        _builder.append("if (");
        CharSequence _checkInputPattern = this.checkInputPattern(transitions.getAction().getInputPattern());
        _builder.append(_checkInputPattern);
        _builder.append(" isSchedulable_");
        String _name = transitions.getAction().getName();
        _builder.append(_name);
        _builder.append("() ");
        CharSequence _printOutputPattern = this.printOutputPattern(transitions.getAction().getOutputPattern());
        _builder.append(_printOutputPattern);
        _builder.append(" ");
        {
          boolean _hasAttribute = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
          if (_hasAttribute) {
            _builder.append(" && (16384 - wIdx_");
            String _name_1 = transitions.getAction().getName();
            _builder.append(_name_1);
            _builder.append(" + readIdx_");
            String _name_2 = transitions.getAction().getName();
            _builder.append(_name_2);
            _builder.append("[0] >= 1) ");
          }
        }
        _builder.append(") {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append(this.entityName, "\t");
        _builder.append("_");
        String _name_3 = transitions.getAction().getBody().getName();
        _builder.append(_name_3, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("_FSM_state = my_state_");
        String _name_4 = transitions.getTarget().getName();
        _builder.append(_name_4, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("goto finished;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.append("else {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("_FSM_state = my_state_");
    String _name_5 = state.getName();
    _builder.append(_name_5, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("bool");
    return _builder;
  }
  
  public CharSequence script(final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("open_project -reset subProject_");
    _builder.append(this.entityName);
    _builder.newLineIfNotEmpty();
    _builder.append("set_top ");
    _builder.append(this.entityName);
    _builder.append("_scheduler");
    _builder.newLineIfNotEmpty();
    _builder.append("add_files ");
    _builder.append(this.entityName);
    _builder.append(".cpp");
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
    _builder.append("source \"directive_");
    _builder.append(this.entityName);
    _builder.append(".tcl\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.append("csynth_design");
    _builder.newLine();
    _builder.append("exit");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence directive(final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        {
          EList<Action> _actions = this.actor.getActions();
          for(final Action action : _actions) {
            final Connection connection = this.incomingPortMap.get(port);
            _builder.newLineIfNotEmpty();
            {
              boolean _contains = action.getInputPattern().contains(port);
              if (_contains) {
                _builder.append("set_directive_resource -core RAM_1P \"");
                _builder.append(this.entityName);
                _builder.append("_");
                String _name = action.getBody().getName();
                _builder.append(_name);
                _builder.append("\" ");
                CharSequence _ramName = this.ramName(connection);
                _builder.append(_ramName);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    return _builder;
  }
}
