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
package net.sf.orcc.backends.c.preesm;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import net.sf.orcc.backends.c.InstancePrinter;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Generate network as graphml file
 * 
 * @author Antoine Lorence
 * @author Karol Desnos
 */
@SuppressWarnings("all")
public class ActorPrinter extends InstancePrinter {
  public CharSequence getActorIDLContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("module ");
    String _simpleName = this.actor.getSimpleName();
    _builder.append(_simpleName);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef long parameter;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef char i8;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef short i16;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef long i32;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef long long i64;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef char u8;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef unsigned short u16;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef unsigned long u32;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("typedef unsigned long long u64;");
    _builder.newLine();
    _builder.newLine();
    {
      boolean _isEmpty = this.actor.getStateVars().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        CharSequence _printStateVarsStruct = this.printStateVarsStruct();
        _builder.append(_printStateVarsStruct, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_1 = this.actor.getInitializes().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("\t");
        CharSequence _printInterface = this.printInterface(IterableExtensions.<Action>head(this.actor.getInitializes()), "init");
        _builder.append(_printInterface, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty_2 = this.actor.getActions().isEmpty();
      boolean _not_2 = (!_isEmpty_2);
      if (_not_2) {
        _builder.append("\t");
        CharSequence _printInterface_1 = this.printInterface(IterableExtensions.<Action>head(this.actor.getActions()), "loop");
        _builder.append(_printInterface_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("};");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printInterface(final Action action, final String inter) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("interface ");
    _builder.append(inter);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("void ");
    String _simpleName = this.actor.getSimpleName();
    _builder.append(_simpleName, "\t");
    _builder.append("_");
    String _name = action.getName();
    _builder.append(_name, "\t");
    _builder.append(" (");
    String _printIDLParameters = this.printIDLParameters(action);
    _builder.append(_printIDLParameters, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("};");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getActorCContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("Generated by the Orcc C Embedded backend");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("from actor \"");
    String _name = this.actor.getName();
    _builder.append(_name, " ");
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include <stdio.h>");
    _builder.newLine();
    _builder.append("#include <stdlib.h>");
    _builder.newLine();
    _builder.append("#include \"orcc_types.h\"");
    _builder.newLine();
    _builder.newLine();
    {
      final Function1<Procedure, Boolean> _function = new Function1<Procedure, Boolean>() {
        @Override
        public Boolean apply(final Procedure it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      boolean _isEmpty = IterableExtensions.isEmpty(IterableExtensions.<Procedure>filter(this.actor.getProcs(), _function));
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          boolean _isEmpty_1 = this.actor.getStateVars().isEmpty();
          boolean _not_1 = (!_isEmpty_1);
          if (_not_1) {
            _builder.append("////////////////////////////////////////////////////////////////////////////////");
            _builder.newLine();
            _builder.append("// State variables structure");
            _builder.newLine();
            CharSequence _printStateVarsStruct = this.printStateVarsStruct();
            _builder.append(_printStateVarsStruct);
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Functions / procedures");
        _builder.newLine();
        {
          final Function1<Procedure, Boolean> _function_1 = new Function1<Procedure, Boolean>() {
            @Override
            public Boolean apply(final Procedure it) {
              boolean _isNative = it.isNative();
              return Boolean.valueOf((!_isNative));
            }
          };
          Iterable<Procedure> _filter = IterableExtensions.<Procedure>filter(this.actor.getProcs(), _function_1);
          for(final Procedure procedure : _filter) {
            CharSequence _doSwitch = this.doSwitch(procedure.getReturnType());
            _builder.append(_doSwitch);
            _builder.append(" ");
            String _simpleName = this.actor.getSimpleName();
            _builder.append(_simpleName);
            _builder.append("_");
            String _name_1 = procedure.getName();
            _builder.append(_name_1);
            _builder.append("(");
            String _printParameters = this.printParameters(procedure);
            _builder.append(_printParameters);
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
          for(final Procedure procedure_1 : _filter_1) {
            CharSequence _doSwitch_1 = this.doSwitch(procedure_1.getReturnType());
            _builder.append(_doSwitch_1);
            _builder.append(" ");
            String _simpleName_1 = this.actor.getSimpleName();
            _builder.append(_simpleName_1);
            _builder.append("_");
            String _name_2 = procedure_1.getName();
            _builder.append(_name_2);
            _builder.append("(");
            String _printParameters_1 = this.printParameters(procedure_1);
            _builder.append(_printParameters_1);
            _builder.append(") {");
            _builder.newLineIfNotEmpty();
            {
              boolean _isEmpty_2 = procedure_1.getLocals().isEmpty();
              boolean _not_2 = (!_isEmpty_2);
              if (_not_2) {
                {
                  EList<Var> _locals = procedure_1.getLocals();
                  for(final Var local : _locals) {
                    _builder.append("\t");
                    CharSequence _declare = this.declare(local);
                    _builder.append(_declare, "\t");
                    _builder.append(";");
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
                {
                  EList<Var> _stateVars = this.actor.getStateVars();
                  for(final Var stateVar : _stateVars) {
                    _builder.append("\t");
                    CharSequence _doSwitch_2 = this.doSwitch(stateVar.getType());
                    _builder.append(_doSwitch_2, "\t");
                    _builder.append(" ");
                    String _name_3 = stateVar.getName();
                    _builder.append(_name_3, "\t");
                    _builder.append(" = stateVars_o->");
                    String _name_4 = stateVar.getName();
                    _builder.append(_name_4, "\t");
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.newLine();
            {
              EList<Block> _blocks = procedure_1.getBlocks();
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
          }
        }
      }
    }
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Actions");
    _builder.newLine();
    {
      EList<Action> _actions = this.actor.getActions();
      for(final Action action : _actions) {
        CharSequence _print = this.print(action);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_4 = this.actor.getInitializes().isEmpty();
      boolean _not_4 = (!_isEmpty_4);
      if (_not_4) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Initializes ");
        _builder.newLine();
        _builder.newLine();
        {
          EList<Action> _initializes = this.actor.getInitializes();
          for(final Action init : _initializes) {
            CharSequence _print_1 = this.print(init);
            _builder.append(_print_1);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  private CharSequence printStateVarsStruct() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("struct StateVars {");
    _builder.newLine();
    {
      EList<Var> _stateVars = this.actor.getStateVars();
      for(final Var stateVar : _stateVars) {
        _builder.append("\t");
        CharSequence _doSwitch = this.doSwitch(stateVar.getType());
        _builder.append(_doSwitch, "\t");
        _builder.append(" ");
        String _name = stateVar.getName();
        _builder.append(_name, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private String printParameters(final Action action) {
    final Function1<Var, String> _function = new Function1<Var, String>() {
      @Override
      public String apply(final Var it) {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _declare = ActorPrinter.this.declare(it);
        _builder.append(_declare);
        return _builder.toString();
      }
    };
    List<String> _map = ListExtensions.<Var, String>map(this.actor.getParameters(), _function);
    final Function1<Port, String> _function_1 = new Function1<Port, String>() {
      @Override
      public String apply(final Port it) {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _doSwitch = ActorPrinter.this.doSwitch(it.getType());
        _builder.append(_doSwitch);
        _builder.append(" *");
        String _name = it.getName();
        _builder.append(_name);
        return _builder.toString();
      }
    };
    List<String> _map_1 = ListExtensions.<Port, String>map(action.getInputPattern().getPorts(), _function_1);
    Iterable<String> _plus = Iterables.<String>concat(_map, _map_1);
    final Function1<Port, String> _function_2 = new Function1<Port, String>() {
      @Override
      public String apply(final Port it) {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _doSwitch = ActorPrinter.this.doSwitch(it.getType());
        _builder.append(_doSwitch);
        _builder.append(" *");
        String _name = it.getName();
        _builder.append(_name);
        return _builder.toString();
      }
    };
    List<String> _map_2 = ListExtensions.<Port, String>map(action.getOutputPattern().getPorts(), _function_2);
    Iterable<String> elements = Iterables.<String>concat(_plus, _map_2);
    boolean _isEmpty = this.actor.getStateVars().isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList("struct StateVars *stateVars_i", "struct StateVars *stateVars_o");
      Iterable<String> _plus_1 = Iterables.<String>concat(elements, _newArrayList);
      elements = _plus_1;
    }
    final Function1<String, Boolean> _function_3 = new Function1<String, Boolean>() {
      @Override
      public Boolean apply(final String it) {
        boolean _isEmpty = it.isEmpty();
        return Boolean.valueOf((!_isEmpty));
      }
    };
    return IterableExtensions.join(IterableExtensions.<String>filter(elements, _function_3), ", ");
  }
  
  private String printIDLParameters(final Action action) {
    final Function1<Var, String> _function = new Function1<Var, String>() {
      @Override
      public String apply(final Var it) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("in parameter ");
        String _name = it.getName();
        _builder.append(_name);
        return _builder.toString();
      }
    };
    List<String> _map = ListExtensions.<Var, String>map(this.actor.getParameters(), _function);
    final Function1<Port, String> _function_1 = new Function1<Port, String>() {
      @Override
      public String apply(final Port it) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("in ");
        CharSequence _doSwitch = ActorPrinter.this.doSwitch(it.getType());
        _builder.append(_doSwitch);
        _builder.append(" ");
        String _name = it.getName();
        _builder.append(_name);
        return _builder.toString();
      }
    };
    List<String> _map_1 = ListExtensions.<Port, String>map(action.getInputPattern().getPorts(), _function_1);
    Iterable<String> _plus = Iterables.<String>concat(_map, _map_1);
    final Function1<Port, String> _function_2 = new Function1<Port, String>() {
      @Override
      public String apply(final Port it) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("out ");
        CharSequence _doSwitch = ActorPrinter.this.doSwitch(it.getType());
        _builder.append(_doSwitch);
        _builder.append(" ");
        String _name = it.getName();
        _builder.append(_name);
        return _builder.toString();
      }
    };
    List<String> _map_2 = ListExtensions.<Port, String>map(action.getOutputPattern().getPorts(), _function_2);
    Iterable<String> elements = Iterables.<String>concat(_plus, _map_2);
    boolean _isEmpty = this.actor.getStateVars().isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList("in struct StateVars *stateVars_i", "out struct StateVars *stateVars_o");
      Iterable<String> _plus_1 = Iterables.<String>concat(elements, _newArrayList);
      elements = _plus_1;
    }
    final Function1<String, Boolean> _function_3 = new Function1<String, Boolean>() {
      @Override
      public Boolean apply(final String it) {
        boolean _isEmpty = it.isEmpty();
        return Boolean.valueOf((!_isEmpty));
      }
    };
    return IterableExtensions.join(IterableExtensions.<String>filter(elements, _function_3), ", ");
  }
  
  private String printParameters(final Procedure procedure) {
    final Function1<Var, String> _function = new Function1<Var, String>() {
      @Override
      public String apply(final Var it) {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _doSwitch = ActorPrinter.this.doSwitch(it.getType());
        _builder.append(_doSwitch);
        _builder.append(" ");
        String _name = it.getName();
        _builder.append(_name);
        return _builder.toString();
      }
    };
    List<String> _map = ListExtensions.<Var, String>map(this.actor.getParameters(), _function);
    final Function1<Param, String> _function_1 = new Function1<Param, String>() {
      @Override
      public String apply(final Param it) {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _declare = ActorPrinter.this.declare(it.getVariable());
        _builder.append(_declare);
        return _builder.toString();
      }
    };
    List<String> _map_1 = ListExtensions.<Param, String>map(procedure.getParameters(), _function_1);
    Iterable<String> elements = Iterables.<String>concat(_map, _map_1);
    boolean _isEmpty = this.actor.getStateVars().isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList("struct StateVars *stateVars_o");
      Iterable<String> _plus = Iterables.<String>concat(elements, _newArrayList);
      elements = _plus;
    }
    final Function1<String, Boolean> _function_2 = new Function1<String, Boolean>() {
      @Override
      public Boolean apply(final String it) {
        boolean _isEmpty = it.isEmpty();
        return Boolean.valueOf((!_isEmpty));
      }
    };
    return IterableExtensions.join(IterableExtensions.<String>filter(elements, _function_2), ", ");
  }
  
  @Override
  protected CharSequence print(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void ");
    String _simpleName = this.actor.getSimpleName();
    _builder.append(_simpleName);
    _builder.append("_");
    String _name = action.getBody().getName();
    _builder.append(_name);
    _builder.append("(");
    String _printParameters = this.printParameters(action);
    _builder.append(_printParameters);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    {
      boolean _isEmpty = action.getBody().getLocals().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
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
      }
    }
    {
      boolean _isEmpty_1 = this.actor.getStateVars().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("\t");
        _builder.append("// Initialize stateVars and work on them");
        _builder.newLine();
        {
          EList<Var> _stateVars = this.actor.getStateVars();
          for(final Var stateVar : _stateVars) {
            {
              boolean _isEmpty_2 = stateVar.getType().getDimensionsExpr().isEmpty();
              if (_isEmpty_2) {
                _builder.append("\t");
                CharSequence _declare_1 = this.declare(stateVar);
                _builder.append(_declare_1, "\t");
                _builder.append(" = stateVars_i->");
                String _name_1 = stateVar.getName();
                _builder.append(_name_1, "\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("\t");
                CharSequence _doSwitch = this.doSwitch(stateVar.getType());
                _builder.append(_doSwitch, "\t");
                _builder.append(" *");
                String _name_2 = stateVar.getName();
                _builder.append(_name_2, "\t");
                _builder.append(" = stateVars_i->");
                String _name_3 = stateVar.getName();
                _builder.append(_name_3, "\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    {
      EList<Block> _blocks = action.getBody().getBlocks();
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
  public CharSequence caseInstReturn(final InstReturn ret) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.actor.getStateVars().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("// Write state Var to output buf");
        _builder.newLine();
        {
          EList<Var> _stateVars = this.actor.getStateVars();
          for(final Var stateVar : _stateVars) {
            {
              boolean _isEmpty_1 = stateVar.getType().getDimensionsExpr().isEmpty();
              if (_isEmpty_1) {
                _builder.append("stateVars_o->");
                String _name = stateVar.getName();
                _builder.append(_name);
                _builder.append(" = ");
                String _name_1 = stateVar.getName();
                _builder.append(_name_1);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("// Copy ");
                String _name_2 = stateVar.getName();
                _builder.append(_name_2);
                _builder.append(" to output buffer");
                _builder.newLineIfNotEmpty();
                _builder.append("memcpy(stateVars_o->");
                String _name_3 = stateVar.getName();
                _builder.append(_name_3);
                _builder.append(", ");
                String _name_4 = stateVar.getName();
                _builder.append(_name_4);
                _builder.append(", ");
                Integer _head = IterableExtensions.<Integer>head(stateVar.getType().getDimensions());
                _builder.append(_head);
                _builder.append(");");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
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
}
