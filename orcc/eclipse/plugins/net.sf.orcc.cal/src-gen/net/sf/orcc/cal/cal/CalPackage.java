/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see net.sf.orcc.cal.cal.CalFactory
 * @model kind="package"
 * @generated
 */
public interface CalPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "cal";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://orcc.sf.net/cal/Cal";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "cal";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  CalPackage eINSTANCE = net.sf.orcc.cal.cal.impl.CalPackageImpl.init();

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstEntityImpl <em>Ast Entity</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstEntityImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstEntity()
   * @generated
   */
  int AST_ENTITY = 0;

  /**
   * The feature id for the '<em><b>Package</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ENTITY__PACKAGE = 0;

  /**
   * The feature id for the '<em><b>Imports</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ENTITY__IMPORTS = 1;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ENTITY__ANNOTATIONS = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ENTITY__NAME = 3;

  /**
   * The feature id for the '<em><b>Actor</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ENTITY__ACTOR = 4;

  /**
   * The feature id for the '<em><b>Unit</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ENTITY__UNIT = 5;

  /**
   * The number of structural features of the '<em>Ast Entity</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ENTITY_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ImportImpl <em>Import</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ImportImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getImport()
   * @generated
   */
  int IMPORT = 1;

  /**
   * The feature id for the '<em><b>Imported Namespace</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT__IMPORTED_NAMESPACE = 0;

  /**
   * The number of structural features of the '<em>Import</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IMPORT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstUnitImpl <em>Ast Unit</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstUnitImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstUnit()
   * @generated
   */
  int AST_UNIT = 2;

  /**
   * The feature id for the '<em><b>Functions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_UNIT__FUNCTIONS = 0;

  /**
   * The feature id for the '<em><b>Procedures</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_UNIT__PROCEDURES = 1;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_UNIT__VARIABLES = 2;

  /**
   * The number of structural features of the '<em>Ast Unit</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_UNIT_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.VariableImpl <em>Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.VariableImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getVariable()
   * @generated
   */
  int VARIABLE = 3;

  /**
   * The feature id for the '<em><b>Constant</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__CONSTANT = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__VALUE = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__NAME = 2;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__ANNOTATIONS = 3;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__TYPE = 4;

  /**
   * The feature id for the '<em><b>Dimensions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__DIMENSIONS = 5;

  /**
   * The number of structural features of the '<em>Variable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstActorImpl <em>Ast Actor</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstActorImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstActor()
   * @generated
   */
  int AST_ACTOR = 4;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__PARAMETERS = 0;

  /**
   * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__INPUTS = 1;

  /**
   * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__OUTPUTS = 2;

  /**
   * The feature id for the '<em><b>Functions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__FUNCTIONS = 3;

  /**
   * The feature id for the '<em><b>Procedures</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__PROCEDURES = 4;

  /**
   * The feature id for the '<em><b>Actions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__ACTIONS = 5;

  /**
   * The feature id for the '<em><b>Initializes</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__INITIALIZES = 6;

  /**
   * The feature id for the '<em><b>State Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__STATE_VARIABLES = 7;

  /**
   * The feature id for the '<em><b>Local Fsms</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__LOCAL_FSMS = 8;

  /**
   * The feature id for the '<em><b>Schedule Fsm</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__SCHEDULE_FSM = 9;

  /**
   * The feature id for the '<em><b>Schedule Reg Exp</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__SCHEDULE_REG_EXP = 10;

  /**
   * The feature id for the '<em><b>Priorities</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR__PRIORITIES = 11;

  /**
   * The number of structural features of the '<em>Ast Actor</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTOR_FEATURE_COUNT = 12;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstPortImpl <em>Ast Port</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstPortImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstPort()
   * @generated
   */
  int AST_PORT = 5;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PORT__ANNOTATIONS = 0;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PORT__TYPE = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PORT__NAME = 2;

  /**
   * The number of structural features of the '<em>Ast Port</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PORT_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.FunctionImpl <em>Function</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.FunctionImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getFunction()
   * @generated
   */
  int FUNCTION = 6;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUNCTION__ANNOTATIONS = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUNCTION__NAME = 1;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUNCTION__PARAMETERS = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUNCTION__TYPE = 3;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUNCTION__VARIABLES = 4;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUNCTION__EXPRESSION = 5;

  /**
   * The number of structural features of the '<em>Function</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUNCTION_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstProcedureImpl <em>Ast Procedure</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstProcedureImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstProcedure()
   * @generated
   */
  int AST_PROCEDURE = 7;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PROCEDURE__ANNOTATIONS = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PROCEDURE__NAME = 1;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PROCEDURE__PARAMETERS = 2;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PROCEDURE__VARIABLES = 3;

  /**
   * The feature id for the '<em><b>Statements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PROCEDURE__STATEMENTS = 4;

  /**
   * The number of structural features of the '<em>Ast Procedure</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_PROCEDURE_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTagImpl <em>Ast Tag</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTagImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTag()
   * @generated
   */
  int AST_TAG = 8;

  /**
   * The feature id for the '<em><b>Identifiers</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TAG__IDENTIFIERS = 0;

  /**
   * The number of structural features of the '<em>Ast Tag</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TAG_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.InequalityImpl <em>Inequality</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.InequalityImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getInequality()
   * @generated
   */
  int INEQUALITY = 9;

  /**
   * The feature id for the '<em><b>Tags</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INEQUALITY__TAGS = 0;

  /**
   * The number of structural features of the '<em>Inequality</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INEQUALITY_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.PriorityImpl <em>Priority</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.PriorityImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getPriority()
   * @generated
   */
  int PRIORITY = 10;

  /**
   * The feature id for the '<em><b>Inequalities</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRIORITY__INEQUALITIES = 0;

  /**
   * The number of structural features of the '<em>Priority</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRIORITY_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ScheduleFsmImpl <em>Schedule Fsm</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ScheduleFsmImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getScheduleFsm()
   * @generated
   */
  int SCHEDULE_FSM = 11;

  /**
   * The feature id for the '<em><b>Initial State</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SCHEDULE_FSM__INITIAL_STATE = 0;

  /**
   * The feature id for the '<em><b>Contents</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SCHEDULE_FSM__CONTENTS = 1;

  /**
   * The number of structural features of the '<em>Schedule Fsm</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SCHEDULE_FSM_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.FsmImpl <em>Fsm</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.FsmImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getFsm()
   * @generated
   */
  int FSM = 12;

  /**
   * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FSM__TRANSITIONS = 0;

  /**
   * The feature id for the '<em><b>States</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FSM__STATES = 1;

  /**
   * The number of structural features of the '<em>Fsm</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FSM_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTransitionImpl <em>Ast Transition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTransitionImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTransition()
   * @generated
   */
  int AST_TRANSITION = 13;

  /**
   * The feature id for the '<em><b>Source</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TRANSITION__SOURCE = 0;

  /**
   * The feature id for the '<em><b>Tag</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TRANSITION__TAG = 1;

  /**
   * The feature id for the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TRANSITION__TARGET = 2;

  /**
   * The feature id for the '<em><b>External Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TRANSITION__EXTERNAL_TARGET = 3;

  /**
   * The number of structural features of the '<em>Ast Transition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TRANSITION_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExternalTargetImpl <em>External Target</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExternalTargetImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExternalTarget()
   * @generated
   */
  int EXTERNAL_TARGET = 14;

  /**
   * The feature id for the '<em><b>Fsm</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTERNAL_TARGET__FSM = 0;

  /**
   * The feature id for the '<em><b>State</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTERNAL_TARGET__STATE = 1;

  /**
   * The feature id for the '<em><b>From</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTERNAL_TARGET__FROM = 2;

  /**
   * The feature id for the '<em><b>To</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTERNAL_TARGET__TO = 3;

  /**
   * The number of structural features of the '<em>External Target</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTERNAL_TARGET_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstStateImpl <em>Ast State</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstStateImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstState()
   * @generated
   */
  int AST_STATE = 15;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_STATE__NAME = 0;

  /**
   * The feature id for the '<em><b>Node</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_STATE__NODE = 1;

  /**
   * The number of structural features of the '<em>Ast State</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_STATE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.RegExpImpl <em>Reg Exp</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.RegExpImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getRegExp()
   * @generated
   */
  int REG_EXP = 16;

  /**
   * The feature id for the '<em><b>Exp</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP__EXP = 0;

  /**
   * The number of structural features of the '<em>Reg Exp</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.LocalFsmImpl <em>Local Fsm</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.LocalFsmImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getLocalFsm()
   * @generated
   */
  int LOCAL_FSM = 17;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FSM__NAME = 0;

  /**
   * The feature id for the '<em><b>Contents</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FSM__CONTENTS = 1;

  /**
   * The number of structural features of the '<em>Local Fsm</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FSM_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstActionImpl <em>Ast Action</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstActionImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstAction()
   * @generated
   */
  int AST_ACTION = 18;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTION__ANNOTATIONS = 0;

  /**
   * The feature id for the '<em><b>Tag</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTION__TAG = 1;

  /**
   * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTION__INPUTS = 2;

  /**
   * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTION__OUTPUTS = 3;

  /**
   * The feature id for the '<em><b>Guard</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTION__GUARD = 4;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTION__VARIABLES = 5;

  /**
   * The feature id for the '<em><b>Statements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTION__STATEMENTS = 6;

  /**
   * The number of structural features of the '<em>Ast Action</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ACTION_FEATURE_COUNT = 7;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.InputPatternImpl <em>Input Pattern</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.InputPatternImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getInputPattern()
   * @generated
   */
  int INPUT_PATTERN = 19;

  /**
   * The feature id for the '<em><b>Port</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_PATTERN__PORT = 0;

  /**
   * The feature id for the '<em><b>Tokens</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_PATTERN__TOKENS = 1;

  /**
   * The feature id for the '<em><b>Repeat</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_PATTERN__REPEAT = 2;

  /**
   * The number of structural features of the '<em>Input Pattern</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_PATTERN_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.OutputPatternImpl <em>Output Pattern</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.OutputPatternImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getOutputPattern()
   * @generated
   */
  int OUTPUT_PATTERN = 20;

  /**
   * The feature id for the '<em><b>Port</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_PATTERN__PORT = 0;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_PATTERN__VALUES = 1;

  /**
   * The feature id for the '<em><b>Repeat</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_PATTERN__REPEAT = 2;

  /**
   * The number of structural features of the '<em>Output Pattern</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_PATTERN_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.GuardImpl <em>Guard</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.GuardImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getGuard()
   * @generated
   */
  int GUARD = 21;

  /**
   * The feature id for the '<em><b>Expressions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GUARD__EXPRESSIONS = 0;

  /**
   * The number of structural features of the '<em>Guard</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GUARD_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.StatementImpl <em>Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.StatementImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatement()
   * @generated
   */
  int STATEMENT = 28;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT__ANNOTATIONS = 0;

  /**
   * The number of structural features of the '<em>Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.StatementAssignImpl <em>Statement Assign</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.StatementAssignImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementAssign()
   * @generated
   */
  int STATEMENT_ASSIGN = 22;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_ASSIGN__ANNOTATIONS = STATEMENT__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_ASSIGN__TARGET = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_ASSIGN__INDEXES = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_ASSIGN__VALUE = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Statement Assign</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_ASSIGN_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.StatementCallImpl <em>Statement Call</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.StatementCallImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementCall()
   * @generated
   */
  int STATEMENT_CALL = 23;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_CALL__ANNOTATIONS = STATEMENT__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Procedure</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_CALL__PROCEDURE = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_CALL__ARGUMENTS = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Statement Call</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_CALL_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.StatementForeachImpl <em>Statement Foreach</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.StatementForeachImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementForeach()
   * @generated
   */
  int STATEMENT_FOREACH = 24;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_FOREACH__ANNOTATIONS = STATEMENT__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Variable</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_FOREACH__VARIABLE = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Lower</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_FOREACH__LOWER = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Higher</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_FOREACH__HIGHER = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Statements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_FOREACH__STATEMENTS = STATEMENT_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Statement Foreach</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_FOREACH_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.StatementIfImpl <em>Statement If</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.StatementIfImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementIf()
   * @generated
   */
  int STATEMENT_IF = 25;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_IF__ANNOTATIONS = STATEMENT__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Condition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_IF__CONDITION = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Then</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_IF__THEN = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Elsifs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_IF__ELSIFS = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Else</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_IF__ELSE = STATEMENT_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Statement If</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_IF_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.StatementElsifImpl <em>Statement Elsif</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.StatementElsifImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementElsif()
   * @generated
   */
  int STATEMENT_ELSIF = 26;

  /**
   * The feature id for the '<em><b>Condition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_ELSIF__CONDITION = 0;

  /**
   * The feature id for the '<em><b>Then</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_ELSIF__THEN = 1;

  /**
   * The number of structural features of the '<em>Statement Elsif</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_ELSIF_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.StatementWhileImpl <em>Statement While</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.StatementWhileImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementWhile()
   * @generated
   */
  int STATEMENT_WHILE = 27;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_WHILE__ANNOTATIONS = STATEMENT__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Condition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_WHILE__CONDITION = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Statements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_WHILE__STATEMENTS = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Statement While</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_WHILE_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstExpressionImpl <em>Ast Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstExpressionImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstExpression()
   * @generated
   */
  int AST_EXPRESSION = 29;

  /**
   * The number of structural features of the '<em>Ast Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_EXPRESSION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionCallImpl <em>Expression Call</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionCallImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionCall()
   * @generated
   */
  int EXPRESSION_CALL = 30;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_CALL__ANNOTATIONS = AST_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Function</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_CALL__FUNCTION = AST_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_CALL__PARAMETERS = AST_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Expression Call</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_CALL_FEATURE_COUNT = AST_EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionIndexImpl <em>Expression Index</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionIndexImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionIndex()
   * @generated
   */
  int EXPRESSION_INDEX = 31;

  /**
   * The feature id for the '<em><b>Source</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_INDEX__SOURCE = AST_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_INDEX__INDEXES = AST_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Expression Index</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_INDEX_FEATURE_COUNT = AST_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionIfImpl <em>Expression If</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionIfImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionIf()
   * @generated
   */
  int EXPRESSION_IF = 32;

  /**
   * The feature id for the '<em><b>Condition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_IF__CONDITION = AST_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Then</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_IF__THEN = AST_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Elsifs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_IF__ELSIFS = AST_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Else</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_IF__ELSE = AST_EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Expression If</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_IF_FEATURE_COUNT = AST_EXPRESSION_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionElsifImpl <em>Expression Elsif</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionElsifImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionElsif()
   * @generated
   */
  int EXPRESSION_ELSIF = 33;

  /**
   * The feature id for the '<em><b>Condition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_ELSIF__CONDITION = 0;

  /**
   * The feature id for the '<em><b>Then</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_ELSIF__THEN = 1;

  /**
   * The number of structural features of the '<em>Expression Elsif</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_ELSIF_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionListImpl <em>Expression List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionListImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionList()
   * @generated
   */
  int EXPRESSION_LIST = 34;

  /**
   * The feature id for the '<em><b>Expressions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_LIST__EXPRESSIONS = AST_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Generators</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_LIST__GENERATORS = AST_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Expression List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_LIST_FEATURE_COUNT = AST_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.GeneratorImpl <em>Generator</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.GeneratorImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getGenerator()
   * @generated
   */
  int GENERATOR = 35;

  /**
   * The feature id for the '<em><b>Variable</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERATOR__VARIABLE = 0;

  /**
   * The feature id for the '<em><b>Lower</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERATOR__LOWER = 1;

  /**
   * The feature id for the '<em><b>Higher</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERATOR__HIGHER = 2;

  /**
   * The number of structural features of the '<em>Generator</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERATOR_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionVariableImpl <em>Expression Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionVariableImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionVariable()
   * @generated
   */
  int EXPRESSION_VARIABLE = 36;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_VARIABLE__VALUE = AST_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Expression Variable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_VARIABLE_FEATURE_COUNT = AST_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionLiteralImpl <em>Expression Literal</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionLiteralImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionLiteral()
   * @generated
   */
  int EXPRESSION_LITERAL = 37;

  /**
   * The number of structural features of the '<em>Expression Literal</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_LITERAL_FEATURE_COUNT = AST_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionBooleanImpl <em>Expression Boolean</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionBooleanImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionBoolean()
   * @generated
   */
  int EXPRESSION_BOOLEAN = 38;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_BOOLEAN__VALUE = EXPRESSION_LITERAL_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Expression Boolean</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_BOOLEAN_FEATURE_COUNT = EXPRESSION_LITERAL_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionFloatImpl <em>Expression Float</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionFloatImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionFloat()
   * @generated
   */
  int EXPRESSION_FLOAT = 39;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_FLOAT__VALUE = EXPRESSION_LITERAL_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Expression Float</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_FLOAT_FEATURE_COUNT = EXPRESSION_LITERAL_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionIntegerImpl <em>Expression Integer</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionIntegerImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionInteger()
   * @generated
   */
  int EXPRESSION_INTEGER = 40;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_INTEGER__VALUE = EXPRESSION_LITERAL_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Expression Integer</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_INTEGER_FEATURE_COUNT = EXPRESSION_LITERAL_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionStringImpl <em>Expression String</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionStringImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionString()
   * @generated
   */
  int EXPRESSION_STRING = 41;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_STRING__VALUE = EXPRESSION_LITERAL_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Expression String</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_STRING_FEATURE_COUNT = EXPRESSION_LITERAL_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTypeImpl <em>Ast Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTypeImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstType()
   * @generated
   */
  int AST_TYPE = 42;

  /**
   * The number of structural features of the '<em>Ast Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTypeBoolImpl <em>Ast Type Bool</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTypeBoolImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeBool()
   * @generated
   */
  int AST_TYPE_BOOL = 43;

  /**
   * The number of structural features of the '<em>Ast Type Bool</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_BOOL_FEATURE_COUNT = AST_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTypeFloatImpl <em>Ast Type Float</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTypeFloatImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeFloat()
   * @generated
   */
  int AST_TYPE_FLOAT = 44;

  /**
   * The number of structural features of the '<em>Ast Type Float</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_FLOAT_FEATURE_COUNT = AST_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTypeHalfImpl <em>Ast Type Half</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTypeHalfImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeHalf()
   * @generated
   */
  int AST_TYPE_HALF = 45;

  /**
   * The number of structural features of the '<em>Ast Type Half</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_HALF_FEATURE_COUNT = AST_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTypeDoubleImpl <em>Ast Type Double</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTypeDoubleImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeDouble()
   * @generated
   */
  int AST_TYPE_DOUBLE = 46;

  /**
   * The number of structural features of the '<em>Ast Type Double</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_DOUBLE_FEATURE_COUNT = AST_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTypeIntImpl <em>Ast Type Int</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTypeIntImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeInt()
   * @generated
   */
  int AST_TYPE_INT = 47;

  /**
   * The feature id for the '<em><b>Size</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_INT__SIZE = AST_TYPE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Ast Type Int</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_INT_FEATURE_COUNT = AST_TYPE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTypeListImpl <em>Ast Type List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTypeListImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeList()
   * @generated
   */
  int AST_TYPE_LIST = 48;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_LIST__TYPE = AST_TYPE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Size</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_LIST__SIZE = AST_TYPE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Ast Type List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_LIST_FEATURE_COUNT = AST_TYPE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTypeStringImpl <em>Ast Type String</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTypeStringImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeString()
   * @generated
   */
  int AST_TYPE_STRING = 49;

  /**
   * The number of structural features of the '<em>Ast Type String</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_STRING_FEATURE_COUNT = AST_TYPE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstTypeUintImpl <em>Ast Type Uint</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstTypeUintImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeUint()
   * @generated
   */
  int AST_TYPE_UINT = 50;

  /**
   * The feature id for the '<em><b>Size</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_UINT__SIZE = AST_TYPE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Ast Type Uint</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_TYPE_UINT_FEATURE_COUNT = AST_TYPE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.VariableReferenceImpl <em>Variable Reference</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.VariableReferenceImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getVariableReference()
   * @generated
   */
  int VARIABLE_REFERENCE = 51;

  /**
   * The feature id for the '<em><b>Variable</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_REFERENCE__VARIABLE = 0;

  /**
   * The number of structural features of the '<em>Variable Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_REFERENCE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AstAnnotationImpl <em>Ast Annotation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AstAnnotationImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstAnnotation()
   * @generated
   */
  int AST_ANNOTATION = 52;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ANNOTATION__NAME = 0;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ANNOTATION__ARGUMENTS = 1;

  /**
   * The number of structural features of the '<em>Ast Annotation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AST_ANNOTATION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.AnnotationArgumentImpl <em>Annotation Argument</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.AnnotationArgumentImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAnnotationArgument()
   * @generated
   */
  int ANNOTATION_ARGUMENT = 53;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_ARGUMENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_ARGUMENT__VALUE = 1;

  /**
   * The number of structural features of the '<em>Annotation Argument</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNOTATION_ARGUMENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.RegExpBinaryImpl <em>Reg Exp Binary</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.RegExpBinaryImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getRegExpBinary()
   * @generated
   */
  int REG_EXP_BINARY = 54;

  /**
   * The feature id for the '<em><b>Exp</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_BINARY__EXP = REG_EXP__EXP;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_BINARY__LEFT = REG_EXP_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_BINARY__OPERATOR = REG_EXP_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_BINARY__RIGHT = REG_EXP_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Reg Exp Binary</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_BINARY_FEATURE_COUNT = REG_EXP_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.RegExpUnaryImpl <em>Reg Exp Unary</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.RegExpUnaryImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getRegExpUnary()
   * @generated
   */
  int REG_EXP_UNARY = 55;

  /**
   * The feature id for the '<em><b>Exp</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_UNARY__EXP = REG_EXP__EXP;

  /**
   * The feature id for the '<em><b>Child</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_UNARY__CHILD = REG_EXP_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Unary Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_UNARY__UNARY_OPERATOR = REG_EXP_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Reg Exp Unary</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_UNARY_FEATURE_COUNT = REG_EXP_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.RegExpTagImpl <em>Reg Exp Tag</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.RegExpTagImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getRegExpTag()
   * @generated
   */
  int REG_EXP_TAG = 56;

  /**
   * The feature id for the '<em><b>Exp</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_TAG__EXP = REG_EXP__EXP;

  /**
   * The feature id for the '<em><b>Tag</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_TAG__TAG = REG_EXP_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Reg Exp Tag</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REG_EXP_TAG_FEATURE_COUNT = REG_EXP_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionBinaryImpl <em>Expression Binary</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionBinaryImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionBinary()
   * @generated
   */
  int EXPRESSION_BINARY = 57;

  /**
   * The feature id for the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_BINARY__LEFT = AST_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_BINARY__OPERATOR = AST_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_BINARY__RIGHT = AST_EXPRESSION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Expression Binary</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_BINARY_FEATURE_COUNT = AST_EXPRESSION_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link net.sf.orcc.cal.cal.impl.ExpressionUnaryImpl <em>Expression Unary</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see net.sf.orcc.cal.cal.impl.ExpressionUnaryImpl
   * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionUnary()
   * @generated
   */
  int EXPRESSION_UNARY = 58;

  /**
   * The feature id for the '<em><b>Unary Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_UNARY__UNARY_OPERATOR = AST_EXPRESSION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_UNARY__EXPRESSION = AST_EXPRESSION_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Expression Unary</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_UNARY_FEATURE_COUNT = AST_EXPRESSION_FEATURE_COUNT + 2;


  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstEntity <em>Ast Entity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Entity</em>'.
   * @see net.sf.orcc.cal.cal.AstEntity
   * @generated
   */
  EClass getAstEntity();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.AstEntity#getPackage <em>Package</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Package</em>'.
   * @see net.sf.orcc.cal.cal.AstEntity#getPackage()
   * @see #getAstEntity()
   * @generated
   */
  EAttribute getAstEntity_Package();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstEntity#getImports <em>Imports</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Imports</em>'.
   * @see net.sf.orcc.cal.cal.AstEntity#getImports()
   * @see #getAstEntity()
   * @generated
   */
  EReference getAstEntity_Imports();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstEntity#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotations</em>'.
   * @see net.sf.orcc.cal.cal.AstEntity#getAnnotations()
   * @see #getAstEntity()
   * @generated
   */
  EReference getAstEntity_Annotations();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.AstEntity#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see net.sf.orcc.cal.cal.AstEntity#getName()
   * @see #getAstEntity()
   * @generated
   */
  EAttribute getAstEntity_Name();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstEntity#getActor <em>Actor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Actor</em>'.
   * @see net.sf.orcc.cal.cal.AstEntity#getActor()
   * @see #getAstEntity()
   * @generated
   */
  EReference getAstEntity_Actor();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstEntity#getUnit <em>Unit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Unit</em>'.
   * @see net.sf.orcc.cal.cal.AstEntity#getUnit()
   * @see #getAstEntity()
   * @generated
   */
  EReference getAstEntity_Unit();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.Import <em>Import</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Import</em>'.
   * @see net.sf.orcc.cal.cal.Import
   * @generated
   */
  EClass getImport();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.Import#getImportedNamespace <em>Imported Namespace</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Imported Namespace</em>'.
   * @see net.sf.orcc.cal.cal.Import#getImportedNamespace()
   * @see #getImport()
   * @generated
   */
  EAttribute getImport_ImportedNamespace();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstUnit <em>Ast Unit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Unit</em>'.
   * @see net.sf.orcc.cal.cal.AstUnit
   * @generated
   */
  EClass getAstUnit();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstUnit#getFunctions <em>Functions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Functions</em>'.
   * @see net.sf.orcc.cal.cal.AstUnit#getFunctions()
   * @see #getAstUnit()
   * @generated
   */
  EReference getAstUnit_Functions();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstUnit#getProcedures <em>Procedures</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Procedures</em>'.
   * @see net.sf.orcc.cal.cal.AstUnit#getProcedures()
   * @see #getAstUnit()
   * @generated
   */
  EReference getAstUnit_Procedures();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstUnit#getVariables <em>Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variables</em>'.
   * @see net.sf.orcc.cal.cal.AstUnit#getVariables()
   * @see #getAstUnit()
   * @generated
   */
  EReference getAstUnit_Variables();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.Variable <em>Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Variable</em>'.
   * @see net.sf.orcc.cal.cal.Variable
   * @generated
   */
  EClass getVariable();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.Variable#isConstant <em>Constant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Constant</em>'.
   * @see net.sf.orcc.cal.cal.Variable#isConstant()
   * @see #getVariable()
   * @generated
   */
  EAttribute getVariable_Constant();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.Variable#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see net.sf.orcc.cal.cal.Variable#getValue()
   * @see #getVariable()
   * @generated
   */
  EReference getVariable_Value();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.Variable#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see net.sf.orcc.cal.cal.Variable#getName()
   * @see #getVariable()
   * @generated
   */
  EAttribute getVariable_Name();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Variable#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotations</em>'.
   * @see net.sf.orcc.cal.cal.Variable#getAnnotations()
   * @see #getVariable()
   * @generated
   */
  EReference getVariable_Annotations();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.Variable#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see net.sf.orcc.cal.cal.Variable#getType()
   * @see #getVariable()
   * @generated
   */
  EReference getVariable_Type();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Variable#getDimensions <em>Dimensions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Dimensions</em>'.
   * @see net.sf.orcc.cal.cal.Variable#getDimensions()
   * @see #getVariable()
   * @generated
   */
  EReference getVariable_Dimensions();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstActor <em>Ast Actor</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Actor</em>'.
   * @see net.sf.orcc.cal.cal.AstActor
   * @generated
   */
  EClass getAstActor();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getParameters()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_Parameters();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getInputs <em>Inputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Inputs</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getInputs()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_Inputs();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getOutputs <em>Outputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Outputs</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getOutputs()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_Outputs();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getFunctions <em>Functions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Functions</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getFunctions()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_Functions();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getProcedures <em>Procedures</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Procedures</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getProcedures()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_Procedures();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getActions <em>Actions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Actions</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getActions()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_Actions();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getInitializes <em>Initializes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Initializes</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getInitializes()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_Initializes();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getStateVariables <em>State Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>State Variables</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getStateVariables()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_StateVariables();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getLocalFsms <em>Local Fsms</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Local Fsms</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getLocalFsms()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_LocalFsms();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstActor#getScheduleFsm <em>Schedule Fsm</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Schedule Fsm</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getScheduleFsm()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_ScheduleFsm();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstActor#getScheduleRegExp <em>Schedule Reg Exp</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Schedule Reg Exp</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getScheduleRegExp()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_ScheduleRegExp();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstActor#getPriorities <em>Priorities</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Priorities</em>'.
   * @see net.sf.orcc.cal.cal.AstActor#getPriorities()
   * @see #getAstActor()
   * @generated
   */
  EReference getAstActor_Priorities();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstPort <em>Ast Port</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Port</em>'.
   * @see net.sf.orcc.cal.cal.AstPort
   * @generated
   */
  EClass getAstPort();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstPort#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotations</em>'.
   * @see net.sf.orcc.cal.cal.AstPort#getAnnotations()
   * @see #getAstPort()
   * @generated
   */
  EReference getAstPort_Annotations();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstPort#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see net.sf.orcc.cal.cal.AstPort#getType()
   * @see #getAstPort()
   * @generated
   */
  EReference getAstPort_Type();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.AstPort#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see net.sf.orcc.cal.cal.AstPort#getName()
   * @see #getAstPort()
   * @generated
   */
  EAttribute getAstPort_Name();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.Function <em>Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Function</em>'.
   * @see net.sf.orcc.cal.cal.Function
   * @generated
   */
  EClass getFunction();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Function#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotations</em>'.
   * @see net.sf.orcc.cal.cal.Function#getAnnotations()
   * @see #getFunction()
   * @generated
   */
  EReference getFunction_Annotations();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.Function#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see net.sf.orcc.cal.cal.Function#getName()
   * @see #getFunction()
   * @generated
   */
  EAttribute getFunction_Name();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Function#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see net.sf.orcc.cal.cal.Function#getParameters()
   * @see #getFunction()
   * @generated
   */
  EReference getFunction_Parameters();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.Function#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see net.sf.orcc.cal.cal.Function#getType()
   * @see #getFunction()
   * @generated
   */
  EReference getFunction_Type();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Function#getVariables <em>Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variables</em>'.
   * @see net.sf.orcc.cal.cal.Function#getVariables()
   * @see #getFunction()
   * @generated
   */
  EReference getFunction_Variables();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.Function#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see net.sf.orcc.cal.cal.Function#getExpression()
   * @see #getFunction()
   * @generated
   */
  EReference getFunction_Expression();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstProcedure <em>Ast Procedure</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Procedure</em>'.
   * @see net.sf.orcc.cal.cal.AstProcedure
   * @generated
   */
  EClass getAstProcedure();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstProcedure#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotations</em>'.
   * @see net.sf.orcc.cal.cal.AstProcedure#getAnnotations()
   * @see #getAstProcedure()
   * @generated
   */
  EReference getAstProcedure_Annotations();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.AstProcedure#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see net.sf.orcc.cal.cal.AstProcedure#getName()
   * @see #getAstProcedure()
   * @generated
   */
  EAttribute getAstProcedure_Name();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstProcedure#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see net.sf.orcc.cal.cal.AstProcedure#getParameters()
   * @see #getAstProcedure()
   * @generated
   */
  EReference getAstProcedure_Parameters();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstProcedure#getVariables <em>Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variables</em>'.
   * @see net.sf.orcc.cal.cal.AstProcedure#getVariables()
   * @see #getAstProcedure()
   * @generated
   */
  EReference getAstProcedure_Variables();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstProcedure#getStatements <em>Statements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Statements</em>'.
   * @see net.sf.orcc.cal.cal.AstProcedure#getStatements()
   * @see #getAstProcedure()
   * @generated
   */
  EReference getAstProcedure_Statements();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTag <em>Ast Tag</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Tag</em>'.
   * @see net.sf.orcc.cal.cal.AstTag
   * @generated
   */
  EClass getAstTag();

  /**
   * Returns the meta object for the attribute list '{@link net.sf.orcc.cal.cal.AstTag#getIdentifiers <em>Identifiers</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Identifiers</em>'.
   * @see net.sf.orcc.cal.cal.AstTag#getIdentifiers()
   * @see #getAstTag()
   * @generated
   */
  EAttribute getAstTag_Identifiers();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.Inequality <em>Inequality</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Inequality</em>'.
   * @see net.sf.orcc.cal.cal.Inequality
   * @generated
   */
  EClass getInequality();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Inequality#getTags <em>Tags</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Tags</em>'.
   * @see net.sf.orcc.cal.cal.Inequality#getTags()
   * @see #getInequality()
   * @generated
   */
  EReference getInequality_Tags();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.Priority <em>Priority</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Priority</em>'.
   * @see net.sf.orcc.cal.cal.Priority
   * @generated
   */
  EClass getPriority();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Priority#getInequalities <em>Inequalities</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Inequalities</em>'.
   * @see net.sf.orcc.cal.cal.Priority#getInequalities()
   * @see #getPriority()
   * @generated
   */
  EReference getPriority_Inequalities();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ScheduleFsm <em>Schedule Fsm</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Schedule Fsm</em>'.
   * @see net.sf.orcc.cal.cal.ScheduleFsm
   * @generated
   */
  EClass getScheduleFsm();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.ScheduleFsm#getInitialState <em>Initial State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Initial State</em>'.
   * @see net.sf.orcc.cal.cal.ScheduleFsm#getInitialState()
   * @see #getScheduleFsm()
   * @generated
   */
  EReference getScheduleFsm_InitialState();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ScheduleFsm#getContents <em>Contents</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Contents</em>'.
   * @see net.sf.orcc.cal.cal.ScheduleFsm#getContents()
   * @see #getScheduleFsm()
   * @generated
   */
  EReference getScheduleFsm_Contents();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.Fsm <em>Fsm</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Fsm</em>'.
   * @see net.sf.orcc.cal.cal.Fsm
   * @generated
   */
  EClass getFsm();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Fsm#getTransitions <em>Transitions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Transitions</em>'.
   * @see net.sf.orcc.cal.cal.Fsm#getTransitions()
   * @see #getFsm()
   * @generated
   */
  EReference getFsm_Transitions();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Fsm#getStates <em>States</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>States</em>'.
   * @see net.sf.orcc.cal.cal.Fsm#getStates()
   * @see #getFsm()
   * @generated
   */
  EReference getFsm_States();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTransition <em>Ast Transition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Transition</em>'.
   * @see net.sf.orcc.cal.cal.AstTransition
   * @generated
   */
  EClass getAstTransition();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.AstTransition#getSource <em>Source</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Source</em>'.
   * @see net.sf.orcc.cal.cal.AstTransition#getSource()
   * @see #getAstTransition()
   * @generated
   */
  EReference getAstTransition_Source();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstTransition#getTag <em>Tag</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Tag</em>'.
   * @see net.sf.orcc.cal.cal.AstTransition#getTag()
   * @see #getAstTransition()
   * @generated
   */
  EReference getAstTransition_Tag();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.AstTransition#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Target</em>'.
   * @see net.sf.orcc.cal.cal.AstTransition#getTarget()
   * @see #getAstTransition()
   * @generated
   */
  EReference getAstTransition_Target();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstTransition#getExternalTarget <em>External Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>External Target</em>'.
   * @see net.sf.orcc.cal.cal.AstTransition#getExternalTarget()
   * @see #getAstTransition()
   * @generated
   */
  EReference getAstTransition_ExternalTarget();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExternalTarget <em>External Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>External Target</em>'.
   * @see net.sf.orcc.cal.cal.ExternalTarget
   * @generated
   */
  EClass getExternalTarget();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.ExternalTarget#getFsm <em>Fsm</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Fsm</em>'.
   * @see net.sf.orcc.cal.cal.ExternalTarget#getFsm()
   * @see #getExternalTarget()
   * @generated
   */
  EReference getExternalTarget_Fsm();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.ExternalTarget#getState <em>State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>State</em>'.
   * @see net.sf.orcc.cal.cal.ExternalTarget#getState()
   * @see #getExternalTarget()
   * @generated
   */
  EReference getExternalTarget_State();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.ExternalTarget#getFrom <em>From</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>From</em>'.
   * @see net.sf.orcc.cal.cal.ExternalTarget#getFrom()
   * @see #getExternalTarget()
   * @generated
   */
  EReference getExternalTarget_From();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.ExternalTarget#getTo <em>To</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>To</em>'.
   * @see net.sf.orcc.cal.cal.ExternalTarget#getTo()
   * @see #getExternalTarget()
   * @generated
   */
  EReference getExternalTarget_To();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstState <em>Ast State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast State</em>'.
   * @see net.sf.orcc.cal.cal.AstState
   * @generated
   */
  EClass getAstState();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.AstState#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see net.sf.orcc.cal.cal.AstState#getName()
   * @see #getAstState()
   * @generated
   */
  EAttribute getAstState_Name();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.AstState#getNode <em>Node</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Node</em>'.
   * @see net.sf.orcc.cal.cal.AstState#getNode()
   * @see #getAstState()
   * @generated
   */
  EAttribute getAstState_Node();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.RegExp <em>Reg Exp</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Reg Exp</em>'.
   * @see net.sf.orcc.cal.cal.RegExp
   * @generated
   */
  EClass getRegExp();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.RegExp#getExp <em>Exp</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Exp</em>'.
   * @see net.sf.orcc.cal.cal.RegExp#getExp()
   * @see #getRegExp()
   * @generated
   */
  EReference getRegExp_Exp();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.LocalFsm <em>Local Fsm</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Local Fsm</em>'.
   * @see net.sf.orcc.cal.cal.LocalFsm
   * @generated
   */
  EClass getLocalFsm();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.LocalFsm#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see net.sf.orcc.cal.cal.LocalFsm#getName()
   * @see #getLocalFsm()
   * @generated
   */
  EAttribute getLocalFsm_Name();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.LocalFsm#getContents <em>Contents</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Contents</em>'.
   * @see net.sf.orcc.cal.cal.LocalFsm#getContents()
   * @see #getLocalFsm()
   * @generated
   */
  EReference getLocalFsm_Contents();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstAction <em>Ast Action</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Action</em>'.
   * @see net.sf.orcc.cal.cal.AstAction
   * @generated
   */
  EClass getAstAction();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstAction#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotations</em>'.
   * @see net.sf.orcc.cal.cal.AstAction#getAnnotations()
   * @see #getAstAction()
   * @generated
   */
  EReference getAstAction_Annotations();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstAction#getTag <em>Tag</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Tag</em>'.
   * @see net.sf.orcc.cal.cal.AstAction#getTag()
   * @see #getAstAction()
   * @generated
   */
  EReference getAstAction_Tag();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstAction#getInputs <em>Inputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Inputs</em>'.
   * @see net.sf.orcc.cal.cal.AstAction#getInputs()
   * @see #getAstAction()
   * @generated
   */
  EReference getAstAction_Inputs();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstAction#getOutputs <em>Outputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Outputs</em>'.
   * @see net.sf.orcc.cal.cal.AstAction#getOutputs()
   * @see #getAstAction()
   * @generated
   */
  EReference getAstAction_Outputs();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstAction#getGuard <em>Guard</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Guard</em>'.
   * @see net.sf.orcc.cal.cal.AstAction#getGuard()
   * @see #getAstAction()
   * @generated
   */
  EReference getAstAction_Guard();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstAction#getVariables <em>Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variables</em>'.
   * @see net.sf.orcc.cal.cal.AstAction#getVariables()
   * @see #getAstAction()
   * @generated
   */
  EReference getAstAction_Variables();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstAction#getStatements <em>Statements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Statements</em>'.
   * @see net.sf.orcc.cal.cal.AstAction#getStatements()
   * @see #getAstAction()
   * @generated
   */
  EReference getAstAction_Statements();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.InputPattern <em>Input Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Input Pattern</em>'.
   * @see net.sf.orcc.cal.cal.InputPattern
   * @generated
   */
  EClass getInputPattern();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.InputPattern#getPort <em>Port</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Port</em>'.
   * @see net.sf.orcc.cal.cal.InputPattern#getPort()
   * @see #getInputPattern()
   * @generated
   */
  EReference getInputPattern_Port();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.InputPattern#getTokens <em>Tokens</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Tokens</em>'.
   * @see net.sf.orcc.cal.cal.InputPattern#getTokens()
   * @see #getInputPattern()
   * @generated
   */
  EReference getInputPattern_Tokens();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.InputPattern#getRepeat <em>Repeat</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Repeat</em>'.
   * @see net.sf.orcc.cal.cal.InputPattern#getRepeat()
   * @see #getInputPattern()
   * @generated
   */
  EReference getInputPattern_Repeat();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.OutputPattern <em>Output Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Output Pattern</em>'.
   * @see net.sf.orcc.cal.cal.OutputPattern
   * @generated
   */
  EClass getOutputPattern();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.OutputPattern#getPort <em>Port</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Port</em>'.
   * @see net.sf.orcc.cal.cal.OutputPattern#getPort()
   * @see #getOutputPattern()
   * @generated
   */
  EReference getOutputPattern_Port();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.OutputPattern#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Values</em>'.
   * @see net.sf.orcc.cal.cal.OutputPattern#getValues()
   * @see #getOutputPattern()
   * @generated
   */
  EReference getOutputPattern_Values();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.OutputPattern#getRepeat <em>Repeat</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Repeat</em>'.
   * @see net.sf.orcc.cal.cal.OutputPattern#getRepeat()
   * @see #getOutputPattern()
   * @generated
   */
  EReference getOutputPattern_Repeat();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.Guard <em>Guard</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Guard</em>'.
   * @see net.sf.orcc.cal.cal.Guard
   * @generated
   */
  EClass getGuard();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Guard#getExpressions <em>Expressions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Expressions</em>'.
   * @see net.sf.orcc.cal.cal.Guard#getExpressions()
   * @see #getGuard()
   * @generated
   */
  EReference getGuard_Expressions();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.StatementAssign <em>Statement Assign</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Statement Assign</em>'.
   * @see net.sf.orcc.cal.cal.StatementAssign
   * @generated
   */
  EClass getStatementAssign();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.StatementAssign#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Target</em>'.
   * @see net.sf.orcc.cal.cal.StatementAssign#getTarget()
   * @see #getStatementAssign()
   * @generated
   */
  EReference getStatementAssign_Target();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.StatementAssign#getIndexes <em>Indexes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Indexes</em>'.
   * @see net.sf.orcc.cal.cal.StatementAssign#getIndexes()
   * @see #getStatementAssign()
   * @generated
   */
  EReference getStatementAssign_Indexes();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.StatementAssign#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see net.sf.orcc.cal.cal.StatementAssign#getValue()
   * @see #getStatementAssign()
   * @generated
   */
  EReference getStatementAssign_Value();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.StatementCall <em>Statement Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Statement Call</em>'.
   * @see net.sf.orcc.cal.cal.StatementCall
   * @generated
   */
  EClass getStatementCall();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.StatementCall#getProcedure <em>Procedure</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Procedure</em>'.
   * @see net.sf.orcc.cal.cal.StatementCall#getProcedure()
   * @see #getStatementCall()
   * @generated
   */
  EReference getStatementCall_Procedure();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.StatementCall#getArguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arguments</em>'.
   * @see net.sf.orcc.cal.cal.StatementCall#getArguments()
   * @see #getStatementCall()
   * @generated
   */
  EReference getStatementCall_Arguments();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.StatementForeach <em>Statement Foreach</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Statement Foreach</em>'.
   * @see net.sf.orcc.cal.cal.StatementForeach
   * @generated
   */
  EClass getStatementForeach();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.StatementForeach#getVariable <em>Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Variable</em>'.
   * @see net.sf.orcc.cal.cal.StatementForeach#getVariable()
   * @see #getStatementForeach()
   * @generated
   */
  EReference getStatementForeach_Variable();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.StatementForeach#getLower <em>Lower</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lower</em>'.
   * @see net.sf.orcc.cal.cal.StatementForeach#getLower()
   * @see #getStatementForeach()
   * @generated
   */
  EReference getStatementForeach_Lower();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.StatementForeach#getHigher <em>Higher</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Higher</em>'.
   * @see net.sf.orcc.cal.cal.StatementForeach#getHigher()
   * @see #getStatementForeach()
   * @generated
   */
  EReference getStatementForeach_Higher();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.StatementForeach#getStatements <em>Statements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Statements</em>'.
   * @see net.sf.orcc.cal.cal.StatementForeach#getStatements()
   * @see #getStatementForeach()
   * @generated
   */
  EReference getStatementForeach_Statements();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.StatementIf <em>Statement If</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Statement If</em>'.
   * @see net.sf.orcc.cal.cal.StatementIf
   * @generated
   */
  EClass getStatementIf();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.StatementIf#getCondition <em>Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Condition</em>'.
   * @see net.sf.orcc.cal.cal.StatementIf#getCondition()
   * @see #getStatementIf()
   * @generated
   */
  EReference getStatementIf_Condition();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.StatementIf#getThen <em>Then</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Then</em>'.
   * @see net.sf.orcc.cal.cal.StatementIf#getThen()
   * @see #getStatementIf()
   * @generated
   */
  EReference getStatementIf_Then();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.StatementIf#getElsifs <em>Elsifs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elsifs</em>'.
   * @see net.sf.orcc.cal.cal.StatementIf#getElsifs()
   * @see #getStatementIf()
   * @generated
   */
  EReference getStatementIf_Elsifs();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.StatementIf#getElse <em>Else</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Else</em>'.
   * @see net.sf.orcc.cal.cal.StatementIf#getElse()
   * @see #getStatementIf()
   * @generated
   */
  EReference getStatementIf_Else();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.StatementElsif <em>Statement Elsif</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Statement Elsif</em>'.
   * @see net.sf.orcc.cal.cal.StatementElsif
   * @generated
   */
  EClass getStatementElsif();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.StatementElsif#getCondition <em>Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Condition</em>'.
   * @see net.sf.orcc.cal.cal.StatementElsif#getCondition()
   * @see #getStatementElsif()
   * @generated
   */
  EReference getStatementElsif_Condition();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.StatementElsif#getThen <em>Then</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Then</em>'.
   * @see net.sf.orcc.cal.cal.StatementElsif#getThen()
   * @see #getStatementElsif()
   * @generated
   */
  EReference getStatementElsif_Then();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.StatementWhile <em>Statement While</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Statement While</em>'.
   * @see net.sf.orcc.cal.cal.StatementWhile
   * @generated
   */
  EClass getStatementWhile();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.StatementWhile#getCondition <em>Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Condition</em>'.
   * @see net.sf.orcc.cal.cal.StatementWhile#getCondition()
   * @see #getStatementWhile()
   * @generated
   */
  EReference getStatementWhile_Condition();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.StatementWhile#getStatements <em>Statements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Statements</em>'.
   * @see net.sf.orcc.cal.cal.StatementWhile#getStatements()
   * @see #getStatementWhile()
   * @generated
   */
  EReference getStatementWhile_Statements();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.Statement <em>Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Statement</em>'.
   * @see net.sf.orcc.cal.cal.Statement
   * @generated
   */
  EClass getStatement();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.Statement#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotations</em>'.
   * @see net.sf.orcc.cal.cal.Statement#getAnnotations()
   * @see #getStatement()
   * @generated
   */
  EReference getStatement_Annotations();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstExpression <em>Ast Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Expression</em>'.
   * @see net.sf.orcc.cal.cal.AstExpression
   * @generated
   */
  EClass getAstExpression();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionCall <em>Expression Call</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Call</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionCall
   * @generated
   */
  EClass getExpressionCall();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.ExpressionCall#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotations</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionCall#getAnnotations()
   * @see #getExpressionCall()
   * @generated
   */
  EReference getExpressionCall_Annotations();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.ExpressionCall#getFunction <em>Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Function</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionCall#getFunction()
   * @see #getExpressionCall()
   * @generated
   */
  EReference getExpressionCall_Function();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.ExpressionCall#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionCall#getParameters()
   * @see #getExpressionCall()
   * @generated
   */
  EReference getExpressionCall_Parameters();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionIndex <em>Expression Index</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Index</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionIndex
   * @generated
   */
  EClass getExpressionIndex();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionIndex#getSource <em>Source</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Source</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionIndex#getSource()
   * @see #getExpressionIndex()
   * @generated
   */
  EReference getExpressionIndex_Source();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.ExpressionIndex#getIndexes <em>Indexes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Indexes</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionIndex#getIndexes()
   * @see #getExpressionIndex()
   * @generated
   */
  EReference getExpressionIndex_Indexes();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionIf <em>Expression If</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression If</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionIf
   * @generated
   */
  EClass getExpressionIf();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionIf#getCondition <em>Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Condition</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionIf#getCondition()
   * @see #getExpressionIf()
   * @generated
   */
  EReference getExpressionIf_Condition();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionIf#getThen <em>Then</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Then</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionIf#getThen()
   * @see #getExpressionIf()
   * @generated
   */
  EReference getExpressionIf_Then();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.ExpressionIf#getElsifs <em>Elsifs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elsifs</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionIf#getElsifs()
   * @see #getExpressionIf()
   * @generated
   */
  EReference getExpressionIf_Elsifs();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionIf#getElse <em>Else</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Else</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionIf#getElse()
   * @see #getExpressionIf()
   * @generated
   */
  EReference getExpressionIf_Else();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionElsif <em>Expression Elsif</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Elsif</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionElsif
   * @generated
   */
  EClass getExpressionElsif();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionElsif#getCondition <em>Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Condition</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionElsif#getCondition()
   * @see #getExpressionElsif()
   * @generated
   */
  EReference getExpressionElsif_Condition();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionElsif#getThen <em>Then</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Then</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionElsif#getThen()
   * @see #getExpressionElsif()
   * @generated
   */
  EReference getExpressionElsif_Then();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionList <em>Expression List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression List</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionList
   * @generated
   */
  EClass getExpressionList();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.ExpressionList#getExpressions <em>Expressions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Expressions</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionList#getExpressions()
   * @see #getExpressionList()
   * @generated
   */
  EReference getExpressionList_Expressions();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.ExpressionList#getGenerators <em>Generators</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Generators</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionList#getGenerators()
   * @see #getExpressionList()
   * @generated
   */
  EReference getExpressionList_Generators();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.Generator <em>Generator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Generator</em>'.
   * @see net.sf.orcc.cal.cal.Generator
   * @generated
   */
  EClass getGenerator();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.Generator#getVariable <em>Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Variable</em>'.
   * @see net.sf.orcc.cal.cal.Generator#getVariable()
   * @see #getGenerator()
   * @generated
   */
  EReference getGenerator_Variable();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.Generator#getLower <em>Lower</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lower</em>'.
   * @see net.sf.orcc.cal.cal.Generator#getLower()
   * @see #getGenerator()
   * @generated
   */
  EReference getGenerator_Lower();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.Generator#getHigher <em>Higher</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Higher</em>'.
   * @see net.sf.orcc.cal.cal.Generator#getHigher()
   * @see #getGenerator()
   * @generated
   */
  EReference getGenerator_Higher();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionVariable <em>Expression Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Variable</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionVariable
   * @generated
   */
  EClass getExpressionVariable();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionVariable#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionVariable#getValue()
   * @see #getExpressionVariable()
   * @generated
   */
  EReference getExpressionVariable_Value();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionLiteral <em>Expression Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Literal</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionLiteral
   * @generated
   */
  EClass getExpressionLiteral();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionBoolean <em>Expression Boolean</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Boolean</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionBoolean
   * @generated
   */
  EClass getExpressionBoolean();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.ExpressionBoolean#isValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionBoolean#isValue()
   * @see #getExpressionBoolean()
   * @generated
   */
  EAttribute getExpressionBoolean_Value();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionFloat <em>Expression Float</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Float</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionFloat
   * @generated
   */
  EClass getExpressionFloat();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.ExpressionFloat#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionFloat#getValue()
   * @see #getExpressionFloat()
   * @generated
   */
  EAttribute getExpressionFloat_Value();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionInteger <em>Expression Integer</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Integer</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionInteger
   * @generated
   */
  EClass getExpressionInteger();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.ExpressionInteger#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionInteger#getValue()
   * @see #getExpressionInteger()
   * @generated
   */
  EAttribute getExpressionInteger_Value();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionString <em>Expression String</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression String</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionString
   * @generated
   */
  EClass getExpressionString();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.ExpressionString#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionString#getValue()
   * @see #getExpressionString()
   * @generated
   */
  EAttribute getExpressionString_Value();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstType <em>Ast Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Type</em>'.
   * @see net.sf.orcc.cal.cal.AstType
   * @generated
   */
  EClass getAstType();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTypeBool <em>Ast Type Bool</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Type Bool</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeBool
   * @generated
   */
  EClass getAstTypeBool();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTypeFloat <em>Ast Type Float</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Type Float</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeFloat
   * @generated
   */
  EClass getAstTypeFloat();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTypeHalf <em>Ast Type Half</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Type Half</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeHalf
   * @generated
   */
  EClass getAstTypeHalf();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTypeDouble <em>Ast Type Double</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Type Double</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeDouble
   * @generated
   */
  EClass getAstTypeDouble();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTypeInt <em>Ast Type Int</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Type Int</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeInt
   * @generated
   */
  EClass getAstTypeInt();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstTypeInt#getSize <em>Size</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Size</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeInt#getSize()
   * @see #getAstTypeInt()
   * @generated
   */
  EReference getAstTypeInt_Size();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTypeList <em>Ast Type List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Type List</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeList
   * @generated
   */
  EClass getAstTypeList();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstTypeList#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Type</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeList#getType()
   * @see #getAstTypeList()
   * @generated
   */
  EReference getAstTypeList_Type();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstTypeList#getSize <em>Size</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Size</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeList#getSize()
   * @see #getAstTypeList()
   * @generated
   */
  EReference getAstTypeList_Size();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTypeString <em>Ast Type String</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Type String</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeString
   * @generated
   */
  EClass getAstTypeString();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstTypeUint <em>Ast Type Uint</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Type Uint</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeUint
   * @generated
   */
  EClass getAstTypeUint();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.AstTypeUint#getSize <em>Size</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Size</em>'.
   * @see net.sf.orcc.cal.cal.AstTypeUint#getSize()
   * @see #getAstTypeUint()
   * @generated
   */
  EReference getAstTypeUint_Size();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.VariableReference <em>Variable Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Variable Reference</em>'.
   * @see net.sf.orcc.cal.cal.VariableReference
   * @generated
   */
  EClass getVariableReference();

  /**
   * Returns the meta object for the reference '{@link net.sf.orcc.cal.cal.VariableReference#getVariable <em>Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Variable</em>'.
   * @see net.sf.orcc.cal.cal.VariableReference#getVariable()
   * @see #getVariableReference()
   * @generated
   */
  EReference getVariableReference_Variable();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AstAnnotation <em>Ast Annotation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Ast Annotation</em>'.
   * @see net.sf.orcc.cal.cal.AstAnnotation
   * @generated
   */
  EClass getAstAnnotation();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.AstAnnotation#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see net.sf.orcc.cal.cal.AstAnnotation#getName()
   * @see #getAstAnnotation()
   * @generated
   */
  EAttribute getAstAnnotation_Name();

  /**
   * Returns the meta object for the containment reference list '{@link net.sf.orcc.cal.cal.AstAnnotation#getArguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arguments</em>'.
   * @see net.sf.orcc.cal.cal.AstAnnotation#getArguments()
   * @see #getAstAnnotation()
   * @generated
   */
  EReference getAstAnnotation_Arguments();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.AnnotationArgument <em>Annotation Argument</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annotation Argument</em>'.
   * @see net.sf.orcc.cal.cal.AnnotationArgument
   * @generated
   */
  EClass getAnnotationArgument();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.AnnotationArgument#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see net.sf.orcc.cal.cal.AnnotationArgument#getName()
   * @see #getAnnotationArgument()
   * @generated
   */
  EAttribute getAnnotationArgument_Name();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.AnnotationArgument#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see net.sf.orcc.cal.cal.AnnotationArgument#getValue()
   * @see #getAnnotationArgument()
   * @generated
   */
  EAttribute getAnnotationArgument_Value();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.RegExpBinary <em>Reg Exp Binary</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Reg Exp Binary</em>'.
   * @see net.sf.orcc.cal.cal.RegExpBinary
   * @generated
   */
  EClass getRegExpBinary();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.RegExpBinary#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see net.sf.orcc.cal.cal.RegExpBinary#getLeft()
   * @see #getRegExpBinary()
   * @generated
   */
  EReference getRegExpBinary_Left();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.RegExpBinary#getOperator <em>Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operator</em>'.
   * @see net.sf.orcc.cal.cal.RegExpBinary#getOperator()
   * @see #getRegExpBinary()
   * @generated
   */
  EAttribute getRegExpBinary_Operator();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.RegExpBinary#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see net.sf.orcc.cal.cal.RegExpBinary#getRight()
   * @see #getRegExpBinary()
   * @generated
   */
  EReference getRegExpBinary_Right();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.RegExpUnary <em>Reg Exp Unary</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Reg Exp Unary</em>'.
   * @see net.sf.orcc.cal.cal.RegExpUnary
   * @generated
   */
  EClass getRegExpUnary();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.RegExpUnary#getChild <em>Child</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Child</em>'.
   * @see net.sf.orcc.cal.cal.RegExpUnary#getChild()
   * @see #getRegExpUnary()
   * @generated
   */
  EReference getRegExpUnary_Child();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.RegExpUnary#getUnaryOperator <em>Unary Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Unary Operator</em>'.
   * @see net.sf.orcc.cal.cal.RegExpUnary#getUnaryOperator()
   * @see #getRegExpUnary()
   * @generated
   */
  EAttribute getRegExpUnary_UnaryOperator();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.RegExpTag <em>Reg Exp Tag</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Reg Exp Tag</em>'.
   * @see net.sf.orcc.cal.cal.RegExpTag
   * @generated
   */
  EClass getRegExpTag();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.RegExpTag#getTag <em>Tag</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Tag</em>'.
   * @see net.sf.orcc.cal.cal.RegExpTag#getTag()
   * @see #getRegExpTag()
   * @generated
   */
  EReference getRegExpTag_Tag();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionBinary <em>Expression Binary</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Binary</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionBinary
   * @generated
   */
  EClass getExpressionBinary();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionBinary#getLeft <em>Left</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Left</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionBinary#getLeft()
   * @see #getExpressionBinary()
   * @generated
   */
  EReference getExpressionBinary_Left();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.ExpressionBinary#getOperator <em>Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operator</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionBinary#getOperator()
   * @see #getExpressionBinary()
   * @generated
   */
  EAttribute getExpressionBinary_Operator();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionBinary#getRight <em>Right</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Right</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionBinary#getRight()
   * @see #getExpressionBinary()
   * @generated
   */
  EReference getExpressionBinary_Right();

  /**
   * Returns the meta object for class '{@link net.sf.orcc.cal.cal.ExpressionUnary <em>Expression Unary</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Unary</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionUnary
   * @generated
   */
  EClass getExpressionUnary();

  /**
   * Returns the meta object for the attribute '{@link net.sf.orcc.cal.cal.ExpressionUnary#getUnaryOperator <em>Unary Operator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Unary Operator</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionUnary#getUnaryOperator()
   * @see #getExpressionUnary()
   * @generated
   */
  EAttribute getExpressionUnary_UnaryOperator();

  /**
   * Returns the meta object for the containment reference '{@link net.sf.orcc.cal.cal.ExpressionUnary#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see net.sf.orcc.cal.cal.ExpressionUnary#getExpression()
   * @see #getExpressionUnary()
   * @generated
   */
  EReference getExpressionUnary_Expression();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  CalFactory getCalFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstEntityImpl <em>Ast Entity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstEntityImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstEntity()
     * @generated
     */
    EClass AST_ENTITY = eINSTANCE.getAstEntity();

    /**
     * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AST_ENTITY__PACKAGE = eINSTANCE.getAstEntity_Package();

    /**
     * The meta object literal for the '<em><b>Imports</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ENTITY__IMPORTS = eINSTANCE.getAstEntity_Imports();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ENTITY__ANNOTATIONS = eINSTANCE.getAstEntity_Annotations();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AST_ENTITY__NAME = eINSTANCE.getAstEntity_Name();

    /**
     * The meta object literal for the '<em><b>Actor</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ENTITY__ACTOR = eINSTANCE.getAstEntity_Actor();

    /**
     * The meta object literal for the '<em><b>Unit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ENTITY__UNIT = eINSTANCE.getAstEntity_Unit();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ImportImpl <em>Import</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ImportImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getImport()
     * @generated
     */
    EClass IMPORT = eINSTANCE.getImport();

    /**
     * The meta object literal for the '<em><b>Imported Namespace</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IMPORT__IMPORTED_NAMESPACE = eINSTANCE.getImport_ImportedNamespace();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstUnitImpl <em>Ast Unit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstUnitImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstUnit()
     * @generated
     */
    EClass AST_UNIT = eINSTANCE.getAstUnit();

    /**
     * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_UNIT__FUNCTIONS = eINSTANCE.getAstUnit_Functions();

    /**
     * The meta object literal for the '<em><b>Procedures</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_UNIT__PROCEDURES = eINSTANCE.getAstUnit_Procedures();

    /**
     * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_UNIT__VARIABLES = eINSTANCE.getAstUnit_Variables();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.VariableImpl <em>Variable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.VariableImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getVariable()
     * @generated
     */
    EClass VARIABLE = eINSTANCE.getVariable();

    /**
     * The meta object literal for the '<em><b>Constant</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VARIABLE__CONSTANT = eINSTANCE.getVariable_Constant();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE__VALUE = eINSTANCE.getVariable_Value();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE__ANNOTATIONS = eINSTANCE.getVariable_Annotations();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE__TYPE = eINSTANCE.getVariable_Type();

    /**
     * The meta object literal for the '<em><b>Dimensions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE__DIMENSIONS = eINSTANCE.getVariable_Dimensions();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstActorImpl <em>Ast Actor</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstActorImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstActor()
     * @generated
     */
    EClass AST_ACTOR = eINSTANCE.getAstActor();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__PARAMETERS = eINSTANCE.getAstActor_Parameters();

    /**
     * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__INPUTS = eINSTANCE.getAstActor_Inputs();

    /**
     * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__OUTPUTS = eINSTANCE.getAstActor_Outputs();

    /**
     * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__FUNCTIONS = eINSTANCE.getAstActor_Functions();

    /**
     * The meta object literal for the '<em><b>Procedures</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__PROCEDURES = eINSTANCE.getAstActor_Procedures();

    /**
     * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__ACTIONS = eINSTANCE.getAstActor_Actions();

    /**
     * The meta object literal for the '<em><b>Initializes</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__INITIALIZES = eINSTANCE.getAstActor_Initializes();

    /**
     * The meta object literal for the '<em><b>State Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__STATE_VARIABLES = eINSTANCE.getAstActor_StateVariables();

    /**
     * The meta object literal for the '<em><b>Local Fsms</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__LOCAL_FSMS = eINSTANCE.getAstActor_LocalFsms();

    /**
     * The meta object literal for the '<em><b>Schedule Fsm</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__SCHEDULE_FSM = eINSTANCE.getAstActor_ScheduleFsm();

    /**
     * The meta object literal for the '<em><b>Schedule Reg Exp</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__SCHEDULE_REG_EXP = eINSTANCE.getAstActor_ScheduleRegExp();

    /**
     * The meta object literal for the '<em><b>Priorities</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTOR__PRIORITIES = eINSTANCE.getAstActor_Priorities();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstPortImpl <em>Ast Port</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstPortImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstPort()
     * @generated
     */
    EClass AST_PORT = eINSTANCE.getAstPort();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_PORT__ANNOTATIONS = eINSTANCE.getAstPort_Annotations();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_PORT__TYPE = eINSTANCE.getAstPort_Type();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AST_PORT__NAME = eINSTANCE.getAstPort_Name();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.FunctionImpl <em>Function</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.FunctionImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getFunction()
     * @generated
     */
    EClass FUNCTION = eINSTANCE.getFunction();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FUNCTION__ANNOTATIONS = eINSTANCE.getFunction_Annotations();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FUNCTION__NAME = eINSTANCE.getFunction_Name();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FUNCTION__PARAMETERS = eINSTANCE.getFunction_Parameters();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FUNCTION__TYPE = eINSTANCE.getFunction_Type();

    /**
     * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FUNCTION__VARIABLES = eINSTANCE.getFunction_Variables();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FUNCTION__EXPRESSION = eINSTANCE.getFunction_Expression();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstProcedureImpl <em>Ast Procedure</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstProcedureImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstProcedure()
     * @generated
     */
    EClass AST_PROCEDURE = eINSTANCE.getAstProcedure();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_PROCEDURE__ANNOTATIONS = eINSTANCE.getAstProcedure_Annotations();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AST_PROCEDURE__NAME = eINSTANCE.getAstProcedure_Name();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_PROCEDURE__PARAMETERS = eINSTANCE.getAstProcedure_Parameters();

    /**
     * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_PROCEDURE__VARIABLES = eINSTANCE.getAstProcedure_Variables();

    /**
     * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_PROCEDURE__STATEMENTS = eINSTANCE.getAstProcedure_Statements();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTagImpl <em>Ast Tag</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTagImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTag()
     * @generated
     */
    EClass AST_TAG = eINSTANCE.getAstTag();

    /**
     * The meta object literal for the '<em><b>Identifiers</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AST_TAG__IDENTIFIERS = eINSTANCE.getAstTag_Identifiers();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.InequalityImpl <em>Inequality</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.InequalityImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getInequality()
     * @generated
     */
    EClass INEQUALITY = eINSTANCE.getInequality();

    /**
     * The meta object literal for the '<em><b>Tags</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INEQUALITY__TAGS = eINSTANCE.getInequality_Tags();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.PriorityImpl <em>Priority</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.PriorityImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getPriority()
     * @generated
     */
    EClass PRIORITY = eINSTANCE.getPriority();

    /**
     * The meta object literal for the '<em><b>Inequalities</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PRIORITY__INEQUALITIES = eINSTANCE.getPriority_Inequalities();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ScheduleFsmImpl <em>Schedule Fsm</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ScheduleFsmImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getScheduleFsm()
     * @generated
     */
    EClass SCHEDULE_FSM = eINSTANCE.getScheduleFsm();

    /**
     * The meta object literal for the '<em><b>Initial State</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SCHEDULE_FSM__INITIAL_STATE = eINSTANCE.getScheduleFsm_InitialState();

    /**
     * The meta object literal for the '<em><b>Contents</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SCHEDULE_FSM__CONTENTS = eINSTANCE.getScheduleFsm_Contents();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.FsmImpl <em>Fsm</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.FsmImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getFsm()
     * @generated
     */
    EClass FSM = eINSTANCE.getFsm();

    /**
     * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FSM__TRANSITIONS = eINSTANCE.getFsm_Transitions();

    /**
     * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FSM__STATES = eINSTANCE.getFsm_States();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTransitionImpl <em>Ast Transition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTransitionImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTransition()
     * @generated
     */
    EClass AST_TRANSITION = eINSTANCE.getAstTransition();

    /**
     * The meta object literal for the '<em><b>Source</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_TRANSITION__SOURCE = eINSTANCE.getAstTransition_Source();

    /**
     * The meta object literal for the '<em><b>Tag</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_TRANSITION__TAG = eINSTANCE.getAstTransition_Tag();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_TRANSITION__TARGET = eINSTANCE.getAstTransition_Target();

    /**
     * The meta object literal for the '<em><b>External Target</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_TRANSITION__EXTERNAL_TARGET = eINSTANCE.getAstTransition_ExternalTarget();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExternalTargetImpl <em>External Target</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExternalTargetImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExternalTarget()
     * @generated
     */
    EClass EXTERNAL_TARGET = eINSTANCE.getExternalTarget();

    /**
     * The meta object literal for the '<em><b>Fsm</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXTERNAL_TARGET__FSM = eINSTANCE.getExternalTarget_Fsm();

    /**
     * The meta object literal for the '<em><b>State</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXTERNAL_TARGET__STATE = eINSTANCE.getExternalTarget_State();

    /**
     * The meta object literal for the '<em><b>From</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXTERNAL_TARGET__FROM = eINSTANCE.getExternalTarget_From();

    /**
     * The meta object literal for the '<em><b>To</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXTERNAL_TARGET__TO = eINSTANCE.getExternalTarget_To();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstStateImpl <em>Ast State</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstStateImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstState()
     * @generated
     */
    EClass AST_STATE = eINSTANCE.getAstState();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AST_STATE__NAME = eINSTANCE.getAstState_Name();

    /**
     * The meta object literal for the '<em><b>Node</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AST_STATE__NODE = eINSTANCE.getAstState_Node();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.RegExpImpl <em>Reg Exp</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.RegExpImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getRegExp()
     * @generated
     */
    EClass REG_EXP = eINSTANCE.getRegExp();

    /**
     * The meta object literal for the '<em><b>Exp</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REG_EXP__EXP = eINSTANCE.getRegExp_Exp();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.LocalFsmImpl <em>Local Fsm</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.LocalFsmImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getLocalFsm()
     * @generated
     */
    EClass LOCAL_FSM = eINSTANCE.getLocalFsm();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOCAL_FSM__NAME = eINSTANCE.getLocalFsm_Name();

    /**
     * The meta object literal for the '<em><b>Contents</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LOCAL_FSM__CONTENTS = eINSTANCE.getLocalFsm_Contents();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstActionImpl <em>Ast Action</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstActionImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstAction()
     * @generated
     */
    EClass AST_ACTION = eINSTANCE.getAstAction();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTION__ANNOTATIONS = eINSTANCE.getAstAction_Annotations();

    /**
     * The meta object literal for the '<em><b>Tag</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTION__TAG = eINSTANCE.getAstAction_Tag();

    /**
     * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTION__INPUTS = eINSTANCE.getAstAction_Inputs();

    /**
     * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTION__OUTPUTS = eINSTANCE.getAstAction_Outputs();

    /**
     * The meta object literal for the '<em><b>Guard</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTION__GUARD = eINSTANCE.getAstAction_Guard();

    /**
     * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTION__VARIABLES = eINSTANCE.getAstAction_Variables();

    /**
     * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ACTION__STATEMENTS = eINSTANCE.getAstAction_Statements();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.InputPatternImpl <em>Input Pattern</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.InputPatternImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getInputPattern()
     * @generated
     */
    EClass INPUT_PATTERN = eINSTANCE.getInputPattern();

    /**
     * The meta object literal for the '<em><b>Port</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INPUT_PATTERN__PORT = eINSTANCE.getInputPattern_Port();

    /**
     * The meta object literal for the '<em><b>Tokens</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INPUT_PATTERN__TOKENS = eINSTANCE.getInputPattern_Tokens();

    /**
     * The meta object literal for the '<em><b>Repeat</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INPUT_PATTERN__REPEAT = eINSTANCE.getInputPattern_Repeat();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.OutputPatternImpl <em>Output Pattern</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.OutputPatternImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getOutputPattern()
     * @generated
     */
    EClass OUTPUT_PATTERN = eINSTANCE.getOutputPattern();

    /**
     * The meta object literal for the '<em><b>Port</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OUTPUT_PATTERN__PORT = eINSTANCE.getOutputPattern_Port();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OUTPUT_PATTERN__VALUES = eINSTANCE.getOutputPattern_Values();

    /**
     * The meta object literal for the '<em><b>Repeat</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OUTPUT_PATTERN__REPEAT = eINSTANCE.getOutputPattern_Repeat();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.GuardImpl <em>Guard</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.GuardImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getGuard()
     * @generated
     */
    EClass GUARD = eINSTANCE.getGuard();

    /**
     * The meta object literal for the '<em><b>Expressions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GUARD__EXPRESSIONS = eINSTANCE.getGuard_Expressions();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.StatementAssignImpl <em>Statement Assign</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.StatementAssignImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementAssign()
     * @generated
     */
    EClass STATEMENT_ASSIGN = eINSTANCE.getStatementAssign();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_ASSIGN__TARGET = eINSTANCE.getStatementAssign_Target();

    /**
     * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_ASSIGN__INDEXES = eINSTANCE.getStatementAssign_Indexes();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_ASSIGN__VALUE = eINSTANCE.getStatementAssign_Value();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.StatementCallImpl <em>Statement Call</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.StatementCallImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementCall()
     * @generated
     */
    EClass STATEMENT_CALL = eINSTANCE.getStatementCall();

    /**
     * The meta object literal for the '<em><b>Procedure</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_CALL__PROCEDURE = eINSTANCE.getStatementCall_Procedure();

    /**
     * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_CALL__ARGUMENTS = eINSTANCE.getStatementCall_Arguments();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.StatementForeachImpl <em>Statement Foreach</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.StatementForeachImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementForeach()
     * @generated
     */
    EClass STATEMENT_FOREACH = eINSTANCE.getStatementForeach();

    /**
     * The meta object literal for the '<em><b>Variable</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_FOREACH__VARIABLE = eINSTANCE.getStatementForeach_Variable();

    /**
     * The meta object literal for the '<em><b>Lower</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_FOREACH__LOWER = eINSTANCE.getStatementForeach_Lower();

    /**
     * The meta object literal for the '<em><b>Higher</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_FOREACH__HIGHER = eINSTANCE.getStatementForeach_Higher();

    /**
     * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_FOREACH__STATEMENTS = eINSTANCE.getStatementForeach_Statements();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.StatementIfImpl <em>Statement If</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.StatementIfImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementIf()
     * @generated
     */
    EClass STATEMENT_IF = eINSTANCE.getStatementIf();

    /**
     * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_IF__CONDITION = eINSTANCE.getStatementIf_Condition();

    /**
     * The meta object literal for the '<em><b>Then</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_IF__THEN = eINSTANCE.getStatementIf_Then();

    /**
     * The meta object literal for the '<em><b>Elsifs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_IF__ELSIFS = eINSTANCE.getStatementIf_Elsifs();

    /**
     * The meta object literal for the '<em><b>Else</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_IF__ELSE = eINSTANCE.getStatementIf_Else();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.StatementElsifImpl <em>Statement Elsif</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.StatementElsifImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementElsif()
     * @generated
     */
    EClass STATEMENT_ELSIF = eINSTANCE.getStatementElsif();

    /**
     * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_ELSIF__CONDITION = eINSTANCE.getStatementElsif_Condition();

    /**
     * The meta object literal for the '<em><b>Then</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_ELSIF__THEN = eINSTANCE.getStatementElsif_Then();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.StatementWhileImpl <em>Statement While</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.StatementWhileImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatementWhile()
     * @generated
     */
    EClass STATEMENT_WHILE = eINSTANCE.getStatementWhile();

    /**
     * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_WHILE__CONDITION = eINSTANCE.getStatementWhile_Condition();

    /**
     * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT_WHILE__STATEMENTS = eINSTANCE.getStatementWhile_Statements();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.StatementImpl <em>Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.StatementImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getStatement()
     * @generated
     */
    EClass STATEMENT = eINSTANCE.getStatement();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT__ANNOTATIONS = eINSTANCE.getStatement_Annotations();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstExpressionImpl <em>Ast Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstExpressionImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstExpression()
     * @generated
     */
    EClass AST_EXPRESSION = eINSTANCE.getAstExpression();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionCallImpl <em>Expression Call</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionCallImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionCall()
     * @generated
     */
    EClass EXPRESSION_CALL = eINSTANCE.getExpressionCall();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_CALL__ANNOTATIONS = eINSTANCE.getExpressionCall_Annotations();

    /**
     * The meta object literal for the '<em><b>Function</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_CALL__FUNCTION = eINSTANCE.getExpressionCall_Function();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_CALL__PARAMETERS = eINSTANCE.getExpressionCall_Parameters();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionIndexImpl <em>Expression Index</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionIndexImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionIndex()
     * @generated
     */
    EClass EXPRESSION_INDEX = eINSTANCE.getExpressionIndex();

    /**
     * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_INDEX__SOURCE = eINSTANCE.getExpressionIndex_Source();

    /**
     * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_INDEX__INDEXES = eINSTANCE.getExpressionIndex_Indexes();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionIfImpl <em>Expression If</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionIfImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionIf()
     * @generated
     */
    EClass EXPRESSION_IF = eINSTANCE.getExpressionIf();

    /**
     * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_IF__CONDITION = eINSTANCE.getExpressionIf_Condition();

    /**
     * The meta object literal for the '<em><b>Then</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_IF__THEN = eINSTANCE.getExpressionIf_Then();

    /**
     * The meta object literal for the '<em><b>Elsifs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_IF__ELSIFS = eINSTANCE.getExpressionIf_Elsifs();

    /**
     * The meta object literal for the '<em><b>Else</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_IF__ELSE = eINSTANCE.getExpressionIf_Else();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionElsifImpl <em>Expression Elsif</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionElsifImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionElsif()
     * @generated
     */
    EClass EXPRESSION_ELSIF = eINSTANCE.getExpressionElsif();

    /**
     * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_ELSIF__CONDITION = eINSTANCE.getExpressionElsif_Condition();

    /**
     * The meta object literal for the '<em><b>Then</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_ELSIF__THEN = eINSTANCE.getExpressionElsif_Then();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionListImpl <em>Expression List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionListImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionList()
     * @generated
     */
    EClass EXPRESSION_LIST = eINSTANCE.getExpressionList();

    /**
     * The meta object literal for the '<em><b>Expressions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_LIST__EXPRESSIONS = eINSTANCE.getExpressionList_Expressions();

    /**
     * The meta object literal for the '<em><b>Generators</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_LIST__GENERATORS = eINSTANCE.getExpressionList_Generators();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.GeneratorImpl <em>Generator</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.GeneratorImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getGenerator()
     * @generated
     */
    EClass GENERATOR = eINSTANCE.getGenerator();

    /**
     * The meta object literal for the '<em><b>Variable</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GENERATOR__VARIABLE = eINSTANCE.getGenerator_Variable();

    /**
     * The meta object literal for the '<em><b>Lower</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GENERATOR__LOWER = eINSTANCE.getGenerator_Lower();

    /**
     * The meta object literal for the '<em><b>Higher</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GENERATOR__HIGHER = eINSTANCE.getGenerator_Higher();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionVariableImpl <em>Expression Variable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionVariableImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionVariable()
     * @generated
     */
    EClass EXPRESSION_VARIABLE = eINSTANCE.getExpressionVariable();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_VARIABLE__VALUE = eINSTANCE.getExpressionVariable_Value();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionLiteralImpl <em>Expression Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionLiteralImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionLiteral()
     * @generated
     */
    EClass EXPRESSION_LITERAL = eINSTANCE.getExpressionLiteral();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionBooleanImpl <em>Expression Boolean</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionBooleanImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionBoolean()
     * @generated
     */
    EClass EXPRESSION_BOOLEAN = eINSTANCE.getExpressionBoolean();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION_BOOLEAN__VALUE = eINSTANCE.getExpressionBoolean_Value();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionFloatImpl <em>Expression Float</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionFloatImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionFloat()
     * @generated
     */
    EClass EXPRESSION_FLOAT = eINSTANCE.getExpressionFloat();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION_FLOAT__VALUE = eINSTANCE.getExpressionFloat_Value();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionIntegerImpl <em>Expression Integer</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionIntegerImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionInteger()
     * @generated
     */
    EClass EXPRESSION_INTEGER = eINSTANCE.getExpressionInteger();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION_INTEGER__VALUE = eINSTANCE.getExpressionInteger_Value();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionStringImpl <em>Expression String</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionStringImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionString()
     * @generated
     */
    EClass EXPRESSION_STRING = eINSTANCE.getExpressionString();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION_STRING__VALUE = eINSTANCE.getExpressionString_Value();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTypeImpl <em>Ast Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTypeImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstType()
     * @generated
     */
    EClass AST_TYPE = eINSTANCE.getAstType();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTypeBoolImpl <em>Ast Type Bool</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTypeBoolImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeBool()
     * @generated
     */
    EClass AST_TYPE_BOOL = eINSTANCE.getAstTypeBool();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTypeFloatImpl <em>Ast Type Float</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTypeFloatImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeFloat()
     * @generated
     */
    EClass AST_TYPE_FLOAT = eINSTANCE.getAstTypeFloat();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTypeHalfImpl <em>Ast Type Half</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTypeHalfImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeHalf()
     * @generated
     */
    EClass AST_TYPE_HALF = eINSTANCE.getAstTypeHalf();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTypeDoubleImpl <em>Ast Type Double</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTypeDoubleImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeDouble()
     * @generated
     */
    EClass AST_TYPE_DOUBLE = eINSTANCE.getAstTypeDouble();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTypeIntImpl <em>Ast Type Int</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTypeIntImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeInt()
     * @generated
     */
    EClass AST_TYPE_INT = eINSTANCE.getAstTypeInt();

    /**
     * The meta object literal for the '<em><b>Size</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_TYPE_INT__SIZE = eINSTANCE.getAstTypeInt_Size();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTypeListImpl <em>Ast Type List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTypeListImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeList()
     * @generated
     */
    EClass AST_TYPE_LIST = eINSTANCE.getAstTypeList();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_TYPE_LIST__TYPE = eINSTANCE.getAstTypeList_Type();

    /**
     * The meta object literal for the '<em><b>Size</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_TYPE_LIST__SIZE = eINSTANCE.getAstTypeList_Size();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTypeStringImpl <em>Ast Type String</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTypeStringImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeString()
     * @generated
     */
    EClass AST_TYPE_STRING = eINSTANCE.getAstTypeString();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstTypeUintImpl <em>Ast Type Uint</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstTypeUintImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstTypeUint()
     * @generated
     */
    EClass AST_TYPE_UINT = eINSTANCE.getAstTypeUint();

    /**
     * The meta object literal for the '<em><b>Size</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_TYPE_UINT__SIZE = eINSTANCE.getAstTypeUint_Size();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.VariableReferenceImpl <em>Variable Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.VariableReferenceImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getVariableReference()
     * @generated
     */
    EClass VARIABLE_REFERENCE = eINSTANCE.getVariableReference();

    /**
     * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VARIABLE_REFERENCE__VARIABLE = eINSTANCE.getVariableReference_Variable();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AstAnnotationImpl <em>Ast Annotation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AstAnnotationImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAstAnnotation()
     * @generated
     */
    EClass AST_ANNOTATION = eINSTANCE.getAstAnnotation();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AST_ANNOTATION__NAME = eINSTANCE.getAstAnnotation_Name();

    /**
     * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AST_ANNOTATION__ARGUMENTS = eINSTANCE.getAstAnnotation_Arguments();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.AnnotationArgumentImpl <em>Annotation Argument</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.AnnotationArgumentImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getAnnotationArgument()
     * @generated
     */
    EClass ANNOTATION_ARGUMENT = eINSTANCE.getAnnotationArgument();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNOTATION_ARGUMENT__NAME = eINSTANCE.getAnnotationArgument_Name();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNOTATION_ARGUMENT__VALUE = eINSTANCE.getAnnotationArgument_Value();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.RegExpBinaryImpl <em>Reg Exp Binary</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.RegExpBinaryImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getRegExpBinary()
     * @generated
     */
    EClass REG_EXP_BINARY = eINSTANCE.getRegExpBinary();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REG_EXP_BINARY__LEFT = eINSTANCE.getRegExpBinary_Left();

    /**
     * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REG_EXP_BINARY__OPERATOR = eINSTANCE.getRegExpBinary_Operator();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REG_EXP_BINARY__RIGHT = eINSTANCE.getRegExpBinary_Right();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.RegExpUnaryImpl <em>Reg Exp Unary</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.RegExpUnaryImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getRegExpUnary()
     * @generated
     */
    EClass REG_EXP_UNARY = eINSTANCE.getRegExpUnary();

    /**
     * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REG_EXP_UNARY__CHILD = eINSTANCE.getRegExpUnary_Child();

    /**
     * The meta object literal for the '<em><b>Unary Operator</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REG_EXP_UNARY__UNARY_OPERATOR = eINSTANCE.getRegExpUnary_UnaryOperator();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.RegExpTagImpl <em>Reg Exp Tag</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.RegExpTagImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getRegExpTag()
     * @generated
     */
    EClass REG_EXP_TAG = eINSTANCE.getRegExpTag();

    /**
     * The meta object literal for the '<em><b>Tag</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REG_EXP_TAG__TAG = eINSTANCE.getRegExpTag_Tag();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionBinaryImpl <em>Expression Binary</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionBinaryImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionBinary()
     * @generated
     */
    EClass EXPRESSION_BINARY = eINSTANCE.getExpressionBinary();

    /**
     * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_BINARY__LEFT = eINSTANCE.getExpressionBinary_Left();

    /**
     * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION_BINARY__OPERATOR = eINSTANCE.getExpressionBinary_Operator();

    /**
     * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_BINARY__RIGHT = eINSTANCE.getExpressionBinary_Right();

    /**
     * The meta object literal for the '{@link net.sf.orcc.cal.cal.impl.ExpressionUnaryImpl <em>Expression Unary</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.sf.orcc.cal.cal.impl.ExpressionUnaryImpl
     * @see net.sf.orcc.cal.cal.impl.CalPackageImpl#getExpressionUnary()
     * @generated
     */
    EClass EXPRESSION_UNARY = eINSTANCE.getExpressionUnary();

    /**
     * The meta object literal for the '<em><b>Unary Operator</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION_UNARY__UNARY_OPERATOR = eINSTANCE.getExpressionUnary_UnaryOperator();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_UNARY__EXPRESSION = eINSTANCE.getExpressionUnary_Expression();

  }

} //CalPackage
