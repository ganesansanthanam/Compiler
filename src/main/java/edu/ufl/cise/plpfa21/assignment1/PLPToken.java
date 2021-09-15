package edu.ufl.cise.plpfa21.assignment1;

import java.util.HashMap;

@SuppressWarnings("unused")
public class PLPToken implements IPLPToken {
	public String texty;
	public int lineNumber;
	public int characterPosition;
	public Kind kind;
	public PLPToken(Kind kind,String texty,int lineNumber,int characterPosition) {
		this.kind = kind;
		this.lineNumber = lineNumber;
		this.characterPosition = characterPosition;
		this.texty = texty;
	}
	@Override
	public Kind getKind() {
		// TODO Auto-generated method stub
		return this.kind;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.texty;
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return this.lineNumber;
	}

	@Override
	public int getCharPositionInLine() {
		// TODO Auto-generated method stub
		return this.characterPosition;
	}

	@Override
	public String getStringValue() {
		// TODO Auto-generated method stub
		return this.texty;
	}

	@Override
	public int getIntValue() {
		// TODO Auto-generated method stub
		return Integer.parseInt(this.texty);
	}

}
