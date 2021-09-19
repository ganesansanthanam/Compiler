package edu.ufl.cise.plpfa21.assignment2;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.ufl.cise.plpfa21.assignment1.CompilerComponentFactory;


class ExampleParserTests {
	
	static boolean VERBOSE = true;
	
	void noErrorParse(String input) throws SyntaxException {
		IPLPParser parser = CompilerComponentFactory.getParser(input);
		try {
			parser.parse();
		} catch (Throwable e) {
			throw new RuntimeException(e); 
		}
	}
	

	private void syntaxErrorParse(String input, int line, int column) throws SyntaxException {
		IPLPParser parser = CompilerComponentFactory.getParser(input);
		assertThrows(SyntaxException.class, () -> {
			try {
			parser.parse();
			}
			catch(SyntaxException e) {
				if (VERBOSE) System.out.println(input + '\n' + e.getMessage());
				Assertions.assertEquals(line, e.line);
				Assertions.assertEquals(column, e.column);
				throw e;
			}
		});
		
	}

	

	@Test public void test0() throws SyntaxException {
		String input = """

		""";
		noErrorParse(input);
	}
	

		@Test public void test1() throws SyntaxException {
		String input = """
		VAL a: INT = 0;
		""";
		noErrorParse(input);
		}


		@Test public void test2() throws SyntaxException  {
		String input = """
		VAL a: STRING = "hello";
		""";
		noErrorParse(input);
		}


		@Test public void test3() throws SyntaxException  {
		String input = """
		VAL b: BOOLEAN = TRUE;
		""";
		noErrorParse(input);
		}


		@Test public void test4() throws SyntaxException  {
		String input = """
		VAR b: LIST[];
		""";
		noErrorParse(input);
		}

       //This input has a syntax error at line 2, position 19.
		@Test public void test5() throws SyntaxException   {
		String input = """
		FUN func() DO
		WHILE x>0 DO x=x-1 END
		END
		""";
		syntaxErrorParse(input,2,19);
		}


	

}
