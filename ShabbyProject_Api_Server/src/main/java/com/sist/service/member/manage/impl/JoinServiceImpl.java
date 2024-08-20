package com.sist.service.member.manage.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.common.exception.BadRequestException;
import com.sist.dto.member.EmailAuthDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.repository.member.MemberAccountRepository;
import com.sist.repository.member.impl.MybatisMemberAccountRepository;
import com.sist.service.member.mail.MailService;
import com.sist.service.member.manage.JoinService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService{

	
private final MemberAccountRepository memberAccountRepository;
private final MailService mailService; //이메일 인증관련 
private final BCryptPasswordEncoder bCryptPasswordEncoder;

	//회원가입 
	public ResponseEntity<?> join(MemberDTO dto){
			
		if(dto.getIntroduce().equals("")) {
			dto.setIntroduce(null); // 만약 자기소개 입력안했을시에는 데이터베이스에 널값으로 저장 =- > 관리하기 편할거같음 
		}
		
		String password=dto.getPassword();
		String phone=dto.getPhone();
		
		dto.setPassword(bCryptPasswordEncoder.encode(password)); // 패스워드 암호화
		dto.setPhone(bCryptPasswordEncoder.encode(phone)); //핸드폰번호 암호화
		
		dto.setRole("ROLE_USER"); //일반회원 권한 
		
		
			memberAccountRepository.join(dto);//회원가입 데이터베이스 저장 
		
		
		
		return new ResponseEntity<>("OK",HttpStatus.OK); //성공 
		
	}
	
	//닉네임 중복검증완료
	public ResponseEntity<?> nickNameValidation(String nickName){
			MemberDTO dto = memberAccountRepository.findByUserNickname(nickName);
			
			if(dto!=null) {
				
				throw new BadRequestException("중복된 닉네임 입니다.");//사용자 정의400에러 발생
			}
		
			return new ResponseEntity<>("OK",HttpStatus.OK);//검증완료
			
					
	}

	@Override
	public ResponseEntity<?> emailAuth(MemberDTO dto) {//이메일 인증번호 전송 
		// TODO Auto-generated method stub
		return mailService.emailSend(dto.getEmail());
	}

	@Override
	public ResponseEntity<?> emailValidation(EmailAuthDTO dto) {//이메일 인증번호 검증
		// TODO Auto-generated method stub
		return mailService.emailAuthValidation(dto.getEmail(), dto.getCode());
	}

}
