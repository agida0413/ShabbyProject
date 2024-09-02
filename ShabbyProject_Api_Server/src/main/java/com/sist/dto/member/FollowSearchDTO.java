package com.sist.dto.member;

import lombok.Data;

@Data
public class FollowSearchDTO {
	private String keyword;
	private int rowSize;
	private int startRow;
	private int idNum;
}
