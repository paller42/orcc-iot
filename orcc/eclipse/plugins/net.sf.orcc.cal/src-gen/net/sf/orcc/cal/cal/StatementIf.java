/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statement If</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.StatementIf#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementIf#getThen <em>Then</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementIf#getElsifs <em>Elsifs</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementIf#getElse <em>Else</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getStatementIf()
 * @model
 * @generated
 */
public interface StatementIf extends Statement
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
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementIf_Condition()
   * @model containment="true"
   * @generated
   */
  AstExpression getCondition();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.StatementIf#getCondition <em>Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Condition</em>' containment reference.
   * @see #getCondition()
   * @generated
   */
  void setCondition(AstExpression value);

  /**
   * Returns the value of the '<em><b>Then</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Statement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Then</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Then</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementIf_Then()
   * @model containment="true"
   * @generated
   */
  EList<Statement> getThen();

  /**
   * Returns the value of the '<em><b>Elsifs</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.StatementElsif}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elsifs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elsifs</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementIf_Elsifs()
   * @model containment="true"
   * @generated
   */
  EList<StatementElsif> getElsifs();

  /**
   * Returns the value of the '<em><b>Else</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Statement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Else</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Else</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementIf_Else()
   * @model containment="true"
   * @generated
   */
  EList<Statement> getElse();

} // StatementIf
