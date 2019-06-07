/**
 */
package net.sf.orcc.cal.cal.impl;

import java.util.Collection;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.Statement;
import net.sf.orcc.cal.cal.StatementForeach;
import net.sf.orcc.cal.cal.Variable;

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
 * An implementation of the model object '<em><b>Statement Foreach</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.impl.StatementForeachImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.StatementForeachImpl#getLower <em>Lower</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.StatementForeachImpl#getHigher <em>Higher</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.StatementForeachImpl#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StatementForeachImpl extends StatementImpl implements StatementForeach
{
  /**
   * The cached value of the '{@link #getVariable() <em>Variable</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariable()
   * @generated
   * @ordered
   */
  protected Variable variable;

  /**
   * The cached value of the '{@link #getLower() <em>Lower</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLower()
   * @generated
   * @ordered
   */
  protected AstExpression lower;

  /**
   * The cached value of the '{@link #getHigher() <em>Higher</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHigher()
   * @generated
   * @ordered
   */
  protected AstExpression higher;

  /**
   * The cached value of the '{@link #getStatements() <em>Statements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStatements()
   * @generated
   * @ordered
   */
  protected EList<Statement> statements;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected StatementForeachImpl()
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
    return CalPackage.Literals.STATEMENT_FOREACH;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Variable getVariable()
  {
    return variable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetVariable(Variable newVariable, NotificationChain msgs)
  {
    Variable oldVariable = variable;
    variable = newVariable;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.STATEMENT_FOREACH__VARIABLE, oldVariable, newVariable);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVariable(Variable newVariable)
  {
    if (newVariable != variable)
    {
      NotificationChain msgs = null;
      if (variable != null)
        msgs = ((InternalEObject)variable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.STATEMENT_FOREACH__VARIABLE, null, msgs);
      if (newVariable != null)
        msgs = ((InternalEObject)newVariable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.STATEMENT_FOREACH__VARIABLE, null, msgs);
      msgs = basicSetVariable(newVariable, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.STATEMENT_FOREACH__VARIABLE, newVariable, newVariable));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstExpression getLower()
  {
    return lower;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLower(AstExpression newLower, NotificationChain msgs)
  {
    AstExpression oldLower = lower;
    lower = newLower;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.STATEMENT_FOREACH__LOWER, oldLower, newLower);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLower(AstExpression newLower)
  {
    if (newLower != lower)
    {
      NotificationChain msgs = null;
      if (lower != null)
        msgs = ((InternalEObject)lower).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.STATEMENT_FOREACH__LOWER, null, msgs);
      if (newLower != null)
        msgs = ((InternalEObject)newLower).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.STATEMENT_FOREACH__LOWER, null, msgs);
      msgs = basicSetLower(newLower, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.STATEMENT_FOREACH__LOWER, newLower, newLower));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstExpression getHigher()
  {
    return higher;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetHigher(AstExpression newHigher, NotificationChain msgs)
  {
    AstExpression oldHigher = higher;
    higher = newHigher;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.STATEMENT_FOREACH__HIGHER, oldHigher, newHigher);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHigher(AstExpression newHigher)
  {
    if (newHigher != higher)
    {
      NotificationChain msgs = null;
      if (higher != null)
        msgs = ((InternalEObject)higher).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.STATEMENT_FOREACH__HIGHER, null, msgs);
      if (newHigher != null)
        msgs = ((InternalEObject)newHigher).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.STATEMENT_FOREACH__HIGHER, null, msgs);
      msgs = basicSetHigher(newHigher, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.STATEMENT_FOREACH__HIGHER, newHigher, newHigher));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Statement> getStatements()
  {
    if (statements == null)
    {
      statements = new EObjectContainmentEList<Statement>(Statement.class, this, CalPackage.STATEMENT_FOREACH__STATEMENTS);
    }
    return statements;
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
      case CalPackage.STATEMENT_FOREACH__VARIABLE:
        return basicSetVariable(null, msgs);
      case CalPackage.STATEMENT_FOREACH__LOWER:
        return basicSetLower(null, msgs);
      case CalPackage.STATEMENT_FOREACH__HIGHER:
        return basicSetHigher(null, msgs);
      case CalPackage.STATEMENT_FOREACH__STATEMENTS:
        return ((InternalEList<?>)getStatements()).basicRemove(otherEnd, msgs);
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
      case CalPackage.STATEMENT_FOREACH__VARIABLE:
        return getVariable();
      case CalPackage.STATEMENT_FOREACH__LOWER:
        return getLower();
      case CalPackage.STATEMENT_FOREACH__HIGHER:
        return getHigher();
      case CalPackage.STATEMENT_FOREACH__STATEMENTS:
        return getStatements();
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
      case CalPackage.STATEMENT_FOREACH__VARIABLE:
        setVariable((Variable)newValue);
        return;
      case CalPackage.STATEMENT_FOREACH__LOWER:
        setLower((AstExpression)newValue);
        return;
      case CalPackage.STATEMENT_FOREACH__HIGHER:
        setHigher((AstExpression)newValue);
        return;
      case CalPackage.STATEMENT_FOREACH__STATEMENTS:
        getStatements().clear();
        getStatements().addAll((Collection<? extends Statement>)newValue);
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
      case CalPackage.STATEMENT_FOREACH__VARIABLE:
        setVariable((Variable)null);
        return;
      case CalPackage.STATEMENT_FOREACH__LOWER:
        setLower((AstExpression)null);
        return;
      case CalPackage.STATEMENT_FOREACH__HIGHER:
        setHigher((AstExpression)null);
        return;
      case CalPackage.STATEMENT_FOREACH__STATEMENTS:
        getStatements().clear();
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
      case CalPackage.STATEMENT_FOREACH__VARIABLE:
        return variable != null;
      case CalPackage.STATEMENT_FOREACH__LOWER:
        return lower != null;
      case CalPackage.STATEMENT_FOREACH__HIGHER:
        return higher != null;
      case CalPackage.STATEMENT_FOREACH__STATEMENTS:
        return statements != null && !statements.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //StatementForeachImpl
