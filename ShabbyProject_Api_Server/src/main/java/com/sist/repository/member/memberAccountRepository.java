package com.sist.repository.member;

import org.springframework.stereotype.Repository;

import com.sist.mapper.member.memberAccountMapper;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class memberAccountRepository {
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
}
