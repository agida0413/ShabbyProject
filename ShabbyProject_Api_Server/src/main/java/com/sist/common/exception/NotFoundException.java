package com.sist.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException{
private final String errorCode;
private final String errorMessage;

 	//404관련 예외 
	public NotFoundException(String errorMessage) {//클라이언트에서 받을 메시지
		// TODO Auto-generated constructor stub
		super(errorMessage);
		this.errorCode="404";//404 에러임을 인지 
		this.errorMessage=errorMessage;
	}
	
	  public String getErrorCode() {
	        return errorCode;
	    }

	    @Override
	    public String getMessage() {
	        return errorMessage;
	    }
}
