package com.calclang.namer;

public class Declaration {

	private final int idx;
	
	public Declaration(int idx) {
		this.idx = idx;
	}
	
	public int getDeclIdx() {
		return idx;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Declaration))
			return false;
		Declaration oD = (Declaration) o;
		return idx == oD.idx;
	}
	
	@Override
	public int hashCode() {
		return idx;
	}
	
}
