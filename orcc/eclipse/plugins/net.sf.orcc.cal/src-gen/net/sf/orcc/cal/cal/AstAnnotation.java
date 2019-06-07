/**
 */
package net.sf.orcc.cal.cal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.AstAnnotation#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.AstAnnotation#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @see net.sf.orcc.cal.cal.CalPackage#getAstAnnotation()
 * @model
 * @generated
 */
public interface AstAnnotation extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see net.sf.orcc.cal.cal.CalPackage#getAstAnnotation_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link net.sf.orcc.cal.cal.AstAnnotation#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
   * The list contents are of type {@link net.sf.orcc.cal.cal.AnnotationArgument}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arguments</em>' containment reference list.
   * @see net.sf.orcc.cal.cal.CalPackage#getAstAnnotation_Arguments()
   * @model containment="true"
   * @generated
   */
  EList<AnnotationArgument> getArguments();

} // AstAnnotation
