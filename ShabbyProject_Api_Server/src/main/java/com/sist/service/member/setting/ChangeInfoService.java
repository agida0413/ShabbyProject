package com.sist.service.member.setting;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.sist.dto.member.MemberDTO;

public interface ChangeInfoService {
	public ResponseEntity<?> getLockedInfo();//회원정보 
	public ResponseEntity<?> nickNameUpdate(MemberDTO dto);//닉네임변경
	public ResponseEntity<?> passwordUpdate(String password,String newPassword);//패스워드 변경
	public ResponseEntity<?> phoneChange(MemberDTO dto);//핸드폰 번호 변경 
}
