package com.sist.vo;

import lombok.Data;

@Data
public class TokenStoreVO {
private int toNum;
private int idNum;
private String refresh;
private String expiration;

}
