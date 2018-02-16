package lexer;

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

	private boolean match(char c) {
		return currentPosition < input.length()
				&& input.charAt(currentPosition) == c;
	}

	public Token nextToken() {
		skipWhitespace();
		if (currentPosition >= input.length()) {
			return new Token(TokenType.TOK_EOF);
		}

		// From here on, we have at least one character in the input

		if (Character.isDigit(input.charAt(currentPosition))) {
			return lexInteger();
		}

		if (match('+')) {
			currentPosition++;
			// Just an example how to lex operators that consist of two
			// characters.
			if (match('=')) {
				currentPosition++;
				return new Token(TokenType.TOK_PLUS_EQUALS);
			}
			// Otherwise, we've seen nothing.
			return new Token(TokenType.TOK_PLUS);
		}

		if (match('-')) {
			currentPosition++;
			return new Token(TokenType.TOK_MINUS);
		}

		if (match('*')) {
			currentPosition++;
			return new Token(TokenType.TOK_MULT);
		}

		if (Character.isAlphabetic(input.charAt(currentPosition))) {
			return lexIdentifier();
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

	Token lexIdentifier() {
		StringBuilder resultBuilder = new StringBuilder();
		while (currentPosition < input.length()
				&& (Character.isAlphabetic(input.charAt(currentPosition)) || Character
						.isDigit(input.charAt(currentPosition)))) {
			resultBuilder.append(input.charAt(currentPosition));
			currentPosition++;
		}

		String result = resultBuilder.toString();

		// Check if the identifier is one of the reserved keywords
		if (result.equals("if")) {
			return new Token(TokenType.TOK_KW_IF);
		}

		// Identifier is not a keyword, so we treat it as identifier
		return new TokenIdentifier(result);
	}
}
