/*
 * generated by Xtext
 */
package net.sf.orcc.cal.serializer;

import com.google.inject.Inject;
import java.util.List;
import net.sf.orcc.cal.services.CalGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class CalSyntacticSequencer extends AbstractSyntacticSequencer {

	protected CalGrammarAccess grammarAccess;
	protected AbstractElementAlias match_AstAction_DoKeyword_9_0_q;
	protected AbstractElementAlias match_AstProcedure_BeginKeyword_6_1_q;
	protected AbstractElementAlias match_ExpressionPostfix_LeftParenthesisKeyword_6_0_a;
	protected AbstractElementAlias match_ExpressionPostfix_LeftParenthesisKeyword_6_0_p;
	protected AbstractElementAlias match_Initialize_DoKeyword_8_0_q;
	protected AbstractElementAlias match_RegExpGrouping_LeftParenthesisKeyword_1_0_a;
	protected AbstractElementAlias match_RegExpGrouping_LeftParenthesisKeyword_1_0_p;
	protected AbstractElementAlias match_StatementIf_ElseKeyword_6_0_q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (CalGrammarAccess) access;
		match_AstAction_DoKeyword_9_0_q = new TokenAlias(false, true, grammarAccess.getAstActionAccess().getDoKeyword_9_0());
		match_AstProcedure_BeginKeyword_6_1_q = new TokenAlias(false, true, grammarAccess.getAstProcedureAccess().getBeginKeyword_6_1());
		match_ExpressionPostfix_LeftParenthesisKeyword_6_0_a = new TokenAlias(true, true, grammarAccess.getExpressionPostfixAccess().getLeftParenthesisKeyword_6_0());
		match_ExpressionPostfix_LeftParenthesisKeyword_6_0_p = new TokenAlias(true, false, grammarAccess.getExpressionPostfixAccess().getLeftParenthesisKeyword_6_0());
		match_Initialize_DoKeyword_8_0_q = new TokenAlias(false, true, grammarAccess.getInitializeAccess().getDoKeyword_8_0());
		match_RegExpGrouping_LeftParenthesisKeyword_1_0_a = new TokenAlias(true, true, grammarAccess.getRegExpGroupingAccess().getLeftParenthesisKeyword_1_0());
		match_RegExpGrouping_LeftParenthesisKeyword_1_0_p = new TokenAlias(true, false, grammarAccess.getRegExpGroupingAccess().getLeftParenthesisKeyword_1_0());
		match_StatementIf_ElseKeyword_6_0_q = new TokenAlias(false, true, grammarAccess.getStatementIfAccess().getElseKeyword_6_0());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_AstAction_DoKeyword_9_0_q.equals(syntax))
				emit_AstAction_DoKeyword_9_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_AstProcedure_BeginKeyword_6_1_q.equals(syntax))
				emit_AstProcedure_BeginKeyword_6_1_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ExpressionPostfix_LeftParenthesisKeyword_6_0_a.equals(syntax))
				emit_ExpressionPostfix_LeftParenthesisKeyword_6_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_ExpressionPostfix_LeftParenthesisKeyword_6_0_p.equals(syntax))
				emit_ExpressionPostfix_LeftParenthesisKeyword_6_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_Initialize_DoKeyword_8_0_q.equals(syntax))
				emit_Initialize_DoKeyword_8_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_RegExpGrouping_LeftParenthesisKeyword_1_0_a.equals(syntax))
				emit_RegExpGrouping_LeftParenthesisKeyword_1_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_RegExpGrouping_LeftParenthesisKeyword_1_0_p.equals(syntax))
				emit_RegExpGrouping_LeftParenthesisKeyword_1_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_StatementIf_ElseKeyword_6_0_q.equals(syntax))
				emit_StatementIf_ElseKeyword_6_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     'do'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) 'action' '==>' (ambiguity) 'end' (rule start)
	 *     annotations+=AstAnnotation 'action' '==>' (ambiguity) 'end' (rule end)
	 *     guard=Guard (ambiguity) 'end' (rule end)
	 *     inputs+=InputPattern '==>' (ambiguity) 'end' (rule end)
	 *     outputs+=OutputPattern (ambiguity) 'end' (rule end)
	 *     tag=AstTag ':' 'action' '==>' (ambiguity) 'end' (rule end)
	 *     variables+=ValuedVariableDeclaration (ambiguity) 'end' (rule end)
	 */
	protected void emit_AstAction_DoKeyword_9_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'begin'?
	 *
	 * This ambiguous syntax occurs at:
	 *     name=ID '(' ')' (ambiguity) 'end' (rule end)
	 *     parameters+=VariableDeclaration ')' (ambiguity) 'end' (rule end)
	 */
	protected void emit_AstProcedure_BeginKeyword_6_1_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) '[' expressions+=AstExpression
	 *     (rule start) (ambiguity) 'if' condition=AstExpression
	 *     (rule start) (ambiguity) annotations+=AstAnnotation
	 *     (rule start) (ambiguity) function=[Function|QualifiedName]
	 *     (rule start) (ambiguity) source=VariableReference
	 *     (rule start) (ambiguity) unaryOperator='#'
	 *     (rule start) (ambiguity) unaryOperator='-'
	 *     (rule start) (ambiguity) unaryOperator='not'
	 *     (rule start) (ambiguity) unaryOperator='~'
	 *     (rule start) (ambiguity) value=BOOL
	 *     (rule start) (ambiguity) value=DECIMAL
	 *     (rule start) (ambiguity) value=HEX
	 *     (rule start) (ambiguity) value=OCTAL
	 *     (rule start) (ambiguity) value=REAL
	 *     (rule start) (ambiguity) value=STRING
	 *     (rule start) (ambiguity) value=VariableReference
	 *     (rule start) (ambiguity) {ExpressionBinary.left=}
	 */
	protected void emit_ExpressionPostfix_LeftParenthesisKeyword_6_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) unaryOperator='#'
	 *     (rule start) (ambiguity) unaryOperator='-'
	 *     (rule start) (ambiguity) unaryOperator='not'
	 *     (rule start) (ambiguity) unaryOperator='~'
	 *     (rule start) (ambiguity) {ExpressionBinary.left=}
	 */
	protected void emit_ExpressionPostfix_LeftParenthesisKeyword_6_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'do'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) 'initialize' '==>' (ambiguity) 'end' (rule start)
	 *     annotations+=AstAnnotation 'initialize' '==>' (ambiguity) 'end' (rule end)
	 *     guard=Guard (ambiguity) 'end' (rule end)
	 *     outputs+=OutputPattern (ambiguity) 'end' (rule end)
	 *     tag=AstTag ':' 'initialize' '==>' (ambiguity) 'end' (rule end)
	 *     variables+=ValuedVariableDeclaration (ambiguity) 'end' (rule end)
	 */
	protected void emit_Initialize_DoKeyword_8_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) tag=AstTag
	 *     (rule start) (ambiguity) {RegExpBinary.left=}
	 *     (rule start) (ambiguity) {RegExpUnary.child=}
	 */
	protected void emit_RegExpGrouping_LeftParenthesisKeyword_1_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) {RegExpBinary.left=}
	 *     (rule start) (ambiguity) {RegExpUnary.child=}
	 */
	protected void emit_RegExpGrouping_LeftParenthesisKeyword_1_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'else'?
	 *
	 * This ambiguous syntax occurs at:
	 *     condition=AstExpression 'then' (ambiguity) 'end' (rule end)
	 *     elsifs+=StatementElsif (ambiguity) 'end' (rule end)
	 *     then+=Statement (ambiguity) 'end' (rule end)
	 */
	protected void emit_StatementIf_ElseKeyword_6_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
