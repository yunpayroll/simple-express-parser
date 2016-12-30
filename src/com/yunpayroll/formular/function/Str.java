package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.util.Stack;

public class Str extends Function{

	@Override
    protected void checkNumOfArgs() throws ParseException {
	    if(getNumOfArgs()!=1) {
	    	throw new ParseException("function str must be have 1 args");
	    }
    }

	@Override
    protected void operator(Stack<Object> stack) throws ParseException {
		Object p1 = stack.pop();
		stack.push(p1==null?"":p1.toString());
	}
	
	@Override
	public String toString() {
	    return "str";
	}

}
