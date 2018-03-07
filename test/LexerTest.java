import static org.junit.Assert.*;
import lexer.Lexer;
import lexer.Token;
import lexer.TokenBool;
import lexer.TokenIdentifier;
import lexer.TokenInteger;
import lexer.TokenType;

import org.junit.Test;

public class LexerTest {

	private Lexer l;

	@Test
	public void testEmptyString() {
		l = new Lexer("");
		assertEquals(TokenType.TOK_EOF, l.nextToken().getTokenType());
	}

	@Test
	public void testSingleDigitInteger() {
		l = new Lexer("5");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_INT, t.getTokenType());
		assertEquals(5, ((TokenInteger) t).getValue());
	}

	@Test
	public void testMultiDigitInteger() {
		l = new Lexer("4545372");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_INT, t.getTokenType());
		assertEquals(4545372, ((TokenInteger) t).getValue());
	}

	@Test
	public void testSinglePlus() {
		l = new Lexer("+");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_PLUS, t.getTokenType());
	}

	@Test
	public void testSingleMinus() {
		l = new Lexer("-");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_MINUS, t.getTokenType());
	}

	@Test
	public void testSingleMult() {
		l = new Lexer("*");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_MULT, t.getTokenType());
	}

	@Test
	public void testMultipleIntegers() {
		l = new Lexer("12 34");
		Token t1 = l.nextToken();
		Token t2 = l.nextToken();
		assertEquals(TokenType.TOK_INT, t1.getTokenType());
		assertEquals(TokenType.TOK_INT, t2.getTokenType());
		assertEquals(12, ((TokenInteger) t1).getValue());
		assertEquals(34, ((TokenInteger) t2).getValue());
	}

	@Test
	public void testSimpleExpression() {
		l = new Lexer("1 + 12 + 300+4+");
		Token t1 = l.nextToken();
		Token t2 = l.nextToken();
		Token t3 = l.nextToken();
		Token t4 = l.nextToken();
		Token t5 = l.nextToken();
		Token t6 = l.nextToken();
		Token t7 = l.nextToken();
		Token t8 = l.nextToken();
		Token t9 = l.nextToken();
		assertEquals(TokenType.TOK_INT, t1.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t2.getTokenType());
		assertEquals(TokenType.TOK_INT, t3.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t4.getTokenType());
		assertEquals(TokenType.TOK_INT, t5.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t6.getTokenType());
		assertEquals(TokenType.TOK_INT, t7.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t8.getTokenType());
		assertEquals(TokenType.TOK_EOF, t9.getTokenType());
	}

	@Test
	public void testMixedPlussesAndMinusesExpression() {
		l = new Lexer("1 - 12 + 300-4-");
		Token t1 = l.nextToken();
		Token t2 = l.nextToken();
		Token t3 = l.nextToken();
		Token t4 = l.nextToken();
		Token t5 = l.nextToken();
		Token t6 = l.nextToken();
		Token t7 = l.nextToken();
		Token t8 = l.nextToken();
		Token t9 = l.nextToken();
		assertEquals(TokenType.TOK_INT, t1.getTokenType());
		assertEquals(TokenType.TOK_MINUS, t2.getTokenType());
		assertEquals(TokenType.TOK_INT, t3.getTokenType());
		assertEquals(TokenType.TOK_PLUS, t4.getTokenType());
		assertEquals(TokenType.TOK_INT, t5.getTokenType());
		assertEquals(TokenType.TOK_MINUS, t6.getTokenType());
		assertEquals(TokenType.TOK_INT, t7.getTokenType());
		assertEquals(TokenType.TOK_MINUS, t8.getTokenType());
		assertEquals(TokenType.TOK_EOF, t9.getTokenType());
	}

	@Test
	public void testIdentifier() {
		l = new Lexer("missPiggy");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_IDENTIFIER, t.getTokenType());
		assertEquals("missPiggy", ((TokenIdentifier) t).getValue());
	}

	@Test
	public void testIdentifierWithDigits() {
		l = new Lexer("mi55Piggy23");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_IDENTIFIER, t.getTokenType());
		assertEquals("mi55Piggy23", ((TokenIdentifier) t).getValue());
	}

	@Test
	public void testKeywordIf() {
		l = new Lexer("if");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_KW_IF, t.getTokenType());
	}

	@Test
	public void testKeywordPrefix() {
		l = new Lexer("iffy");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_IDENTIFIER, t.getTokenType());
	}

	@Test
	public void testBooleanConstantTrue() {
		l = new Lexer("True");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_BOOL, t.getTokenType());
		assertEquals(true, ((TokenBool) t).getValue());
	}

	private void assertTokenType(TokenType expected) {
		assertEquals(expected, l.nextToken().getTokenType());
	}

	@Test
	public void testLetExpression() {
		l = new Lexer("let x = 5 in x");
		assertTokenType(TokenType.TOK_KW_LET);
		assertTokenType(TokenType.TOK_IDENTIFIER);
		assertTokenType(TokenType.TOK_EQUALS);
		assertTokenType(TokenType.TOK_INT);
		assertTokenType(TokenType.TOK_KW_IN);
		assertTokenType(TokenType.TOK_IDENTIFIER);
	}
}
