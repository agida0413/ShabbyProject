package com.sist.common.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;
//임시패스워드 생성기 

public final class RandomGenerator {
			private static final int CODE_LENGTH = 6;
			private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";//소문자모음
		    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//대문자모음
		    private static final String DIGITS = "0123456789";//숫자모음 
		    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:',.<>?";//특수문자모음 
		    
		    //모든 문자조합 
		    private static final String ALL_CHARS = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARS;
	
		    private static final SecureRandom RANDOM = new SecureRandom();//시큐리티의 강력한 난수를 생성하기위해
		    
		    
		    public static String generateRandomPassword() {
	    	
	    	//최소숫자하나 포함 , 소문자하나포함 ,대문자하나포함 , 특수문자 포함 총 4자리 
	    	StringBuilder password= new StringBuilder();
	    	password.append(getRandomCharacter(DIGITS));//숫자
	    	password.append(getRandomCharacter(LOWERCASE));//소문자
	    	password.append(getRandomCharacter(UPPERCASE));//대문자
	    	password.append(getRandomCharacter(SPECIAL_CHARS));//특수문자
	    	
	    	
	    	//나머지 12자리 
	    	for (int i = 4; i < 15; i++) {
	            password.append(getRandomCharacter(ALL_CHARS));
	        }
	    	
	    	 char[] passwordArray = password.toString().toCharArray();//배열 형태로 변환
	    	 
	    	 //피셔-에이츠 알고리즘으로 무작위로 섞기 
	    	 for (int i = passwordArray.length - 1; i > 0; i--) {//배열 뒤부터 처음 인덱스까지 루프 16번 반복 
	             int index = RANDOM.nextInt(i + 1);//0~i 무작위 정수 생성 
	             char temp = passwordArray[index]; // 임시변수에 선택된 인덱소 요소 저장 
	             passwordArray[index] = passwordArray[i]; //현재루프 돌고있는 인덱스 요소 = > 랜덤으로 생성한index 로 이동
	             passwordArray[i] = temp;//임시변수를 다시 i (현재인덱스) 위치로 이동 
	         }	
	    	 
	    	 return new String(passwordArray);//스트링형태 리턴 
	    	 
	    	}
	    
	    
		    private static char getRandomCharacter(String source) {
		        int index = RANDOM.nextInt(source.length()); //시큐리티 난수생성을 통해 위에 정의한 각각모음들의 길이에서 인덱스를 생성함 
		        return source.charAt(index);//생성된 인덱스의 실제 값 반환
		    }
		    
		    
		    
		  //난수를 이용하여 랜덤인증번호 생성
		    public static String generateRandomCode() {
		        SecureRandom random = new SecureRandom();//난수 생성= > security 의 보안을 높이기 위한 secureRandom
		        
		       //10 의 code_length 제곱 생성 ex) codelength=6으로 정의 되어있으니 10의 6제곱 0~10의6제곱 만큼의 랜덤 정수를 뽑는다. (999999까지)
		        int code = random.nextInt((int) Math.pow(10, CODE_LENGTH));
		    
		        //6자리 포맷팅 ex) code_length가 6 이므로 123 == 000123
		        return String.format("%0" + CODE_LENGTH + "d", code);
		    }
		}


