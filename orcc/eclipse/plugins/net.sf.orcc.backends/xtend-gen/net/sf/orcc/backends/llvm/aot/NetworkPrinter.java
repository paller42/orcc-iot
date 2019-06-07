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

import com.google.common.collect.Iterables;
import java.util.Map;
import net.sf.orcc.backends.BackendsConstants;
import net.sf.orcc.backends.llvm.aot.LLVMTemplate;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Vertex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Compile Network LLVM source code
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class NetworkPrinter extends LLVMTemplate {
  private Network network;
  
  protected String optionDatalayout = BackendsConstants.LLVM_DEFAULT_TARGET_DATALAYOUT;
  
  protected String optionArch = BackendsConstants.LLVM_DEFAULT_TARGET_TRIPLE;
  
  public Network setNetwork(final Network network) {
    return this.network = network;
  }
  
  @Override
  public void setOptions(final Map<String, Object> options) {
    super.setOptions(options);
    boolean _containsKey = options.containsKey(BackendsConstants.LLVM_TARGET_TRIPLE);
    if (_containsKey) {
      Object _get = options.get(BackendsConstants.LLVM_TARGET_TRIPLE);
      this.optionArch = ((String) _get);
    }
    boolean _containsKey_1 = options.containsKey(BackendsConstants.LLVM_TARGET_DATALAYOUT);
    if (_containsKey_1) {
      Object _get_1 = options.get(BackendsConstants.LLVM_TARGET_DATALAYOUT);
      this.optionDatalayout = ((String) _get_1);
    }
  }
  
  public CharSequence getNetworkFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("target datalayout = \"");
    _builder.append(this.optionDatalayout);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("target triple = \"");
    _builder.append(this.optionArch);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; Declare and initialize FIFO variables ");
    _builder.newLine();
    _builder.newLine();
    _builder.append("declare void @init_orcc(i32 %argc, i8** %argv)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("@network = global i32 zeroinitializer");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Connection> _connections = this.network.getConnections();
      for(final Connection conn : _connections) {
        _builder.append("@fifo_");
        Object _objectValue = conn.getAttribute("id").getObjectValue();
        _builder.append(_objectValue);
        _builder.append("_content = global [");
        int _safeSize = this.safeSize(conn);
        _builder.append(_safeSize);
        _builder.append(" x ");
        CharSequence _doSwitch = this.doSwitch(conn.getSourcePort().getType());
        _builder.append(_doSwitch);
        _builder.append("] zeroinitializer");
        _builder.newLineIfNotEmpty();
        _builder.append("@fifo_");
        Object _objectValue_1 = conn.getAttribute("id").getObjectValue();
        _builder.append(_objectValue_1);
        _builder.append("_rdIndex = global i32 zeroinitializer");
        _builder.newLineIfNotEmpty();
        _builder.append("@fifo_");
        Object _objectValue_2 = conn.getAttribute("id").getObjectValue();
        _builder.append(_objectValue_2);
        _builder.append("_wrIndex = global i32 zeroinitializer");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; Declare the scheduling function of each actor");
    _builder.newLine();
    _builder.newLine();
    {
      Iterable<Instance> _actorInstances = this.getActorInstances(this.network.getChildren());
      for(final Instance instance : _actorInstances) {
        _builder.append("declare void @");
        String _name = instance.getName();
        _builder.append(_name);
        _builder.append("_scheduler()");
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty = instance.getActor().getInitializes().isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            _builder.append("declare void @");
            String _name_1 = instance.getName();
            _builder.append(_name_1);
            _builder.append("_initialize()");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      Iterable<Actor> _filter = Iterables.<Actor>filter(this.network.getChildren(), Actor.class);
      for(final Actor actor : _filter) {
        _builder.append("declare void @");
        String _name_2 = actor.getName();
        _builder.append(_name_2);
        _builder.append("_scheduler()");
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty_1 = actor.getInitializes().isEmpty();
          boolean _not_1 = (!_isEmpty_1);
          if (_not_1) {
            _builder.append("declare void @");
            String _name_3 = actor.getName();
            _builder.append(_name_3);
            _builder.append("_initialize()");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("; The main function - A simple round-robin scheduler");
    _builder.newLine();
    _builder.newLine();
    _builder.append("define void @main(i32 %argc, i8** %argv) noinline noreturn nounwind {");
    _builder.newLine();
    _builder.append("entry:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("call void @init_orcc(i32 %argc, i8** %argv);");
    _builder.newLine();
    {
      Iterable<Instance> _actorInstances_1 = this.getActorInstances(this.network.getChildren());
      for(final Instance instance_1 : _actorInstances_1) {
        {
          boolean _isEmpty_2 = instance_1.getActor().getInitializes().isEmpty();
          boolean _not_2 = (!_isEmpty_2);
          if (_not_2) {
            _builder.append("\t");
            _builder.append("call void @");
            String _name_4 = instance_1.getName();
            _builder.append(_name_4, "\t");
            _builder.append("_initialize()");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      Iterable<Actor> _filter_1 = Iterables.<Actor>filter(this.network.getChildren(), Actor.class);
      for(final Actor actor_1 : _filter_1) {
        {
          boolean _isEmpty_3 = actor_1.getInitializes().isEmpty();
          boolean _not_3 = (!_isEmpty_3);
          if (_not_3) {
            _builder.append("\t");
            _builder.append("call void @");
            String _name_5 = actor_1.getName();
            _builder.append(_name_5, "\t");
            _builder.append("_initialize()");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("br label %loop");
    _builder.newLine();
    _builder.newLine();
    _builder.append("loop:");
    _builder.newLine();
    {
      EList<Vertex> _children = this.network.getChildren();
      for(final Vertex child : _children) {
        _builder.append("\t");
        _builder.append("call void @");
        String _label = child.getLabel();
        _builder.append(_label, "\t");
        _builder.append("_scheduler()");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("br label %loop");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
