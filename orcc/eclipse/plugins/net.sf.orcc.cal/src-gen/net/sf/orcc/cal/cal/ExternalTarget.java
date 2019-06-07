/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.ExternalTarget#getFsm <em>Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ExternalTarget#getState <em>State</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ExternalTarget#getFrom <em>From</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ExternalTarget#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getExternalTarget()
 * @model
 * @generated
 */
public interface ExternalTarget extends EObject
{
  /**
   * Returns the value of the '<em><b>Fsm</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fsm</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fsm</em>' reference.
   * @see #setFsm(LocalFsm)
   * @see net.sf.orcc.cal.cal.CalPackage#getExternalTarget_Fsm()
   * @model
   * @generated
   */
  LocalFsm getFsm();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExternalTarget#getFsm <em>Fsm</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fsm</em>' reference.
   * @see #getFsm()
   * @generated
   */
  void setFsm(LocalFsm value);

  /**
   * Returns the value of the '<em><b>State</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>State</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>State</em>' reference.
   * @see #setState(AstState)
   * @see net.sf.orcc.cal.cal.CalPackage#getExternalTarget_State()
   * @model
   * @generated
   */
  AstState getState();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExternalTarget#getState <em>State</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>State</em>' reference.
   * @see #getState()
   * @generated
   */
  void setState(AstState value);

  /**
   * Returns the value of the '<em><b>From</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>From</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>From</em>' reference.
   * @see #setFrom(AstState)
   * @see net.sf.orcc.cal.cal.CalPackage#getExternalTarget_From()
   * @model
   * @generated
   */
  AstState getFrom();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExternalTarget#getFrom <em>From</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From</em>' reference.
   * @see #getFrom()
   * @generated
   */
  void setFrom(AstState value);

  /**
   * Returns the value of the '<em><b>To</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>To</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>To</em>' reference.
   * @see #setTo(AstState)
   * @see net.sf.orcc.cal.cal.CalPackage#getExternalTarget_To()
   * @model
   * @generated
   */
  AstState getTo();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExternalTarget#getTo <em>To</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>To</em>' reference.
   * @see #getTo()
   * @generated
   */
  void setTo(AstState value);

} // ExternalTarget
