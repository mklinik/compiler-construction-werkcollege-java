package parser;

import lexer.Token;
import lexer.TokenIdentifier;
import util.Visitor;

public class AstIdentifier extends AstExpr {
	private final String identifier;
	
	public AstIdentifier(Token token) {
		this.identifier = ((TokenIdentifier)token).getValue();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	public String getIdentifier() {
		return identifier;
	}

}
