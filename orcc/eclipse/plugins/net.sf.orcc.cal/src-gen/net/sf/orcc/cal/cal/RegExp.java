/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reg Exp</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.RegExp#getExp <em>Exp</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getRegExp()
 * @model
 * @generated
 */
public interface RegExp extends EObject
{
  /**
   * Returns the value of the '<em><b>Exp</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exp</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exp</em>' containment reference.
   * @see #setExp(RegExp)
   * @see net.sf.orcc.cal.cal.CalPackage#getRegExp_Exp()
   * @model containment="true"
   * @generated
   */
  RegExp getExp();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.RegExp#getExp <em>Exp</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exp</em>' containment reference.
   * @see #getExp()
   * @generated
   */
  void setExp(RegExp value);

} // RegExp
