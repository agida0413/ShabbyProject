package com.sist.service.member.manage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.sist.dto.member.EmailAuthDTO;
import com.sist.dto.member.MemberDTO;


public interface JoinService {
	public ResponseEntity<?> join(MemberDTO dto);//회원가입 
	public ResponseEntity<?> nickNameValidation(String nickName);//검증
	public ResponseEntity<?> emailAuth(MemberDTO dto);//인증번호 전송
	public ResponseEntity<?> emailValidation(EmailAuthDTO dto);//인증코드 검증
	
}
