package com.sist.common.handler;


import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.ConflictException;
import com.sist.common.exception.InternerException;
import com.sist.common.exception.NotFoundException;
import com.sist.dto.api.ResponseDTO;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    

    // 400 관련 예외 처리
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO<Void>> handleBadRequestException(BadRequestException ex) {
        log.error("BadRequestException: {}", ex.getMessage(), ex);
        
        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage()
        );

        return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
    }

    // 404 관련 예외 처리
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handleNotFoundException(NotFoundException ex) {
        log.error("NotFoundException: {}", ex.getMessage(), ex);

        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage()
        );

        return new ResponseEntity<>(responseApi, HttpStatus.NOT_FOUND);
    }

    // 409 관련 예외 처리
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseDTO<Void>> handleConflictException(ConflictException ex) {
        log.error("ConflictException: {}", ex.getMessage(), ex);

        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.CONFLICT.value(),
            ex.getMessage()
        );

        return new ResponseEntity<>(responseApi, HttpStatus.CONFLICT);
    }

    // Validation 실패 오류 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("ValidationException: {}", ex.getMessage(), ex);

        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.BAD_REQUEST.value(),
            "잘못된 입력입니다."
        );

        return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
    }

    // 서버 내부 오류 처리
    @ExceptionHandler(InternerException.class)
    public ResponseEntity<ResponseDTO<Void>> handleInternerException(InternerException ex) {
    	//서버 내부오류는 클라이언트에게는 단순 ' 서버내부 오류입니다. ' 를 전송하고 , 서버측에서는 상세 로그를 확인해야하니
    	//두개의 매개변수를 받아 첫번째 인자는 클라이언트에게 전송할 에러메시지 , 두번째 인자는 서버가 인지할 메시지를 받는다 .
        log.error("InternerException: {}", ex.getServerErrorMsg(), ex);

        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getMessage()
        );

        return new ResponseEntity<>(responseApi, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  
    // 기타 예기치 못한 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Void>> handleGenericException(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);

        ResponseDTO<Void> response = new ResponseDTO<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "서버 내부 오류입니다. 잠시 뒤 이용해 주세요."
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
   
}
