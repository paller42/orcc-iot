/**
 */
package net.sf.orcc.cal.cal.util;

import net.sf.orcc.cal.cal.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.cal.cal.CalPackage
 * @generated
 */
public class CalSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static CalPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CalSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = CalPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case CalPackage.AST_ENTITY:
      {
        AstEntity astEntity = (AstEntity)theEObject;
        T result = caseAstEntity(astEntity);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.IMPORT:
      {
        Import import_ = (Import)theEObject;
        T result = caseImport(import_);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_UNIT:
      {
        AstUnit astUnit = (AstUnit)theEObject;
        T result = caseAstUnit(astUnit);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.VARIABLE:
      {
        Variable variable = (Variable)theEObject;
        T result = caseVariable(variable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_ACTOR:
      {
        AstActor astActor = (AstActor)theEObject;
        T result = caseAstActor(astActor);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_PORT:
      {
        AstPort astPort = (AstPort)theEObject;
        T result = caseAstPort(astPort);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.FUNCTION:
      {
        Function function = (Function)theEObject;
        T result = caseFunction(function);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_PROCEDURE:
      {
        AstProcedure astProcedure = (AstProcedure)theEObject;
        T result = caseAstProcedure(astProcedure);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TAG:
      {
        AstTag astTag = (AstTag)theEObject;
        T result = caseAstTag(astTag);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.INEQUALITY:
      {
        Inequality inequality = (Inequality)theEObject;
        T result = caseInequality(inequality);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.PRIORITY:
      {
        Priority priority = (Priority)theEObject;
        T result = casePriority(priority);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.SCHEDULE_FSM:
      {
        ScheduleFsm scheduleFsm = (ScheduleFsm)theEObject;
        T result = caseScheduleFsm(scheduleFsm);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.FSM:
      {
        Fsm fsm = (Fsm)theEObject;
        T result = caseFsm(fsm);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TRANSITION:
      {
        AstTransition astTransition = (AstTransition)theEObject;
        T result = caseAstTransition(astTransition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXTERNAL_TARGET:
      {
        ExternalTarget externalTarget = (ExternalTarget)theEObject;
        T result = caseExternalTarget(externalTarget);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_STATE:
      {
        AstState astState = (AstState)theEObject;
        T result = caseAstState(astState);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.REG_EXP:
      {
        RegExp regExp = (RegExp)theEObject;
        T result = caseRegExp(regExp);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.LOCAL_FSM:
      {
        LocalFsm localFsm = (LocalFsm)theEObject;
        T result = caseLocalFsm(localFsm);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_ACTION:
      {
        AstAction astAction = (AstAction)theEObject;
        T result = caseAstAction(astAction);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.INPUT_PATTERN:
      {
        InputPattern inputPattern = (InputPattern)theEObject;
        T result = caseInputPattern(inputPattern);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.OUTPUT_PATTERN:
      {
        OutputPattern outputPattern = (OutputPattern)theEObject;
        T result = caseOutputPattern(outputPattern);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.GUARD:
      {
        Guard guard = (Guard)theEObject;
        T result = caseGuard(guard);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.STATEMENT_ASSIGN:
      {
        StatementAssign statementAssign = (StatementAssign)theEObject;
        T result = caseStatementAssign(statementAssign);
        if (result == null) result = caseStatement(statementAssign);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.STATEMENT_CALL:
      {
        StatementCall statementCall = (StatementCall)theEObject;
        T result = caseStatementCall(statementCall);
        if (result == null) result = caseStatement(statementCall);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.STATEMENT_FOREACH:
      {
        StatementForeach statementForeach = (StatementForeach)theEObject;
        T result = caseStatementForeach(statementForeach);
        if (result == null) result = caseStatement(statementForeach);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.STATEMENT_IF:
      {
        StatementIf statementIf = (StatementIf)theEObject;
        T result = caseStatementIf(statementIf);
        if (result == null) result = caseStatement(statementIf);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.STATEMENT_ELSIF:
      {
        StatementElsif statementElsif = (StatementElsif)theEObject;
        T result = caseStatementElsif(statementElsif);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.STATEMENT_WHILE:
      {
        StatementWhile statementWhile = (StatementWhile)theEObject;
        T result = caseStatementWhile(statementWhile);
        if (result == null) result = caseStatement(statementWhile);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.STATEMENT:
      {
        Statement statement = (Statement)theEObject;
        T result = caseStatement(statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_EXPRESSION:
      {
        AstExpression astExpression = (AstExpression)theEObject;
        T result = caseAstExpression(astExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_CALL:
      {
        ExpressionCall expressionCall = (ExpressionCall)theEObject;
        T result = caseExpressionCall(expressionCall);
        if (result == null) result = caseAstExpression(expressionCall);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_INDEX:
      {
        ExpressionIndex expressionIndex = (ExpressionIndex)theEObject;
        T result = caseExpressionIndex(expressionIndex);
        if (result == null) result = caseAstExpression(expressionIndex);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_IF:
      {
        ExpressionIf expressionIf = (ExpressionIf)theEObject;
        T result = caseExpressionIf(expressionIf);
        if (result == null) result = caseAstExpression(expressionIf);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_ELSIF:
      {
        ExpressionElsif expressionElsif = (ExpressionElsif)theEObject;
        T result = caseExpressionElsif(expressionElsif);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_LIST:
      {
        ExpressionList expressionList = (ExpressionList)theEObject;
        T result = caseExpressionList(expressionList);
        if (result == null) result = caseAstExpression(expressionList);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.GENERATOR:
      {
        Generator generator = (Generator)theEObject;
        T result = caseGenerator(generator);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_VARIABLE:
      {
        ExpressionVariable expressionVariable = (ExpressionVariable)theEObject;
        T result = caseExpressionVariable(expressionVariable);
        if (result == null) result = caseAstExpression(expressionVariable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_LITERAL:
      {
        ExpressionLiteral expressionLiteral = (ExpressionLiteral)theEObject;
        T result = caseExpressionLiteral(expressionLiteral);
        if (result == null) result = caseAstExpression(expressionLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_BOOLEAN:
      {
        ExpressionBoolean expressionBoolean = (ExpressionBoolean)theEObject;
        T result = caseExpressionBoolean(expressionBoolean);
        if (result == null) result = caseExpressionLiteral(expressionBoolean);
        if (result == null) result = caseAstExpression(expressionBoolean);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_FLOAT:
      {
        ExpressionFloat expressionFloat = (ExpressionFloat)theEObject;
        T result = caseExpressionFloat(expressionFloat);
        if (result == null) result = caseExpressionLiteral(expressionFloat);
        if (result == null) result = caseAstExpression(expressionFloat);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_INTEGER:
      {
        ExpressionInteger expressionInteger = (ExpressionInteger)theEObject;
        T result = caseExpressionInteger(expressionInteger);
        if (result == null) result = caseExpressionLiteral(expressionInteger);
        if (result == null) result = caseAstExpression(expressionInteger);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_STRING:
      {
        ExpressionString expressionString = (ExpressionString)theEObject;
        T result = caseExpressionString(expressionString);
        if (result == null) result = caseExpressionLiteral(expressionString);
        if (result == null) result = caseAstExpression(expressionString);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TYPE:
      {
        AstType astType = (AstType)theEObject;
        T result = caseAstType(astType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TYPE_BOOL:
      {
        AstTypeBool astTypeBool = (AstTypeBool)theEObject;
        T result = caseAstTypeBool(astTypeBool);
        if (result == null) result = caseAstType(astTypeBool);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TYPE_FLOAT:
      {
        AstTypeFloat astTypeFloat = (AstTypeFloat)theEObject;
        T result = caseAstTypeFloat(astTypeFloat);
        if (result == null) result = caseAstType(astTypeFloat);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TYPE_HALF:
      {
        AstTypeHalf astTypeHalf = (AstTypeHalf)theEObject;
        T result = caseAstTypeHalf(astTypeHalf);
        if (result == null) result = caseAstType(astTypeHalf);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TYPE_DOUBLE:
      {
        AstTypeDouble astTypeDouble = (AstTypeDouble)theEObject;
        T result = caseAstTypeDouble(astTypeDouble);
        if (result == null) result = caseAstType(astTypeDouble);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TYPE_INT:
      {
        AstTypeInt astTypeInt = (AstTypeInt)theEObject;
        T result = caseAstTypeInt(astTypeInt);
        if (result == null) result = caseAstType(astTypeInt);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TYPE_LIST:
      {
        AstTypeList astTypeList = (AstTypeList)theEObject;
        T result = caseAstTypeList(astTypeList);
        if (result == null) result = caseAstType(astTypeList);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TYPE_STRING:
      {
        AstTypeString astTypeString = (AstTypeString)theEObject;
        T result = caseAstTypeString(astTypeString);
        if (result == null) result = caseAstType(astTypeString);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_TYPE_UINT:
      {
        AstTypeUint astTypeUint = (AstTypeUint)theEObject;
        T result = caseAstTypeUint(astTypeUint);
        if (result == null) result = caseAstType(astTypeUint);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.VARIABLE_REFERENCE:
      {
        VariableReference variableReference = (VariableReference)theEObject;
        T result = caseVariableReference(variableReference);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.AST_ANNOTATION:
      {
        AstAnnotation astAnnotation = (AstAnnotation)theEObject;
        T result = caseAstAnnotation(astAnnotation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.ANNOTATION_ARGUMENT:
      {
        AnnotationArgument annotationArgument = (AnnotationArgument)theEObject;
        T result = caseAnnotationArgument(annotationArgument);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.REG_EXP_BINARY:
      {
        RegExpBinary regExpBinary = (RegExpBinary)theEObject;
        T result = caseRegExpBinary(regExpBinary);
        if (result == null) result = caseRegExp(regExpBinary);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.REG_EXP_UNARY:
      {
        RegExpUnary regExpUnary = (RegExpUnary)theEObject;
        T result = caseRegExpUnary(regExpUnary);
        if (result == null) result = caseRegExp(regExpUnary);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.REG_EXP_TAG:
      {
        RegExpTag regExpTag = (RegExpTag)theEObject;
        T result = caseRegExpTag(regExpTag);
        if (result == null) result = caseRegExp(regExpTag);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_BINARY:
      {
        ExpressionBinary expressionBinary = (ExpressionBinary)theEObject;
        T result = caseExpressionBinary(expressionBinary);
        if (result == null) result = caseAstExpression(expressionBinary);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case CalPackage.EXPRESSION_UNARY:
      {
        ExpressionUnary expressionUnary = (ExpressionUnary)theEObject;
        T result = caseExpressionUnary(expressionUnary);
        if (result == null) result = caseAstExpression(expressionUnary);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Entity</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Entity</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstEntity(AstEntity object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Import</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Import</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseImport(Import object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Unit</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Unit</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstUnit(AstUnit object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Variable</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVariable(Variable object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Actor</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Actor</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstActor(AstActor object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Port</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Port</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstPort(AstPort object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Function</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFunction(Function object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Procedure</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Procedure</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstProcedure(AstProcedure object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Tag</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Tag</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTag(AstTag object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Inequality</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Inequality</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInequality(Inequality object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Priority</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Priority</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePriority(Priority object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Schedule Fsm</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Schedule Fsm</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseScheduleFsm(ScheduleFsm object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Fsm</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Fsm</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFsm(Fsm object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Transition</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Transition</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTransition(AstTransition object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>External Target</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>External Target</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExternalTarget(ExternalTarget object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast State</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast State</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstState(AstState object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Reg Exp</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Reg Exp</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRegExp(RegExp object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Local Fsm</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Local Fsm</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLocalFsm(LocalFsm object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Action</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Action</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstAction(AstAction object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Input Pattern</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Input Pattern</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInputPattern(InputPattern object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Output Pattern</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Output Pattern</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOutputPattern(OutputPattern object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Guard</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Guard</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGuard(Guard object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Statement Assign</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Statement Assign</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStatementAssign(StatementAssign object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Statement Call</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Statement Call</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStatementCall(StatementCall object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Statement Foreach</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Statement Foreach</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStatementForeach(StatementForeach object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Statement If</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Statement If</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStatementIf(StatementIf object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Statement Elsif</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Statement Elsif</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStatementElsif(StatementElsif object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Statement While</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Statement While</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStatementWhile(StatementWhile object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStatement(Statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstExpression(AstExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Call</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Call</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionCall(ExpressionCall object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Index</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Index</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionIndex(ExpressionIndex object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression If</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression If</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionIf(ExpressionIf object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Elsif</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Elsif</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionElsif(ExpressionElsif object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionList(ExpressionList object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Generator</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Generator</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGenerator(Generator object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Variable</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Variable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionVariable(ExpressionVariable object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionLiteral(ExpressionLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Boolean</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Boolean</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionBoolean(ExpressionBoolean object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Float</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Float</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionFloat(ExpressionFloat object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Integer</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Integer</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionInteger(ExpressionInteger object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression String</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression String</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionString(ExpressionString object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstType(AstType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Type Bool</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Type Bool</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTypeBool(AstTypeBool object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Type Float</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Type Float</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTypeFloat(AstTypeFloat object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Type Half</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Type Half</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTypeHalf(AstTypeHalf object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Type Double</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Type Double</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTypeDouble(AstTypeDouble object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Type Int</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Type Int</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTypeInt(AstTypeInt object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Type List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Type List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTypeList(AstTypeList object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Type String</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Type String</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTypeString(AstTypeString object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Type Uint</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Type Uint</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstTypeUint(AstTypeUint object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Variable Reference</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Variable Reference</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVariableReference(VariableReference object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ast Annotation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ast Annotation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAstAnnotation(AstAnnotation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annotation Argument</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annotation Argument</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnotationArgument(AnnotationArgument object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Reg Exp Binary</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Reg Exp Binary</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRegExpBinary(RegExpBinary object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Reg Exp Unary</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Reg Exp Unary</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRegExpUnary(RegExpUnary object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Reg Exp Tag</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Reg Exp Tag</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRegExpTag(RegExpTag object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Binary</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Binary</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionBinary(ExpressionBinary object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Unary</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Unary</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpressionUnary(ExpressionUnary object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //CalSwitch
