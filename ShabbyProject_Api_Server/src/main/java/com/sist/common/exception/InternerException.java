package com.sist.common.exception;

import org.springframework.http.HttpStatus;

public class InternerException extends RuntimeException{
	
		private final String errorCode;
		private final String errorMessage;
		private final String serverErrorMsg;

		//400 에러 관련 사용자 정의 예외
		//서버 내부오류는 클라이언트에게는 단순 ' 서버내부 오류입니다. ' 를 전송하고 , 서버측에서는 상세 로그를 확인해야하니
    	//두개의 매개변수를 받아 첫번째 인자는 클라이언트에게 전송할 에러메시지 , 두번째 인자는 서버가 인지할 메시지를 받는다 .
		public InternerException(String errorMessage,String serverErrorMsg) {//클라이언트에서 받을 메시지 
			// TODO Auto-generated constructor stub
			super(errorMessage);
			this.errorCode="500";//400번 에러임을 인지 
			this.errorMessage=errorMessage;
			this.serverErrorMsg=serverErrorMsg;
		}
	
		public String getErrorCode() {
	        return errorCode;
	    }
		public String getServerErrorMsg() {
			return serverErrorMsg;
		}

	    @Override
	    public String getMessage() {
	        return errorMessage;
	    }
}
