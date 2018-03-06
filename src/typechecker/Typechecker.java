package typechecker;

import java.util.LinkedList;
import java.util.List;

import parser.AstExprBinOp;
import parser.AstExprBool;
import parser.AstExprInteger;
import parser.AstNode;
import util.CompileError;
import util.Visitor;

public class Typechecker implements Visitor {
	private List<CompileError> errors = null;

	public List<CompileError> getErrors() {
		return errors;
	}
	
	private void error(String errorMessage)
	{
		errors.add(new CompileError(errorMessage));
	}

	@Override
	public void visit(AstExprInteger e) {
		e.setType(new TypeInt());
	}

	@Override
	public void visit(AstExprBinOp e) {
		switch (e.getOperator()) {
		default:
			error("Typechecker: Unknown operator " + e.getOperator());
		}
	}

	@Override
	public void visit(AstExprBool e) {
		e.setType(new TypeBool());
	}

	public boolean typecheck(AstNode ast) {
		errors = new LinkedList<>();
		ast.accept(this);
		return errors.isEmpty();
	}

}
