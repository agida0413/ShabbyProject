package com.sist.service.member.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.member.RequestFollowDTO;
import com.sist.dto.member.ResponseFollowDTO;
import com.sist.repository.member.FollowRepository;
import com.sist.service.member.FollowService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
	
	private final FollowRepository followRepository;
	private final SimpleCodeGet simpleCodeGet;
	@Override
	public ResponseEntity<ResponseDTO<ResponseFollowDTO>> followingBykeyword(String keyword,int page,int rowSize) {
		// TODO Auto-generated method stub
		
		//공통모듈에서 offset 계산과 , 현재 securityContextHolder에 담긴  idNum가져옴
		int offSet=simpleCodeGet.getOffset(rowSize,page);
		int idNum=simpleCodeGet.getIdNum();
		
		//데이터베이스 전송객체 생성 및 값 세팅 
		RequestFollowDTO dto = new RequestFollowDTO();
		dto.setIdNum(idNum);
		dto.setKeyword(keyword);	
		dto.setRowSize(rowSize);
		dto.setStartRow(offSet);
		
		//MemberDto 리스트 형태로 결과 반환
		List<MemberDTO> list= followRepository.followingByKeyword(dto);
		//클라이언트 전송 객체 생성
		ResponseFollowDTO resDto= new ResponseFollowDTO();
		//MemberDto 리스트 형태 변수에 값 세팅  
		resDto.setFollowList(list);
		
		return new ResponseEntity<ResponseDTO<ResponseFollowDTO>>
		(new ResponseDTO<ResponseFollowDTO>(resDto),HttpStatus.OK); //성공 
	}

}
