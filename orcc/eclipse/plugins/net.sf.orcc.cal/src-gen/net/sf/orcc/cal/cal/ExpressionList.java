/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionList#getExpressions <em>Expressions</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionList#getGenerators <em>Generators</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getExpressionList()
 * @model
 * @generated
 */
public interface ExpressionList extends AstExpression
{
  /**
   * Returns the value of the '<em><b>Expressions</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstExpression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expressions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expressions</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionList_Expressions()
   * @model containment="true"
   * @generated
   */
  EList<AstExpression> getExpressions();

  /**
   * Returns the value of the '<em><b>Generators</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Generator}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Generators</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Generators</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionList_Generators()
   * @model containment="true"
   * @generated
   */
  EList<Generator> getGenerators();

} // ExpressionList
