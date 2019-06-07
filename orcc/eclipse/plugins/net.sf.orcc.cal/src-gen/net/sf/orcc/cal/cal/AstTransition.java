/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.AstTransition#getSource <em>Source</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstTransition#getTag <em>Tag</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstTransition#getTarget <em>Target</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstTransition#getExternalTarget <em>External Target</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getAstTransition()
 * @model
 * @generated
 */
public interface AstTransition extends EObject
{
  /**
   * Returns the value of the '<em><b>Source</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Source</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Source</em>' reference.
   * @see #setSource(AstState)
   * @see net.sf.orcc.cal.cal.CalPackage#getAstTransition_Source()
   * @model
   * @generated
   */
  AstState getSource();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.AstTransition#getSource <em>Source</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Source</em>' reference.
   * @see #getSource()
   * @generated
   */
  void setSource(AstState value);

  /**
   * Returns the value of the '<em><b>Tag</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tag</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tag</em>' containment reference.
   * @see #setTag(AstTag)
   * @see net.sf.orcc.cal.cal.CalPackage#getAstTransition_Tag()
   * @model containment="true"
   * @generated
   */
  AstTag getTag();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.AstTransition#getTag <em>Tag</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tag</em>' containment reference.
   * @see #getTag()
   * @generated
   */
  void setTag(AstTag value);

  /**
   * Returns the value of the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target</em>' reference.
   * @see #setTarget(AstState)
   * @see net.sf.orcc.cal.cal.CalPackage#getAstTransition_Target()
   * @model
   * @generated
   */
  AstState getTarget();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.AstTransition#getTarget <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' reference.
   * @see #getTarget()
   * @generated
   */
  void setTarget(AstState value);

  /**
   * Returns the value of the '<em><b>External Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>External Target</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>External Target</em>' containment reference.
   * @see #setExternalTarget(ExternalTarget)
   * @see net.sf.orcc.cal.cal.CalPackage#getAstTransition_ExternalTarget()
   * @model containment="true"
   * @generated
   */
  ExternalTarget getExternalTarget();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.AstTransition#getExternalTarget <em>External Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>External Target</em>' containment reference.
   * @see #getExternalTarget()
   * @generated
   */
  void setExternalTarget(ExternalTarget value);

} // AstTransition
