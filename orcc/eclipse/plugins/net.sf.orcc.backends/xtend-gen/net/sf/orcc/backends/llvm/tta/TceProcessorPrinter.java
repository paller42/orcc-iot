/**
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta;

import java.util.Arrays;
import net.sf.orcc.backends.llvm.tta.TTAPrinter;
import net.sf.orcc.backends.llvm.tta.architecture.Bus;
import net.sf.orcc.backends.llvm.tta.architecture.Element;
import net.sf.orcc.backends.llvm.tta.architecture.ExprBinary;
import net.sf.orcc.backends.llvm.tta.architecture.ExprFalse;
import net.sf.orcc.backends.llvm.tta.architecture.ExprTrue;
import net.sf.orcc.backends.llvm.tta.architecture.ExprUnary;
import net.sf.orcc.backends.llvm.tta.architecture.FuPort;
import net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Guard;
import net.sf.orcc.backends.llvm.tta.architecture.Implementation;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Operation;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.Reads;
import net.sf.orcc.backends.llvm.tta.architecture.RegisterFile;
import net.sf.orcc.backends.llvm.tta.architecture.Resource;
import net.sf.orcc.backends.llvm.tta.architecture.Segment;
import net.sf.orcc.backends.llvm.tta.architecture.ShortImmediate;
import net.sf.orcc.backends.llvm.tta.architecture.Socket;
import net.sf.orcc.backends.llvm.tta.architecture.Term;
import net.sf.orcc.backends.llvm.tta.architecture.TermBool;
import net.sf.orcc.backends.llvm.tta.architecture.TermUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Writes;
import net.sf.orcc.util.OrccLogger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class TceProcessorPrinter extends TTAPrinter {
  private EMap<String, Implementation> hwDb;
  
  private final int MAX_ADDRESS = Integer.MAX_VALUE;
  
  public EMap<String, Implementation> setHwDb(final EMap<String, Implementation> hwDb) {
    return this.hwDb = hwDb;
  }
  
  public CharSequence getAdf(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    _builder.newLine();
    _builder.append("<adf version=\"1.7\">");
    _builder.newLine();
    {
      EList<Bus> _buses = processor.getBuses();
      for(final Bus bus : _buses) {
        _builder.append("\t");
        CharSequence _adf = this.getAdf(bus);
        _builder.append(_adf, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Socket> _sockets = processor.getSockets();
      for(final Socket socket : _sockets) {
        _builder.append("\t");
        CharSequence _adf_1 = this.getAdf(socket);
        _builder.append(_adf_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<FunctionUnit> _functionUnits = processor.getFunctionUnits();
      for(final FunctionUnit fu : _functionUnits) {
        _builder.append("\t");
        CharSequence _adf_2 = this.getAdf(fu);
        _builder.append(_adf_2, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<RegisterFile> _registerFiles = processor.getRegisterFiles();
      for(final RegisterFile rf : _registerFiles) {
        _builder.append("\t");
        CharSequence _adf_3 = this.getAdf(rf);
        _builder.append(_adf_3, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _adf_4 = this.getAdf(processor.getROM(), processor, false);
    _builder.append(_adf_4, "\t");
    _builder.newLineIfNotEmpty();
    {
      EList<Memory> _localRAMs = processor.getLocalRAMs();
      for(final Memory ram : _localRAMs) {
        _builder.append("\t");
        CharSequence _adf_5 = this.getAdf(ram, processor, true);
        _builder.append(_adf_5, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Memory> _sharedRAMs = processor.getSharedRAMs();
      for(final Memory ram_1 : _sharedRAMs) {
        _builder.append("\t");
        CharSequence _adf_6 = this.getAdf(ram_1, processor, true);
        _builder.append(_adf_6, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    GlobalControlUnit _gcu = processor.getGcu();
    CharSequence _adf_7 = null;
    if (_gcu!=null) {
      _adf_7=this.getAdf(_gcu);
    }
    _builder.append(_adf_7, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("</adf>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getAdf(final Bus bus) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<bus name=\"");
    String _name = bus.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<width>");
    int _width = bus.getWidth();
    _builder.append(_width, "\t");
    _builder.append("</width>");
    _builder.newLineIfNotEmpty();
    {
      EList<Guard> _guards = bus.getGuards();
      for(final Guard guard : _guards) {
        _builder.append("\t");
        _builder.append("<guard>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        CharSequence _adfExpr = this.getAdfExpr(guard);
        _builder.append(_adfExpr, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</guard>");
        _builder.newLine();
      }
    }
    {
      EList<Segment> _segments = bus.getSegments();
      for(final Segment segment : _segments) {
        _builder.append("\t");
        CharSequence _adf = this.getAdf(segment);
        _builder.append(_adf, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _adf_1 = this.getAdf(bus.getShortImmediate());
    _builder.append(_adf_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("</bus>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence _getAdfExpr(final ExprBinary expr) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isAnd = expr.isAnd();
      if (_isAnd) {
        _builder.append("<and-expr>");
      } else {
        _builder.append("<or-expr>");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _adfExpr = this.getAdfExpr(expr.getE1());
    _builder.append(_adfExpr, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _adfExpr_1 = this.getAdfExpr(expr.getE2());
    _builder.append(_adfExpr_1, "\t");
    _builder.newLineIfNotEmpty();
    {
      boolean _isAnd_1 = expr.isAnd();
      if (_isAnd_1) {
        _builder.append("</and-expr>");
      } else {
        _builder.append("</or-expr>");
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _getAdfExpr(final ExprUnary expr) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isSimple = expr.isSimple();
      if (_isSimple) {
        _builder.append("<simple-expr>");
      } else {
        _builder.append("<inverted-expr>");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _adfTerm = this.getAdfTerm(expr.getTerm());
    _builder.append(_adfTerm, "\t");
    _builder.newLineIfNotEmpty();
    {
      boolean _isSimple_1 = expr.isSimple();
      if (_isSimple_1) {
        _builder.append("</simple-expr>");
      } else {
        _builder.append("</inverted-expr>");
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _getAdfExpr(final ExprFalse object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<always-false/>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence _getAdfExpr(final ExprTrue object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<always-true/>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence _getAdfTerm(final TermBool term) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<bool>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<name>");
    String _name = term.getRegister().getName();
    _builder.append(_name, "\t");
    _builder.append("</name>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<index>");
    int _index = term.getIndex();
    _builder.append(_index, "\t");
    _builder.append("</index>");
    _builder.newLineIfNotEmpty();
    _builder.append("</bool>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence _getAdfTerm(final TermUnit term) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<unit>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<name>");
    String _name = term.getFunctionUnit().getName();
    _builder.append(_name, "\t");
    _builder.append("</name>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<port>");
    String _name_1 = term.getPort().getName();
    _builder.append(_name_1, "\t");
    _builder.append("</port>");
    _builder.newLineIfNotEmpty();
    _builder.append("</unit>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence _getAdfElement(final Reads element) {
    StringConcatenation _builder = new StringConcatenation();
    EObject _eContainer = element.eContainer();
    final Operation op = ((Operation) _eContainer);
    _builder.newLineIfNotEmpty();
    _builder.append("<reads name=\"");
    Integer _get = op.getPortToIndexMap().get(element.getPort());
    _builder.append(_get);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<start-cycle>");
    int _startCycle = element.getStartCycle();
    _builder.append(_startCycle, "\t");
    _builder.append("</start-cycle>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<cycles>");
    int _cycles = element.getCycles();
    _builder.append(_cycles, "\t");
    _builder.append("</cycles>");
    _builder.newLineIfNotEmpty();
    _builder.append("</reads>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence _getAdfElement(final Writes element) {
    StringConcatenation _builder = new StringConcatenation();
    EObject _eContainer = element.eContainer();
    final Operation op = ((Operation) _eContainer);
    _builder.newLineIfNotEmpty();
    _builder.append("<writes name=\"");
    Integer _get = op.getPortToIndexMap().get(element.getPort());
    _builder.append(_get);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<start-cycle>");
    int _startCycle = element.getStartCycle();
    _builder.append(_startCycle, "\t");
    _builder.append("</start-cycle>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<cycles>");
    int _cycles = element.getCycles();
    _builder.append(_cycles, "\t");
    _builder.append("</cycles>");
    _builder.newLineIfNotEmpty();
    _builder.append("</writes>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence _getAdfElement(final Resource element) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<resource name=\"");
    String _name = element.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<start-cycle>");
    int _startCycle = element.getStartCycle();
    _builder.append(_startCycle, "\t");
    _builder.append("</start-cycle>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<cycles>");
    int _cycles = element.getCycles();
    _builder.append(_cycles, "\t");
    _builder.append("</cycles>");
    _builder.newLineIfNotEmpty();
    _builder.append("</resource>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getAdf(final FuPort port, final boolean needWidth, final boolean isSpecial) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    {
      if (isSpecial) {
        _builder.append("special-");
      }
    }
    _builder.append("port name=\"");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    Socket _inputSocket = port.getInputSocket();
    CharSequence _connect = null;
    if (_inputSocket!=null) {
      _connect=this.connect(_inputSocket);
    }
    _builder.append(_connect, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    Socket _outputSocket = port.getOutputSocket();
    CharSequence _connect_1 = null;
    if (_outputSocket!=null) {
      _connect_1=this.connect(_outputSocket);
    }
    _builder.append(_connect_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    {
      if (needWidth) {
        _builder.append("<width>");
        int _width = port.getWidth();
        _builder.append(_width, "\t");
        _builder.append("</width>");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    {
      boolean _isTrigger = port.isTrigger();
      if (_isTrigger) {
        _builder.append("<triggers/>");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    {
      boolean _isOpcodeSelector = port.isOpcodeSelector();
      if (_isOpcodeSelector) {
        _builder.append("<sets-opcode/>");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("</");
    {
      if (isSpecial) {
        _builder.append("special-");
      }
    }
    _builder.append("port>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence getAdf(final FunctionUnit fu) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<function-unit name=\"");
    String _name = fu.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    {
      EList<FuPort> _ports = fu.getPorts();
      for(final FuPort port : _ports) {
        _builder.append("\t");
        CharSequence _adf = this.getAdf(port, true, false);
        _builder.append(_adf, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Operation> _operations = fu.getOperations();
      for(final Operation operation : _operations) {
        _builder.append("\t");
        CharSequence _adf_1 = this.getAdf(operation, false);
        _builder.append(_adf_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _connect = this.connect(fu.getAddressSpace());
    _builder.append(_connect, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("</function-unit>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getAdf(final GlobalControlUnit gcu) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<global-control-unit name=\"");
    String _name = gcu.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    {
      EList<FuPort> _ports = gcu.getPorts();
      for(final FuPort port : _ports) {
        _builder.append("\t");
        CharSequence _adf = this.getAdf(port, true, false);
        _builder.append(_adf, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _adf_1 = this.getAdf(gcu.getReturnAddress(), true, true);
    _builder.append(_adf_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<return-address>");
    String _name_1 = gcu.getReturnAddress().getName();
    _builder.append(_name_1, "\t");
    _builder.append("</return-address>");
    _builder.newLineIfNotEmpty();
    {
      EList<Operation> _operations = gcu.getOperations();
      for(final Operation operation : _operations) {
        _builder.append("\t");
        CharSequence _adf_2 = this.getAdf(operation, true);
        _builder.append(_adf_2, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("<address-space>");
    String _name_2 = gcu.getAddressSpace().getName();
    _builder.append(_name_2, "\t");
    _builder.append("</address-space>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<delay-slots>");
    int _delaySlots = gcu.getDelaySlots();
    _builder.append(_delaySlots, "\t");
    _builder.append("</delay-slots>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<guard-latency>");
    int _guardLatency = gcu.getGuardLatency();
    _builder.append(_guardLatency, "\t");
    _builder.append("</guard-latency>");
    _builder.newLineIfNotEmpty();
    _builder.append("</global-control-unit>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getAdf(final Operation operation, final boolean isControl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    {
      if (isControl) {
        _builder.append("ctrl-");
      }
    }
    _builder.append("operation>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<name>");
    String _name = operation.getName();
    _builder.append(_name, "\t");
    _builder.append("</name>");
    _builder.newLineIfNotEmpty();
    {
      EList<FuPort> _ports = operation.getPorts();
      for(final FuPort port : _ports) {
        _builder.append("\t");
        _builder.append("<bind name=\"");
        Integer _get = operation.getPortToIndexMap().get(port);
        _builder.append(_get, "\t");
        _builder.append("\">");
        String _name_1 = port.getName();
        _builder.append(_name_1, "\t");
        _builder.append("</bind>");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("<pipeline>");
    _builder.newLine();
    {
      EList<Element> _pipeline = operation.getPipeline();
      for(final Element element : _pipeline) {
        _builder.append("\t\t");
        CharSequence _adfElement = this.getAdfElement(element);
        _builder.append(_adfElement, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("</pipeline>");
    _builder.newLine();
    _builder.append("</");
    {
      if (isControl) {
        _builder.append("ctrl-");
      }
    }
    _builder.append("operation>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence getAdf(final RegisterFile rf) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<register-file name=\"");
    String _name = rf.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<type>normal</type>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<size>");
    int _size = rf.getSize();
    _builder.append(_size, "\t");
    _builder.append("</size>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<width>");
    int _width = rf.getWidth();
    _builder.append(_width, "\t");
    _builder.append("</width>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<max-reads>");
    int _maxReads = rf.getMaxReads();
    _builder.append(_maxReads, "\t");
    _builder.append("</max-reads>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<max-writes>");
    int _maxWrites = rf.getMaxWrites();
    _builder.append(_maxWrites, "\t");
    _builder.append("</max-writes>");
    _builder.newLineIfNotEmpty();
    {
      EList<FuPort> _ports = rf.getPorts();
      for(final FuPort port : _ports) {
        _builder.append("\t");
        CharSequence _adf = this.getAdf(port, false, false);
        _builder.append(_adf, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</register-file>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getAdf(final Socket socket) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<socket name=\"");
    String _name = socket.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    {
      EList<Segment> _connectedSegments = socket.getConnectedSegments();
      for(final Segment segment : _connectedSegments) {
        _builder.append("\t");
        CharSequence _connect = this.connect(segment, socket);
        _builder.append(_connect, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</socket>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getAdf(final Memory addressSpace, final Processor processor, final boolean isRAM) {
    CharSequence _xblockexpression = null;
    {
      long _xifexpression = (long) 0;
      if (((addressSpace.getMaxAddress() < 0) || (addressSpace.getMaxAddress() > this.MAX_ADDRESS))) {
        int _xblockexpression_1 = (int) 0;
        {
          String _name = addressSpace.getName();
          String _plus = ("Address-space \'" + _name);
          String _plus_1 = (_plus + "\' of ");
          String _name_1 = processor.getName();
          String _plus_2 = (_plus_1 + _name_1);
          String _plus_3 = (_plus_2 + " exceeds the maximal size.");
          OrccLogger.warnln(_plus_3);
          _xblockexpression_1 = this.MAX_ADDRESS;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        _xifexpression = addressSpace.getMaxAddress();
      }
      final long maxAddress = _xifexpression;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<address-space name=\"");
      String _name = addressSpace.getName();
      _builder.append(_name);
      _builder.append("\">");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("<width>");
      int _wordWidth = addressSpace.getWordWidth();
      _builder.append(_wordWidth, "\t");
      _builder.append("</width>");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("<min-address>");
      long _minAddress = addressSpace.getMinAddress();
      _builder.append(_minAddress, "\t");
      _builder.append("</min-address>");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("<max-address>");
      _builder.append(maxAddress, "\t");
      _builder.append("</max-address>");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("<shared-memory>");
      boolean _isShared = addressSpace.isShared();
      _builder.append(_isShared, "\t");
      _builder.append("</shared-memory>");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      {
        if (isRAM) {
          _builder.append("<numerical-id>");
          Integer _get = processor.getMemToAddrSpaceIdMap().get(addressSpace);
          _builder.append(_get, "\t");
          _builder.append("</numerical-id>");
        }
      }
      _builder.newLineIfNotEmpty();
      _builder.append("</address-space>");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  private CharSequence getAdf(final Segment segment) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<segment name=\"");
    String _name = segment.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<writes-to/>");
    _builder.newLine();
    _builder.append("</segment>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getAdf(final ShortImmediate shortImmediate) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<short-immediate>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<extension>zero</extension>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<width>");
    int _width = shortImmediate.getWidth();
    _builder.append(_width, "\t");
    _builder.append("</width>");
    _builder.newLineIfNotEmpty();
    _builder.append("</short-immediate>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence connect(final Segment segment, final Socket socket) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isInput = socket.isInput();
      if (_isInput) {
        _builder.append("<reads-from>");
      } else {
        _builder.append("<writes-to>");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<bus>");
    String _name = segment.getBus().getName();
    _builder.append(_name, "\t");
    _builder.append("</bus>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<segment>");
    String _name_1 = segment.getName();
    _builder.append(_name_1, "\t");
    _builder.append("</segment>");
    _builder.newLineIfNotEmpty();
    {
      boolean _isInput_1 = socket.isInput();
      if (_isInput_1) {
        _builder.append("</reads-from>");
      } else {
        _builder.append("</writes-to>");
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence connect(final Socket socket) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<connects-to>");
    String _name = socket.getName();
    _builder.append(_name);
    _builder.append("</connects-to>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence connect(final Memory addressSpace) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((addressSpace == null)) {
        _builder.append("<address-space/>");
      } else {
        _builder.append("<address-space>");
        String _name = addressSpace.getName();
        _builder.append(_name);
        _builder.append("</address-space>");
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence getIdf(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    _builder.newLine();
    _builder.append("<adf-implementation>");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<ic-decoder-plugin>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<name>DefaultICDecoder</name>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<file>DefaultICDecoderPlugin.so</file>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<hdb-file>asic_130nm_1.5V.hdb</hdb-file>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</ic-decoder-plugin>");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<FunctionUnit> _functionUnits = processor.getFunctionUnits();
      for(final FunctionUnit fu : _functionUnits) {
        _builder.append("\t");
        CharSequence _idf = this.getIdf(fu);
        _builder.append(_idf, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<RegisterFile> _registerFiles = processor.getRegisterFiles();
      for(final RegisterFile rf : _registerFiles) {
        _builder.append("\t");
        CharSequence _idf_1 = this.getIdf(rf);
        _builder.append(_idf_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("</adf-implementation>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getIdf(final FunctionUnit fu) {
    CharSequence _xblockexpression = null;
    {
      final Implementation impl = this.hwDb.get(fu.getImplementation());
      CharSequence _xifexpression = null;
      if ((impl == null)) {
        String _name = fu.getName();
        String _plus = ("Unknown implementation of " + _name);
        String _plus_1 = (_plus + ", the design will not be able to be generated.");
        OrccLogger.noticeln(_plus_1);
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("<fu name=\"");
        String _name_1 = fu.getName();
        _builder.append(_name_1);
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<hdb-file>");
        String _hdbFile = impl.getHdbFile();
        _builder.append(_hdbFile, "\t");
        _builder.append("</hdb-file>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<fu-id>");
        int _id = impl.getId();
        _builder.append(_id, "\t");
        _builder.append("</fu-id>");
        _builder.newLineIfNotEmpty();
        _builder.append("</fu>");
        _builder.newLine();
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private CharSequence getIdf(final RegisterFile rf) {
    CharSequence _xblockexpression = null;
    {
      final Implementation impl = this.hwDb.get(rf.getImplementation());
      CharSequence _xifexpression = null;
      if ((impl == null)) {
        String _name = rf.getName();
        String _plus = ("Unknown implementation of " + _name);
        String _plus_1 = (_plus + ", the design will not be able to be generated.");
        OrccLogger.noticeln(_plus_1);
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("<rf name=\"");
        String _name_1 = rf.getName();
        _builder.append(_name_1);
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<hdb-file>");
        String _hdbFile = impl.getHdbFile();
        _builder.append(_hdbFile, "\t");
        _builder.append("</hdb-file>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t   ");
        _builder.append("<rf-id>");
        int _id = impl.getId();
        _builder.append(_id, "\t   ");
        _builder.append("</rf-id>");
        _builder.newLineIfNotEmpty();
        _builder.append("</rf>");
        _builder.newLine();
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private CharSequence getAdfExpr(final Guard expr) {
    if (expr instanceof ExprBinary) {
      return _getAdfExpr((ExprBinary)expr);
    } else if (expr instanceof ExprFalse) {
      return _getAdfExpr((ExprFalse)expr);
    } else if (expr instanceof ExprTrue) {
      return _getAdfExpr((ExprTrue)expr);
    } else if (expr instanceof ExprUnary) {
      return _getAdfExpr((ExprUnary)expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
  
  private CharSequence getAdfTerm(final Term term) {
    if (term instanceof TermBool) {
      return _getAdfTerm((TermBool)term);
    } else if (term instanceof TermUnit) {
      return _getAdfTerm((TermUnit)term);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(term).toString());
    }
  }
  
  private CharSequence getAdfElement(final Element element) {
    if (element instanceof Reads) {
      return _getAdfElement((Reads)element);
    } else if (element instanceof Resource) {
      return _getAdfElement((Resource)element);
    } else if (element instanceof Writes) {
      return _getAdfElement((Writes)element);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(element).toString());
    }
  }
}
