/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.AstUnit#getFunctions <em>Functions</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstUnit#getProcedures <em>Procedures</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstUnit#getVariables <em>Variables</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getAstUnit()
 * @model
 * @generated
 */
public interface AstUnit extends EObject
{
  /**
   * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Function}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Functions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Functions</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstUnit_Functions()
   * @model containment="true"
   * @generated
   */
  EList<Function> getFunctions();

  /**
   * Returns the value of the '<em><b>Procedures</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstProcedure}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Procedures</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Procedures</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstUnit_Procedures()
   * @model containment="true"
   * @generated
   */
  EList<AstProcedure> getProcedures();

  /**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Variable}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstUnit_Variables()
   * @model containment="true"
   * @generated
   */
  EList<Variable> getVariables();

} // AstUnit
