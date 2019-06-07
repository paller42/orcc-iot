package net.sf.orcc.backends.c.dal;

import java.util.Collection;
import java.util.List;
import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Type;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generate and print process network description for DAL backend.
 * 
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C NetworkPrinter
 */
@SuppressWarnings("all")
public class NetworkCPrinter extends CTemplate {
  protected Network network;
  
  protected boolean classify;
  
  public boolean setNetwork(final Network network, final boolean classify) {
    boolean _xblockexpression = false;
    {
      this.network = network;
      _xblockexpression = this.classify = classify;
    }
    return _xblockexpression;
  }
  
  public CharSequence getFifoSizeHeaderContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#define FIFO_SIZE ");
    _builder.append(this.fifoSize);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence getNetworkFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<processnetwork xmlns=\"http://www.tik.ee.ethz.ch/~euretile/schema/PROCESSNETWORK\" ");
    _builder.newLine();
    _builder.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
    _builder.newLine();
    _builder.append("xsi:schemaLocation=\"http://www.tik.ee.ethz.ch/~euretile/schema/PROCESSNETWORK");
    _builder.newLine();
    _builder.append("http://www.tik.ee.ethz.ch/~euretile/schema/processnetwork.xsd\" name=\"");
    String _name = this.network.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      List<Actor> _allActors = this.network.getAllActors();
      for(final Actor actor : _allActors) {
        _builder.append("\t");
        String _xifexpression = null;
        if (((actor.getInputs().size() > 0) && (actor.getOutputs().size() > 0))) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("local");
          _xifexpression = _builder_1.toString();
        } else {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("io");
          _xifexpression = _builder_2.toString();
        }
        final String actorType = _xifexpression;
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<process name=\"");
        String _name_1 = actor.getName();
        _builder.append(_name_1, "\t");
        _builder.append("\" type=\"");
        _builder.append(actorType, "\t");
        _builder.append("\"> ");
        _builder.newLineIfNotEmpty();
        {
          EList<Port> _inputs = actor.getInputs();
          for(final Port port : _inputs) {
            _builder.append("\t");
            _builder.append("<port type=\"input\" name=\"");
            String _name_2 = port.getName();
            _builder.append(_name_2, "\t");
            _builder.append("\"/>");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<Port> _outputs = actor.getOutputs();
          for(final Port port_1 : _outputs) {
            _builder.append("\t");
            _builder.append("<port type=\"output\" name=\"");
            String _name_3 = port_1.getName();
            _builder.append(_name_3, "\t");
            _builder.append("\"/>");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("<source type=\"c\" location=\"");
        String _name_4 = actor.getName();
        _builder.append(_name_4, "\t");
        _builder.append(".c\"/>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</process>");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
      }
    }
    {
      EList<Vertex> _children = this.network.getChildren();
      for(final Vertex child : _children) {
        _builder.append("\t");
        CharSequence _allocateFifos = this.allocateFifos(child);
        _builder.append(_allocateFifos, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Vertex> _children_1 = this.network.getChildren();
      for(final Vertex child_1 : _children_1) {
        _builder.append("\t");
        CharSequence _assignFifo = this.assignFifo(child_1);
        _builder.append(_assignFifo, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</processnetwork>");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence assignFifo(final Vertex vertex) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Collection<Connection> _values = vertex.<Entity>getAdapter(Entity.class).getIncomingPortMap().values();
      for(final Connection inList : _values) {
        _builder.append("<connection name=\"C");
        Integer _valueAsObject = inList.<Integer>getValueAsObject("idNoBcast");
        _builder.append(_valueAsObject);
        _builder.append("-");
        String _name = inList.getTargetPort().getName();
        _builder.append(_name);
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<origin name=\"C");
        Integer _valueAsObject_1 = inList.<Integer>getValueAsObject("idNoBcast");
        _builder.append(_valueAsObject_1, "\t");
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("<port name=\"1\"/>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</origin>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<target name=\"");
        String _label = inList.getTarget().getLabel();
        _builder.append(_label, "\t");
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("<port name=\"");
        String _name_1 = inList.getTargetPort().getName();
        _builder.append(_name_1, "\t\t");
        _builder.append("\"/>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</target>");
        _builder.newLine();
        _builder.append("</connection>");
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      Collection<List<Connection>> _values_1 = vertex.<Entity>getAdapter(Entity.class).getOutgoingPortMap().values();
      for(final List<Connection> outList : _values_1) {
        _builder.append("<connection name=\"");
        String _name_2 = outList.get(0).getSourcePort().getName();
        _builder.append(_name_2);
        _builder.append("-C");
        Integer _valueAsObject_2 = IterableExtensions.<Connection>head(outList).<Integer>getValueAsObject("idNoBcast");
        _builder.append(_valueAsObject_2);
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<origin name=\"");
        String _label_1 = vertex.getLabel();
        _builder.append(_label_1, "\t");
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("<port name=\"");
        String _name_3 = outList.get(0).getSourcePort().getName();
        _builder.append(_name_3, "\t\t");
        _builder.append("\"/>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</origin>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<target name=\"C");
        Integer _valueAsObject_3 = IterableExtensions.<Connection>head(outList).<Integer>getValueAsObject("idNoBcast");
        _builder.append(_valueAsObject_3, "\t");
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("<port name=\"0\"/>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</target>");
        _builder.newLine();
        _builder.append("</connection>");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
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
  
  private int sizeOf(final Type type) {
    boolean _isFloat = type.isFloat();
    if (_isFloat) {
      return 4;
    } else {
      if ((type.isInt() || type.isUint())) {
        int _sizeInBits = type.getSizeInBits();
        boolean _greaterThan = (_sizeInBits > 16);
        if (_greaterThan) {
          return 4;
        } else {
          int _sizeInBits_1 = type.getSizeInBits();
          boolean _greaterThan_1 = (_sizeInBits_1 > 8);
          if (_greaterThan_1) {
            return 2;
          }
        }
      }
    }
    return 1;
  }
  
  protected CharSequence allocateFifo(final Connection conn, final int nbReaders) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.classify) {
        _builder.append("<sw_channel type=\"fifo\" initialtokens=\"");
        Integer _xifexpression = null;
        boolean _hasAttribute = conn.hasAttribute("InitialTokens");
        if (_hasAttribute) {
          _xifexpression = conn.<Integer>getValueAsObject("InitialTokens");
        } else {
          _xifexpression = Integer.valueOf(0);
        }
        _builder.append(_xifexpression);
        _builder.append("\" tokensize=\"");
        Integer _valueAsObject = conn.<Integer>getValueAsObject("TokenRate");
        Integer _valueAsObject_1 = conn.<Integer>getValueAsObject("TokenSize");
        int _multiply = ((_valueAsObject).intValue() * (_valueAsObject_1).intValue());
        _builder.append(_multiply);
        _builder.append("\" size=\"");
        Integer _xifexpression_1 = null;
        Integer _size = conn.getSize();
        boolean _tripleNotEquals = (_size != null);
        if (_tripleNotEquals) {
          _xifexpression_1 = conn.getSize();
        } else {
          _xifexpression_1 = Integer.valueOf(this.fifoSize);
        }
        _builder.append(_xifexpression_1);
        _builder.append("\" name=\"C");
        Object _valueAsObject_2 = conn.<Object>getValueAsObject("idNoBcast");
        _builder.append(_valueAsObject_2);
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("<sw_channel type=\"fifo\" size=\"");
        int _xifexpression_2 = (int) 0;
        Integer _size_1 = conn.getSize();
        boolean _tripleNotEquals_1 = (_size_1 != null);
        if (_tripleNotEquals_1) {
          Integer _size_2 = conn.getSize();
          int _sizeOf = this.sizeOf(conn.getSourcePort().getType());
          _xifexpression_2 = ((_size_2).intValue() * _sizeOf);
        } else {
          int _sizeOf_1 = this.sizeOf(conn.getSourcePort().getType());
          _xifexpression_2 = (this.fifoSize * _sizeOf_1);
        }
        _builder.append(_xifexpression_2);
        _builder.append("\" name=\"C");
        Object _valueAsObject_3 = conn.<Object>getValueAsObject("idNoBcast");
        _builder.append(_valueAsObject_3);
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("<port type=\"input\" name=\"0\"/>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<port type=\"output\" name=\"1\"/>");
    _builder.newLine();
    _builder.append("</sw_channel>");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
}
