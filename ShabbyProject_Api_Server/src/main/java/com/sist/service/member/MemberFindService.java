package com.sist.service.member;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;


public interface MemberFindService {
	//이메일(아이디 ) 찾기 서비스
	public ResponseEntity<ResponseDTO<MemberDTO>> findEmail(MemberDTO dto);
	//비밀번호 찾기 서비스
	public ResponseEntity<ResponseDTO<Void>> passwordFind(MemberDTO dto);
}
