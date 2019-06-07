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

import net.sf.orcc.backends.llvm.tta.TTAPrinter;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Link;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Port;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.util.ArchitectureUtil;
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class PyDesignPrinter extends TTAPrinter {
  private FPGA fpga;
  
  public FPGA setFpga(final FPGA fpga) {
    return this.fpga = fpga;
  }
  
  public CharSequence getPython(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# -*- coding: utf-8 -*-");
    _builder.newLine();
    _builder.append("#");
    _builder.newLine();
    _builder.append("# Generated from <design.name> using Open-RVC CAL Compiler");
    _builder.newLine();
    _builder.append("#");
    _builder.newLine();
    _builder.newLine();
    _builder.append("from orcc_ import *");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Processor> _processors = design.getProcessors();
      for(final Processor processor : _processors) {
        CharSequence _pythonInit = this.getPythonInit(processor);
        _builder.append(_pythonInit);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("## Processors initialization");
    _builder.newLine();
    _builder.append("processors = [");
    _builder.newLine();
    {
      EList<Processor> _processors_1 = design.getProcessors();
      boolean _hasElements = false;
      for(final Processor processor_1 : _processors_1) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "\t");
        }
        _builder.append("\t");
        CharSequence _python = this.getPython(processor_1);
        _builder.append(_python, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("]");
    _builder.newLine();
    _builder.newLine();
    _builder.append("## Memories initialization");
    _builder.newLine();
    _builder.append("memories = [");
    _builder.newLine();
    {
      EList<Memory> _sharedMemories = design.getSharedMemories();
      boolean _hasElements_1 = false;
      for(final Memory memory : _sharedMemories) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate(",", "\t");
        }
        _builder.append("\t");
        CharSequence _python_1 = this.getPython(memory);
        _builder.append(_python_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("]");
    _builder.newLine();
    _builder.newLine();
    _builder.append("## Network initialization");
    _builder.newLine();
    _builder.append("design = Design(\"");
    String _name = design.getName();
    _builder.append(_name);
    _builder.append("\", processors, memories, ");
    {
      boolean _isAltera = this.fpga.isAltera();
      if (_isAltera) {
        _builder.append("True");
      } else {
        _builder.append("False");
      }
    }
    _builder.append(", \"");
    FPGA.Family _family = this.fpga.getFamily();
    _builder.append(_family);
    _builder.append("\", \"");
    String _device = this.fpga.getDevice();
    _builder.append(_device);
    _builder.append("\", \"");
    String _package = this.fpga.getPackage();
    _builder.append(_package);
    _builder.append("\")");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence getPython(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Processor(\"");
    String _name = processor.getName();
    _builder.append(_name);
    _builder.append("\", ");
    String _name_1 = processor.getName();
    _builder.append(_name_1);
    _builder.append("_instances, ");
    String _name_2 = processor.getName();
    _builder.append(_name_2);
    _builder.append("_inputs, ");
    String _name_3 = processor.getName();
    _builder.append(_name_3);
    _builder.append("_outputs, ");
    CharSequence _usePrint = this.usePrint(processor);
    _builder.append(_usePrint);
    _builder.append(", \"");
    FPGA.Family _family = this.fpga.getFamily();
    _builder.append(_family);
    _builder.append("\", \"");
    String _device = this.fpga.getDevice();
    _builder.append(_device);
    _builder.append("\", \"");
    String _package = this.fpga.getPackage();
    _builder.append(_package);
    _builder.append("\")");
    return _builder;
  }
  
  private CharSequence getPython(final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Memory(\"");
    String _name = memory.getName();
    _builder.append(_name);
    _builder.append("\", 32, ");
    long _depth = memory.getDepth();
    long _divide = (_depth / 4);
    _builder.append(_divide);
    _builder.append(", \"");
    String _version = this.fpga.getVersion();
    _builder.append(_version);
    _builder.append("\")");
    return _builder;
  }
  
  private CharSequence getPython(final Port port, final int index) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Port(\"");
    String _label = port.getLabel();
    _builder.append(_label);
    _builder.append("\", ");
    _builder.append(index);
    {
      boolean _isNative = port.isNative();
      if (_isNative) {
        _builder.append(", True, ");
        int _size = port.getSize();
        _builder.append(_size);
      }
    }
    _builder.append(")");
    return _builder;
  }
  
  private CharSequence getPythonInit(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = processor.getName();
    _builder.append(_name);
    _builder.append("_inputs = [");
    {
      EList<Edge> _incoming = processor.getIncoming();
      boolean _hasElements = false;
      for(final Edge edge : _incoming) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        CharSequence _python = this.getPython(((Link) edge).getTargetPort(), 0);
        _builder.append(_python);
      }
    }
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _name_1 = processor.getName();
    _builder.append(_name_1);
    _builder.append("_outputs = [");
    {
      EList<Edge> _outgoing = processor.getOutgoing();
      boolean _hasElements_1 = false;
      for(final Edge edge_1 : _outgoing) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        CharSequence _python_1 = this.getPython(((Link) edge_1).getSourcePort(), 0);
        _builder.append(_python_1);
      }
    }
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _name_2 = processor.getName();
    _builder.append(_name_2);
    _builder.append("_instances = [");
    {
      EList<Vertex> _mappedActors = processor.getMappedActors();
      boolean _hasElements_2 = false;
      for(final Vertex actor : _mappedActors) {
        if (!_hasElements_2) {
          _hasElements_2 = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        _builder.append("\"");
        String _label = actor.getLabel();
        _builder.append(_label);
        _builder.append("\"");
      }
    }
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence usePrint(final Processor processor) {
    CharSequence _xifexpression = null;
    boolean _needToPrint = ArchitectureUtil.needToPrint(processor.getMappedActors());
    if (_needToPrint) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("True");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("False");
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
}
