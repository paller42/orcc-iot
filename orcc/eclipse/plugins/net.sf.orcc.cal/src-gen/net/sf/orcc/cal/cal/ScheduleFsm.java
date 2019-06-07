/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Schedule Fsm</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.ScheduleFsm#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ScheduleFsm#getContents <em>Contents</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getScheduleFsm()
 * @model
 * @generated
 */
public interface ScheduleFsm extends EObject
{
  /**
   * Returns the value of the '<em><b>Initial State</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Initial State</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Initial State</em>' reference.
   * @see #setInitialState(AstState)
   * @see net.sf.orcc.cal.cal.CalPackage#getScheduleFsm_InitialState()
   * @model
   * @generated
   */
  AstState getInitialState();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ScheduleFsm#getInitialState <em>Initial State</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Initial State</em>' reference.
   * @see #getInitialState()
   * @generated
   */
  void setInitialState(AstState value);

  /**
   * Returns the value of the '<em><b>Contents</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Contents</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Contents</em>' containment reference.
   * @see #setContents(Fsm)
   * @see net.sf.orcc.cal.cal.CalPackage#getScheduleFsm_Contents()
   * @model containment="true"
   * @generated
   */
  Fsm getContents();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ScheduleFsm#getContents <em>Contents</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Contents</em>' containment reference.
   * @see #getContents()
   * @generated
   */
  void setContents(Fsm value);

} // ScheduleFsm
