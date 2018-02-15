
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
		assertEquals(TokenType.TOK_INT, l.nextToken().getTokenType());
	}

}
