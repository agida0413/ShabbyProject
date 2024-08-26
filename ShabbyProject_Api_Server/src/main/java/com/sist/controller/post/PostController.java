package com.sist.controller.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.post.RequestPostDTO;
import com.sist.service.post.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
	
	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<ResponseDTO<Void>> postInsert(RequestPostDTO dto) {
		
		return postService.postInsertTransaction(dto);
	}
}
