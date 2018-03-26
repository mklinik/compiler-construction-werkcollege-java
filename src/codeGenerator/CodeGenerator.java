package codeGenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import parser.AstAbstraction;
import parser.AstExprBinOp;
import parser.AstExprBool;
import parser.AstExprInteger;
import parser.AstIdentifier;
import parser.AstNode;
import parser.AstTypeBool;
import parser.AstTypeFunction;
import parser.AstTypeInt;
import util.Visitor;

public class CodeGenerator implements Visitor {
	private List<String> output;

	public List<String> getOutput() {
		return output;
	}
	
	public CodeGenerator()
	{
		output = new LinkedList<>();
	}
	
	public void writeToFile(String filename) throws FileNotFoundException
	{
		PrintWriter out = new PrintWriter(filename);
		for( String line : output )
		{
			out.println(line);
		}
		out.close();
	}
	
	public void generateCode(AstNode ast, String outputFilename) throws FileNotFoundException
	{
		ast.accept(this);
		writeToFile(outputFilename);
	}

	@Override
	public void visit(AstExprInteger i) {
		output.add("push " + i.getValue());
	}

	@Override
	public void visit(AstExprBinOp e) {
		switch(e.getOperator())
		{
		case TOK_PLUS:
			output.add("add");
			break;
		default:
			throw new Error("Code generator: AstExprBinOp: unknown operator " + e.getOperator().toString());
		}
	}

	@Override
	public void visit(AstExprBool astBool) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AstAbstraction astAbs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AstTypeInt astTypeInt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AstTypeBool astTypeBool) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AstIdentifier astIdentifier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AstTypeFunction astTypeFunction) {
		// TODO Auto-generated method stub

	}

}
