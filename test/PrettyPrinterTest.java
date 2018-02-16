import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import parser.AstNode;
import parser.Parser;
import util.PrettyPrinter;


public class PrettyPrinterTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testInteger() {
		Parser p = new Parser("42");
		AstNode ast = p.pExpr();
		PrettyPrinter pp = new PrettyPrinter();
		ast.accept(pp);
		assertEquals("42", pp.getResultString());
	}
	
	
	@Test
	public void testPlus() {
		Parser p = new Parser("4   + 2");
		AstNode ast = p.pExpr();
		PrettyPrinter pp = new PrettyPrinter();
		ast.accept(pp);
		assertEquals("4+2", pp.getResultString());
	}

}
