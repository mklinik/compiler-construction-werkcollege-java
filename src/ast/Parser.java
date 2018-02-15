package ast;

import lex.Lexer;

public class Parser {
	private Lexer lexer;
	public Parser(Lexer l) {
		lexer = l;
	}
	public Parser(String input)
	{
		lexer = new Lexer(input);
	}
	
	public AstExpr pExpr()
	{
		throw new Error("pExpr not implemented yet");
	}

}
