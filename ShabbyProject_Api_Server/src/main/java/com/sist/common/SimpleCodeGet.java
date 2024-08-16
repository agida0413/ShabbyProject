package com.sist.common;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sist.service.member.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Component
public class SimpleCodeGet {
//공통으로 자주사용하는 함수들
	

	public int getIdNum(Authentication authentication) {
		   CustomUserDetails userDetails= (CustomUserDetails)authentication.getPrincipal();
	    	int idNum =userDetails.getIdNum();
	    		return idNum;
	}
	
	
}
