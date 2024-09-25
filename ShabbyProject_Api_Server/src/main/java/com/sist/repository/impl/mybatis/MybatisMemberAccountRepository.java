package com.sist.repository.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.sist.dto.member.EmailAuthDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.mapper.MemberAccountMapper;
import com.sist.repository.MemberAccountRepository;

import lombok.RequiredArgsConstructor;
//회원가입 , 로그인 , 회원정보가져오기 , 비밀번호찾기 , 아이디찾기 등 회원정보관련 
@RequiredArgsConstructor
@Repository
public class MybatisMemberAccountRepository implements MemberAccountRepository {
private final MemberAccountMapper mapper;
	
	// 이메일 기반 회원정보 찾기
	public MemberDTO findByUserEmail(String email) {
		return mapper.findByUserEmail(email);
	}
	//고유번호 기반 회원정보 찾기
	@Override
	public MemberDTO findByIdNum(int idNum) {
		// TODO Auto-generated method stub
		return mapper.findByUserIdNum(idNum);
	}
	//고유번호 기반 회원 자기소개 찾기
	@Override
	public MemberDTO introduceByUserIdNum(int idNum) {
		// TODO Auto-generated method stub
		return mapper.introduceByUserIdNum(idNum);
	}
	// 닉네임 기반 회원정보 찾기
	
	public MemberDTO findByUserNickname(String nickname) {
		return mapper.findByUserNickname(nickname);
	}
	
	// 휴대폰번호 기반 회원정보 찾기
	public MemberDTO findByUserPhone(String phone) {
		return mapper.findByUserPhone(phone);
	}
	
	// 파라미터로 멤버vo 받아 데이터베이스에 저장 
	public void join (MemberDTO dto) {
		mapper.join(dto);
	}
	
	//회원가입시 이메일 인증코드 전송, 데이터저장 
	public void emailAuthSave(EmailAuthDTO dto) {
		mapper.emailAuthSave(dto);
	}
	//저장된 인정코드 검증 
	public EmailAuthDTO emailAuthGetValidation(String email) {
		
		return mapper.emailAuthGetValidation(email);
	}
	
	//인증상태 y 로 업데이트
	public void emailAuthClear(int emailAuthNum) {
		mapper.emailAuthClear(emailAuthNum);
	}
	
	//아이디 찾기 
	public MemberDTO findEmail(MemberDTO dto) {
	return	mapper.findEmail(dto);
	}
	
	
	//임시패스워드발급 = > 데이터베이스 저장 
	public void tempPasswordUpdate(MemberDTO dto) {
		mapper.tempPasswordUpdate(dto);
	}
	
	@Override
	public String getLock(String nickname) {
		// TODO Auto-generated method stub
		return mapper.getLock(nickname);
	}
	@Override
	public EmailAuthDTO emailAuthBeforeJoin(String email) {
		// TODO Auto-generated method stub
		return mapper.emailAuthBeforeJoin(email);
	}
	



}
