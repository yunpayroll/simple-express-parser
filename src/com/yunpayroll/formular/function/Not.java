package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class Not extends AbstractOperator {

	@Override
	protected void operator(Stack<Object> stack) throws ParseException {
		Object p1 = stack.pop();
		if ((p1 instanceof BigDecimal) ) {
			stack.push( BigDecimal.valueOf( ((BigDecimal)p1).doubleValue()>=1.0?0.0:1.0 ));
			return;
		}

		throw new ParseException("op add must be focus on double or string");
	}

	
	@Override
	public String toString() {
	    return "!";
	}
}