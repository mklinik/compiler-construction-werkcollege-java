package typechecker;


public interface Type {
	public Type applySubstitution(Substitution substitution);
}
