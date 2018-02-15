
public class TokenError extends Token {
	String error;
	public TokenError(String string) {
		super();
		error = string;
		tokenType = TokenType.TOK_ERR;
	}

}
