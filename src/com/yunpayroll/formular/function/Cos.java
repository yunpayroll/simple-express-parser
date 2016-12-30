package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class Cos extends Function{

	@Override
    protected void checkNumOfArgs() throws ParseException {
	    if(getNumOfArgs()!=1) {
	    	throw new ParseException("function cos must be have 1 args");
	    }
    }

	@Override
    protected void operator(Stack<Object> stack) throws ParseException {
		Object p1 = stack.pop();
		if( !(p1 instanceof BigDecimal) ) {
			throw new ParseException("function cos's args must be a number type");
		}
		BigDecimal arg = (BigDecimal)p1;
		stack.push(BigDecimal.valueOf(Math.cos(arg.doubleValue())));
	}

	@Override
	public String toString() {
	    return "cos";
	}
}
