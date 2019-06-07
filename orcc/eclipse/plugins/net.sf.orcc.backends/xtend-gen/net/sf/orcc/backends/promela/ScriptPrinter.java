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

import net.sf.orcc.backends.promela.PromelaTemplate;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generated an initial schedule with only actor level scheduling completed
 * 
 * @author Johan Ersfolk
 */
@SuppressWarnings("all")
public class ScriptPrinter extends PromelaTemplate {
  private Network network;
  
  public Network setNetwork(final Network network) {
    return this.network = network;
  }
  
  public CharSequence getScriptFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Generated from \"");
    String _name = this.network.getName();
    _builder.append(_name);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("from pylibs.modelchecking import ModelChecker");
    _builder.newLine();
    _builder.append("from pylibs.scheduler import SchedulerXML, FSM, Transition");
    _builder.newLine();
    _builder.append("from pylibs.interaction import UserArgs");
    _builder.newLine();
    _builder.append("from pylibs.schedconfig import Configuration, RunConfiguration, StateDescription, ChannelConfigXML");
    _builder.newLine();
    _builder.newLine();
    _builder.append("uargs = UserArgs()");
    _builder.newLine();
    _builder.append("uargs.parseargs()");
    _builder.newLine();
    _builder.newLine();
    _builder.append("actors=");
    final Function1<Actor, CharSequence> _function = new Function1<Actor, CharSequence>() {
      @Override
      public CharSequence apply(final Actor it) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\"");
        String _simpleName = it.getSimpleName();
        _builder.append(_simpleName);
        _builder.append("\"");
        return _builder.toString();
      }
    };
    String _join = IterableExtensions.<Actor>join(this.network.getAllActors(), "[", ", ", "]", _function);
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("conf=Configuration(\'config_");
    String _simpleName = this.network.getSimpleName();
    _builder.append(_simpleName);
    _builder.append("\', \'config.txt\')");
    _builder.newLineIfNotEmpty();
    _builder.append("conf.loadconfiguration(actors)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("if uargs.removeactor is not None:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("conf.removeactor(uargs.removeactor)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("conf.saveconfiguration()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("exit()");
    _builder.newLine();
    _builder.newLine();
    _builder.append("if uargs.setleader is not None:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("conf.setleader(uargs.setleader)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("ccx=ChannelConfigXML(\'schedule_info_");
    String _simpleName_1 = this.network.getSimpleName();
    _builder.append(_simpleName_1);
    _builder.append(".xml\')");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("rc=RunConfiguration(conf)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("mc = ModelChecker()");
    _builder.newLine();
    _builder.newLine();
    _builder.append("if uargs.configure:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("scheduler=SchedulerXML(\'schedule_");
    String _simpleName_2 = this.network.getSimpleName();
    _builder.append(_simpleName_2, "\t");
    _builder.append(".xml\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("fsm=scheduler.getactorfsm(conf.leader)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fsm.printfsm()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fsm.savefsm(\'config_");
    String _simpleName_3 = this.network.getSimpleName();
    _builder.append(_simpleName_3, "\t");
    _builder.append("\', \'scheduler.txt\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("rc.confinitsearch()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("mc.simulate(\'main_");
    String _simpleName_4 = this.network.getSimpleName();
    _builder.append(_simpleName_4, "\t");
    _builder.append(".pml\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("endstate = mc.endstate");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("sd=StateDescription()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("sd.fromstring(endstate)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("sd.savestate(\'config_");
    String _simpleName_5 = this.network.getSimpleName();
    _builder.append(_simpleName_5, "\t");
    _builder.append("\', \'s0.txt\')");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("if uargs.runcheckerid is not None:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fsm=FSM()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fsm.loadfsm(\'config_");
    String _simpleName_6 = this.network.getSimpleName();
    _builder.append(_simpleName_6, "\t");
    _builder.append("\', \'scheduler.txt\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("fsm.printfsm()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("sched=fsm.gettransition(uargs.runcheckerid)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if sched.nsrc==\'?\':");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("print (\'Error: The starting state for the requested schedule is still unknown\')");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("exit()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("print (\"\\nRunning schedule search for:\\n\"+sched.tostring())");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("statefile=sched.nsrc+\'.txt\'");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("sd=StateDescription()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("sd.loadstate(\'config_");
    String _simpleName_7 = this.network.getSimpleName();
    _builder.append(_simpleName_7, "\t");
    _builder.append("\', statefile)");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("inputs=ccx.findinputs(conf)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if uargs.findspecificstate is not None:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("next_state_file=uargs.findspecificstate+\'.txt\'");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("nsd=StateDescription()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("nsd.loadstate(\'config_");
    String _simpleName_8 = this.network.getSimpleName();
    _builder.append(_simpleName_8, "\t\t");
    _builder.append("\', next_state_file)");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("rc.configure(sd, inputs, sched.src, sched.action, sched.dst, nsd.getfsmstates(), sd.getinitializers())");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("else:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("rc.configure(sd, inputs, sched.src, sched.action, sched.dst, {}, sd.getinitializers())");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("conf.setschedule(uargs.runcheckerid)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("if uargs.runchecker:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("mc.generatemc(\'main_");
    String _simpleName_9 = this.network.getSimpleName();
    _builder.append(_simpleName_9, "\t");
    _builder.append(".pml\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("mc.compilemc(uargs.shortestPath)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("mc.runmc(uargs.shortestPath)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if mc.tracefound:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("print (\"\\n\\nSchedule found.\")");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("fsm=FSM()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("fsm.loadfsm(\'config_");
    String _simpleName_10 = this.network.getSimpleName();
    _builder.append(_simpleName_10, "\t\t");
    _builder.append("\', \'scheduler.txt\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("sched=fsm.gettransition(conf.schedule)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("mc.simulatetrail(\'main_");
    String _simpleName_11 = this.network.getSimpleName();
    _builder.append(_simpleName_11, "\t\t");
    _builder.append(".pml\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("nsd=StateDescription()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("nsd.fromstring(mc.endstate)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("nsd.setfilter(\'schedule_info_");
    String _simpleName_12 = this.network.getSimpleName();
    _builder.append(_simpleName_12, "\t\t");
    _builder.append(".xml\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("ns=nsd.isequalto(\'config_");
    String _simpleName_13 = this.network.getSimpleName();
    _builder.append(_simpleName_13, "\t\t");
    _builder.append("\', fsm.getnstates(sched.dst))");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("if ns is None:");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("ns=fsm.getnewstatename()");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("fsm.addnewnstate(sched.dst, ns)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("nsd.savestate(\'config_");
    String _simpleName_14 = this.network.getSimpleName();
    _builder.append(_simpleName_14, "\t\t");
    _builder.append("\', ns+\'.txt\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("sched.ndst=ns");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("sched.sequence=mc.schedxml");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("fsm.savefsm(\'config_");
    String _simpleName_15 = this.network.getSimpleName();
    _builder.append(_simpleName_15, "\t\t");
    _builder.append("\', \'scheduler.txt\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("fsm.printfsm()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("else:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("print (\"\\n\\nSchedule was not found!!\")");
    _builder.newLine();
    _builder.newLine();
    _builder.append("if uargs.printXML:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fsm=FSM()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fsm.loadfsm(\'config_");
    String _simpleName_16 = this.network.getSimpleName();
    _builder.append(_simpleName_16, "\t");
    _builder.append("\', \'scheduler.txt\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("fsm.savefsm(\'config_");
    String _simpleName_17 = this.network.getSimpleName();
    _builder.append(_simpleName_17, "\t");
    _builder.append("\', \'scheduler.txt\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("scheduler=SchedulerXML(\'schedule_");
    String _simpleName_18 = this.network.getSimpleName();
    _builder.append(_simpleName_18, "\t");
    _builder.append(".xml\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("scheduler.savenewfsm(fsm, conf, \'schedule_");
    String _simpleName_19 = this.network.getSimpleName();
    _builder.append(_simpleName_19, "\t");
    _builder.append(".xml\', \'config_");
    String _simpleName_20 = this.network.getSimpleName();
    _builder.append(_simpleName_20, "\t");
    _builder.append("\')");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.newLine();
    _builder.append("conf.printconfiguration()");
    _builder.newLine();
    _builder.append("conf.saveconfiguration()");
    _builder.newLine();
    return _builder;
  }
}
