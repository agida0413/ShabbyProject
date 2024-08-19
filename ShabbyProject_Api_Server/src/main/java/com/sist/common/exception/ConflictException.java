package com.sist.common.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends RuntimeException{
private final String errorCode;
private final String errorMessage;
	//409관련 예외 

	public ConflictException(String errorMessage) {//클라이언트에서 받을 메시지
		// TODO Auto-generated constructor stub
		super(errorMessage);
		this.errorCode="409";//409 에러임을 인지
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
