package com.sist.repository.member;

import org.springframework.stereotype.Repository;

import com.sist.dto.MemberDTO;
import com.sist.mapper.member.MemberSettingMapper;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MybatisMemberSettingRepository implements MemberSettingRepository{
	private final MemberSettingMapper memberSettingMapper;
	
	//닉네임 변경
	@Override
	public void updateNickName(MemberDTO dto) {
		// TODO Auto-generated method stub
		memberSettingMapper.updateNickName(dto);
	}
	//패스워드 변경 
	@Override
	public void updatePassword(MemberDTO dto) {
		// TODO Auto-generated method stub

		memberSettingMapper.updatePassword(dto);
	}
	//핸드폰 번호 변경
	@Override
	public void updatePhone(MemberDTO dto) {
		// TODO Auto-generated method stub
		memberSettingMapper.updatePhone(dto);
		
	}
	
	

}
