package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.util.Stack;

public class Or extends AbstractOperator{

	@Override
	protected void operator(Stack<Object> stack) throws ParseException {
		Object p1 = stack.pop();
		Object p2 = stack.pop();
		if ((p1 instanceof Double) && (p2 instanceof Double)) {
			Integer rst = ((Double)p2).intValue() | ((Double)p1).intValue();
			stack.push(Double.valueOf(rst));
			return;
		}
		
		throw new ParseException("op Subtract must be focus on double ");
	}

	
	@Override
	public String toString() {
	    return "|";
	}
}
