package com.yunpayroll.formular;


public class Variable implements Element{
	
	private String name;
	
	private Object value;
	
	public Variable(String name,Object value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
    public void visit(ElementVisitor visitor) throws ParseException {
		visitor.visit(this);
    }
	
	
	
	
}
