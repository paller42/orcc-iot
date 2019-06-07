/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statement Foreach</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.StatementForeach#getVariable <em>Variable</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementForeach#getLower <em>Lower</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementForeach#getHigher <em>Higher</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementForeach#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getStatementForeach()
 * @model
 * @generated
 */
public interface StatementForeach extends Statement
{
  /**
   * Returns the value of the '<em><b>Variable</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Variable</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variable</em>' containment reference.
   * @see #setVariable(Variable)
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementForeach_Variable()
   * @model containment="true"
   * @generated
   */
  Variable getVariable();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.StatementForeach#getVariable <em>Variable</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Variable</em>' containment reference.
   * @see #getVariable()
   * @generated
   */
  void setVariable(Variable value);

  /**
   * Returns the value of the '<em><b>Lower</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Lower</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lower</em>' containment reference.
   * @see #setLower(AstExpression)
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementForeach_Lower()
   * @model containment="true"
   * @generated
   */
  AstExpression getLower();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.StatementForeach#getLower <em>Lower</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lower</em>' containment reference.
   * @see #getLower()
   * @generated
   */
  void setLower(AstExpression value);

  /**
   * Returns the value of the '<em><b>Higher</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Higher</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Higher</em>' containment reference.
   * @see #setHigher(AstExpression)
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementForeach_Higher()
   * @model containment="true"
   * @generated
   */
  AstExpression getHigher();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.StatementForeach#getHigher <em>Higher</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Higher</em>' containment reference.
   * @see #getHigher()
   * @generated
   */
  void setHigher(AstExpression value);

  /**
   * Returns the value of the '<em><b>Statements</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Statement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Statements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Statements</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementForeach_Statements()
   * @model containment="true"
   * @generated
   */
  EList<Statement> getStatements();

} // StatementForeach
