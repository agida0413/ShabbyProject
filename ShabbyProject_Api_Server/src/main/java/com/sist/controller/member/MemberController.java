package com.sist.controller.member;

import java.net.http.HttpRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sist.service.member.JoinService;
import com.sist.service.member.MemberFindService;
import com.sist.service.member.mail.MailServiceImpl;
import com.sist.vo.EmailAuthVO;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
private final JoinService joinService;//회원가입 서비스 	
private final MemberFindService memberFindService;// 회원정보 찾기 서비스 
	
	//회원가입 
	@PostMapping
	public ResponseEntity<?> join(MemberVO vo){
		
		return joinService.join(vo);
	}
	//닉네임 중복검증 
	@PostMapping("/nickValidate")
	public ResponseEntity<?> nickNameValidation(@RequestBody MemberVO vo){
		return joinService.nickNameValidation(vo.getNickname());
	}

	//회원가입시 이메일 인증코드 받는 api
	@PostMapping("/emailAuth")
	public ResponseEntity<?> emailAuth(@RequestBody MemberVO vo){
			
		return joinService.emailAuth(vo);
		
	}
	// 회원가입시 인증번호 검증하는 api
	@PostMapping("/emailValidate")
	public ResponseEntity<?> emailValidation(@RequestBody EmailAuthVO vo){
		return joinService.emailValidation(vo);
	}
	//아이디 찾기 
	@PostMapping("/findEmail")
	public ResponseEntity<?> findId(@RequestBody MemberVO vo){
		System.out.println(vo);
		return memberFindService.findEmail(vo);
	}
	
	//패스워드 찾기/초기화 = > 이메일 서비스 
	@PostMapping("/findPassword")
	public ResponseEntity<?> findPassword(@RequestBody MemberVO vo){
		
		return memberFindService.passwordFind(vo);
	}
	
	
	
	
	
}
