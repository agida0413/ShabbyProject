package com.sist.service.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.sist.repository.member.memberAccountRepository;
import com.sist.service.member.mail.MailService;
import com.sist.vo.EmailAuthVO;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService{

	
private final memberAccountRepository memberAccountRepository;
private final MailService mailService; //이메일 인증관련 
private final BCryptPasswordEncoder bCryptPasswordEncoder;

	//회원가입 
	public ResponseEntity<?> join(MemberVO vo){
			
		if(vo.getIntroduce().equals("")) {
			vo.setIntroduce(null); // 만약 자기소개 입력안했을시에는 데이터베이스에 널값으로 저장 =- > 관리하기 편할거같음 
		}
		String password=vo.getPassword();
		String phone=vo.getPhone();
		System.out.println(vo.getPhone());
		vo.setPassword(bCryptPasswordEncoder.encode(password)); // 패스워드 암호화
		vo.setPhone(bCryptPasswordEncoder.encode(phone)); //핸드폰번호 암호화
		
		vo.setRole("ROLE_USER"); //일반회원 권한 
		
		try {
			memberAccountRepository.join(vo);//회원가입 데이터베이스 저장 
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("server err",HttpStatus.INTERNAL_SERVER_ERROR);//익셉션 시 500 응답코드 
		}
		
		return new ResponseEntity<>("OK",HttpStatus.OK); //성공 
		
	}
	
	//닉네임 중복검증완료
	public ResponseEntity<?> nickNameValidation(String nickName){
			MemberVO vo = memberAccountRepository.findByUserNickname(nickName);
			
			if(vo!=null) {
				return new ResponseEntity<>("already nickName",HttpStatus.BAD_REQUEST);//중복닉네임은 400응답코드 
			}
		
			return new ResponseEntity<>("OK",HttpStatus.OK);//검증완료
			
					
	}

	@Override
	public ResponseEntity<?> emailAuth(MemberVO vo) {//이메일 인증번호 전송 
		// TODO Auto-generated method stub
		return mailService.emailSend(vo.getEmail());
	}

	@Override
	public ResponseEntity<?> emailValidation(EmailAuthVO vo) {//이메일 인증번호 검증
		// TODO Auto-generated method stub
		return mailService.emailAuthValidation(vo.getEmail(), vo.getCode());
	}

}
