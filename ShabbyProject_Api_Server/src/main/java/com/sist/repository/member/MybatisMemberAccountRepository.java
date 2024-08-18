package com.sist.repository.member;

import org.springframework.stereotype.Repository;

import com.sist.mapper.member.memberAccountMapper;
import com.sist.vo.EmailAuthVO;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MybatisMemberAccountRepository implements MemberAccountRepository {
private final memberAccountMapper mapper;
	
	// 이메일 기반 회원정보 찾기
	public MemberVO findByUserEmail(String email) {
		return mapper.findByUserEmail(email);
	}
	
	
	// 닉네임 기반 회원정보 찾기
	
	public MemberVO findByUserNickname(String nickname) {
		return mapper.findByUserNickname(nickname);
	}
	
	// 휴대폰번호 기반 회원정보 찾기
	public MemberVO findByUserPhone(String phone) {
		return mapper.findByUserPhone(phone);
	}
	
	// 파라미터로 멤버vo 받아 데이터베이스에 저장 
	public void join (MemberVO vo) {
		mapper.join(vo);
	}
	
	//회원가입시 이메일 인증코드 전송, 데이터저장 
	public void emailAuthSave(EmailAuthVO vo) {
		mapper.emailAuthSave(vo);
	}
	//저장된 인정코드 검증 
	public EmailAuthVO emailAuthGetValidation(String email) {
		
		return mapper.emailAuthGetValidation(email);
	}
	
	//인증상태 y 로 업데이트
	public void emailAuthClear(int emailAuthNum) {
		mapper.emailAuthClear(emailAuthNum);
	}
	
	//아이디 찾기 
	public MemberVO findEmail(MemberVO vo) {
	return	mapper.findEmail(vo);
	}
	
	
	//임시패스워드발급 = > 데이터베이스 저장 
	public void tempPasswordUpdate(MemberVO vo) {
		mapper.tempPasswordUpdate(vo);
	}
}
