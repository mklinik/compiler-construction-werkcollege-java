
public class Token {
	private final TokenType tokenType;
	
	public Token(TokenType tokType)
	{
		tokenType = tokType;
	}

	TokenType getTokenType() {
		return tokenType;
	}
}
