package com.calclang.application;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.V1_6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.TraceClassVisitor;

import com.calclang.ast.Node;
import com.calclang.ast.SimpleNode;
import com.calclang.codegen.ByteCodeGenerator;
import com.calclang.interpreter.VirtualMachine;
import com.calclang.namer.SymbolTable;
import com.calclang.parser.CalcLangParser;
import com.calclang.parser.ParseException;
import com.calclang.runtime.CalcObject;

public class CalcLangRunner {

	public Node parse(InputStream stream) throws ParseException {
		CalcLangParser parser = new CalcLangParser(stream);
		return CalcLangParser.Prog();
	}
	
	public CalcObject interpret(InputStream stream, VirtualMachine vm) throws ParseException {
		Node root = parse(stream);
		return interpret(root, vm);
	}
	
	public CalcObject interpret(Node node, VirtualMachine vm) {
		node.interpret(vm);
		if (vm.isStackEmpty()) 
			return null;
		return vm.pop();
	}
	
	public void compile(Node program, VirtualMachine vm, String clazzName, File outFile) throws IOException {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		TraceClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(System.out));
		cv.visit(V1_6, ACC_PUBLIC + ACC_SUPER, clazzName, null, "java/lang/Object", null);
		MethodVisitor mv;
		
		// Runner class default no arg ctor
		mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
		
		// Runner class execute method impl
		mv = cv.visitMethod(ACC_PUBLIC, "execute", "()V", null, null);
		mv.visitCode();
		
		program.codeGen(new ByteCodeGenerator(vm, mv));
		
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0); // arguments here ignored, because we're doing COMPUTE_FRAMES
		mv.visitEnd();
		
		// Runner class main method
		mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
		mv.visitCode();
		mv.visitTypeInsn(NEW, clazzName);
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKESPECIAL, clazzName, "<init>", "()V");
		mv.visitMethodInsn(INVOKEVIRTUAL, clazzName, "execute", "()V");
		mv.visitInsn(RETURN);
		mv.visitMaxs(2, 1);
		mv.visitEnd();
		
		cv.visitEnd();

		FileOutputStream fos = new FileOutputStream(outFile);
		fos.write(cw.toByteArray());
		fos.close();
	}
	
	public Node dump(InputStream stream) throws ParseException {
		Node root = parse(stream);
		((SimpleNode)root).dump("");
		return root;
	}
	
	public void bindNames(Node n) {
		n.bindNames(new SymbolTable());
	}
	
	private static String stripExt(String nameWithExt) {
		int idx = nameWithExt.lastIndexOf('.');
		if (idx == -1)
			return nameWithExt;
		return nameWithExt.substring(0, idx);
	}
	
	public static void main(String[] args) throws Exception {
		InputStream input;
		String clazzName;
		if (args.length == 0) {
			input = System.in;
			clazzName = "CalcLangScript";
		} else {
			input = new FileInputStream(args[0]);
			clazzName = stripExt(args[0]);
		}
		CalcLangRunner runner = new CalcLangRunner();
		Node n = runner.dump(input);
		runner.bindNames(n);
		VirtualMachine vm = new VirtualMachine();
		runner.interpret(n, vm);
		System.out.println("Compiling source...");
		runner.compile(n, vm, clazzName, new File(clazzName + ".class"));
	}
	
}
