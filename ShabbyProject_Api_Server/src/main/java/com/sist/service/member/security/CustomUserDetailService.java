package com.sist.service.member.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.dto.MemberDTO;
import com.sist.repository.member.MemberAccountRepository;
import com.sist.repository.member.MybatisMemberAccountRepository;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{
private final MemberAccountRepository repository;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		MemberDTO dto=repository.findByUserEmail(email);//이메일을 통한 회원 검증
		
		if(dto!=null) {

			
			return new CustomUserDetails(dto);
		}

		return null;
	}

}
