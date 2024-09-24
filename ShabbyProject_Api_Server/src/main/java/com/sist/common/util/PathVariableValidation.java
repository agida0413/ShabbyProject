package com.sist.common.util;

import java.util.List;
import java.util.regex.Pattern;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.stereotype.Component;

import com.sist.dto.hobby.HobbyDTO;

//클라이언트에서 진행한 validation => @validation 어노테이션이 불가한 pathvariable 검증

public final class PathVariableValidation {


	// `_`를 제외한 모든 특수 문자를 검증하는 메소드
    public static boolean keyWordValService(String keyword) {
        // 허용할 문자: 알파벳, 숫자, `_`,한국어 만
    	  Pattern pattern = Pattern.compile("[^a-zA-Z0-9_ㄱ-ㅎㅏ-ㅣ가-힣]");

     
            // 특수 문자가 포함된 경우 false 반환
            if (pattern.matcher(keyword).find()) {
                return false;
            }
        

        return true; // 모든 태그가 유효한 경우
    }
		
		
		// `_`를 제외한 모든 특수 문자를 검증하는 메소드
	    public static boolean keyWordValService(List<String> keywordList) {
	        // 허용할 문자: 알파벳, 숫자, `_`,한국어 만
	    	  Pattern pattern = Pattern.compile("[^a-zA-Z0-9_ㄱ-ㅎㅏ-ㅣ가-힣]");

	        for (String keyword : keywordList) {
	            // 특수 문자가 포함된 경우 false 반환
	            if (pattern.matcher(keyword).find()) {
	                return false;
	            }
	        }

	        return true; // 모든 태그가 유효한 경우
	    }

		public static boolean nickNameValService(String nickname) {
			
			//닉네임 null 불가
	    	if(!cantNull(nickname)) {
				return false;
			}
			//닉네임 공백으로 시작 불가 
			if(!cantFirstWhiteSapce(nickname)) {
				return false;
			} 
			//닉네임 공백 포함 불가
			if(!cantWhiteSpace(nickname)) {
				return false;
			}
			//닉네임 한글포함 불가
			if(!cantKorean(nickname)) {
				return false;
			}
			//닉네임 _를 제외한 특수문자 포함 불가
			if(!cantSpecialChar(nickname)) {
				return false;
			}
			//닉네임 15자 이상 불가
			if(!cantOverLength(nickname, 15)) {
				return false;
			}

		 return true;
		}
		
		public static boolean pageValidation(int page) {
			
		if(!cantUnderLength(page, 1)) {
			return false;
		}
		
		
		 return true;
		}

	
		public static boolean emailValService(String email) {
			//이메일 입력 null 불가
			if (!cantNull(email)) {
				return false;
			}

			// 이메일 형식이 아닐경우 불가
			if (!mustEmailType(email)) {

				return false;
			}
			// 이메일 첫 문자 공백 불가
			if (!cantFirstWhiteSapce(email)) {
				return false;
			}

			// 이메일 공백포함 불가
			if (!cantWhiteSpace(email)) {
				return false;
			}

			return true;
		}
		
		public static boolean authCodeValidation(String code) {
			
			if(!mustSameLength(code, 6)) {
				return false;
			}
			return true;
		}
		
		
		public static boolean nameValSevice(String name) {
			//이름 널 불가
			if(!cantNull(name)) {
				return false;
			}
			//이름 공백포함 불가
			if(!cantWhiteSpace(name)){
				return false;
			}
			//이름 공백 시작 불가
			if(!cantFirstWhiteSapce(name)) {
				return false;
			}


			return true;
		}
		
		public static boolean phoneValService(String phone) {
			//반드시11자리
			if(!mustSameLength(phone,11)) {
				return false;
			}
			//한국어를 포함 할수 없음
			if(!cantKorean(phone)) {
				return false;
			}
			//첫글자 공백일수 없음
			if(!cantFirstWhiteSapce(phone)) {
				return false;
			}
			//공백을 포함할수없음
			if(!cantWhiteSpace(phone)) {
				return false;
			}
			//숫자로만 이루어져야함
			if(!mustOnlyNum(phone)) {
				return false;
			}

			return true;
		}


	//입력값이 존재해야 함 
	public static boolean cantNull(String value) {

		if(value==null) {

			return false;
		}

		return true;
	}

	//input 타입에서 공백만 보낼수 없음   
	public static boolean cantFirstWhiteSapce(String value) {

		if(value.startsWith(" ")) {

			return false;
		}

		return true;
	}
	//중간에 공백 포함 불가 
	public static boolean cantWhiteSpace(String value) {


		// 공백을 포함하는지 확인하기 위해 정규 표현식 사용
        Pattern whitespacePattern = Pattern.compile("\\s");
        //공백 포함시 false 
      if(whitespacePattern.matcher(value).find()) {
    	  return false;
      }

		return true;
	}
	//특정 길이를 넘을 수 없음 
	public static boolean cantOverLength(String value,int length) {


		if(value.length()>=length) {
			return false;
		}

		return true;
	}
	//특정 길이를 넘어야함 
	public static boolean cantUnderLength(String value,int length) {

		if(value.length()<length) {
			return false;
		}

		return true;
	}
	//특정숫자 이상 
	public static boolean cantUnderLength(int value,int length) {

		if(value<length) {
			return false;
		}

		return true;
	}

	//특정길이와 같아야함
	public static boolean mustSameLength(String value, int length) {

		if (value.length() != length) {
			return false;
		}

		return true;
	}

	//한국어 사용금지 
	public static boolean cantKorean(String value) {


		Pattern koreanPattern = Pattern.compile("[ㄱ-ㅎㅏ-ㅣ가-힣]");

		if(koreanPattern.matcher(value).find()) {
			return false;
		}

		return true;
	}
	//영문자 숫자 ,언더스코어를 제외한 특수문자 사용금지 
	public static boolean cantSpecialChar(String value) {

		Pattern specialCharPattern=Pattern.compile("[^a-zA-Z0-9_ ]");

		if(specialCharPattern.matcher(value).find()) {
			return false;
		}

		return true;
	}

	//숫자를 포함해야함 
	public static boolean mustContainNum(String value) {

		Pattern numPattern = Pattern.compile(".*\\d.*");
		if(!numPattern.matcher(value).find()) {
			return false;
		}

		return true;
	}
	//숫자로만 이루어져야함
	public static boolean mustOnlyNum(String value) {

			Pattern numPattern = Pattern.compile("^\\d+$");
			if(!numPattern.matcher(value).find()) {
				return false;
			}

			return true;
		}

	//문자를 포함해야함 
	public static boolean mustContainEng(String value) {

		Pattern engPattern=Pattern.compile("[a-zA-Z]");
		if(!engPattern.matcher(value).find()) {
			return false;
		}

		return true;

	}
	//특수문자를 포함해야함
	public static boolean mustContainSpecialChar(String value) {

		Pattern specialCharPattern=Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

		if(!specialCharPattern.matcher(value).find()) {
			return false;
		}
		return true;
	}
	//이메일 형식이여야 함
	public static boolean mustEmailType(String value) {
		Pattern emailPattern=Pattern.compile(".+@.+\\..+");

		if(!emailPattern.matcher(value).find()) {
			return false;
		}

		return true;
	}
}
