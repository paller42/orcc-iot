/**
 * Copyright (c) 2011, Abo Akademi University
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
 *   * Neither the name of the Abo Akademi University nor the names of its
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
package net.sf.orcc.backends.promela;

import java.util.List;
import java.util.Set;
import net.sf.orcc.backends.promela.PromelaTemplate;
import net.sf.orcc.backends.promela.transform.Schedule;
import net.sf.orcc.backends.promela.transform.ScheduleBalanceEq;
import net.sf.orcc.backends.promela.transform.Scheduler;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.ir.Var;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Prints the contents of the class ScheduleBalanceEq to an XML file
 * 
 * @author Johan Ersfolk
 */
@SuppressWarnings("all")
public class ScheduleInfoPrinter extends PromelaTemplate {
  private Network network;
  
  private final ScheduleBalanceEq balanceEq;
  
  public ScheduleInfoPrinter(final ScheduleBalanceEq balanceEq) {
    this.balanceEq = balanceEq;
  }
  
  public Network setNetwork(final Network network) {
    return this.network = network;
  }
  
  public CharSequence getSchedulerFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<!-- Generated from \"");
    String _name = this.network.getName();
    _builder.append(_name);
    _builder.append("\" -->");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("<network>");
    _builder.newLine();
    {
      Set<Actor> _actors = this.balanceEq.getActors();
      for(final Actor actor : _actors) {
        _builder.append("\t");
        CharSequence _printInstance = this.printInstance(actor);
        _builder.append(_printInstance, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</network>");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence printInstance(final Actor actor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<actor name=\"");
    String _name = actor.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _schedulesxml = this.schedulesxml(this.balanceEq.getScheduler(actor));
    _builder.append(_schedulesxml, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<connections>");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _connections = this.connections(actor);
    _builder.append(_connections, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</connections>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<state>");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _stateVarsInState = this.stateVarsInState(this.balanceEq.getScheduler(actor));
    _builder.append(_stateVarsInState, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</state>");
    _builder.newLine();
    _builder.append("</actor>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence stateVarsInState(final Scheduler scheduler) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Set<State> _keySet = scheduler.getStateToRelevantVars().keySet();
      for(final State state : _keySet) {
        {
          if ((state != null)) {
            _builder.append("<fsmstate name=\'");
            _builder.append(state);
            _builder.append("\'>");
            _builder.newLineIfNotEmpty();
            {
              Set<Var> _get = scheduler.getStateToRelevantVars().get(state);
              for(final Var variable : _get) {
                _builder.append("\t");
                _builder.append("<variable name=\'");
                String _name = variable.getName();
                _builder.append(_name, "\t");
                _builder.append("\'/>");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("</fsmstate>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("<allstates>");
    _builder.newLine();
    {
      Set<Var> _schedulingVars = scheduler.getSchedulingVars();
      for(final Var variable_1 : _schedulingVars) {
        _builder.append("\t");
        _builder.append("<variable name=\'");
        String _name_1 = variable_1.getName();
        _builder.append(_name_1, "\t");
        _builder.append("\'/>");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</allstates>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence schedulesxml(final Scheduler scheduler) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Set<Schedule> _schedules = scheduler.getSchedules();
      for(final Schedule sched : _schedules) {
        _builder.append("<schedule initstate=\"");
        CharSequence _initStateName = this.initStateName(sched);
        _builder.append(_initStateName);
        _builder.append("\" action=\"");
        CharSequence _initStateName_1 = this.initStateName(sched);
        _builder.append(_initStateName_1);
        _builder.append("_");
        CharSequence _enablingActionName = this.enablingActionName(sched);
        _builder.append(_enablingActionName);
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _rates = this.rates(sched);
        _builder.append(_rates, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("</schedule>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence connections(final Actor instance) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Set<Port> _keySet = instance.getIncomingPortMap().keySet();
      for(final Port port : _keySet) {
        {
          Actor _source = this.balanceEq.getSource(instance.getIncomingPortMap().get(port));
          boolean _tripleNotEquals = (_source != null);
          if (_tripleNotEquals) {
            _builder.append("<input port=\"");
            String _name = port.getName();
            _builder.append(_name);
            _builder.append("\" instance=\"");
            String _simpleName = this.balanceEq.getSource(instance.getIncomingPortMap().get(port)).getSimpleName();
            _builder.append(_simpleName);
            _builder.append("\" channelID=\"");
            CharSequence _connID = this.connID(port, instance);
            _builder.append(_connID);
            _builder.append("\"/>");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("<input port=\"");
            String _name_1 = port.getName();
            _builder.append(_name_1);
            _builder.append("\" instance=\"NULL\" channelID=\"");
            CharSequence _connID_1 = this.connID(port, instance);
            _builder.append(_connID_1);
            _builder.append("\"/>");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      Set<Port> _keySet_1 = instance.getOutgoingPortMap().keySet();
      for(final Port port_1 : _keySet_1) {
        {
          List<Connection> _get = instance.getOutgoingPortMap().get(port_1);
          for(final Connection con : _get) {
            {
              Actor _destination = this.balanceEq.getDestination(con);
              boolean _tripleNotEquals_1 = (_destination != null);
              if (_tripleNotEquals_1) {
                _builder.append("<output port=\"");
                String _name_2 = port_1.getName();
                _builder.append(_name_2);
                _builder.append("\" instance=\"");
                String _simpleName_1 = this.balanceEq.getDestination(con).getSimpleName();
                _builder.append(_simpleName_1);
                _builder.append("\" channelID=\"");
                CharSequence _connID_2 = this.connID(port_1, instance);
                _builder.append(_connID_2);
                _builder.append("\"/>");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("<output port=\"");
                String _name_3 = port_1.getName();
                _builder.append(_name_3);
                _builder.append("\" instance=\"NULL\" channelID=\"");
                CharSequence _connID_3 = this.connID(port_1, instance);
                _builder.append(_connID_3);
                _builder.append("\"/>");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence connID(final Port port, final Actor actor) {
    CharSequence _xifexpression = null;
    Connection _get = actor.getIncomingPortMap().get(port);
    boolean _tripleNotEquals = (_get != null);
    if (_tripleNotEquals) {
      CharSequence _xblockexpression = null;
      {
        final Connection connection = actor.getIncomingPortMap().get(port);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("chan_");
        Object _valueAsObject = connection.<Object>getValueAsObject("id");
        _builder.append(_valueAsObject);
        _xblockexpression = _builder;
      }
      _xifexpression = _xblockexpression;
    } else {
      CharSequence _xifexpression_1 = null;
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(actor.getOutgoingPortMap().get(port));
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _xblockexpression_1 = null;
        {
          final Connection connection = actor.getOutgoingPortMap().get(port).get(0);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("chan_");
          Object _valueAsObject = connection.<Object>getValueAsObject("id");
          _builder.append(_valueAsObject);
          _xblockexpression_1 = _builder;
        }
        _xifexpression_1 = _xblockexpression_1;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  public CharSequence rates(final Schedule schedule) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<rates>");
    _builder.newLine();
    {
      Set<String> _keySet = schedule.getPortPeeks().keySet();
      for(final String port : _keySet) {
        _builder.append("<peek port=\"");
        _builder.append(port);
        _builder.append("\" value=\"");
        Object _get = schedule.getPortPeeks().get(port).get(0);
        _builder.append(_get);
        _builder.append("\"/>");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Set<String> _keySet_1 = schedule.getPortReads().keySet();
      for(final String port_1 : _keySet_1) {
        _builder.append("<read port=\"");
        _builder.append(port_1);
        _builder.append("\" value=\"");
        int _size = schedule.getPortReads().get(port_1).size();
        _builder.append(_size);
        _builder.append("\"/>");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Set<String> _keySet_2 = schedule.getPortWrites().keySet();
      for(final String port_2 : _keySet_2) {
        _builder.append("<write port=\"");
        _builder.append(port_2);
        _builder.append("\" value=\"");
        int _size_1 = schedule.getPortWrites().get(port_2).size();
        _builder.append(_size_1);
        _builder.append("\"/>");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</rates>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence enablingActionName(final Schedule schedule) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Action _enablingAction = schedule.getEnablingAction();
      boolean _tripleNotEquals = (_enablingAction != null);
      if (_tripleNotEquals) {
        String _name = schedule.getEnablingAction().getName();
        _builder.append(_name);
      } else {
        String _name_1 = schedule.getSequence().get(0).getName();
        _builder.append(_name_1);
      }
    }
    return _builder;
  }
  
  public CharSequence initStateName(final Schedule schedule) {
    StringConcatenation _builder = new StringConcatenation();
    {
      State _initState = schedule.getInitState();
      boolean _tripleNotEquals = (_initState != null);
      if (_tripleNotEquals) {
        String _name = schedule.getInitState().getName();
        _builder.append(_name);
      } else {
        _builder.append("one_state");
      }
    }
    return _builder;
  }
  
  public CharSequence endStateName(final Schedule schedule) {
    StringConcatenation _builder = new StringConcatenation();
    {
      State _endState = schedule.getEndState();
      boolean _tripleNotEquals = (_endState != null);
      if (_tripleNotEquals) {
        String _name = schedule.getEndState().getName();
        _builder.append(_name);
      } else {
        _builder.append("one_state");
      }
    }
    return _builder;
  }
}
