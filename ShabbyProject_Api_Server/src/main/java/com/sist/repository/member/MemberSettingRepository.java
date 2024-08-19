package com.sist.repository.member;

import com.sist.dto.MemberDTO;
import com.sist.vo.MemberVO;

public interface MemberSettingRepository {
	public void updateNickName(MemberDTO dto);
	public void updatePassword(MemberDTO dto);
	public void updatePhone(MemberDTO dto);
}
