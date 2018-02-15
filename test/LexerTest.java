
import static org.junit.Assert.*;

import org.junit.Test;


public class LexerTest {

	@Test
	public void testEmptyString() {
		Lexer l = new Lexer("");
		assertEquals(TokenType.TOK_EOF, l.nextToken().getTokenType());
	}

}
