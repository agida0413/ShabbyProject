package com.sist.mapper.post;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.feed.GetUserFeedInformDTO;
import com.sist.dto.post.DoPostLikeDTO;
import com.sist.dto.post.GetPostDetailDTO;
import com.sist.dto.post.PostDelDTO;
import com.sist.dto.post.PostDetailDTO;
import com.sist.dto.post.PostListDTO;
import com.sist.dto.post.WritePostDTO;
@Mapper
public interface PostMapper {
	
	//게시물 테이블 삽입
	public void postInsert(WritePostDTO dto);
	//해당 게시물과 연관된 관심사 테이블 삽입
	public void hobbyInsert(WritePostDTO dto);
	//해당 게시물과 연관된 인물태그 삽입
	public void followTagInsert(WritePostDTO dto);
	//해당 게시물과 연관된 사진들 삽입
	public void postImgInsert(WritePostDTO dto);
	//사용자  피드에서 게시물리스트 가져오기
	public List<PostListDTO> postList(GetUserFeedInformDTO dto);
	
	//게시물 상세보기 
	public PostDetailDTO postDetail(GetPostDetailDTO dto);
	//게시물 삭제 
	public void postDelete(PostDelDTO dto);
	
	
	//postnum기반 이미지 리스트
	public List<String> postImgListByPostNum(int postNum);
	//좋아요 삽입, 삭제
	public void postLikeInsert(DoPostLikeDTO dto);
	public void postLikeDelete(DoPostLikeDTO dto);

	//좋아요 작업 후 좋아요 카운트
	public int afterDoPostLike(int postNum);
}
