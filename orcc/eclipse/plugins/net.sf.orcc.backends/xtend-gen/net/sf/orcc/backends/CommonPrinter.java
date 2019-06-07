package net.sf.orcc.backends;

import com.google.common.collect.Iterables;
import java.util.List;
import java.util.Map;
import net.sf.orcc.OrccLaunchConstants;
import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.util.OrccLogger;
import org.apache.commons.lang.WordUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Define commons methods for all backends printers
 */
@SuppressWarnings("all")
public abstract class CommonPrinter extends AbstractIrVisitor<CharSequence> {
  protected int precedence = Integer.MAX_VALUE;
  
  protected int branch = 0;
  
  protected int fifoSize;
  
  public CommonPrinter() {
    super(true);
  }
  
  public void setOptions(final Map<String, Object> options) {
    boolean _containsKey = options.containsKey(OrccLaunchConstants.FIFO_SIZE);
    if (_containsKey) {
      Object _get = options.get(OrccLaunchConstants.FIFO_SIZE);
      this.fifoSize = (((Integer) _get)).intValue();
    } else {
      this.fifoSize = OrccLaunchConstants.DEFAULT_FIFO_SIZE;
    }
    return;
  }
  
  /**
   * Print indexes list when accessing to an array (ex : <code>[INDEX][2][i + 1]</code>)
   * or when declaring it. If list is empty, return an empty string.
   */
  protected String printArrayIndexes(final List<Expression> exprList) {
    final Function1<Expression, CharSequence> _function = new Function1<Expression, CharSequence>() {
      @Override
      public CharSequence apply(final Expression it) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("[");
        CharSequence _doSwitch = CommonPrinter.this.doSwitch(it);
        _builder.append(_doSwitch);
        _builder.append("]");
        return _builder.toString();
      }
    };
    return IterableExtensions.<Expression>join(exprList, "", _function);
  }
  
  /**
   * Split the string into lines with a max of n characters
   */
  protected String wrap(final CharSequence charSeq, final int n) {
    return WordUtils.wrap(charSeq.toString(), n);
  }
  
  protected String wrap(final CharSequence charSeq) {
    return this.wrap(charSeq, 80);
  }
  
  /**
   * Check if the given connection has a specified BUFFER_SIZE.
   */
  protected boolean hasSpecificSize(final Connection connection) {
    Integer _size = null;
    if (connection!=null) {
      _size=connection.getSize();
    }
    return (_size != null);
  }
  
  /**
   * Return the specified size of the connection, or the
   * default size for all other connections
   */
  protected int safeSize(final Connection connection) {
    int _xifexpression = (int) 0;
    boolean _hasSpecificSize = this.hasSpecificSize(connection);
    if (_hasSpecificSize) {
      _xifexpression = connection.getSize().intValue();
    } else {
      _xifexpression = this.fifoSize;
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseTypeBool(final TypeBool type) {
    return "bool";
  }
  
  @Override
  public CharSequence caseTypeFloat(final TypeFloat type) {
    String _xifexpression = null;
    boolean _isHalf = type.isHalf();
    if (_isHalf) {
      _xifexpression = "half";
    } else {
      String _xifexpression_1 = null;
      boolean _isDouble = type.isDouble();
      if (_isDouble) {
        _xifexpression_1 = "double";
      } else {
        _xifexpression_1 = "float";
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  @Override
  public CharSequence caseTypeInt(final TypeInt type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("int(size=");
    int _size = type.getSize();
    _builder.append(_size);
    _builder.append(");");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeUint(final TypeUint type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("uint(size=");
    int _size = type.getSize();
    _builder.append(_size);
    _builder.append(");");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeString(final TypeString type) {
    return "String";
  }
  
  @Override
  public CharSequence caseTypeList(final TypeList type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("List(type:");
    CharSequence _doSwitch = this.doSwitch(type.getType());
    _builder.append(_doSwitch);
    _builder.append(", size=");
    int _size = type.getSize();
    _builder.append(_size);
    _builder.append(")");
    return _builder;
  }
  
  @Override
  public CharSequence caseTypeVoid(final TypeVoid object) {
    return "void";
  }
  
  /**
   * Print expression after saving informations to correctly
   * add parenthesis everywhere it is needed
   */
  protected CharSequence printExpr(final Expression expr, final int newPrecedence, final int newBranch) {
    final int oldBranch = this.branch;
    final int oldPrecedence = this.precedence;
    this.branch = newBranch;
    this.precedence = newPrecedence;
    final CharSequence resultingExpr = this.doSwitch(expr);
    this.precedence = oldPrecedence;
    this.branch = oldBranch;
    return resultingExpr;
  }
  
  protected String stringRepresentation(final OpBinary op) {
    return op.getText();
  }
  
  protected String stringRepresentation(final OpUnary op) {
    return op.getText();
  }
  
  @Override
  public CharSequence caseExprBinary(final ExprBinary expr) {
    CharSequence _xblockexpression = null;
    {
      final OpBinary op = expr.getOp();
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _printExpr = this.printExpr(expr.getE1(), op.getPrecedence(), 0);
      _builder.append(_printExpr);
      _builder.append(" ");
      String _stringRepresentation = this.stringRepresentation(op);
      _builder.append(_stringRepresentation);
      _builder.append(" ");
      CharSequence _printExpr_1 = this.printExpr(expr.getE2(), op.getPrecedence(), 1);
      _builder.append(_printExpr_1);
      final String resultingExpr = _builder.toString();
      CharSequence _xifexpression = null;
      boolean _needsParentheses = op.needsParentheses(this.precedence, this.branch);
      if (_needsParentheses) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("(");
        _builder_1.append(resultingExpr);
        _builder_1.append(")");
        _xifexpression = _builder_1;
      } else {
        _xifexpression = resultingExpr;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  @Override
  public CharSequence caseExprUnary(final ExprUnary expr) {
    StringConcatenation _builder = new StringConcatenation();
    String _stringRepresentation = this.stringRepresentation(expr.getOp());
    _builder.append(_stringRepresentation);
    CharSequence _printExpr = this.printExpr(expr.getExpr(), Integer.MIN_VALUE, this.branch);
    _builder.append(_printExpr);
    return _builder;
  }
  
  @Override
  public CharSequence caseExprFloat(final ExprFloat expr) {
    return String.valueOf(expr.getValue());
  }
  
  @Override
  public CharSequence caseExprInt(final ExprInt expr) {
    return String.valueOf(expr.getValue());
  }
  
  @Override
  public CharSequence caseExprBool(final ExprBool expr) {
    return String.valueOf(expr.isValue());
  }
  
  @Override
  public CharSequence caseExprVar(final ExprVar expr) {
    return expr.getUse().getVariable().getName();
  }
  
  @Override
  public CharSequence caseExprList(final ExprList expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    final Function1<Expression, CharSequence> _function = new Function1<Expression, CharSequence>() {
      @Override
      public CharSequence apply(final Expression it) {
        return CommonPrinter.this.printExpr(it, Integer.MAX_VALUE, 0);
      }
    };
    String _join = IterableExtensions.<Expression>join(expr.getValue(), ", ", _function);
    _builder.append(_join);
    _builder.append("}");
    return _builder;
  }
  
  @Override
  public CharSequence caseExprString(final ExprString expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"");
    String _valueOf = String.valueOf(expr.getValue());
    _builder.append(_valueOf);
    _builder.append("\"");
    return _builder;
  }
  
  /**
   * The default case is useful to support code generation
   * for specific instructions
   */
  @Override
  public CharSequence defaultCase(final EObject object) {
    String _xifexpression = null;
    if ((object instanceof BlockFor)) {
      _xifexpression = this.caseBlockFor(((BlockFor) object));
    } else {
      String _xifexpression_1 = null;
      if ((object instanceof InstTernary)) {
        _xifexpression_1 = this.caseInstTernary(((InstTernary) object));
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  /**
   * Print specific BlockFor object.
   * @param block For block
   * @see #defaultCase(EObject object)
   */
  public String caseBlockFor(final BlockFor block) {
    OrccLogger.warnln(
      ("This application contains for loop, which is not yet supported. " + 
        "Please override caseBlockFor() method in the backend you are using."));
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  /**
   * Print specific InstTernary object.
   * @param inst The ternary instruction
   * @see #defaultCase(EObject object)
   */
  public String caseInstTernary(final InstTernary inst) {
    OrccLogger.warnln(
      ("This application contains ternary instructions, which is not yet supported. " + 
        "Please override caseInstTernary() method in the backend you are using."));
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  /**
   * Filter ports, and return only thus which are not native as a list
   */
  protected Iterable<? extends Port> getNotNative(final Iterable<? extends Port> ports) {
    final Function1<Port, Boolean> _function = new Function1<Port, Boolean>() {
      @Override
      public Boolean apply(final Port it) {
        boolean _isNative = it.isNative();
        return Boolean.valueOf((!_isNative));
      }
    };
    return IterableExtensions.filter(ports, _function);
  }
  
  /**
   * Filter procedures, and return only thus which are not native as a list
   */
  protected Iterable<? extends Procedure> getNotNativeProcs(final Iterable<? extends Procedure> procs) {
    final Function1<Procedure, Boolean> _function = new Function1<Procedure, Boolean>() {
      @Override
      public Boolean apply(final Procedure it) {
        boolean _isNative = it.isNative();
        return Boolean.valueOf((!_isNative));
      }
    };
    return IterableExtensions.filter(procs, _function);
  }
  
  /**
   * Filter vertex, return only Actor's Instances.
   */
  protected Iterable<Instance> getActorInstances(final List<Vertex> vertices) {
    final Function1<Instance, Boolean> _function = new Function1<Instance, Boolean>() {
      @Override
      public Boolean apply(final Instance it) {
        return Boolean.valueOf(it.isActor());
      }
    };
    return IterableExtensions.<Instance>filter(Iterables.<Instance>filter(vertices, Instance.class), _function);
  }
}
