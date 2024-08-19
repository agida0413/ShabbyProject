package com.sist.mapper.member;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.EmailAuthDTO;
import com.sist.dto.MemberDTO;
import com.sist.vo.EmailAuthVO;
import com.sist.vo.MemberVO;

@Mapper
public interface MemberAccountMapper {

	public MemberDTO findByUserEmail(String email);
	public MemberDTO findByUserIdNum(int idNum);
	public MemberDTO findByUserNickname(String nickname);
	public MemberDTO findByUserPhone(String phone);
	public void join (MemberDTO dto);
	public void emailAuthSave(EmailAuthDTO dto);
	public EmailAuthDTO emailAuthGetValidation(String email);
	public void emailAuthClear(int emailAuthNum);
	public MemberDTO findEmail(MemberDTO dto);
	public void tempPasswordUpdate(MemberDTO dto);
}
