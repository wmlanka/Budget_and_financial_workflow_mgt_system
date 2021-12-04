package com.finance.util;

public class ConstraintException extends Exception{
 	
	private Exception exception;

	public ConstraintException(String message){
		super(message);
	}  

	public ConstraintException(Exception exception)
	{
		super(exception.getMessage());
		this.exception = exception;
	}

	public Exception getException(){
		return exception;
	}
	
}
