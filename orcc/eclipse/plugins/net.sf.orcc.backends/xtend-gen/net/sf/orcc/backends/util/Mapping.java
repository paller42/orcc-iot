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
package net.sf.orcc.backends.util;

import com.google.common.collect.Iterables;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.CommonPrinter;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Printer used to create the xcf file, containing information on
 * mapping between actors and processor cores
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class Mapping extends CommonPrinter {
  private Network network;
  
  private Map<Vertex, String> invMapping;
  
  private Map<String, List<Vertex>> mapping;
  
  private List<Vertex> unmapped;
  
  private int i;
  
  public Mapping(final Network network, final Map<String, String> map) {
    this.setNetwork(network);
    final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
      @Override
      public Boolean apply(final String it) {
        return Boolean.valueOf(StringExtensions.isNullOrEmpty(it));
      }
    };
    boolean _forall = IterableExtensions.<String>forall(map.values(), _function);
    boolean _not = (!_forall);
    if (_not) {
      Iterable<Instance> _actorInstances = this.getActorInstances(network.getChildren());
      for (final Instance instance : _actorInstances) {
        this.tryToMap(instance, map.get(instance.getHierarchicalName()));
      }
      Iterable<Actor> _filter = Iterables.<Actor>filter(network.getChildren(), Actor.class);
      for (final Actor actor : _filter) {
        boolean _hasAttribute = actor.hasAttribute("mergedActors");
        if (_hasAttribute) {
          final List<String> clusteredActors = actor.<List<String>>getValueAsObject("mergedActors");
          for (final String actorName : clusteredActors) {
            {
              String _name = network.getName();
              String _plus = (_name + "_");
              String _plus_1 = (_plus + actorName);
              final String aTarget = map.get(_plus_1);
              if ((aTarget != null)) {
                boolean _vertexExists = this.vertexExists(((Vertex) actor));
                boolean _not_1 = (!_vertexExists);
                if (_not_1) {
                  this.tryToMap(actor, aTarget);
                  String _name_1 = network.getName();
                  String _plus_2 = (_name_1 + "_");
                  String _name_2 = actor.getName();
                  String _plus_3 = (_plus_2 + _name_2);
                  map.put(_plus_3, aTarget);
                }
              }
            }
          }
        }
      }
      Iterable<Actor> _filter_1 = Iterables.<Actor>filter(network.getChildren(), Actor.class);
      for (final Actor actor_1 : _filter_1) {
        boolean _hasAttribute_1 = actor_1.hasAttribute("broadcastOrigin");
        if (_hasAttribute_1) {
          final String origin = actor_1.getValueAsString("broadcastOrigin");
          String _name = network.getName();
          String _plus = (_name + "_");
          String _plus_1 = (_plus + origin);
          final String originTarget = map.get(_plus_1);
          this.tryToMap(actor_1, originTarget);
        } else {
          boolean _hasAttribute_2 = actor_1.hasAttribute("mergedActors");
          boolean _not_1 = (!_hasAttribute_2);
          if (_not_1) {
            String _name_1 = network.getName();
            String _plus_2 = (_name_1 + "_");
            String _name_2 = actor_1.getName();
            String _plus_3 = (_plus_2 + _name_2);
            this.tryToMap(actor_1, map.get(_plus_3));
          }
        }
      }
    } else {
      Iterable<Instance> _actorInstances_1 = this.getActorInstances(network.getChildren());
      Iterable<Actor> _filter_2 = Iterables.<Actor>filter(network.getChildren(), Actor.class);
      Iterable<Vertex> _plus_4 = Iterables.<Vertex>concat(_actorInstances_1, _filter_2);
      for (final Vertex instance_1 : _plus_4) {
        this.unmapped.add(instance_1);
      }
    }
  }
  
  private boolean vertexExists(final Vertex newVertex) {
    Collection<List<Vertex>> _values = this.mapping.values();
    for (final List<Vertex> vertexList : _values) {
      for (final Vertex v : vertexList) {
        boolean _equals = v.equals(newVertex);
        if (_equals) {
          return true;
        }
      }
    }
    return false;
  }
  
  public Mapping(final Network network, final File xcfFile) {
    try {
      this.setNetwork(network);
      if (((!xcfFile.exists()) || (!xcfFile.isFile()))) {
        throw new OrccRuntimeException("The XCF file does not exist.");
      }
      final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      final Document dom = builder.parse(xcfFile);
      final Element configuration = dom.getDocumentElement();
      configuration.normalize();
      Node _item = configuration.getElementsByTagName("Partitioning").item(0);
      final Element partitioning = ((Element) _item);
      if ((partitioning != null)) {
        final NodeList partitions = partitioning.getElementsByTagName("Partition");
        int _length = partitions.getLength();
        int _minus = (_length - 1);
        IntegerRange _upTo = new IntegerRange(0, _minus);
        for (final Integer i : _upTo) {
          {
            final Node partNode = partitions.item((i).intValue());
            final Element partition = ((Element) partNode);
            final String partName = partition.getAttribute("id");
            final NodeList instances = partition.getElementsByTagName("Instance");
            int _length_1 = instances.getLength();
            int _minus_1 = (_length_1 - 1);
            IntegerRange _upTo_1 = new IntegerRange(0, _minus_1);
            for (final Integer j : _upTo_1) {
              {
                final Node instNode = instances.item((j).intValue());
                final Element instance = ((Element) instNode);
                final String instName = instance.getAttribute("id");
                final Vertex vertex = network.getChild(instName);
                if ((vertex != null)) {
                  this.tryToMap(vertex, partName);
                } else {
                  OrccLogger.warnln(("Try to map an unknown actor called " + instName));
                }
              }
            }
          }
        }
      } else {
        throw new OrccRuntimeException("Wrong XCF file");
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private List<Vertex> setNetwork(final Network network) {
    List<Vertex> _xblockexpression = null;
    {
      this.network = network;
      HashMap<String, List<Vertex>> _hashMap = new HashMap<String, List<Vertex>>();
      this.mapping = _hashMap;
      HashMap<Vertex, String> _hashMap_1 = new HashMap<Vertex, String>();
      this.invMapping = _hashMap_1;
      ArrayList<Vertex> _arrayList = new ArrayList<Vertex>();
      _xblockexpression = this.unmapped = _arrayList;
    }
    return _xblockexpression;
  }
  
  private Object tryToMap(final Vertex vertex, final String component) {
    Object _xifexpression = null;
    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(component);
    boolean _not = (!_isNullOrEmpty);
    if (_not) {
      _xifexpression = this.map(component, vertex);
    } else {
      boolean _xblockexpression = false;
      {
        String _label = vertex.getLabel();
        String _plus = ("The instance \'" + _label);
        String _plus_1 = (_plus + "\' is not mapped.");
        OrccLogger.warnln(_plus_1);
        _xblockexpression = this.unmapped.add(vertex);
      }
      _xifexpression = Boolean.valueOf(_xblockexpression);
    }
    return _xifexpression;
  }
  
  public CharSequence getContentFile() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<Configuration>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<Partitioning>");
    _builder.newLine();
    {
      boolean _isEmpty = this.mapping.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          Collection<List<Vertex>> _values = this.mapping.values();
          for(final List<Vertex> vertices : _values) {
            _builder.append("\t\t");
            CharSequence _partition = this.getPartition(vertices);
            _builder.append(_partition, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
      } else {
        _builder.append("\t\t");
        CharSequence _partition_1 = this.getPartition(this.network.getChildren());
        _builder.append(_partition_1, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      for(final Vertex vertex : this.unmapped) {
        _builder.append("\t\t");
        _builder.append("<!-- Unmapped id=\"");
        String _label = vertex.getLabel();
        _builder.append(_label, "\t\t");
        _builder.append("\" -->");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("</Partitioning>");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- Other useful informations related to any element of the instanciated model can be printed here -->");
    _builder.newLine();
    _builder.append("</Configuration>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getPartition(final Iterable<Vertex> entities) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<Partition id=\"");
    _builder.append(this.i = (this.i + 1));
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    {
      for(final Vertex entity : entities) {
        _builder.append("\t");
        _builder.append("<Instance id=\"");
        String _label = entity.getLabel();
        _builder.append(_label, "\t");
        _builder.append("\"/>");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</Partition>");
    _builder.newLine();
    return _builder;
  }
  
  public Set<String> getComponents() {
    return this.mapping.keySet();
  }
  
  public String getComponent(final Vertex v) {
    return this.invMapping.get(v);
  }
  
  public Map<String, List<Vertex>> getMapping() {
    return this.mapping;
  }
  
  public List<Vertex> getUnmapped() {
    return this.unmapped;
  }
  
  public String map(final String component, final Vertex v) {
    String _xblockexpression = null;
    {
      boolean _containsKey = this.mapping.containsKey(component);
      boolean _not = (!_containsKey);
      if (_not) {
        ArrayList<Vertex> _arrayList = new ArrayList<Vertex>();
        this.mapping.put(component, _arrayList);
      }
      this.mapping.get(component).add(v);
      _xblockexpression = this.invMapping.put(v, component);
    }
    return _xblockexpression;
  }
}
