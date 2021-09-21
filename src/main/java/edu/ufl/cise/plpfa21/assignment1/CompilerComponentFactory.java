package edu.ufl.cise.plpfa21.assignment1;

import edu.ufl.cise.plpfa21.assignment2.IPLPParser;
import edu.ufl.cise.plpfa21.assignment2.PLPParser;
import edu.ufl.cise.plpfa21.assignment2.SyntaxException;

public class CompilerComponentFactory {

	static IPLPLexer getLexer(String input){
		//TODO  create and return a Lexer instance to parse the given input.
		return new PLPLexer(input);
	}
	public static IPLPParser getParser(String input){
		//TODO  create and return a Parser instance to parse the given input.
		return new PLPParser(input);
	}
	

}
