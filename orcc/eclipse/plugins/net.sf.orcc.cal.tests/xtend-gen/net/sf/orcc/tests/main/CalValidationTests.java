/**
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.tests.main;

import com.google.inject.Inject;
import net.sf.orcc.cal.CalDiagnostic;
import net.sf.orcc.cal.CalInjectorProvider;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.validation.CalValidator;
import net.sf.orcc.cal.validation.StructuralValidator;
import net.sf.orcc.cal.validation.TypeValidator;
import net.sf.orcc.cal.validation.WarningValidator;
import net.sf.orcc.tests.util.CalTestsHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(CalInjectorProvider.class)
@SuppressWarnings("all")
public class CalValidationTests extends CalTestsHelper {
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;
  
  @Inject
  protected CalValidator defaultValidator;
  
  @Inject
  protected StructuralValidator structuralValidator;
  
  @Inject
  protected TypeValidator typesValidator;
  
  @Inject
  protected WarningValidator warningsValidator;
  
  /**
   * Test the given EObject with the available validators. The returned
   * AssertableDiagnostics object can be checked for specific errors/warning
   * set from them
   */
  private AssertableDiagnostics defaultValidation(final EObject object) {
    return new ValidatorTester<CalValidator>(this.defaultValidator, this.injector).validate(object);
  }
  
  private AssertableDiagnostics structuralValidation(final EObject object) {
    return new ValidatorTester<StructuralValidator>(this.structuralValidator, this.injector).validate(object);
  }
  
  private AssertableDiagnostics typesValidation(final EObject object) {
    return new ValidatorTester<TypeValidator>(this.typesValidator, this.injector).validate(object);
  }
  
  private AssertableDiagnostics warningsValidation(final EObject object) {
    return new ValidatorTester<WarningValidator>(this.warningsValidator, this.injector).validate(object);
  }
  
  @Test
  public void testInvalidFSM() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor BadFsm() ==> :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("action1: action ==> end");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("schedule fsm State1:");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("State1 (action1) --> State2;");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("State1 (action1) --> State3;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder);
      this._validationTestHelper.assertError(entity, CalPackage.Literals.AST_TRANSITION, Diagnostic.LINKING_DIAGNOSTIC);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testParamIsConstant() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor Param(int param) ==> :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("action ==>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("do");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("param := param + 1;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder, URI.createPlatformResourceURI("Param.cal", true), this.resourceSetProvider.get());
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertNoErrors(entity);
      this.typesValidation(entity).assertError(CalDiagnostic.ERROR_CONSTANT_ASSIGNATION, "param is not assignable");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testPattern1() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor Pattern1() int I ==> int O :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("action O:[o] ==>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder);
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertError(entity, CalPackage.Literals.INPUT_PATTERN, Diagnostic.LINKING_DIAGNOSTIC);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testPattern2() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor Pattern2() int I ==> int O :");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("action I:[42] ==>");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder);
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertError(entity, CalPackage.Literals.INPUT_PATTERN, Diagnostic.SYNTAX_DIAGNOSTIC);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testPattern3() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor Pattern3() int I ==> :");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("action I:[x], I:[y] ==>");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder, URI.createPlatformResourceURI("Pattern3.cal", true), this.resourceSetProvider.get());
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertNoErrors(entity);
      this.defaultValidation(entity).assertOK();
      this.structuralValidation(entity).assertError(CalDiagnostic.ERROR_DUPLICATE_PORT_REFERENCE, "duplicate reference to port I");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testPattern4() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor Pattern4() ==> int O :");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("action ==> O:[1], O:[2]");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder, URI.createPlatformResourceURI("Pattern4.cal", true), this.resourceSetProvider.get());
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertNoErrors(entity);
      this.defaultValidation(entity).assertOK();
      this.structuralValidation(entity).assertError(CalDiagnostic.ERROR_DUPLICATE_PORT_REFERENCE, "duplicate reference to port O");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testTypeError1() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("unit TypeError1 :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("function abc(uint c) --> bool :");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("c > 4");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("end");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("procedure buggy()");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("var");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("uint a[3][2],");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("bool b := abc(a[2])");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("begin");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder, URI.createPlatformResourceURI("TypeError1.cal", true), this.resourceSetProvider.get());
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertNoErrors(entity);
      this.defaultValidation(entity).assertOK();
      this.typesValidation(entity).assertError(CalDiagnostic.ERROR_TYPE_CONVERSION, 
        "Type mismatch: cannot convert from List");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testTypeError2() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor TypeError2() ==> :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("@native procedure compare_init() end");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("@native procedure compare_NBytes(uint(size=8) outTable[1], uint(size=12) nbTokenToRead) end");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("readByte : action ==>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("var");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("int(size=8)  tmp := 17");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("do");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("compare_NBytes(tmp, 1);");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder, URI.createPlatformResourceURI("TypeError2.cal", true), this.resourceSetProvider.get());
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertNoErrors(entity);
      this.defaultValidation(entity).assertOK();
      this.typesValidation(entity).assertError(CalDiagnostic.ERROR_TYPE_CONVERSION, 
        "Type mismatch: cannot convert from int");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testTypeError3() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor TypeError3 () ==> :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int(size=-42) x;");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder, URI.createPlatformResourceURI("TypeError3.cal", true), this.resourceSetProvider.get());
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertNoErrors(entity);
      this.defaultValidation(entity).assertOK();
      this.typesValidation(entity).assertError(CalDiagnostic.ERROR_TYPE_SYNTAX, 
        "This size must evaluate to a compile-time constant greater than zero");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testWarningVariableUnused() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor UnusedVariable() ==> :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int aStateVariable := 8;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("action1: action ==>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("do");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("print(\"something\");");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder);
      this._validationTestHelper.assertNoIssues(entity);
      this.warningsValidation(entity).assertWarning(CalDiagnostic.WARNING_UNUSED, "The variable aStateVariable is never used");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
