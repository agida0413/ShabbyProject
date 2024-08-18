package com.sist.mapper.member;

import org.apache.ibatis.annotations.Mapper;

import com.sist.vo.MemberVO;

@Mapper
public interface MemberSettingMapper {
	public void updateNickName(MemberVO vo);//닉네임 변경
}
