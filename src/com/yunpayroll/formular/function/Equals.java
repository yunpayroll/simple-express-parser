package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class Equals extends AbstractOperator {

	@Override
	protected void operator(Stack<Object> stack) throws ParseException {
		Object p1 = stack.pop();
		Object p2 = stack.pop();
		
		stack.push(BigDecimal.valueOf(p1.equals(p2)?1.0:0.0));

	}

	
	@Override
	public String toString() {
	    return "==";
	}
}
