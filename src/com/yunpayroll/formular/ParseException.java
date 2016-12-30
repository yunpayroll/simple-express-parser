package com.yunpayroll.formular;

public class ParseException extends Exception {
	/**
	 * 
	 */
    private static final long serialVersionUID = -8442990869237806404L;
	protected boolean specialConstructor=false;
	public Token currentToken;

	public ParseException(Token currentTokenVal,String message) {
		super(message);
		specialConstructor = true;
		currentToken = currentTokenVal;
	}
	public ParseException(Token currentTokenVal) {
		super("");
		specialConstructor = true;
		currentToken = currentTokenVal;
	}

	public ParseException(String str) {
		super(str);
	}

	@Override
	public String getMessage() {
		if (!specialConstructor) {
			return super.getMessage();
		}
//		int bl = currentToken.beginLine;
		int bc = currentToken.beginColumn;
//		int el = currentToken.endLine;
		int ec = currentToken.endColumn;
		
		StringBuilder  sb = new StringBuilder(super.getMessage());
		sb.append("，错误可能发生在").append(bc);
		sb.append("到").append(ec);
		
		return sb.toString();
		
	}

}
