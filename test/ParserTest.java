import static org.junit.Assert.*;

import org.junit.Test;

import ast.AstExprInteger;
import ast.AstNode;
import ast.Parser;


public class ParserTest {

	@Test
	public void testInteger() {
		Parser p = new Parser("5");
		AstNode ast = p.pExpr();
		assertEquals(new AstExprInteger(5), ast);
	}

}
