package com.calclang.namer;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

	private final Map<String, Declaration> currentScope = new HashMap<String, Declaration>();
	private int declCounter = 0;
	
	public Declaration makeDeclFor(String id) {
		if (currentScope.containsKey(id)) 
			throw new IllegalArgumentException("Decl already exists for: " + id);
		Declaration decl = new Declaration(declCounter++);
		currentScope.put(id, decl);
		return decl;
	}
	
	public Declaration getDeclFor(String id) {
		if (!currentScope.containsKey(id))
			throw new IllegalArgumentException("No decl for: " + id);
		return currentScope.get(id);
	}
	
	public boolean containsDeclFor(String id) {
		return currentScope.containsKey(id);
	}
	
}
