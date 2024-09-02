package com.sist.repository.feed.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.dto.feed.GetUserFeedInformDTO;
import com.sist.dto.feed.UserFeedInformDTO;
import com.sist.dto.post.PostListDTO;
import com.sist.dto.feed.UpdateProfileDTO;
import com.sist.mapper.feed.FeedMapper;
import com.sist.repository.feed.FeedRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MybatisFeedRepository implements FeedRepository{
	private final FeedMapper feedMapper;
	
	
	// 사용자 피드에서 게시물 리스트를 제외한 피드정보 가져오기 ( 닉네임,프로필사진 ,자기소개 , 비공개여부 , 게시물등록수 , 팔로잉수 , 팔로워 수 ) 
	@Override
	public UserFeedInformDTO userFeedInfoFromMember(GetUserFeedInformDTO dto) {
		// TODO Auto-generated method stub
		return feedMapper.userFeedInfoFromMember(dto);
	}
	
	
	//현재 프로필 이미지 url 갖고오기
	@Override
	public String profileImgGet(int idNum) {
		// TODO Auto-generated method stub
		return feedMapper.profileImgGet(idNum);
	}
	//프로필 이미지 업데이트
	@Override
	public void profileImgUpdate(UpdateProfileDTO dto) {
		// TODO Auto-generated method stub
		feedMapper.profileImgUpdate(dto);
	}
	//자기소개 업데이트
	@Override
	public void introduceUpdate(UpdateProfileDTO dto) {
		// TODO Auto-generated method stub
		feedMapper.introduceUpdate(dto);
	}

}
