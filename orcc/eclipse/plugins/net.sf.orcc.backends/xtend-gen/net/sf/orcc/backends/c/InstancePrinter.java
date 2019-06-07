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
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF YUSE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.c;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.sf.orcc.OrccLaunchConstants;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.BackendsConstants;
import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.Attributable;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.OrccAttributes;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.util.EcoreHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Generate and print instance source file for C backend.
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class InstancePrinter extends CTemplate {
  protected Instance instance;
  
  protected Actor actor;
  
  protected Attributable attributable;
  
  protected Map<Port, Connection> incomingPortMap;
  
  protected Map<Port, List<Connection>> outgoingPortMap;
  
  protected String entityName;
  
  private boolean profile = false;
  
  private boolean inlineActors = false;
  
  private boolean inlineActions = false;
  
  private boolean checkArrayInbounds = false;
  
  private boolean newSchedul = false;
  
  private boolean enableTrace = false;
  
  private String traceFolder;
  
  private boolean isActionAligned = false;
  
  private boolean debugActor = false;
  
  private boolean debugAction = false;
  
  private boolean papify = false;
  
  private boolean papifyMultiplex = false;
  
  private Iterable<Action> papifyActions;
  
  private Iterable<String> papiEvents;
  
  private boolean genWeights = false;
  
  private boolean genWeightsFilter = true;
  
  private boolean genWeightsDump = false;
  
  private boolean genWeightsExit = false;
  
  private Action genWeightsExitAction = null;
  
  private String genWeightsExitCond = null;
  
  private boolean linkNativeLib;
  
  private String linkNativeLibHeaders;
  
  protected final Pattern inputPattern = DfFactory.eINSTANCE.createPattern();
  
  protected final Map<State, Pattern> transitionPattern = new HashMap<State, Pattern>();
  
  protected Action currentAction;
  
  @Override
  public void setOptions(final Map<String, Object> options) {
    super.setOptions(options);
    boolean _containsKey = options.containsKey(BackendsConstants.PROFILE);
    if (_containsKey) {
      Object _get = options.get(BackendsConstants.PROFILE);
      this.profile = (((Boolean) _get)).booleanValue();
    }
    boolean _containsKey_1 = options.containsKey(BackendsConstants.CHECK_ARRAY_INBOUNDS);
    if (_containsKey_1) {
      Object _get_1 = options.get(BackendsConstants.CHECK_ARRAY_INBOUNDS);
      this.checkArrayInbounds = (((Boolean) _get_1)).booleanValue();
    }
    boolean _containsKey_2 = options.containsKey(BackendsConstants.NEW_SCHEDULER);
    if (_containsKey_2) {
      Object _get_2 = options.get(BackendsConstants.NEW_SCHEDULER);
      this.newSchedul = (((Boolean) _get_2)).booleanValue();
    }
    boolean _containsKey_3 = options.containsKey(OrccLaunchConstants.ENABLE_TRACES);
    if (_containsKey_3) {
      Object _get_3 = options.get(OrccLaunchConstants.ENABLE_TRACES);
      this.enableTrace = (((Boolean) _get_3)).booleanValue();
      Object _get_4 = options.get(OrccLaunchConstants.TRACES_FOLDER);
      String _replace = null;
      if (((String) _get_4)!=null) {
        _replace=((String) _get_4).replace("\\", "\\\\");
      }
      this.traceFolder = _replace;
    }
    boolean _containsKey_4 = options.containsKey(BackendsConstants.INLINE);
    if (_containsKey_4) {
      Object _get_5 = options.get(BackendsConstants.INLINE);
      this.inlineActors = (((Boolean) _get_5)).booleanValue();
      boolean _containsKey_5 = options.containsKey(BackendsConstants.INLINE_NOTACTIONS);
      if (_containsKey_5) {
        Object _get_6 = options.get(BackendsConstants.INLINE_NOTACTIONS);
        boolean _not = (!(((Boolean) _get_6)).booleanValue());
        this.inlineActions = _not;
      }
    }
    boolean _containsKey_6 = options.containsKey(BackendsConstants.PAPIFY);
    if (_containsKey_6) {
      Object _get_7 = options.get(BackendsConstants.PAPIFY);
      this.papify = (((Boolean) _get_7)).booleanValue();
      boolean _containsKey_7 = options.containsKey(BackendsConstants.PAPIFY_MULTIPLEX);
      if (_containsKey_7) {
        Object _get_8 = options.get(BackendsConstants.PAPIFY_MULTIPLEX);
        this.papifyMultiplex = (((Boolean) _get_8)).booleanValue();
      }
    }
    boolean _containsKey_8 = options.containsKey(BackendsConstants.GEN_WEIGHTS);
    if (_containsKey_8) {
      Object _get_9 = options.get(BackendsConstants.GEN_WEIGHTS);
      this.genWeights = (((Boolean) _get_9)).booleanValue();
    }
    boolean _containsKey_9 = options.containsKey(BackendsConstants.GEN_WEIGHTS_FILTER);
    if (_containsKey_9) {
      Object _get_10 = options.get(BackendsConstants.GEN_WEIGHTS_FILTER);
      this.genWeightsFilter = (((Boolean) _get_10)).booleanValue();
    }
    boolean _containsKey_10 = options.containsKey(BackendsConstants.GEN_WEIGHTS_DUMP);
    if (_containsKey_10) {
      Object _get_11 = options.get(BackendsConstants.GEN_WEIGHTS_DUMP);
      this.genWeightsDump = (((Boolean) _get_11)).booleanValue();
    }
    boolean _containsKey_11 = options.containsKey(BackendsConstants.LINK_NATIVE_LIBRARY);
    if (_containsKey_11) {
      Object _get_12 = options.get(BackendsConstants.LINK_NATIVE_LIBRARY);
      this.linkNativeLib = (((Boolean) _get_12)).booleanValue();
      Object _get_13 = options.get(BackendsConstants.LINK_NATIVE_LIBRARY_HEADERS);
      this.linkNativeLibHeaders = ((String) _get_13);
    }
  }
  
  public CharSequence getInstanceContent(final Instance instance) {
    CharSequence _xblockexpression = null;
    {
      this.setInstance(instance);
      _xblockexpression = this.getFileContent();
    }
    return _xblockexpression;
  }
  
  public CharSequence getActorContent(final Actor actor) {
    CharSequence _xblockexpression = null;
    {
      this.setActor(actor);
      _xblockexpression = this.getFileContent();
    }
    return _xblockexpression;
  }
  
  public Object setInstance(final Instance instance) {
    Object _xblockexpression = null;
    {
      boolean _isActor = instance.isActor();
      boolean _not = (!_isActor);
      if (_not) {
        throw new OrccRuntimeException((("Instance " + this.entityName) + " is not an Actor\'s instance"));
      }
      this.instance = instance;
      this.entityName = instance.getName();
      this.actor = instance.getActor();
      this.attributable = instance;
      this.incomingPortMap = instance.getIncomingPortMap();
      this.outgoingPortMap = instance.getOutgoingPortMap();
      this.setDebug();
      this.buildInputPattern();
      this.buildTransitionPattern();
      this.initializePapifyOptions();
      _xblockexpression = this.initializeGenWeightsOptions();
    }
    return _xblockexpression;
  }
  
  public Object setActor(final Actor actor) {
    Object _xblockexpression = null;
    {
      this.entityName = actor.getName();
      this.actor = actor;
      this.attributable = actor;
      this.incomingPortMap = actor.getIncomingPortMap();
      this.outgoingPortMap = actor.getOutgoingPortMap();
      this.setDebug();
      this.buildInputPattern();
      this.buildTransitionPattern();
      this.initializePapifyOptions();
      _xblockexpression = this.initializeGenWeightsOptions();
    }
    return _xblockexpression;
  }
  
  private Iterable<String> initializePapifyOptions() {
    Iterable<String> _xifexpression = null;
    if (this.papify) {
      Iterable<String> _xblockexpression = null;
      {
        final Function1<Action, Boolean> _function = new Function1<Action, Boolean>() {
          @Override
          public Boolean apply(final Action it) {
            return Boolean.valueOf(it.hasAttribute(OrccAttributes.PAPIFY_ATTRIBUTE));
          }
        };
        this.papifyActions = IterableExtensions.<Action>filter(this.actor.getActions(), _function);
        if ((this.actor.hasAttribute(OrccAttributes.PAPIFY_ATTRIBUTE) && (((Object[])Conversions.unwrapArray(this.papifyActions, Object.class)).length == 0))) {
          EList<Action> _actions = this.actor.getActions();
          for (final Action action : _actions) {
            action.addAttribute(OrccAttributes.PAPIFY_ATTRIBUTE);
          }
          final Function1<Action, Boolean> _function_1 = new Function1<Action, Boolean>() {
            @Override
            public Boolean apply(final Action it) {
              return Boolean.valueOf(it.hasAttribute(OrccAttributes.PAPIFY_ATTRIBUTE));
            }
          };
          this.papifyActions = IterableExtensions.<Action>filter(this.actor.getActions(), _function_1);
        }
        Attribute _attribute = this.actor.getAttribute(OrccAttributes.PAPIFY_ATTRIBUTE);
        EList<Attribute> _attributes = null;
        if (_attribute!=null) {
          _attributes=_attribute.getAttributes();
        }
        List<String> _map = null;
        if (_attributes!=null) {
          final Function1<Attribute, String> _function_2 = new Function1<Attribute, String>() {
            @Override
            public String apply(final Attribute it) {
              return it.getName();
            }
          };
          _map=ListExtensions.<Attribute, String>map(_attributes, _function_2);
        }
        _xblockexpression = this.papiEvents = _map;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  private Object initializeGenWeightsOptions() {
    Object _xifexpression = null;
    if ((this.genWeights && (((Object[])Conversions.unwrapArray(IterableExtensions.<Action>filter(this.actor.getActions(), new Function1<Action, Boolean>() {
      @Override
      public Boolean apply(final Action it) {
        return Boolean.valueOf(it.hasAttribute(OrccAttributes.GEN_WEIGHTS_EXIT_ATTRIBUTE));
      }
    }), Object.class)).length > 0))) {
      String _xblockexpression = null;
      {
        this.genWeightsExit = true;
        final Function1<Action, Boolean> _function = new Function1<Action, Boolean>() {
          @Override
          public Boolean apply(final Action it) {
            return Boolean.valueOf(it.hasAttribute(OrccAttributes.GEN_WEIGHTS_EXIT_ATTRIBUTE));
          }
        };
        this.genWeightsExitAction = ((Action[])Conversions.unwrapArray(IterableExtensions.<Action>filter(this.actor.getActions(), _function), Action.class))[0];
        Attribute _attribute = this.genWeightsExitAction.getAttribute(OrccAttributes.GEN_WEIGHTS_EXIT_ATTRIBUTE);
        String _valueAsString = null;
        if (_attribute!=null) {
          _valueAsString=_attribute.getValueAsString("condition");
        }
        this.genWeightsExitCond = _valueAsString;
        String _xifexpression_1 = null;
        if (((this.genWeightsExitCond == null) || Objects.equal(this.genWeightsExitCond, ""))) {
          _xifexpression_1 = this.genWeightsExitCond = "1";
        }
        _xblockexpression = _xifexpression_1;
      }
      _xifexpression = _xblockexpression;
    } else {
      boolean _xblockexpression_1 = false;
      {
        this.genWeightsExit = false;
        this.genWeightsExitAction = null;
        _xblockexpression_1 = (this.genWeightsExitCond == null);
      }
      _xifexpression = Boolean.valueOf(_xblockexpression_1);
    }
    return _xifexpression;
  }
  
  protected CharSequence getFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// Source file is \"");
    IFile _file = this.actor.getFile();
    _builder.append(_file);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
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
    {
      if (this.checkArrayInbounds) {
        _builder.append("#include <assert.h>");
        _builder.newLine();
      }
    }
    _builder.append("#include \"orcc_config.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"types.h\"");
    _builder.newLine();
    _builder.append("#include \"fifo.h\"");
    _builder.newLine();
    _builder.append("#include \"util.h\"");
    _builder.newLine();
    _builder.append("#include \"scheduler.h\"");
    _builder.newLine();
    _builder.append("#include \"dataflow.h\"");
    _builder.newLine();
    _builder.append("#include \"cycle.h\"");
    _builder.newLine();
    {
      if ((this.actor.hasAttribute(OrccAttributes.PAPIFY_ATTRIBUTE) && this.papify)) {
        _builder.append("#include \"eventLib.h\"");
        _builder.newLine();
        _builder.append("FILE* papi_output_");
        String _name = this.actor.getName();
        _builder.append(_name);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("papi_action_s *Papi_actions_");
        String _name_1 = this.actor.getName();
        _builder.append(_name_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("long long papi_");
        String _name_2 = this.actor.getName();
        _builder.append(_name_2);
        _builder.append("_start_usec, papi_");
        String _name_3 = this.actor.getName();
        _builder.append(_name_3);
        _builder.append("_end_usec;");
        _builder.newLineIfNotEmpty();
        _builder.append("int papi_");
        String _name_4 = this.actor.getName();
        _builder.append(_name_4);
        _builder.append("_eventCodeSetSize;");
        _builder.newLineIfNotEmpty();
        _builder.append("int *papi_");
        String _name_5 = this.actor.getName();
        _builder.append(_name_5);
        _builder.append("_eventCodeSet;");
        _builder.newLineIfNotEmpty();
        _builder.append("unsigned long *papi_");
        String _name_6 = this.actor.getName();
        _builder.append(_name_6);
        _builder.append("_eventset;");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      if (this.profile) {
        _builder.append("#include \"profiling.h\"");
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      if (this.genWeights) {
        _builder.append("#include \"rdtsc.h\"");
        _builder.newLine();
        _builder.append("#include \"options.h\"");
        _builder.newLine();
        _builder.append("#include <libgen.h>");
        _builder.newLine();
        _builder.newLine();
      }
    }
    {
      if ((this.linkNativeLib && (!Objects.equal(this.linkNativeLibHeaders, "")))) {
        CharSequence _printNativeLibHeaders = this.printNativeLibHeaders(this.linkNativeLibHeaders);
        _builder.append(_printNativeLibHeaders);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      if ((this.instance != null)) {
        CharSequence _printAttributes = this.printAttributes(this.instance);
        _builder.append(_printAttributes);
        _builder.newLineIfNotEmpty();
      } else {
        CharSequence _printAttributes_1 = this.printAttributes(this.actor);
        _builder.append(_printAttributes_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Instance");
    _builder.newLine();
    _builder.append("extern actor_t ");
    _builder.append(this.entityName);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _hasAttribute = this.actor.hasAttribute("actor_shared_variables");
      if (_hasAttribute) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Shared Variables");
        _builder.newLine();
        {
          Object _objectValue = this.actor.getAttribute("actor_shared_variables").getObjectValue();
          for(final Var v : ((HashSet<Var>) _objectValue)) {
            _builder.append("extern ");
            CharSequence _doSwitch = this.doSwitch(v.getType());
            _builder.append(_doSwitch);
            _builder.append(" ");
            String _name_7 = v.getName();
            _builder.append(_name_7);
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
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getInputs());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Input FIFOs");
        _builder.newLine();
        {
          EList<Port> _inputs = this.actor.getInputs();
          for(final Port port : _inputs) {
            String _xifexpression = null;
            Connection _get = this.incomingPortMap.get(port);
            boolean _tripleNotEquals = (_get != null);
            if (_tripleNotEquals) {
              _xifexpression = "extern ";
            }
            _builder.append(_xifexpression);
            _builder.append("fifo_");
            CharSequence _doSwitch_1 = this.doSwitch(port.getType());
            _builder.append(_doSwitch_1);
            _builder.append("_t *");
            CharSequence _fullName = this.fullName(port);
            _builder.append(_fullName);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Input Fifo control variables");
        _builder.newLine();
        {
          EList<Port> _inputs_1 = this.actor.getInputs();
          for(final Port port_1 : _inputs_1) {
            _builder.append("static unsigned int index_");
            String _name_8 = port_1.getName();
            _builder.append(_name_8);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("static unsigned int numTokens_");
            String _name_9 = port_1.getName();
            _builder.append(_name_9);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("#define SIZE_");
            String _name_10 = port_1.getName();
            _builder.append(_name_10);
            _builder.append(" ");
            Integer _sizeOrDefaultSize = this.sizeOrDefaultSize(this.incomingPortMap.get(port_1));
            _builder.append(_sizeOrDefaultSize);
            _builder.newLineIfNotEmpty();
            _builder.append("#define tokens_");
            String _name_11 = port_1.getName();
            _builder.append(_name_11);
            _builder.append(" ");
            CharSequence _fullName_1 = this.fullName(port_1);
            _builder.append(_fullName_1);
            _builder.append("->contents");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            {
              Connection _get_1 = this.incomingPortMap.get(port_1);
              boolean _tripleNotEquals_1 = (_get_1 != null);
              if (_tripleNotEquals_1) {
                _builder.append("extern connection_t connection_");
                String _label = this.incomingPortMap.get(port_1).getSource().getLabel();
                _builder.append(_label);
                _builder.append("_");
                String _label_1 = this.actor.getLabel();
                _builder.append(_label_1);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
                _builder.append("#define rate_");
                String _name_12 = port_1.getName();
                _builder.append(_name_12);
                _builder.append(" connection_");
                String _label_2 = this.incomingPortMap.get(port_1).getSource().getLabel();
                _builder.append(_label_2);
                _builder.append("_");
                String _label_3 = this.actor.getLabel();
                _builder.append(_label_3);
                _builder.append(".rate\t\t\t\t");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("extern connection_t connection_");
                String _name_13 = ((Port) port_1).getName();
                _builder.append(_name_13);
                _builder.append("_");
                String _label_4 = this.actor.getLabel();
                _builder.append(_label_4);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
                _builder.append("#define rate_");
                String _name_14 = port_1.getName();
                _builder.append(_name_14);
                _builder.append(" connection_");
                String _name_15 = ((Port) port_1).getName();
                _builder.append(_name_15);
                _builder.append("_");
                String _label_5 = this.actor.getLabel();
                _builder.append(_label_5);
                _builder.append(".rate\t\t\t\t");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        {
          if (this.enableTrace) {
            _builder.append("////////////////////////////////////////////////////////////////////////////////");
            _builder.newLine();
            _builder.append("// Trace files declaration (in)");
            _builder.newLine();
            {
              EList<Port> _inputs_2 = this.actor.getInputs();
              for(final Port port_2 : _inputs_2) {
                _builder.append("FILE *file_");
                String _name_16 = port_2.getName();
                _builder.append(_name_16);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.newLine();
          }
        }
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Predecessors");
        _builder.newLine();
        {
          EList<Port> _inputs_3 = this.actor.getInputs();
          for(final Port port_3 : _inputs_3) {
            {
              Connection _get_2 = this.incomingPortMap.get(port_3);
              boolean _tripleNotEquals_2 = (_get_2 != null);
              if (_tripleNotEquals_2) {
                _builder.append("extern actor_t ");
                String _label_6 = this.incomingPortMap.get(port_3).getSource().getLabel();
                _builder.append(_label_6);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        _builder.newLine();
      }
    }
    {
      final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(IterableExtensions.<Port>filter(this.actor.getOutputs(), _function));
      boolean _not_1 = (!_isNullOrEmpty_1);
      if (_not_1) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Output FIFOs");
        _builder.newLine();
        {
          final Function1<Port, Boolean> _function_1 = new Function1<Port, Boolean>() {
            @Override
            public Boolean apply(final Port it) {
              boolean _isNative = it.isNative();
              return Boolean.valueOf((!_isNative));
            }
          };
          Iterable<Port> _filter = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_1);
          for(final Port port_4 : _filter) {
            _builder.append("extern fifo_");
            CharSequence _doSwitch_2 = this.doSwitch(port_4.getType());
            _builder.append(_doSwitch_2);
            _builder.append("_t *");
            CharSequence _fullName_2 = this.fullName(port_4);
            _builder.append(_fullName_2);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Output Fifo control variables");
        _builder.newLine();
        {
          final Function1<Port, Boolean> _function_2 = new Function1<Port, Boolean>() {
            @Override
            public Boolean apply(final Port it) {
              boolean _isNative = it.isNative();
              return Boolean.valueOf((!_isNative));
            }
          };
          Iterable<Port> _filter_1 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_2);
          for(final Port port_5 : _filter_1) {
            _builder.append("static unsigned int index_");
            String _name_17 = port_5.getName();
            _builder.append(_name_17);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("#define NUM_READERS_");
            String _name_18 = port_5.getName();
            _builder.append(_name_18);
            _builder.append(" ");
            int _size = this.outgoingPortMap.get(port_5).size();
            _builder.append(_size);
            _builder.newLineIfNotEmpty();
            _builder.append("#define SIZE_");
            String _name_19 = port_5.getName();
            _builder.append(_name_19);
            _builder.append(" ");
            Integer _sizeOrDefaultSize_1 = this.sizeOrDefaultSize(this.outgoingPortMap.get(port_5).get(0));
            _builder.append(_sizeOrDefaultSize_1);
            _builder.newLineIfNotEmpty();
            _builder.append("#define tokens_");
            String _name_20 = port_5.getName();
            _builder.append(_name_20);
            _builder.append(" ");
            CharSequence _fullName_3 = this.fullName(port_5);
            _builder.append(_fullName_3);
            _builder.append("->contents");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
        {
          if (this.enableTrace) {
            _builder.append("////////////////////////////////////////////////////////////////////////////////");
            _builder.newLine();
            _builder.append("// Trace files declaration (out)");
            _builder.newLine();
            {
              final Function1<Port, Boolean> _function_3 = new Function1<Port, Boolean>() {
                @Override
                public Boolean apply(final Port it) {
                  boolean _isNative = it.isNative();
                  return Boolean.valueOf((!_isNative));
                }
              };
              Iterable<Port> _filter_2 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_3);
              for(final Port port_6 : _filter_2) {
                _builder.append("FILE *file_");
                String _name_21 = port_6.getName();
                _builder.append(_name_21);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.newLine();
          }
        }
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Successors");
        _builder.newLine();
        {
          EList<Port> _outputs = this.actor.getOutputs();
          for(final Port port_7 : _outputs) {
            {
              List<Connection> _get_3 = this.outgoingPortMap.get(port_7);
              for(final Connection successor : _get_3) {
                _builder.append("extern actor_t ");
                String _label_7 = successor.getTarget().getLabel();
                _builder.append(_label_7);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        _builder.newLine();
      }
    }
    {
      if ((((this.instance != null) && (!IterableExtensions.isNullOrEmpty(this.instance.getArguments()))) || (!IterableExtensions.isNullOrEmpty(this.actor.getParameters())))) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Parameter values of the instance");
        _builder.newLine();
        {
          if ((this.instance != null)) {
            {
              EList<Argument> _arguments = this.instance.getArguments();
              for(final Argument arg : _arguments) {
                {
                  boolean _isExprList = arg.getValue().isExprList();
                  if (_isExprList) {
                    _builder.append("static ");
                    {
                      Type _type = arg.getValue().getType();
                      boolean _isUint = ((TypeList) _type).getInnermostType().isUint();
                      if (_isUint) {
                        _builder.append("unsigned ");
                      }
                    }
                    _builder.append("int ");
                    String _name_22 = arg.getVariable().getName();
                    _builder.append(_name_22);
                    String _printArrayIndexes = this.printArrayIndexes(arg.getValue().getType().getDimensionsExpr());
                    _builder.append(_printArrayIndexes);
                    _builder.append(" = ");
                    CharSequence _doSwitch_3 = this.doSwitch(arg.getValue());
                    _builder.append(_doSwitch_3);
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  } else {
                    _builder.append("#define ");
                    String _name_23 = arg.getVariable().getName();
                    _builder.append(_name_23);
                    _builder.append(" ");
                    CharSequence _doSwitch_4 = this.doSwitch(arg.getValue());
                    _builder.append(_doSwitch_4);
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          } else {
            {
              EList<Var> _parameters = this.actor.getParameters();
              for(final Var variable : _parameters) {
                CharSequence _declare = this.declare(variable);
                _builder.append(_declare);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        _builder.newLine();
      }
    }
    {
      if (this.profile) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Action\'s workload for profiling");
        _builder.newLine();
        {
          EList<Action> _actions = this.actor.getActions();
          for(final Action action : _actions) {
            _builder.append("extern action_t action_");
            String _name_24 = this.actor.getName();
            _builder.append(_name_24);
            _builder.append("_");
            String _name_25 = action.getName();
            _builder.append(_name_25);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("#define action_");
            String _name_26 = action.getName();
            _builder.append(_name_26);
            _builder.append(" action_");
            String _name_27 = this.actor.getName();
            _builder.append(_name_27);
            _builder.append("_");
            String _name_28 = action.getName();
            _builder.append(_name_28);
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      if (this.genWeights) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Declarations for genWeights");
        _builder.newLine();
        CharSequence _printGenWeightsDeclartions = this.printGenWeightsDeclartions();
        _builder.append(_printGenWeightsDeclartions);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    {
      boolean _isNullOrEmpty_2 = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not_2 = (!_isNullOrEmpty_2);
      if (_not_2) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// State variables of the actor");
        _builder.newLine();
        {
          final Function1<Var, Boolean> _function_4 = new Function1<Var, Boolean>() {
            @Override
            public Boolean apply(final Var it) {
              boolean _hasAttribute = it.hasAttribute("shared");
              return Boolean.valueOf((!_hasAttribute));
            }
          };
          Iterable<Var> _filter_3 = IterableExtensions.<Var>filter(this.actor.getStateVars(), _function_4);
          for(final Var variable_1 : _filter_3) {
            CharSequence _declare_1 = this.declare(variable_1);
            _builder.append(_declare_1);
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
      }
    }
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Initial FSM state of the actor");
        _builder.newLine();
        _builder.append("enum states {");
        _builder.newLine();
        {
          EList<State> _states = this.actor.getFsm().getStates();
          boolean _hasElements = false;
          for(final State state : _states) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "\t");
            }
            _builder.append("\t");
            _builder.append("my_state_");
            String _name_29 = state.getName();
            _builder.append(_name_29, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("};");
        _builder.newLine();
        _builder.newLine();
        _builder.append("static char *stateNames[] = {");
        _builder.newLine();
        {
          EList<State> _states_1 = this.actor.getFsm().getStates();
          boolean _hasElements_1 = false;
          for(final State state_1 : _states_1) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate(",", "\t");
            }
            _builder.append("\t");
            _builder.append("\"");
            String _name_30 = state_1.getName();
            _builder.append(_name_30, "\t");
            _builder.append("\"");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("};");
        _builder.newLine();
        _builder.newLine();
        _builder.append("static enum states _FSM_state;");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.newLine();
    CharSequence _additionalDeclarations = this.additionalDeclarations();
    _builder.append(_additionalDeclarations);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Token functions");
    _builder.newLine();
    {
      EList<Port> _inputs_4 = this.actor.getInputs();
      for(final Port port_8 : _inputs_4) {
        CharSequence _readTokensFunctions = this.readTokensFunctions(port_8);
        _builder.append(_readTokensFunctions);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      Iterable<? extends Port> _notNative = this.getNotNative(this.actor.getOutputs());
      for(final Port port_9 : _notNative) {
        CharSequence _writeTokensFunctions = this.writeTokensFunctions(port_9);
        _builder.append(_writeTokensFunctions);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Functions/procedures");
    _builder.newLine();
    {
      EList<Procedure> _procs = this.actor.getProcs();
      for(final Procedure proc : _procs) {
        CharSequence _declare_2 = this.declare(proc);
        _builder.append(_declare_2);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      Iterable<? extends Procedure> _notNativeProcs = this.getNotNativeProcs(this.actor.getProcs());
      for(final Procedure proc_1 : _notNativeProcs) {
        CharSequence _print = this.print(proc_1);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Actions");
    _builder.newLine();
    {
      EList<Action> _actions_1 = this.actor.getActions();
      for(final Action action_1 : _actions_1) {
        CharSequence _print_1 = this.print(action_1);
        _builder.append(_print_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Initializes");
    _builder.newLine();
    CharSequence _printInitialize = this.printInitialize();
    _builder.append(_printInitialize);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Action scheduler");
    _builder.newLine();
    CharSequence _printActorScheduler = this.printActorScheduler();
    _builder.append(_printActorScheduler);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence printActorScheduler() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        CharSequence _printFsm = this.printFsm();
        _builder.append(_printFsm);
        _builder.newLineIfNotEmpty();
      } else {
        CharSequence _noInline = this.getNoInline();
        _builder.append(_noInline);
        _builder.append("void ");
        _builder.append(this.entityName);
        _builder.append("_scheduler(schedinfo_t *si) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("int i = 0;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("si->ports = 0;");
        _builder.newLine();
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printGenWeightsInitSchedulerProfilingVars = this.printGenWeightsInitSchedulerProfilingVars();
        _builder.append(_printGenWeightsInitSchedulerProfilingVars, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _printCallTokensFunctions = this.printCallTokensFunctions();
        _builder.append(_printCallTokensFunctions, "\t");
        _builder.newLineIfNotEmpty();
        {
          if (this.enableTrace) {
            _builder.append("\t");
            CharSequence _printOpenFiles = this.printOpenFiles();
            _builder.append(_printOpenFiles, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printActionSchedulingLoop = this.printActionSchedulingLoop(this.actor.getActionsOutsideFsm());
        _builder.append(_printActionSchedulingLoop, "\t");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("finished:");
        _builder.newLine();
        {
          if (this.enableTrace) {
            _builder.append("\t");
            CharSequence _printCloseFiles = this.printCloseFiles();
            _builder.append(_printCloseFiles, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        {
          EList<Port> _inputs = this.actor.getInputs();
          for(final Port port : _inputs) {
            _builder.append("\t");
            _builder.append("read_end_");
            String _name = port.getName();
            _builder.append(_name, "\t");
            _builder.append("();");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Iterable<? extends Port> _notNative = this.getNotNative(this.actor.getOutputs());
          for(final Port port_1 : _notNative) {
            _builder.append("\t");
            _builder.append("write_end_");
            String _name_1 = port_1.getName();
            _builder.append(_name_1, "\t");
            _builder.append("();");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          if ((IterableExtensions.isNullOrEmpty(this.actor.getInputs()) && IterableExtensions.isNullOrEmpty(this.actor.getOutputs()))) {
            _builder.append("\t");
            _builder.append("// no read_end/write_end here!");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("return;");
            _builder.newLine();
          }
        }
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence printFsm() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _inline = this.getInline();
        _builder.append(_inline);
        _builder.append("void ");
        _builder.append(this.entityName);
        _builder.append("_outside_FSM_scheduler(schedinfo_t *si) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("int i = 0;");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printGenWeightsInitSchedulerProfilingVars = this.printGenWeightsInitSchedulerProfilingVars();
        _builder.append(_printGenWeightsInitSchedulerProfilingVars, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _printActionSchedulingLoop = this.printActionSchedulingLoop(this.actor.getActionsOutsideFsm());
        _builder.append(_printActionSchedulingLoop, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("finished:");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("// no read_end/write_end here!");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("return;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    CharSequence _noInline = this.getNoInline();
    _builder.append(_noInline);
    _builder.append("void ");
    _builder.append(this.entityName);
    _builder.append("_scheduler(schedinfo_t *si) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("int i = 0;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    CharSequence _printGenWeightsInitSchedulerProfilingVars_1 = this.printGenWeightsInitSchedulerProfilingVars();
    _builder.append(_printGenWeightsInitSchedulerProfilingVars_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _printCallTokensFunctions = this.printCallTokensFunctions();
    _builder.append(_printCallTokensFunctions, "\t");
    _builder.newLineIfNotEmpty();
    {
      if (this.enableTrace) {
        _builder.append("\t");
        CharSequence _printOpenFiles = this.printOpenFiles();
        _builder.append(_printOpenFiles, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// jump to FSM state");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("switch (_FSM_state) {");
    _builder.newLine();
    {
      EList<State> _states = this.actor.getFsm().getStates();
      for(final State state : _states) {
        _builder.append("\t");
        _builder.append("case my_state_");
        String _name = state.getName();
        _builder.append(_name, "\t");
        _builder.append(":");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("goto l_");
        String _name_1 = state.getName();
        _builder.append(_name_1, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printf(\"unknown state in ");
    _builder.append(this.entityName, "\t\t");
    _builder.append(".c : %s\\n\", stateNames[_FSM_state]);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("exit(1);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// FSM transitions");
    _builder.newLine();
    {
      EList<State> _states_1 = this.actor.getFsm().getStates();
      for(final State state_1 : _states_1) {
        CharSequence _printStateLabel = this.printStateLabel(state_1);
        _builder.append(_printStateLabel);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("finished:");
    _builder.newLine();
    {
      if (this.enableTrace) {
        _builder.append("\t");
        CharSequence _printCloseFiles = this.printCloseFiles();
        _builder.append(_printCloseFiles, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        _builder.append("\t");
        _builder.append("read_end_");
        String _name_2 = port.getName();
        _builder.append(_name_2, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative = this.getNotNative(this.actor.getOutputs());
      for(final Port port_1 : _notNative) {
        _builder.append("\t");
        _builder.append("write_end_");
        String _name_3 = port_1.getName();
        _builder.append(_name_3, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      if ((IterableExtensions.isNullOrEmpty(this.actor.getInputs()) && IterableExtensions.isNullOrEmpty(this.actor.getOutputs()))) {
        _builder.append("\t");
        _builder.append("// compiler needs to have something after the \'finished\' label");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("i = i;");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printStateLabel(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("l_");
    String _name = state.getName();
    _builder.append(_name);
    _builder.append(":");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append(this.entityName, "\t");
        _builder.append("_outside_FSM_scheduler(si);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("i += si->num_firings;");
        _builder.newLine();
      }
    }
    {
      boolean _isEmpty_1 = state.getOutgoing().isEmpty();
      if (_isEmpty_1) {
        _builder.append("\t");
        _builder.append("printf(\"Stuck in state \\\"");
        String _name_1 = state.getName();
        _builder.append(_name_1, "\t");
        _builder.append("\\\" in ");
        _builder.append(this.entityName, "\t");
        _builder.append("\\n\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("exit(1);");
        _builder.newLine();
      } else {
        _builder.append("\t");
        CharSequence _printStateTransitions = this.printStateTransitions(state);
        _builder.append(_printStateTransitions, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence printAlignmentConditions(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int isAligned = 1;");
    _builder.newLine();
    {
      EList<Port> _ports = action.getInputPattern().getPorts();
      for(final Port port : _ports) {
        {
          if ((port.hasAttribute(((action.getName() + "_") + OrccAttributes.ALIGNABLE)) && (!port.hasAttribute(OrccAttributes.ALIGNED_ALWAYS)))) {
            _builder.append("\t");
            _builder.append("isAligned &= ((index_");
            String _name = port.getName();
            _builder.append(_name, "\t");
            _builder.append(" % SIZE_");
            String _name_1 = port.getName();
            _builder.append(_name_1, "\t");
            _builder.append(") < ((index_");
            String _name_2 = port.getName();
            _builder.append(_name_2, "\t");
            _builder.append(" + ");
            int _numTokens = action.getInputPattern().getNumTokens(port);
            _builder.append(_numTokens, "\t");
            _builder.append(") % SIZE_");
            String _name_3 = port.getName();
            _builder.append(_name_3, "\t");
            _builder.append("));");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      EList<Port> _ports_1 = action.getOutputPattern().getPorts();
      for(final Port port_1 : _ports_1) {
        {
          if ((port_1.hasAttribute(((action.getName() + "_") + OrccAttributes.ALIGNABLE)) && (!port_1.hasAttribute(OrccAttributes.ALIGNED_ALWAYS)))) {
            _builder.append("\t");
            _builder.append("isAligned &= ((index_");
            String _name_4 = port_1.getName();
            _builder.append(_name_4, "\t");
            _builder.append(" % SIZE_");
            String _name_5 = port_1.getName();
            _builder.append(_name_5, "\t");
            _builder.append(") < ((index_");
            String _name_6 = port_1.getName();
            _builder.append(_name_6, "\t");
            _builder.append(" + ");
            int _numTokens_1 = action.getOutputPattern().getNumTokens(port_1);
            _builder.append(_numTokens_1, "\t");
            _builder.append(") % SIZE_");
            String _name_7 = port_1.getName();
            _builder.append(_name_7, "\t");
            _builder.append("));");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  protected String printStateTransition(final State state, final Transition trans) {
    StringConcatenation _builder = new StringConcatenation();
    final boolean outputSchedulable = (trans.getAction().hasAttribute(OrccAttributes.OUTPUT_SCHEDULABLE) || this.actor.hasAttribute(OrccAttributes.OUTPUT_SCHEDULABLE));
    _builder.newLineIfNotEmpty();
    _builder.append("if (");
    CharSequence _checkInputPattern = this.checkInputPattern(trans.getAction().getInputPattern());
    _builder.append(_checkInputPattern);
    {
      if (outputSchedulable) {
        CharSequence _checkOutputPattern = this.checkOutputPattern(trans.getAction().getOutputPattern());
        _builder.append(_checkOutputPattern);
      }
    }
    String _name = trans.getAction().getScheduler().getName();
    _builder.append(_name);
    _builder.append("()) {");
    _builder.newLineIfNotEmpty();
    {
      if (((!trans.getAction().getOutputPattern().isEmpty()) && (!outputSchedulable))) {
        _builder.append("\t");
        CharSequence _printOutputPattern = this.printOutputPattern(trans.getAction().getOutputPattern());
        _builder.append(_printOutputPattern, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("_FSM_state = my_state_");
        String _name_1 = state.getName();
        _builder.append(_name_1, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("si->num_firings = i;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("si->reason = full;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("goto finished;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    String _name_2 = trans.getAction().getBody().getName();
    String _plus = ("actionState_" + _name_2);
    CharSequence _genWeightsSchedulerTock = this.genWeightsSchedulerTock(_plus);
    _builder.append(_genWeightsSchedulerTock, "\t");
    _builder.newLineIfNotEmpty();
    {
      boolean _hasAttribute = trans.getAction().hasAttribute(OrccAttributes.ALIGNED_ALWAYS);
      if (_hasAttribute) {
        _builder.append("\t");
        String _name_3 = trans.getAction().getBody().getName();
        _builder.append(_name_3, "\t");
        _builder.append("_aligned();");
        _builder.newLineIfNotEmpty();
      } else {
        boolean _hasAttribute_1 = trans.getAction().hasAttribute(OrccAttributes.ALIGNABLE);
        if (_hasAttribute_1) {
          _builder.append("\t");
          CharSequence _printAlignmentConditions = this.printAlignmentConditions(trans.getAction());
          _builder.append(_printAlignmentConditions, "\t");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("if (isAligned) {");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t");
          String _name_4 = trans.getAction().getBody().getName();
          _builder.append(_name_4, "\t\t\t");
          _builder.append("_aligned();");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("} else {");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t");
          String _name_5 = trans.getAction().getBody().getName();
          _builder.append(_name_5, "\t\t\t");
          _builder.append("();");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
        } else {
          _builder.append("\t");
          String _name_6 = trans.getAction().getBody().getName();
          _builder.append(_name_6, "\t");
          _builder.append("();");
          _builder.newLineIfNotEmpty();
        }
      }
    }
    _builder.append("\t");
    CharSequence _genWeightsSchedulerTick = this.genWeightsSchedulerTick("_currSelectedAction");
    _builder.append(_genWeightsSchedulerTick, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("i++;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("goto l_");
    String _name_7 = trans.getTarget().getName();
    _builder.append(_name_7, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    final String output = _builder.toString();
    return output;
  }
  
  protected CharSequence printStateTransitions(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<Edge, Transition> _function = new Function1<Edge, Transition>() {
        @Override
        public Transition apply(final Edge it) {
          return ((Transition) it);
        }
      };
      List<Transition> _map = ListExtensions.<Edge, Transition>map(state.getOutgoing(), _function);
      boolean _hasElements = false;
      for(final Transition trans : _map) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" else ", "");
        }
        String _printStateTransition = this.printStateTransition(state, trans);
        _builder.append(_printStateTransition);
        _builder.newLineIfNotEmpty();
        _builder.append("}");
      }
    }
    _builder.append(" else {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _printTransitionPattern = this.printTransitionPattern(this.transitionPattern.get(state));
    _builder.append(_printTransitionPattern, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("_FSM_state = my_state_");
    String _name = state.getName();
    _builder.append(_name, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printTransitionPattern(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.newSchedul) {
        {
          EList<Port> _ports = pattern.getPorts();
          for(final Port port : _ports) {
            CharSequence _printTransitionPatternPort = this.printTransitionPatternPort(port, pattern);
            _builder.append(_printTransitionPatternPort);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("si->num_firings = i;");
    _builder.newLine();
    _builder.append("si->reason = starved;");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printTransitionPatternPort(final Port port, final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (numTokens_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" - index_");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append(" < ");
    int _numTokens = pattern.getNumTokens(port);
    _builder.append(_numTokens);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("if( ! ");
    _builder.append(this.entityName, "\t");
    _builder.append(".sched->round_robin || i > 0) {");
    _builder.newLineIfNotEmpty();
    {
      boolean _containsKey = this.incomingPortMap.containsKey(port);
      if (_containsKey) {
        _builder.append("\t\t");
        _builder.append("sched_add_schedulable(");
        _builder.append(this.entityName, "\t\t");
        _builder.append(".sched, &");
        String _label = this.incomingPortMap.get(port).getSource().getLabel();
        _builder.append(_label, "\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printCallTokensFunctions() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        _builder.append("read_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Iterable<? extends Port> _notNative = this.getNotNative(this.actor.getOutputs());
      for(final Port port_1 : _notNative) {
        _builder.append("write_");
        String _name_1 = port_1.getName();
        _builder.append(_name_1);
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence printInitialize() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Action> _initializes = this.actor.getInitializes();
      for(final Action init : _initializes) {
        CharSequence _print = this.print(init);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    CharSequence _inline = this.getInline();
    _builder.append(_inline);
    _builder.append("void ");
    _builder.append(this.entityName);
    _builder.append("_initialize(schedinfo_t *si) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("int i = 0;");
    _builder.newLine();
    {
      if ((this.actor.hasAttribute(OrccAttributes.PAPIFY_ATTRIBUTE) && this.papify)) {
        _builder.append("\t");
        _builder.append("/* Papify initialization */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("mkdir(\"papi-output\", 0777);");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("Papi_actions_");
        String _name = this.actor.getName();
        _builder.append(_name, "\t");
        _builder.append(" = malloc(sizeof(papi_action_s) * ");
        int _size = IterableExtensions.size(this.papifyActions);
        _builder.append(_size, "\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("papi_output_");
        String _name_1 = this.actor.getName();
        _builder.append(_name_1, "\t");
        _builder.append(" = fopen(\"papi-output/papi_output_");
        String _name_2 = this.actor.getName();
        _builder.append(_name_2, "\t");
        _builder.append(".csv\",\"w\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("papi_");
        String _name_3 = this.actor.getName();
        _builder.append(_name_3, "\t");
        _builder.append("_eventCodeSetSize = ");
        int _size_1 = this.actor.getAttribute(OrccAttributes.PAPIFY_ATTRIBUTE).getAttributes().size();
        _builder.append(_size_1, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("papi_");
        String _name_4 = this.actor.getName();
        _builder.append(_name_4, "\t");
        _builder.append("_eventCodeSet = malloc(sizeof(unsigned long) * papi_");
        String _name_5 = this.actor.getName();
        _builder.append(_name_5, "\t");
        _builder.append("_eventCodeSetSize);");
        _builder.newLineIfNotEmpty();
        {
          int _size_2 = IterableExtensions.size(this.papiEvents);
          int _minus = (_size_2 - 1);
          IntegerRange _upTo = new IntegerRange(0, _minus);
          for(final Integer i : _upTo) {
            _builder.append("\t");
            _builder.append("papi_");
            String _name_6 = this.actor.getName();
            _builder.append(_name_6, "\t");
            _builder.append("_eventCodeSet[");
            _builder.append(i, "\t");
            _builder.append("] = ");
            String _get = ((String[])Conversions.unwrapArray(this.papiEvents, String.class))[(i).intValue()];
            _builder.append(_get, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("papi_");
        String _name_7 = this.actor.getName();
        _builder.append(_name_7, "\t");
        _builder.append("_eventset = malloc(sizeof(int) * papi_");
        String _name_8 = this.actor.getName();
        _builder.append(_name_8, "\t");
        _builder.append("_eventCodeSetSize);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("papi_");
        String _name_9 = this.actor.getName();
        _builder.append(_name_9, "\t");
        _builder.append("_eventset = PAPI_NULL;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        {
          for(final Action action : this.papifyActions) {
            _builder.append("\t");
            final CharSequence papiStructI = this.papifyStruct(action);
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(papiStructI, "\t");
            _builder.append(".action_id = malloc(strlen(\"");
            String _name_10 = action.getName();
            _builder.append(_name_10, "\t");
            _builder.append("\")+1);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(papiStructI, "\t");
            _builder.append(".action_id = \"");
            String _name_11 = action.getName();
            _builder.append(_name_11, "\t");
            _builder.append("\";");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append(papiStructI, "\t");
            _builder.append(".counterValues = malloc(sizeof(unsigned long) * papi_");
            String _name_12 = this.actor.getName();
            _builder.append(_name_12, "\t");
            _builder.append("_eventCodeSetSize);");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("\t");
        _builder.append("fprintf(papi_output_");
        String _name_13 = this.actor.getName();
        _builder.append(_name_13, "\t");
        _builder.append(",\"Actor,Action,tini,tend,");
        String _join = IterableExtensions.join(this.papiEvents, ",");
        _builder.append(_join, "\t");
        _builder.append("\\n\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("fclose(papi_output_");
        String _name_14 = this.actor.getName();
        _builder.append(_name_14, "\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"Creating eventlist for actor ");
        String _name_15 = this.actor.getName();
        _builder.append(_name_15, "\t");
        _builder.append("\\n\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("event_create_eventList(&(papi_");
        String _name_16 = this.actor.getName();
        _builder.append(_name_16, "\t");
        _builder.append("_eventset), papi_");
        String _name_17 = this.actor.getName();
        _builder.append(_name_17, "\t");
        _builder.append("_eventCodeSetSize, papi_");
        String _name_18 = this.actor.getName();
        _builder.append(_name_18, "\t");
        _builder.append("_eventCodeSet, -1);");
        _builder.newLineIfNotEmpty();
        {
          if (this.papifyMultiplex) {
            _builder.append("\t");
            _builder.append("eventList_set_multiplex(&(papi_");
            String _name_19 = this.actor.getName();
            _builder.append(_name_19, "\t");
            _builder.append("_eventset));");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("/* End of Papify initialization */");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.append("\t");
    CharSequence _additionalInitializes = this.additionalInitializes();
    _builder.append(_additionalInitializes, "\t");
    _builder.newLineIfNotEmpty();
    {
      Iterable<? extends Port> _notNative = this.getNotNative(this.actor.getOutputs());
      for(final Port port : _notNative) {
        _builder.append("\t");
        _builder.append("write_");
        String _name_20 = port.getName();
        _builder.append(_name_20, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        _builder.append("\t");
        _builder.append("/* Set initial state to current FSM state */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("_FSM_state = my_state_");
        String _name_21 = this.actor.getFsm().getInitialState().getName();
        _builder.append(_name_21, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Action> _initializes_1 = this.actor.getInitializes();
      for(final Action initialize : _initializes_1) {
        _builder.append("\t");
        _builder.append("if(");
        String _name_22 = initialize.getScheduler().getName();
        _builder.append(_name_22, "\t");
        _builder.append("()) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        String _name_23 = initialize.getName();
        _builder.append(_name_23, "\t\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("finished:");
    _builder.newLine();
    {
      Iterable<? extends Port> _notNative_1 = this.getNotNative(this.actor.getOutputs());
      for(final Port port_1 : _notNative_1) {
        _builder.append("\t");
        _builder.append("write_end_");
        String _name_24 = port_1.getName();
        _builder.append(_name_24, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected void checkConnectivy() {
    final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
      @Override
      public Boolean apply(final Port it) {
        Connection _get = InstancePrinter.this.incomingPortMap.get(it);
        return Boolean.valueOf((_get == null));
      }
    };
    Iterable<Port> _filter = IterableExtensions.<Port>filter(this.actor.getInputs(), _function);
    for (final Port port : _filter) {
      String _name = port.getName();
      String _plus = ((("[" + this.entityName) + "] Input port ") + _name);
      String _plus_1 = (_plus + " not connected.");
      OrccLogger.noticeln(_plus_1);
    }
    final Function1<Port, Boolean> _function_1 = new Function1<Port, Boolean>() {
      @Override
      public Boolean apply(final Port it) {
        return Boolean.valueOf(IterableExtensions.isNullOrEmpty(InstancePrinter.this.outgoingPortMap.get(it)));
      }
    };
    Iterable<Port> _filter_1 = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_1);
    for (final Port port_1 : _filter_1) {
      String _name_1 = port_1.getName();
      String _plus_2 = ((("[" + this.entityName) + "] Output port ") + _name_1);
      String _plus_3 = (_plus_2 + " not connected.");
      OrccLogger.noticeln(_plus_3);
    }
  }
  
  protected CharSequence printActionSchedulingLoop(final List<Action> actions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("while (1) {");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _printActionsScheduling = this.printActionsScheduling(actions);
    _builder.append(_printActionsScheduling, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected String printActionScheduling(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    final boolean outputSchedulable = (action.hasAttribute(OrccAttributes.OUTPUT_SCHEDULABLE) || this.actor.hasAttribute(OrccAttributes.OUTPUT_SCHEDULABLE));
    _builder.newLineIfNotEmpty();
    _builder.append("if (");
    CharSequence _checkInputPattern = this.checkInputPattern(action.getInputPattern());
    _builder.append(_checkInputPattern);
    {
      if (outputSchedulable) {
        CharSequence _checkOutputPattern = this.checkOutputPattern(action.getOutputPattern());
        _builder.append(_checkOutputPattern);
      }
    }
    String _name = action.getScheduler().getName();
    _builder.append(_name);
    _builder.append("()) {");
    _builder.newLineIfNotEmpty();
    {
      if (((!action.getOutputPattern().isEmpty()) && (!outputSchedulable))) {
        _builder.append("\t");
        CharSequence _printOutputPattern = this.printOutputPattern(action.getOutputPattern());
        _builder.append(_printOutputPattern, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("si->num_firings = i;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("si->reason = full;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("goto finished;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    String _name_1 = action.getBody().getName();
    String _plus = ("actionState_" + _name_1);
    CharSequence _genWeightsSchedulerTock = this.genWeightsSchedulerTock(_plus);
    _builder.append(_genWeightsSchedulerTock, "\t");
    _builder.newLineIfNotEmpty();
    {
      boolean _hasAttribute = action.hasAttribute(OrccAttributes.ALIGNED_ALWAYS);
      if (_hasAttribute) {
        _builder.append("\t");
        String _name_2 = action.getBody().getName();
        _builder.append(_name_2, "\t");
        _builder.append("_aligned();");
        _builder.newLineIfNotEmpty();
      } else {
        boolean _hasAttribute_1 = action.hasAttribute(OrccAttributes.ALIGNABLE);
        if (_hasAttribute_1) {
          _builder.append("\t");
          CharSequence _printAlignmentConditions = this.printAlignmentConditions(action);
          _builder.append(_printAlignmentConditions, "\t");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("if (isAligned) {");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t");
          String _name_3 = action.getBody().getName();
          _builder.append(_name_3, "\t\t\t");
          _builder.append("_aligned();");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("} else {");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t\t");
          String _name_4 = action.getBody().getName();
          _builder.append(_name_4, "\t\t\t");
          _builder.append("();");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
        } else {
          _builder.append("\t");
          String _name_5 = action.getBody().getName();
          _builder.append(_name_5, "\t");
          _builder.append("();");
          _builder.newLineIfNotEmpty();
        }
      }
    }
    _builder.append("\t");
    CharSequence _genWeightsSchedulerTick = this.genWeightsSchedulerTick("_currSelectedAction");
    _builder.append(_genWeightsSchedulerTick, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("i++;");
    _builder.newLine();
    final String output = _builder.toString();
    return output;
  }
  
  protected CharSequence printActionsScheduling(final Iterable<Action> actions) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final Action action : actions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" else ", "");
        }
        String _printActionScheduling = this.printActionScheduling(action);
        _builder.append(_printActionScheduling);
        _builder.newLineIfNotEmpty();
        _builder.append("}");
      }
    }
    _builder.append(" else {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _printTransitionPattern = this.printTransitionPattern(this.inputPattern);
    _builder.append(_printTransitionPattern, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printOutputPattern(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("int stop = 0;");
    _builder.newLine();
    {
      EList<Port> _ports = pattern.getPorts();
      for(final Port port : _ports) {
        int i = (-1);
        _builder.newLineIfNotEmpty();
        {
          List<Connection> _get = this.outgoingPortMap.get(port);
          for(final Connection connection : _get) {
            _builder.append("if (");
            int _numTokens = pattern.getNumTokens(port);
            _builder.append(_numTokens);
            _builder.append(" > SIZE_");
            String _name = port.getName();
            _builder.append(_name);
            _builder.append(" - index_");
            String _name_1 = port.getName();
            _builder.append(_name_1);
            _builder.append(" + ");
            CharSequence _fullName = this.fullName(port);
            _builder.append(_fullName);
            _builder.append("->read_inds[");
            _builder.append(i = (i + 1));
            _builder.append("]) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("stop = 1;");
            _builder.newLine();
            {
              if (this.newSchedul) {
                _builder.append("\t");
                _builder.append("if( ! ");
                _builder.append(this.entityName, "\t");
                _builder.append(".sched->round_robin || i > 0) {");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("\t");
                _builder.append("sched_add_schedulable(");
                _builder.append(this.entityName, "\t\t");
                _builder.append(".sched, &");
                String _label = connection.getTarget().getLabel();
                _builder.append(_label, "\t\t");
                _builder.append(");");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                _builder.append("}");
                _builder.newLine();
              }
            }
            _builder.append("}");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("if (stop != 0) {");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence checkInputPattern(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _ports = pattern.getPorts();
      for(final Port port : _ports) {
        _builder.append("numTokens_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append(" - index_");
        String _name_1 = port.getName();
        _builder.append(_name_1);
        _builder.append(" >= ");
        int _numTokens = pattern.getNumTokens(port);
        _builder.append(_numTokens);
        _builder.append(" && ");
      }
    }
    return _builder;
  }
  
  protected CharSequence checkOutputPattern(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _ports = pattern.getPorts();
      for(final Port port : _ports) {
        int i = (-1);
        {
          List<Connection> _get = this.outgoingPortMap.get(port);
          for(final Connection connection : _get) {
            _builder.append("!(");
            int _numTokens = pattern.getNumTokens(port);
            _builder.append(_numTokens);
            _builder.append(" > SIZE_");
            String _name = port.getName();
            _builder.append(_name);
            _builder.append(" - index_");
            String _name_1 = port.getName();
            _builder.append(_name_1);
            _builder.append(" + ");
            CharSequence _fullName = this.fullName(port);
            _builder.append(_fullName);
            _builder.append("->read_inds[");
            _builder.append(i = (i + 1));
            _builder.append("]) && ");
          }
        }
      }
    }
    return _builder;
  }
  
  protected CharSequence writeTokensFunctions(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static void write_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("index_");
    String _name_1 = port.getName();
    _builder.append(_name_1, "\t");
    _builder.append(" = ");
    CharSequence _fullName = this.fullName(port);
    _builder.append(_fullName, "\t");
    _builder.append("->write_ind;");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static void write_end_");
    String _name_2 = port.getName();
    _builder.append(_name_2);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _fullName_1 = this.fullName(port);
    _builder.append(_fullName_1, "\t");
    _builder.append("->write_ind = index_");
    String _name_3 = port.getName();
    _builder.append(_name_3, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence readTokensFunctions(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static void read_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    {
      boolean _containsKey = this.incomingPortMap.containsKey(port);
      if (_containsKey) {
        _builder.append("\t");
        _builder.append("index_");
        String _name_1 = port.getName();
        _builder.append(_name_1, "\t");
        _builder.append(" = ");
        CharSequence _fullName = this.fullName(port);
        _builder.append(_fullName, "\t");
        _builder.append("->read_inds[0];");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("numTokens_");
        String _name_2 = port.getName();
        _builder.append(_name_2, "\t");
        _builder.append(" = index_");
        String _name_3 = port.getName();
        _builder.append(_name_3, "\t");
        _builder.append(" + fifo_");
        CharSequence _doSwitch = this.doSwitch(port.getType());
        _builder.append(_doSwitch, "\t");
        _builder.append("_get_num_tokens(");
        CharSequence _fullName_1 = this.fullName(port);
        _builder.append(_fullName_1, "\t");
        _builder.append(", 0);");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("/* Input port ");
        CharSequence _fullName_2 = this.fullName(port);
        _builder.append(_fullName_2, "\t");
        _builder.append(" not connected */");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("index_");
        String _name_4 = port.getName();
        _builder.append(_name_4, "\t");
        _builder.append(" = 0;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("numTokens_");
        String _name_5 = port.getName();
        _builder.append(_name_5, "\t");
        _builder.append(" = 0;");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static void read_end_");
    String _name_6 = port.getName();
    _builder.append(_name_6);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    {
      boolean _containsKey_1 = this.incomingPortMap.containsKey(port);
      if (_containsKey_1) {
        _builder.append("\t");
        CharSequence _fullName_3 = this.fullName(port);
        _builder.append(_fullName_3, "\t");
        _builder.append("->read_inds[0] = index_");
        String _name_7 = port.getName();
        _builder.append(_name_7, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("/* Input port ");
        CharSequence _fullName_4 = this.fullName(port);
        _builder.append(_fullName_4, "\t");
        _builder.append(" not connected */");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence printCore(final Action action, final boolean isAligned) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static ");
    {
      if (this.inlineActions) {
        CharSequence _inline = this.getInline();
        _builder.append(_inline);
      } else {
        CharSequence _noInline = this.getNoInline();
        _builder.append(_noInline);
      }
    }
    _builder.append("void ");
    String _name = action.getBody().getName();
    _builder.append(_name);
    {
      if (isAligned) {
        _builder.append("_aligned");
      }
    }
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _profileStart = this.profileStart(action);
    _builder.append(_profileStart, "\t");
    _builder.newLineIfNotEmpty();
    {
      if ((action.hasAttribute(OrccAttributes.PAPIFY_ATTRIBUTE) && this.papify)) {
        _builder.append("\t");
        _builder.append("papi_");
        String _name_1 = this.actor.getName();
        _builder.append(_name_1, "\t");
        _builder.append("_start_usec = PAPI_get_real_usec();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("event_start(&(papi_");
        String _name_2 = this.actor.getName();
        _builder.append(_name_2, "\t");
        _builder.append("_eventset), -1);");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Var> _locals = action.getBody().getLocals();
      for(final Var variable : _locals) {
        _builder.append("\t");
        CharSequence _declare = this.declare(variable);
        _builder.append(_declare, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("string temp1,temp2;");
    _builder.newLine();
    {
      if ((this.debugActor || this.debugAction)) {
        _builder.append("\t");
        _builder.append("printf(\"-- ");
        _builder.append(this.entityName, "\t");
        _builder.append(": ");
        String _name_3 = action.getName();
        _builder.append(_name_3, "\t");
        {
          if (isAligned) {
            _builder.append(" (aligned)");
          }
        }
        _builder.append("\\n\");");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      if (this.debugAction) {
        _builder.append("\t");
        CharSequence _debugTokens = this.debugTokens(action.getInputPattern());
        _builder.append(_debugTokens, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _writeTraces = this.writeTraces(action.getInputPattern());
    _builder.append(_writeTraces, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _genWeightsActionsTick = this.genWeightsActionsTick(action);
    _builder.append(_genWeightsActionsTick, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _beforeActionBody = this.beforeActionBody();
    _builder.append(_beforeActionBody, "\t");
    _builder.newLineIfNotEmpty();
    {
      EList<Block> _blocks = action.getBody().getBlocks();
      for(final Block block : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch = this.doSwitch(block);
        _builder.append(_doSwitch, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _afterActionBody = this.afterActionBody();
    _builder.append(_afterActionBody, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _genWeightsActionsTock = this.genWeightsActionsTock(action);
    _builder.append(_genWeightsActionsTock, "\t");
    _builder.newLineIfNotEmpty();
    {
      if (this.debugAction) {
        _builder.append("\t");
        CharSequence _debugTokens_1 = this.debugTokens(action.getOutputPattern());
        _builder.append(_debugTokens_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _writeTraces_1 = this.writeTraces(action.getOutputPattern());
    _builder.append(_writeTraces_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("// Update ports indexes");
    _builder.newLine();
    {
      EList<Port> _ports = action.getInputPattern().getPorts();
      for(final Port port : _ports) {
        _builder.append("\t");
        _builder.append("index_");
        String _name_4 = port.getName();
        _builder.append(_name_4, "\t");
        _builder.append(" += ");
        int _numTokens = action.getInputPattern().getNumTokens(port);
        _builder.append(_numTokens, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        {
          int _numTokens_1 = action.getInputPattern().getNumTokens(port);
          boolean _greaterEqualsThan = (_numTokens_1 >= BackendsConstants.MIN_REPEAT_RWEND);
          if (_greaterEqualsThan) {
            _builder.append("\t");
            _builder.append("read_end_");
            String _name_5 = port.getName();
            _builder.append(_name_5, "\t");
            _builder.append("();");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      EList<Port> _ports_1 = action.getOutputPattern().getPorts();
      for(final Port port_1 : _ports_1) {
        _builder.append("\t");
        _builder.append("index_");
        String _name_6 = port_1.getName();
        _builder.append(_name_6, "\t");
        _builder.append(" += ");
        int _numTokens_2 = action.getOutputPattern().getNumTokens(port_1);
        _builder.append(_numTokens_2, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        {
          int _numTokens_3 = action.getOutputPattern().getNumTokens(port_1);
          boolean _greaterEqualsThan_1 = (_numTokens_3 >= BackendsConstants.MIN_REPEAT_RWEND);
          if (_greaterEqualsThan_1) {
            _builder.append("\t");
            _builder.append("write_end_");
            String _name_7 = port_1.getName();
            _builder.append(_name_7, "\t");
            _builder.append("();");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      if ((action.hasAttribute(OrccAttributes.PAPIFY_ATTRIBUTE) && this.papify)) {
        _builder.append("\t");
        final CharSequence papiStructI = this.papifyStruct(action);
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("event_stop(&(papi_");
        String _name_8 = this.actor.getName();
        _builder.append(_name_8, "\t");
        _builder.append("_eventset), papi_");
        String _name_9 = this.actor.getName();
        _builder.append(_name_9, "\t");
        _builder.append("_eventCodeSetSize, ");
        _builder.append(papiStructI, "\t");
        _builder.append(".counterValues, -1);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("papi_");
        String _name_10 = this.actor.getName();
        _builder.append(_name_10, "\t");
        _builder.append("_end_usec = PAPI_get_real_usec();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("papi_output_");
        String _name_11 = this.actor.getName();
        _builder.append(_name_11, "\t");
        _builder.append(" = fopen(\"papi-output/papi_output_");
        String _name_12 = this.actor.getName();
        _builder.append(_name_12, "\t");
        _builder.append(".csv\",\"a+\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("fprintf(papi_output_");
        String _name_13 = this.actor.getName();
        _builder.append(_name_13, "\t");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("\"%s,%s,%llu,%llu,");
        int _size = IterableExtensions.size(this.papiEvents);
        int _minus = (_size - 1);
        final Function1<Integer, CharSequence> _function = new Function1<Integer, CharSequence>() {
          @Override
          public CharSequence apply(final Integer it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("%lu");
            return _builder.toString();
          }
        };
        String _join = IterableExtensions.<Integer>join(new IntegerRange(0, _minus), ",", _function);
        _builder.append(_join, "\t\t");
        _builder.append("\\n\",");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("\"");
        String _name_14 = this.actor.getName();
        _builder.append(_name_14, "\t\t");
        _builder.append("\", ");
        _builder.append(papiStructI, "\t\t");
        _builder.append(".action_id, papi_");
        String _name_15 = this.actor.getName();
        _builder.append(_name_15, "\t\t");
        _builder.append("_start_usec, papi_");
        String _name_16 = this.actor.getName();
        _builder.append(_name_16, "\t\t");
        _builder.append("_end_usec,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        int _size_1 = IterableExtensions.size(this.papiEvents);
        int _minus_1 = (_size_1 - 1);
        final Function1<Integer, CharSequence> _function_1 = new Function1<Integer, CharSequence>() {
          @Override
          public CharSequence apply(final Integer it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(papiStructI);
            _builder.append(".counterValues[");
            _builder.append(it);
            _builder.append("]");
            return _builder.toString();
          }
        };
        String _join_1 = IterableExtensions.<Integer>join(new IntegerRange(0, _minus_1), ", ", _function_1);
        _builder.append(_join_1, "\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("fclose(papi_output_");
        String _name_17 = this.actor.getName();
        _builder.append(_name_17, "\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _profileEnd = this.profileEnd(action);
    _builder.append(_profileEnd, "\t");
    _builder.newLineIfNotEmpty();
    {
      if ((this.genWeightsExit && (this.genWeightsExitAction == action))) {
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printCallsToGenWeightsStats = this.printCallsToGenWeightsStats();
        _builder.append(_printCallsToGenWeightsStats, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence afterActionBody() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence beforeActionBody() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence additionalInitializes() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence additionalDeclarations() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence printAdditionalIncludes() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  private CharSequence writeTraces(final Pattern pattern) {
    CharSequence _xblockexpression = null;
    {
      if ((!this.enableTrace)) {
        StringConcatenation _builder = new StringConcatenation();
        return _builder.toString();
      }
      StringConcatenation _builder_1 = new StringConcatenation();
      {
        EList<Port> _ports = pattern.getPorts();
        for(final Port port : _ports) {
          _builder_1.append("{");
          _builder_1.newLine();
          _builder_1.append("\t");
          _builder_1.append("// Write traces");
          _builder_1.newLine();
          _builder_1.append("\t");
          _builder_1.append("int i;");
          _builder_1.newLine();
          _builder_1.append("\t");
          _builder_1.append("for (i = 0; i < ");
          int _numTokens = pattern.getNumTokens(port);
          _builder_1.append(_numTokens, "\t");
          _builder_1.append("; i++) {");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t");
          _builder_1.append("fprintf(file_");
          String _name = port.getName();
          _builder_1.append(_name, "\t\t");
          _builder_1.append(", \"%");
          String _printfFormat = this.printfFormat(port.getType());
          _builder_1.append(_printfFormat, "\t\t");
          _builder_1.append("\\n\", tokens_");
          String _name_1 = port.getName();
          _builder_1.append(_name_1, "\t\t");
          _builder_1.append("[(index_");
          String _name_2 = port.getName();
          _builder_1.append(_name_2, "\t\t");
          _builder_1.append(" + i) % SIZE_");
          String _name_3 = port.getName();
          _builder_1.append(_name_3, "\t\t");
          _builder_1.append("]);");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          _builder_1.append("}");
          _builder_1.newLine();
          _builder_1.append("}");
          _builder_1.newLine();
        }
      }
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }
  
  private CharSequence debugTokens(final Pattern pattern) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _ports = pattern.getPorts();
      for(final Port port : _ports) {
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("int i;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"--- ");
        String _name = port.getName();
        _builder.append(_name, "\t");
        _builder.append(": \");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("for (i = 0; i < ");
        int _numTokens = pattern.getNumTokens(port);
        _builder.append(_numTokens, "\t");
        _builder.append("; i++) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("printf(\"%");
        String _printfFormat = this.printfFormat(port.getType());
        _builder.append(_printfFormat, "\t\t");
        _builder.append(" \", tokens_");
        String _name_1 = port.getName();
        _builder.append(_name_1, "\t\t");
        _builder.append("[(index_");
        String _name_2 = port.getName();
        _builder.append(_name_2, "\t\t");
        _builder.append(" + i) % SIZE_");
        String _name_3 = port.getName();
        _builder.append(_name_3, "\t\t");
        _builder.append("]);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"\\n\");");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence genWeightsActionsTick(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((this.genWeights && (!this.actor.getInitializes().contains(action)))) {
        _builder.newLine();
        _builder.append("rdtsc_warmup(&actionsCyclesHigh1, &actionsCyclesLow1, &actionsCyclesHigh2, &actionsCyclesLow2);");
        _builder.newLine();
        _builder.append("rdtsc_tick(&actionsCyclesHigh1, &actionsCyclesLow1);");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence genWeightsActionsTock(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((this.genWeights && (!this.actor.getInitializes().contains(action)))) {
        _builder.newLine();
        _builder.append("rdtsc_tock(&actionsCyclesHigh2, &actionsCyclesLow2);");
        _builder.newLine();
        _builder.append("saveNewFiringWeight(profDataAction_");
        String _name = this.actor.getName();
        _builder.append(_name);
        _builder.append("_");
        String _name_1 = action.getName();
        _builder.append(_name_1);
        _builder.append(", rdtsc_getTicksCount(actionsCyclesHigh1, actionsCyclesLow1, actionsCyclesHigh2, actionsCyclesLow2));");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence genWeightsSchedulerTick(final String actionStateStr) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.genWeights) {
        _builder.newLine();
        _builder.append("_lastSelectedAction = ");
        _builder.append(actionStateStr);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("rdtsc_warmup(&schedulerCyclesHigh1, &schedulerCyclesLow1, &schedulerCyclesHigh2, &schedulerCyclesLow2);");
        _builder.newLine();
        _builder.append("rdtsc_tick(&schedulerCyclesHigh1, &schedulerCyclesLow1);");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence genWeightsSchedulerTock(final String actionStateStr) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.genWeights) {
        _builder.newLine();
        _builder.append("rdtsc_tock(&schedulerCyclesHigh2, &schedulerCyclesLow2);");
        _builder.newLine();
        _builder.append("_currSelectedAction = ");
        _builder.append(actionStateStr);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("saveNewShedulerWeight(&profDataScheduler_");
        String _name = this.actor.getName();
        _builder.append(_name);
        _builder.append("->_map[_lastSelectedAction][_currSelectedAction], ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t\t\t  ");
        _builder.append("actionNames[_lastSelectedAction], actionNames[_currSelectedAction], ");
        _builder.newLine();
        _builder.append("\t\t\t\t\t  ");
        _builder.append("rdtsc_getTicksCount(schedulerCyclesHigh1, schedulerCyclesLow1, schedulerCyclesHigh2, schedulerCyclesLow2));");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence printGenWeightsInitSchedulerProfilingVars() {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.genWeights) {
        _builder.append("if(!ifInitSchedulerProfilingVars) {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("initializeSchedulerProfilingVars(profDataScheduler_");
        String _name = this.actor.getName();
        _builder.append(_name, "\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("ifInitSchedulerProfilingVars = 1;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
        CharSequence _genWeightsSchedulerTick = this.genWeightsSchedulerTick("OUTSIDE");
        _builder.append(_genWeightsSchedulerTick);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence printGenWeightsDeclartions() {
    CharSequence _xblockexpression = null;
    {
      Network network = null;
      if (this.genWeightsExit) {
        EObject _eContainer = this.actor.eContainer();
        if ((_eContainer instanceof Network)) {
          EObject _eContainer_1 = this.actor.eContainer();
          network = ((Network) _eContainer_1);
        }
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      {
        if ((this.genWeightsExit && (network != null))) {
          _builder.append("// Data structures for profling actions.");
          _builder.newLine();
          {
            EList<Vertex> _children = network.getChildren();
            for(final Vertex child : _children) {
              CharSequence _printGenWeightsActionProfilingVars = this.printGenWeightsActionProfilingVars(child.<Actor>getAdapter(Actor.class));
              _builder.append(_printGenWeightsActionProfilingVars);
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.newLine();
          _builder.append("// Data structures for profling scheduler.");
          _builder.newLine();
          {
            EList<Vertex> _children_1 = network.getChildren();
            for(final Vertex child_1 : _children_1) {
              CharSequence _printGenWeightsSchedulerProfilingPart1 = this.printGenWeightsSchedulerProfilingPart1(child_1.<Actor>getAdapter(Actor.class));
              _builder.append(_printGenWeightsSchedulerProfilingPart1);
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.newLine();
          CharSequence _printGenWeightsSchedulerProfilingPart2 = this.printGenWeightsSchedulerProfilingPart2(this.actor);
          _builder.append(_printGenWeightsSchedulerProfilingPart2);
          _builder.newLineIfNotEmpty();
        } else {
          _builder.append("// Data structures for profling actions.");
          _builder.newLine();
          CharSequence _printGenWeightsActionProfilingVars_1 = this.printGenWeightsActionProfilingVars(this.actor);
          _builder.append(_printGenWeightsActionProfilingVars_1);
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("// Data structures for profling scheduler.");
          _builder.newLine();
          CharSequence _printGenWeightsSchedulerProfilingVars = this.printGenWeightsSchedulerProfilingVars(this.actor);
          _builder.append(_printGenWeightsSchedulerProfilingVars);
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      _builder.append("static unsigned int actionsCyclesHigh1, actionsCyclesLow1, actionsCyclesHigh2, actionsCyclesLow2;");
      _builder.newLine();
      _builder.append("static unsigned int schedulerCyclesHigh1, schedulerCyclesLow1, schedulerCyclesHigh2, schedulerCyclesLow2;");
      _builder.newLine();
      _builder.append("static int ifInitSchedulerProfilingVars = 0;");
      _builder.newLine();
      {
        if (this.genWeightsExit) {
          _builder.newLine();
          CharSequence _printCalcGenWeightsStatsFunctions = this.printCalcGenWeightsStatsFunctions();
          _builder.append(_printCalcGenWeightsStatsFunctions);
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printGenWeightsActionProfilingVars(final Actor actor) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Action> _actions = actor.getActions();
      for(final Action action : _actions) {
        _builder.append("extern rdtsc_data_t *profDataAction_");
        String _name = actor.getName();
        _builder.append(_name);
        _builder.append("_");
        String _name_1 = action.getName();
        _builder.append(_name_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence printGenWeightsSchedulerProfilingPart1(final Actor actor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("extern rdtsc_scheduler_map_t *profDataScheduler_");
    String _name = actor.getName();
    _builder.append(_name);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence printGenWeightsSchedulerProfilingPart2(final Actor actor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// ActionName as states");
    _builder.newLine();
    _builder.append("enum actionStates {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("OUTSIDE,");
    _builder.newLine();
    {
      EList<Action> _actions = actor.getActions();
      boolean _hasElements = false;
      for(final Action action : _actions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "\t");
        }
        _builder.append("\t");
        _builder.append("actionState_");
        String _name = action.getName();
        _builder.append(_name, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static char *actionNames[] = {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"\",");
    _builder.newLine();
    {
      EList<Action> _actions_1 = actor.getActions();
      boolean _hasElements_1 = false;
      for(final Action action_1 : _actions_1) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate(",", "\t");
        }
        _builder.append("\t");
        _builder.append("\"");
        String _name_1 = action_1.getName();
        _builder.append(_name_1, "\t");
        _builder.append("\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static enum actionStates _lastSelectedAction;");
    _builder.newLine();
    _builder.append("static enum actionStates _currSelectedAction;");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printGenWeightsSchedulerProfilingVars(final Actor actor) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printGenWeightsSchedulerProfilingPart1 = this.printGenWeightsSchedulerProfilingPart1(actor);
    _builder.append(_printGenWeightsSchedulerProfilingPart1);
    _builder.newLineIfNotEmpty();
    CharSequence _printGenWeightsSchedulerProfilingPart2 = this.printGenWeightsSchedulerProfilingPart2(actor);
    _builder.append(_printGenWeightsSchedulerProfilingPart2);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence printCallsToGenWeightsStats() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if(");
    _builder.append(this.genWeightsExitCond);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("calcGenWeightsActions();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("calcGenWeightsSchedulers();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("exit(1); // Exiting the program after stats calculations & reporting are finished.");
    _builder.newLine();
    _builder.append("} // ");
    _builder.append(this.genWeightsExitCond);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence printCalcGenWeightsStatsFunctions() {
    CharSequence _xblockexpression = null;
    {
      Network network = null;
      EObject _eContainer = this.actor.eContainer();
      if ((_eContainer instanceof Network)) {
        EObject _eContainer_1 = this.actor.eContainer();
        network = ((Network) _eContainer_1);
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      {
        if ((network != null)) {
          _builder.append("void calcGenWeightsActions() {");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("FILE *fpGenWeightsStats = NULL;");
          _builder.newLine();
          _builder.append("\t");
          {
            if (this.genWeightsDump) {
              _builder.append("FILE *fpGenWeightsFirings = NULL;");
            }
          }
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("char fnGenWeightsStats[FILENAME_MAX];");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("int useFilter = ");
          {
            if (this.genWeightsFilter) {
              _builder.append("1");
            } else {
              _builder.append("0");
            }
          }
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("\t");
          _builder.append("if(opt->input_file != NULL)");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("sprintf(fnGenWeightsStats, \"actions_weights_");
          String _simpleName = network.getSimpleName();
          _builder.append(_simpleName, "\t\t");
          _builder.append("_%s.exdf\", basename(opt->input_file));");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("else");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("sprintf(fnGenWeightsStats, \"actions_weights_");
          String _simpleName_1 = network.getSimpleName();
          _builder.append(_simpleName_1, "\t\t");
          _builder.append(".exdf\");");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("fpGenWeightsStats = fopen(fnGenWeightsStats, \"w\");");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("if(fpGenWeightsStats == NULL) {");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("printf(\"Error opening output file \\\"%s\\\" for generation of total action weights.\\nExiting...\", fnGenWeightsStats);");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("exit(0);");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
          _builder.newLine();
          {
            if (this.genWeightsDump) {
              _builder.append("\t");
              _builder.append("// Create directory for dumping individual weights for all firings");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("mkdir(\"Profiling-Data\", 0777);");
              _builder.newLine();
              _builder.append("\t");
              _builder.newLine();
            }
          }
          _builder.append("\t");
          _builder.append("fprintf(fpGenWeightsStats, \"<?xml version=\\\"1.0\\\" ?>\\n<network name=\\\"");
          String _simpleName_2 = network.getSimpleName();
          _builder.append(_simpleName_2, "\t");
          _builder.append("\\\">\\n\");");
          _builder.newLineIfNotEmpty();
          {
            EList<Vertex> _children = network.getChildren();
            for(final Vertex child : _children) {
              _builder.append("\t");
              CharSequence _printCalcGenWeightsStatsInstanceActions = this.printCalcGenWeightsStatsInstanceActions(child.<Actor>getAdapter(Actor.class));
              _builder.append(_printCalcGenWeightsStatsInstanceActions, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.newLine();
          _builder.append("\t");
          _builder.append("fprintf(fpGenWeightsStats, \"</network>\\n\");");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("fclose(fpGenWeightsStats);");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("printf(\"Execution action weights are generated in file: %s\\n\", fnGenWeightsStats);");
          _builder.newLine();
          _builder.append("} // end-of-calcGenWeightsActions().");
          _builder.newLine();
          _builder.newLine();
          _builder.append("void calcGenWeightsSchedulers() {");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("FILE *fpGenWeightsStats = NULL;");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("int i, j;");
          _builder.newLine();
          _builder.append("\t");
          {
            if (this.genWeightsDump) {
              _builder.append("FILE *fpGenWeightsFirings = NULL;");
            }
          }
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("char fnGenWeightsStats[FILENAME_MAX];");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("int useFilter = ");
          {
            if (this.genWeightsFilter) {
              _builder.append("1");
            } else {
              _builder.append("0");
            }
          }
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("\t");
          _builder.append("if(opt->input_file != NULL)");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("sprintf(fnGenWeightsStats, \"schedulers_weights_");
          String _simpleName_3 = network.getSimpleName();
          _builder.append(_simpleName_3, "\t\t");
          _builder.append("_%s.sxdf\", basename(opt->input_file));");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("else");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("sprintf(fnGenWeightsStats, \"schedulers_weights_");
          String _simpleName_4 = network.getSimpleName();
          _builder.append(_simpleName_4, "\t\t");
          _builder.append(".sxdf\");");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("fpGenWeightsStats = fopen(fnGenWeightsStats, \"w\");");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("if(fpGenWeightsStats == NULL) {");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("printf(\"Error opening output file \\\"%s\\\" for generation of total scheduler weights.\\nExiting...\", fnGenWeightsStats);");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("exit(0);");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
          _builder.newLine();
          {
            if (this.genWeightsDump) {
              _builder.append("\t");
              _builder.append("// Create directory for dumping individual weights for all firings");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("mkdir(\"Profiling-Data\", 0777);");
              _builder.newLine();
              _builder.append("\t");
              _builder.newLine();
            }
          }
          _builder.append("\t");
          _builder.append("fprintf(fpGenWeightsStats, \"<?xml version=\\\"1.0\\\" ?>\\n<network name=\\\"");
          String _simpleName_5 = network.getSimpleName();
          _builder.append(_simpleName_5, "\t");
          _builder.append("\\\">\\n\");");
          _builder.newLineIfNotEmpty();
          {
            EList<Vertex> _children_1 = network.getChildren();
            for(final Vertex child_1 : _children_1) {
              _builder.append("\t");
              CharSequence _printCalcGenWeightsStatsInstanceScheduler = this.printCalcGenWeightsStatsInstanceScheduler(child_1.<Actor>getAdapter(Actor.class));
              _builder.append(_printCalcGenWeightsStatsInstanceScheduler, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.newLine();
          _builder.append("\t");
          _builder.append("fprintf(fpGenWeightsStats, \"</network>\\n\");");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("fclose(fpGenWeightsStats);");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("printf(\"Execution scheduler weights are generated in file: %s\\n\", fnGenWeightsStats);");
          _builder.newLine();
          _builder.append("} // end-of-calcGenWeightsScheduler().");
          _builder.newLine();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence printCalcGenWeightsStatsInstanceActions(final Actor actor) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.genWeightsDump) {
        _builder.append("fpGenWeightsFirings = fopen(\"Profiling-Data/actions_weights_");
        String _name = actor.getName();
        _builder.append(_name);
        _builder.append(".exdf\", \"w\");");
        _builder.newLineIfNotEmpty();
        _builder.append("if(fpGenWeightsFirings == NULL) {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"Error opening output file \\\"%s\\\" for generation of individual action weights.\\nExiting...\", \"Profiling-Data/actions_weights_");
        String _name_1 = actor.getName();
        _builder.append(_name_1, "\t");
        _builder.append(".exdf\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("exit(0);");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("fprintf(fpGenWeightsStats, \"\\t<actor id=\\\"");
    String _name_2 = actor.getName();
    _builder.append(_name_2);
    _builder.append("\\\">\\n\");");
    _builder.newLineIfNotEmpty();
    {
      EList<Action> _actions = actor.getActions();
      for(final Action action : _actions) {
        {
          if (this.genWeightsDump) {
            _builder.append("printAllFiringsWeights(\"");
            String _name_3 = action.getName();
            _builder.append(_name_3);
            _builder.append("\", profDataAction_");
            String _name_4 = actor.getName();
            _builder.append(_name_4);
            _builder.append("_");
            String _name_5 = action.getName();
            _builder.append(_name_5);
            _builder.append(", fpGenWeightsFirings);");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("calcWeightStats(profDataAction_");
        String _name_6 = actor.getName();
        _builder.append(_name_6);
        _builder.append("_");
        String _name_7 = action.getName();
        _builder.append(_name_7);
        _builder.append(", useFilter);");
        _builder.newLineIfNotEmpty();
        _builder.append("fprintf(fpGenWeightsStats, \"\\t\\t<action id=\\\"");
        String _name_8 = action.getName();
        _builder.append(_name_8);
        _builder.append("\\\" clockcycles=\\\"%Lf\\\" clockcycles-min=\\\"%Lf\\\" clockcycles-max=\\\"%Lf\\\" clockcycles-var=\\\"%Lf\\\" firings=\\\"%\"PRIu64\"\\\"/>\\n\", ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("profDataAction_");
        String _name_9 = actor.getName();
        _builder.append(_name_9, "\t");
        _builder.append("_");
        String _name_10 = action.getName();
        _builder.append(_name_10, "\t");
        _builder.append("->_avgWeight, ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("(profDataAction_");
        String _name_11 = actor.getName();
        _builder.append(_name_11, "\t");
        _builder.append("_");
        String _name_12 = action.getName();
        _builder.append(_name_12, "\t");
        _builder.append("->_numFirings > 0)?profDataAction_");
        String _name_13 = actor.getName();
        _builder.append(_name_13, "\t");
        _builder.append("_");
        String _name_14 = action.getName();
        _builder.append(_name_14, "\t");
        _builder.append("->_minWeight:0, ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("profDataAction_");
        String _name_15 = actor.getName();
        _builder.append(_name_15, "\t");
        _builder.append("_");
        String _name_16 = action.getName();
        _builder.append(_name_16, "\t");
        _builder.append("->_maxWeight,");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("profDataAction_");
        String _name_17 = actor.getName();
        _builder.append(_name_17, "\t");
        _builder.append("_");
        String _name_18 = action.getName();
        _builder.append(_name_18, "\t");
        _builder.append("->_variance, ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("profDataAction_");
        String _name_19 = actor.getName();
        _builder.append(_name_19, "\t");
        _builder.append("_");
        String _name_20 = action.getName();
        _builder.append(_name_20, "\t");
        _builder.append("->_numFirings);");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.append("fprintf(fpGenWeightsStats, \"\\t</actor>\\n\");");
    _builder.newLine();
    {
      if (this.genWeightsDump) {
        _builder.append("fclose(fpGenWeightsFirings);");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printCalcGenWeightsStatsInstanceScheduler(final Actor actor) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.genWeightsDump) {
        _builder.append("fpGenWeightsFirings = fopen(\"Profiling-Data/schedulers_weights_");
        String _name = actor.getName();
        _builder.append(_name);
        _builder.append(".sxdf\", \"w\");");
        _builder.newLineIfNotEmpty();
        _builder.append("if(fpGenWeightsFirings == NULL) {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("printf(\"Error opening output file \\\"%s\\\" for generation of individual scheduler weights.\\nExiting...\", \"Profiling-Data/schedulers_weights_");
        String _name_1 = actor.getName();
        _builder.append(_name_1, "\t");
        _builder.append(".sxdf\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("exit(0);");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("fprintf(fpGenWeightsStats, \"\\t<actor id=\\\"");
    String _name_2 = actor.getName();
    _builder.append(_name_2);
    _builder.append("\\\">\\n\");");
    _builder.newLineIfNotEmpty();
    _builder.append("for(i=0; i<profDataScheduler_");
    String _name_3 = actor.getName();
    _builder.append(_name_3);
    _builder.append("->_sizeX; i++) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("for(j=0; j<profDataScheduler_");
    String _name_4 = actor.getName();
    _builder.append(_name_4, "\t");
    _builder.append("->_sizeY; j++) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("if(profDataScheduler_");
    String _name_5 = actor.getName();
    _builder.append(_name_5, "\t\t");
    _builder.append("->_map[i][j]._profData != NULL && profDataScheduler_");
    String _name_6 = actor.getName();
    _builder.append(_name_6, "\t\t");
    _builder.append("->_map[i][j]._profData->_head != NULL) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    {
      if (this.genWeightsDump) {
        _builder.append("printAllSchedFiringsWeights(&profDataScheduler_");
        String _name_7 = actor.getName();
        _builder.append(_name_7, "\t\t\t");
        _builder.append("->_map[i][j], fpGenWeightsFirings);");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("calcWeightStats(profDataScheduler_");
    String _name_8 = actor.getName();
    _builder.append(_name_8, "\t\t\t");
    _builder.append("->_map[i][j]._profData, useFilter);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("fprintf(fpGenWeightsStats, \"\\t\\t<scheduling source=\\\"%s\\\" target=\\\"%s\\\" clockcycles=\\\"%Lf\\\" clockcycles-min=\\\"%Lf\\\" clockcycles-max=\\\"%Lf\\\" clockcycles-var=\\\"%Lf\\\" firings=\\\"%\"PRIu64\"\\\"/>\\n\", ");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("profDataScheduler_");
    String _name_9 = actor.getName();
    _builder.append(_name_9, "\t\t\t\t");
    _builder.append("->_map[i][j]._srcAction,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("profDataScheduler_");
    String _name_10 = actor.getName();
    _builder.append(_name_10, "\t\t\t\t");
    _builder.append("->_map[i][j]._dstAction,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("profDataScheduler_");
    String _name_11 = actor.getName();
    _builder.append(_name_11, "\t\t\t\t");
    _builder.append("->_map[i][j]._profData->_avgWeight, ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("(profDataScheduler_");
    String _name_12 = actor.getName();
    _builder.append(_name_12, "\t\t\t\t");
    _builder.append("->_map[i][j]._profData->_numFirings > 0)?profDataScheduler_");
    String _name_13 = actor.getName();
    _builder.append(_name_13, "\t\t\t\t");
    _builder.append("->_map[i][j]._profData->_minWeight:0, ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("profDataScheduler_");
    String _name_14 = actor.getName();
    _builder.append(_name_14, "\t\t\t\t");
    _builder.append("->_map[i][j]._profData->_maxWeight,");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("profDataScheduler_");
    String _name_15 = actor.getName();
    _builder.append(_name_15, "\t\t\t\t");
    _builder.append("->_map[i][j]._profData->_variance, ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("profDataScheduler_");
    String _name_16 = actor.getName();
    _builder.append(_name_16, "\t\t\t\t");
    _builder.append("->_map[i][j]._profData->_numFirings);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("fprintf(fpGenWeightsStats, \"\\t</actor>\\n\");");
    _builder.newLine();
    {
      if (this.genWeightsDump) {
        _builder.append("fclose(fpGenWeightsFirings);");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence profileStart(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((this.profile && (!this.actor.getInitializes().contains(action)))) {
        _builder.append("ticks tick_in = getticks();");
        _builder.newLine();
        _builder.append("ticks tick_out;");
        _builder.newLine();
        _builder.append("double diff_tick;");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence profileEnd(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _contains = this.actor.getInitializes().contains(action);
      boolean _not = (!_contains);
      if (_not) {
        {
          EList<Port> _ports = action.getInputPattern().getPorts();
          for(final Port port : _ports) {
            _builder.append("rate_");
            String _name = port.getName();
            _builder.append(_name);
            _builder.append(" += ");
            int _numTokens = action.getInputPattern().getNumTokens(port);
            _builder.append(_numTokens);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      if ((this.profile && (!this.actor.getInitializes().contains(action)))) {
        _builder.append("tick_out = getticks();");
        _builder.newLine();
        _builder.append("diff_tick = elapsed(tick_out, tick_in);");
        _builder.newLine();
        _builder.append("update_ticks_stats(&action_");
        String _name_1 = action.getName();
        _builder.append(_name_1);
        _builder.append(", diff_tick);");
        _builder.newLineIfNotEmpty();
        _builder.append("action_");
        String _name_2 = action.getName();
        _builder.append(_name_2);
        _builder.append(".firings++;");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence papifyStruct(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Papi_actions_");
    String _name = this.actor.getName();
    _builder.append(_name);
    _builder.append("[");
    int _indexOf = IterableExtensions.<Action>toList(this.papifyActions).indexOf(action);
    _builder.append(_indexOf);
    _builder.append("]");
    return _builder;
  }
  
  protected CharSequence print(final Action action) {
    CharSequence _xblockexpression = null;
    {
      this.currentAction = action;
      this.isActionAligned = false;
      this.debugAction = action.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _print = this.print(action.getScheduler());
      _builder.append(_print);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      {
        boolean _hasAttribute = action.hasAttribute(OrccAttributes.ALIGNED_ALWAYS);
        boolean _not = (!_hasAttribute);
        if (_not) {
          CharSequence _printCore = this.printCore(action, false);
          _builder.append(_printCore);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if (this.isActionAligned = action.hasAttribute(OrccAttributes.ALIGNABLE)) {
          CharSequence _printCore_1 = this.printCore(action, true);
          _builder.append(_printCore_1);
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence print(final Procedure proc) {
    CharSequence _xblockexpression = null;
    {
      final boolean isOptimizable = proc.hasAttribute(OrccAttributes.DIRECTIVE_OPTIMIZE_C);
      Attribute _attribute = proc.getAttribute(OrccAttributes.DIRECTIVE_OPTIMIZE_C);
      String _valueAsString = null;
      if (_attribute!=null) {
        _valueAsString=_attribute.getValueAsString("condition");
      }
      final String optCond = _valueAsString;
      Attribute _attribute_1 = proc.getAttribute(OrccAttributes.DIRECTIVE_OPTIMIZE_C);
      String _valueAsString_1 = null;
      if (_attribute_1!=null) {
        _valueAsString_1=_attribute_1.getValueAsString("name");
      }
      final String optName = _valueAsString_1;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("static ");
      CharSequence _inline = this.getInline();
      _builder.append(_inline);
      CharSequence _doSwitch = this.doSwitch(proc.getReturnType());
      _builder.append(_doSwitch);
      _builder.append(" ");
      String _name = proc.getName();
      _builder.append(_name);
      _builder.append("(");
      final Function1<Param, CharSequence> _function = new Function1<Param, CharSequence>() {
        @Override
        public CharSequence apply(final Param it) {
          return InstancePrinter.this.declare(it);
        }
      };
      String _join = IterableExtensions.<Param>join(proc.getParameters(), ", ", _function);
      _builder.append(_join);
      _builder.append(") {");
      _builder.newLineIfNotEmpty();
      {
        if (isOptimizable) {
          _builder.append("\t");
          _builder.append("#if ");
          _builder.append(optCond, "\t");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append(optName, "\t");
          _builder.append("(");
          final Function1<Param, CharSequence> _function_1 = new Function1<Param, CharSequence>() {
            @Override
            public CharSequence apply(final Param it) {
              return it.getVariable().getName();
            }
          };
          String _join_1 = IterableExtensions.<Param>join(proc.getParameters(), ", ", _function_1);
          _builder.append(_join_1, "\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("#else");
          _builder.newLine();
        }
      }
      {
        EList<Var> _locals = proc.getLocals();
        for(final Var variable : _locals) {
          _builder.append("\t");
          CharSequence _declare = this.declare(variable);
          _builder.append(_declare, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      {
        EList<Block> _blocks = proc.getBlocks();
        for(final Block block : _blocks) {
          _builder.append("\t");
          CharSequence _doSwitch_1 = this.doSwitch(block);
          _builder.append(_doSwitch_1, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if (isOptimizable) {
          _builder.append("\t");
          _builder.append("#endif // ");
          _builder.append(optCond, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence declare(final Procedure proc) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _isNative = proc.isNative();
      if (_isNative) {
        _xifexpression = "extern";
      } else {
        _xifexpression = "static";
      }
      final String modifier = _xifexpression;
      StringConcatenation _builder = new StringConcatenation();
      {
        String _name = proc.getName();
        boolean _notEquals = (!Objects.equal(_name, "print"));
        if (_notEquals) {
          _builder.append(modifier);
          _builder.append(" ");
          CharSequence _doSwitch = this.doSwitch(proc.getReturnType());
          _builder.append(_doSwitch);
          _builder.append(" ");
          String _name_1 = proc.getName();
          _builder.append(_name_1);
          _builder.append("(");
          final Function1<Param, CharSequence> _function = new Function1<Param, CharSequence>() {
            @Override
            public CharSequence apply(final Param it) {
              return InstancePrinter.this.declare(it);
            }
          };
          String _join = IterableExtensions.<Param>join(proc.getParameters(), ", ", _function);
          _builder.append(_join);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  protected CharSequence declare(final Var variable) {
    CharSequence _xifexpression = null;
    if ((((variable.isGlobal() && variable.isInitialized()) && (!variable.isAssignable())) && (!variable.getType().isList()))) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#define ");
      String _name = variable.getName();
      _builder.append(_name);
      _builder.append(" ");
      CharSequence _doSwitch = this.doSwitch(variable.getInitialValue());
      _builder.append(_doSwitch);
      _xifexpression = _builder;
    } else {
      CharSequence _xblockexpression = null;
      {
        String _xifexpression_1 = null;
        if (((!variable.isAssignable()) && variable.isGlobal())) {
          _xifexpression_1 = "const ";
        }
        final String const_ = _xifexpression_1;
        String _xifexpression_2 = null;
        boolean _isGlobal = variable.isGlobal();
        if (_isGlobal) {
          _xifexpression_2 = "static ";
        }
        final String global = _xifexpression_2;
        final Type type = variable.getType();
        final String dims = this.printArrayIndexes(variable.getType().getDimensionsExpr());
        String _xifexpression_3 = null;
        boolean _isInitialized = variable.isInitialized();
        if (_isInitialized) {
          CharSequence _doSwitch_1 = this.doSwitch(variable.getInitialValue());
          _xifexpression_3 = (" = " + _doSwitch_1);
        }
        final String init = _xifexpression_3;
        String _xifexpression_4 = null;
        boolean _isGlobal_1 = variable.isGlobal();
        if (_isGlobal_1) {
          _xifexpression_4 = ";";
        }
        final String end = _xifexpression_4;
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(global);
        _builder_1.append(const_);
        CharSequence _doSwitch_2 = this.doSwitch(type);
        _builder_1.append(_doSwitch_2);
        _builder_1.append(" ");
        String _name_1 = variable.getName();
        _builder_1.append(_name_1);
        _builder_1.append(dims);
        _builder_1.append(init);
        _builder_1.append(end);
        _xblockexpression = _builder_1;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  protected CharSequence declare(final Param param) {
    CharSequence _xblockexpression = null;
    {
      final Var variable = param.getVariable();
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _doSwitch = this.doSwitch(variable.getType());
      _builder.append(_doSwitch);
      _builder.append(" ");
      String _name = variable.getName();
      _builder.append(_name);
      String _printArrayIndexes = this.printArrayIndexes(variable.getType().getDimensionsExpr());
      _builder.append(_printArrayIndexes);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  private String getReaderId(final Port port) {
    String _xifexpression = null;
    boolean _containsKey = this.incomingPortMap.containsKey(port);
    if (_containsKey) {
      _xifexpression = String.valueOf(this.incomingPortMap.get(port).<Integer>getValueAsObject("fifoId"));
    } else {
      _xifexpression = "-1";
    }
    return _xifexpression;
  }
  
  protected CharSequence fullName(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.entityName);
    _builder.append("_");
    String _name = port.getName();
    _builder.append(_name);
    return _builder;
  }
  
  protected Integer sizeOrDefaultSize(final Connection conn) {
    Integer _xifexpression = null;
    if (((conn == null) || (conn.getSize() == null))) {
      _xifexpression = Integer.valueOf(this.fifoSize);
    } else {
      _xifexpression = conn.getSize();
    }
    return _xifexpression;
  }
  
  private CharSequence printOpenFiles() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _inputs = this.actor.getInputs();
      EList<Port> _outputs = this.actor.getOutputs();
      Iterable<Port> _plus = Iterables.<Port>concat(_inputs, _outputs);
      for(final Port port : _plus) {
        _builder.append("file_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append(" = fopen(\"");
        _builder.append(this.traceFolder);
        String _replace = File.separator.replace("\\", "\\\\");
        _builder.append(_replace);
        CharSequence _fullName = this.fullName(port);
        _builder.append(_fullName);
        _builder.append(".txt\", \"a\");");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence printCloseFiles() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _inputs = this.actor.getInputs();
      EList<Port> _outputs = this.actor.getOutputs();
      Iterable<Port> _plus = Iterables.<Port>concat(_inputs, _outputs);
      for(final Port port : _plus) {
        _builder.append("fclose(file_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseBlockIf(final BlockIf block) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    CharSequence _doSwitch = this.doSwitch(block.getCondition());
    _builder.append(_doSwitch);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    {
      EList<Block> _thenBlocks = block.getThenBlocks();
      for(final Block thenBlock : _thenBlocks) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(thenBlock);
        _builder.append(_doSwitch_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    {
      boolean _isElseRequired = block.isElseRequired();
      if (_isElseRequired) {
        _builder.append(" else {");
        _builder.newLineIfNotEmpty();
        {
          EList<Block> _elseBlocks = block.getElseBlocks();
          for(final Block elseBlock : _elseBlocks) {
            _builder.append("\t");
            CharSequence _doSwitch_2 = this.doSwitch(elseBlock);
            _builder.append(_doSwitch_2, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseBlockWhile(final BlockWhile blockWhile) {
    CharSequence _xifexpression = null;
    if (((!this.isActionAligned) || (!blockWhile.hasAttribute(OrccAttributes.REMOVABLE_COPY)))) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("while (");
      CharSequence _doSwitch = this.doSwitch(blockWhile.getCondition());
      _builder.append(_doSwitch);
      _builder.append(") {");
      _builder.newLineIfNotEmpty();
      {
        EList<Block> _blocks = blockWhile.getBlocks();
        for(final Block block : _blocks) {
          _builder.append("\t");
          CharSequence _doSwitch_1 = this.doSwitch(block);
          _builder.append(_doSwitch_1, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("}");
      _builder.newLine();
      _xifexpression = _builder;
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseBlockBasic(final BlockBasic block) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Instruction> _instructions = block.getInstructions();
      for(final Instruction instr : _instructions) {
        CharSequence _doSwitch = this.doSwitch(instr);
        _builder.append(_doSwitch);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public String caseBlockFor(final BlockFor block) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("for (");
    final Function1<Instruction, CharSequence> _function = new Function1<Instruction, CharSequence>() {
      @Override
      public CharSequence apply(final Instruction it) {
        StringConcatenation _builder = new StringConcatenation();
        String _expression = InstancePrinter.this.toExpression(it);
        _builder.append(_expression);
        return _builder.toString();
      }
    };
    String _join = IterableExtensions.<Instruction>join(block.getInit(), ", ", _function);
    _builder.append(_join);
    _builder.append(" ; ");
    CharSequence _doSwitch = this.doSwitch(block.getCondition());
    _builder.append(_doSwitch);
    _builder.append(" ; ");
    final Function1<Instruction, CharSequence> _function_1 = new Function1<Instruction, CharSequence>() {
      @Override
      public CharSequence apply(final Instruction it) {
        StringConcatenation _builder = new StringConcatenation();
        String _expression = InstancePrinter.this.toExpression(it);
        _builder.append(_expression);
        return _builder.toString();
      }
    };
    String _join_1 = IterableExtensions.<Instruction>join(block.getStep(), ", ", _function_1);
    _builder.append(_join_1);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    {
      EList<Block> _blocks = block.getBlocks();
      for(final Block contentBlock : _blocks) {
        _builder.append("\t");
        CharSequence _doSwitch_1 = this.doSwitch(contentBlock);
        _builder.append(_doSwitch_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public CharSequence caseInstAssign(final InstAssign inst) {
    CharSequence _xblockexpression = null;
    {
      Type _type = inst.getTarget().getVariable().getType();
      String _plus = ("InstAssign: target type: " + _type);
      String _plus_1 = (_plus + "; value type: ");
      Type _type_1 = inst.getValue().getType();
      String _plus_2 = (_plus_1 + _type_1);
      OrccLogger.warnln(_plus_2);
      CharSequence _xifexpression = null;
      boolean _isString = inst.getTarget().getVariable().getType().isString();
      if (_isString) {
        CharSequence _xifexpression_1 = null;
        boolean _isString_1 = inst.getValue().getType().isString();
        if (_isString_1) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("strcpy( ");
          String _name = inst.getTarget().getVariable().getName();
          _builder.append(_name);
          _builder.append(" , ");
          CharSequence _doSwitch = this.doSwitch(inst.getValue());
          _builder.append(_doSwitch);
          _builder.append(" );");
          _builder.newLineIfNotEmpty();
          _xifexpression_1 = _builder;
        } else {
          CharSequence _xifexpression_2 = null;
          boolean _isFloat = inst.getValue().getType().isFloat();
          if (_isFloat) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("sprintf( temp,\"%f\",");
            CharSequence _doSwitch_1 = this.doSwitch(inst.getValue());
            _builder_1.append(_doSwitch_1);
            _builder_1.append(");");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("strcpy( ");
            String _name_1 = inst.getTarget().getVariable().getName();
            _builder_1.append(_name_1);
            _builder_1.append(",temp ); ");
            _builder_1.newLineIfNotEmpty();
            _xifexpression_2 = _builder_1;
          } else {
            CharSequence _xifexpression_3 = null;
            boolean _isInt = inst.getValue().getType().isInt();
            if (_isInt) {
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("sprintf( temp,\"%d\",");
              CharSequence _doSwitch_2 = this.doSwitch(inst.getValue());
              _builder_2.append(_doSwitch_2);
              _builder_2.append(");");
              _builder_2.newLineIfNotEmpty();
              _builder_2.append("strcpy( ");
              String _name_2 = inst.getTarget().getVariable().getName();
              _builder_2.append(_name_2);
              _builder_2.append(",temp ); ");
              _builder_2.newLineIfNotEmpty();
              _xifexpression_3 = _builder_2;
            }
            _xifexpression_2 = _xifexpression_3;
          }
          _xifexpression_1 = _xifexpression_2;
        }
        _xifexpression = _xifexpression_1;
      } else {
        StringConcatenation _builder_3 = new StringConcatenation();
        String _name_3 = inst.getTarget().getVariable().getName();
        _builder_3.append(_name_3);
        _builder_3.append(" = ");
        CharSequence _doSwitch_3 = this.doSwitch(inst.getValue());
        _builder_3.append(_doSwitch_3);
        _builder_3.append(";");
        _builder_3.newLineIfNotEmpty();
        _xifexpression = _builder_3;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * Print extra code for array inbounds checking (ex: C assert) at each usage (load/store)
   * If exprList is empty, return an empty string.
   */
  private CharSequence checkArrayInbounds(final List<Expression> exprList, final List<Integer> dims) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = exprList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          int _size = exprList.size();
          int _minus = (_size - 1);
          IntegerRange _upTo = new IntegerRange(0, _minus);
          for(final Integer i : _upTo) {
            _builder.append("assert((");
            CharSequence _doSwitch = this.doSwitch(exprList.get((i).intValue()));
            _builder.append(_doSwitch);
            _builder.append(") < ");
            Integer _get = dims.get((i).intValue());
            _builder.append(_get);
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseInstLoad(final InstLoad load) {
    CharSequence _xblockexpression = null;
    {
      final Var target = load.getTarget().getVariable();
      final Var source = load.getSource().getVariable();
      final Port port = this.getPort(source);
      Type _type = load.getTarget().getVariable().getType();
      String _plus = ("InstLoad: target type: " + _type);
      String _plus_1 = (_plus + "; source type: ");
      Type _type_1 = load.getSource().getVariable().getType();
      String _plus_2 = (_plus_1 + _type_1);
      OrccLogger.warnln(_plus_2);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      {
        if ((port != null)) {
          {
            if (((this.isActionAligned && port.hasAttribute(((this.currentAction.getName() + "_") + OrccAttributes.ALIGNABLE))) || port.hasAttribute(OrccAttributes.ALIGNED_ALWAYS))) {
              {
                boolean _isString = target.getType().isString();
                if (_isString) {
                  _builder.append("strcpy( ");
                  String _name = target.getName();
                  _builder.append(_name);
                  _builder.append(",tokens_");
                  String _name_1 = port.getName();
                  _builder.append(_name_1);
                  _builder.append("[(index_");
                  String _name_2 = port.getName();
                  _builder.append(_name_2);
                  _builder.append(" % SIZE_");
                  String _name_3 = port.getName();
                  _builder.append(_name_3);
                  _builder.append(") + (");
                  CharSequence _doSwitch = this.doSwitch(IterableExtensions.<Expression>head(load.getIndexes()));
                  _builder.append(_doSwitch);
                  _builder.append(")] );");
                  _builder.newLineIfNotEmpty();
                } else {
                  String _name_4 = target.getName();
                  _builder.append(_name_4);
                  _builder.append(" = tokens_");
                  String _name_5 = port.getName();
                  _builder.append(_name_5);
                  _builder.append("[(index_");
                  String _name_6 = port.getName();
                  _builder.append(_name_6);
                  _builder.append(" % SIZE_");
                  String _name_7 = port.getName();
                  _builder.append(_name_7);
                  _builder.append(") + (");
                  CharSequence _doSwitch_1 = this.doSwitch(IterableExtensions.<Expression>head(load.getIndexes()));
                  _builder.append(_doSwitch_1);
                  _builder.append(")];");
                  _builder.newLineIfNotEmpty();
                }
              }
            } else {
              {
                boolean _isString_1 = target.getType().isString();
                if (_isString_1) {
                  _builder.append("strcpy( ");
                  String _name_8 = target.getName();
                  _builder.append(_name_8);
                  _builder.append(",tokens_");
                  String _name_9 = port.getName();
                  _builder.append(_name_9);
                  _builder.append("[(index_");
                  String _name_10 = port.getName();
                  _builder.append(_name_10);
                  _builder.append(" + (");
                  CharSequence _doSwitch_2 = this.doSwitch(IterableExtensions.<Expression>head(load.getIndexes()));
                  _builder.append(_doSwitch_2);
                  _builder.append(")) % SIZE_");
                  String _name_11 = port.getName();
                  _builder.append(_name_11);
                  _builder.append("] );");
                  _builder.newLineIfNotEmpty();
                } else {
                  String _name_12 = target.getName();
                  _builder.append(_name_12);
                  _builder.append(" = tokens_");
                  String _name_13 = port.getName();
                  _builder.append(_name_13);
                  _builder.append("[(index_");
                  String _name_14 = port.getName();
                  _builder.append(_name_14);
                  _builder.append(" + (");
                  CharSequence _doSwitch_3 = this.doSwitch(IterableExtensions.<Expression>head(load.getIndexes()));
                  _builder.append(_doSwitch_3);
                  _builder.append(")) % SIZE_");
                  String _name_15 = port.getName();
                  _builder.append(_name_15);
                  _builder.append("];");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
        } else {
          {
            if (this.checkArrayInbounds) {
              CharSequence _checkArrayInbounds = this.checkArrayInbounds(load.getIndexes(), source.getType().getDimensions());
              _builder.append(_checkArrayInbounds);
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((target.getType().isString() && (source.getType().isString() || (source.getType().isList() && ((TypeList) source.getType()).getInnermostType().isString())))) {
              _builder.append("strcpy( ");
              String _name_16 = target.getName();
              _builder.append(_name_16);
              _builder.append(" , ");
              String _name_17 = source.getName();
              _builder.append(_name_17);
              String _printArrayIndexes = this.printArrayIndexes(load.getIndexes());
              _builder.append(_printArrayIndexes);
              _builder.append(" );");
              _builder.newLineIfNotEmpty();
            } else {
              String _name_18 = target.getName();
              _builder.append(_name_18);
              _builder.append(" = ");
              String _name_19 = source.getName();
              _builder.append(_name_19);
              String _printArrayIndexes_1 = this.printArrayIndexes(load.getIndexes());
              _builder.append(_printArrayIndexes_1);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseInstStore(final InstStore store) {
    CharSequence _xblockexpression = null;
    {
      final Var target = store.getTarget().getVariable();
      Type _type = store.getTarget().getVariable().getType();
      String _plus = ("InstStore: target type: " + _type);
      String _plus_1 = (_plus + "; store value type: ");
      Type _type_1 = store.getValue().getType();
      String _plus_2 = (_plus_1 + _type_1);
      OrccLogger.warnln(_plus_2);
      final Port port = this.getPort(target);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      {
        if ((port != null)) {
          {
            boolean _isNative = port.isNative();
            if (_isNative) {
              _builder.append("printf(\"");
              String _name = port.getName();
              _builder.append(_name);
              _builder.append(" = %i\\n\", ");
              CharSequence _doSwitch = this.doSwitch(store.getValue());
              _builder.append(_doSwitch);
              _builder.append(");");
              _builder.newLineIfNotEmpty();
            } else {
              if (((this.isActionAligned && port.hasAttribute(((this.currentAction.getName() + "_") + OrccAttributes.ALIGNABLE))) || port.hasAttribute(OrccAttributes.ALIGNED_ALWAYS))) {
                {
                  boolean _isString = target.getType().isString();
                  if (_isString) {
                    _builder.append("strcpy( tokens_");
                    String _name_1 = port.getName();
                    _builder.append(_name_1);
                    _builder.append("[(index_");
                    String _name_2 = port.getName();
                    _builder.append(_name_2);
                    _builder.append(" % SIZE_");
                    String _name_3 = port.getName();
                    _builder.append(_name_3);
                    _builder.append(") + (");
                    CharSequence _doSwitch_1 = this.doSwitch(IterableExtensions.<Expression>head(store.getIndexes()));
                    _builder.append(_doSwitch_1);
                    _builder.append(")], ");
                    CharSequence _doSwitch_2 = this.doSwitch(store.getValue());
                    _builder.append(_doSwitch_2);
                    _builder.append(" );");
                    _builder.newLineIfNotEmpty();
                  } else {
                    _builder.append("tokens_");
                    String _name_4 = port.getName();
                    _builder.append(_name_4);
                    _builder.append("[(index_");
                    String _name_5 = port.getName();
                    _builder.append(_name_5);
                    _builder.append(" % SIZE_");
                    String _name_6 = port.getName();
                    _builder.append(_name_6);
                    _builder.append(") + (");
                    CharSequence _doSwitch_3 = this.doSwitch(IterableExtensions.<Expression>head(store.getIndexes()));
                    _builder.append(_doSwitch_3);
                    _builder.append(")] = ");
                    CharSequence _doSwitch_4 = this.doSwitch(store.getValue());
                    _builder.append(_doSwitch_4);
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  }
                }
              } else {
                {
                  if ((target.getType().isString() || (target.getType().isList() && ((TypeList) target.getType()).getInnermostType().isString()))) {
                    _builder.append("strcpy( tokens_");
                    String _name_7 = port.getName();
                    _builder.append(_name_7);
                    _builder.append("[(index_");
                    String _name_8 = port.getName();
                    _builder.append(_name_8);
                    _builder.append(" + (");
                    CharSequence _doSwitch_5 = this.doSwitch(IterableExtensions.<Expression>head(store.getIndexes()));
                    _builder.append(_doSwitch_5);
                    _builder.append(")) % SIZE_");
                    String _name_9 = port.getName();
                    _builder.append(_name_9);
                    _builder.append("], ");
                    CharSequence _doSwitch_6 = this.doSwitch(store.getValue());
                    _builder.append(_doSwitch_6);
                    _builder.append(");");
                    _builder.newLineIfNotEmpty();
                  } else {
                    _builder.append("tokens_");
                    String _name_10 = port.getName();
                    _builder.append(_name_10);
                    _builder.append("[(index_");
                    String _name_11 = port.getName();
                    _builder.append(_name_11);
                    _builder.append(" + (");
                    CharSequence _doSwitch_7 = this.doSwitch(IterableExtensions.<Expression>head(store.getIndexes()));
                    _builder.append(_doSwitch_7);
                    _builder.append(")) % SIZE_");
                    String _name_12 = port.getName();
                    _builder.append(_name_12);
                    _builder.append("] = ");
                    CharSequence _doSwitch_8 = this.doSwitch(store.getValue());
                    _builder.append(_doSwitch_8);
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        } else {
          {
            if (this.checkArrayInbounds) {
              CharSequence _checkArrayInbounds = this.checkArrayInbounds(store.getIndexes(), target.getType().getDimensions());
              _builder.append(_checkArrayInbounds);
              _builder.newLineIfNotEmpty();
            }
          }
          {
            if ((target.getType().isString() && (store.getValue().getType().isString() || (store.getValue().getType().isList() && ((TypeList) store.getValue().getType()).getInnermostType().isString())))) {
              _builder.append("strcpy( temp1,\"\" );");
              _builder.newLine();
              CharSequence _doSwitch_9 = this.doSwitch(store.getValue());
              _builder.append(_doSwitch_9);
              _builder.newLineIfNotEmpty();
              _builder.append("strcpy( ");
              String _name_13 = target.getName();
              _builder.append(_name_13);
              String _printArrayIndexes = this.printArrayIndexes(store.getIndexes());
              _builder.append(_printArrayIndexes);
              _builder.append(",temp1 );");
              _builder.newLineIfNotEmpty();
            } else {
              String _name_14 = target.getName();
              _builder.append(_name_14);
              String _printArrayIndexes_1 = this.printArrayIndexes(store.getIndexes());
              _builder.append(_printArrayIndexes_1);
              _builder.append(" = ");
              CharSequence _doSwitch_10 = this.doSwitch(store.getValue());
              _builder.append(_doSwitch_10);
              _builder.append(";");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseInstCall(final InstCall call) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isPrint = call.isPrint();
      if (_isPrint) {
        _builder.append("printf(");
        String _join = IterableExtensions.join(this.printfArgs(call.getArguments()), ", ");
        _builder.append(_join);
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.newLine();
        {
          Def _target = call.getTarget();
          boolean _tripleNotEquals = (_target != null);
          if (_tripleNotEquals) {
            String _name = call.getTarget().getVariable().getName();
            _builder.append(_name);
            _builder.append(" = ");
          }
        }
        String _name_1 = call.getProcedure().getName();
        _builder.append(_name_1);
        _builder.append("(");
        final Function1<Arg, CharSequence> _function = new Function1<Arg, CharSequence>() {
          @Override
          public CharSequence apply(final Arg it) {
            return InstancePrinter.this.print(it);
          }
        };
        String _join_1 = IterableExtensions.<Arg>join(call.getArguments(), ", ", _function);
        _builder.append(_join_1);
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseInstReturn(final InstReturn ret) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Expression _value = ret.getValue();
      boolean _tripleNotEquals = (_value != null);
      if (_tripleNotEquals) {
        _builder.append("return ");
        CharSequence _doSwitch = this.doSwitch(ret.getValue());
        _builder.append(_doSwitch);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  @Override
  public String caseInstTernary(final InstTernary inst) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = inst.getTarget().getVariable().getName();
    _builder.append(_name);
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(inst.getConditionValue());
    _builder.append(_doSwitch);
    _builder.append(" ? ");
    CharSequence _doSwitch_1 = this.doSwitch(inst.getTrueValue());
    _builder.append(_doSwitch_1);
    _builder.append(" : ");
    CharSequence _doSwitch_2 = this.doSwitch(inst.getFalseValue());
    _builder.append(_doSwitch_2);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  @Override
  public CharSequence caseExprVar(final ExprVar expr) {
    CharSequence _xblockexpression = null;
    {
      final Port port = this.copyOf(expr);
      CharSequence _xifexpression = null;
      if (((port != null) && this.isActionAligned)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("&tokens_");
        String _name = port.getName();
        _builder.append(_name);
        _builder.append("[index_");
        String _name_1 = port.getName();
        _builder.append(_name_1);
        _builder.append(" % SIZE_");
        String _name_2 = port.getName();
        _builder.append(_name_2);
        _builder.append("]");
        _xifexpression = _builder;
      } else {
        _xifexpression = expr.getUse().getVariable().getName();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * Returns the port object corresponding to the given variable.
   * 
   * @param variable
   *            a variable
   * @return the corresponding port, or <code>null</code>
   */
  protected Port getPort(final Var variable) {
    Port _xifexpression = null;
    if ((this.currentAction == null)) {
      _xifexpression = null;
    } else {
      Port _xifexpression_1 = null;
      Pattern _inputPattern = null;
      if (this.currentAction!=null) {
        _inputPattern=this.currentAction.getInputPattern();
      }
      boolean _contains = _inputPattern.contains(variable);
      if (_contains) {
        _xifexpression_1 = this.currentAction.getInputPattern().getPort(variable);
      } else {
        Port _xifexpression_2 = null;
        Pattern _outputPattern = null;
        if (this.currentAction!=null) {
          _outputPattern=this.currentAction.getOutputPattern();
        }
        boolean _contains_1 = _outputPattern.contains(variable);
        if (_contains_1) {
          _xifexpression_2 = this.currentAction.getOutputPattern().getPort(variable);
        } else {
          Port _xifexpression_3 = null;
          Pattern _peekPattern = null;
          if (this.currentAction!=null) {
            _peekPattern=this.currentAction.getPeekPattern();
          }
          boolean _contains_2 = _peekPattern.contains(variable);
          if (_contains_2) {
            _xifexpression_3 = this.currentAction.getPeekPattern().getPort(variable);
          } else {
            _xifexpression_3 = null;
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  /**
   * Returns the port object in case the corresponding expression is
   * just a straight copy of the tokens.
   * 
   * @param expr
   *            an expression
   * @return the corresponding port, or <code>null</code>
   */
  private Port copyOf(final ExprVar expr) {
    final Action action = EcoreHelper.<Action>getContainerOfType(expr, Action.class);
    final Var variable = expr.getUse().getVariable();
    if ((((action == null) || (!expr.getType().isList())) || (!variable.hasAttribute(OrccAttributes.COPY_OF_TOKENS)))) {
      return null;
    }
    EObject _valueAsEObject = variable.<EObject>getValueAsEObject(OrccAttributes.COPY_OF_TOKENS);
    return ((Port) _valueAsEObject);
  }
  
  private CharSequence print(final Arg arg) {
    CharSequence _xifexpression = null;
    boolean _isByRef = arg.isByRef();
    if (_isByRef) {
      String _name = ((ArgByRef) arg).getUse().getVariable().getName();
      String _plus = ("&" + _name);
      String _printArrayIndexes = this.printArrayIndexes(((ArgByRef) arg).getIndexes());
      _xifexpression = (_plus + _printArrayIndexes);
    } else {
      _xifexpression = this.doSwitch(((ArgByVal) arg).getValue());
    }
    return _xifexpression;
  }
  
  private CharSequence getInline() {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.inlineActors) {
        _builder.append("__attribute__((always_inline)) ");
      }
    }
    return _builder;
  }
  
  private CharSequence getNoInline() {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (this.inlineActors) {
        _builder.append("__attribute__((noinline)) ");
      }
    }
    return _builder;
  }
  
  private boolean setDebug() {
    return this.debugActor = this.actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG);
  }
  
  private void buildInputPattern() {
    this.inputPattern.clear();
    EList<Action> _actionsOutsideFsm = this.actor.getActionsOutsideFsm();
    for (final Action action : _actionsOutsideFsm) {
      {
        final Pattern actionPattern = action.getInputPattern();
        EList<Port> _ports = actionPattern.getPorts();
        for (final Port port : _ports) {
          {
            int numTokens = Math.max(this.inputPattern.getNumTokens(port), actionPattern.getNumTokens(port));
            this.inputPattern.setNumTokens(port, numTokens);
          }
        }
      }
    }
  }
  
  private void buildTransitionPattern() {
    final FSM fsm = this.actor.getFsm();
    this.transitionPattern.clear();
    if ((fsm != null)) {
      EList<State> _states = fsm.getStates();
      for (final State state : _states) {
        {
          final Pattern pattern = DfFactory.eINSTANCE.createPattern();
          EList<Edge> _outgoing = state.getOutgoing();
          for (final Edge edge : _outgoing) {
            {
              final Pattern actionPattern = ((Transition) edge).getAction().getInputPattern();
              EList<Port> _ports = actionPattern.getPorts();
              for (final Port port : _ports) {
                {
                  int numTokens = Math.max(pattern.getNumTokens(port), actionPattern.getNumTokens(port));
                  pattern.setNumTokens(port, numTokens);
                }
              }
            }
          }
          this.transitionPattern.put(state, pattern);
        }
      }
    }
  }
}
