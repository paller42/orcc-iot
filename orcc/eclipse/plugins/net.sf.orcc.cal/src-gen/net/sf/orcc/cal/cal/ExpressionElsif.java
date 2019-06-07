/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Elsif</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionElsif#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionElsif#getThen <em>Then</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getExpressionElsif()
 * @model
 * @generated
 */
public interface ExpressionElsif extends EObject
{
  /**
   * Returns the value of the '<em><b>Condition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Condition</em>' containment reference.
   * @see #setCondition(AstExpression)
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionElsif_Condition()
   * @model containment="true"
   * @generated
   */
  AstExpression getCondition();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExpressionElsif#getCondition <em>Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Condition</em>' containment reference.
   * @see #getCondition()
   * @generated
   */
  void setCondition(AstExpression value);

  /**
   * Returns the value of the '<em><b>Then</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Then</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Then</em>' containment reference.
   * @see #setThen(AstExpression)
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionElsif_Then()
   * @model containment="true"
   * @generated
   */
  AstExpression getThen();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExpressionElsif#getThen <em>Then</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Then</em>' containment reference.
   * @see #getThen()
   * @generated
   */
  void setThen(AstExpression value);

} // ExpressionElsif
