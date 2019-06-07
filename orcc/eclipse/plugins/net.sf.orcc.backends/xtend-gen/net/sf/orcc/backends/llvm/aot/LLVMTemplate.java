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
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.CommonPrinter;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
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
import net.sf.orcc.ir.util.TypeUtil;
import net.sf.orcc.util.Attribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Default LLVM Printer. Call ExpressionPrinter when necessary and print data types.
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public abstract class LLVMTemplate extends CommonPrinter {
  private Type currentType = null;
  
  protected boolean signed = false;
  
  protected boolean floating = false;
  
  @Override
  public String stringRepresentation(final OpBinary op) {
    String _switchResult = null;
    if (op != null) {
      switch (op) {
        case BITAND:
          _switchResult = "and";
          break;
        case BITOR:
          _switchResult = "or";
          break;
        case BITXOR:
          _switchResult = "xor";
          break;
        case DIV:
          String _xifexpression = null;
          if (this.floating) {
            _xifexpression = "fdiv";
          } else {
            String _xifexpression_1 = null;
            if (this.signed) {
              _xifexpression_1 = "sdiv";
            } else {
              _xifexpression_1 = "udiv";
            }
            _xifexpression = _xifexpression_1;
          }
          _switchResult = _xifexpression;
          break;
        case DIV_INT:
          String _xifexpression_2 = null;
          if (this.floating) {
            _xifexpression_2 = "fdiv";
          } else {
            String _xifexpression_3 = null;
            if (this.signed) {
              _xifexpression_3 = "sdiv";
            } else {
              _xifexpression_3 = "udiv";
            }
            _xifexpression_2 = _xifexpression_3;
          }
          _switchResult = _xifexpression_2;
          break;
        case EQ:
          String _xifexpression_4 = null;
          if (this.floating) {
            _xifexpression_4 = "fcmp oeq";
          } else {
            _xifexpression_4 = "icmp eq";
          }
          _switchResult = _xifexpression_4;
          break;
        case EXP:
          _switchResult = "pow";
          break;
        case GE:
          String _xifexpression_5 = null;
          if (this.floating) {
            _xifexpression_5 = "fcmp oge";
          } else {
            String _xifexpression_6 = null;
            if (this.signed) {
              _xifexpression_6 = "icmp sge";
            } else {
              _xifexpression_6 = "icmp uge";
            }
            _xifexpression_5 = _xifexpression_6;
          }
          _switchResult = _xifexpression_5;
          break;
        case GT:
          String _xifexpression_7 = null;
          if (this.floating) {
            _xifexpression_7 = "fcmp ogt";
          } else {
            String _xifexpression_8 = null;
            if (this.signed) {
              _xifexpression_8 = "icmp sgt";
            } else {
              _xifexpression_8 = "icmp ugt";
            }
            _xifexpression_7 = _xifexpression_8;
          }
          _switchResult = _xifexpression_7;
          break;
        case LOGIC_AND:
          _switchResult = "and";
          break;
        case LE:
          String _xifexpression_9 = null;
          if (this.floating) {
            _xifexpression_9 = "fcmp ole";
          } else {
            String _xifexpression_10 = null;
            if (this.signed) {
              _xifexpression_10 = "icmp sle";
            } else {
              _xifexpression_10 = "icmp ule";
            }
            _xifexpression_9 = _xifexpression_10;
          }
          _switchResult = _xifexpression_9;
          break;
        case LOGIC_OR:
          _switchResult = "or";
          break;
        case LT:
          String _xifexpression_11 = null;
          if (this.floating) {
            _xifexpression_11 = "fcmp olt";
          } else {
            String _xifexpression_12 = null;
            if (this.signed) {
              _xifexpression_12 = "icmp slt";
            } else {
              _xifexpression_12 = "icmp ult";
            }
            _xifexpression_11 = _xifexpression_12;
          }
          _switchResult = _xifexpression_11;
          break;
        case MINUS:
          String _xifexpression_13 = null;
          if (this.floating) {
            _xifexpression_13 = "fsub";
          } else {
            _xifexpression_13 = "sub";
          }
          _switchResult = _xifexpression_13;
          break;
        case MOD:
          String _xifexpression_14 = null;
          if (this.floating) {
            _xifexpression_14 = "frem";
          } else {
            String _xifexpression_15 = null;
            if (this.signed) {
              _xifexpression_15 = "srem";
            } else {
              _xifexpression_15 = "urem";
            }
            _xifexpression_14 = _xifexpression_15;
          }
          _switchResult = _xifexpression_14;
          break;
        case NE:
          String _xifexpression_16 = null;
          if (this.floating) {
            _xifexpression_16 = "fcmp one";
          } else {
            _xifexpression_16 = "icmp ne";
          }
          _switchResult = _xifexpression_16;
          break;
        case PLUS:
          String _xifexpression_17 = null;
          if (this.floating) {
            _xifexpression_17 = "fadd";
          } else {
            _xifexpression_17 = "add";
          }
          _switchResult = _xifexpression_17;
          break;
        case SHIFT_LEFT:
          _switchResult = "shl";
          break;
        case SHIFT_RIGHT:
          String _xifexpression_18 = null;
          if (this.signed) {
            _xifexpression_18 = "ashr";
          } else {
            _xifexpression_18 = "lshr";
          }
          _switchResult = _xifexpression_18;
          break;
        case TIMES:
          String _xifexpression_19 = null;
          if (this.floating) {
            _xifexpression_19 = "fmul";
          } else {
            _xifexpression_19 = "mul";
          }
          _switchResult = _xifexpression_19;
          break;
        default:
          throw new OrccRuntimeException(("Unknown binary operator : " + op));
      }
    } else {
      throw new OrccRuntimeException(("Unknown binary operator : " + op));
    }
    return _switchResult;
  }
  
  @Override
  public CharSequence caseExprBinary(final ExprBinary expr) {
    CharSequence _xblockexpression = null;
    {
      final OpBinary op = expr.getOp();
      final Expression e1 = expr.getE1();
      final Expression e2 = expr.getE2();
      Type type = null;
      boolean _equals = Objects.equal(op, OpBinary.SHIFT_RIGHT);
      if (_equals) {
        type = e1.getType();
      } else {
        type = TypeUtil.getLub(e1.getType(), e2.getType());
      }
      if ((type == null)) {
        Type _type = e1.getType();
        String _plus = ("Type mismatch: cannot evaluate the least upper bound between " + _type);
        String _plus_1 = (_plus + " and ");
        Type _type_1 = e2.getType();
        String _plus_2 = (_plus_1 + _type_1);
        throw new OrccRuntimeException(_plus_2);
      }
      boolean _isUint = type.isUint();
      boolean _not = (!_isUint);
      this.signed = _not;
      this.floating = type.isFloat();
      StringConcatenation _builder = new StringConcatenation();
      String _stringRepresentation = this.stringRepresentation(op);
      _builder.append(_stringRepresentation);
      _builder.append(" ");
      CharSequence _doSwitch = this.doSwitch(e1.getType());
      _builder.append(_doSwitch);
      _builder.append(" ");
      CharSequence _doSwitch_1 = this.doSwitch(e1);
      _builder.append(_doSwitch_1);
      _builder.append(", ");
      CharSequence _doSwitch_2 = this.doSwitch(e2);
      _builder.append(_doSwitch_2);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseExprUnary(final ExprUnary expr) {
    throw new OrccRuntimeException("No unary expression in LLVM");
  }
  
  @Override
  public CharSequence caseExprString(final ExprString expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("c");
    CharSequence _caseExprString = super.caseExprString(expr);
    _builder.append(_caseExprString);
    return _builder;
  }
  
  @Override
  public CharSequence caseExprVar(final ExprVar expr) {
    return this.print(expr.getUse().getVariable());
  }
  
  @Override
  public CharSequence caseExprList(final ExprList exprList) {
    final Type prevType = this.currentType;
    Type _xifexpression = null;
    EObject _eContainer = exprList.eContainer();
    if ((_eContainer instanceof Var)) {
      EObject _eContainer_1 = exprList.eContainer();
      Type _type = ((Var) _eContainer_1).getType();
      _xifexpression = ((TypeList) _type).getType();
    } else {
      _xifexpression = ((TypeList) this.currentType).getType();
    }
    this.currentType = _xifexpression;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("[");
    final Function1<Expression, CharSequence> _function = new Function1<Expression, CharSequence>() {
      @Override
      public CharSequence apply(final Expression it) {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _doSwitch = LLVMTemplate.this.doSwitch(LLVMTemplate.this.currentType);
        _builder.append(_doSwitch);
        _builder.append(" ");
        CharSequence _doSwitch_1 = LLVMTemplate.this.doSwitch(it);
        _builder.append(_doSwitch_1);
        return _builder.toString();
      }
    };
    String _join = IterableExtensions.<Expression>join(exprList.getValue(), ", ", _function);
    _builder.append(_join);
    _builder.append("]");
    final String list = _builder.toString();
    this.currentType = prevType;
    return this.wrap(list);
  }
  
  @Override
  public CharSequence caseExprBool(final ExprBool expr) {
    String _xifexpression = null;
    boolean _isValue = expr.isValue();
    if (_isValue) {
      _xifexpression = "1";
    } else {
      _xifexpression = "0";
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseExprInt(final ExprInt expr) {
    return expr.getValue().toString();
  }
  
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("i1");
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
    _builder.append("i");
    int _size = type.getSize();
    _builder.append(_size);
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeFloat(final TypeFloat type) {
    CharSequence _xifexpression = null;
    int _size = type.getSize();
    boolean _equals = (_size == 16);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("half");
      _xifexpression = _builder;
    } else {
      CharSequence _xifexpression_1 = null;
      int _size_1 = type.getSize();
      boolean _equals_1 = (_size_1 == 64);
      if (_equals_1) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("double");
        _xifexpression_1 = _builder_1;
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("float");
        _xifexpression_1 = _builder_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseTypeString(final TypeString type) {
    CharSequence _xifexpression = null;
    int _size = type.getSize();
    boolean _equals = (_size == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("i8*");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("[");
      int _size_1 = type.getSize();
      _builder_1.append(_size_1);
      _builder_1.append(" x i8]");
      _xifexpression = _builder_1;
    }
    return _xifexpression;
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
    _builder.append("[");
    int _size = typeList.getSize();
    _builder.append(_size);
    _builder.append(" x ");
    CharSequence _doSwitch = this.doSwitch(typeList.getType());
    _builder.append(_doSwitch);
    _builder.append("]");
    return _builder;
  }
  
  /**
   * Helpers
   */
  protected Object getSafeId(final Connection connection, final Port port) {
    Object _elvis = null;
    Attribute _attribute = null;
    if (connection!=null) {
      _attribute=connection.getAttribute("id");
    }
    Object _objectValue = null;
    if (_attribute!=null) {
      _objectValue=_attribute.getObjectValue();
    }
    if (_objectValue != null) {
      _elvis = _objectValue;
    } else {
      String _name = port.getName();
      _elvis = _name;
    }
    return _elvis;
  }
  
  protected CharSequence print(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isGlobal = variable.isGlobal();
      if (_isGlobal) {
        _builder.append("@");
      } else {
        _builder.append("%");
      }
    }
    String _name = variable.getName();
    _builder.append(_name);
    return _builder;
  }
}
