package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class LogicAnd  extends AbstractOperator{

	@Override
	protected void operator(Stack<Object> stack) throws ParseException {
		Object p1 = stack.pop();
		Object p2 = stack.pop();
		if ((p1 instanceof BigDecimal) && (p2 instanceof BigDecimal)) {
			boolean rst = ((BigDecimal)p2).intValue()>=1 && ((BigDecimal)p1).intValue()>=1;
			stack.push(BigDecimal.valueOf( rst?1.0:0.0));
			return;
		}
		
		throw new ParseException("op Subtract must be focus on double ");
	}

	
	
	@Override
	public String toString() {
	    return "&";
	}
}
