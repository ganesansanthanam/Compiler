package edu.ufl.cise.plpfa21.assignment1;
import java.util.*;

@SuppressWarnings("unused")
public class PLPLexer implements IPLPLexer {
	ArrayList<IPLPToken> token_List;
	private static int tokenId;
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		int id=getTokenId();
		if(token_List.get(id-1).getKind()== PLPTokenKinds.Kind.ERROR) {
			id--;
			throw new LexicalException(token_List.get(id).getText(), token_List.get(id).getLine(),token_List.get(id).getCharPositionInLine());
		}
		return token_List.get(--id);
	}
	private static int getTokenId(){
		tokenId++;
		return tokenId;
	}
	public boolean isaSymbol(String s,int line,int charPos){
		if(s.matches("[=,;:()<>!+*/-]") || s.charAt(0)=='[' || s.charAt(0)==']') {
			String[] symbols = {"=", ",", ";", ":", "(", ")", "[", "]", "<", ">", "!", "+", "-", "*", "/"};
			Hashtable<String, PLPTokenKinds.Kind> SymbolKind = new Hashtable<String, PLPTokenKinds.Kind>(); // itrate symbols and push into hashtable check for symbol and object use approapriate kind
			SymbolKind.put("=", PLPTokenKinds.Kind.ASSIGN);
			SymbolKind.put(",", PLPTokenKinds.Kind.COMMA);
			SymbolKind.put(";", PLPTokenKinds.Kind.SEMI);
			SymbolKind.put(":", PLPTokenKinds.Kind.COLON);
			SymbolKind.put("(", PLPTokenKinds.Kind.LPAREN);
			SymbolKind.put(")", PLPTokenKinds.Kind.RPAREN);
			SymbolKind.put("[", PLPTokenKinds.Kind.LSQUARE);
			SymbolKind.put("]", PLPTokenKinds.Kind.RSQUARE);
			SymbolKind.put("<", PLPTokenKinds.Kind.LT);
			SymbolKind.put(">", PLPTokenKinds.Kind.GT);
			SymbolKind.put("!", PLPTokenKinds.Kind.BANG);
			SymbolKind.put("+", PLPTokenKinds.Kind.PLUS);
			SymbolKind.put("-", PLPTokenKinds.Kind.MINUS);
			SymbolKind.put("*", PLPTokenKinds.Kind.TIMES);
			SymbolKind.put("/", PLPTokenKinds.Kind.DIV);
			token_List.add(new PLPToken(SymbolKind.get(s), s, line, charPos));
			return true;
		}
		return false;
	}
	public PLPLexer(String input){
		token_List= new ArrayList<IPLPToken>();
		tokenId=0;
		//enum State { START, SYMBOL, IDENTIFIER, IntLiteral, StringLiteral, EscapeSequence, Comment };
		String[] Keywords= {"VAR","VAL","FUN","DO","END","LET","SWITCH","CASE","DEFAULT","IF","WHILE","RETURN","NIL"
				,"TRUE","FALSE","INT","STRING","BOOLEAN","LIST"};
		List<String> KeywordList = Arrays.asList(Keywords);
		//State state = State.START;
		boolean CommentFlag = true;
		int BigL = input.length();
		int line_Number=1,character_pos=0;//,letter_count=0;
		if(input.isBlank()) {
			token_List.add(new PLPToken(PLPTokenKinds.Kind.EOF,"",line_Number,character_pos));
		}
		else
		{
 			String Word = "";
			for(int i=0;i<BigL;i++) {
				if(input.charAt(i)=='\n') {
					line_Number++;
					character_pos=-1;
					//letter_count=0;
				}

				if(Character.isLetter(input.charAt(i)) || input.charAt(i)=='_' || input.charAt(i)=='$') {
					Word += input.charAt(i++);
					while (BigL > i && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '$' || input.charAt(i) == '_')) {
						Word += input.charAt(i++);
					}
					--i;
					if (KeywordList.contains(Word)) {
						switch (Word) {
							case "VAR" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_VAR, Word, line_Number, character_pos));
							}
							case "VAL" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_VAL, Word, line_Number, character_pos));
							}
							case "SWITCH" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_SWITCH, Word, line_Number, character_pos));
							}
							case "CASE" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_CASE, Word, line_Number, character_pos));
							}
							case "BOOLEAN" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_BOOLEAN, Word, line_Number, character_pos));
							}
							case "LIST" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_LIST, Word, line_Number, character_pos));
							}
							case "DEFAULT" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_DEFAULT, Word, line_Number, character_pos));
							}
							case "DO" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_DO, Word, line_Number, character_pos));
							}
							case "ELSE" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_ELSE, Word, line_Number, character_pos));
							}
							case "END" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_END, Word, line_Number, character_pos));
							}
							case "TRUE" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_TRUE, Word, line_Number, character_pos));
							}
							case "FALSE" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_FALSE, Word, line_Number, character_pos));
							}
							case "FLOAT" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_FLOAT, Word, line_Number, character_pos));
							}
							case "FUN" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_FUN, Word, line_Number, character_pos));
							}
							case "IF" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_IF, Word, line_Number, character_pos));
							}
							case "INT" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_INT, Word, line_Number, character_pos));
							}
							case "LET" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_LET, Word, line_Number, character_pos));
							}
							case "NIL" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_NIL, Word, line_Number, character_pos));
							}
							case "RETURN" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_RETURN, Word, line_Number, character_pos));
							}
							case "STRING" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_STRING, Word, line_Number, character_pos));
							}
							case "WHILE" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_WHILE, Word, line_Number, character_pos));
							}
						}
						character_pos+=Word.length();
					}
					else{
						if(Word.matches("^[a-zA-Z0-9]+$")) {
							token_List.add(new PLPToken(PLPTokenKinds.Kind.IDENTIFIER, Word, line_Number, character_pos));
							character_pos+=Word.length();
						}
					}
				Word= "";
				}

				else if(Character.isDigit(input.charAt(i))) {
					while(BigL>i && (Character.isDigit(input.charAt(i)))){
						Word+=input.charAt(i++);
					}
					--i;
					try {
						if (Word.matches("[0-9]+") && Integer.parseInt(Word) <= 2147483647 && Integer.parseInt(Word) >= 0) {
							token_List.add(new PLPToken(PLPTokenKinds.Kind.INT_LITERAL, Word, line_Number, character_pos));
							character_pos += Word.length();
						}
					}
					catch (Exception e) {
						token_List.add(new PLPToken(PLPTokenKinds.Kind.ERROR,"Integer value too large",line_Number,character_pos));
					}
				Word= "";
				}

				else if(input.charAt(i)=='/' && input.charAt(i+1)=='*'){
					i+=2;
					while (BigL-1>i && input.charAt(i)!='*' && input.charAt(i+1)!='/'){
						++i;
					}
					Boolean condition = BigL==i && input.charAt(i-1)!='*' && input.charAt(i)!='/';
					if(condition){
						token_List.add(new PLPToken(PLPTokenKinds.Kind.ERROR,"Comments not closed properly",line_Number,character_pos));
					}
					i++;
				}

				else if(Character.toString(input.charAt(i)).equals("\'")){
					while (BigL>i && !(Character.toString(input.charAt(++i)).equals("\'"))){
						switch (input.charAt(i)){
							case '\b'-> Word+='\b';
							case '\t'-> Word+='\t';
							case '\n'-> Word+='\n';
							case '\r'-> Word+='\r';
							case '\f'-> Word+='\f';
							case '\"'-> Word+='\"';
							case '\''-> Word+='\'';
							case '\\'-> Word+='\\';
							case ' ' -> Word+=' ';
						}
						if(Character.isLetter(input.charAt(i)) || Character.isDigit(input.charAt(i))) {
							Word += input.charAt(i);
						}
						else if(Character.toString(input.charAt(i)).matches("[&+,:;=|<>/*()!-]") || input.charAt(i)==']' || input.charAt(i)=='[') {
							Word += input.charAt(i);
						}
						else if(Character.toString(input.charAt(i)).matches("[`~!@#$%^_{}.?]") || input.charAt(i) =='%') {
							Word += input.charAt(i);
						}
					}
					if(BigL==i && !(Character.toString(input.charAt(i)).equals("\'"))){
						token_List.add(new PLPToken(PLPTokenKinds.Kind.ERROR,"String literals not closed properly ",line_Number,character_pos));
					}
					Word = '\'' + Word + '\'';
					token_List.add(new PLPToken(PLPTokenKinds.Kind.STRING_LITERAL,Word,line_Number,character_pos));
					character_pos+=Word.length();
					Word="";
				}

				else if(Character.toString(input.charAt(i)).matches("[&+,:;=|<>/*()!-]") || input.charAt(i)==']' || input.charAt(i)=='['){
					if(input.charAt(i)==input.charAt(i+1) && Character.toString(input.charAt(i)).matches("[&=|]")){
						Word= Character.toString(input.charAt(i))+Character.toString(input.charAt(i++));
						switch (input.charAt(i)){
							case '&' -> token_List.add(new PLPToken(PLPTokenKinds.Kind.AND, Word, line_Number, character_pos));
							case '|' -> token_List.add(new PLPToken(PLPTokenKinds.Kind.OR, Word, line_Number, character_pos));
							case '=' -> token_List.add(new PLPToken(PLPTokenKinds.Kind.EQUALS, Word, line_Number, character_pos));
						}
						character_pos+=Word.length();
					}
					else if(input.charAt(i)=='!' && input.charAt(i+1)=='='){ // =,;:()[]<>!+-*/
						Word= Character.toString(input.charAt(i))+Character.toString(input.charAt(++i));
						token_List.add(new PLPToken(PLPTokenKinds.Kind.NOT_EQUALS, Word, line_Number, character_pos));
						character_pos+=Word.length();
					}
					else {
						if(isaSymbol(Character.toString(input.charAt(i)),line_Number,character_pos))
							++character_pos;
					}
					Word="";
				}

				else if(Character.toString(input.charAt(i)).matches("[`~!@#$%^_{}.'?]") || input.charAt(i) =='%') {
					token_List.add(new PLPToken(PLPTokenKinds.Kind.ERROR, "Illegal characters", line_Number, character_pos));
					++character_pos;
				}

				else if(input.charAt(i)=='"'){
					while ( BigL>i && input.charAt(++i)!='"'){
						switch (input.charAt(i)){
							case '\b'-> Word+='\b';
							case '\t'-> Word+='\t';
							case '\n'-> Word+='\n';
							case '\r'-> Word+='\r';
							case '\f'-> Word+='\f';
							case '\"'-> Word+='\"';
							case '\''-> Word+='\'';
							case '\\'-> Word+='\\';
							case ' ' -> Word+=' ';
						}
						if(Character.isLetter(input.charAt(i)) || Character.isDigit(input.charAt(i))) {
							Word += input.charAt(i);
						}
						else if(Character.toString(input.charAt(i)).matches("[&+,:;=|<>/*()!-]") || input.charAt(i)==']' || input.charAt(i)=='[') {
							Word += input.charAt(i);
						}
						else if(Character.toString(input.charAt(i)).matches("[`~!@#$%^_{}.?]") || input.charAt(i) =='%') {
							Word += input.charAt(i);
						}
					}
					if(BigL==i && input.charAt(i)!='"'){
						token_List.add(new PLPToken(PLPTokenKinds.Kind.ERROR,"String literals not closed properly ",line_Number,character_pos));
					}
					Word = '\"' + Word + '\"';
					token_List.add(new PLPToken(PLPTokenKinds.Kind.STRING_LITERAL,Word,line_Number,character_pos));
					character_pos+=Word.length();
					Word="";
				}

				else{
					character_pos++;
				}
				//character_pos++;
		}
		}
		token_List.add(new PLPToken(PLPTokenKinds.Kind.EOF,"",line_Number, 0));
	}
}