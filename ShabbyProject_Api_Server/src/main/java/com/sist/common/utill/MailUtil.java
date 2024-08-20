package com.sist.common.utill;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class MailUtil {
	private static final int CODE_LENGTH = 6;
	
	//인증코드를 받아 html을 생성함 
		public String getCertificationMessage(String certificationNumber) {
			
			String certificationMessage="";
			certificationMessage+="<h1 style='text-align:center;'>[ Shabby 회원가입] 인증메일 </h1>";
			certificationMessage+="<h3 style='text-align:center;'>인증코드:<strong style='font-size:32px; letter-spacing:8px;'> "+certificationNumber
					+certificationMessage+"</strong></h3>";
			return certificationMessage;
		}
		public String getPasswordResetMessage(String newPassword) {
			
			String certificationMessage="";
			certificationMessage+="<h1 style='text-align:center;'>[ Shabby 임시비밀번호 발급 ] 메일 </h1>";
			certificationMessage+="<h3 style='text-align:center;'>새비밀번호:<strong style='font-size:32px; letter-spacing:8px;'> "+newPassword
					+certificationMessage+"</strong></h3>";
			return certificationMessage;
		}
		
		//난수를 이용하여 랜덤인증번호 생성
	    public String generateRandomCode() {
	        SecureRandom random = new SecureRandom();
	        int code = random.nextInt((int) Math.pow(10, CODE_LENGTH));
	      
	        return String.format("%0" + CODE_LENGTH + "d", code);
	    }
}
