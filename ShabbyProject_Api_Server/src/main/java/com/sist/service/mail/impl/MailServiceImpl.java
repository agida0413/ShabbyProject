package com.sist.service.mail.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.common.exception.NotFoundException;
import com.sist.common.util.MailUtil;
import com.sist.common.util.PasswordGenerator;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.EmailAuthDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.repository.member.MemberAccountRepository;
import com.sist.repository.member.impl.MybatisMemberAccountRepository;
import com.sist.service.mail.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
	private final MailUtil mailUtil;
	private final JavaMailSender javaMailSender;
	private final MemberAccountRepository memberAccountRepository;//멤버관련 레파지토리
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final PasswordGenerator passwordGenerator;// 임시패스워드 생성 
	
	//이메일 중복검증 및 발송
	@Transactional
	public ResponseEntity<ResponseDTO<Void>> emailSend(String email ){
		
		//매개변수로 받은 이메일기반 회원찾기
		MemberDTO dto= memberAccountRepository.findByUserEmail(email); 
		
		//이미 해당 이메일을 사용하고 있는 회원이 있으면
		if(dto!=null) {
			
			throw new BadRequestException("이미 사용중인 이메일 입니다.");//사용자 정의 400에러 발생
		}
		
		try {
			
				MimeMessage message= javaMailSender.createMimeMessage(); //메일링 객체 생성 
				MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(message,true);
				
				String certificationNumber=mailUtil.generateRandomCode(); //랜덤 인증번호 생성 
			
			    EmailAuthDTO emailDto = new EmailAuthDTO();//이메일 정보 엔티티 
		        emailDto.setEmail(email);// 매개변수로 받은 이메일 세팅
		    
		        emailDto.setCode(bCryptPasswordEncoder.encode(certificationNumber));// 생성된 인증번호 
		        emailDto.setExpiration(LocalDateTime.now().plusMinutes(3)); // 인증시간 3분 
		        
		   
		        
				String htmlContent=mailUtil.getCertificationMessage(certificationNumber); // 이메일로 보낼 html 
				
				mimeMessageHelper.setTo(email);//보낼 상대 
				mimeMessageHelper.setSubject("[Shabby] 회원가입 인증코드입니다."); //제목 
				mimeMessageHelper.setText(htmlContent,true);
				javaMailSender.send(message);//전송 
			     memberAccountRepository.emailAuthSave(emailDto); //데이터베이스에 인증코드와 정보 저장 
			     
		} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//기타 에러 
				throw new BadRequestException("에러가 발생했습니다. 다시 시도해주세요.");//사용자 정의 400에러 발생
		}
		
		return new ResponseEntity<ResponseDTO<Void>>
		(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}
	
	
	//인증코드가 맞는지 검증 
	@Transactional
	public ResponseEntity<ResponseDTO<Void>> emailAuthValidation(String email, String code){
		

	// 전달받은 이메일을 바탕으로 데이터베이스의 최신row를 찾음 
	EmailAuthDTO dto=memberAccountRepository.emailAuthGetValidation(email);
	
	//인증코드가 틀린경우
	if(!bCryptPasswordEncoder.matches(code, dto.getCode())) {
		
		throw new BadRequestException("인증코드가 틀렸습니다.");
	}
	
	 //현재시간 
	 LocalDateTime now = LocalDateTime.now();
	 
	 //인증시간이 만료되었을 경우 
	 if(now.isAfter(dto.getExpiration())) {
		
		 throw new BadRequestException("인증시간이 만료되었습니다.");//사용자 정의 400에러 발생
	 }
	 
	 memberAccountRepository.emailAuthClear(dto.getEmailAuthNum());// 데이터베이스 row에 인증이 완료되었음을 update함 
		
	 
	 return new ResponseEntity<ResponseDTO<Void>>
		(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	 
	}
	
	
    
   

    //임시 패스워드 발급 및 패스워드 초기화
	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<Void>> emailPasswordReset(MemberDTO dto) {
		
		// TODO Auto-generated method stub
		//매개변수로 받은 이메일 
		String email=dto.getEmail();
		//매개변수로 받은 이메일 기반 맴버를 찾음 
		
		MemberDTO findDto = memberAccountRepository.findByUserEmail(email);
		
		//해당이메일기반 회원정보가 존재하지않을시 
		if(findDto==null) {
			throw new NotFoundException("등록된 정보가 없습니다.");
		}
		//해당 이메일을 사용하는 회원은 존재하나 입력한 이름이 다를경우 
		if(!dto.getName().equals(findDto.getName())) {
			
			throw new BadRequestException("입력한 정보가 일치하지 않습니다.");//사용자 정의 400에러 발생
		}
		//해당 이메일을 사용하는 회원은 존재하나 휴대폰 번호가 다를경우
		if(!bCryptPasswordEncoder.matches(dto.getPhone(), findDto.getPhone())) {
			throw new BadRequestException("입력한 정보가 일치하지 않습니다.");//사용자 정의 400에러 발생
		}
		
				
		try {
			
			MimeMessage message= javaMailSender.createMimeMessage(); //메일링 객체 생성 
			MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(message,true);
			
			String password =passwordGenerator.generateRandomPassword();//임시패스워드 생성
		
	  
			String htmlContent=mailUtil.getPasswordResetMessage(password); // 이메일로 보낼 html 
			
			mimeMessageHelper.setTo(email);//보낼 상대 
			mimeMessageHelper.setSubject("[Shabby] 임시패스워드 발급메일입니다."); //제목 
			mimeMessageHelper.setText(htmlContent,true);
			javaMailSender.send(message);//전송 
			
			String securedPassword= bCryptPasswordEncoder.encode(password);//데이터베이스에 암호화 해서 저장할 패스워드 
			dto.setPassword(securedPassword);//vo에 새로운 임시비밀번호 저장 
			memberAccountRepository.tempPasswordUpdate(dto);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new InternerException("서버 내부오류입니다. 잠시 뒤 이용해 주세요. ");//사용자 정의 500에러 발생
			// 익셉션 발생 = > 글로벌 핸들러에서잡음
		}		
		
		
		
		return new ResponseEntity<ResponseDTO<Void>>
		(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}
}
