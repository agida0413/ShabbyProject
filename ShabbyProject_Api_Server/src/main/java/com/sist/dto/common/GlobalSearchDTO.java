package com.sist.dto.common;

import lombok.Data;

@Data
public class GlobalSearchDTO {
	private int idNum;
	private String keyword;
	private int rowSize;//행개수 
	private int startRow;//시작행
}
