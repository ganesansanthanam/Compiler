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
		return token_List.get(--id);
	}
	private static int getTokenId(){
		tokenId++;
		return tokenId;
	}
	public Hashtable<String, PLPTokenKinds.Kind> isaSymbol(String s,int line,int charPos){
		String[] symbols = {"=",",",";",":","(",")","[","]","<",">","!","+","-","*","/"};
		Hashtable<String, PLPTokenKinds.Kind> k = new Hashtable<String, PLPTokenKinds.Kind>(); // itrate symbols and push into hashtable check for symbol and object use approapriate kind
		/*switch (s)
				{
					case '=': token_List.add(new PLPToken(PLPTokenKinds.Kind.EQUALS,r, line, charPos));
				}*/
		return k;
	}
	public PLPLexer(String input)
	{
		token_List= new ArrayList<IPLPToken>();
		tokenId=0;
		enum State { START, SYMBOL, IDENTIFIER, IntLiteral, StringLiteral, EscapeSequence, Comment };
		String[] Keywords= {"VAR","VAL","FUN","DO","END","LET","SWITCH","CASE","DEFAULT","IF","WHILE","RETURN","NIL"
				,"TRUE","FALSE","INT","STRING","BOOLEAN","LIST"};
		List<String> KeywordList = Arrays.asList(Keywords);
		State state = State.START;
		HashMap<Integer, String> sentence_mapper = new HashMap<Integer, String>();
		boolean CommentFlag = true;
		String [] Lines = input.split("\n");
		int BigL = input.length();
		int line_Number=1,character_pos=0,letter_count=0;
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
					letter_count=0;
				}
				if(Character.isLetter(input.charAt(i))) {
					Word += input.charAt(i++);
					while (i < BigL && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '$' || input.charAt(i) == '_')) {
						Word += input.charAt(i++);
					}
					--i;
					if (KeywordList.contains(Word.toUpperCase()) && Word.matches("^([a-zA-Z_$][a-zA-Z\\\\d_$]*)$")) {
						switch (Word.toUpperCase()) {
							case "VAR" -> {
								token_List.add(new PLPToken(PLPTokenKinds.Kind.KW_VAR, Word, line_Number, character_pos));
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
					while(i<input.length()&&(Character.isDigit(input.charAt(i)))){
						Word+=input.charAt(i++);
					}
					--i;
					if(Word.matches("[0-9]+") && Integer.parseInt(Word)<=2147483647 && Integer.parseInt(Word)>=0) {
						token_List.add(new PLPToken(PLPTokenKinds.Kind.INT_LITERAL, Word, line_Number, character_pos));
						character_pos+=Word.length();
					}

				Word= "";
				}
				else if(Character.toString(input.charAt(i)).matches("[$&+,:;=?@#|'<>.^*()%!-]")){
					isaSymbol(Character.toString(input.charAt(i)),line_Number,character_pos);
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