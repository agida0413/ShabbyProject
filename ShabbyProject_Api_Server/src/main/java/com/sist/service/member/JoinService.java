package com.sist.service.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.sist.vo.EmailAuthVO;
import com.sist.vo.MemberVO;

public interface JoinService {
	public ResponseEntity<?> join(MemberVO vo);//회원가입 
	public ResponseEntity<?> nickNameValidation(String nickName);//검증
	public ResponseEntity<?> emailAuth(MemberVO vo);//인증번호 전송
	public ResponseEntity<?> emailValidation(EmailAuthVO vo);//인증코드 검증
	
}
