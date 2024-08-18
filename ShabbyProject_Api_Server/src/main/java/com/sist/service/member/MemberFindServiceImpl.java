package com.sist.service.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.repository.member.MemberAccountRepository;
import com.sist.repository.member.MybatisMemberAccountRepository;
import com.sist.service.member.mail.MailService;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MemberFindServiceImpl implements MemberFindService{
private final MemberAccountRepository memberAccountRepository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;	
private final MailService mailService;

	@Override
	public ResponseEntity<?> findEmail(MemberVO vo) {
		// TODO Auto-generated method stub
			
		MemberVO findVO = memberAccountRepository.findEmail(vo);//해당 하는 정보 찾음 
		if(findVO==null) {
			return new ResponseEntity<>("no data",HttpStatus.NOT_FOUND);//404 에러 가입정보가 없음 
		}
			System.out.println(findVO.getPhone());
		//만약 핸드폰 번호가 일치하지 않다면 
		if(!bCryptPasswordEncoder.matches(vo.getPhone(), findVO.getPhone())) {
			//정보가 일치하지않음 
			return new ResponseEntity<>("incorrect data",HttpStatus.BAD_REQUEST);//400 에러 
		}
		
		
		
		return new ResponseEntity<>(findVO,HttpStatus.OK);//성공시 200코드와 찾은 정보 반환 
		
		
		
	}
	
	
	//임시비밀번호 메일로 발급 및 초기화
	@Override
	public ResponseEntity<?> passwordFind(MemberVO vo) {
		// TODO Auto-generated method stub
		return mailService.emailPasswordReset(vo);
	}
	
	

}
