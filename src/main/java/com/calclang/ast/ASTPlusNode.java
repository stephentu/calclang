package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.CalcObject;

public class ASTPlusNode extends UnopNode {
	
	public ASTPlusNode(int id) {
		super(id);
	}

	public ASTPlusNode(CalcLangParser p, int id) {
		super(p, id);
	}

	@Override
	protected void doUnopAction(VirtualMachine vm, CalcObject c) {
		vm.push(c.plus());
	}

}
