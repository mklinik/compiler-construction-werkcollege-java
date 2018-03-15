package util;

import parser.AstExprBool;
import parser.AstExprBinOp;
import parser.AstExprInteger;
import parser.AstIdentifier;
import parser.AstAbstraction;
import parser.AstTypeBool;
import parser.AstTypeFunction;
import parser.AstTypeInt;

public interface Visitor {
	public void visit(AstExprInteger i);

	public void visit(AstExprBinOp e);

	public void visit(AstExprBool astBool);

	public void visit(AstAbstraction astLetBinding);

	public void visit(AstTypeInt astTypeInt);

	public void visit(AstTypeBool astTypeBool);

	public void visit(AstIdentifier astIdentifier);

	public void visit(AstTypeFunction astTypeFunction);
}