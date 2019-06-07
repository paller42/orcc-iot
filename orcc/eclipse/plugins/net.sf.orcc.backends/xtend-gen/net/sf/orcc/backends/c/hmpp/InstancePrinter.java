/**
 * Copyright (c) 2009-2013, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c.hmpp;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.Attributable;
import net.sf.orcc.util.Attribute;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {
  private final List<Procedure> codelets = CollectionLiterals.<Procedure>newArrayList();
  
  private final List<InstCall> callsites = CollectionLiterals.<InstCall>newArrayList();
  
  public List<Procedure> getCodelets() {
    return this.codelets;
  }
  
  public List<InstCall> getCallSites() {
    return this.callsites;
  }
  
  public String defaultFileName(final Procedure procedure) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CODELET_CODE_");
    String _name = procedure.getName();
    _builder.append(_name);
    _builder.append("_DEFAULT.c");
    return _builder.toString();
  }
  
  public String wrapperFileName(final Procedure procedure) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CODELET_CODE_");
    String _name = procedure.getName();
    _builder.append(_name);
    _builder.append("_GEN_WRAPPER.c");
    return _builder.toString();
  }
  
  public String selectorFileName(final InstCall call) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CODELET_CODE_");
    String _name = call.getProcedure().getName();
    _builder.append(_name);
    _builder.append("_SELECTOR.c");
    return _builder.toString();
  }
  
  @Override
  public CharSequence caseInstCall(final InstCall call) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printCallsite = this.printCallsite(call);
    _builder.append(_printCallsite);
    _builder.newLineIfNotEmpty();
    CharSequence _caseInstCall = super.caseInstCall(call);
    _builder.append(_caseInstCall);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public String caseBlockFor(final BlockFor block) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printGridify = this.printGridify(block);
    _builder.append(_printGridify);
    _builder.newLineIfNotEmpty();
    String _caseBlockFor = super.caseBlockFor(block);
    _builder.append(_caseBlockFor);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  @Override
  protected CharSequence declare(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printResident = this.printResident(variable);
    _builder.append(_printResident);
    _builder.newLineIfNotEmpty();
    CharSequence _declare = super.declare(variable);
    _builder.append(_declare);
    return _builder;
  }
  
  @Override
  protected CharSequence declare(final Procedure proc) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printCodelet = this.printCodelet(proc);
    _builder.append(_printCodelet);
    _builder.newLineIfNotEmpty();
    CharSequence _declare = super.declare(proc);
    _builder.append(_declare);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  protected CharSequence beforeActionBody() {
    return this.printAdvancedload(this.currentAction.getBody());
  }
  
  @Override
  protected CharSequence afterActionBody() {
    return this.printDelegatedstore(this.currentAction.getBody());
  }
  
  public CharSequence getDefaultContent(final Procedure procedure) {
    return super.print(procedure);
  }
  
  public CharSequence getWrapperContent(final Procedure procedure) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"");
    String _defaultFileName = this.defaultFileName(procedure);
    _builder.append(_defaultFileName);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence getSelectorContent(final InstCall call) {
    CharSequence _xblockexpression = null;
    {
      final Attribute group = IterableExtensions.<Attribute>head(this.filterGroupsLabels(call.getAttribute("callsite").getAttributes()));
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/* Group name chosen */");
      _builder.newLine();
      _builder.append("#pragma orcc2hmpp group=");
      String _replaceAll = group.getName().replaceAll("<|>", "");
      _builder.append(_replaceAll);
      _builder.newLineIfNotEmpty();
      _builder.append("/* Accelerator identifier */");
      _builder.newLine();
      _builder.append("#pragma orcc2hmpp GPU=1");
      _builder.newLine();
      _builder.append("#pragma hmpp ");
      String _name = group.getName();
      _builder.append(_name);
      _builder.append(" cdlt_");
      String _name_1 = call.getProcedure().getName();
      _builder.append(_name_1);
      _builder.append(" callsite");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  protected CharSequence print(final Procedure proc) {
    CharSequence _xifexpression = null;
    boolean _hasAttribute = proc.hasAttribute("codelet");
    if (_hasAttribute) {
      CharSequence _xblockexpression = null;
      {
        this.codelets.add(proc);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("#include \"");
        String _wrapperFileName = this.wrapperFileName(proc);
        _builder.append(_wrapperFileName);
        _builder.append("\"");
        _builder.newLineIfNotEmpty();
        _xblockexpression = _builder;
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = super.print(proc);
    }
    return _xifexpression;
  }
  
  @Override
  protected CharSequence printAttributes(final Attributable object) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((((object instanceof Instance) || (object instanceof Actor)) && object.hasAttribute("group"))) {
        {
          Iterable<Attribute> _filterGroupsLabels = this.filterGroupsLabels(object.getAttribute("group").getAttributes());
          for(final Attribute grp : _filterGroupsLabels) {
            final EList<Attribute> params = grp.getAttributes();
            _builder.newLineIfNotEmpty();
            _builder.append("#pragma hmpp ");
            String _name = grp.getName();
            _builder.append(_name);
            _builder.append(" group");
            final Function1<Attribute, CharSequence> _function = new Function1<Attribute, CharSequence>() {
              @Override
              public CharSequence apply(final Attribute it) {
                StringConcatenation _builder = new StringConcatenation();
                String _name = it.getName();
                _builder.append(_name);
                _builder.append("=");
                String _stringValue = it.getStringValue();
                _builder.append(_stringValue);
                return _builder.toString();
              }
            };
            String _join = IterableExtensions.<Attribute>join(params, ", ", ", ", "", _function);
            _builder.append(_join);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  private CharSequence printResident(final Var variable) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasAttribute = variable.hasAttribute("resident");
      if (_hasAttribute) {
        {
          Iterable<Attribute> _filterGroupsLabels = this.filterGroupsLabels(variable.getAttribute("resident").getAttributes());
          for(final Attribute grp : _filterGroupsLabels) {
            final String direction = grp.getValueAsString("direction");
            _builder.newLineIfNotEmpty();
            String _xifexpression = null;
            boolean _isList = variable.getType().isList();
            if (_isList) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(", args[::");
              String _name = variable.getName();
              _builder_1.append(_name);
              _builder_1.append("].size={");
              Type _type = variable.getType();
              String _join = IterableExtensions.join(((TypeList) _type).getDimensions(), ",");
              _builder_1.append(_join);
              _builder_1.append("}");
              _xifexpression = _builder_1.toString();
            } else {
              _xifexpression = "";
            }
            final String sizeArg = _xifexpression;
            _builder.newLineIfNotEmpty();
            _builder.append("#pragma hmpp ");
            String _name_1 = grp.getName();
            _builder.append(_name_1);
            _builder.append(" resident, args[::");
            String _name_2 = variable.getName();
            _builder.append(_name_2);
            _builder.append("].io=");
            _builder.append(direction);
            _builder.append(sizeArg);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  private CharSequence printGridify(final BlockFor block) {
    CharSequence _xifexpression = null;
    boolean _hasAttribute = block.hasAttribute("gridify");
    if (_hasAttribute) {
      CharSequence _xblockexpression = null;
      {
        final EList<Attribute> attrList = block.getAttribute("gridify").getAttributes();
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("#pragma hmppcg gridify (");
        final Function1<Attribute, CharSequence> _function = new Function1<Attribute, CharSequence>() {
          @Override
          public CharSequence apply(final Attribute it) {
            StringConcatenation _builder = new StringConcatenation();
            EObject _containedValue = it.getContainedValue();
            String _name = ((ExprVar) _containedValue).getUse().getVariable().getName();
            _builder.append(_name);
            return _builder.toString();
          }
        };
        String _join = IterableExtensions.<Attribute>join(attrList, ",", _function);
        _builder.append(_join);
        _builder.append(")");
        _xblockexpression = _builder;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  private CharSequence printCodelet(final Procedure proc) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasAttribute = proc.hasAttribute("codelet");
      if (_hasAttribute) {
        {
          Iterable<Attribute> _filterGroupsLabels = this.filterGroupsLabels(proc.getAttribute("codelet").getAttributes());
          for(final Attribute grp : _filterGroupsLabels) {
            {
              Iterable<Attribute> _filterCodeletsLabels = this.filterCodeletsLabels(grp.getAttributes());
              for(final Attribute cdlt : _filterCodeletsLabels) {
                final EList<Attribute> params = proc.getAttribute("codelet").getAttributes();
                _builder.newLineIfNotEmpty();
                final Function1<Attribute, CharSequence> _function = new Function1<Attribute, CharSequence>() {
                  @Override
                  public CharSequence apply(final Attribute it) {
                    StringConcatenation _builder = new StringConcatenation();
                    String _name = it.getName();
                    _builder.append(_name);
                    _builder.append("=");
                    String _stringValue = it.getStringValue();
                    _builder.append(_stringValue);
                    return _builder.toString();
                  }
                };
                final String paramsString = IterableExtensions.<Attribute>join(this.filterNoGroupsLabels(params), ", ", ", ", "", _function);
                _builder.newLineIfNotEmpty();
                _builder.newLine();
                Object _objectValue = cdlt.getObjectValue();
                final Map<String, String> vars = ((Map<String, String>) _objectValue);
                _builder.newLineIfNotEmpty();
                final Function1<Map.Entry<String, String>, CharSequence> _function_1 = new Function1<Map.Entry<String, String>, CharSequence>() {
                  @Override
                  public CharSequence apply(final Map.Entry<String, String> it) {
                    StringConcatenation _builder = new StringConcatenation();
                    _builder.append("args[");
                    String _key = it.getKey();
                    _builder.append(_key);
                    _builder.append("].io=");
                    String _value = it.getValue();
                    _builder.append(_value);
                    return _builder.toString();
                  }
                };
                final String transferString = IterableExtensions.<Map.Entry<String, String>>join(vars.entrySet(), ", ", ", ", "", _function_1);
                _builder.newLineIfNotEmpty();
                _builder.append("#pragma hmpp ");
                String _name = grp.getName();
                _builder.append(_name);
                _builder.append(" ");
                String _name_1 = cdlt.getName();
                _builder.append(_name_1);
                _builder.append(" codelet");
                _builder.append(paramsString);
                _builder.append(transferString);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  private CharSequence printCallsite(final InstCall call) {
    CharSequence _xifexpression = null;
    boolean _hasAttribute = call.hasAttribute("callsite");
    if (_hasAttribute) {
      CharSequence _xblockexpression = null;
      {
        this.callsites.add(call);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("#include \"");
        String _selectorFileName = this.selectorFileName(call);
        _builder.append(_selectorFileName);
        _builder.append("\"");
        _xblockexpression = _builder;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  private CharSequence printAdvancedload(final Procedure procedure) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasAttribute = procedure.hasAttribute("advancedload");
      if (_hasAttribute) {
        {
          Iterable<Attribute> _filterGroupsLabels = this.filterGroupsLabels(procedure.getAttribute("advancedload").getAttributes());
          for(final Attribute grp : _filterGroupsLabels) {
            {
              Iterable<Attribute> _filterCodeletsLabels = this.filterCodeletsLabels(grp.getAttributes());
              for(final Attribute cdlt : _filterCodeletsLabels) {
                {
                  Object _objectValue = cdlt.getObjectValue();
                  for(final String varName : ((Set<String>) _objectValue)) {
                    _builder.append("#pragma hmpp ");
                    String _name = grp.getName();
                    _builder.append(_name);
                    _builder.append(" ");
                    String _name_1 = cdlt.getName();
                    _builder.append(_name_1);
                    _builder.append(" advancedload, args[::");
                    _builder.append(varName);
                    _builder.append("]");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  private CharSequence printDelegatedstore(final Procedure procedure) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasAttribute = procedure.hasAttribute("delegatedstore");
      if (_hasAttribute) {
        {
          Iterable<Attribute> _filterGroupsLabels = this.filterGroupsLabels(procedure.getAttribute("delegatedstore").getAttributes());
          for(final Attribute grp : _filterGroupsLabels) {
            {
              Iterable<Attribute> _filterCodeletsLabels = this.filterCodeletsLabels(grp.getAttributes());
              for(final Attribute cdlt : _filterCodeletsLabels) {
                {
                  Object _objectValue = cdlt.getObjectValue();
                  for(final String varName : ((Set<String>) _objectValue)) {
                    _builder.append("#pragma hmpp ");
                    String _name = grp.getName();
                    _builder.append(_name);
                    _builder.append(" ");
                    String _name_1 = cdlt.getName();
                    _builder.append(_name_1);
                    _builder.append(" delegatedstore, args[::");
                    _builder.append(varName);
                    _builder.append("]");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  private Iterable<Attribute> filterGroupsLabels(final Iterable<Attribute> attrs) {
    final Function1<Attribute, Boolean> _function = new Function1<Attribute, Boolean>() {
      @Override
      public Boolean apply(final Attribute it) {
        return Boolean.valueOf(it.getName().startsWith("<grp_"));
      }
    };
    return IterableExtensions.<Attribute>filter(attrs, _function);
  }
  
  private Iterable<Attribute> filterNoGroupsLabels(final Iterable<Attribute> attrs) {
    final Function1<Attribute, Boolean> _function = new Function1<Attribute, Boolean>() {
      @Override
      public Boolean apply(final Attribute it) {
        boolean _startsWith = it.getName().startsWith("<grp_");
        return Boolean.valueOf((!_startsWith));
      }
    };
    return IterableExtensions.<Attribute>filter(attrs, _function);
  }
  
  private Iterable<Attribute> filterCodeletsLabels(final Iterable<Attribute> attrs) {
    final Function1<Attribute, Boolean> _function = new Function1<Attribute, Boolean>() {
      @Override
      public Boolean apply(final Attribute it) {
        return Boolean.valueOf(it.getName().startsWith("cdlt_"));
      }
    };
    return IterableExtensions.<Attribute>filter(attrs, _function);
  }
}
