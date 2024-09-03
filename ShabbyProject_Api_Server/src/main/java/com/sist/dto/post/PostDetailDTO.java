package com.sist.dto.post;

import java.util.List;

import lombok.Data;

@Data
public class PostDetailDTO {
	 private int postNum;
	 private int idNum;
	 private String content;
	 private String createDate;
	 private String editDate;
	 private String onlyMe;
	 private String nickname;
	 private String strImgList; // 쉼표로 구분된 이미지 URL 목록
	 private String strHobbyList; // 쉼표로 구분된 취미 목록
	 private String strTagList; // 쉼표로 구분된 태그 목록
	 
	 //실제 json으로 변형될 리스트 
	 private List<String> imgList; 
	 private List<String> hobbyList;
	 private List<String> tagList;
}
