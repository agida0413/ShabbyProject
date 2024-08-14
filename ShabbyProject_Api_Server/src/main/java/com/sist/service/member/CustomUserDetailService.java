package com.sist.service.member;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.sist.repository.member.memberAccountRepository;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{
private final memberAccountRepository repository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
//		String password="1234";
//		System.out.println(bCryptPasswordEncoder.encode(password));
		MemberVO vo=repository.findByUserEmail(email);
		
		if(vo!=null) {

			
			return new CustomUserDetails(vo);
		}

		return null;
	}

}
