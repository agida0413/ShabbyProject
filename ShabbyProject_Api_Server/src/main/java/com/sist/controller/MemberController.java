package com.sist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.sist.service.member.JoinService;
import com.sist.service.member.mail.MailService;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
private final MailService mailService;//메일 인증관련 
private final JoinService joinService;//회원가입 서비스 	
	
	//회원가입 
	@PostMapping
	public ResponseEntity<?> join(MemberVO vo){
		
		return joinService.join(vo);
	}
	//닉네임 중복검증 
	@PostMapping("/nickValidate")
	public ResponseEntity<?> nickNameValidation(String nickName){
		return joinService.nickNameValidation(nickName);
	}

	//회원가입시 이메일 인증코드 받는 api
	@PostMapping("/emailAuth")
	public ResponseEntity<?> emailAuth(String email){
	
		return mailService.emailSend(email);
		
	}
	// 회원가입시 인증번호 검증하는 api
	@PostMapping("/emailValidate")
	public ResponseEntity<?> emailValidation(String email,String code){
		return mailService.emailAuthValidation(email, code);
	}
	
	
	
}
