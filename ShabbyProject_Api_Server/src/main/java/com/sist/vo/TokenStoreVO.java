package com.sist.vo;

import lombok.Data;

@Data
public class TokenStoreVO {
private int to_num;
private int id_num;
private String refresh;
private String expiration;

}
