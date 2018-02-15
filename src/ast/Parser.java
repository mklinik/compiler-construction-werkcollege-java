package ast;

import lex.Lexer;
import lex.Token;
import lex.TokenType;

public class Parser {
	private Lexer lexer;
	private Token currentToken;
	
	// =========================================================
	// Constructors
	// =========================================================

	public Parser(Lexer l) {
		lexer = l;
	}

	public Parser(String input) {
		lexer = new Lexer(input);
	}
	
	// =========================================================
	// Helper functions
	// =========================================================

	// Helper function to fetch the next token
	private void next() {
		currentToken = lexer.nextToken();
	}

	// Helper function to check the type of the current token
	private boolean match(TokenType tok) {
		return currentToken.getTokenType() == tok;
	}

	// Helper function to generate error messages
	private Error error(String name) {
		return new Error(name + ": unexpected token "
				+ currentToken.getTokenType().toString());
	}
	
	// ==========================================================
	// The actual parser
	// ==========================================================

	public AstExpr pExpr() {
		AstExpr ast = pTerm();
		return pExpr_(ast);
	}

	public AstExpr pExpr_(AstExpr lhs) {
		next();
		
		// Plus and minus have the same precedence, therefore they are in the same rule
		if (match(TokenType.TOK_PLUS)) {
			AstExpr rhs = pTerm();
			return pExpr_(new AstExprBinOp(lhs, TokenType.TOK_PLUS, rhs));
		}
		
		if(match(TokenType.TOK_MINUS)) {
			AstExpr rhs = pTerm();
			return pExpr_(new AstExprBinOp(lhs, TokenType.TOK_MINUS, rhs));
		}

		return lhs;
	}

	public AstExpr pTerm() {
		return pFactor();
	}

	public AstExpr pFactor() {
		next();
		if (match(TokenType.TOK_INT)) {
			return new AstExprInteger(currentToken);
		}

		throw error("pFactor");
	}

}
