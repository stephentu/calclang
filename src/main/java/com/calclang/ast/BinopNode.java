package com.calclang.ast;

import com.calclang.codegen.ByteCodeGenerator;
import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.CalcObject;
import com.calclang.util.Pair;

public abstract class BinopNode extends AbstractNode {

	public BinopNode(int i) {
		super(i);
	}
	
	public BinopNode(CalcLangParser p, int i) {
		super(p, i);
	}

	public Node getLeft() {
		return jjtGetChild(0);
	}
	
	public Node getRight() {
		return jjtGetChild(1);
	}
	
	public boolean hasLValue() {
		return true;
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		getLeft().interpret(vm);
		getRight().interpret(vm);
		Pair<CalcObject, CalcObject> p = vm.popBinop(); // p0 is right, p1 is left
		doBinopAction(vm, p.getRight(), p.getLeft());
	}
	
	protected abstract void doBinopAction(VirtualMachine vm, CalcObject left, CalcObject right);
	
	@Override
	public void codeGen(ByteCodeGenerator gen) {
		getLeft().codeGen(gen);
		getRight().codeGen(gen);
		doCodeGenAction(gen);
	}
	
	protected abstract void doCodeGenAction(ByteCodeGenerator gen);
	
}
