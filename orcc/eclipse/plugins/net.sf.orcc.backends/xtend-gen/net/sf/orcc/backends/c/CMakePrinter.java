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
package net.sf.orcc.backends.c;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.Map;
import net.sf.orcc.backends.BackendsConstants;
import net.sf.orcc.backends.CommonPrinter;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generate CMakeList.txt content
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class CMakePrinter extends CommonPrinter {
  protected Network network;
  
  protected boolean linkNativeLib;
  
  protected String linkNativeLibFolder;
  
  public Network setNetwork(final Network network) {
    return this.network = network;
  }
  
  @Override
  public void setOptions(final Map<String, Object> options) {
    super.setOptions(options);
    boolean _containsKey = options.containsKey(BackendsConstants.LINK_NATIVE_LIBRARY);
    if (_containsKey) {
      Object _get = options.get(BackendsConstants.LINK_NATIVE_LIBRARY);
      this.linkNativeLib = (((Boolean) _get)).booleanValue();
      Object _get_1 = options.get(BackendsConstants.LINK_NATIVE_LIBRARY_FOLDER);
      this.linkNativeLibFolder = ((String) _get_1);
    }
    if ((this.linkNativeLib && (!Objects.equal(this.linkNativeLibFolder, "")))) {
      this.linkNativeLib = true;
    } else {
      this.linkNativeLib = false;
    }
  }
  
  public CharSequence rootCMakeContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
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
    _builder.newLine();
    _builder.append("# Configure ouput folder for generated binary");
    _builder.newLine();
    _builder.append("set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/bin)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Definitions configured and used in subdirectories");
    _builder.newLine();
    _builder.append("set(extra_definitions)");
    _builder.newLine();
    _builder.append("set(extra_includes)");
    _builder.newLine();
    _builder.append("set(extra_libraries)");
    _builder.newLine();
    _builder.newLine();
    {
      if (this.linkNativeLib) {
        _builder.newLine();
        _builder.append("# Native lib");
        _builder.newLine();
        _builder.append("set(external_definitions)");
        _builder.newLine();
        _builder.append("set(external_include_paths)");
        _builder.newLine();
        _builder.append("set(external_library_paths)");
        _builder.newLine();
        _builder.append("set(external_libraries)");
        _builder.newLine();
        _builder.newLine();
        _builder.append("# All external vars should be set by the CMakeLists.txt inside the following folder.");
        _builder.newLine();
        _builder.append("add_subdirectory(");
        _builder.append(this.linkNativeLibFolder);
        _builder.append(" ");
        _builder.append(this.linkNativeLibFolder);
        _builder.append(")");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("if(external_definitions)");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("list(APPEND extra_definitions ${external_definitions})");
        _builder.newLine();
        _builder.append("endif()");
        _builder.newLine();
        _builder.newLine();
        _builder.append("if(external_include_paths)");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("list(APPEND extra_includes ${external_include_paths})");
        _builder.newLine();
        _builder.append("endif()");
        _builder.newLine();
        _builder.newLine();
        _builder.append("if(external_libraries)");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("list(APPEND extra_libraries ${external_libraries})");
        _builder.newLine();
        _builder.append("endif()");
        _builder.newLine();
        _builder.newLine();
        _builder.append("if(external_library_paths)");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("link_directories(${external_library_paths})");
        _builder.newLine();
        _builder.append("endif()");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.append("# Runtime libraries inclusion");
    _builder.newLine();
    _builder.append("include_directories(");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("${PROJECT_BINARY_DIR}/libs # to find config.h");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("libs/orcc-native/include");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("libs/orcc-runtime/include");
    _builder.newLine();
    _builder.append(")");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    CharSequence _addLibrariesSubdirs = this.addLibrariesSubdirs();
    _builder.append(_addLibrariesSubdirs);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * Goal of this method is to allow text produced to be extended
   * for specific usages (other backends)
   */
  protected CharSequence addLibrariesSubdirs() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Compile required libs");
    _builder.newLine();
    _builder.append("add_subdirectory(libs)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# Compile application");
    _builder.newLine();
    _builder.append("add_subdirectory(src)");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence srcCMakeContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# Generated from ");
    String _simpleName = this.network.getSimpleName();
    _builder.append(_simpleName);
    _builder.newLineIfNotEmpty();
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
    {
      EList<Port> _outputs = this.network.getOutputs();
      for(final Port child_2 : _outputs) {
        _builder.append("\t");
        String _name = child_2.getName();
        _builder.append(_name, "\t");
        _builder.append(".c");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Port> _inputs = this.network.getInputs();
      for(final Port child_3 : _inputs) {
        _builder.append("\t");
        String _name_1 = child_3.getName();
        _builder.append(_name_1, "\t");
        _builder.append(".c");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append(")");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# [MSVC] Ensure OpenCV imported targets are reachable in this file");
    _builder.newLine();
    _builder.append("# They may be imported in ${extra_libraries}");
    _builder.newLine();
    _builder.append("find_package(OpenCV QUIET)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("include_directories(${extra_includes})");
    _builder.newLine();
    _builder.append("add_definitions(${extra_definitions})");
    _builder.newLine();
    _builder.append("add_executable(");
    String _simpleName_2 = this.network.getSimpleName();
    _builder.append(_simpleName_2);
    _builder.append(" ${filenames})");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("# Build library without any external library required");
    _builder.newLine();
    _builder.append("target_link_libraries(");
    String _simpleName_3 = this.network.getSimpleName();
    _builder.append(_simpleName_3);
    _builder.append(" orcc-native orcc-runtime websockets cjson ${extra_libraries})");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
