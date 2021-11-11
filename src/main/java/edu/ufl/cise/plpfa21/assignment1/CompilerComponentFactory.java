package edu.ufl.cise.plpfa21.assignment1;
import edu.ufl.cise.plpfa21.assignment2.*;
import edu.ufl.cise.plpfa21.assignment3.ast.ASTVisitor;
import edu.ufl.cise.plpfa21.assignment4.*;
public class CompilerComponentFactory {

	public static IPLPLexer getLexer(String input) {
		//Replace with whatever is needed for your lexer.
		return new PLPLexer(input);
	}

	public static IPLPParser getParser(String input) {
		//Replace this with whatever is needed for your parser.
		IPLPLexer lex = new PLPLexer(input);
		return  new PLPParser(lex);
	}

	public static ASTVisitor getTypeCheckVisitor() {
		// Replace this with whatever is needed for your compiler
		return new TypeCheckVisitor();
	}
}
