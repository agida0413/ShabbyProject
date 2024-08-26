package com.sist.repository.post;

import com.sist.dto.post.RequestPostDTO;

public interface PostRepository {
		//게시물 테이블 삽입
		public void postInsert(RequestPostDTO dto);
		//해당 게시물과 연관된 관심사 테이블 삽입
		public void hobbyInsert(RequestPostDTO dto);
		//해당 게시물과 연관된 인물태그 삽입
		public void followTagInsert(RequestPostDTO dto);
		//해당 게시물과 연관된 사진들 삽입
		public void postImgInsert(RequestPostDTO dto);
	
}
