package com.yunpayroll.formular.function;

import com.yunpayroll.formular.ParseException;

import java.math.BigDecimal;
import java.util.Stack;

public class Sum extends Function{

	@Override
    protected void checkNumOfArgs() throws ParseException {
		
    }

	@Override
    protected void operator(Stack<Object> stack) throws ParseException {
		
		if(numOfArgs==0 ) {
			stack.push(0.0);
			return;
		}
		Object sum =null;
		for( int i=0;i<numOfArgs;i++ ) {
			Object p2 = stack.pop();
			if ((p2 instanceof BigDecimal)) {
				sum = sum==null? BigDecimal.valueOf( 0.0):sum;
				sum = ((BigDecimal)p2).add((BigDecimal)sum);
			}else if((p2 instanceof String)){
				sum = sum==null?"":sum;
				sum = p2 +  sum.toString();
			} else {
				throw new ParseException("function sum must be have 1 args");
			}
		}
		stack.push(sum);
    }

}
