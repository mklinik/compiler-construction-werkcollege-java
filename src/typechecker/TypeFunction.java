package typechecker;

import java.util.HashMap;

public class TypeFunction implements Type {
	private Type argType;
	private Type resultType;

	public Type getArgType() {
		return argType;
	}

	public Type getResultType() {
		return resultType;
	}

	public TypeFunction(Type argType, Type resultType) {
		this.argType = argType;
		this.resultType = resultType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((argType == null) ? 0 : argType.hashCode());
		result = prime * result
				+ ((resultType == null) ? 0 : resultType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeFunction other = (TypeFunction) obj;
		if (argType == null) {
			if (other.argType != null)
				return false;
		} else if (!argType.equals(other.argType))
			return false;
		if (resultType == null) {
			if (other.resultType != null)
				return false;
		} else if (!resultType.equals(other.resultType))
			return false;
		return true;
	}

	@Override
	public Type applySubstitution(HashMap<String, Type> substitution) {
		this.argType = this.argType.applySubstitution(substitution);
		this.resultType = this.resultType.applySubstitution(substitution);
		return this;
	}

}
