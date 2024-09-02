package com.sist.repository.member.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.dto.feed.DoFollowDTO;
import com.sist.dto.feed.FollowInFeedDTO;
import com.sist.dto.feed.FollowListDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.member.FollowSearchDTO;
import com.sist.mapper.member.FollowMapper;
import com.sist.repository.member.FollowRepository;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MyBatisFollowRepository implements FollowRepository{
	
	private final FollowMapper followMapper;
	@Override
	public List<MemberDTO> followingByKeyword(FollowSearchDTO dto) {
		// TODO Auto-generated method stub
		return followMapper.followingByKeyword(dto);
	}

	@Override
	public List<MemberDTO> followingAll() {
		// TODO Auto-generated method stub
		return followMapper.followingAll();
	}

	@Override
	public List<MemberDTO> followerByKeyword() {
		// TODO Auto-generated method stub
		return followMapper.followerByKeyword();
	}

	@Override
	public List<MemberDTO> followerAll() {
		// TODO Auto-generated method stub
		return followMapper.followerAll();
	}

	@Override
	public List<FollowListDTO> getFollowInFeed(FollowInFeedDTO dto) {
		// TODO Auto-generated method stub
		return followMapper.getFollowInFeed(dto);
	}

	@Override
	public void doFollow(DoFollowDTO dto) {
		// TODO Auto-generated method stub
		
		followMapper.doFollow(dto);
		
	}

}
