package com.sist.service.member;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.feed.DoFollowDTO;
import com.sist.dto.member.FollowSearchResultDTO;

public interface FollowService {
	//검색어,행개수, 페이지 기반 현재 본인의 팔로잉 리스트를 가져옴 
	public ResponseEntity<ResponseDTO<FollowSearchResultDTO>> followingBykeyword(String keyword,int page,int rowSize);
	
	public ResponseEntity<ResponseDTO<Void>> doFollow(DoFollowDTO dto);
}
