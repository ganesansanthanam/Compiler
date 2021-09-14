package edu.ufl.cise.plpfa21.assignment1;
import java.util.HashMap;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class PLPLexer implements IPLPLexer {
	ArrayList<IPLPToken> token_List;
	private int tokenId;
	@Override
	public IPLPToken nextToken() throws LexicalException {
		// TODO Auto-generated method stub
		return token_List.get(tokenId);
	}
	public PLPLexer(String input)
	{
		token_List= new ArrayList<IPLPToken>();
		tokenId=0;
		enum State { START, SYMBOL, IDENTIFIER, IntLiteral, StringLiteral, EscapeSequence, Comment };
		String[] KEYWORDS= {"VAR","VAL","FUN","DO","END","LET","SWITCH","CASE","DEFAULT","IF","WHILE","RETURN","NIL"
				,"TRUE","FALSE","INT","STRING","BOOLEAN","LIST"};
		State state = State.START;
		HashMap<Integer, String> sentence_mapper = new HashMap<Integer, String>();
		boolean CommentFlag = true;
		String [] Lines = input.split("\n");
		int BigL = input.length();
		int line_Number=1,character_pos=0;
		if(input.isBlank()) {
			token_List.add(new PLPToken(PLPTokenKinds.Kind.EOF,line_Number,character_pos));
		}
		else
		{
			for(int counter=0;counter<=BigL;BigL++)
			{
			String sentence = Lines[counter];
			 /*String title = switch (person) {
	            case Dali, Picasso      -> "painter";
	            case Mozart, Prokofiev  -> "composer";
	            case Goethe, Dostoevsky -> "writer";
	        };*/
				if(CommentFlag && !sentence.strip().startsWith("/*") && !sentence.isBlank())
					sentence_mapper.put(counter, sentence);
				if(sentence.strip().startsWith("/*"))
					CommentFlag = false;
				if(sentence.strip().endsWith("*/"))
					CommentFlag = true;
			}
		}
	}
}