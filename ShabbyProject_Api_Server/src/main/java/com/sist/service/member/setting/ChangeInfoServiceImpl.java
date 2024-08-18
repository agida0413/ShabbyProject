package com.sist.service.member.setting;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.sist.common.SimpleCodeGet;
import com.sist.repository.member.MemberAccountRepository;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ChangeInfoServiceImpl implements ChangeInfoService{

	private final MemberAccountRepository memberAccountRepository;
	private final SimpleCodeGet simpleCodeGet;
	
	@Override
	public ResponseEntity<?> getInfo(Authentication authentication) {
		// TODO Auto-generated method stub
	
		String email=simpleCodeGet.getEmail(authentication);
		
		MemberVO vo = memberAccountRepository.findByUserEmail(email);
		
		if(vo==null) {
			return new ResponseEntity<>("no-access",HttpStatus.UNAUTHORIZED);//401
		}
		
		
		return new ResponseEntity<>(vo,HttpStatus.OK);//401;
	}
	
	

}
