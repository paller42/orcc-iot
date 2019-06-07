/**
 */
package net.sf.orcc.cal.cal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Unary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionUnary#getUnaryOperator <em>Unary Operator</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionUnary#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getExpressionUnary()
 * @model
 * @generated
 */
public interface ExpressionUnary extends AstExpression
{
  /**
   * Returns the value of the '<em><b>Unary Operator</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Unary Operator</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Unary Operator</em>' attribute.
   * @see #setUnaryOperator(String)
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionUnary_UnaryOperator()
   * @model
   * @generated
   */
  String getUnaryOperator();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExpressionUnary#getUnaryOperator <em>Unary Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Unary Operator</em>' attribute.
   * @see #getUnaryOperator()
   * @generated
   */
  void setUnaryOperator(String value);

  /**
   * Returns the value of the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expression</em>' containment reference.
   * @see #setExpression(AstExpression)
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionUnary_Expression()
   * @model containment="true"
   * @generated
   */
  AstExpression getExpression();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExpressionUnary#getExpression <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' containment reference.
   * @see #getExpression()
   * @generated
   */
  void setExpression(AstExpression value);

} // ExpressionUnary
