package com.manu.narvar;

public class NarvarException extends Exception{
	
	private String customMessage;
	public NarvarException() {
		super();
	}
	
	public NarvarException(String message) {
		super(message);
		this.customMessage=message;
	}
	
	public NarvarException(String message, Exception e) {
		super(e);
		this.customMessage=message;
	}
	
	public NarvarException(Exception e) {
		super(e);
	}

}
