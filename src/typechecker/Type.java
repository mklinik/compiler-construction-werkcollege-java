package typechecker;

import java.util.HashMap;

public interface Type {
	public Type applySubstitution(HashMap<String, Type> substitution);
}
