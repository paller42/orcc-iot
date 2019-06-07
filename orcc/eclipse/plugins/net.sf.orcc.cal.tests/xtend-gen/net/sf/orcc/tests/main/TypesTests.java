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
import net.sf.orcc.cal.CalInjectorProvider;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.tests.util.CalTestsHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(CalInjectorProvider.class)
@SuppressWarnings("all")
public class TypesTests extends CalTestsHelper {
  private AstEntity entity;
  
  private AstActor actor;
  
  private final IrFactory irFact = IrFactory.eINSTANCE;
  
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;
  
  private Variable stateVar(final AstActor actor, final String varName) {
    EList<Variable> _stateVariables = actor.getStateVariables();
    for (final Variable variable : _stateVariables) {
      boolean _equals = variable.getName().equals(varName);
      if (_equals) {
        return variable;
      }
    }
    return null;
  }
  
  @Before
  public void initializeActor() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("actor TypesTester() ==> :");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int \t\t\ti32\t:= -123;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int(size=16) \ti16\t:= -111;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int(size=4) \ti4\t:= -1;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int(size=46) \ti46\t:= 145147;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int(size=64) \ti64\t:= 145147;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("uint\t\t\tu32\t:= 456;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("uint(size=16)\tu16\t:= 9999;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("uint(size=60)\tu60\t:= 12345678900;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("uint(size=1)\tu1\t:= 1;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("float\t\t\tfl\t:= 5.2;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("half\t\t\tha\t:= 5.2;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("double\t\t\tdb\t:= 5.2;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("bool\t\t\tbo\t:= true;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Additions");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int \t\t\tres1\t= i32 + i32;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("uint\t\t\tres2\t= u16 + u16;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres3\t= i16 + u16;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres4\t= u60 + i16;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("float\t\t\tres5\t= i16 + fl;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("half\t\t\tres6\t= i32 + ha;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("double\t\t\tres7\t= u60 + db;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Subtractions");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres8\t= i32 - 4;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres9\t= i32 - i32;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres10\t= u32 - i32;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("float\t\t\tres11\t= i32 - fl;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Divisions");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres12\t= i32 / 3;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres13\t= i32 / i32;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres14\t= u32 / i16;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("float\t\t\tres15\t= i32 / ha;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("double\t\t\tres16\t= db / i16;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int\t\t\t\tres17\t= i32 / i4;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int\t\t\t\tres18\t= i64 / u1;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int\t\t\t\tres19\t= i4 / u1;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Multiplications");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres20\t= i32 * 20;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres21\t= u16 * 62;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres22\t= i32 * u16;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres23\t= i4 * u60;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("int\t\t\tres24\t= u1 * i4;");
      _builder.newLine();
      _builder.append("\t ");
      _builder.append("float\t\t\tres25\t= ha * u16;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// LSHIFT");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int\t\t\t\tres26\t= i16 << 5;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("uint\t\t\tres27\t= u32 << i4;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int\t\t\t\tres28\t= i4 << i4;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// RSHIFT");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int\t\t\t\tres29\t= i16 >> 5;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("uint\t\t\tres30\t= u32 >> i4;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int\t\t\t\tres31\t= i4 >> i4;");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      this.entity = this._parseHelper.parse(_builder);
      this.actor = this.entity.getActor();
      return;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void checkEntityIssues() {
    Assert.assertNotNull(this.entity);
    this._validationTestHelper.assertNoErrors(this.entity);
  }
  
  @Test
  public void testAdditions() {
    final AstExpression a = this.stateVar(this.actor, "res1").getValue();
    final AstExpression b = this.stateVar(this.actor, "res2").getValue();
    final AstExpression c = this.stateVar(this.actor, "res3").getValue();
    final AstExpression d = this.stateVar(this.actor, "res4").getValue();
    final AstExpression e = this.stateVar(this.actor, "res5").getValue();
    final AstExpression f = this.stateVar(this.actor, "res6").getValue();
    final AstExpression g = this.stateVar(this.actor, "res7").getValue();
    Assert.assertEquals(this.irFact.createTypeInt(33), Typer.getType(a));
    Assert.assertEquals(this.irFact.createTypeUint(17), Typer.getType(b));
    Assert.assertEquals(this.irFact.createTypeInt(18), Typer.getType(c));
    Assert.assertEquals(this.irFact.createTypeInt(62), Typer.getType(d));
    Assert.assertEquals(this.irFact.createTypeFloat(), Typer.getType(e));
    Assert.assertEquals(this.irFact.createTypeFloat(16), Typer.getType(f));
    Assert.assertEquals(this.irFact.createTypeFloat(64), Typer.getType(g));
  }
  
  @Test
  public void testSubtractions() {
    final AstExpression a = this.stateVar(this.actor, "res8").getValue();
    final AstExpression b = this.stateVar(this.actor, "res9").getValue();
    final AstExpression c = this.stateVar(this.actor, "res10").getValue();
    final AstExpression d = this.stateVar(this.actor, "res11").getValue();
    Assert.assertEquals(this.irFact.createTypeInt(32), Typer.getType(a));
    Assert.assertEquals(this.irFact.createTypeInt(32), Typer.getType(b));
    Assert.assertEquals(this.irFact.createTypeInt(33), Typer.getType(c));
    Assert.assertEquals(this.irFact.createTypeFloat(), Typer.getType(d));
  }
  
  @Test
  public void testDivisions() {
    final AstExpression a = this.stateVar(this.actor, "res12").getValue();
    final AstExpression b = this.stateVar(this.actor, "res13").getValue();
    final AstExpression c = this.stateVar(this.actor, "res14").getValue();
    final AstExpression d = this.stateVar(this.actor, "res15").getValue();
    final AstExpression e = this.stateVar(this.actor, "res16").getValue();
    final AstExpression f = this.stateVar(this.actor, "res17").getValue();
    final AstExpression g = this.stateVar(this.actor, "res18").getValue();
    final AstExpression h = this.stateVar(this.actor, "res19").getValue();
    Assert.assertEquals(this.irFact.createTypeInt(32), Typer.getType(a));
    Assert.assertEquals(this.irFact.createTypeInt(32), Typer.getType(b));
    Assert.assertEquals(this.irFact.createTypeUint(32), Typer.getType(c));
    Assert.assertEquals(this.irFact.createTypeFloat(16), Typer.getType(d));
    Assert.assertEquals(this.irFact.createTypeFloat(64), Typer.getType(e));
    Assert.assertEquals(this.irFact.createTypeInt(32), Typer.getType(f));
    Assert.assertEquals(this.irFact.createTypeInt(64), Typer.getType(g));
    Assert.assertEquals(this.irFact.createTypeInt(4), Typer.getType(h));
  }
  
  @Test
  public void testMultiplications() {
    final AstExpression a = this.stateVar(this.actor, "res20").getValue();
    final AstExpression b = this.stateVar(this.actor, "res21").getValue();
    final AstExpression c = this.stateVar(this.actor, "res22").getValue();
    final AstExpression d = this.stateVar(this.actor, "res23").getValue();
    final AstExpression e = this.stateVar(this.actor, "res24").getValue();
    final AstExpression f = this.stateVar(this.actor, "res25").getValue();
    Assert.assertEquals(this.irFact.createTypeInt(37), Typer.getType(a));
    Assert.assertEquals(this.irFact.createTypeUint(22), Typer.getType(b));
    Assert.assertEquals(this.irFact.createTypeInt(48), Typer.getType(c));
    Assert.assertEquals(this.irFact.createTypeInt(64), Typer.getType(d));
    Assert.assertEquals(this.irFact.createTypeInt(5), Typer.getType(e));
    Assert.assertEquals(this.irFact.createTypeFloat(16), Typer.getType(f));
  }
  
  @Test
  public void testLShift() {
    final AstExpression a = this.stateVar(this.actor, "res26").getValue();
    final AstExpression b = this.stateVar(this.actor, "res27").getValue();
    final AstExpression c = this.stateVar(this.actor, "res28").getValue();
    Assert.assertEquals(this.irFact.createTypeInt(23), Typer.getType(a));
    Assert.assertEquals(this.irFact.createTypeInt(47), Typer.getType(b));
    Assert.assertEquals(this.irFact.createTypeInt(19), Typer.getType(c));
  }
  
  @Test
  public void testRShift() {
    final AstExpression a = this.stateVar(this.actor, "res29").getValue();
    final AstExpression b = this.stateVar(this.actor, "res30").getValue();
    final AstExpression c = this.stateVar(this.actor, "res31").getValue();
    Assert.assertEquals(this.irFact.createTypeInt(16), Typer.getType(a));
    Assert.assertEquals(this.irFact.createTypeUint(32), Typer.getType(b));
    Assert.assertEquals(this.irFact.createTypeInt(4), Typer.getType(c));
  }
}
