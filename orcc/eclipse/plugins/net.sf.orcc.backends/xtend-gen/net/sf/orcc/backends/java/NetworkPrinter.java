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
package net.sf.orcc.backends.java;

import com.google.common.collect.Iterables;
import java.util.List;
import net.sf.orcc.backends.java.JavaTemplate;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
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
public class NetworkPrinter extends JavaTemplate {
  private Network network;
  
  public Network setNetwork(final Network network) {
    return this.network = network;
  }
  
  public CharSequence getNetworkFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// File generated from ");
    String _fileName = this.network.getFileName();
    _builder.append(_fileName);
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _simpleName = this.network.getSimpleName();
    _builder.append(_simpleName);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.net.URI;");
    _builder.newLine();
    _builder.append("import java.net.URISyntaxException;");
    _builder.newLine();
    _builder.append("import net.sf.orcc.runtime.*;");
    _builder.newLine();
    _builder.append("import net.sf.orcc.runtime.actors.*;");
    _builder.newLine();
    _builder.append("import net.sf.orcc.runtime.source.GenericSource;");
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
    _builder.newLine();
    _builder.append("public class ");
    String _simpleName_1 = this.network.getSimpleName();
    _builder.append(_simpleName_1);
    _builder.append(" implements IScheduler {");
    _builder.newLineIfNotEmpty();
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
        String _name_1 = instance.getName();
        _builder.append(_name_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
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
        String _name_2 = instance_1.getName();
        _builder.append(_name_2, "\t");
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
    _builder.append("public void initialize( URI serverURI ) {");
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
        {
          boolean _isNative_1 = instance_2.getActor().isNative();
          boolean _not = (!_isNative_1);
          if (_not) {
            _builder.append("\t\t");
            String _name_3 = instance_2.getName();
            _builder.append(_name_3, "\t\t");
            _builder.append(" = new ");
            String _simpleName_2 = instance_2.getSimpleName();
            _builder.append(_simpleName_2, "\t\t");
            _builder.append("(");
            CharSequence _printArguments = this.printArguments(instance_2.getActor().getParameters(), instance_2.getArguments());
            _builder.append(_printArguments, "\t\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t\t");
            String _name_4 = instance_2.getName();
            _builder.append(_name_4, "\t\t");
            _builder.append(" = new ");
            String _simpleName_3 = instance_2.getActor().getSimpleName();
            _builder.append(_simpleName_3, "\t\t");
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
        String _name_5 = instance_3.getName();
        _builder.append(_name_5, "\t\t");
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
    _builder.append("\t\t");
    _builder.append("WebSocketFifo wf;");
    _builder.newLine();
    {
      EList<Connection> _connections = this.network.getConnections();
      for(final Connection connection : _connections) {
        _builder.append("\t\t");
        _builder.newLine();
        {
          Vertex _source = connection.getSource();
          if ((_source instanceof Port)) {
            _builder.append("\t\t");
            _builder.append("/**");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* Input port ");
            Vertex _source_1 = connection.getSource();
            String _name_6 = ((Port) _source_1).getName();
            _builder.append(_name_6, "\t\t ");
            _builder.append(" connected here");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("wf = new  WebSocketFifo<");
            Vertex _source_2 = connection.getSource();
            CharSequence _doSwitch_2 = this.doSwitch(((Port) _source_2).getType());
            _builder.append(_doSwitch_2, "\t\t");
            _builder.append(">(");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("this,");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("\"o_\"+\"");
            String _label = connection.getSource().getLabel();
            _builder.append(_label, "\t\t\t");
            _builder.append("\".substring(2),");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("true,");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("serverURI,");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            Integer _xifexpression = null;
            Integer _size_1 = connection.getSize();
            boolean _tripleNotEquals = (_size_1 != null);
            if (_tripleNotEquals) {
              _xifexpression = connection.getSize();
            } else {
              _xifexpression = Integer.valueOf(this.fifoSize);
            }
            _builder.append(_xifexpression, "\t\t\t");
            _builder.append(",");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("\"");
            Vertex _source_3 = connection.getSource();
            CharSequence _doSwitch_3 = this.doSwitch(((Port) _source_3).getType());
            _builder.append(_doSwitch_3, "\t\t\t");
            _builder.append("\"");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(");");
            _builder.newLine();
            _builder.append("\t\t");
            Vertex _target = connection.getTarget();
            String _name_7 = ((Instance) _target).getName();
            _builder.append(_name_7, "\t\t");
            _builder.append(".setFifo(\"");
            String _name_8 = connection.getTargetPort().getName();
            _builder.append(_name_8, "\t\t");
            _builder.append("\", wf);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("wf.connect(\"");
            Vertex _source_4 = connection.getSource();
            CharSequence _doSwitch_4 = this.doSwitch(((Port) _source_4).getType());
            _builder.append(_doSwitch_4, "\t\t");
            _builder.append("\");");
            _builder.newLineIfNotEmpty();
          } else {
            Vertex _target_1 = connection.getTarget();
            if ((_target_1 instanceof Port)) {
              _builder.append("\t\t");
              _builder.append("/**");
              _builder.newLine();
              _builder.append("\t\t");
              _builder.append(" ");
              _builder.append("* Output port ");
              Vertex _target_2 = connection.getTarget();
              String _name_9 = ((Port) _target_2).getName();
              _builder.append(_name_9, "\t\t ");
              _builder.append(" connected here");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.append(" ");
              _builder.append("*/");
              _builder.newLine();
              _builder.append("\t\t");
              _builder.append("wf = new  WebSocketFifo<");
              Vertex _target_3 = connection.getTarget();
              CharSequence _doSwitch_5 = this.doSwitch(((Port) _target_3).getType());
              _builder.append(_doSwitch_5, "\t\t");
              _builder.append(">(");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.append("\t");
              _builder.append("this,");
              _builder.newLine();
              _builder.append("\t\t");
              _builder.append("\t");
              _builder.append("\"i_\"+\"");
              String _label_1 = connection.getTarget().getLabel();
              _builder.append(_label_1, "\t\t\t");
              _builder.append("\".substring(2),");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.append("\t");
              _builder.append("false,");
              _builder.newLine();
              _builder.append("\t\t");
              _builder.append("\t");
              _builder.append("serverURI,");
              _builder.newLine();
              _builder.append("\t\t");
              _builder.append("\t");
              _builder.append("1,");
              _builder.newLine();
              _builder.append("\t\t");
              _builder.append("\t");
              _builder.append("\"");
              Vertex _target_4 = connection.getTarget();
              CharSequence _doSwitch_6 = this.doSwitch(((Port) _target_4).getType());
              _builder.append(_doSwitch_6, "\t\t\t");
              _builder.append("\"");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.append(");");
              _builder.newLine();
              _builder.append("\t\t");
              Vertex _source_5 = connection.getSource();
              String _name_10 = ((Instance) _source_5).getName();
              _builder.append(_name_10, "\t\t");
              _builder.append(".setFifo(\"");
              String _name_11 = connection.getSourcePort().getName();
              _builder.append(_name_11, "\t\t");
              _builder.append("\", wf);");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.append("wf.connect(\"");
              Vertex _target_5 = connection.getTarget();
              CharSequence _doSwitch_7 = this.doSwitch(((Port) _target_5).getType());
              _builder.append(_doSwitch_7, "\t\t");
              _builder.append("\");");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("\t\t");
              _builder.append("f = new Fifo<");
              CharSequence _xifexpression_1 = null;
              Vertex _target_6 = connection.getTarget();
              if ((_target_6 instanceof Instance)) {
                _xifexpression_1 = this.doSwitch(connection.getTargetPort().getType());
              } else {
                Vertex _target_7 = connection.getTarget();
                _xifexpression_1 = this.doSwitch(((Port) _target_7).getType());
              }
              _builder.append(_xifexpression_1, "\t\t");
              _builder.append(" >(");
              Integer _xifexpression_2 = null;
              Integer _size_2 = connection.getSize();
              boolean _tripleNotEquals_1 = (_size_2 != null);
              if (_tripleNotEquals_1) {
                _xifexpression_2 = connection.getSize();
              } else {
                _xifexpression_2 = Integer.valueOf(this.fifoSize);
              }
              _builder.append(_xifexpression_2, "\t\t");
              _builder.append(");");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              Vertex _source_6 = connection.getSource();
              String _name_12 = ((Instance) _source_6).getName();
              _builder.append(_name_12, "\t\t");
              _builder.append(".setFifo(\"");
              String _name_13 = connection.getSourcePort().getName();
              _builder.append(_name_13, "\t\t");
              _builder.append("\", f);");
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              Vertex _target_8 = connection.getTarget();
              String _name_14 = ((Instance) _target_8).getName();
              _builder.append(_name_14, "\t\t");
              _builder.append(".setFifo(\"");
              String _name_15 = connection.getTargetPort().getName();
              _builder.append(_name_15, "\t\t");
              _builder.append("\", f);");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
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
            String _name_16 = instance_4.getName();
            _builder.append(_name_16, "\t\t");
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
      Iterable<Instance> _filter_5 = Iterables.<Instance>filter(this.network.getChildren(), Instance.class);
      for(final Instance instance_5 : _filter_5) {
        _builder.append("\t\t\t");
        _builder.append("i += ");
        String _name_17 = instance_5.getName();
        _builder.append(_name_17, "\t\t\t");
        _builder.append(".schedule();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.append("} while (i > 0);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("System.out.println(\"No more action to launch\");");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static void main(String[] args) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if( args.length < 1 ) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("System.out.println( \"Usage: java ");
    String _simpleName_4 = this.network.getSimpleName();
    _builder.append(_simpleName_4, "\t\t\t");
    _builder.append(".");
    String _simpleName_5 = this.network.getSimpleName();
    _builder.append(_simpleName_5, "\t\t\t");
    _builder.append(" ws://<server address>:8080/iopod\" );");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("System.exit( 1 );");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("String serverURI = args[0];");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("try {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("URI uri = new URI( serverURI );");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("CLIParameters.getInstance().setArguments(args);");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("GenericSource.setFileName(CLIParameters.getInstance().getSourceFile());");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("IScheduler scheduler = new ");
    String _simpleName_6 = this.network.getSimpleName();
    _builder.append(_simpleName_6, "\t\t\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("scheduler.initialize( uri );");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("while( true ) {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("scheduler.schedule();");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("try {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("Thread.sleep( 1000L );");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("} catch( InterruptedException ex ) {}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("} catch( URISyntaxException ex ) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("System.out.println( \"Invalid server URI: \"+serverURI );");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("System.exit( 1 );");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * TODO : replace this awful wart by a great way to detect an instance is  broadcast
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
