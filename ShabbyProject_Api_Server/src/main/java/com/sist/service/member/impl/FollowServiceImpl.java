package com.sist.service.member.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sist.common.exception.BadRequestException;
import com.sist.common.util.PathVariableValidation;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.feed.DoFollowDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.member.FollowSearchDTO;
import com.sist.dto.member.FollowSearchResultDTO;
import com.sist.repository.member.FollowRepository;
import com.sist.service.member.FollowService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
	
	
	private final FollowRepository followRepository;
	
	
	
	
	@Override
	public ResponseEntity<ResponseDTO<FollowSearchResultDTO>> followingBykeyword(String keyword,int page,int rowSize) {
		// TODO Auto-generated method stub
		
		if(!PathVariableValidation.pageValidation(page)||!PathVariableValidation.pageValidation(rowSize)) {
			throw new BadRequestException("유효하지 않은 입력입니다.");
		}
		
		//공통모듈에서 offset 계산과 , 현재 securityContextHolder에 담긴  idNum가져옴
		int offSet=SimpleCodeGet.getOffset(rowSize,page);
		int idNum=SimpleCodeGet.getIdNum();
		
		//데이터베이스 전송객체 생성 및 값 세팅 
		FollowSearchDTO dto = new FollowSearchDTO();
		dto.setIdNum(idNum);
		dto.setKeyword(keyword);	
		dto.setRowSize(rowSize);
		dto.setStartRow(offSet);
		
		//MemberDto 리스트 형태로 결과 반환
		List<MemberDTO> list= followRepository.followingByKeyword(dto);
		//클라이언트 전송 객체 생성
		FollowSearchResultDTO resDto= new FollowSearchResultDTO();
		//MemberDto 리스트 형태 변수에 값 세팅  
		resDto.setFollowList(list);
		
		return new ResponseEntity<ResponseDTO<FollowSearchResultDTO>>
		(new ResponseDTO<FollowSearchResultDTO>(resDto),HttpStatus.OK); //성공 
	}




	@Override
	public ResponseEntity<ResponseDTO<Void>> doFollow(DoFollowDTO dto) {
		// TODO Auto-generated method stub
		
		int idNum=SimpleCodeGet.getIdNum();
		dto.setIdNum(idNum);
		followRepository.doFollow(dto);
		
		return new ResponseEntity<ResponseDTO<Void>>
		(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}

}
