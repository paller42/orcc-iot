/**
 */
package net.sf.orcc.cal.cal.impl;

import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.RegExp;
import net.sf.orcc.cal.cal.RegExpUnary;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reg Exp Unary</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.impl.RegExpUnaryImpl#getChild <em>Child</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.RegExpUnaryImpl#getUnaryOperator <em>Unary Operator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RegExpUnaryImpl extends RegExpImpl implements RegExpUnary
{
  /**
   * The cached value of the '{@link #getChild() <em>Child</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getChild()
   * @generated
   * @ordered
   */
  protected RegExp child;

  /**
   * The default value of the '{@link #getUnaryOperator() <em>Unary Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUnaryOperator()
   * @generated
   * @ordered
   */
  protected static final String UNARY_OPERATOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUnaryOperator() <em>Unary Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUnaryOperator()
   * @generated
   * @ordered
   */
  protected String unaryOperator = UNARY_OPERATOR_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RegExpUnaryImpl()
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
    return CalPackage.Literals.REG_EXP_UNARY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegExp getChild()
  {
    return child;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetChild(RegExp newChild, NotificationChain msgs)
  {
    RegExp oldChild = child;
    child = newChild;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.REG_EXP_UNARY__CHILD, oldChild, newChild);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setChild(RegExp newChild)
  {
    if (newChild != child)
    {
      NotificationChain msgs = null;
      if (child != null)
        msgs = ((InternalEObject)child).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.REG_EXP_UNARY__CHILD, null, msgs);
      if (newChild != null)
        msgs = ((InternalEObject)newChild).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.REG_EXP_UNARY__CHILD, null, msgs);
      msgs = basicSetChild(newChild, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.REG_EXP_UNARY__CHILD, newChild, newChild));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getUnaryOperator()
  {
    return unaryOperator;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUnaryOperator(String newUnaryOperator)
  {
    String oldUnaryOperator = unaryOperator;
    unaryOperator = newUnaryOperator;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.REG_EXP_UNARY__UNARY_OPERATOR, oldUnaryOperator, unaryOperator));
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
      case CalPackage.REG_EXP_UNARY__CHILD:
        return basicSetChild(null, msgs);
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
      case CalPackage.REG_EXP_UNARY__CHILD:
        return getChild();
      case CalPackage.REG_EXP_UNARY__UNARY_OPERATOR:
        return getUnaryOperator();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case CalPackage.REG_EXP_UNARY__CHILD:
        setChild((RegExp)newValue);
        return;
      case CalPackage.REG_EXP_UNARY__UNARY_OPERATOR:
        setUnaryOperator((String)newValue);
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
      case CalPackage.REG_EXP_UNARY__CHILD:
        setChild((RegExp)null);
        return;
      case CalPackage.REG_EXP_UNARY__UNARY_OPERATOR:
        setUnaryOperator(UNARY_OPERATOR_EDEFAULT);
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
      case CalPackage.REG_EXP_UNARY__CHILD:
        return child != null;
      case CalPackage.REG_EXP_UNARY__UNARY_OPERATOR:
        return UNARY_OPERATOR_EDEFAULT == null ? unaryOperator != null : !UNARY_OPERATOR_EDEFAULT.equals(unaryOperator);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (unaryOperator: ");
    result.append(unaryOperator);
    result.append(')');
    return result.toString();
  }

} //RegExpUnaryImpl
