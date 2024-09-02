package com.sist.service.member;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.follow.DoFollowDTO;
import com.sist.dto.follow.FollowListDTO;
import com.sist.dto.follow.FollowSearchResultDTO;
import com.sist.dto.follow.UnFollowDTO;

public interface FollowService {
	//검색어,행개수, 페이지 기반 현재 본인의 팔로잉 리스트를 가져옴 
	public ResponseEntity<ResponseDTO<FollowSearchResultDTO>> followingBykeyword(String keyword,int page,int rowSize);
	
	public ResponseEntity<ResponseDTO<String>> doFollow(DoFollowDTO dto);
	
	public ResponseEntity<ResponseDTO<FollowListDTO>> getFollowInFeed(String nickname,String flwType,int page);


	public ResponseEntity<ResponseDTO<Void>> unFollow(UnFollowDTO dto);
}
