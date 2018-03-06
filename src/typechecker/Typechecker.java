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
	// These are for convenience.
	private static final Type typeInt = new TypeInt();
	private static final Type typeBool = new TypeBool();

	private List<CompileError> errors = null;

	public List<CompileError> getErrors() {
		return errors;
	}

	private void error(String errorMessage) {
		errors.add(new CompileError(errorMessage));
	}

	public void printErrors() {
		for (CompileError e : errors) {
			System.out.println(e.getErrorMessage());
		}
	}

	public String getAllErrors() {
		StringBuilder result = new StringBuilder();
		for (CompileError e : errors) {
			result.append(e.getErrorMessage());
			result.append("\n");
		}
		return result.toString();
	}

	@Override
	public void visit(AstExprInteger e) {
		e.setType(new TypeInt());
	}

	@Override
	public void visit(AstExprBinOp e) {
		e.getLeft().accept(this);
		e.getRight().accept(this);

		switch (e.getOperator()) {
		case TOK_PLUS:
		case TOK_MINUS:
			if (e.getLeft().getType().equals(typeInt)
					&& e.getRight().getType().equals(typeInt))
				e.setType(typeInt);
			break;

		case TOK_LESS_THAN:
			if (e.getLeft().getType().equals(typeInt)
					&& e.getRight().getType().equals(typeInt))
				e.setType(typeBool);
			break;

		default:
			error("Typechecker: Unknown operator " + e.getOperator());
			break;
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
