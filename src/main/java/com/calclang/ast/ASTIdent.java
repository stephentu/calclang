package com.calclang.ast;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;

public class ASTIdent extends AbstractNode {

	public String symbol;
	
	public ASTIdent(int i) {
		super(i);
	}
	
	public ASTIdent(CalcLangParser p, int i) {
		super(p, i);
	}

	public boolean hasLValue() {
		return true;
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		vm.load(symbol);
	}

	@Override
	public String getSymbol() {
		return symbol;
	}
	
}
