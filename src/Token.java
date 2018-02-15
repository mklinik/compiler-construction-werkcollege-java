
public abstract class Token {
	protected TokenType tokenType;

	TokenType getTokenType() {
		return tokenType;
	}

	void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
}
