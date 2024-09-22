package com.sist.service.primary;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.follow.DoFollowDTO;
import com.sist.dto.follow.FollowInformDTO;
import com.sist.dto.follow.FollowListDTO;
import com.sist.dto.follow.FollowSearchResultDTO;
import com.sist.dto.follow.HandleFollowReqDTO;
import com.sist.dto.follow.UnFollowDTO;

public interface FollowService {
	//검색어,행개수, 페이지 기반 현재 본인의 팔로잉 리스트를 가져옴 
	public ResponseEntity<ResponseDTO<FollowSearchResultDTO>> followingBykeyword(String keyword,int page,int rowSize);
	//인서트 팔로우(팔로잉)
	public ResponseEntity<ResponseDTO<String>> doFollow(DoFollowDTO dto);
	//딜리트 팔로우 (언팔로우)
	public ResponseEntity<ResponseDTO<Void>> unFollow(UnFollowDTO dto);
	//사용자 피드내에서 팔로우/팔로워 목록을 불러옴 ==> flwType은 팔로우냐 팔로워냐 구분
	public ResponseEntity<ResponseDTO<FollowListDTO>> getFollowInFeed(String nickname,String flwType,int page);
	//팔로우 요청 수락 or 거절 
	public ResponseEntity<ResponseDTO<Void>> handleFollowRequest(HandleFollowReqDTO dto);
	//팔로우 추천 
	public ResponseEntity<ResponseDTO<List<FollowInformDTO>>> followRecommend();
	
}
