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

import com.google.common.collect.Iterables;
import net.sf.orcc.backends.llvm.aot.LLVMTemplate;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.graph.Vertex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class SwProcessorPrinter extends LLVMTemplate {
  public CharSequence getContent(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("; Declare and initialize FIFO variables ");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<Memory> _localRAMs = processor.getLocalRAMs();
      EList<Memory> _sharedRAMs = processor.getSharedRAMs();
      Iterable<Memory> _plus = Iterables.<Memory>concat(_localRAMs, _sharedRAMs);
      for(final Memory ram : _plus) {
        _builder.append("\t");
        final Integer addrSpace = processor.getMemToAddrSpaceIdMap().get(ram);
        _builder.newLineIfNotEmpty();
        {
          EList<Connection> _mappedConnections = ram.getMappedConnections();
          for(final Connection conn : _mappedConnections) {
            _builder.append("\t");
            _builder.append("@fifo_");
            Object _objectValue = conn.getAttribute("id").getObjectValue();
            _builder.append(_objectValue, "\t");
            _builder.append("_content = addrspace(");
            _builder.append(addrSpace, "\t");
            _builder.append(") global [");
            int _safeSize = this.safeSize(conn);
            _builder.append(_safeSize, "\t");
            _builder.append(" x ");
            CharSequence _doSwitch = this.doSwitch(conn.getSourcePort().getType());
            _builder.append(_doSwitch, "\t");
            _builder.append("] zeroinitializer");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("@fifo_");
            Object _objectValue_1 = conn.getAttribute("id").getObjectValue();
            _builder.append(_objectValue_1, "\t");
            _builder.append("_rdIndex = addrspace(");
            _builder.append(addrSpace, "\t");
            _builder.append(") global i32 zeroinitializer");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("@fifo_");
            Object _objectValue_2 = conn.getAttribute("id").getObjectValue();
            _builder.append(_objectValue_2, "\t");
            _builder.append("_wrIndex = addrspace(");
            _builder.append(addrSpace, "\t");
            _builder.append(") global i32 zeroinitializer");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("; Declare the scheduling function of each actor");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<Vertex> _mappedActors = processor.getMappedActors();
      for(final Vertex vertex : _mappedActors) {
        _builder.append("\t");
        _builder.append("declare void @");
        String _label = vertex.getLabel();
        _builder.append(_label, "\t");
        _builder.append("_scheduler()");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Vertex> _mappedActors_1 = processor.getMappedActors();
      for(final Vertex vertex_1 : _mappedActors_1) {
        _builder.append("\t");
        final Actor actor = vertex_1.<Actor>getAdapter(Actor.class);
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty = actor.getInitializes().isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            _builder.append("\t");
            _builder.append("declare void @");
            String _label_1 = vertex_1.getLabel();
            _builder.append(_label_1, "\t");
            _builder.append("_initialize()");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("; The main function - A simple round-robin scheduler");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("define void @main() noreturn nounwind {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entry:");
    _builder.newLine();
    {
      EList<Vertex> _mappedActors_2 = processor.getMappedActors();
      for(final Vertex vertex_2 : _mappedActors_2) {
        _builder.append("\t\t");
        final Actor actor_1 = vertex_2.<Actor>getAdapter(Actor.class);
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty_1 = actor_1.getInitializes().isEmpty();
          boolean _not_1 = (!_isEmpty_1);
          if (_not_1) {
            _builder.append("\t\t");
            _builder.append("call void @");
            String _label_2 = vertex_2.getLabel();
            _builder.append(_label_2, "\t\t");
            _builder.append("_initialize()");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.append("br label %loop");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("loop:");
    _builder.newLine();
    {
      EList<Vertex> _mappedActors_3 = processor.getMappedActors();
      for(final Vertex vertex_3 : _mappedActors_3) {
        _builder.append("\t\t");
        _builder.append("call void @");
        String _label_3 = vertex_3.getLabel();
        _builder.append(_label_3, "\t\t");
        _builder.append("_scheduler()");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.append("br label %loop");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
