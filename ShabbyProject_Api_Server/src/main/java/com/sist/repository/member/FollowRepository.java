package com.sist.repository.member;

import java.util.List;

import com.sist.dto.feed.RequestFollowListDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.member.RequestFollowDTO;

public interface FollowRepository {
	//<!--키워드 검색을 통한 자신이 팔로잉 하는 리스트 -->
		public List<MemberDTO>followingByKeyword(RequestFollowDTO dto);
		//<!-- 전체 자신이 팔로잉 하는 리스트 -->
		public List<MemberDTO>followingAll();
		//<!--키워드 검색을 통한 자신을 팔로워 하는 리스트 -->
		public List<MemberDTO>followerByKeyword();
		//	<!-- 전체 자신을 팔로잉 하는 리스트 -->
		public List<MemberDTO>followerAll();
		//사용자 피드에서 follow, follower 가져오기
		public List<MemberDTO>getFollowInFeed(RequestFollowListDTO dto);
}
