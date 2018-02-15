package lex;

public class Lexer {
	String input = null;
	int currentPosition = 0;

	public Lexer(String inp) {
		input = inp;
	}

	void skipWhitespace() {
		while (currentPosition < input.length()
				&& Character.isWhitespace(input.charAt(currentPosition))) {
			currentPosition++;
		}
	}

	public Token nextToken() {
		skipWhitespace();
		if (currentPosition >= input.length()) {
			return new Token(TokenType.TOK_EOF);
		}

		// We have at least one character in the input
		if (Character.isDigit(input.charAt(currentPosition))) {
			return lexInteger();
		}

		if (input.charAt(currentPosition) == '+') {
			currentPosition++;
			return new Token(TokenType.TOK_PLUS);
		}

		return new TokenError("Unknown character in input: '"
				+ input.charAt(currentPosition) + "'");
	}

	Token lexInteger() {
		int currentValue = 0;
		while (currentPosition < input.length()
				&& Character.isDigit(input.charAt(currentPosition))) {
			currentValue *= 10;
			currentValue += Character.getNumericValue(input
					.charAt(currentPosition));
			currentPosition++;
		}

		return new TokenInteger(currentValue);
	}
}
