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
package net.sf.orcc.backends.c.preesm;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.util.OrccLogger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generate network as graphml file
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class NetworkPrinter extends CTemplate {
  private Network network;
  
  public Network setNetwork(final Network network) {
    return this.network = network;
  }
  
  public CharSequence getSizesCSV() {
    CharSequence _xblockexpression = null;
    {
      ArrayList<String> keys = CollectionLiterals.<String>newArrayList("i64", "i32", "i16", "i8");
      ArrayList<Integer> values = CollectionLiterals.<Integer>newArrayList(Integer.valueOf(8), Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(1));
      Iterable<Instance> _filter = Iterables.<Instance>filter(this.network.getChildren(), Instance.class);
      for (final Instance instance : _filter) {
        boolean _isEmpty = instance.getActor().getStateVars().isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          StringConcatenation _builder = new StringConcatenation();
          String _simpleName = instance.getActor().getSimpleName();
          _builder.append(_simpleName);
          _builder.append("_stateVars");
          keys.add(_builder.toString());
          final Function2<Integer, Var, Integer> _function = new Function2<Integer, Var, Integer>() {
            @Override
            public Integer apply(final Integer total, final Var v) {
              int _sizeInBits = v.getType().getSizeInBits();
              return Integer.valueOf(((total).intValue() + _sizeInBits));
            }
          };
          final Integer structSize = IterableExtensions.<Var, Integer>fold(instance.getActor().getStateVars(), Integer.valueOf(0), _function);
          if ((((structSize).intValue() % 8) == 0)) {
            values.add(Integer.valueOf(((structSize).intValue() / 8)));
          } else {
            OrccLogger.warnln("State variables seems to have not been resized.");
            values.add(Integer.valueOf((((structSize).intValue() / 8) + 1)));
          }
        }
      }
      StringConcatenation _builder_1 = new StringConcatenation();
      String _join = IterableExtensions.join(keys, ",");
      _builder_1.append(_join);
      _builder_1.newLineIfNotEmpty();
      String _join_1 = IterableExtensions.join(values, ",");
      _builder_1.append(_join_1);
      _builder_1.newLineIfNotEmpty();
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }
  
  public CharSequence getNetworkContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"graph_desc\" attr.type=\"string\" for=\"node\" id=\"graph_desc\"/>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"name\" attr.type=\"string\" for=\"graph\" id=\"name\"/>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"name\" attr.type=\"string\" for=\"node\" id=\"name\"/>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"arguments\" attr.type=\"string\" for=\"node\" id=\"arguments\"/>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"parameters\" attr.type=\"string\" for=\"graph\" id=\"parameters\"/>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"variables\" attr.type=\"string\" for=\"graph\" id=\"variables\"/>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"edge_prod\" attr.type=\"string\" for=\"edge\" id=\"edge_prod\">");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("<desc>org.sdf4j.model.sdf.types.SDFNumericalEdgePropertyTypeFactory</desc>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("</key>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"edge_delay\" attr.type=\"string\" for=\"edge\" id=\"edge_delay\">");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("<desc>org.sdf4j.model.sdf.types.SDFNumericalEdgePropertyTypeFactory</desc>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("</key>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"edge_cons\" attr.type=\"string\" for=\"edge\" id=\"edge_cons\">");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("<desc>org.sdf4j.model.sdf.types.SDFNumericalEdgePropertyTypeFactory</desc>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("</key>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<key attr.name=\"data_type\" attr.type=\"string\" for=\"edge\" id=\"data_type\">");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("<desc>org.sdf4j.model.sdf.types.SDFTextualEdgePropertyTypeFactory</desc>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("</key>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<graph edgedefault=\"directed\">");
    _builder.newLine();
    {
      String _simpleName = this.network.getSimpleName();
      boolean _tripleNotEquals = (_simpleName != null);
      if (_tripleNotEquals) {
        _builder.append("\t    ");
        _builder.append("<data key=\"name\">");
        String _simpleName_1 = this.network.getSimpleName();
        _builder.append(_simpleName_1, "\t    ");
        _builder.append("</data>");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t    ");
        _builder.append("<data key=\"name\" />");
        _builder.newLine();
      }
    }
    {
      boolean _isEmpty = this.network.getParameters().isEmpty();
      if (_isEmpty) {
        _builder.append("\t    ");
        _builder.append("<data key=\"parameters\"/>");
        _builder.newLine();
      } else {
        _builder.append("\t    ");
        _builder.append("<data key=\"parameters\">");
        _builder.newLine();
        {
          EList<Var> _parameters = this.network.getParameters();
          for(final Var parameter : _parameters) {
            _builder.append("\t    ");
            _builder.append("\t");
            _builder.append("<parameter name=\"");
            String _name = parameter.getName();
            _builder.append(_name, "\t    \t");
            _builder.append("\"/>");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t    ");
        _builder.append("</data>");
        _builder.newLine();
      }
    }
    {
      boolean _isEmpty_1 = this.network.getVariables().isEmpty();
      if (_isEmpty_1) {
        _builder.append("\t\t");
        _builder.append("<data key=\"variables\"/>");
        _builder.newLine();
      } else {
        _builder.append("\t\t");
        _builder.append("<data key=\"variables\">");
        _builder.newLine();
        {
          EList<Var> _variables = this.network.getVariables();
          for(final Var variable : _variables) {
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<variable name=\"");
            String _name_1 = variable.getName();
            _builder.append(_name_1, "\t\t\t");
            _builder.append("\" value=\"");
            CharSequence _doSwitch = this.doSwitch(variable.getInitialValue());
            _builder.append(_doSwitch, "\t\t\t");
            _builder.append("\"/>");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t\t");
        _builder.append("</data>");
        _builder.newLine();
      }
    }
    {
      Iterable<Instance> _filter = Iterables.<Instance>filter(this.network.getChildren(), Instance.class);
      for(final Instance instance : _filter) {
        _builder.append("\t    ");
        CharSequence _print = this.print(instance);
        _builder.append(_print, "\t    ");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Connection> _connections = this.network.getConnections();
      for(final Connection conn : _connections) {
        _builder.append("\t    ");
        CharSequence _print_1 = this.print(conn);
        _builder.append(_print_1, "\t    ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    ");
    _builder.append("</graph>");
    _builder.newLine();
    _builder.append("</graphml>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence print(final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<edge source=\"");
    Vertex _source = connection.getSource();
    String _name = ((Instance) _source).getName();
    _builder.append(_name);
    _builder.append("\" sourceport=\"");
    String _name_1 = connection.getSourcePort().getName();
    _builder.append(_name_1);
    _builder.append("\" target=\"");
    Vertex _target = connection.getTarget();
    String _name_2 = ((Instance) _target).getName();
    _builder.append(_name_2);
    _builder.append("\" targetport=\"");
    String _name_3 = connection.getTargetPort().getName();
    _builder.append(_name_3);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("<data key=\"edge_prod\">");
    Vertex _source_1 = connection.getSource();
    MoC _moC = ((Instance) _source_1).getActor().getMoC();
    Integer _get = ((CSDFMoC) _moC).getOutputPattern().getNumTokensMap().get(connection.getSourcePort());
    _builder.append(_get, "    ");
    _builder.append("</data>");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("<data key=\"edge_delay\">");
    Object _printDelays = this.printDelays(connection);
    _builder.append(_printDelays, "    ");
    _builder.append("</data>");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("<data key=\"edge_cons\">");
    Vertex _target_1 = connection.getTarget();
    MoC _moC_1 = ((Instance) _target_1).getActor().getMoC();
    Integer _get_1 = ((CSDFMoC) _moC_1).getInputPattern().getNumTokensMap().get(connection.getTargetPort());
    _builder.append(_get_1, "    ");
    _builder.append("</data>");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("<data key=\"data_type\">");
    CharSequence _doSwitch = this.doSwitch(connection.getSourcePort().getType());
    _builder.append(_doSwitch, "    ");
    _builder.append("</data>");
    _builder.newLineIfNotEmpty();
    _builder.append("</edge>");
    _builder.newLine();
    return _builder;
  }
  
  private Object printDelays(final Connection connection) {
    Object _xifexpression = null;
    Vertex _source = connection.getSource();
    MoC _moC = ((Instance) _source).getActor().getMoC();
    boolean _containsKey = ((CSDFMoC) _moC).getDelayPattern().getNumTokensMap().containsKey(connection.getSourcePort());
    if (_containsKey) {
      Vertex _source_1 = connection.getSource();
      MoC _moC_1 = ((Instance) _source_1).getActor().getMoC();
      _xifexpression = ((CSDFMoC) _moC_1).getDelayPattern().getNumTokensMap().get(connection.getSourcePort());
    } else {
      _xifexpression = "0";
    }
    return _xifexpression;
  }
  
  private CharSequence print(final Instance instance) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<node id=\"");
    String _name = instance.getName();
    _builder.append(_name);
    _builder.append("\" kind=\"vertex\">");
    _builder.newLineIfNotEmpty();
    _builder.append("<data key=\"graph_desc\">../Code/IDL/");
    String _simpleName = instance.getActor().getSimpleName();
    _builder.append(_simpleName);
    _builder.append(".idl</data>");
    _builder.newLineIfNotEmpty();
    _builder.append("<data key=\"name\">");
    String _simpleName_1 = instance.getActor().getSimpleName();
    _builder.append(_simpleName_1);
    _builder.append("</data>");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = instance.getArguments().isEmpty();
      if (_isEmpty) {
        _builder.append("\t");
        _builder.append("<data key=\"arguments\"/>");
        _builder.newLine();
      } else {
        {
          EList<Argument> _arguments = instance.getArguments();
          for(final Argument arg : _arguments) {
            _builder.append("\t");
            CharSequence _print = this.print(arg);
            _builder.append(_print, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("</node>");
    _builder.newLine();
    {
      boolean _isEmpty_1 = instance.getActor().getStateVars().isEmpty();
      boolean _not = (!_isEmpty_1);
      if (_not) {
        _builder.append("<edge source=\"");
        String _name_1 = instance.getName();
        _builder.append(_name_1);
        _builder.append("\" sourceport=\"stateVars_o\" target=\"");
        String _name_2 = instance.getName();
        _builder.append(_name_2);
        _builder.append("\" targetport=\"stateVars_i\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<data key=\"edge_prod\">1</data>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<data key=\"edge_delay\">1</data>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<data key=\"edge_cons\">1</data>");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("<data key=\"data_type\">struct StateVars *</data>");
        _builder.newLine();
        _builder.append("</edge>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence print(final Argument argument) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<data key=\"arguments\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<argument name=\"");
    String _name = argument.getVariable().getName();
    _builder.append(_name, "\t");
    _builder.append("\" value=\"");
    CharSequence _doSwitch = this.doSwitch(argument.getValue());
    _builder.append(_doSwitch, "\t");
    _builder.append("\"/>");
    _builder.newLineIfNotEmpty();
    _builder.append("</data>");
    _builder.newLine();
    return _builder;
  }
}
