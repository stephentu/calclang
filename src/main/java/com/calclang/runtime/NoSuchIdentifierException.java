package com.calclang.runtime;

public class NoSuchIdentifierException extends RuntimeException {

	public NoSuchIdentifierException(String ident) {
		super("No such identifier: " + ident);
	}

}
