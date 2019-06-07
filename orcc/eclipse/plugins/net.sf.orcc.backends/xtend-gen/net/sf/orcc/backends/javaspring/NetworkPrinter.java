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
package net.sf.orcc.backends.javaspring;

import com.google.common.collect.Iterables;
import java.util.HashMap;
import java.util.List;
import net.sf.orcc.backends.javaspring.JavaSpringScriptPrinter;
import net.sf.orcc.backends.javaspring.JavaSpringTemplate;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.Result;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Compile Top_network Java source code
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class NetworkPrinter extends JavaSpringTemplate {
  private Network network;
  
  private String networkSimpleName;
  
  private String topPath;
  
  private Result result;
  
  private JavaSpringScriptPrinter javaScriptsPrinter;
  
  private HashMap<String, String> mapOfPortNames = new HashMap<String, String>();
  
  public String setNetwork(final Network network, final String path, final Result result, final JavaSpringScriptPrinter printer) {
    String _xblockexpression = null;
    {
      this.network = network;
      this.topPath = path;
      this.result = result;
      this.javaScriptsPrinter = printer;
      _xblockexpression = this.networkSimpleName = network.getSimpleName().replaceAll("[^A-Za-z0-9]", "");
    }
    return _xblockexpression;
  }
  
  public String setNetwork(final Network network, final JavaSpringScriptPrinter printer) {
    String _xblockexpression = null;
    {
      this.network = network;
      _xblockexpression = this.networkSimpleName = network.getSimpleName().replaceAll("[^A-Za-z0-9]", "");
    }
    return _xblockexpression;
  }
  
  public HashMap<String, String> getMapOfPortNames() {
    return this.mapOfPortNames;
  }
  
  public CharSequence getNetworkFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// File generated from ");
    String _fileName = this.network.getFileName();
    _builder.append(_fileName);
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    _builder.append(this.networkSimpleName);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.slf4j.Logger;");
    _builder.newLine();
    _builder.append("import org.slf4j.LoggerFactory;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import net.sf.orcc.runtime.*;");
    _builder.newLine();
    _builder.append("import net.sf.orcc.runtime.actors.*;");
    _builder.newLine();
    _builder.append("import net.sf.orcc.runtime.source.GenericSource;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.springframework.boot.CommandLineRunner;");
    _builder.newLine();
    _builder.append("import org.springframework.stereotype.Component;");
    _builder.newLine();
    _builder.append("import org.springframework.beans.factory.annotation.Autowired;");
    _builder.newLine();
    {
      List<Actor> _allActors = this.network.getAllActors();
      for(final Actor act : _allActors) {
        {
          boolean _isNative = act.isNative();
          if (_isNative) {
            _builder.append("import ");
            String _name = act.getName();
            _builder.append(_name);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("@Component");
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(this.networkSimpleName);
    _builder.append(" implements IScheduler, CommandLineRunner {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("private static final Logger LOGGER = LoggerFactory.getLogger(");
    _builder.append(this.networkSimpleName, "\t");
    _builder.append(".class);\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("public ");
    _builder.append(this.networkSimpleName);
    _builder.append("(){ ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("LOGGER.info(\"Object of Class ");
    _builder.append(this.networkSimpleName, "\t");
    _builder.append(" is created: \" + this);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("//initialize();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//schedule();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Declare actors objects");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          boolean _isBroadcast = NetworkPrinter.this.isBroadcast(it);
          return Boolean.valueOf((!_isBroadcast));
        }
      };
      Iterable<Instance> _filter = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function);
      for(final Instance instance : _filter) {
        _builder.append("\t");
        _builder.append("private IActor ");
        String _replaceAll = instance.getName().replaceAll("[^A-Za-z0-9]", "");
        _builder.append(_replaceAll, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    this.mapOfPortNames.clear();
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Declare input fifos");
    _builder.newLine();
    {
      EList<Port> _inputs = this.network.getInputs();
      for(final Port port : _inputs) {
        _builder.append("\t");
        _builder.append("private Fifo inFifo_");
        String _name_1 = port.getName();
        _builder.append(_name_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        System.out.println(this.mapOfPortNames.put(port.getName(), this.doSwitch(port.getType()).toString()));
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Declare output fifos ");
    _builder.newLine();
    {
      EList<Port> _outputs = this.network.getOutputs();
      for(final Port port_1 : _outputs) {
        _builder.append("\t");
        _builder.append("private Fifo outFifo_");
        String _name_2 = port_1.getName();
        _builder.append(_name_2, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        System.out.println(this.mapOfPortNames.put(port_1.getName(), this.doSwitch(port_1.getType()).toString()));
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Declare broadcast");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function_1 = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          return Boolean.valueOf(NetworkPrinter.this.isBroadcast(it));
        }
      };
      Iterable<Instance> _filter_1 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_1);
      for(final Instance instance_1 : _filter_1) {
        _builder.append("\t");
        _builder.append("private Broadcast<");
        CharSequence _doSwitch = this.doSwitch(instance_1.getActor().getInput("input").getType());
        _builder.append(_doSwitch, "\t");
        _builder.append("> ");
        String _replaceAll_1 = instance_1.getName().replaceAll("[^A-Za-z0-9]", "");
        _builder.append(_replaceAll_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@SuppressWarnings(\"unchecked\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void initialize() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// Instantiate actors");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function_2 = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          boolean _isBroadcast = NetworkPrinter.this.isBroadcast(it);
          return Boolean.valueOf((!_isBroadcast));
        }
      };
      Iterable<Instance> _filter_2 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_2);
      for(final Instance instance_2 : _filter_2) {
        _builder.append("\t\t");
        _builder.newLine();
        {
          boolean _isNative_1 = instance_2.getActor().isNative();
          boolean _not = (!_isNative_1);
          if (_not) {
            _builder.append("\t\t");
            String _replaceAll_2 = instance_2.getName().replaceAll("[^A-Za-z0-9]", "");
            _builder.append(_replaceAll_2, "\t\t");
            _builder.append(" = new ");
            String _replaceAll_3 = instance_2.getSimpleName().replaceAll("[^A-Za-z0-9]", "");
            _builder.append(_replaceAll_3, "\t\t");
            _builder.append("(");
            CharSequence _printArguments = this.printArguments(instance_2.getActor().getParameters(), instance_2.getArguments());
            _builder.append(_printArguments, "\t\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t\t");
            String _replaceAll_4 = instance_2.getName().replaceAll("[^A-Za-z0-9]", "");
            _builder.append(_replaceAll_4, "\t\t");
            _builder.append(" = new ");
            String _replaceAll_5 = instance_2.getActor().getSimpleName().replaceAll("[^A-Za-z0-9]", "");
            _builder.append(_replaceAll_5, "\t\t");
            _builder.append("(");
            CharSequence _printArguments_1 = this.printArguments(instance_2.getActor().getParameters(), instance_2.getArguments());
            _builder.append(_printArguments_1, "\t\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// Instantiate broadcast");
    _builder.newLine();
    {
      final Function1<Instance, Boolean> _function_3 = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          return Boolean.valueOf(NetworkPrinter.this.isBroadcast(it));
        }
      };
      Iterable<Instance> _filter_3 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(this.network.getChildren(), Instance.class), _function_3);
      for(final Instance instance_3 : _filter_3) {
        _builder.append("\t\t");
        String _replaceAll_6 = instance_3.getName().replaceAll("[^A-Za-z0-9]", "");
        _builder.append(_replaceAll_6, "\t\t");
        _builder.append(" = new Broadcast<");
        CharSequence _doSwitch_1 = this.doSwitch(instance_3.getActor().getInput("input").getType());
        _builder.append(_doSwitch_1, "\t\t");
        _builder.append(">(");
        int _size = instance_3.getOutgoing().size();
        _builder.append(_size, "\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("@SuppressWarnings(\"rawtypes\")");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Fifo f;");
    _builder.newLine();
    {
      EList<Connection> _connections = this.network.getConnections();
      for(final Connection connection : _connections) {
        {
          Vertex _source = connection.getSource();
          if ((_source instanceof Port)) {
            _builder.append("\t\t");
            _builder.append("inFifo_");
            Vertex _source_1 = connection.getSource();
            String _name_3 = ((Port) _source_1).getName();
            _builder.append(_name_3, "\t\t");
            _builder.append(" = new Fifo<");
            Vertex _source_2 = connection.getSource();
            CharSequence _doSwitch_2 = this.doSwitch(((Port) _source_2).getType());
            _builder.append(_doSwitch_2, "\t\t");
            _builder.append(">(");
            Integer _xifexpression = null;
            Integer _size_1 = connection.getSize();
            boolean _tripleNotEquals = (_size_1 != null);
            if (_tripleNotEquals) {
              _xifexpression = connection.getSize();
            } else {
              _xifexpression = Integer.valueOf(this.fifoSize);
            }
            _builder.append(_xifexpression, "\t\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            Vertex _target = connection.getTarget();
            String _replaceAll_7 = ((Instance) _target).getName().replaceAll("[^A-Za-z0-9]", "");
            _builder.append(_replaceAll_7, "\t\t");
            _builder.append(".setFifo(\"");
            String _name_4 = connection.getTargetPort().getName();
            _builder.append(_name_4, "\t\t");
            _builder.append("\", inFifo_");
            Vertex _source_3 = connection.getSource();
            String _name_5 = ((Port) _source_3).getName();
            _builder.append(_name_5, "\t\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          } else {
            Vertex _target_1 = connection.getTarget();
            if ((_target_1 instanceof Port)) {
              _builder.append("\t\t");
              _builder.append("outFifo_");
              Vertex _target_2 = connection.getTarget();
              String _name_6 = ((Port) _target_2).getName();
              _builder.append(_name_6, "\t\t");
              _builder.append(" = new Fifo<");
              Vertex _target_3 = connection.getTarget();
              CharSequence _doSwitch_3 = this.doSwitch(((Port) _target_3).getType());
              _builder.append(_doSwitch_3, "\t\t");
              _builder.append(">(");
              Integer _xifexpression_1 = null;
              Integer _size_2 = connection.getSize();
              boolean _tripleNotEquals_1 = (_size_2 != null);
              if (_tripleNotEquals_1) {
                _xifexpression_1 = connection.getSize();
              } else {
                _xifexpression_1 = Integer.valueOf(this.fifoSize);
              }
              _builder.append(_xifexpression_1, "\t\t");
              _builder.append(");");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              Vertex _source_4 = connection.getSource();
              String _replaceAll_8 = ((Instance) _source_4).getName().replaceAll("[^A-Za-z0-9]", "");
              _builder.append(_replaceAll_8, "\t\t");
              _builder.append(".setFifo(\"");
              String _name_7 = connection.getSourcePort().getName();
              _builder.append(_name_7, "\t\t");
              _builder.append("\", outFifo_");
              Vertex _target_4 = connection.getTarget();
              String _name_8 = ((Port) _target_4).getName();
              _builder.append(_name_8, "\t\t");
              _builder.append(");\t\t\t\t\t");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.newLine();
            } else {
              _builder.append("\t\t");
              _builder.append("f = new Fifo<");
              CharSequence _doSwitch_4 = this.doSwitch(connection.getTargetPort().getType());
              _builder.append(_doSwitch_4, "\t\t");
              _builder.append(">(");
              Integer _xifexpression_2 = null;
              Integer _size_3 = connection.getSize();
              boolean _tripleNotEquals_2 = (_size_3 != null);
              if (_tripleNotEquals_2) {
                _xifexpression_2 = connection.getSize();
              } else {
                _xifexpression_2 = Integer.valueOf(this.fifoSize);
              }
              _builder.append(_xifexpression_2, "\t\t");
              _builder.append(");");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              Vertex _source_5 = connection.getSource();
              String _replaceAll_9 = ((Instance) _source_5).getName().replaceAll("[^A-Za-z0-9]", "");
              _builder.append(_replaceAll_9, "\t\t");
              _builder.append(".setFifo(\"");
              String _name_9 = connection.getSourcePort().getName();
              _builder.append(_name_9, "\t\t");
              _builder.append("\", f);");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              Vertex _target_5 = connection.getTarget();
              String _replaceAll_10 = ((Instance) _target_5).getName().replaceAll("[^A-Za-z0-9]", "");
              _builder.append(_replaceAll_10, "\t\t");
              _builder.append(".setFifo(\"");
              String _name_10 = connection.getTargetPort().getName();
              _builder.append(_name_10, "\t\t");
              _builder.append("\", f);\t\t\t\t\t");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<Port> _inputs_1 = this.network.getInputs();
      for(final Port port_2 : _inputs_1) {
        _builder.append("\t");
        _builder.append("//@Override // needed if you are using interface for this...");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("public void receive_");
        String _name_11 = port_2.getName();
        _builder.append(_name_11, "\t");
        _builder.append("_");
        String _string = this.doSwitch(port_2.getType()).toString();
        _builder.append(_string, "\t");
        _builder.append("Token( String pod, ");
        String _string_1 = this.doSwitch(port_2.getType()).toString();
        _builder.append(_string_1, "\t");
        _builder.append(" v ) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("LOGGER.info(\"Class: ");
        String _name_12 = this.network.getName();
        _builder.append(_name_12, "\t");
        _builder.append(", Port: ");
        String _name_13 = port_2.getName();
        _builder.append(_name_13, "\t");
        _builder.append("Token: pod: \" + pod + \" v: \" + v);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("//if( \"input\".equals( pod ) ) {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("put");
        String _name_14 = port_2.getName();
        _builder.append(_name_14, "\t\t");
        _builder.append("_");
        String _string_2 = this.doSwitch(port_2.getType()).toString();
        _builder.append(_string_2, "\t\t");
        _builder.append("TokenIntoFifo( inFifo_");
        String _name_15 = port_2.getName();
        _builder.append(_name_15, "\t\t");
        _builder.append(",v );");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("//}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}\t\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("private void put");
        String _name_16 = port_2.getName();
        _builder.append(_name_16, "\t");
        _builder.append("_");
        String _string_3 = this.doSwitch(port_2.getType()).toString();
        _builder.append(_string_3, "\t");
        _builder.append("TokenIntoFifo( Fifo<");
        String _string_4 = this.doSwitch(port_2.getType()).toString();
        _builder.append(_string_4, "\t");
        _builder.append("> f, ");
        String _string_5 = this.doSwitch(port_2.getType()).toString();
        _builder.append(_string_5, "\t");
        _builder.append(" v ) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("LOGGER.info( \"Class: ");
        String _name_17 = this.network.getName();
        _builder.append(_name_17, "\t\t");
        _builder.append(". put");
        CharSequence _doSwitch_5 = this.doSwitch(port_2.getType());
        _builder.append(_doSwitch_5, "\t\t");
        _builder.append("TokenIntoFifo: f: \" + f + \" value: \" + v );");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("if( f.hasRoom(1) ) {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("f.write( v );");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("schedule();");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _queueConfig = this.javaScriptsPrinter.getQueueConfig(this.networkSimpleName, port_2.getName(), "input");
        String _name_18 = port_2.getName();
        String _plus = ("QueueCfg_" + _name_18);
        String _plus_1 = (_plus + ".java");
        System.out.println(this.result.merge(FilesManager.writeFile(_queueConfig, this.topPath, _plus_1)));
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _queueFileContent = this.javaScriptsPrinter.getQueueFileContent(this.networkSimpleName, port_2.getName(), this.doSwitch(port_2.getType()).toString(), "input");
        String _name_19 = port_2.getName();
        String _plus_2 = ("Queue_" + _name_19);
        String _plus_3 = (_plus_2 + ".java");
        System.out.println(this.result.merge(FilesManager.writeFile(_queueFileContent, this.topPath, _plus_3)));
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      EList<Port> _outputs_1 = this.network.getOutputs();
      for(final Port port_3 : _outputs_1) {
        _builder.append("\t");
        _builder.append("@Autowired");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("Queue_");
        String _name_20 = port_3.getName();
        _builder.append(_name_20, "\t");
        _builder.append("  queue_");
        String _name_21 = port_3.getName();
        _builder.append(_name_21, "\t");
        _builder.append("; // Autowire with senders, since this object sends messages to queue");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("private int scheduleOutputFifo_");
        String _name_22 = port_3.getName();
        _builder.append(_name_22, "\t");
        _builder.append(" ( String podName, Fifo<");
        String _string_6 = this.doSwitch(port_3.getType()).toString();
        _builder.append(_string_6, "\t");
        _builder.append("> f ) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("boolean res = false;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("if( f.hasTokens(1) ){");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("res = true;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        String _string_7 = this.doSwitch(port_3.getType()).toString();
        _builder.append(_string_7, "\t\t");
        _builder.append(" v = f.read();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("LOGGER.info(\"Sending from: \" + this + \" value: \" + v + \" using object: \" + queue_");
        String _name_23 = port_3.getName();
        _builder.append(_name_23, "\t\t");
        _builder.append(" );");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("queue_");
        String _name_24 = port_3.getName();
        _builder.append(_name_24, "\t\t");
        _builder.append(".send");
        CharSequence _doSwitch_6 = this.doSwitch(port_3.getType());
        _builder.append(_doSwitch_6, "\t\t");
        _builder.append("ToQueue(podName, v );");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("return res ? 1 : 0;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _queueConfig_1 = this.javaScriptsPrinter.getQueueConfig(this.networkSimpleName, port_3.getName(), "output");
        String _name_25 = port_3.getName();
        String _plus_4 = ("QueueCfg_" + _name_25);
        String _plus_5 = (_plus_4 + ".java");
        System.out.println(this.result.merge(FilesManager.writeFile(_queueConfig_1, this.topPath, _plus_5)));
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _queueFileContent_1 = this.javaScriptsPrinter.getQueueFileContent(this.networkSimpleName, port_3.getName(), this.doSwitch(port_3.getType()).toString(), "output");
        String _name_26 = port_3.getName();
        String _plus_6 = ("Queue_" + _name_26);
        String _plus_7 = (_plus_6 + ".java");
        System.out.println(this.result.merge(FilesManager.writeFile(_queueFileContent_1, this.topPath, _plus_7)));
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    System.out.println(this.result.merge(FilesManager.writeFile(this.javaScriptsPrinter.getDBRepository(this.networkSimpleName), this.topPath, "DBRepository.java")));
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    System.out.println(this.result.merge(FilesManager.writeFile(this.javaScriptsPrinter.getIDBRepository(this.networkSimpleName), this.topPath, "IDBRepository.java")));
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void schedule() {");
    _builder.newLine();
    {
      Iterable<Instance> _filter_4 = Iterables.<Instance>filter(this.network.getChildren(), Instance.class);
      for(final Instance instance_4 : _filter_4) {
        {
          Actor _actor = instance_4.getActor();
          EList<Action> _initializes = null;
          if (_actor!=null) {
            _initializes=_actor.getInitializes();
          }
          boolean _isEmpty = _initializes.isEmpty();
          boolean _not_1 = (!_isEmpty);
          if (_not_1) {
            _builder.append("\t\t");
            String _name_27 = instance_4.getName();
            _builder.append(_name_27, "\t\t");
            _builder.append(".initialize();");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("int i;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("do {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("i = 0;");
    _builder.newLine();
    {
      EList<Port> _outputs_2 = this.network.getOutputs();
      for(final Port port_4 : _outputs_2) {
        _builder.append("\t\t\t");
        _builder.append("i += scheduleOutputFifo_");
        String _name_28 = port_4.getName();
        _builder.append(_name_28, "\t\t\t");
        _builder.append("(\"");
        String _name_29 = port_4.getName();
        _builder.append(_name_29, "\t\t\t");
        _builder.append("\", outFifo_");
        String _name_30 = port_4.getName();
        _builder.append(_name_30, "\t\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<Instance> _filter_5 = Iterables.<Instance>filter(this.network.getChildren(), Instance.class);
      for(final Instance instance_5 : _filter_5) {
        _builder.append("\t\t\t");
        _builder.append("i += ");
        String _replaceAll_11 = instance_5.getName().replaceAll("[^A-Za-z0-9]", "");
        _builder.append(_replaceAll_11, "\t\t\t");
        _builder.append(".schedule();");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _inputs_2 = this.network.getInputs();
      for(final Port port_5 : _inputs_2) {
        _builder.append("\t\t\t");
        _builder.append("//\ti += scheduleInputFifo_");
        String _name_31 = port_5.getName();
        _builder.append(_name_31, "\t\t\t");
        _builder.append("(\"");
        String _name_32 = port_5.getName();
        _builder.append(_name_32, "\t\t\t");
        _builder.append("\", inFifo_");
        String _name_33 = port_5.getName();
        _builder.append(_name_33, "\t\t\t");
        _builder.append("); //not necessary for inputs");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("} while (i > 0);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("System.out.println(\"No more action to launch, listening.....\");");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//System.exit(0);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void run(String[] args) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("LOGGER.info(\"----------------------Run function started in ");
    _builder.append(this.networkSimpleName, "\t\t");
    _builder.append("-------------------\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("CLIParameters.getInstance().setArguments(args);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("GenericSource.setFileName(CLIParameters.getInstance().getSourceFile());");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//IScheduler scheduler = new ");
    _builder.append(this.networkSimpleName, "\t\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("IScheduler scheduler = this;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("scheduler.initialize();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("scheduler.schedule();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//initialize();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//schedule();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("LOGGER.info(\"----------------------Run function complete----------------------------------------\");");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * TODO : replace this awful wart by a great way to detect an instance is broadcast
   * If an actors has 1 in port called "input" and a list of
   * out ports called "output_x", it is a Broadcast actor
   */
  public boolean isBroadcast(final Instance instance) {
    Port _input = instance.getActor().getInput("input");
    boolean _tripleEquals = (_input == null);
    if (_tripleEquals) {
      return false;
    }
    EList<Port> _outputs = instance.getActor().getOutputs();
    for (final Port port : _outputs) {
      boolean _startsWith = port.getName().startsWith("output_");
      boolean _not = (!_startsWith);
      if (_not) {
        return false;
      }
    }
    return true;
  }
}
