package com.sist.repository.member.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.dto.member.MemberDTO;
import com.sist.dto.member.RequestFollowDTO;
import com.sist.mapper.member.FollowMapper;
import com.sist.repository.member.FollowRepository;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MyBatisFollowRepository implements FollowRepository{
	
	private final FollowMapper followMapper;
	@Override
	public List<MemberDTO> followingByKeyword(RequestFollowDTO dto) {
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

}
