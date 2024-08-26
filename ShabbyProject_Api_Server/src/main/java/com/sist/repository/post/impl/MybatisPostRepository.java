package com.sist.repository.post.impl;

import org.springframework.stereotype.Repository;

import com.sist.dto.post.RequestPostDTO;
import com.sist.mapper.post.PostMapper;
import com.sist.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MybatisPostRepository implements PostRepository {
	
	private final PostMapper postMapper;
	
	//게시물 테이블 삽입
	@Override
	public void postInsert(RequestPostDTO dto) {
		// TODO Auto-generated method stub
		postMapper.postInsert(dto);
	}
	//해당 게시물과 연관된 관심사 테이블 삽입
	@Override
	public void hobbyInsert(RequestPostDTO dto) {
		// TODO Auto-generated method stub
		postMapper.hobbyInsert(dto);
	}
	//해당 게시물과 연관된 인물태그 삽입
	@Override
	public void followTagInsert(RequestPostDTO dto) {
		// TODO Auto-generated method stub
		postMapper.followTagInsert(dto);
	}
	//해당 게시물과 연관된 사진들 삽입
	@Override
	public void postImgInsert(RequestPostDTO dto) {
		// TODO Auto-generated method stub
		postMapper.postImgInsert(dto);
	}

}
