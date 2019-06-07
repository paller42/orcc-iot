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
package net.sf.orcc.backends.c;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.sf.orcc.backends.BackendsConstants;
import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Var;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generate and print network source file for C backend.
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class NetworkPrinter extends CTemplate {
  protected Network network;
  
  protected boolean profile = false;
  
  protected boolean newSchedul = false;
  
  private boolean papify = false;
  
  private boolean papifyMultiplex = false;
  
  private boolean genWeights = false;
  
  private int genWeightsActionDataCounter = 0;
  
  private int genWeightsSchedulerDataCounter = 0;
  
  private boolean linkNativeLib;
  
  private String linkNativeLibHeaders;
  
  public Network setNetwork(final Network network) {
    return this.network = network;
  }
  
  @Override
  public void setOptions(final Map<String, Object> options) {
    super.setOptions(options);
    boolean _containsKey = options.containsKey(BackendsConstants.PROFILE);
    if (_containsKey) {
      Object _get = options.get(BackendsConstants.PROFILE);
      this.profile = (((Boolean) _get)).booleanValue();
    }
    boolean _containsKey_1 = options.containsKey(BackendsConstants.NEW_SCHEDULER);
    if (_containsKey_1) {
      Object _get_1 = options.get(BackendsConstants.NEW_SCHEDULER);
      this.newSchedul = (((Boolean) _get_1)).booleanValue();
    }
    boolean _containsKey_2 = options.containsKey(BackendsConstants.PAPIFY);
    if (_containsKey_2) {
      Object _get_2 = options.get(BackendsConstants.PAPIFY);
      this.papify = (((Boolean) _get_2)).booleanValue();
      boolean _containsKey_3 = options.containsKey(BackendsConstants.PAPIFY_MULTIPLEX);
      if (_containsKey_3) {
        Object _get_3 = options.get(BackendsConstants.PAPIFY_MULTIPLEX);
        this.papifyMultiplex = (((Boolean) _get_3)).booleanValue();
      }
    }
    boolean _containsKey_4 = options.containsKey(BackendsConstants.GEN_WEIGHTS);
    if (_containsKey_4) {
      Object _get_4 = options.get(BackendsConstants.GEN_WEIGHTS);
      this.genWeights = (((Boolean) _get_4)).booleanValue();
      this.genWeightsActionDataCounter = 0;
      this.genWeightsSchedulerDataCounter = 0;
    }
    boolean _containsKey_5 = options.containsKey(BackendsConstants.LINK_NATIVE_LIBRARY);
    if (_containsKey_5) {
      Object _get_5 = options.get(BackendsConstants.LINK_NATIVE_LIBRARY);
      this.linkNativeLib = (((Boolean) _get_5)).booleanValue();
      Object _get_6 = options.get(BackendsConstants.LINK_NATIVE_LIBRARY_HEADERS);
      this.linkNativeLibHeaders = ((String) _get_6);
    }
  }
  
  protected CharSequence getNetworkFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
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
    _builder.append("#include <string.h>");
    _builder.newLine();
    CharSequence _printAdditionalIncludes = this.printAdditionalIncludes();
    _builder.append(_printAdditionalIncludes);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include \"types.h\"");
    _builder.newLine();
    _builder.append("#include \"fifo.h\"");
    _builder.newLine();
    _builder.append("#include \"util.h\"");
    _builder.newLine();
    _builder.append("#include \"dataflow.h\"");
    _builder.newLine();
    _builder.append("#include \"serialize.h\"");
    _builder.newLine();
    _builder.append("#include \"options.h\"");
    _builder.newLine();
    _builder.append("#include \"scheduler.h\"");
    _builder.newLine();
    _builder.newLine();
    {
      if (this.genWeights) {
        _builder.append("#include \"rdtsc.h\"");
        _builder.newLine();
        _builder.append("#include <stdint.h>");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("// Server address");
    _builder.newLine();
    _builder.append("#define SERVER_ADDRESS_BUFLEN\t128");
    _builder.newLine();
    _builder.append("char server_address[SERVER_ADDRESS_BUFLEN+1];");
    _builder.newLine();
    _builder.append("#define DEFAULT_SERVER_PORT\t8080");
    _builder.newLine();
    _builder.append("int server_port;");
    _builder.newLine();
    _builder.newLine();
    {
      if ((this.linkNativeLib && (!Objects.equal(this.linkNativeLibHeaders, "")))) {
        CharSequence _printNativeLibHeaders = this.printNativeLibHeaders(this.linkNativeLibHeaders);
        _builder.append(_printNativeLibHeaders);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// FIFO allocation");
    _builder.newLine();
    {
      EList<Port> _inputs = this.network.getInputs();
      for(final Port child : _inputs) {
        CharSequence _allocateFifos = this.allocateFifos(child);
        _builder.append(_allocateFifos);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<Vertex> _children = this.network.getChildren();
      for(final Vertex child_1 : _children) {
        CharSequence _allocateFifos_1 = this.allocateFifos(child_1);
        _builder.append(_allocateFifos_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// FIFO pointer assignments");
    _builder.newLine();
    {
      EList<Port> _inputs_1 = this.network.getInputs();
      for(final Port child_2 : _inputs_1) {
        CharSequence _assignFifo = this.assignFifo(child_2);
        _builder.append(_assignFifo);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<Vertex> _children_1 = this.network.getChildren();
      for(final Vertex child_3 : _children_1) {
        CharSequence _assignFifo_1 = this.assignFifo(child_3);
        _builder.append(_assignFifo_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      if (this.profile) {
        _builder.append("/////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Declaration of the actions");
        _builder.newLine();
        {
          EList<Vertex> _children_2 = this.network.getChildren();
          for(final Vertex child_4 : _children_2) {
            {
              EList<Action> _actions = child_4.<Actor>getAdapter(Actor.class).getActions();
              for(final Action action : _actions) {
                _builder.append("action_t action_");
                String _label = child_4.getLabel();
                _builder.append(_label);
                _builder.append("_");
                String _name_1 = action.getName();
                _builder.append(_name_1);
                _builder.append(" = {\"");
                String _name_2 = action.getName();
                _builder.append(_name_2);
                _builder.append("\", 0, 0, -1, -1, -1, 0, 0};");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        {
          EList<Vertex> _children_3 = this.network.getChildren();
          for(final Vertex child_5 : _children_3) {
            _builder.append("action_t *");
            String _label_1 = child_5.getLabel();
            _builder.append(_label_1);
            _builder.append("_actions[] = {");
            _builder.newLineIfNotEmpty();
            {
              EList<Action> _actions_1 = child_5.<Actor>getAdapter(Actor.class).getActions();
              boolean _hasElements = false;
              for(final Action action_1 : _actions_1) {
                if (!_hasElements) {
                  _hasElements = true;
                } else {
                  _builder.appendImmediate(",", "\t");
                }
                _builder.append("\t");
                _builder.append("&action_");
                String _label_2 = child_5.getLabel();
                _builder.append(_label_2, "\t");
                _builder.append("_");
                String _name_3 = action_1.getName();
                _builder.append(_name_3, "\t");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("};");
            _builder.newLine();
            _builder.newLine();
          }
        }
      }
    }
    {
      if (this.genWeights) {
        _builder.append("/////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Declare rdtsc_data for the actors/actions");
        _builder.newLine();
        {
          EList<Vertex> _children_4 = this.network.getChildren();
          for(final Vertex child_6 : _children_4) {
            CharSequence _allocateGenWeightsActionData = this.allocateGenWeightsActionData(child_6);
            _builder.append(_allocateGenWeightsActionData);
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("// Declare rdtsc_data for the actors/scheduler");
        _builder.newLine();
        {
          EList<Vertex> _children_5 = this.network.getChildren();
          for(final Vertex child_7 : _children_5) {
            CharSequence _allocateGenWeightsSchedulerData = this.allocateGenWeightsSchedulerData(child_7);
            _builder.append(_allocateGenWeightsSchedulerData);
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
      }
    }
    CharSequence _additionalDeclarations = this.additionalDeclarations();
    _builder.append(_additionalDeclarations);
    _builder.newLineIfNotEmpty();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Actor functions");
    _builder.newLine();
    {
      EList<Vertex> _children_6 = this.network.getChildren();
      for(final Vertex child_8 : _children_6) {
        _builder.append("extern void ");
        String _label_3 = child_8.getLabel();
        _builder.append(_label_3);
        _builder.append("_initialize(schedinfo_t *si);");
        _builder.newLineIfNotEmpty();
        _builder.append("extern void ");
        String _label_4 = child_8.getLabel();
        _builder.append(_label_4);
        _builder.append("_scheduler(schedinfo_t *si);");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _inputs_2 = this.network.getInputs();
      for(final Port child_9 : _inputs_2) {
        _builder.append("extern void ");
        String _name_4 = child_9.getName();
        _builder.append(_name_4);
        _builder.append("_initialize(schedinfo_t *si);");
        _builder.newLineIfNotEmpty();
        _builder.append("extern void ");
        String _name_5 = child_9.getName();
        _builder.append(_name_5);
        _builder.append("_scheduler(schedinfo_t *si);");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _outputs = this.network.getOutputs();
      for(final Port child_10 : _outputs) {
        _builder.append("extern void ");
        String _name_6 = child_10.getName();
        _builder.append(_name_6);
        _builder.append("_initialize(schedinfo_t *si);");
        _builder.newLineIfNotEmpty();
        _builder.append("extern void ");
        String _name_7 = child_10.getName();
        _builder.append(_name_7);
        _builder.append("_scheduler(schedinfo_t *si);");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Declaration of the actors array");
    _builder.newLine();
    {
      EList<Vertex> _children_7 = this.network.getChildren();
      for(final Vertex child_11 : _children_7) {
        {
          if (this.profile) {
            _builder.append("actor_t ");
            String _label_5 = child_11.getLabel();
            _builder.append(_label_5);
            _builder.append(" = {\"");
            String _label_6 = child_11.getLabel();
            _builder.append(_label_6);
            _builder.append("\", ");
            String _label_7 = child_11.getLabel();
            _builder.append(_label_7);
            _builder.append("_initialize, ");
            String _label_8 = child_11.getLabel();
            _builder.append(_label_8);
            _builder.append("_scheduler, 0, 0, 0, 0, NULL, -1, ");
            int _indexOf = this.network.getChildren().indexOf(child_11);
            _builder.append(_indexOf);
            _builder.append(", 0, 1, 0, 0, 0, ");
            String _label_9 = child_11.getLabel();
            _builder.append(_label_9);
            _builder.append("_actions, ");
            int _size = child_11.<Actor>getAdapter(Actor.class).getActions().size();
            _builder.append(_size);
            _builder.append(", 0, \"");
            String _replace = child_11.<Actor>getAdapter(Actor.class).getFile().getProjectRelativePath().removeFirstSegments(1).removeFileExtension().toString().replace("/", ".");
            _builder.append(_replace);
            _builder.append("\", 0, 0, 0};");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("actor_t ");
            String _label_10 = child_11.getLabel();
            _builder.append(_label_10);
            _builder.append(" = {\"");
            String _label_11 = child_11.getLabel();
            _builder.append(_label_11);
            _builder.append("\", ");
            String _label_12 = child_11.getLabel();
            _builder.append(_label_12);
            _builder.append("_initialize, ");
            String _label_13 = child_11.getLabel();
            _builder.append(_label_13);
            _builder.append("_scheduler, 0, 0, 0, 0, NULL, -1, ");
            int _indexOf_1 = this.network.getChildren().indexOf(child_11);
            _builder.append(_indexOf_1);
            _builder.append(", 0, 1, 0, 0, 0, NULL, 0, 0, \"\", 0, 0, 0};");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      EList<Port> _inputs_3 = this.network.getInputs();
      for(final Port child_12 : _inputs_3) {
        _builder.append("actor_t ");
        String _name_8 = child_12.getName();
        _builder.append(_name_8);
        _builder.append(" = {\"");
        String _name_9 = child_12.getName();
        _builder.append(_name_9);
        _builder.append("\", ");
        String _name_10 = child_12.getName();
        _builder.append(_name_10);
        _builder.append("_initialize, ");
        String _name_11 = child_12.getName();
        _builder.append(_name_11);
        _builder.append("_scheduler, 0, 0, 0, 0, NULL, -1, 0, 0, 1, 0, 0, 0, NULL, 0, 0, \"\", 0, 0, 0};");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _outputs_1 = this.network.getOutputs();
      for(final Port child_13 : _outputs_1) {
        _builder.append("actor_t ");
        String _name_12 = child_13.getName();
        _builder.append(_name_12);
        _builder.append(" = {\"");
        String _name_13 = child_13.getName();
        _builder.append(_name_13);
        _builder.append("\", ");
        String _name_14 = child_13.getName();
        _builder.append(_name_14);
        _builder.append("_initialize, ");
        String _name_15 = child_13.getName();
        _builder.append(_name_15);
        _builder.append("_scheduler, 0, 0, 0, 0, NULL, -1, 0, 0, 1, 0, 0, 0, NULL, 0, 0, \"\", 0, 0, 0};");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("actor_t *actors[] = {");
    _builder.newLine();
    {
      EList<Vertex> _children_8 = this.network.getChildren();
      boolean _hasElements_1 = false;
      for(final Vertex child_14 : _children_8) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate(",", "\t");
        }
        _builder.append("\t");
        _builder.append("&");
        String _label_14 = child_14.getLabel();
        _builder.append(_label_14, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      int _size_1 = this.network.getOutputs().size();
      boolean _greaterThan = (_size_1 > 0);
      if (_greaterThan) {
        _builder.append("\t");
        _builder.append(",");
        _builder.newLine();
        {
          EList<Port> _outputs_2 = this.network.getOutputs();
          boolean _hasElements_2 = false;
          for(final Port child_15 : _outputs_2) {
            if (!_hasElements_2) {
              _hasElements_2 = true;
            } else {
              _builder.appendImmediate(",", "\t");
            }
            _builder.append("\t");
            _builder.append("&");
            String _name_16 = child_15.getName();
            _builder.append(_name_16, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      int _size_2 = this.network.getInputs().size();
      boolean _greaterThan_1 = (_size_2 > 0);
      if (_greaterThan_1) {
        _builder.append("\t");
        _builder.append(",");
        _builder.newLine();
        {
          EList<Port> _inputs_4 = this.network.getInputs();
          boolean _hasElements_3 = false;
          for(final Port child_16 : _inputs_4) {
            if (!_hasElements_3) {
              _hasElements_3 = true;
            } else {
              _builder.appendImmediate(",", "\t");
            }
            _builder.append("\t");
            _builder.append("&");
            String _name_17 = child_16.getName();
            _builder.append(_name_17, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Declaration of the connections array");
    _builder.newLine();
    {
      EList<Connection> _connections = this.network.getConnections();
      for(final Connection connection : _connections) {
        _builder.append("connection_t connection_");
        String _label_15 = connection.getSource().getLabel();
        _builder.append(_label_15);
        _builder.append("_");
        String _label_16 = connection.getTarget().getLabel();
        _builder.append(_label_16);
        _builder.append(" = {&");
        String _label_17 = connection.getSource().getLabel();
        _builder.append(_label_17);
        _builder.append(", &");
        String _label_18 = connection.getTarget().getLabel();
        _builder.append(_label_18);
        _builder.append(", 0, 0};");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("connection_t *connections[] = {");
    _builder.newLine();
    {
      EList<Connection> _connections_1 = this.network.getConnections();
      boolean _hasElements_4 = false;
      for(final Connection connection_1 : _connections_1) {
        if (!_hasElements_4) {
          _hasElements_4 = true;
        } else {
          _builder.appendImmediate(",", "\t");
        }
        _builder.append("\t");
        _builder.append("&connection_");
        String _label_19 = connection_1.getSource().getLabel();
        _builder.append(_label_19, "\t");
        _builder.append("_");
        String _label_20 = connection_1.getTarget().getLabel();
        _builder.append(_label_20, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Declaration of the network");
    _builder.newLine();
    _builder.append("network_t network = {\"");
    String _name_18 = this.network.getName();
    _builder.append(_name_18);
    _builder.append("\", actors, connections, ");
    int _size_3 = this.network.getAllActors().size();
    int _size_4 = this.network.getOutputs().size();
    int _plus = (_size_3 + _size_4);
    int _size_5 = this.network.getInputs().size();
    int _plus_1 = (_plus + _size_5);
    _builder.append(_plus_1);
    _builder.append(", ");
    int _size_6 = this.network.getConnections().size();
    _builder.append(_size_6);
    _builder.append("};");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _hasAttribute = this.network.hasAttribute("network_shared_variables");
      if (_hasAttribute) {
        _builder.append("/////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Shared Variables");
        _builder.newLine();
        {
          Object _objectValue = this.network.getAttribute("network_shared_variables").getObjectValue();
          for(final Var v : ((HashSet<Var>) _objectValue)) {
            CharSequence _doSwitch = this.doSwitch(v.getType());
            _builder.append(_doSwitch);
            _builder.append(" ");
            String _name_19 = v.getName();
            _builder.append(_name_19);
            {
              List<Integer> _dimensions = v.getType().getDimensions();
              for(final Integer dim : _dimensions) {
                _builder.append("[");
                _builder.append(dim);
                _builder.append("]");
              }
            }
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("static void parse_port() {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("char *colonp;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if( ( colonp = strchr( server_address , \':\' ) ) != NULL ) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("*colonp = 0;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("++colonp;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if( sscanf( colonp,\"%u\",&server_port ) < 1 )");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("server_port = DEFAULT_SERVER_PORT;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} else");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("server_port = DEFAULT_SERVER_PORT;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Main");
    _builder.newLine();
    _builder.append("int main(int argc, char *argv[]) {");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _beforeMain = this.beforeMain();
    _builder.append(_beforeMain, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("if( argc <= 1 ) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printf( \"Usage: ");
    String _name_20 = this.network.getName();
    _builder.append(_name_20, "\t\t");
    _builder.append(" <server address>\\n\" );");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("return 1;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("strncpy( server_address,argv[1],SERVER_ADDRESS_BUFLEN); server_address[SERVER_ADDRESS_BUFLEN] = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("parse_port();");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("options_t *opt = init_orcc(argc, argv);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("set_scheduling_strategy(");
    {
      if ((!this.newSchedul)) {
        _builder.append("\"RR\"");
      } else {
        _builder.append("\"DD\"");
      }
    }
    _builder.append(", opt);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("launcher(opt, &network);");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _afterMain = this.afterMain();
    _builder.append(_afterMain, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return compareErrors;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence assignFifo(final Vertex vertex) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((vertex instanceof Actor)) {
        {
          Collection<List<Connection>> _values = ((Actor)vertex).<Entity>getAdapter(Entity.class).getOutgoingPortMap().values();
          for(final List<Connection> connList : _values) {
            CharSequence _printFifoAssign = this.printFifoAssign(IterableExtensions.<Connection>head(connList).getSource().getLabel(), IterableExtensions.<Connection>head(connList).getSourcePort(), (IterableExtensions.<Connection>head(connList).<Integer>getValueAsObject("idNoBcast")).intValue());
            _builder.append(_printFifoAssign);
            _builder.newLineIfNotEmpty();
            {
              for(final Connection conn : connList) {
                {
                  if (((conn.getTargetPort() == null) && (conn.getTarget() instanceof Port))) {
                    Vertex _target = conn.getTarget();
                    CharSequence _printOutputPortFifoAssign = this.printOutputPortFifoAssign(((Port) _target), (conn.<Integer>getValueAsObject("idNoBcast")).intValue());
                    _builder.append(_printOutputPortFifoAssign);
                    _builder.newLineIfNotEmpty();
                  } else {
                    CharSequence _printFifoAssign_1 = this.printFifoAssign(conn.getTarget().getLabel(), conn.getTargetPort(), (conn.<Integer>getValueAsObject("idNoBcast")).intValue());
                    _builder.append(_printFifoAssign_1);
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
      } else {
        {
          int _size = ((Port) vertex).getOutgoing().size();
          boolean _greaterThan = (_size > 0);
          if (_greaterThan) {
            CharSequence _printInputPortFifoAssign = this.printInputPortFifoAssign(((Port) vertex), (((Port) vertex).getOutgoing().get(0).<Integer>getValueAsObject("idNoBcast")).intValue());
            _builder.append(_printInputPortFifoAssign);
            _builder.newLineIfNotEmpty();
            {
              EList<Edge> _outgoing = ((Port) vertex).getOutgoing();
              for(final Edge conn_1 : _outgoing) {
                CharSequence _printFifoAssign_2 = this.printFifoAssign(((Connection) conn_1).getTarget().getLabel(), ((Connection) conn_1).getTargetPort(), (conn_1.<Integer>getValueAsObject("idNoBcast")).intValue());
                _builder.append(_printFifoAssign_2);
                _builder.newLineIfNotEmpty();
              }
            }
          } else {
            CharSequence _printOutputPortFifoAssign_1 = this.printOutputPortFifoAssign(((Port) vertex), (((Port) vertex).getOutgoing().get(0).<Integer>getValueAsObject("idNoBcast")).intValue());
            _builder.append(_printOutputPortFifoAssign_1);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  protected CharSequence printFifoAssign(final String name, final Port port, final int fifoIndex) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fifo_");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch);
    _builder.append("_t *");
    _builder.append(name);
    _builder.append("_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" = &fifo_");
    _builder.append(fifoIndex);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence printInputPortFifoAssign(final Port port, final int fifoIndex) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fifo_");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch);
    _builder.append("_t *");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_O = &fifo_");
    _builder.append(fifoIndex);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence printOutputPortFifoAssign(final Port port, final int fifoIndex) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fifo_");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch);
    _builder.append("_t *");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_I = &fifo_");
    _builder.append(fifoIndex);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence allocateFifos(final Vertex vertex) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Collection<List<Connection>> _values = vertex.<Entity>getAdapter(Entity.class).getOutgoingPortMap().values();
      for(final List<Connection> connectionList : _values) {
        CharSequence _allocateFifo = this.allocateFifo(connectionList.get(0), connectionList.size());
        _builder.append(_allocateFifo);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence allocateFifos(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Edge> _outgoing = port.getOutgoing();
      for(final Edge connectionList : _outgoing) {
        CharSequence _allocateFifo = this.allocateFifo(((Connection) connectionList), port.getOutgoing().size());
        _builder.append(_allocateFifo);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence allocateFifo(final Connection conn, final int nbReaders) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Vertex _source = conn.getSource();
      if ((_source instanceof Port)) {
        _builder.append("DECLARE_FIFO(");
        Vertex _source_1 = conn.getSource();
        CharSequence _doSwitch = this.doSwitch(((Port) _source_1).getType());
        _builder.append(_doSwitch);
        _builder.append(", ");
        Integer _xifexpression = null;
        Integer _size = conn.getSize();
        boolean _tripleNotEquals = (_size != null);
        if (_tripleNotEquals) {
          _xifexpression = conn.getSize();
        } else {
          _xifexpression = Integer.valueOf(this.fifoSize);
        }
        _builder.append(_xifexpression);
        _builder.append(", ");
        Object _valueAsObject = conn.<Object>getValueAsObject("idNoBcast");
        _builder.append(_valueAsObject);
        _builder.append(", ");
        _builder.append(nbReaders);
        _builder.append(")");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("DECLARE_FIFO(");
        CharSequence _doSwitch_1 = this.doSwitch(conn.getSourcePort().getType());
        _builder.append(_doSwitch_1);
        _builder.append(", ");
        Integer _xifexpression_1 = null;
        Integer _size_1 = conn.getSize();
        boolean _tripleNotEquals_1 = (_size_1 != null);
        if (_tripleNotEquals_1) {
          _xifexpression_1 = conn.getSize();
        } else {
          _xifexpression_1 = Integer.valueOf(this.fifoSize);
        }
        _builder.append(_xifexpression_1);
        _builder.append(", ");
        Object _valueAsObject_1 = conn.<Object>getValueAsObject("idNoBcast");
        _builder.append(_valueAsObject_1);
        _builder.append(", ");
        _builder.append(nbReaders);
        _builder.append(")");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence allocateGenWeightsActionData(final Vertex vertex) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Action> _actions = vertex.<Actor>getAdapter(Actor.class).getActions();
      for(final Action action : _actions) {
        _builder.append("DECLARE_ACTION_PROFILING_DATA(");
        _builder.append(this.genWeightsActionDataCounter);
        _builder.append(")");
        _builder.newLineIfNotEmpty();
        _builder.append("rdtsc_data_t *profDataAction_");
        String _label = vertex.getLabel();
        _builder.append(_label);
        _builder.append("_");
        String _name = action.getName();
        _builder.append(_name);
        _builder.append(" = &profDataAction_");
        _builder.append(this.genWeightsActionDataCounter);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        CharSequence _incGenWeightsActionDataCounter = this.incGenWeightsActionDataCounter();
        _builder.append(_incGenWeightsActionDataCounter);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence allocateGenWeightsSchedulerData(final Vertex vertex) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DECLARE_SCHEDULER_PROFILING_DATA(");
    _builder.append(this.genWeightsSchedulerDataCounter);
    _builder.append(", ");
    int _length = ((Object[])Conversions.unwrapArray(vertex.<Actor>getAdapter(Actor.class).getActions(), Object.class)).length;
    int _plus = (_length + 1);
    _builder.append(_plus);
    _builder.append(", ");
    int _length_1 = ((Object[])Conversions.unwrapArray(vertex.<Actor>getAdapter(Actor.class).getActions(), Object.class)).length;
    _builder.append(_length_1);
    _builder.append("+1)");
    _builder.newLineIfNotEmpty();
    _builder.append("rdtsc_scheduler_map_t *profDataScheduler_");
    String _label = vertex.getLabel();
    _builder.append(_label);
    _builder.append(" = &profDataScheduler_");
    _builder.append(this.genWeightsSchedulerDataCounter);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    CharSequence _incGenWeightsSchedulerDataCounter = this.incGenWeightsSchedulerDataCounter();
    _builder.append(_incGenWeightsSchedulerDataCounter);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence incGenWeightsActionDataCounter() {
    CharSequence _xblockexpression = null;
    {
      this.genWeightsActionDataCounter++;
      StringConcatenation _builder = new StringConcatenation();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  private CharSequence incGenWeightsSchedulerDataCounter() {
    CharSequence _xblockexpression = null;
    {
      this.genWeightsSchedulerDataCounter++;
      StringConcatenation _builder = new StringConcatenation();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printAdditionalIncludes() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence additionalDeclarations() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence additionalAtExitActions() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence afterMain() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence beforeMain() {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.papify) {
        {
          if (this.papifyMultiplex) {
            _builder.append("event_init_multiplex();");
            _builder.newLine();
          } else {
            _builder.append("event_init();\t");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
}
