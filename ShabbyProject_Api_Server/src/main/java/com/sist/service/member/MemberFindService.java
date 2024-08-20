package com.sist.service.member;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;


public interface MemberFindService {

	public ResponseEntity<ResponseDTO<MemberDTO>> findEmail(MemberDTO dto);
	public ResponseEntity<ResponseDTO<Void>> passwordFind(MemberDTO dto);
}
