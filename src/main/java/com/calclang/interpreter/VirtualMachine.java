package com.calclang.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.calclang.namer.Declaration;
import com.calclang.runtime.CalcObject;
import com.calclang.runtime.NullRegisterContentException;
import com.calclang.util.Pair;

public class VirtualMachine {

	private final List<CalcObject> registers = new ArrayList<CalcObject>();
	private final Stack<CalcObject> runtimeStack = new Stack<CalcObject>();
	private final Map<Declaration, VirtualRegister> declarations = new HashMap<Declaration, VirtualRegister>();
	private int registerCounter = 0;

	public void push(CalcObject c) {
		runtimeStack.push(c);
	}

	public VirtualRegister newRegister() {
		int newRegIdx = registerCounter++;
		VirtualRegister reg = new VirtualRegister(this, newRegIdx);
		if (registers.size() != newRegIdx) 
			throw new IllegalStateException("Register list out of order");
		registers.add(null);
		return reg;
	}
	
	public void pushAssign(VirtualRegister reg, CalcObject c) {
		assign(reg, c);
		push(c);
	}
	
	public void assign(VirtualRegister reg, CalcObject c) {
		registers.set(reg.registerIdx(), c);
	}
	
	public VirtualRegister getRegisterFor(Declaration decl) {
		if (!declarations.containsKey(decl))
			declarations.put(decl, newRegister());
		return declarations.get(decl);
	}
	
	public CalcObject retrieve(VirtualRegister reg) {
		CalcObject o = registers.get(reg.registerIdx());
		if (o == null)
			throw new NullRegisterContentException();
		return o;
	}
	
	public void load(VirtualRegister reg) {
		push(retrieve(reg));
	}
	
	public CalcObject pop() {
		return runtimeStack.pop();
	}
	
	public Pair<CalcObject, CalcObject> popBinop() {
		CalcObject left = pop();
		CalcObject right = pop();
		return new Pair<CalcObject, CalcObject>(left, right);
	}
	
	public boolean isStackEmpty() {
		return runtimeStack.isEmpty();
	}
	
}
