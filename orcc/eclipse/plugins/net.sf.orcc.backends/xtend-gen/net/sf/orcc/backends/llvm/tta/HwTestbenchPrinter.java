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

import com.google.common.collect.Iterables;
import net.sf.orcc.backends.llvm.tta.TTAPrinter;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Link;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Port;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.graph.Edge;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class HwTestbenchPrinter extends TTAPrinter {
  private FPGA fpga;
  
  public FPGA setFpga(final FPGA fpga) {
    return this.fpga = fpga;
  }
  
  public CharSequence getVhdl(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("-- Generated from ");
    String _name = design.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("------------------------------------------------------------------------------");
    _builder.newLine();
    _builder.newLine();
    _builder.append("library ieee;");
    _builder.newLine();
    _builder.append("use ieee.std_logic_1164.all; ");
    _builder.newLine();
    _builder.append("use std.textio.all;");
    _builder.newLine();
    _builder.append("use ieee.numeric_std.all;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("library work;");
    _builder.newLine();
    _builder.append("use work.sim_package.all;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("entity tb_top is");
    _builder.newLine();
    _builder.newLine();
    _builder.append("end tb_top;");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("architecture arch_tb_top of tb_top is ");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("-- Signal & constant declaration");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("--------------------------------------------------------------------------- ");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _declareVertexSigAndConst = this.declareVertexSigAndConst(design);
    _builder.append(_declareVertexSigAndConst, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("---------------------------------------------------------------------------");
    _builder.newLine();
    _builder.newLine();
    _builder.append("begin");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("top_orcc : entity work.top");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("port map (");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("clk => clk,");
    _builder.newLine();
    _builder.append("      ");
    CharSequence _mapSignals = this.mapSignals(design);
    _builder.append(_mapSignals, "      ");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("rst_n => rst_n);");
    _builder.newLine();
    _builder.append("      ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("-- clock generation");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("clk <= not clk after PERIOD/2;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("-- reset generation");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("reset_proc: process");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("begin");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("rst_n <= \'0\';");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("wait for 100 ns;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("rst_n <= \'1\';");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("wait;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end process;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("end architecture arch_tb_top;");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence declareVertexSigAndConst(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("constant PERIOD : time := 10 ns;");
    _builder.newLine();
    _builder.append("--");
    _builder.newLine();
    _builder.append("type severity_level is (note, warning, error, failure);\t\t");
    _builder.newLine();
    _builder.append("--");
    _builder.newLine();
    _builder.append("-- Input and Output signals");
    _builder.newLine();
    {
      EList<Port> _inputs = design.getInputs();
      EList<Port> _outputs = design.getOutputs();
      Iterable<Port> _plus = Iterables.<Port>concat(_inputs, _outputs);
      for(final Port port : _plus) {
        CharSequence _declareSignal = this.declareSignal(port);
        _builder.append(_declareSignal);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("--");
    _builder.newLine();
    _builder.append("-- Configuration");
    _builder.newLine();
    _builder.append("signal clk   : std_logic := \'0\';");
    _builder.newLine();
    _builder.append("signal rst_n : std_logic := \'0\';");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence declareSignal(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("signal ");
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" : std_logic_vector(");
    int _size = port.getSize();
    _builder.append(_size);
    _builder.append("-1 downto 0);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence mapSignals(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Port> _inputs = design.getInputs();
      EList<Port> _outputs = design.getOutputs();
      Iterable<Port> _plus = Iterables.<Port>concat(_inputs, _outputs);
      for(final Port port : _plus) {
        CharSequence _mapSignal = this.mapSignal(port);
        _builder.append(_mapSignal);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence mapSignal(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = port.getName();
    _builder.append(_name);
    _builder.append(" => ");
    String _name_1 = port.getName();
    _builder.append(_name_1);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence getWave(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("onerror {resume}");
    _builder.newLine();
    _builder.append("quietly WaveActivateNextPane {} 0");
    _builder.newLine();
    _builder.append("add wave -noupdate -divider <NULL>");
    _builder.newLine();
    _builder.append("add wave -noupdate -divider Top");
    _builder.newLine();
    _builder.append("add wave -noupdate -divider <NULL>");
    _builder.newLine();
    _builder.append("add wave -noupdate -format Logic /tb_top/clk");
    _builder.newLine();
    _builder.append("add wave -noupdate -format Logic /tb_top/rst_n");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Port> _inputs = design.getInputs();
      EList<Port> _outputs = design.getOutputs();
      Iterable<Port> _plus = Iterables.<Port>concat(_inputs, _outputs);
      for(final Port port : _plus) {
        CharSequence _wave = this.getWave(port);
        _builder.append(_wave);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<Processor> _processors = design.getProcessors();
      for(final Processor processor : _processors) {
        CharSequence _wave_1 = this.getWave(processor);
        _builder.append(_wave_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("TreeUpdate [SetDefaultTree]");
    _builder.newLine();
    _builder.append("WaveRestoreCursors {{Cursor 1} {112 ps} 0}");
    _builder.newLine();
    _builder.append("configure wave -namecolwidth 222");
    _builder.newLine();
    _builder.append("configure wave -valuecolwidth 100");
    _builder.newLine();
    _builder.append("configure wave -justifyvalue left");
    _builder.newLine();
    _builder.append("configure wave -signalnamewidth 1");
    _builder.newLine();
    _builder.append("configure wave -snapdistance 10");
    _builder.newLine();
    _builder.append("configure wave -datasetprefix 0");
    _builder.newLine();
    _builder.append("configure wave -rowmargin 4");
    _builder.newLine();
    _builder.append("configure wave -childrowmargin 2");
    _builder.newLine();
    _builder.append("configure wave -gridoffset 0");
    _builder.newLine();
    _builder.append("configure wave -gridperiod 1");
    _builder.newLine();
    _builder.append("configure wave -griddelta 40");
    _builder.newLine();
    _builder.append("configure wave -timeline 0");
    _builder.newLine();
    _builder.append("configure wave -timelineunits ns");
    _builder.newLine();
    _builder.append("update");
    _builder.newLine();
    _builder.append("WaveRestoreZoom {0 ps} {2911 ps}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getWave(final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("add wave -noupdate -format Literal /tb_top/top_orcc/");
    String _label = port.getLabel();
    _builder.append(_label);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence getWave(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("add wave -noupdate -divider <NULL>");
    _builder.newLine();
    _builder.append("add wave -noupdate -divider ");
    String _name = processor.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("add wave -noupdate -divider inputs");
    _builder.newLine();
    {
      EList<Edge> _incoming = processor.getIncoming();
      for(final Edge edge : _incoming) {
        final Link link = ((Link) edge);
        _builder.newLineIfNotEmpty();
        CharSequence _wave = this.getWave(link, processor, link.getTargetPort());
        _builder.append(_wave);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("add wave -noupdate -divider outputs");
    _builder.newLine();
    {
      EList<Edge> _outgoing = processor.getOutgoing();
      for(final Edge edge_1 : _outgoing) {
        final Link link_1 = ((Link) edge_1);
        _builder.newLineIfNotEmpty();
        CharSequence _wave_1 = this.getWave(link_1, processor, link_1.getSourcePort());
        _builder.append(_wave_1);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence getWave(final Link link, final Processor processor, final Port port) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isSignal = link.isSignal();
      boolean _not = (!_isSignal);
      if (_not) {
        _builder.append("add wave -noupdate -format Literal -radix decimal tb_top/top_orcc/");
        String _name = processor.getName();
        _builder.append(_name);
        _builder.append("_inst/fu_");
        String _name_1 = port.getName();
        _builder.append(_name_1);
        _builder.append("_dmem_data_in");
        _builder.newLineIfNotEmpty();
        _builder.append("add wave -noupdate -format Literal -radix decimal tb_top/top_orcc/");
        String _name_2 = processor.getName();
        _builder.append(_name_2);
        _builder.append("_inst/fu_");
        String _name_3 = port.getName();
        _builder.append(_name_3);
        _builder.append("_dmem_data_out");
        _builder.newLineIfNotEmpty();
        _builder.append("add wave -noupdate -format Literal -radix decimal tb_top/top_orcc/");
        String _name_4 = processor.getName();
        _builder.append(_name_4);
        _builder.append("_inst/fu_");
        String _name_5 = port.getName();
        _builder.append(_name_5);
        _builder.append("_dmem_addr");
        _builder.newLineIfNotEmpty();
        _builder.append("add wave -noupdate -format Logic tb_top/top_orcc/");
        String _name_6 = processor.getName();
        _builder.append(_name_6);
        _builder.append("_inst/fu_");
        String _name_7 = port.getName();
        _builder.append(_name_7);
        _builder.append("_dmem_wr_en");
        _builder.newLineIfNotEmpty();
        _builder.append("add wave -noupdate -format Literal tb_top/top_orcc/");
        String _name_8 = processor.getName();
        _builder.append(_name_8);
        _builder.append("_inst/fu_");
        String _name_9 = port.getName();
        _builder.append(_name_9);
        _builder.append("_dmem_bytemask");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("add wave -noupdate -format Literal tb_top/top_orcc/");
        String _name_10 = processor.getName();
        _builder.append(_name_10);
        _builder.append("_inst/fu_");
        String _name_11 = port.getName();
        _builder.append(_name_11);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence getTcl(final Design design) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Remove old libraries");
    _builder.newLine();
    _builder.append("vdel -all -lib work");
    _builder.newLine();
    {
      boolean _isAltera = this.fpga.isAltera();
      if (_isAltera) {
        _builder.append("vdel -all -lib altera_mf");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("# Create Working library");
    _builder.newLine();
    _builder.append("vlib work");
    _builder.newLine();
    _builder.newLine();
    {
      boolean _isAltera_1 = this.fpga.isAltera();
      if (_isAltera_1) {
        _builder.append("# Create and compile Altera library");
        _builder.newLine();
        _builder.append("vlib altera_mf");
        _builder.newLine();
        _builder.append("vmap altera_mf altera_mf");
        _builder.newLine();
        _builder.append("vcom -quiet -opt=-clkOpt -work altera_mf -93 -explicit simulation/altera_mf_components.vhd");
        _builder.newLine();
        _builder.append("vcom -quiet -opt=-clkOpt -work altera_mf -93 -explicit simulation/altera_mf.vhd");
        _builder.newLine();
      } else {
        _builder.append("exec compxlib -s mti_se -l vhdl -arch virtex6 -lib unisim -lib xilinxcorelib &");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("# Compile other components");
    _builder.newLine();
    {
      boolean _isAltera_2 = this.fpga.isAltera();
      if (_isAltera_2) {
        _builder.append("vcom -93 -quiet -work work wrapper/altera_ram_1p.vhd");
        _builder.newLine();
        _builder.append("vcom -93 -quiet -work work wrapper/altera_ram_2p.vhd");
        _builder.newLine();
        _builder.append("vcom -93 -quiet -work work wrapper/altera_rom.vhd");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work simulation/sim_package.vhd");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Compile interface components");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work interface/counter.vhd");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work interface/segment_display_conv.vhd");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work interface/segment_display_sel.vhd");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work interface/fps_eval.vhd");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Compile Shared components");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work share/vhdl/util_pkg.vhdl");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work share/vhdl/tce_util_pkg.vhdl");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work share/vhdl/rf_1wr_1rd_always_1_guarded_0.vhd");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work share/vhdl/mul.vhdl");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work share/vhdl/ldh_ldhu_ldq_ldqu_ldw_sth_stq_stw.vhdl");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work share/vhdl/add_and_eq_gt_gtu_ior_shl_shr_shru_sub_sxhw_sxqw_xor.vhdl");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work share/vhdl/stratix3_led_io_always_1.vhd");
    _builder.newLine();
    _builder.newLine();
    {
      EList<Processor> _processors = design.getProcessors();
      for(final Processor processor : _processors) {
        CharSequence _tcl = this.getTcl(processor);
        _builder.append(_tcl);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<Memory> _sharedMemories = design.getSharedMemories();
      for(final Memory memory : _sharedMemories) {
        CharSequence _tcl_1 = this.getTcl(memory);
        _builder.append(_tcl_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("# Network");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work top.vhd");
    _builder.newLine();
    _builder.append("vcom -93 -quiet -work work top_tb.vhd");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Simulate");
    _builder.newLine();
    _builder.append("vsim -novopt ");
    {
      boolean _isAltera_3 = this.fpga.isAltera();
      if (_isAltera_3) {
        _builder.append("-L altera_mf ");
      }
    }
    _builder.append("work.tb_top -t ps -do \"do wave.do;\"");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence getTcl(final Memory memory) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("vcom -93 -quiet -work work wrapper/dram_2p_");
    String _name = memory.getName();
    _builder.append(_name);
    _builder.append(".vhd");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence getTcl(final Processor processor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Compile processor ");
    String _name = processor.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_1 = processor.getName();
    _builder.append(_name_1);
    _builder.append("/tta/vhdl/");
    String _name_2 = processor.getName();
    _builder.append(_name_2);
    _builder.append("_tl_imem_mau_pkg.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_3 = processor.getName();
    _builder.append(_name_3);
    _builder.append("/tta/vhdl/");
    String _name_4 = processor.getName();
    _builder.append(_name_4);
    _builder.append("_tl_globals_pkg.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_5 = processor.getName();
    _builder.append(_name_5);
    _builder.append("/tta/vhdl/");
    String _name_6 = processor.getName();
    _builder.append(_name_6);
    _builder.append("_tl_params_pkg.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_7 = processor.getName();
    _builder.append(_name_7);
    _builder.append("/tta/vhdl/");
    String _name_8 = processor.getName();
    _builder.append(_name_8);
    _builder.append("_mem_constants_pkg.vhd");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_9 = processor.getName();
    _builder.append(_name_9);
    _builder.append("/tta/vhdl/");
    String _name_10 = processor.getName();
    _builder.append(_name_10);
    _builder.append("_tl.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_11 = processor.getName();
    _builder.append(_name_11);
    _builder.append("/tta/vhdl/");
    String _name_12 = processor.getName();
    _builder.append(_name_12);
    _builder.append(".vhd");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_13 = processor.getName();
    _builder.append(_name_13);
    _builder.append("/tta/gcu_ic/gcu_opcodes_pkg.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_14 = processor.getName();
    _builder.append(_name_14);
    _builder.append("/tta/gcu_ic/output_socket_");
    int _size = processor.getBuses().size();
    _builder.append(_size);
    _builder.append("_1.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_15 = processor.getName();
    _builder.append(_name_15);
    _builder.append("/tta/gcu_ic/output_socket_1_1.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_16 = processor.getName();
    _builder.append(_name_16);
    _builder.append("/tta/gcu_ic/input_socket_");
    int _size_1 = processor.getBuses().size();
    _builder.append(_size_1);
    _builder.append(".vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_17 = processor.getName();
    _builder.append(_name_17);
    _builder.append("/tta/gcu_ic/ifetch.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_18 = processor.getName();
    _builder.append(_name_18);
    _builder.append("/tta/gcu_ic/idecompressor.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_19 = processor.getName();
    _builder.append(_name_19);
    _builder.append("/tta/gcu_ic/ic.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.append("vcom -93 -quiet -work work ");
    String _name_20 = processor.getName();
    _builder.append(_name_20);
    _builder.append("/tta/gcu_ic/decoder.vhdl");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      boolean _isXilinx = this.fpga.isXilinx();
      if (_isXilinx) {
        _builder.append("vcom -93 -quiet -work work ");
        String _name_21 = processor.getName();
        _builder.append(_name_21);
        _builder.append("/tta/vhdl/dram_");
        String _name_22 = processor.getName();
        _builder.append(_name_22);
        _builder.append(".vhd");
        _builder.newLineIfNotEmpty();
        _builder.append("vcom -93 -quiet -work work ");
        String _name_23 = processor.getName();
        _builder.append(_name_23);
        _builder.append("/tta/vhdl/irom_");
        String _name_24 = processor.getName();
        _builder.append(_name_24);
        _builder.append(".vhd");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("exec cp -f ");
        String _name_25 = processor.getName();
        _builder.append(_name_25);
        _builder.append("/tta/vhdl/dram_");
        String _name_26 = processor.getName();
        _builder.append(_name_26);
        _builder.append(".mif . &");
        _builder.newLineIfNotEmpty();
        _builder.append("exec cp -f ");
        String _name_27 = processor.getName();
        _builder.append(_name_27);
        _builder.append("/tta/vhdl/irom_");
        String _name_28 = processor.getName();
        _builder.append(_name_28);
        _builder.append(".mif . &");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("exec cp -f wrapper/dram_");
        String _name_29 = processor.getName();
        _builder.append(_name_29);
        _builder.append(".mif . &");
        _builder.newLineIfNotEmpty();
        _builder.append("exec cp -f wrapper/irom_");
        String _name_30 = processor.getName();
        _builder.append(_name_30);
        _builder.append(".mif . &");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
