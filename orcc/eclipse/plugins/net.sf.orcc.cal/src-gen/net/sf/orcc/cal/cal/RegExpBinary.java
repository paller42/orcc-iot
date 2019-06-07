/**
 */
package net.sf.orcc.cal.cal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reg Exp Binary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.RegExpBinary#getLeft <em>Left</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.RegExpBinary#getOperator <em>Operator</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.RegExpBinary#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getRegExpBinary()
 * @model
 * @generated
 */
public interface RegExpBinary extends RegExp
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
   * @see #setLeft(RegExp)
   * @see net.sf.orcc.cal.cal.CalPackage#getRegExpBinary_Left()
   * @model containment="true"
   * @generated
   */
  RegExp getLeft();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.RegExpBinary#getLeft <em>Left</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left</em>' containment reference.
   * @see #getLeft()
   * @generated
   */
  void setLeft(RegExp value);

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
   * @see net.sf.orcc.cal.cal.CalPackage#getRegExpBinary_Operator()
   * @model
   * @generated
   */
  String getOperator();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.RegExpBinary#getOperator <em>Operator</em>}' attribute.
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
   * @see #setRight(RegExp)
   * @see net.sf.orcc.cal.cal.CalPackage#getRegExpBinary_Right()
   * @model containment="true"
   * @generated
   */
  RegExp getRight();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.RegExpBinary#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(RegExp value);

} // RegExpBinary
