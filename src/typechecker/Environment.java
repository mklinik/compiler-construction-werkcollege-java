package typechecker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Environment extends HashMap<String, Type> {
	// Applies a substitution to all types in the environment
	public void applySubstitution(Substitution s) {
		Iterator it = this.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Type> pair = (Map.Entry<String, Type>) it.next();
			this.put(pair.getKey(), pair.getValue().applySubstitution(s));
		}
	}
}
