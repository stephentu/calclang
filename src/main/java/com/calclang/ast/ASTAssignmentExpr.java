package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;

public class ASTAssignmentExpr extends com.calclang.ast.AbstractNode {
	public ASTAssignmentExpr(int id) {
		super(id);
	}

	public ASTAssignmentExpr(CalcLangParser p, int id) {
		super(p, id);
	}

	public boolean hasLValue() {
		return false;
	}
	
	public Node getIdent() {
		return jjtGetChild(0);
	}
	
	public Node getTarget() {
		return jjtGetChild(1);
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		getTarget().interpret(vm);
		vm.assign(getIdent().getSymbol(), vm.pop());
	}

}
