/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statement Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.StatementCall#getProcedure <em>Procedure</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.StatementCall#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getStatementCall()
 * @model
 * @generated
 */
public interface StatementCall extends Statement
{
  /**
   * Returns the value of the '<em><b>Procedure</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Procedure</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Procedure</em>' reference.
   * @see #setProcedure(AstProcedure)
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementCall_Procedure()
   * @model
   * @generated
   */
  AstProcedure getProcedure();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.StatementCall#getProcedure <em>Procedure</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Procedure</em>' reference.
   * @see #getProcedure()
   * @generated
   */
  void setProcedure(AstProcedure value);

  /**
   * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstExpression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arguments</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getStatementCall_Arguments()
   * @model containment="true"
   * @generated
   */
  EList<AstExpression> getArguments();

} // StatementCall
