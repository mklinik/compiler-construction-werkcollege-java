package typechecker;

public abstract class Type {
	public abstract Type applySubstitution(Substitution substitution);
}
