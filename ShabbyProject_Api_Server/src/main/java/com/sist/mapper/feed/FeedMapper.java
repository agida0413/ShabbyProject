package com.sist.mapper.feed;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.feed.RequestUserFeedDTO;
import com.sist.dto.feed.ResponsePostListDTO;
import com.sist.dto.feed.ResponseUserFeedDTO;
import com.sist.dto.feed.UpdateProfileDTO;

@Mapper
public interface FeedMapper {
	// 사용자 피드에서 게시물 리스트를 제외한 피드정보 가져오기 ( 닉네임,프로필사진 ,자기소개 , 비공개여부 , 게시물등록수 , 팔로잉수 , 팔로워 수 ) 
	public ResponseUserFeedDTO userFeedInfoFromMember(RequestUserFeedDTO dto);
	//사용자  피드에서 게시물리스트 가져오기
	public List<ResponsePostListDTO> userFeedPostList(RequestUserFeedDTO dto);
	
	public String profileImgGet(int idNum);
	public void profileImgUpdate(UpdateProfileDTO dto);
	
	public void introduceUpdate(UpdateProfileDTO dto);
}
