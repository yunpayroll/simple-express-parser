package com.yunpayroll.formular;

import java.io.IOException;

public class TokenManager {

	/** 操作符 */
	public static final int OPERATOR = 1;
	/** 数字字符 */
	public static final int NUMBER = 2;
	/** 字符串 */
	public static final int STRING = 3;
	/** 左括号  */
	public static final int LRND = 4;
	/** 右括号 */
	public static final int RRND = 5;
	/** 逗号 */
	public static final int COMMA = 6;
	/** 函数 */
	public static final int FUNCTION = 7;
	/** 字符串常量 */
	public static final int CONSTANT = 8;
	JavaCharStream input_stream;
	public TokenManager(JavaCharStream charStream) {
		this.input_stream = charStream;
	}
	
	public int tokenType;
	
	
	public Token getNextToken() throws ParseException,IOException{
		return nextToken(true);
	}
	
	private Token nextToken(boolean isBeginToken)throws ParseException,IOException{

		
		while(true) {
			char c  ;
			try{
				if(isBeginToken) {
					c = input_stream.BeginToken();	
				} else {
					c = input_stream.readChar();
				}
				
			}catch(IOException e) {
				tokenType=0;
				return fillToken();
			} 
			switch(c) {
				case '|':
					tokenType = OPERATOR;
					char cor = input_stream.readChar();
					if(cor=='|') {
						tokenType = OPERATOR;
						return fillToken();
					} else {
						input_stream.backup(1);
					}
					return fillToken();
				case '~':
					tokenType = OPERATOR;
					return fillToken();
				case '^':
					tokenType = OPERATOR;
					return fillToken();	
				case '*':
					tokenType = OPERATOR;
					return fillToken();
				case '/':
					tokenType = OPERATOR;
					return fillToken();
				case '+':
					tokenType = OPERATOR;
					return fillToken();
				case '-':
					tokenType = OPERATOR;
					return fillToken();
				case '&':
					tokenType = OPERATOR;
					char cand = input_stream.readChar();
					if(cand=='&') {
						tokenType = OPERATOR;
						return fillToken();
					} else {
						input_stream.backup(1);
					}
					return fillToken();
				case '%':
					tokenType = OPERATOR;
					return fillToken();
				case ',':
					tokenType = COMMA;
					return fillToken();
				case '>':
					if(input_stream.readChar()!='=') {
						input_stream.backup(1);
					}
					tokenType = OPERATOR;
					return fillToken();
				case '<':
					if(input_stream.readChar()!='=') {
						input_stream.backup(1);
					}
					tokenType = OPERATOR;
					return fillToken();
				case '(':
					tokenType = LRND;
					return fillToken();
				case ')':
					tokenType = RRND;
					return fillToken();
				case '\t':
					continue;
				case '\n':
					continue;
				case ' ':
					continue;
				case '=':
					char c3 = input_stream.readChar();
					if(c3=='=') {
						tokenType = OPERATOR;
						return fillToken();
					} else {
						throw new ParseException("= is error" );
					}
				case '"':
					tokenType = STRING;
					try{
						for(;;) {
							char c2 = input_stream.readChar();
							if( (! (isString(c2)||isNumber(c2) )) && c2!='"') {
								throw new Exception("\" error");
							}
							if(c2=='"') {
								tokenType = CONSTANT;
								return fillToken();
							}
						}
					}catch(Exception e) {
						throw new ParseException("\" error");
					}
					
				case '!':
					if(input_stream.readChar()!='='  ) {
						input_stream.backup(1);
						tokenType = OPERATOR;
						return fillToken();
					} else{
						tokenType = OPERATOR;
						return fillToken();
					}
					

				default:
					if(  isString(c) ) {
						tokenType = STRING;
						try{
							for(;;) {
								char c2 = input_stream.readChar();
								if(! (isString(c2)||isNumber(c2) )) {
									break ;
								}
							}
						}catch(Exception e) {
							return	fillToken();
						}
						
						input_stream.backup(1);
						tokenType = STRING;
						return fillToken();
					} else if((c>=48 && c<=57)||c=='.') {
						tokenType = NUMBER;
						try{
							int docnum=0;
							for(;;) {
								char c2 = input_stream.readChar();
								if(c2=='.') {
									docnum++;
									if(docnum>1) {
										throw new ParseException(".");
									}
								}
								if(isString(c2)) {
									Token t = fillToken();
									throw new ParseException(t,"变量或者函数只能以字母开头 ");
								}
								if(!isNumber(c2)) {
									break ;
								}
							}
						}catch(IOException e) {
							return	fillToken();
						}
						
						input_stream.backup(1);
						tokenType = NUMBER;
						return fillToken();
					} else {
						throw new ParseException(""+c+ " is error");
					}
					
			}
		}
   
	
	}
	
	protected Token fillToken() {
		final Token t;
		final String curTokenImage;
		final int beginLine;
		final int endLine;
		final int beginColumn;
		final int endColumn;
		curTokenImage = tokenType==0?"":input_stream.GetImage();
		beginLine = input_stream.getBeginLine();
		beginColumn = input_stream.getBeginColumn();
		endLine = input_stream.getEndLine();
		endColumn = input_stream.getEndColumn();
		t = Token.newToken(this.tokenType, curTokenImage);

		t.beginLine = beginLine;
		t.endLine = endLine;
		t.beginColumn = beginColumn;
		t.endColumn = endColumn;

		return t;
	}
	
	private  boolean isString(char c) {
		return (c>='a' && c<='z') ||(c>='A' && c<='Z')||c>='\u4e00'&& c<='\u9fa5' ||c=='_';
	}
	private  boolean isNumber(char c){
		return (c>=48 && c<=57)||c=='.';
	}
}
