package com.yunpayroll.formular;

import java.io.Reader;
import java.io.StringReader;
import java.util.Formatter;
import java.util.Random;

public class Test {
	public static void main(String[] args) {
		String newLine;
		Formatter formatter = new Formatter();
        try {
        	System.lineSeparator();
            newLine = formatter.format("%n").toString();
        } catch (Exception e) {
            // Should not reach here, but just in case.
            newLine = "\n";
        } finally {
            formatter.close();
        }
		

        System.out.println(newLine);
		System.out.println(12.0%.9);
		Double.valueOf(new Random().nextLong());
		System.out.println(Math.sin(Math.PI));
	    String str = "1.234";
	    Reader r = new StringReader(str);
	    JavaCharStream charStream = new JavaCharStream(r, 1, 1); 
	    ParserTokenManager tokenManager = new ParserTokenManager(charStream);
	    Token t =null;
	    do{
	    	t= tokenManager.getNextToken();
	    	System.out.println(t);
	    }while(t.kind!=0);
//	    
//	    for(int i=0;i<160;i++) {
//	    	System.out.println(i+"----"+(char)i);
//	    }
//		String a = "1e";
//		System.out.println(Double.valueOf(a));
	}
}
