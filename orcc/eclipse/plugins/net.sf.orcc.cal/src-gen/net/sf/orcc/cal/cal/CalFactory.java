/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.cal.cal.CalPackage
 * @generated
 */
public interface CalFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  CalFactory eINSTANCE = net.sf.orcc.cal.cal.impl.CalFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Ast Entity</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Entity</em>'.
   * @generated
   */
  AstEntity createAstEntity();

  /**
   * Returns a new object of class '<em>Import</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Import</em>'.
   * @generated
   */
  Import createImport();

  /**
   * Returns a new object of class '<em>Ast Unit</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Unit</em>'.
   * @generated
   */
  AstUnit createAstUnit();

  /**
   * Returns a new object of class '<em>Variable</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Variable</em>'.
   * @generated
   */
  Variable createVariable();

  /**
   * Returns a new object of class '<em>Ast Actor</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Actor</em>'.
   * @generated
   */
  AstActor createAstActor();

  /**
   * Returns a new object of class '<em>Ast Port</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Port</em>'.
   * @generated
   */
  AstPort createAstPort();

  /**
   * Returns a new object of class '<em>Function</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Function</em>'.
   * @generated
   */
  Function createFunction();

  /**
   * Returns a new object of class '<em>Ast Procedure</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Procedure</em>'.
   * @generated
   */
  AstProcedure createAstProcedure();

  /**
   * Returns a new object of class '<em>Ast Tag</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Tag</em>'.
   * @generated
   */
  AstTag createAstTag();

  /**
   * Returns a new object of class '<em>Inequality</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Inequality</em>'.
   * @generated
   */
  Inequality createInequality();

  /**
   * Returns a new object of class '<em>Priority</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Priority</em>'.
   * @generated
   */
  Priority createPriority();

  /**
   * Returns a new object of class '<em>Schedule Fsm</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Schedule Fsm</em>'.
   * @generated
   */
  ScheduleFsm createScheduleFsm();

  /**
   * Returns a new object of class '<em>Fsm</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Fsm</em>'.
   * @generated
   */
  Fsm createFsm();

  /**
   * Returns a new object of class '<em>Ast Transition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Transition</em>'.
   * @generated
   */
  AstTransition createAstTransition();

  /**
   * Returns a new object of class '<em>External Target</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>External Target</em>'.
   * @generated
   */
  ExternalTarget createExternalTarget();

  /**
   * Returns a new object of class '<em>Ast State</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast State</em>'.
   * @generated
   */
  AstState createAstState();

  /**
   * Returns a new object of class '<em>Reg Exp</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Reg Exp</em>'.
   * @generated
   */
  RegExp createRegExp();

  /**
   * Returns a new object of class '<em>Local Fsm</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Local Fsm</em>'.
   * @generated
   */
  LocalFsm createLocalFsm();

  /**
   * Returns a new object of class '<em>Ast Action</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Action</em>'.
   * @generated
   */
  AstAction createAstAction();

  /**
   * Returns a new object of class '<em>Input Pattern</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Input Pattern</em>'.
   * @generated
   */
  InputPattern createInputPattern();

  /**
   * Returns a new object of class '<em>Output Pattern</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Output Pattern</em>'.
   * @generated
   */
  OutputPattern createOutputPattern();

  /**
   * Returns a new object of class '<em>Guard</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Guard</em>'.
   * @generated
   */
  Guard createGuard();

  /**
   * Returns a new object of class '<em>Statement Assign</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Statement Assign</em>'.
   * @generated
   */
  StatementAssign createStatementAssign();

  /**
   * Returns a new object of class '<em>Statement Call</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Statement Call</em>'.
   * @generated
   */
  StatementCall createStatementCall();

  /**
   * Returns a new object of class '<em>Statement Foreach</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Statement Foreach</em>'.
   * @generated
   */
  StatementForeach createStatementForeach();

  /**
   * Returns a new object of class '<em>Statement If</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Statement If</em>'.
   * @generated
   */
  StatementIf createStatementIf();

  /**
   * Returns a new object of class '<em>Statement Elsif</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Statement Elsif</em>'.
   * @generated
   */
  StatementElsif createStatementElsif();

  /**
   * Returns a new object of class '<em>Statement While</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Statement While</em>'.
   * @generated
   */
  StatementWhile createStatementWhile();

  /**
   * Returns a new object of class '<em>Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Statement</em>'.
   * @generated
   */
  Statement createStatement();

  /**
   * Returns a new object of class '<em>Ast Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Expression</em>'.
   * @generated
   */
  AstExpression createAstExpression();

  /**
   * Returns a new object of class '<em>Expression Call</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Call</em>'.
   * @generated
   */
  ExpressionCall createExpressionCall();

  /**
   * Returns a new object of class '<em>Expression Index</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Index</em>'.
   * @generated
   */
  ExpressionIndex createExpressionIndex();

  /**
   * Returns a new object of class '<em>Expression If</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression If</em>'.
   * @generated
   */
  ExpressionIf createExpressionIf();

  /**
   * Returns a new object of class '<em>Expression Elsif</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Elsif</em>'.
   * @generated
   */
  ExpressionElsif createExpressionElsif();

  /**
   * Returns a new object of class '<em>Expression List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression List</em>'.
   * @generated
   */
  ExpressionList createExpressionList();

  /**
   * Returns a new object of class '<em>Generator</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Generator</em>'.
   * @generated
   */
  Generator createGenerator();

  /**
   * Returns a new object of class '<em>Expression Variable</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Variable</em>'.
   * @generated
   */
  ExpressionVariable createExpressionVariable();

  /**
   * Returns a new object of class '<em>Expression Literal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Literal</em>'.
   * @generated
   */
  ExpressionLiteral createExpressionLiteral();

  /**
   * Returns a new object of class '<em>Expression Boolean</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Boolean</em>'.
   * @generated
   */
  ExpressionBoolean createExpressionBoolean();

  /**
   * Returns a new object of class '<em>Expression Float</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Float</em>'.
   * @generated
   */
  ExpressionFloat createExpressionFloat();

  /**
   * Returns a new object of class '<em>Expression Integer</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Integer</em>'.
   * @generated
   */
  ExpressionInteger createExpressionInteger();

  /**
   * Returns a new object of class '<em>Expression String</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression String</em>'.
   * @generated
   */
  ExpressionString createExpressionString();

  /**
   * Returns a new object of class '<em>Ast Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Type</em>'.
   * @generated
   */
  AstType createAstType();

  /**
   * Returns a new object of class '<em>Ast Type Bool</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Type Bool</em>'.
   * @generated
   */
  AstTypeBool createAstTypeBool();

  /**
   * Returns a new object of class '<em>Ast Type Float</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Type Float</em>'.
   * @generated
   */
  AstTypeFloat createAstTypeFloat();

  /**
   * Returns a new object of class '<em>Ast Type Half</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Type Half</em>'.
   * @generated
   */
  AstTypeHalf createAstTypeHalf();

  /**
   * Returns a new object of class '<em>Ast Type Double</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Type Double</em>'.
   * @generated
   */
  AstTypeDouble createAstTypeDouble();

  /**
   * Returns a new object of class '<em>Ast Type Int</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Type Int</em>'.
   * @generated
   */
  AstTypeInt createAstTypeInt();

  /**
   * Returns a new object of class '<em>Ast Type List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Type List</em>'.
   * @generated
   */
  AstTypeList createAstTypeList();

  /**
   * Returns a new object of class '<em>Ast Type String</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Type String</em>'.
   * @generated
   */
  AstTypeString createAstTypeString();

  /**
   * Returns a new object of class '<em>Ast Type Uint</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Type Uint</em>'.
   * @generated
   */
  AstTypeUint createAstTypeUint();

  /**
   * Returns a new object of class '<em>Variable Reference</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Variable Reference</em>'.
   * @generated
   */
  VariableReference createVariableReference();

  /**
   * Returns a new object of class '<em>Ast Annotation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Ast Annotation</em>'.
   * @generated
   */
  AstAnnotation createAstAnnotation();

  /**
   * Returns a new object of class '<em>Annotation Argument</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annotation Argument</em>'.
   * @generated
   */
  AnnotationArgument createAnnotationArgument();

  /**
   * Returns a new object of class '<em>Reg Exp Binary</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Reg Exp Binary</em>'.
   * @generated
   */
  RegExpBinary createRegExpBinary();

  /**
   * Returns a new object of class '<em>Reg Exp Unary</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Reg Exp Unary</em>'.
   * @generated
   */
  RegExpUnary createRegExpUnary();

  /**
   * Returns a new object of class '<em>Reg Exp Tag</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Reg Exp Tag</em>'.
   * @generated
   */
  RegExpTag createRegExpTag();

  /**
   * Returns a new object of class '<em>Expression Binary</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Binary</em>'.
   * @generated
   */
  ExpressionBinary createExpressionBinary();

  /**
   * Returns a new object of class '<em>Expression Unary</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression Unary</em>'.
   * @generated
   */
  ExpressionUnary createExpressionUnary();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  CalPackage getCalPackage();

} //CalFactory
