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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.sf.orcc.backends.CommonPrinter;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.util.EcoreHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Default Java Printer
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public abstract class JavaTemplate extends CommonPrinter {
  /**
   * Types
   */
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    CharSequence _xifexpression = null;
    boolean _isFifoType = this.isFifoType(type);
    if (_isFifoType) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Boolean");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("boolean");
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseTypeInt(final TypeInt type) {
    String _xifexpression = null;
    boolean _isFifoType = this.isFifoType(type);
    if (_isFifoType) {
      _xifexpression = this.printFifoInt(type.getSize());
    } else {
      _xifexpression = this.printInt(type.getSize());
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseTypeUint(final TypeUint type) {
    String _xifexpression = null;
    boolean _isFifoType = this.isFifoType(type);
    if (_isFifoType) {
      _xifexpression = this.printFifoInt(type.getSize());
    } else {
      _xifexpression = this.printInt(type.getSize());
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseTypeFloat(final TypeFloat type) {
    CharSequence _xifexpression = null;
    boolean _isFifoType = this.isFifoType(type);
    if (_isFifoType) {
      _xifexpression = this.printFifoFloat(type.getSize());
    } else {
      _xifexpression = this.printFloat(type.getSize());
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseTypeString(final TypeString type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("String");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeVoid(final TypeVoid type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeList(final TypeList typeList) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(typeList.getInnermostType());
    _builder.append(_doSwitch);
    {
      int _size = typeList.getDimensions().size();
      IntegerRange _upTo = new IntegerRange(1, _size);
      for(final Integer i : _upTo) {
        _builder.append("[]");
      }
    }
    return _builder;
  }
  
  private CharSequence printFloat(final int size) {
    CharSequence _xifexpression = null;
    if ((size == 64)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("double");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("float");
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  private CharSequence printFifoFloat(final int size) {
    CharSequence _xifexpression = null;
    if ((size == 64)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Double");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("Float");
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  private String printInt(final int size) {
    if ((size <= 32)) {
      return "int";
    } else {
      if ((size <= 64)) {
        return "long";
      } else {
        return null;
      }
    }
  }
  
  private String printFifoInt(final int size) {
    if ((size <= 32)) {
      return "Integer";
    } else {
      if ((size <= 64)) {
        return "Long";
      } else {
        return null;
      }
    }
  }
  
  public boolean needToWriteFile(final CharSequence s, final File file) {
    return true;
  }
  
  /**
   * Return true if this type object is used in a Fifo
   */
  public boolean isFifoType(final Type type) {
    Port _containerOfType = EcoreHelper.<Port>getContainerOfType(type, Port.class);
    return (_containerOfType != null);
  }
  
  public CharSequence printArguments(final EList<Var> actorParams, final EList<Argument> arguments) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final Var paramVar : actorParams) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        final Function1<Argument, Boolean> _function = new Function1<Argument, Boolean>() {
          @Override
          public Boolean apply(final Argument arg) {
            Var _variable = arg.getVariable();
            return Boolean.valueOf(Objects.equal(_variable, paramVar));
          }
        };
        CharSequence _doSwitch = this.doSwitch(IterableExtensions.<Argument>findFirst(arguments, _function).getValue());
        _builder.append(_doSwitch);
      }
    }
    return _builder;
  }
  
  /**
   * Print procedure parameter, when calling it. This helper may not
   * be used with "print" calls
   */
  public String printParameters(final InstCall call) {
    String _xifexpression = null;
    boolean _isEmpty = call.getArguments().isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      String _xblockexpression = null;
      {
        List<CharSequence> finalList = new ArrayList<CharSequence>();
        int _size = call.getArguments().size();
        int _minus = (_size - 1);
        IntegerRange _upTo = new IntegerRange(0, _minus);
        for (final Integer i : _upTo) {
          {
            final Type procParamType = call.getProcedure().getParameters().get((i).intValue()).getVariable().getType();
            Type _xifexpression_1 = null;
            boolean _isByRef = call.getArguments().get((i).intValue()).isByRef();
            if (_isByRef) {
              Arg _get = call.getArguments().get((i).intValue());
              _xifexpression_1 = ((ArgByRef) _get).getUse().getVariable().getType();
            } else {
              Arg _get_1 = call.getArguments().get((i).intValue());
              _xifexpression_1 = ((ArgByVal) _get_1).getValue().getType();
            }
            final Type callArgType = _xifexpression_1;
            boolean _isCastNeeded = this.isCastNeeded(procParamType, callArgType);
            if (_isCastNeeded) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("(");
              CharSequence _doSwitch = this.doSwitch(procParamType);
              _builder.append(_doSwitch);
              _builder.append(") (");
              CharSequence _printParameter = this.printParameter(call.getArguments().get((i).intValue()));
              _builder.append(_printParameter);
              _builder.append(")");
              finalList.add(_builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              CharSequence _printParameter_1 = this.printParameter(call.getArguments().get((i).intValue()));
              _builder_1.append(_printParameter_1);
              finalList.add(_builder_1);
            }
          }
        }
        _xblockexpression = IterableExtensions.join(finalList, ", ");
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  /**
   * Print an argument of a call statement
   */
  public CharSequence printParameter(final Arg arg) {
    boolean _isByVal = arg.isByVal();
    if (_isByVal) {
      boolean _isExprBinary = ((ArgByVal) arg).getValue().isExprBinary();
      if (_isExprBinary) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("(");
        CharSequence _doSwitch = this.doSwitch(((ArgByVal) arg).getValue());
        _builder.append(_doSwitch);
        _builder.append(")");
        return _builder.toString();
      } else {
        return this.doSwitch(((ArgByVal) arg).getValue());
      }
    } else {
      return ((ArgByRef) arg).getUse().getVariable().getName();
    }
  }
  
  /**
   * Return true if size of type "to" is less than "from's" one
   */
  public boolean isCastNeeded(final Type to, final Type from) {
    if ((to.isFloat() && (!((TypeFloat) to).isDouble()))) {
      return true;
    }
    int _xifexpression = (int) 0;
    boolean _isList = to.isList();
    if (_isList) {
      _xifexpression = ((TypeList) to).getInnermostType().getSizeInBits();
    } else {
      _xifexpression = to.getSizeInBits();
    }
    final int sizeTo = _xifexpression;
    int _xifexpression_1 = (int) 0;
    boolean _isList_1 = from.isList();
    if (_isList_1) {
      _xifexpression_1 = ((TypeList) from).getInnermostType().getSizeInBits();
    } else {
      _xifexpression_1 = from.getSizeInBits();
    }
    final int sizeFrom = _xifexpression_1;
    return (sizeTo < sizeFrom);
  }
  
  /**
   * Print a variable declaration, with its modifiers (final, public/private),
   * its type and its initial value (if any)
   */
  public CharSequence declareVariable(final Var variable) {
    CharSequence _xblockexpression = null;
    {
      String modifier = "";
      boolean _isLocal = variable.isLocal();
      boolean _not = (!_isLocal);
      if (_not) {
        String _xifexpression = null;
        boolean _isGlobal = variable.isGlobal();
        if (_isGlobal) {
          _xifexpression = "public ";
        } else {
          _xifexpression = "private ";
        }
        modifier = _xifexpression;
      }
      String _xifexpression_1 = null;
      boolean _isInitialized = variable.isInitialized();
      if (_isInitialized) {
        String _xifexpression_2 = null;
        if ((variable.getType().isFloat() && (!((TypeFloat) variable.getType()).isDouble()))) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(" ");
          _builder.append("= (float)( ");
          CharSequence _doSwitch = this.doSwitch(variable.getInitialValue());
          _builder.append(_doSwitch, " ");
          _builder.append(" )");
          _xifexpression_2 = _builder.toString();
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(" ");
          _builder_1.append("= ");
          CharSequence _doSwitch_1 = this.doSwitch(variable.getInitialValue());
          _builder_1.append(_doSwitch_1, " ");
          _xifexpression_2 = _builder_1.toString();
        }
        _xifexpression_1 = _xifexpression_2;
      } else {
        String _xifexpression_3 = null;
        Object _value = variable.getValue();
        boolean _tripleNotEquals = (_value != null);
        if (_tripleNotEquals) {
          String _xifexpression_4 = null;
          Object _value_1 = variable.getValue();
          if ((_value_1 instanceof Expression)) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append(" ");
            _builder_2.append("= ");
            Object _value_2 = variable.getValue();
            CharSequence _doSwitch_2 = this.doSwitch(((Expression) _value_2));
            _builder_2.append(_doSwitch_2, " ");
            _xifexpression_4 = _builder_2.toString();
          } else {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append(" ");
            _builder_3.append("= ");
            Object _value_3 = variable.getValue();
            _builder_3.append(_value_3, " ");
            _xifexpression_4 = _builder_3.toString();
          }
          _xifexpression_3 = _xifexpression_4;
        } else {
          String _xifexpression_5 = null;
          boolean _isList = variable.getType().isList();
          if (_isList) {
            String _xblockexpression_1 = null;
            {
              Type _type = variable.getType();
              final TypeList type = ((TypeList) _type);
              StringConcatenation _builder_4 = new StringConcatenation();
              _builder_4.append(" ");
              _builder_4.append("= new ");
              CharSequence _doSwitch_3 = this.doSwitch(type.getInnermostType());
              _builder_4.append(_doSwitch_3, " ");
              String _printArrayIndexes = this.printArrayIndexes(type.getDimensionsExpr());
              _builder_4.append(_printArrayIndexes, " ");
              _xblockexpression_1 = _builder_4.toString();
            }
            _xifexpression_5 = _xblockexpression_1;
          }
          _xifexpression_3 = _xifexpression_5;
        }
        _xifexpression_1 = _xifexpression_3;
      }
      final String initialization = _xifexpression_1;
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append(modifier);
      CharSequence _doSwitch_3 = this.doSwitch(variable.getType());
      _builder_4.append(_doSwitch_3);
      _builder_4.append(" ");
      String _name = variable.getName();
      _builder_4.append(_name);
      _builder_4.append(initialization);
      _builder_4.append(";");
      _builder_4.newLineIfNotEmpty();
      _xblockexpression = _builder_4;
    }
    return _xblockexpression;
  }
}
