package com.sist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.member.MemberDTO;
import com.sist.dto.follow.DoFollowDTO;
import com.sist.dto.follow.FollowInFeedDTO;
import com.sist.dto.follow.FollowInformDTO;
import com.sist.dto.follow.FollowListDTO;
import com.sist.dto.follow.FollowSearchDTO;
import com.sist.dto.follow.HandleFollowReqDTO;
import com.sist.dto.follow.UnFollowDTO;
@Mapper
public interface FollowMapper {
	//<!--키워드 검색을 통한 자신이 팔로잉 하는 리스트 -->
	public List<MemberDTO>followingByKeyword(FollowSearchDTO dto);
	
	//사용자 피드에서 follow, follower 가져오기
	public List<FollowInformDTO>getFollowInFeed(FollowInFeedDTO dto);
	
	//팔로우 인서트(팔로잉)
	public void doFollow(DoFollowDTO dto);
	//팔로우 작업 후 클라이언트에게 상태값을 보내기 위한 데이터반환 ===> dom 리로드
	public String afterFollow(int followNum);
	//팔로우 delete (언팔로우)
	public void unFollow(UnFollowDTO dto);
	//팔로우 요청 수락
	public void acceptFollow(HandleFollowReqDTO dto);
	//팔로우 요청 거절 
	public void refuseFollow(HandleFollowReqDTO dto);
	//공개 계정 전환시 팔로우 대기- > 수락 변경
	public void changeFollowStatus(int idNum);
	//팔로우 추천
	public List<FollowInformDTO> followRecommend(int idNum);
}	
