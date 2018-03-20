import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import parser.AstExpr;
import parser.AstNode;
import parser.Parser;
import typechecker.Substitution;
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
	public void testUnificationFails()
	{
		// Example from the slides where unification fails
		Type t1 = new TypeFunction(new TypeFunction(new TypeVariable("a"), new TypeInt()), new TypeVariable("a"));
		Type t2 = new TypeFunction(new TypeFunction(new TypeBool(), new TypeInt()), new TypeInt());
		try{
			Typeinference.unify(t1, t2);
			assertTrue("unify must throw an exception", false);
		}
		catch(Error e)
		{
			assertEquals("cannot unify types", e.getMessage());
		}
	}
	
	@Test
	public void testUnificationSucceeds()
	{
		// Same as before, but now two different type variables make unification succeed
		Type t1 = new TypeFunction(new TypeFunction(new TypeVariable("a"), new TypeInt()), new TypeVariable("b"));
		Type t2 = new TypeFunction(new TypeFunction(new TypeBool(), new TypeInt()), new TypeInt());
		Substitution s = Typeinference.unify(t1, t2);
		assertTrue("must have found a", s.containsKey("a"));
		assertTrue("must have found b", s.containsKey("b"));
		assertEquals(new TypeBool(), s.get("a"));
		assertEquals(new TypeInt(), s.get("b"));
	}

	@Test
	public void testSubstitutionInt() {
		Type t = new TypeInt();
		Substitution subst = new Substitution();
		subst.put("a", new TypeInt());
		assertEquals(new TypeInt(), t.applySubstitution(subst));
	}

	@Test
	public void testSubstitutionVar() {
		Type t = new TypeVariable("a");
		Substitution subst = new Substitution();
		subst.put("a", new TypeBool());
		assertEquals(new TypeBool(), t.applySubstitution(subst));
	}

	@Test
	public void testSubstitutionFunction() {
		Type t = new TypeFunction(new TypeVariable("a"), new TypeVariable("b"));
		Substitution subst = new Substitution();
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
