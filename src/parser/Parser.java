package parser;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenIdentifier;
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

	// Helper function that throws a compile error when the token doesn't match
	private Token mustMatch(TokenType tok, String location) {
		if (currentToken.getTokenType() == tok) {
			return currentToken;
		} else {
			throw error(location);
		}
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

	public AstExpr pExpr1_(AstExpr lhs) {
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
		if (match(TokenType.TOK_KW_LET)) {
			next();
			AstType type = pType();
			Token identifier = mustMatch(TokenType.TOK_IDENTIFIER,
					"let binding");
			next();
			mustMatch(TokenType.TOK_KW_IN, "let binding");
			next();
			AstExpr body = pExpr();
			return new AstAbstraction(type,
					((TokenIdentifier) identifier).getValue(), body);
		}
		if (match(TokenType.TOK_IDENTIFIER))
		{
			return new AstIdentifier(next());
		}

		throw error("pBaseExpr");
	}

	// Only base types supported for now
	public AstType pType() {
		if (match(TokenType.TOK_IDENTIFIER)) {
			String typ = ((TokenIdentifier)currentToken).getValue();
			if( typ.equals("Int") )
			{
				next();
				return new AstTypeInt();
			}
			if( typ.equals("Bool") )
			{
				next();
				return new AstTypeBool();
			}
		}
		throw error("pType");
	}

}
