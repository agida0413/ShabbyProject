package com.sist.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class TokenStoreDTO {
private int toNum;
private int idNum;
private String refresh;
private String expiration;

}
