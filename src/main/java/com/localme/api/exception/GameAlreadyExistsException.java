package com.localme.api.exception;

public class GameAlreadyExistsException {
	private String message;

	public GameAlreadyExistsException() {
		
	}

	public GameAlreadyExistsException(String message) {
		super();
		this.message = message;
	}
	
}
