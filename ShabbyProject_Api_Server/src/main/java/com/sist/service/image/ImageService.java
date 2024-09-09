package com.sist.service.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	//이미지 저장 
	public String upload(MultipartFile image);
	//이미지 삭제 
	public void deleteImage(String imageAddress); 
}
