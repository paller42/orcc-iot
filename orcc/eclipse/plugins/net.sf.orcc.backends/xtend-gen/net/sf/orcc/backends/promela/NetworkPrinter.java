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
package net.sf.orcc.backends.promela;

import com.google.common.collect.Iterables;
import net.sf.orcc.backends.promela.PromelaTemplate;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Compile top Network Promela source code
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class NetworkPrinter extends PromelaTemplate {
  private Network network;
  
  public Network setNetwork(final Network network) {
    return this.network = network;
  }
  
  public CharSequence getNetworkFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// Generated from \"");
    String _name = this.network.getName();
    _builder.append(_name);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#define uint int");
    _builder.newLine();
    _builder.append("#define SIZE 1");
    _builder.newLine();
    _builder.newLine();
    _builder.append("// FIFO sizes");
    _builder.newLine();
    {
      EList<Connection> _connections = this.network.getConnections();
      for(final Connection connection : _connections) {
        CharSequence _setSizesFifo = this.setSizesFifo(connection);
        _builder.append(_setSizesFifo);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("// FIFO allocation");
    _builder.newLine();
    {
      EList<Connection> _connections_1 = this.network.getConnections();
      for(final Connection connection_1 : _connections_1) {
        CharSequence _allocateFifo = this.allocateFifo(connection_1);
        _builder.append(_allocateFifo);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("// FIFO assignment");
    _builder.newLine();
    {
      EList<Connection> _connections_2 = this.network.getConnections();
      for(final Connection connection_2 : _connections_2) {
        CharSequence _assignFifo = this.assignFifo(connection_2);
        _builder.append(_assignFifo);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("int promela_prog_initiated=0;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("int promela_has_progress=0;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("// Include the actors");
    _builder.newLine();
    _builder.append("#ifdef MANAGED");
    _builder.newLine();
    _builder.append("#include \"tmp_include_actors.pml\"");
    _builder.newLine();
    _builder.append("#else");
    _builder.newLine();
    {
      Iterable<Actor> _filter = Iterables.<Actor>filter(this.network.getChildren(), Actor.class);
      for(final Actor actor : _filter) {
        _builder.append("#include \"");
        String _simpleName = actor.getSimpleName();
        _builder.append(_simpleName);
        _builder.append(".pml\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("#endif");
    _builder.newLine();
    _builder.newLine();
    _builder.append("proctype dummy() {");
    _builder.newLine();
    _builder.append("chan_0?promela_prog_initiated;}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("init {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/*Inputs here*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#ifdef MANAGED");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#include \"tmp_state.pml\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#endif");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("promela_prog_initiated=1;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/*Start processes*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("atomic{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#ifdef MANAGED");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#include \"tmp_start_actors.pml\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#else");
    _builder.newLine();
    {
      Iterable<Actor> _filter_1 = Iterables.<Actor>filter(this.network.getChildren(), Actor.class);
      for(final Actor actor_1 : _filter_1) {
        _builder.append("\t\t");
        _builder.append("run ");
        String _simpleName_1 = actor_1.getSimpleName();
        _builder.append(_simpleName_1, "\t\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.append("#endif");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("#ifdef MANAGED");
    _builder.newLine();
    _builder.append("#include \"tmp_ltl_expr.pml\"");
    _builder.newLine();
    _builder.append("#endif");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence setSizesFifo(final Connection connection) {
    CharSequence _xblockexpression = null;
    {
      Object _xifexpression = null;
      if (((connection.getSourcePort() == null) || (connection.getTargetPort() == null))) {
        _xifexpression = Integer.valueOf(1000);
      } else {
        Object _xifexpression_1 = null;
        Integer _size = connection.getSize();
        boolean _tripleNotEquals = (_size != null);
        if (_tripleNotEquals) {
          _xifexpression_1 = connection.getSize();
        } else {
          _xifexpression_1 = "SIZE";
        }
        _xifexpression = ((Object)_xifexpression_1);
      }
      final Object size = ((Object)_xifexpression);
      StringConcatenation _builder = new StringConcatenation();
      {
        Port _sourcePort = connection.getSourcePort();
        boolean _tripleNotEquals_1 = (_sourcePort != null);
        if (_tripleNotEquals_1) {
          _builder.append("#define chan_");
          Vertex _source = connection.getSource();
          String _simpleName = ((Actor) _source).getSimpleName();
          _builder.append(_simpleName);
          _builder.append("_");
          String _name = connection.getSourcePort().getName();
          _builder.append(_name);
          _builder.append("_SIZE ");
          _builder.append(((Object)size));
          _builder.newLineIfNotEmpty();
        }
      }
      {
        Port _targetPort = connection.getTargetPort();
        boolean _tripleNotEquals_2 = (_targetPort != null);
        if (_tripleNotEquals_2) {
          _builder.append("#define chan_");
          Vertex _target = connection.getTarget();
          String _simpleName_1 = ((Actor) _target).getSimpleName();
          _builder.append(_simpleName_1);
          _builder.append("_");
          String _name_1 = connection.getTargetPort().getName();
          _builder.append(_name_1);
          _builder.append("_SIZE ");
          _builder.append(((Object)size));
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public CharSequence allocateFifo(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Port _sourcePort = connection.getSourcePort();
      boolean _tripleNotEquals = (_sourcePort != null);
      if (_tripleNotEquals) {
        _builder.append("chan chan_");
        Object _valueAsObject = connection.<Object>getValueAsObject("id");
        _builder.append(_valueAsObject);
        _builder.append(" = [chan_");
        Vertex _source = connection.getSource();
        String _simpleName = ((Actor) _source).getSimpleName();
        _builder.append(_simpleName);
        _builder.append("_");
        String _name = connection.getSourcePort().getName();
        _builder.append(_name);
        _builder.append("_SIZE] of {");
        CharSequence _doSwitch = this.doSwitch(connection.getSourcePort().getType());
        _builder.append(_doSwitch);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("chan chan_");
        Object _valueAsObject_1 = connection.<Object>getValueAsObject("id");
        _builder.append(_valueAsObject_1);
        _builder.append(" = [chan_");
        Vertex _target = connection.getTarget();
        String _simpleName_1 = ((Actor) _target).getSimpleName();
        _builder.append(_simpleName_1);
        _builder.append("_");
        String _name_1 = connection.getTargetPort().getName();
        _builder.append(_name_1);
        _builder.append("_SIZE] of {");
        CharSequence _doSwitch_1 = this.doSwitch(connection.getTargetPort().getType());
        _builder.append(_doSwitch_1);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence assignFifo(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Port _sourcePort = connection.getSourcePort();
      boolean _tripleNotEquals = (_sourcePort != null);
      if (_tripleNotEquals) {
        _builder.append("#define chan_");
        Vertex _source = connection.getSource();
        String _simpleName = ((Actor) _source).getSimpleName();
        _builder.append(_simpleName);
        _builder.append("_");
        String _name = connection.getSourcePort().getName();
        _builder.append(_name);
        _builder.append(" chan_");
        Object _valueAsObject = connection.<Object>getValueAsObject("id");
        _builder.append(_valueAsObject);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Port _targetPort = connection.getTargetPort();
      boolean _tripleNotEquals_1 = (_targetPort != null);
      if (_tripleNotEquals_1) {
        _builder.append("#define chan_");
        Vertex _target = connection.getTarget();
        String _simpleName_1 = ((Actor) _target).getSimpleName();
        _builder.append(_simpleName_1);
        _builder.append("_");
        String _name_1 = connection.getTargetPort().getName();
        _builder.append(_name_1);
        _builder.append(" chan_");
        Object _valueAsObject_1 = connection.<Object>getValueAsObject("id");
        _builder.append(_valueAsObject_1);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
