package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class Pow extends AbstractOperator {

	@Override
	protected void operator(Stack<Object> stack) throws ParseException {
		Object p1 = stack.pop();
		Object p2 = stack.pop();
		if ((p1 instanceof BigDecimal) && (p2 instanceof BigDecimal)) {
			stack.push(BigDecimal.valueOf(  Math.pow(((BigDecimal) p2).doubleValue(), ((BigDecimal) p1).doubleValue())));
			return;
		}

		throw new ParseException("op add must be focus on double or string");
	}

//	@Override
//	protected void checkNumOfArgs() throws ParseException {
//		if (getNumOfArgs() != 2) {
//			throw new ParseException("function pow must be have 3 args");
//		}
//	}
	@Override
	public String toString() {
	    return "^";
	}
}