package com.calclang.runtime;

public class IntObject extends CalcObject {

	private final int value;
	
	public IntObject(int value) {
		super(CType.INTEGER);
		this.value = value;
	}
	
	private static void ensureInt(CalcObject c) {
		c.ensureType(CType.INTEGER);
	}
	
	private static IntObject checkCastInt(CalcObject c) {
		ensureInt(c);
		return (IntObject) c;
	}
	
	@Override
	public CalcObject add(CalcObject b) {
		if (b.getType() == CType.INTEGER) {
			IntObject bInt = checkCastInt(b);
			return new IntObject(this.value + bInt.value);
		} else if (b.getType() == CType.MATRIX) {
			MatrixObject bMatrix = (MatrixObject) b;
			return bMatrix.scalarAdd(value);
		} else {
			throw new IllegalArgumentException("Bad object type: " + b.getType());
		}
	}

	@Override
	public CalcObject divide(CalcObject b) {
		IntObject bInt = checkCastInt(b);
		return new IntObject(this.value / bInt.value);
	}

	@Override
	public CalcObject minus() {
		return new IntObject(-this.value);
	}

	@Override
	public CalcObject multiply(CalcObject b) {
		if (b.getType() == CType.INTEGER) {
			IntObject bInt = checkCastInt(b);
			return new IntObject(this.value * bInt.value);
		} else if (b.getType() == CType.MATRIX) {
			MatrixObject bMatrix = (MatrixObject) b;
			return bMatrix.scalarMultiply(value);
		} else {
			throw new IllegalArgumentException("Bad object type: " + b.getType());
		}
	}

	@Override
	public CalcObject plus() {
		return new IntObject(+this.value);
	}

	@Override
	public CalcObject power(CalcObject b) {
		IntObject bInt = checkCastInt(b);
		return new IntObject((int)Math.pow(this.value, bInt.value));
	}

	@Override
	public CalcObject subtract(CalcObject b) {
		if (b.getType() == CType.INTEGER) {
			IntObject bInt = checkCastInt(b);
			return new IntObject(this.value - bInt.value);
		} else if (b.getType() == CType.MATRIX) {
			MatrixObject bMatrix = (MatrixObject) b;
			bMatrix = (MatrixObject) bMatrix.minus();
			return add(bMatrix);
		} else {
			throw new IllegalArgumentException("Bad object type: " + b.getType());
		}
	}
	
	@Override
	public String toString() {
		return "" + value;
	}
	
	public int getValue() {
		return value;
	}
	
}
