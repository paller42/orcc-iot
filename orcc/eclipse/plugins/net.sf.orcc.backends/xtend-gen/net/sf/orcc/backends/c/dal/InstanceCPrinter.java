package net.sf.orcc.backends.c.dal;

import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
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
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrccLogger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Generate and print actor source file for DAL backend.
 * 
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C InstancePrinter
 */
@SuppressWarnings("all")
public class InstanceCPrinter extends CTemplate {
  protected Instance instance;
  
  protected Actor actor;
  
  protected int maxOut;
  
  protected Map<Port, Connection> incomingPortMap;
  
  protected Map<Port, List<Connection>> outgoingPortMap;
  
  protected String entityName;
  
  protected final Pattern inputPattern = DfFactory.eINSTANCE.createPattern();
  
  protected final Map<State, Pattern> transitionPattern = new HashMap<State, Pattern>();
  
  protected Action currentAction;
  
  protected boolean isSDF = false;
  
  protected InstanceCPrinter() {
    super();
    this.instance = null;
  }
  
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("u8");
    return _builder;
  }
  
  protected void setActor(final Actor actor) {
    this.entityName = actor.getName();
    this.actor = actor;
    this.incomingPortMap = actor.getIncomingPortMap();
    this.outgoingPortMap = actor.getOutgoingPortMap();
    this.checkConnectivy();
    this.buildInputPattern();
    this.buildTransitionPattern();
  }
  
  protected CharSequence getFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <stdio.h>");
    _builder.newLine();
    _builder.append("#include <string.h>");
    _builder.newLine();
    _builder.append("#include <stdlib.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"");
    _builder.append(this.entityName);
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
      boolean _not = (!_hasAttribute);
      if (_not) {
        String _name = this.actor.getName();
        String _plus = ("Info: actor " + _name);
        String _plus_1 = (_plus + " inputs are buffered");
        OrccLogger.traceln(_plus_1);
        _builder.newLineIfNotEmpty();
        _builder.append("#define TOKEN_QUANTUM (");
        {
          EList<Port> _inputs = this.actor.getInputs();
          for(final Port port : _inputs) {
            int _numTokensConsumed = port.getNumTokensConsumed();
            _builder.append(_numTokensConsumed);
            _builder.append("+");
          }
        }
        _builder.append("0) ");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("void buffer_input(void *port, void *trg, int cnt, DALProcess *p) {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("DAL_read(port, trg, cnt, p);");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      } else {
        {
          EList<Port> _inputs_1 = this.actor.getInputs();
          for(final Port port_1 : _inputs_1) {
            {
              if ((port_1.hasAttribute("peekPort") && this.actor.hasAttribute("variableInputPattern"))) {
                CharSequence _createPeekPortInit = this.createPeekPortInit(port_1);
                _builder.append(_createPeekPortInit);
                _builder.newLineIfNotEmpty();
                CharSequence _createReadOverload = this.createReadOverload(port_1);
                _builder.append(_createReadOverload);
                _builder.newLineIfNotEmpty();
                CharSequence _createPeekOverload = this.createPeekOverload(port_1);
                _builder.append(_createPeekOverload);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    {
      EList<Port> _outputs = this.actor.getOutputs();
      for(final Port port_2 : _outputs) {
        {
          int _numTokensProduced = port_2.getNumTokensProduced();
          boolean _greaterThan = (_numTokensProduced > 0);
          if (_greaterThan) {
            String _name_1 = this.actor.getName();
            String _plus_2 = ("Info: actor " + _name_1);
            String _plus_3 = (_plus_2 + " output ");
            String _name_2 = port_2.getName();
            String _plus_4 = (_plus_3 + _name_2);
            String _plus_5 = (_plus_4 + " is buffered");
            OrccLogger.traceln(_plus_5);
            _builder.newLineIfNotEmpty();
            _builder.append("#ifdef BLOCKSZ_");
            String _name_3 = this.actor.getName();
            _builder.append(_name_3);
            _builder.append("_");
            String _name_4 = port_2.getName();
            _builder.append(_name_4);
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("#define SZ_");
            String _name_5 = port_2.getName();
            _builder.append(_name_5, "\t");
            _builder.append(" BLOCKSZ_");
            String _name_6 = this.actor.getName();
            _builder.append(_name_6, "\t");
            _builder.append("_");
            String _name_7 = port_2.getName();
            _builder.append(_name_7, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("#else");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("#define SZ_");
            String _name_8 = port_2.getName();
            _builder.append(_name_8, "\t");
            _builder.append(" ");
            Integer _sizeOrDefaultSize = this.sizeOrDefaultSize(this.outgoingPortMap.get(port_2).get(0));
            int _abs = Math.abs(port_2.getNumTokensProduced());
            int _divide = ((_sizeOrDefaultSize).intValue() / _abs);
            int _abs_1 = Math.abs(port_2.getNumTokensProduced());
            int _multiply = (_divide * _abs_1);
            _builder.append(_multiply, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("#endif");
            _builder.newLine();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("void _DAL_write(void *port, void *buffer, int length, int *index, int maxcount, DALProcess *p) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if(index[0] == maxcount) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("DAL_write(port, buffer, length, p);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("index[0] = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void _DAL_flush(void *port, void *buffer, int length, DALProcess *p) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("DAL_write(port, buffer, length, p);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Var> _stateVars = this.actor.getStateVars();
      for(final Var variable : _stateVars) {
        {
          if (((variable.isInitialized() && (!variable.isAssignable())) && (!variable.getType().isList()))) {
            _builder.append("#define ");
            String _name_9 = variable.getName();
            _builder.append(_name_9);
            _builder.append(" ");
            CharSequence _doSwitch = this.doSwitch(variable.getInitialValue());
            _builder.append(_doSwitch);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    {
      EList<Var> _stateVars_1 = this.actor.getStateVars();
      for(final Var variable_1 : _stateVars_1) {
        CharSequence _declareStateVarListInit = this.declareStateVarListInit(variable_1);
        _builder.append(_declareStateVarListInit);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.newLine();
    {
      if ((((this.instance != null) && (!IterableExtensions.isNullOrEmpty(this.instance.getArguments()))) || (!IterableExtensions.isNullOrEmpty(this.actor.getParameters())))) {
        {
          if ((this.instance != null)) {
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
                    String _name_10 = arg.getVariable().getName();
                    _builder.append(_name_10);
                    String _printArrayIndexes = this.printArrayIndexes(arg.getValue().getType().getDimensionsExpr());
                    _builder.append(_printArrayIndexes);
                    _builder.append(" = ");
                    CharSequence _doSwitch_1 = this.doSwitch(arg.getValue());
                    _builder.append(_doSwitch_1);
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  } else {
                    _builder.append("#define ");
                    String _name_11 = arg.getVariable().getName();
                    _builder.append(_name_11);
                    _builder.append(" ");
                    CharSequence _doSwitch_2 = this.doSwitch(arg.getValue());
                    _builder.append(_doSwitch_2);
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          } else {
            {
              EList<Var> _parameters = this.actor.getParameters();
              for(final Var variable_2 : _parameters) {
                CharSequence _declareStateVar = this.declareStateVar(variable_2);
                _builder.append(_declareStateVar);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        _builder.newLine();
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
            String _name_12 = state.getName();
            _builder.append(_name_12, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("};");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      EList<Var> _stateVars_2 = this.actor.getStateVars();
      for(final Var variable_3 : _stateVars_2) {
        CharSequence _declareConstStateVars = this.declareConstStateVars(variable_3);
        _builder.append(_declareConstStateVars);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<Procedure> _procs = this.actor.getProcs();
      for(final Procedure proc : _procs) {
        CharSequence _declareProc = this.declareProc(proc);
        _builder.append(_declareProc);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<Procedure> _procs_1 = this.actor.getProcs();
      for(final Procedure proc_1 : _procs_1) {
        CharSequence _print = this.print(proc_1);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_init(DALProcess *_p) {");
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getInitializes());
      boolean _not_1 = (!_isNullOrEmpty);
      if (_not_1) {
        {
          EList<Action> _initializes = this.actor.getInitializes();
          for(final Action init : _initializes) {
            _builder.append("\t");
            String _printInitializeVars = this.printInitializeVars(init);
            _builder.append(_printInitializeVars, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<Action> _initializes_1 = this.actor.getInitializes();
          for(final Action init_1 : _initializes_1) {
            {
              EList<Var> _variables = init_1.getOutputPattern().getVariables();
              for(final Var variable_4 : _variables) {
                _builder.append("\t");
                CharSequence _doSwitch_3 = this.doSwitch(variable_4.getType());
                _builder.append(_doSwitch_3, "\t");
                _builder.append(" ");
                String _name_13 = variable_4.getName();
                _builder.append(_name_13, "\t");
                _builder.append("_buffer");
                String _printArrayIndexes_1 = this.printArrayIndexes(variable_4.getType().getDimensionsExpr());
                _builder.append(_printArrayIndexes_1, "\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    {
      boolean _hasFsm_1 = this.actor.hasFsm();
      if (_hasFsm_1) {
        _builder.append("\t");
        _builder.append("_p->local->_FSM_state = my_state_");
        String _name_14 = this.actor.getFsm().getInitialState().getName();
        _builder.append(_name_14, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      final Function1<Procedure, Boolean> _function = new Function1<Procedure, Boolean>() {
        @Override
        public Boolean apply(final Procedure it) {
          return Boolean.valueOf(it.isNative());
        }
      };
      boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(IterableExtensions.<Procedure>filter(this.actor.getProcs(), _function));
      boolean _not_2 = (!_isNullOrEmpty_1);
      if (_not_2) {
        _builder.append("\t");
        _builder.append("_p->local->_io = NULL;");
        _builder.newLine();
      }
    }
    {
      EList<Port> _inputs_2 = this.actor.getInputs();
      for(final Port port_3 : _inputs_2) {
        {
          if ((port_3.hasAttribute("peekPort") && this.actor.hasAttribute("variableInputPattern"))) {
            _builder.append("\t");
            _builder.append("_p->local->_fo_filled_");
            String _name_15 = port_3.getName();
            _builder.append(_name_15, "\t");
            _builder.append(" = 0;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _isNullOrEmpty_2 = IterableExtensions.isNullOrEmpty(this.actor.getInitializes());
      boolean _not_3 = (!_isNullOrEmpty_2);
      if (_not_3) {
        {
          EList<Action> _initializes_2 = this.actor.getInitializes();
          for(final Action init_2 : _initializes_2) {
            _builder.append("\t");
            String _printInitializeBlocks = this.printInitializeBlocks(init_2);
            _builder.append(_printInitializeBlocks, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      EList<Var> _stateVars_3 = this.actor.getStateVars();
      for(final Var variable_5 : _stateVars_3) {
        _builder.append("\t");
        CharSequence _declareStateVarInInit = this.declareStateVarInInit(variable_5);
        _builder.append(_declareStateVarInInit, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isNullOrEmpty_3 = IterableExtensions.isNullOrEmpty(this.actor.getInitializes());
      boolean _not_4 = (!_isNullOrEmpty_3);
      if (_not_4) {
        {
          EList<Action> _initializes_3 = this.actor.getInitializes();
          for(final Action init_3 : _initializes_3) {
            {
              EList<Port> _ports = init_3.getOutputPattern().getPorts();
              for(final Port port_4 : _ports) {
                {
                  if (this.isSDF) {
                    _builder.append("\t");
                    _builder.append("DAL_write((void*)PORT_");
                    String _name_16 = port_4.getName();
                    _builder.append(_name_16, "\t");
                    _builder.append(", ");
                    String _name_17 = port_4.getName();
                    _builder.append(_name_17, "\t");
                    _builder.append("_buffer, sizeof(");
                    CharSequence _doSwitch_4 = this.doSwitch(port_4.getType());
                    _builder.append(_doSwitch_4, "\t");
                    _builder.append(")*");
                    int _numTokens = init_3.getOutputPattern().getNumTokens(port_4);
                    _builder.append(_numTokens, "\t");
                    _builder.append(", _p);");
                    _builder.newLineIfNotEmpty();
                  } else {
                    {
                      int _numTokensProduced_1 = port_4.getNumTokensProduced();
                      boolean _lessThan = (_numTokensProduced_1 < 0);
                      if (_lessThan) {
                        _builder.append("\t");
                        _builder.append("DAL_write((void*)PORT_");
                        String _name_18 = port_4.getName();
                        _builder.append(_name_18, "\t");
                        _builder.append(", ");
                        String _name_19 = port_4.getName();
                        _builder.append(_name_19, "\t");
                        _builder.append("_buffer, sizeof(");
                        CharSequence _doSwitch_5 = this.doSwitch(port_4.getType());
                        _builder.append(_doSwitch_5, "\t");
                        _builder.append(")*");
                        int _numTokens_1 = init_3.getOutputPattern().getNumTokens(port_4);
                        _builder.append(_numTokens_1, "\t");
                        _builder.append(", _p);");
                        _builder.newLineIfNotEmpty();
                      } else {
                        _builder.append("\t");
                        String _name_20 = port_4.getName();
                        _builder.append(_name_20, "\t");
                        _builder.append("_index[0] += ");
                        int _numTokens_2 = init_3.getOutputPattern().getNumTokens(port_4);
                        _builder.append(_numTokens_2, "\t");
                        _builder.append(";");
                        _builder.newLineIfNotEmpty();
                        _builder.append("\t");
                        _builder.append("_DAL_write((void*)PORT_");
                        String _name_21 = port_4.getName();
                        _builder.append(_name_21, "\t");
                        _builder.append(", ");
                        String _name_22 = port_4.getName();
                        _builder.append(_name_22, "\t");
                        _builder.append("_buffer, SZ_");
                        String _name_23 = port_4.getName();
                        _builder.append(_name_23, "\t");
                        _builder.append("*sizeof(");
                        CharSequence _doSwitch_6 = this.doSwitch(port_4.getType());
                        _builder.append(_doSwitch_6, "\t");
                        _builder.append("), ");
                        String _name_24 = port_4.getName();
                        _builder.append(_name_24, "\t");
                        _builder.append("_index, SZ_");
                        String _name_25 = port_4.getName();
                        _builder.append(_name_25, "\t");
                        _builder.append(", _p);");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("return 0;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_finish(DALProcess *_p) {");
    _builder.newLineIfNotEmpty();
    {
      final Function1<Procedure, Boolean> _function_1 = new Function1<Procedure, Boolean>() {
        @Override
        public Boolean apply(final Procedure it) {
          return Boolean.valueOf(it.isNative());
        }
      };
      boolean _isNullOrEmpty_4 = IterableExtensions.isNullOrEmpty(IterableExtensions.<Procedure>filter(this.actor.getProcs(), _function_1));
      boolean _not_5 = (!_isNullOrEmpty_4);
      if (_not_5) {
        _builder.append("\t");
        _builder.append("free(_p->local->_io);");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("return 0;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Action> _actions = this.actor.getActions();
      for(final Action action : _actions) {
        String _print_1 = this.print(action);
        _builder.append(_print_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.newLine();
    CharSequence _actorScheduler = this.actorScheduler();
    _builder.append(_actorScheduler);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence actorScheduler() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        CharSequence _printFsm = this.printFsm();
        _builder.append(_printFsm);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("int ");
        _builder.append(this.entityName);
        _builder.append("_fire(DALProcess *_p) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("int iter = 0;");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        {
          EList<Port> _outputs = this.actor.getOutputs();
          for(final Port port : _outputs) {
            {
              int _numTokensProduced = port.getNumTokensProduced();
              boolean _greaterThan = (_numTokensProduced > 0);
              if (_greaterThan) {
                CharSequence _doSwitch = this.doSwitch(port.getType());
                _builder.append(_doSwitch);
                _builder.append(" ");
                String _name = port.getName();
                _builder.append(_name);
                _builder.append("_buffer[SZ_");
                String _name_1 = port.getName();
                _builder.append(_name_1);
                _builder.append("];");
                _builder.newLineIfNotEmpty();
                _builder.append("int ");
                String _name_2 = port.getName();
                _builder.append(_name_2);
                _builder.append("_index = 0;");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        {
          EList<Port> _inputs = this.actor.getInputs();
          for(final Port port_1 : _inputs) {
            {
              if ((port_1.hasAttribute("peekPort") && this.actor.hasAttribute("variableInputPattern"))) {
                _builder.append("_DAL_");
                String _name_3 = port_1.getName();
                _builder.append(_name_3);
                _builder.append("_initial((void*)PORT_");
                String _name_4 = port_1.getName();
                _builder.append(_name_4);
                _builder.append(", _p);");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        {
          boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
          if (_hasAttribute) {
            _builder.append("for(iter = 0; iter < ITERATIONS; iter++)");
            _builder.newLine();
          } else {
            {
              EList<Port> _inputs_1 = this.actor.getInputs();
              for(final Port port_2 : _inputs_1) {
                _builder.append("\t\t\t");
                CharSequence _doSwitch_1 = this.doSwitch(port_2.getType());
                _builder.append(_doSwitch_1, "\t\t\t");
                _builder.append(" ");
                String _name_5 = port_2.getName();
                _builder.append(_name_5, "\t\t\t");
                _builder.append("_buffer[");
                int _numTokensConsumed = port_2.getNumTokensConsumed();
                _builder.append(_numTokensConsumed, "\t\t\t");
                _builder.append("];");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t\t");
                _builder.append("int ");
                String _name_6 = port_2.getName();
                _builder.append(_name_6, "\t\t\t");
                _builder.append("_index = 0;");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.newLine();
            {
              EList<Port> _inputs_2 = this.actor.getInputs();
              for(final Port port_3 : _inputs_2) {
                _builder.append("\t\t\t");
                _builder.append("buffer_input((void*)PORT_");
                String _name_7 = port_3.getName();
                _builder.append(_name_7, "\t\t\t");
                _builder.append(", ");
                String _name_8 = port_3.getName();
                _builder.append(_name_8, "\t\t\t");
                _builder.append("_buffer, sizeof(");
                CharSequence _doSwitch_2 = this.doSwitch(port_3.getType());
                _builder.append(_doSwitch_2, "\t\t\t");
                _builder.append(")*");
                int _numTokensConsumed_1 = port_3.getNumTokensConsumed();
                _builder.append(_numTokensConsumed_1, "\t\t\t");
                _builder.append(", _p);");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("\t\t\t");
            _builder.append("while(TOKEN_QUANTUM - iter >= TOKEN_QUANTUM)");
            _builder.newLine();
          }
        }
        _builder.append("\t");
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t\t");
        CharSequence _printActionLoop = this.printActionLoop(this.actor.getActionsOutsideFsm());
        _builder.append(_printActionLoop, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("finished:");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("iter = iter;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("flush:");
        _builder.newLine();
        {
          EList<Port> _outputs_1 = this.actor.getOutputs();
          for(final Port port_4 : _outputs_1) {
            {
              int _numTokensProduced_1 = port_4.getNumTokensProduced();
              boolean _greaterThan_1 = (_numTokensProduced_1 > 0);
              if (_greaterThan_1) {
                _builder.append("_DAL_flush((void*)PORT_");
                String _name_9 = port_4.getName();
                _builder.append(_name_9);
                _builder.append(", ");
                String _name_10 = port_4.getName();
                _builder.append(_name_10);
                _builder.append("_buffer, ");
                String _name_11 = port_4.getName();
                _builder.append(_name_11);
                _builder.append("_index*sizeof(");
                CharSequence _doSwitch_3 = this.doSwitch(port_4.getType());
                _builder.append(_doSwitch_3);
                _builder.append("), _p);");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        _builder.append("\t");
        _builder.append("return 0;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence printFsm() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
          if (_hasAttribute) {
            CharSequence _inline = this.getInline();
            _builder.append(_inline);
            _builder.append("void ");
            _builder.append(this.entityName);
            _builder.append("_outside_FSM_scheduler(DALProcess *_p");
            {
              EList<Port> _outputs = this.actor.getOutputs();
              for(final Port port : _outputs) {
                {
                  int _numTokensProduced = port.getNumTokensProduced();
                  boolean _greaterThan = (_numTokensProduced > 0);
                  if (_greaterThan) {
                    _builder.append(", ");
                    CharSequence _doSwitch = this.doSwitch(port.getType());
                    _builder.append(_doSwitch);
                    _builder.append(" *");
                    String _name = port.getName();
                    _builder.append(_name);
                    _builder.append("_buffer, int *");
                    String _name_1 = port.getName();
                    _builder.append(_name_1);
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(")");
            _builder.newLineIfNotEmpty();
          } else {
            CharSequence _inline_1 = this.getInline();
            _builder.append(_inline_1);
            _builder.append("void ");
            _builder.append(this.entityName);
            _builder.append("_outside_FSM_scheduler(DALProcess *_p");
            {
              EList<Port> _inputs = this.actor.getInputs();
              for(final Port port_1 : _inputs) {
                _builder.append(", ");
                CharSequence _doSwitch_1 = this.doSwitch(port_1.getType());
                _builder.append(_doSwitch_1);
                _builder.append(" *");
                String _name_2 = port_1.getName();
                _builder.append(_name_2);
                _builder.append("_buffer, int *");
                String _name_3 = port_1.getName();
                _builder.append(_name_3);
                _builder.append("_index");
              }
            }
            {
              EList<Port> _outputs_1 = this.actor.getOutputs();
              for(final Port port_2 : _outputs_1) {
                {
                  int _numTokensProduced_1 = port_2.getNumTokensProduced();
                  boolean _greaterThan_1 = (_numTokensProduced_1 > 0);
                  if (_greaterThan_1) {
                    _builder.append(", ");
                    CharSequence _doSwitch_2 = this.doSwitch(port_2.getType());
                    _builder.append(_doSwitch_2);
                    _builder.append(" *");
                    String _name_4 = port_2.getName();
                    _builder.append(_name_4);
                    _builder.append("_buffer, int *");
                    String _name_5 = port_2.getName();
                    _builder.append(_name_5);
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(", int *iter)");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printOutsideFSMActionLoop = this.printOutsideFSMActionLoop(this.actor.getActionsOutsideFsm());
        _builder.append(_printOutsideFSMActionLoop, "\t");
        _builder.newLineIfNotEmpty();
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
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_fire(DALProcess *_p) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("int iter = 0;");
    _builder.newLine();
    {
      EList<Port> _outputs_2 = this.actor.getOutputs();
      for(final Port port_3 : _outputs_2) {
        {
          int _numTokensProduced_2 = port_3.getNumTokensProduced();
          boolean _greaterThan_2 = (_numTokensProduced_2 > 0);
          if (_greaterThan_2) {
            _builder.append("\t");
            CharSequence _doSwitch_3 = this.doSwitch(port_3.getType());
            _builder.append(_doSwitch_3, "\t");
            _builder.append(" ");
            String _name_6 = port_3.getName();
            _builder.append(_name_6, "\t");
            _builder.append("_buffer[SZ_");
            String _name_7 = port_3.getName();
            _builder.append(_name_7, "\t");
            _builder.append("];");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("int ");
            String _name_8 = port_3.getName();
            _builder.append(_name_8, "\t");
            _builder.append("_index = 0;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      EList<Port> _inputs_1 = this.actor.getInputs();
      for(final Port port_4 : _inputs_1) {
        {
          if ((port_4.hasAttribute("peekPort") && this.actor.hasAttribute("variableInputPattern"))) {
            _builder.append("\t");
            _builder.append("_DAL_");
            String _name_9 = port_4.getName();
            _builder.append(_name_9, "\t");
            _builder.append("_initial((void*)PORT_");
            String _name_10 = port_4.getName();
            _builder.append(_name_10, "\t");
            _builder.append(", _p);");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _hasAttribute_1 = this.actor.hasAttribute("variableInputPattern");
      if (_hasAttribute_1) {
        _builder.append("\t");
        _builder.append("for(iter = 0; iter < ITERATIONS; iter++)");
        _builder.newLine();
      } else {
        {
          EList<Port> _inputs_2 = this.actor.getInputs();
          for(final Port port_5 : _inputs_2) {
            _builder.append("\t");
            CharSequence _doSwitch_4 = this.doSwitch(port_5.getType());
            _builder.append(_doSwitch_4, "\t");
            _builder.append(" ");
            String _name_11 = port_5.getName();
            _builder.append(_name_11, "\t");
            _builder.append("_buffer[");
            int _numTokensConsumed = port_5.getNumTokensConsumed();
            _builder.append(_numTokensConsumed, "\t");
            _builder.append("];");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("int ");
            String _name_12 = port_5.getName();
            _builder.append(_name_12, "\t");
            _builder.append("_index = 0;");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        {
          EList<Port> _inputs_3 = this.actor.getInputs();
          for(final Port port_6 : _inputs_3) {
            _builder.append("\t");
            _builder.append("buffer_input((void*)PORT_");
            String _name_13 = port_6.getName();
            _builder.append(_name_13, "\t");
            _builder.append(", ");
            String _name_14 = port_6.getName();
            _builder.append(_name_14, "\t");
            _builder.append("_buffer, sizeof(");
            CharSequence _doSwitch_5 = this.doSwitch(port_6.getType());
            _builder.append(_doSwitch_5, "\t");
            _builder.append(")*");
            int _numTokensConsumed_1 = port_6.getNumTokensConsumed();
            _builder.append(_numTokensConsumed_1, "\t");
            _builder.append(", _p);");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("while(TOKEN_QUANTUM - iter >= TOKEN_QUANTUM)");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("{");
    _builder.newLine();
    {
      boolean _isEmpty_1 = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        {
          boolean _hasAttribute_2 = this.actor.hasAttribute("variableInputPattern");
          if (_hasAttribute_2) {
            _builder.append("\t");
            _builder.append(this.entityName, "\t");
            _builder.append("_outside_FSM_scheduler(_p");
            {
              EList<Port> _outputs_3 = this.actor.getOutputs();
              for(final Port port_7 : _outputs_3) {
                {
                  int _numTokensProduced_3 = port_7.getNumTokensProduced();
                  boolean _greaterThan_3 = (_numTokensProduced_3 > 0);
                  if (_greaterThan_3) {
                    _builder.append(", ");
                    String _name_15 = port_7.getName();
                    _builder.append(_name_15, "\t");
                    _builder.append("_buffer, &");
                    String _name_16 = port_7.getName();
                    _builder.append(_name_16, "\t");
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t");
            _builder.append(this.entityName, "\t");
            _builder.append("_outside_FSM_scheduler(_p");
            {
              EList<Port> _inputs_4 = this.actor.getInputs();
              for(final Port port_8 : _inputs_4) {
                _builder.append(", ");
                String _name_17 = port_8.getName();
                _builder.append(_name_17, "\t");
                _builder.append("_buffer, &");
                String _name_18 = port_8.getName();
                _builder.append(_name_18, "\t");
                _builder.append("_index");
              }
            }
            {
              EList<Port> _outputs_4 = this.actor.getOutputs();
              for(final Port port_9 : _outputs_4) {
                {
                  int _numTokensProduced_4 = port_9.getNumTokensProduced();
                  boolean _greaterThan_4 = (_numTokensProduced_4 > 0);
                  if (_greaterThan_4) {
                    _builder.append(", ");
                    String _name_19 = port_9.getName();
                    _builder.append(_name_19, "\t");
                    _builder.append("_buffer, &");
                    String _name_20 = port_9.getName();
                    _builder.append(_name_20, "\t");
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(", &iter);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("if(TOKEN_QUANTUM - iter < TOKEN_QUANTUM) goto flush;");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t\t\t");
    _builder.append("switch (_p->local->_FSM_state) {");
    _builder.newLine();
    {
      EList<State> _states = this.actor.getFsm().getStates();
      for(final State state : _states) {
        _builder.append("\t");
        _builder.append("case my_state_");
        String _name_21 = state.getName();
        _builder.append(_name_21, "\t");
        _builder.append(":");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("goto l_");
        String _name_22 = state.getName();
        _builder.append(_name_22, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t\t");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("printf(\"unknown state in ");
    _builder.append(this.entityName, "\t\t\t\t");
    _builder.append(".c\\n\");");
    _builder.newLineIfNotEmpty();
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
    _builder.append("\t\t");
    _builder.append("iter = iter;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("flush:");
    _builder.newLine();
    {
      EList<Port> _outputs_5 = this.actor.getOutputs();
      for(final Port port_10 : _outputs_5) {
        {
          int _numTokensProduced_5 = port_10.getNumTokensProduced();
          boolean _greaterThan_5 = (_numTokensProduced_5 > 0);
          if (_greaterThan_5) {
            _builder.append("\t");
            _builder.append("_DAL_flush((void*)PORT_");
            String _name_23 = port_10.getName();
            _builder.append(_name_23, "\t");
            _builder.append(", ");
            String _name_24 = port_10.getName();
            _builder.append(_name_24, "\t");
            _builder.append("_buffer, ");
            String _name_25 = port_10.getName();
            _builder.append(_name_25, "\t");
            _builder.append("_index*sizeof(");
            CharSequence _doSwitch_6 = this.doSwitch(port_10.getType());
            _builder.append(_doSwitch_6, "\t");
            _builder.append("), _p);");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("return 0;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printStateLabel(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("l_");
    String _name = state.getName();
    _builder.append(_name);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = state.getOutgoing().isEmpty();
      if (_isEmpty) {
        _builder.append("\t");
        _builder.append("printf(\"Stuck in state \\\"");
        String _name_1 = state.getName();
        _builder.append(_name_1, "\t");
        _builder.append("\\\" in ");
        _builder.append(this.entityName, "\t");
        _builder.append("\\n\");");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        CharSequence _printStateTransitions = this.printStateTransitions(state);
        _builder.append(_printStateTransitions, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence printStateTransitions(final State state) {
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
      for(final Transition trans : _map) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" else ", "");
        }
        {
          boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
          if (_hasAttribute) {
            _builder.append("if (");
            String _name = trans.getAction().getScheduler().getName();
            _builder.append(_name);
            _builder.append("(_p)) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            String _name_1 = trans.getAction().getBody().getName();
            _builder.append(_name_1, "\t");
            _builder.append("(_p");
            {
              EList<Port> _outputs = this.actor.getOutputs();
              for(final Port port : _outputs) {
                {
                  int _numTokensProduced = port.getNumTokensProduced();
                  boolean _greaterThan = (_numTokensProduced > 0);
                  if (_greaterThan) {
                    _builder.append(", ");
                    String _name_2 = port.getName();
                    _builder.append(_name_2, "\t");
                    _builder.append("_buffer, &");
                    String _name_3 = port.getName();
                    _builder.append(_name_3, "\t");
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("if (");
            String _name_4 = trans.getAction().getScheduler().getName();
            _builder.append(_name_4);
            _builder.append("(_p");
            {
              EList<Port> _inputs = this.actor.getInputs();
              for(final Port port_1 : _inputs) {
                _builder.append(", ");
                String _name_5 = port_1.getName();
                _builder.append(_name_5);
                _builder.append("_buffer, &");
                String _name_6 = port_1.getName();
                _builder.append(_name_6);
                _builder.append("_index");
              }
            }
            _builder.append(", &iter)) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            String _name_7 = trans.getAction().getBody().getName();
            _builder.append(_name_7, "\t");
            _builder.append("(_p");
            {
              EList<Port> _inputs_1 = this.actor.getInputs();
              for(final Port port_2 : _inputs_1) {
                _builder.append(", ");
                String _name_8 = port_2.getName();
                _builder.append(_name_8, "\t");
                _builder.append("_buffer, &");
                String _name_9 = port_2.getName();
                _builder.append(_name_9, "\t");
                _builder.append("_index");
              }
            }
            {
              EList<Port> _outputs_1 = this.actor.getOutputs();
              for(final Port port_3 : _outputs_1) {
                {
                  int _numTokensProduced_1 = port_3.getNumTokensProduced();
                  boolean _greaterThan_1 = (_numTokensProduced_1 > 0);
                  if (_greaterThan_1) {
                    _builder.append(", ");
                    String _name_10 = port_3.getName();
                    _builder.append(_name_10, "\t");
                    _builder.append("_buffer, &");
                    String _name_11 = port_3.getName();
                    _builder.append(_name_11, "\t");
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(", &iter);");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          boolean _equals = trans.getTarget().getName().equals(state.getName());
          boolean _not = (!_equals);
          if (_not) {
            _builder.append("\t");
            _builder.append("_p->local->_FSM_state = my_state_");
            String _name_12 = trans.getTarget().getName();
            _builder.append(_name_12, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
      }
    }
    _builder.newLineIfNotEmpty();
    CharSequence _printTransitionPattern = this.printTransitionPattern(this.transitionPattern.get(state));
    _builder.append(_printTransitionPattern);
    _builder.newLineIfNotEmpty();
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printTransitionPattern(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence initializeFunction() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Action> _initializes = this.actor.getInitializes();
      for(final Action init : _initializes) {
        String _print = this.print(init);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    CharSequence _inline = this.getInline();
    _builder.append(_inline);
    _builder.append("void ");
    _builder.append(this.entityName);
    _builder.append("_initialize(DALProcess *_p) {");
    _builder.newLineIfNotEmpty();
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        _builder.append("\t");
        _builder.append("/* Set initial state to current FSM state */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("p->local->_FSM_state = my_state_");
        String _name = this.actor.getFsm().getInitialState().getName();
        _builder.append(_name, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getInitializes());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("\t");
        CharSequence _printActions = this.printActions(this.actor.getInitializes());
        _builder.append(_printActions, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
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
    return _builder;
  }
  
  private void checkConnectivy() {
    final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
      @Override
      public Boolean apply(final Port it) {
        boolean _isInputConneted = InstanceCPrinter.this.isInputConneted(it);
        return Boolean.valueOf((!_isInputConneted));
      }
    };
    Iterable<Port> _filter = IterableExtensions.<Port>filter(this.actor.getInputs(), _function);
    for (final Port port : _filter) {
      String _name = port.getName();
      String _plus = ((("[" + this.entityName) + "] Input port ") + _name);
      String _plus_1 = (_plus + " not connected.");
      OrccLogger.noticeln(_plus_1);
    }
    final Function1<Port, Boolean> _function_1 = new Function1<Port, Boolean>() {
      @Override
      public Boolean apply(final Port it) {
        boolean _isOutputConnected = InstanceCPrinter.this.isOutputConnected(it);
        return Boolean.valueOf((!_isOutputConnected));
      }
    };
    Iterable<Port> _filter_1 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_1);
    for (final Port port_1 : _filter_1) {
      String _name_1 = port_1.getName();
      String _plus_2 = ((("[" + this.entityName) + "] Output port ") + _name_1);
      String _plus_3 = (_plus_2 + " not connected.");
      OrccLogger.noticeln(_plus_3);
    }
  }
  
  private CharSequence printActionLoop(final List<Action> actions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("while (1) {");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _printActions = this.printActions(actions);
    _builder.append(_printActions, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printOutsideFSMActionLoop(final List<Action> actions) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
      boolean _not = (!_hasAttribute);
      if (_not) {
        _builder.append("while (TOKEN_QUANTUM - iter[0] >= TOKEN_QUANTUM) {");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printOutsideFSMActions = this.printOutsideFSMActions(actions);
        _builder.append(_printOutsideFSMActions, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
      } else {
        _builder.append("while (1) {");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printOutsideFSMActions_1 = this.printOutsideFSMActions(actions);
        _builder.append(_printOutsideFSMActions_1, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence printActions(final Iterable<Action> actions) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final Action action : actions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" else ", "");
        }
        {
          boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
          if (_hasAttribute) {
            _builder.append("if (");
            String _name = action.getScheduler().getName();
            _builder.append(_name);
            _builder.append("(_p)) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            String _name_1 = action.getBody().getName();
            _builder.append(_name_1, "\t");
            _builder.append("(_p");
            {
              EList<Port> _outputs = this.actor.getOutputs();
              for(final Port port : _outputs) {
                {
                  int _numTokensProduced = port.getNumTokensProduced();
                  boolean _greaterThan = (_numTokensProduced > 0);
                  if (_greaterThan) {
                    _builder.append(", ");
                    String _name_2 = port.getName();
                    _builder.append(_name_2, "\t");
                    _builder.append("_buffer, &");
                    String _name_3 = port.getName();
                    _builder.append(_name_3, "\t");
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("if (");
            String _name_4 = action.getScheduler().getName();
            _builder.append(_name_4);
            _builder.append("(_p");
            {
              EList<Port> _inputs = this.actor.getInputs();
              for(final Port port_1 : _inputs) {
                _builder.append(", ");
                String _name_5 = port_1.getName();
                _builder.append(_name_5);
                _builder.append("_buffer, &");
                String _name_6 = port_1.getName();
                _builder.append(_name_6);
                _builder.append("_index");
              }
            }
            _builder.append(", &iter)) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            String _name_7 = action.getBody().getName();
            _builder.append(_name_7, "\t");
            _builder.append("(_p");
            {
              EList<Port> _inputs_1 = this.actor.getInputs();
              for(final Port port_2 : _inputs_1) {
                _builder.append(", ");
                String _name_8 = port_2.getName();
                _builder.append(_name_8, "\t");
                _builder.append("_buffer, &");
                String _name_9 = port_2.getName();
                _builder.append(_name_9, "\t");
                _builder.append("_index");
              }
            }
            {
              EList<Port> _outputs_1 = this.actor.getOutputs();
              for(final Port port_3 : _outputs_1) {
                {
                  int _numTokensProduced_1 = port_3.getNumTokensProduced();
                  boolean _greaterThan_1 = (_numTokensProduced_1 > 0);
                  if (_greaterThan_1) {
                    _builder.append(", ");
                    String _name_10 = port_3.getName();
                    _builder.append(_name_10, "\t");
                    _builder.append("_buffer, &");
                    String _name_11 = port_3.getName();
                    _builder.append(_name_11, "\t");
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(", &iter);");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("goto finished;");
        _builder.newLine();
        _builder.append("}");
      }
    }
    _builder.append(" else {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _printTransitionPattern = this.printTransitionPattern(this.inputPattern);
    _builder.append(_printTransitionPattern, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printOutsideFSMActions(final Iterable<Action> actions) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final Action action : actions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" else ", "");
        }
        {
          boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
          if (_hasAttribute) {
            _builder.append("if (");
            String _name = action.getScheduler().getName();
            _builder.append(_name);
            _builder.append("(_p)) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            String _name_1 = action.getBody().getName();
            _builder.append(_name_1, "\t");
            _builder.append("(_p");
            {
              EList<Port> _outputs = this.actor.getOutputs();
              for(final Port port : _outputs) {
                {
                  int _numTokensProduced = port.getNumTokensProduced();
                  boolean _greaterThan = (_numTokensProduced > 0);
                  if (_greaterThan) {
                    _builder.append(", ");
                    String _name_2 = port.getName();
                    _builder.append(_name_2, "\t");
                    _builder.append("_buffer, ");
                    String _name_3 = port.getName();
                    _builder.append(_name_3, "\t");
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("if (");
            String _name_4 = action.getScheduler().getName();
            _builder.append(_name_4);
            _builder.append("(_p");
            {
              EList<Port> _inputs = this.actor.getInputs();
              for(final Port port_1 : _inputs) {
                _builder.append(", ");
                String _name_5 = port_1.getName();
                _builder.append(_name_5);
                _builder.append("_buffer, ");
                String _name_6 = port_1.getName();
                _builder.append(_name_6);
                _builder.append("_index");
              }
            }
            _builder.append(", iter)) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            String _name_7 = action.getBody().getName();
            _builder.append(_name_7, "\t");
            _builder.append("(_p");
            {
              EList<Port> _inputs_1 = this.actor.getInputs();
              for(final Port port_2 : _inputs_1) {
                _builder.append(", ");
                String _name_8 = port_2.getName();
                _builder.append(_name_8, "\t");
                _builder.append("_buffer, ");
                String _name_9 = port_2.getName();
                _builder.append(_name_9, "\t");
                _builder.append("_index");
              }
            }
            {
              EList<Port> _outputs_1 = this.actor.getOutputs();
              for(final Port port_3 : _outputs_1) {
                {
                  int _numTokensProduced_1 = port_3.getNumTokensProduced();
                  boolean _greaterThan_1 = (_numTokensProduced_1 > 0);
                  if (_greaterThan_1) {
                    _builder.append(", ");
                    String _name_10 = port_3.getName();
                    _builder.append(_name_10, "\t");
                    _builder.append("_buffer, ");
                    String _name_11 = port_3.getName();
                    _builder.append(_name_11, "\t");
                    _builder.append("_index");
                  }
                }
              }
            }
            _builder.append(", iter);");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("goto finished;");
        _builder.newLine();
        _builder.append("}");
      }
    }
    _builder.append(" else {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _printTransitionPattern = this.printTransitionPattern(this.inputPattern);
    _builder.append(_printTransitionPattern, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private String print(final Action action) {
    this.currentAction = action;
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    {
      boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
      if (_hasAttribute) {
        _builder.append("int ");
        String _name = action.getBody().getName();
        _builder.append(_name);
        _builder.append("(DALProcess *_p");
        {
          EList<Port> _outputs = this.actor.getOutputs();
          for(final Port port : _outputs) {
            {
              int _numTokensProduced = port.getNumTokensProduced();
              boolean _greaterThan = (_numTokensProduced > 0);
              if (_greaterThan) {
                _builder.append(", ");
                CharSequence _doSwitch = this.doSwitch(port.getType());
                _builder.append(_doSwitch);
                _builder.append(" *");
                String _name_1 = port.getName();
                _builder.append(_name_1);
                _builder.append("_buffer, int *");
                String _name_2 = port.getName();
                _builder.append(_name_2);
                _builder.append("_index");
              }
            }
          }
        }
        _builder.append(") {");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("int ");
        String _name_3 = action.getBody().getName();
        _builder.append(_name_3);
        _builder.append("(DALProcess *_p");
        {
          EList<Port> _inputs = this.actor.getInputs();
          for(final Port port_1 : _inputs) {
            _builder.append(", ");
            CharSequence _doSwitch_1 = this.doSwitch(port_1.getType());
            _builder.append(_doSwitch_1);
            _builder.append(" *");
            String _name_4 = port_1.getName();
            _builder.append(_name_4);
            _builder.append("_buffer, int *");
            String _name_5 = port_1.getName();
            _builder.append(_name_5);
            _builder.append("_index");
          }
        }
        {
          EList<Port> _outputs_1 = this.actor.getOutputs();
          for(final Port port_2 : _outputs_1) {
            {
              int _numTokensProduced_1 = port_2.getNumTokensProduced();
              boolean _greaterThan_1 = (_numTokensProduced_1 > 0);
              if (_greaterThan_1) {
                _builder.append(", ");
                CharSequence _doSwitch_2 = this.doSwitch(port_2.getType());
                _builder.append(_doSwitch_2);
                _builder.append(" *");
                String _name_6 = port_2.getName();
                _builder.append(_name_6);
                _builder.append("_buffer, int *");
                String _name_7 = port_2.getName();
                _builder.append(_name_7);
                _builder.append("_index");
              }
            }
          }
        }
        _builder.append(", int *iter) {");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _ports = action.getInputPattern().getPorts();
      for(final Port port_3 : _ports) {
        {
          if (this.isSDF) {
            _builder.append("\t");
            CharSequence _doSwitch_3 = this.doSwitch(port_3.getType());
            _builder.append(_doSwitch_3, "\t");
            _builder.append(" ");
            String _name_8 = port_3.getName();
            _builder.append(_name_8, "\t");
            _builder.append("_buffer[");
            int _numTokens = action.getInputPattern().getNumTokens(port_3);
            _builder.append(_numTokens, "\t");
            _builder.append("];");
            _builder.newLineIfNotEmpty();
          } else {
            {
              if (((!port_3.hasAttribute("peekPort")) && this.actor.hasAttribute("variableInputPattern"))) {
                _builder.append("\t");
                CharSequence _doSwitch_4 = this.doSwitch(port_3.getType());
                _builder.append(_doSwitch_4, "\t");
                _builder.append(" ");
                String _name_9 = port_3.getName();
                _builder.append(_name_9, "\t");
                _builder.append("_buffer[");
                int _numTokens_1 = action.getInputPattern().getNumTokens(port_3);
                _builder.append(_numTokens_1, "\t");
                _builder.append("];");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    {
      EList<Port> _ports_1 = action.getOutputPattern().getPorts();
      for(final Port port_4 : _ports_1) {
        {
          if (this.isSDF) {
            _builder.append("\t");
            CharSequence _doSwitch_5 = this.doSwitch(port_4.getType());
            _builder.append(_doSwitch_5, "\t");
            _builder.append(" ");
            String _name_10 = port_4.getName();
            _builder.append(_name_10, "\t");
            _builder.append("_buffer[");
            int _numTokens_2 = action.getOutputPattern().getNumTokens(port_4);
            _builder.append(_numTokens_2, "\t");
            _builder.append("];");
            _builder.newLineIfNotEmpty();
          } else {
            {
              int _numTokensProduced_2 = port_4.getNumTokensProduced();
              boolean _lessThan = (_numTokensProduced_2 < 0);
              if (_lessThan) {
                _builder.append("\t");
                CharSequence _doSwitch_6 = this.doSwitch(port_4.getType());
                _builder.append(_doSwitch_6, "\t");
                _builder.append(" ");
                String _name_11 = port_4.getName();
                _builder.append(_name_11, "\t");
                _builder.append("_buffer[");
                int _numTokens_3 = action.getOutputPattern().getNumTokens(port_4);
                _builder.append(_numTokens_3, "\t");
                _builder.append("];");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
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
    {
      EList<Port> _ports_2 = action.getInputPattern().getPorts();
      for(final Port port_5 : _ports_2) {
        {
          if (this.isSDF) {
            _builder.append("\t");
            _builder.append("DAL_read((void*)PORT_");
            String _name_12 = port_5.getName();
            _builder.append(_name_12, "\t");
            _builder.append(", ");
            String _name_13 = port_5.getName();
            _builder.append(_name_13, "\t");
            _builder.append("_buffer, sizeof(");
            CharSequence _doSwitch_7 = this.doSwitch(port_5.getType());
            _builder.append(_doSwitch_7, "\t");
            _builder.append(")*");
            int _numTokens_4 = action.getInputPattern().getNumTokens(port_5);
            _builder.append(_numTokens_4, "\t");
            _builder.append(", _p);");
            _builder.newLineIfNotEmpty();
          } else {
            {
              if (((!port_5.hasAttribute("peekPort")) && this.actor.hasAttribute("variableInputPattern"))) {
                _builder.append("\t");
                _builder.append("DAL_read((void*)PORT_");
                String _name_14 = port_5.getName();
                _builder.append(_name_14, "\t");
                _builder.append(", ");
                String _name_15 = port_5.getName();
                _builder.append(_name_15, "\t");
                _builder.append("_buffer, sizeof(");
                CharSequence _doSwitch_8 = this.doSwitch(port_5.getType());
                _builder.append(_doSwitch_8, "\t");
                _builder.append(")*");
                int _numTokens_5 = action.getInputPattern().getNumTokens(port_5);
                _builder.append(_numTokens_5, "\t");
                _builder.append(", _p);");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    {
      EList<Block> _blocks = action.getBody().getBlocks();
      for(final Block block : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch_9 = this.doSwitch(block);
        _builder.append(_doSwitch_9, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _ports_3 = action.getOutputPattern().getPorts();
      for(final Port port_6 : _ports_3) {
        {
          if (this.isSDF) {
            _builder.append("\t");
            _builder.append("DAL_write((void*)PORT_");
            String _name_16 = port_6.getName();
            _builder.append(_name_16, "\t");
            _builder.append(", ");
            String _name_17 = port_6.getName();
            _builder.append(_name_17, "\t");
            _builder.append("_buffer, sizeof(");
            CharSequence _doSwitch_10 = this.doSwitch(port_6.getType());
            _builder.append(_doSwitch_10, "\t");
            _builder.append(")*");
            int _numTokens_6 = action.getOutputPattern().getNumTokens(port_6);
            _builder.append(_numTokens_6, "\t");
            _builder.append(", _p);");
            _builder.newLineIfNotEmpty();
          } else {
            {
              int _numTokensProduced_3 = port_6.getNumTokensProduced();
              boolean _lessThan_1 = (_numTokensProduced_3 < 0);
              if (_lessThan_1) {
                _builder.append("\t");
                _builder.append("DAL_write((void*)PORT_");
                String _name_18 = port_6.getName();
                _builder.append(_name_18, "\t");
                _builder.append(", ");
                String _name_19 = port_6.getName();
                _builder.append(_name_19, "\t");
                _builder.append("_buffer, sizeof(");
                CharSequence _doSwitch_11 = this.doSwitch(port_6.getType());
                _builder.append(_doSwitch_11, "\t");
                _builder.append(")*");
                int _numTokens_7 = action.getOutputPattern().getNumTokens(port_6);
                _builder.append(_numTokens_7, "\t");
                _builder.append(", _p);");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("\t");
                String _name_20 = port_6.getName();
                _builder.append(_name_20, "\t");
                _builder.append("_index[0] += ");
                int _numTokens_8 = action.getOutputPattern().getNumTokens(port_6);
                _builder.append(_numTokens_8, "\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("_DAL_write((void*)PORT_");
                String _name_21 = port_6.getName();
                _builder.append(_name_21, "\t");
                _builder.append(", ");
                String _name_22 = port_6.getName();
                _builder.append(_name_22, "\t");
                _builder.append("_buffer, SZ_");
                String _name_23 = port_6.getName();
                _builder.append(_name_23, "\t");
                _builder.append("*sizeof(");
                CharSequence _doSwitch_12 = this.doSwitch(port_6.getType());
                _builder.append(_doSwitch_12, "\t");
                _builder.append("), ");
                String _name_24 = port_6.getName();
                _builder.append(_name_24, "\t");
                _builder.append("_index, SZ_");
                String _name_25 = port_6.getName();
                _builder.append(_name_25, "\t");
                _builder.append(", _p);");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("return 0;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    CharSequence _printScheduler = this.printScheduler(action.getScheduler());
    _builder.append(_printScheduler);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    final String output = _builder.toString();
    this.currentAction = null;
    return output;
  }
  
  private String printInitializeVars(final Action action) {
    this.currentAction = action;
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Var> _locals = action.getBody().getLocals();
      for(final Var variable : _locals) {
        CharSequence _declare = this.declare(variable);
        _builder.append(_declare);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    final String output = _builder.toString();
    this.currentAction = null;
    return output;
  }
  
  private String printInitializeBlocks(final Action action) {
    this.currentAction = action;
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Block> _blocks = action.getBody().getBlocks();
      for(final Block block : _blocks) {
        CharSequence _doSwitch = this.doSwitch(block);
        _builder.append(_doSwitch);
        _builder.newLineIfNotEmpty();
      }
    }
    final String output = _builder.toString();
    this.currentAction = null;
    return output;
  }
  
  protected CharSequence declareProc(final Procedure proc) {
    CharSequence _xblockexpression = null;
    {
      String comma = ",";
      StringConcatenation _builder = new StringConcatenation();
      {
        String _name = proc.getName();
        boolean _notEquals = (!Objects.equal(_name, "print"));
        if (_notEquals) {
          {
            int _size = proc.getParameters().size();
            boolean _equals = (_size == 0);
            if (_equals) {
              _builder.append(comma = "");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("static ");
          CharSequence _doSwitch = this.doSwitch(proc.getReturnType());
          _builder.append(_doSwitch);
          _builder.append(" ");
          String _name_1 = proc.getName();
          _builder.append(_name_1);
          _builder.append("(DALProcess *_p");
          _builder.append(comma);
          _builder.append(" ");
          final Function1<Param, CharSequence> _function = new Function1<Param, CharSequence>() {
            @Override
            public CharSequence apply(final Param it) {
              return InstanceCPrinter.this.declare(it.getVariable());
            }
          };
          String _join = IterableExtensions.<Param>join(proc.getParameters(), ", ", _function);
          _builder.append(_join);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  private CharSequence print(final Procedure proc) {
    CharSequence _xblockexpression = null;
    {
      String comma = ",";
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      {
        if (((proc != null) && (!Objects.equal(proc.getName(), "print")))) {
          {
            int _size = proc.getParameters().size();
            boolean _equals = (_size == 0);
            if (_equals) {
              _builder.append(comma = "");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            boolean _isNative = proc.isNative();
            if (_isNative) {
              _builder.append("static ");
              CharSequence _doSwitch = this.doSwitch(proc.getReturnType());
              _builder.append(_doSwitch);
              _builder.append(" ");
              String _name = proc.getName();
              _builder.append(_name);
              _builder.append("(DALProcess *_p");
              _builder.append(comma);
              _builder.append(" ");
              final Function1<Param, CharSequence> _function = new Function1<Param, CharSequence>() {
                @Override
                public CharSequence apply(final Param it) {
                  return InstanceCPrinter.this.declare(it.getVariable());
                }
              };
              String _join = IterableExtensions.<Param>join(proc.getParameters(), ", ", _function);
              _builder.append(_join);
              _builder.append(") {");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("#include \"");
              String _name_1 = proc.getName();
              _builder.append(_name_1, "\t");
              _builder.append(".def\"");
              _builder.newLineIfNotEmpty();
              _builder.append("}");
              _builder.newLine();
            } else {
              _builder.append("static ");
              CharSequence _doSwitch_1 = this.doSwitch(proc.getReturnType());
              _builder.append(_doSwitch_1);
              _builder.append(" ");
              String _name_2 = proc.getName();
              _builder.append(_name_2);
              _builder.append("(DALProcess *_p");
              _builder.append(comma);
              _builder.append(" ");
              final Function1<Param, CharSequence> _function_1 = new Function1<Param, CharSequence>() {
                @Override
                public CharSequence apply(final Param it) {
                  return InstanceCPrinter.this.declare(it.getVariable());
                }
              };
              String _join_1 = IterableExtensions.<Param>join(proc.getParameters(), ", ", _function_1);
              _builder.append(_join_1);
              _builder.append(") {");
              _builder.newLineIfNotEmpty();
              {
                EList<Var> _locals = proc.getLocals();
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
                EList<Block> _blocks = proc.getBlocks();
                for(final Block block : _blocks) {
                  _builder.append("\t");
                  CharSequence _doSwitch_2 = this.doSwitch(block);
                  _builder.append(_doSwitch_2, "\t");
                  _builder.newLineIfNotEmpty();
                }
              }
              _builder.append("}");
              _builder.newLine();
            }
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printScheduler(final Procedure proc) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
      if (_hasAttribute) {
        _builder.append("int ");
        String _name = proc.getName();
        _builder.append(_name);
        _builder.append("(DALProcess *_p) {");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("int ");
        String _name_1 = proc.getName();
        _builder.append(_name_1);
        _builder.append("(DALProcess *_p");
        {
          EList<Port> _inputs = this.actor.getInputs();
          for(final Port port : _inputs) {
            _builder.append(" ,");
            CharSequence _doSwitch = this.doSwitch(port.getType());
            _builder.append(_doSwitch);
            _builder.append(" *");
            String _name_2 = port.getName();
            _builder.append(_name_2);
            _builder.append("_buffer, int *");
            String _name_3 = port.getName();
            _builder.append(_name_3);
            _builder.append("_index");
          }
        }
        _builder.append(", int *iter) {");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Var> _locals = proc.getLocals();
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
      EList<Block> _blocks = proc.getBlocks();
      for(final Block block : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(block);
        _builder.append(_doSwitch_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence declareStateVar(final Var variable) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      if (((variable.isInitialized() && (!variable.isAssignable())) && (!variable.getType().isList()))) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("#define ");
        String _name = variable.getName();
        _builder.append(_name);
        _builder.append(" ");
        CharSequence _doSwitch = this.doSwitch(variable.getInitialValue());
        _builder.append(_doSwitch);
        _xifexpression = _builder.toString();
      } else {
        String _xblockexpression_1 = null;
        {
          String _xifexpression_1 = null;
          boolean _isAssignable = variable.isAssignable();
          boolean _not = (!_isAssignable);
          if (_not) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("const ");
            _xifexpression_1 = _builder_1.toString();
          } else {
            StringConcatenation _builder_2 = new StringConcatenation();
            _xifexpression_1 = _builder_2.toString();
          }
          final String const_ = _xifexpression_1;
          String _xifexpression_2 = null;
          boolean _isInitialized = variable.isInitialized();
          if (_isInitialized) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append(" ");
            _builder_3.append("= ");
            CharSequence _doSwitch_1 = this.doSwitch(variable.getInitialValue());
            _builder_3.append(_doSwitch_1, " ");
            _xifexpression_2 = _builder_3.toString();
          } else {
            StringConcatenation _builder_4 = new StringConcatenation();
            _xifexpression_2 = _builder_4.toString();
          }
          final String init = _xifexpression_2;
          StringConcatenation _builder_5 = new StringConcatenation();
          _builder_5.append("static ");
          _builder_5.append(const_);
          CharSequence _declare = this.declare(variable);
          _builder_5.append(_declare);
          _builder_5.append(init);
          _builder_5.append(";");
          _xblockexpression_1 = _builder_5.toString();
        }
        _xifexpression = _xblockexpression_1;
      }
      final String varDecl = _xifexpression;
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(varDecl);
      _builder_1.newLineIfNotEmpty();
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }
  
  private CharSequence declareStateVarInInit(final Var variable) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      if ((variable.isInitialized() && variable.isAssignable())) {
        String _xifexpression_1 = null;
        boolean _isList = variable.getType().isList();
        if (_isList) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("memcpy(_p->local->");
          String _name = variable.getName();
          _builder.append(_name);
          _builder.append(", ");
          String _name_1 = variable.getName();
          _builder.append(_name_1);
          _builder.append("_initial, sizeof(");
          String _name_2 = variable.getName();
          _builder.append(_name_2);
          _builder.append("_initial));");
          _xifexpression_1 = _builder.toString();
        } else {
          String _xblockexpression_1 = null;
          {
            String _xifexpression_2 = null;
            boolean _isInitialized = variable.isInitialized();
            if (_isInitialized) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(" ");
              _builder_1.append("= ");
              CharSequence _doSwitch = this.doSwitch(variable.getInitialValue());
              _builder_1.append(_doSwitch, " ");
              _xifexpression_2 = _builder_1.toString();
            } else {
              StringConcatenation _builder_2 = new StringConcatenation();
              _xifexpression_2 = _builder_2.toString();
            }
            final String init = _xifexpression_2;
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("_p->local->");
            String _name_3 = variable.getName();
            _builder_3.append(_name_3);
            _builder_3.append(init);
            _builder_3.append(";");
            _xblockexpression_1 = _builder_3.toString();
          }
          _xifexpression_1 = _xblockexpression_1;
        }
        _xifexpression = _xifexpression_1;
      }
      final String varDecl = _xifexpression;
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(varDecl);
      _builder_1.newLineIfNotEmpty();
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }
  
  private CharSequence declareStateVarListInit(final Var variable) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      if (((variable.isInitialized() && variable.isAssignable()) && variable.getType().isList())) {
        String _xblockexpression_1 = null;
        {
          String _xifexpression_1 = null;
          boolean _isInitialized = variable.isInitialized();
          if (_isInitialized) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(" ");
            _builder.append("= ");
            CharSequence _doSwitch = this.doSwitch(variable.getInitialValue());
            _builder.append(_doSwitch, " ");
            _xifexpression_1 = _builder.toString();
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _xifexpression_1 = _builder_1.toString();
          }
          final String init = _xifexpression_1;
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("static const ");
          CharSequence _doSwitch_1 = this.doSwitch(variable.getType());
          _builder_2.append(_doSwitch_1);
          _builder_2.append(" ");
          String _name = variable.getName();
          _builder_2.append(_name);
          _builder_2.append("_initial[]");
          _builder_2.append(init);
          _builder_2.append(";");
          _xblockexpression_1 = _builder_2.toString();
        }
        _xifexpression = _xblockexpression_1;
      }
      final String varDecl = _xifexpression;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(varDecl);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  private CharSequence declareConstStateVars(final Var variable) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isAssignable = variable.isAssignable();
      boolean _not = (!_isAssignable);
      if (_not) {
        String _xifexpression_1 = null;
        if (((variable.isInitialized() && (!variable.isAssignable())) && (!variable.getType().isList()))) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("#define ");
          String _name = variable.getName();
          _builder.append(_name);
          _builder.append(" ");
          CharSequence _doSwitch = this.doSwitch(variable.getInitialValue());
          _builder.append(_doSwitch);
          _xifexpression_1 = _builder.toString();
        } else {
          String _xblockexpression_1 = null;
          {
            String _xifexpression_2 = null;
            boolean _isAssignable_1 = variable.isAssignable();
            boolean _not_1 = (!_isAssignable_1);
            if (_not_1) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("const ");
              _xifexpression_2 = _builder_1.toString();
            } else {
              StringConcatenation _builder_2 = new StringConcatenation();
              _xifexpression_2 = _builder_2.toString();
            }
            final String const_ = _xifexpression_2;
            String _xifexpression_3 = null;
            boolean _isInitialized = variable.isInitialized();
            if (_isInitialized) {
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append(" ");
              _builder_3.append("= ");
              CharSequence _doSwitch_1 = this.doSwitch(variable.getInitialValue());
              _builder_3.append(_doSwitch_1, " ");
              _xifexpression_3 = _builder_3.toString();
            } else {
              StringConcatenation _builder_4 = new StringConcatenation();
              _xifexpression_3 = _builder_4.toString();
            }
            final String init = _xifexpression_3;
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append("static ");
            _builder_5.append(const_);
            CharSequence _declare = this.declare(variable);
            _builder_5.append(_declare);
            _builder_5.append(init);
            _builder_5.append(";");
            _xblockexpression_1 = _builder_5.toString();
          }
          _xifexpression_1 = _xblockexpression_1;
        }
        _xifexpression = _xifexpression_1;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1.toString();
      }
      final String varDecl = _xifexpression;
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(varDecl);
      _builder_2.newLineIfNotEmpty();
      _xblockexpression = _builder_2;
    }
    return _xblockexpression;
  }
  
  private CharSequence createPeekPortInit(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void _DAL_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_initial(void *port, DALProcess *p) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("if (p->local->_fo_filled_");
    String _name_1 = port.getName();
    _builder.append(_name_1, "\t");
    _builder.append(" == 1) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("DAL_read(port, &p->local->_fo_");
    String _name_2 = port.getName();
    _builder.append(_name_2, "\t");
    _builder.append(", sizeof(");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch, "\t");
    _builder.append("), p);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("p->local->_fo_filled_");
    String _name_3 = port.getName();
    _builder.append(_name_3, "\t");
    _builder.append(" = 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence createReadOverload(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void _DAL_read_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("(void *port, ");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch);
    _builder.append(" *trg, DALProcess *p) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("trg[0] = p->local->_fo_");
    String _name_1 = port.getName();
    _builder.append(_name_1, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("DAL_read(port, &p->local->_fo_");
    String _name_2 = port.getName();
    _builder.append(_name_2, "\t");
    _builder.append(", sizeof(");
    CharSequence _doSwitch_1 = this.doSwitch(port.getType());
    _builder.append(_doSwitch_1, "\t");
    _builder.append("), p);");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence createPeekOverload(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void _DAL_peek_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("(void *port, ");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch);
    _builder.append(" *trg, DALProcess *p) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("trg[0] = p->local->_fo_");
    String _name_1 = port.getName();
    _builder.append(_name_1, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence caseBlockIf(final BlockIf block) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    CharSequence _doSwitch = this.doSwitch(block.getCondition());
    _builder.append(_doSwitch);
    _builder.append(") {");
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
    _builder.append("}");
    {
      boolean _isElseRequired = block.isElseRequired();
      if (_isElseRequired) {
        _builder.append(" else {");
        _builder.newLineIfNotEmpty();
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
    _builder.append("while (");
    CharSequence _doSwitch = this.doSwitch(blockWhile.getCondition());
    _builder.append(_doSwitch);
    _builder.append(") {");
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
  
  @Override
  public String caseBlockFor(final BlockFor block) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("for (");
    final Function1<Instruction, CharSequence> _function = new Function1<Instruction, CharSequence>() {
      @Override
      public CharSequence apply(final Instruction it) {
        StringConcatenation _builder = new StringConcatenation();
        String _expression = InstanceCPrinter.this.toExpression(it);
        _builder.append(_expression);
        return _builder.toString();
      }
    };
    String _join = IterableExtensions.<Instruction>join(block.getInit(), ", ", _function);
    _builder.append(_join);
    _builder.append(" ; ");
    CharSequence _doSwitch = this.doSwitch(block.getCondition());
    _builder.append(_doSwitch);
    _builder.append(" ; ");
    final Function1<Instruction, CharSequence> _function_1 = new Function1<Instruction, CharSequence>() {
      @Override
      public CharSequence apply(final Instruction it) {
        StringConcatenation _builder = new StringConcatenation();
        String _expression = InstanceCPrinter.this.toExpression(it);
        _builder.append(_expression);
        return _builder.toString();
      }
    };
    String _join_1 = IterableExtensions.<Instruction>join(block.getStep(), ", ", _function_1);
    _builder.append(_join_1);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    {
      EList<Block> _blocks = block.getBlocks();
      for(final Block contentBlock : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(contentBlock);
        _builder.append(_doSwitch_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public CharSequence caseInstAssign(final InstAssign inst) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = inst.getTarget().getVariable().getName();
    _builder.append(_name);
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(inst.getValue());
    _builder.append(_doSwitch);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public CharSequence caseInstLoad(final InstLoad load) {
    CharSequence _xblockexpression = null;
    {
      final Port srcPort = this.getPort(load.getSource().getVariable());
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      {
        if ((srcPort != null)) {
          {
            Pattern _peekPattern = null;
            if (this.currentAction!=null) {
              _peekPattern=this.currentAction.getPeekPattern();
            }
            boolean _contains = _peekPattern.contains(load.getSource().getVariable());
            if (_contains) {
              {
                boolean _hasAttribute = this.actor.hasAttribute("variableInputPattern");
                boolean _not = (!_hasAttribute);
                if (_not) {
                  String _name = load.getTarget().getVariable().getName();
                  _builder.append(_name);
                  _builder.append(" = ");
                  String _name_1 = srcPort.getName();
                  _builder.append(_name_1);
                  _builder.append("_buffer[");
                  String _name_2 = srcPort.getName();
                  _builder.append(_name_2);
                  _builder.append("_index[0]];");
                  _builder.newLineIfNotEmpty();
                } else {
                  _builder.append("_DAL_peek_");
                  String _name_3 = srcPort.getName();
                  _builder.append(_name_3);
                  _builder.append("((void*)PORT_");
                  String _name_4 = srcPort.getName();
                  _builder.append(_name_4);
                  _builder.append(", &");
                  String _name_5 = load.getTarget().getVariable().getName();
                  _builder.append(_name_5);
                  _builder.append(", _p);");
                  _builder.newLineIfNotEmpty();
                }
              }
            } else {
              {
                boolean _hasAttribute_1 = this.actor.hasAttribute("variableInputPattern");
                boolean _not_1 = (!_hasAttribute_1);
                if (_not_1) {
                  String _name_6 = load.getTarget().getVariable().getName();
                  _builder.append(_name_6);
                  _builder.append(" = ");
                  String _name_7 = srcPort.getName();
                  _builder.append(_name_7);
                  _builder.append("_buffer[");
                  String _name_8 = srcPort.getName();
                  _builder.append(_name_8);
                  _builder.append("_index[0]];");
                  _builder.newLineIfNotEmpty();
                  String _name_9 = srcPort.getName();
                  _builder.append(_name_9);
                  _builder.append("_index[0] ++;");
                  _builder.newLineIfNotEmpty();
                  _builder.append("iter[0] ++;");
                  _builder.newLine();
                } else {
                  {
                    boolean _hasAttribute_2 = srcPort.hasAttribute("peekPort");
                    if (_hasAttribute_2) {
                      _builder.append("_DAL_read_");
                      String _name_10 = srcPort.getName();
                      _builder.append(_name_10);
                      _builder.append("((void*)PORT_");
                      String _name_11 = srcPort.getName();
                      _builder.append(_name_11);
                      _builder.append(", &");
                      String _name_12 = load.getTarget().getVariable().getName();
                      _builder.append(_name_12);
                      _builder.append(", _p);");
                      _builder.newLineIfNotEmpty();
                    } else {
                      String _name_13 = load.getTarget().getVariable().getName();
                      _builder.append(_name_13);
                      _builder.append(" = ");
                      String _name_14 = srcPort.getName();
                      _builder.append(_name_14);
                      _builder.append("_buffer[");
                      CharSequence _doSwitch = this.doSwitch(IterableExtensions.<Expression>head(load.getIndexes()));
                      _builder.append(_doSwitch);
                      _builder.append("];");
                      _builder.newLineIfNotEmpty();
                    }
                  }
                }
              }
            }
          }
        } else {
          {
            if (((load.getSource().getVariable().isGlobal() == true) && (load.getSource().getVariable().isAssignable() == true))) {
              String _name_15 = load.getTarget().getVariable().getName();
              _builder.append(_name_15);
              _builder.append(" = _p->local->");
              String _name_16 = load.getSource().getVariable().getName();
              _builder.append(_name_16);
              String _printArrayIndexes = this.printArrayIndexes(load.getIndexes());
              _builder.append(_printArrayIndexes);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            } else {
              String _name_17 = load.getTarget().getVariable().getName();
              _builder.append(_name_17);
              _builder.append(" = ");
              String _name_18 = load.getSource().getVariable().getName();
              _builder.append(_name_18);
              String _printArrayIndexes_1 = this.printArrayIndexes(load.getIndexes());
              _builder.append(_printArrayIndexes_1);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseInstStore(final InstStore store) {
    CharSequence _xblockexpression = null;
    {
      final Port trgtPort = this.getPort(store.getTarget().getVariable());
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      {
        if ((trgtPort != null)) {
          _builder.newLine();
          {
            boolean _isNative = this.currentAction.getOutputPattern().getVarToPortMap().get(store.getTarget().getVariable()).isNative();
            if (_isNative) {
              _builder.append("printf(\"");
              String _name = trgtPort.getName();
              _builder.append(_name);
              _builder.append(" = %i\\n\", ");
              CharSequence _doSwitch = this.doSwitch(store.getValue());
              _builder.append(_doSwitch);
              _builder.append(");");
              _builder.newLineIfNotEmpty();
            } else {
              {
                int _numTokensProduced = trgtPort.getNumTokensProduced();
                boolean _greaterThan = (_numTokensProduced > 0);
                if (_greaterThan) {
                  String _name_1 = trgtPort.getName();
                  _builder.append(_name_1);
                  _builder.append("_buffer[");
                  String _name_2 = trgtPort.getName();
                  _builder.append(_name_2);
                  _builder.append("_index[0] + ");
                  CharSequence _doSwitch_1 = this.doSwitch(IterableExtensions.<Expression>head(store.getIndexes()));
                  _builder.append(_doSwitch_1);
                  _builder.append("] = ");
                  CharSequence _doSwitch_2 = this.doSwitch(store.getValue());
                  _builder.append(_doSwitch_2);
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                } else {
                  String _name_3 = trgtPort.getName();
                  _builder.append(_name_3);
                  _builder.append("_buffer[");
                  CharSequence _doSwitch_3 = this.doSwitch(IterableExtensions.<Expression>head(store.getIndexes()));
                  _builder.append(_doSwitch_3);
                  _builder.append("] = ");
                  CharSequence _doSwitch_4 = this.doSwitch(store.getValue());
                  _builder.append(_doSwitch_4);
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
        } else {
          {
            boolean _isGlobal = store.getTarget().getVariable().isGlobal();
            boolean _equals = (_isGlobal == true);
            if (_equals) {
              _builder.append("_p->local->");
              String _name_4 = store.getTarget().getVariable().getName();
              _builder.append(_name_4);
              String _printArrayIndexes = this.printArrayIndexes(store.getIndexes());
              _builder.append(_printArrayIndexes);
              _builder.append(" = ");
              CharSequence _doSwitch_5 = this.doSwitch(store.getValue());
              _builder.append(_doSwitch_5);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            } else {
              String _name_5 = store.getTarget().getVariable().getName();
              _builder.append(_name_5);
              String _printArrayIndexes_1 = this.printArrayIndexes(store.getIndexes());
              _builder.append(_printArrayIndexes_1);
              _builder.append(" = ");
              CharSequence _doSwitch_6 = this.doSwitch(store.getValue());
              _builder.append(_doSwitch_6);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseInstCall(final InstCall call) {
    CharSequence _xblockexpression = null;
    {
      String comma = ",";
      StringConcatenation _builder = new StringConcatenation();
      {
        int _size = call.getArguments().size();
        boolean _equals = (_size == 0);
        if (_equals) {
          _builder.append(comma = "");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _isPrint = call.isPrint();
        if (_isPrint) {
          _builder.append("printf(");
          String _join = IterableExtensions.join(this.printfArgs(call.getArguments()), ", ");
          _builder.append(_join);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        } else {
          {
            Def _target = call.getTarget();
            boolean _tripleNotEquals = (_target != null);
            if (_tripleNotEquals) {
              String _name = call.getTarget().getVariable().getName();
              _builder.append(_name);
              _builder.append(" = ");
            }
          }
          String _name_1 = call.getProcedure().getName();
          _builder.append(_name_1);
          _builder.append("(_p");
          _builder.append(comma);
          _builder.append(" ");
          final Function1<Arg, CharSequence> _function = new Function1<Arg, CharSequence>() {
            @Override
            public CharSequence apply(final Arg it) {
              return InstanceCPrinter.this.printCallArg(it);
            }
          };
          String _join_1 = IterableExtensions.<Arg>join(call.getArguments(), ", ", _function);
          _builder.append(_join_1);
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
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    {
      Expression _value = ret.getValue();
      boolean _tripleNotEquals = (_value != null);
      if (_tripleNotEquals) {
        _builder.append("return ");
        CharSequence _doSwitch = this.doSwitch(ret.getValue());
        _builder.append(_doSwitch);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public String caseInstTernary(final InstTernary inst) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = inst.getTarget().getVariable().getName();
    _builder.append(_name);
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(inst.getConditionValue());
    _builder.append(_doSwitch);
    _builder.append(" ? ");
    CharSequence _doSwitch_1 = this.doSwitch(inst.getTrueValue());
    _builder.append(_doSwitch_1);
    _builder.append(" : ");
    CharSequence _doSwitch_2 = this.doSwitch(inst.getFalseValue());
    _builder.append(_doSwitch_2);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private Port getPort(final Var variable) {
    Port _xifexpression = null;
    if ((this.currentAction == null)) {
      _xifexpression = null;
    } else {
      Port _xifexpression_1 = null;
      Pattern _inputPattern = null;
      if (this.currentAction!=null) {
        _inputPattern=this.currentAction.getInputPattern();
      }
      boolean _contains = _inputPattern.contains(variable);
      if (_contains) {
        _xifexpression_1 = this.currentAction.getInputPattern().getPort(variable);
      } else {
        Port _xifexpression_2 = null;
        Pattern _outputPattern = null;
        if (this.currentAction!=null) {
          _outputPattern=this.currentAction.getOutputPattern();
        }
        boolean _contains_1 = _outputPattern.contains(variable);
        if (_contains_1) {
          _xifexpression_2 = this.currentAction.getOutputPattern().getPort(variable);
        } else {
          Port _xifexpression_3 = null;
          Pattern _peekPattern = null;
          if (this.currentAction!=null) {
            _peekPattern=this.currentAction.getPeekPattern();
          }
          boolean _contains_2 = _peekPattern.contains(variable);
          if (_contains_2) {
            _xifexpression_3 = this.currentAction.getPeekPattern().getPort(variable);
          } else {
            _xifexpression_3 = null;
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  private CharSequence printCallArg(final Arg arg) {
    CharSequence _xifexpression = null;
    boolean _isByRef = arg.isByRef();
    if (_isByRef) {
      String _name = ((ArgByRef) arg).getUse().getVariable().getName();
      String _plus = ("&" + _name);
      String _printArrayIndexes = this.printArrayIndexes(((ArgByRef) arg).getIndexes());
      _xifexpression = (_plus + _printArrayIndexes);
    } else {
      _xifexpression = this.doSwitch(((ArgByVal) arg).getValue());
    }
    return _xifexpression;
  }
  
  private CharSequence getInline() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  private Integer sizeOrDefaultSize(final Connection conn) {
    Integer _xifexpression = null;
    if (((conn == null) || (conn.getSize() == null))) {
      _xifexpression = Integer.valueOf(this.fifoSize);
    } else {
      _xifexpression = conn.getSize();
    }
    return _xifexpression;
  }
  
  private boolean isOutputConnected(final Port port) {
    boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.outgoingPortMap.get(port));
    return (!_isNullOrEmpty);
  }
  
  private boolean isInputConneted(final Port port) {
    Connection _get = this.incomingPortMap.get(port);
    return (_get != null);
  }
  
  private void buildInputPattern() {
    EList<Action> _actionsOutsideFsm = this.actor.getActionsOutsideFsm();
    for (final Action action : _actionsOutsideFsm) {
      {
        final Pattern actionPattern = action.getInputPattern();
        EList<Port> _ports = actionPattern.getPorts();
        for (final Port port : _ports) {
          {
            int numTokens = Math.max(this.inputPattern.getNumTokens(port), actionPattern.getNumTokens(port));
            this.inputPattern.setNumTokens(port, numTokens);
          }
        }
      }
    }
  }
  
  private void buildTransitionPattern() {
    final FSM fsm = this.actor.getFsm();
    if ((fsm != null)) {
      EList<State> _states = fsm.getStates();
      for (final State state : _states) {
        {
          final Pattern pattern = DfFactory.eINSTANCE.createPattern();
          EList<Edge> _outgoing = state.getOutgoing();
          for (final Edge edge : _outgoing) {
            {
              final Pattern actionPattern = ((Transition) edge).getAction().getInputPattern();
              EList<Port> _ports = actionPattern.getPorts();
              for (final Port port : _ports) {
                {
                  int numTokens = Math.max(pattern.getNumTokens(port), actionPattern.getNumTokens(port));
                  pattern.setNumTokens(port, numTokens);
                }
              }
            }
          }
          this.transitionPattern.put(state, pattern);
        }
      }
    }
  }
}
