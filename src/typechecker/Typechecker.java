package typechecker;

import parser.AstExprBinOp;
import parser.AstExprBool;
import parser.AstExprInteger;
import parser.AstNode;
import util.Visitor;

public class Typechecker implements Visitor {

	public Typechecker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visit(AstExprInteger e) {
		e.setType(new TypeInt());
	}

	@Override
	public void visit(AstExprBinOp e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AstExprBool e) {
		e.setType(new TypeBool());
	}

	public void typecheck(AstNode ast) {
		ast.accept(this);
	}

}
