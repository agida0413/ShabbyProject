package com.sist.service.member;


import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.setting.ChangeNickNameDTO;
import com.sist.dto.setting.ChangePasswordDTO;
import com.sist.dto.setting.ChangePhoneDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ChangeInfoService {
	public ResponseEntity<ResponseDTO<MemberDTO>> getLockedInfo();//회원정보 
	public ResponseEntity<ResponseDTO<Void>> nickNameUpdate(ChangeNickNameDTO dto,HttpServletResponse response,HttpServletRequest request);//닉네임변경
	public ResponseEntity<ResponseDTO<Void>> passwordUpdate(ChangePasswordDTO dto);//패스워드 변경
	public ResponseEntity<ResponseDTO<Void>> phoneChange(ChangePhoneDTO dto);//핸드폰 번호 변경 
	public ResponseEntity<ResponseDTO<MemberDTO>> updateLockedState(MemberDTO dto);//공개/비공개 모드변경
	public ResponseEntity<ResponseDTO<Void>> deleteMember(MemberDTO dto);//회원삭제
	
}
