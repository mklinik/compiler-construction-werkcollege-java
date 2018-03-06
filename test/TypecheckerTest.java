import static org.junit.Assert.*;

import org.junit.Test;

import parser.AstExpr;
import parser.AstNode;
import parser.Parser;
import typechecker.TypeBool;
import typechecker.TypeInt;
import typechecker.Typechecker;

public class TypecheckerTest {

	private AstNode typecheckExpr(String input) {
		Parser p = new Parser(input);
		AstExpr expr = p.pExpr();
		Typechecker t = new Typechecker();
		t.typecheck(expr);
		return expr;
	}

	@Test
	public void testCompareTypes() {
		assertEquals(new TypeInt(), new TypeInt());
		assertEquals(new TypeBool(), new TypeBool());
		assertNotEquals(new TypeInt(), new TypeBool());
	}

	@Test
	public void testIntegerConstant() {
		AstNode e = typecheckExpr("5");
		assertEquals(new TypeInt(), e.getType());
	}

}
