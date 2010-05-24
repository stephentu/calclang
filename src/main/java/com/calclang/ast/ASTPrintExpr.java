package com.calclang.ast;

import com.calclang.codegen.ByteCodeGenerator;
import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;

public class ASTPrintExpr extends AbstractNode {
	
	public ASTPrintExpr(int id) {
		super(id);
	}

	public ASTPrintExpr(CalcLangParser p, int id) {
		super(p, id);
	}

	public boolean hasLValue() {
		return false;
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		for (int i = 0; i < jjtGetNumChildren(); i++) {
			jjtGetChild(i).interpret(vm);
			if (i == jjtGetNumChildren() - 1) {
				System.out.println(vm.pop());
			} else {
				System.out.print(vm.pop());
				System.out.print(", ");
			}
		}
	}
	
	@Override
	public void codeGen(ByteCodeGenerator gen) {
		for (int i = 0; i < jjtGetNumChildren(); i++) {
			jjtGetChild(i).codeGen(gen);
			if (i == jjtGetNumChildren() - 1) {
				gen.println();
			} else {
				gen.print();
				gen.print(", ");
			}
		}
	}

}

