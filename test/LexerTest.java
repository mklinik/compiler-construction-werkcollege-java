
import static org.junit.Assert.*;

import org.junit.Test;


public class LexerTest {

	@Test
	public void testEmptyString() {
		Lexer l = new Lexer("");
		assertEquals(TokenType.TOK_EOF, l.nextToken().getTokenType());
	}
	
	@Test
	public void testSingleInteger() {
		Lexer l = new Lexer("5");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_INT, t.getTokenType());
		assertEquals(5, ((TokenInteger)t).getValue());
	}
	
	@Test
	public void testSinglePlus() {
		Lexer l = new Lexer("+");
		Token t = l.nextToken();
		assertEquals(TokenType.TOK_PLUS, t.getTokenType());
	}

}
