/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Priority</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.Priority#getInequalities <em>Inequalities</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getPriority()
 * @model
 * @generated
 */
public interface Priority extends EObject
{
  /**
   * Returns the value of the '<em><b>Inequalities</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.Inequality}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Inequalities</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Inequalities</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getPriority_Inequalities()
   * @model containment="true"
   * @generated
   */
  EList<Inequality> getInequalities();

} // Priority
