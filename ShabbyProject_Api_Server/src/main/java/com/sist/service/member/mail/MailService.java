package com.sist.service.member.mail;

import org.springframework.http.ResponseEntity;

import com.sist.dto.MemberDTO;
import com.sist.vo.MemberVO;

public interface MailService {
	public ResponseEntity<?> emailSend(String email );
	public ResponseEntity<?> emailAuthValidation(String email, String code);
	public String getCertificationMessage(String certificationNumber);
	public String generateRandomCode();
	public ResponseEntity<?> emailPasswordReset(MemberDTO dto);
}
