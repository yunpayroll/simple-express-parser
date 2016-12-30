package com.yunpayroll.formular;

import com.yunpayroll.formular.function.Operator;

import java.util.Stack;

public class ElementVisitor {
	private Stack<Object> stack;
	
	public ElementVisitor() {
		stack = new Stack<Object>();
	}
	public void visit(Operator op) throws ParseException{
		op.run(stack);
	}
	public void visit(Constant con) throws ParseException{
		stack.push(con.getValue());
	}
	
	public void visit(Variable var) throws ParseException{
		if(var.getValue()==null) {
			throw new ParseException(var.getName()+"的变量不存在。");
		}
		stack.push(var.getValue());
	}
	public Object getValue(){
		return stack.pop();
	}
}
