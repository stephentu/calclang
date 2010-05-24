package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.namer.SymbolTable;
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
	
	public ASTIdent getIdent() {
		return (ASTIdent) jjtGetChild(0);
	}
	
	public Node getTarget() {
		return jjtGetChild(1);
	}
	
	@Override
	public void bindNames(SymbolTable symTab) {
		getTarget().bindNames(symTab);
		getIdent().makeName(symTab);
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		getTarget().interpret(vm);
		vm.assign(vm.getRegisterFor(getIdent().getDeclaration()), vm.pop());
	}

}
