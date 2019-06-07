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
import net.sf.orcc.backends.promela.transform.Scheduler;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.State;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Generated an initial schedule with only actor level scheduling completed
 * 
 * @author Johan Ersfolk
 */
@SuppressWarnings("all")
public class SchedulePrinter extends PromelaTemplate {
  private Network network;
  
  private final Set<Scheduler> actorSchedulers;
  
  public SchedulePrinter(final Set<Scheduler> actorSchedulers) {
    this.actorSchedulers = actorSchedulers;
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
    _builder.append("<superactorlist>");
    _builder.newLine();
    {
      for(final Scheduler actorScheduler : this.actorSchedulers) {
        CharSequence _superActor = this.superActor(actorScheduler);
        _builder.append(_superActor);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</superactorlist>");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence superActor(final Scheduler actorSched) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<superactor name=\"cluster_");
    String _name = actorSched.getActor().getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<actor name=\"");
    String _name_1 = actorSched.getActor().getName();
    _builder.append(_name_1, "\t");
    _builder.append("\"/>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _schedulerxml = this.schedulerxml(actorSched);
    _builder.append(_schedulerxml, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _schedulesxml = this.schedulesxml(actorSched);
    _builder.append(_schedulesxml, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("</superactor>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence schedulerxml(final Scheduler scheduler) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<fsm initial=\"");
    CharSequence _initStateName = this.initStateName(scheduler);
    _builder.append(_initStateName);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    {
      Set<Schedule> _schedules = scheduler.getSchedules();
      for(final Schedule sched : _schedules) {
        _builder.append("<transition action=\"");
        CharSequence _initStateName_1 = this.initStateName(sched);
        _builder.append(_initStateName_1);
        _builder.append("_");
        CharSequence _enablingActionName = this.enablingActionName(sched);
        _builder.append(_enablingActionName);
        _builder.append("\" dst=\"");
        CharSequence _endStateName = this.endStateName(sched);
        _builder.append(_endStateName);
        _builder.append("\" src=\"");
        CharSequence _initStateName_2 = this.initStateName(sched);
        _builder.append(_initStateName_2);
        _builder.append("\"/>");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</fsm>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence schedulesxml(final Scheduler scheduler) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Set<Schedule> _schedules = scheduler.getSchedules();
      for(final Schedule sched : _schedules) {
        _builder.append("<superaction name=\"");
        CharSequence _initStateName = this.initStateName(sched);
        _builder.append(_initStateName);
        _builder.append("_");
        CharSequence _enablingActionName = this.enablingActionName(sched);
        _builder.append(_enablingActionName);
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<guard></guard>");
        _builder.newLine();
        {
          List<Action> _sequence = sched.getSequence();
          for(final Action action : _sequence) {
            _builder.append("<iterand action=\"");
            _builder.append(action);
            _builder.append("\" actor=\"");
            String _name = scheduler.getActor().getName();
            _builder.append(_name);
            _builder.append("\" repetitions=\"1\"/>");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("</superaction>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence scheduledatarates(final Schedule schedule) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _enablingActionName = this.enablingActionName(schedule);
    _builder.append(_enablingActionName);
    _builder.newLineIfNotEmpty();
    {
      Set<String> _keySet = schedule.getPortPeeks().keySet();
      for(final String port : _keySet) {
        _builder.append(port);
        _builder.append("=");
        Object _get = schedule.getPortPeeks().get(port).get(0);
        _builder.append(_get);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Set<String> _keySet_1 = schedule.getPortReads().keySet();
      for(final String port_1 : _keySet_1) {
        _builder.append(port_1);
        _builder.append("=");
        int _size = schedule.getPortReads().get(port_1).size();
        _builder.append(_size);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Set<String> _keySet_2 = schedule.getPortWrites().keySet();
      for(final String port_2 : _keySet_2) {
        _builder.append(port_2);
        _builder.append("=");
        int _size_1 = schedule.getPortWrites().get(port_2).size();
        _builder.append(_size_1);
        _builder.newLineIfNotEmpty();
      }
    }
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
  
  public CharSequence initStateName(final Scheduler scheduler) {
    StringConcatenation _builder = new StringConcatenation();
    {
      State _initialState = scheduler.getInitialState();
      boolean _tripleNotEquals = (_initialState != null);
      if (_tripleNotEquals) {
        String _name = scheduler.getInitialState().getName();
        _builder.append(_name);
      } else {
        _builder.append("one_state");
      }
    }
    return _builder;
  }
}
