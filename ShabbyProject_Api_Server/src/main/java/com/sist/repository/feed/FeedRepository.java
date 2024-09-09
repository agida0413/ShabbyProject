package com.sist.repository.feed;

import java.util.List;

import com.sist.dto.feed.GetUserFeedInformDTO;
import com.sist.dto.feed.UserFeedInformDTO;
import com.sist.dto.post.PostListDTO;
import com.sist.dto.feed.UpdateProfileDTO;

public interface FeedRepository {
	// 사용자 피드에서 게시물 리스트를 제외한 피드정보 가져오기 ( 닉네임,프로필사진 ,자기소개 , 비공개여부 , 게시물등록수 , 팔로잉수 , 팔로워 수 ) 
	public UserFeedInformDTO userFeedInfoFromMember(GetUserFeedInformDTO dto);	
	// 현재 프로필 이미지 url을 갖고오기
	public String profileImgGet(int idNum);
	//프로필 이미지 url 업데이트
	public void profileImgUpdate(UpdateProfileDTO dto);
	//자기소개 업데이트
	public void introduceUpdate(UpdateProfileDTO dto);
}
