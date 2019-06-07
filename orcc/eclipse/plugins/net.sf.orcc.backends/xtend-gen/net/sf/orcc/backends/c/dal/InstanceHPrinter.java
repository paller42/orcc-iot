package net.sf.orcc.backends.c.dal;

import com.google.common.base.Objects;
import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.TypeBool;
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
public class InstanceHPrinter extends CTemplate {
  private Actor actor;
  
  private String entityName;
  
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("u8");
    return _builder;
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
    _builder.append("#include \"global.h\"");
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
            CharSequence _enumerate = this.enumerate(this.actor.getInputs().get(((i).intValue() - 1)));
            _builder.append(_enumerate);
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
            CharSequence _enumerate_1 = this.enumerate(this.actor.getOutputs().get(((i_1).intValue() - 1)));
            _builder.append(_enumerate_1);
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
    _builder.append("\t");
    _builder.append("void *_io;");
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
      EList<Action> _initializes = this.actor.getInitializes();
      for(final Action action : _initializes) {
        CharSequence _print = this.print(action);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
      }
    }
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
  
  private CharSequence print(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    {
      String _name = action.getBody().getName();
      boolean _equals = Objects.equal(_name, "fire_terminate");
      if (_equals) {
        _builder.append("int ");
        _builder.append(this.entityName);
        _builder.append("_fire(DALProcess *);");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("int ");
        _builder.append(this.entityName);
        _builder.append("_");
        String _name_1 = action.getBody().getName();
        _builder.append(_name_1);
        _builder.append("(DALProcess *);");
        _builder.newLineIfNotEmpty();
      }
    }
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
  
  private CharSequence enumerate(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#define PORT_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" \"");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
