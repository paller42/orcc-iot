/**
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c.compa;

import java.util.List;
import java.util.Map;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Generate and print instance source file for COMPA backend.
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {
  private boolean printMainFunc;
  
  private boolean enableTest = false;
  
  public boolean setOptions(final Map<String, Object> options, final boolean printTop) {
    boolean _xblockexpression = false;
    {
      super.setOptions(options);
      _xblockexpression = this.printMainFunc = (!printTop);
    }
    return _xblockexpression;
  }
  
  @Override
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
        _builder.append("i += ");
        _builder.append(this.entityName, "\t");
        _builder.append("_outside_FSM_scheduler();");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty_1 = state.getOutgoing().isEmpty();
      if (_isEmpty_1) {
        _builder.append("\t");
        _builder.append("xil_printf(\"Stuck in state \\\"");
        String _name_1 = state.getName();
        _builder.append(_name_1, "\t");
        _builder.append("\\\" in the instance ");
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
  
  @Override
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
        _builder.append("if (");
        CharSequence _checkInputPattern = this.checkInputPattern(trans.getAction().getInputPattern());
        _builder.append(_checkInputPattern);
        String _name = trans.getAction().getScheduler().getName();
        _builder.append(_name);
        _builder.append("()) {");
        _builder.newLineIfNotEmpty();
        {
          Pattern _outputPattern = trans.getAction().getOutputPattern();
          boolean _tripleNotEquals = (_outputPattern != null);
          if (_tripleNotEquals) {
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
            _builder.append("goto finished;");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
        _builder.append("\t");
        String _name_2 = trans.getAction().getBody().getName();
        _builder.append(_name_2, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("i++;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("goto l_");
        String _name_3 = trans.getTarget().getName();
        _builder.append(_name_3, "\t");
        _builder.append(";");
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
    String _name_4 = state.getName();
    _builder.append(_name_4, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("goto finished;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  protected CharSequence printTransitionPattern(final Pattern pattern) {
    return null;
  }
  
  @Override
  protected CharSequence printActorScheduler() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        CharSequence _printFsm = this.printFsm();
        _builder.append(_printFsm);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("int ");
        _builder.append(this.entityName);
        _builder.append("_scheduler() {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("int i = 0;");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _printCallTokensFunctions = this.printCallTokensFunctions();
        _builder.append(_printCallTokensFunctions, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _printActionSchedulingLoop = this.printActionSchedulingLoop(this.actor.getActionsOutsideFsm());
        _builder.append(_printActionSchedulingLoop, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("finished:");
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
        _builder.append("\t");
        _builder.append("return i;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence printInitialize() {
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
    _builder.append("void ");
    _builder.append(this.entityName);
    _builder.append("_initialize() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("int i = 0;");
    _builder.newLine();
    {
      boolean _hasFsm = this.actor.hasFsm();
      if (_hasFsm) {
        _builder.append("\t");
        _builder.append("/* Set initial state to current FSM state */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("_FSM_state = my_state_");
        String _name = this.actor.getFsm().getInitialState().getName();
        _builder.append(_name, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getInitializes());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("\t");
        CharSequence _printActionsScheduling = this.printActionsScheduling(this.actor.getInitializes());
        _builder.append(_printActionsScheduling, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
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
    return _builder;
  }
  
  @Override
  protected CharSequence printFsm() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.actor.getActionsOutsideFsm().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("int ");
        _builder.append(this.entityName);
        _builder.append("_outside_FSM_scheduler() {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("int i = 0;");
        _builder.newLine();
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
        _builder.append("return i;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("int ");
    _builder.append(this.entityName);
    _builder.append("_scheduler() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("int i = 0;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    CharSequence _printCallTokensFunctions = this.printCallTokensFunctions();
    _builder.append(_printCallTokensFunctions, "\t");
    _builder.newLineIfNotEmpty();
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
    _builder.append("xil_printf(\"unknown state in ");
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
      final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
        @Override
        public Boolean apply(final Port it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Port> _filter = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function);
      for(final Port port_1 : _filter) {
        _builder.append("\t");
        _builder.append("write_end_");
        String _name_3 = port_1.getName();
        _builder.append(_name_3, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("return i;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  protected CharSequence printActionsScheduling(final Iterable<Action> actions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// Action loop");
    _builder.newLine();
    {
      boolean _hasElements = false;
      for(final Action action : actions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" else ", "");
        }
        _builder.append("if (");
        CharSequence _checkInputPattern = this.checkInputPattern(this.inputPattern);
        _builder.append(_checkInputPattern);
        String _name = action.getScheduler().getName();
        _builder.append(_name);
        _builder.append("()) {");
        _builder.newLineIfNotEmpty();
        {
          Pattern _outputPattern = action.getOutputPattern();
          boolean _tripleNotEquals = (_outputPattern != null);
          if (_tripleNotEquals) {
            _builder.append("\t");
            CharSequence _printOutputPattern = this.printOutputPattern(action.getOutputPattern());
            _builder.append(_printOutputPattern, "\t");
            _builder.newLineIfNotEmpty();
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
        _builder.append(_name_1, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("i++;");
        _builder.newLine();
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
  
  protected CharSequence printInitSD() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void initSD(){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("FRESULT rc = FR_OK;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("//\tXil_DCacheDisable();");
    _builder.newLine();
    _builder.append("//\tXil_ICacheDisable();");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Register volume work area, initialize device */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("rc = f_mount(0, &fatfs);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (rc != FR_OK) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("xil_printf(\"SD_INIT_FAIL: %d\\n\", rc);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("//\tXil_DCacheEnable();");
    _builder.newLine();
    _builder.append("//\tXil_ICacheEnable();");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printInitSharedMemory() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void initSharedMemory()");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("//    unsigned char * base = \t(unsigned char *) 0x30000000;");
    _builder.newLine();
    _builder.append("//    unsigned char * end = \t(unsigned char *) 0x300C0000;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("unsigned char * base = \t(unsigned char *) XPAR_SHARED_MEMORY_96KB_AXI_BRAM_CTRL_1_S_AXI_BASEADDR;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("unsigned char * end = \t(unsigned char *) XPAR_SHARED_MEMORY_96KB_AXI_BRAM_CTRL_1_S_AXI_HIGHADDR;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("unsigned char * ptr;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("int i = 0;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("xil_printf(\"Initializing memory range : %x - %x ...\", base, end);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("for(ptr= base; ptr < end; ptr++) {");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("unsigned char expected = i & 0xFF;");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("*(ptr) = expected;");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("if (expected != *ptr) {");
    _builder.newLine();
    _builder.append("    \t\t");
    _builder.append("xil_printf(\"Error at %x : write %x but read %x \\r\\n\", ptr, expected, *ptr);");
    _builder.newLine();
    _builder.append("    \t\t");
    _builder.append("break;");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("xil_printf(\"done!\\r\\n\");");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printTestInit() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void testInit(){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("init_platform();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("initSharedMemory();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("initSD();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("// Opening input trace files.");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("FRESULT rc = FR_OK;");
    _builder.newLine();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        _builder.append("    ");
        _builder.append("rc = f_open(&fil_");
        String _name = port.getName();
        _builder.append(_name, "    ");
        _builder.append(", traces_");
        String _name_1 = port.getName();
        _builder.append(_name_1, "    ");
        _builder.append(", FA_READ);");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("if (rc != FR_OK) {");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("\t");
        _builder.append("xil_printf(\"f_open failed: %d\\n\", rc);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("\t");
        _builder.append("exit(-1);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Opening output trace files.");
    _builder.newLine();
    {
      EList<Port> _outputs = this.actor.getOutputs();
      for(final Port port_1 : _outputs) {
        _builder.append("    ");
        _builder.append("rc = f_open(&fil_");
        String _name_2 = port_1.getName();
        _builder.append(_name_2, "    ");
        _builder.append(", traces_");
        String _name_3 = port_1.getName();
        _builder.append(_name_3, "    ");
        _builder.append(", FA_READ);");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("if (rc != FR_OK) {");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("\t");
        _builder.append("xil_printf(\"f_open failed: %d\\n\", rc);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("\t");
        _builder.append("exit(-1);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printTestScheduler() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void testScheduler(){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("uint i, nbWrittenTokens, nbReadTokens;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("unsigned int nbFreeSlots;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("unsigned int nbTokens;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("char value[16];");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("FRESULT rc = FR_OK;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int traceToken, fifoToken;");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    {
      EList<Port> _inputs = this.actor.getInputs();
      for(final Port port : _inputs) {
        _builder.append("\t");
        _builder.append("write_");
        String _name = port.getName();
        _builder.append(_name, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("nbFreeSlots = fifo_");
        CharSequence _doSwitch = this.doSwitch(port.getType());
        _builder.append(_doSwitch, "\t");
        _builder.append("_get_room(");
        _builder.append(this.entityName, "\t");
        _builder.append("_");
        String _name_1 = port.getName();
        _builder.append(_name_1, "\t");
        _builder.append(", 1);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("if(nbFreeSlots>0){");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("for (nbWrittenTokens = 0; nbWrittenTokens < nbFreeSlots; nbWrittenTokens++) {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("i = 0;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("memset(value, 0, sizeof(value));");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("do{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("rc = f_read(&fil_");
        String _name_2 = port.getName();
        _builder.append(_name_2, "\t\t\t\t");
        _builder.append(", &value[i], 1, 0);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("if(rc != FR_OK){");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t\t");
        _builder.append("xil_printf(\"f_read failed: %d\\n\", rc);");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t\t");
        _builder.append("exit(-1);");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("}while(value[i++] != \'\\n\');");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("fifo_");
        CharSequence _doSwitch_1 = this.doSwitch(port.getType());
        _builder.append(_doSwitch_1, "\t\t\t");
        _builder.append("_write_1(");
        _builder.append(this.entityName, "\t\t\t");
        _builder.append("_");
        String _name_3 = port.getName();
        _builder.append(_name_3, "\t\t\t");
        _builder.append(", atoi(value));");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("index_");
        String _name_4 = port.getName();
        _builder.append(_name_4, "\t\t");
        _builder.append(" += nbWrittenTokens;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("write_end_");
        String _name_5 = port.getName();
        _builder.append(_name_5, "\t\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    {
      EList<Port> _outputs = this.actor.getOutputs();
      for(final Port port_1 : _outputs) {
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("read_");
        String _name_6 = port_1.getName();
        _builder.append(_name_6, "\t\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("nbTokens = fifo_");
        CharSequence _doSwitch_2 = this.doSwitch(port_1.getType());
        _builder.append(_doSwitch_2, "\t\t");
        _builder.append("_get_num_tokens(");
        _builder.append(this.entityName, "\t\t");
        _builder.append("_");
        String _name_7 = port_1.getName();
        _builder.append(_name_7, "\t\t");
        _builder.append(", 0);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("if(nbTokens>0){");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("for (nbReadTokens = 0; nbReadTokens < nbTokens; nbReadTokens++) {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("i = 0;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("memset(value, 0, sizeof(value));");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("do{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t\t");
        _builder.append("rc = f_read(&fil_");
        String _name_8 = port_1.getName();
        _builder.append(_name_8, "\t\t\t\t\t");
        _builder.append(", &value[i], 1, 0);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t\t\t");
        _builder.append("if(rc != FR_OK){");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t\t\t");
        _builder.append("xil_printf(\"f_read failed: %d\\n\", rc);");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t\t\t");
        _builder.append("exit(-1);");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("}while(value[i++] != \'\\n\');");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("traceToken = atoi(value);");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("fifoToken = fifo_");
        CharSequence _doSwitch_3 = this.doSwitch(port_1.getType());
        _builder.append(_doSwitch_3, "\t\t\t\t");
        _builder.append("_read_1(");
        _builder.append(this.entityName, "\t\t\t\t");
        _builder.append("_");
        String _name_9 = port_1.getName();
        _builder.append(_name_9, "\t\t\t\t");
        _builder.append(", 0);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("if(fifoToken != traceToken){");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t\t");
        _builder.append("xil_printf(\"Error at token %d: %d != %d\\n\", nbReadTokens, fifoToken, traceToken);");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t\t");
        _builder.append("exit(-1);");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("index_");
        String _name_10 = port_1.getName();
        _builder.append(_name_10, "\t\t\t");
        _builder.append(" += nbReadTokens;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("// index_IN += (64 * nbTokens);");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("read_end_");
        String _name_11 = port_1.getName();
        _builder.append(_name_11, "\t\t\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence printMain() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("int main(int argc, char *argv[]) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int i;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int stop = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      if (this.enableTest) {
        _builder.append("testInit();");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append(this.entityName, "\t");
    _builder.append("_initialize();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("while(1) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("i = 0;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    {
      if (this.enableTest) {
        _builder.append("\t\t");
        _builder.append("testScheduler();");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("i += ");
    _builder.append(this.entityName, "\t\t");
    _builder.append("_scheduler();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("stop = stop || (i == 0);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("xil_printf(\"End of simulation !\\n\");");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return compareErrors;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
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
    _builder.append(" = (*");
    CharSequence _fullName = this.fullName(port);
    _builder.append(_fullName, "\t");
    _builder.append("->write_ind);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("numFree_");
    String _name_2 = port.getName();
    _builder.append(_name_2, "\t");
    _builder.append(" = index_");
    String _name_3 = port.getName();
    _builder.append(_name_3, "\t");
    _builder.append(" + fifo_");
    CharSequence _doSwitch = this.doSwitch(port.getType());
    _builder.append(_doSwitch, "\t");
    _builder.append("_get_room(");
    CharSequence _fullName_1 = this.fullName(port);
    _builder.append(_fullName_1, "\t");
    _builder.append(", NUM_READERS_");
    String _name_4 = port.getName();
    _builder.append(_name_4, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static void write_end_");
    String _name_5 = port.getName();
    _builder.append(_name_5);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("(*");
    CharSequence _fullName_2 = this.fullName(port);
    _builder.append(_fullName_2, "\t");
    _builder.append("->write_ind) = index_");
    String _name_6 = port.getName();
    _builder.append(_name_6, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence testReadTokensFunctions(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static void read_");
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
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static void read_end_");
    String _name_4 = port.getName();
    _builder.append(_name_4);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _fullName_2 = this.fullName(port);
    _builder.append(_fullName_2, "\t");
    _builder.append("->read_inds[0] = index_");
    String _name_5 = port.getName();
    _builder.append(_name_5, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getContent() {
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
    _builder.newLine();
    {
      if (this.printMainFunc) {
        _builder.append("#include \"fifoAllocations.h\"");
        _builder.newLine();
      } else {
        _builder.append("#include \"fifo.h\"");
        _builder.newLine();
      }
    }
    _builder.append("#include \"util.h\"");
    _builder.newLine();
    _builder.append("#include \"dataflow.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("extern void xil_printf( const char *ctrl1, ...);");
    _builder.newLine();
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
    _builder.newLine();
    {
      if ((this.printMainFunc != true)) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Instance");
        _builder.newLine();
        _builder.append("extern actor_t ");
        _builder.append(this.entityName);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getInputs());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Input FIFOs");
        _builder.newLine();
        {
          if ((this.printMainFunc != true)) {
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
                _builder.append(" fifo_");
                CharSequence _doSwitch = this.doSwitch(port.getType());
                _builder.append(_doSwitch);
                _builder.append("_t *");
                CharSequence _fullName = this.fullName(port);
                _builder.append(_fullName);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Input Fifo control variables");
        _builder.newLine();
        {
          EList<Port> _inputs_1 = this.actor.getInputs();
          for(final Port port_1 : _inputs_1) {
            _builder.append("static unsigned int index_");
            String _name = port_1.getName();
            _builder.append(_name);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("static unsigned int numTokens_");
            String _name_1 = port_1.getName();
            _builder.append(_name_1);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("#define SIZE_");
            String _name_2 = port_1.getName();
            _builder.append(_name_2);
            _builder.append(" ");
            Integer _sizeOrDefaultSize = this.sizeOrDefaultSize(this.incomingPortMap.get(port_1));
            _builder.append(_sizeOrDefaultSize);
            _builder.newLineIfNotEmpty();
            _builder.append("#define tokens_");
            String _name_3 = port_1.getName();
            _builder.append(_name_3);
            _builder.append(" \t");
            CharSequence _fullName_1 = this.fullName(port_1);
            _builder.append(_fullName_1);
            _builder.append("->contents");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Predecessors");
        _builder.newLine();
        {
          EList<Port> _inputs_2 = this.actor.getInputs();
          for(final Port port_2 : _inputs_2) {
            {
              Connection _get_1 = this.incomingPortMap.get(port_2);
              boolean _tripleNotEquals_1 = (_get_1 != null);
              if (_tripleNotEquals_1) {
                _builder.append("extern actor_t ");
                String _label = this.incomingPortMap.get(port_2).getSource().getLabel();
                _builder.append(_label);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
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
          if ((this.printMainFunc != true)) {
            {
              final Function1<Port, Boolean> _function_1 = new Function1<Port, Boolean>() {
                @Override
                public Boolean apply(final Port it) {
                  boolean _isNative = it.isNative();
                  return Boolean.valueOf((!_isNative));
                }
              };
              Iterable<Port> _filter = IterableExtensions.<Port>filter(this.actor.getOutputs(), _function_1);
              for(final Port port_3 : _filter) {
                _builder.append("extern fifo_");
                CharSequence _doSwitch_1 = this.doSwitch(port_3.getType());
                _builder.append(_doSwitch_1);
                _builder.append("_t *");
                CharSequence _fullName_2 = this.fullName(port_3);
                _builder.append(_fullName_2);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
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
          for(final Port port_4 : _filter_1) {
            _builder.append("static unsigned int index_");
            String _name_4 = port_4.getName();
            _builder.append(_name_4);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("static unsigned int numFree_");
            String _name_5 = port_4.getName();
            _builder.append(_name_5);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("#define NUM_READERS_");
            String _name_6 = port_4.getName();
            _builder.append(_name_6);
            _builder.append(" ");
            int _size = this.outgoingPortMap.get(port_4).size();
            _builder.append(_size);
            _builder.newLineIfNotEmpty();
            _builder.append("#define SIZE_");
            String _name_7 = port_4.getName();
            _builder.append(_name_7);
            _builder.append(" ");
            Integer _sizeOrDefaultSize_1 = this.sizeOrDefaultSize(this.outgoingPortMap.get(port_4).get(0));
            _builder.append(_sizeOrDefaultSize_1);
            _builder.newLineIfNotEmpty();
            _builder.append("#define tokens_");
            String _name_8 = port_4.getName();
            _builder.append(_name_8);
            _builder.append(" ");
            CharSequence _fullName_3 = this.fullName(port_4);
            _builder.append(_fullName_3);
            _builder.append("->contents");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Successors");
        _builder.newLine();
        {
          EList<Port> _outputs = this.actor.getOutputs();
          for(final Port port_5 : _outputs) {
            {
              List<Connection> _get_2 = this.outgoingPortMap.get(port_5);
              for(final Connection successor : _get_2) {
                _builder.append("extern actor_t ");
                String _label_1 = successor.getTarget().getLabel();
                _builder.append(_label_1);
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      if (this.enableTest) {
        _builder.append("// Including header for actor test");
        _builder.newLine();
        _builder.append("#include \"decoder_parser_parseheaders_test.h\"");
        _builder.newLine();
      }
    }
    _builder.newLine();
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
                    String _name_9 = arg.getVariable().getName();
                    _builder.append(_name_9);
                    String _printArrayIndexes = this.printArrayIndexes(arg.getValue().getType().getDimensionsExpr());
                    _builder.append(_printArrayIndexes);
                    _builder.append(" = ");
                    CharSequence _doSwitch_2 = this.doSwitch(arg.getValue());
                    _builder.append(_doSwitch_2);
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  } else {
                    _builder.append("#define ");
                    String _name_10 = arg.getVariable().getName();
                    _builder.append(_name_10);
                    _builder.append(" ");
                    CharSequence _doSwitch_3 = this.doSwitch(arg.getValue());
                    _builder.append(_doSwitch_3);
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
    _builder.newLine();
    {
      boolean _isNullOrEmpty_2 = IterableExtensions.isNullOrEmpty(this.actor.getStateVars());
      boolean _not_2 = (!_isNullOrEmpty_2);
      if (_not_2) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// State variables of the actor");
        _builder.newLine();
        {
          EList<Var> _stateVars = this.actor.getStateVars();
          for(final Var variable_1 : _stateVars) {
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
            String _name_11 = state.getName();
            _builder.append(_name_11, "\t");
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
            String _name_12 = state_1.getName();
            _builder.append(_name_12, "\t");
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
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Token functions");
    _builder.newLine();
    {
      EList<Port> _inputs_3 = this.actor.getInputs();
      for(final Port port_6 : _inputs_3) {
        CharSequence _readTokensFunctions = this.readTokensFunctions(port_6);
        _builder.append(_readTokensFunctions);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      Iterable<? extends Port> _notNative = this.getNotNative(this.actor.getOutputs());
      for(final Port port_7 : _notNative) {
        CharSequence _writeTokensFunctions = this.writeTokensFunctions(port_7);
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
      EList<Action> _actions = this.actor.getActions();
      for(final Action action : _actions) {
        CharSequence _print_1 = this.print(action);
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
    _builder.newLine();
    {
      if (this.printMainFunc) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// main");
        _builder.newLine();
        CharSequence _printMain = this.printMain();
        _builder.append(_printMain);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence getTestContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"ff.h\"");
    _builder.newLine();
    _builder.append("#include \"xparameters.h\"");
    _builder.newLine();
    _builder.append("#include \"platform.h\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static FATFS fatfs;");
    _builder.newLine();
    _builder.newLine();
    {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(this.actor.getInputs());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Input FIFOs");
        _builder.newLine();
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Input Fifo test control variables");
        _builder.newLine();
        {
          EList<Port> _inputs = this.actor.getInputs();
          for(final Port port : _inputs) {
            _builder.append("static unsigned int numFree_");
            String _name = port.getName();
            _builder.append(_name);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("#define NUM_READERS_");
            String _name_1 = port.getName();
            _builder.append(_name_1);
            _builder.append("\t1");
            _builder.newLineIfNotEmpty();
            _builder.append("#define traces_");
            String _name_2 = port.getName();
            _builder.append(_name_2);
            _builder.append(" \t\"traces/");
            String _name_3 = port.getName();
            _builder.append(_name_3);
            _builder.append(".txt\"");
            _builder.newLineIfNotEmpty();
            _builder.append("static \tFIL\t\t\t\t\tfil_");
            String _name_4 = port.getName();
            _builder.append(_name_4);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
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
        _builder.append("////////////////////////////////////////////////////////////////////////////////");
        _builder.newLine();
        _builder.append("// Output Fifo control variables");
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
          for(final Port port_1 : _filter) {
            _builder.append("static unsigned int numTokens_");
            String _name_5 = port_1.getName();
            _builder.append(_name_5);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("#define traces_");
            String _name_6 = port_1.getName();
            _builder.append(_name_6);
            _builder.append(" \t\"traces/");
            String _name_7 = port_1.getName();
            _builder.append(_name_7);
            _builder.append(".txt\"");
            _builder.newLineIfNotEmpty();
            _builder.append("static \tFIL\t\t\t\t\tfil_");
            String _name_8 = port_1.getName();
            _builder.append(_name_8);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("////////////////////////////////////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("// Token functions for test");
    _builder.newLine();
    {
      EList<Port> _inputs_1 = this.actor.getInputs();
      for(final Port port_2 : _inputs_1) {
        CharSequence _writeTokensFunctions = this.writeTokensFunctions(port_2);
        _builder.append(_writeTokensFunctions);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      Iterable<? extends Port> _notNative = this.getNotNative(this.actor.getOutputs());
      for(final Port port_3 : _notNative) {
        CharSequence _testReadTokensFunctions = this.testReadTokensFunctions(port_3);
        _builder.append(_testReadTokensFunctions);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    CharSequence _printInitSD = this.printInitSD();
    _builder.append(_printInitSD);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _printInitSharedMemory = this.printInitSharedMemory();
    _builder.append(_printInitSharedMemory);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _printTestInit = this.printTestInit();
    _builder.append(_printTestInit);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _printTestScheduler = this.printTestScheduler();
    _builder.append(_printTestScheduler);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
}
