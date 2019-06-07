/**
 */
package net.sf.orcc.cal.cal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Float</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.ExpressionFloat#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getExpressionFloat()
 * @model
 * @generated
 */
public interface ExpressionFloat extends ExpressionLiteral
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(float)
   * @see net.sf.orcc.cal.cal.CalPackage#getExpressionFloat_Value()
   * @model
   * @generated
   */
  float getValue();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.ExpressionFloat#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(float value);

} // ExpressionFloat
