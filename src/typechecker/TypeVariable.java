package typechecker;

import java.util.HashMap;

public class TypeVariable implements Type {
	private String variable;
	
	public String getVariable() {
		return variable;
	}

	public TypeVariable(String variable)
	{
		this.variable = variable;
	}

	@Override
	public Type applySubstitution(HashMap<String, Type> substitution) {
		if( substitution.containsKey(variable))
		{
			return substitution.get(variable);
		}
		else
		{
			return this;
		}
	}

}
