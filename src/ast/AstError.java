package ast;

public class AstError extends AstNode {
	String message;
	public AstError(String msg) {
		message = msg;
	}

}
