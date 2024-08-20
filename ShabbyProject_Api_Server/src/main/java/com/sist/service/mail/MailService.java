package com.sist.service.mail;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;


public interface MailService {
	public ResponseEntity<ResponseDTO<Void>> emailSend(String email );
	public ResponseEntity<ResponseDTO<Void>> emailAuthValidation(String email, String code);
	public ResponseEntity<ResponseDTO<Void>> emailPasswordReset(MemberDTO dto);
}
