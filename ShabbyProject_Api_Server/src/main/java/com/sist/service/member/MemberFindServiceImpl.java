package com.sist.service.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.NotFoundException;
import com.sist.dto.MemberDTO;
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
	public ResponseEntity<?> findEmail(MemberDTO dto ) {
		// TODO Auto-generated method stub
			
		MemberDTO findDto= memberAccountRepository.findEmail(dto);//해당 하는 정보 찾음 
		if(findDto==null) {
			 throw new NotFoundException("등록된 회원정보가 없습니다.");
		}
			System.out.println(findDto.getPhone());
		//만약 핸드폰 번호가 일치하지 않다면 
		if(!bCryptPasswordEncoder.matches(dto.getPhone(), findDto.getPhone())) {
			//정보가 일치하지않음 
			throw new BadRequestException("입력한 정보가 일치하지 않습니다.");//사용자 정의400에러 발생
		}
		
		
		
		return new ResponseEntity<>(findDto,HttpStatus.OK);//성공시 200코드와 찾은 정보 반환 
		
		
		
	}
	
	
	//임시비밀번호 메일로 발급 및 초기화
	@Override
	public ResponseEntity<?> passwordFind(MemberDTO dto) {
		// TODO Auto-generated method stub
		return mailService.emailPasswordReset(dto);
	}
	
	

}
