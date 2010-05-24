package com.calclang.ast;

import com.calclang.codegen.ByteCodeGenerator;
import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.CalcObject;

public class ASTPower extends BinopNode {
	public ASTPower(int id) {
		super(id);
	}

	public ASTPower(CalcLangParser p, int id) {
		super(p, id);
	}

	@Override
	protected void doBinopAction(VirtualMachine vm, CalcObject left, CalcObject right) {
		vm.push(left.power(right));
	}

	@Override
	protected void doCodeGenAction(ByteCodeGenerator gen) {
		// TODO Auto-generated method stub
		
	}

}

