/**
 */
package net.sf.orcc.cal.cal.impl;

import java.util.Collection;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.Variable;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Input Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.impl.InputPatternImpl#getPort <em>Port</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.InputPatternImpl#getTokens <em>Tokens</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.InputPatternImpl#getRepeat <em>Repeat</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InputPatternImpl extends MinimalEObjectImpl.Container implements InputPattern
{
  /**
   * The cached value of the '{@link #getPort() <em>Port</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPort()
   * @generated
   * @ordered
   */
  protected AstPort port;

  /**
   * The cached value of the '{@link #getTokens() <em>Tokens</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTokens()
   * @generated
   * @ordered
   */
  protected EList<Variable> tokens;

  /**
   * The cached value of the '{@link #getRepeat() <em>Repeat</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRepeat()
   * @generated
   * @ordered
   */
  protected AstExpression repeat;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InputPatternImpl()
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
    return CalPackage.Literals.INPUT_PATTERN;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstPort getPort()
  {
    if (port != null && port.eIsProxy())
    {
      InternalEObject oldPort = (InternalEObject)port;
      port = (AstPort)eResolveProxy(oldPort);
      if (port != oldPort)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, CalPackage.INPUT_PATTERN__PORT, oldPort, port));
      }
    }
    return port;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstPort basicGetPort()
  {
    return port;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPort(AstPort newPort)
  {
    AstPort oldPort = port;
    port = newPort;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.INPUT_PATTERN__PORT, oldPort, port));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Variable> getTokens()
  {
    if (tokens == null)
    {
      tokens = new EObjectContainmentEList<Variable>(Variable.class, this, CalPackage.INPUT_PATTERN__TOKENS);
    }
    return tokens;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstExpression getRepeat()
  {
    return repeat;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetRepeat(AstExpression newRepeat, NotificationChain msgs)
  {
    AstExpression oldRepeat = repeat;
    repeat = newRepeat;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.INPUT_PATTERN__REPEAT, oldRepeat, newRepeat);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRepeat(AstExpression newRepeat)
  {
    if (newRepeat != repeat)
    {
      NotificationChain msgs = null;
      if (repeat != null)
        msgs = ((InternalEObject)repeat).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.INPUT_PATTERN__REPEAT, null, msgs);
      if (newRepeat != null)
        msgs = ((InternalEObject)newRepeat).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.INPUT_PATTERN__REPEAT, null, msgs);
      msgs = basicSetRepeat(newRepeat, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.INPUT_PATTERN__REPEAT, newRepeat, newRepeat));
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
      case CalPackage.INPUT_PATTERN__TOKENS:
        return ((InternalEList<?>)getTokens()).basicRemove(otherEnd, msgs);
      case CalPackage.INPUT_PATTERN__REPEAT:
        return basicSetRepeat(null, msgs);
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
      case CalPackage.INPUT_PATTERN__PORT:
        if (resolve) return getPort();
        return basicGetPort();
      case CalPackage.INPUT_PATTERN__TOKENS:
        return getTokens();
      case CalPackage.INPUT_PATTERN__REPEAT:
        return getRepeat();
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
      case CalPackage.INPUT_PATTERN__PORT:
        setPort((AstPort)newValue);
        return;
      case CalPackage.INPUT_PATTERN__TOKENS:
        getTokens().clear();
        getTokens().addAll((Collection<? extends Variable>)newValue);
        return;
      case CalPackage.INPUT_PATTERN__REPEAT:
        setRepeat((AstExpression)newValue);
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
      case CalPackage.INPUT_PATTERN__PORT:
        setPort((AstPort)null);
        return;
      case CalPackage.INPUT_PATTERN__TOKENS:
        getTokens().clear();
        return;
      case CalPackage.INPUT_PATTERN__REPEAT:
        setRepeat((AstExpression)null);
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
      case CalPackage.INPUT_PATTERN__PORT:
        return port != null;
      case CalPackage.INPUT_PATTERN__TOKENS:
        return tokens != null && !tokens.isEmpty();
      case CalPackage.INPUT_PATTERN__REPEAT:
        return repeat != null;
    }
    return super.eIsSet(featureID);
  }

} //InputPatternImpl
