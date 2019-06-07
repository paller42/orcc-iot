/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Index</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionIndex#getSource <em>Source</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionIndex#getIndexes <em>Indexes</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getExpressionIndex()
 * @model
 * @generated
 */
public interface ExpressionIndex extends AstExpression
{
  /**
   * Returns the value of the '<em><b>Source</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Source</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Source</em>' containment reference.
   * @see #setSource(VariableReference)
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionIndex_Source()
   * @model containment="true"
   * @generated
   */
  VariableReference getSource();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExpressionIndex#getSource <em>Source</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Source</em>' containment reference.
   * @see #getSource()
   * @generated
   */
  void setSource(VariableReference value);

  /**
   * Returns the value of the '<em><b>Indexes</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstExpression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Indexes</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Indexes</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionIndex_Indexes()
   * @model containment="true"
   * @generated
   */
  EList<AstExpression> getIndexes();

} // ExpressionIndex
