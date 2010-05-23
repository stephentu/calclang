package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.MatrixObject;

public class ASTMatrixRowEntry extends AbstractNode {
	
	public ASTMatrixRowEntry(int id) {
		super(id);
	}

	public ASTMatrixRowEntry(CalcLangParser p, int id) {
		super(p, id);
	}

	@Override
	public void interpret(VirtualMachine vm) {
		System.out.println("num rows: " + jjtGetNumChildren());
		MatrixObject[] rows = new MatrixObject[jjtGetNumChildren()];
		for (int i = 0; i < jjtGetNumChildren(); i++) {
			jjtGetChild(i).interpret(vm);
			rows[i] = (MatrixObject) vm.pop();
		}
		vm.push(new MatrixObject(rows));
	}
	
	public boolean hasLValue() {
		return true;
	}

}
