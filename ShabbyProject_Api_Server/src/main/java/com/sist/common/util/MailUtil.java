package com.sist.common.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class MailUtil {
	private static final int CODE_LENGTH = 6;
	
	//인증코드 메일 html생성기
		public String getCertificationMessage(String certificationNumber) {//인증코드를 매개변수로 받는다 .
			
			String certificationMessage="";//보낼 메시지 
			//메일로보낼 html 결합 
			certificationMessage+="<h1 style='text-align:center;'>[ Shabby 회원가입] 인증메일 </h1>";
			certificationMessage+="<h3 style='text-align:center;'>인증코드:<strong style='font-size:32px; letter-spacing:8px;'> "+certificationNumber
					+certificationMessage+"</strong></h3>";
			return certificationMessage;
		}
		//임시 비밀번호 발송메일 html 생성기 
		public String getPasswordResetMessage(String newPassword) {//임시비밀번호를 받아온다 . 
			
			//메일로보낼 html 결합 
			String certificationMessage="";
			certificationMessage+="<h1 style='text-align:center;'>[ Shabby 임시비밀번호 발급 ] 메일 </h1>";
			certificationMessage+="<h3 style='text-align:center;'>새비밀번호:<strong style='font-size:32px; letter-spacing:8px;'> "+newPassword
					+certificationMessage+"</strong></h3>";
			
			return certificationMessage;
		}
		
		//난수를 이용하여 랜덤인증번호 생성
	    public String generateRandomCode() {
	        SecureRandom random = new SecureRandom();//난수 생성= > security 의 보안을 높이기 위한 secureRandom
	        
	       //10 의 code_length 제곱 생성 ex) codelength=6으로 정의 되어있으니 10의 6제곱 0~10의6제곱 만큼의 랜덤 정수를 뽑는다. (999999까지)
	        int code = random.nextInt((int) Math.pow(10, CODE_LENGTH));
	    
	        //6자리 포맷팅 ex) code_length가 6 이므로 123 == 000123
	        return String.format("%0" + CODE_LENGTH + "d", code);
	    }
}
