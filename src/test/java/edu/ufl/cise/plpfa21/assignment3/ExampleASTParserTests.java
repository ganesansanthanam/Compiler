package edu.ufl.cise.plpfa21.assignment3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import edu.ufl.cise.plpfa21.assignment2.SyntaxException;
import edu.ufl.cise.plpfa21.assignment3.ast.*;
import org.junit.jupiter.api.Test;

import edu.ufl.cise.plpfa21.assignment1.CompilerComponentFactory;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds;
import edu.ufl.cise.plpfa21.assignment2.IPLPParser;
import edu.ufl.cise.plpfa21.assignment3.ast.IType.TypeKind;

class ExampleASTParserTests implements PLPTokenKinds {

	private IASTNode getAST(String input) throws Exception {
		IPLPParser parser = CompilerComponentFactory.getParser(input);
		return parser.parse();
	}

	@Test
	public void test0() throws Exception {
		String input = """

				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test1() throws Exception {
		String input = """
				VAL a: INT = 0;
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "a");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IPrimitiveType);
		IPrimitiveType n9 = (IPrimitiveType) n8;
		assertEquals(n9.getType(), TypeKind.INT);
		IExpression n10 = n3.getExpression();
		assertTrue(n10 instanceof IIntLiteralExpression);
		IIntLiteralExpression n11 = (IIntLiteralExpression) n10;
		assertEquals(n11.getValue(), 0);
	}

	@Test
	public void test2() throws Exception {
		String input = """
				VAL a: STRING = "hello";
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "a");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IPrimitiveType);
		IPrimitiveType n9 = (IPrimitiveType) n8;
		assertEquals(n9.getType(), TypeKind.STRING);
		IExpression n10 = n3.getExpression();
		assertTrue(n10 instanceof IStringLiteralExpression);
		IStringLiteralExpression n11 = (IStringLiteralExpression) n10;
		assertEquals("hello", n11.getValue());
	}

	@Test
	public void test3() throws Exception {
		String input = """
				VAL b: BOOLEAN = TRUE;
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "b");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IPrimitiveType);
		IPrimitiveType n9 = (IPrimitiveType) n8;
		assertEquals(n9.getType(), TypeKind.BOOLEAN);
		IExpression n10 = n3.getExpression();
		assertTrue(n10 instanceof IBooleanLiteralExpression);
		IBooleanLiteralExpression n11 = (IBooleanLiteralExpression) n10;
		assertEquals(n11.getValue(), true);
	}

	@Test
	public void test4() throws Exception {
		String input = """
				VAR b: LIST[];
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IMutableGlobal);
		IMutableGlobal n3 = (IMutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "b");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IListType);
		IListType n9 = (IListType) n8;
	}



	@Test
	public void test5() throws Exception {
		String input = """
				VAR c = f();
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IMutableGlobal);
		IMutableGlobal n3 = (IMutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "c");
		IExpression n8 = n3.getExpression();
		assertTrue(n8 instanceof IFunctionCallExpression);
		IFunctionCallExpression n9 = (IFunctionCallExpression) n8;
		IIdentifier n10 = n9.getName();
		assertTrue(n10 instanceof IIdentifier);
		IIdentifier n11 = (IIdentifier) n10;
		assertEquals(n11.getName(), "f");
	}

	@Test
	public void test6() throws Exception {
		String input = """
				VAL c = a+b;
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "c");
		IExpression n8 = n3.getExpression();
		assertTrue(n8 instanceof IBinaryExpression);
		IBinaryExpression n9 = (IBinaryExpression) n8;
		IExpression n10 = n9.getLeft();
		assertTrue(n10 instanceof IIdentExpression);
		IIdentExpression n12 = (IIdentExpression) n10;
		IIdentifier n13 = n12.getName();
		assertTrue(n13 instanceof IIdentifier);
		IIdentifier n14 = (IIdentifier) n13;
		assertEquals(n14.getName(), "a");
		IExpression n11 = n9.getRight();
		assertTrue(n11 instanceof IIdentExpression);
		IIdentExpression n15 = (IIdentExpression) n11;
		IIdentifier n16 = n15.getName();
		assertTrue(n16 instanceof IIdentifier);
		IIdentifier n17 = (IIdentifier) n16;
		assertEquals(n17.getName(), "b");
		assertEquals(n9.getOp(), Kind.PLUS);
	}



	@Test
	public void test7() throws Exception {
		String input = """
				VAL d = ((2));
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "d");
		IExpression n8 = n3.getExpression();
		assertTrue(n8 instanceof IIntLiteralExpression);
		IIntLiteralExpression n9 = (IIntLiteralExpression) n8;
		assertEquals(n9.getValue(), 2);
	}

	@Test
	public void test8() throws Exception {
		String input = """
				VAL d = ((a+b)/(c+f()));
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "d");
		IExpression n8 = n3.getExpression();
		assertTrue(n8 instanceof IBinaryExpression);
		IBinaryExpression n9 = (IBinaryExpression) n8;
		IExpression n10 = n9.getLeft();
		assertTrue(n10 instanceof IBinaryExpression);
		IBinaryExpression n12 = (IBinaryExpression) n10;
		IExpression n13 = n12.getLeft();
		assertTrue(n13 instanceof IIdentExpression);
		IIdentExpression n15 = (IIdentExpression) n13;
		IIdentifier n16 = n15.getName();
		assertTrue(n16 instanceof IIdentifier);
		IIdentifier n17 = (IIdentifier) n16;
		assertEquals(n17.getName(), "a");
		IExpression n14 = n12.getRight();
		assertTrue(n14 instanceof IIdentExpression);
		IIdentExpression n18 = (IIdentExpression) n14;
		IIdentifier n19 = n18.getName();
		assertTrue(n19 instanceof IIdentifier);
		IIdentifier n20 = (IIdentifier) n19;
		assertEquals(n20.getName(), "b");
		assertEquals(n12.getOp(), Kind.PLUS);
		IExpression n11 = n9.getRight();
		assertTrue(n11 instanceof IBinaryExpression);
		IBinaryExpression n21 = (IBinaryExpression) n11;
		IExpression n22 = n21.getLeft();
		assertTrue(n22 instanceof IIdentExpression);
		IIdentExpression n24 = (IIdentExpression) n22;
		IIdentifier n25 = n24.getName();
		assertTrue(n25 instanceof IIdentifier);
		IIdentifier n26 = (IIdentifier) n25;
		assertEquals(n26.getName(), "c");
		IExpression n23 = n21.getRight();
		assertTrue(n23 instanceof IFunctionCallExpression);
		IFunctionCallExpression n27 = (IFunctionCallExpression) n23;
		IIdentifier n28 = n27.getName();
		assertTrue(n28 instanceof IIdentifier);
		IIdentifier n29 = (IIdentifier) n28;
		assertEquals(n29.getName(), "f");
		assertEquals(n21.getOp(), Kind.PLUS);
		assertEquals(n9.getOp(), Kind.DIV);
	}


	@Test
	public void test9() throws Exception {
		String input = """
				VAR A:LIST[LIST[INT]];
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IMutableGlobal);
		IMutableGlobal n3 = (IMutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "A");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IListType);
		IListType n9 = (IListType) n8;
		IType n10 = n9.getElementType();
		assertTrue(n10 instanceof IListType);
		IListType n11 = (IListType) n10;
		IType n12 = n11.getElementType();
		assertTrue(n12 instanceof IPrimitiveType);
		IPrimitiveType n13 = (IPrimitiveType) n12;
		assertEquals(n13.getType(), TypeKind.INT);
	}
	
	@Test public void test10() throws Exception{
		String input = """
		VAR A;
		VAL B=0;

		""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0=(IProgram)ast;
		List<IDeclaration> n1=n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IMutableGlobal);
		IMutableGlobal n3=(IMutableGlobal)n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5=(INameDef)n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7=(IIdentifier)n6;
		assertEquals(n7.getName(),"A");
		IDeclaration n8 = n1.get(1);
		assertTrue(n8 instanceof IImmutableGlobal);
		IImmutableGlobal n9=(IImmutableGlobal)n8;
		INameDef n10 = n9.getVarDef();
		assertTrue(n10 instanceof INameDef);
		INameDef n11=(INameDef)n10;
		IIdentifier n12 = n11.getIdent();
		assertTrue(n12 instanceof IIdentifier);
		IIdentifier n13=(IIdentifier)n12;
		assertEquals(n13.getName(),"B");
		IExpression n14 = n9.getExpression();
		assertTrue(n14 instanceof IIntLiteralExpression);
		IIntLiteralExpression n15=(IIntLiteralExpression)n14;
		assertEquals(n15.getValue(),0);
		}

	@Test public void test11() throws Exception{
		String input = """
		FUN f() DO
		RETURN NIL;
		END
		""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0=(IProgram)ast;
		List<IDeclaration> n1=n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IFunctionDeclaration);
		IFunctionDeclaration n3=(IFunctionDeclaration)n2;
		IIdentifier n4 = n3.getName();
		assertTrue(n4 instanceof IIdentifier);
		assertEquals(n4.getName(),"f");
		IBlock n5 = n3.getBlock();
		assertTrue(n5 instanceof IBlock);
		List<IStatement> n6 = n5.getStatements();
		IStatement n7=n6.get(0);
		assertTrue(n7 instanceof IReturnStatement);
		IReturnStatement n8 = (IReturnStatement)n7;
		IExpression n9 = n8.getExpression();
		assertTrue(n9 instanceof INilConstantExpression);
	}

	@Test public void test12() throws Exception {
		String input = """
				FUN f() DO
				RETURN NIL;
				END
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		int n2 = n1.size();
		assertEquals(n2, 1);
		IDeclaration n3 = n1.get(0);
		assertTrue(n3 instanceof IFunctionDeclaration);
		IFunctionDeclaration n4 = (IFunctionDeclaration) n3;
		IIdentifier n5 = n4.getName();
		assertEquals(n5.getName(), "f");
		List<INameDef> n6 = n4.getArgs();
		int n7 = n6.size();
		assertEquals(n7, 0);
		IType n8 = n4.getResultType();
		assertEquals(n8, null);
		IBlock n9 = n4.getBlock();
		List<IStatement> n10 = n9.getStatements();
		int n11 = n10.size();
		assertEquals(n11, 1);
		IStatement n12 = n10.get(0);
		assertTrue(n12 instanceof IReturnStatement);
		IReturnStatement n13 = (IReturnStatement) n12;
		IExpression n14 = n13.getExpression();
		assertTrue(n14 instanceof INilConstantExpression);
	}

	@Test public void test13() throws Exception {
		String input = """
		FUN soln(a, b:LIST[], c:STRING, d:LIST[INT]):INT DO
		SWITCH a<6
		CASE a+2: LET a=2*5 DO END
		DEFAULT RETURN a&&b;
		END
		IF b==d && !a DO 4*6; END
		END
		VAL a = 1-5;
		VAR a:STRING = "thisisa \nstring";
		""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		int n2 = n1.size();
		assertEquals(n2, 3);
	}

	@Test public void test14() throws Exception {
		String input = """
				FUN func() DO
				IF x==0 DO x = 1; END
				END  /*FUN*/
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
	}

	@Test public void test15() throws Exception {
		String input = """
			FUN func() DO
			SWITCH x
			CASE 0 : y=0;
			CASE 1 : y=1;
			CASE 2 : y=2;
			DEFAULT y=3;
			END  /*SWITCH*/
			END  /*FUN*/
		""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
	}

	@Test public void test16() throws Exception {
		String input = """
				FUN func() DO
				SWITCH x
				CASE 0 : y=0;
				CASE 1 : y=1;
				CASE 2 : y=2;
				DEFAULT y=3;
				END  /*SWITCH*/
				END  /*FUN*/
				""";
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		int n2 = n1.size();
		IDeclaration n3 = n1.get(0);
		assertTrue(n3 instanceof IFunctionDeclaration);
		IFunctionDeclaration n4 = (IFunctionDeclaration) n3;
		IIdentifier n5 = n4.getName();
		assertEquals(n5.getName(), "func");
		List<INameDef> n6 = n4.getArgs();
		IBlock n9 = n4.getBlock();
		List<IStatement> n10 = n9.getStatements();
		IStatement n12 = n10.get(0);
		assertTrue(n12 instanceof ISwitchStatement);
		ISwitchStatement n13 = (ISwitchStatement) n12;
		String s = n12.getText();
	}

}