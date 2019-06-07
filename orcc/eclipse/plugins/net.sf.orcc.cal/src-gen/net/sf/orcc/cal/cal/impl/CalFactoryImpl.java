/**
 */
package net.sf.orcc.cal.cal.impl;

import net.sf.orcc.cal.cal.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CalFactoryImpl extends EFactoryImpl implements CalFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static CalFactory init()
  {
    try
    {
      CalFactory theCalFactory = (CalFactory)EPackage.Registry.INSTANCE.getEFactory(CalPackage.eNS_URI);
      if (theCalFactory != null)
      {
        return theCalFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new CalFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CalFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case CalPackage.AST_ENTITY: return createAstEntity();
      case CalPackage.IMPORT: return createImport();
      case CalPackage.AST_UNIT: return createAstUnit();
      case CalPackage.VARIABLE: return createVariable();
      case CalPackage.AST_ACTOR: return createAstActor();
      case CalPackage.AST_PORT: return createAstPort();
      case CalPackage.FUNCTION: return createFunction();
      case CalPackage.AST_PROCEDURE: return createAstProcedure();
      case CalPackage.AST_TAG: return createAstTag();
      case CalPackage.INEQUALITY: return createInequality();
      case CalPackage.PRIORITY: return createPriority();
      case CalPackage.SCHEDULE_FSM: return createScheduleFsm();
      case CalPackage.FSM: return createFsm();
      case CalPackage.AST_TRANSITION: return createAstTransition();
      case CalPackage.EXTERNAL_TARGET: return createExternalTarget();
      case CalPackage.AST_STATE: return createAstState();
      case CalPackage.REG_EXP: return createRegExp();
      case CalPackage.LOCAL_FSM: return createLocalFsm();
      case CalPackage.AST_ACTION: return createAstAction();
      case CalPackage.INPUT_PATTERN: return createInputPattern();
      case CalPackage.OUTPUT_PATTERN: return createOutputPattern();
      case CalPackage.GUARD: return createGuard();
      case CalPackage.STATEMENT_ASSIGN: return createStatementAssign();
      case CalPackage.STATEMENT_CALL: return createStatementCall();
      case CalPackage.STATEMENT_FOREACH: return createStatementForeach();
      case CalPackage.STATEMENT_IF: return createStatementIf();
      case CalPackage.STATEMENT_ELSIF: return createStatementElsif();
      case CalPackage.STATEMENT_WHILE: return createStatementWhile();
      case CalPackage.STATEMENT: return createStatement();
      case CalPackage.AST_EXPRESSION: return createAstExpression();
      case CalPackage.EXPRESSION_CALL: return createExpressionCall();
      case CalPackage.EXPRESSION_INDEX: return createExpressionIndex();
      case CalPackage.EXPRESSION_IF: return createExpressionIf();
      case CalPackage.EXPRESSION_ELSIF: return createExpressionElsif();
      case CalPackage.EXPRESSION_LIST: return createExpressionList();
      case CalPackage.GENERATOR: return createGenerator();
      case CalPackage.EXPRESSION_VARIABLE: return createExpressionVariable();
      case CalPackage.EXPRESSION_LITERAL: return createExpressionLiteral();
      case CalPackage.EXPRESSION_BOOLEAN: return createExpressionBoolean();
      case CalPackage.EXPRESSION_FLOAT: return createExpressionFloat();
      case CalPackage.EXPRESSION_INTEGER: return createExpressionInteger();
      case CalPackage.EXPRESSION_STRING: return createExpressionString();
      case CalPackage.AST_TYPE: return createAstType();
      case CalPackage.AST_TYPE_BOOL: return createAstTypeBool();
      case CalPackage.AST_TYPE_FLOAT: return createAstTypeFloat();
      case CalPackage.AST_TYPE_HALF: return createAstTypeHalf();
      case CalPackage.AST_TYPE_DOUBLE: return createAstTypeDouble();
      case CalPackage.AST_TYPE_INT: return createAstTypeInt();
      case CalPackage.AST_TYPE_LIST: return createAstTypeList();
      case CalPackage.AST_TYPE_STRING: return createAstTypeString();
      case CalPackage.AST_TYPE_UINT: return createAstTypeUint();
      case CalPackage.VARIABLE_REFERENCE: return createVariableReference();
      case CalPackage.AST_ANNOTATION: return createAstAnnotation();
      case CalPackage.ANNOTATION_ARGUMENT: return createAnnotationArgument();
      case CalPackage.REG_EXP_BINARY: return createRegExpBinary();
      case CalPackage.REG_EXP_UNARY: return createRegExpUnary();
      case CalPackage.REG_EXP_TAG: return createRegExpTag();
      case CalPackage.EXPRESSION_BINARY: return createExpressionBinary();
      case CalPackage.EXPRESSION_UNARY: return createExpressionUnary();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstEntity createAstEntity()
  {
    AstEntityImpl astEntity = new AstEntityImpl();
    return astEntity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Import createImport()
  {
    ImportImpl import_ = new ImportImpl();
    return import_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstUnit createAstUnit()
  {
    AstUnitImpl astUnit = new AstUnitImpl();
    return astUnit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Variable createVariable()
  {
    VariableImpl variable = new VariableImpl();
    return variable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstActor createAstActor()
  {
    AstActorImpl astActor = new AstActorImpl();
    return astActor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstPort createAstPort()
  {
    AstPortImpl astPort = new AstPortImpl();
    return astPort;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Function createFunction()
  {
    FunctionImpl function = new FunctionImpl();
    return function;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstProcedure createAstProcedure()
  {
    AstProcedureImpl astProcedure = new AstProcedureImpl();
    return astProcedure;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTag createAstTag()
  {
    AstTagImpl astTag = new AstTagImpl();
    return astTag;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Inequality createInequality()
  {
    InequalityImpl inequality = new InequalityImpl();
    return inequality;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Priority createPriority()
  {
    PriorityImpl priority = new PriorityImpl();
    return priority;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScheduleFsm createScheduleFsm()
  {
    ScheduleFsmImpl scheduleFsm = new ScheduleFsmImpl();
    return scheduleFsm;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Fsm createFsm()
  {
    FsmImpl fsm = new FsmImpl();
    return fsm;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTransition createAstTransition()
  {
    AstTransitionImpl astTransition = new AstTransitionImpl();
    return astTransition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExternalTarget createExternalTarget()
  {
    ExternalTargetImpl externalTarget = new ExternalTargetImpl();
    return externalTarget;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState createAstState()
  {
    AstStateImpl astState = new AstStateImpl();
    return astState;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegExp createRegExp()
  {
    RegExpImpl regExp = new RegExpImpl();
    return regExp;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalFsm createLocalFsm()
  {
    LocalFsmImpl localFsm = new LocalFsmImpl();
    return localFsm;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstAction createAstAction()
  {
    AstActionImpl astAction = new AstActionImpl();
    return astAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InputPattern createInputPattern()
  {
    InputPatternImpl inputPattern = new InputPatternImpl();
    return inputPattern;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OutputPattern createOutputPattern()
  {
    OutputPatternImpl outputPattern = new OutputPatternImpl();
    return outputPattern;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Guard createGuard()
  {
    GuardImpl guard = new GuardImpl();
    return guard;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatementAssign createStatementAssign()
  {
    StatementAssignImpl statementAssign = new StatementAssignImpl();
    return statementAssign;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatementCall createStatementCall()
  {
    StatementCallImpl statementCall = new StatementCallImpl();
    return statementCall;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatementForeach createStatementForeach()
  {
    StatementForeachImpl statementForeach = new StatementForeachImpl();
    return statementForeach;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatementIf createStatementIf()
  {
    StatementIfImpl statementIf = new StatementIfImpl();
    return statementIf;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatementElsif createStatementElsif()
  {
    StatementElsifImpl statementElsif = new StatementElsifImpl();
    return statementElsif;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatementWhile createStatementWhile()
  {
    StatementWhileImpl statementWhile = new StatementWhileImpl();
    return statementWhile;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Statement createStatement()
  {
    StatementImpl statement = new StatementImpl();
    return statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstExpression createAstExpression()
  {
    AstExpressionImpl astExpression = new AstExpressionImpl();
    return astExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionCall createExpressionCall()
  {
    ExpressionCallImpl expressionCall = new ExpressionCallImpl();
    return expressionCall;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionIndex createExpressionIndex()
  {
    ExpressionIndexImpl expressionIndex = new ExpressionIndexImpl();
    return expressionIndex;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionIf createExpressionIf()
  {
    ExpressionIfImpl expressionIf = new ExpressionIfImpl();
    return expressionIf;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionElsif createExpressionElsif()
  {
    ExpressionElsifImpl expressionElsif = new ExpressionElsifImpl();
    return expressionElsif;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionList createExpressionList()
  {
    ExpressionListImpl expressionList = new ExpressionListImpl();
    return expressionList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Generator createGenerator()
  {
    GeneratorImpl generator = new GeneratorImpl();
    return generator;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionVariable createExpressionVariable()
  {
    ExpressionVariableImpl expressionVariable = new ExpressionVariableImpl();
    return expressionVariable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionLiteral createExpressionLiteral()
  {
    ExpressionLiteralImpl expressionLiteral = new ExpressionLiteralImpl();
    return expressionLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionBoolean createExpressionBoolean()
  {
    ExpressionBooleanImpl expressionBoolean = new ExpressionBooleanImpl();
    return expressionBoolean;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionFloat createExpressionFloat()
  {
    ExpressionFloatImpl expressionFloat = new ExpressionFloatImpl();
    return expressionFloat;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionInteger createExpressionInteger()
  {
    ExpressionIntegerImpl expressionInteger = new ExpressionIntegerImpl();
    return expressionInteger;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionString createExpressionString()
  {
    ExpressionStringImpl expressionString = new ExpressionStringImpl();
    return expressionString;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstType createAstType()
  {
    AstTypeImpl astType = new AstTypeImpl();
    return astType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTypeBool createAstTypeBool()
  {
    AstTypeBoolImpl astTypeBool = new AstTypeBoolImpl();
    return astTypeBool;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTypeFloat createAstTypeFloat()
  {
    AstTypeFloatImpl astTypeFloat = new AstTypeFloatImpl();
    return astTypeFloat;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTypeHalf createAstTypeHalf()
  {
    AstTypeHalfImpl astTypeHalf = new AstTypeHalfImpl();
    return astTypeHalf;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTypeDouble createAstTypeDouble()
  {
    AstTypeDoubleImpl astTypeDouble = new AstTypeDoubleImpl();
    return astTypeDouble;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTypeInt createAstTypeInt()
  {
    AstTypeIntImpl astTypeInt = new AstTypeIntImpl();
    return astTypeInt;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTypeList createAstTypeList()
  {
    AstTypeListImpl astTypeList = new AstTypeListImpl();
    return astTypeList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTypeString createAstTypeString()
  {
    AstTypeStringImpl astTypeString = new AstTypeStringImpl();
    return astTypeString;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTypeUint createAstTypeUint()
  {
    AstTypeUintImpl astTypeUint = new AstTypeUintImpl();
    return astTypeUint;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableReference createVariableReference()
  {
    VariableReferenceImpl variableReference = new VariableReferenceImpl();
    return variableReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstAnnotation createAstAnnotation()
  {
    AstAnnotationImpl astAnnotation = new AstAnnotationImpl();
    return astAnnotation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationArgument createAnnotationArgument()
  {
    AnnotationArgumentImpl annotationArgument = new AnnotationArgumentImpl();
    return annotationArgument;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegExpBinary createRegExpBinary()
  {
    RegExpBinaryImpl regExpBinary = new RegExpBinaryImpl();
    return regExpBinary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegExpUnary createRegExpUnary()
  {
    RegExpUnaryImpl regExpUnary = new RegExpUnaryImpl();
    return regExpUnary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegExpTag createRegExpTag()
  {
    RegExpTagImpl regExpTag = new RegExpTagImpl();
    return regExpTag;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionBinary createExpressionBinary()
  {
    ExpressionBinaryImpl expressionBinary = new ExpressionBinaryImpl();
    return expressionBinary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionUnary createExpressionUnary()
  {
    ExpressionUnaryImpl expressionUnary = new ExpressionUnaryImpl();
    return expressionUnary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CalPackage getCalPackage()
  {
    return (CalPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static CalPackage getPackage()
  {
    return CalPackage.eINSTANCE;
  }

} //CalFactoryImpl
