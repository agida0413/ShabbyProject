package com.sist.mapper.member;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.MemberDTO;
import com.sist.vo.MemberVO;

@Mapper
public interface MemberSettingMapper {
	public void updateNickName(MemberDTO dto);//닉네임 변경
	public void updatePassword(MemberDTO dto);//패스워드 변경
	public void updatePhone(MemberDTO dto);//핸드폰 번호 변경
}
