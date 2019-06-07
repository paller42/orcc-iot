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
 * 
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
package net.sf.orcc.backends.llvm.jit;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.sf.orcc.backends.ir.InstCast;
import net.sf.orcc.backends.llvm.aot.InstancePrinter;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.QSDFMoC;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

/**
 * Generate Jade content
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class ActorPrinter extends InstancePrinter {
  private final List<Integer> objRefList = new ArrayList<Integer>();
  
  private final List<Pattern> patternList = new ArrayList<Pattern>();
  
  @Override
  public void setActor(final Actor actor) {
    super.setActor(actor);
    this.patternList.clear();
    this.computePatterns();
    this.objRefList.clear();
    this.computeCastedList();
  }
  
  @Override
  public void setInstance(final Instance instance) {
    throw new UnsupportedOperationException(
      ("Jade backend is unable" + 
        " to generate code from Instances"));
  }
  
  /**
   * Return the unique id associated with the given object, prepended with '!'
   * 
   * @param object the object
   * @return unique reference to the given object
   */
  private String getObjectReference(final Object object) {
    int id = this.objRefList.indexOf(Integer.valueOf(object.hashCode()));
    if ((id == (-1))) {
      id = this.objRefList.size();
      this.objRefList.add(Integer.valueOf(object.hashCode()));
    }
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("!");
    _builder.append(id);
    return _builder.toString();
  }
  
  @Override
  public CharSequence getContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; Generated from \"");
    String _name = this.actor.getName();
    _builder.append(_name);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("declare i32 @printf(i8* noalias , ...) nounwind");
    _builder.newLine();
    _builder.newLine();
    {
      boolean _isEmpty = this.actor.getInputs().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        _builder.newLine();
        _builder.append("; Input FIFOs");
        _builder.newLine();
        {
          EList<Port> _inputs = this.actor.getInputs();
          for(final Port input : _inputs) {
            CharSequence _fifo = this.fifo(input);
            _builder.append(_fifo);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_1 = this.actor.getOutputs().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        _builder.newLine();
        _builder.append("; Output FIFOs");
        _builder.newLine();
        {
          EList<Port> _outputs = this.actor.getOutputs();
          for(final Port output : _outputs) {
            CharSequence _fifo_1 = this.fifo(output);
            _builder.append(_fifo_1);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_2 = this.actor.getParameters().isEmpty();
      boolean _not_2 = (!_isEmpty_2);
      if (_not_2) {
        _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        _builder.newLine();
        _builder.append("; Parameter values of the instance");
        _builder.newLine();
        {
          EList<Var> _parameters = this.actor.getParameters();
          for(final Var param : _parameters) {
            CharSequence _actorParameter = this.actorParameter(param);
            _builder.append(_actorParameter);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_3 = this.actor.getStateVars().isEmpty();
      boolean _not_3 = (!_isEmpty_3);
      if (_not_3) {
        _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        _builder.newLine();
        _builder.append("; State variables of the actor");
        _builder.newLine();
        {
          EList<Var> _stateVars = this.actor.getStateVars();
          for(final Var variable : _stateVars) {
            CharSequence _declare = this.declare(variable);
            _builder.append(_declare);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_4 = this.actor.getProcs().isEmpty();
      boolean _not_4 = (!_isEmpty_4);
      if (_not_4) {
        _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        _builder.newLine();
        _builder.append("; Functions/procedures");
        _builder.newLine();
        {
          EList<Procedure> _procs = this.actor.getProcs();
          for(final Procedure proc : _procs) {
            CharSequence _print = this.print(proc);
            _builder.append(_print);
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_5 = this.actor.getInitializes().isEmpty();
      boolean _not_5 = (!_isEmpty_5);
      if (_not_5) {
        _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        _builder.newLine();
        _builder.append("; Initializes");
        _builder.newLine();
        {
          EList<Action> _initializes = this.actor.getInitializes();
          for(final Action action : _initializes) {
            CharSequence _print_1 = this.print(action);
            _builder.append(_print_1);
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; Actions");
    _builder.newLine();
    {
      EList<Action> _actions = this.actor.getActions();
      for(final Action action_1 : _actions) {
        CharSequence _print_2 = this.print(action_1);
        _builder.append(_print_2);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.newLine();
    CharSequence _decl_MD = this.decl_MD();
    _builder.append(_decl_MD);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence decl_MD() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("!source = !{");
    String _objectReference = this.getObjectReference(this.actor.getFile().getFullPath());
    _builder.append(_objectReference);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("!name = !{");
    String _objectReference_1 = this.getObjectReference(this.actor.getName());
    _builder.append(_objectReference_1);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("!action_scheduler = !{");
    String _objectReference_2 = this.getObjectReference(this.actor);
    _builder.append(_objectReference_2);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = this.actor.getInputs().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("!inputs = !{");
        final Function1<Port, CharSequence> _function = new Function1<Port, CharSequence>() {
          @Override
          public CharSequence apply(final Port it) {
            return ActorPrinter.this.getObjectReference(it);
          }
        };
        String _join = IterableExtensions.<Port>join(this.actor.getInputs(), ", ", _function);
        _builder.append(_join);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty_1 = this.actor.getOutputs().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("!outputs = !{");
        final Function1<Port, CharSequence> _function_1 = new Function1<Port, CharSequence>() {
          @Override
          public CharSequence apply(final Port it) {
            return ActorPrinter.this.getObjectReference(it);
          }
        };
        String _join_1 = IterableExtensions.<Port>join(this.actor.getOutputs(), ", ", _function_1);
        _builder.append(_join_1);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty_2 = this.actor.getParameters().isEmpty();
      boolean _not_2 = (!_isEmpty_2);
      if (_not_2) {
        _builder.append("!parameters = !{");
        final Function1<Var, CharSequence> _function_2 = new Function1<Var, CharSequence>() {
          @Override
          public CharSequence apply(final Var it) {
            return ActorPrinter.this.getObjectReference(it);
          }
        };
        String _join_2 = IterableExtensions.<Var>join(this.actor.getParameters(), ", ", _function_2);
        _builder.append(_join_2);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty_3 = this.actor.getStateVars().isEmpty();
      boolean _not_3 = (!_isEmpty_3);
      if (_not_3) {
        _builder.append("!state_variables = !{");
        final Function1<Var, CharSequence> _function_3 = new Function1<Var, CharSequence>() {
          @Override
          public CharSequence apply(final Var it) {
            return ActorPrinter.this.getObjectReference(it);
          }
        };
        String _join_3 = IterableExtensions.<Var>join(this.actor.getStateVars(), ", ", _function_3);
        _builder.append(_join_3);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_4 = this.actor.getProcs().isEmpty();
      boolean _not_4 = (!_isEmpty_4);
      if (_not_4) {
        _builder.append("!procedures = !{");
        final Function1<Procedure, CharSequence> _function_4 = new Function1<Procedure, CharSequence>() {
          @Override
          public CharSequence apply(final Procedure it) {
            return ActorPrinter.this.getObjectReference(it);
          }
        };
        String _join_4 = IterableExtensions.<Procedure>join(this.actor.getProcs(), ", ", _function_4);
        _builder.append(_join_4);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty_5 = this.actor.getInitializes().isEmpty();
      boolean _not_5 = (!_isEmpty_5);
      if (_not_5) {
        _builder.append("!initializes = !{");
        final Function1<Action, CharSequence> _function_5 = new Function1<Action, CharSequence>() {
          @Override
          public CharSequence apply(final Action it) {
            return ActorPrinter.this.getObjectReference(it);
          }
        };
        String _join_5 = IterableExtensions.<Action>join(this.actor.getInitializes(), ", ", _function_5);
        _builder.append(_join_5);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty_6 = this.actor.getActions().isEmpty();
      boolean _not_6 = (!_isEmpty_6);
      if (_not_6) {
        _builder.append("!actions = !{");
        final Function1<Action, CharSequence> _function_6 = new Function1<Action, CharSequence>() {
          @Override
          public CharSequence apply(final Action it) {
            return ActorPrinter.this.getObjectReference(it);
          }
        };
        String _join_6 = IterableExtensions.<Action>join(this.actor.getActions(), ", ", _function_6);
        _builder.append(_join_6);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      MoC _moC = this.actor.getMoC();
      boolean _tripleNotEquals = (_moC != null);
      if (_tripleNotEquals) {
        _builder.append("!MoC = !{");
        String _objectReference_3 = this.getObjectReference(this.actor.getMoC());
        _builder.append(_objectReference_3);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    String _objectReference_4 = this.getObjectReference(this.actor.getFile().getFullPath());
    _builder.append(_objectReference_4);
    _builder.append(" = metadata !{");
    CharSequence _name_MD = this.name_MD(this.actor.getFile().getFullPath().toString());
    _builder.append(_name_MD);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    String _objectReference_5 = this.getObjectReference(this.actor.getName());
    _builder.append(_objectReference_5);
    _builder.append(" = metadata !{");
    CharSequence _name_MD_1 = this.name_MD(this.actor.getName());
    _builder.append(_name_MD_1);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("; Action-scheduler");
    _builder.newLine();
    CharSequence _actionScheduler_MD = this.actionScheduler_MD();
    _builder.append(_actionScheduler_MD);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty_7 = this.actor.getInputs().isEmpty();
      boolean _not_7 = (!_isEmpty_7);
      if (_not_7) {
        _builder.newLine();
        _builder.append("; Input ports");
        _builder.newLine();
        {
          EList<Port> _inputs = this.actor.getInputs();
          for(final Port port : _inputs) {
            String _objectReference_6 = this.getObjectReference(port);
            _builder.append(_objectReference_6);
            _builder.append(" = metadata !{metadata ");
            String _objectReference_7 = this.getObjectReference(port.getType());
            _builder.append(_objectReference_7);
            _builder.append(", ");
            CharSequence _name_MD_2 = this.name_MD(port.getName());
            _builder.append(_name_MD_2);
            _builder.append(", ");
            CharSequence _doSwitch = this.doSwitch(port.getType());
            _builder.append(_doSwitch);
            _builder.append("** ");
            CharSequence _fifoVarName = this.fifoVarName(port);
            _builder.append(_fifoVarName);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
            String _objectReference_8 = this.getObjectReference(port.getType());
            _builder.append(_objectReference_8);
            _builder.append(" = metadata !{");
            CharSequence _varType_MD = this.varType_MD(port.getType());
            _builder.append(_varType_MD);
            _builder.append(", ");
            CharSequence _varSize_MD = this.varSize_MD(port.getType());
            _builder.append(_varSize_MD);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _isEmpty_8 = this.actor.getOutputs().isEmpty();
      boolean _not_8 = (!_isEmpty_8);
      if (_not_8) {
        _builder.newLine();
        _builder.append("; Output ports");
        _builder.newLine();
        {
          EList<Port> _outputs = this.actor.getOutputs();
          for(final Port port_1 : _outputs) {
            String _objectReference_9 = this.getObjectReference(port_1);
            _builder.append(_objectReference_9);
            _builder.append(" = metadata !{metadata ");
            String _objectReference_10 = this.getObjectReference(port_1.getType());
            _builder.append(_objectReference_10);
            _builder.append(", ");
            CharSequence _name_MD_3 = this.name_MD(port_1.getName());
            _builder.append(_name_MD_3);
            _builder.append(", ");
            CharSequence _doSwitch_1 = this.doSwitch(port_1.getType());
            _builder.append(_doSwitch_1);
            _builder.append("** ");
            CharSequence _fifoVarName_1 = this.fifoVarName(port_1);
            _builder.append(_fifoVarName_1);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
            String _objectReference_11 = this.getObjectReference(port_1.getType());
            _builder.append(_objectReference_11);
            _builder.append(" = metadata !{");
            CharSequence _varType_MD_1 = this.varType_MD(port_1.getType());
            _builder.append(_varType_MD_1);
            _builder.append(", ");
            CharSequence _varSize_MD_1 = this.varSize_MD(port_1.getType());
            _builder.append(_varSize_MD_1);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _isEmpty_9 = this.actor.getParameters().isEmpty();
      boolean _not_9 = (!_isEmpty_9);
      if (_not_9) {
        _builder.newLine();
        _builder.append("; Parameters");
        _builder.newLine();
        {
          EList<Var> _parameters = this.actor.getParameters();
          for(final Var param : _parameters) {
            _builder.append("; ");
            String _name = param.getName();
            _builder.append(_name);
            _builder.newLineIfNotEmpty();
            String _objectReference_12 = this.getObjectReference(param);
            _builder.append(_objectReference_12);
            _builder.append(" = metadata !{metadata ");
            String _objectReference_13 = this.getObjectReference(param.getName());
            _builder.append(_objectReference_13);
            _builder.append(", metadata ");
            String _objectReference_14 = this.getObjectReference(param.getType());
            _builder.append(_objectReference_14);
            _builder.append(", ");
            CharSequence _doSwitch_2 = this.doSwitch(param.getType());
            _builder.append(_doSwitch_2);
            _builder.append("* @");
            String _name_1 = param.getName();
            _builder.append(_name_1);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
            String _objectReference_15 = this.getObjectReference(param.getName());
            _builder.append(_objectReference_15);
            _builder.append(" = metadata !{");
            CharSequence _name_MD_4 = this.name_MD(param.getName());
            _builder.append(_name_MD_4);
            _builder.append(", ");
            CharSequence _varAssignable_MD = this.varAssignable_MD(param);
            _builder.append(_varAssignable_MD);
            _builder.append(", i32 0, ");
            CharSequence _varIndex_MD = this.varIndex_MD(param);
            _builder.append(_varIndex_MD);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
            String _objectReference_16 = this.getObjectReference(param.getType());
            _builder.append(_objectReference_16);
            _builder.append(" = metadata  !{");
            CharSequence _varType_MD_2 = this.varType_MD(param.getType());
            _builder.append(_varType_MD_2);
            _builder.append(", ");
            CharSequence _varSize_MD_2 = this.varSize_MD(param.getType());
            _builder.append(_varSize_MD_2);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _isEmpty_10 = this.actor.getStateVars().isEmpty();
      boolean _not_10 = (!_isEmpty_10);
      if (_not_10) {
        _builder.newLine();
        _builder.append("; State Variables");
        _builder.newLine();
        {
          EList<Var> _stateVars = this.actor.getStateVars();
          for(final Var stateVar : _stateVars) {
            _builder.append(";; ");
            String _name_2 = stateVar.getName();
            _builder.append(_name_2);
            _builder.newLineIfNotEmpty();
            String _objectReference_17 = this.getObjectReference(stateVar);
            _builder.append(_objectReference_17);
            _builder.append(" = metadata !{metadata ");
            String _objectReference_18 = this.getObjectReference(stateVar.getName());
            _builder.append(_objectReference_18);
            _builder.append(", metadata ");
            String _objectReference_19 = this.getObjectReference(stateVar.getType());
            _builder.append(_objectReference_19);
            _builder.append(", ");
            {
              boolean _isInitialized = stateVar.isInitialized();
              if (_isInitialized) {
                _builder.append("metadata ");
                String _objectReference_20 = this.getObjectReference(stateVar.getInitialValue());
                _builder.append(_objectReference_20);
              } else {
                _builder.append("null");
              }
            }
            _builder.append(", ");
            CharSequence _doSwitch_3 = this.doSwitch(stateVar.getType());
            _builder.append(_doSwitch_3);
            _builder.append("* @");
            String _name_3 = stateVar.getName();
            _builder.append(_name_3);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
            String _objectReference_21 = this.getObjectReference(stateVar.getName());
            _builder.append(_objectReference_21);
            _builder.append(" = metadata !{");
            CharSequence _name_MD_5 = this.name_MD(stateVar.getName());
            _builder.append(_name_MD_5);
            _builder.append(", ");
            CharSequence _varAssignable_MD_1 = this.varAssignable_MD(stateVar);
            _builder.append(_varAssignable_MD_1);
            _builder.append(", i32 0, ");
            CharSequence _varIndex_MD_1 = this.varIndex_MD(stateVar);
            _builder.append(_varIndex_MD_1);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
            String _objectReference_22 = this.getObjectReference(stateVar.getType());
            _builder.append(_objectReference_22);
            _builder.append(" = metadata !{");
            CharSequence _varType_MD_3 = this.varType_MD(stateVar.getType());
            _builder.append(_varType_MD_3);
            _builder.append(", ");
            CharSequence _varSize_MD_3 = this.varSize_MD(stateVar.getType());
            _builder.append(_varSize_MD_3);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
            {
              boolean _isInitialized_1 = stateVar.isInitialized();
              if (_isInitialized_1) {
                String _objectReference_23 = this.getObjectReference(stateVar.getInitialValue());
                _builder.append(_objectReference_23);
                _builder.append(" = metadata !{");
                CharSequence _doSwitch_4 = this.doSwitch(stateVar.getType());
                _builder.append(_doSwitch_4);
                _builder.append(" ");
                CharSequence _doSwitch_5 = this.doSwitch(stateVar.getInitialValue());
                _builder.append(_doSwitch_5);
                _builder.append("}");
              }
            }
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _isEmpty_11 = this.actor.getProcs().isEmpty();
      boolean _not_11 = (!_isEmpty_11);
      if (_not_11) {
        _builder.newLine();
        _builder.append("; Functions and procedures");
        _builder.newLine();
        {
          EList<Procedure> _procs = this.actor.getProcs();
          for(final Procedure proc : _procs) {
            String _objectReference_24 = this.getObjectReference(proc);
            _builder.append(_objectReference_24);
            _builder.append(" = metadata !{");
            CharSequence _name_MD_6 = this.name_MD(proc.getName());
            _builder.append(_name_MD_6);
            _builder.append(", ");
            CharSequence _procNative_MD = this.procNative_MD(proc);
            _builder.append(_procNative_MD);
            _builder.append(", ");
            CharSequence _doSwitch_6 = this.doSwitch(proc.getReturnType());
            _builder.append(_doSwitch_6);
            _builder.append("(");
            final Function1<Param, CharSequence> _function_7 = new Function1<Param, CharSequence>() {
              @Override
              public CharSequence apply(final Param it) {
                return ActorPrinter.this.argumentTypeDeclaration(it);
              }
            };
            String _wrap = this.wrap(IterableExtensions.<Param>join(proc.getParameters(), ", ", _function_7));
            _builder.append(_wrap);
            _builder.append(")* @");
            String _name_4 = proc.getName();
            _builder.append(_name_4);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _isEmpty_12 = this.actor.getInitializes().isEmpty();
      boolean _not_12 = (!_isEmpty_12);
      if (_not_12) {
        _builder.newLine();
        _builder.append("; Initializes");
        _builder.newLine();
        {
          EList<Action> _initializes = this.actor.getInitializes();
          for(final Action action : _initializes) {
            CharSequence _action_MD = this.action_MD(action);
            _builder.append(_action_MD);
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("; Actions");
    _builder.newLine();
    {
      EList<Action> _actions = this.actor.getActions();
      for(final Action action_1 : _actions) {
        CharSequence _action_MD_1 = this.action_MD(action_1);
        _builder.append(_action_MD_1);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("; Patterns");
    _builder.newLine();
    {
      for(final Pattern pattern : this.patternList) {
        CharSequence _pattern_MD = this.pattern_MD(pattern);
        _builder.append(_pattern_MD);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("; Variables of patterns");
    _builder.newLine();
    {
      final Function1<Pattern, Boolean> _function_8 = new Function1<Pattern, Boolean>() {
        @Override
        public Boolean apply(final Pattern it) {
          boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(it.getPortToVarMap());
          return Boolean.valueOf((!_isNullOrEmpty));
        }
      };
      Iterable<Pattern> _filter = IterableExtensions.<Pattern>filter(this.patternList, _function_8);
      for(final Pattern pattern_1 : _filter) {
        String _objectReference_25 = this.getObjectReference(pattern_1.getPortToVarMap());
        _builder.append(_objectReference_25);
        _builder.append(" = metadata !{");
        final Function1<Port, CharSequence> _function_9 = new Function1<Port, CharSequence>() {
          @Override
          public CharSequence apply(final Port it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("metadata ");
            String _objectReference = ActorPrinter.this.getObjectReference(it);
            _builder.append(_objectReference);
            return _builder.toString();
          }
        };
        String _join_7 = IterableExtensions.<Port>join(pattern_1.getPorts(), ", ", _function_9);
        _builder.append(_join_7);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("; Number of tokens of patterns");
    _builder.newLine();
    {
      final Function1<Pattern, Boolean> _function_10 = new Function1<Pattern, Boolean>() {
        @Override
        public Boolean apply(final Pattern it) {
          boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(it.getNumTokensMap());
          return Boolean.valueOf((!_isNullOrEmpty));
        }
      };
      Iterable<Pattern> _filter_1 = IterableExtensions.<Pattern>filter(this.patternList, _function_10);
      for(final Pattern pattern_2 : _filter_1) {
        String _objectReference_26 = this.getObjectReference(pattern_2.getNumTokensMap());
        _builder.append(_objectReference_26);
        _builder.append(" = metadata !{");
        final Function1<Port, CharSequence> _function_11 = new Function1<Port, CharSequence>() {
          @Override
          public CharSequence apply(final Port it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("metadata ");
            String _objectReference = ActorPrinter.this.getObjectReference(it);
            _builder.append(_objectReference);
            _builder.append(", i32 ");
            Integer _get = pattern_2.getNumTokensMap().get(it);
            _builder.append(_get);
            return _builder.toString();
          }
        };
        String _join_8 = IterableExtensions.<Port>join(pattern_2.getPorts(), ", ", _function_11);
        _builder.append(_join_8);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _hasMoC = this.actor.hasMoC();
      if (_hasMoC) {
        _builder.newLine();
        _builder.append("; MoC");
        _builder.newLine();
        CharSequence _moC_MD = this.moC_MD();
        _builder.append(_moC_MD);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence moC_MD() {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isCSDF = this.actor.getMoC().isCSDF();
      if (_isCSDF) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(" ");
        _builder.append(", metadata ");
        String _objectReference = this.getObjectReference("csdfmoc_hack_key");
        _builder.append(_objectReference, " ");
        _xifexpression = _builder.toString();
      } else {
        String _xifexpression_1 = null;
        boolean _isQuasiStatic = this.actor.getMoC().isQuasiStatic();
        if (_isQuasiStatic) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(" ");
          _builder_1.append(", ");
          MoC _moC = this.actor.getMoC();
          final Function1<Action, CharSequence> _function = new Function1<Action, CharSequence>() {
            @Override
            public CharSequence apply(final Action it) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("metadata ");
              String _objectReference = ActorPrinter.this.getObjectReference(it);
              _builder.append(_objectReference);
              return _builder.toString();
            }
          };
          String _join = IterableExtensions.<Action>join(((QSDFMoC) _moC).getActions(), ", ", _function);
          _builder_1.append(_join, " ");
          _xifexpression_1 = _builder_1.toString();
        } else {
          _xifexpression_1 = "";
        }
        _xifexpression = _xifexpression_1;
      }
      final String value = _xifexpression;
      StringConcatenation _builder_2 = new StringConcatenation();
      String _objectReference_1 = this.getObjectReference(this.actor.getMoC());
      _builder_2.append(_objectReference_1);
      _builder_2.append(" = metadata !{metadata !\"");
      String _shortName = this.actor.getMoC().getShortName();
      _builder_2.append(_shortName);
      _builder_2.append("\"");
      _builder_2.append(value);
      _builder_2.append("}");
      _builder_2.newLineIfNotEmpty();
      _builder_2.newLine();
      {
        boolean _isCSDF_1 = this.actor.getMoC().isCSDF();
        if (_isCSDF_1) {
          MoC _moC_1 = this.actor.getMoC();
          CharSequence _MoC_CSDF_MD = this.MoC_CSDF_MD(((CSDFMoC) _moC_1));
          _builder_2.append(_MoC_CSDF_MD);
          _builder_2.newLineIfNotEmpty();
        } else {
          boolean _isQuasiStatic_1 = this.actor.getMoC().isQuasiStatic();
          if (_isQuasiStatic_1) {
            {
              MoC _moC_2 = this.actor.getMoC();
              Set<Action> _actions = ((QSDFMoC) _moC_2).getActions();
              for(final Action action : _actions) {
                String _objectReference_2 = this.getObjectReference(action);
                _builder_2.append(_objectReference_2);
                _builder_2.append(" = metadata !{metadata ");
                CharSequence _action_MD = this.action_MD(action);
                _builder_2.append(_action_MD);
                _builder_2.append(", metadata ");
                MoC _moC_3 = this.actor.getMoC();
                MoC _get = ((QSDFMoC) _moC_3).getConfigurations().get(action);
                String _objectReference_3 = this.getObjectReference(((CSDFMoC) _get));
                _builder_2.append(_objectReference_3);
                _builder_2.append("}");
                _builder_2.newLineIfNotEmpty();
                _builder_2.newLine();
                MoC _moC_4 = this.actor.getMoC();
                MoC _get_1 = ((QSDFMoC) _moC_4).getConfigurations().get(action);
                CharSequence _MoC_CSDF_MD_1 = this.MoC_CSDF_MD(((CSDFMoC) _get_1));
                _builder_2.append(_MoC_CSDF_MD_1);
                _builder_2.newLineIfNotEmpty();
              }
            }
          }
        }
      }
      _xblockexpression = _builder_2;
    }
    return _xblockexpression;
  }
  
  private CharSequence MoC_CSDF_MD(final CSDFMoC csdfmoc) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isEmpty = csdfmoc.getInputPattern().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("metadata ");
        String _objectReference = this.getObjectReference(csdfmoc.getInputPattern());
        _builder.append(_objectReference);
        _xifexpression = _builder.toString();
      } else {
        _xifexpression = "null";
      }
      final String inPattern = _xifexpression;
      String _xifexpression_1 = null;
      boolean _isEmpty_1 = csdfmoc.getOutputPattern().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("metadata ");
        String _objectReference_1 = this.getObjectReference(csdfmoc.getOutputPattern());
        _builder_1.append(_objectReference_1);
        _xifexpression_1 = _builder_1.toString();
      } else {
        _xifexpression_1 = "null";
      }
      final String outPattern = _xifexpression_1;
      StringConcatenation _builder_2 = new StringConcatenation();
      String _objectReference_2 = this.getObjectReference("csdfmoc_hack_key");
      _builder_2.append(_objectReference_2);
      _builder_2.append(" = metadata !{i32 ");
      int _numberOfPhases = csdfmoc.getNumberOfPhases();
      _builder_2.append(_numberOfPhases);
      _builder_2.append(" , ");
      _builder_2.append(inPattern);
      _builder_2.append(", ");
      _builder_2.append(outPattern);
      _builder_2.append(", metadata ");
      String _objectReference_3 = this.getObjectReference(csdfmoc.getInvocations());
      _builder_2.append(_objectReference_3);
      _builder_2.append("}");
      _builder_2.newLineIfNotEmpty();
      String _objectReference_4 = this.getObjectReference(csdfmoc.getInvocations());
      _builder_2.append(_objectReference_4);
      _builder_2.append(" = metadata !{");
      final Function1<Invocation, CharSequence> _function = new Function1<Invocation, CharSequence>() {
        @Override
        public CharSequence apply(final Invocation it) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("metadata ");
          String _objectReference = ActorPrinter.this.getObjectReference(it.getAction());
          _builder.append(_objectReference);
          return _builder.toString();
        }
      };
      String _join = IterableExtensions.<Invocation>join(csdfmoc.getInvocations(), ", ", _function);
      _builder_2.append(_join);
      _builder_2.append("}");
      _builder_2.newLineIfNotEmpty();
      _xblockexpression = _builder_2;
    }
    return _xblockexpression;
  }
  
  private CharSequence pattern_MD(final Pattern pattern) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(pattern.getNumTokensMap());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("metadata ");
        String _objectReference = this.getObjectReference(pattern.getNumTokensMap());
        _builder.append(_objectReference);
        _xifexpression = _builder.toString();
      } else {
        _xifexpression = "null";
      }
      final String numTokens = _xifexpression;
      String _xifexpression_1 = null;
      boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(pattern.getPortToVarMap());
      boolean _not_1 = (!_isNullOrEmpty_1);
      if (_not_1) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("metadata ");
        String _objectReference_1 = this.getObjectReference(pattern.getPortToVarMap());
        _builder_1.append(_objectReference_1);
        _xifexpression_1 = _builder_1.toString();
      } else {
        _xifexpression_1 = "null";
      }
      final String variables = _xifexpression_1;
      StringConcatenation _builder_2 = new StringConcatenation();
      String _objectReference_2 = this.getObjectReference(pattern);
      _builder_2.append(_objectReference_2);
      _builder_2.append(" = metadata !{");
      _builder_2.append(numTokens);
      _builder_2.append(", ");
      _builder_2.append(variables);
      _builder_2.append("}");
      _builder_2.newLineIfNotEmpty();
      _xblockexpression = _builder_2;
    }
    return _xblockexpression;
  }
  
  private CharSequence action_MD(final Action action) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isEmpty = action.getTag().getIdentifiers().isEmpty();
      if (_isEmpty) {
        _xifexpression = "null";
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("metadata ");
        String _objectReference = this.getObjectReference(action.getTag());
        _builder.append(_objectReference);
        _xifexpression = _builder.toString();
      }
      final String tag = _xifexpression;
      String _xifexpression_1 = null;
      boolean _isEmpty_1 = action.getInputPattern().isEmpty();
      if (_isEmpty_1) {
        _xifexpression_1 = "null";
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("metadata ");
        String _objectReference_1 = this.getObjectReference(action.getInputPattern());
        _builder_1.append(_objectReference_1);
        _xifexpression_1 = _builder_1.toString();
      }
      final String input = _xifexpression_1;
      String _xifexpression_2 = null;
      boolean _isEmpty_2 = action.getOutputPattern().isEmpty();
      if (_isEmpty_2) {
        _xifexpression_2 = "null";
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("metadata ");
        String _objectReference_2 = this.getObjectReference(action.getOutputPattern());
        _builder_2.append(_objectReference_2);
        _xifexpression_2 = _builder_2.toString();
      }
      final String output = _xifexpression_2;
      String _xifexpression_3 = null;
      boolean _isEmpty_3 = action.getPeekPattern().isEmpty();
      if (_isEmpty_3) {
        _xifexpression_3 = "null";
      } else {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("metadata ");
        String _objectReference_3 = this.getObjectReference(action.getPeekPattern());
        _builder_3.append(_objectReference_3);
        _xifexpression_3 = _builder_3.toString();
      }
      final String peek = _xifexpression_3;
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append(";; ");
      String _name = action.getName();
      _builder_4.append(_name);
      _builder_4.newLineIfNotEmpty();
      String _objectReference_4 = this.getObjectReference(action);
      _builder_4.append(_objectReference_4);
      _builder_4.append(" = metadata !{");
      _builder_4.append(tag);
      _builder_4.append(", ");
      _builder_4.append(input);
      _builder_4.append(", ");
      _builder_4.append(output);
      _builder_4.append(", ");
      _builder_4.append(peek);
      _builder_4.append(", metadata ");
      String _objectReference_5 = this.getObjectReference(action.getScheduler());
      _builder_4.append(_objectReference_5);
      _builder_4.append(", metadata ");
      String _objectReference_6 = this.getObjectReference(action.getBody());
      _builder_4.append(_objectReference_6);
      _builder_4.append("}");
      _builder_4.newLineIfNotEmpty();
      {
        boolean _isEmpty_4 = action.getTag().getIdentifiers().isEmpty();
        boolean _not = (!_isEmpty_4);
        if (_not) {
          String _objectReference_7 = this.getObjectReference(action.getTag());
          _builder_4.append(_objectReference_7);
          _builder_4.append(" = metadata  !{");
          final Function1<String, CharSequence> _function = new Function1<String, CharSequence>() {
            @Override
            public CharSequence apply(final String it) {
              return ActorPrinter.this.name_MD(it);
            }
          };
          String _join = IterableExtensions.<String>join(action.getTag().getIdentifiers(), ", ", _function);
          _builder_4.append(_join);
          _builder_4.append("}");
          _builder_4.newLineIfNotEmpty();
        }
      }
      String _objectReference_8 = this.getObjectReference(action.getScheduler());
      _builder_4.append(_objectReference_8);
      _builder_4.append(" = metadata  !{");
      CharSequence _name_MD = this.name_MD(action.getScheduler().getName());
      _builder_4.append(_name_MD);
      _builder_4.append(", ");
      CharSequence _procNative_MD = this.procNative_MD(action.getScheduler());
      _builder_4.append(_procNative_MD);
      _builder_4.append(", i1()* @");
      String _name_1 = action.getScheduler().getName();
      _builder_4.append(_name_1);
      _builder_4.append("}");
      _builder_4.newLineIfNotEmpty();
      String _objectReference_9 = this.getObjectReference(action.getBody());
      _builder_4.append(_objectReference_9);
      _builder_4.append(" = metadata  !{");
      CharSequence _name_MD_1 = this.name_MD(action.getBody().getName());
      _builder_4.append(_name_MD_1);
      _builder_4.append(", ");
      CharSequence _procNative_MD_1 = this.procNative_MD(action.getBody());
      _builder_4.append(_procNative_MD_1);
      _builder_4.append(", void()* @");
      String _name_2 = action.getBody().getName();
      _builder_4.append(_name_2);
      _builder_4.append("}");
      _builder_4.newLineIfNotEmpty();
      _xblockexpression = _builder_4;
    }
    return _xblockexpression;
  }
  
  private CharSequence argumentTypeDeclaration(final Param param) {
    CharSequence _xifexpression = null;
    boolean _isString = param.getVariable().getType().isString();
    if (_isString) {
      _xifexpression = "i8*";
    } else {
      CharSequence _xifexpression_1 = null;
      boolean _isList = param.getVariable().getType().isList();
      if (_isList) {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _doSwitch = this.doSwitch(param.getVariable().getType());
        _builder.append(_doSwitch);
        _builder.append("*");
        _xifexpression_1 = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _doSwitch_1 = this.doSwitch(param.getVariable().getType());
        _builder_1.append(_doSwitch_1);
        _xifexpression_1 = _builder_1;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  private CharSequence procNative_MD(final Procedure proc) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isNative = proc.isNative();
      if (_isNative) {
        _builder.append("i1 1");
      } else {
        _builder.append("i1 0");
      }
    }
    return _builder;
  }
  
  private CharSequence varIndex_MD(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("i32 ");
    int _index = variable.getIndex();
    _builder.append(_index);
    return _builder;
  }
  
  private CharSequence varAssignable_MD(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("i1 ");
    {
      boolean _isAssignable = variable.isAssignable();
      if (_isAssignable) {
        _builder.append("1");
      } else {
        _builder.append("0");
      }
    }
    return _builder;
  }
  
  private CharSequence varSize_MD(final Type type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isList = type.isList();
      if (_isList) {
        final Function1<Integer, CharSequence> _function = new Function1<Integer, CharSequence>() {
          @Override
          public CharSequence apply(final Integer it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("i32 ");
            _builder.append(it);
            return _builder.toString();
          }
        };
        String _join = IterableExtensions.<Integer>join(((TypeList) type).getDimensions(), ", ", _function);
        _builder.append(_join);
      } else {
        _builder.append("null");
      }
    }
    return _builder;
  }
  
  private CharSequence name_MD(final String name) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("metadata !\"");
    _builder.append(name);
    _builder.append("\"");
    return _builder;
  }
  
  private CharSequence actionScheduler_MD() {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("metadata ");
        String _objectReference = this.getObjectReference(this.actor.getActionsOutsideFsm());
        _builder.append(_objectReference);
        _xifexpression = _builder.toString();
      } else {
        _xifexpression = "null";
      }
      final String outsideFSM = _xifexpression;
      String _xifexpression_1 = null;
      FSM _fsm = this.actor.getFsm();
      boolean _tripleNotEquals = (_fsm != null);
      if (_tripleNotEquals) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("metadata ");
        String _objectReference_1 = this.getObjectReference(this.actor.getFsm());
        _builder_1.append(_objectReference_1);
        _xifexpression_1 = _builder_1.toString();
      } else {
        _xifexpression_1 = "null";
      }
      final String fsm = _xifexpression_1;
      StringConcatenation _builder_2 = new StringConcatenation();
      String _objectReference_2 = this.getObjectReference(this.actor);
      _builder_2.append(_objectReference_2);
      _builder_2.append(" = metadata !{");
      _builder_2.append(outsideFSM);
      _builder_2.append(", ");
      _builder_2.append(fsm);
      _builder_2.append("}");
      _builder_2.newLineIfNotEmpty();
      {
        boolean _isEmpty_1 = this.actor.getActionsOutsideFsm().isEmpty();
        boolean _not_1 = (!_isEmpty_1);
        if (_not_1) {
          _builder_2.newLine();
          _builder_2.append(";; Actions outside FSM");
          _builder_2.newLine();
          String _objectReference_3 = this.getObjectReference(this.actor.getActionsOutsideFsm());
          _builder_2.append(_objectReference_3);
          _builder_2.append(" = metadata !{");
          final Function1<Action, CharSequence> _function = new Function1<Action, CharSequence>() {
            @Override
            public CharSequence apply(final Action it) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("metadata ");
              String _objectReference = ActorPrinter.this.getObjectReference(it);
              _builder.append(_objectReference);
              return _builder.toString();
            }
          };
          String _join = IterableExtensions.<Action>join(this.actor.getActionsOutsideFsm(), ", ", _function);
          _builder_2.append(_join);
          _builder_2.append("}");
          _builder_2.newLineIfNotEmpty();
        }
      }
      _builder_2.newLine();
      {
        FSM _fsm_1 = this.actor.getFsm();
        boolean _tripleNotEquals_1 = (_fsm_1 != null);
        if (_tripleNotEquals_1) {
          _builder_2.newLine();
          _builder_2.append(";; FSM");
          _builder_2.newLine();
          CharSequence _FSM_MD = this.FSM_MD();
          _builder_2.append(_FSM_MD);
          _builder_2.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder_2;
    }
    return _xblockexpression;
  }
  
  private CharSequence FSM_MD() {
    StringConcatenation _builder = new StringConcatenation();
    String _objectReference = this.getObjectReference(this.actor.getFsm());
    _builder.append(_objectReference);
    _builder.append(" = metadata !{");
    CharSequence _name_MD = this.name_MD(this.actor.getFsm().getInitialState().getName());
    _builder.append(_name_MD);
    _builder.append(", metadata ");
    String _objectReference_1 = this.getObjectReference(this.actor.getFsm().getStates());
    _builder.append(_objectReference_1);
    _builder.append(", metadata ");
    String _objectReference_2 = this.getObjectReference(this.actor.getName().concat("_transitions"));
    _builder.append(_objectReference_2);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append(";;; States");
    _builder.newLine();
    String _objectReference_3 = this.getObjectReference(this.actor.getFsm().getStates());
    _builder.append(_objectReference_3);
    _builder.append(" = metadata !{");
    final Function1<State, CharSequence> _function = new Function1<State, CharSequence>() {
      @Override
      public CharSequence apply(final State it) {
        return ActorPrinter.this.name_MD(it.getName());
      }
    };
    String _join = IterableExtensions.<State>join(this.actor.getFsm().getStates(), ", ", _function);
    _builder.append(_join);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append(";;; All transitions");
    _builder.newLine();
    String _objectReference_4 = this.getObjectReference(this.actor.getName().concat("_transitions"));
    _builder.append(_objectReference_4);
    _builder.append(" = metadata !{");
    final Function1<State, CharSequence> _function_1 = new Function1<State, CharSequence>() {
      @Override
      public CharSequence apply(final State it) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("metadata ");
        String _objectReference = ActorPrinter.this.getObjectReference(it);
        _builder.append(_objectReference);
        return _builder.toString();
      }
    };
    String _join_1 = IterableExtensions.<State>join(this.actor.getFsm().getStates(), ", ", _function_1);
    _builder.append(_join_1);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    {
      EList<State> _states = this.actor.getFsm().getStates();
      for(final State state : _states) {
        CharSequence _transition_MD = this.transition_MD(state);
        _builder.append(_transition_MD);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence transition_MD(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(";;;; Transitions from ");
    String _name = state.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = state.getOutgoing().isEmpty();
      if (_isEmpty) {
        String _objectReference = this.getObjectReference(state);
        _builder.append(_objectReference);
        _builder.append(" = metadata !{");
        CharSequence _name_MD = this.name_MD(state.getName());
        _builder.append(_name_MD);
        _builder.append(", null}");
        _builder.newLineIfNotEmpty();
      } else {
        String _objectReference_1 = this.getObjectReference(state);
        _builder.append(_objectReference_1);
        _builder.append(" = metadata !{");
        CharSequence _name_MD_1 = this.name_MD(state.getName());
        _builder.append(_name_MD_1);
        _builder.append(", metadata ");
        String _objectReference_2 = this.getObjectReference(state.getOutgoing());
        _builder.append(_objectReference_2);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
        String _objectReference_3 = this.getObjectReference(state.getOutgoing());
        _builder.append(_objectReference_3);
        _builder.append(" = metadata !{");
        final Function1<Edge, CharSequence> _function = new Function1<Edge, CharSequence>() {
          @Override
          public CharSequence apply(final Edge it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("metadata ");
            String _objectReference = ActorPrinter.this.getObjectReference(((Transition) it));
            _builder.append(_objectReference);
            return _builder.toString();
          }
        };
        String _join = IterableExtensions.<Edge>join(state.getOutgoing(), ", ", _function);
        _builder.append(_join);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
        {
          EList<Edge> _outgoing = state.getOutgoing();
          for(final Edge transition : _outgoing) {
            String _objectReference_4 = this.getObjectReference(((Transition) transition));
            _builder.append(_objectReference_4);
            _builder.append(" = metadata !{metadata ");
            String _objectReference_5 = this.getObjectReference(((Transition) transition).getAction());
            _builder.append(_objectReference_5);
            _builder.append(", ");
            CharSequence _name_MD_2 = this.name_MD(((Transition) transition).getTarget().getName());
            _builder.append(_name_MD_2);
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  private CharSequence varType_MD(final Type type) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    boolean _isInt = type.isInt();
    if (_isInt) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("i32 ");
      int _size = ((TypeInt) type).getSize();
      _builder.append(_size);
      _switchResult = _builder;
    }
    if (!_matched) {
      boolean _isUint = type.isUint();
      if (_isUint) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("i32 ");
        int _size_1 = ((TypeUint) type).getSize();
        _builder_1.append(_size_1);
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      boolean _isBool = type.isBool();
      if (_isBool) {
        _matched=true;
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("i32 1");
        _switchResult = _builder_2;
      }
    }
    if (!_matched) {
      boolean _isList = type.isList();
      if (_isList) {
        _matched=true;
        StringConcatenation _builder_3 = new StringConcatenation();
        CharSequence _varType_MD = this.varType_MD(((TypeList) type).getInnermostType());
        _builder_3.append(_varType_MD);
        _switchResult = _builder_3;
      }
    }
    if (!_matched) {
      boolean _isString = type.isString();
      if (_isString) {
        _matched=true;
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("i32 8");
        _switchResult = _builder_4;
      }
    }
    if (!_matched) {
      _switchResult = "";
    }
    return _switchResult;
  }
  
  @Override
  public CharSequence caseInstLoad(final InstLoad load) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isList = load.getSource().getVariable().getType().isList();
      if (_isList) {
        CharSequence _varName = this.varName(load.getSource().getVariable(), load);
        _builder.append(_varName);
        _builder.append(" = getelementptr ");
        CharSequence _doSwitch = this.doSwitch(load.getSource().getVariable().getType());
        _builder.append(_doSwitch);
        _builder.append("* ");
        CharSequence _print = this.print(load.getSource().getVariable());
        _builder.append(_print);
        _builder.append(", i32 0");
        final Function1<Expression, CharSequence> _function = new Function1<Expression, CharSequence>() {
          @Override
          public CharSequence apply(final Expression it) {
            return ActorPrinter.this.printIndex(it);
          }
        };
        String _join = IterableExtensions.<Expression>join(load.getIndexes(), ", ", ", ", "", _function);
        _builder.append(_join);
        _builder.newLineIfNotEmpty();
        CharSequence _print_1 = this.print(load.getTarget().getVariable());
        _builder.append(_print_1);
        _builder.append(" = load ");
        Type _type = load.getSource().getVariable().getType();
        CharSequence _doSwitch_1 = this.doSwitch(((TypeList) _type).getInnermostType());
        _builder.append(_doSwitch_1);
        _builder.append("* ");
        CharSequence _varName_1 = this.varName(load.getSource().getVariable(), load);
        _builder.append(_varName_1);
        _builder.newLineIfNotEmpty();
      } else {
        CharSequence _print_2 = this.print(load.getTarget().getVariable());
        _builder.append(_print_2);
        _builder.append(" = load ");
        CharSequence _doSwitch_2 = this.doSwitch(load.getSource().getVariable().getType());
        _builder.append(_doSwitch_2);
        _builder.append("* ");
        CharSequence _print_3 = this.print(load.getSource().getVariable());
        _builder.append(_print_3);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseInstStore(final InstStore store) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isList = store.getTarget().getVariable().getType().isList();
      if (_isList) {
        CharSequence _varName = this.varName(store.getTarget().getVariable(), store);
        _builder.append(_varName);
        _builder.append(" = getelementptr ");
        CharSequence _doSwitch = this.doSwitch(store.getTarget().getVariable().getType());
        _builder.append(_doSwitch);
        _builder.append("* ");
        CharSequence _print = this.print(store.getTarget().getVariable());
        _builder.append(_print);
        _builder.append(", i32 0");
        final Function1<Expression, CharSequence> _function = new Function1<Expression, CharSequence>() {
          @Override
          public CharSequence apply(final Expression it) {
            return ActorPrinter.this.printIndex(it);
          }
        };
        String _join = IterableExtensions.<Expression>join(store.getIndexes(), ", ", ", ", "", _function);
        _builder.append(_join);
        _builder.newLineIfNotEmpty();
        _builder.append("store ");
        Type _type = store.getTarget().getVariable().getType();
        CharSequence _doSwitch_1 = this.doSwitch(((TypeList) _type).getInnermostType());
        _builder.append(_doSwitch_1);
        _builder.append(" ");
        CharSequence _doSwitch_2 = this.doSwitch(store.getValue());
        _builder.append(_doSwitch_2);
        _builder.append(", ");
        Type _type_1 = store.getTarget().getVariable().getType();
        CharSequence _doSwitch_3 = this.doSwitch(((TypeList) _type_1).getInnermostType());
        _builder.append(_doSwitch_3);
        _builder.append("* ");
        CharSequence _varName_1 = this.varName(store.getTarget().getVariable(), store);
        _builder.append(_varName_1);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("store ");
        CharSequence _doSwitch_4 = this.doSwitch(store.getTarget().getVariable().getType());
        _builder.append(_doSwitch_4);
        _builder.append(" ");
        CharSequence _doSwitch_5 = this.doSwitch(store.getValue());
        _builder.append(_doSwitch_5);
        _builder.append(", ");
        CharSequence _doSwitch_6 = this.doSwitch(store.getTarget().getVariable().getType());
        _builder.append(_doSwitch_6);
        _builder.append("* ");
        CharSequence _print_1 = this.print(store.getTarget().getVariable());
        _builder.append(_print_1);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseInstReturn(final InstReturn retInst) {
    CharSequence _xifexpression = null;
    Expression _value = retInst.getValue();
    boolean _tripleEquals = (_value == null);
    if (_tripleEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("ret void");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("ret ");
      CharSequence _doSwitch = this.doSwitch(retInst.getValue().getType());
      _builder_1.append(_doSwitch);
      _builder_1.append(" ");
      CharSequence _doSwitch_1 = this.doSwitch(retInst.getValue());
      _builder_1.append(_doSwitch_1);
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  @Override
  protected CharSequence print(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("define ");
    CharSequence _doSwitch = this.doSwitch(action.getScheduler().getReturnType());
    _builder.append(_doSwitch);
    _builder.append(" @");
    String _name = action.getScheduler().getName();
    _builder.append(_name);
    _builder.append("(");
    final Function1<Param, CharSequence> _function = new Function1<Param, CharSequence>() {
      @Override
      public CharSequence apply(final Param it) {
        return ActorPrinter.this.declare(it);
      }
    };
    String _join = IterableExtensions.<Param>join(action.getScheduler().getParameters(), ", ", _function);
    _builder.append(_join);
    _builder.append(") nounwind {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    {
      EList<Var> _locals = action.getScheduler().getLocals();
      for(final Var local : _locals) {
        _builder.append("\t");
        CharSequence _declare = this.declare(local);
        _builder.append(_declare, "\t");
        _builder.newLineIfNotEmpty();
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
      Iterable<Port> _filter = IterableExtensions.<Port>filter(action.getPeekPattern().getPorts(), _function_1);
      for(final Port port : _filter) {
        _builder.append("\t");
        CharSequence _fifoVar = this.fifoVar(port, action.getInputPattern().getPortToVarMap().get(port));
        _builder.append(_fifoVar, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("br label %b");
    CharSequence _label = this.label(IterableExtensions.<Block>head(action.getScheduler().getBlocks()));
    _builder.append(_label, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Block> _blocks = action.getScheduler().getBlocks();
      for(final Block block : _blocks) {
        CharSequence _doSwitch_1 = this.doSwitch(block);
        _builder.append(_doSwitch_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("define void @");
    String _name_1 = action.getBody().getName();
    _builder.append(_name_1);
    _builder.append("(");
    final Function1<Param, CharSequence> _function_2 = new Function1<Param, CharSequence>() {
      @Override
      public CharSequence apply(final Param it) {
        return ActorPrinter.this.declare(it);
      }
    };
    String _join_1 = IterableExtensions.<Param>join(action.getBody().getParameters(), ", ", _function_2);
    _builder.append(_join_1);
    _builder.append(") nounwind {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    {
      EList<Var> _locals_1 = action.getBody().getLocals();
      for(final Var local_1 : _locals_1) {
        _builder.append("\t");
        CharSequence _declare_1 = this.declare(local_1);
        _builder.append(_declare_1, "\t");
        _builder.newLineIfNotEmpty();
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
      Iterable<Port> _filter_1 = IterableExtensions.<Port>filter(action.getInputPattern().getPorts(), _function_3);
      for(final Port port_1 : _filter_1) {
        _builder.append("\t");
        CharSequence _fifoVar_1 = this.fifoVar(port_1, action.getInputPattern().getPortToVarMap().get(port_1));
        _builder.append(_fifoVar_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      final Function1<Port, Boolean> _function_4 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter_2 = IterableExtensions.<Port>filter(action.getOutputPattern().getPorts(), _function_4);
      for(final Port port_2 : _filter_2) {
        _builder.append("\t");
        CharSequence _fifoVar_2 = this.fifoVar(port_2, action.getOutputPattern().getPortToVarMap().get(port_2));
        _builder.append(_fifoVar_2, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("br label %b");
    CharSequence _label_1 = this.label(IterableExtensions.<Block>head(action.getBody().getBlocks()));
    _builder.append(_label_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Block> _blocks_1 = action.getBody().getBlocks();
      for(final Block block_1 : _blocks_1) {
        CharSequence _doSwitch_2 = this.doSwitch(block_1);
        _builder.append(_doSwitch_2);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence actorParameter(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("@");
    String _name = variable.getName();
    _builder.append(_name);
    _builder.append(" = global ");
    CharSequence _doSwitch = this.doSwitch(variable.getType());
    _builder.append(_doSwitch);
    _builder.append(" undef");
    return _builder;
  }
  
  private CharSequence fifo(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _fifoVarName = this.fifoVarName(port);
    _builder.append(_fifoVarName);
    _builder.append(" = global ");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch);
    _builder.append("* null");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence fifoVarName(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("@");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_ptr");
    return _builder;
  }
  
  private CharSequence fifoVar(final Port port, final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("%");
    String _name = variable.getName();
    _builder.append(_name);
    _builder.append("_ptr = load ");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch);
    _builder.append("** ");
    CharSequence _fifoVarName = this.fifoVarName(port);
    _builder.append(_fifoVarName);
    _builder.newLineIfNotEmpty();
    _builder.append("%");
    String _name_1 = variable.getName();
    _builder.append(_name_1);
    _builder.append(" = bitcast ");
    CharSequence _doSwitch_1 = this.doSwitch(port.getType());
    _builder.append(_doSwitch_1);
    _builder.append("* %");
    String _name_2 = variable.getName();
    _builder.append(_name_2);
    _builder.append("_ptr to ");
    CharSequence _doSwitch_2 = this.doSwitch(variable.getType());
    _builder.append(_doSwitch_2);
    _builder.append("*");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private void computePatterns() {
    EList<Action> _initializes = this.actor.getInitializes();
    for (final Action init : _initializes) {
      {
        this.compute(init.getInputPattern());
        this.compute(init.getOutputPattern());
        this.compute(init.getPeekPattern());
      }
    }
    EList<Action> _actions = this.actor.getActions();
    for (final Action action : _actions) {
      {
        this.compute(action.getInputPattern());
        this.compute(action.getOutputPattern());
        this.compute(action.getPeekPattern());
      }
    }
    boolean _hasMoC = this.actor.hasMoC();
    if (_hasMoC) {
      final MoC moc = this.actor.getMoC();
      if ((moc.isSDF() || moc.isCSDF())) {
        this.compute(((CSDFMoC) moc).getInputPattern());
        this.compute(((CSDFMoC) moc).getOutputPattern());
      } else {
        boolean _isQuasiStatic = moc.isQuasiStatic();
        if (_isQuasiStatic) {
          Set<Action> _actions_1 = ((QSDFMoC) moc).getActions();
          for (final Action action_1 : _actions_1) {
            {
              MoC _moC = ((QSDFMoC) moc).getMoC(action_1);
              this.compute(((CSDFMoC) _moC).getInputPattern());
              MoC _moC_1 = ((QSDFMoC) moc).getMoC(action_1);
              this.compute(((CSDFMoC) _moC_1).getOutputPattern());
            }
          }
        }
      }
    }
  }
  
  private boolean compute(final Pattern pattern) {
    boolean _xifexpression = false;
    boolean _isEmpty = pattern.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      _xifexpression = this.patternList.add(pattern);
    }
    return _xifexpression;
  }
  
  @Override
  protected void computeCastedList() {
    this.castedList.clear();
    Iterable<Var> _filter = Iterables.<Var>filter(IteratorExtensions.<EObject>toIterable(this.actor.eAllContents()), Var.class);
    for (final Var variable : _filter) {
      if (((variable.getType().isList() && (!variable.getDefs().isEmpty())) && (IterableExtensions.<Def>head(variable.getDefs()).eContainer() instanceof InstCast))) {
        this.castedList.add(variable);
      }
    }
  }
}
