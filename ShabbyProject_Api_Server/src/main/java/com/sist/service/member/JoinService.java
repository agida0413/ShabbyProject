package com.sist.service.member;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.sist.repository.member.memberAccountRepository;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinService {

	
private final memberAccountRepository repository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;

//public ResponseEntity<?> join(MemberVO vo) {
//	String email=vo.getEmail();
//	String password=vo.getPassword();
//	
//	MemberVO findVo=new MemberVO();
//	findVo= repository.findByUserEmail(email);
//	
//	if(ett==null) {
//		UserEntity entity= new UserEntity();
//		entity.setUsername(username);
//		entity.setPassword(bCryptPasswordEncoder.encode(password));
//		entity.setRole("ROLE_ADMIN");
//	
//		mybatisUserRepository.save(entity);
//		result="OK";
//	}else {
//		result="NO";
//	}
//	
//	return result;
//}
}
