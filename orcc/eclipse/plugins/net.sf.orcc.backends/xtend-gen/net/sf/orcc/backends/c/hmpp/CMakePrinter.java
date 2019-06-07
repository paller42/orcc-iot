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
package net.sf.orcc.backends.c.hmpp;

import com.google.common.collect.Iterables;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class CMakePrinter extends net.sf.orcc.backends.c.CMakePrinter {
  @Override
  public CharSequence rootCMakeContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Generated from ");
    String _simpleName = this.network.getSimpleName();
    _builder.append(_simpleName);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("cmake_minimum_required (VERSION 2.6)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("project (");
    String _simpleName_1 = this.network.getSimpleName();
    _builder.append(_simpleName_1);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("# Output folder");
    _builder.newLine();
    _builder.append("set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/bin)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Libraries folder");
    _builder.newLine();
    _builder.append("set(LIBS_DIR ${CMAKE_SOURCE_DIR}/libs)");
    _builder.newLine();
    _builder.append("set(SRC_DIR ${CMAKE_SOURCE_DIR}/src)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Runtime libraries inclusion");
    _builder.newLine();
    _builder.append("set(ORCC_INCLUDE_DIR ${LIBS_DIR}/orcc/include)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("option(USE_HMPP True)");
    _builder.newLine();
    _builder.append("# Hmpp compiler");
    _builder.newLine();
    _builder.append("if(USE_HMPP)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("find_program(HMPP_COMPILER hmpp)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if(\"${HMPP_COMPILER}\" STREQUAL \"HMPP_COMPILER-NOTFOUND\")");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("message(FATAL_ERROR \"HMPP compiler not found, please locate it manually by setting HMPP_COMPILER\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("endif()");
    _builder.newLine();
    _builder.append("endif()");
    _builder.newLine();
    _builder.newLine();
    CharSequence _addLibrariesSubdirs = this.addLibrariesSubdirs();
    _builder.append(_addLibrariesSubdirs);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  public CharSequence srcCMakeContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Generated from ");
    String _simpleName = this.network.getSimpleName();
    _builder.append(_simpleName);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("cmake_minimum_required (VERSION 2.6)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("set(filenames");
    _builder.newLine();
    _builder.append("\t");
    String _simpleName_1 = this.network.getSimpleName();
    _builder.append(_simpleName_1, "\t");
    _builder.append(".c");
    _builder.newLineIfNotEmpty();
    {
      final Function1<Instance, Boolean> _function = new Function1<Instance, Boolean>() {
        @Override
        public Boolean apply(final Instance it) {
          boolean _isNative = it.getActor().isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Instance> _filter = IterableExtensions.<Instance>filter(this.getActorInstances(this.network.getChildren()), _function);
      for(final Instance child : _filter) {
        _builder.append("\t");
        String _label = child.getLabel();
        _builder.append(_label, "\t");
        _builder.append(".c");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      final Function1<Actor, Boolean> _function_1 = new Function1<Actor, Boolean>() {
        @Override
        public Boolean apply(final Actor it) {
          boolean _isNative = it.isNative();
          return Boolean.valueOf((!_isNative));
        }
      };
      Iterable<Actor> _filter_1 = IterableExtensions.<Actor>filter(Iterables.<Actor>filter(this.network.getChildren(), Actor.class), _function_1);
      for(final Actor child_1 : _filter_1) {
        _builder.append("\t");
        String _label_1 = child_1.getLabel();
        _builder.append(_label_1, "\t");
        _builder.append(".c");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append(")");
    _builder.newLine();
    _builder.newLine();
    _builder.append("if(USE_HMPP)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("set(CMAKE_C_FLAGS \"${CMAKE_C_COMPILER} ${CMAKE_C_FLAGS}\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("set(CMAKE_CXX_FLAGS \"${CMAKE_CXX_COMPILER} ${CMAKE_CXX_FLAGS}\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("set(CMAKE_C_COMPILER ${HMPP_COMPILER})");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("set(CMAKE_CXX_COMPILER ${HMPP_COMPILER})");
    _builder.newLine();
    _builder.append("endif()");
    _builder.newLine();
    _builder.newLine();
    _builder.append("include_directories(${ORCC_INCLUDE_DIR} ${ROXML_INCLUDE_DIR} ${SDL_INCLUDE_DIR})");
    _builder.newLine();
    _builder.newLine();
    _builder.append("add_executable(");
    String _simpleName_2 = this.network.getSimpleName();
    _builder.append(_simpleName_2);
    _builder.append(" ${filenames})");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("if(USE_HMPP)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("add_custom_command(TARGET ");
    String _simpleName_3 = this.network.getSimpleName();
    _builder.append(_simpleName_3, "\t");
    _builder.append(" POST_BUILD");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("COMMAND cp ${CMAKE_CURRENT_BINARY_DIR}/*.hmg* ${EXECUTABLE_OUTPUT_PATH}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(")");
    _builder.newLine();
    _builder.append("endif()");
    _builder.newLine();
    _builder.newLine();
    _builder.append("target_link_libraries(");
    String _simpleName_4 = this.network.getSimpleName();
    _builder.append(_simpleName_4);
    _builder.append(" orcc)");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("# Build library without any external library required (SDL, pthread, etc)");
    _builder.newLine();
    _builder.append("if(NOT NO_EXTERNAL_DEPENDENCIES)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("target_link_libraries(");
    String _simpleName_5 = this.network.getSimpleName();
    _builder.append(_simpleName_5, "\t");
    _builder.append(" ${CMAKE_THREAD_LIBS_INIT})");
    _builder.newLineIfNotEmpty();
    _builder.append("endif(NOT NO_EXTERNAL_DEPENDENCIES)");
    _builder.newLine();
    return _builder;
  }
}
