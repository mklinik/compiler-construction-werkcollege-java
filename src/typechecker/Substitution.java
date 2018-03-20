package typechecker;

import java.util.HashMap;

// We extend HashMap to give it a better name
public class Substitution extends HashMap<String, Type> {
	public void combine(Substitution other) {
		putAll(other);
	}
}
