package com.sist.dto.feed;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateProfileDTO {
	private int idNum;
	private MultipartFile profileImgFile;
	private String profile;
	private String introduce;
}
