/**
 */
package net.sf.orcc.cal.cal.util;

import net.sf.orcc.cal.cal.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.cal.cal.CalPackage
 * @generated
 */
public class CalAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static CalPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CalAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = CalPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CalSwitch<Adapter> modelSwitch =
    new CalSwitch<Adapter>()
    {
      @Override
      public Adapter caseAstEntity(AstEntity object)
      {
        return createAstEntityAdapter();
      }
      @Override
      public Adapter caseImport(Import object)
      {
        return createImportAdapter();
      }
      @Override
      public Adapter caseAstUnit(AstUnit object)
      {
        return createAstUnitAdapter();
      }
      @Override
      public Adapter caseVariable(Variable object)
      {
        return createVariableAdapter();
      }
      @Override
      public Adapter caseAstActor(AstActor object)
      {
        return createAstActorAdapter();
      }
      @Override
      public Adapter caseAstPort(AstPort object)
      {
        return createAstPortAdapter();
      }
      @Override
      public Adapter caseFunction(Function object)
      {
        return createFunctionAdapter();
      }
      @Override
      public Adapter caseAstProcedure(AstProcedure object)
      {
        return createAstProcedureAdapter();
      }
      @Override
      public Adapter caseAstTag(AstTag object)
      {
        return createAstTagAdapter();
      }
      @Override
      public Adapter caseInequality(Inequality object)
      {
        return createInequalityAdapter();
      }
      @Override
      public Adapter casePriority(Priority object)
      {
        return createPriorityAdapter();
      }
      @Override
      public Adapter caseScheduleFsm(ScheduleFsm object)
      {
        return createScheduleFsmAdapter();
      }
      @Override
      public Adapter caseFsm(Fsm object)
      {
        return createFsmAdapter();
      }
      @Override
      public Adapter caseAstTransition(AstTransition object)
      {
        return createAstTransitionAdapter();
      }
      @Override
      public Adapter caseExternalTarget(ExternalTarget object)
      {
        return createExternalTargetAdapter();
      }
      @Override
      public Adapter caseAstState(AstState object)
      {
        return createAstStateAdapter();
      }
      @Override
      public Adapter caseRegExp(RegExp object)
      {
        return createRegExpAdapter();
      }
      @Override
      public Adapter caseLocalFsm(LocalFsm object)
      {
        return createLocalFsmAdapter();
      }
      @Override
      public Adapter caseAstAction(AstAction object)
      {
        return createAstActionAdapter();
      }
      @Override
      public Adapter caseInputPattern(InputPattern object)
      {
        return createInputPatternAdapter();
      }
      @Override
      public Adapter caseOutputPattern(OutputPattern object)
      {
        return createOutputPatternAdapter();
      }
      @Override
      public Adapter caseGuard(Guard object)
      {
        return createGuardAdapter();
      }
      @Override
      public Adapter caseStatementAssign(StatementAssign object)
      {
        return createStatementAssignAdapter();
      }
      @Override
      public Adapter caseStatementCall(StatementCall object)
      {
        return createStatementCallAdapter();
      }
      @Override
      public Adapter caseStatementForeach(StatementForeach object)
      {
        return createStatementForeachAdapter();
      }
      @Override
      public Adapter caseStatementIf(StatementIf object)
      {
        return createStatementIfAdapter();
      }
      @Override
      public Adapter caseStatementElsif(StatementElsif object)
      {
        return createStatementElsifAdapter();
      }
      @Override
      public Adapter caseStatementWhile(StatementWhile object)
      {
        return createStatementWhileAdapter();
      }
      @Override
      public Adapter caseStatement(Statement object)
      {
        return createStatementAdapter();
      }
      @Override
      public Adapter caseAstExpression(AstExpression object)
      {
        return createAstExpressionAdapter();
      }
      @Override
      public Adapter caseExpressionCall(ExpressionCall object)
      {
        return createExpressionCallAdapter();
      }
      @Override
      public Adapter caseExpressionIndex(ExpressionIndex object)
      {
        return createExpressionIndexAdapter();
      }
      @Override
      public Adapter caseExpressionIf(ExpressionIf object)
      {
        return createExpressionIfAdapter();
      }
      @Override
      public Adapter caseExpressionElsif(ExpressionElsif object)
      {
        return createExpressionElsifAdapter();
      }
      @Override
      public Adapter caseExpressionList(ExpressionList object)
      {
        return createExpressionListAdapter();
      }
      @Override
      public Adapter caseGenerator(Generator object)
      {
        return createGeneratorAdapter();
      }
      @Override
      public Adapter caseExpressionVariable(ExpressionVariable object)
      {
        return createExpressionVariableAdapter();
      }
      @Override
      public Adapter caseExpressionLiteral(ExpressionLiteral object)
      {
        return createExpressionLiteralAdapter();
      }
      @Override
      public Adapter caseExpressionBoolean(ExpressionBoolean object)
      {
        return createExpressionBooleanAdapter();
      }
      @Override
      public Adapter caseExpressionFloat(ExpressionFloat object)
      {
        return createExpressionFloatAdapter();
      }
      @Override
      public Adapter caseExpressionInteger(ExpressionInteger object)
      {
        return createExpressionIntegerAdapter();
      }
      @Override
      public Adapter caseExpressionString(ExpressionString object)
      {
        return createExpressionStringAdapter();
      }
      @Override
      public Adapter caseAstType(AstType object)
      {
        return createAstTypeAdapter();
      }
      @Override
      public Adapter caseAstTypeBool(AstTypeBool object)
      {
        return createAstTypeBoolAdapter();
      }
      @Override
      public Adapter caseAstTypeFloat(AstTypeFloat object)
      {
        return createAstTypeFloatAdapter();
      }
      @Override
      public Adapter caseAstTypeHalf(AstTypeHalf object)
      {
        return createAstTypeHalfAdapter();
      }
      @Override
      public Adapter caseAstTypeDouble(AstTypeDouble object)
      {
        return createAstTypeDoubleAdapter();
      }
      @Override
      public Adapter caseAstTypeInt(AstTypeInt object)
      {
        return createAstTypeIntAdapter();
      }
      @Override
      public Adapter caseAstTypeList(AstTypeList object)
      {
        return createAstTypeListAdapter();
      }
      @Override
      public Adapter caseAstTypeString(AstTypeString object)
      {
        return createAstTypeStringAdapter();
      }
      @Override
      public Adapter caseAstTypeUint(AstTypeUint object)
      {
        return createAstTypeUintAdapter();
      }
      @Override
      public Adapter caseVariableReference(VariableReference object)
      {
        return createVariableReferenceAdapter();
      }
      @Override
      public Adapter caseAstAnnotation(AstAnnotation object)
      {
        return createAstAnnotationAdapter();
      }
      @Override
      public Adapter caseAnnotationArgument(AnnotationArgument object)
      {
        return createAnnotationArgumentAdapter();
      }
      @Override
      public Adapter caseRegExpBinary(RegExpBinary object)
      {
        return createRegExpBinaryAdapter();
      }
      @Override
      public Adapter caseRegExpUnary(RegExpUnary object)
      {
        return createRegExpUnaryAdapter();
      }
      @Override
      public Adapter caseRegExpTag(RegExpTag object)
      {
        return createRegExpTagAdapter();
      }
      @Override
      public Adapter caseExpressionBinary(ExpressionBinary object)
      {
        return createExpressionBinaryAdapter();
      }
      @Override
      public Adapter caseExpressionUnary(ExpressionUnary object)
      {
        return createExpressionUnaryAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstEntity <em>Ast Entity</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstEntity
   * @generated
   */
  public Adapter createAstEntityAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.Import <em>Import</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.Import
   * @generated
   */
  public Adapter createImportAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstUnit <em>Ast Unit</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstUnit
   * @generated
   */
  public Adapter createAstUnitAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.Variable <em>Variable</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.Variable
   * @generated
   */
  public Adapter createVariableAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstActor <em>Ast Actor</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstActor
   * @generated
   */
  public Adapter createAstActorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstPort <em>Ast Port</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstPort
   * @generated
   */
  public Adapter createAstPortAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.Function <em>Function</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.Function
   * @generated
   */
  public Adapter createFunctionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstProcedure <em>Ast Procedure</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstProcedure
   * @generated
   */
  public Adapter createAstProcedureAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTag <em>Ast Tag</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTag
   * @generated
   */
  public Adapter createAstTagAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.Inequality <em>Inequality</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.Inequality
   * @generated
   */
  public Adapter createInequalityAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.Priority <em>Priority</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.Priority
   * @generated
   */
  public Adapter createPriorityAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ScheduleFsm <em>Schedule Fsm</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ScheduleFsm
   * @generated
   */
  public Adapter createScheduleFsmAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.Fsm <em>Fsm</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.Fsm
   * @generated
   */
  public Adapter createFsmAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTransition <em>Ast Transition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTransition
   * @generated
   */
  public Adapter createAstTransitionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExternalTarget <em>External Target</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExternalTarget
   * @generated
   */
  public Adapter createExternalTargetAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstState <em>Ast State</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstState
   * @generated
   */
  public Adapter createAstStateAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.RegExp <em>Reg Exp</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.RegExp
   * @generated
   */
  public Adapter createRegExpAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.LocalFsm <em>Local Fsm</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.LocalFsm
   * @generated
   */
  public Adapter createLocalFsmAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstAction <em>Ast Action</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstAction
   * @generated
   */
  public Adapter createAstActionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.InputPattern <em>Input Pattern</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.InputPattern
   * @generated
   */
  public Adapter createInputPatternAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.OutputPattern <em>Output Pattern</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.OutputPattern
   * @generated
   */
  public Adapter createOutputPatternAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.Guard <em>Guard</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.Guard
   * @generated
   */
  public Adapter createGuardAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.StatementAssign <em>Statement Assign</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.StatementAssign
   * @generated
   */
  public Adapter createStatementAssignAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.StatementCall <em>Statement Call</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.StatementCall
   * @generated
   */
  public Adapter createStatementCallAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.StatementForeach <em>Statement Foreach</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.StatementForeach
   * @generated
   */
  public Adapter createStatementForeachAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.StatementIf <em>Statement If</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.StatementIf
   * @generated
   */
  public Adapter createStatementIfAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.StatementElsif <em>Statement Elsif</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.StatementElsif
   * @generated
   */
  public Adapter createStatementElsifAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.StatementWhile <em>Statement While</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.StatementWhile
   * @generated
   */
  public Adapter createStatementWhileAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.Statement <em>Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.Statement
   * @generated
   */
  public Adapter createStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstExpression <em>Ast Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstExpression
   * @generated
   */
  public Adapter createAstExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionCall <em>Expression Call</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionCall
   * @generated
   */
  public Adapter createExpressionCallAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionIndex <em>Expression Index</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionIndex
   * @generated
   */
  public Adapter createExpressionIndexAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionIf <em>Expression If</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionIf
   * @generated
   */
  public Adapter createExpressionIfAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionElsif <em>Expression Elsif</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionElsif
   * @generated
   */
  public Adapter createExpressionElsifAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionList <em>Expression List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionList
   * @generated
   */
  public Adapter createExpressionListAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.Generator <em>Generator</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.Generator
   * @generated
   */
  public Adapter createGeneratorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionVariable <em>Expression Variable</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionVariable
   * @generated
   */
  public Adapter createExpressionVariableAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionLiteral <em>Expression Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionLiteral
   * @generated
   */
  public Adapter createExpressionLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionBoolean <em>Expression Boolean</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionBoolean
   * @generated
   */
  public Adapter createExpressionBooleanAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionFloat <em>Expression Float</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionFloat
   * @generated
   */
  public Adapter createExpressionFloatAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionInteger <em>Expression Integer</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionInteger
   * @generated
   */
  public Adapter createExpressionIntegerAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionString <em>Expression String</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionString
   * @generated
   */
  public Adapter createExpressionStringAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstType <em>Ast Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstType
   * @generated
   */
  public Adapter createAstTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTypeBool <em>Ast Type Bool</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTypeBool
   * @generated
   */
  public Adapter createAstTypeBoolAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTypeFloat <em>Ast Type Float</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTypeFloat
   * @generated
   */
  public Adapter createAstTypeFloatAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTypeHalf <em>Ast Type Half</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTypeHalf
   * @generated
   */
  public Adapter createAstTypeHalfAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTypeDouble <em>Ast Type Double</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTypeDouble
   * @generated
   */
  public Adapter createAstTypeDoubleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTypeInt <em>Ast Type Int</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTypeInt
   * @generated
   */
  public Adapter createAstTypeIntAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTypeList <em>Ast Type List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTypeList
   * @generated
   */
  public Adapter createAstTypeListAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTypeString <em>Ast Type String</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTypeString
   * @generated
   */
  public Adapter createAstTypeStringAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstTypeUint <em>Ast Type Uint</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstTypeUint
   * @generated
   */
  public Adapter createAstTypeUintAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.VariableReference <em>Variable Reference</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.VariableReference
   * @generated
   */
  public Adapter createVariableReferenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AstAnnotation <em>Ast Annotation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AstAnnotation
   * @generated
   */
  public Adapter createAstAnnotationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.AnnotationArgument <em>Annotation Argument</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.AnnotationArgument
   * @generated
   */
  public Adapter createAnnotationArgumentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.RegExpBinary <em>Reg Exp Binary</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.RegExpBinary
   * @generated
   */
  public Adapter createRegExpBinaryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.RegExpUnary <em>Reg Exp Unary</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.RegExpUnary
   * @generated
   */
  public Adapter createRegExpUnaryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.RegExpTag <em>Reg Exp Tag</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.RegExpTag
   * @generated
   */
  public Adapter createRegExpTagAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionBinary <em>Expression Binary</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionBinary
   * @generated
   */
  public Adapter createExpressionBinaryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link net.sf.orcc.cal.cal.ExpressionUnary <em>Expression Unary</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see net.sf.orcc.cal.cal.ExpressionUnary
   * @generated
   */
  public Adapter createExpressionUnaryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //CalAdapterFactory
