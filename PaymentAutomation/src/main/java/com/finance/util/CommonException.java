package com.finance.util;

public class CommonException extends Exception{
	protected Exception exception;

	public CommonException(String message){
		super(message);
	}

	public CommonException(Exception exception){
		super(exception.getMessage());
		this.exception = exception;
	}

	public Exception getException(){
		return exception;
	}
}
