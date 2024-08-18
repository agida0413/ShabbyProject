package com.sist.repository.member;

import org.springframework.stereotype.Repository;

import com.sist.mapper.member.MemberSettingMapper;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MybatisMemberSettingRepository implements MemberSettingRepository{
	private final MemberSettingMapper memberSettingMapper;
	
	//닉네임 변경
	@Override
	public void updateNickName(MemberVO vo) {
		// TODO Auto-generated method stub
		memberSettingMapper.updateNickName(vo);
	}

}
