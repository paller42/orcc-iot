/**
 */
package net.sf.orcc.cal.cal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reg Exp Tag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.RegExpTag#getTag <em>Tag</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getRegExpTag()
 * @model
 * @generated
 */
public interface RegExpTag extends RegExp
{
  /**
   * Returns the value of the '<em><b>Tag</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tag</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tag</em>' containment reference.
   * @see #setTag(AstTag)
   * @see net.sf.orcc.cal.cal.CalPackage#getRegExpTag_Tag()
   * @model containment="true"
   * @generated
   */
  AstTag getTag();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.RegExpTag#getTag <em>Tag</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tag</em>' containment reference.
   * @see #getTag()
   * @generated
   */
  void setTag(AstTag value);

} // RegExpTag
