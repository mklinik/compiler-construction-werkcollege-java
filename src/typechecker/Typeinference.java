package typechecker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import parser.AstAbstraction;
import parser.AstExpr;
import parser.AstExprBinOp;
import parser.AstExprBool;
import parser.AstExprInteger;
import parser.AstIdentifier;
import parser.AstTypeBool;
import parser.AstTypeFunction;
import parser.AstTypeInt;
import util.CompileError;
import util.Visitor;

public class Typeinference implements Visitor {
	// These are for convenience.
	private static final Type typeInt = new TypeInt();
	private static final Type typeBool = new TypeBool();

	private int nextTypeVariable;
	private HashMap<String, Type> env;
	private HashMap<String, Type> subst;

	private List<CompileError> errors = null;

	public List<CompileError> getErrors() {
		return errors;
	}

	public String getAllErrors() {
		StringBuilder result = new StringBuilder();
		for (CompileError e : errors) {
			result.append(e.getErrorMessage());
			result.append("\n");
		}
		return result.toString();
	}

	private void error(String errorMessage) {
		errors.add(new CompileError(errorMessage));
	}

	private void unify(Type left, Type right) {
		if (left.getClass() == right.getClass()) {
			// function types must unify recursively
			if (left instanceof TypeFunction) {
				TypeFunction l = (TypeFunction) left;
				TypeFunction r = (TypeFunction) right;
				unify(l.getArgType().applySubstitution(subst), r.getArgType()
						.applySubstitution(subst));
				unify(l.getResultType().applySubstitution(subst), r
						.getResultType().applySubstitution(subst));
				return;
			}
			// base types always unify
			return;
		} else if (left instanceof TypeVariable) {
			// TODO: occurs check
			subst.put(((TypeVariable) left).getVariable(), right);
		} else if (right instanceof TypeVariable) {
			// TODO: occurs check
			subst.put(((TypeVariable) right).getVariable(), left);
		} else {
			throw new Error("cannot unify types");
		}
	}

	private String freshTypeVariable() {
		return "a" + nextTypeVariable++;
	}

	@Override
	public void visit(AstExprInteger i) {
		i.setType(typeInt);
	}

	@Override
	public void visit(AstExprBinOp e) {
		try {
			e.getLeft().accept(this);
			e.getRight().accept(this);

			switch (e.getOperator()) {
			case TOK_PLUS:
			case TOK_MINUS:
				unify(e.getLeft().getType(), typeInt);
				unify(e.getRight().getType(), typeInt);
				e.setType(typeInt);
				break;

			case TOK_LESS_THAN:
				error("Typeinference: <= not implemented");
				break;

			default:
				error("Typecheinference: Unknown operator " + e.getOperator());
				break;
			}

			e.getLeft().setType(e.getLeft().getType().applySubstitution(subst));
			e.getRight().setType(
					e.getRight().getType().applySubstitution(subst));
		} catch (Error err) {
			error(err.getMessage());
		}
	}

	// algorithm W takes: environment, expression and returns: substitution and
	// type such that type.applySubst(subst) is the type of the expression

	@Override
	public void visit(AstExprBool astBool) {
		astBool.setType(typeBool);
	}

	@Override
	public void visit(AstAbstraction e) {
		Type tvar = new TypeVariable(freshTypeVariable());
		env.put(e.getIdentifier(), tvar);
		e.getBody().accept(this);
		e.setType(new TypeFunction(tvar.applySubstitution(subst), e.getBody()
				.getType().applySubstitution(subst)));
	}

	@Override
	public void visit(AstTypeInt astTypeInt) {
		astTypeInt.setType(new TypeInt());
	}

	@Override
	public void visit(AstTypeBool astTypeBool) {
		astTypeBool.setType(new TypeBool());

	}

	@Override
	public void visit(AstIdentifier e) {
		e.setType(env.get(e.getIdentifier()));
	}

	@Override
	public void visit(AstTypeFunction astTypeFunction) {
		// TODO Auto-generated method stub

	}

	public boolean typeinference(AstExpr ast) {
		errors = new LinkedList<>();
		env = new HashMap<>();
		subst = new HashMap<>();
		nextTypeVariable = 0;
		ast.accept(this);
		return errors.isEmpty();
	}

}
