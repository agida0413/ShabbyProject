package com.sist.dto.follow;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FollowInFeedDTO {
	
	private int rowSize; //행개수 
	private int page; //페이지 
	private int startRow; //offest
	private int idNum; //회원 고유번호 
	private String nickname; //닉네임 
	private String flwType; //팔로우 or 팔로워 구분 하는 변수 
}
