/**
 */
package net.sf.orcc.cal.cal.impl;

import java.util.Collection;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.Generator;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expression List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.impl.ExpressionListImpl#getExpressions <em>Expressions</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.ExpressionListImpl#getGenerators <em>Generators</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExpressionListImpl extends AstExpressionImpl implements ExpressionList
{
  /**
   * The cached value of the '{@link #getExpressions() <em>Expressions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpressions()
   * @generated
   * @ordered
   */
  protected EList<AstExpression> expressions;

  /**
   * The cached value of the '{@link #getGenerators() <em>Generators</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGenerators()
   * @generated
   * @ordered
   */
  protected EList<Generator> generators;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExpressionListImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return CalPackage.Literals.EXPRESSION_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<AstExpression> getExpressions()
  {
    if (expressions == null)
    {
      expressions = new EObjectContainmentEList<AstExpression>(AstExpression.class, this, CalPackage.EXPRESSION_LIST__EXPRESSIONS);
    }
    return expressions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Generator> getGenerators()
  {
    if (generators == null)
    {
      generators = new EObjectContainmentEList<Generator>(Generator.class, this, CalPackage.EXPRESSION_LIST__GENERATORS);
    }
    return generators;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case CalPackage.EXPRESSION_LIST__EXPRESSIONS:
        return ((InternalEList<?>)getExpressions()).basicRemove(otherEnd, msgs);
      case CalPackage.EXPRESSION_LIST__GENERATORS:
        return ((InternalEList<?>)getGenerators()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case CalPackage.EXPRESSION_LIST__EXPRESSIONS:
        return getExpressions();
      case CalPackage.EXPRESSION_LIST__GENERATORS:
        return getGenerators();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case CalPackage.EXPRESSION_LIST__EXPRESSIONS:
        getExpressions().clear();
        getExpressions().addAll((Collection<? extends AstExpression>)newValue);
        return;
      case CalPackage.EXPRESSION_LIST__GENERATORS:
        getGenerators().clear();
        getGenerators().addAll((Collection<? extends Generator>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case CalPackage.EXPRESSION_LIST__EXPRESSIONS:
        getExpressions().clear();
        return;
      case CalPackage.EXPRESSION_LIST__GENERATORS:
        getGenerators().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case CalPackage.EXPRESSION_LIST__EXPRESSIONS:
        return expressions != null && !expressions.isEmpty();
      case CalPackage.EXPRESSION_LIST__GENERATORS:
        return generators != null && !generators.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ExpressionListImpl
