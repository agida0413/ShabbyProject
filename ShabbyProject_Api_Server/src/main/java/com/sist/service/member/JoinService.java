package com.sist.service.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.EmailAuthDTO;
import com.sist.dto.member.MemberDTO;


public interface JoinService {
	public ResponseEntity<ResponseDTO<Void>> join(MemberDTO dto);//회원가입 
	public ResponseEntity<ResponseDTO<Void>> nickNameValidation(String nickName);//검증
	public ResponseEntity<ResponseDTO<Void>> emailAuth(MemberDTO dto);//인증번호 전송
	public ResponseEntity<ResponseDTO<Void>> emailValidation(EmailAuthDTO dto);//인증코드 검증
	
}
