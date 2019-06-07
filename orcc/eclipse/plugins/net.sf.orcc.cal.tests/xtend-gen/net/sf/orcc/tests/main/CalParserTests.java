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
import java.util.Collections;
import net.sf.orcc.cal.CalInjectorProvider;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.services.Evaluator;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.tests.util.CalTestsHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(CalInjectorProvider.class)
@SuppressWarnings("all")
public class CalParserTests extends CalTestsHelper {
  private final IrFactory irFact = IrFactory.eINSTANCE;
  
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;
  
  @Test
  public void testSimpleActor() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor SimpleActor() ==> :");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder);
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertNoErrors(entity);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testActor_initializeOnly() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor InitializePattern() int I ==> int O :");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("initialize ==> O:[42]");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("end");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder);
      Assert.assertNotNull(entity);
      this._validationTestHelper.assertNoErrors(entity);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testIntTypes() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor TypeInt() ==> :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("uint(size=3) x := 7;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int(size=15) y := x + 1;");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder);
      final EList<Variable> stateVars = entity.getActor().getStateVariables();
      final Variable x = stateVars.get(0);
      final Variable y = stateVars.get(1);
      final TypeUint expected = this.irFact.createTypeUint(3);
      Assert.assertEquals(expected, Typer.getType(x));
      Assert.assertEquals(expected, Typer.getType(x.getValue()));
      Assert.assertEquals(this.irFact.createTypeUint(4), Typer.getType(y.getValue()));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testBigIntegers() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor TypeInt() ==> :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("uint(size=64) bigint := 9223372036854775808;");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(entity);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testGenerator() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor Generator() ==> :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int stateVar[1][2][3] :=");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("[");
      _builder.newLine();
      _builder.append("\t  ");
      _builder.append("[");
      _builder.newLine();
      _builder.append("\t    ");
      _builder.append("[ i * j * k : for int i in 1 .. 3 ] : for int j in 1 .. 2");
      _builder.newLine();
      _builder.append("\t  ");
      _builder.append("] : for int k in 1 .. 1");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("];");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      final AstEntity entity = this._parseHelper.parse(_builder);
      final Expression varValue = Evaluator.getValue(IterableExtensions.<Variable>head(entity.getActor().getStateVariables()));
      ExprInt _createExprInt = this.irFact.createExprInt(1);
      ExprInt _createExprInt_1 = this.irFact.createExprInt(2);
      ExprInt _createExprInt_2 = this.irFact.createExprInt(3);
      final ExprList l11 = this.irFact.createExprList(
        Collections.<Expression>unmodifiableList(CollectionLiterals.<Expression>newArrayList(((Expression) _createExprInt), ((Expression) _createExprInt_1), ((Expression) _createExprInt_2))));
      ExprInt _createExprInt_3 = this.irFact.createExprInt(2);
      ExprInt _createExprInt_4 = this.irFact.createExprInt(4);
      ExprInt _createExprInt_5 = this.irFact.createExprInt(6);
      final ExprList l12 = this.irFact.createExprList(
        Collections.<Expression>unmodifiableList(CollectionLiterals.<Expression>newArrayList(((Expression) _createExprInt_3), ((Expression) _createExprInt_4), ((Expression) _createExprInt_5))));
      final ExprList l1 = this.irFact.createExprList();
      l1.getValue().add(l11);
      l1.getValue().add(l12);
      final ExprList expected = this.irFact.createExprList();
      expected.getValue().add(l1);
      Assert.assertTrue(EcoreUtil.equals(expected, varValue));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
