import static org.junit.Assert.*;

import org.junit.Test;

import parser.AstExpr;
import parser.AstNode;
import parser.Parser;
import typechecker.TypeInt;
import typechecker.Typechecker;


public class TypecheckerTest {
	
	private AstNode typecheckExpr(String input)
	{
		Parser p = new Parser(input);
		AstExpr expr = p.pExpr();
		Typechecker t = new Typechecker();
		t.typecheck(expr);
		return expr;
	}

	@Test
	public void test() {
		AstNode e = typecheckExpr("5");
		assertEquals(new TypeInt(), e.getType());
	}

}
