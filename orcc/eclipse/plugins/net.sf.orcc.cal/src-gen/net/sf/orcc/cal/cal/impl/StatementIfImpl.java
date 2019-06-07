/**
 */
package net.sf.orcc.cal.cal.impl;

import java.util.Collection;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.Statement;
import net.sf.orcc.cal.cal.StatementElsif;
import net.sf.orcc.cal.cal.StatementIf;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Statement If</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.impl.StatementIfImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.StatementIfImpl#getThen <em>Then</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.StatementIfImpl#getElsifs <em>Elsifs</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.StatementIfImpl#getElse <em>Else</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StatementIfImpl extends StatementImpl implements StatementIf
{
  /**
   * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCondition()
   * @generated
   * @ordered
   */
  protected AstExpression condition;

  /**
   * The cached value of the '{@link #getThen() <em>Then</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getThen()
   * @generated
   * @ordered
   */
  protected EList<Statement> then;

  /**
   * The cached value of the '{@link #getElsifs() <em>Elsifs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElsifs()
   * @generated
   * @ordered
   */
  protected EList<StatementElsif> elsifs;

  /**
   * The cached value of the '{@link #getElse() <em>Else</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElse()
   * @generated
   * @ordered
   */
  protected EList<Statement> else_;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected StatementIfImpl()
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
    return CalPackage.Literals.STATEMENT_IF;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstExpression getCondition()
  {
    return condition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCondition(AstExpression newCondition, NotificationChain msgs)
  {
    AstExpression oldCondition = condition;
    condition = newCondition;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.STATEMENT_IF__CONDITION, oldCondition, newCondition);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCondition(AstExpression newCondition)
  {
    if (newCondition != condition)
    {
      NotificationChain msgs = null;
      if (condition != null)
        msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.STATEMENT_IF__CONDITION, null, msgs);
      if (newCondition != null)
        msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.STATEMENT_IF__CONDITION, null, msgs);
      msgs = basicSetCondition(newCondition, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.STATEMENT_IF__CONDITION, newCondition, newCondition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Statement> getThen()
  {
    if (then == null)
    {
      then = new EObjectContainmentEList<Statement>(Statement.class, this, CalPackage.STATEMENT_IF__THEN);
    }
    return then;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<StatementElsif> getElsifs()
  {
    if (elsifs == null)
    {
      elsifs = new EObjectContainmentEList<StatementElsif>(StatementElsif.class, this, CalPackage.STATEMENT_IF__ELSIFS);
    }
    return elsifs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Statement> getElse()
  {
    if (else_ == null)
    {
      else_ = new EObjectContainmentEList<Statement>(Statement.class, this, CalPackage.STATEMENT_IF__ELSE);
    }
    return else_;
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
      case CalPackage.STATEMENT_IF__CONDITION:
        return basicSetCondition(null, msgs);
      case CalPackage.STATEMENT_IF__THEN:
        return ((InternalEList<?>)getThen()).basicRemove(otherEnd, msgs);
      case CalPackage.STATEMENT_IF__ELSIFS:
        return ((InternalEList<?>)getElsifs()).basicRemove(otherEnd, msgs);
      case CalPackage.STATEMENT_IF__ELSE:
        return ((InternalEList<?>)getElse()).basicRemove(otherEnd, msgs);
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
      case CalPackage.STATEMENT_IF__CONDITION:
        return getCondition();
      case CalPackage.STATEMENT_IF__THEN:
        return getThen();
      case CalPackage.STATEMENT_IF__ELSIFS:
        return getElsifs();
      case CalPackage.STATEMENT_IF__ELSE:
        return getElse();
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
      case CalPackage.STATEMENT_IF__CONDITION:
        setCondition((AstExpression)newValue);
        return;
      case CalPackage.STATEMENT_IF__THEN:
        getThen().clear();
        getThen().addAll((Collection<? extends Statement>)newValue);
        return;
      case CalPackage.STATEMENT_IF__ELSIFS:
        getElsifs().clear();
        getElsifs().addAll((Collection<? extends StatementElsif>)newValue);
        return;
      case CalPackage.STATEMENT_IF__ELSE:
        getElse().clear();
        getElse().addAll((Collection<? extends Statement>)newValue);
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
      case CalPackage.STATEMENT_IF__CONDITION:
        setCondition((AstExpression)null);
        return;
      case CalPackage.STATEMENT_IF__THEN:
        getThen().clear();
        return;
      case CalPackage.STATEMENT_IF__ELSIFS:
        getElsifs().clear();
        return;
      case CalPackage.STATEMENT_IF__ELSE:
        getElse().clear();
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
      case CalPackage.STATEMENT_IF__CONDITION:
        return condition != null;
      case CalPackage.STATEMENT_IF__THEN:
        return then != null && !then.isEmpty();
      case CalPackage.STATEMENT_IF__ELSIFS:
        return elsifs != null && !elsifs.isEmpty();
      case CalPackage.STATEMENT_IF__ELSE:
        return else_ != null && !else_.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //StatementIfImpl
