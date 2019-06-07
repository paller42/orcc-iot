/**
 * Copyright (c) 2013, University of Rennes 1 / IRISA
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
 *   * Neither the name of the University of Rennes 1 / IRISA nor the names of its
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
package net.sf.orcc.simulators.profiling;

import net.sf.orcc.df.Connection;
import net.sf.orcc.simulators.slow.SimulatorFifo;
import net.sf.orcc.tools.stats.StatisticsPrinter;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Generate statistics and profiling information about an application.
 * 
 * @author Hervé Yviquel
 */
@SuppressWarnings("all")
public class ProfilingPrinter extends StatisticsPrinter {
  @Override
  public CharSequence getConnectionsHeader() {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _connectionsHeader = super.getConnectionsHeader();
    _builder.append(_connectionsHeader);
    _builder.append(", Traffic");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public CharSequence getStats(final Connection conn) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _stats = super.getStats(conn);
    _builder.append(_stats);
    _builder.append(", ");
    long _traffic = this.getTraffic(conn);
    _builder.append(_traffic);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private long getTraffic(final Connection conn) {
    Object _objectValue = conn.getAttribute("fifo").getObjectValue();
    final SimulatorFifo fifo = ((SimulatorFifo) _objectValue);
    return fifo.getTraffic();
  }
}
