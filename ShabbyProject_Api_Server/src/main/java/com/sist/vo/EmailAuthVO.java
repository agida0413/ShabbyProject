package com.sist.vo;

import lombok.Data;

@Data
public class EmailAuthVO {
private int emailAuthNum;
private int idNum;
private String code;
private String expiration;
private String isAuth;
}
