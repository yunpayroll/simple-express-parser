package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class Random extends Function{

	@Override
    protected void checkNumOfArgs() throws ParseException {
		if (getNumOfArgs()!=0) {
			throw new ParseException("function random must't  have  args");
        }
    }

	@Override
    protected void operator(Stack<Object> stack) throws ParseException {
		stack.push( BigDecimal.valueOf(Double.valueOf(new java.util.Random().nextLong())) );
    }

}
