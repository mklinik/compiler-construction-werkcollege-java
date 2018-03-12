package parser;

import util.Visitor;

public class AstLetBinding extends AstExpr {
	private final AstType type;
	private final String identifier;
	private final AstExpr definition;
	private final AstExpr body;

	public AstType getAstType() {
		return type;
	}

	public String getIdentifier() {
		return identifier;
	}

	public AstExpr getDefinition() {
		return definition;
	}

	public AstExpr getBody() {
		return body;
	}

	public AstLetBinding(AstType type, String identifier, AstExpr definition,
			AstExpr body) {
		this.type = type;
		this.identifier = identifier;
		this.definition = definition;
		this.body = body;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result
				+ ((definition == null) ? 0 : definition.hashCode());
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		AstLetBinding other = (AstLetBinding) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (definition == null) {
			if (other.definition != null)
				return false;
		} else if (!definition.equals(other.definition))
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
