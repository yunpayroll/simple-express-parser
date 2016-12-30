package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ElementVisitor;
import com.yunpayroll.formular.ParseException;

import java.util.Stack;

public abstract class AbstractOperator implements Operator {
	
	protected void checkStack(Stack<Object> inStack) throws ParseException {
		/* Check if stack is null */
		if (null == inStack) {
			throw new ParseException("Stack argument null");
		}
	}
	
	
	public void run(Stack<Object> stack) throws ParseException{
		checkStack(stack);
		operator(stack);
	}
	
	protected abstract void operator(Stack<Object> stack) throws ParseException;

	public void visit(ElementVisitor visitor) throws ParseException{
		visitor.visit(this);
	}
	@Override
	public Operator clone() throws CloneNotSupportedException {
	    return (Operator)super.clone();
	}
	
}
