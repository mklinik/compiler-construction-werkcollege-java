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
	public Parser(String input)
	{
		lexer = new Lexer(input);
	}
	
	private void next()
	{
		currentToken = lexer.nextToken();
	}
	
	private boolean match(TokenType tok)
	{
		return currentToken.getTokenType() == tok;
	}
	
	public AstExpr pExpr()
	{
		next();
		if( match(TokenType.TOK_INT) )
		{
			return new AstExprInteger(currentToken);
		}
			
		throw new Error("pExpr not implemented yet");
	}

}
