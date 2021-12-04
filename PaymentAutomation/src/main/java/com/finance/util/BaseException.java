package com.finance.util;

public class BaseException extends CommonException{

	public BaseException(String message){
		super(message);
	}

	public BaseException(Exception exception){
		super(exception);
	}	
	
}
