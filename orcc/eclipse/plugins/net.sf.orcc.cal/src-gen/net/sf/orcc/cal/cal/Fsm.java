/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fsm</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.Fsm#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.Fsm#getStates <em>States</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getFsm()
 * @model
 * @generated
 */
public interface Fsm extends EObject
{
  /**
   * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstTransition}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Transitions</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getFsm_Transitions()
   * @model containment="true"
   * @generated
   */
  EList<AstTransition> getTransitions();

  /**
   * Returns the value of the '<em><b>States</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstState}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>States</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>States</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getFsm_States()
   * @model containment="true" transient="true"
   * @generated
   */
  EList<AstState> getStates();

} // Fsm
