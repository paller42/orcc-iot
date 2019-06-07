/**
 * Copyright (c) 2013, University of Rennes 1 / IRISA
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
 *   * Neither the name of the University of Rennes 1 / IRISA nor the names of its
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
package net.sf.orcc.tools.stats;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.moc.MoC;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Generate statistics about an application.
 * 
 * @author Hervé Yviquel
 */
@SuppressWarnings("all")
public class StatisticsPrinter {
  public CharSequence getContent(final Network network) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _networkHeader = this.getNetworkHeader();
    _builder.append(_networkHeader);
    _builder.newLineIfNotEmpty();
    CharSequence _stats = this.getStats(network);
    _builder.append(_stats);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _childrenHeader = this.getChildrenHeader();
    _builder.append(_childrenHeader);
    _builder.newLineIfNotEmpty();
    {
      EList<Vertex> _children = network.getChildren();
      for(final Vertex child : _children) {
        CharSequence _stats_1 = this.getStats(child);
        _builder.append(_stats_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    CharSequence _connectionsHeader = this.getConnectionsHeader();
    _builder.append(_connectionsHeader);
    _builder.newLineIfNotEmpty();
    {
      EList<Connection> _connections = network.getConnections();
      for(final Connection conn : _connections) {
        CharSequence _stats_2 = this.getStats(conn);
        _builder.append(_stats_2);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence getNetworkHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Name, Package, Actors, Connections, SDF, CSDF, QSDF, KPN, DPN");
    return _builder;
  }
  
  protected CharSequence getStats(final Network network) {
    StringConcatenation _builder = new StringConcatenation();
    String _simpleName = network.getSimpleName();
    _builder.append(_simpleName);
    _builder.append(", ");
    String _package = network.getPackage();
    _builder.append(_package);
    _builder.append(", ");
    int _size = network.getChildren().size();
    _builder.append(_size);
    _builder.append(", ");
    int _size_1 = network.getConnections().size();
    _builder.append(_size_1);
    _builder.append(", ");
    final Function1<MoC, Boolean> _function = new Function1<MoC, Boolean>() {
      @Override
      public Boolean apply(final MoC it) {
        return Boolean.valueOf(it.isSDF());
      }
    };
    int _size_2 = IterableExtensions.size(IterableExtensions.<MoC>filter(this.getMoCs(network), _function));
    _builder.append(_size_2);
    _builder.append(", ");
    final Function1<MoC, Boolean> _function_1 = new Function1<MoC, Boolean>() {
      @Override
      public Boolean apply(final MoC it) {
        return Boolean.valueOf((it.isCSDF() && (!it.isSDF())));
      }
    };
    int _size_3 = IterableExtensions.size(IterableExtensions.<MoC>filter(this.getMoCs(network), _function_1));
    _builder.append(_size_3);
    _builder.append(", ");
    final Function1<MoC, Boolean> _function_2 = new Function1<MoC, Boolean>() {
      @Override
      public Boolean apply(final MoC it) {
        return Boolean.valueOf(it.isQuasiStatic());
      }
    };
    int _size_4 = IterableExtensions.size(IterableExtensions.<MoC>filter(this.getMoCs(network), _function_2));
    _builder.append(_size_4);
    _builder.append(", ");
    final Function1<MoC, Boolean> _function_3 = new Function1<MoC, Boolean>() {
      @Override
      public Boolean apply(final MoC it) {
        return Boolean.valueOf(it.isKPN());
      }
    };
    int _size_5 = IterableExtensions.size(IterableExtensions.<MoC>filter(this.getMoCs(network), _function_3));
    _builder.append(_size_5);
    _builder.append(", ");
    final Function1<MoC, Boolean> _function_4 = new Function1<MoC, Boolean>() {
      @Override
      public Boolean apply(final MoC it) {
        return Boolean.valueOf(it.isDPN());
      }
    };
    int _size_6 = IterableExtensions.size(IterableExtensions.<MoC>filter(this.getMoCs(network), _function_4));
    _builder.append(_size_6);
    return _builder;
  }
  
  protected CharSequence getChildrenHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Name, Incoming, Outgoing, Inputs, Outputs, Actions, FSM, MoC");
    return _builder;
  }
  
  private CharSequence getStats(final Vertex v) {
    CharSequence _xblockexpression = null;
    {
      final Actor actor = v.<Actor>getAdapter(Actor.class);
      StringConcatenation _builder = new StringConcatenation();
      String _label = v.getLabel();
      _builder.append(_label);
      _builder.append(", ");
      int _size = v.getIncoming().size();
      _builder.append(_size);
      _builder.append(", ");
      int _size_1 = v.getOutgoing().size();
      _builder.append(_size_1);
      _builder.append(", ");
      int _size_2 = actor.getInputs().size();
      _builder.append(_size_2);
      _builder.append(", ");
      int _size_3 = actor.getOutputs().size();
      _builder.append(_size_3);
      _builder.append(", ");
      int _size_4 = actor.getActions().size();
      _builder.append(_size_4);
      _builder.append(", ");
      boolean _hasFsm = actor.hasFsm();
      _builder.append(_hasFsm);
      _builder.append(", ");
      MoC _moC = actor.getMoC();
      String _shortName = null;
      if (_moC!=null) {
        _shortName=_moC.getShortName();
      }
      _builder.append(_shortName);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence getConnectionsHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Source, SrcPort, Target, TgtPort, Size");
    return _builder;
  }
  
  protected CharSequence getStats(final Connection conn) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((conn.getSource() instanceof Actor) && (conn.getTargetPort() instanceof Port))) {
        String _label = conn.getSource().getLabel();
        _builder.append(_label);
        _builder.append(", ");
        String _name = conn.getSourcePort().getName();
        _builder.append(_name);
        _builder.append(", ");
        String _label_1 = conn.getTarget().getLabel();
        _builder.append(_label_1);
        _builder.append(", ");
        String _name_1 = conn.getTargetPort().getName();
        _builder.append(_name_1);
        _builder.append(", ");
        Integer _size = conn.getSize();
        _builder.append(_size);
        _builder.newLineIfNotEmpty();
      } else {
        if (((conn.getSource() instanceof Port) && (conn.getTarget() instanceof Actor))) {
          String _label_2 = conn.getSource().getLabel();
          _builder.append(_label_2);
          _builder.append(", \"\", ");
          String _label_3 = conn.getTarget().getLabel();
          _builder.append(_label_3);
          _builder.append(", ");
          String _name_2 = conn.getTargetPort().getName();
          _builder.append(_name_2);
          _builder.append(", ");
          Integer _size_1 = conn.getSize();
          _builder.append(_size_1);
          _builder.newLineIfNotEmpty();
        } else {
          if (((conn.getSource() instanceof Actor) && (conn.getTarget() instanceof Port))) {
            String _label_4 = conn.getSource().getLabel();
            _builder.append(_label_4);
            _builder.append(", ");
            String _name_3 = conn.getSourcePort().getName();
            _builder.append(_name_3);
            _builder.append(", ");
            String _label_5 = conn.getTarget().getLabel();
            _builder.append(_label_5);
            _builder.append(", \"\", ");
            Integer _size_2 = conn.getSize();
            _builder.append(_size_2);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  private Iterable<MoC> getMoCs(final Network network) {
    final Function1<Vertex, MoC> _function = new Function1<Vertex, MoC>() {
      @Override
      public MoC apply(final Vertex it) {
        Actor _adapter = it.<Actor>getAdapter(Actor.class);
        MoC _moC = null;
        if (_adapter!=null) {
          _moC=_adapter.getMoC();
        }
        return _moC;
      }
    };
    return IterableExtensions.<MoC>filterNull(ListExtensions.<Vertex, MoC>map(network.getChildren(), _function));
  }
}
