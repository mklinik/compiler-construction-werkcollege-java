package typechecker;

import java.util.HashMap;

public class TypeInt implements Type {
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public Type applySubstitution(HashMap<String, Type> substitution) {
		return this;
	}
}
