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
package net.sf.orcc.backends.java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.File;
import java.util.List;
import java.util.Map;
import net.sf.orcc.backends.java.JavaTemplate;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.util.EcoreHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Compile Top_network Java source code
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class ActorPrinter extends JavaTemplate {
  private Actor actor;
  
  private Instance instance;
  
  public ActorPrinter(final Instance instance, final Map<String, Object> options) {
    this.instance = instance;
    this.actor = instance.getActor();
  }
  
  public int print(final String targetFolder) {
    final CharSequence content = this.getActorFileContent();
    String _simpleName = this.actor.getSimpleName();
    String _plus = ((targetFolder + File.separator) + _simpleName);
    String _plus_1 = (_plus + ".java");
    final File file = new File(_plus_1);
    boolean _needToWriteFile = this.needToWriteFile(content, file);
    if (_needToWriteFile) {
      OrccUtil.printFile(content, file);
      return 0;
    } else {
      return 1;
    }
  }
  
  public CharSequence getActorFileContent() {
    CharSequence _xblockexpression = null;
    {
      EObject _eContainer = this.instance.eContainer();
      final Network network = ((Network) _eContainer);
      String _xifexpression = null;
      if ((network != null)) {
        _xifexpression = network.getSimpleName();
      } else {
        _xifexpression = "";
      }
      final String packageName = _xifexpression;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/**");
      _builder.newLine();
      _builder.append(" ");
      _builder.append("* Generated from \"");
      String _fileName = this.actor.getFileName();
      _builder.append(_fileName, " ");
      _builder.append("\"");
      _builder.newLineIfNotEmpty();
      _builder.append(" ");
      _builder.append("*/");
      _builder.newLine();
      _builder.append("package ");
      _builder.append(packageName);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("import net.sf.orcc.runtime.Fifo;");
      _builder.newLine();
      _builder.append("import net.sf.orcc.runtime.actors.IActor;");
      _builder.newLine();
      {
        final Function1<Procedure, Boolean> _function = new Function1<Procedure, Boolean>() {
          @Override
          public Boolean apply(final Procedure proc) {
            return Boolean.valueOf((proc.isNative() && (!Objects.equal(proc.getName(), "print"))));
          }
        };
        boolean _exists = IterableExtensions.<Procedure>exists(this.actor.getProcs(), _function);
        if (_exists) {
          _builder.append("import net.sf.orcc.runtime.NativeProcedure;");
          _builder.newLine();
        }
      }
      _builder.newLine();
      _builder.append("public class ");
      String _simpleName = this.instance.getSimpleName();
      _builder.append(_simpleName);
      _builder.append(" implements IActor {");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("// Input FIFOs");
      _builder.newLine();
      {
        EList<Port> _inputs = this.actor.getInputs();
        for(final Port port : _inputs) {
          _builder.append("\t");
          _builder.append("private Fifo<");
          CharSequence _doSwitch = this.doSwitch(port.getType());
          _builder.append(_doSwitch, "\t");
          _builder.append("> fifo_");
          String _name = port.getName();
          _builder.append(_name, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      _builder.append("// Output FIFOs");
      _builder.newLine();
      {
        EList<Port> _outputs = this.actor.getOutputs();
        for(final Port port_1 : _outputs) {
          _builder.append("\t");
          _builder.append("private Fifo<");
          CharSequence _doSwitch_1 = this.doSwitch(port_1.getType());
          _builder.append(_doSwitch_1, "\t");
          _builder.append("> fifo_");
          String _name_1 = port_1.getName();
          _builder.append(_name_1, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Actor\'s parameters");
      _builder.newLine();
      {
        EList<Var> _parameters = this.actor.getParameters();
        for(final Var variable : _parameters) {
          _builder.append("\t");
          CharSequence _declareVariable = this.declareVariable(variable);
          _builder.append(_declareVariable, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      _builder.append("// Actor\'s state variables");
      _builder.newLine();
      {
        EList<Var> _stateVars = this.actor.getStateVars();
        for(final Var stateVar : _stateVars) {
          _builder.append("\t");
          CharSequence _declareVariable_1 = this.declareVariable(stateVar);
          _builder.append(_declareVariable_1, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      _builder.newLine();
      {
        int _size = this.actor.getParameters().size();
        boolean _greaterThan = (_size > 0);
        if (_greaterThan) {
          _builder.append("\t");
          _builder.append("//Constructor");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("public ");
          String _simpleName_1 = this.instance.getSimpleName();
          _builder.append(_simpleName_1, "\t");
          _builder.append("(");
          {
            EList<Var> _parameters_1 = this.actor.getParameters();
            boolean _hasElements = false;
            for(final Var param : _parameters_1) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate(", ", "\t");
              }
              CharSequence _doSwitch_2 = this.doSwitch(param.getType());
              _builder.append(_doSwitch_2, "\t");
              _builder.append(" ");
              String _name_2 = param.getName();
              _builder.append(_name_2, "\t");
            }
          }
          _builder.append(") {");
          _builder.newLineIfNotEmpty();
          {
            EList<Var> _parameters_2 = this.actor.getParameters();
            for(final Var param_1 : _parameters_2) {
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("this.");
              String _name_3 = param_1.getName();
              _builder.append(_name_3, "\t\t");
              _builder.append(" = ");
              String _name_4 = param_1.getName();
              _builder.append(_name_4, "\t\t");
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Functions/procedures");
      _builder.newLine();
      {
        EList<Procedure> _procs = this.actor.getProcs();
        for(final Procedure proc : _procs) {
          _builder.append("\t");
          CharSequence _doSwitch_3 = this.doSwitch(proc);
          _builder.append(_doSwitch_3, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/***********");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("* Actions");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("**********/");
      _builder.newLine();
      {
        EList<Action> _actions = this.actor.getActions();
        for(final Action action : _actions) {
          _builder.append("\t");
          CharSequence _printAction = this.printAction(action);
          _builder.append(_printAction, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      _builder.newLine();
      {
        boolean _isEmpty = this.actor.getInitializes().isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          {
            EList<Action> _initializes = this.actor.getInitializes();
            for(final Action init : _initializes) {
              _builder.append("\t");
              CharSequence _printAction_1 = this.printAction(init);
              _builder.append(_printAction_1, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("@Override");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("public void initialize() {");
      _builder.newLine();
      {
        boolean _isEmpty_1 = this.actor.getInitializes().isEmpty();
        boolean _not_1 = (!_isEmpty_1);
        if (_not_1) {
          {
            EList<Action> _initializes_1 = this.actor.getInitializes();
            for(final Action initAction : _initializes_1) {
              _builder.append("\t\t");
              String _name_5 = initAction.getName();
              _builder.append(_name_5, "\t\t");
              _builder.append("();");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("@Override");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("@SuppressWarnings(\"unchecked\")");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("public <T> void setFifo(String portName, Fifo<T> fifo) {");
      _builder.newLine();
      _builder.append("\t\t");
      {
        EList<Port> _inputs_1 = this.actor.getInputs();
        EList<Port> _outputs_1 = this.actor.getOutputs();
        Iterable<Port> _plus = Iterables.<Port>concat(_inputs_1, _outputs_1);
        for(final Port port_2 : _plus) {
          _builder.append("if (\"");
          String _name_6 = port_2.getName();
          _builder.append(_name_6, "\t\t");
          _builder.append("\".equals(portName)) {");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t");
          _builder.append("\t\t");
          _builder.append("fifo_");
          String _name_7 = port_2.getName();
          _builder.append(_name_7, "\t\t\t\t");
          _builder.append(" = (Fifo<");
          CharSequence _doSwitch_4 = this.doSwitch(port_2.getType());
          _builder.append(_doSwitch_4, "\t\t\t\t");
          _builder.append(">) fifo;");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t");
          _builder.append("} else ");
        }
      }
      _builder.append(" {");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t\t");
      _builder.append("String msg = \"unknown port \\\"\" + portName + \"\\\"\";");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("throw new IllegalArgumentException(msg);");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      {
        boolean _hasFsm = this.actor.hasFsm();
        if (_hasFsm) {
          _builder.append("\t");
          CharSequence _printFSMScheduler = this.printFSMScheduler();
          _builder.append(_printFSMScheduler, "\t");
          _builder.newLineIfNotEmpty();
        } else {
          _builder.append("\t");
          CharSequence _printSimpleScheduler = this.printSimpleScheduler();
          _builder.append(_printSimpleScheduler, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * Procedures / actions / methods
   */
  @Override
  public CharSequence caseProcedure(final Procedure procedure) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isNative = procedure.isNative();
      boolean _not = (!_isNative);
      if (_not) {
        _builder.append("private ");
        CharSequence _doSwitch = this.doSwitch(procedure.getReturnType());
        _builder.append(_doSwitch);
        _builder.append(" ");
        String _name = procedure.getName();
        _builder.append(_name);
        _builder.append("(");
        {
          EList<Param> _parameters = procedure.getParameters();
          boolean _hasElements = false;
          for(final Param param : _parameters) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(", ", "");
            }
            CharSequence _doSwitch_1 = this.doSwitch(param.getVariable().getType());
            _builder.append(_doSwitch_1);
            _builder.append(" ");
            String _name_1 = param.getVariable().getName();
            _builder.append(_name_1);
          }
        }
        _builder.append(") {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        {
          EList<Var> _locals = procedure.getLocals();
          for(final Var local : _locals) {
            CharSequence _declareVariable = this.declareVariable(local);
            _builder.append(_declareVariable, "\t");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        {
          EList<Block> _blocks = procedure.getBlocks();
          for(final Block block : _blocks) {
            CharSequence _doSwitch_2 = this.doSwitch(block);
            _builder.append(_doSwitch_2, "\t");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence printAction(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("public ");
    CharSequence _doSwitch = this.doSwitch(action.getScheduler().getReturnType());
    _builder.append(_doSwitch);
    _builder.append(" ");
    String _name = action.getScheduler().getName();
    _builder.append(_name);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    {
      EList<Var> _locals = action.getScheduler().getLocals();
      for(final Var localVar : _locals) {
        _builder.append("\t");
        CharSequence _declareVariable = this.declareVariable(localVar);
        _builder.append(_declareVariable, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _ports = action.getPeekPattern().getPorts();
      for(final Port peekPort : _ports) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(action.getPeekPattern().getPortToVarMap().get(peekPort).getType());
        _builder.append(_doSwitch_1, "\t");
        _builder.append(" ");
        String _name_1 = action.getPeekPattern().getPortToVarMap().get(peekPort).getName();
        _builder.append(_name_1, "\t");
        _builder.append(" = new ");
        Type _type = action.getPeekPattern().getPortToVarMap().get(peekPort).getType();
        CharSequence _doSwitch_2 = this.doSwitch(((TypeList) _type).getInnermostType());
        _builder.append(_doSwitch_2, "\t");
        Type _type_1 = action.getPeekPattern().getPortToVarMap().get(peekPort).getType();
        List<Integer> _dimensions = ((TypeList) _type_1).getDimensions();
        _builder.append(_dimensions, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("for(int fifo_index = 0 ; fifo_index < ");
        Integer _get = action.getPeekPattern().getNumTokensMap().get(peekPort);
        _builder.append(_get, "\t");
        _builder.append(" ; ++fifo_index) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        String _name_2 = action.getPeekPattern().getPortToVarMap().get(peekPort).getName();
        _builder.append(_name_2, "\t\t");
        _builder.append("[fifo_index] = fifo_");
        String _name_3 = peekPort.getName();
        _builder.append(_name_3, "\t\t");
        _builder.append(".peek(fifo_index);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    {
      EList<Block> _blocks = action.getScheduler().getBlocks();
      for(final Block block : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch_3 = this.doSwitch(block);
        _builder.append(_doSwitch_3, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("private void ");
    String _name_4 = action.getName();
    _builder.append(_name_4);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("//Locals");
    _builder.newLine();
    {
      EList<Var> _locals_1 = action.getBody().getLocals();
      for(final Var local : _locals_1) {
        _builder.append("\t");
        CharSequence _declareVariable_1 = this.declareVariable(local);
        _builder.append(_declareVariable_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("//Input ports");
    _builder.newLine();
    {
      EList<Port> _ports_1 = action.getInputPattern().getPorts();
      for(final Port inPort : _ports_1) {
        _builder.append("\t");
        CharSequence _declareVariable_2 = this.declareVariable(action.getInputPattern().getPortToVarMap().get(inPort));
        _builder.append(_declareVariable_2, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("//Output ports");
    _builder.newLine();
    {
      EList<Port> _ports_2 = action.getOutputPattern().getPorts();
      for(final Port outPort : _ports_2) {
        _builder.append("\t");
        CharSequence _declareVariable_3 = this.declareVariable(action.getOutputPattern().getPortToVarMap().get(outPort));
        _builder.append(_declareVariable_3, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      EList<Port> _ports_3 = action.getInputPattern().getPorts();
      for(final Port inPort_1 : _ports_3) {
        _builder.append("\t");
        _builder.append("for(int fifo_index = 0 ; fifo_index < ");
        Integer _get_1 = action.getInputPattern().getNumTokensMap().get(inPort_1);
        _builder.append(_get_1, "\t");
        _builder.append(" ; ++fifo_index) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        String _name_5 = action.getInputPattern().getPortToVarMap().get(inPort_1).getName();
        _builder.append(_name_5, "\t\t");
        _builder.append("[fifo_index] = fifo_");
        String _name_6 = inPort_1.getName();
        _builder.append(_name_6, "\t\t");
        _builder.append(".read();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    {
      EList<Block> _blocks_1 = action.getBody().getBlocks();
      for(final Block block_1 : _blocks_1) {
        _builder.append("\t");
        CharSequence _doSwitch_4 = this.doSwitch(block_1);
        _builder.append(_doSwitch_4, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _ports_4 = action.getOutputPattern().getPorts();
      for(final Port outPort_1 : _ports_4) {
        _builder.append("\t");
        _builder.append("for(int fifo_index = 0 ; fifo_index < ");
        Integer _get_2 = action.getOutputPattern().getNumTokensMap().get(outPort_1);
        _builder.append(_get_2, "\t");
        _builder.append(" ; ++fifo_index) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("fifo_");
        String _name_7 = outPort_1.getName();
        _builder.append(_name_7, "\t\t");
        _builder.append(".write(");
        String _name_8 = action.getOutputPattern().getPortToVarMap().get(outPort_1).getName();
        _builder.append(_name_8, "\t\t");
        _builder.append("[fifo_index]);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Scheduling / FSM
   */
  public CharSequence printSimpleScheduler() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("public int schedule() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("boolean res;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int i = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("do {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("res = false;");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Action, CharSequence> _function = new Function1<Action, CharSequence>() {
      @Override
      public CharSequence apply(final Action it) {
        return ActorPrinter.this.actionFireingTest(it);
      }
    };
    String _join = IterableExtensions.<Action>join(this.actor.getActions(), " else ", _function);
    _builder.append(_join, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("i += res ? 1 : 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} while(res);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return i;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printFSMScheduler() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("private enum States {");
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
        String _name = state.getName();
        _builder.append(_name, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("private States _FSM_state = States.");
    String _name_1 = this.actor.getFsm().getInitialState().getName();
    _builder.append(_name_1);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("private boolean outside_FSM_scheduler() {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("boolean res = false;");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        final Function1<Action, CharSequence> _function = new Function1<Action, CharSequence>() {
          @Override
          public CharSequence apply(final Action it) {
            return ActorPrinter.this.actionFireingTest(it);
          }
        };
        String _join = IterableExtensions.<Action>join(this.actor.getActionsOutsideFsm(), " else ", _function);
        _builder.append(_join, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("return res;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    {
      EList<State> _states_1 = this.actor.getFsm().getStates();
      for(final State state_1 : _states_1) {
        _builder.append("private boolean stateScheduler_");
        String _name_2 = state_1.getName();
        _builder.append(_name_2);
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("boolean res = false;");
        _builder.newLine();
        {
          EList<Edge> _outgoing = state_1.getOutgoing();
          boolean _hasElements_1 = false;
          for(final Edge edge : _outgoing) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate(" else ", "\t");
            }
            _builder.append("\t");
            CharSequence _schedulingTestState = this.schedulingTestState(((Transition) edge));
            _builder.append(_schedulingTestState, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("return res;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("public int schedule() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int i = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int total;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("do {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("total = i;");
    _builder.newLine();
    {
      boolean _isEmpty_1 = this.actor.getActionsOutsideFsm().isEmpty();
      if (_isEmpty_1) {
        _builder.append("\t\t");
        CharSequence _fsmSwitch = this.fsmSwitch(this.actor.getFsm().getStates());
        _builder.append(_fsmSwitch, "\t\t");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t\t");
        _builder.append("if (outside_FSM_scheduler()) {");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("i++;");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("} else {");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        CharSequence _fsmSwitch_1 = this.fsmSwitch(this.actor.getFsm().getStates());
        _builder.append(_fsmSwitch_1, "\t\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("} while (total < i);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return i;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Print the switch structure of a FSM.
   */
  public CharSequence fsmSwitch(final EList<State> list) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("switch (_FSM_state) {");
    _builder.newLine();
    {
      EList<State> _states = this.actor.getFsm().getStates();
      for(final State state : _states) {
        _builder.append("case ");
        String _name = state.getName();
        _builder.append(_name);
        _builder.append(" :");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("if(stateScheduler_");
        String _name_1 = state.getName();
        _builder.append(_name_1, "\t");
        _builder.append("()) ++i;");
        _builder.newLineIfNotEmpty();
        _builder.append("break;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("default: System.out.println(\"unknown state: %s\\n\" + _FSM_state); break;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Print an if structure which test if an action is fireable, fire
   * it and update current state in FSM.
   */
  public CharSequence schedulingTestState(final Transition transition) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    String _inputSchedulingTest = this.inputSchedulingTest(transition.getAction());
    _builder.append(_inputSchedulingTest);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = transition.getAction().getOutputPattern().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("if (");
        String _outputSchedulingTest = this.outputSchedulingTest(transition.getAction());
        _builder.append(_outputSchedulingTest, "\t");
        _builder.append(") {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        Action _action = transition.getAction();
        _builder.append(_action, "\t\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("_FSM_state = States.");
        String _name = transition.getTarget().getName();
        _builder.append(_name, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("res = true;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      } else {
        _builder.append("\t");
        _builder.append("_FSM_state = States.");
        String _name_1 = transition.getTarget().getName();
        _builder.append(_name_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        Action _action_1 = transition.getAction();
        _builder.append(_action_1, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("res = true;");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Print a if structure which test if an action is fireable and fire it.
   */
  public CharSequence actionFireingTest(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if(");
    String _inputSchedulingTest = this.inputSchedulingTest(action);
    _builder.append(_inputSchedulingTest);
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = action.getOutputPattern().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("if(");
        String _outputSchedulingTest = this.outputSchedulingTest(action);
        _builder.append(_outputSchedulingTest, "\t");
        _builder.append("){");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        String _name = action.getName();
        _builder.append(_name, "\t\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("res = true;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      } else {
        _builder.append("\t");
        String _name_1 = action.getName();
        _builder.append(_name_1, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("res = true;");
        _builder.newLine();
      }
    }
    _builder.append("}");
    return _builder;
  }
  
  /**
   * Print the list of inputs controls (fifo_x.hasTokens(n)) separated by && and
   * followed by action firing test.
   * Only one line printed.
   */
  public String inputSchedulingTest(final Action action) {
    boolean _isEmpty = action.getInputPattern().getPorts().isEmpty();
    if (_isEmpty) {
      StringConcatenation _builder = new StringConcatenation();
      String _name = action.getScheduler().getName();
      _builder.append(_name);
      _builder.append("()");
      return _builder.toString();
    } else {
      EList<Port> _ports = action.getInputPattern().getPorts();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(" ");
      _builder_1.append("&& ");
      String _name_1 = action.getScheduler().getName();
      _builder_1.append(_name_1, " ");
      _builder_1.append("()");
      final Function1<Port, CharSequence> _function = new Function1<Port, CharSequence>() {
        @Override
        public CharSequence apply(final Port it) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("fifo_");
          String _name = it.getName();
          _builder.append(_name);
          _builder.append(".hasTokens(");
          Integer _get = action.getInputPattern().getNumTokensMap().get(it);
          _builder.append(_get);
          _builder.append(")");
          return _builder.toString();
        }
      };
      return IterableExtensions.<Port>join(_ports, "", " && ", _builder_1, _function);
    }
  }
  
  /**
   * Print the list of outputs controls (fifo_x.hasRooms(n)) separated by &&.
   * Only one line printed.
   */
  public String outputSchedulingTest(final Action action) {
    final Function1<Port, CharSequence> _function = new Function1<Port, CharSequence>() {
      @Override
      public CharSequence apply(final Port it) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("fifo_");
        String _name = it.getName();
        _builder.append(_name);
        _builder.append(".hasRoom(");
        Integer _get = action.getOutputPattern().getNumTokensMap().get(it);
        _builder.append(_get);
        _builder.append(")");
        return _builder.toString();
      }
    };
    return IterableExtensions.<Port>join(action.getOutputPattern().getPorts(), " && ", _function);
  }
  
  /**
   * Blocks
   */
  @Override
  public CharSequence caseBlockIf(final BlockIf block) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if(");
    CharSequence _doSwitch = this.doSwitch(block.getCondition());
    _builder.append(_doSwitch);
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    {
      EList<Block> _thenBlocks = block.getThenBlocks();
      for(final Block thenBlock : _thenBlocks) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(thenBlock);
        _builder.append(_doSwitch_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("} ");
    {
      boolean _isElseRequired = block.isElseRequired();
      if (_isElseRequired) {
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
        _builder.append("else {");
        _builder.newLine();
        {
          EList<Block> _elseBlocks = block.getElseBlocks();
          for(final Block elseBlock : _elseBlocks) {
            _builder.append("\t");
            CharSequence _doSwitch_2 = this.doSwitch(elseBlock);
            _builder.append(_doSwitch_2, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseBlockWhile(final BlockWhile blockWhile) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("while(");
    CharSequence _doSwitch = this.doSwitch(blockWhile.getCondition());
    _builder.append(_doSwitch);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    {
      EList<Block> _blocks = blockWhile.getBlocks();
      for(final Block loopContent : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(loopContent);
        _builder.append(_doSwitch_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
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
  
  /**
   * Instructions
   */
  @Override
  public CharSequence caseInstAssign(final InstAssign inst) {
    CharSequence _xifexpression = null;
    boolean _isCastNeeded = this.isCastNeeded(inst.getTarget().getVariable().getType(), inst.getValue().getType());
    if (_isCastNeeded) {
      CharSequence _xblockexpression = null;
      {
        CharSequence _xifexpression_1 = null;
        boolean _isList = inst.getTarget().getVariable().getType().isList();
        if (_isList) {
          Type _type = inst.getTarget().getVariable().getType();
          _xifexpression_1 = this.doSwitch(((TypeList) _type).getInnermostType());
        } else {
          _xifexpression_1 = this.doSwitch(inst.getTarget().getVariable().getType());
        }
        final CharSequence castType = _xifexpression_1;
        StringConcatenation _builder = new StringConcatenation();
        String _name = inst.getTarget().getVariable().getName();
        _builder.append(_name);
        _builder.append(" = (");
        _builder.append(castType);
        _builder.append(") (");
        CharSequence _doSwitch = this.doSwitch(inst.getValue());
        _builder.append(_doSwitch);
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _xblockexpression = _builder;
      }
      _xifexpression = _xblockexpression;
    } else {
      StringConcatenation _builder = new StringConcatenation();
      String _name = inst.getTarget().getVariable().getName();
      _builder.append(_name);
      _builder.append(" = ");
      CharSequence _doSwitch = this.doSwitch(inst.getValue());
      _builder.append(_doSwitch);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _xifexpression = _builder;
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
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isCastNeeded = this.isCastNeeded(store.getTarget().getVariable().getType(), store.getValue().getType());
      if (_isCastNeeded) {
        String _xblockexpression_1 = null;
        {
          CharSequence _xifexpression_1 = null;
          boolean _isList = store.getTarget().getVariable().getType().isList();
          if (_isList) {
            Type _type = store.getTarget().getVariable().getType();
            _xifexpression_1 = this.doSwitch(((TypeList) _type).getInnermostType());
          } else {
            _xifexpression_1 = this.doSwitch(store.getTarget().getVariable().getType());
          }
          final CharSequence castType = _xifexpression_1;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("(");
          _builder.append(castType);
          _builder.append(") (");
          CharSequence _doSwitch = this.doSwitch(store.getValue());
          _builder.append(_doSwitch);
          _builder.append(")");
          _xblockexpression_1 = _builder.toString();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _doSwitch = this.doSwitch(store.getValue());
        _builder.append(_doSwitch);
        _xifexpression = _builder.toString();
      }
      final String sourceExprValue = _xifexpression;
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name = store.getTarget().getVariable().getName();
      _builder_1.append(_name);
      String _printArrayIndexes = this.printArrayIndexes(store.getIndexes());
      _builder_1.append(_printArrayIndexes);
      _builder_1.append(" = ");
      _builder_1.append(sourceExprValue);
      _builder_1.append(";");
      _builder_1.newLineIfNotEmpty();
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseInstCall(final InstCall call) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isNative = call.getProcedure().isNative();
      if (_isNative) {
        _xifexpression = "NativeProcedure.";
      }
      final String native_ = _xifexpression;
      String _xifexpression_1 = null;
      Def _target = call.getTarget();
      boolean _tripleNotEquals = (_target != null);
      if (_tripleNotEquals) {
        String _xblockexpression_1 = null;
        {
          String _xifexpression_2 = null;
          int _sizeInBits = call.getTarget().getVariable().getType().getSizeInBits();
          int _sizeInBits_1 = call.getProcedure().getReturnType().getSizeInBits();
          boolean _lessThan = (_sizeInBits < _sizeInBits_1);
          if (_lessThan) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("(");
            CharSequence _doSwitch = this.doSwitch(call.getTarget().getVariable().getType());
            _builder.append(_doSwitch);
            _builder.append(") ");
            _xifexpression_2 = _builder.toString();
          }
          final String cast = _xifexpression_2;
          StringConcatenation _builder_1 = new StringConcatenation();
          String _name = call.getTarget().getVariable().getName();
          _builder_1.append(_name);
          _builder_1.append(" = ");
          _builder_1.append(cast);
          _xblockexpression_1 = _builder_1.toString();
        }
        _xifexpression_1 = _xblockexpression_1;
      }
      final String target = _xifexpression_1;
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _isPrint = call.isPrint();
        if (_isPrint) {
          _builder.append("System.out.println(");
          {
            EList<Arg> _arguments = call.getArguments();
            boolean _hasElements = false;
            for(final Arg param : _arguments) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate(" + ", "");
              }
              CharSequence _printParameter = this.printParameter(param);
              _builder.append(_printParameter);
            }
          }
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        } else {
          _builder.append(target);
          _builder.append(native_);
          String _name = call.getProcedure().getName();
          _builder.append(_name);
          _builder.append("(");
          String _printParameters = this.printParameters(call);
          _builder.append(_printParameters);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseInstReturn(final InstReturn ret) {
    CharSequence _xifexpression = null;
    Expression _value = ret.getValue();
    boolean _tripleNotEquals = (_value != null);
    if (_tripleNotEquals) {
      CharSequence _xblockexpression = null;
      {
        final Procedure proc = EcoreHelper.<Procedure>getContainerOfType(ret, Procedure.class);
        CharSequence _xifexpression_1 = null;
        Type _returnType = null;
        if (proc!=null) {
          _returnType=proc.getReturnType();
        }
        int _sizeInBits = _returnType.getSizeInBits();
        int _sizeInBits_1 = ret.getValue().getType().getSizeInBits();
        boolean _lessThan = (_sizeInBits < _sizeInBits_1);
        if (_lessThan) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("return (");
          CharSequence _doSwitch = this.doSwitch(proc.getReturnType());
          _builder.append(_doSwitch);
          _builder.append(")(");
          CharSequence _doSwitch_1 = this.doSwitch(ret.getValue());
          _builder.append(_doSwitch_1);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _xifexpression_1 = _builder;
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("return ");
          CharSequence _doSwitch_2 = this.doSwitch(ret.getValue());
          _builder_1.append(_doSwitch_2);
          _builder_1.append(";");
          _builder_1.newLineIfNotEmpty();
          _xifexpression_1 = _builder_1;
        }
        _xblockexpression = _xifexpression_1;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  /**
   * Expressions
   */
  @Override
  public CharSequence caseExprInt(final ExprInt object) {
    String _xblockexpression = null;
    {
      final long longVal = object.getValue().longValue();
      String _xifexpression = null;
      if (((longVal < Integer.MIN_VALUE) || (longVal > Integer.MAX_VALUE))) {
        String _valueOf = String.valueOf(object.getValue());
        _xifexpression = (_valueOf + "L");
      } else {
        _xifexpression = String.valueOf(object.getValue());
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
