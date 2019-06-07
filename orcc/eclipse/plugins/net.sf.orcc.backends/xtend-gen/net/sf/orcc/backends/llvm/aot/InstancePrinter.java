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
package net.sf.orcc.backends.llvm.aot;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.BackendsConstants;
import net.sf.orcc.backends.ir.ExprNull;
import net.sf.orcc.backends.ir.InstCast;
import net.sf.orcc.backends.llvm.aot.LLVMTemplate;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrccAttributes;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.util.EcoreHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Compile Instance llvm source code
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class InstancePrinter extends LLVMTemplate {
  protected Instance instance;
  
  protected Actor actor;
  
  protected Map<Port, Connection> incomingPortMap;
  
  protected Map<Port, List<Connection>> outgoingPortMap;
  
  protected String name;
  
  protected final List<Var> castedList = new ArrayList<Var>();
  
  private final Map<State, Integer> stateToLabel = new HashMap<State, Integer>();
  
  private final Map<Pattern, Map<Port, Integer>> portToIndexByPatternMap = new HashMap<Pattern, Map<Port, Integer>>();
  
  protected boolean optionInline = false;
  
  protected String optionDatalayout = BackendsConstants.LLVM_DEFAULT_TARGET_DATALAYOUT;
  
  protected String optionArch = BackendsConstants.LLVM_DEFAULT_TARGET_TRIPLE;
  
  protected boolean isActionAligned = false;
  
  @Override
  public void setOptions(final Map<String, Object> options) {
    super.setOptions(options);
    boolean _containsKey = options.containsKey(BackendsConstants.INLINE);
    if (_containsKey) {
      Object _get = options.get(BackendsConstants.INLINE);
      this.optionInline = (((Boolean) _get)).booleanValue();
    }
    boolean _containsKey_1 = options.containsKey(BackendsConstants.LLVM_TARGET_TRIPLE);
    if (_containsKey_1) {
      Object _get_1 = options.get(BackendsConstants.LLVM_TARGET_TRIPLE);
      this.optionArch = ((String) _get_1);
    }
    boolean _containsKey_2 = options.containsKey(BackendsConstants.LLVM_TARGET_DATALAYOUT);
    if (_containsKey_2) {
      Object _get_2 = options.get(BackendsConstants.LLVM_TARGET_DATALAYOUT);
      this.optionDatalayout = ((String) _get_2);
    }
  }
  
  public void setInstance(final Instance instance) {
    boolean _isActor = instance.isActor();
    boolean _not = (!_isActor);
    if (_not) {
      String _name = instance.getName();
      String _plus = ("Instance " + _name);
      String _plus_1 = (_plus + " is not an Actor\'s instance");
      throw new OrccRuntimeException(_plus_1);
    }
    this.instance = instance;
    this.name = instance.getName();
    this.actor = instance.getActor();
    this.incomingPortMap = instance.getIncomingPortMap();
    this.outgoingPortMap = instance.getOutgoingPortMap();
    this.computeCastedList();
    this.computeStateToLabel();
    this.computePortToIndexByPatternMap();
  }
  
  public void setActor(final Actor actor) {
    this.name = actor.getName();
    this.actor = actor;
    this.incomingPortMap = actor.getIncomingPortMap();
    this.outgoingPortMap = actor.getOutgoingPortMap();
    this.computeCastedList();
    this.computeStateToLabel();
    this.computePortToIndexByPatternMap();
  }
  
  public CharSequence getContent() {
    StringConcatenation _builder = new StringConcatenation();
    final Iterable<? extends Port> inputs = this.getNotNative(this.actor.getInputs());
    _builder.newLineIfNotEmpty();
    final Iterable<? extends Port> outputs = this.getNotNative(this.actor.getOutputs());
    _builder.newLineIfNotEmpty();
    CharSequence _printDatalayout = this.printDatalayout();
    _builder.append(_printDatalayout);
    _builder.newLineIfNotEmpty();
    CharSequence _printArchitecture = this.printArchitecture();
    _builder.append(_printArchitecture);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
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
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; FIFOs");
    _builder.newLine();
    _builder.newLine();
    {
      for(final Port port : inputs) {
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        CharSequence _printExternalFifo = this.printExternalFifo(connection, port);
        _builder.append(_printExternalFifo);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      for(final Port port_1 : outputs) {
        {
          List<Connection> _get = this.outgoingPortMap.get(port_1);
          for(final Connection connection_1 : _get) {
            {
              boolean _contains = this.incomingPortMap.values().contains(connection_1);
              boolean _not = (!_contains);
              if (_not) {
                CharSequence _printExternalFifo_1 = this.printExternalFifo(connection_1, port_1);
                _builder.append(_printExternalFifo_1);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    {
      for(final Port port_2 : inputs) {
        final Connection connection_2 = this.incomingPortMap.get(port_2);
        _builder.newLineIfNotEmpty();
        CharSequence _printInput = this.printInput(connection_2, port_2);
        _builder.append(_printInput);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      for(final Port port_3 : outputs) {
        {
          List<Connection> _get_1 = this.outgoingPortMap.get(port_3);
          for(final Connection connection_3 : _get_1) {
            CharSequence _printOutput = this.printOutput(connection_3, port_3);
            _builder.append(_printOutput);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; Parameters");
    _builder.newLine();
    _builder.newLine();
    {
      if ((this.instance != null)) {
        {
          EList<Argument> _arguments = this.instance.getArguments();
          for(final Argument arg : _arguments) {
            _builder.append("@");
            String _name_1 = arg.getVariable().getName();
            _builder.append(_name_1);
            _builder.append(" = internal global ");
            CharSequence _doSwitch = this.doSwitch(arg.getVariable().getType());
            _builder.append(_doSwitch);
            _builder.append(" ");
            CharSequence _doSwitch_1 = this.doSwitch(arg.getValue());
            _builder.append(_doSwitch_1);
            _builder.newLineIfNotEmpty();
          }
        }
      } else {
        {
          EList<Var> _parameters = this.actor.getParameters();
          for(final Var param : _parameters) {
            CharSequence _declare = this.declare(param);
            _builder.append(_declare);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; State variables");
    _builder.newLine();
    {
      EList<Var> _stateVars = this.actor.getStateVars();
      for(final Var variable : _stateVars) {
        CharSequence _declare_1 = this.declare(variable);
        _builder.append(_declare_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
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
    _builder.newLine();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; Initializes");
    _builder.newLine();
    {
      EList<Action> _initializes = this.actor.getInitializes();
      for(final Action init : _initializes) {
        CharSequence _print_1 = this.print(init);
        _builder.append(_print_1);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; Actions");
    _builder.newLine();
    {
      EList<Action> _actions = this.actor.getActions();
      for(final Action action : _actions) {
        CharSequence _print_2 = this.print(action);
        _builder.append(_print_2);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; Action-scheduler");
    _builder.newLine();
    {
      boolean _isEmpty = this.actor.getInitializes().isEmpty();
      boolean _not_1 = (!_isEmpty);
      if (_not_1) {
        _builder.append("define void @");
        _builder.append(this.name);
        _builder.append("_initialize() nounwind {");
        _builder.newLineIfNotEmpty();
        _builder.append("entry:");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printCallStartTokenFunctions = this.printCallStartTokenFunctions();
        _builder.append(_printCallStartTokenFunctions, "\t");
        _builder.newLineIfNotEmpty();
        {
          EList<Action> _initializes_1 = this.actor.getInitializes();
          for(final Action init_1 : _initializes_1) {
            _builder.append("\t");
            _builder.append("call ");
            CharSequence _doSwitch_2 = this.doSwitch(init_1.getBody().getReturnType());
            _builder.append(_doSwitch_2, "\t");
            _builder.append(" @");
            String _name_2 = init_1.getBody().getName();
            _builder.append(_name_2, "\t");
            _builder.append("()");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        CharSequence _printCallEndTokenFunctions = this.printCallEndTokenFunctions();
        _builder.append(_printCallEndTokenFunctions, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("ret void");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        CharSequence _schedulerWithFSM = this.schedulerWithFSM();
        _builder.append(_schedulerWithFSM);
        _builder.newLineIfNotEmpty();
      } else {
        CharSequence _schedulerWithoutFSM = this.schedulerWithoutFSM();
        _builder.append(_schedulerWithoutFSM);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence printDatalayout() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("target datalayout = \"");
    _builder.append(this.optionDatalayout);
    _builder.append("\"");
    return _builder;
  }
  
  protected CharSequence printArchitecture() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("target triple = \"");
    _builder.append(this.optionArch);
    _builder.append("\"");
    return _builder;
  }
  
  private CharSequence schedulerWithFSM() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("@_FSM_state = internal global i32 ");
    Integer _get = this.stateToLabel.get(this.actor.getFsm().getInitialState());
    _builder.append(_get);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("define void @");
        _builder.append(this.name);
        _builder.append("_outside_FSM_scheduler() nounwind {");
        _builder.newLineIfNotEmpty();
        _builder.append("entry:");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("br label %bb_outside_scheduler_start");
        _builder.newLine();
        _builder.newLine();
        _builder.append("bb_outside_scheduler_start:");
        _builder.newLine();
        _builder.append("\t");
        _builder.append(";; no read/write here!");
        _builder.newLine();
        CharSequence _printActionLoop = this.printActionLoop(this.actor.getActionsOutsideFsm(), true);
        _builder.append(_printActionLoop);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("bb_outside_finished:");
        _builder.newLine();
        _builder.append("\t");
        _builder.append(";; no read_end/write_end here!");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("ret void");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("define void @");
    _builder.append(this.name);
    _builder.append("_scheduler() nounwind {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("br label %bb_scheduler_start");
    _builder.newLine();
    _builder.newLine();
    _builder.append("bb_scheduler_start:");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _printCallStartTokenFunctions = this.printCallStartTokenFunctions();
    _builder.append(_printCallStartTokenFunctions, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _printFsmSwitch = this.printFsmSwitch(this.actor.getFsm());
    _builder.append(_printFsmSwitch, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("br label %bb_scheduler_start");
    _builder.newLine();
    _builder.newLine();
    _builder.append("default:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("; TODO: print error");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("br label %bb_scheduler_start");
    _builder.newLine();
    _builder.newLine();
    {
      EList<State> _states = this.actor.getFsm().getStates();
      for(final State state : _states) {
        CharSequence _printTransition = this.printTransition(state);
        _builder.append(_printTransition);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("bb_waiting:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("br label %bb_finished");
    _builder.newLine();
    _builder.newLine();
    _builder.append("bb_finished:");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _printCallEndTokenFunctions = this.printCallEndTokenFunctions();
    _builder.append(_printCallEndTokenFunctions, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ret void");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printFsmSwitch(final FSM fsm) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("%local_FSM_state = load i32* @_FSM_state");
    _builder.newLine();
    _builder.append("switch i32 %local_FSM_state, label %default [");
    _builder.newLine();
    _builder.append("\t");
    final Function1<State, CharSequence> _function = new Function1<State, CharSequence>() {
      @Override
      public CharSequence apply(final State it) {
        return InstancePrinter.this.printFsmState(it);
      }
    };
    String _join = IterableExtensions.join(ListExtensions.<State, CharSequence>map(fsm.getStates(), _function));
    _builder.append(_join, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("]");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printFsmState(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("i32 ");
    Integer _get = this.stateToLabel.get(state);
    _builder.append(_get);
    _builder.append(", label %bb_s_");
    String _name = state.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence printTransition(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("; STATE ");
    String _name = state.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("bb_s_");
    String _name_1 = state.getName();
    _builder.append(_name_1);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("call void @");
        _builder.append(this.name, "\t");
        _builder.append("_outside_FSM_scheduler()");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<Transition> _filter = Iterables.<Transition>filter(state.getOutgoing(), Transition.class);
      for(final Transition transition : _filter) {
        final Action action = transition.getAction();
        _builder.newLineIfNotEmpty();
        final String actionName = action.getName();
        _builder.newLineIfNotEmpty();
        String _name_2 = state.getName();
        String _plus = (_name_2 + "_");
        final String extName = (_plus + actionName);
        _builder.newLineIfNotEmpty();
        final Pattern inputPattern = action.getInputPattern();
        _builder.newLineIfNotEmpty();
        final Pattern outputPattern = action.getOutputPattern();
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("; ACTION ");
        _builder.append(actionName, "\t");
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty_1 = IterableExtensions.isEmpty(this.getNotNative(inputPattern.getPorts()));
          boolean _not_1 = (!_isEmpty_1);
          if (_not_1) {
            _builder.append("\t");
            _builder.append(";; Input pattern");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _checkInputPattern = this.checkInputPattern(action, inputPattern, state);
            _builder.append(_checkInputPattern, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%guard_");
            _builder.append(extName, "\t");
            _builder.append(" = call ");
            CharSequence _doSwitch = this.doSwitch(action.getScheduler().getReturnType());
            _builder.append(_doSwitch, "\t");
            _builder.append(" @");
            String _name_3 = action.getScheduler().getName();
            _builder.append(_name_3, "\t");
            _builder.append(" ()");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%is_schedulable_");
            _builder.append(extName, "\t");
            _builder.append(" = icmp eq ");
            CharSequence _doSwitch_1 = this.doSwitch(action.getScheduler().getReturnType());
            _builder.append(_doSwitch_1, "\t");
            _builder.append(" %guard_");
            _builder.append(extName, "\t");
            _builder.append(", 1");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%is_fireable_");
            _builder.append(extName, "\t");
            _builder.append(" = and i1 %is_schedulable_");
            _builder.append(extName, "\t");
            _builder.append(", %has_valid_inputs_");
            _builder.append(extName, "\t");
            _builder.append("_");
            int _size = inputPattern.getPorts().size();
            _builder.append(_size, "\t");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("\t");
            _builder.append("br i1 %is_fireable_");
            _builder.append(extName, "\t");
            _builder.append(", label %bb_");
            _builder.append(extName, "\t");
            _builder.append("_check_outputs, label %bb_");
            _builder.append(extName, "\t");
            _builder.append("_unschedulable");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t");
            _builder.append(";; Empty input pattern");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("%guard_");
            _builder.append(extName, "\t");
            _builder.append(" = call ");
            CharSequence _doSwitch_2 = this.doSwitch(action.getScheduler().getReturnType());
            _builder.append(_doSwitch_2, "\t");
            _builder.append(" @");
            String _name_4 = action.getScheduler().getName();
            _builder.append(_name_4, "\t");
            _builder.append(" ()");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%is_fireable_");
            _builder.append(extName, "\t");
            _builder.append(" = icmp eq ");
            CharSequence _doSwitch_3 = this.doSwitch(action.getScheduler().getReturnType());
            _builder.append(_doSwitch_3, "\t");
            _builder.append(" %guard_");
            _builder.append(extName, "\t");
            _builder.append(", 1");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("\t");
            _builder.append("br i1 %is_fireable_");
            _builder.append(extName, "\t");
            _builder.append(", label %bb_");
            _builder.append(extName, "\t");
            _builder.append("_check_outputs, label %bb_");
            _builder.append(extName, "\t");
            _builder.append("_unschedulable");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("bb_");
        _builder.append(extName);
        _builder.append("_check_outputs:");
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty_2 = IterableExtensions.isEmpty(this.getNotNative(outputPattern.getPorts()));
          boolean _not_2 = (!_isEmpty_2);
          if (_not_2) {
            _builder.append("\t");
            final Port lastPort = IterableExtensions.<Port>last(outputPattern.getPorts());
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(";; Output pattern");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _checkOutputPattern = this.checkOutputPattern(action, outputPattern, state);
            _builder.append(_checkOutputPattern, "\t");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("\t");
            _builder.append("br i1 %has_valid_outputs_");
            String _name_5 = lastPort.getName();
            _builder.append(_name_5, "\t");
            _builder.append("_");
            Object _safeId = this.getSafeId(IterableExtensions.<Connection>last(this.outgoingPortMap.get(lastPort)), lastPort);
            _builder.append(_safeId, "\t");
            _builder.append("_");
            _builder.append(extName, "\t");
            _builder.append(", label %bb_");
            _builder.append(extName, "\t");
            _builder.append("_fire, label %bb_");
            String _name_6 = state.getName();
            _builder.append(_name_6, "\t");
            _builder.append("_finished");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t");
            _builder.append(";; Empty output pattern");
            _builder.newLine();
            _builder.newLine();
            _builder.append("\t");
            _builder.append("br label %bb_");
            _builder.append(extName, "\t");
            _builder.append("_fire");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("bb_");
        _builder.append(extName);
        _builder.append("_fire:");
        _builder.newLineIfNotEmpty();
        {
          boolean _hasAttribute = action.hasAttribute(OrccAttributes.ALIGNED_ALWAYS);
          if (_hasAttribute) {
            _builder.append("call void @");
            String _name_7 = action.getBody().getName();
            _builder.append(_name_7);
            _builder.append("_aligned()");
            _builder.newLineIfNotEmpty();
          } else {
            boolean _hasAttribute_1 = action.hasAttribute(OrccAttributes.ALIGNABLE);
            if (_hasAttribute_1) {
              CharSequence _printAlignmentConditions = this.printAlignmentConditions(action, state);
              _builder.append(_printAlignmentConditions);
              _builder.newLineIfNotEmpty();
              _builder.newLine();
              _builder.append("bb_");
              _builder.append(extName);
              _builder.append("_fire_aligned:");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("call void @");
              String _name_8 = action.getBody().getName();
              _builder.append(_name_8, "\t");
              _builder.append("_aligned()");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("br label %bb_");
              _builder.append(extName, "\t");
              _builder.append("_fire_ret");
              _builder.newLineIfNotEmpty();
              _builder.newLine();
              _builder.append("bb_");
              _builder.append(extName);
              _builder.append("_fire_notaligned:");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("call void @");
              String _name_9 = action.getBody().getName();
              _builder.append(_name_9, "\t");
              _builder.append("()");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("br label %bb_");
              _builder.append(extName, "\t");
              _builder.append("_fire_ret");
              _builder.newLineIfNotEmpty();
              _builder.newLine();
              _builder.append("bb_");
              _builder.append(extName);
              _builder.append("_fire_ret:");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("call void @");
              String _name_10 = action.getBody().getName();
              _builder.append(_name_10);
              _builder.append("()");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        _builder.newLine();
        _builder.append("\t");
        _builder.append("br label %bb_s_");
        String _name_11 = transition.getTarget().getName();
        _builder.append(_name_11, "\t");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("bb_");
        _builder.append(extName);
        _builder.append("_unschedulable:");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("br label %bb_");
    String _name_12 = state.getName();
    _builder.append(_name_12, "\t");
    _builder.append("_finished");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("bb_");
    String _name_13 = state.getName();
    _builder.append(_name_13);
    _builder.append("_finished:");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("store i32 ");
    Integer _get = this.stateToLabel.get(state);
    _builder.append(_get, "\t");
    _builder.append(", i32* @_FSM_state");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("br label %bb_waiting");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence schedulerWithoutFSM() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("define void @");
    _builder.append(this.name);
    _builder.append("_scheduler() nounwind {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _printCallStartTokenFunctions = this.printCallStartTokenFunctions();
    _builder.append(_printCallStartTokenFunctions, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("br label %bb_scheduler_start");
    _builder.newLine();
    _builder.newLine();
    _builder.append("bb_scheduler_start:");
    _builder.newLine();
    CharSequence _printActionLoop = this.printActionLoop(this.actor.getActionsOutsideFsm(), false);
    _builder.append(_printActionLoop);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("bb_waiting:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("br label %bb_finished");
    _builder.newLine();
    _builder.newLine();
    _builder.append("bb_finished:");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _printCallEndTokenFunctions = this.printCallEndTokenFunctions();
    _builder.append(_printCallEndTokenFunctions, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ret void");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printAlignmentConditions(final Action action, final State state) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    if ((state != null)) {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name = state.getName();
      _builder_1.append(_name);
      _builder_1.append("_");
      _xifexpression = _builder_1.toString();
    } else {
      _xifexpression = "";
    }
    final String stateName = _xifexpression;
    _builder.newLineIfNotEmpty();
    String _xifexpression_1 = null;
    if ((state != null)) {
      StringConcatenation _builder_2 = new StringConcatenation();
      String _name_1 = state.getName();
      _builder_2.append(_name_1);
      _builder_2.append("_");
      String _name_2 = action.getName();
      _builder_2.append(_name_2);
      _xifexpression_1 = _builder_2.toString();
    } else {
      StringConcatenation _builder_3 = new StringConcatenation();
      String _name_3 = action.getName();
      _builder_3.append(_name_3);
      _xifexpression_1 = _builder_3.toString();
    }
    final String actionName = _xifexpression_1;
    _builder.newLineIfNotEmpty();
    final Map<Port, Integer> portToIndexMap = this.portToIndexByPatternMap.get(action.getInputPattern());
    _builder.newLineIfNotEmpty();
    final Function1<Port, List<Connection>> _function = new Function1<Port, List<Connection>>() {
      @Override
      public List<Connection> apply(final Port it) {
        return InstancePrinter.this.outgoingPortMap.get(it);
      }
    };
    final List<Connection> connections = IterableExtensions.<Connection>toList(Iterables.<Connection>concat(IterableExtensions.map(this.getNotNative(action.getOutputPattern().getPorts()), _function)));
    _builder.newLineIfNotEmpty();
    {
      EList<Port> _ports = action.getInputPattern().getPorts();
      for(final Port port : _ports) {
        {
          if ((port.hasAttribute(((action.getName() + "_") + OrccAttributes.ALIGNABLE)) && (!port.hasAttribute(OrccAttributes.ALIGNED_ALWAYS)))) {
            String _name_4 = port.getName();
            String _plus = (_name_4 + "_");
            String _plus_1 = (_plus + stateName);
            String _name_5 = action.getName();
            String _plus_2 = (_plus_1 + _name_5);
            String _plus_3 = (_plus_2 + "_");
            Integer _get = portToIndexMap.get(port);
            final String extName = (_plus_3 + _get);
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%tmp_align1_");
            _builder.append(extName, "\t");
            _builder.append(" = urem i32 %index_");
            _builder.append(extName, "\t");
            _builder.append(", %size_");
            _builder.append(extName, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%tmp_align2_");
            _builder.append(extName, "\t");
            _builder.append(" = add i32 %index_");
            _builder.append(extName, "\t");
            _builder.append(", ");
            Integer _get_1 = action.getInputPattern().getNumTokensMap().get(port);
            _builder.append(_get_1, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%tmp_align3_");
            _builder.append(extName, "\t");
            _builder.append(" = urem i32 %tmp_align2_");
            _builder.append(extName, "\t");
            _builder.append(", %size_");
            _builder.append(extName, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%is_aligned_");
            _builder.append(extName, "\t");
            _builder.append(" = icmp slt i32 %tmp_align1_");
            _builder.append(extName, "\t");
            _builder.append(", %tmp_align3_");
            _builder.append(extName, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("br i1 %is_aligned_");
            _builder.append(extName, "\t");
            _builder.append(", label %next_aligned_");
            _builder.append(extName, "\t");
            _builder.append(", label %bb_");
            _builder.append(actionName, "\t");
            _builder.append("_fire_notaligned");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("next_aligned_");
            _builder.append(extName);
            _builder.append(":");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      for(final Connection connection : connections) {
        final Port port_1 = connection.getSourcePort();
        _builder.newLineIfNotEmpty();
        String _name_6 = port_1.getName();
        String _plus_4 = (_name_6 + "_");
        Object _safeId = this.getSafeId(connection, port_1);
        final String name = (_plus_4 + _safeId);
        _builder.newLineIfNotEmpty();
        String _name_7 = action.getName();
        final String extName_1 = (((name + "_") + stateName) + _name_7);
        _builder.newLineIfNotEmpty();
        final Integer numTokens = action.getOutputPattern().getNumTokensMap().get(port_1);
        _builder.newLineIfNotEmpty();
        {
          if ((port_1.hasAttribute(((action.getName() + "_") + OrccAttributes.ALIGNABLE)) && (!port_1.hasAttribute(OrccAttributes.ALIGNED_ALWAYS)))) {
            _builder.newLine();
            _builder.append("\t");
            _builder.append("%tmp_align1_");
            _builder.append(extName_1, "\t");
            _builder.append(" = urem i32 %index_");
            _builder.append(extName_1, "\t");
            _builder.append(", %size_");
            _builder.append(extName_1, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%tmp_align2_");
            _builder.append(extName_1, "\t");
            _builder.append(" = add i32 %index_");
            _builder.append(extName_1, "\t");
            _builder.append(", ");
            _builder.append(numTokens, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%tmp_align3_");
            _builder.append(extName_1, "\t");
            _builder.append(" = urem i32 %tmp_align2_");
            _builder.append(extName_1, "\t");
            _builder.append(", %size_");
            _builder.append(extName_1, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%is_aligned_");
            _builder.append(extName_1, "\t");
            _builder.append(" = icmp slt i32 %tmp_align1_");
            _builder.append(extName_1, "\t");
            _builder.append(", %tmp_align3_");
            _builder.append(extName_1, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("br i1 %is_aligned_");
            _builder.append(extName_1, "\t");
            _builder.append(", label %next_aligned_");
            _builder.append(extName_1, "\t");
            _builder.append(", label %bb_");
            _builder.append(actionName, "\t");
            _builder.append("_fire_notaligned");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("next_aligned_");
            _builder.append(extName_1);
            _builder.append(":");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("br label %bb_");
    _builder.append(actionName, "\t");
    _builder.append("_fire_aligned");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence printActionLoop(final EList<Action> actions, final boolean outsideFSM) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Action action : actions) {
        final String name = action.getName();
        _builder.newLineIfNotEmpty();
        final Pattern inputPattern = action.getInputPattern();
        _builder.newLineIfNotEmpty();
        final Pattern outputPattern = action.getOutputPattern();
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("; ACTION ");
        _builder.append(name, "\t");
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty = IterableExtensions.isEmpty(this.getNotNative(inputPattern.getPorts()));
          boolean _not = (!_isEmpty);
          if (_not) {
            _builder.append("\t");
            _builder.append(";; Input pattern");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _checkInputPattern = this.checkInputPattern(action, inputPattern, null);
            _builder.append(_checkInputPattern, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%guard_");
            _builder.append(name, "\t");
            _builder.append(" = call ");
            CharSequence _doSwitch = this.doSwitch(action.getScheduler().getReturnType());
            _builder.append(_doSwitch, "\t");
            _builder.append(" @");
            String _name = action.getScheduler().getName();
            _builder.append(_name, "\t");
            _builder.append(" ()");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%is_schedulable_");
            _builder.append(name, "\t");
            _builder.append(" = icmp eq ");
            CharSequence _doSwitch_1 = this.doSwitch(action.getScheduler().getReturnType());
            _builder.append(_doSwitch_1, "\t");
            _builder.append(" %guard_");
            _builder.append(name, "\t");
            _builder.append(", 1");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%is_fireable_");
            _builder.append(name, "\t");
            _builder.append(" = and i1 %is_schedulable_");
            _builder.append(name, "\t");
            _builder.append(", %has_valid_inputs_");
            _builder.append(name, "\t");
            _builder.append("_");
            int _size = inputPattern.getPorts().size();
            _builder.append(_size, "\t");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("\t");
            _builder.append("br i1 %is_fireable_");
            _builder.append(name, "\t");
            _builder.append(", label %bb_");
            _builder.append(name, "\t");
            _builder.append("_check_outputs, label %bb_");
            _builder.append(name, "\t");
            _builder.append("_unschedulable");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t");
            _builder.append(";; Empty input pattern");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("%guard_");
            _builder.append(name, "\t");
            _builder.append(" = call ");
            CharSequence _doSwitch_2 = this.doSwitch(action.getScheduler().getReturnType());
            _builder.append(_doSwitch_2, "\t");
            _builder.append(" @");
            String _name_1 = action.getScheduler().getName();
            _builder.append(_name_1, "\t");
            _builder.append(" ()");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("%is_fireable_");
            _builder.append(name, "\t");
            _builder.append(" = icmp eq ");
            CharSequence _doSwitch_3 = this.doSwitch(action.getScheduler().getReturnType());
            _builder.append(_doSwitch_3, "\t");
            _builder.append(" %guard_");
            _builder.append(name, "\t");
            _builder.append(", 1");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("\t");
            _builder.append("br i1 %is_fireable_");
            _builder.append(name, "\t");
            _builder.append(", label %bb_");
            _builder.append(name, "\t");
            _builder.append("_check_outputs, label %bb_");
            _builder.append(name, "\t");
            _builder.append("_unschedulable");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("bb_");
        _builder.append(name);
        _builder.append("_check_outputs:");
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty_1 = IterableExtensions.isEmpty(this.getNotNative(outputPattern.getPorts()));
          boolean _not_1 = (!_isEmpty_1);
          if (_not_1) {
            _builder.append("\t");
            final Port lastPort = IterableExtensions.<Port>last(outputPattern.getPorts());
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(";; Output pattern");
            _builder.newLine();
            _builder.append("\t");
            CharSequence _checkOutputPattern = this.checkOutputPattern(action, outputPattern, null);
            _builder.append(_checkOutputPattern, "\t");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("\t");
            _builder.append("br i1 %has_valid_outputs_");
            String _name_2 = lastPort.getName();
            _builder.append(_name_2, "\t");
            _builder.append("_");
            Object _safeId = this.getSafeId(IterableExtensions.<Connection>last(this.outgoingPortMap.get(lastPort)), lastPort);
            _builder.append(_safeId, "\t");
            _builder.append("_");
            _builder.append(name, "\t");
            _builder.append(", label %bb_");
            _builder.append(name, "\t");
            _builder.append("_fire, label %bb");
            {
              if (outsideFSM) {
                _builder.append("_outside");
              }
            }
            _builder.append("_finished");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t");
            _builder.append(";; Empty output pattern");
            _builder.newLine();
            _builder.newLine();
            _builder.append("\t");
            _builder.append("br label %bb_");
            _builder.append(name, "\t");
            _builder.append("_fire");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("bb_");
        _builder.append(name);
        _builder.append("_fire:");
        _builder.newLineIfNotEmpty();
        {
          boolean _hasAttribute = action.hasAttribute(OrccAttributes.ALIGNED_ALWAYS);
          if (_hasAttribute) {
            _builder.append("call void @");
            String _name_3 = action.getBody().getName();
            _builder.append(_name_3);
            _builder.append("_aligned()");
            _builder.newLineIfNotEmpty();
          } else {
            boolean _hasAttribute_1 = action.hasAttribute(OrccAttributes.ALIGNABLE);
            if (_hasAttribute_1) {
              CharSequence _printAlignmentConditions = this.printAlignmentConditions(action, null);
              _builder.append(_printAlignmentConditions);
              _builder.newLineIfNotEmpty();
              _builder.newLine();
              _builder.append("bb_");
              _builder.append(name);
              _builder.append("_fire_aligned:");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("call void @");
              String _name_4 = action.getBody().getName();
              _builder.append(_name_4, "\t");
              _builder.append("_aligned()");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("br label %bb_");
              _builder.append(name, "\t");
              _builder.append("_fire_ret");
              _builder.newLineIfNotEmpty();
              _builder.newLine();
              _builder.append("bb_");
              _builder.append(name);
              _builder.append("_fire_notaligned:");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("call void @");
              String _name_5 = action.getBody().getName();
              _builder.append(_name_5, "\t");
              _builder.append("()");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("br label %bb_");
              _builder.append(name, "\t");
              _builder.append("_fire_ret");
              _builder.newLineIfNotEmpty();
              _builder.newLine();
              _builder.append("bb_");
              _builder.append(name);
              _builder.append("_fire_ret:");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("call void @");
              String _name_6 = action.getBody().getName();
              _builder.append(_name_6);
              _builder.append("()");
              _builder.newLineIfNotEmpty();
            }
          }
        }
        {
          if (outsideFSM) {
            _builder.append("\t");
            _builder.append("br label %bb_outside_scheduler_start");
            _builder.newLine();
          } else {
            _builder.append("\t");
            _builder.append("br label %bb_scheduler_start");
            _builder.newLine();
          }
        }
        _builder.newLine();
        _builder.append("bb_");
        _builder.append(name);
        _builder.append("_unschedulable:");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      if (outsideFSM) {
        _builder.append("\t");
        _builder.append("br label %bb_outside_finished");
        _builder.newLine();
      } else {
        _builder.append("\t");
        _builder.append("br label %bb_waiting");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence checkInputPattern(final Action action, final Pattern pattern, final State state) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      if ((state != null)) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = state.getName();
        _builder.append(_name);
        _builder.append("_");
        _xifexpression = _builder.toString();
      } else {
        _xifexpression = "";
      }
      final String stateName = _xifexpression;
      final Map<Port, Integer> portToIndexMap = this.portToIndexByPatternMap.get(pattern);
      final Port firstPort = IterableExtensions.head(this.getNotNative(pattern.getPorts()));
      String _name_1 = firstPort.getName();
      String _plus = (_name_1 + "_");
      Object _safeId = this.getSafeId(this.incomingPortMap.get(firstPort), firstPort);
      final String firstName = (_plus + _safeId);
      String _name_2 = firstPort.getName();
      String _plus_1 = (_name_2 + "_");
      String _plus_2 = (_plus_1 + stateName);
      String _name_3 = action.getName();
      String _plus_3 = (_plus_2 + _name_3);
      String _plus_4 = (_plus_3 + "_");
      Integer _get = portToIndexMap.get(firstPort);
      final String extName = (_plus_4 + _get);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("%numTokens_");
      _builder_1.append(extName);
      _builder_1.append(" = load i32* @numTokens_");
      _builder_1.append(firstName);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("%index_");
      _builder_1.append(extName);
      _builder_1.append(" = load i32* @index_");
      _builder_1.append(firstName);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("%size_");
      _builder_1.append(extName);
      _builder_1.append(" = load i32* @SIZE_");
      _builder_1.append(firstName);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("%status_");
      _builder_1.append(extName);
      _builder_1.append(" = sub i32 %numTokens_");
      _builder_1.append(extName);
      _builder_1.append(", %index_");
      _builder_1.append(extName);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("%has_valid_inputs_");
      _builder_1.append(stateName);
      String _name_4 = action.getName();
      _builder_1.append(_name_4);
      _builder_1.append("_");
      Integer _get_1 = portToIndexMap.get(firstPort);
      _builder_1.append(_get_1);
      _builder_1.append(" = icmp sge i32 %status_");
      _builder_1.append(extName);
      _builder_1.append(", ");
      Integer _get_2 = pattern.getNumTokensMap().get(firstPort);
      _builder_1.append(_get_2);
      _builder_1.newLineIfNotEmpty();
      _builder_1.newLine();
      {
        Iterable<? extends Port> _tail = IterableExtensions.tail(this.getNotNative(pattern.getPorts()));
        for(final Port port : _tail) {
          String _name_5 = port.getName();
          String _plus_5 = (_name_5 + "_");
          Object _safeId_1 = this.getSafeId(this.incomingPortMap.get(port), port);
          final String name = (_plus_5 + _safeId_1);
          _builder_1.newLineIfNotEmpty();
          String _name_6 = port.getName();
          String _plus_6 = (_name_6 + "_");
          String _plus_7 = (_plus_6 + stateName);
          String _name_7 = action.getName();
          String _plus_8 = (_plus_7 + _name_7);
          String _plus_9 = (_plus_8 + "_");
          Integer _get_3 = portToIndexMap.get(port);
          final String pExtName = (_plus_9 + _get_3);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%numTokens_");
          _builder_1.append(pExtName);
          _builder_1.append(" = load i32* @numTokens_");
          _builder_1.append(name);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%index_");
          _builder_1.append(pExtName);
          _builder_1.append(" = load i32* @index_");
          _builder_1.append(name);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%size_");
          _builder_1.append(pExtName);
          _builder_1.append(" = load i32* @SIZE_");
          _builder_1.append(name);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%status_");
          _builder_1.append(pExtName);
          _builder_1.append(" = sub i32 %numTokens_");
          _builder_1.append(pExtName);
          _builder_1.append(", %index_");
          _builder_1.append(pExtName);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%available_input_");
          _builder_1.append(stateName);
          String _name_8 = action.getName();
          _builder_1.append(_name_8);
          _builder_1.append("_");
          String _name_9 = port.getName();
          _builder_1.append(_name_9);
          _builder_1.append(" = icmp uge i32 %status_");
          _builder_1.append(pExtName);
          _builder_1.append(", ");
          Integer _get_4 = pattern.getNumTokensMap().get(port);
          _builder_1.append(_get_4);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%has_valid_inputs_");
          _builder_1.append(stateName);
          String _name_10 = action.getName();
          _builder_1.append(_name_10);
          _builder_1.append("_");
          Integer _get_5 = portToIndexMap.get(port);
          _builder_1.append(_get_5);
          _builder_1.append(" = and i1 %has_valid_inputs_");
          _builder_1.append(stateName);
          String _name_11 = action.getName();
          _builder_1.append(_name_11);
          _builder_1.append("_");
          EList<Port> _ports = pattern.getPorts();
          int _indexOf = pattern.getPorts().indexOf(port);
          int _minus = (_indexOf - 1);
          Integer _get_6 = portToIndexMap.get(_ports.get(_minus));
          _builder_1.append(_get_6);
          _builder_1.append(", %available_input_");
          _builder_1.append(stateName);
          String _name_12 = action.getName();
          _builder_1.append(_name_12);
          _builder_1.append("_");
          String _name_13 = port.getName();
          _builder_1.append(_name_13);
          _builder_1.newLineIfNotEmpty();
          _builder_1.newLine();
        }
      }
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }
  
  private CharSequence checkOutputPattern(final Action action, final Pattern pattern, final State state) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      if ((state != null)) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = state.getName();
        _builder.append(_name);
        _builder.append("_");
        _xifexpression = _builder.toString();
      } else {
        _xifexpression = "";
      }
      final String stateName = _xifexpression;
      final Function1<Port, List<Connection>> _function = new Function1<Port, List<Connection>>() {
        @Override
        public List<Connection> apply(final Port it) {
          return InstancePrinter.this.outgoingPortMap.get(it);
        }
      };
      final List<Connection> connections = IterableExtensions.<Connection>toList(Iterables.<Connection>concat(IterableExtensions.map(this.getNotNative(pattern.getPorts()), _function)));
      StringConcatenation _builder_1 = new StringConcatenation();
      {
        for(final Connection connection : connections) {
          final Port port = connection.getSourcePort();
          _builder_1.newLineIfNotEmpty();
          String _name_1 = port.getName();
          String _plus = (_name_1 + "_");
          Object _safeId = this.getSafeId(connection, port);
          final String name = (_plus + _safeId);
          _builder_1.newLineIfNotEmpty();
          String _name_2 = action.getName();
          final String extName = (((name + "_") + stateName) + _name_2);
          _builder_1.newLineIfNotEmpty();
          final Integer numTokens = pattern.getNumTokensMap().get(port);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%size_");
          _builder_1.append(extName);
          _builder_1.append(" = load i32* @SIZE_");
          _builder_1.append(name);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%index_");
          _builder_1.append(extName);
          _builder_1.append(" = load i32* @index_");
          _builder_1.append(name);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%rdIndex_");
          _builder_1.append(extName);
          _builder_1.append(" = load i32* @rdIndex_");
          _builder_1.append(name);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%tmp_");
          _builder_1.append(extName);
          _builder_1.append(" = sub i32 %size_");
          _builder_1.append(extName);
          _builder_1.append(", %index_");
          _builder_1.append(extName);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("%status_");
          _builder_1.append(extName);
          _builder_1.append(" = add i32 %tmp_");
          _builder_1.append(extName);
          _builder_1.append(", %rdIndex_");
          _builder_1.append(extName);
          _builder_1.newLineIfNotEmpty();
          {
            boolean _equals = connection.equals(IterableExtensions.<Connection>head(connections));
            boolean _not = (!_equals);
            if (_not) {
              int _indexOf = connections.indexOf(connection);
              int _minus = (_indexOf - 1);
              final Connection lastConnection = connections.get(_minus);
              _builder_1.newLineIfNotEmpty();
              String _name_3 = lastConnection.getSourcePort().getName();
              String _plus_1 = (_name_3 + "_");
              Object _safeId_1 = this.getSafeId(lastConnection, lastConnection.getSourcePort());
              final String lastName = (_plus_1 + _safeId_1);
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("%available_output_");
              _builder_1.append(extName);
              _builder_1.append(" = icmp sge i32 %status_");
              _builder_1.append(extName);
              _builder_1.append(", ");
              _builder_1.append(numTokens);
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("%has_valid_outputs_");
              _builder_1.append(extName);
              _builder_1.append(" = and i1 %has_valid_outputs_");
              _builder_1.append(lastName);
              _builder_1.append("_");
              _builder_1.append(stateName);
              String _name_4 = action.getName();
              _builder_1.append(_name_4);
              _builder_1.append(", %available_output_");
              _builder_1.append(extName);
              _builder_1.newLineIfNotEmpty();
            } else {
              _builder_1.append("%has_valid_outputs_");
              _builder_1.append(extName);
              _builder_1.append(" = icmp uge i32 %status_");
              _builder_1.append(extName);
              _builder_1.append(", ");
              _builder_1.append(numTokens);
              _builder_1.newLineIfNotEmpty();
            }
          }
        }
      }
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }
  
  private CharSequence printCallStartTokenFunctions() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        _builder.append("call void @read_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append("_");
        Object _safeId = this.getSafeId(connection, port);
        _builder.append(_safeId);
        _builder.append("()");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative = this.getNotNative(this.actor.getOutputs());
      for(final Port port_1 : _notNative) {
        {
          List<Connection> _get = this.outgoingPortMap.get(port_1);
          for(final Connection connection_1 : _get) {
            _builder.append("call void @write_");
            String _name_1 = port_1.getName();
            _builder.append(_name_1);
            _builder.append("_");
            Object _safeId_1 = this.getSafeId(connection_1, port_1);
            _builder.append(_safeId_1);
            _builder.append("()");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  protected CharSequence printCallEndTokenFunctions() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        final Connection connection = this.incomingPortMap.get(port);
        _builder.newLineIfNotEmpty();
        _builder.append("call void @read_end_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append("_");
        Object _safeId = this.getSafeId(connection, port);
        _builder.append(_safeId);
        _builder.append("()");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative = this.getNotNative(this.actor.getOutputs());
      for(final Port port_1 : _notNative) {
        {
          List<Connection> _get = this.outgoingPortMap.get(port_1);
          for(final Connection connection_1 : _get) {
            _builder.append("call void @write_end_");
            String _name_1 = port_1.getName();
            _builder.append(_name_1);
            _builder.append("_");
            Object _safeId_1 = this.getSafeId(connection_1, port_1);
            _builder.append(_safeId_1);
            _builder.append("()");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  protected CharSequence print(final Action action) {
    CharSequence _xblockexpression = null;
    {
      this.isActionAligned = false;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("define internal ");
      CharSequence _doSwitch = this.doSwitch(action.getScheduler().getReturnType());
      _builder.append(_doSwitch);
      _builder.append(" @");
      String _name = action.getScheduler().getName();
      _builder.append(_name);
      _builder.append("() nounwind {");
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
        Iterable<? extends Port> _notNative = this.getNotNative(action.getPeekPattern().getPorts());
        for(final Port port : _notNative) {
          _builder.append("\t");
          CharSequence _loadVar = this.loadVar(port, this.incomingPortMap.get(port), action.getBody().getName());
          _builder.append(_loadVar, "\t");
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
      {
        boolean _hasAttribute = action.hasAttribute(OrccAttributes.ALIGNED_ALWAYS);
        boolean _not = (!_hasAttribute);
        if (_not) {
          CharSequence _printCore = this.printCore(action, false);
          _builder.append(_printCore);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if (this.isActionAligned = action.hasAttribute(OrccAttributes.ALIGNABLE)) {
          CharSequence _printCore_1 = this.printCore(action, true);
          _builder.append(_printCore_1);
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printCore(final Action action, final boolean isAligned) {
    StringConcatenation _builder = new StringConcatenation();
    final Pattern inputPattern = action.getInputPattern();
    _builder.newLineIfNotEmpty();
    final Pattern outputPattern = action.getOutputPattern();
    _builder.newLineIfNotEmpty();
    _builder.append("define internal ");
    CharSequence _doSwitch = this.doSwitch(action.getBody().getReturnType());
    _builder.append(_doSwitch);
    _builder.append(" @");
    String _name = action.getBody().getName();
    _builder.append(_name);
    {
      if (isAligned) {
        _builder.append("_aligned");
      }
    }
    _builder.append("() ");
    {
      if (this.optionInline) {
        _builder.append("noinline ");
      }
    }
    _builder.append("nounwind {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    {
      EList<Var> _locals = action.getBody().getLocals();
      for(final Var local : _locals) {
        _builder.append("\t");
        CharSequence _declare = this.declare(local);
        _builder.append(_declare, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative = this.getNotNative(inputPattern.getPorts());
      for(final Port port : _notNative) {
        _builder.append("\t");
        CharSequence _loadVar = this.loadVar(port, this.incomingPortMap.get(port), action.getBody().getName());
        _builder.append(_loadVar, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative_1 = this.getNotNative(outputPattern.getPorts());
      for(final Port port_1 : _notNative_1) {
        {
          List<Connection> _get = this.outgoingPortMap.get(port_1);
          for(final Connection connection : _get) {
            _builder.append("\t");
            CharSequence _loadVar_1 = this.loadVar(port_1, connection, action.getBody().getName());
            _builder.append(_loadVar_1, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("br label %b");
    CharSequence _label = this.label(IterableExtensions.<Block>head(action.getBody().getBlocks()));
    _builder.append(_label, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Block> _blocks = action.getBody().getBlocks();
      for(final Block block : _blocks) {
        CharSequence _doSwitch_1 = this.doSwitch(block);
        _builder.append(_doSwitch_1);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative_2 = this.getNotNative(inputPattern.getPorts());
      for(final Port port_2 : _notNative_2) {
        _builder.append("\t");
        CharSequence _updateVar = this.updateVar(port_2, this.incomingPortMap.get(port_2), Integer.valueOf(inputPattern.getNumTokens(port_2)), action.getBody().getName());
        _builder.append(_updateVar, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative_3 = this.getNotNative(outputPattern.getPorts());
      for(final Port port_3 : _notNative_3) {
        {
          List<Connection> _get_1 = this.outgoingPortMap.get(port_3);
          for(final Connection connection_1 : _get_1) {
            _builder.append("\t");
            CharSequence _updateVar_1 = this.updateVar(port_3, connection_1, Integer.valueOf(outputPattern.getNumTokens(port_3)), action.getBody().getName());
            _builder.append(_updateVar_1, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("ret void");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence loadVar(final Port port, final Connection connection, final String actionName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("%local_size_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_");
    Object _safeId = this.getSafeId(connection, port);
    _builder.append(_safeId);
    _builder.append(" = load i32* @SIZE_");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("_");
    Object _safeId_1 = this.getSafeId(connection, port);
    _builder.append(_safeId_1);
    _builder.newLineIfNotEmpty();
    {
      if (((this.isActionAligned && port.hasAttribute(((actionName + "_") + OrccAttributes.ALIGNABLE))) || port.hasAttribute(OrccAttributes.ALIGNED_ALWAYS))) {
        _builder.append("%orig_local_index_");
        String _name_2 = port.getName();
        _builder.append(_name_2);
        _builder.append("_");
        Object _safeId_2 = this.getSafeId(connection, port);
        _builder.append(_safeId_2);
        _builder.append(" = load i32* @index_");
        String _name_3 = port.getName();
        _builder.append(_name_3);
        _builder.append("_");
        Object _safeId_3 = this.getSafeId(connection, port);
        _builder.append(_safeId_3);
        _builder.newLineIfNotEmpty();
        _builder.append("%local_index_");
        String _name_4 = port.getName();
        _builder.append(_name_4);
        _builder.append("_");
        Object _safeId_4 = this.getSafeId(connection, port);
        _builder.append(_safeId_4);
        _builder.append(" = urem i32 %orig_local_index_");
        String _name_5 = port.getName();
        _builder.append(_name_5);
        _builder.append("_");
        Object _safeId_5 = this.getSafeId(connection, port);
        _builder.append(_safeId_5);
        _builder.append(", %local_size_");
        String _name_6 = port.getName();
        _builder.append(_name_6);
        _builder.append("_");
        Object _safeId_6 = this.getSafeId(connection, port);
        _builder.append(_safeId_6);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("%local_index_");
        String _name_7 = port.getName();
        _builder.append(_name_7);
        _builder.append("_");
        Object _safeId_7 = this.getSafeId(connection, port);
        _builder.append(_safeId_7);
        _builder.append(" = load i32* @index_");
        String _name_8 = port.getName();
        _builder.append(_name_8);
        _builder.append("_");
        Object _safeId_8 = this.getSafeId(connection, port);
        _builder.append(_safeId_8);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence updateVar(final Port port, final Connection connection, final Integer numTokens, final String actionName) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((this.isActionAligned && port.hasAttribute(((actionName + "_") + OrccAttributes.ALIGNABLE))) || port.hasAttribute(OrccAttributes.ALIGNED_ALWAYS))) {
        _builder.append("%new_index_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append("_");
        Object _safeId = this.getSafeId(connection, port);
        _builder.append(_safeId);
        _builder.append(" = add i32 %orig_local_index_");
        String _name_1 = port.getName();
        _builder.append(_name_1);
        _builder.append("_");
        Object _safeId_1 = this.getSafeId(connection, port);
        _builder.append(_safeId_1);
        _builder.append(", ");
        _builder.append(numTokens);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("%new_index_");
        String _name_2 = port.getName();
        _builder.append(_name_2);
        _builder.append("_");
        Object _safeId_2 = this.getSafeId(connection, port);
        _builder.append(_safeId_2);
        _builder.append(" = add i32 %local_index_");
        String _name_3 = port.getName();
        _builder.append(_name_3);
        _builder.append("_");
        Object _safeId_3 = this.getSafeId(connection, port);
        _builder.append(_safeId_3);
        _builder.append(", ");
        _builder.append(numTokens);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("store i32 %new_index_");
    String _name_4 = port.getName();
    _builder.append(_name_4);
    _builder.append("_");
    Object _safeId_4 = this.getSafeId(connection, port);
    _builder.append(_safeId_4);
    _builder.append(", i32* @index_");
    String _name_5 = port.getName();
    _builder.append(_name_5);
    _builder.append("_");
    Object _safeId_5 = this.getSafeId(connection, port);
    _builder.append(_safeId_5);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence print(final Procedure procedure) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<Param, CharSequence> _function = new Function1<Param, CharSequence>() {
      @Override
      public CharSequence apply(final Param it) {
        return InstancePrinter.this.declare(it);
      }
    };
    final String parameters = IterableExtensions.<Param>join(procedure.getParameters(), ", ", _function);
    _builder.newLineIfNotEmpty();
    {
      if ((procedure.isNative() || IterableExtensions.isNullOrEmpty(procedure.getBlocks()))) {
        _builder.append("declare ");
        CharSequence _doSwitch = this.doSwitch(procedure.getReturnType());
        _builder.append(_doSwitch);
        _builder.append(" @");
        String _name = procedure.getName();
        _builder.append(_name);
        _builder.append("(");
        _builder.append(parameters);
        _builder.append(") nounwind");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("define internal ");
        CharSequence _doSwitch_1 = this.doSwitch(procedure.getReturnType());
        _builder.append(_doSwitch_1);
        _builder.append(" @");
        String _name_1 = procedure.getName();
        _builder.append(_name_1);
        _builder.append("(");
        _builder.append(parameters);
        _builder.append(") nounwind {");
        _builder.newLineIfNotEmpty();
        _builder.append("entry:");
        _builder.newLine();
        {
          EList<Var> _locals = procedure.getLocals();
          for(final Var local : _locals) {
            CharSequence _declare = this.declare(local);
            _builder.append(_declare);
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("br label %b");
        CharSequence _label = this.label(IterableExtensions.<Block>head(procedure.getBlocks()));
        _builder.append(_label, "\t");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        {
          EList<Block> _blocks = procedure.getBlocks();
          for(final Block block : _blocks) {
            CharSequence _doSwitch_2 = this.doSwitch(block);
            _builder.append(_doSwitch_2);
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence label(final Block block) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("b");
    int _number = block.getCfgNode().getNumber();
    _builder.append(_number);
    return _builder;
  }
  
  protected CharSequence declare(final Var variable) {
    CharSequence _xifexpression = null;
    boolean _isGlobal = variable.isGlobal();
    if (_isGlobal) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("@");
      String _name = variable.getName();
      _builder.append(_name);
      _builder.append(" = internal ");
      {
        boolean _isAssignable = variable.isAssignable();
        if (_isAssignable) {
          _builder.append("global");
        } else {
          _builder.append("constant");
        }
      }
      _builder.append(" ");
      CharSequence _doSwitch = this.doSwitch(variable.getType());
      _builder.append(_doSwitch);
      _builder.append(" ");
      CharSequence _initialize = this.initialize(variable);
      _builder.append(_initialize);
      _xifexpression = _builder;
    } else {
      CharSequence _xifexpression_1 = null;
      if ((variable.getType().isList() && (!this.castedList.contains(variable)))) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("%");
        String _name_1 = variable.getName();
        _builder_1.append(_name_1);
        _builder_1.append(" = alloca ");
        CharSequence _doSwitch_1 = this.doSwitch(variable.getType());
        _builder_1.append(_doSwitch_1);
        _xifexpression_1 = _builder_1;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  protected CharSequence initialize(final Var variable) {
    CharSequence _xifexpression = null;
    Expression _initialValue = variable.getInitialValue();
    boolean _tripleNotEquals = (_initialValue != null);
    if (_tripleNotEquals) {
      _xifexpression = this.doSwitch(variable.getInitialValue());
    } else {
      _xifexpression = "zeroinitializer";
    }
    return _xifexpression;
  }
  
  protected CharSequence declare(final Param param) {
    CharSequence _xblockexpression = null;
    {
      final String pName = param.getVariable().getName();
      final Type pType = param.getVariable().getType();
      CharSequence _xifexpression = null;
      boolean _isString = pType.isString();
      if (_isString) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("i8* %");
        _builder.append(pName);
        _xifexpression = _builder;
      } else {
        CharSequence _xifexpression_1 = null;
        boolean _isList = pType.isList();
        if (_isList) {
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _doSwitch = this.doSwitch(pType);
          _builder_1.append(_doSwitch);
          _builder_1.append("* noalias %");
          _builder_1.append(pName);
          _xifexpression_1 = _builder_1;
        } else {
          StringConcatenation _builder_2 = new StringConcatenation();
          CharSequence _doSwitch_1 = this.doSwitch(pType);
          _builder_2.append(_doSwitch_1);
          _builder_2.append(" %");
          _builder_2.append(pName);
          _xifexpression_1 = _builder_2;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private CharSequence printInput(final Connection connection, final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    final Object id = this.getSafeId(connection, port);
    _builder.newLineIfNotEmpty();
    String _name = port.getName();
    String _plus = (_name + "_");
    final String name = (_plus + id);
    _builder.newLineIfNotEmpty();
    final CharSequence addrSpace = this.getAddrSpace(connection);
    _builder.newLineIfNotEmpty();
    final CharSequence prop = this.getProperties(port);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("@SIZE_");
    _builder.append(name);
    _builder.append(" = internal constant i32 ");
    int _safeSize = this.safeSize(connection);
    _builder.append(_safeSize);
    _builder.newLineIfNotEmpty();
    _builder.append("@index_");
    _builder.append(name);
    _builder.append(" = internal global i32 0");
    _builder.newLineIfNotEmpty();
    _builder.append("@numTokens_");
    _builder.append(name);
    _builder.append(" = internal global i32 0");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("define internal void @read_");
    _builder.append(name);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("br label %read");
    _builder.newLine();
    _builder.newLine();
    _builder.append("read:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("%rdIndex = load");
    _builder.append(prop, "\t");
    _builder.append(" i32");
    _builder.append(addrSpace, "\t");
    _builder.append("* @fifo_");
    _builder.append(id, "\t");
    _builder.append("_rdIndex");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("store i32 %rdIndex, i32* @index_");
    _builder.append(name, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("%wrIndex = load");
    _builder.append(prop, "\t");
    _builder.append(" i32");
    _builder.append(addrSpace, "\t");
    _builder.append("* @fifo_");
    _builder.append(id, "\t");
    _builder.append("_wrIndex");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("%getNumTokens = sub i32 %wrIndex, %rdIndex");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("%numTokens = add i32 %rdIndex, %getNumTokens");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("store i32 %numTokens, i32* @numTokens_");
    _builder.append(name, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ret void");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("define internal void @read_end_");
    _builder.append(name);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("br label %read_end");
    _builder.newLine();
    _builder.newLine();
    _builder.append("read_end:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("%rdIndex = load i32* @index_");
    _builder.append(name, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("store");
    _builder.append(prop, "\t");
    _builder.append(" i32 %rdIndex, i32");
    _builder.append(addrSpace, "\t");
    _builder.append("* @fifo_");
    _builder.append(id, "\t");
    _builder.append("_rdIndex");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ret void");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printOutput(final Connection connection, final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    final Object id = this.getSafeId(connection, port);
    _builder.newLineIfNotEmpty();
    String _name = port.getName();
    String _plus = (_name + "_");
    final String name = (_plus + id);
    _builder.newLineIfNotEmpty();
    final CharSequence addrSpace = this.getAddrSpace(connection);
    _builder.newLineIfNotEmpty();
    final CharSequence prop = this.getProperties(port);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("@SIZE_");
    _builder.append(name);
    _builder.append(" = internal constant i32 ");
    int _safeSize = this.safeSize(connection);
    _builder.append(_safeSize);
    _builder.newLineIfNotEmpty();
    _builder.append("@index_");
    _builder.append(name);
    _builder.append(" = internal global i32 0");
    _builder.newLineIfNotEmpty();
    _builder.append("@rdIndex_");
    _builder.append(name);
    _builder.append(" = internal global i32 0");
    _builder.newLineIfNotEmpty();
    _builder.append("@numFree_");
    _builder.append(name);
    _builder.append(" = internal global i32 0");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("define internal void @write_");
    _builder.append(name);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("br label %write");
    _builder.newLine();
    _builder.newLine();
    _builder.append("write:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("%wrIndex = load");
    _builder.append(prop, "\t");
    _builder.append(" i32");
    _builder.append(addrSpace, "\t");
    _builder.append("* @fifo_");
    _builder.append(id, "\t");
    _builder.append("_wrIndex");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("store i32 %wrIndex, i32* @index_");
    _builder.append(name, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("%rdIndex = load");
    _builder.append(prop, "\t");
    _builder.append(" i32");
    _builder.append(addrSpace, "\t");
    _builder.append("* @fifo_");
    _builder.append(id, "\t");
    _builder.append("_rdIndex");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("store i32 %rdIndex, i32* @rdIndex_");
    _builder.append(name, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("%size = load i32* @SIZE_");
    _builder.append(name, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("%numTokens = sub i32 %wrIndex, %rdIndex");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("%getNumFree = sub i32 %size, %numTokens");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("%numFree = add i32 %wrIndex, %getNumFree");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("store i32 %numFree, i32* @numFree_");
    _builder.append(name, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ret void");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("define internal void @write_end_");
    _builder.append(name);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("br label %write_end");
    _builder.newLine();
    _builder.newLine();
    _builder.append("write_end:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("%wrIndex = load i32* @index_");
    _builder.append(name, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("store");
    _builder.append(prop, "\t");
    _builder.append(" i32 %wrIndex, i32");
    _builder.append(addrSpace, "\t");
    _builder.append("* @fifo_");
    _builder.append(id, "\t");
    _builder.append("_wrIndex");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ret void");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Returns an annotation describing the address space.
   * This annotation is required by the TTA backend.
   */
  protected CharSequence getAddrSpace(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  /**
   * Returns an annotation describing the properties of the access.
   * This annotation is required by the TTA backend.
   */
  protected CharSequence getProperties(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  /**
   * Returns an annotation describing the properties of the access.
   * This annotation is required by the TTA backend.
   */
  protected CharSequence getProperties(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  private CharSequence printExternalFifo(final Connection conn, final Port port) {
    CharSequence _xblockexpression = null;
    {
      Object _safeId = this.getSafeId(conn, port);
      final String fifoName = ("fifo_" + _safeId);
      final CharSequence type = this.doSwitch(port.getType());
      CharSequence _xifexpression = null;
      if ((conn != null)) {
        CharSequence _xblockexpression_1 = null;
        {
          final CharSequence addrSpace = this.getAddrSpace(conn);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("@");
          _builder.append(fifoName);
          _builder.append("_content = external");
          _builder.append(addrSpace);
          _builder.append(" global [");
          int _safeSize = this.safeSize(conn);
          _builder.append(_safeSize);
          _builder.append(" x ");
          _builder.append(type);
          _builder.append("]");
          _builder.newLineIfNotEmpty();
          _builder.append("@");
          _builder.append(fifoName);
          _builder.append("_rdIndex = external");
          _builder.append(addrSpace);
          _builder.append(" global i32");
          _builder.newLineIfNotEmpty();
          _builder.append("@");
          _builder.append(fifoName);
          _builder.append("_wrIndex = external");
          _builder.append(addrSpace);
          _builder.append(" global i32");
          _builder.newLineIfNotEmpty();
          _xblockexpression_1 = _builder;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        CharSequence _xblockexpression_2 = null;
        {
          String _name = port.getName();
          String _plus = ((("[" + this.name) + "] Port ") + _name);
          String _plus_1 = (_plus + " not connected.");
          OrccLogger.noticeln(_plus_1);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("@");
          _builder.append(fifoName);
          _builder.append("_content = internal global [");
          int _safeSize = this.safeSize(conn);
          _builder.append(_safeSize);
          _builder.append(" x ");
          _builder.append(type);
          _builder.append("] zeroinitializer");
          _builder.newLineIfNotEmpty();
          _builder.append("@");
          _builder.append(fifoName);
          _builder.append("_rdIndex = internal global i32 zeroinitializer");
          _builder.newLineIfNotEmpty();
          _builder.append("@");
          _builder.append(fifoName);
          _builder.append("_wrIndex = internal global i32 zeroinitializer");
          _builder.newLineIfNotEmpty();
          _xblockexpression_2 = _builder;
        }
        _xifexpression = _xblockexpression_2;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private CharSequence getNextLabel(final Block block) {
    CharSequence _xifexpression = null;
    boolean _isBlockWhile = block.isBlockWhile();
    if (_isBlockWhile) {
      _xifexpression = this.label(((BlockWhile) block).getJoinBlock());
    } else {
      _xifexpression = this.label(block);
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseBlockBasic(final BlockBasic blockBasic) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("b");
    CharSequence _label = this.label(blockBasic);
    _builder.append(_label);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    {
      EList<Instruction> _instructions = blockBasic.getInstructions();
      for(final Instruction instr : _instructions) {
        _builder.append("\t");
        CharSequence _doSwitch = this.doSwitch(instr);
        _builder.append(_doSwitch, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty = blockBasic.getCfgNode().getSuccessors().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("br label %b");
        Vertex _head = IterableExtensions.<Vertex>head(blockBasic.getCfgNode().getSuccessors());
        CharSequence _nextLabel = this.getNextLabel(((CfgNode) _head).getNode());
        _builder.append(_nextLabel, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseBlockIf(final BlockIf blockIf) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("b");
    CharSequence _label = this.label(blockIf);
    _builder.append(_label);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("br i1 ");
    CharSequence _doSwitch = this.doSwitch(blockIf.getCondition());
    _builder.append(_doSwitch, "\t");
    _builder.append(", label %b");
    CharSequence _nextLabel = this.getNextLabel(IterableExtensions.<Block>head(blockIf.getThenBlocks()));
    _builder.append(_nextLabel, "\t");
    _builder.append(", label %b");
    CharSequence _nextLabel_1 = this.getNextLabel(IterableExtensions.<Block>head(blockIf.getElseBlocks()));
    _builder.append(_nextLabel_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Block> _thenBlocks = blockIf.getThenBlocks();
      for(final Block block : _thenBlocks) {
        CharSequence _doSwitch_1 = this.doSwitch(block);
        _builder.append(_doSwitch_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<Block> _elseBlocks = blockIf.getElseBlocks();
      for(final Block block_1 : _elseBlocks) {
        CharSequence _doSwitch_2 = this.doSwitch(block_1);
        _builder.append(_doSwitch_2);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    CharSequence _doSwitch_3 = this.doSwitch(blockIf.getJoinBlock());
    _builder.append(_doSwitch_3);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public CharSequence caseBlockWhile(final BlockWhile blockwhile) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("b");
    CharSequence _label = this.label(blockwhile.getJoinBlock());
    _builder.append(_label);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    {
      EList<Instruction> _instructions = blockwhile.getJoinBlock().getInstructions();
      for(final Instruction instr : _instructions) {
        _builder.append("\t");
        CharSequence _doSwitch = this.doSwitch(instr);
        _builder.append(_doSwitch, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("br i1 ");
    CharSequence _doSwitch_1 = this.doSwitch(blockwhile.getCondition());
    _builder.append(_doSwitch_1, "\t");
    _builder.append(", label %b");
    CharSequence _label_1 = this.label(IterableExtensions.<Block>head(blockwhile.getBlocks()));
    _builder.append(_label_1, "\t");
    _builder.append(", label %b");
    CharSequence _label_2 = this.label(blockwhile);
    _builder.append(_label_2, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Block> _blocks = blockwhile.getBlocks();
      for(final Block block : _blocks) {
        CharSequence _doSwitch_2 = this.doSwitch(block);
        _builder.append(_doSwitch_2);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("b");
    CharSequence _label_3 = this.label(blockwhile);
    _builder.append(_label_3);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("br label %b");
    Vertex _head = IterableExtensions.<Vertex>head(blockwhile.getCfgNode().getSuccessors());
    CharSequence _nextLabel = this.getNextLabel(((CfgNode) _head).getNode());
    _builder.append(_nextLabel, "\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public CharSequence caseInstruction(final Instruction instr) {
    CharSequence _xifexpression = null;
    if ((instr instanceof InstCast)) {
      return this.caseInstCast(((InstCast) instr));
    } else {
      _xifexpression = super.caseInstruction(instr);
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseExpression(final Expression expr) {
    CharSequence _xifexpression = null;
    if ((expr instanceof ExprNull)) {
      return this.caseExprNull(((ExprNull) expr));
    } else {
      _xifexpression = super.caseExpression(expr);
    }
    return _xifexpression;
  }
  
  public CharSequence caseExprNull(final ExprNull expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("null");
    return _builder;
  }
  
  public CharSequence caseInstCast(final InstCast cast) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("%");
    String _name = cast.getTarget().getVariable().getName();
    _builder.append(_name);
    _builder.append(" = ");
    CharSequence _castOp = this.getCastOp(cast);
    _builder.append(_castOp);
    _builder.append(" ");
    CharSequence _castType = this.getCastType(cast.getSource().getVariable());
    _builder.append(_castType);
    _builder.append(" ");
    CharSequence _print = this.print(cast.getSource().getVariable());
    _builder.append(_print);
    _builder.append(" to ");
    CharSequence _castType_1 = this.getCastType(cast.getTarget().getVariable());
    _builder.append(_castType_1);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence getCastOp(final InstCast cast) {
    CharSequence _xifexpression = null;
    boolean _isList = cast.getSource().getVariable().getType().isList();
    if (_isList) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("bitcast");
      _xifexpression = _builder;
    } else {
      CharSequence _xifexpression_1 = null;
      boolean _isExtended = cast.isExtended();
      boolean _not = (!_isExtended);
      if (_not) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("trunc");
        _xifexpression_1 = _builder_1;
      } else {
        CharSequence _xifexpression_2 = null;
        boolean _isSigned = cast.isSigned();
        if (_isSigned) {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("sext");
          _xifexpression_2 = _builder_2;
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("zext");
          _xifexpression_2 = _builder_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  private CharSequence getCastType(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(variable.getType());
    _builder.append(_doSwitch);
    {
      boolean _isList = variable.getType().isList();
      if (_isList) {
        _builder.append("*");
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseInstAssign(final InstAssign assign) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("%");
    String _name = assign.getTarget().getVariable().getName();
    _builder.append(_name);
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(assign.getValue());
    _builder.append(_doSwitch);
    return _builder;
  }
  
  @Override
  public CharSequence caseInstPhi(final InstPhi phi) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _print = this.print(phi.getTarget().getVariable());
    _builder.append(_print);
    _builder.append(" = phi ");
    CharSequence _doSwitch = this.doSwitch(phi.getTarget().getVariable().getType());
    _builder.append(_doSwitch);
    _builder.append(" ");
    CharSequence _phiPairs = this.getPhiPairs(phi);
    _builder.append(_phiPairs);
    return _builder;
  }
  
  private CharSequence getPhiPairs(final InstPhi phi) {
    StringConcatenation _builder = new StringConcatenation();
    Vertex _head = IterableExtensions.<Vertex>head(phi.getBlock().getCfgNode().getPredecessors());
    CharSequence _printPhiExpr = this.printPhiExpr(IterableExtensions.<Expression>head(phi.getValues()), ((CfgNode) _head).getNode());
    _builder.append(_printPhiExpr);
    _builder.append(", ");
    Vertex _head_1 = IterableExtensions.<Vertex>head(IterableExtensions.<Vertex>tail(phi.getBlock().getCfgNode().getPredecessors()));
    CharSequence _printPhiExpr_1 = this.printPhiExpr(IterableExtensions.<Expression>head(IterableExtensions.<Expression>tail(phi.getValues())), ((CfgNode) _head_1).getNode());
    _builder.append(_printPhiExpr_1);
    return _builder;
  }
  
  private CharSequence printPhiExpr(final Expression expr, final Block block) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("[");
    CharSequence _doSwitch = this.doSwitch(expr);
    _builder.append(_doSwitch);
    _builder.append(", %b");
    CharSequence _label = this.label(block);
    _builder.append(_label);
    _builder.append("]");
    return _builder;
  }
  
  @Override
  public CharSequence caseInstReturn(final InstReturn retInst) {
    CharSequence _xblockexpression = null;
    {
      final Action action = EcoreHelper.<Action>getContainerOfType(retInst, Action.class);
      CharSequence _xifexpression = null;
      if (((action == null) || Objects.equal(EcoreHelper.<Procedure>getContainerOfType(retInst, Procedure.class), action.getScheduler()))) {
        CharSequence _xifexpression_1 = null;
        Expression _value = retInst.getValue();
        boolean _tripleEquals = (_value == null);
        if (_tripleEquals) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("ret void");
          _xifexpression_1 = _builder;
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("ret ");
          CharSequence _doSwitch = this.doSwitch(retInst.getValue().getType());
          _builder_1.append(_doSwitch);
          _builder_1.append(" ");
          CharSequence _doSwitch_1 = this.doSwitch(retInst.getValue());
          _builder_1.append(_doSwitch_1);
          _xifexpression_1 = _builder_1;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseInstStore(final InstStore store) {
    CharSequence _xblockexpression = null;
    {
      final Action action = EcoreHelper.<Action>getContainerOfType(store, Action.class);
      final Var variable = store.getTarget().getVariable();
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _isList = variable.getType().isList();
        if (_isList) {
          Type _type = variable.getType();
          final Type innerType = ((TypeList) _type).getInnermostType();
          _builder.newLineIfNotEmpty();
          {
            if ((((action != null) && action.getOutputPattern().contains(variable)) && (!action.getOutputPattern().getVarToPortMap().get(variable).isNative()))) {
              final Port port = action.getOutputPattern().getVarToPortMap().get(variable);
              _builder.newLineIfNotEmpty();
              {
                List<Connection> _get = this.outgoingPortMap.get(port);
                for(final Connection connection : _get) {
                  CharSequence _printPortAccess = this.printPortAccess(action, connection, port, variable, IterableExtensions.<Expression>head(store.getIndexes()), store);
                  _builder.append(_printPortAccess);
                  _builder.newLineIfNotEmpty();
                  _builder.append("store");
                  CharSequence _properties = this.getProperties(port);
                  _builder.append(_properties);
                  _builder.append(" ");
                  CharSequence _doSwitch = this.doSwitch(innerType);
                  _builder.append(_doSwitch);
                  _builder.append(" ");
                  CharSequence _doSwitch_1 = this.doSwitch(store.getValue());
                  _builder.append(_doSwitch_1);
                  _builder.append(", ");
                  CharSequence _doSwitch_2 = this.doSwitch(innerType);
                  _builder.append(_doSwitch_2);
                  CharSequence _addrSpace = this.getAddrSpace(connection);
                  _builder.append(_addrSpace);
                  _builder.append("* ");
                  CharSequence _varName = this.varName(variable, store);
                  _builder.append(_varName);
                  _builder.append("_");
                  Object _safeId = this.getSafeId(connection, port);
                  _builder.append(_safeId);
                  _builder.newLineIfNotEmpty();
                }
              }
            } else {
              CharSequence _varName_1 = this.varName(variable, store);
              _builder.append(_varName_1);
              _builder.append(" = getelementptr ");
              CharSequence _doSwitch_3 = this.doSwitch(variable.getType());
              _builder.append(_doSwitch_3);
              _builder.append("* ");
              CharSequence _print = this.print(variable);
              _builder.append(_print);
              _builder.append(", i32 0");
              final Function1<Expression, CharSequence> _function = new Function1<Expression, CharSequence>() {
                @Override
                public CharSequence apply(final Expression it) {
                  return InstancePrinter.this.printIndex(it);
                }
              };
              String _join = IterableExtensions.<Expression>join(store.getIndexes(), ", ", ", ", "", _function);
              _builder.append(_join);
              _builder.newLineIfNotEmpty();
              _builder.append("store ");
              CharSequence _doSwitch_4 = this.doSwitch(innerType);
              _builder.append(_doSwitch_4);
              _builder.append(" ");
              CharSequence _doSwitch_5 = this.doSwitch(store.getValue());
              _builder.append(_doSwitch_5);
              _builder.append(", ");
              CharSequence _doSwitch_6 = this.doSwitch(innerType);
              _builder.append(_doSwitch_6);
              _builder.append("* ");
              CharSequence _varName_2 = this.varName(variable, store);
              _builder.append(_varName_2);
              _builder.newLineIfNotEmpty();
            }
          }
        } else {
          _builder.append("store ");
          CharSequence _doSwitch_7 = this.doSwitch(variable.getType());
          _builder.append(_doSwitch_7);
          _builder.append(" ");
          CharSequence _doSwitch_8 = this.doSwitch(store.getValue());
          _builder.append(_doSwitch_8);
          _builder.append(", ");
          CharSequence _doSwitch_9 = this.doSwitch(variable.getType());
          _builder.append(_doSwitch_9);
          _builder.append("* ");
          CharSequence _print_1 = this.print(variable);
          _builder.append(_print_1);
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseInstLoad(final InstLoad load) {
    CharSequence _xblockexpression = null;
    {
      final Action action = EcoreHelper.<Action>getContainerOfType(load, Action.class);
      final Var variable = load.getSource().getVariable();
      final CharSequence target = this.print(load.getTarget().getVariable());
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _isList = variable.getType().isList();
        if (_isList) {
          Type _type = variable.getType();
          final Type innerType = ((TypeList) _type).getInnermostType();
          _builder.newLineIfNotEmpty();
          {
            if ((((action != null) && action.getInputPattern().contains(variable)) && (!action.getInputPattern().getVarToPortMap().get(variable).isNative()))) {
              final Port port = action.getInputPattern().getVarToPortMap().get(variable);
              _builder.newLineIfNotEmpty();
              final Connection connection = this.incomingPortMap.get(port);
              _builder.newLineIfNotEmpty();
              CharSequence _printPortAccess = this.printPortAccess(action, connection, port, variable, IterableExtensions.<Expression>head(load.getIndexes()), load);
              _builder.append(_printPortAccess);
              _builder.newLineIfNotEmpty();
              _builder.append(target);
              _builder.append(" = load");
              CharSequence _properties = this.getProperties(port);
              _builder.append(_properties);
              _builder.append(" ");
              CharSequence _doSwitch = this.doSwitch(innerType);
              _builder.append(_doSwitch);
              CharSequence _addrSpace = this.getAddrSpace(connection);
              _builder.append(_addrSpace);
              _builder.append("* ");
              CharSequence _varName = this.varName(variable, load);
              _builder.append(_varName);
              _builder.append("_");
              Object _safeId = this.getSafeId(connection, port);
              _builder.append(_safeId);
              _builder.newLineIfNotEmpty();
            } else {
              if ((((action != null) && action.getOutputPattern().contains(variable)) && (!action.getOutputPattern().getVarToPortMap().get(variable).isNative()))) {
                final Port port_1 = action.getOutputPattern().getVarToPortMap().get(variable);
                _builder.newLineIfNotEmpty();
                final Connection connection_1 = IterableExtensions.<Connection>head(this.outgoingPortMap.get(port_1));
                _builder.newLineIfNotEmpty();
                CharSequence _printPortAccess_1 = this.printPortAccess(action, connection_1, port_1, variable, IterableExtensions.<Expression>head(load.getIndexes()), load);
                _builder.append(_printPortAccess_1);
                _builder.newLineIfNotEmpty();
                _builder.append(target);
                _builder.append(" = load");
                CharSequence _properties_1 = this.getProperties(port_1);
                _builder.append(_properties_1);
                _builder.append(" ");
                CharSequence _doSwitch_1 = this.doSwitch(innerType);
                _builder.append(_doSwitch_1);
                CharSequence _addrSpace_1 = this.getAddrSpace(connection_1);
                _builder.append(_addrSpace_1);
                _builder.append("* ");
                CharSequence _varName_1 = this.varName(variable, load);
                _builder.append(_varName_1);
                _builder.append("_");
                Object _safeId_1 = this.getSafeId(connection_1, port_1);
                _builder.append(_safeId_1);
                _builder.newLineIfNotEmpty();
              } else {
                if (((action != null) && action.getPeekPattern().contains(variable))) {
                  final Port port_2 = action.getPeekPattern().getVarToPortMap().get(variable);
                  _builder.newLineIfNotEmpty();
                  final Connection connection_2 = this.incomingPortMap.get(port_2);
                  _builder.newLineIfNotEmpty();
                  CharSequence _printPortAccess_2 = this.printPortAccess(action, connection_2, port_2, variable, IterableExtensions.<Expression>head(load.getIndexes()), load);
                  _builder.append(_printPortAccess_2);
                  _builder.newLineIfNotEmpty();
                  _builder.append(target);
                  _builder.append(" = load");
                  CharSequence _properties_2 = this.getProperties(port_2);
                  _builder.append(_properties_2);
                  _builder.append(" ");
                  CharSequence _doSwitch_2 = this.doSwitch(innerType);
                  _builder.append(_doSwitch_2);
                  CharSequence _addrSpace_2 = this.getAddrSpace(connection_2);
                  _builder.append(_addrSpace_2);
                  _builder.append("* ");
                  CharSequence _varName_2 = this.varName(variable, load);
                  _builder.append(_varName_2);
                  _builder.append("_");
                  Object _safeId_2 = this.getSafeId(connection_2, port_2);
                  _builder.append(_safeId_2);
                  _builder.newLineIfNotEmpty();
                } else {
                  CharSequence _varName_3 = this.varName(variable, load);
                  _builder.append(_varName_3);
                  _builder.append(" = getelementptr ");
                  CharSequence _doSwitch_3 = this.doSwitch(variable.getType());
                  _builder.append(_doSwitch_3);
                  _builder.append("* ");
                  CharSequence _print = this.print(variable);
                  _builder.append(_print);
                  _builder.append(", i32 0");
                  final Function1<Expression, CharSequence> _function = new Function1<Expression, CharSequence>() {
                    @Override
                    public CharSequence apply(final Expression it) {
                      return InstancePrinter.this.printIndex(it);
                    }
                  };
                  String _join = IterableExtensions.<Expression>join(load.getIndexes(), ", ", ", ", "", _function);
                  _builder.append(_join);
                  _builder.newLineIfNotEmpty();
                  _builder.append(target);
                  _builder.append(" = load");
                  CharSequence _properties_3 = this.getProperties(variable);
                  _builder.append(_properties_3);
                  _builder.append(" ");
                  CharSequence _doSwitch_4 = this.doSwitch(innerType);
                  _builder.append(_doSwitch_4);
                  _builder.append("* ");
                  CharSequence _varName_4 = this.varName(variable, load);
                  _builder.append(_varName_4);
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
        } else {
          _builder.append(target);
          _builder.append(" = load");
          CharSequence _properties_4 = this.getProperties(variable);
          _builder.append(_properties_4);
          _builder.append(" ");
          CharSequence _doSwitch_5 = this.doSwitch(variable.getType());
          _builder.append(_doSwitch_5);
          _builder.append("* ");
          CharSequence _print_1 = this.print(variable);
          _builder.append(_print_1);
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printIndex(final Expression index) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(index.getType());
    _builder.append(_doSwitch);
    _builder.append(" ");
    CharSequence _doSwitch_1 = this.doSwitch(index);
    _builder.append(_doSwitch_1);
    return _builder;
  }
  
  @Override
  public CharSequence caseInstCall(final InstCall call) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isPrint = call.isPrint();
      if (_isPrint) {
        _builder.append("call i32 (i8*, ...)* @printf(");
        final Function1<Arg, CharSequence> _function = new Function1<Arg, CharSequence>() {
          @Override
          public CharSequence apply(final Arg it) {
            return InstancePrinter.this.printArgument(it, ((ArgByVal) it).getValue().getType());
          }
        };
        String _join = IterableExtensions.<Arg>join(call.getArguments(), ", ", _function);
        _builder.append(_join);
        _builder.append(")");
        _builder.newLineIfNotEmpty();
      } else {
        {
          Def _target = call.getTarget();
          boolean _tripleNotEquals = (_target != null);
          if (_tripleNotEquals) {
            _builder.append("%");
            String _name = call.getTarget().getVariable().getName();
            _builder.append(_name);
            _builder.append(" = ");
          }
        }
        _builder.append("call ");
        CharSequence _doSwitch = this.doSwitch(call.getProcedure().getReturnType());
        _builder.append(_doSwitch);
        _builder.append(" @");
        String _name_1 = call.getProcedure().getName();
        _builder.append(_name_1);
        _builder.append(" (");
        String _join_1 = IterableExtensions.join(this.format(call.getArguments(), call.getProcedure().getParameters()), ", ");
        _builder.append(_join_1);
        _builder.append(")");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected ArrayList<CharSequence> format(final EList<Arg> args, final EList<Param> params) {
    final ArrayList<CharSequence> paramList = new ArrayList<CharSequence>();
    int _size = params.size();
    boolean _notEquals = (_size != 0);
    if (_notEquals) {
      int _size_1 = params.size();
      int _minus = (_size_1 - 1);
      IntegerRange _upTo = new IntegerRange(0, _minus);
      for (final Integer i : _upTo) {
        paramList.add(this.printArgument(args.get((i).intValue()), params.get((i).intValue()).getVariable().getType()));
      }
    }
    return paramList;
  }
  
  protected CharSequence printArgument(final Arg arg, final Type type) {
    CharSequence _xifexpression = null;
    boolean _isByRef = arg.isByRef();
    if (_isByRef) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("TODO");
      _xifexpression = _builder;
    } else {
      CharSequence _xifexpression_1 = null;
      boolean _isString = type.isString();
      if (_isString) {
        CharSequence _xblockexpression = null;
        {
          Expression _value = ((ArgByVal) arg).getValue();
          final ExprVar expr = ((ExprVar) _value);
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("i8* ");
          {
            boolean _isLocal = expr.getUse().getVariable().isLocal();
            if (_isLocal) {
              _builder_1.append(" ");
              CharSequence _doSwitch = this.doSwitch(expr);
              _builder_1.append(_doSwitch);
              _builder_1.append(" ");
            } else {
              _builder_1.append(" getelementptr (");
              CharSequence _doSwitch_1 = this.doSwitch(expr.getType());
              _builder_1.append(_doSwitch_1);
              _builder_1.append("* ");
              CharSequence _doSwitch_2 = this.doSwitch(expr);
              _builder_1.append(_doSwitch_2);
              _builder_1.append(", i32 0, i32 0)");
            }
          }
          _xblockexpression = _builder_1;
        }
        _xifexpression_1 = _xblockexpression;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _doSwitch = this.doSwitch(type);
        _builder_1.append(_doSwitch);
        {
          boolean _isList = type.isList();
          if (_isList) {
            _builder_1.append("*");
          }
        }
        _builder_1.append(" ");
        CharSequence _doSwitch_1 = this.doSwitch(((ArgByVal) arg).getValue());
        _builder_1.append(_doSwitch_1);
        _xifexpression_1 = _builder_1;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  protected CharSequence varName(final Var variable, final Instruction instr) {
    CharSequence _xblockexpression = null;
    {
      final Procedure procedure = EcoreHelper.<Procedure>getContainerOfType(instr, Procedure.class);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("%");
      String _name = variable.getName();
      _builder.append(_name);
      _builder.append("_elt_");
      Object _objectValue = procedure.getAttribute("accessMap").getObjectValue();
      Integer _get = ((Map<Instruction, Integer>) _objectValue).get(instr);
      _builder.append(_get);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  private CharSequence printPortAccess(final Action action, final Connection connection, final Port port, final Var variable, final Expression index, final Instruction instr) {
    CharSequence _xblockexpression = null;
    {
      final Procedure procedure = EcoreHelper.<Procedure>getContainerOfType(instr, Procedure.class);
      Object _objectValue = procedure.getAttribute("accessMap").getObjectValue();
      final Map<Instruction, Integer> accessMap = ((Map<Instruction, Integer>) _objectValue);
      final Integer accessId = accessMap.get(instr);
      final int indexSize = index.getType().getSizeInBits();
      final boolean needCast = ((indexSize != 32) && (!index.isExprInt()));
      final Object connId = this.getSafeId(connection, port);
      String _name = port.getName();
      String _plus = (_name + "_");
      final String fifoName = (_plus + connId);
      String _name_1 = variable.getName();
      String _plus_1 = (_name_1 + "_");
      String _plus_2 = (_plus_1 + accessId);
      String _plus_3 = (_plus_2 + "_");
      final String extName = (_plus_3 + connId);
      StringConcatenation _builder = new StringConcatenation();
      {
        if (needCast) {
          _builder.append("%cast_index_");
          _builder.append(extName);
          _builder.append(" = ");
          {
            if ((indexSize < 32)) {
              _builder.append("zext");
            } else {
              _builder.append("trunc");
            }
          }
          _builder.append(" ");
          CharSequence _doSwitch = this.doSwitch(index.getType());
          _builder.append(_doSwitch);
          _builder.append(" ");
          CharSequence _doSwitch_1 = this.doSwitch(index);
          _builder.append(_doSwitch_1);
          _builder.append(" to i32");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if (((this.isActionAligned && port.hasAttribute(((action.getName() + "_") + OrccAttributes.ALIGNABLE))) || port.hasAttribute(OrccAttributes.ALIGNED_ALWAYS))) {
          _builder.append("%final_index_");
          _builder.append(extName);
          _builder.append(" = add i32 %local_index_");
          _builder.append(fifoName);
          _builder.append(", ");
          {
            if (needCast) {
              _builder.append("%cast_index_");
              _builder.append(extName);
            } else {
              CharSequence _doSwitch_2 = this.doSwitch(index);
              _builder.append(_doSwitch_2);
            }
          }
          _builder.newLineIfNotEmpty();
        } else {
          _builder.append("%tmp_index_");
          _builder.append(extName);
          _builder.append(" = add i32 %local_index_");
          _builder.append(fifoName);
          _builder.append(", ");
          {
            if (needCast) {
              _builder.append("%cast_index_");
              _builder.append(extName);
            } else {
              CharSequence _doSwitch_3 = this.doSwitch(index);
              _builder.append(_doSwitch_3);
            }
          }
          _builder.newLineIfNotEmpty();
          _builder.append("%final_index_");
          _builder.append(extName);
          _builder.append(" = urem i32 %tmp_index_");
          _builder.append(extName);
          _builder.append(", %local_size_");
          _builder.append(fifoName);
          _builder.newLineIfNotEmpty();
        }
      }
      CharSequence _varName = this.varName(variable, instr);
      _builder.append(_varName);
      _builder.append("_");
      _builder.append(connId);
      _builder.append(" = getelementptr [");
      int _safeSize = this.safeSize(connection);
      _builder.append(_safeSize);
      _builder.append(" x ");
      CharSequence _doSwitch_4 = this.doSwitch(port.getType());
      _builder.append(_doSwitch_4);
      _builder.append("]");
      CharSequence _addrSpace = this.getAddrSpace(connection);
      _builder.append(_addrSpace);
      _builder.append("* @fifo_");
      _builder.append(connId);
      _builder.append("_content, i32 0, i32 %final_index_");
      _builder.append(extName);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected void computeCastedList() {
    Iterable<Var> _filter = Iterables.<Var>filter(IteratorExtensions.<EObject>toIterable(this.actor.eAllContents()), Var.class);
    for (final Var variable : _filter) {
      if (((variable.getType().isList() && (!variable.getDefs().isEmpty())) && (IterableExtensions.<Def>head(variable.getDefs()).eContainer() instanceof InstCast))) {
        this.castedList.add(variable);
      }
    }
  }
  
  private void computeStateToLabel() {
    boolean _hasFsm = this.actor.hasFsm();
    if (_hasFsm) {
      int i = 0;
      EList<State> _states = this.actor.getFsm().getStates();
      for (final State state : _states) {
        {
          this.stateToLabel.put(state, Integer.valueOf(i));
          i = (i + 1);
        }
      }
    }
  }
  
  private void computePortToIndexByPatternMap() {
    Iterable<Pattern> _filter = Iterables.<Pattern>filter(IteratorExtensions.<EObject>toIterable(this.actor.eAllContents()), Pattern.class);
    for (final Pattern pattern : _filter) {
      {
        final HashMap<Port, Integer> portToIndex = new HashMap<Port, Integer>();
        int i = 1;
        EList<Port> _ports = pattern.getPorts();
        for (final Port port : _ports) {
          {
            portToIndex.put(port, Integer.valueOf(i));
            i = (i + 1);
          }
        }
        this.portToIndexByPatternMap.put(pattern, portToIndex);
      }
    }
  }
}
