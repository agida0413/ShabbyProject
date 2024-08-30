package com.sist.service.security.impl;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.common.exception.BadRequestException;
import com.sist.common.util.PathVariableValidation;
import com.sist.dto.member.MemberDTO;
import com.sist.repository.member.MemberAccountRepository;
import com.sist.repository.member.impl.MybatisMemberAccountRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{
private final MemberAccountRepository repository;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if(!PathVariableValidation.emailValService(email)) {
			throw new BadRequestException("유효하지 않은 입력입니다.");
		}
		//시큐리티 진행을 위해 해당이메일을 가진 회원정보를 가져옴 
		MemberDTO dto=repository.findByUserEmail(email);
		//이메일 기반 회원이 있다면 
		if(dto!=null) {

			
			return new CustomUserDetails(dto);
		}
		//없을경우 null 리턴
		return null;
	}

}
