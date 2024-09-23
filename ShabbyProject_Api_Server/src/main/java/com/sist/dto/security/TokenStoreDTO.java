package com.sist.dto.security;

import java.sql.Date;

import lombok.Data;
import lombok.Getter;

@Data
public class TokenStoreDTO {
	
private int toNum; //토큰 고유번호 
private int idNum; //회원 고유번호 
private String refresh; //리프레시 토큰 
private Date expiration; //유효기간
private String browser;
}
