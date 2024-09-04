package com.sist.dto.feed;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileDTO {
	private int idNum;//회원고유번호 
	private MultipartFile profileImgFile; //사진파일  
	private String profile;//이미지 url = > 데이터베이스 저장 
	@Size(max = 100, message = "자기소개는 100자리이하야합니다.")
	private String introduce; //자기소개
}
