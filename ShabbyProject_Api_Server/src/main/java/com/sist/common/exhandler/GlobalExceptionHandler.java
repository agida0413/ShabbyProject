package com.sist.common.exhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.ConflictException;
import com.sist.common.exception.NotFoundException;
import com.sist.dto.api.ResponseDTO;


//스프링 시큐리티 외 예외 => 전역 예외처리 
@RestControllerAdvice
public class GlobalExceptionHandler {
	
		//400관련 예외 
	 @ExceptionHandler(BadRequestException.class)
	    public ResponseEntity<ResponseDTO<Void>> handleBadRequestException(BadRequestException ex) {

	        ResponseDTO<Void> responseApi = new ResponseDTO<Void>(
	         
	            HttpStatus.BAD_REQUEST.value(),
	            ex.getMessage()
	          
	        );
	        
	        return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
	    }
	
	
	 //404관련 예외
	 @ExceptionHandler(NotFoundException.class)
	    public ResponseEntity<ResponseDTO<Void>> handleNotfoundException(NotFoundException ex) {

	        ResponseDTO<Void> responseApi = new ResponseDTO<Void>(
	          
	            HttpStatus.NOT_FOUND.value(),
	            ex.getMessage()
	          
	        );
	        
	        return new ResponseEntity<>(responseApi, HttpStatus.NOT_FOUND);
	    }
	 //409관련 예외
	 @ExceptionHandler(ConflictException.class)
	    public ResponseEntity<ResponseDTO<Void>> handleConflictException(ConflictException ex) {

	        ResponseDTO<Void> responseApi = new ResponseDTO<Void>(
	         
	            HttpStatus.CONFLICT.value(),
	            ex.getMessage()
	         
	        );
	        
	        return new ResponseEntity<>(responseApi, HttpStatus.CONFLICT);
	    }
	 
	
	 //그외 예기치 못한 예외 === 서버오류로 정의
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<ResponseDTO<Void>> handleGenericException(Exception ex) {
	        ResponseDTO<Void> response = new ResponseDTO<>(
	           
	            HttpStatus.INTERNAL_SERVER_ERROR.value(),
	            "서버 내부오류입니다. 잠시 뒤 이용해주세요."//메시지
	       
	        );

	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
}
