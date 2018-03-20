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
	
	// input parameters
	private HashMap<String, Type> env;
	private Type expectedType;
	
	// result
	private Substitution result;

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

	public static Substitution unify(Type left, Type right) {
		if (left.getClass() == right.getClass()) {
			// function types must unify recursively
			if (left instanceof TypeFunction) {
				TypeFunction l = (TypeFunction) left;
				TypeFunction r = (TypeFunction) right;
				Substitution s1 = unify(l.getArgType(), r.getArgType());
				Substitution s2 = unify(l.getResultType().applySubstitution(s1), r.getResultType().applySubstitution(s1));
				s1.combine(s2);
				return s1;
			} else {
				// base types always unify
				return new Substitution();
			}
		} else if (left instanceof TypeVariable) {
			// TODO: occurs check
			Substitution result = new Substitution();
			result.put(((TypeVariable) left).getVariable(), right);
			return result;
		} else if (right instanceof TypeVariable) {
			// TODO: occurs check
			Substitution result = new Substitution();
			result.put(((TypeVariable) right).getVariable(), left);
			return result;
		} else {
			throw new Error("cannot unify types");
		}
	}

	private String freshTypeVariable() {
		return "a" + nextTypeVariable++;
	}

	@Override
	public void visit(AstExprInteger i) {
		result.putAll(unify(expectedType, typeInt));
		i.setType(expectedType.applySubstitution(result));
	}

	@Override
	public void visit(AstExprBinOp e) {
	}

	// algorithm W takes: environment, expression and returns: substitution and
	// type such that type.applySubst(subst) is the type of the expression

	@Override
	public void visit(AstExprBool astBool) {
	}

	@Override
	public void visit(AstAbstraction e) {
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

	public Typeinference(AstExpr ast) {
		this.env = new HashMap<>();
		this.expectedType = new TypeVariable(freshTypeVariable());
		this.result = new Substitution();
		errors = new LinkedList<>();
		nextTypeVariable = 0;
		ast.accept(this);
	}
}
