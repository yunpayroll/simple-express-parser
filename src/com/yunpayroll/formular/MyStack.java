package com.yunpayroll.formular;

import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack<E> extends Stack<E> {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3354170405543908729L;

	/**
     * Creates an empty Stack.
     */
    public MyStack() {
    }

    public synchronized E peek(int size) {
        int     len = size();

        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - size);
    }
}
