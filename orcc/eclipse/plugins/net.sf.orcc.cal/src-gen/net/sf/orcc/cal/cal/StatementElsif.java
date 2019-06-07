/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statement Elsif</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.StatementElsif#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementElsif#getThen <em>Then</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getStatementElsif()
 * @model
 * @generated
 */
public interface StatementElsif extends EObject
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
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementElsif_Condition()
   * @model containment="true"
   * @generated
   */
  AstExpression getCondition();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.StatementElsif#getCondition <em>Condition</em>}' containment reference.
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
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementElsif_Then()
   * @model containment="true"
   * @generated
   */
  EList<Statement> getThen();

} // StatementElsif
