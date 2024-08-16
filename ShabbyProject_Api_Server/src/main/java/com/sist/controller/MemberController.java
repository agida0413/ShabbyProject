package com.sist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.sist.service.member.mail.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
private final MailService service;
	
	@PostMapping("/emailAuth")
	public ResponseEntity<?> emailAuth(String email){
	
		return service.emailSend(email);
		
	}
	
	@PostMapping("/emailValidation")
	public ResponseEntity<?> emailValidation(String email,String code){
		return service.emailAuthValidation(email, code);
	}
	
	
	
}
