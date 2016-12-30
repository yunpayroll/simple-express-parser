package com.yunpayroll.formular;

public class Constant implements Element{
	
	private Object value;
	
	public Constant(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
	    return value.toString();
	}
	@Override
    public void visit(ElementVisitor visitor) throws ParseException {
		visitor.visit(this);
    }
}
