package com.sist.service.feed;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;

import com.sist.dto.feed.ResponsePostListDTO;
import com.sist.dto.feed.ResponseUserFeedDTO;
import com.sist.dto.feed.UpdateProfileDTO;

public interface FeedService {
	//사용자 피드 정보 ==> 게시물 제외
	public ResponseEntity<ResponseDTO<ResponseUserFeedDTO>> loadUserFeedInfo(String nickname);
	//사용자 피드 정보 == >게시물
	public ResponseEntity<ResponseDTO<List<ResponsePostListDTO>>> loadUserFeedPostList(String nickname,int page);
	
	public ResponseEntity<ResponseDTO<Void>> updateProfileImg(UpdateProfileDTO dto);
	
	public ResponseEntity<ResponseDTO<Void>> updateIntroduce(UpdateProfileDTO dto);
}
