package typechecker;

public abstract class Type {
	public abstract Type applySubstitution(Substitution substitution);

	// This is an alternative implementation of the unification algorithm.
	// Compare with TypeInference.unify. I'm not sure which one is better java
	public Substitution unifyWith(Type t) {
		if (t instanceof TypeInt)
			return unifyWith((TypeInt) t);
		else if (t instanceof TypeBool)
			return unifyWith((TypeBool) t);
		else if (t instanceof TypeFunction)
			return unifyWith((TypeFunction) t);
		else if (t instanceof TypeVariable)
			return unifyWith((TypeVariable) t);
		return null;
	}

	public Substitution unifyWith(TypeInt t) {
		return cannotUnify(this, t);
	}

	public Substitution unifyWith(TypeBool t) {
		return cannotUnify(this, t);
	}

	public Substitution unifyWith(TypeFunction t) {
		return cannotUnify(this, t);
	}

	public Substitution unifyWith(TypeVariable t) {
		Substitution s = new Substitution();
		if (!this.equals(t)) {
			s.put(t.getVariable(), this);
		}
		return s;
	}

	private Substitution cannotUnify(Type t1, Type t2) {
		throw new Error("cannot unify types " + t1.toString() + " and "
				+ t2.toString());
	}
}
