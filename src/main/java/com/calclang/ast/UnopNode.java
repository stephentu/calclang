package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.CalcObject;

public abstract class UnopNode extends AbstractNode {

	public UnopNode(int i) {
		super(i);
	}
	
	public UnopNode(CalcLangParser p, int i) {
		super(p, i);
	}

	public boolean hasLValue() {
		return true;
	}
	
	public Node getChild() {
		return jjtGetChild(0);
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		getChild().interpret(vm);
		doUnopAction(vm, vm.pop());
	}
	
	protected abstract void doUnopAction(VirtualMachine vm, CalcObject c);
	
}
