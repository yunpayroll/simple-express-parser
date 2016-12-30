package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class Iff extends Function{

	public Iff(){
		
	}

	@Override
    protected void operator(Stack<Object> stack) throws ParseException {
	    Object p1 = stack.pop();
	    Object p2 = stack.pop();
	    Object p3 = stack.pop();
	    if( !(p1 instanceof Number) ) {
	    	throw new ParseException(" the first para of function if must be number type");
	    }
	    Object rst = ((BigDecimal)p1).compareTo(BigDecimal.valueOf(1.0)) >=0?p2:p3;
	    stack.push(rst);
	}

	@Override
    protected void checkNumOfArgs() throws ParseException {
	 if(getNumOfArgs()!=3) {
		 throw new ParseException("function if must be have 3 args");
	 }
    }
	@Override
	public String toString() {
	    return "iff";
	}
}
