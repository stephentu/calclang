package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.CalcObject;

public class ASTAddExpr extends BinopNode {
  
	public ASTAddExpr(int id) {
		super(id);
	}

	public ASTAddExpr(CalcLangParser p, int id) {
		super(p, id);
	}

	@Override
	protected void doBinopAction(VirtualMachine vm, CalcObject left, CalcObject right) {
		vm.push(left.add(right));
	}

}
