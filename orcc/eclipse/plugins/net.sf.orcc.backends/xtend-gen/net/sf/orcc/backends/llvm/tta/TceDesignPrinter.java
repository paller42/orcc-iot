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
import net.sf.orcc.backends.llvm.tta.TTAPrinter;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * The template to print the Multiprocessor Architecture Description File.
 * 
 * @author Herve Yviquel
 */
@SuppressWarnings("all")
public class TceDesignPrinter extends TTAPrinter {
  private String path;
  
  public String setPath(final String path) {
    return this.path = path;
  }
  
  public CharSequence getPndf(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    _builder.newLine();
    _builder.append("<processor-network version=\"0.1\">");
    _builder.newLine();
    {
      EList<Processor> _processors = design.getProcessors();
      for(final Processor processor : _processors) {
        _builder.append("\t");
        CharSequence _pndf = this.getPndf(processor);
        _builder.append(_pndf, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</processor-network>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getPndf(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<processor name=\"");
    String _name = processor.getName();
    _builder.append(_name);
    _builder.append("\" >");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<adf>");
    _builder.append(this.path, "\t");
    _builder.append("/");
    String _name_1 = processor.getName();
    _builder.append(_name_1, "\t");
    _builder.append("/");
    String _name_2 = processor.getName();
    _builder.append(_name_2, "\t");
    _builder.append(".adf</adf>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<tpef>");
    _builder.append(this.path, "\t");
    _builder.append("/");
    String _name_3 = processor.getName();
    _builder.append(_name_3, "\t");
    _builder.append("/");
    String _name_4 = processor.getName();
    _builder.append(_name_4, "\t");
    _builder.append(".tpef</tpef>");
    _builder.newLineIfNotEmpty();
    {
      EList<Vertex> _mappedActors = processor.getMappedActors();
      for(final Vertex vertex : _mappedActors) {
        _builder.append("\t");
        final Actor actor = vertex.<Actor>getAdapter(Actor.class);
        _builder.newLineIfNotEmpty();
        {
          final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
            @Override
            public Boolean apply(final Port port) {
              boolean _isNative = port.isNative();
              return Boolean.valueOf((!_isNative));
            }
          };
          Iterable<Port> _filter = IterableExtensions.<Port>filter(actor.getInputs(), _function);
          for(final Port input : _filter) {
            _builder.append("\t");
            final Connection incoming = actor.getIncomingPortMap().get(input);
            _builder.newLineIfNotEmpty();
            {
              if (((incoming != null) && (!processor.getMappedActors().contains(incoming.getSource())))) {
                _builder.append("\t");
                _builder.append("<input name=\"");
                String _name_5 = input.getName();
                _builder.append(_name_5, "\t");
                _builder.append("\">");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<address-space>");
                String _name_6 = processor.getMemory(incoming).getName();
                _builder.append(_name_6, "\t\t");
                _builder.append("</address-space>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<var-name>fifo_");
                String _string = incoming.<Object>getValueAsObject("id").toString();
                _builder.append(_string, "\t\t");
                _builder.append("</var-name>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<signed>");
                boolean _isInt = input.getType().isInt();
                _builder.append(_isInt, "\t\t");
                _builder.append("</signed>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<width>");
                int _width = this.getWidth(input);
                _builder.append(_width, "\t\t");
                _builder.append("</width>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<size>");
                int _safeSize = this.safeSize(incoming);
                _builder.append(_safeSize, "\t\t");
                _builder.append("</size>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<trace>");
                _builder.append(this.path, "\t\t");
                _builder.append("/trace/");
                {
                  boolean _contains = vertex.getLabel().contains("cluster");
                  boolean _not = (!_contains);
                  if (_not) {
                    String _label = vertex.getLabel();
                    _builder.append(_label, "\t\t");
                    _builder.append("_");
                  }
                }
                String _name_7 = input.getName();
                _builder.append(_name_7, "\t\t");
                _builder.append(".txt</trace>");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("</input>");
                _builder.newLine();
              }
            }
          }
        }
        {
          final Function1<Port, Boolean> _function_1 = new Function1<Port, Boolean>() {
            @Override
            public Boolean apply(final Port it) {
              boolean _isNative = it.isNative();
              return Boolean.valueOf((!_isNative));
            }
          };
          Iterable<Port> _filter_1 = IterableExtensions.<Port>filter(actor.getOutputs(), _function_1);
          for(final Port output : _filter_1) {
            {
              List<Connection> _get = actor.getOutgoingPortMap().get(output);
              for(final Connection outgoing : _get) {
                {
                  boolean _contains_1 = processor.getMappedActors().contains(outgoing.getTarget());
                  boolean _not_1 = (!_contains_1);
                  if (_not_1) {
                    _builder.append("\t");
                    final String id = outgoing.<Object>getValueAsObject("id").toString();
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("<output name=\"");
                    String _name_8 = output.getName();
                    _builder.append(_name_8, "\t");
                    _builder.append("_");
                    _builder.append(id, "\t");
                    _builder.append("\">");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("<address-space>");
                    String _name_9 = processor.getMemory(outgoing).getName();
                    _builder.append(_name_9, "\t\t");
                    _builder.append("</address-space>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("<var-name>fifo_");
                    _builder.append(id, "\t\t");
                    _builder.append("</var-name>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("<signed>");
                    boolean _isInt_1 = output.getType().isInt();
                    _builder.append(_isInt_1, "\t\t");
                    _builder.append("</signed>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("<width>");
                    int _width_1 = this.getWidth(output);
                    _builder.append(_width_1, "\t\t");
                    _builder.append("</width>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("<size>");
                    int _safeSize_1 = this.safeSize(outgoing);
                    _builder.append(_safeSize_1, "\t\t");
                    _builder.append("</size>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("<trace>");
                    _builder.append(this.path, "\t\t");
                    _builder.append("/trace/");
                    {
                      boolean _contains_2 = vertex.getLabel().contains("cluster");
                      boolean _not_2 = (!_contains_2);
                      if (_not_2) {
                        String _label_1 = vertex.getLabel();
                        _builder.append(_label_1, "\t\t");
                        _builder.append("_");
                      }
                    }
                    String _name_10 = output.getName();
                    _builder.append(_name_10, "\t\t");
                    _builder.append(".txt</trace>");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("</output>");
                    _builder.newLine();
                  }
                }
              }
            }
          }
        }
      }
    }
    _builder.append("</processor>");
    _builder.newLine();
    return _builder;
  }
  
  private int getWidth(final Port port) {
    int _sizeInBits = port.getType().getSizeInBits();
    double _divide = (_sizeInBits / 8.0);
    return Double.valueOf(Math.ceil(_divide)).intValue();
  }
}
