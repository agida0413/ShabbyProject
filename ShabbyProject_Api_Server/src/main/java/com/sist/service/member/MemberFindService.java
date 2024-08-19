package com.sist.service.member;

import org.springframework.http.ResponseEntity;

import com.sist.dto.MemberDTO;
import com.sist.vo.MemberVO;

public interface MemberFindService {

	public ResponseEntity<?> findEmail(MemberDTO dto);
	public ResponseEntity<?> passwordFind(MemberDTO dto);
}
