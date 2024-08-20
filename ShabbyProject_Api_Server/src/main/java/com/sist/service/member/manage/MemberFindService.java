package com.sist.service.member.manage;

import org.springframework.http.ResponseEntity;

import com.sist.dto.member.MemberDTO;


public interface MemberFindService {

	public ResponseEntity<?> findEmail(MemberDTO dto);
	public ResponseEntity<?> passwordFind(MemberDTO dto);
}
