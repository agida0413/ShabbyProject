package com.sist.service.member.mail;

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

import com.sist.common.SimpleCodeGet;
import com.sist.repository.member.memberAccountRepository;
import com.sist.vo.EmailAuthVO;
import com.sist.vo.MemberVO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private static final int CODE_LENGTH = 6;
	private final JavaMailSender javaMailSender;
	private final memberAccountRepository memberAccountRepository;
	private final BCryptPasswordEncoder byBCryptPasswordEncoder;
	
	//이메일 중복검증 및 발송
	@Transactional
	public ResponseEntity<?> emailSend(String email ){
		
		MemberVO vo= memberAccountRepository.findByUserEmail(email);// 이메일 중복검증 
		if(vo!=null) {
			//중복이메일 일때 
			 return new ResponseEntity<>("already user", HttpStatus.BAD_REQUEST); //400응답코드 
		}
		
		try {
			
			MimeMessage message= javaMailSender.createMimeMessage(); //메일링 객체 생성 
			MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(message,true);
			
			String certificationNumber=generateRandomCode(); //랜덤 인증번호 생성 
			
			   EmailAuthVO authVo = new EmailAuthVO(); //이메일 정보 엔티티 
		        authVo.setEmail(email);// 매개변수로 받은 이메일 세팅
		    
		        authVo.setCode(certificationNumber);// 생성된 인증번호 
		        authVo.setExpiration(LocalDateTime.now().plusMinutes(3)); // 인증시간 3분 
		        
		        memberAccountRepository.emailAuthSave(authVo); //데이터베이스에 인증코드와 정보 저장 
		        
			String htmlContent= getCertificationMessage(certificationNumber); // 이메일로 보낼 html 
			
			mimeMessageHelper.setTo(email);//보낼 상대 
			mimeMessageHelper.setSubject("[Shabby] 회원가입 인증코드입니다."); //제목 
			mimeMessageHelper.setText(htmlContent,true);
			javaMailSender.send(message);//전송 
			
		} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//기타 에러 
			 return new ResponseEntity<>("something err", HttpStatus.NOT_FOUND); //404응답코드 
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	//인증코드가 맞는지 검증 
	@Transactional
	public ResponseEntity<?> emailAuthValidation(String email, String code){
		
	EmailAuthVO vo = new EmailAuthVO();
	vo.setEmail(email);

	vo.setCode(code);
	
	EmailAuthVO valiVO=memberAccountRepository.emailAuthGetValidation(vo);// 전달받은 이메일과, 인증코드를 바탕으로 데이터베이스의 최신row를 찾음 
	
	if(valiVO==null) {
		//정보가없을 시 
		return new ResponseEntity<>("not auth",HttpStatus.BAD_REQUEST);//400 응답코드 
	}
	
	 LocalDateTime now = LocalDateTime.now();//현재시간 
	 
	 if(now.isAfter(valiVO.getExpiration())) {
		 //인증시간이 만료되었을 경우 
		 return new ResponseEntity<>("expiration done",HttpStatus.UNPROCESSABLE_ENTITY);//422 응답코드
	 }
	 
	 memberAccountRepository.emailAuthClear(valiVO.getEmailAuthNum());// 데이터베이스 row에 인증이 완료되었음을 update함 
		
	 
	 return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	
	//인증코드를 받아 html을 생성함 
	private String getCertificationMessage(String certificationNumber) {
		
		String certificationMessage="";
		certificationMessage+="<h1 style='text-align:center;'>[ Shabby 회원가입] 인증메일 </h1>";
		certificationMessage+="<h3 style='text-align:center;'>인증코드:<strong style='font-size:32px; letter-spacing:8px;'> "+certificationNumber
				+certificationMessage+"</strong></h3>";
		return certificationMessage;
	}
	
	
	//난수를 이용하여 랜덤인증번호 생성
    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt((int) Math.pow(10, CODE_LENGTH));
      
        return String.format("%0" + CODE_LENGTH + "d", code);
    }
}
