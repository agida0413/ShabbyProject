package com.sist.common;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ResponseApi<T> {

    private boolean success;
    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    //요청 실패시의 ResponseApi
    public ResponseApi(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
    //요청 성공시의 ResponseApi
    public ResponseApi(T data) {
        this.success=true;
        this.code = 200;
        this.message = "요청성공";
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
