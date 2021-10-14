package edu.ufl.cise.plpfa21.assignment1;
import edu.ufl.cise.plpfa21.assignment2.*;

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
}
