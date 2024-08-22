package com.sist.service.member.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.common.exception.BadRequestException;
import com.sist.common.util.ValidationService;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.EmailAuthDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.repository.member.MemberAccountRepository;
import com.sist.repository.member.impl.MybatisMemberAccountRepository;
import com.sist.service.mail.MailService;
import com.sist.service.member.JoinService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService{

	
private final MemberAccountRepository memberAccountRepository;
private final MailService mailService; //이메일 인증관련 
private final BCryptPasswordEncoder bCryptPasswordEncoder;
private final ValidationService validationService;

	//회원가입 
	public ResponseEntity<ResponseDTO<Void>> join(MemberDTO dto){
		//매개변수로 받은 패스워드
		String password=dto.getPassword();
		//매개변수로 받은 핸드폰번호 
		String phone=dto.getPhone();
		
		//데이터베이스에서 인증관련 엔티티를 가져온다(이메일기반 가장 최신)
		EmailAuthDTO emailDto=memberAccountRepository.emailAuthGetValidation(dto.getEmail());
		
		//이메일 인증 validation ==> 이메일 인증코드 엔티티에서 인증완료여부를 갖고와 검증한다.
		if(emailDto.getIsAuth().contains("N")) {
			throw new BadRequestException("비정상적인 접근입니다.");
		}
		//회원가입 관련 데이터삽입 특정 조건 validation(Ex 비밀번호 특수문자 포함 등)
		if(!validationService.joinValService(dto)) {
			throw new BadRequestException("비정상적인 접근입니다.");
		}
		
		dto.setPassword(bCryptPasswordEncoder.encode(password)); // 패스워드 암호화
		dto.setRole("ROLE_USER"); //일반회원 권한 
		
		MemberDTO findDto= memberAccountRepository.findByUserPhone(phone);
		
		
		if(findDto!=null) {
			throw new BadRequestException("중복된 휴대폰 번호입니다.");
		}
		
		if(dto.getIntroduce().equals("")) {
			dto.setIntroduce(null); // 만약 자기소개 입력안했을시에는 데이터베이스에 널값으로 저장 =- > 관리용이
		}
		
	
	
		
		
		
		memberAccountRepository.join(dto);//회원가입 데이터베이스 저장 
		
		
		
		return new ResponseEntity<ResponseDTO<Void>>
						(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
		
		
	}
	
	//닉네임 중복검증
	public ResponseEntity<ResponseDTO<Void>> nickNameValidation(String nickName){
			//매개변수로 받은 닉네임 기반 회원찾기 
			MemberDTO dto = memberAccountRepository.findByUserNickname(nickName);
			
			//이미 그 닉네임을 사용하는 회원이 있는경우
			if(dto!=null) {
				
				throw new BadRequestException("중복된 닉네임 입니다.");//사용자 정의400에러 발생
			}
		
			return new ResponseEntity<ResponseDTO<Void>>
			(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
			
					
	}

	@Override
	public ResponseEntity<ResponseDTO<Void>> emailAuth(MemberDTO dto) {//이메일 인증번호 전송 
		// TODO Auto-generated method stub
		return mailService.emailSend(dto.getEmail());
	}

	@Override
	public ResponseEntity<ResponseDTO<Void>> emailValidation(EmailAuthDTO dto) {//이메일 인증번호 검증
		// TODO Auto-generated method stub
		return mailService.emailAuthValidation(dto.getEmail(), dto.getCode());
	}

}
