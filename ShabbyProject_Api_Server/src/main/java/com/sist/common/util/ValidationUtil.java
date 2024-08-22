package com.sist.common.util;

import java.util.regex.Pattern;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.stereotype.Component;

//클라이언트에서 진행한 validation => 서버에서 한번더 validation(ex 비밀번호 특수문자 포함 , 공백 불가 등)
@Component
public class ValidationUtil {


	
	
	
	//입력값이 존재해야 함 
	public boolean cantNull(String value) {
	
		if(value==null) {
			
			return false;
		}
		
		return true;
	}

	//input 타입에서 공백만 보낼수 없음   
	public boolean cantFirstWhiteSapce(String value) {
		
		
	
		if(value.startsWith(" ")) {
			
			return false;
		}
		
		return true;
	}
	//중간에 공백 포함 불가 
	public boolean cantWhiteSpace(String value) {
		
		
		// 공백을 포함하는지 확인하기 위해 정규 표현식 사용
        Pattern whitespacePattern = Pattern.compile("\\s");
        //공백 포함시 false 
      if(whitespacePattern.matcher(value).find()) {
    	  return false;
      }
		
		return true;
	}
	//특정 길이를 넘을 수 없음 
	public boolean cantOverLength(String value,int length) {
		
		
		if(value.length()>=length) {
			return false;
		}
		
		return true;
	}
	//특정 길이를 넘어야함 
	public boolean cantUnderLength(String value,int length) {
		
		if(value.length()<length) {
			return false;
		}
		
		return true;
	}
	
	//특정길이와 같아야함
	public boolean mustSameLength(String value, int length) {

		if (value.length() != length) {
			return false;
		}

		return true;
	}
	
	//한국어 사용금지 
	public boolean cantKorean(String value) {
		
		
		Pattern koreanPattern = Pattern.compile("[ㄱ-ㅎㅏ-ㅣ가-힣]");
		
		if(koreanPattern.matcher(value).find()) {
			return false;
		}
		
		return true;
	}
	//영문자 숫자 ,언더스코어를 제외한 특수문자 사용금지 
	public boolean cantSpecialChar(String value) {
		
		Pattern specialCharPattern=Pattern.compile("[^a-zA-Z0-9_ ]");
		
		if(specialCharPattern.matcher(value).find()) {
			return false;
		}
		
		return true;
	}
	
	//숫자를 포함해야함 
	public boolean mustContainNum(String value) {
		
		Pattern numPattern = Pattern.compile(".*\\d.*");
		if(!numPattern.matcher(value).find()) {
			return false;
		}
		
		return true;
	}
	//숫자로만 이루어져야함
	public boolean mustOnlyNum(String value) {
			
			Pattern numPattern = Pattern.compile("^\\d+$");
			if(!numPattern.matcher(value).find()) {
				return false;
			}
			
			return true;
		}
	
	//문자를 포함해야함 
	public boolean mustContainEng(String value) {
		
		Pattern engPattern=Pattern.compile("[a-zA-Z]");
		if(!engPattern.matcher(value).find()) {
			return false;
		}
		
		return true;
		
	}
	//특수문자를 포함해야함
	public boolean mustContainSpecialChar(String value) {
		
		Pattern specialCharPattern=Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
		
		if(!specialCharPattern.matcher(value).find()) {
			return false;
		}
		return true;
	}
	//이메일 형식이여야 함
	public boolean mustEmailType(String value) {
		Pattern emailPattern=Pattern.compile(".+@.+\\..+");
		
		if(!emailPattern.matcher(value).find()) {
			return false;
		}
		
		return true;
	}
}

