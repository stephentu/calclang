package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;

public class ASTProg extends AbstractNode {
	public ASTProg(int id) {
		super(id);
	}

	public ASTProg(CalcLangParser p, int id) {
		super(p, id);
	}

	public boolean hasLValue() {
		return false;
	}

	public void interpret(VirtualMachine vm) {
		for (Node n : children)
			n.interpret(vm);
	}
	
}

