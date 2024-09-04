package com.sist.dto.post;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class DoPostLikeDTO {
	
private int idNum; //회원고유번호
@Min(value = 1, message= "0이상 정수여야함")
private int postNum; //게시물 고유번호 
@NotNull(message = "null일 수 없음")
private boolean liked; //현재 로그인한 회원이 좋아요를 눌렀는지 아닌지 
private int likeCount; //좋아요 개수

}
