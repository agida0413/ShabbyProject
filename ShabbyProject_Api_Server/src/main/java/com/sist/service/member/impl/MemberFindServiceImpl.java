package com.sist.service.member.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.NotFoundException;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.repository.member.MemberAccountRepository;
import com.sist.repository.member.impl.MybatisMemberAccountRepository;
import com.sist.service.mail.MailService;
import com.sist.service.member.MemberFindService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MemberFindServiceImpl implements MemberFindService{
private final MemberAccountRepository memberAccountRepository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;	
private final MailService mailService;
	
	//이메일(아이디) 찾기 서비스
	@Override
	public  ResponseEntity<ResponseDTO<MemberDTO>>  findEmail(MemberDTO dto ) {
		// TODO Auto-generated method stub
			
		MemberDTO findDto= memberAccountRepository.findEmail(dto);//해당 하는 정보 찾음 
		
		//findDto가 없을시(찾은 정보가 없을시)
		if(findDto==null) {
			 throw new NotFoundException("등록된 회원정보가 없습니다.");
		}
			
		//만약 핸드폰 번호가 일치하지 않다면 
		if(!bCryptPasswordEncoder.matches(dto.getPhone(), findDto.getPhone())) {
			//정보가 일치하지않음 
			throw new BadRequestException("입력한 정보가 일치하지 않습니다.");//사용자 정의400에러 발생
		}
		
		MemberDTO sendDto= new MemberDTO();//보안을 위해 두가지 정보만 전송 
		
		sendDto.setEmail(findDto.getEmail());//찾은 이메일 세팅
		sendDto.setVueCreateDate(findDto.getFormatVueCreateDate());//찾은 정보의 스트링 형식의 날짜형 데이터를 세팅 
		
		return new ResponseEntity<ResponseDTO<MemberDTO>>
		(new ResponseDTO<MemberDTO>(sendDto),HttpStatus.OK); //성공 //성공시 200코드와 찾은 정보 반환 
		
		
		
	}
	
	
	//임시비밀번호 메일로 발급 및 초기화
	@Override
	public  ResponseEntity<ResponseDTO<Void>>  passwordFind(MemberDTO dto) {
		
		// TODO Auto-generated method stub
		return mailService.emailPasswordReset(dto);
	}
	
	

}
