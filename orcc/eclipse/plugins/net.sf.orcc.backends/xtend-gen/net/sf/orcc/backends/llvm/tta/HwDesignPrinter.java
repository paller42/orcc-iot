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
import net.sf.orcc.backends.llvm.tta.architecture.Component;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Link;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Port;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.Signal;
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.util.Attribute;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class HwDesignPrinter extends TTAPrinter {
  private ExpressionPrinter exprPrinter;
  
  private FPGA fpga;
  
  public HwDesignPrinter() {
    super();
    ExpressionPrinter _expressionPrinter = new ExpressionPrinter();
    this.exprPrinter = _expressionPrinter;
  }
  
  public FPGA setFpga(final FPGA fpga) {
    return this.fpga = fpga;
  }
  
  public CharSequence getVhdl(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Title      : Network: ");
    String _name = design.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("-- Project    : ");
    _builder.newLine();
    _builder.append("-------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- File       : ");
    String _name_1 = design.getName();
    _builder.append(_name_1);
    _builder.append(".vhd");
    _builder.newLineIfNotEmpty();
    _builder.append("-- Author     : Orcc - TTA");
    _builder.newLine();
    _builder.append("-- Company    : ");
    _builder.newLine();
    _builder.append("-- Created    : ");
    _builder.newLine();
    _builder.append("-- Standard   : VHDL\'93");
    _builder.newLine();
    _builder.append("-------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Copyright (c)  ");
    _builder.newLine();
    _builder.append("-------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Revisions  :");
    _builder.newLine();
    _builder.append("-- Date        Version  Author  Description");
    _builder.newLine();
    _builder.append("-- ");
    _builder.newLine();
    _builder.append("-------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("library ieee;");
    _builder.newLine();
    _builder.append("use ieee.std_logic_1164.all;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("library work;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("entity top is");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("port");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("(");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("clk : in  std_logic;");
    _builder.newLine();
    _builder.append("      ");
    CharSequence _declarePorts = this.declarePorts(design);
    _builder.append(_declarePorts, "      ");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("rst_n : in  std_logic");
    _builder.newLine();
    _builder.append("      ");
    _builder.append(");");
    _builder.newLine();
    _builder.append("end top;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("architecture bdf_type of top is");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _declare = this.declare(design);
    _builder.append(_declare, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("begin");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _instantiate = this.instantiate(design);
    _builder.append(_instantiate, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("end bdf_type;");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getVhdl(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.newLine();
    String _name = processor.getName();
    _builder.append(_name);
    _builder.append("_inst : entity work.");
    String _name_1 = processor.getName();
    _builder.append(_name_1);
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("generic map(device_family => \"");
    FPGA.Family _family = this.fpga.getFamily();
    _builder.append(_family, "  ");
    _builder.append("\")");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("port map(clk                    => clk,");
    _builder.newLine();
    _builder.append("           ");
    CharSequence _assignPorts = this.assignPorts(processor);
    _builder.append(_assignPorts, "           ");
    _builder.newLineIfNotEmpty();
    _builder.append("           ");
    _builder.append("rst_n                  => rst_n);");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getVhdl(final Component component) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = component.getName();
    _builder.append(_name);
    _builder.append("_inst : entity work.");
    String _name_1 = component.getName();
    _builder.append(_name_1);
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    {
      boolean _isEmpty = component.getAttributes().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("generic map(");
        CharSequence _assignGenerics = this.assignGenerics(component.getAttributes());
        _builder.append(_assignGenerics, "  ");
        _builder.append(")");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("port map(clk => clk,");
    _builder.newLine();
    _builder.append("           ");
    CharSequence _assignPorts = this.assignPorts(component);
    _builder.append(_assignPorts, "           ");
    _builder.newLineIfNotEmpty();
    _builder.append("           ");
    _builder.append("rst_n => rst_n);");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getVhdl(final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isXilinx = this.fpga.isXilinx();
      if (_isXilinx) {
        String _name = memory.getName();
        _builder.append(_name);
        _builder.append("_bytemask_p1 <= (");
        String _name_1 = memory.getName();
        _builder.append(_name_1);
        _builder.append("_wren_p1 & ");
        String _name_2 = memory.getName();
        _builder.append(_name_2);
        _builder.append("_wren_p1 & ");
        String _name_3 = memory.getName();
        _builder.append(_name_3);
        _builder.append("_wren_p1 & ");
        String _name_4 = memory.getName();
        _builder.append(_name_4);
        _builder.append("_wren_p1) and ");
        String _name_5 = memory.getName();
        _builder.append(_name_5);
        _builder.append("_byteen_p1;");
        _builder.newLineIfNotEmpty();
        String _name_6 = memory.getName();
        _builder.append(_name_6);
        _builder.append("_bytemask_p2 <= (");
        String _name_7 = memory.getName();
        _builder.append(_name_7);
        _builder.append("_wren_p2 & ");
        String _name_8 = memory.getName();
        _builder.append(_name_8);
        _builder.append("_wren_p2 & ");
        String _name_9 = memory.getName();
        _builder.append(_name_9);
        _builder.append("_wren_p2 & ");
        String _name_10 = memory.getName();
        _builder.append(_name_10);
        _builder.append("_wren_p2) and ");
        String _name_11 = memory.getName();
        _builder.append(_name_11);
        _builder.append("_byteen_p2;");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        String _name_12 = memory.getName();
        _builder.append(_name_12);
        _builder.append(" : entity work.dram_2p_");
        String _name_13 = memory.getName();
        _builder.append(_name_13);
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("port map(clka   => clk,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("wea    => ");
        String _name_14 = memory.getName();
        _builder.append(_name_14, "           ");
        _builder.append("_bytemask_p1,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("addra  => ");
        String _name_15 = memory.getName();
        _builder.append(_name_15, "           ");
        _builder.append("_address_p1,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("dina   => ");
        String _name_16 = memory.getName();
        _builder.append(_name_16, "           ");
        _builder.append("_data_p1,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("douta  => ");
        String _name_17 = memory.getName();
        _builder.append(_name_17, "           ");
        _builder.append("_queue_p1,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("clkb   => clk,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("web    => ");
        String _name_18 = memory.getName();
        _builder.append(_name_18, "           ");
        _builder.append("_bytemask_p2,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("addrb  => ");
        String _name_19 = memory.getName();
        _builder.append(_name_19, "           ");
        _builder.append("_address_p2,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("dinb   => ");
        String _name_20 = memory.getName();
        _builder.append(_name_20, "           ");
        _builder.append("_data_p2,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("doutb  => ");
        String _name_21 = memory.getName();
        _builder.append(_name_21, "           ");
        _builder.append("_queue_p2);");
        _builder.newLineIfNotEmpty();
      } else {
        String _name_22 = memory.getName();
        _builder.append(_name_22);
        _builder.append(" : entity work.dram_2p");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("generic map(depth      => ");
        long _depth = memory.getDepth();
        _builder.append(_depth, "  ");
        _builder.append("/4,");
        _builder.newLineIfNotEmpty();
        _builder.append("              ");
        _builder.append("byte_width => ");
        int _wordWidth = memory.getWordWidth();
        _builder.append(_wordWidth, "              ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("              ");
        _builder.append("addr_width => ");
        int _addrWidth = memory.getAddrWidth();
        _builder.append(_addrWidth, "              ");
        _builder.append("-2,");
        _builder.newLineIfNotEmpty();
        _builder.append("              ");
        _builder.append("bytes      => 4,");
        _builder.newLine();
        _builder.append("              ");
        _builder.append("device_family => \"");
        FPGA.Family _family = this.fpga.getFamily();
        _builder.append(_family, "              ");
        _builder.append("\")");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("port map(clk        => clk,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("wren_p1    => ");
        String _name_23 = memory.getName();
        _builder.append(_name_23, "           ");
        _builder.append("_wren_p1,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("address_p1 => ");
        String _name_24 = memory.getName();
        _builder.append(_name_24, "           ");
        _builder.append("_address_p1,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("byteen_p1  => ");
        String _name_25 = memory.getName();
        _builder.append(_name_25, "           ");
        _builder.append("_byteen_p1,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("data_p1    => ");
        String _name_26 = memory.getName();
        _builder.append(_name_26, "           ");
        _builder.append("_data_p1,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("queue_p1   => ");
        String _name_27 = memory.getName();
        _builder.append(_name_27, "           ");
        _builder.append("_queue_p1,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("wren_p2    => ");
        String _name_28 = memory.getName();
        _builder.append(_name_28, "           ");
        _builder.append("_wren_p2,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("address_p2 => ");
        String _name_29 = memory.getName();
        _builder.append(_name_29, "           ");
        _builder.append("_address_p2,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("byteen_p2  => ");
        String _name_30 = memory.getName();
        _builder.append(_name_30, "           ");
        _builder.append("_byteen_p2,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("data_p2    => ");
        String _name_31 = memory.getName();
        _builder.append(_name_31, "           ");
        _builder.append("_data_p2,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("queue_p2   => ");
        String _name_32 = memory.getName();
        _builder.append(_name_32, "           ");
        _builder.append("_queue_p2,");
        _builder.newLineIfNotEmpty();
        _builder.append("           ");
        _builder.append("rst_n      => rst_n);");
        _builder.newLine();
      }
    }
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence assign(final Port port, final Signal signal) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" <= s_");
    String _name_1 = signal.getName();
    _builder.append(_name_1);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence assignPorts(final Component component) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Edge> _incoming = component.getIncoming();
      for(final Edge edge : _incoming) {
        final Link link = ((Link) edge);
        _builder.newLineIfNotEmpty();
        final Port port = link.getTargetPort();
        _builder.newLineIfNotEmpty();
        CharSequence _assignInput = this.assignInput(port, link);
        _builder.append(_assignInput);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Edge> _outgoing = component.getOutgoing();
      for(final Edge edge_1 : _outgoing) {
        final Link link_1 = ((Link) edge_1);
        _builder.newLineIfNotEmpty();
        final Port port_1 = link_1.getSourcePort();
        _builder.newLineIfNotEmpty();
        CharSequence _assignOutput = this.assignOutput(port_1, link_1);
        _builder.append(_assignOutput);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence assignGenerics(final EList<Attribute> attributes) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final Attribute attribute : attributes) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",\n", "");
        }
        String _name = attribute.getName();
        _builder.append(_name);
        _builder.append(" => ");
        String _doSwitch = this.exprPrinter.doSwitch(attribute.getReferencedValue());
        _builder.append(_doSwitch);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence _assignInput(final Port port, final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fu_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_dmem_data_in  => ");
    String _name_1 = memory.getName();
    _builder.append(_name_1);
    _builder.append("_queue_p2,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_2 = port.getName();
    _builder.append(_name_2);
    _builder.append("_dmem_data_out => ");
    String _name_3 = memory.getName();
    _builder.append(_name_3);
    _builder.append("_data_p2,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_4 = port.getName();
    _builder.append(_name_4);
    _builder.append("_dmem_addr     => ");
    String _name_5 = memory.getName();
    _builder.append(_name_5);
    _builder.append("_address_p2,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_6 = port.getName();
    _builder.append(_name_6);
    _builder.append("_dmem_wr_en    => ");
    String _name_7 = memory.getName();
    _builder.append(_name_7);
    _builder.append("_wren_p2,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_8 = port.getName();
    _builder.append(_name_8);
    _builder.append("_dmem_bytemask => ");
    String _name_9 = memory.getName();
    _builder.append(_name_9);
    _builder.append("_byteen_p2,");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _assignInput(final Port port, final Signal signal) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" => s_");
    String _name_1 = signal.getName();
    _builder.append(_name_1);
    _builder.append("(");
    int _size = signal.getSize();
    _builder.append(_size);
    _builder.append("-1 downto 0),");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _assignOutput(final Port port, final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fu_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_dmem_data_in  => ");
    String _name_1 = memory.getName();
    _builder.append(_name_1);
    _builder.append("_queue_p1,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_2 = port.getName();
    _builder.append(_name_2);
    _builder.append("_dmem_data_out => ");
    String _name_3 = memory.getName();
    _builder.append(_name_3);
    _builder.append("_data_p1,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_4 = port.getName();
    _builder.append(_name_4);
    _builder.append("_dmem_addr     => ");
    String _name_5 = memory.getName();
    _builder.append(_name_5);
    _builder.append("_address_p1,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_6 = port.getName();
    _builder.append(_name_6);
    _builder.append("_dmem_wr_en    => ");
    String _name_7 = memory.getName();
    _builder.append(_name_7);
    _builder.append("_wren_p1,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_8 = port.getName();
    _builder.append(_name_8);
    _builder.append("_dmem_bytemask => ");
    String _name_9 = memory.getName();
    _builder.append(_name_9);
    _builder.append("_byteen_p1,");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _assignOutput(final Port port, final Signal signal) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("(");
    int _size = signal.getSize();
    _builder.append(_size);
    _builder.append("-1 downto 0) => s_");
    String _name_1 = signal.getName();
    _builder.append(_name_1);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence instantiate(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Ports instantiation ");
    _builder.newLine();
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    {
      EList<Port> _inputs = design.getInputs();
      for(final Port port : _inputs) {
        Edge _get = port.getOutgoing().get(0);
        CharSequence _assign = this.assign(port, ((Signal) _get));
        _builder.append(_assign);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _outputs = design.getOutputs();
      for(final Port port_1 : _outputs) {
        Edge _get_1 = port_1.getIncoming().get(0);
        CharSequence _assign_1 = this.assign(port_1, ((Signal) _get_1));
        _builder.append(_assign_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Buffers instantiation ");
    _builder.newLine();
    _builder.append("--------------------------------------------------------------------------- ");
    _builder.newLine();
    {
      EList<Memory> _sharedMemories = design.getSharedMemories();
      boolean _hasElements = false;
      for(final Memory memory : _sharedMemories) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\n", "");
        }
        CharSequence _vhdl = this.getVhdl(memory);
        _builder.append(_vhdl);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Processors instantiation ");
    _builder.newLine();
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    {
      EList<Processor> _processors = design.getProcessors();
      boolean _hasElements_1 = false;
      for(final Processor processor : _processors) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate("\n", "");
        }
        CharSequence _vhdl_1 = this.getVhdl(processor);
        _builder.append(_vhdl_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Components instantiation ");
    _builder.newLine();
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    {
      EList<Component> _components = design.getComponents();
      boolean _hasElements_2 = false;
      for(final Component component : _components) {
        if (!_hasElements_2) {
          _hasElements_2 = true;
        } else {
          _builder.appendImmediate("\n", "");
        }
        CharSequence _vhdl_2 = this.getVhdl(component);
        _builder.append(_vhdl_2);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence declare(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((this.fpga.isXilinx() && (!IterableExtensions.isNullOrEmpty(design.getSharedMemories())))) {
        final int addrWidth = design.getSharedMemories().get(0).getAddrWidth();
        _builder.newLineIfNotEmpty();
        _builder.append("---------------------------------------------------------------------------");
        _builder.newLine();
        _builder.append("-- Components declaration");
        _builder.newLine();
        _builder.append("---------------------------------------------------------------------------\t\t");
        _builder.newLine();
        _builder.append("component dram_2p");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("port (");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("clka  : in  std_logic;");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("wea   : in  std_logic_vector(3 downto 0);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("addra : in  std_logic_vector(");
        _builder.append(addrWidth, "    ");
        _builder.append("-2-1 downto 0);");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("dina  : in  std_logic_vector(31 downto 0);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("douta : out std_logic_vector(31 downto 0);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("clkb  : in  std_logic;");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("web   : in  std_logic_vector(3 downto 0);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("addrb : in  std_logic_vector(");
        _builder.append(addrWidth, "    ");
        _builder.append("-2-1 downto 0);");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("dinb  : in  std_logic_vector(31 downto 0);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("doutb : out std_logic_vector(31 downto 0));");
        _builder.newLine();
        _builder.append("end component;");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Signals declaration");
    _builder.newLine();
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    {
      EList<Signal> _signals = design.getSignals();
      for(final Signal signal : _signals) {
        CharSequence _declare = this.declare(signal);
        _builder.append(_declare);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Memory> _sharedMemories = design.getSharedMemories();
      for(final Memory memory : _sharedMemories) {
        CharSequence _declareSignals = this.declareSignals(memory);
        _builder.append(_declareSignals);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence declare(final Signal signal) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("signal s_");
    String _name = signal.getName();
    _builder.append(_name);
    _builder.append(" : std_logic_vector(");
    int _size = signal.getSize();
    _builder.append(_size);
    _builder.append("-1 downto 0);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence declareSignals(final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("signal ");
    String _name = memory.getName();
    _builder.append(_name);
    _builder.append("_wren_p1     : std_logic;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal ");
    String _name_1 = memory.getName();
    _builder.append(_name_1);
    _builder.append("_address_p1  : std_logic_vector(");
    int _addrWidth = memory.getAddrWidth();
    _builder.append(_addrWidth);
    _builder.append("-2-1 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal ");
    String _name_2 = memory.getName();
    _builder.append(_name_2);
    _builder.append("_byteen_p1   : std_logic_vector(3 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal ");
    String _name_3 = memory.getName();
    _builder.append(_name_3);
    _builder.append("_data_p1     : std_logic_vector(31 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal ");
    String _name_4 = memory.getName();
    _builder.append(_name_4);
    _builder.append("_queue_p1    : std_logic_vector(31 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal ");
    String _name_5 = memory.getName();
    _builder.append(_name_5);
    _builder.append("_wren_p2     : std_logic;");
    _builder.newLineIfNotEmpty();
    _builder.append("signal ");
    String _name_6 = memory.getName();
    _builder.append(_name_6);
    _builder.append("_address_p2  : std_logic_vector(");
    int _addrWidth_1 = memory.getAddrWidth();
    _builder.append(_addrWidth_1);
    _builder.append("-2-1 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal ");
    String _name_7 = memory.getName();
    _builder.append(_name_7);
    _builder.append("_byteen_p2   : std_logic_vector(3 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal ");
    String _name_8 = memory.getName();
    _builder.append(_name_8);
    _builder.append("_data_p2     : std_logic_vector(31 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("signal ");
    String _name_9 = memory.getName();
    _builder.append(_name_9);
    _builder.append("_queue_p2    : std_logic_vector(31 downto 0);");
    _builder.newLineIfNotEmpty();
    {
      boolean _isXilinx = this.fpga.isXilinx();
      if (_isXilinx) {
        _builder.append("signal ");
        String _name_10 = memory.getName();
        _builder.append(_name_10);
        _builder.append("_bytemask_p1 : std_logic_vector(3 downto 0);");
        _builder.newLineIfNotEmpty();
        _builder.append("signal ");
        String _name_11 = memory.getName();
        _builder.append(_name_11);
        _builder.append("_bytemask_p2 : std_logic_vector(3 downto 0);");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence declarePorts(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _inputs = design.getInputs();
      for(final Port port : _inputs) {
        String _name = port.getName();
        _builder.append(_name);
        _builder.append(" : in std_logic_vector(");
        int _size = port.getSize();
        _builder.append(_size);
        _builder.append("-1 downto 0);");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _outputs = design.getOutputs();
      for(final Port port_1 : _outputs) {
        String _name_1 = port_1.getName();
        _builder.append(_name_1);
        _builder.append(" : out std_logic_vector(");
        int _size_1 = port_1.getSize();
        _builder.append(_size_1);
        _builder.append("-1 downto 0);");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence assignInput(final Port port, final Link memory) {
    if (memory instanceof Memory) {
      return _assignInput(port, (Memory)memory);
    } else if (memory instanceof Signal) {
      return _assignInput(port, (Signal)memory);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(port, memory).toString());
    }
  }
  
  private CharSequence assignOutput(final Port port, final Link memory) {
    if (memory instanceof Memory) {
      return _assignOutput(port, (Memory)memory);
    } else if (memory instanceof Signal) {
      return _assignOutput(port, (Signal)memory);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(port, memory).toString());
    }
  }
}
