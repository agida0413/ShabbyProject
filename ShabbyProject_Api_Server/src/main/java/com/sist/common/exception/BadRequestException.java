package com.sist.common.exception;

public class BadRequestException extends RuntimeException{
	
		private final String errorCode;
		private final String errorMessage;
		
	
		
	    //400 에러 관련 사용자 정의 예외
		public BadRequestException(String errorMessage) {//클라이언트에서 받을 메시지 
			// TODO Auto-generated constructor stub
			super(errorMessage);
			this.errorCode="400";//400번 에러임을 인지 
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
