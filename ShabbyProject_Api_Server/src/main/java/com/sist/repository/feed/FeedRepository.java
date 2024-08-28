package com.sist.repository.feed;

import java.util.List;

import com.sist.dto.feed.RequestUserFeedDTO;
import com.sist.dto.feed.ResponsePostListDTO;
import com.sist.dto.feed.ResponseUserFeedDTO;

public interface FeedRepository {
	// 사용자 피드에서 게시물 리스트를 제외한 피드정보 가져오기 ( 닉네임,프로필사진 ,자기소개 , 비공개여부 , 게시물등록수 , 팔로잉수 , 팔로워 수 ) 
	public ResponseUserFeedDTO userFeedInfoFromMember(RequestUserFeedDTO dto);
	//사용자  피드에서 게시물리스트 가져오기
	public List<ResponsePostListDTO> userFeedPostList(RequestUserFeedDTO dto);
}
