package parser;

import typechecker.Type;
import util.Visitor;

public abstract class AstNode {
	private Type type = null;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public abstract void accept(Visitor v);
}
