package com.calclang.ast;

import java.lang.UnsupportedOperationException;

import com.calclang.parser.CalcLangParser;
import com.calclang.interpreter.VirtualMachine;

public abstract class AbstractNode extends SimpleNode {

    public AbstractNode(int i) {
        super(i);
    }

    public AbstractNode(CalcLangParser p, int i) {
        super(p, i);
    }

    public void interpret(VirtualMachine vm) {
        throw new UnsupportedOperationException("interpret");
    }

    public void codeGen(VirtualMachine vm) {
        throw new UnsupportedOperationException("codeGen");
    }
    
    public String getSymbol() {
    	throw new  UnsupportedOperationException("getSymbol");
    }

}
