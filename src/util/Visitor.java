package util;

import parser.AstExprBool;
import parser.AstExprBinOp;
import parser.AstExprInteger;
import parser.AstLetBinding;

public interface Visitor {
	public void visit(AstExprInteger i);

	public void visit(AstExprBinOp e);

	public void visit(AstExprBool astBool);

	public void visit(AstLetBinding astLetBinding);
}