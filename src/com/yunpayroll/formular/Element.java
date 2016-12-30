package com.yunpayroll.formular;


public interface Element extends Cloneable {
	public void visit(ElementVisitor visitor) throws ParseException;
	default  public String getClassName() {
        return this.getClass().getSimpleName();
    }
	
}
