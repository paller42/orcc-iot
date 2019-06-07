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
package net.sf.orcc.backends.promela;

import java.math.BigInteger;
import net.sf.orcc.backends.c.CTemplate;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Default C Printer
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public abstract class PromelaTemplate extends CTemplate {
  /**
   * Types
   */
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("bool");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeInt(final TypeInt type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("int");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeUint(final TypeUint type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("int");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeFloat(final TypeFloat type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("int");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeList(final TypeList typeList) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(typeList.getInnermostType());
    _builder.append(_doSwitch);
    return _builder;
  }
  
  @Override
  public CharSequence caseExprFloat(final ExprFloat object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("0");
    return _builder;
  }
  
  @Override
  public CharSequence caseExprList(final ExprList object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("1");
    return _builder;
  }
  
  /**
   * {«object.value.join(", ")[doSwitch]»}
   */
  @Override
  public CharSequence caseExprInt(final ExprInt object) {
    StringConcatenation _builder = new StringConcatenation();
    BigInteger _value = object.getValue();
    _builder.append(_value);
    {
      boolean _isLong = object.isLong();
      if (_isLong) {
        _builder.append("L");
      }
    }
    return _builder;
  }
  
  @Override
  public CharSequence caseExprBool(final ExprBool object) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isValue = object.isValue();
      if (_isValue) {
        _builder.append("1");
      } else {
        _builder.append("0");
      }
    }
    return _builder;
  }
}
