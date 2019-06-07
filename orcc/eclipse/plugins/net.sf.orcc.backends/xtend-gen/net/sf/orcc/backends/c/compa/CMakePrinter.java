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
package net.sf.orcc.backends.c.compa;

import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class CMakePrinter {
  public CharSequence rootCMakeContent(final String entityName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Generated from ");
    _builder.append(entityName);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("cmake_minimum_required (VERSION 2.6)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("project (");
    _builder.append(entityName);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("# Include the subdirectory where cmake modules can be found.");
    _builder.newLine();
    _builder.append("set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} \"${CMAKE_SOURCE_DIR}/../cmake/Modules/\")");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Tries to find the ORCC library.");
    _builder.newLine();
    _builder.append("find_package(LibORCC REQUIRED)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Tries to find the Xilinx libraries.");
    _builder.newLine();
    _builder.append("find_package(LibXil REQUIRED)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Runtime libraries inclusion");
    _builder.newLine();
    _builder.append("include_directories(${LibORCC_INCLUDE_DIRS} ${LibXil_INCLUDE_DIRS})");
    _builder.newLine();
    _builder.append("set(LIBS ${LIBS} ${LibORCC_LIBRARIES} ${LibXil_LIBRARIES})");
    _builder.newLine();
    _builder.newLine();
    _builder.append("set(filenames");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(entityName, "\t");
    _builder.append(".c");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("platform.c");
    _builder.newLine();
    _builder.append(")");
    _builder.newLine();
    _builder.newLine();
    _builder.append("add_executable(");
    _builder.append(entityName);
    _builder.append(".elf ${filenames})");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("# Link to orcc library.");
    _builder.newLine();
    _builder.append("TARGET_LINK_LIBRARIES(");
    _builder.append(entityName);
    _builder.append(".elf ${LIBS})");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
