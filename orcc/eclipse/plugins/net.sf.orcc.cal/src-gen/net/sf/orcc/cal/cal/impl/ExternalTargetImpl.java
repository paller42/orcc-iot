/**
 */
package net.sf.orcc.cal.cal.impl;

import net.sf.orcc.cal.cal.AstState;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.ExternalTarget;
import net.sf.orcc.cal.cal.LocalFsm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Target</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.impl.ExternalTargetImpl#getFsm <em>Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.ExternalTargetImpl#getState <em>State</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.ExternalTargetImpl#getFrom <em>From</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.ExternalTargetImpl#getTo <em>To</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExternalTargetImpl extends MinimalEObjectImpl.Container implements ExternalTarget
{
  /**
   * The cached value of the '{@link #getFsm() <em>Fsm</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFsm()
   * @generated
   * @ordered
   */
  protected LocalFsm fsm;

  /**
   * The cached value of the '{@link #getState() <em>State</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getState()
   * @generated
   * @ordered
   */
  protected AstState state;

  /**
   * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFrom()
   * @generated
   * @ordered
   */
  protected AstState from;

  /**
   * The cached value of the '{@link #getTo() <em>To</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTo()
   * @generated
   * @ordered
   */
  protected AstState to;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExternalTargetImpl()
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
    return CalPackage.Literals.EXTERNAL_TARGET;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalFsm getFsm()
  {
    if (fsm != null && fsm.eIsProxy())
    {
      InternalEObject oldFsm = (InternalEObject)fsm;
      fsm = (LocalFsm)eResolveProxy(oldFsm);
      if (fsm != oldFsm)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, CalPackage.EXTERNAL_TARGET__FSM, oldFsm, fsm));
      }
    }
    return fsm;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalFsm basicGetFsm()
  {
    return fsm;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFsm(LocalFsm newFsm)
  {
    LocalFsm oldFsm = fsm;
    fsm = newFsm;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.EXTERNAL_TARGET__FSM, oldFsm, fsm));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState getState()
  {
    if (state != null && state.eIsProxy())
    {
      InternalEObject oldState = (InternalEObject)state;
      state = (AstState)eResolveProxy(oldState);
      if (state != oldState)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, CalPackage.EXTERNAL_TARGET__STATE, oldState, state));
      }
    }
    return state;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState basicGetState()
  {
    return state;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setState(AstState newState)
  {
    AstState oldState = state;
    state = newState;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.EXTERNAL_TARGET__STATE, oldState, state));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState getFrom()
  {
    if (from != null && from.eIsProxy())
    {
      InternalEObject oldFrom = (InternalEObject)from;
      from = (AstState)eResolveProxy(oldFrom);
      if (from != oldFrom)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, CalPackage.EXTERNAL_TARGET__FROM, oldFrom, from));
      }
    }
    return from;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState basicGetFrom()
  {
    return from;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFrom(AstState newFrom)
  {
    AstState oldFrom = from;
    from = newFrom;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.EXTERNAL_TARGET__FROM, oldFrom, from));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState getTo()
  {
    if (to != null && to.eIsProxy())
    {
      InternalEObject oldTo = (InternalEObject)to;
      to = (AstState)eResolveProxy(oldTo);
      if (to != oldTo)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, CalPackage.EXTERNAL_TARGET__TO, oldTo, to));
      }
    }
    return to;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState basicGetTo()
  {
    return to;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTo(AstState newTo)
  {
    AstState oldTo = to;
    to = newTo;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.EXTERNAL_TARGET__TO, oldTo, to));
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
      case CalPackage.EXTERNAL_TARGET__FSM:
        if (resolve) return getFsm();
        return basicGetFsm();
      case CalPackage.EXTERNAL_TARGET__STATE:
        if (resolve) return getState();
        return basicGetState();
      case CalPackage.EXTERNAL_TARGET__FROM:
        if (resolve) return getFrom();
        return basicGetFrom();
      case CalPackage.EXTERNAL_TARGET__TO:
        if (resolve) return getTo();
        return basicGetTo();
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
      case CalPackage.EXTERNAL_TARGET__FSM:
        setFsm((LocalFsm)newValue);
        return;
      case CalPackage.EXTERNAL_TARGET__STATE:
        setState((AstState)newValue);
        return;
      case CalPackage.EXTERNAL_TARGET__FROM:
        setFrom((AstState)newValue);
        return;
      case CalPackage.EXTERNAL_TARGET__TO:
        setTo((AstState)newValue);
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
      case CalPackage.EXTERNAL_TARGET__FSM:
        setFsm((LocalFsm)null);
        return;
      case CalPackage.EXTERNAL_TARGET__STATE:
        setState((AstState)null);
        return;
      case CalPackage.EXTERNAL_TARGET__FROM:
        setFrom((AstState)null);
        return;
      case CalPackage.EXTERNAL_TARGET__TO:
        setTo((AstState)null);
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
      case CalPackage.EXTERNAL_TARGET__FSM:
        return fsm != null;
      case CalPackage.EXTERNAL_TARGET__STATE:
        return state != null;
      case CalPackage.EXTERNAL_TARGET__FROM:
        return from != null;
      case CalPackage.EXTERNAL_TARGET__TO:
        return to != null;
    }
    return super.eIsSet(featureID);
  }

} //ExternalTargetImpl
