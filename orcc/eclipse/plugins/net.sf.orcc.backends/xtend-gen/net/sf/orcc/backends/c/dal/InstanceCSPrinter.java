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
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrccLogger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generate and print actor source file for DAL backend.
 * 
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C InstancePrinter
 */
@SuppressWarnings("all")
public class InstanceCSPrinter extends CTemplate {
  protected Instance instance;
  
  protected Actor actor;
  
  protected Map<Port, Connection> incomingPortMap;
  
  protected Map<Port, List<Connection>> outgoingPortMap;
  
  protected String entityName;
  
  protected final Pattern inputPattern = DfFactory.eINSTANCE.createPattern();
  
  protected final Map<State, Pattern> transitionPattern = new HashMap<State, Pattern>();
  
  protected Action currentAction;
  
  protected InstanceCSPrinter() {
    super();
    this.instance = null;
  }
  
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("int");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeInt(final TypeInt type) {
    CharSequence _xifexpression = null;
    int _size = type.getSize();
    boolean _greaterThan = (_size > 16);
    if (_greaterThan) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("int");
      _xifexpression = _builder;
    } else {
      CharSequence _xifexpression_1 = null;
      int _size_1 = type.getSize();
      boolean _greaterThan_1 = (_size_1 > 8);
      if (_greaterThan_1) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("short");
        _xifexpression_1 = _builder_1;
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("char");
        _xifexpression_1 = _builder_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseTypeUint(final TypeUint type) {
    CharSequence _xifexpression = null;
    int _size = type.getSize();
    boolean _greaterThan = (_size > 16);
    if (_greaterThan) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("unsigned int");
      _xifexpression = _builder;
    } else {
      CharSequence _xifexpression_1 = null;
      int _size_1 = type.getSize();
      boolean _greaterThan_1 = (_size_1 > 8);
      if (_greaterThan_1) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("unsigned short");
        _xifexpression_1 = _builder_1;
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("unsigned char");
        _xifexpression_1 = _builder_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
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
    _builder.append("int sqrti(int x) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return (int) sqrt((float) x);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"");
    _builder.append(this.entityName);
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        {
          EList<Var> _stateVars = this.actor.getStateVars();
          for(final Var variable : _stateVars) {
            {
              if (((variable.isInitialized() && (!variable.isAssignable())) && (!variable.getType().isList()))) {
                _builder.append("#define ");
                String _name = variable.getName();
                _builder.append(_name);
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
                    String _name_1 = arg.getVariable().getName();
                    _builder.append(_name_1);
                    String _printArrayIndexes = this.printArrayIndexes(arg.getValue().getType().getDimensionsExpr());
                    _builder.append(_printArrayIndexes);
                    _builder.append(" = ");
                    CharSequence _doSwitch_1 = this.doSwitch(arg.getValue());
                    _builder.append(_doSwitch_1);
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  } else {
                    _builder.append("#define ");
                    String _name_2 = arg.getVariable().getName();
                    _builder.append(_name_2);
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
    _builder.newLine();
    {
      boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not_1 = (!_isNullOrEmpty_1);
      if (_not_1) {
        {
          EList<Var> _stateVars_2 = this.actor.getStateVars();
          for(final Var variable_3 : _stateVars_2) {
            CharSequence _declareConstStateVarsNonList = this.declareConstStateVarsNonList(variable_3);
            _builder.append(_declareConstStateVarsNonList);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_init(DALProcess *_p) {");
    _builder.newLineIfNotEmpty();
    {
      boolean _isNullOrEmpty_2 = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not_2 = (!_isNullOrEmpty_2);
      if (_not_2) {
        {
          EList<Var> _stateVars_3 = this.actor.getStateVars();
          for(final Var variable_4 : _stateVars_3) {
            _builder.append("\t");
            CharSequence _declareStateVarInInit = this.declareStateVarInInit(variable_4);
            _builder.append(_declareStateVarInInit, "\t");
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
    _builder.newLine();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_finish(DALProcess *_p) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("return 0;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    CharSequence _actorScheduler = this.actorScheduler();
    _builder.append(_actorScheduler);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence actorScheduler() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_fire(DALProcess *_p) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        {
          EList<Var> _stateVars = this.actor.getStateVars();
          for(final Var variable : _stateVars) {
            _builder.append("\t");
            CharSequence _declareConstStateVarsList = this.declareConstStateVarsList(variable);
            _builder.append(_declareConstStateVarsList, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      EList<Action> _actions = this.actor.getActions();
      for(final Action action : _actions) {
        _builder.append("\t");
        String _print = this.print(action);
        _builder.append(_print, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return 0;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private void checkConnectivy() {
    final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
      @Override
      public Boolean apply(final Port it) {
        boolean _isInputConneted = InstanceCSPrinter.this.isInputConneted(it);
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
        boolean _isOutputConnected = InstanceCSPrinter.this.isOutputConnected(it);
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
  
  private String print(final Action action) {
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
    {
      EList<Block> _blocks = action.getBody().getBlocks();
      for(final Block block : _blocks) {
        {
          boolean _isBlockWhile = block.isBlockWhile();
          if (_isBlockWhile) {
            CharSequence _forEach = this.forEach(((BlockWhile) block), action.getOutputPattern().getPorts().get(0));
            _builder.append(_forEach);
            _builder.newLineIfNotEmpty();
          } else {
            CharSequence _doSwitch = this.doSwitch(block);
            _builder.append(_doSwitch);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    final String output = _builder.toString();
    this.currentAction = null;
    return output;
  }
  
  protected CharSequence printScheduler(final Procedure proc) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("int ");
    String _name = proc.getName();
    _builder.append(_name, "\t");
    _builder.append("(DALProcess *_p");
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        _builder.append(" ,");
        CharSequence _doSwitch = this.doSwitch(port.getType());
        _builder.append(_doSwitch, "\t");
        _builder.append(" *");
        String _name_1 = port.getName();
        _builder.append(_name_1, "\t");
        _builder.append("_buffer, int *");
        String _name_2 = port.getName();
        _builder.append(_name_2, "\t");
        _builder.append("_index");
      }
    }
    _builder.append(", int *iter) {");
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
  
  private CharSequence declareConstStateVarsNonList(final Var variable) {
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
          StringConcatenation _builder_1 = new StringConcatenation();
          _xifexpression_1 = _builder_1.toString();
        }
        _xifexpression = _xifexpression_1;
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _xifexpression = _builder_2.toString();
      }
      final String varDecl = _xifexpression;
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append(varDecl);
      _builder_3.newLineIfNotEmpty();
      _xblockexpression = _builder_3;
    }
    return _xblockexpression;
  }
  
  private CharSequence declareConstStateVarsList(final Var variable) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isAssignable = variable.isAssignable();
      boolean _not = (!_isAssignable);
      if (_not) {
        String _xifexpression_1 = null;
        if (((variable.isInitialized() && (!variable.isAssignable())) && (!variable.getType().isList()))) {
          StringConcatenation _builder = new StringConcatenation();
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
              CharSequence _doSwitch = this.doSwitch(variable.getInitialValue());
              _builder_3.append(_doSwitch, " ");
              _xifexpression_3 = _builder_3.toString();
            } else {
              StringConcatenation _builder_4 = new StringConcatenation();
              _xifexpression_3 = _builder_4.toString();
            }
            final String init = _xifexpression_3;
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append(const_);
            _builder_5.append(" ");
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
  
  public CharSequence printIterations(final BlockWhile blockWhile) {
    CharSequence _xifexpression = null;
    boolean _isExprBinary = blockWhile.getCondition().isExprBinary();
    if (_isExprBinary) {
      CharSequence _xblockexpression = null;
      {
        Expression _condition = blockWhile.getCondition();
        ExprBinary eb = ((ExprBinary) _condition);
        CharSequence _xifexpression_1 = null;
        OpBinary _op = eb.getOp();
        boolean _equals = Objects.equal(_op, OpBinary.LE);
        if (_equals) {
          StringConcatenation _builder = new StringConcatenation();
          CharSequence _doSwitch = this.doSwitch(eb.getE2());
          _builder.append(_doSwitch);
          _xifexpression_1 = _builder;
        } else {
          CharSequence _xblockexpression_1 = null;
          {
            String _name = eb.getOp().getName();
            String _plus = ("Unsupported optype: " + _name);
            OrccLogger.traceln(_plus);
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("unknown");
            _xblockexpression_1 = _builder_1;
          }
          _xifexpression_1 = _xblockexpression_1;
        }
        _xblockexpression = _xifexpression_1;
      }
      _xifexpression = _xblockexpression;
    } else {
      CharSequence _xblockexpression_1 = null;
      {
        String _string = blockWhile.getCondition().toString();
        String _plus = ("Unsupported expression type: " + _string);
        OrccLogger.traceln(_plus);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("unknown");
        _xblockexpression_1 = _builder;
      }
      _xifexpression = _xblockexpression_1;
    }
    return _xifexpression;
  }
  
  public CharSequence printIterand(final BlockWhile blockWhile) {
    CharSequence _xifexpression = null;
    boolean _isExprBinary = blockWhile.getCondition().isExprBinary();
    if (_isExprBinary) {
      CharSequence _xblockexpression = null;
      {
        Expression _condition = blockWhile.getCondition();
        ExprBinary eb = ((ExprBinary) _condition);
        CharSequence _xifexpression_1 = null;
        OpBinary _op = eb.getOp();
        boolean _equals = Objects.equal(_op, OpBinary.LE);
        if (_equals) {
          CharSequence _xifexpression_2 = null;
          boolean _isExprVar = eb.getE1().isExprVar();
          if (_isExprVar) {
            StringConcatenation _builder = new StringConcatenation();
            CharSequence _doSwitch = this.doSwitch(eb.getE1());
            _builder.append(_doSwitch);
            _xifexpression_2 = _builder;
          } else {
            CharSequence _xblockexpression_1 = null;
            {
              String _string = eb.getE1().toString();
              String _plus = ("Unsupported iterand expression type: " + _string);
              OrccLogger.traceln(_plus);
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("unknown");
              _xblockexpression_1 = _builder_1;
            }
            _xifexpression_2 = _xblockexpression_1;
          }
          _xifexpression_1 = _xifexpression_2;
        } else {
          CharSequence _xblockexpression_2 = null;
          {
            String _name = eb.getOp().getName();
            String _plus = ("Unsupported optype: " + _name);
            OrccLogger.traceln(_plus);
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("unknown");
            _xblockexpression_2 = _builder_1;
          }
          _xifexpression_1 = _xblockexpression_2;
        }
        _xblockexpression = _xifexpression_1;
      }
      _xifexpression = _xblockexpression;
    } else {
      CharSequence _xblockexpression_1 = null;
      {
        String _string = blockWhile.getCondition().toString();
        String _plus = ("Unsupported expression type: " + _string);
        OrccLogger.traceln(_plus);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("unknown");
        _xblockexpression_1 = _builder;
      }
      _xifexpression = _xblockexpression_1;
    }
    return _xifexpression;
  }
  
  public CharSequence forEach(final BlockWhile blockWhile, final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("for (");
    CharSequence _printIterand = this.printIterand(blockWhile);
    _builder.append(_printIterand);
    _builder.append(" = get_global_id(0) * (BLOCK_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_COUNT / get_global_size(0)); ");
    CharSequence _printIterand_1 = this.printIterand(blockWhile);
    _builder.append(_printIterand_1);
    _builder.append(" < (get_global_id(0) + 1) * (BLOCK_");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("_COUNT / get_global_size(0)); ) // block!");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    {
      EList<Block> _blocks = blockWhile.getBlocks();
      for(final Block block : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch = this.doSwitch(block);
        _builder.append(_doSwitch, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * def forEach(BlockWhile blockWhile, Port port)'''
   * //int BLOCK_loopIterations_COUNT = «blockWhile.printIterations» + 1;
   * DAL_foreach (blk : PORT_«port.name»)
   * {
   * «FOR block : blockWhile.blocks»
   * «block.doSwitch»
   * «ENDFOR»
   * }
   * '''
   */
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
        String _expression = InstanceCSPrinter.this.toExpression(it);
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
        String _expression = InstanceCSPrinter.this.toExpression(it);
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
      {
        if ((srcPort != null)) {
          String _name = load.getTarget().getVariable().getName();
          _builder.append(_name);
          _builder.append(" = PORT_");
          String _name_1 = srcPort.getName();
          _builder.append(_name_1);
          _builder.append("_name[");
          CharSequence _doSwitch = this.doSwitch(IterableExtensions.<Expression>head(load.getIndexes()));
          _builder.append(_doSwitch);
          _builder.append("];");
          _builder.newLineIfNotEmpty();
        } else {
          {
            if (((load.getSource().getVariable().isGlobal() == true) && (load.getSource().getVariable().isAssignable() == true))) {
              String _name_2 = load.getTarget().getVariable().getName();
              _builder.append(_name_2);
              _builder.append(" = _p->local->");
              String _name_3 = load.getSource().getVariable().getName();
              _builder.append(_name_3);
              String _printArrayIndexes = this.printArrayIndexes(load.getIndexes());
              _builder.append(_printArrayIndexes);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            } else {
              String _name_4 = load.getTarget().getVariable().getName();
              _builder.append(_name_4);
              _builder.append(" = ");
              String _name_5 = load.getSource().getVariable().getName();
              _builder.append(_name_5);
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
      {
        if ((trgtPort != null)) {
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
              _builder.append("PORT_");
              String _name_1 = trgtPort.getName();
              _builder.append(_name_1);
              _builder.append("_name[");
              CharSequence _doSwitch_1 = this.doSwitch(IterableExtensions.<Expression>head(store.getIndexes()));
              _builder.append(_doSwitch_1);
              _builder.append("] = ");
              CharSequence _doSwitch_2 = this.doSwitch(store.getValue());
              _builder.append(_doSwitch_2);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
        } else {
          {
            boolean _isGlobal = store.getTarget().getVariable().isGlobal();
            boolean _equals = (_isGlobal == true);
            if (_equals) {
              _builder.append("_p->local->");
              String _name_2 = store.getTarget().getVariable().getName();
              _builder.append(_name_2);
              String _printArrayIndexes = this.printArrayIndexes(store.getIndexes());
              _builder.append(_printArrayIndexes);
              _builder.append(" = ");
              CharSequence _doSwitch_3 = this.doSwitch(store.getValue());
              _builder.append(_doSwitch_3);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            } else {
              String _name_3 = store.getTarget().getVariable().getName();
              _builder.append(_name_3);
              String _printArrayIndexes_1 = this.printArrayIndexes(store.getIndexes());
              _builder.append(_printArrayIndexes_1);
              _builder.append(" = ");
              CharSequence _doSwitch_4 = this.doSwitch(store.getValue());
              _builder.append(_doSwitch_4);
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
          _builder.append("(");
          final Function1<Arg, CharSequence> _function = new Function1<Arg, CharSequence>() {
            @Override
            public CharSequence apply(final Arg it) {
              return InstanceCSPrinter.this.printCallArg(it);
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
