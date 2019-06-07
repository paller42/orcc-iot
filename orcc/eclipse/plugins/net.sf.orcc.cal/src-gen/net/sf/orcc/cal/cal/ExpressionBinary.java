/**
 */
package net.sf.orcc.cal.cal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Binary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionBinary#getLeft <em>Left</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionBinary#getOperator <em>Operator</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionBinary#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getExpressionBinary()
 * @model
 * @generated
 */
public interface ExpressionBinary extends AstExpression
{
  /**
   * Returns the value of the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Left</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Left</em>' containment reference.
   * @see #setLeft(AstExpression)
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionBinary_Left()
   * @model containment="true"
   * @generated
   */
  AstExpression getLeft();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExpressionBinary#getLeft <em>Left</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left</em>' containment reference.
   * @see #getLeft()
   * @generated
   */
  void setLeft(AstExpression value);

  /**
   * Returns the value of the '<em><b>Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operator</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operator</em>' attribute.
   * @see #setOperator(String)
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionBinary_Operator()
   * @model
   * @generated
   */
  String getOperator();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExpressionBinary#getOperator <em>Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operator</em>' attribute.
   * @see #getOperator()
   * @generated
   */
  void setOperator(String value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(AstExpression)
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionBinary_Right()
   * @model containment="true"
   * @generated
   */
  AstExpression getRight();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExpressionBinary#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(AstExpression value);

} // ExpressionBinary
