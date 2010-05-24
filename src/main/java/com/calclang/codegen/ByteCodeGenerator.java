package com.calclang.codegen;

import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.SWAP;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.calclang.interpreter.VirtualMachine;
import com.calclang.namer.Declaration;

public class ByteCodeGenerator {

	private final VirtualMachine vm;
	private final MethodVisitor mv;
	
	public ByteCodeGenerator(VirtualMachine vm, MethodVisitor mv) {
		this.vm = vm;
		this.mv = mv;
	}
	
	public void pushInt(int value) {
		mv.visitTypeInsn(NEW, "com/calclang/runtime/IntObject");
		mv.visitInsn(DUP);
		mv.visitLdcInsn(new Integer(value));
		mv.visitMethodInsn(INVOKESPECIAL, "com/calclang/runtime/IntObject", "<init>", "(I)V");
	}
	
	public void loadDeclaration(Declaration decl) {
		mv.visitVarInsn(ALOAD, decl.getDeclIdx());
	}
	
	public void add() {
		mv.visitMethodInsn(INVOKEVIRTUAL, "com/calclang/runtime/CalcObject", "add", "(Lcom/calclang/runtime/CalcObject;)Lcom/calclang/runtime/CalcObject;");
	}
	
	/**
	 * Println the object which is currently the top of the stack
	 */
	public void println() {
		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitInsn(SWAP);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V");
	}
	
	/**
	 * Print the object which is currently the top of the stack
	 */
	public void print() {
		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitInsn(SWAP);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/Object;)V");
	}
	
	public void print(String arg0) {
		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitLdcInsn(arg0);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V");
	}
	
}
