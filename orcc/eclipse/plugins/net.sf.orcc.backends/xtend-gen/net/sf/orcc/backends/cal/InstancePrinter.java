/**
 * Copyright (c) 2016, Heriot-Watt University Edinburgh
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
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF YUSE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.cal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.cal.CALTemplate;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Transition;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
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
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * Generate and print instance source files for the CAL actors,
 * primarily to serve the dataflow transformations in
 * net.sf.orcc.df.transform driven by the Graphiti interface.
 * 
 * @author Rob Stewart
 */
@SuppressWarnings("all")
public class InstancePrinter extends CALTemplate {
  protected String packageDir;
  
  protected String actorName;
  
  protected Instance instance;
  
  protected Actor actor;
  
  protected Attributable attributable;
  
  protected Map<Port, Connection> incomingPortMap;
  
  protected Map<Port, List<Connection>> outgoingPortMap;
  
  protected String entityName;
  
  public CharSequence getFileContent() {
    CharSequence _xblockexpression = null;
    {
      int numInputs = this.actor.getInputs().size();
      int numOutputs = this.actor.getOutputs().size();
      String _replaceAll = this.actor.getPackage().replaceAll("\\.", "/");
      String _plus = (_replaceAll + "/");
      this.packageDir = _plus;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package ");
      String _package = this.actor.getPackage();
      _builder.append(_package);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("actor ");
      _builder.append(this.actorName);
      _builder.append(" ()");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      {
        if ((numInputs > 0)) {
          _builder.append("\t");
          String _printType = this.printType(this.actor.getInputs().get(0).getType());
          _builder.append(_printType, "\t");
          _builder.append(" ");
          String _name = this.actor.getInputs().get(0).getName();
          _builder.append(_name, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if ((numInputs > 1)) {
          {
            List<Port> _subList = this.actor.getInputs().subList(1, numInputs);
            for(final Port input : _subList) {
              _builder.append("\t");
              _builder.append(", ");
              String _printType_1 = this.printType(input.getType());
              _builder.append(_printType_1, "\t");
              _builder.append(" ");
              String _name_1 = input.getName();
              _builder.append(_name_1, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("    ");
      _builder.append("==>");
      _builder.newLine();
      {
        if ((numOutputs > 0)) {
          _builder.append("    ");
          String _printType_2 = this.printType(this.actor.getOutputs().get(0).getType());
          _builder.append(_printType_2, "    ");
          _builder.append("  ");
          String _name_2 = this.actor.getOutputs().get(0).getName();
          _builder.append(_name_2, "    ");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if ((numOutputs > 1)) {
          {
            List<Port> _subList_1 = this.actor.getOutputs().subList(1, numOutputs);
            for(final Port output : _subList_1) {
              _builder.append("    ");
              _builder.append(", ");
              String _printType_3 = this.printType(output.getType());
              _builder.append(_printType_3, "    ");
              _builder.append("  ");
              String _name_3 = output.getName();
              _builder.append(_name_3, "    ");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("  ");
      _builder.append(":");
      _builder.newLine();
      {
        EList<Var> _stateVars = this.actor.getStateVars();
        for(final Var theVar : _stateVars) {
          _builder.append("  ");
          String _printType_4 = this.printType(theVar.getType());
          _builder.append(_printType_4, "  ");
          _builder.append(" ");
          String _name_4 = theVar.getName();
          _builder.append(_name_4, "  ");
          _builder.append(" := ");
          CharSequence _printExpression = this.printExpression(theVar.getInitialValue());
          _builder.append(_printExpression, "  ");
          _builder.append(";\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      {
        EList<Action> _actions = this.actor.getActions();
        for(final Action action : _actions) {
          _builder.append("  ");
          CharSequence _printAction = this.printAction(action);
          _builder.append(_printAction, "  ");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("  ");
      _builder.newLine();
      {
        FSM _fsm = this.actor.getFsm();
        boolean _tripleNotEquals = (_fsm != null);
        if (_tripleNotEquals) {
          _builder.append("  ");
          _builder.append("schedule fsm ");
          String _name_5 = this.actor.getFsm().getInitialState().getName();
          _builder.append(_name_5, "  ");
          _builder.append(" :");
          _builder.newLineIfNotEmpty();
          {
            EList<Transition> _transitions = this.actor.getFsm().getTransitions();
            for(final Transition trans : _transitions) {
              _builder.append("  ");
              String _name_6 = trans.getSource().getName();
              _builder.append(_name_6, "  ");
              _builder.append(" (");
              String _name_7 = trans.getAction().getName();
              _builder.append(_name_7, "  ");
              _builder.append(") --> ");
              String _name_8 = trans.getTarget().getName();
              _builder.append(_name_8, "  ");
              _builder.append("; ");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("  ");
      _builder.append("end");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printExpression(final Expression exp) {
    CharSequence _xblockexpression = null;
    {
      String _switchResult = null;
      boolean _matched = false;
      if (exp instanceof ExprBinary) {
        _matched=true;
        _switchResult = this.printBinaryExpr(((ExprBinary)exp)).toString();
      }
      if (!_matched) {
        if (exp instanceof ExprBool) {
          _matched=true;
          String _xifexpression = null;
          boolean _isValue = ((ExprBool)exp).isValue();
          if (_isValue) {
            _xifexpression = "true";
          } else {
            _xifexpression = "false";
          }
          _switchResult = _xifexpression;
        }
      }
      if (!_matched) {
        if (exp instanceof ExprFloat) {
          _matched=true;
          _switchResult = ((ExprFloat)exp).getValue().toString();
        }
      }
      if (!_matched) {
        if (exp instanceof ExprInt) {
          _matched=true;
          _switchResult = Integer.toString(((ExprInt)exp).getIntValue());
        }
      }
      if (!_matched) {
        if (exp instanceof ExprList) {
          _matched=true;
          _switchResult = this.printList(((ExprList)exp));
        }
      }
      if (!_matched) {
        if (exp instanceof ExprString) {
          _matched=true;
          String _value = ((ExprString)exp).getValue();
          String _plus = ("\"" + _value);
          _switchResult = (_plus + "\"");
        }
      }
      if (!_matched) {
        if (exp instanceof ExprUnary) {
          _matched=true;
          _switchResult = this.printUnaryOp(((ExprUnary)exp).getOp()).toString();
        }
      }
      if (!_matched) {
        if (exp instanceof ExprVar) {
          _matched=true;
          _switchResult = this.printLocalVarExpr(((ExprVar)exp));
        }
      }
      final String expPrinted = _switchResult;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(expPrinted);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected String printList(final ExprList list) {
    String _xblockexpression = null;
    {
      List<String> elems = new ArrayList<String>();
      EList<Expression> _value = list.getValue();
      for (final Expression exp : _value) {
        elems.add(this.printExpression(exp).toString());
      }
      _xblockexpression = this.delimitWith(elems, ",");
    }
    return _xblockexpression;
  }
  
  protected CharSequence printBinaryExpr(final ExprBinary exp) {
    CharSequence _xblockexpression = null;
    {
      final Expression e1 = exp.getE1();
      String _xifexpression = null;
      boolean _isExprBinary = e1.isExprBinary();
      if (_isExprBinary) {
        String _string = this.printExpression(e1).toString();
        String _plus = ("(" + _string);
        _xifexpression = (_plus + ")");
      } else {
        _xifexpression = this.printExpression(e1).toString();
      }
      final String e1WellFormed = _xifexpression;
      final Expression e2 = exp.getE2();
      String _xifexpression_1 = null;
      boolean _isExprBinary_1 = e2.isExprBinary();
      if (_isExprBinary_1) {
        String _string_1 = this.printExpression(e2).toString();
        String _plus_1 = ("(" + _string_1);
        _xifexpression_1 = (_plus_1 + ")");
      } else {
        _xifexpression_1 = this.printExpression(e2).toString();
      }
      final String e2WellFormed = _xifexpression_1;
      CharSequence _printBinaryOp = this.printBinaryOp(exp.getOp());
      String _plus_2 = ((e1WellFormed + " ") + _printBinaryOp);
      String _plus_3 = (_plus_2 + " ");
      final String expWellFormed = (_plus_3 + e2WellFormed);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(expWellFormed);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public String setInstance(final Instance instance) {
    String _xblockexpression = null;
    {
      boolean _isActor = instance.isActor();
      boolean _not = (!_isActor);
      if (_not) {
        throw new OrccRuntimeException((("Instance " + this.entityName) + " is not an Actor\'s instance"));
      }
      this.instance = instance;
      this.entityName = instance.getName();
      this.actor = instance.getActor();
      this.attributable = instance;
      this.incomingPortMap = instance.getIncomingPortMap();
      this.outgoingPortMap = instance.getOutgoingPortMap();
      _xblockexpression = this.actorName = this.takeAfterLast(this.actor.getName(), "\\.");
    }
    return _xblockexpression;
  }
  
  public String setActor(final Actor actor) {
    String _xblockexpression = null;
    {
      this.entityName = actor.getName();
      this.actor = actor;
      this.attributable = actor;
      this.incomingPortMap = actor.getIncomingPortMap();
      this.outgoingPortMap = actor.getOutgoingPortMap();
      _xblockexpression = this.actorName = this.takeAfterLast(this.actor.getName(), "\\.");
    }
    return _xblockexpression;
  }
  
  protected CharSequence printAction(final Action action) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _startsWith = action.getName().startsWith("untagged");
      if (_startsWith) {
        _xifexpression = "";
      } else {
        String _name = action.getName();
        _xifexpression = (_name + ":");
      }
      final String actionNameTag = _xifexpression;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(actionNameTag);
      _builder.append(" action");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      CharSequence _printInputPattern = this.printInputPattern(action);
      _builder.append(_printInputPattern, "  ");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("==>");
      _builder.newLine();
      _builder.append("  ");
      CharSequence _printOutputPattern = this.printOutputPattern(action);
      _builder.append(_printOutputPattern, "  ");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      CharSequence _printGuard = this.printGuard(action);
      _builder.append(_printGuard, "  ");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      CharSequence _printLocalVars = this.printLocalVars(action);
      _builder.append(_printLocalVars, "  ");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("do");
      _builder.newLine();
      _builder.append("  ");
      CharSequence _printActionBody = this.printActionBody(action);
      _builder.append(_printActionBody, "  ");
      _builder.newLineIfNotEmpty();
      _builder.append(" ");
      _builder.append("end");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printLocalVars(final Action action) {
    CharSequence _xblockexpression = null;
    {
      final EList<Var> locals = action.getBody().getLocals();
      List<String> varDecls = new ArrayList<String>();
      List<String> inPatternVars = this.inputPatternVars(action);
      for (final Var localVar : locals) {
        boolean _contains = inPatternVars.contains(localVar.getName());
        boolean _not = (!_contains);
        if (_not) {
          String _printType = this.printType(localVar.getType());
          String _plus = (_printType + " ");
          String _formatVarName = this.formatVarName(localVar.getName());
          String _plus_1 = (_plus + _formatVarName);
          varDecls.add(_plus_1);
        }
      }
      CharSequence _xifexpression = null;
      int _size = varDecls.size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("var");
        _builder.newLine();
        String _delimitWith = this.delimitWith(varDecls, ",");
        _builder.append(_delimitWith);
        _builder.newLineIfNotEmpty();
        _xifexpression = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected String printType(final Type theType) {
    String _switchResult = null;
    boolean _matched = false;
    if (theType instanceof TypeBool) {
      _matched=true;
      _switchResult = "bool";
    }
    if (!_matched) {
      if (theType instanceof TypeFloat) {
        _matched=true;
        _switchResult = "float";
      }
    }
    if (!_matched) {
      if (theType instanceof TypeInt) {
        _matched=true;
        int _size = ((TypeInt)theType).getSize();
        String _plus = ("int(size=" + Integer.valueOf(_size));
        _switchResult = (_plus + ")");
      }
    }
    if (!_matched) {
      if (theType instanceof TypeList) {
        _matched=true;
        _switchResult = "";
      }
    }
    if (!_matched) {
      if (theType instanceof TypeString) {
        _matched=true;
        _switchResult = "string";
      }
    }
    if (!_matched) {
      if (theType instanceof TypeUint) {
        _matched=true;
        int _size = ((TypeUint)theType).getSize();
        String _plus = ("uint(size=" + Integer.valueOf(_size));
        _switchResult = (_plus + ")");
      }
    }
    if (!_matched) {
      if (theType instanceof TypeVoid) {
        _matched=true;
        _switchResult = "void";
      }
    }
    if (!_matched) {
      _switchResult = "int(size=8)";
    }
    return _switchResult;
  }
  
  protected CharSequence printActionBody(final Action action) {
    CharSequence _xblockexpression = null;
    {
      List<String> printedBlocks = new ArrayList<String>();
      final EList<Block> blocks = action.getBody().getBlocks();
      for (final Block block : blocks) {
        {
          final String printedBlock = this.printBlock(block).toString();
          printedBlocks.add(printedBlock);
        }
      }
      StringConcatenation _builder = new StringConcatenation();
      {
        for(final String block_1 : printedBlocks) {
          _builder.append(block_1);
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printBlock(final Block block) {
    String _switchResult = null;
    boolean _matched = false;
    if (block instanceof BlockIf) {
      _matched=true;
      _switchResult = this.printBlockIf(((BlockIf)block)).toString();
    }
    if (!_matched) {
      if (block instanceof BlockWhile) {
        _matched=true;
        _switchResult = this.printBlockWhile(((BlockWhile)block)).toString();
      }
    }
    if (!_matched) {
      if (block instanceof BlockBasic) {
        _matched=true;
        _switchResult = this.printBlockBasic(((BlockBasic)block)).toString();
      }
    }
    return _switchResult;
  }
  
  protected CharSequence printBlockIf(final BlockIf blockIf) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    CharSequence _printExpression = this.printExpression(blockIf.getCondition());
    _builder.append(_printExpression);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("then");
    _builder.newLine();
    {
      EList<Block> _thenBlocks = blockIf.getThenBlocks();
      for(final Block block : _thenBlocks) {
        CharSequence _printBlock = this.printBlock(block);
        _builder.append(_printBlock);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("else");
    _builder.newLine();
    {
      EList<Block> _elseBlocks = blockIf.getElseBlocks();
      for(final Block block_1 : _elseBlocks) {
        CharSequence _printBlock_1 = this.printBlock(block_1);
        _builder.append(_printBlock_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printBlockWhile(final BlockWhile blockWhile) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("while (");
    CharSequence _printExpression = this.printExpression(blockWhile.getCondition());
    _builder.append(_printExpression);
    _builder.append(") do");
    _builder.newLineIfNotEmpty();
    {
      EList<Block> _blocks = blockWhile.getBlocks();
      for(final Block block : _blocks) {
        CharSequence _printBlock = this.printBlock(block);
        _builder.append(_printBlock);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printBlockBasic(final BlockBasic blockBasic) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Instruction> _instructions = blockBasic.getInstructions();
      for(final Instruction inst : _instructions) {
        CharSequence _printInstruction = this.printInstruction(inst);
        _builder.append(_printInstruction);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence printGuard(final Action action) {
    CharSequence _xblockexpression = null;
    {
      String guard = "";
      BlockBasic block = action.getScheduler().getFirst();
      EList<Instruction> _instructions = block.getInstructions();
      for (final Instruction inst : _instructions) {
        if ((inst instanceof InstAssign)) {
          final InstAssign instAssign = ((InstAssign) inst);
          boolean _isExprBinary = instAssign.getValue().isExprBinary();
          if (_isExprBinary) {
            Expression _value = instAssign.getValue();
            final ExprBinary guardBinary = ((ExprBinary) _value);
            CharSequence _printExpression = this.printExpression(guardBinary);
            String _plus = ("guard " + _printExpression);
            guard = _plus;
          }
        }
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(guard);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printBinaryOp(final OpBinary op) {
    CharSequence _xblockexpression = null;
    {
      String _switchResult = null;
      if (op != null) {
        switch (op) {
          case BITAND:
            _switchResult = "&";
            break;
          case BITOR:
            _switchResult = "|";
            break;
          case BITXOR:
            _switchResult = "^";
            break;
          case DIV:
            _switchResult = "/";
            break;
          case DIV_INT:
            _switchResult = "/";
            break;
          case EQ:
            _switchResult = "=";
            break;
          case GE:
            _switchResult = ">=";
            break;
          case GT:
            _switchResult = ">";
            break;
          case LE:
            _switchResult = "<";
            break;
          case LOGIC_AND:
            _switchResult = "&&";
            break;
          case LOGIC_OR:
            _switchResult = "||";
            break;
          case LT:
            _switchResult = "<";
            break;
          case MINUS:
            _switchResult = "-";
            break;
          case MOD:
            _switchResult = "mod";
            break;
          case NE:
            _switchResult = "!=";
            break;
          case PLUS:
            _switchResult = "+";
            break;
          case SHIFT_LEFT:
            _switchResult = "<<";
            break;
          case SHIFT_RIGHT:
            _switchResult = ">>";
            break;
          case TIMES:
            _switchResult = "*";
            break;
          default:
            _switchResult = "";
            break;
        }
      } else {
        _switchResult = "";
      }
      final String opPrinted = _switchResult;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(opPrinted);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printUnaryOp(final OpUnary op) {
    CharSequence _xblockexpression = null;
    {
      String _switchResult = null;
      if (op != null) {
        switch (op) {
          case BITNOT:
            _switchResult = "not";
            break;
          case LOGIC_NOT:
            _switchResult = "not";
            break;
          case MINUS:
            _switchResult = "-";
            break;
          case NUM_ELTS:
            _switchResult = "";
            break;
          default:
            _switchResult = "";
            break;
        }
      } else {
        _switchResult = "";
      }
      final String opPrinted = _switchResult;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(opPrinted);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected String printLocalVarExpr(final ExprVar exp) {
    return exp.getUse().getVariable().getName().replace("local_", "");
  }
  
  protected String formatVarName(final String s) {
    return s.replace("local_", "");
  }
  
  protected CharSequence printInstruction(final Instruction inst) {
    CharSequence _xblockexpression = null;
    {
      String _switchResult = null;
      boolean _matched = false;
      if (inst instanceof InstAssign) {
        _matched=true;
        String _name = ((InstAssign)inst).getTarget().getVariable().getName();
        String _plus = (_name + " := ");
        CharSequence _printExpression = this.printExpression(((InstAssign)inst).getValue());
        String _plus_1 = (_plus + _printExpression);
        _switchResult = (_plus_1 + ";");
      }
      if (!_matched) {
        if (inst instanceof InstCall) {
          _matched=true;
          String _name = ((InstCall)inst).getProcedure().getName();
          String _plus = (_name + "(");
          String _printArgs = this.printArgs(((InstCall)inst).getArguments());
          String _plus_1 = (_plus + _printArgs);
          _switchResult = (_plus_1 + ");");
        }
      }
      if (!_matched) {
        if (inst instanceof InstLoad) {
          _matched=true;
          _switchResult = "";
        }
      }
      if (!_matched) {
        if (inst instanceof InstPhi) {
          _matched=true;
          _switchResult = "";
        }
      }
      if (!_matched) {
        if (inst instanceof InstReturn) {
          _matched=true;
          _switchResult = "";
        }
      }
      if (!_matched) {
        if (inst instanceof InstStore) {
          _matched=true;
          String _xifexpression = null;
          boolean _isTargetOutputPort = this.isTargetOutputPort(((InstStore)inst).getTarget().getVariable().getName());
          if (_isTargetOutputPort) {
            _xifexpression = "";
          } else {
            String _name = ((InstStore)inst).getTarget().getVariable().getName();
            String _plus = (_name + " := ");
            CharSequence _printExpression = this.printExpression(((InstStore)inst).getValue());
            String _plus_1 = (_plus + _printExpression);
            _xifexpression = (_plus_1 + ";");
          }
          _switchResult = _xifexpression;
        }
      }
      if (!_matched) {
        _switchResult = "";
      }
      final String instPrinted = _switchResult;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(instPrinted);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected String printArgs(final EList<Arg> args) {
    String _xblockexpression = null;
    {
      List<String> argsStrs = new ArrayList<String>();
      for (final Arg arg : args) {
        boolean _isByVal = arg.isByVal();
        if (_isByVal) {
          final Expression exp = ((ArgByVal) arg).getValue();
          argsStrs.add(this.printExpression(exp).toString());
        }
      }
      _xblockexpression = this.delimitWith(argsStrs, "+");
    }
    return _xblockexpression;
  }
  
  protected boolean isTargetInputPort(final String str) {
    boolean b = false;
    EList<Port> _inputs = this.actor.getInputs();
    for (final Port p : _inputs) {
      boolean _equals = str.equals(p.getName());
      if (_equals) {
        b = true;
      }
    }
    return b;
  }
  
  protected boolean isTargetOutputPort(final String str) {
    boolean b = false;
    EList<Port> _outputs = this.actor.getOutputs();
    for (final Port p : _outputs) {
      boolean _equals = str.equals(p.getName());
      if (_equals) {
        b = true;
      }
    }
    return b;
  }
  
  protected List<String> inputPatternVars(final Action action) {
    List<String> _xblockexpression = null;
    {
      Map<String, List<String>> inputMap = new HashMap<String, List<String>>();
      EList<Instruction> _instructions = action.getBody().getFirst().getInstructions();
      for (final Instruction inst : _instructions) {
        if ((inst instanceof InstLoad)) {
          InstLoad instLoad = ((InstLoad) inst);
          boolean _isTargetInputPort = this.isTargetInputPort(instLoad.getSource().getVariable().getName());
          if (_isTargetInputPort) {
            List<String> previousList = new ArrayList<String>();
            List<String> _get = inputMap.get(instLoad.getSource().getVariable().getName());
            boolean _tripleNotEquals = (_get != null);
            if (_tripleNotEquals) {
              previousList = inputMap.get(instLoad.getSource().getVariable().getName());
            }
            final String token = this.formatVarName(instLoad.getTarget().getVariable().getName());
            previousList.add(token);
            inputMap.put(instLoad.getSource().getVariable().getName(), previousList);
          }
        }
      }
      List<String> inPatternVars = new ArrayList<String>();
      Set<Map.Entry<String, List<String>>> _entrySet = inputMap.entrySet();
      for (final Map.Entry<String, List<String>> entry : _entrySet) {
        List<String> _value = entry.getValue();
        for (final String varStr : _value) {
          inPatternVars.add(varStr);
        }
      }
      _xblockexpression = inPatternVars;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printInputPattern(final Action actn) {
    CharSequence _xblockexpression = null;
    {
      Map<String, List<String>> inputMap = new HashMap<String, List<String>>();
      Map<String, Map<Integer, Var>> loadLookup = new HashMap<String, Map<Integer, Var>>();
      List<Instruction> actionInstructions = new ArrayList<Instruction>();
      EList<Block> _blocks = actn.getBody().getBlocks();
      for (final Block block : _blocks) {
        List<Instruction> _allInstructionsInBlock = this.allInstructionsInBlock(block);
        for (final Instruction inst : _allInstructionsInBlock) {
          actionInstructions.add(inst);
        }
      }
      for (final Instruction inst_1 : actionInstructions) {
        if ((inst_1 instanceof InstLoad)) {
          InstLoad instLoad = ((InstLoad) inst_1);
          boolean _isEmpty = instLoad.getIndexes().isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            Map<Integer, Var> _xifexpression = null;
            Map<Integer, Var> _get = loadLookup.get(instLoad.getSource().getVariable().getName());
            boolean _tripleEquals = (_get == null);
            if (_tripleEquals) {
              _xifexpression = new HashMap<Integer, Var>();
            } else {
              _xifexpression = loadLookup.get(instLoad.getSource().getVariable().getName());
            }
            Map<Integer, Var> varListAtPort = _xifexpression;
            varListAtPort.put(this.intFromExpr(instLoad.getIndexes().get(0)), instLoad.getTarget().getVariable());
            loadLookup.put(instLoad.getSource().getVariable().getName(), varListAtPort);
          }
        }
      }
      Set<Map.Entry<String, Map<Integer, Var>>> _entrySet = loadLookup.entrySet();
      for (final Map.Entry<String, Map<Integer, Var>> entry : _entrySet) {
        {
          String _key = entry.getKey();
          ArrayList<String> _arrayList = new ArrayList<String>();
          inputMap.put(_key, _arrayList);
          Set<Map.Entry<Integer, Var>> _entrySet_1 = entry.getValue().entrySet();
          for (final Map.Entry<Integer, Var> token : _entrySet_1) {
            {
              List<String> listStrTokens = inputMap.get(entry.getKey());
              listStrTokens.add(token.getValue().getName());
              inputMap.put(entry.getKey(), listStrTokens);
            }
          }
        }
      }
      List<String> inputPattern = CollectionLiterals.<String>newArrayList();
      Set<Map.Entry<String, List<String>>> _entrySet_1 = inputMap.entrySet();
      for (final Map.Entry<String, List<String>> entry_1 : _entrySet_1) {
        String _key = entry_1.getKey();
        String _plus = (_key + ":[");
        String _delimitWith = this.delimitWith(entry_1.getValue(), ",");
        String _plus_1 = (_plus + _delimitWith);
        String _plus_2 = (_plus_1 + "]");
        inputPattern.add(_plus_2);
      }
      String _xifexpression_1 = null;
      boolean _isEmpty_1 = inputPattern.isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _xifexpression_1 = this.delimitWith(inputPattern, ",");
      } else {
        _xifexpression_1 = "";
      }
      final String inputPatternStr = _xifexpression_1;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(inputPatternStr);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printOutputPattern(final Action actn) {
    CharSequence _xblockexpression = null;
    {
      Map<String, List<String>> outputMap = new HashMap<String, List<String>>();
      Map<String, Map<Integer, Expression>> storeLookup = new HashMap<String, Map<Integer, Expression>>();
      List<Instruction> actionInstructions = new ArrayList<Instruction>();
      EList<Block> _blocks = actn.getBody().getBlocks();
      for (final Block block : _blocks) {
        List<Instruction> _allInstructionsInBlock = this.allInstructionsInBlock(block);
        for (final Instruction inst : _allInstructionsInBlock) {
          actionInstructions.add(inst);
        }
      }
      for (final Instruction inst_1 : actionInstructions) {
        if ((inst_1 instanceof InstStore)) {
          InstStore instStore = ((InstStore) inst_1);
          boolean _isEmpty = instStore.getIndexes().isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            boolean _isTargetOutputPort = this.isTargetOutputPort(instStore.getTarget().getVariable().getName());
            if (_isTargetOutputPort) {
              Map<Integer, Expression> _xifexpression = null;
              Map<Integer, Expression> _get = storeLookup.get(instStore.getTarget().getVariable().getName());
              boolean _tripleEquals = (_get == null);
              if (_tripleEquals) {
                _xifexpression = new HashMap<Integer, Expression>();
              } else {
                _xifexpression = storeLookup.get(instStore.getTarget().getVariable().getName());
              }
              Map<Integer, Expression> varListAtPort = _xifexpression;
              varListAtPort.put(this.intFromExpr(instStore.getIndexes().get(0)), instStore.getValue());
              storeLookup.put(instStore.getTarget().getVariable().getName(), varListAtPort);
            }
          }
        }
      }
      Set<Map.Entry<String, Map<Integer, Expression>>> _entrySet = storeLookup.entrySet();
      for (final Map.Entry<String, Map<Integer, Expression>> entry : _entrySet) {
        {
          String _key = entry.getKey();
          ArrayList<String> _arrayList = new ArrayList<String>();
          outputMap.put(_key, _arrayList);
          Set<Map.Entry<Integer, Expression>> _entrySet_1 = entry.getValue().entrySet();
          for (final Map.Entry<Integer, Expression> token : _entrySet_1) {
            {
              List<String> listStrTokens = outputMap.get(entry.getKey());
              listStrTokens.add(this.printExpression(token.getValue()).toString());
              outputMap.put(entry.getKey(), listStrTokens);
            }
          }
        }
      }
      List<String> outputPattern = CollectionLiterals.<String>newArrayList();
      Set<Map.Entry<String, List<String>>> _entrySet_1 = outputMap.entrySet();
      for (final Map.Entry<String, List<String>> entry_1 : _entrySet_1) {
        String _key = entry_1.getKey();
        String _plus = (_key + ":[");
        String _delimitWith = this.delimitWith(entry_1.getValue(), ",");
        String _plus_1 = (_plus + _delimitWith);
        String _plus_2 = (_plus_1 + "]");
        outputPattern.add(_plus_2);
      }
      String _xifexpression_1 = null;
      boolean _isEmpty_1 = outputPattern.isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _xifexpression_1 = this.delimitWith(outputPattern, ",");
      } else {
        _xifexpression_1 = "";
      }
      final String outputPatternStr = _xifexpression_1;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(outputPatternStr);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected List<Instruction> allInstructionsInBlock(final Block block) {
    List<Instruction> _switchResult = null;
    boolean _matched = false;
    if (block instanceof BlockBasic) {
      _matched=true;
      _switchResult = this.instructionsFromBlockBasic(((BlockBasic)block));
    }
    if (!_matched) {
      if (block instanceof BlockIf) {
        _matched=true;
        _switchResult = this.instructionsFromBlockIf(((BlockIf)block));
      }
    }
    if (!_matched) {
      if (block instanceof BlockWhile) {
        _matched=true;
        _switchResult = this.instructionsFromBlockWhile(((BlockWhile)block));
      }
    }
    return _switchResult;
  }
  
  protected EList<Instruction> instructionsFromBlockBasic(final BlockBasic block) {
    return block.getInstructions();
  }
  
  protected List<Instruction> instructionsFromBlockIf(final BlockIf block) {
    List<Instruction> _xblockexpression = null;
    {
      List<Instruction> instructions = new ArrayList<Instruction>();
      EList<Block> _thenBlocks = block.getThenBlocks();
      for (final Block thenBlock : _thenBlocks) {
        instructions.addAll(this.allInstructionsInBlock(thenBlock));
      }
      EList<Block> _elseBlocks = block.getElseBlocks();
      for (final Block elseBlock : _elseBlocks) {
        instructions.addAll(this.allInstructionsInBlock(elseBlock));
      }
      _xblockexpression = instructions;
    }
    return _xblockexpression;
  }
  
  protected List<Instruction> instructionsFromBlockWhile(final BlockWhile block) {
    List<Instruction> _xblockexpression = null;
    {
      List<Instruction> instructions = new ArrayList<Instruction>();
      EList<Block> _blocks = block.getBlocks();
      for (final Block subBlock : _blocks) {
        instructions.addAll(this.allInstructionsInBlock(subBlock));
      }
      _xblockexpression = instructions;
    }
    return _xblockexpression;
  }
  
  protected Integer intFromExpr(final Expression exp) {
    Integer _switchResult = null;
    boolean _matched = false;
    if (exp instanceof ExprInt) {
      _matched=true;
      int _intValue = ((ExprInt)exp).getIntValue();
      _switchResult = new Integer(_intValue);
    }
    if (!_matched) {
      _switchResult = Integer.valueOf((-1));
    }
    return _switchResult;
  }
  
  protected String delimitWith(final List<String> strList, final String delimChar) {
    String _xblockexpression = null;
    {
      StringBuilder sb = new StringBuilder();
      String delim = "";
      for (final String i : strList) {
        {
          sb.append(delim).append(i);
          delim = delimChar;
        }
      }
      _xblockexpression = sb.toString();
    }
    return _xblockexpression;
  }
  
  protected String takeAfterLast(final String s, final String c) {
    String _xblockexpression = null;
    {
      String[] ss = s.split(c);
      Iterator<String> it = Arrays.<String>asList(ss).iterator();
      String lastOne = null;
      while (it.hasNext()) {
        lastOne = it.next();
      }
      _xblockexpression = lastOne;
    }
    return _xblockexpression;
  }
}
