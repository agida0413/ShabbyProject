package com.sist.repository;

import com.sist.dto.member.MemberDTO;


public interface MemberSettingRepository {
// 	<!-- 닉네임 변경 (검증 후 업데이트 sql)-->
	public void updateNickName(MemberDTO dto);
	//<!--패스워드 변경 (검증후)-->
	public void updatePassword(MemberDTO dto);
	//<!--핸드폰 번호 변경-->
	public void updatePhone(MemberDTO dto);
	//공개/비공개 모드 변경
	public void updateLockedState(MemberDTO dto);
	//회원삭제
	public void deleteMember(MemberDTO dto);

}
