package typechecker;

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

public class TypeInference implements Visitor {
	// input parameters
	private Environment env;
	private Type expectedType;

	// result
	private Substitution result;
	
	// These are for convenience.
	private static final Type typeInt = new TypeInt();
	private static final Type typeBool = new TypeBool();

	// for generating fresh type variables
	private int nextTypeVariable;
	
	private Type freshTypeVariable() {
		return new TypeVariable("a" + nextTypeVariable++);
	}

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

	// Unification as in the lecture slides
	public static Substitution unify(Type left, Type right) {
		if (left.getClass() == right.getClass()) {
			// function types must unify recursively
			if (left instanceof TypeFunction) {
				TypeFunction l = (TypeFunction) left;
				TypeFunction r = (TypeFunction) right;
				Substitution s1 = unify(l.getArgType(), r.getArgType());
				Substitution s2 = unify(
						l.getResultType().applySubstitution(s1), r
								.getResultType().applySubstitution(s1));
				s1.putAll(s2);
				return s1;
			} else if (left instanceof TypeVariable) {
				if (left.equals(right)) {
					return new Substitution();
				} else {
					Substitution result = new Substitution();
					result.put(((TypeVariable) left).getVariable(), right);
					return result;
				}
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

	@Override
	public void visit(AstExprInteger i) {
		result.putAll(unify(expectedType, typeInt));
		i.setType(expectedType.applySubstitution(result));
	}

	@Override
	public void visit(AstExprBinOp e) {
		switch (e.getOperator()) {
		case TOK_PLUS:
		case TOK_MINUS:
			Type _expectedType = expectedType;

			expectedType = typeInt;
			e.getLeft().accept(this);

			env.applySubstitution(result);
			expectedType = typeInt;
			e.getRight().accept(this);

			result.putAll(unify(_expectedType.applySubstitution(result),
					typeInt));
			e.setType(_expectedType.applySubstitution(result));
			break;

		case TOK_LESS_THAN:
			error("Typeinference: <= not implemented");
			break;

		default:
			error("Typecheinference: Unknown operator " + e.getOperator());
			break;
		}

	}

	@Override
	public void visit(AstExprBool e) {
		result.putAll(unify(expectedType, typeBool));
		e.setType(expectedType.applySubstitution(result));
	}

	@Override
	public void visit(AstAbstraction e) {
		Type _expectedType = expectedType;
		Type a = freshTypeVariable();
		Type b = freshTypeVariable();
		env.put(e.getIdentifier(), a);
		expectedType = b;
		e.getBody().accept(this);
		env.remove(e.getIdentifier());
		result.putAll(unify(
				_expectedType.applySubstitution(result),
				new TypeFunction(a.applySubstitution(result), b
						.applySubstitution(result))));
		e.setType(_expectedType.applySubstitution(result));

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
		result.putAll(unify(expectedType, env.get(e.getIdentifier())));
		e.setType(expectedType.applySubstitution(result));
	}

	@Override
	public void visit(AstTypeFunction astTypeFunction) {
		// TODO Auto-generated method stub

	}

	public TypeInference(AstExpr ast) {
		nextTypeVariable = 0;
		this.env = new Environment();
		this.expectedType = freshTypeVariable();
		this.result = new Substitution();
		errors = new LinkedList<>();
		try {
			ast.accept(this);
		} catch (Error e) {
			error(e.getMessage());
		}
	}
}
