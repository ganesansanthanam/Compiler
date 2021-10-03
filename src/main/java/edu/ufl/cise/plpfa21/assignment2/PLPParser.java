package edu.ufl.cise.plpfa21.assignment2;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment1.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PLPParser implements IPLPParser{
    IPLPLexer lex;
    IPLPToken token;
    public  void parse() throws SyntaxException, LexicalException {
        Program();
    }

    public PLPParser (IPLPLexer lex) {
        this.lex = lex;
        try {
            this.token= lex.nextToken();
        } catch (LexicalException e) {
        }
    }

    private void Program() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        if(k.equals(Kind.EOF)){
            matchEOF();
        }
        else if((k.equals(Kind.KW_VAL) || k.equals(Kind.KW_VAR) || k.equals(Kind.KW_FUN))){
            while (!(token.getKind().equals(Kind.EOF))) {
                Declaration();
            }
            matchEOF();
        }
        else {
            String errorMessage = "Illegal Statement - VAR,VAL,FUN Line: " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
            throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
        }
    }

    private void Declaration() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        if (k.equals(Kind.KW_VAL)) {
            matchToken(Kind.KW_VAL);
            NameDef();
            matchToken(Kind.ASSIGN);
            Expression();
            matchToken(Kind.SEMI);
        }
        else if(k.equals(Kind.KW_VAR)){
            matchToken(Kind.KW_VAR);
            NameDef();
            if(token.getKind().equals(Kind.ASSIGN)){
                matchToken(Kind.ASSIGN);
                Expression();
            }
            matchToken(Kind.SEMI);
        }
        else if(k.equals(Kind.KW_FUN)) {
            Function();
        }
        else {
            String errorMessage = "Illegal Statement - VAR,VAL,FUN Line: " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
            throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
        }
    }

    private void Function() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        if (k.equals(Kind.KW_FUN)){
            matchToken(Kind.KW_FUN);
            matchToken(Kind.IDENTIFIER);
            matchToken(Kind.LPAREN);
            if(token.getKind().equals(Kind.IDENTIFIER)) {
                NameDef();
                while (token.getKind().equals(Kind.COMMA)) {
                    matchToken(Kind.COMMA);
                    NameDef();
                }
            }
            matchToken(Kind.RPAREN);
            if(token.getKind().equals(Kind.COLON)){
                matchToken(Kind.COLON);
                Type();
            }
            matchToken(Kind.KW_DO);
            Block();
            matchToken(Kind.KW_END);
        }
    }

    private void Block() throws SyntaxException, LexicalException {
        ArrayList<Kind> blockFirst = new ArrayList<Kind>();
        ArrayList<Kind> expressionFirst = new ArrayList<Kind>();
        blockFirst.add(Kind.KW_LET);
        blockFirst.add(Kind.KW_SWITCH);
        blockFirst.add(Kind.KW_IF);
        blockFirst.add(Kind.KW_WHILE);
        blockFirst.add(Kind.KW_RETURN);
        blockFirst.add(Kind.KW_NIL);
        blockFirst.add(Kind.KW_TRUE);
        blockFirst.add(Kind.KW_FALSE);
        blockFirst.add(Kind.INT_LITERAL);
        blockFirst.add(Kind.STRING_LITERAL);
        blockFirst.add(Kind.LPAREN);
        blockFirst.add(Kind.IDENTIFIER);
        blockFirst.add(Kind.BANG);
        blockFirst.add(Kind.MINUS);
        while (blockFirst.contains(token.getKind())){
            Statement();
        }
    }

    private void Statement() throws SyntaxException, LexicalException {
        ArrayList<Kind> statementFirst = new ArrayList<Kind>();
        ArrayList<Kind> expressionFirst = new ArrayList<Kind>();
        statementFirst.add(Kind.KW_LET);
        statementFirst.add(Kind.KW_SWITCH);
        statementFirst.add(Kind.KW_IF);
        statementFirst.add(Kind.KW_WHILE);
        statementFirst.add(Kind.KW_RETURN);
        expressionFirst.add(Kind.KW_NIL);
        expressionFirst.add(Kind.KW_TRUE);
        expressionFirst.add(Kind.KW_FALSE);
        expressionFirst.add(Kind.INT_LITERAL);
        expressionFirst.add(Kind.STRING_LITERAL);
        expressionFirst.add(Kind.LPAREN);
        expressionFirst.add(Kind.IDENTIFIER);
        expressionFirst.add(Kind.BANG);
        expressionFirst.add(Kind.MINUS);
        if(statementFirst.contains(token.getKind())){
            if(token.getKind().equals(Kind.KW_LET)){
                matchToken(Kind.KW_LET);
                NameDef();
                if (token.getKind().equals(Kind.ASSIGN)){
                    matchToken(Kind.ASSIGN);
                    Expression();
                }
                matchToken(Kind.SEMI);
            }
            else if(token.getKind().equals(Kind.KW_IF) || token.getKind().equals(Kind.KW_WHILE)){
                matchToken(token.getKind());
                Expression();
                matchToken(Kind.KW_DO);
                Block();
                matchToken(Kind.KW_END);
            }
            else if(token.getKind().equals(Kind.KW_RETURN)){
                matchToken(Kind.KW_RETURN);
                Expression();
                matchToken(Kind.SEMI);
            }
            else if(token.getKind().equals(Kind.KW_SWITCH)){
                matchToken(Kind.KW_SWITCH);
                Expression();
                while (token.getKind().equals(Kind.KW_CASE)){
                    matchToken(Kind.KW_CASE);
                    Expression();
                    matchToken(Kind.COLON);
                    Block();
                }
                matchToken(Kind.KW_DEFAULT);
                Block();
                matchToken(Kind.KW_END);
            }
        }
        else if(expressionFirst.contains(token.getKind())){
            Expression();
            if (token.getKind().equals(Kind.ASSIGN)){
                matchToken(Kind.ASSIGN);
                Expression();
            }
            matchToken(Kind.SEMI);
        }
        else {
            String errorMessage = "Illegal Statement - needs LET, IF, WHILE, SWITCH, RETURN OR EXPRESSION Line: " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
            throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
        }
    }

    private void NameDef() throws SyntaxException, LexicalException {
        matchToken(Kind.IDENTIFIER);
        if (token.getKind().equals(Kind.COLON)) {
            matchToken(Kind.COLON);
            Type();
        }
    }

    private void Expression() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        ArrayList<Kind> kindList = new ArrayList<Kind>();
        kindList.add(Kind.KW_NIL);
        kindList.add(Kind.KW_TRUE);
        kindList.add(Kind.KW_FALSE);
        kindList.add(Kind.INT_LITERAL);
        kindList.add(Kind.STRING_LITERAL);
        kindList.add(Kind.LPAREN);
        kindList.add(Kind.IDENTIFIER);
        kindList.add(Kind.BANG);
        kindList.add(Kind.MINUS);
        if (kindList.contains(k)){
            LogicalExpression();
        }
        else {
            throw new SyntaxException("Not a valid expression",token.getLine(),token.getCharPositionInLine());
        }
    }

    private void LogicalExpression() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        ArrayList<Kind> kindList = new ArrayList<Kind>();
        kindList.add(Kind.KW_NIL);
        kindList.add(Kind.KW_TRUE);
        kindList.add(Kind.KW_FALSE);
        kindList.add(Kind.INT_LITERAL);
        kindList.add(Kind.STRING_LITERAL);
        kindList.add(Kind.LPAREN);
        kindList.add(Kind.IDENTIFIER);
        kindList.add(Kind.BANG);
        kindList.add(Kind.MINUS);
        if (kindList.contains(k)){
            ComparisonExpression();
            while (token.getKind().equals(Kind.AND) || token.getKind().equals(Kind.OR)){
                matchToken(token.getKind());
                ComparisonExpression();
            }
        }
        else {
            throw new SyntaxException("Not a valid logical expression",token.getLine(),token.getCharPositionInLine());
        }
    }

    private void ComparisonExpression() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        ArrayList<Kind> kindList = new ArrayList<Kind>();
        kindList.add(Kind.KW_NIL);
        kindList.add(Kind.KW_TRUE);
        kindList.add(Kind.KW_FALSE);
        kindList.add(Kind.INT_LITERAL);
        kindList.add(Kind.STRING_LITERAL);
        kindList.add(Kind.LPAREN);
        kindList.add(Kind.IDENTIFIER);
        kindList.add(Kind.BANG);
        kindList.add(Kind.MINUS);
        if (kindList.contains(k)){
            AdditiveExpression();
            while (token.getKind().equals(Kind.LT) || token.getKind().equals(Kind.GT) || token.getKind().equals(Kind.EQUALS) || token.getKind().equals(Kind.NOT_EQUALS)){
                matchToken(token.getKind());
                AdditiveExpression();
            }
        }
        else {
            throw new SyntaxException("Not a valid Comparison expression",token.getLine(),token.getCharPositionInLine());
        }
    }

    private void AdditiveExpression() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        ArrayList<Kind> kindList = new ArrayList<Kind>();
        kindList.add(Kind.KW_NIL);
        kindList.add(Kind.KW_TRUE);
        kindList.add(Kind.KW_FALSE);
        kindList.add(Kind.INT_LITERAL);
        kindList.add(Kind.STRING_LITERAL);
        kindList.add(Kind.LPAREN);
        kindList.add(Kind.IDENTIFIER);
        kindList.add(Kind.BANG);
        kindList.add(Kind.MINUS);
        if (kindList.contains(k)){
            MultiplicativeExpression();
            while(token.getKind().equals(Kind.PLUS) || token.getKind().equals(Kind.MINUS)){
                matchToken(token.getKind());
                MultiplicativeExpression();
            }

        }
        else {
            throw new SyntaxException("Not a valid Additive expression",token.getLine(),token.getCharPositionInLine());
        }
    }

    private void MultiplicativeExpression() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        ArrayList<Kind> kindList = new ArrayList<Kind>();
        kindList.add(Kind.KW_NIL);
        kindList.add(Kind.KW_TRUE);
        kindList.add(Kind.KW_FALSE);
        kindList.add(Kind.INT_LITERAL);
        kindList.add(Kind.STRING_LITERAL);
        kindList.add(Kind.LPAREN);
        kindList.add(Kind.IDENTIFIER);
        kindList.add(Kind.BANG);
        kindList.add(Kind.MINUS);
        if (kindList.contains(k)){
            UnaryExpression();
            while (token.getKind().equals(Kind.TIMES) || token.getKind().equals(Kind.DIV)){
                matchToken(token.getKind());
                UnaryExpression();
            }
        }
        else {
            throw new SyntaxException("Not a valid Multiplicative expression",token.getLine(),token.getCharPositionInLine());
        }
    }

    private void UnaryExpression() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        ArrayList<Kind> kindList = new ArrayList<Kind>();
        kindList.add(Kind.KW_NIL);
        kindList.add(Kind.KW_TRUE);
        kindList.add(Kind.KW_FALSE);
        kindList.add(Kind.INT_LITERAL);
        kindList.add(Kind.STRING_LITERAL);
        kindList.add(Kind.LPAREN);
        kindList.add(Kind.IDENTIFIER);
        if(k.equals(Kind.BANG) || k.equals(Kind.MINUS)){
            matchToken(token.getKind());
            PrimaryExpression();
        }
        else if (kindList.contains(k)){
            PrimaryExpression();
        }
        else {
            throw new SyntaxException("Not a valid Unary expression",token.getLine(),token.getCharPositionInLine());
        }
    }

    private void PrimaryExpression() throws SyntaxException, LexicalException {
        ArrayList<Kind> kindList = new ArrayList<Kind>();
        kindList.add(Kind.KW_NIL);
        kindList.add(Kind.KW_TRUE);
        kindList.add(Kind.KW_FALSE);
        kindList.add(Kind.INT_LITERAL);
        kindList.add(Kind.STRING_LITERAL);
        kindList.add(Kind.LPAREN);
        kindList.add(Kind.IDENTIFIER);
        if(kindList.contains(token.getKind())){
            if(token.getKind().equals(Kind.IDENTIFIER) || token.getKind().equals(Kind.LPAREN)){
                if(token.getKind().equals(Kind.LPAREN)){
                    matchToken(Kind.LPAREN);
                    Expression();
                    matchToken(Kind.RPAREN);
                }
                else{
                    matchToken(Kind.IDENTIFIER);
                    if (token.getKind().equals(Kind.LSQUARE) || token.getKind().equals(Kind.LPAREN)){
                        if(token.getKind().equals(Kind.LSQUARE)){
                            matchToken(Kind.LSQUARE);
                            Expression();
                            matchToken(Kind.RSQUARE);
                        }
                        else if(token.getKind().equals(Kind.LPAREN)){
                            matchToken(Kind.LPAREN);
                            if(kindList.contains(token.getKind()) || token.getKind().equals(Kind.BANG) || token.getKind().equals(Kind.MINUS)){
                                Expression();
                                while (token.getKind().equals(Kind.COMMA)){
                                    matchToken(Kind.COMMA);
                                    Expression();
                                }
                            }
                            matchToken(Kind.RPAREN);
                        }
                    }
                }
            }
            else matchToken(token.getKind());
        }
    }

    private void Type() throws SyntaxException, LexicalException {
        if (token.getKind().equals(Kind.KW_INT) || token.getKind().equals(Kind.KW_STRING) || token.getKind().equals(Kind.KW_BOOLEAN)){
            matchToken(token.getKind());
        }
        else if(token.getKind().equals(Kind.KW_LIST)){
            matchToken(Kind.KW_LIST);
            matchToken(Kind.LSQUARE);
            if(!(token.getKind().equals(Kind.RSQUARE))) {
                Type();
            }
            matchToken(Kind.RSQUARE);
        }
        else {
            String errorMessage = "Illegal Type - INT, STRING, LIST Line " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
            throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
        }
    }

    private IPLPToken matchToken(Kind kind) throws SyntaxException, LexicalException {
        if (kind.equals(token.getKind()))
        {
            return consumeToken();
        }
        String errorMessage = "Illegal syntax Missing: " + kind.toString() + " Line: " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
        throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
    }

    private void matchEOF() throws SyntaxException {
        if (!(token.getKind().equals(Kind.EOF))) {
            throw new SyntaxException("End of file not found",token.getLine(),token.getCharPositionInLine());
        }
    }

    private IPLPToken consumeToken() throws SyntaxException, LexicalException {
        this.token = lex.nextToken();
        return token;
    }
}
