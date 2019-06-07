package net.sf.orcc.backends.c.dal;

import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generate and print actor header file for DAL backend.
 * 
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C InstancePrinter
 */
@SuppressWarnings("all")
public class InstanceHSPrinter extends CTemplate {
  private Actor actor;
  
  private String entityName;
  
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
  
  public Actor setActor(final Actor actor) {
    Actor _xblockexpression = null;
    {
      this.entityName = actor.getName();
      _xblockexpression = this.actor = actor;
    }
    return _xblockexpression;
  }
  
  public CharSequence getFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef ");
    _builder.append(this.entityName);
    _builder.append("_H");
    _builder.newLineIfNotEmpty();
    _builder.append("#define ");
    _builder.append(this.entityName);
    _builder.append("_H");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include <dal.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#define EVENT_DONE \"stop_state1\"");
    _builder.newLine();
    _builder.newLine();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getInputs());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        {
          int _size = this.actor.getInputs().size();
          IntegerRange _upTo = new IntegerRange(1, _size);
          for(final Integer i : _upTo) {
            CharSequence _enumerateInput = this.enumerateInput(this.actor.getInputs().get(((i).intValue() - 1)));
            _builder.append(_enumerateInput);
            _builder.newLineIfNotEmpty();
          }
        }
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
      boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(IterableExtensions.<Port>filter(this.actor.getOutputs(), _function));
      boolean _not_1 = (!_isNullOrEmpty_1);
      if (_not_1) {
        {
          int _size_1 = this.actor.getOutputs().size();
          IntegerRange _upTo_1 = new IntegerRange(1, _size_1);
          for(final Integer i_1 : _upTo_1) {
            CharSequence _enumerateOutput = this.enumerateOutput(this.actor.getOutputs().get(((i_1).intValue() - 1)));
            _builder.append(_enumerateOutput);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("typedef struct _local_states {");
    _builder.newLine();
    {
      boolean _isNullOrEmpty_2 = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not_2 = (!_isNullOrEmpty_2);
      if (_not_2) {
        {
          EList<Var> _stateVars = this.actor.getStateVars();
          for(final Var variable : _stateVars) {
            _builder.append("\t");
            CharSequence _declareStateVar = this.declareStateVar(variable);
            _builder.append(_declareStateVar, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        {
          if ((port.hasAttribute("peekPort") && this.actor.hasAttribute("variableInputPattern"))) {
            _builder.append("\t");
            CharSequence _doSwitch = this.doSwitch(port.getType());
            _builder.append(_doSwitch, "\t");
            _builder.append(" _fo_");
            String _name = port.getName();
            _builder.append(_name, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("int _fo_filled_");
            String _name_1 = port.getName();
            _builder.append(_name_1, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("int _count;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int _FSM_state;");
    _builder.newLine();
    _builder.append("} ");
    _builder.append(this.entityName);
    _builder.append("_State;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_init(DALProcess *);");
    _builder.newLineIfNotEmpty();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_fire(DALProcess *);");
    _builder.newLineIfNotEmpty();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_finish(DALProcess *);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _isNullOrEmpty_3 = IterableExtensions.isNullOrEmpty(this.actor.getParameters());
      boolean _not_3 = (!_isNullOrEmpty_3);
      if (_not_3) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Parameter values of the instance");
        _builder.newLine();
        {
          EList<Var> _parameters = this.actor.getParameters();
          for(final Var variable_1 : _parameters) {
            CharSequence _declareStateVar_1 = this.declareStateVar(variable_1);
            _builder.append(_declareStateVar_1);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence declareStateVar(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isAssignable = variable.isAssignable();
      if (_isAssignable) {
        CharSequence _declare = this.declare(variable);
        _builder.append(_declare);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence enumerateInput(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#define PORT_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" \"");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("typedef ");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch);
    _builder.append(" TOKEN_");
    String _name_2 = port.getName();
    _builder.append(_name_2);
    _builder.append("_t;");
    _builder.newLineIfNotEmpty();
    _builder.append("#define TOKEN_");
    String _name_3 = port.getName();
    _builder.append(_name_3);
    _builder.append("_RATE ");
    int _numTokensConsumed = port.getNumTokensConsumed();
    _builder.append(_numTokensConsumed);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence enumerateOutput(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#define PORT_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" \"");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("typedef ");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch);
    _builder.append(" TOKEN_");
    String _name_2 = port.getName();
    _builder.append(_name_2);
    _builder.append("_t;");
    _builder.newLineIfNotEmpty();
    _builder.append("#define TOKEN_");
    String _name_3 = port.getName();
    _builder.append(_name_3);
    _builder.append("_RATE ");
    int _numTokensProduced = port.getNumTokensProduced();
    int _minus = (-_numTokensProduced);
    _builder.append(_minus);
    _builder.newLineIfNotEmpty();
    _builder.append("#define BLOCK_");
    String _name_4 = port.getName();
    _builder.append(_name_4);
    _builder.append("_SIZE\t1");
    _builder.newLineIfNotEmpty();
    _builder.append("#define BLOCK_");
    String _name_5 = port.getName();
    _builder.append(_name_5);
    _builder.append("_COUNT (TOKEN_");
    String _name_6 = port.getName();
    _builder.append(_name_6);
    _builder.append("_RATE / BLOCK_");
    String _name_7 = port.getName();
    _builder.append(_name_7);
    _builder.append("_SIZE)");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
