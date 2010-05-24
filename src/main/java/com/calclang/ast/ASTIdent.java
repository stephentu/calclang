package com.calclang.ast;

import com.calclang.codegen.ByteCodeGenerator;
import com.calclang.interpreter.VirtualMachine;
import com.calclang.namer.Declaration;
import com.calclang.namer.SymbolTable;
import com.calclang.parser.CalcLangParser;
import com.calclang.runtime.NoSuchIdentifierException;

public class ASTIdent extends AbstractNode {

	public String symbol;
	private Declaration decl;
	
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
	public void bindNames(SymbolTable symTab) {
		if (!symTab.containsDeclFor(symbol))
			throw new NoSuchIdentifierException(symbol);
		decl = symTab.getDeclFor(symbol);
	}
	
	void makeName(SymbolTable symTab) {
		if (!symTab.containsDeclFor(symbol))
			decl = symTab.makeDeclFor(symbol);
	}
	
	@Override
	public void interpret(VirtualMachine vm) {
		if (decl == null)
			throw new IllegalStateException("Cannot interpret with no decl binding for symbol: " + symbol);
		vm.load(vm.getRegisterFor(getDeclaration()));
	}

	@Override
	public void codeGen(ByteCodeGenerator gen) {
		if (decl == null)
			throw new IllegalStateException("Cannot code gen with no decl binding for symbol: " + symbol);
		gen.loadDeclaration(decl);
	}
	
	@Override
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public Declaration getDeclaration() {
		return decl;
	}
	
}
