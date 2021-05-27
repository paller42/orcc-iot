package hu.sze.orcciot.backends.c.STM32F4

import java.util.LinkedList
import java.util.List
import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.ir.Arg
import net.sf.orcc.ir.ArgByRef
import net.sf.orcc.ir.ArgByVal
import net.sf.orcc.ir.ExprBinary
import net.sf.orcc.ir.ExprBool
import net.sf.orcc.ir.ExprInt
import net.sf.orcc.ir.ExprString
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.Instruction
import net.sf.orcc.ir.OpBinary
import net.sf.orcc.ir.Type
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeFloat
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeString
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.TypeVoid
import net.sf.orcc.ir.Var
import net.sf.orcc.util.Attributable
import net.sf.orcc.util.util.EcoreHelper
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.OrccRuntimeException

/*
 * Default C Printer
 *  
 * @author Antoine Lorence
 * 
 */
abstract class CTemplate extends CommonPrinter {

	/////////////////////////////////
	// Expressions
	/////////////////////////////////
	override caseExprBinary(ExprBinary expr) {
		OrccLogger::warnln( "caseExprBinary: type1: "+expr.e1.type+"; type2: "+expr.e2.type+"; op: "+expr.op.stringRepresentation )
		val op = expr.op
		if( expr.type.string ) {
			if( expr.op.stringRepresentation == "+" ) {
			'''
				concat_str( «printStringOperand(expr.e1,0)», «printStringOperand(expr.e2,1)» )
			'''
			} else
				throw new OrccRuntimeException( "Only + operator is supported on String operands" )
		} else {
			val container = EcoreHelper.getContainerOfType(expr, typeof(Expression))
			var nextPrec = if (op == OpBinary::SHIFT_LEFT || op == OpBinary::SHIFT_RIGHT) {

				// special case, for shifts always put parentheses because compilers
				// often issue warnings
					Integer::MIN_VALUE;
				} else {
					op.precedence;
				}

			val resultingExpr = '''«expr.e1.printExpr(nextPrec, 0)» «op.stringRepresentation» «expr.e2.printExpr(nextPrec, 1)»'''

			if (op.needsParentheses(precedence, branch) || (container !== null && op.logical)) {
				'''(«resultingExpr»)'''
			} else {
				resultingExpr
			}
		}
	}

	override caseExprBool(ExprBool object) {
		if(object.value) "1" else "0"
	}

	override caseExprInt(ExprInt object) {
		val longVal = object.value.longValue
		if (longVal < Integer::MIN_VALUE || longVal > Integer::MAX_VALUE) {
			'''«longVal»L'''
		} else {
			'''«longVal»'''
		}
	}

	override protected stringRepresentation(OpBinary op) {
		if (op == OpBinary::DIV_INT)
			"/"
		else
			super.stringRepresentation(op)
	}

	/////////////////////////////////
	// Types
	/////////////////////////////////
	override caseTypeBool(TypeBool type) '''i32'''

	override caseTypeInt(TypeInt type) '''i«type.size»'''

	override caseTypeUint(TypeUint type) '''u«type.size»'''

	override caseTypeFloat(TypeFloat type) {
		if (type.size == 64) '''double''' else '''float'''
	}

	override caseTypeString(TypeString type) '''string'''

	override caseTypeVoid(TypeVoid type) '''void'''

	override caseTypeList(TypeList typeList)
		//TODO : print sizes
	'''«typeList.innermostType.doSwitch»'''

	def protected declare(Var variable) '''«variable.type.doSwitch» «variable.name»«variable.type.dimensionsExpr.
		printArrayIndexes»'''

	/////////////////////////////////
	// Helpers
	/////////////////////////////////
	/**
	  * Print for a type, the corresponding formatted text to
	  * use inside a printf() call.
	  * @param type the type to print
	  * @return printf() type format
	  */
	def protected printfFormat(Type type) {
		switch type {
			case type.bool: "i"
			case type.float: "f"
			case type.int && (type as TypeInt).long: "lli"
			case type.int: "i"
			case type.uint && (type as TypeUint).long: "llu"
			case type.uint: "u"
			case type.list: "p"
			case type.string: "s"
			case type.void: "p"
		}
	}

	def protected printfArgs(List<Arg> args) {
		val finalArgs = new LinkedList<CharSequence>

		val printfPattern = new StringBuilder
		printfPattern.append('"')

		for (arg : args) {

			if (arg.byRef) {
				printfPattern.append("%" + (arg as ArgByRef).use.variable.type.printfFormat)
				finalArgs.add((arg as ArgByRef).use.variable.name)
			} else if ((arg as ArgByVal).value.exprString) {
				printfPattern.append(((arg as ArgByVal).value as ExprString).value)
			} else {
				printfPattern.append("%" + (arg as ArgByVal).value.type.printfFormat)
				finalArgs.add((arg as ArgByVal).value.doSwitch)
			}

		}
		printfPattern.append('"')
		finalArgs.addFirst(printfPattern.toString)
		return finalArgs
	}

	/**
	 * Print attributes for an Attributable object.
	 * Do nothing on C backend, but is used by others.
	 * @param object the object
	 * @return comment block
	 */
	def protected printAttributes(Attributable object) ''''''

	/**
	 * This helper return a representation of a given instruction without
	 * trailing whitespace and semicolon
	 */
	def protected toExpression(Instruction instruction) {
		instruction.doSwitch.toString.replaceAll("([^;]+);(\\s+)?", "$1")
	}
	
	def protected printNativeLibHeaders(String linkNativeLibHeaders) {
	'''
		// -- Native lib headers
		«FOR header : linkNativeLibHeaders.split(";")»
			#include "«header.trim()»"
		«ENDFOR»
		
	'''
	}
	
	def printStringOperand(Expression expr,int pos) {
		if(expr.type.string)
		'''string_operand_string(«expr.printExpr(Integer::MIN_VALUE, pos)»)'''
		else
		if(expr.type.int)
		'''string_operand_int(«expr.printExpr(Integer::MIN_VALUE, pos)»)'''
		else
		if(expr.type.float)
		'''string_operand_float(«expr.printExpr(Integer::MIN_VALUE, pos)»)'''
		else
		if(expr.type.uint)
		'''string_operand_uint(«expr.printExpr(Integer::MIN_VALUE, pos)»)'''
		else
			throw new OrccRuntimeException( "Unsupported type for string concatenation: "+expr.type )
	}
}
