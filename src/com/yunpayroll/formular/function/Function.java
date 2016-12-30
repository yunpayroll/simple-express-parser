package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ElementVisitor;
import com.yunpayroll.formular.ParseException;

import java.util.Stack;

public abstract class Function implements Operator {
	protected int numOfArgs;
	protected void checkStack(Stack<Object> inStack) throws ParseException {
		/* Check if stack is null */
		if (null == inStack) {
			throw new ParseException("Stack argument null");
		}
	}
	
	public void setNumOfArgs(int num) {
		this.numOfArgs = num;
	}
	
	public int getNumOfArgs(){
		return numOfArgs;
	}

	protected abstract void checkNumOfArgs() throws ParseException;
		
	
	public void run(Stack<Object> stack) throws ParseException{
		checkStack(stack);
		checkNumOfArgs();
		operator(stack);
	}
	
	protected abstract void operator(Stack<Object> stack) throws ParseException;

	public void visit(ElementVisitor visitor) throws ParseException{
		visitor.visit(this);
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
}
