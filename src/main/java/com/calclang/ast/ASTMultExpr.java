package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.CalcObject;

public class ASTMultExpr extends BinopNode {
	public ASTMultExpr(int id) {
		super(id);
	}

	public ASTMultExpr(CalcLangParser p, int id) {
		super(p, id);
	}

	@Override
	protected void doBinopAction(VirtualMachine vm, CalcObject left, CalcObject right) {
		vm.push(left.multiply(right));
	}
}
