package com.sist.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;

@Data
public class EmailAuthDTO {
private int emailAuthNum;
private String email;
private String code;
private LocalDateTime expiration;
private String isAuth;
}
