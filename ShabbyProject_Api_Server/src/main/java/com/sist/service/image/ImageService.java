package com.sist.service.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	public String upload(MultipartFile image);
	public void deleteImage(String imageAddress); 
}
