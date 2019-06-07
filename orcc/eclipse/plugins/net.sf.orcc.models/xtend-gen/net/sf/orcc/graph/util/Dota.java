/**
 * Copyright (c) 2012, Synflow
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
package net.sf.orcc.graph.util;

import java.util.HashMap;
import java.util.Map;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Graph;
import net.sf.orcc.graph.Vertex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * This class defines a (very) simple Graph to DOT converter.
 * 
 * @author Matthieu Wipliez
 */
@SuppressWarnings("all")
public class Dota {
  private Map<Vertex, Integer> vertexMap;
  
  private int rank;
  
  public Dota() {
    HashMap<Vertex, Integer> _hashMap = new HashMap<Vertex, Integer>();
    this.vertexMap = _hashMap;
  }
  
  public CharSequence dot(final Graph graph) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("digraph G {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("node [label=\"\", shape=box];");
    _builder.newLine();
    {
      EList<Vertex> _vertices = graph.getVertices();
      for(final Vertex vertex : _vertices) {
        _builder.append("\t");
        CharSequence _dot = this.dot(vertex);
        _builder.append(_dot, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Edge> _edges = graph.getEdges();
      for(final Edge edge : _edges) {
        _builder.append("\t");
        CharSequence _dot_1 = this.dot(edge);
        _builder.append(_dot_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence dot(final Edge edge) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("node_");
    Integer _id = this.getId(edge.getSource());
    _builder.append(_id);
    _builder.append(" -> node_");
    Integer _id_1 = this.getId(edge.getTarget());
    _builder.append(_id_1);
    _builder.append(" [label=\"");
    String _label = edge.getLabel();
    _builder.append(_label);
    _builder.append("\"];");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence dot(final Vertex vertex) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("node_");
    Integer _id = this.getId(vertex);
    _builder.append(_id);
    _builder.append(" [label=\"(");
    int _number = vertex.getNumber();
    _builder.append(_number);
    _builder.append(") ");
    String _label = vertex.getLabel();
    _builder.append(_label);
    _builder.append("\"];");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private Integer getId(final Vertex vertex) {
    Integer _xblockexpression = null;
    {
      Integer id = this.vertexMap.get(vertex);
      if ((id == null)) {
        id = Integer.valueOf(this.rank);
        this.vertexMap.put(vertex, id);
        this.rank = (this.rank + 1);
      }
      _xblockexpression = id;
    }
    return _xblockexpression;
  }
}
