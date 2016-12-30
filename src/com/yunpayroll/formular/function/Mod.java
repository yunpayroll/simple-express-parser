package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class Mod extends AbstractOperator {

	@Override
	protected void operator(Stack<Object> stack) throws ParseException {
		Object p1 = stack.pop();
		Object p2 = stack.pop();
		if ((p1 instanceof BigDecimal) && (p2 instanceof BigDecimal)) {
			double rst = ((BigDecimal) p2).doubleValue() % ((BigDecimal) p1).doubleValue();
			stack.push(BigDecimal.valueOf( rst));
			return;
		}

		throw new ParseException("op add must be focus on double or string");
	}

	
	@Override
	public String toString() {
	    return "%";
	}
}