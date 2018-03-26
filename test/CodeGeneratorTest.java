import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import codeGenerator.CodeGenerator;

import parser.AstExpr;
import parser.Parser;


public class CodeGeneratorTest {

	@Test
	public void testIntegerConstant() throws FileNotFoundException {
		Parser p = new Parser("5");
		AstExpr ast = p.pExpr();
		CodeGenerator gen = new CodeGenerator();
		gen.generateCode(ast, "test.ssm");
	}

}
