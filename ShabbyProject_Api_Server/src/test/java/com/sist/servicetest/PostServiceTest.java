package com.sist.servicetest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.post.DoPostLikeDTO;
import com.sist.dto.post.PostDelDTO;
import com.sist.dto.post.PostDetailDTO;
import com.sist.dto.post.WritePostDTO;
import com.sist.service.post.PostService;
@Component
public class PostServiceTest implements PostService {

	@Override
	public ResponseEntity<ResponseDTO<Void>> postInsertTransaction(WritePostDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Test
	@Override
	public ResponseEntity<ResponseDTO<PostDetailDTO>> postDetail(int postNum) {
		// TODO Auto-generated method stub
		postNum=1;
		int i=2;
		
		String name="홍길동";
		String name2="임하경";
		assertThat(name).isEqualTo(name2);
	
		
		return null;
	}

	@Override
	public ResponseEntity<ResponseDTO<Void>> postDelete(PostDelDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseDTO<Void>> postUpdate(WritePostDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseDTO<DoPostLikeDTO>> doPostLike(DoPostLikeDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
