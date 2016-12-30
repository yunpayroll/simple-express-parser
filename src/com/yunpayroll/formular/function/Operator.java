package com.yunpayroll.formular.function;

import com.yunpayroll.formular.Element;
import com.yunpayroll.formular.ParseException;

import java.util.Stack;

public interface Operator extends Element {
	public void run(Stack<Object> stack) throws ParseException;
}
