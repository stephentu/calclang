package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.CalcObject;

public class ASTMinusNode extends UnopNode {
	
	public ASTMinusNode(int id) {
		super(id);
	}

	public ASTMinusNode(CalcLangParser p, int id) {
		super(p, id);
	}

	@Override
	protected void doUnopAction(VirtualMachine vm, CalcObject c) {
		vm.push(c.minus());
	}

}

