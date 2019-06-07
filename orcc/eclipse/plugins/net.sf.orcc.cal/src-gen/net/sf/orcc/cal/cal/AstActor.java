/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Actor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getFunctions <em>Functions</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getProcedures <em>Procedures</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getActions <em>Actions</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getInitializes <em>Initializes</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getStateVariables <em>State Variables</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getLocalFsms <em>Local Fsms</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getScheduleFsm <em>Schedule Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getScheduleRegExp <em>Schedule Reg Exp</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstActor#getPriorities <em>Priorities</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getAstActor()
 * @model
 * @generated
 */
public interface AstActor extends EObject
{
  /**
   * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Variable}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_Parameters()
   * @model containment="true"
   * @generated
   */
  EList<Variable> getParameters();

  /**
   * Returns the value of the '<em><b>Inputs</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstPort}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Inputs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Inputs</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_Inputs()
   * @model containment="true"
   * @generated
   */
  EList<AstPort> getInputs();

  /**
   * Returns the value of the '<em><b>Outputs</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstPort}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Outputs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Outputs</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_Outputs()
   * @model containment="true"
   * @generated
   */
  EList<AstPort> getOutputs();

  /**
   * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Function}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Functions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Functions</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_Functions()
   * @model containment="true"
   * @generated
   */
  EList<Function> getFunctions();

  /**
   * Returns the value of the '<em><b>Procedures</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstProcedure}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Procedures</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Procedures</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_Procedures()
   * @model containment="true"
   * @generated
   */
  EList<AstProcedure> getProcedures();

  /**
   * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstAction}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Actions</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_Actions()
   * @model containment="true"
   * @generated
   */
  EList<AstAction> getActions();

  /**
   * Returns the value of the '<em><b>Initializes</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstAction}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Initializes</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Initializes</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_Initializes()
   * @model containment="true"
   * @generated
   */
  EList<AstAction> getInitializes();

  /**
   * Returns the value of the '<em><b>State Variables</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Variable}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>State Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>State Variables</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_StateVariables()
   * @model containment="true"
   * @generated
   */
  EList<Variable> getStateVariables();

  /**
   * Returns the value of the '<em><b>Local Fsms</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.LocalFsm}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Local Fsms</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Local Fsms</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_LocalFsms()
   * @model containment="true"
   * @generated
   */
  EList<LocalFsm> getLocalFsms();

  /**
   * Returns the value of the '<em><b>Schedule Fsm</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Schedule Fsm</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Schedule Fsm</em>' containment reference.
   * @see #setScheduleFsm(ScheduleFsm)
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_ScheduleFsm()
   * @model containment="true"
   * @generated
   */
  ScheduleFsm getScheduleFsm();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.AstActor#getScheduleFsm <em>Schedule Fsm</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Schedule Fsm</em>' containment reference.
   * @see #getScheduleFsm()
   * @generated
   */
  void setScheduleFsm(ScheduleFsm value);

  /**
   * Returns the value of the '<em><b>Schedule Reg Exp</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Schedule Reg Exp</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Schedule Reg Exp</em>' containment reference.
   * @see #setScheduleRegExp(RegExp)
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_ScheduleRegExp()
   * @model containment="true"
   * @generated
   */
  RegExp getScheduleRegExp();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.AstActor#getScheduleRegExp <em>Schedule Reg Exp</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Schedule Reg Exp</em>' containment reference.
   * @see #getScheduleRegExp()
   * @generated
   */
  void setScheduleRegExp(RegExp value);

  /**
   * Returns the value of the '<em><b>Priorities</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Priority}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Priorities</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Priorities</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstActor_Priorities()
   * @model containment="true"
   * @generated
   */
  EList<Priority> getPriorities();

} // AstActor
