package com.sist.mapper.member;

import org.apache.ibatis.annotations.Mapper;

import com.sist.vo.EmailAuthVO;
import com.sist.vo.MemberVO;

@Mapper
public interface memberAccountMapper {

	public MemberVO findByUserEmail(String email);
	public MemberVO findByUserNickname(String nickname);
	public MemberVO findByUserPhone(String phone);
	public void join (MemberVO vo);
	public void emailAuthSave(EmailAuthVO vo);
	public EmailAuthVO emailAuthGetValidation(String email);
	public void emailAuthClear(int emailAuthNum);
}
