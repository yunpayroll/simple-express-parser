package com.yunpayroll.formular;

public class Test1 {
	public static void main(String[] args) throws Exception {
//		 String str = "1+3*1+4";
////		String str = "\u6c49汉_aaaaa+b1111+11011";
//		Reader r = new StringReader(str);
//		JavaCharStream charStream = new JavaCharStream(r, 1, 1,1024); 
//		TokenManager tm = new TokenManager(charStream);
//		Token t;
//		do{
//			t = tm.getNextToken();
//			System.out.println(t);
//		}while(t.kind!=0);
		String line = "10 * 10 + 1 + 2 * 3 + 5 * 2";
		long s = System.currentTimeMillis();
		MyParser myParser = new MyParser();
		for(int i=0;i<10000;i++) {
			myParser.parse(line);
			// evaluate the expression
			
			// did an error occur while evaluating?
			 Object value = myParser.getValue();
//			System.out.println(value);
		}
		System.out.println(System.currentTimeMillis()-s);
    }
 
	
}
