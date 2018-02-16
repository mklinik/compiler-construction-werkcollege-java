package util;

import lexer.TokenType;
import parser.AstExprBinOp;
import parser.AstExprInteger;

public class PrettyPrinter implements Visitor {
	StringBuilder result;

	public String getResultString() {
		return result.toString();
	}

	public PrettyPrinter() {
		result = new StringBuilder();
	}

	@Override
	public void visit(AstExprInteger i) {
		result.append(i.getValue());
	}

	@Override
	public void visit(AstExprBinOp e) {
		e.getLeft().accept(this);
		result.append(" OP ");
		e.getRight().accept(this);
	}
}
