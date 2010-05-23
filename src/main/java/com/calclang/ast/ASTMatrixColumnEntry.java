package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.MatrixObject;

public class ASTMatrixColumnEntry extends AbstractNode {
	public ASTMatrixColumnEntry(int id) {
		super(id);
	}

	public ASTMatrixColumnEntry(CalcLangParser p, int id) {
		super(p, id);
	}

	public boolean hasLValue() {
		return true;
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		MatrixObject buffer = null;
		for (int i = 0; i < jjtGetNumChildren(); i++) {
			jjtGetChild(i).interpret(vm);
			MatrixObject next = MatrixObject.matrixFromCalcObject(vm.pop());
			if (buffer == null) {
				buffer = next;
			} else {
				buffer = buffer.concat(next);
			}
		}
		vm.push(buffer);
	}

}