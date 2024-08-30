package com.sist.dto.feed;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileDTO {
	private int idNum;
	private MultipartFile profileImgFile;
	private String profile;
	@Size(max = 100, message = "자기소개는 100자리이하야합니다.")
	private String introduce;
}
