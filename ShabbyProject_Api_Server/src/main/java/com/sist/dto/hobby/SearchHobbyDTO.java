package com.sist.dto.hobby;

import lombok.Data;

@Data
public class SearchHobbyDTO {

private String keyword; //검색어 
private int rowSize; //행개수 
private int startRow; //시작행

}
