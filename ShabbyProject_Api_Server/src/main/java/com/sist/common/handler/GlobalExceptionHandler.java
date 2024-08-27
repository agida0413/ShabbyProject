package com.sist.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.ConflictException;
import com.sist.common.exception.InternerException;
import com.sist.common.exception.NotFoundException;
import com.sist.dto.api.ResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 400 관련 예외 처리
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO<Void>> handleBadRequestException(BadRequestException ex) {
        logger.error("BadRequestException: {}", ex.getMessage(), ex);

        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage()
        );

        return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
    }

    // 404 관련 예외 처리
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handleNotFoundException(NotFoundException ex) {
        logger.error("NotFoundException: {}", ex.getMessage(), ex);

        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage()
        );

        return new ResponseEntity<>(responseApi, HttpStatus.NOT_FOUND);
    }

    // 409 관련 예외 처리
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseDTO<Void>> handleConflictException(ConflictException ex) {
        logger.error("ConflictException: {}", ex.getMessage(), ex);

        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.CONFLICT.value(),
            ex.getMessage()
        );

        return new ResponseEntity<>(responseApi, HttpStatus.CONFLICT);
    }

    // Validation 실패 오류 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        logger.error("ValidationException: {}", ex.getMessage(), ex);

        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.BAD_REQUEST.value(),
            "잘못된 입력입니다."
        );

        return new ResponseEntity<>(responseApi, HttpStatus.BAD_REQUEST);
    }

    // 서버 내부 오류 처리
    @ExceptionHandler(InternerException.class)
    public ResponseEntity<ResponseDTO<Void>> handleInternerException(InternerException ex) {
        logger.error("InternerException: {}", ex.getMessage(), ex);

        ResponseDTO<Void> responseApi = new ResponseDTO<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getMessage()
        );

        return new ResponseEntity<>(responseApi, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 기타 예기치 못한 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Void>> handleGenericException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage(), ex);

        ResponseDTO<Void> response = new ResponseDTO<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "서버 내부 오류입니다. 잠시 뒤 이용해 주세요."
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
