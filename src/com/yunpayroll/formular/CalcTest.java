package com.yunpayroll.formular;

import java.io.BufferedReader;
import java.io.FileReader;

public class CalcTest {
	int lineCount = 0;
	public static void main(String[] args) {
		String fileName;
		// get filename from argument, or use default
		if (args!=null && args.length>0) {
			fileName = args[0];
		} else {
			fileName = "D:\\workspace\\formular\\src\\TestExpressions.txt";
		}
		CalcTest ct = new CalcTest();
		ct.testWithFile(fileName);
    }
	public void testWithFile(String fileName) {
		BufferedReader reader;
		Object v1, v2;
		boolean hasError = false;

		// Load the input file
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (Exception e) {
			println("File \""+fileName+"\" not found");
			return;
		}
		
		// reset the line count
		lineCount = 0;
		long start = System.currentTimeMillis();
		// cycle through the expressions in pairs of two
		println("Evaluating and comparing expressions...");
		while (true) {
			// get values of a pair of two lines
			try {
				v1 = parseNextLine(reader); //returns null when end of file is reached
				v2 = parseNextLine(reader);
			} catch (Exception e) {
				println(e.getMessage());
				hasError = true;
				break;
			}
			
			// v1 or v2 is null when end of file is reached
			if (v1 == null || v2 == null) {
				println("Reached end of file.");
				break;
			}
	
			// compare the results
			if (!equal(v1, v2)) {
				hasError = true;
				print("Line: " + lineCount + ": ");
				println(v1.toString() + " != " + v2.toString());
				System.err.println("Line: " + lineCount + ": ");
			}
		}
		
		// Closing remarks
		print("\n" + lineCount + " lines processed. ");
		if (hasError) {
			print("Errors were found.\n\n");
		} else {
			print("No errors were found.\n\n");
		}
		System.out.println(System.currentTimeMillis()-start);
	}
	private Object parseNextLine(BufferedReader reader) throws Exception {
		Object value;
		String line;
		
		// cycle till a valid line is found
		do {
			line = reader.readLine(); // returns null on end of file
			if (line == null) return null;
			lineCount++;
		} while (line.length() == 0 || line.trim().charAt(0) == '#');
		MyParser myParser = new MyParser();
		
		
		// evaluate the expression
		 myParser.parse(line);
		// did an error occur while evaluating?
		 value = myParser.getValue();
			
		return value;
	}

	private boolean equal(Object param1, Object param2)
	{
//		System.out.println(this.lineCount+":"+param1+"----------------"+param2);
		// test any other types here
		return param1.equals(param2);
	}
	
	
	private static void print(String str) {
		System.out.print(str);
	}

	/**
	 * Helper function for printing lines.
	 */
	private static void println(String str) {
		System.out.println(str);
	}

}
