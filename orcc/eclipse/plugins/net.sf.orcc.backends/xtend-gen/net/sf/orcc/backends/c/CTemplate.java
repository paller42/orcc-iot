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
package net.sf.orcc.backends.c;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.CommonPrinter;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.Attributable;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.util.EcoreHelper;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Default C Printer
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public abstract class CTemplate extends CommonPrinter {
  @Override
  public CharSequence caseExprBinary(final ExprBinary expr) {
    CharSequence _xblockexpression = null;
    {
      Type _type = expr.getE1().getType();
      String _plus = ("caseExprBinary: type1: " + _type);
      String _plus_1 = (_plus + "; type2: ");
      Type _type_1 = expr.getE2().getType();
      String _plus_2 = (_plus_1 + _type_1);
      String _plus_3 = (_plus_2 + "; op: ");
      String _stringRepresentation = this.stringRepresentation(expr.getOp());
      String _plus_4 = (_plus_3 + _stringRepresentation);
      OrccLogger.warnln(_plus_4);
      final OpBinary op = expr.getOp();
      CharSequence _xifexpression = null;
      boolean _isString = expr.getType().isString();
      if (_isString) {
        CharSequence _xifexpression_1 = null;
        String _stringRepresentation_1 = this.stringRepresentation(expr.getOp());
        boolean _equals = Objects.equal(_stringRepresentation_1, "+");
        if (_equals) {
          StringConcatenation _builder = new StringConcatenation();
          CharSequence _printStringOperand = this.printStringOperand(expr.getE1(), 0);
          _builder.append(_printStringOperand);
          _builder.newLineIfNotEmpty();
          _builder.append("strcat(temp1,temp2);");
          _builder.newLine();
          CharSequence _printStringOperand_1 = this.printStringOperand(expr.getE2(), 1);
          _builder.append(_printStringOperand_1);
          _builder.newLineIfNotEmpty();
          _builder.append("strcat(temp1,temp2);");
          _builder.newLine();
          _xifexpression_1 = _builder;
        } else {
          throw new OrccRuntimeException("Only + operator is supported on String operands");
        }
        _xifexpression = _xifexpression_1;
      } else {
        CharSequence _xblockexpression_1 = null;
        {
          final Expression container = EcoreHelper.<Expression>getContainerOfType(expr, Expression.class);
          int _xifexpression_2 = (int) 0;
          if ((Objects.equal(op, OpBinary.SHIFT_LEFT) || Objects.equal(op, OpBinary.SHIFT_RIGHT))) {
            _xifexpression_2 = Integer.MIN_VALUE;
          } else {
            _xifexpression_2 = op.getPrecedence();
          }
          int nextPrec = _xifexpression_2;
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _printExpr = this.printExpr(expr.getE1(), nextPrec, 0);
          _builder_1.append(_printExpr);
          _builder_1.append(" ");
          String _stringRepresentation_2 = this.stringRepresentation(op);
          _builder_1.append(_stringRepresentation_2);
          _builder_1.append(" ");
          CharSequence _printExpr_1 = this.printExpr(expr.getE2(), nextPrec, 1);
          _builder_1.append(_printExpr_1);
          final String resultingExpr = _builder_1.toString();
          CharSequence _xifexpression_3 = null;
          if ((op.needsParentheses(this.precedence, this.branch) || ((container != null) && op.isLogical()))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("(");
            _builder_2.append(resultingExpr);
            _builder_2.append(")");
            _xifexpression_3 = _builder_2;
          } else {
            _xifexpression_3 = resultingExpr;
          }
          _xblockexpression_1 = _xifexpression_3;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseExprBool(final ExprBool object) {
    String _xifexpression = null;
    boolean _isValue = object.isValue();
    if (_isValue) {
      _xifexpression = "1";
    } else {
      _xifexpression = "0";
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseExprInt(final ExprInt object) {
    CharSequence _xblockexpression = null;
    {
      final long longVal = object.getValue().longValue();
      CharSequence _xifexpression = null;
      if (((longVal < Integer.MIN_VALUE) || (longVal > Integer.MAX_VALUE))) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(longVal);
        _builder.append("L");
        _xifexpression = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(longVal);
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  @Override
  protected String stringRepresentation(final OpBinary op) {
    String _xifexpression = null;
    boolean _equals = Objects.equal(op, OpBinary.DIV_INT);
    if (_equals) {
      _xifexpression = "/";
    } else {
      _xifexpression = super.stringRepresentation(op);
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("i32");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeInt(final TypeInt type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("i");
    int _size = type.getSize();
    _builder.append(_size);
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeUint(final TypeUint type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("u");
    int _size = type.getSize();
    _builder.append(_size);
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeFloat(final TypeFloat type) {
    CharSequence _xifexpression = null;
    int _size = type.getSize();
    boolean _equals = (_size == 64);
    if (_equals) {
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
  
  @Override
  public CharSequence caseTypeString(final TypeString type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("string");
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
    return _builder;
  }
  
  protected CharSequence declare(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(variable.getType());
    _builder.append(_doSwitch);
    _builder.append(" ");
    String _name = variable.getName();
    _builder.append(_name);
    String _printArrayIndexes = this.printArrayIndexes(variable.getType().getDimensionsExpr());
    _builder.append(_printArrayIndexes);
    return _builder;
  }
  
  /**
   * Print for a type, the corresponding formatted text to
   * use inside a printf() call.
   * @param type the type to print
   * @return printf() type format
   */
  protected String printfFormat(final Type type) {
    String _switchResult = null;
    boolean _matched = false;
    boolean _isBool = type.isBool();
    if (_isBool) {
      _matched=true;
      _switchResult = "i";
    }
    if (!_matched) {
      boolean _isFloat = type.isFloat();
      if (_isFloat) {
        _matched=true;
        _switchResult = "f";
      }
    }
    if (!_matched) {
      if ((type.isInt() && ((TypeInt) type).isLong())) {
        _matched=true;
        _switchResult = "lli";
      }
    }
    if (!_matched) {
      boolean _isInt = type.isInt();
      if (_isInt) {
        _matched=true;
        _switchResult = "i";
      }
    }
    if (!_matched) {
      if ((type.isUint() && ((TypeUint) type).isLong())) {
        _matched=true;
        _switchResult = "llu";
      }
    }
    if (!_matched) {
      boolean _isUint = type.isUint();
      if (_isUint) {
        _matched=true;
        _switchResult = "u";
      }
    }
    if (!_matched) {
      boolean _isList = type.isList();
      if (_isList) {
        _matched=true;
        _switchResult = "p";
      }
    }
    if (!_matched) {
      boolean _isString = type.isString();
      if (_isString) {
        _matched=true;
        _switchResult = "s";
      }
    }
    if (!_matched) {
      boolean _isVoid = type.isVoid();
      if (_isVoid) {
        _matched=true;
        _switchResult = "p";
      }
    }
    return _switchResult;
  }
  
  protected LinkedList<CharSequence> printfArgs(final List<Arg> args) {
    final LinkedList<CharSequence> finalArgs = new LinkedList<CharSequence>();
    final StringBuilder printfPattern = new StringBuilder();
    printfPattern.append("\"");
    for (final Arg arg : args) {
      boolean _isByRef = arg.isByRef();
      if (_isByRef) {
        String _printfFormat = this.printfFormat(((ArgByRef) arg).getUse().getVariable().getType());
        String _plus = ("%" + _printfFormat);
        printfPattern.append(_plus);
        finalArgs.add(((ArgByRef) arg).getUse().getVariable().getName());
      } else {
        boolean _isExprString = ((ArgByVal) arg).getValue().isExprString();
        if (_isExprString) {
          Expression _value = ((ArgByVal) arg).getValue();
          printfPattern.append(((ExprString) _value).getValue());
        } else {
          String _printfFormat_1 = this.printfFormat(((ArgByVal) arg).getValue().getType());
          String _plus_1 = ("%" + _printfFormat_1);
          printfPattern.append(_plus_1);
          finalArgs.add(this.doSwitch(((ArgByVal) arg).getValue()));
        }
      }
    }
    printfPattern.append("\"");
    finalArgs.addFirst(printfPattern.toString());
    return finalArgs;
  }
  
  /**
   * Print attributes for an Attributable object.
   * Do nothing on C backend, but is used by others.
   * @param object the object
   * @return comment block
   */
  protected CharSequence printAttributes(final Attributable object) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  /**
   * This helper return a representation of a given instruction without
   * trailing whitespace and semicolon
   */
  protected String toExpression(final Instruction instruction) {
    return this.doSwitch(instruction).toString().replaceAll("([^;]+);(\\s+)?", "$1");
  }
  
  protected CharSequence printNativeLibHeaders(final String linkNativeLibHeaders) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// -- Native lib headers");
    _builder.newLine();
    {
      String[] _split = linkNativeLibHeaders.split(";");
      for(final String header : _split) {
        _builder.append("#include \"");
        String _trim = header.trim();
        _builder.append(_trim);
        _builder.append("\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printStringOperand(final Expression expr, final int pos) {
    CharSequence _xifexpression = null;
    boolean _isString = expr.getType().isString();
    if (_isString) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("strcpy(temp2,");
      CharSequence _printExpr = this.printExpr(expr, Integer.MIN_VALUE, pos);
      _builder.append(_printExpr);
      _builder.append(");");
      _xifexpression = _builder;
    } else {
      CharSequence _xifexpression_1 = null;
      boolean _isInt = expr.getType().isInt();
      if (_isInt) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("sprintf(temp2,\"%d\",");
        CharSequence _printExpr_1 = this.printExpr(expr, Integer.MIN_VALUE, pos);
        _builder_1.append(_printExpr_1);
        _builder_1.append(");");
        _xifexpression_1 = _builder_1;
      } else {
        CharSequence _xifexpression_2 = null;
        boolean _isFloat = expr.getType().isFloat();
        if (_isFloat) {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("sprintf(temp2,\"%f\",");
          CharSequence _printExpr_2 = this.printExpr(expr, Integer.MIN_VALUE, pos);
          _builder_2.append(_printExpr_2);
          _builder_2.append(");");
          _xifexpression_2 = _builder_2;
        } else {
          CharSequence _xifexpression_3 = null;
          boolean _isUint = expr.getType().isUint();
          if (_isUint) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("sprintf(temp2,\"%u\",");
            CharSequence _printExpr_3 = this.printExpr(expr, Integer.MIN_VALUE, pos);
            _builder_3.append(_printExpr_3);
            _builder_3.append(");");
            _xifexpression_3 = _builder_3;
          } else {
            Type _type = expr.getType();
            String _plus = ("Unsupported type for string concatenation: " + _type);
            throw new OrccRuntimeException(_plus);
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
}
