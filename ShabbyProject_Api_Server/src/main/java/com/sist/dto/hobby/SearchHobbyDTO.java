package com.sist.dto.hobby;

import lombok.Data;

@Data
public class SearchHobbyDTO {

private String keyword;
private int rowSize;
private int startRow;

}
