package com.sist.common;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sist.service.member.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Component
public class SimpleCodeGet {
//공통으로 자주사용하는 함수들
	
	
	//회원고유번호를 authentication에서 꺼내옴 
	public int getIdNum(Authentication authentication) {
		   CustomUserDetails userDetails= (CustomUserDetails)authentication.getPrincipal();
	    	int idNum =userDetails.getIdNum();
	    		return idNum;
	}
	
	
	public String getEmail(Authentication authentication) {
		   CustomUserDetails userDetails= (CustomUserDetails)authentication.getPrincipal();
	    	String email = userDetails.getUsername();
	    		return email;
	}
	
	
}
