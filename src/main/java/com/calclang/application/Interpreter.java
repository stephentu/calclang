package com.calclang.application;

import java.io.FileInputStream;
import java.io.InputStream;

import com.calclang.ast.Node;
import com.calclang.ast.SimpleNode;
import com.calclang.interpreter.VirtualMachine;
import com.calclang.parser.CalcLangParser;
import com.calclang.parser.ParseException;
import com.calclang.runtime.CalcObject;

public class Interpreter {

	public Node parse(InputStream stream) throws ParseException {
		CalcLangParser parser = new CalcLangParser(stream);
		return CalcLangParser.Prog();
	}
	
	public CalcObject interpret(InputStream stream) throws ParseException {
		Node root = parse(stream);
		return interpret(root);
	}
	
	public CalcObject interpret(Node node) {
		VirtualMachine vm = new VirtualMachine();
		node.interpret(vm);
		if (vm.isStackEmpty()) 
			return null;
		return vm.pop();
	}
	
	public Node dump(InputStream stream) throws ParseException {
		Node root = parse(stream);
		((SimpleNode)root).dump("");
		return root;
	}
	
	public static void main(String[] args) throws Exception {
		InputStream input;
		if (args.length == 0) {
			input = System.in;
		} else {
			input = new FileInputStream(args[0]);
		}
		Interpreter interpreter = new Interpreter();
		Node n = interpreter.dump(input);
		CalcObject result = interpreter.interpret(n);
		if (result != null) {
			System.out.println(result);
		} else {
			System.out.println("Unit");
		}
	}
	
}
