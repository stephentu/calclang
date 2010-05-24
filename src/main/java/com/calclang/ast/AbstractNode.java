package com.calclang.ast;

import java.lang.UnsupportedOperationException;

import com.calclang.namer.Declaration;
import com.calclang.namer.SymbolTable;
import com.calclang.parser.CalcLangParser;
import com.calclang.codegen.ByteCodeGenerator;
import com.calclang.interpreter.VirtualMachine;

public abstract class AbstractNode extends SimpleNode {

    public AbstractNode(int i) {
        super(i);
    }

    public AbstractNode(CalcLangParser p, int i) {
        super(p, i);
    }

	public void bindNames(SymbolTable symTab) {
		if (children != null)
			for (Node n : children)
				n.bindNames(symTab);
	}
    
    public void interpret(VirtualMachine vm) {
        throw new UnsupportedOperationException("interpret");
    }

    public void codeGen(ByteCodeGenerator gen) {
        throw new UnsupportedOperationException("codeGen");
    }
    
    public String getSymbol() {
    	throw new UnsupportedOperationException("getSymbol");
    }
    
    public Declaration getDeclaration() {
    	throw new UnsupportedOperationException("getDeclaration");
    }

}
