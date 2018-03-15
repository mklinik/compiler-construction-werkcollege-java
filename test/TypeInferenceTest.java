import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import parser.AstExpr;
import parser.AstNode;
import parser.Parser;

import typechecker.Type;
import typechecker.TypeBool;
import typechecker.TypeFunction;
import typechecker.TypeInt;
import typechecker.TypeVariable;
import typechecker.Typeinference;

public class TypeInferenceTest {
	private Typeinference tc = null;

	@Before
	public void setUp() throws Exception {
		tc = new Typeinference();
	}

	private void assertTypecheckSuccess() {
		assertTrue(tc.getAllErrors(), tc.getAllErrors().length() == 0);
	}

	private void assertTypecheckFailure(String message) {
		assertEquals(message, tc.getAllErrors());
	}

	private AstNode typecheckExpr(String input) {
		Parser p = new Parser(input);
		AstExpr expr = p.pExpr();
		tc.typeinference(expr);
		return expr;
	}

	@Test
	public void testSubstitutionInt() {
		Type t = new TypeInt();
		HashMap<String, Type> subst = new HashMap<String, Type>();
		subst.put("a", new TypeInt());
		assertEquals(new TypeInt(), t.applySubstitution(subst));
	}

	@Test
	public void testSubstitutionVar() {
		Type t = new TypeVariable("a");
		HashMap<String, Type> subst = new HashMap<String, Type>();
		subst.put("a", new TypeBool());
		assertEquals(new TypeBool(), t.applySubstitution(subst));
	}

	@Test
	public void testSubstitutionFunction() {
		Type t = new TypeFunction(new TypeVariable("a"), new TypeVariable("b"));
		HashMap<String, Type> subst = new HashMap<String, Type>();
		subst.put("a", new TypeBool());
		subst.put("b", new TypeInt());
		assertEquals(new TypeFunction(new TypeBool(), new TypeInt()),
				t.applySubstitution(subst));
	}

	@Test
	public void testIntegerConstant() {
		AstNode e = typecheckExpr("5");
		assertTypecheckSuccess();
		assertEquals(new TypeInt(), e.getType());
	}

	@Test
	public void testPlusWithConstants() {
		AstNode e = typecheckExpr("5 + 3");
		assertTypecheckSuccess();
		assertEquals(new TypeInt(), e.getType());
	}

	@Test
	public void testPlusWithTypeError() {
		typecheckExpr("5 + True");
		assertTypecheckFailure("cannot unify types\n");
	}

	@Test
	public void testIdentityFunction() {
		AstNode e = typecheckExpr("fun x . x");
		assertTypecheckSuccess();
		assertEquals(new TypeFunction(new TypeVariable("a0"), new TypeVariable(
				"a0")), e.getType());
	}

	@Test
	public void testConstantFunction() {
		AstNode e = typecheckExpr("fun x . fun y . x");
		assertTypecheckSuccess();
		assertEquals(new TypeFunction(new TypeVariable("a0"), new TypeFunction(
				new TypeVariable("a1"), new TypeVariable("a0"))), e.getType());
	}
	
	@Test
	public void testAdditionFunction() {
		AstNode e = typecheckExpr("fun x . fun y . x + y");
		assertTypecheckSuccess();
		assertEquals(new TypeFunction(new TypeInt(), new TypeFunction(
				new TypeInt(), new TypeInt())), e.getType());
	}

}
