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
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.df.Actor;
import net.sf.orcc.frontend.ActorTransformer;
import net.sf.orcc.frontend.UnitTransformer;
import net.sf.orcc.tests.util.CalTestsHelper;
import net.sf.orcc.tests.util.TestInterpreter;
import net.sf.orcc.util.Attributable;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(CalInjectorProvider.class)
@SuppressWarnings("all")
public class CalInterpreterTests extends CalTestsHelper {
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;
  
  /**
   * Transform the given AstEntity into its Actor / Unit equivalent
   */
  private Attributable transformEntity(final AstEntity entity) {
    AstActor _actor = entity.getActor();
    boolean _tripleNotEquals = (_actor != null);
    if (_tripleNotEquals) {
      return new ActorTransformer().doSwitch(entity.getActor());
    } else {
      AstUnit _unit = entity.getUnit();
      boolean _tripleNotEquals_1 = (_unit != null);
      if (_tripleNotEquals_1) {
        return new UnitTransformer().doSwitch(entity.getUnit());
      }
    }
    Assert.fail("The given AstEntity is not an Actor or a Unit.");
    return null;
  }
  
  /**
   * Run the specific test interpreter on the given Actor. Returns
   * a String with all content printed while this actor's execution.
   */
  private String runInterpreter(final Actor actor) {
    String _xblockexpression = null;
    {
      final TestInterpreter interpreter = new TestInterpreter(actor);
      interpreter.initialize();
      interpreter.schedule();
      _xblockexpression = interpreter.getOutput();
    }
    return _xblockexpression;
  }
  
  @Test
  public void testElseIf() {
    final AstEntity entity = this.parseFile("/test/pass/Elsif.cal");
    this._validationTestHelper.assertNoErrors(entity);
    Attributable _transformEntity = this.transformEntity(entity);
    final String resultString = this.runInterpreter(((Actor) _transformEntity));
    Assert.assertEquals("okok", resultString);
  }
  
  @Test
  public void testElseIfExpr() {
    final AstEntity entity = this.parseFile("/test/pass/ElsifExpr.cal");
    this._validationTestHelper.assertNoErrors(entity);
    Attributable _transformEntity = this.transformEntity(entity);
    final String resultString = this.runInterpreter(((Actor) _transformEntity));
    Assert.assertEquals("result = 0", resultString);
  }
  
  @Test
  public void testElseIfStateVar() {
    final AstEntity entity = this.parseFile("/test/pass/ElsifStateVar.cal");
    this._validationTestHelper.assertNoErrors(entity);
    Attributable _transformEntity = this.transformEntity(entity);
    final String resultString = this.runInterpreter(((Actor) _transformEntity));
    Assert.assertEquals("ok", resultString);
  }
  
  @Test
  public void testInitStateVarFunction() {
    final AstEntity entity = this.parseFile("/test/pass/InitStateVarFunction.cal");
    this._validationTestHelper.assertNoErrors(entity);
    Attributable _transformEntity = this.transformEntity(entity);
    final String resultString = this.runInterpreter(((Actor) _transformEntity));
    Assert.assertEquals("pp = 8", resultString);
  }
  
  @Test
  public void testExecShadow() {
    final AstEntity entity = this.parseFile("/test/pass/Shadowing.cal");
    this._validationTestHelper.assertNoErrors(entity);
    Attributable _transformEntity = this.transformEntity(entity);
    final String resultString = this.runInterpreter(((Actor) _transformEntity));
    Assert.assertEquals("x = 0", resultString);
  }
  
  @Test
  public void testExecWhile() throws Exception {
    final AstEntity entity = this.parseFile("/test/pass/CodegenWhile.cal");
    this._validationTestHelper.assertNoErrors(entity);
    Attributable _transformEntity = this.transformEntity(entity);
    final String resultString = this.runInterpreter(((Actor) _transformEntity));
    Assert.assertEquals("idx is 60", resultString);
  }
}
