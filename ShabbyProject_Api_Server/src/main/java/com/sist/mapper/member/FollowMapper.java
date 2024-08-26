package com.sist.mapper.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.member.MemberDTO;
import com.sist.dto.member.RequestFollowDTO;
@Mapper
public interface FollowMapper {
	//<!--키워드 검색을 통한 자신이 팔로잉 하는 리스트 -->
	public List<MemberDTO>followingByKeyword(RequestFollowDTO dto);
	//<!-- 전체 자신이 팔로잉 하는 리스트 -->
	public List<MemberDTO>followingAll();
	//<!--키워드 검색을 통한 자신을 팔로워 하는 리스트 -->
	public List<MemberDTO>followerByKeyword();
	//	<!-- 전체 자신을 팔로잉 하는 리스트 -->
	public List<MemberDTO>followerAll();
}
