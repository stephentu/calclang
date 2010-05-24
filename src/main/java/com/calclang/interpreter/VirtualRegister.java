package com.calclang.interpreter;

import com.calclang.runtime.CalcObject;

public class VirtualRegister {

	private final VirtualMachine vm;
	private final int idx;
	
	VirtualRegister(VirtualMachine vm, int idx) {
		this.vm = vm;
		this.idx = idx;
	}
	
	public CalcObject getObject() {
		return vm.retrieve(this);
	}
	
	int registerIdx() {
		return idx;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof VirtualRegister))
			return false;
		VirtualRegister oR = (VirtualRegister) o;
		return idx == oR.idx;
	}
	
	@Override
	public int hashCode() {
		return idx;
	}
	
}
