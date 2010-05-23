package com.calclang.runtime;

public abstract class CalcObject {

	public static enum CType {
		INTEGER,
		MATRIX;
	}
	
	private final CType type;
	
	public CalcObject(CType type) {
		this.type = type;
	}
	
	public CType getType() {
		return type;
	}
	
	/** Operators **/
	
	/** lval = this + b */
	public abstract CalcObject add(CalcObject b);
	
	/** lval = this - b */
	public abstract CalcObject subtract(CalcObject b);
	
	/** lval = this * b */
	public abstract CalcObject multiply(CalcObject b);
	
	/** lval = this / b */
	public abstract CalcObject divide(CalcObject b);
	
	/** lval = this ^ b */
	public abstract CalcObject power(CalcObject b);
	
	/** lval = + this */
	public abstract CalcObject plus();
	
	/** lval = - this */
	public abstract CalcObject minus();
	
	public void ensureType(CType type) {
		if (getType() != type)
			throw new RuntimeTypeMismatchException();
	}
	
	public boolean checkType(CType type) {
		return getType() == type;
	}
	
}
