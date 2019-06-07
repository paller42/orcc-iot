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
import net.sf.orcc.backends.llvm.tta.architecture.Link;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Port;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.Signal;
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.graph.Edge;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class HwProcessorPrinter extends TTAPrinter {
  private FPGA fpga;
  
  public FPGA setFpga(final FPGA fpga) {
    return this.fpga = fpga;
  }
  
  public CharSequence getVhdl(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Title      : ");
    String _name = processor.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("-- Project    : ");
    _builder.newLine();
    _builder.append("-------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- File       : ");
    String _name_1 = processor.getName();
    _builder.append(_name_1);
    _builder.append(".vhd");
    _builder.newLineIfNotEmpty();
    _builder.append("-- Author     : Orcc - TTA");
    _builder.newLine();
    _builder.append("-- Company    : ");
    _builder.newLine();
    _builder.append("-- Created    : ");
    _builder.newLine();
    _builder.append("-- Standard   : VHDL 93");
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
    _builder.append("use work.");
    String _name_2 = processor.getName();
    _builder.append(_name_2);
    _builder.append("_tl_globals.all;");
    _builder.newLineIfNotEmpty();
    _builder.append("use work.");
    String _name_3 = processor.getName();
    _builder.append(_name_3);
    _builder.append("_tl_imem_mau.all;");
    _builder.newLineIfNotEmpty();
    _builder.append("use work.");
    String _name_4 = processor.getName();
    _builder.append(_name_4);
    _builder.append("_tl_params.all;");
    _builder.newLineIfNotEmpty();
    _builder.append("use work.");
    String _name_5 = processor.getName();
    _builder.append(_name_5);
    _builder.append("_mem_constants.all;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.append("------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("entity ");
    String _name_6 = processor.getName();
    _builder.append(_name_6);
    _builder.append(" is");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("generic");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("(");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("device_family : string");
    _builder.newLine();
    _builder.append("      ");
    _builder.append(");");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("port");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("(");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("clk                    : in  std_logic;");
    _builder.newLine();
    _builder.append("      ");
    CharSequence _declarePorts = this.declarePorts(processor);
    _builder.append(_declarePorts, "      ");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("rst_n                  : in  std_logic");
    _builder.newLine();
    _builder.append("      ");
    _builder.append(");");
    _builder.newLine();
    _builder.append("end ");
    String _name_7 = processor.getName();
    _builder.append(_name_7);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.append("------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("architecture bdf_type of ");
    String _name_8 = processor.getName();
    _builder.append(_name_8);
    _builder.append(" is");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _declare = this.declare(processor);
    _builder.append(_declare, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("begin");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _assign = this.assign(processor);
    _builder.append(_assign, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("end bdf_type;");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence declare(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isXilinx = this.fpga.isXilinx();
      if (_isXilinx) {
        _builder.append("---------------------------------------------------------------------------");
        _builder.newLine();
        _builder.append("-- Components declaration");
        _builder.newLine();
        _builder.append("---------------------------------------------------------------------------");
        _builder.newLine();
        _builder.append("component dram_");
        String _name = processor.getName();
        _builder.append(_name);
        _builder.newLineIfNotEmpty();
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
        _builder.append("addra : in  std_logic_vector(fu_LSU_0_addrw-2-1 downto 0);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("dina  : in  std_logic_vector(fu_LSU_0_dataw-1 downto 0);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("douta : out std_logic_vector(fu_LSU_0_dataw-1 downto 0));");
        _builder.newLine();
        _builder.append("end component;");
        _builder.newLine();
        _builder.newLine();
        _builder.append("component irom_");
        String _name_1 = processor.getName();
        _builder.append(_name_1);
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("port (");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("clka  : in  std_logic;");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("addra : in  std_logic_vector(INSTRUCTIONADDRWIDTH-1 downto 0);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("douta : out std_logic_vector(IMEMWIDTHINMAUS*IMEMMAUWIDTH-1 downto 0));");
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
    _builder.append("signal dram_addr          : std_logic_vector(fu_LSU_0_addrw-2-1 downto 0);");
    _builder.newLine();
    _builder.append("signal wren_wire          : std_logic;");
    _builder.newLine();
    _builder.append("signal wren_x_wire        : std_logic;");
    _builder.newLine();
    _builder.append("signal dram_data_in_wire  : std_logic_vector(fu_LSU_0_dataw-1 downto 0);");
    _builder.newLine();
    _builder.append("signal dram_data_out_wire : std_logic_vector(fu_LSU_0_dataw-1 downto 0);");
    _builder.newLine();
    _builder.append("signal bytemask_wire      : std_logic_vector(fu_LSU_0_dataw/8-1 downto 0);");
    _builder.newLine();
    {
      boolean _isXilinx_1 = this.fpga.isXilinx();
      if (_isXilinx_1) {
        _builder.append("signal bytemask_i         : std_logic_vector(fu_LSU_0_dataw/8-1 downto 0);");
        _builder.newLine();
        _builder.append("signal bytemask_i2        : std_logic_vector(fu_LSU_0_dataw/8-1 downto 0);");
        _builder.newLine();
      }
    }
    _builder.append("--");
    _builder.newLine();
    _builder.append("signal imem_addr          : std_logic_vector(IMEMADDRWIDTH-1 downto 0);");
    _builder.newLine();
    _builder.append("signal idata_wire         : std_logic_vector(IMEMWIDTHINMAUS*IMEMMAUWIDTH-1 downto 0);");
    _builder.newLine();
    _builder.append("--");
    _builder.newLine();
    {
      EList<Edge> _incoming = processor.getIncoming();
      for(final Edge edge : _incoming) {
        final Link link = ((Link) edge);
        _builder.newLineIfNotEmpty();
        final Port port = link.getTargetPort();
        _builder.newLineIfNotEmpty();
        CharSequence _declareSignal = this.declareSignal(port, link);
        _builder.append(_declareSignal);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Edge> _outgoing = processor.getOutgoing();
      for(final Edge edge_1 : _outgoing) {
        final Link link_1 = ((Link) edge_1);
        _builder.newLineIfNotEmpty();
        final Port port_1 = link_1.getSourcePort();
        _builder.newLineIfNotEmpty();
        CharSequence _declareSignal_1 = this.declareSignal(port_1, link_1);
        _builder.append(_declareSignal_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence assign(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("wren_wire <= not(wren_x_wire);");
    _builder.newLine();
    {
      EList<Edge> _incoming = processor.getIncoming();
      for(final Edge edge : _incoming) {
        final Link link = ((Link) edge);
        _builder.newLineIfNotEmpty();
        final Port port = link.getTargetPort();
        _builder.newLineIfNotEmpty();
        CharSequence _mapSignal = this.mapSignal(port, link);
        _builder.append(_mapSignal);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Edge> _outgoing = processor.getOutgoing();
      for(final Edge edge_1 : _outgoing) {
        final Link link_1 = ((Link) edge_1);
        _builder.newLineIfNotEmpty();
        final Port port_1 = link_1.getSourcePort();
        _builder.newLineIfNotEmpty();
        CharSequence _mapSignal_1 = this.mapSignal(port_1, link_1);
        _builder.append(_mapSignal_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      boolean _isAltera = this.fpga.isAltera();
      if (_isAltera) {
        _builder.append("inst_dram_");
        String _name = processor.getName();
        _builder.append(_name);
        _builder.append(" : entity work.dram_1p");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("generic map(depth         => DATADEPTH,");
        _builder.newLine();
        _builder.append("              ");
        _builder.append("byte_width    => fu_LSU_0_dataw/4,");
        _builder.newLine();
        _builder.append("              ");
        _builder.append("addr_width\t  => fu_LSU_0_addrw-2,");
        _builder.newLine();
        _builder.append("              ");
        _builder.append("bytes         => 4,");
        _builder.newLine();
        _builder.append("              ");
        _builder.append("init_file     => \"dram_");
        String _name_1 = processor.getName();
        _builder.append(_name_1, "              ");
        _builder.append(".mif\",");
        _builder.newLineIfNotEmpty();
        _builder.append("              ");
        _builder.append("device_family => device_family)");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("port map(clk     => clk,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("wren    => wren_wire,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("address => dram_addr,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("byteen  => bytemask_wire,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("data    => dram_data_in_wire,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("queue   => dram_data_out_wire,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("rst_n   => rst_n);");
        _builder.newLine();
        _builder.newLine();
        _builder.append("inst_irom_");
        String _name_2 = processor.getName();
        _builder.append(_name_2);
        _builder.append(" : entity work.irom");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("generic map(depth         => INSTRUCTIONDEPTH,");
        _builder.newLine();
        _builder.append("              ");
        _builder.append("byte_width    => IMEMMAUWIDTH,");
        _builder.newLine();
        _builder.append("              ");
        _builder.append("addr_width    => INSTRUCTIONADDRWIDTH,");
        _builder.newLine();
        _builder.append("              ");
        _builder.append("bytes         => IMEMWIDTHINMAUS,");
        _builder.newLine();
        _builder.append("              ");
        _builder.append("init_file     => \"irom_");
        String _name_3 = processor.getName();
        _builder.append(_name_3, "              ");
        _builder.append(".mif\",");
        _builder.newLineIfNotEmpty();
        _builder.append("              ");
        _builder.append("device_family => device_family)");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("port map(clk     => clk,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("address => imem_addr(INSTRUCTIONADDRWIDTH-1 downto 0),");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("queue   => idata_wire,");
        _builder.newLine();
        _builder.append("           ");
        _builder.append("rst_n   => rst_n);");
        _builder.newLine();
      } else {
        _builder.append("bytemask_i2 <= wren_wire & wren_wire & wren_wire & wren_wire;");
        _builder.newLine();
        _builder.append("bytemask_i  <= bytemask_i2 and bytemask_wire;");
        _builder.newLine();
        _builder.newLine();
        _builder.append("inst_irom_");
        String _name_4 = processor.getName();
        _builder.append(_name_4);
        _builder.append(" : irom_");
        String _name_5 = processor.getName();
        _builder.append(_name_5);
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("port map (");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("clka  => clk,");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("addra => imem_addr(INSTRUCTIONADDRWIDTH-1 downto 0),");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("douta => idata_wire);");
        _builder.newLine();
        _builder.newLine();
        _builder.append("inst_dram_");
        String _name_6 = processor.getName();
        _builder.append(_name_6);
        _builder.append(" : dram_");
        String _name_7 = processor.getName();
        _builder.append(_name_7);
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("port map (");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("clka  => clk,");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("wea   => bytemask_i,");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("addra => dram_addr,");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("dina  => dram_data_in_wire,");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("douta => dram_data_out_wire);");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("inst_");
    String _name_8 = processor.getName();
    _builder.append(_name_8);
    _builder.append("_tl : entity work.");
    String _name_9 = processor.getName();
    _builder.append(_name_9);
    _builder.append("_tl");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("port map(clk                      => clk,");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("busy                     => \'0\',");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("imem_addr                => imem_addr,");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("imem_data                => idata_wire,");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("pc_init                  => (others => \'0\'),");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("fu_LSU_0_dmem_data_in    => dram_data_out_wire,");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("fu_LSU_0_dmem_data_out   => dram_data_in_wire,");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("fu_LSU_0_dmem_addr       => dram_addr,");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("fu_LSU_0_dmem_wr_en_x(0) => wren_x_wire,");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("fu_LSU_0_dmem_bytemask   => bytemask_wire,");
    _builder.newLine();
    _builder.append("           ");
    CharSequence _mapPorts = this.mapPorts(processor);
    _builder.append(_mapPorts, "           ");
    _builder.newLineIfNotEmpty();
    _builder.append("           ");
    _builder.append("rstx                     => rst_n);");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence declarePorts(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Edge> _incoming = processor.getIncoming();
      for(final Edge edge : _incoming) {
        final Link link = ((Link) edge);
        _builder.newLineIfNotEmpty();
        final Port port = link.getTargetPort();
        _builder.newLineIfNotEmpty();
        CharSequence _declarePort = this.declarePort(port, link);
        _builder.append(_declarePort);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Edge> _outgoing = processor.getOutgoing();
      for(final Edge edge_1 : _outgoing) {
        final Link link_1 = ((Link) edge_1);
        _builder.newLineIfNotEmpty();
        final Port port_1 = link_1.getSourcePort();
        _builder.newLineIfNotEmpty();
        CharSequence _declarePort_1 = this.declarePort(port_1, link_1);
        _builder.append(_declarePort_1);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence _declarePort(final Port port, final Signal signal) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" : out std_logic_vector(");
    int _size = signal.getSize();
    _builder.append(_size);
    _builder.append("-1 downto 0);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _declarePort(final Port port, final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fu_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_dmem_data_in  : in std_logic_vector(fu_");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("_dataw-1 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_2 = port.getName();
    _builder.append(_name_2);
    _builder.append("_dmem_data_out : out std_logic_vector(fu_");
    String _name_3 = port.getName();
    _builder.append(_name_3);
    _builder.append("_dataw-1 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_4 = port.getName();
    _builder.append(_name_4);
    _builder.append("_dmem_addr     : out std_logic_vector(fu_");
    String _name_5 = port.getName();
    _builder.append(_name_5);
    _builder.append("_addrw-2-1 downto 0);");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_6 = port.getName();
    _builder.append(_name_6);
    _builder.append("_dmem_wr_en    : out std_logic;");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_7 = port.getName();
    _builder.append(_name_7);
    _builder.append("_dmem_bytemask : out std_logic_vector(fu_");
    String _name_8 = port.getName();
    _builder.append(_name_8);
    _builder.append("_dataw/8-1 downto 0);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _declareSignal(final Port port, final Signal signal) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("signal ");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_i : std_logic_vector(7 downto 0);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _declareSignal(final Port port, final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("signal fu_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_dmem_wr_en_x : std_logic;");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence mapPorts(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Edge> _incoming = processor.getIncoming();
      for(final Edge edge : _incoming) {
        final Link link = ((Link) edge);
        _builder.newLineIfNotEmpty();
        final Port port = link.getTargetPort();
        _builder.newLineIfNotEmpty();
        CharSequence _mapPort = this.mapPort(port, link);
        _builder.append(_mapPort);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Edge> _outgoing = processor.getOutgoing();
      for(final Edge edge_1 : _outgoing) {
        final Link link_1 = ((Link) edge_1);
        _builder.newLineIfNotEmpty();
        final Port port_1 = link_1.getSourcePort();
        _builder.newLineIfNotEmpty();
        CharSequence _mapPort_1 = this.mapPort(port_1, link_1);
        _builder.append(_mapPort_1);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence _mapPort(final Port port, final Signal signal) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fu_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_STRATIXIII_LED => ");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("_i,");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _mapPort(final Port port, final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fu_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_dmem_data_in    => fu_");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("_dmem_data_in,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_2 = port.getName();
    _builder.append(_name_2);
    _builder.append("_dmem_data_out   => fu_");
    String _name_3 = port.getName();
    _builder.append(_name_3);
    _builder.append("_dmem_data_out,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_4 = port.getName();
    _builder.append(_name_4);
    _builder.append("_dmem_addr       => fu_");
    String _name_5 = port.getName();
    _builder.append(_name_5);
    _builder.append("_dmem_addr,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_6 = port.getName();
    _builder.append(_name_6);
    _builder.append("_dmem_wr_en_x(0) => fu_");
    String _name_7 = port.getName();
    _builder.append(_name_7);
    _builder.append("_dmem_wr_en_x,");
    _builder.newLineIfNotEmpty();
    _builder.append("fu_");
    String _name_8 = port.getName();
    _builder.append(_name_8);
    _builder.append("_dmem_bytemask   => fu_");
    String _name_9 = port.getName();
    _builder.append(_name_9);
    _builder.append("_dmem_bytemask,");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _mapSignal(final Port port, final Signal signal) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" <= ");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("_i(");
    int _size = signal.getSize();
    _builder.append(_size);
    _builder.append("-1 downto 0);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence _mapSignal(final Port port, final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fu_");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append("_dmem_wr_en <= not(fu_");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append("_dmem_wr_en_x);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence declarePort(final Port port, final Link memory) {
    if (memory instanceof Memory) {
      return _declarePort(port, (Memory)memory);
    } else if (memory instanceof Signal) {
      return _declarePort(port, (Signal)memory);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(port, memory).toString());
    }
  }
  
  private CharSequence declareSignal(final Port port, final Link memory) {
    if (memory instanceof Memory) {
      return _declareSignal(port, (Memory)memory);
    } else if (memory instanceof Signal) {
      return _declareSignal(port, (Signal)memory);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(port, memory).toString());
    }
  }
  
  private CharSequence mapPort(final Port port, final Link memory) {
    if (memory instanceof Memory) {
      return _mapPort(port, (Memory)memory);
    } else if (memory instanceof Signal) {
      return _mapPort(port, (Signal)memory);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(port, memory).toString());
    }
  }
  
  private CharSequence mapSignal(final Port port, final Link memory) {
    if (memory instanceof Memory) {
      return _mapSignal(port, (Memory)memory);
    } else if (memory instanceof Signal) {
      return _mapSignal(port, (Signal)memory);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(port, memory).toString());
    }
  }
}
