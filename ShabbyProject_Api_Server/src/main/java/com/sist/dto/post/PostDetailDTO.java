package com.sist.dto.post;

import java.util.List;

import lombok.Data;

@Data
public class PostDetailDTO {
	 private int postNum; //게시물 고유번호 
	 private int idNum; //회원 고유번호 
	 private String content; //게시물 내용 
	 private String createDate; //생성일 
	 private String editDate;//수정일 
	 private String onlyMe; //나만보기인 게시물인지 
	 private String nickname; //닉네임 
	 private int likeCount; //좋아요 개수 
	 private String strImgList; // 쉼표로 구분된 이미지 URL 목록
	 private String strHobbyList; // 쉼표로 구분된 취미 목록
	 private String strTagList; // 쉼표로 구분된 태그 목록
	 private String profile; //프로필 사진 
	 private boolean liked; //현재 게시물을 보고있는 로그인한 유저가 이 게시물에 대해 좋아요를 눌렀는지 여부 
	 private int likeCheck; //liked 필드를 클라이언트로 보내기 위해 판별을 하는 데이터베이스로 부터 받은 행의 개수(join 하여 얻은값) 
	 private boolean itsMe; //현재 게시물이 내게시물 인지 판별 
	 
	 //실제 json으로 변형될 리스트(,을 짤라 배열로 변환)  
	 private List<String> imgList;  
	 private List<String> hobbyList;
	 private List<String> tagList;
	 
}
