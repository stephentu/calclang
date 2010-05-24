package com.calclang.ast;

import com.calclang.codegen.ByteCodeGenerator;
import com.calclang.interpreter.VirtualMachine;
import com.calclang.namer.Declaration;
import com.calclang.namer.SymbolTable;

public interface Node {
        
    /** This method is called after the node has been made the current node.  It indicates that child nodes can now be added to it. */
    public void jjtOpen();

    /** This method is called after all the child nodes have been added. */
    public void jjtClose();

    /** This pair of methods are used to inform the node of its parent. */
    public void jjtSetParent(Node n);
    public Node jjtGetParent();

    /** This method tells the node to add its argument to the node's list of children.  */
    public void jjtAddChild(Node n, int i);

    /** This method returns a child node.  The children are numbered from zero, left to right. */
    public Node jjtGetChild(int i);

    /** Return the number of children the node has. */
    int jjtGetNumChildren();

    public boolean hasLValue();
    
    public void bindNames(SymbolTable symTab);
    
    public void interpret(VirtualMachine vm);

    public void codeGen(ByteCodeGenerator gen);

    public String getSymbol();
    
    public Declaration getDeclaration();
    
}
