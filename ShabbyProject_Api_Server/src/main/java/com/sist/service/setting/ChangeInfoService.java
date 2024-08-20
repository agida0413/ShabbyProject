package com.sist.service.setting;


import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;

public interface ChangeInfoService {
	public ResponseEntity<ResponseDTO<String>> getLockedInfo();//회원정보 
	public ResponseEntity<ResponseDTO<Void>> nickNameUpdate(MemberDTO dto);//닉네임변경
	public ResponseEntity<ResponseDTO<Void>> passwordUpdate(String password,String newPassword);//패스워드 변경
	public ResponseEntity<ResponseDTO<Void>> phoneChange(MemberDTO dto);//핸드폰 번호 변경 
}
