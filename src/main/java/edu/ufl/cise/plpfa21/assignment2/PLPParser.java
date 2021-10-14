package edu.ufl.cise.plpfa21.assignment2;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment1.*;
import edu.ufl.cise.plpfa21.assignment3.ast.*;
import edu.ufl.cise.plpfa21.assignment3.astimpl.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PLPParser implements IPLPParser{
    IPLPLexer lex;
    IPLPToken token;
    public IASTNode parse() throws SyntaxException, LexicalException {
        this.token= lex.nextToken();
        return Program();
    }

    public PLPParser (IPLPLexer lex) {
        this.lex = lex;
    }

    private IProgram Program() throws SyntaxException, LexicalException {
        Kind k = token.getKind();
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        List<IDeclaration> declarations_list = new ArrayList<IDeclaration>();
        IProgram p = null;
        if(k.equals(Kind.EOF)){
            matchEOF();
        }
        else if((k.equals(Kind.KW_VAL) || k.equals(Kind.KW_VAR) || k.equals(Kind.KW_FUN))){
            while (!(token.getKind().equals(Kind.EOF))) {
                declarations_list.add(Declaration());
            }
            matchEOF();
        }
        else {
            String errorMessage = "Illegal Statement - VAR,VAL,FUN Line: " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
            throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
        }
        p = new Program__(Line, CharPositionInLine, Text, declarations_list);
        return p;
    }

    private IDeclaration Declaration() throws SyntaxException, LexicalException {
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        IDeclaration d = null;
        INameDef n = null;
        IExpression e = null;
        if (this.token.getKind().equals(Kind.KW_VAL)) {
            matchToken(Kind.KW_VAL);
            n = NameDef();
            matchToken(Kind.ASSIGN);
            e = Expression();
            matchToken(Kind.SEMI);
            d = new ImmutableGlobal__(Line, CharPositionInLine, Text, n, e);
        }
        else if(this.token.getKind().equals(Kind.KW_VAR)){
            matchToken(Kind.KW_VAR);
            n = NameDef();
            if(token.getKind().equals(Kind.ASSIGN)){
                matchToken(Kind.ASSIGN);
                e = Expression();
            }
            matchToken(Kind.SEMI);
            d = new MutableGlobal__(Line, CharPositionInLine, Text, n, e);
        }
        else if(this.token.getKind().equals(Kind.KW_FUN)) {
            d = Function();
        }
        else {
            String errorMessage = "Illegal Statement - VAR,VAL,FUN Line: " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
            throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
        }
        return d;
    }

    private IFunctionDeclaration Function() throws SyntaxException, LexicalException {
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text=token.getText();
        IFunctionDeclaration f = null;
        List<INameDef> args_list = new ArrayList<INameDef>();
        IType t = null;
        IBlock b = null;
        IIdentifier i = null;
        matchToken(Kind.KW_FUN);
        Text = token.getText();
        i = new Identifier__(Line, CharPositionInLine,Text,Text);
        matchToken(Kind.IDENTIFIER);
        matchToken(Kind.LPAREN);
        if(token.getKind().equals(Kind.IDENTIFIER)) {
            args_list.add(NameDef());
            while (token.getKind().equals(Kind.COMMA)) {
                matchToken(Kind.COMMA);
                args_list.add(NameDef());
            }
        }
        matchToken(Kind.RPAREN);
        if(token.getKind().equals(Kind.COLON)){
            matchToken(Kind.COLON);
            t = Type();
        }
        matchToken(Kind.KW_DO);
        b = Block();
        matchToken(Kind.KW_END);
        f = new FunctionDeclaration___(Line ,CharPositionInLine,Text, i, args_list, t, b);
        return f;
    }

    private IBlock Block() throws SyntaxException, LexicalException {
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        IBlock b = null;
        List<IStatement> statement_list = new ArrayList<IStatement>();
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
            statement_list.add(Statement());
        }
        b = new Block__(Line ,CharPositionInLine,Text, statement_list);
        return(b);
    }

    private IStatement Statement() throws SyntaxException, LexicalException {
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        IStatement s = null;
        IExpression e = null;
        IExpression er = null;
        IBlock b = null;
        List<IExpression> exp_list = new ArrayList<IExpression>();
        List<IBlock> block_list = new ArrayList<IBlock>();
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
                INameDef n = NameDef();
                if (token.getKind().equals(Kind.ASSIGN)){
                    matchToken(Kind.ASSIGN);
                    e = Expression();
                }
                //matchToken(Kind.SEMI);
                matchToken(PLPTokenKinds.Kind.KW_DO);
                b = Block();
                matchToken(PLPTokenKinds.Kind.KW_END);
                s = new LetStatement__(Line ,CharPositionInLine, Text, b , e, n);
            }
            else if(token.getKind().equals(Kind.KW_IF) || token.getKind().equals(Kind.KW_WHILE)){
                matchToken(token.getKind());
                e = Expression();
                matchToken(Kind.KW_DO);
                b = Block();
                matchToken(Kind.KW_END);
                if(token.getKind().equals(Kind.KW_IF)){
                    s = new IfStatement__(Line ,CharPositionInLine, Text, e, b);
                }
                else{
                    s = new WhileStatement__(Line ,CharPositionInLine, Text, e, b);
                }
            }
            else if(token.getKind().equals(Kind.KW_RETURN)){
                matchToken(Kind.KW_RETURN);
                e = Expression();
                matchToken(Kind.SEMI);
                s = new ReturnStatement__(Line ,CharPositionInLine, Text, e);
            }
            else if(token.getKind().equals(Kind.KW_SWITCH)){
                matchToken(Kind.KW_SWITCH);
                e = Expression();
                while (token.getKind().equals(Kind.KW_CASE)){
                    matchToken(Kind.KW_CASE);
                    exp_list.add(Expression());
                    matchToken(Kind.COLON);
                    block_list.add(Block());
                }
                matchToken(Kind.KW_DEFAULT);
                Block();
                matchToken(Kind.KW_END);
            }
        }
        else if(expressionFirst.contains(token.getKind())){
            e = Expression();
            if (token.getKind().equals(Kind.ASSIGN)){
                matchToken(Kind.ASSIGN);
                er = Expression();
            }
            matchToken(Kind.SEMI);
            s = new AssignmentStatement__(Line ,CharPositionInLine, Text, e, er);
        }
        else {
            String errorMessage = "Illegal Statement - needs LET, IF, WHILE, SWITCH, RETURN OR EXPRESSION Line: " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
            throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
        }
        return s;
    }

    private INameDef NameDef() throws SyntaxException, LexicalException {
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        INameDef n = null;
        IType t = null;
        IIdentifier i = new Identifier__(Line, CharPositionInLine,Text,Text);
        matchToken(Kind.IDENTIFIER);
        if (token.getKind().equals(Kind.COLON)) {
            matchToken(Kind.COLON);
            t = Type();
        }
        n = new NameDef__(Line ,CharPositionInLine,Text, i, t);
        return(n);
    }

    private IExpression Expression() throws SyntaxException, LexicalException {
        return LogicalExpression();
    }

    private IExpression LogicalExpression() throws SyntaxException, LexicalException {
        IExpression r = null;
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        r = ComparisonExpression();
        while (token.getKind().equals(Kind.AND) || token.getKind().equals(Kind.OR)){
            if(token.getKind().equals(Kind.AND)) {
                matchToken(Kind.AND);
                r = new BinaryExpression__(Line, CharPositionInLine, Text, r, ComparisonExpression(), PLPTokenKinds.Kind.AND);
            }
            else {
                matchToken(Kind.OR);
                r = new BinaryExpression__(Line, CharPositionInLine, Text, r, ComparisonExpression(), PLPTokenKinds.Kind.OR);
            }
        }
        return r;
    }

    private IExpression ComparisonExpression() throws SyntaxException, LexicalException {
        IExpression r = null;
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        r = AdditiveExpression();
        while (token.getKind().equals(Kind.LT) || token.getKind().equals(Kind.GT) || token.getKind().equals(Kind.EQUALS) || token.getKind().equals(Kind.NOT_EQUALS)){
            if(token.getKind().equals(Kind.LT)) {
                matchToken(Kind.LT);
                r = new BinaryExpression__(Line ,CharPositionInLine, Text, r, AdditiveExpression(),PLPTokenKinds.Kind.LT);
            }
            else if(token.getKind().equals(Kind.GT)) {
                matchToken(Kind.GT);
                r = new BinaryExpression__(Line ,CharPositionInLine, Text, r, AdditiveExpression(),PLPTokenKinds.Kind.GT);
            }
            else if(token.getKind().equals(Kind.EQUALS)) {
                matchToken(Kind.EQUALS);
                r = new BinaryExpression__(Line ,CharPositionInLine, Text, r, AdditiveExpression(), Kind.EQUALS);
            }
            else{
                matchToken(Kind.NOT_EQUALS);
                r = new BinaryExpression__(Line ,CharPositionInLine, Text, r, AdditiveExpression(), Kind.NOT_EQUALS);
            }
        }
        return r;
    }

    private IExpression AdditiveExpression() throws SyntaxException, LexicalException {
        IExpression r = null;
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        r = MultiplicativeExpression();
        while(token.getKind().equals(Kind.PLUS) || token.getKind().equals(Kind.MINUS)){
            if(token.getKind().equals(Kind.PLUS)) {
                matchToken(Kind.PLUS);
                r = new BinaryExpression__(Line ,CharPositionInLine, Text, r, MultiplicativeExpression(),Kind.PLUS);
            }
            else{
                matchToken(Kind.MINUS);
                r = new BinaryExpression__(Line ,CharPositionInLine, Text, r, MultiplicativeExpression(), Kind.MINUS);
            }
        }
        return r;
    }

    private IExpression MultiplicativeExpression() throws SyntaxException, LexicalException {
        IExpression r = null;
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        r = UnaryExpression();
        while (token.getKind().equals(Kind.TIMES) || token.getKind().equals(Kind.DIV)){
            if(token.getKind().equals(Kind.TIMES)) {
                matchToken(Kind.TIMES);
                r = new BinaryExpression__(Line ,CharPositionInLine, Text, r, UnaryExpression(),PLPTokenKinds.Kind.TIMES);
            }
            else{
                matchToken(Kind.DIV);
                r = new BinaryExpression__(Line ,CharPositionInLine, Text, r, UnaryExpression(),PLPTokenKinds.Kind.DIV);
            }
        }
        return r;
    }

    private IExpression UnaryExpression() throws SyntaxException, LexicalException {
        IExpression r = null;
        PLPTokenKinds.Kind kind = null;
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        if(token.getKind().equals(Kind.BANG)) {
            matchToken(PLPTokenKinds.Kind.BANG);
            kind = PLPTokenKinds.Kind.BANG;
        }
        else if(token.getKind().equals(Kind.MINUS)) {
            matchToken(PLPTokenKinds.Kind.MINUS);
            kind = PLPTokenKinds.Kind.MINUS;
        }
        r = PrimaryExpression();
        if(kind != null) {
            r = new UnaryExpression__(Line ,CharPositionInLine, Text, r, kind );
        }
        return r;
    }

    private IExpression PrimaryExpression() throws SyntaxException, LexicalException {
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        IExpression retnode = null;
        List<IExpression> args = new ArrayList<IExpression>();
        IIdentifier ident_node = null;
        IExpression expnode = null;

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
                    matchToken(PLPTokenKinds.Kind.LPAREN);
                    retnode = Expression();
                    matchToken(PLPTokenKinds.Kind.RPAREN);
                }
                else{
                    ident_node = new Identifier__(Line, CharPositionInLine,Text,Text);
                    retnode = new IdentExpression__(Line, CharPositionInLine,Text,ident_node);
                    matchToken(Kind.IDENTIFIER);
                    if(token.getKind().equals(Kind.LSQUARE)){
                        matchToken(PLPTokenKinds.Kind.LSQUARE);
                        expnode = Expression();
                        matchToken(PLPTokenKinds.Kind.RSQUARE);
                        retnode = new ListSelectorExpression__(Line, CharPositionInLine, Text, ident_node,expnode);
                    }
                    else if(token.getKind().equals(Kind.LPAREN)){
                        matchToken(Kind.LPAREN);
                        if(kindList.contains(token.getKind()) || token.getKind().equals(Kind.BANG) || token.getKind().equals(Kind.MINUS)){
                            args.add(Expression());
                            while (token.getKind().equals(Kind.COMMA)){
                                matchToken(Kind.COMMA);
                                args.add(Expression());
                            }
                        }
                        matchToken(Kind.RPAREN);
                        retnode = new FunctionCallExpression__(Line, CharPositionInLine,Text,ident_node,args);
                    }

                }
            }
            else{
                if(this.token.getKind().equals(Kind.KW_NIL)){
                    retnode = new NilConstantExpression__(token.getLine(), token.getCharPositionInLine(),
                            token.getText());
                    matchToken(PLPTokenKinds.Kind.KW_NIL);
                }
                else if(this.token.getKind().equals(Kind.KW_TRUE)){
                    retnode = new BooleanLiteralExpression__(token.getLine(), token.getCharPositionInLine(),
                            token.getText(),true);
                    matchToken(PLPTokenKinds.Kind.KW_TRUE);
                }
                else if(this.token.getKind().equals(Kind.KW_FALSE)) {
                    retnode = new BooleanLiteralExpression__(token.getLine(), token.getCharPositionInLine(),
                            token.getText(),false);
                    matchToken(PLPTokenKinds.Kind.KW_FALSE);
                }
                else if(this.token.getKind().equals(Kind.INT_LITERAL)) {
                    retnode = new IntLiteralExpression__(token.getLine(), token.getCharPositionInLine(),token.getText(),token.getIntValue());
                    matchToken(PLPTokenKinds.Kind.INT_LITERAL);
                }
                else if(this.token.getKind().equals(Kind.STRING_LITERAL)) {
                    retnode = new StringLiteralExpression__(token.getLine(), token.getCharPositionInLine(),
                            token.getText(),token.getStringValue());
                    matchToken(PLPTokenKinds.Kind.STRING_LITERAL);
                }
            }
        }
        else {
            throw new SyntaxException("Invalid Expression", token.getLine(), token.getCharPositionInLine());
        }
        return retnode;
    }

    private IType Type() throws SyntaxException, LexicalException {
        IType r = null;
        int Line = token.getLine();
        int CharPositionInLine = token.getCharPositionInLine();
        String Text = token.getText();
        IType t = null;
        if(token.getKind().equals(Kind.KW_INT)){
            matchToken(PLPTokenKinds.Kind.KW_INT);
            r = new PrimitiveType__(Line, CharPositionInLine, Text, IType.TypeKind.INT);
        }
        else if (token.getKind().equals(Kind.KW_STRING)){
            matchToken(PLPTokenKinds.Kind.KW_STRING);
            r = new PrimitiveType__(Line, CharPositionInLine, Text, IType.TypeKind.STRING);
        }
        else if (token.getKind().equals(Kind.KW_BOOLEAN)){
            matchToken(Kind.KW_BOOLEAN);
            r = new PrimitiveType__(Line, CharPositionInLine, Text, IType.TypeKind.BOOLEAN);
        }

        else if(token.getKind().equals(Kind.KW_LIST)){
            matchToken(Kind.KW_LIST);
            matchToken(Kind.LSQUARE);
            if(!(token.getKind().equals(Kind.RSQUARE))) {
                Line = token.getLine();
                CharPositionInLine = token.getCharPositionInLine();
                Text = token.getText();
                t = Type();
            }
            matchToken(Kind.RSQUARE);
            r = new ListType__(Line, CharPositionInLine, Text, t);
        }
        else {
            String errorMessage = "Illegal Type - INT, STRING, LIST Line " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
            throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
        }
        return r;
    }

    private IPLPToken matchToken(Kind kind) throws SyntaxException, LexicalException {
        if (kind.equals(token.getKind()))
        {
            this.token = lex.nextToken();
            return token;
        }
        String errorMessage = "Illegal syntax Missing: " + kind.toString() + " Line: " + token.getLine() + " Charposition: " + token.getCharPositionInLine();
        throw new SyntaxException(errorMessage,token.getLine(),token.getCharPositionInLine());
    }

    private void matchEOF() throws SyntaxException {
        if (!(token.getKind().equals(Kind.EOF))) {
            throw new SyntaxException("End of file not found",token.getLine(),token.getCharPositionInLine());
        }
    }
}
