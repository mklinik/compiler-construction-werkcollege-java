package parser;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;

public class Parser {
	private Lexer lexer;
	private Token currentToken;

	// =========================================================
	// Constructors
	// =========================================================

	public Parser(Lexer l) {
		lexer = l;
		next();
	}

	public Parser(String input) {
		lexer = new Lexer(input);
		next();
	}

	// =========================================================
	// Helper functions
	// =========================================================

	// Helper function to fetch the next token. Convention: A parser calls next
	// whenever it consumes a token.
	private Token next() {
		Token oldToken = currentToken;
		currentToken = lexer.nextToken();
		return oldToken;
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
		return pExpr1();
	}

	public AstExpr pExpr1() {
		AstExpr lhs = pExpr2();
		return pExpr1_(lhs);
	}
	
	public AstExpr pExpr1_(AstExpr lhs)
	{
		if (match(TokenType.TOK_LESS_THAN)) {
			next();
			AstExpr rhs = pExpr2();
			return pExpr1_(new AstExprBinOp(lhs, TokenType.TOK_LESS_THAN, rhs));
		}

		return lhs;
	}

	
	public AstExpr pExpr2() {
		AstExpr lhs = pExpr3();
		return pExpr2_(lhs);
	}

	public AstExpr pExpr2_(AstExpr lhs) {
		// Plus and minus have the same precedence, therefore they are in the
		// same rule
		if (match(TokenType.TOK_PLUS)) {
			next();
			AstExpr rhs = pExpr3();
			return pExpr2_(new AstExprBinOp(lhs, TokenType.TOK_PLUS, rhs));
		}

		if (match(TokenType.TOK_MINUS)) {
			next();
			AstExpr rhs = pExpr3();
			return pExpr2_(new AstExprBinOp(lhs, TokenType.TOK_MINUS, rhs));
		}

		return lhs;
	}

	public AstExpr pExpr3() {
		AstExpr lhs = pBaseExpr();
		return pExpr3_(lhs);
	}

	private AstExpr pExpr3_(AstExpr lhs) {
		if (match(TokenType.TOK_MULT)) {
			next();
			AstExpr rhs = pBaseExpr();
			return pExpr3_(new AstExprBinOp(lhs, TokenType.TOK_MULT, rhs));
		}
		return lhs;
	}

	public AstExpr pBaseExpr() {
		if (match(TokenType.TOK_INT)) {
			return new AstExprInteger(next());
		}
		if (match(TokenType.TOK_BOOL)) {
			return new AstExprBool(next());
		}

		throw error("pFactor");
	}

}
