package com.sist.dto.follow;

import lombok.Data;

@Data
public class FollowSearchDTO {
	
	private String keyword; //검색어 
	private int rowSize; //행개수 
	private int startRow; //시작 행 
	private int idNum; //회원 고유버호 
}
