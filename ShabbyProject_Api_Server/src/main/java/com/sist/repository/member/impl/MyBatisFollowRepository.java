package com.sist.repository.member.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.dto.member.MemberDTO;
import com.sist.dto.follow.DoFollowDTO;
import com.sist.dto.follow.FollowInFeedDTO;
import com.sist.dto.follow.FollowInformDTO;
import com.sist.dto.follow.FollowListDTO;
import com.sist.dto.follow.FollowSearchDTO;
import com.sist.dto.follow.HandleFollowReqDTO;
import com.sist.dto.follow.UnFollowDTO;
import com.sist.mapper.member.FollowMapper;
import com.sist.repository.member.FollowRepository;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MyBatisFollowRepository implements FollowRepository{
	
	private final FollowMapper followMapper;
	
	//<!--키워드 검색을 통한 자신이 팔로잉 하는 리스트 -->
	@Override
	public List<MemberDTO> followingByKeyword(FollowSearchDTO dto) {
		// TODO Auto-generated method stub
		return followMapper.followingByKeyword(dto);
	}



	//사용자 피드에서 follow, follower 가져오기
	@Override
	public List<FollowInformDTO> getFollowInFeed(FollowInFeedDTO dto) {
		// TODO Auto-generated method stub
		return followMapper.getFollowInFeed(dto);
	}
	//팔로우 인서트(팔로잉)
	@Override
	public void doFollow(DoFollowDTO dto) {
		// TODO Auto-generated method stub
		
		followMapper.doFollow(dto);
		
	}
	//팔로우 작업 후 클라이언트에게 상태값을 보내기 위한 데이터반환 ===> dom 리로드
	@Override
	public String afterFollow(int followNum) {
		// TODO Auto-generated method stub
		return followMapper.afterFollow(followNum);
	}
	//팔로우 delete (언팔로우)
	@Override
	public void unFollow(UnFollowDTO dto) {
		// TODO Auto-generated method stub
		
		followMapper.unFollow(dto);
	}

	@Override
	public void acceptFollow(HandleFollowReqDTO dto) {
		// TODO Auto-generated method stub
		followMapper.acceptFollow(dto);
	}

	@Override
	public void refuseFollow(HandleFollowReqDTO dto) {
		// TODO Auto-generated method stub
		followMapper.refuseFollow(dto);
	}

}
