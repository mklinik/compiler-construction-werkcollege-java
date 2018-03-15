package parser;

import util.Visitor;

public class AstTypeFunction extends AstType {
	private final AstType argType;
	private final AstType resultType;

	public AstType getArgType() {
		return argType;
	}

	public AstType getResultType() {
		return resultType;
	}

	public AstTypeFunction(AstType argType, AstType resultType) {
		this.argType = argType;
		this.resultType = resultType;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
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
		AstTypeFunction other = (AstTypeFunction) obj;
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

}
