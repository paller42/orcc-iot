/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statement Assign</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.StatementAssign#getTarget <em>Target</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementAssign#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementAssign#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getStatementAssign()
 * @model
 * @generated
 */
public interface StatementAssign extends Statement
{
  /**
   * Returns the value of the '<em><b>Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target</em>' containment reference.
   * @see #setTarget(VariableReference)
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementAssign_Target()
   * @model containment="true"
   * @generated
   */
  VariableReference getTarget();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.StatementAssign#getTarget <em>Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' containment reference.
   * @see #getTarget()
   * @generated
   */
  void setTarget(VariableReference value);

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
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementAssign_Indexes()
   * @model containment="true"
   * @generated
   */
  EList<AstExpression> getIndexes();

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(AstExpression)
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementAssign_Value()
   * @model containment="true"
   * @generated
   */
  AstExpression getValue();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.StatementAssign#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(AstExpression value);

} // StatementAssign
