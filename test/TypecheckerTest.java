import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import parser.AstExpr;
import parser.AstNode;
import parser.Parser;
import typechecker.TypeBool;
import typechecker.TypeInt;
import typechecker.Typechecker;

public class TypecheckerTest {
	private Typechecker tc = null;
	
	@Before
	public void setUp() throws Exception {
		tc = new Typechecker();
	}

	private AstNode typecheckExpr(String input) {
		Parser p = new Parser(input);
		AstExpr expr = p.pExpr();
		boolean success = tc.typecheck(expr);
		// TODO this is bad if we want to test that the typechecker gives correct error messages for ill-typed programs.
		assertTrue(tc.getAllErrors(), success);
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

	@Test
	public void testBooleanConstantTrue() {
		AstNode eTrue = typecheckExpr("True");
		AstNode eFalse = typecheckExpr("False");
		assertEquals(new TypeBool(), eTrue.getType());
		assertEquals(new TypeBool(), eFalse.getType());
	}
	
	@Test
	public void testPlus() {
		AstNode e = typecheckExpr("5 + 3");
		assertEquals(new TypeInt(), e.getType());
	}
	
	@Test
	public void testLessThan() {
		AstNode e = typecheckExpr("5 < 3");
		assertEquals(new TypeBool(), e.getType());
	}

}
