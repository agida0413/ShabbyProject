package com.sist.controller.member;

import java.net.http.HttpRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.member.EmailAuthDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.service.mail.impl.MailServiceImpl;
import com.sist.service.member.manage.JoinService;
import com.sist.service.member.manage.MemberFindService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
private final JoinService joinService;//회원가입 서비스 	
private final MemberFindService memberFindService;// 회원정보 찾기 서비스 
	
	//회원가입 
	@PostMapping
	public ResponseEntity<?> join(MemberDTO dto){
		
		return joinService.join(dto);
	}
	//닉네임 중복검증 
	@PostMapping("/nickValidate")
	public ResponseEntity<?> nickNameValidation(@RequestBody MemberDTO dto){
		return joinService.nickNameValidation(dto.getNickname());
	}

	//회원가입시 이메일 인증코드 받는 api
	@PostMapping("/emailAuth")
	public ResponseEntity<?> emailAuth(@RequestBody MemberDTO dto){
			
		return joinService.emailAuth(dto);
		
	}
	// 회원가입시 인증번호 검증하는 api
	@PostMapping("/emailValidate")
	public ResponseEntity<?> emailValidation(@RequestBody EmailAuthDTO dto){
		return joinService.emailValidation(dto);
	}
	//아이디 찾기 
	@PostMapping("/findEmail")
	public ResponseEntity<?> findId(@RequestBody MemberDTO dto){
		
		return memberFindService.findEmail(dto);
	}
	
	//패스워드 찾기/초기화 = > 이메일 서비스 
	@PostMapping("/findPassword")
	public ResponseEntity<?> findPassword(@RequestBody MemberDTO dto){
		
		return memberFindService.passwordFind(dto);
	}
	
	
	
	
	
}
