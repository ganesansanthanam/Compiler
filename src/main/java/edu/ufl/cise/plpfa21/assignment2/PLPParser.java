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
            matchToken(Kind.EOF);
        }
        else if((k.equals(Kind.KW_VAL) || k.equals(Kind.KW_VAR) || k.equals(Kind.KW_FUN))){
            Declaration();
            matchToken(Kind.EOF);
        }
        else {
            throw new SyntaxException("Illegal Statement - VAR,VAL,FUN",token.getLine(),token.getCharPositionInLine());
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
            throw new SyntaxException("Illegal Statement - VAR,VAL,FUN",token.getLine(),token.getCharPositionInLine());
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
        expressionFirst.add(Kind.KW_NIL);
        expressionFirst.add(Kind.KW_TRUE);
        expressionFirst.add(Kind.KW_FALSE);
        expressionFirst.add(Kind.INT_LITERAL);
        expressionFirst.add(Kind.STRING_LITERAL);
        expressionFirst.add(Kind.LPAREN);
        expressionFirst.add(Kind.IDENTIFIER);
        expressionFirst.add(Kind.BANG);
        expressionFirst.add(Kind.MINUS);
        if(blockFirst.contains(token.getKind())){
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
            throw new SyntaxException("Illegal Statement/ Syntax error",token.getLine(),token.getCharPositionInLine());
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
    }

    private void PrimaryExpression() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        ArrayList<Kind> kindList = new ArrayList<Kind>();
        kindList.add(Kind.KW_NIL);
        kindList.add(Kind.KW_TRUE);
        kindList.add(Kind.KW_FALSE);
        kindList.add(Kind.INT_LITERAL);
        kindList.add(Kind.STRING_LITERAL);
        kindList.add(Kind.LPAREN);
        kindList.add(Kind.IDENTIFIER);
        if(kindList.contains(k)){
            if(k.equals(Kind.IDENTIFIER) || k.equals(Kind.LPAREN)){
                if(k.equals(Kind.LPAREN)){
                    matchToken(Kind.LPAREN);
                    Expression();
                    matchToken(Kind.RPAREN);
                }
                else{
                    if (k.equals(Kind.LSQUARE) || k.equals(Kind.LPAREN)){
                        if(k.equals(Kind.LSQUARE)){
                            matchToken(Kind.LSQUARE);
                            Expression();
                            matchToken(Kind.RSQUARE);
                        }
                        else if(k.equals(Kind.LPAREN)){
                            matchToken(Kind.LPAREN);
                            if(kindList.contains(token.getKind()) || k.equals(Kind.BANG) || k.equals(Kind.MINUS)){
                                Expression();
                                while (token.getKind().equals(Kind.COMMA)){
                                    matchToken(Kind.COMMA);
                                    Expression();
                                }
                            }
                        }
                    }
                    else {
                        matchToken(token.getKind());
                    }
                }
            }
            else matchToken(token.getKind());
        }
    }

    private void Type() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        if (k.equals(Kind.KW_INT) || k.equals(Kind.KW_STRING) || k.equals(Kind.KW_BOOLEAN)){
            matchToken(k);
        }
        else if(k.equals(Kind.KW_LIST)){
            matchToken(k);
            matchToken(Kind.LSQUARE);
            if (token.getKind().equals(Kind.KW_INT) || token.getKind().equals(Kind.KW_STRING) || token.getKind().equals(Kind.KW_BOOLEAN)){
                matchToken(token.getKind());
            }
            matchToken(Kind.RSQUARE);
        }
        else {
            throw new SyntaxException("Illegal Type - INT, STRING, ",token.getLine(),token.getCharPositionInLine());
        }
    }

    private IPLPToken matchToken(Kind kind) throws SyntaxException, LexicalException {
        if (Kind.EOF.equals(token.getKind()))
        {
            return token;
        }
        else if (kind.equals(token.getKind()))
        {
            return consumeToken();
        }
        throw new SyntaxException("--Illegal syntax--\t" + "Missing: " + kind.toString(),token.getLine(),token.getCharPositionInLine());
    }

    private IPLPToken consumeToken() throws SyntaxException, LexicalException {
        token = lex.nextToken();
        return token;
    }
}
