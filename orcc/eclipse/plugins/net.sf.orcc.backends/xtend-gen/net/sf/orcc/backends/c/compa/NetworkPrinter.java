/**
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c.compa;

import net.sf.orcc.df.Connection;
import net.sf.orcc.graph.Vertex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Generate and print network source file for COMPA backend.
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class NetworkPrinter extends net.sf.orcc.backends.c.NetworkPrinter {
  private int memoryBaseAddr = 0x40000000;
  
  public CharSequence getContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// Generated from \"");
    String _name = this.network.getName();
    _builder.append(_name);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include <locale.h>");
    _builder.newLine();
    _builder.append("#include <stdio.h>");
    _builder.newLine();
    _builder.append("#include <stdlib.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#ifndef _WIN32");
    _builder.newLine();
    _builder.append("#define __USE_GNU");
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"fifoAllocations.h\"");
    _builder.newLine();
    _builder.append("#include \"util.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Action initializes");
    _builder.newLine();
    {
      EList<Vertex> _children = this.network.getChildren();
      for(final Vertex child : _children) {
        _builder.append("extern void ");
        String _label = child.getLabel();
        _builder.append(_label);
        _builder.append("_initialize();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Action schedulers");
    _builder.newLine();
    {
      EList<Vertex> _children_1 = this.network.getChildren();
      for(final Vertex child_1 : _children_1) {
        _builder.append("extern int ");
        String _label_1 = child_1.getLabel();
        _builder.append(_label_1);
        _builder.append("_scheduler();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Actor scheduler");
    _builder.newLine();
    _builder.append("static void scheduler() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int stop = 0;");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Vertex> _children_2 = this.network.getChildren();
      for(final Vertex child_2 : _children_2) {
        _builder.append("\t");
        String _label_2 = child_2.getLabel();
        _builder.append(_label_2, "\t");
        _builder.append("_initialize();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int i;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("while(!stop) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("i = 0;");
    _builder.newLine();
    {
      EList<Vertex> _children_3 = this.network.getChildren();
      for(final Vertex child_3 : _children_3) {
        _builder.append("\t\t");
        _builder.append("i += ");
        String _label_3 = child_3.getLabel();
        _builder.append(_label_3, "\t\t");
        _builder.append("_scheduler();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("stop = stop || (i == 0);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Main");
    _builder.newLine();
    _builder.append("int main(int argc, char *argv[]) {");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("scheduler();");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("printf(\"End of simulation !\\n\");");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return compareErrors;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getFifoContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// Generated from \"");
    String _name = this.network.getName();
    _builder.append(_name);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include \"types.h\"");
    _builder.newLine();
    _builder.append("#include \"fifo.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#define SIZE ");
    _builder.append(this.fifoSize);
    _builder.newLineIfNotEmpty();
    _builder.append("// #define PRINT_FIRINGS");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// FIFO allocation");
    _builder.newLine();
    {
      EList<Vertex> _children = this.network.getChildren();
      for(final Vertex child : _children) {
        CharSequence _allocateFifos = this.allocateFifos(child);
        _builder.append(_allocateFifos);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// FIFO pointer assignments");
    _builder.newLine();
    {
      EList<Vertex> _children_1 = this.network.getChildren();
      for(final Vertex child_1 : _children_1) {
        CharSequence _assignFifo = this.assignFifo(child_1);
        _builder.append(_assignFifo);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    return _builder;
  }
  
  @Override
  protected CharSequence allocateFifo(final Connection conn, final int nbReaders) {
    CharSequence _xblockexpression = null;
    {
      Integer _xifexpression = null;
      Integer _size = conn.getSize();
      boolean _tripleNotEquals = (_size != null);
      if (_tripleNotEquals) {
        _xifexpression = conn.getSize();
      } else {
        _xifexpression = Integer.valueOf(this.fifoSize);
      }
      final Integer size = _xifexpression;
      final Object id = conn.<Object>getValueAsObject("idNoBcast");
      int _xifexpression_1 = (int) 0;
      int _sizeInBits = conn.getSourcePort().getType().getSizeInBits();
      boolean _equals = (_sizeInBits == 1);
      if (_equals) {
        _xifexpression_1 = 4;
      } else {
        int _sizeInBits_1 = conn.getSourcePort().getType().getSizeInBits();
        _xifexpression_1 = (_sizeInBits_1 / 8);
      }
      final int portSizeInBytes = _xifexpression_1;
      final int bufferAddr = this.memoryBaseAddr;
      final int rdIndicesAddr = (this.memoryBaseAddr + ((size).intValue() * portSizeInBytes));
      final int wrIndexAddr = (rdIndicesAddr + (nbReaders * 4));
      this.memoryBaseAddr = (wrIndexAddr + 4);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("static fifo_");
      CharSequence _doSwitch = this.doSwitch(conn.getSourcePort().getType());
      _builder.append(_doSwitch);
      _builder.append("_t fifo_");
      _builder.append(id);
      _builder.append(" = {");
      _builder.append(size);
      _builder.append(", (");
      CharSequence _doSwitch_1 = this.doSwitch(conn.getSourcePort().getType());
      _builder.append(_doSwitch_1);
      _builder.append(" *) ");
      String _format = String.format("0x%x", Integer.valueOf(bufferAddr));
      _builder.append(_format);
      _builder.append(", ");
      _builder.append(nbReaders);
      _builder.append(", (unsigned int *) ");
      String _format_1 = String.format("0x%x", Integer.valueOf(rdIndicesAddr));
      _builder.append(_format_1);
      _builder.append(", (unsigned int *) ");
      String _format_2 = String.format("0x%x", Integer.valueOf(wrIndexAddr));
      _builder.append(_format_2);
      _builder.append("};");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
}
