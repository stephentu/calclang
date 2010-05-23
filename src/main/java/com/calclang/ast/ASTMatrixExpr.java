package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.MatrixObject;

public class ASTMatrixExpr extends AbstractNode {
	
	public ASTMatrixExpr(int id) {
		super(id);
	}

	public ASTMatrixExpr(CalcLangParser p, int id) {
		super(p, id);
	}

	public boolean hasLValue() {
		return true;
	}
	
	public boolean isEmptyMatrix() {
		return jjtGetNumChildren() == 0;
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		if (isEmptyMatrix()) {
			vm.push(new MatrixObject());
		} else {
			jjtGetChild(0).interpret(vm);
		}
	}

}

