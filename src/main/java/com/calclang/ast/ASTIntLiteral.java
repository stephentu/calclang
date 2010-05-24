package com.calclang.ast;

import com.calclang.codegen.ByteCodeGenerator;
import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.IntObject;

public class ASTIntLiteral extends AbstractNode {

	public int intValue;
	
	public ASTIntLiteral(int i) {
		super(i);
	}
	
	public ASTIntLiteral(CalcLangParser p, int i) {
		super(p, i);
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		vm.push(new IntObject(intValue));
	}

	@Override
	public void codeGen(ByteCodeGenerator gen) {
		gen.pushInt(intValue);
	}
	
	public boolean hasLValue() {
		return true;
	}

}
