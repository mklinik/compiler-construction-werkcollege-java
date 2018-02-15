package ast;

import lex.Lexer;
import lex.Token;
import lex.TokenType;

public class Parser {
	private Lexer lexer;
	private Token currentToken;

	public Parser(Lexer l) {
		lexer = l;
	}

	public Parser(String input) {
		lexer = new Lexer(input);
	}

	private void next() {
		currentToken = lexer.nextToken();
	}

	private boolean match(TokenType tok) {
		return currentToken.getTokenType() == tok;
	}

	private Error error(String name) {
		return new Error(name + ": unexpected token "
				+ currentToken.getTokenType().toString());
	}

	public AstExpr pExpr() {
		return pSum();
	}

	public AstExpr pSum() {
		AstExpr lhs = pFactor();
		next();
		if (match(TokenType.TOK_PLUS)) {
			AstExpr rhs = pSum();
			return new AstExprBinOp(lhs, TokenType.TOK_PLUS, rhs);
		}

		return lhs;
	}

	public AstExpr pFactor() {
		next();
		if (match(TokenType.TOK_INT)) {
			return new AstExprInteger(currentToken);
		}

		throw error("pFactor");
	}

}
