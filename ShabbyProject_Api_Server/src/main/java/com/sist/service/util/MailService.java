package com.sist.service.util;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;


public interface MailService {
	//이메일 인증코드 보내기 서비스
	public ResponseEntity<ResponseDTO<Void>> emailSend(String email );
	//이메일 인증코드 검증 서비스
	public ResponseEntity<ResponseDTO<Void>> emailAuthValidation(String email, String code);
	//이메일 임시패스워드 발급 서비스
	public ResponseEntity<ResponseDTO<Void>> emailPasswordReset(MemberDTO dto);
}
