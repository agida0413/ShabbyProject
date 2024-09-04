package com.sist.dto.post;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PostDelDTO {
private int idNum; //회원 고유번호 
@Min(value = 1, message= "0이상 정수여야함")
private int postNum;//게시물 고유번호 
}
