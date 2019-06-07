/**
 */
package net.sf.orcc.cal.cal.impl;

import java.util.Collection;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.LocalFsm;
import net.sf.orcc.cal.cal.Priority;
import net.sf.orcc.cal.cal.RegExp;
import net.sf.orcc.cal.cal.ScheduleFsm;
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
 * An implementation of the model object '<em><b>Ast Actor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getFunctions <em>Functions</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getProcedures <em>Procedures</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getInitializes <em>Initializes</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getStateVariables <em>State Variables</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getLocalFsms <em>Local Fsms</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getScheduleFsm <em>Schedule Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getScheduleRegExp <em>Schedule Reg Exp</em>}</li>
 *   <li>{@link net.sf.orcc.cal.cal.impl.AstActorImpl#getPriorities <em>Priorities</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AstActorImpl extends MinimalEObjectImpl.Container implements AstActor
{
  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected EList<Variable> parameters;

  /**
   * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInputs()
   * @generated
   * @ordered
   */
  protected EList<AstPort> inputs;

  /**
   * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOutputs()
   * @generated
   * @ordered
   */
  protected EList<AstPort> outputs;

  /**
   * The cached value of the '{@link #getFunctions() <em>Functions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunctions()
   * @generated
   * @ordered
   */
  protected EList<Function> functions;

  /**
   * The cached value of the '{@link #getProcedures() <em>Procedures</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProcedures()
   * @generated
   * @ordered
   */
  protected EList<AstProcedure> procedures;

  /**
   * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getActions()
   * @generated
   * @ordered
   */
  protected EList<AstAction> actions;

  /**
   * The cached value of the '{@link #getInitializes() <em>Initializes</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitializes()
   * @generated
   * @ordered
   */
  protected EList<AstAction> initializes;

  /**
   * The cached value of the '{@link #getStateVariables() <em>State Variables</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStateVariables()
   * @generated
   * @ordered
   */
  protected EList<Variable> stateVariables;

  /**
   * The cached value of the '{@link #getLocalFsms() <em>Local Fsms</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLocalFsms()
   * @generated
   * @ordered
   */
  protected EList<LocalFsm> localFsms;

  /**
   * The cached value of the '{@link #getScheduleFsm() <em>Schedule Fsm</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getScheduleFsm()
   * @generated
   * @ordered
   */
  protected ScheduleFsm scheduleFsm;

  /**
   * The cached value of the '{@link #getScheduleRegExp() <em>Schedule Reg Exp</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getScheduleRegExp()
   * @generated
   * @ordered
   */
  protected RegExp scheduleRegExp;

  /**
   * The cached value of the '{@link #getPriorities() <em>Priorities</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPriorities()
   * @generated
   * @ordered
   */
  protected EList<Priority> priorities;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AstActorImpl()
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
    return CalPackage.Literals.AST_ACTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Variable> getParameters()
  {
    if (parameters == null)
    {
      parameters = new EObjectContainmentEList<Variable>(Variable.class, this, CalPackage.AST_ACTOR__PARAMETERS);
    }
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<AstPort> getInputs()
  {
    if (inputs == null)
    {
      inputs = new EObjectContainmentEList<AstPort>(AstPort.class, this, CalPackage.AST_ACTOR__INPUTS);
    }
    return inputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<AstPort> getOutputs()
  {
    if (outputs == null)
    {
      outputs = new EObjectContainmentEList<AstPort>(AstPort.class, this, CalPackage.AST_ACTOR__OUTPUTS);
    }
    return outputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Function> getFunctions()
  {
    if (functions == null)
    {
      functions = new EObjectContainmentEList<Function>(Function.class, this, CalPackage.AST_ACTOR__FUNCTIONS);
    }
    return functions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<AstProcedure> getProcedures()
  {
    if (procedures == null)
    {
      procedures = new EObjectContainmentEList<AstProcedure>(AstProcedure.class, this, CalPackage.AST_ACTOR__PROCEDURES);
    }
    return procedures;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<AstAction> getActions()
  {
    if (actions == null)
    {
      actions = new EObjectContainmentEList<AstAction>(AstAction.class, this, CalPackage.AST_ACTOR__ACTIONS);
    }
    return actions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<AstAction> getInitializes()
  {
    if (initializes == null)
    {
      initializes = new EObjectContainmentEList<AstAction>(AstAction.class, this, CalPackage.AST_ACTOR__INITIALIZES);
    }
    return initializes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Variable> getStateVariables()
  {
    if (stateVariables == null)
    {
      stateVariables = new EObjectContainmentEList<Variable>(Variable.class, this, CalPackage.AST_ACTOR__STATE_VARIABLES);
    }
    return stateVariables;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LocalFsm> getLocalFsms()
  {
    if (localFsms == null)
    {
      localFsms = new EObjectContainmentEList<LocalFsm>(LocalFsm.class, this, CalPackage.AST_ACTOR__LOCAL_FSMS);
    }
    return localFsms;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScheduleFsm getScheduleFsm()
  {
    return scheduleFsm;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetScheduleFsm(ScheduleFsm newScheduleFsm, NotificationChain msgs)
  {
    ScheduleFsm oldScheduleFsm = scheduleFsm;
    scheduleFsm = newScheduleFsm;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.AST_ACTOR__SCHEDULE_FSM, oldScheduleFsm, newScheduleFsm);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setScheduleFsm(ScheduleFsm newScheduleFsm)
  {
    if (newScheduleFsm != scheduleFsm)
    {
      NotificationChain msgs = null;
      if (scheduleFsm != null)
        msgs = ((InternalEObject)scheduleFsm).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ACTOR__SCHEDULE_FSM, null, msgs);
      if (newScheduleFsm != null)
        msgs = ((InternalEObject)newScheduleFsm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ACTOR__SCHEDULE_FSM, null, msgs);
      msgs = basicSetScheduleFsm(newScheduleFsm, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_ACTOR__SCHEDULE_FSM, newScheduleFsm, newScheduleFsm));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegExp getScheduleRegExp()
  {
    return scheduleRegExp;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetScheduleRegExp(RegExp newScheduleRegExp, NotificationChain msgs)
  {
    RegExp oldScheduleRegExp = scheduleRegExp;
    scheduleRegExp = newScheduleRegExp;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.AST_ACTOR__SCHEDULE_REG_EXP, oldScheduleRegExp, newScheduleRegExp);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setScheduleRegExp(RegExp newScheduleRegExp)
  {
    if (newScheduleRegExp != scheduleRegExp)
    {
      NotificationChain msgs = null;
      if (scheduleRegExp != null)
        msgs = ((InternalEObject)scheduleRegExp).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ACTOR__SCHEDULE_REG_EXP, null, msgs);
      if (newScheduleRegExp != null)
        msgs = ((InternalEObject)newScheduleRegExp).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ACTOR__SCHEDULE_REG_EXP, null, msgs);
      msgs = basicSetScheduleRegExp(newScheduleRegExp, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_ACTOR__SCHEDULE_REG_EXP, newScheduleRegExp, newScheduleRegExp));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Priority> getPriorities()
  {
    if (priorities == null)
    {
      priorities = new EObjectContainmentEList<Priority>(Priority.class, this, CalPackage.AST_ACTOR__PRIORITIES);
    }
    return priorities;
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
      case CalPackage.AST_ACTOR__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ACTOR__INPUTS:
        return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ACTOR__OUTPUTS:
        return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ACTOR__FUNCTIONS:
        return ((InternalEList<?>)getFunctions()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ACTOR__PROCEDURES:
        return ((InternalEList<?>)getProcedures()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ACTOR__ACTIONS:
        return ((InternalEList<?>)getActions()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ACTOR__INITIALIZES:
        return ((InternalEList<?>)getInitializes()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ACTOR__STATE_VARIABLES:
        return ((InternalEList<?>)getStateVariables()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ACTOR__LOCAL_FSMS:
        return ((InternalEList<?>)getLocalFsms()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ACTOR__SCHEDULE_FSM:
        return basicSetScheduleFsm(null, msgs);
      case CalPackage.AST_ACTOR__SCHEDULE_REG_EXP:
        return basicSetScheduleRegExp(null, msgs);
      case CalPackage.AST_ACTOR__PRIORITIES:
        return ((InternalEList<?>)getPriorities()).basicRemove(otherEnd, msgs);
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
      case CalPackage.AST_ACTOR__PARAMETERS:
        return getParameters();
      case CalPackage.AST_ACTOR__INPUTS:
        return getInputs();
      case CalPackage.AST_ACTOR__OUTPUTS:
        return getOutputs();
      case CalPackage.AST_ACTOR__FUNCTIONS:
        return getFunctions();
      case CalPackage.AST_ACTOR__PROCEDURES:
        return getProcedures();
      case CalPackage.AST_ACTOR__ACTIONS:
        return getActions();
      case CalPackage.AST_ACTOR__INITIALIZES:
        return getInitializes();
      case CalPackage.AST_ACTOR__STATE_VARIABLES:
        return getStateVariables();
      case CalPackage.AST_ACTOR__LOCAL_FSMS:
        return getLocalFsms();
      case CalPackage.AST_ACTOR__SCHEDULE_FSM:
        return getScheduleFsm();
      case CalPackage.AST_ACTOR__SCHEDULE_REG_EXP:
        return getScheduleRegExp();
      case CalPackage.AST_ACTOR__PRIORITIES:
        return getPriorities();
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
      case CalPackage.AST_ACTOR__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends Variable>)newValue);
        return;
      case CalPackage.AST_ACTOR__INPUTS:
        getInputs().clear();
        getInputs().addAll((Collection<? extends AstPort>)newValue);
        return;
      case CalPackage.AST_ACTOR__OUTPUTS:
        getOutputs().clear();
        getOutputs().addAll((Collection<? extends AstPort>)newValue);
        return;
      case CalPackage.AST_ACTOR__FUNCTIONS:
        getFunctions().clear();
        getFunctions().addAll((Collection<? extends Function>)newValue);
        return;
      case CalPackage.AST_ACTOR__PROCEDURES:
        getProcedures().clear();
        getProcedures().addAll((Collection<? extends AstProcedure>)newValue);
        return;
      case CalPackage.AST_ACTOR__ACTIONS:
        getActions().clear();
        getActions().addAll((Collection<? extends AstAction>)newValue);
        return;
      case CalPackage.AST_ACTOR__INITIALIZES:
        getInitializes().clear();
        getInitializes().addAll((Collection<? extends AstAction>)newValue);
        return;
      case CalPackage.AST_ACTOR__STATE_VARIABLES:
        getStateVariables().clear();
        getStateVariables().addAll((Collection<? extends Variable>)newValue);
        return;
      case CalPackage.AST_ACTOR__LOCAL_FSMS:
        getLocalFsms().clear();
        getLocalFsms().addAll((Collection<? extends LocalFsm>)newValue);
        return;
      case CalPackage.AST_ACTOR__SCHEDULE_FSM:
        setScheduleFsm((ScheduleFsm)newValue);
        return;
      case CalPackage.AST_ACTOR__SCHEDULE_REG_EXP:
        setScheduleRegExp((RegExp)newValue);
        return;
      case CalPackage.AST_ACTOR__PRIORITIES:
        getPriorities().clear();
        getPriorities().addAll((Collection<? extends Priority>)newValue);
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
      case CalPackage.AST_ACTOR__PARAMETERS:
        getParameters().clear();
        return;
      case CalPackage.AST_ACTOR__INPUTS:
        getInputs().clear();
        return;
      case CalPackage.AST_ACTOR__OUTPUTS:
        getOutputs().clear();
        return;
      case CalPackage.AST_ACTOR__FUNCTIONS:
        getFunctions().clear();
        return;
      case CalPackage.AST_ACTOR__PROCEDURES:
        getProcedures().clear();
        return;
      case CalPackage.AST_ACTOR__ACTIONS:
        getActions().clear();
        return;
      case CalPackage.AST_ACTOR__INITIALIZES:
        getInitializes().clear();
        return;
      case CalPackage.AST_ACTOR__STATE_VARIABLES:
        getStateVariables().clear();
        return;
      case CalPackage.AST_ACTOR__LOCAL_FSMS:
        getLocalFsms().clear();
        return;
      case CalPackage.AST_ACTOR__SCHEDULE_FSM:
        setScheduleFsm((ScheduleFsm)null);
        return;
      case CalPackage.AST_ACTOR__SCHEDULE_REG_EXP:
        setScheduleRegExp((RegExp)null);
        return;
      case CalPackage.AST_ACTOR__PRIORITIES:
        getPriorities().clear();
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
      case CalPackage.AST_ACTOR__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
      case CalPackage.AST_ACTOR__INPUTS:
        return inputs != null && !inputs.isEmpty();
      case CalPackage.AST_ACTOR__OUTPUTS:
        return outputs != null && !outputs.isEmpty();
      case CalPackage.AST_ACTOR__FUNCTIONS:
        return functions != null && !functions.isEmpty();
      case CalPackage.AST_ACTOR__PROCEDURES:
        return procedures != null && !procedures.isEmpty();
      case CalPackage.AST_ACTOR__ACTIONS:
        return actions != null && !actions.isEmpty();
      case CalPackage.AST_ACTOR__INITIALIZES:
        return initializes != null && !initializes.isEmpty();
      case CalPackage.AST_ACTOR__STATE_VARIABLES:
        return stateVariables != null && !stateVariables.isEmpty();
      case CalPackage.AST_ACTOR__LOCAL_FSMS:
        return localFsms != null && !localFsms.isEmpty();
      case CalPackage.AST_ACTOR__SCHEDULE_FSM:
        return scheduleFsm != null;
      case CalPackage.AST_ACTOR__SCHEDULE_REG_EXP:
        return scheduleRegExp != null;
      case CalPackage.AST_ACTOR__PRIORITIES:
        return priorities != null && !priorities.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //AstActorImpl
