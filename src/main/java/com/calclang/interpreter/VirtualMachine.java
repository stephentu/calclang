package com.calclang.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.calclang.runtime.CalcObject;
import com.calclang.runtime.NoSuchIdentifierException;
import com.calclang.util.Pair;

public class VirtualMachine {

	private final Map<String, CalcObject> idMap = new HashMap<String, CalcObject>();
	
	private final Stack<CalcObject> runtimeStack = new Stack<CalcObject>();

	public void push(CalcObject c) {
		runtimeStack.push(c);
	}
	
	/** 
	 * Assigns id -> c and pushes c onto stack
	 * @param id
	 * @param c
	 */
	public void pushAssign(String id, CalcObject c) {
		assign(id, c);
		push(c);
	}
	
	public void assign(String id, CalcObject c) {
		idMap.put(id, c);
	}
	
	/** Is an error if id maps to null */
	public CalcObject retrieve(String id) {
		CalcObject o = idMap.get(id);
		if (o == null)
			throw new NoSuchIdentifierException(id);
		return o;
	}
	
	/** Loads id onto stack. is an error if id maps to null */
	public void load(String id) {
		push(retrieve(id));
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
