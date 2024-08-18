package com.sist.service.member.setting;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.sist.vo.MemberVO;

public interface ChangeInfoService {
	public ResponseEntity<?> getInfo();//회원정보 
	public ResponseEntity<?> nickNameUpdate(MemberVO vo);
}
