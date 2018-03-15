import static org.junit.Assert.*;
import lexer.TokenType;

import org.junit.Test;

import parser.*;
import typechecker.TypeBool;

public class ParserTest {

	@Test
	public void testInteger() {
		Parser p = new Parser("5");
		AstExpr ast = p.pExpr();
		assertEquals(new AstExprInteger(5), ast);
	}

	@Test
	public void testSimpleAddition() {
		Parser p = new Parser("1+2");
		AstExpr ast = p.pExpr();
		assertEquals(new AstExprBinOp(new AstExprInteger(1),
				TokenType.TOK_PLUS, new AstExprInteger(2)), ast);
	}

	@Test
	public void testMultipleAddition() {
		Parser p = new Parser("1+2+3");
		AstExpr ast = p.pExpr();
		assertEquals(new AstExprBinOp(new AstExprBinOp(new AstExprInteger(1),
				TokenType.TOK_PLUS, new AstExprInteger(2)), TokenType.TOK_PLUS,
				new AstExprInteger(3)), ast);
	}

	@Test
	public void testMixedAdditionSubtraction() {
		Parser p = new Parser("1+2-3");
		AstExpr ast = p.pExpr();
		assertEquals(new AstExprBinOp(new AstExprBinOp(new AstExprInteger(1),
				TokenType.TOK_PLUS, new AstExprInteger(2)),
				TokenType.TOK_MINUS, new AstExprInteger(3)), ast);
	}

	@Test
	public void testMixedAdditionMultiplication1() {
		Parser p = new Parser("1*2+3");
		AstExpr ast = p.pExpr();
		assertEquals(new AstExprBinOp(new AstExprBinOp(new AstExprInteger(1),
				TokenType.TOK_MULT, new AstExprInteger(2)), TokenType.TOK_PLUS,
				new AstExprInteger(3)), ast);
	}

	@Test
	public void testMixedAdditionMultiplication2() {
		Parser p = new Parser("1+2*3");
		AstExpr ast = p.pExpr();
		assertEquals(new AstExprBinOp(new AstExprInteger(1),
				TokenType.TOK_PLUS, new AstExprBinOp(new AstExprInteger(2),
						TokenType.TOK_MULT, new AstExprInteger(3))), ast);
	}

	@Test
	public void testMultiplicationLeftAssociative() {
		Parser p = new Parser("1*2*3");
		AstExpr ast = p.pExpr();
		assertEquals(new AstExprBinOp(new AstExprBinOp(new AstExprInteger(1),
				TokenType.TOK_MULT, new AstExprInteger(2)), TokenType.TOK_MULT,
				new AstExprInteger(3)), ast);
	}

	@Test
	public void testBooleanTrue() {
		Parser p = new Parser("True");
		AstExpr ast = p.pExpr();
		assertEquals(new AstExprBool(true), ast);
	}

	@Test
	public void testLessThan() {
		Parser p = new Parser("42 < 100");
		AstExpr ast = p.pExpr();
		assertEquals(new AstExprBinOp(new AstExprInteger(42),
				TokenType.TOK_LESS_THAN, new AstExprInteger(100)), ast);
	}

	@Test
	public void testTypeInt() {
		Parser p = new Parser("Int");
		AstType t = p.pType();
		assertEquals(new AstTypeInt(), t);
	}

	@Test
	public void testLetBinding() {
		Parser p = new Parser("fun Bool foo . 5");
		AstExpr e = p.pExpr();
		assertEquals(new AstAbstraction(new AstTypeBool(), "foo",
				new AstExprInteger(5)), e);
	}
}
