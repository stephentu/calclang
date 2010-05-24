package com.calclang.ast;

import com.calclang.codegen.ByteCodeGenerator;
import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.CalcObject;

public class ASTDivExpr extends BinopNode {
	public ASTDivExpr(int id) {
		super(id);
	}

	public ASTDivExpr(CalcLangParser p, int id) {
		super(p, id);
	}

	@Override
	protected void doBinopAction(VirtualMachine vm, CalcObject left, CalcObject right) {
		vm.push(left.divide(right));
	}

	@Override
	protected void doCodeGenAction(ByteCodeGenerator gen) {
		// TODO Auto-generated method stub
		
	}

}
