package com.sist.service.post;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.post.WritePostDTO;

public interface PostService {

	//게시물 삽입 트랜잭션 서비스
	public ResponseEntity<ResponseDTO<Void>> postInsertTransaction(WritePostDTO dto);
}
