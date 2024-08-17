package com.sist.service.member;

import org.springframework.http.ResponseEntity;

import com.sist.vo.MemberVO;

public interface MemberFindService {

	public ResponseEntity<?> findEmail(MemberVO vo);
	public ResponseEntity<?> passwordFind(MemberVO vo);
}
