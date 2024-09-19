package com.sist.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.member.MemberDTO;

@Mapper
public interface MemberSettingMapper {
	// 	<!-- 닉네임 변경 (검증 후 업데이트 sql)-->
	public void updateNickName(MemberDTO dto);
	//<!--패스워드 변경 (검증후)-->
	public void updatePassword(MemberDTO dto);
	//<!--핸드폰 번호 변경-->
	public void updatePhone(MemberDTO dto);
	// 비공개/공개모드 변경
	public void updateLockedState(MemberDTO dto);
	//회원삭제
	public void deleteMember(MemberDTO dto);
}
