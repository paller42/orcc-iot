/**
 */
package net.sf.orcc.cal.cal.impl;

import net.sf.orcc.cal.cal.AstState;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.ExternalTarget;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ast Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstTransitionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstTransitionImpl#getTag <em>Tag</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstTransitionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstTransitionImpl#getExternalTarget <em>External Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AstTransitionImpl extends MinimalEObjectImpl.Container implements AstTransition
{
  /**
   * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSource()
   * @generated
   * @ordered
   */
  protected AstState source;

  /**
   * The cached value of the '{@link #getTag() <em>Tag</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTag()
   * @generated
   * @ordered
   */
  protected AstTag tag;

  /**
   * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTarget()
   * @generated
   * @ordered
   */
  protected AstState target;

  /**
   * The cached value of the '{@link #getExternalTarget() <em>External Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExternalTarget()
   * @generated
   * @ordered
   */
  protected ExternalTarget externalTarget;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AstTransitionImpl()
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
    return CalPackage.Literals.AST_TRANSITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState getSource()
  {
    if (source != null && source.eIsProxy())
    {
      InternalEObject oldSource = (InternalEObject)source;
      source = (AstState)eResolveProxy(oldSource);
      if (source != oldSource)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, CalPackage.AST_TRANSITION__SOURCE, oldSource, source));
      }
    }
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState basicGetSource()
  {
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSource(AstState newSource)
  {
    AstState oldSource = source;
    source = newSource;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_TRANSITION__SOURCE, oldSource, source));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstTag getTag()
  {
    return tag;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTag(AstTag newTag, NotificationChain msgs)
  {
    AstTag oldTag = tag;
    tag = newTag;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.AST_TRANSITION__TAG, oldTag, newTag);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTag(AstTag newTag)
  {
    if (newTag != tag)
    {
      NotificationChain msgs = null;
      if (tag != null)
        msgs = ((InternalEObject)tag).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_TRANSITION__TAG, null, msgs);
      if (newTag != null)
        msgs = ((InternalEObject)newTag).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_TRANSITION__TAG, null, msgs);
      msgs = basicSetTag(newTag, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_TRANSITION__TAG, newTag, newTag));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState getTarget()
  {
    if (target != null && target.eIsProxy())
    {
      InternalEObject oldTarget = (InternalEObject)target;
      target = (AstState)eResolveProxy(oldTarget);
      if (target != oldTarget)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, CalPackage.AST_TRANSITION__TARGET, oldTarget, target));
      }
    }
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstState basicGetTarget()
  {
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTarget(AstState newTarget)
  {
    AstState oldTarget = target;
    target = newTarget;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_TRANSITION__TARGET, oldTarget, target));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExternalTarget getExternalTarget()
  {
    return externalTarget;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExternalTarget(ExternalTarget newExternalTarget, NotificationChain msgs)
  {
    ExternalTarget oldExternalTarget = externalTarget;
    externalTarget = newExternalTarget;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.AST_TRANSITION__EXTERNAL_TARGET, oldExternalTarget, newExternalTarget);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExternalTarget(ExternalTarget newExternalTarget)
  {
    if (newExternalTarget != externalTarget)
    {
      NotificationChain msgs = null;
      if (externalTarget != null)
        msgs = ((InternalEObject)externalTarget).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_TRANSITION__EXTERNAL_TARGET, null, msgs);
      if (newExternalTarget != null)
        msgs = ((InternalEObject)newExternalTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_TRANSITION__EXTERNAL_TARGET, null, msgs);
      msgs = basicSetExternalTarget(newExternalTarget, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_TRANSITION__EXTERNAL_TARGET, newExternalTarget, newExternalTarget));
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
      case CalPackage.AST_TRANSITION__TAG:
        return basicSetTag(null, msgs);
      case CalPackage.AST_TRANSITION__EXTERNAL_TARGET:
        return basicSetExternalTarget(null, msgs);
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
      case CalPackage.AST_TRANSITION__SOURCE:
        if (resolve) return getSource();
        return basicGetSource();
      case CalPackage.AST_TRANSITION__TAG:
        return getTag();
      case CalPackage.AST_TRANSITION__TARGET:
        if (resolve) return getTarget();
        return basicGetTarget();
      case CalPackage.AST_TRANSITION__EXTERNAL_TARGET:
        return getExternalTarget();
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
      case CalPackage.AST_TRANSITION__SOURCE:
        setSource((AstState)newValue);
        return;
      case CalPackage.AST_TRANSITION__TAG:
        setTag((AstTag)newValue);
        return;
      case CalPackage.AST_TRANSITION__TARGET:
        setTarget((AstState)newValue);
        return;
      case CalPackage.AST_TRANSITION__EXTERNAL_TARGET:
        setExternalTarget((ExternalTarget)newValue);
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
      case CalPackage.AST_TRANSITION__SOURCE:
        setSource((AstState)null);
        return;
      case CalPackage.AST_TRANSITION__TAG:
        setTag((AstTag)null);
        return;
      case CalPackage.AST_TRANSITION__TARGET:
        setTarget((AstState)null);
        return;
      case CalPackage.AST_TRANSITION__EXTERNAL_TARGET:
        setExternalTarget((ExternalTarget)null);
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
      case CalPackage.AST_TRANSITION__SOURCE:
        return source != null;
      case CalPackage.AST_TRANSITION__TAG:
        return tag != null;
      case CalPackage.AST_TRANSITION__TARGET:
        return target != null;
      case CalPackage.AST_TRANSITION__EXTERNAL_TARGET:
        return externalTarget != null;
    }
    return super.eIsSet(featureID);
  }

} //AstTransitionImpl
