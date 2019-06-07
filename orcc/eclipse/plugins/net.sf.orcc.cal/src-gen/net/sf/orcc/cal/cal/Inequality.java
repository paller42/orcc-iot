/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inequality</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.Inequality#getTags <em>Tags</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getInequality()
 * @model
 * @generated
 */
public interface Inequality extends EObject
{
  /**
   * Returns the value of the '<em><b>Tags</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AstTag}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tags</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tags</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getInequality_Tags()
   * @model containment="true"
   * @generated
   */
  EList<AstTag> getTags();

} // Inequality
