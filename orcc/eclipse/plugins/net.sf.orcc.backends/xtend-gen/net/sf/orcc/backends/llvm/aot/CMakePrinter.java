/**
 * Copyright (c) 2012, IETR/INSA of Rennes
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
package net.sf.orcc.backends.llvm.aot;

import net.sf.orcc.graph.Vertex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Generate CMakeList.txt content
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class CMakePrinter extends net.sf.orcc.backends.c.CMakePrinter {
  /**
   * Return CMakeList's content to write in the root target folder
   */
  @Override
  public CharSequence rootCMakeContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Generated from ");
    String _simpleName = this.network.getSimpleName();
    _builder.append(_simpleName);
    _builder.newLineIfNotEmpty();
    _builder.append("cmake_minimum_required (VERSION 2.6)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Default compiler must be clang");
    _builder.newLine();
    _builder.append("set(CMAKE_C_COMPILER \"clang\")");
    _builder.newLine();
    _builder.append("project (");
    String _simpleName_1 = this.network.getSimpleName();
    _builder.append(_simpleName_1);
    _builder.append(" C)");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("# Configure specific flags for clang (according to selected build type)");
    _builder.newLine();
    _builder.append("if(NOT \"${CMAKE_BUILD_TYPE}\" STREQUAL \"\")");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("string(TOUPPER ${CMAKE_BUILD_TYPE} CMBT)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("set(CLANG_FLAGS ${CMAKE_C_FLAGS_${CMBT}})");
    _builder.newLine();
    _builder.append("endif()");
    _builder.newLine();
    _builder.newLine();
    _builder.append("message(STATUS \"Clang FLAGS : \" ${CLANG_FLAGS})");
    _builder.newLine();
    _builder.append("separate_arguments(CLANG_FLAGS)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Output folder");
    _builder.newLine();
    _builder.append("set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/bin)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Runtime libraries inclusion");
    _builder.newLine();
    _builder.append("include_directories(libs/orcc-native/include)");
    _builder.newLine();
    _builder.append("include_directories(libs/orcc-runtime/include)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Compile libraries");
    _builder.newLine();
    _builder.append("add_subdirectory(libs)");
    _builder.newLine();
    _builder.append("# Compile application");
    _builder.newLine();
    _builder.append("add_subdirectory(src)");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Return CMakeList's content to write in the src subdirectory
   */
  @Override
  public CharSequence srcCMakeContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Generated from ");
    String _simpleName = this.network.getSimpleName();
    _builder.append(_simpleName);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("set(");
    String _simpleName_1 = this.network.getSimpleName();
    _builder.append(_simpleName_1);
    _builder.append("_SRCS");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _simpleName_2 = this.network.getSimpleName();
    _builder.append(_simpleName_2, "\t");
    _builder.append(".ll");
    _builder.newLineIfNotEmpty();
    {
      EList<Vertex> _children = this.network.getChildren();
      for(final Vertex child : _children) {
        _builder.append("\t");
        String _label = child.getLabel();
        _builder.append(_label, "\t");
        _builder.append(".ll");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append(")");
    _builder.newLine();
    _builder.newLine();
    _builder.append("foreach(_infile ${");
    String _simpleName_3 = this.network.getSimpleName();
    _builder.append(_simpleName_3);
    _builder.append("_SRCS})");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("string(REPLACE \".ll\" ${CMAKE_C_OUTPUT_EXTENSION} _outfile ${_infile})");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("set(_inpath ${CMAKE_CURRENT_SOURCE_DIR}/${_infile})");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("set(_outpath ${CMAKE_CURRENT_BINARY_DIR}/${_outfile})");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("add_custom_command(");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("OUTPUT ${_outpath}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("COMMAND ${CMAKE_C_COMPILER} ${CLANG_FLAGS} -c ${_inpath} -o ${_outpath}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("DEPENDS ${_inpath}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("COMMENT \"Building object ${_outfile} from LLVM bytecode\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append(")");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("list(APPEND ");
    String _simpleName_4 = this.network.getSimpleName();
    _builder.append(_simpleName_4, "    ");
    _builder.append("_OBJS ${_outpath})");
    _builder.newLineIfNotEmpty();
    _builder.append("endforeach()");
    _builder.newLine();
    _builder.newLine();
    _builder.append("add_executable(");
    String _simpleName_5 = this.network.getSimpleName();
    _builder.append(_simpleName_5);
    _builder.append(" ${");
    String _simpleName_6 = this.network.getSimpleName();
    _builder.append(_simpleName_6);
    _builder.append("_OBJS} ${");
    String _simpleName_7 = this.network.getSimpleName();
    _builder.append(_simpleName_7);
    _builder.append("_SRCS})");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("set_target_properties(");
    String _simpleName_8 = this.network.getSimpleName();
    _builder.append(_simpleName_8);
    _builder.append(" PROPERTIES LINKER_LANGUAGE C)");
    _builder.newLineIfNotEmpty();
    _builder.append("target_link_libraries(");
    String _simpleName_9 = this.network.getSimpleName();
    _builder.append(_simpleName_9);
    _builder.append(" orcc-runtime orcc-native ${SDL_LIBRARY})");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
