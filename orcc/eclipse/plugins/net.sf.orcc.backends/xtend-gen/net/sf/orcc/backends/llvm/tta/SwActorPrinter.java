/**
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta;

import java.util.List;
import net.sf.orcc.backends.llvm.aot.InstancePrinter;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class SwActorPrinter extends InstancePrinter {
  private Processor processor;
  
  public Processor setProcessor(final Processor processor) {
    return this.processor = processor;
  }
  
  @Override
  protected CharSequence getAddrSpace(final Connection connection) {
    CharSequence _xblockexpression = null;
    {
      final Integer id = this.processor.getAddrSpaceId(connection);
      CharSequence _xifexpression = null;
      if ((id != null)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(" ");
        _builder.append("addrspace(");
        _builder.append(id, " ");
        _builder.append(")");
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  @Override
  protected CharSequence getProperties(final Port port) {
    CharSequence _xifexpression = null;
    if (((!IterableExtensions.isNullOrEmpty(this.outgoingPortMap.get(port))) || (this.incomingPortMap.get(port) != null))) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(" ");
      _builder.append("volatile");
      _xifexpression = _builder;
    }
    return _xifexpression;
  }
  
  @Override
  protected CharSequence getProperties(final Var variable) {
    CharSequence _xifexpression = null;
    boolean _isAssignable = variable.isAssignable();
    if (_isAssignable) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(" ");
      _builder.append("volatile");
      _xifexpression = _builder;
    }
    return _xifexpression;
  }
  
  private CharSequence printNativeWrite(final Port port, final Var variable) {
    CharSequence _xblockexpression = null;
    {
      Type _type = variable.getType();
      final CharSequence innerType = this.doSwitch(((TypeList) _type).getInnermostType());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("%tmp_");
      String _name = variable.getName();
      _builder.append(_name);
      _builder.append("_elt = getelementptr ");
      CharSequence _doSwitch = this.doSwitch(variable.getType());
      _builder.append(_doSwitch);
      _builder.append("* ");
      CharSequence _print = this.print(variable);
      _builder.append(_print);
      _builder.append(", i32 0, i1 0 ");
      _builder.newLineIfNotEmpty();
      _builder.append("%tmp_");
      String _name_1 = variable.getName();
      _builder.append(_name_1);
      _builder.append(" = load ");
      _builder.append(innerType);
      _builder.append("* %tmp_");
      String _name_2 = variable.getName();
      _builder.append(_name_2);
      _builder.append("_elt");
      _builder.newLineIfNotEmpty();
      _builder.append("tail call void asm sideeffect \"SIG_OUT_");
      String _name_3 = port.getName();
      _builder.append(_name_3);
      _builder.append(".LEDS\", \"ir\"(");
      _builder.append(innerType);
      _builder.append(" %tmp_");
      String _name_4 = variable.getName();
      _builder.append(_name_4);
      _builder.append(") nounwind");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  protected CharSequence printDatalayout() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  @Override
  protected CharSequence printArchitecture() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  @Override
  protected CharSequence printCore(final Action action, final boolean isAligned) {
    StringConcatenation _builder = new StringConcatenation();
    final Pattern inputPattern = action.getInputPattern();
    _builder.newLineIfNotEmpty();
    final Pattern outputPattern = action.getOutputPattern();
    _builder.newLineIfNotEmpty();
    _builder.append("define internal ");
    CharSequence _doSwitch = this.doSwitch(action.getBody().getReturnType());
    _builder.append(_doSwitch);
    _builder.append(" @");
    String _name = action.getBody().getName();
    _builder.append(_name);
    {
      if (isAligned) {
        _builder.append("_aligned");
      }
    }
    _builder.append("() ");
    {
      if (this.optionInline) {
        _builder.append("noinline ");
      }
    }
    _builder.append("nounwind {");
    _builder.newLineIfNotEmpty();
    _builder.append("entry:");
    _builder.newLine();
    {
      EList<Var> _locals = action.getBody().getLocals();
      for(final Var local : _locals) {
        _builder.append("\t");
        CharSequence _declare = this.declare(local);
        _builder.append(_declare, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          return Boolean.valueOf(it.isNative());
        }
      };
      Iterable<Port> _filter = IterableExtensions.<Port>filter(outputPattern.getPorts(), _function);
      for(final Port port : _filter) {
        _builder.append("\t");
        CharSequence _declare_1 = this.declare(outputPattern.getVariable(port));
        _builder.append(_declare_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative = this.getNotNative(inputPattern.getPorts());
      for(final Port port_1 : _notNative) {
        _builder.append("\t");
        CharSequence _loadVar = this.loadVar(port_1, this.incomingPortMap.get(port_1), action.getBody().getName());
        _builder.append(_loadVar, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative_1 = this.getNotNative(outputPattern.getPorts());
      for(final Port port_2 : _notNative_1) {
        {
          List<Connection> _get = this.outgoingPortMap.get(port_2);
          for(final Connection connection : _get) {
            _builder.append("\t");
            CharSequence _loadVar_1 = this.loadVar(port_2, connection, action.getBody().getName());
            _builder.append(_loadVar_1, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("br label %b");
    CharSequence _label = this.label(IterableExtensions.<Block>head(action.getBody().getBlocks()));
    _builder.append(_label, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Block> _blocks = action.getBody().getBlocks();
      for(final Block block : _blocks) {
        CharSequence _doSwitch_1 = this.doSwitch(block);
        _builder.append(_doSwitch_1);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative_2 = this.getNotNative(inputPattern.getPorts());
      for(final Port port_3 : _notNative_2) {
        _builder.append("\t");
        CharSequence _updateVar = this.updateVar(port_3, this.incomingPortMap.get(port_3), Integer.valueOf(inputPattern.getNumTokens(port_3)), action.getBody().getName());
        _builder.append(_updateVar, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative_3 = this.getNotNative(outputPattern.getPorts());
      for(final Port port_4 : _notNative_3) {
        {
          List<Connection> _get_1 = this.outgoingPortMap.get(port_4);
          for(final Connection connection_1 : _get_1) {
            _builder.append("\t");
            CharSequence _updateVar_1 = this.updateVar(port_4, connection_1, Integer.valueOf(outputPattern.getNumTokens(port_4)), action.getBody().getName());
            _builder.append(_updateVar_1, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      final Function1<Port, Boolean> _function_1 = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          return Boolean.valueOf(it.isNative());
        }
      };
      Iterable<Port> _filter_1 = IterableExtensions.<Port>filter(outputPattern.getPorts(), _function_1);
      for(final Port port_5 : _filter_1) {
        _builder.append("\t");
        CharSequence _printNativeWrite = this.printNativeWrite(port_5, action.getOutputPattern().getPortToVarMap().get(port_5));
        _builder.append(_printNativeWrite, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("ret void");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence caseInstCall(final InstCall call) {
    StringConcatenation _builder = new StringConcatenation();
    final Def target = call.getTarget();
    _builder.newLineIfNotEmpty();
    final EList<Arg> args = call.getArguments();
    _builder.newLineIfNotEmpty();
    final EList<Param> parameters = call.getProcedure().getParameters();
    _builder.newLineIfNotEmpty();
    {
      boolean _isNative = call.getProcedure().isNative();
      if (_isNative) {
        _builder.newLine();
        {
          if ((target != null)) {
            _builder.append("%");
            String _name = target.getVariable().getName();
            _builder.append(_name);
            _builder.append(" = ");
          }
        }
        _builder.append("tail call ");
        CharSequence _doSwitch = this.doSwitch(call.getProcedure().getReturnType());
        _builder.append(_doSwitch);
        _builder.append(" asm sideeffect \"ORCC_FU.");
        String _upperCase = call.getProcedure().getName().toUpperCase();
        _builder.append(_upperCase);
        _builder.append("\", \"");
        {
          if ((target != null)) {
            _builder.append("=ir, ");
          }
        }
        _builder.append("ir");
        String _ir = this.getIr(args);
        _builder.append(_ir);
        _builder.append("\"(i32 0");
        {
          boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(args);
          boolean _not = (!_isNullOrEmpty);
          if (_not) {
            _builder.append(", ");
            String _join = IterableExtensions.join(this.format(args, parameters), ", ");
            _builder.append(_join);
          }
        }
        _builder.append(") nounwind");
        _builder.newLineIfNotEmpty();
      } else {
        CharSequence _caseInstCall = super.caseInstCall(call);
        _builder.append(_caseInstCall);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  protected CharSequence print(final Procedure procedure) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isNative = procedure.isNative();
      boolean _not = (!_isNative);
      if (_not) {
        CharSequence _print = super.print(procedure);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private String getIr(final EList<Arg> args) {
    String irs = new String();
    for (final Arg arg : args) {
      irs = (irs + ", ir");
    }
    return irs;
  }
}
