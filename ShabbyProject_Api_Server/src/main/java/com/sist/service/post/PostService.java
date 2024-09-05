package com.sist.service.post;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.post.DoPostLikeDTO;
import com.sist.dto.post.PostDelDTO;
import com.sist.dto.post.PostDetailDTO;
import com.sist.dto.post.WritePostDTO;

public interface PostService {

	//게시물 삽입 트랜잭션 서비스
	public ResponseEntity<ResponseDTO<Void>> postInsertTransaction(WritePostDTO dto);
	//게시물 상세보기 서비스
	public ResponseEntity<ResponseDTO<PostDetailDTO>> postDetail(int postNum);
	//게시물 삭제 서비스 
	public ResponseEntity<ResponseDTO<Void>> postDelete(PostDelDTO dto);
	//게시물 수정 서비스
	public ResponseEntity<ResponseDTO<Void>> postUpdate(WritePostDTO dto);
	//좋아요 서비스 
	public ResponseEntity<ResponseDTO<DoPostLikeDTO>> doPostLike(DoPostLikeDTO dto);
	
	
	

}
