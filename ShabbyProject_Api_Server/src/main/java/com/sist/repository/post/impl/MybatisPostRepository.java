package com.sist.repository.post.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.dto.feed.GetUserFeedInformDTO;
import com.sist.dto.post.DoPostLikeDTO;
import com.sist.dto.post.GetPostDetailDTO;
import com.sist.dto.post.PostDelDTO;
import com.sist.dto.post.PostDetailDTO;
import com.sist.dto.post.PostListDTO;
import com.sist.dto.post.WritePostDTO;
import com.sist.mapper.post.PostMapper;
import com.sist.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MybatisPostRepository implements PostRepository {
	
	private final PostMapper postMapper;
	
	//게시물 테이블 삽입
	@Override
	public void postInsert(WritePostDTO dto) {
		// TODO Auto-generated method stub
		postMapper.postInsert(dto);
	}
	//해당 게시물과 연관된 관심사 테이블 삽입
	@Override
	public void hobbyInsert(WritePostDTO dto) {
		// TODO Auto-generated method stub
		postMapper.hobbyInsert(dto);
	}
	//해당 게시물과 연관된 인물태그 삽입
	@Override
	public void followTagInsert(WritePostDTO dto) {
		// TODO Auto-generated method stub
		postMapper.followTagInsert(dto);
	}
	//해당 게시물과 연관된 사진들 삽입
	@Override
	public void postImgInsert(WritePostDTO dto) {
		// TODO Auto-generated method stub
		postMapper.postImgInsert(dto);
	}
	
	//사용자  피드에서 게시물리스트 가져오기
	@Override
	public List<PostListDTO> postList(GetUserFeedInformDTO dto) {
		// TODO Auto-generated method stub
		return postMapper.postList(dto);
	}
	//게시물 상세보기 
	@Override
	public PostDetailDTO postDetail(GetPostDetailDTO dto) {
		// TODO Auto-generated method stub
		return postMapper.postDetail(dto);
	}
	
	//게시물 좋아요 삽입 
	@Override
	public void postLikeInsert(DoPostLikeDTO dto) {
		// TODO Auto-generated method stub
		postMapper.postLikeInsert(dto);
	}
	//게시물 좋아요 제거 
	@Override
	public void postLikeDelete(DoPostLikeDTO dto) {
		// TODO Auto-generated method stub
		postMapper.postLikeDelete(dto);
	}
	//좋아요 작업 후 좋아요 개수 반환
	@Override
	public int afterDoPostLike(int postNum) {
		// TODO Auto-generated method stub
		return postMapper.afterDoPostLike(postNum);
	}
	@Override
	public void postDelete(PostDelDTO dto) {
		// TODO Auto-generated method stub
		postMapper.postDelete(dto);
	}

	@Override
	public List<String> postImgListByPostNum(int postNum) {
		// TODO Auto-generated method stub
		return postMapper.postImgListByPostNum(postNum);
	}

}
