/**
 */
package net.sf.orcc.cal.cal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Type Uint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.AstTypeUint#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getAstTypeUint()
 * @model
 * @generated
 */
public interface AstTypeUint extends AstType
{
  /**
   * Returns the value of the '<em><b>Size</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Size</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Size</em>' containment reference.
   * @see #setSize(AstExpression)
   * @see net.sf.orcc.cal.cal.CalPackage#getAstTypeUint_Size()
   * @model containment="true"
   * @generated
   */
  AstExpression getSize();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.AstTypeUint#getSize <em>Size</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Size</em>' containment reference.
   * @see #getSize()
   * @generated
   */
  void setSize(AstExpression value);

} // AstTypeUint
