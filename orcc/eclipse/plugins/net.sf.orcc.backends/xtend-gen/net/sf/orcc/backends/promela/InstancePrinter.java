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
package net.sf.orcc.backends.promela;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.orcc.backends.promela.PromelaTemplate;
import net.sf.orcc.backends.promela.transform.PromelaSchedulingModel;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Var;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Compile Instance promela
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class InstancePrinter extends PromelaTemplate {
  private Actor actor;
  
  private Set<Var> schedulingVars;
  
  private Map<Action, List<InstLoad>> loadPeeks;
  
  private Map<Action, List<Expression>> guards;
  
  private Map<EObject, List<Action>> priority;
  
  public Actor setActor(final Actor actor) {
    return this.actor = actor;
  }
  
  public Set<Var> setSchedulingModel(final PromelaSchedulingModel schedulingModel) {
    return this.schedulingVars = schedulingModel.getAllSchedulingVars();
  }
  
  @Override
  public void setOptions(final Map<String, Object> options) {
    super.setOptions(options);
    Object _get = options.get("loadPeeks");
    this.loadPeeks = ((Map<Action, List<InstLoad>>) _get);
    Object _get_1 = options.get("guards");
    this.guards = ((Map<Action, List<Expression>>) _get_1);
    Object _get_2 = options.get("priority");
    this.priority = ((Map<EObject, List<Action>>) _get_2);
  }
  
  public CharSequence getInstanceFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        _builder.append("/* States of the FSM */");
        _builder.newLine();
        {
          int _size = this.actor.getFsm().getStates().size();
          int _minus = (_size - 1);
          IntegerRange _upTo = new IntegerRange(0, _minus);
          for(final Integer i : _upTo) {
            _builder.append("int ");
            String _simpleName = this.actor.getSimpleName();
            _builder.append(_simpleName);
            _builder.append("_state_");
            String _name = this.actor.getFsm().getStates().get((i).intValue()).getName();
            _builder.append(_name);
            _builder.append(" = ");
            _builder.append(i);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("/* Initial State */");
        _builder.newLine();
        _builder.append("int fsm_state_");
        String _simpleName_1 = this.actor.getSimpleName();
        _builder.append(_simpleName_1);
        _builder.append(" = ");
        String _simpleName_2 = this.actor.getSimpleName();
        _builder.append(_simpleName_2);
        _builder.append("_state_");
        String _name_1 = this.actor.getFsm().getInitialState().getName();
        _builder.append(_name_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("int ");
        String _simpleName_3 = this.actor.getSimpleName();
        _builder.append(_simpleName_3);
        _builder.append("_state_one_state = 0;");
        _builder.newLineIfNotEmpty();
        _builder.append("int fsm_state_");
        String _simpleName_4 = this.actor.getSimpleName();
        _builder.append(_simpleName_4);
        _builder.append("=");
        String _simpleName_5 = this.actor.getSimpleName();
        _builder.append(_simpleName_5);
        _builder.append("_state_one_state;");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getInitializes());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("int state_var_");
        String _simpleName_6 = this.actor.getSimpleName();
        _builder.append(_simpleName_6);
        _builder.append("_initialized=0;");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("int state_var_");
        String _simpleName_7 = this.actor.getSimpleName();
        _builder.append(_simpleName_7);
        _builder.append("_initialized=1;");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("int promela_");
    String _simpleName_8 = this.actor.getSimpleName();
    _builder.append(_simpleName_8);
    _builder.append("_has_progress=0;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not_1 = (!_isNullOrEmpty_1);
      if (_not_1) {
        _builder.append("/* State variables */");
        _builder.newLine();
        {
          EList<Var> _stateVars = this.actor.getStateVars();
          for(final Var stateVar : _stateVars) {
            {
              boolean _contains = this.schedulingVars.contains(stateVar);
              if (_contains) {
                CharSequence _declareStateVar = this.declareStateVar(stateVar);
                _builder.append(_declareStateVar);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("/* Process */");
    _builder.newLine();
    _builder.append("proctype ");
    String _simpleName_9 = this.actor.getSimpleName();
    _builder.append(_simpleName_9);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _isNullOrEmpty_2 = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not_2 = (!_isNullOrEmpty_2);
      if (_not_2) {
        _builder.append("\t");
        _builder.append("/* State variables */");
        _builder.newLine();
        {
          EList<Var> _stateVars_1 = this.actor.getStateVars();
          for(final Var stateVar_1 : _stateVars_1) {
            {
              boolean _contains_1 = this.schedulingVars.contains(stateVar_1);
              boolean _not_3 = (!_contains_1);
              if (_not_3) {
                _builder.append("\t");
                CharSequence _declareStateVar_1 = this.declareStateVar(stateVar_1);
                _builder.append(_declareStateVar_1, "\t");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/*peek variables*/");
    _builder.newLine();
    {
      EList<Action> _actions = this.actor.getActions();
      for(final Action action : _actions) {
        {
          List<InstLoad> _get = this.loadPeeks.get(action);
          for(final InstLoad inst : _get) {
            _builder.append("\t");
            CharSequence _doSwitch = this.doSwitch(inst.getTarget().getVariable().getType());
            _builder.append(_doSwitch, "\t");
            _builder.append(" ");
            String _name_2 = inst.getTarget().getVariable().getName();
            _builder.append(_name_2, "\t");
            _builder.append(" = 0;");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          List<InstLoad> _get_1 = this.loadPeeks.get(action);
          for(final InstLoad inst_1 : _get_1) {
            _builder.append("\t");
            _builder.append("bool ");
            String _name_3 = inst_1.getTarget().getVariable().getName();
            _builder.append(_name_3, "\t");
            _builder.append("_done = 0;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    {
      boolean _isNullOrEmpty_3 = IterableExtensions.isNullOrEmpty(this.actor.getParameters());
      boolean _not_4 = (!_isNullOrEmpty_3);
      if (_not_4) {
        _builder.append("\t");
        _builder.append("/* Actor parameters*/");
        _builder.newLine();
        {
          EList<Var> _parameters = this.actor.getParameters();
          for(final Var variable : _parameters) {
            _builder.append("\t");
            CharSequence _declareStateVar_2 = this.declareStateVar(variable);
            _builder.append(_declareStateVar_2, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Ports */");
    _builder.newLine();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(port.getType());
        _builder.append(_doSwitch_1, "\t");
        _builder.append(" ");
        String _name_4 = port.getName();
        _builder.append(_name_4, "\t");
        _builder.append("[");
        int _numTokensConsumed = port.getNumTokensConsumed();
        _builder.append(_numTokensConsumed, "\t");
        _builder.append("];");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _outputs = this.actor.getOutputs();
      for(final Port port_1 : _outputs) {
        _builder.append("\t");
        CharSequence _doSwitch_2 = this.doSwitch(port_1.getType());
        _builder.append(_doSwitch_2, "\t");
        _builder.append(" ");
        String _name_5 = port_1.getName();
        _builder.append(_name_5, "\t");
        _builder.append("[");
        int _numTokensProduced = port_1.getNumTokensProduced();
        _builder.append(_numTokensProduced, "\t");
        _builder.append("];");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int promela_io_index; // used for reading/writing multiple tokens");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Initializers */");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _initializeFunction = this.initializeFunction();
    _builder.append(_initializeFunction, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Actions */");
    _builder.newLine();
    {
      boolean _hasFsm_1 = this.actor.hasFsm();
      if (_hasFsm_1) {
        _builder.append("\t");
        _builder.append("do");
        _builder.newLine();
        {
          EList<State> _states = this.actor.getFsm().getStates();
          for(final State state : _states) {
            _builder.append("\t");
            CharSequence _newState = this.newState(state);
            _builder.append(_newState, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("od;");
        _builder.newLine();
      } else {
        _builder.append("\t");
        _builder.append("do");
        _builder.newLine();
        _builder.append("\t");
        _builder.append(":: skip ->");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("if");
        _builder.newLine();
        {
          EList<Action> _actionsOutsideFsm = this.actor.getActionsOutsideFsm();
          for(final Action action_1 : _actionsOutsideFsm) {
            _builder.append("\t");
            _builder.append("\t");
            CharSequence _printPeekPattern = this.printPeekPattern(action_1);
            _builder.append(_printPeekPattern, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<Action> _actionsOutsideFsm_1 = this.actor.getActionsOutsideFsm();
          for(final Action action_2 : _actionsOutsideFsm_1) {
            _builder.append("\t");
            _builder.append("\t");
            CharSequence _printScheduler = this.printScheduler(action_2);
            _builder.append(_printScheduler, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("fi;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("od;");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence initializeFunction() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getInitializes());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("if");
        _builder.newLine();
        _builder.append(":: state_var_");
        String _simpleName = this.actor.getSimpleName();
        _builder.append(_simpleName);
        _builder.append("_initialized==0 -> atomic {");
        _builder.newLineIfNotEmpty();
        {
          EList<Action> _initializes = this.actor.getInitializes();
          for(final Action init : _initializes) {
            _builder.append("\t");
            _builder.append("/* Temp variables*/");
            _builder.newLine();
            {
              EList<Var> _locals = init.getBody().getLocals();
              for(final Var local : _locals) {
                _builder.append("\t");
                CharSequence _declare = this.declare(local);
                _builder.append(_declare, "\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("\t");
            CharSequence _inputPattern = this.inputPattern(init.getInputPattern());
            _builder.append(_inputPattern, "\t");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            {
              EList<Block> _blocks = init.getBody().getBlocks();
              for(final Block block : _blocks) {
                _builder.append("\t");
                CharSequence _doSwitch = this.doSwitch(block);
                _builder.append(_doSwitch, "\t");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.newLine();
            _builder.append("\t");
            CharSequence _outputPattern = this.outputPattern(init.getOutputPattern());
            _builder.append(_outputPattern, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("state_var_");
            String _simpleName_1 = this.actor.getSimpleName();
            _builder.append(_simpleName_1, "\t");
            _builder.append("_initialized=1;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("promela_has_progress=1;");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("promela_");
            String _simpleName_2 = this.actor.getSimpleName();
            _builder.append(_simpleName_2, "\t");
            _builder.append("_has_progress=1;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("#ifdef PXML");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("printf(\"<iterand actor=\\\"");
            String _simpleName_3 = this.actor.getSimpleName();
            _builder.append(_simpleName_3, "\t");
            _builder.append("\\\" action=\\\"");
            String _name = init.getName();
            _builder.append(_name, "\t");
            _builder.append("\\\" repetitions=\\\"1\\\"/>\\n\");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("#endif");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("#ifdef PNAME");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("printf(\"");
            String _simpleName_4 = this.actor.getSimpleName();
            _builder.append(_simpleName_4, "\t");
            _builder.append(".");
            String _name_1 = init.getName();
            _builder.append(_name_1, "\t");
            _builder.append("();\\n\");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("#endif");
            _builder.newLine();
            {
              boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
              boolean _not_1 = (!_isNullOrEmpty_1);
              if (_not_1) {
                _builder.append("\t");
                _builder.append("#ifdef PSTATE");
                _builder.newLine();
                _builder.append("\t");
                _builder.append("printf(\"");
                final Function1<Var, CharSequence> _function = new Function1<Var, CharSequence>() {
                  @Override
                  public CharSequence apply(final Var it) {
                    StringConcatenation _builder = new StringConcatenation();
                    String _name = it.getName();
                    _builder.append(_name);
                    final Function1<Integer, CharSequence> _function = new Function1<Integer, CharSequence>() {
                      @Override
                      public CharSequence apply(final Integer it) {
                        return "[0]";
                      }
                    };
                    String _join = IterableExtensions.<Integer>join(it.getType().getDimensions(), "", _function);
                    _builder.append(_join);
                    _builder.append("=%d");
                    return _builder.toString();
                  }
                };
                String _join = IterableExtensions.<Var>join(this.actor.getStateVars(), ";", _function);
                _builder.append(_join, "\t");
                _builder.append("\\n\\n\", ");
                final Function1<Var, CharSequence> _function_1 = new Function1<Var, CharSequence>() {
                  @Override
                  public CharSequence apply(final Var it) {
                    StringConcatenation _builder = new StringConcatenation();
                    String _name = it.getName();
                    _builder.append(_name);
                    final Function1<Integer, CharSequence> _function = new Function1<Integer, CharSequence>() {
                      @Override
                      public CharSequence apply(final Integer it) {
                        return "[0]";
                      }
                    };
                    String _join = IterableExtensions.<Integer>join(it.getType().getDimensions(), ",", _function);
                    _builder.append(_join);
                    return _builder.toString();
                  }
                };
                String _join_1 = IterableExtensions.<Var>join(this.actor.getStateVars(), ", ", _function_1);
                _builder.append(_join_1, "\t");
                _builder.append(");");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("#endif");
                _builder.newLine();
              }
            }
          }
        }
        _builder.append("}");
        _builder.newLine();
        _builder.append(":: state_var_");
        String _simpleName_5 = this.actor.getSimpleName();
        _builder.append(_simpleName_5);
        _builder.append("_initialized==1 -> skip;");
        _builder.newLineIfNotEmpty();
        _builder.append("fi;");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence newState(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("::\tfsm_state_");
    String _simpleName = this.actor.getSimpleName();
    _builder.append(_simpleName);
    _builder.append(" == ");
    String _simpleName_1 = this.actor.getSimpleName();
    _builder.append(_simpleName_1);
    _builder.append("_state_");
    String _name = state.getName();
    _builder.append(_name);
    _builder.append(" -> {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("if");
    _builder.newLine();
    {
      EList<Edge> _outgoing = state.getOutgoing();
      for(final Edge edge : _outgoing) {
        _builder.append("\t");
        CharSequence _printPeekPattern = this.printPeekPattern(((Transition) edge).getAction());
        _builder.append(_printPeekPattern, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _printSchedulerFSM = this.printSchedulerFSM(((Transition) edge).getAction(), ((Transition) edge));
        _builder.append(_printSchedulerFSM, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Action> _actionsOutsideFsm = this.actor.getActionsOutsideFsm();
      for(final Action action : _actionsOutsideFsm) {
        _builder.append("\t");
        CharSequence _printPeekPattern_1 = this.printPeekPattern(action);
        _builder.append(_printPeekPattern_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Action> _actionsOutsideFsm_1 = this.actor.getActionsOutsideFsm();
      for(final Action action_1 : _actionsOutsideFsm_1) {
        _builder.append("\t");
        CharSequence _printScheduler = this.printScheduler(action_1);
        _builder.append(_printScheduler, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("fi;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printPeekPattern(final Action action) {
    CharSequence _xblockexpression = null;
    {
      final Pattern pattern = action.getPeekPattern();
      CharSequence _xifexpression = null;
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(pattern.getVariables());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("::\t/*");
        String _name = action.getName();
        _builder.append(_name);
        _builder.append("_peek()*/ atomic { ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("nempty(");
        final Function1<Var, CharSequence> _function = new Function1<Var, CharSequence>() {
          @Override
          public CharSequence apply(final Var it) {
            return InstancePrinter.this.peekPatternCheck(it, pattern);
          }
        };
        String _join = IterableExtensions.<Var>join(pattern.getVariables(), " && ", _function);
        _builder.append(_join, "\t");
        _builder.append(")");
        final Function1<InstLoad, CharSequence> _function_1 = new Function1<InstLoad, CharSequence>() {
          @Override
          public CharSequence apply(final InstLoad it) {
            StringConcatenation _builder = new StringConcatenation();
            String _name = it.getTarget().getVariable().getName();
            _builder.append(_name);
            _builder.append("_done == 0");
            return _builder.toString();
          }
        };
        String _join_1 = IterableExtensions.<InstLoad>join(this.loadPeeks.get(action), " && ", " && ", "", _function_1);
        _builder.append(_join_1, "\t");
        _builder.append(" ->");
        _builder.newLineIfNotEmpty();
        {
          EList<Var> _variables = pattern.getVariables();
          for(final Var variable : _variables) {
            _builder.append("\t");
            _builder.append("chan_");
            String _simpleName = this.actor.getSimpleName();
            _builder.append(_simpleName, "\t");
            _builder.append("_");
            String _name_1 = pattern.getVarToPortMap().get(variable).getName();
            _builder.append(_name_1, "\t");
            _builder.append("?<");
            String _name_2 = variable.getName();
            _builder.append(_name_2, "\t");
            _builder.append("[0]>;");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<Var> _variables_1 = pattern.getVariables();
          for(final Var variable_1 : _variables_1) {
            {
              List<InstLoad> _get = this.loadPeeks.get(action);
              for(final InstLoad ld : _get) {
                _builder.append("\t");
                CharSequence _doSwitch = this.doSwitch(ld);
                _builder.append(_doSwitch, "\t");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        {
          List<InstLoad> _get_1 = this.loadPeeks.get(action);
          for(final InstLoad instLoad : _get_1) {
            _builder.append("\t");
            String _name_3 = instLoad.getTarget().getVariable().getName();
            _builder.append(_name_3, "\t");
            _builder.append("_done = 1;");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
        _builder.newLine();
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public CharSequence peekPatternCheck(final Var variable, final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("chan_");
    String _simpleName = this.actor.getSimpleName();
    _builder.append(_simpleName);
    _builder.append("_");
    String _name = pattern.getVarToPortMap().get(variable).getName();
    _builder.append(_name);
    return _builder;
  }
  
  public CharSequence printSchedulerFSM(final Action action, final Transition trans) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("::\t/* ");
    String _name = action.getName();
    _builder.append(_name);
    _builder.append(" */ atomic { ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _guardFSM = this.guardFSM(action, trans);
    _builder.append(_guardFSM, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _inputChannelCheck = this.inputChannelCheck(action.getInputPattern());
    _builder.append(_inputChannelCheck, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _outputChannelCheck = this.outputChannelCheck(action.getOutputPattern());
    _builder.append(_outputChannelCheck, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("-> ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Temp variables*/");
    _builder.newLine();
    {
      EList<Var> _locals = action.getBody().getLocals();
      for(final Var local : _locals) {
        _builder.append("\t");
        CharSequence _declare = this.declare(local);
        _builder.append(_declare, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t ");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _inputPattern = this.inputPattern(action.getInputPattern());
    _builder.append(_inputPattern, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
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
    _builder.newLine();
    _builder.append("\t");
    CharSequence _outputPattern = this.outputPattern(action.getOutputPattern());
    _builder.append(_outputPattern, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fsm_state_");
    String _simpleName = this.actor.getSimpleName();
    _builder.append(_simpleName, "\t");
    _builder.append(" = ");
    String _simpleName_1 = this.actor.getSimpleName();
    _builder.append(_simpleName_1, "\t");
    _builder.append("_state_");
    String _name_1 = trans.getTarget().getName();
    _builder.append(_name_1, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      List<InstLoad> _get = this.loadPeeks.get(action);
      for(final InstLoad instLoad : _get) {
        _builder.append("\t");
        String _name_2 = instLoad.getTarget().getVariable().getName();
        _builder.append(_name_2, "\t");
        _builder.append("_done = 0;");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("promela_has_progress=1;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("promela_");
    String _simpleName_2 = this.actor.getSimpleName();
    _builder.append(_simpleName_2, "\t");
    _builder.append("_has_progress=1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#ifdef PXML");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("printf(\"<iterand actor=\\\"");
    String _simpleName_3 = this.actor.getSimpleName();
    _builder.append(_simpleName_3, "\t");
    _builder.append("\\\" action=\\\"");
    String _name_3 = action.getName();
    _builder.append(_name_3, "\t");
    _builder.append("\\\" repetitions=\\\"1\\\"/>\\n\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("#endif");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#ifdef PNAME");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("printf(\"");
    String _simpleName_4 = this.actor.getSimpleName();
    _builder.append(_simpleName_4, "\t");
    _builder.append(".");
    String _name_4 = action.getName();
    _builder.append(_name_4, "\t");
    _builder.append("();\\n\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("#endif");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#ifdef PFSM");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("printf(\"state = ");
    String _simpleName_5 = this.actor.getSimpleName();
    _builder.append(_simpleName_5, "\t");
    _builder.append("_state_");
    String _name_5 = trans.getTarget().getName();
    _builder.append(_name_5, "\t");
    _builder.append(";\\n\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("#endif");
    _builder.newLine();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("#ifdef PSTATE");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"");
        final Function1<Var, CharSequence> _function = new Function1<Var, CharSequence>() {
          @Override
          public CharSequence apply(final Var it) {
            StringConcatenation _builder = new StringConcatenation();
            String _name = it.getName();
            _builder.append(_name);
            final Function1<Integer, CharSequence> _function = new Function1<Integer, CharSequence>() {
              @Override
              public CharSequence apply(final Integer it) {
                return "[0]";
              }
            };
            String _join = IterableExtensions.<Integer>join(it.getType().getDimensions(), "", _function);
            _builder.append(_join);
            _builder.append("=%d");
            return _builder.toString();
          }
        };
        String _join = IterableExtensions.<Var>join(this.actor.getStateVars(), ";", _function);
        _builder.append(_join, "\t");
        _builder.append("\\n\\n\", ");
        final Function1<Var, CharSequence> _function_1 = new Function1<Var, CharSequence>() {
          @Override
          public CharSequence apply(final Var it) {
            StringConcatenation _builder = new StringConcatenation();
            String _name = it.getName();
            _builder.append(_name);
            final Function1<Integer, CharSequence> _function = new Function1<Integer, CharSequence>() {
              @Override
              public CharSequence apply(final Integer it) {
                return "[0]";
              }
            };
            String _join = IterableExtensions.<Integer>join(it.getType().getDimensions(), ",", _function);
            _builder.append(_join);
            return _builder.toString();
          }
        };
        String _join_1 = IterableExtensions.<Var>join(this.actor.getStateVars(), ", ", _function_1);
        _builder.append(_join_1, "\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("#endif");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence guardFSM(final Action action, final EObject object) {
    CharSequence _xifexpression = null;
    boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.guards.get(action));
    boolean _not = (!_isNullOrEmpty);
    if (_not) {
      StringConcatenation _builder = new StringConcatenation();
      final Function1<Expression, CharSequence> _function = new Function1<Expression, CharSequence>() {
        @Override
        public CharSequence apply(final Expression it) {
          return InstancePrinter.this.doSwitch(it);
        }
      };
      String _join = IterableExtensions.<Expression>join(this.guards.get(action), " && ", _function);
      _builder.append(_join);
      final Function1<Action, CharSequence> _function_1 = new Function1<Action, CharSequence>() {
        @Override
        public CharSequence apply(final Action it) {
          return InstancePrinter.this.priorities(it);
        }
      };
      String _join_1 = IterableExtensions.<Action>join(this.priority.get(object), " && ", " && ", "", _function_1);
      _builder.append(_join_1);
      CharSequence _peekDone = this.peekDone(action);
      _builder.append(_peekDone);
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("skip");
      final Function1<Action, CharSequence> _function_2 = new Function1<Action, CharSequence>() {
        @Override
        public CharSequence apply(final Action it) {
          return InstancePrinter.this.priorities(it);
        }
      };
      String _join_2 = IterableExtensions.<Action>join(this.priority.get(object), " && ", " && ", "", _function_2);
      _builder_1.append(_join_2);
      CharSequence _peekDone_1 = this.peekDone(action);
      _builder_1.append(_peekDone_1);
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  public CharSequence priorities(final Action action) {
    CharSequence _xifexpression = null;
    boolean _containsKey = this.guards.containsKey(action);
    if (_containsKey) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("!(");
      final Function1<Expression, CharSequence> _function = new Function1<Expression, CharSequence>() {
        @Override
        public CharSequence apply(final Expression it) {
          return InstancePrinter.this.doSwitch(it);
        }
      };
      String _join = IterableExtensions.<Expression>join(this.guards.get(action), " && ", _function);
      _builder.append(_join);
      _builder.append(")");
      CharSequence _peekDone = this.peekDone(action);
      _builder.append(_peekDone);
      _xifexpression = _builder;
    }
    return _xifexpression;
  }
  
  public CharSequence peekDone(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<InstLoad, CharSequence> _function = new Function1<InstLoad, CharSequence>() {
      @Override
      public CharSequence apply(final InstLoad it) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = it.getTarget().getVariable().getName();
        _builder.append(_name);
        _builder.append("_done == 1");
        return _builder.toString();
      }
    };
    String _join = IterableExtensions.<InstLoad>join(this.loadPeeks.get(action), " && ", " && ", "", _function);
    _builder.append(_join);
    return _builder;
  }
  
  public CharSequence inputChannelCheck(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _ports = pattern.getPorts();
      for(final Port port : _ports) {
        _builder.append("&& len(chan_");
        String _simpleName = this.actor.getSimpleName();
        _builder.append(_simpleName);
        _builder.append("_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append(")>=");
        int _numTokens = pattern.getNumTokens(port);
        _builder.append(_numTokens);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence outputChannelCheck(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _ports = pattern.getPorts();
      for(final Port port : _ports) {
        _builder.append("&& chan_");
        String _simpleName = this.actor.getSimpleName();
        _builder.append(_simpleName);
        _builder.append("_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append("_SIZE - len(chan_");
        String _simpleName_1 = this.actor.getSimpleName();
        _builder.append(_simpleName_1);
        _builder.append("_");
        String _name_1 = port.getName();
        _builder.append(_name_1);
        _builder.append(")>=");
        int _numTokens = pattern.getNumTokens(port);
        _builder.append(_numTokens);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence inputPattern(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Var> _variables = pattern.getVariables();
      for(final Var variable : _variables) {
        _builder.append("promela_io_index=0;");
        _builder.newLine();
        _builder.append("do");
        _builder.newLine();
        _builder.append(":: promela_io_index < ");
        Integer _head = IterableExtensions.<Integer>head(variable.getType().getDimensions());
        _builder.append(_head);
        _builder.append(" -> ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("chan_");
        String _simpleName = this.actor.getSimpleName();
        _builder.append(_simpleName, "\t");
        _builder.append("_");
        String _name = pattern.getVarToPortMap().get(variable).getName();
        _builder.append(_name, "\t");
        _builder.append("?");
        String _name_1 = variable.getName();
        _builder.append(_name_1, "\t");
        _builder.append("[promela_io_index];");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("promela_io_index = promela_io_index + 1;");
        _builder.newLine();
        _builder.append(":: else -> break;");
        _builder.newLine();
        _builder.append("od;");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence outputPattern(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Var> _variables = pattern.getVariables();
      for(final Var variable : _variables) {
        _builder.append("promela_io_index=0;");
        _builder.newLine();
        _builder.append("do");
        _builder.newLine();
        _builder.append(":: promela_io_index < ");
        Integer _head = IterableExtensions.<Integer>head(variable.getType().getDimensions());
        _builder.append(_head);
        _builder.append(" -> ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("chan_");
        String _simpleName = this.actor.getSimpleName();
        _builder.append(_simpleName, "\t");
        _builder.append("_");
        String _name = pattern.getVarToPortMap().get(variable).getName();
        _builder.append(_name, "\t");
        _builder.append("!");
        String _name_1 = variable.getName();
        _builder.append(_name_1, "\t");
        _builder.append("[promela_io_index];");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("promela_io_index = promela_io_index + 1;");
        _builder.newLine();
        _builder.append(":: else -> break;");
        _builder.newLine();
        _builder.append("od;");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence printScheduler(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(":: /* ");
    String _name = action.getName();
    _builder.append(_name);
    _builder.append(" */ atomic {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _guard = this.guard(action);
    _builder.append(_guard, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _inputChannelCheck = this.inputChannelCheck(action.getInputPattern());
    _builder.append(_inputChannelCheck, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _outputChannelCheck = this.outputChannelCheck(action.getOutputPattern());
    _builder.append(_outputChannelCheck, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("->");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Temp variables*/");
    _builder.newLine();
    {
      EList<Var> _locals = action.getBody().getLocals();
      for(final Var local : _locals) {
        _builder.append("\t");
        CharSequence _declare = this.declare(local);
        _builder.append(_declare, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _inputPattern = this.inputPattern(action.getInputPattern());
    _builder.append(_inputPattern, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
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
    _builder.newLine();
    _builder.append("\t");
    CharSequence _outputPattern = this.outputPattern(action.getOutputPattern());
    _builder.append(_outputPattern, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("promela_has_progress=1;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("promela_");
    String _simpleName = this.actor.getSimpleName();
    _builder.append(_simpleName, "\t");
    _builder.append("_has_progress=1;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#ifdef PXML");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("printf(\"<iterand actor=\\\"");
    String _simpleName_1 = this.actor.getSimpleName();
    _builder.append(_simpleName_1, "\t");
    _builder.append("\\\" action=\\\"");
    String _name_1 = action.getName();
    _builder.append(_name_1, "\t");
    _builder.append("\\\" repetitions=\\\"1\\\"/>\\n\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("#endif\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#ifdef PNAME");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("printf(\"");
    String _simpleName_2 = this.actor.getSimpleName();
    _builder.append(_simpleName_2, "\t");
    _builder.append(".");
    String _name_2 = action.getName();
    _builder.append(_name_2, "\t");
    _builder.append("();\\n\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("#endif");
    _builder.newLine();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("#ifdef PSTATE");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"");
        final Function1<Var, CharSequence> _function = new Function1<Var, CharSequence>() {
          @Override
          public CharSequence apply(final Var it) {
            StringConcatenation _builder = new StringConcatenation();
            String _name = it.getName();
            _builder.append(_name);
            final Function1<Integer, CharSequence> _function = new Function1<Integer, CharSequence>() {
              @Override
              public CharSequence apply(final Integer it) {
                return "[0]";
              }
            };
            String _join = IterableExtensions.<Integer>join(it.getType().getDimensions(), "", _function);
            _builder.append(_join);
            _builder.append("=%d");
            return _builder.toString();
          }
        };
        String _join = IterableExtensions.<Var>join(this.actor.getStateVars(), ";", _function);
        _builder.append(_join, "\t");
        _builder.append("\\n\\n\", ");
        final Function1<Var, CharSequence> _function_1 = new Function1<Var, CharSequence>() {
          @Override
          public CharSequence apply(final Var it) {
            StringConcatenation _builder = new StringConcatenation();
            String _name = it.getName();
            _builder.append(_name);
            final Function1<Integer, CharSequence> _function = new Function1<Integer, CharSequence>() {
              @Override
              public CharSequence apply(final Integer it) {
                return "[0]";
              }
            };
            String _join = IterableExtensions.<Integer>join(it.getType().getDimensions(), "", _function);
            _builder.append(_join);
            return _builder.toString();
          }
        };
        String _join_1 = IterableExtensions.<Var>join(this.actor.getStateVars(), ",", _function_1);
        _builder.append(_join_1, "\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("#endif");
        _builder.newLine();
      }
    }
    _builder.append("}\t");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence guard(final Action action) {
    return this.guardFSM(action, action);
  }
  
  public CharSequence declareStateVar(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isInitialized = variable.isInitialized();
      if (_isInitialized) {
        {
          boolean _isAssignable = variable.isAssignable();
          boolean _not = (!_isAssignable);
          if (_not) {
            {
              boolean _isList = variable.getType().isList();
              boolean _not_1 = (!_isList);
              if (_not_1) {
                CharSequence _doSwitch = this.doSwitch(variable.getType());
                _builder.append(_doSwitch);
                _builder.append(" ");
                String _name = variable.getName();
                _builder.append(_name);
                _builder.append(" = ");
                String _wrap = this.wrap(this.doSwitch(variable.getInitialValue()));
                _builder.append(_wrap);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              } else {
                CharSequence _declare = this.declare(variable);
                _builder.append(_declare);
                _builder.append(" = ");
                String _wrap_1 = this.wrap(this.doSwitch(variable.getInitialValue()));
                _builder.append(_wrap_1);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          } else {
            CharSequence _declare_1 = this.declare(variable);
            _builder.append(_declare_1);
            _builder.append(" = ");
            String _wrap_2 = this.wrap(this.doSwitch(variable.getInitialValue()));
            _builder.append(_wrap_2);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      } else {
        CharSequence _declare_2 = this.declare(variable);
        _builder.append(_declare_2);
        _builder.append("=0;");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence declare(final Var variable) {
    return this.declare(variable, "");
  }
  
  public CharSequence declare(final Var variable, final String nameSuffix) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(variable.getType());
    _builder.append(_doSwitch);
    _builder.append(" ");
    String _name = variable.getName();
    _builder.append(_name);
    _builder.append(nameSuffix);
    String _printArrayIndexes = this.printArrayIndexes(variable.getType().getDimensionsExpr());
    _builder.append(_printArrayIndexes);
    return _builder;
  }
  
  @Override
  public CharSequence caseInstAssign(final InstAssign assign) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = assign.getTarget().getVariable().getName();
    _builder.append(_name);
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(assign.getValue());
    _builder.append(_doSwitch);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public CharSequence caseInstCall(final InstCall call) {
    CharSequence _xifexpression = null;
    boolean _isPrint = call.isPrint();
    if (_isPrint) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("printf(");
      String _join = IterableExtensions.join(this.printfArgs(call.getArguments()), ", ");
      _builder.append(_join);
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      {
        Def _target = call.getTarget();
        boolean _tripleNotEquals = (_target != null);
        if (_tripleNotEquals) {
          String _name = call.getTarget().getVariable().getName();
          _builder_1.append(_name);
          _builder_1.append(" = ");
        }
      }
      _builder_1.append("1;");
      _builder_1.newLineIfNotEmpty();
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseInstLoad(final InstLoad load) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = load.getTarget().getVariable().getName();
    _builder.append(_name);
    _builder.append(" = ");
    String _name_1 = load.getSource().getVariable().getName();
    _builder.append(_name_1);
    String _printArrayIndexes = this.printArrayIndexes(load.getIndexes());
    _builder.append(_printArrayIndexes);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public CharSequence caseInstStore(final InstStore store) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = store.getTarget().getVariable().getName();
    _builder.append(_name);
    String _printArrayIndexes = this.printArrayIndexes(store.getIndexes());
    _builder.append(_printArrayIndexes);
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(store.getValue());
    _builder.append(_doSwitch);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public CharSequence caseInstReturn(final InstReturn returnInstr) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  @Override
  public CharSequence caseBlockBasic(final BlockBasic block) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Instruction> _instructions = block.getInstructions();
      for(final Instruction instr : _instructions) {
        CharSequence _doSwitch = this.doSwitch(instr);
        _builder.append(_doSwitch);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseBlockIf(final BlockIf blockIf) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if ");
    _builder.newLine();
    _builder.append(":: (");
    CharSequence _doSwitch = this.doSwitch(blockIf.getCondition());
    _builder.append(_doSwitch);
    _builder.append(") -> skip;");
    _builder.newLineIfNotEmpty();
    {
      EList<Block> _thenBlocks = blockIf.getThenBlocks();
      for(final Block block : _thenBlocks) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(block);
        _builder.append(_doSwitch_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(blockIf.getElseBlocks());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append(":: else -> skip;");
        _builder.newLine();
        {
          EList<Block> _elseBlocks = blockIf.getElseBlocks();
          for(final Block block_1 : _elseBlocks) {
            _builder.append("\t");
            CharSequence _doSwitch_2 = this.doSwitch(block_1);
            _builder.append(_doSwitch_2, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("fi;");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _doSwitch_3 = this.doSwitch(blockIf.getJoinBlock());
    _builder.append(_doSwitch_3, "\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public CharSequence caseBlockWhile(final BlockWhile blockWhile) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("do ");
    _builder.newLine();
    _builder.append(":: ");
    CharSequence _doSwitch = this.doSwitch(blockWhile.getCondition());
    _builder.append(_doSwitch);
    _builder.append(" -> skip;");
    _builder.newLineIfNotEmpty();
    {
      EList<Block> _blocks = blockWhile.getBlocks();
      for(final Block block : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(block);
        _builder.append(_doSwitch_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append(":: else -> break;");
    _builder.newLine();
    _builder.append("od;");
    _builder.newLine();
    _builder.newLine();
    CharSequence _doSwitch_2 = this.doSwitch(blockWhile.getJoinBlock());
    _builder.append(_doSwitch_2);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
