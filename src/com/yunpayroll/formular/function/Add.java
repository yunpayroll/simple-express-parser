package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class Add extends AbstractOperator{
 

	@Override
    protected void operator(Stack<Object> stack) throws ParseException {
	    Object p1 = stack.pop();
	    Object p2 = stack.pop();
	    if( (p1 instanceof BigDecimal) &&  (p2 instanceof BigDecimal)) {
	    	BigDecimal rst = ((BigDecimal)p2).add((BigDecimal)p1);
	    	stack.push(rst);
	    	return;
	    }
	    if( (p1 instanceof String) &&  (p2 instanceof String)) {
	    	String rst = (String)p2+(String)p1;
	    	stack.push(rst);
	    	return;
	    }
	    throw new ParseException("op add must be focus on double or string");
	}

	
	@Override
	public String toString() {
	    return "+";
	}
}
