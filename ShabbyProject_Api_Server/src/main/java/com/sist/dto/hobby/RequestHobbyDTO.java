package com.sist.dto.hobby;

import lombok.Data;

@Data
public class RequestHobbyDTO {

private String keyword;
private int rowSize;
private int startRow;

}
