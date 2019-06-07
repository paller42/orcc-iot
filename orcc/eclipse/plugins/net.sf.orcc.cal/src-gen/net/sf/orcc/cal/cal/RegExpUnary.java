/**
 */
package net.sf.orcc.cal.cal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reg Exp Unary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.RegExpUnary#getChild <em>Child</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.RegExpUnary#getUnaryOperator <em>Unary Operator</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getRegExpUnary()
 * @model
 * @generated
 */
public interface RegExpUnary extends RegExp
{
  /**
   * Returns the value of the '<em><b>Child</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Child</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Child</em>' containment reference.
   * @see #setChild(RegExp)
   * @see net.sf.orcc.cal.cal.CalPackage#getRegExpUnary_Child()
   * @model containment="true"
   * @generated
   */
  RegExp getChild();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.RegExpUnary#getChild <em>Child</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Child</em>' containment reference.
   * @see #getChild()
   * @generated
   */
  void setChild(RegExp value);

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
   * @see net.sf.orcc.cal.cal.CalPackage#getRegExpUnary_UnaryOperator()
   * @model
   * @generated
   */
  String getUnaryOperator();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.RegExpUnary#getUnaryOperator <em>Unary Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Unary Operator</em>' attribute.
   * @see #getUnaryOperator()
   * @generated
   */
  void setUnaryOperator(String value);

} // RegExpUnary
