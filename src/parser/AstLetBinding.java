package parser;
import util.Visitor;


public class AstLetBinding extends AstExpr {
	private final String identifier;
	private final AstExpr definition;
	private final AstExpr body;

	public AstLetBinding(String identifier, AstExpr definition,
			AstExpr body) {
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
		return true;
	}

}
