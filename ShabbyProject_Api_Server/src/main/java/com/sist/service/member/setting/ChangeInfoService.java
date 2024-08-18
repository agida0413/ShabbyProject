package com.sist.service.member.setting;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ChangeInfoService {
	public ResponseEntity<?> getInfo(Authentication authentication);//회원정보 
}
