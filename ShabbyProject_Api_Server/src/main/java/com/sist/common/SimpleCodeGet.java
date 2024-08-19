package com.sist.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sist.service.member.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Component
public class SimpleCodeGet {
//공통으로 자주사용하는 함수들
	

	
	
	//회원고유번호를 authentication에서 꺼내옴 
	public int getIdNum() {
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		   CustomUserDetails userDetails= (CustomUserDetails)authentication.getPrincipal();
	    	int idNum =userDetails.getIdNum();

	    		return idNum;
	}
	
	// 로그인 필터에서는 성공시 리프레시 토큰 저장받을 때 idnum이 필요한데 , 그 시점에서는 로그인 필터 내부에 있는 
	// authenrication 객체를 사용행함 . 
	//따라서 오버로딩 = >회원고유번호를 authentication에서 꺼내옴 
	public int getIdNum(Authentication authentication) {
		 
		   CustomUserDetails userDetails= (CustomUserDetails)authentication.getPrincipal();
	    	int idNum =userDetails.getIdNum();
	    		return idNum;
	}
	
	//이메일 정보를 꺼내옴 
	public String getEmail() {
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		   CustomUserDetails userDetails= (CustomUserDetails)authentication.getPrincipal();
	    	String email = userDetails.getUsername();
	    		return email;
	}
	
	
}
