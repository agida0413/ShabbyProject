package com.sist.dto.post;



import lombok.Data;

@Data
public class PostListDTO {
private int postNum;//게시물 고유번호 
private String postImgUrl; //이미지
private String onlyMe; //나만보기 기능의 게시물인지 
private int likeCount; //좋아요 개수 

}
