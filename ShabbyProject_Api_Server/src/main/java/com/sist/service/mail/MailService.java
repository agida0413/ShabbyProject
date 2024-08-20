package com.sist.service.mail;

import org.springframework.http.ResponseEntity;

import com.sist.dto.member.MemberDTO;


public interface MailService {
	public ResponseEntity<?> emailSend(String email );
	public ResponseEntity<?> emailAuthValidation(String email, String code);
	public String getCertificationMessage(String certificationNumber);
	public String generateRandomCode();
	public ResponseEntity<?> emailPasswordReset(MemberDTO dto);
}
