package com.sist.controller.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.post.DoPostLikeDTO;
import com.sist.dto.post.PostDelDTO;
import com.sist.dto.post.PostDetailDTO;
import com.sist.dto.post.WritePostDTO;
import com.sist.service.post.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
	
	private final PostService postService;
	
	//게시물 등록 api
	@PostMapping
	public ResponseEntity<ResponseDTO<Void>> postInsert(@Valid WritePostDTO dto) {
		
		return postService.postInsertTransaction(dto);
	}
	//게시물 상세보기 api
	@GetMapping("/{postNum}")
	public ResponseEntity<ResponseDTO<PostDetailDTO>> postDetail(@PathVariable int postNum){
		return postService.postDetail(postNum);
	}
	//게시물 삭제 api
	@DeleteMapping
	public ResponseEntity<ResponseDTO<Void>> postDelete(@RequestBody @Valid PostDelDTO dto){
		return postService.postDelete(dto);
	}
	
	//게시물 좋아요 api
	@PostMapping("/like")
	public ResponseEntity<ResponseDTO<DoPostLikeDTO>> doPostLike(@RequestBody @Valid DoPostLikeDTO dto){
		
		return postService.doPostLike(dto);
	}
	
}
