import static org.junit.Assert.*;
import lex.TokenType;

import org.junit.Test;

import ast.*;

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

}
