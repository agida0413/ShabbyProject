package com.sist.controller.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.common.exception.BadRequestException;
import com.sist.common.util.PathVariableValidation;
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
		//태그리스트 validation
		if (!PathVariableValidation.keyWordValService(dto.getHobbyList())
			|| !PathVariableValidation.keyWordValService(dto.getFollowTagList())) {
			throw new BadRequestException("태그에 포함되면 안되는 문자가 포함되어있습니다.('#','@',',')");
		}
		return postService.postInsertTransaction(dto);
	}
	//게시물 상세보기 api
	@GetMapping("/{postNum}")
	public ResponseEntity<ResponseDTO<PostDetailDTO>> postDetail(@PathVariable int postNum){
		//정수가 아닐경우
		if (!PathVariableValidation.pageValidation(postNum)) {
			throw new BadRequestException("유효하지 않은 입력입니다.");
		}
		return postService.postDetail(postNum);
	}
	//게시물 삭제 api
	@DeleteMapping
	public ResponseEntity<ResponseDTO<Void>> postDelete(@RequestBody @Valid PostDelDTO dto){
		return postService.postDelete(dto);
	}
	//게시물 수정 api
	@PutMapping
	public ResponseEntity<ResponseDTO<Void>> postUpdate(@Valid WritePostDTO dto){
		//수정할려는 작업에 사진이 없는경우 validation	
		if(dto.getCheckimgNull()==null || dto.getCheckimgNull().size()==0) {
			throw new BadRequestException("사진은 한장이상 첨부해야합니다.");
		}
		return postService.postUpdate(dto);
	}
	
	//게시물 좋아요 api
	@PostMapping("/like")
	public ResponseEntity<ResponseDTO<DoPostLikeDTO>> doPostLike(@RequestBody @Valid DoPostLikeDTO dto){
		
		return postService.doPostLike(dto);
	}
	
}
