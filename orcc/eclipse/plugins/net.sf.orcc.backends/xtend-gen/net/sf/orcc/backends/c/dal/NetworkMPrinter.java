package net.sf.orcc.backends.c.dal;

import java.util.Map;
import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Vertex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Generate and print actor mapping file for DAL backend.
 * 
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C NetworkPrinter
 */
@SuppressWarnings("all")
public class NetworkMPrinter extends CTemplate {
  private Network network;
  
  private int fifoSize;
  
  public int setNetwork(final Network network, final int fifoSize) {
    int _xblockexpression = (int) 0;
    {
      this.network = network;
      _xblockexpression = this.fifoSize = fifoSize;
    }
    return _xblockexpression;
  }
  
  protected CharSequence getMappingFileContent(final Map<String, String> mapping) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<mapping xmlns=\"http://www.tik.ee.ethz.ch/~euretile/schema/MAPPING\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("xsi:schemaLocation=\"http://www.tik.ee.ethz.ch/~euretile/schema/MAPPING     http://www.tik.ee.ethz.ch/~euretile/schema/mapping.xsd\" name=\"mapping1\" processnetwork=\"APP1\">");
    _builder.newLine();
    {
      EList<Vertex> _children = this.network.getChildren();
      for(final Vertex vertex : _children) {
        _builder.append("\t");
        _builder.append("<binding name=\"");
        String _label = vertex.getLabel();
        _builder.append(_label, "\t");
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<process name=\"");
        String _label_1 = vertex.getLabel();
        _builder.append(_label_1, "\t\t");
        _builder.append("\"/>");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        {
          String _name = this.network.getName();
          String _plus = (_name + "_");
          String _label_2 = vertex.getLabel();
          String _plus_1 = (_plus + _label_2);
          String _get = mapping.get(_plus_1);
          boolean _tripleNotEquals = (_get != null);
          if (_tripleNotEquals) {
            {
              String _name_1 = this.network.getName();
              String _plus_2 = (_name_1 + "_");
              String _label_3 = vertex.getLabel();
              String _plus_3 = (_plus_2 + _label_3);
              boolean _equals = mapping.get(_plus_3).equals("");
              if (_equals) {
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<processor name=\"core_0\"/>");
                _builder.newLine();
              } else {
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<processor name=\"");
                String _name_2 = this.network.getName();
                String _plus_4 = (_name_2 + "_");
                String _label_4 = vertex.getLabel();
                String _plus_5 = (_plus_4 + _label_4);
                String _get_1 = mapping.get(_plus_5);
                _builder.append(_get_1, "\t\t");
                _builder.append("\"/>");
                _builder.newLineIfNotEmpty();
              }
            }
            {
              String _name_3 = this.network.getName();
              String _plus_6 = (_name_3 + "_");
              String _label_5 = vertex.getLabel();
              String _plus_7 = (_plus_6 + _label_5);
              boolean _startsWith = mapping.get(_plus_7).startsWith("gpu_");
              if (_startsWith) {
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("<target><opencl workgroups=\"1\" workitems=\"");
                int _numTokens = ((Actor) vertex).getActions().get(0).getOutputPattern().getNumTokens(((Actor) vertex).getOutputs().get(0));
                _builder.append(_numTokens, "\t\t");
                _builder.append("\"/></target>");
                _builder.newLineIfNotEmpty();
              }
            }
          } else {
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<processor name=\"core_0\"/>");
            _builder.newLine();
          }
        }
        _builder.append("\t");
        _builder.append("</binding> ");
        _builder.newLine();
      }
    }
    _builder.append("</mapping>");
    _builder.newLine();
    return _builder;
  }
}
