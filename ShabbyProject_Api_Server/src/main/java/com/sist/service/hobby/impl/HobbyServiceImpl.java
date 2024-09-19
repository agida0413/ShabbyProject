package com.sist.service.hobby.impl;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sist.common.exception.BadRequestException;
import com.sist.common.util.PathVariableValidation;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.hobby.HobbyDTO;
import com.sist.dto.hobby.SearchHobbyDTO;
import com.sist.dto.hobby.SearchHobbyListDTO;
import com.sist.mapper.hobbyMapper;
import com.sist.repository.HobbyRepository;
import com.sist.service.hobby.HobbyService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class HobbyServiceImpl implements HobbyService {
	
	private final int rowSize=5;//조회할 행 개수 
	private final HobbyRepository hobbyRepository;


	//키워드,페이지 기반 관심사 태그 조회 
	@Override
	public ResponseEntity<ResponseDTO<SearchHobbyListDTO>> findHobby(String keyword,int page) {
		// TODO Auto-generated method stub
		//데이터베이스에 보낼 객체 생성
		
		
		SearchHobbyDTO dto = new SearchHobbyDTO();
		//offset 설정 - > 계산로직은 항상 동일하여 공통함수에서 계산
		int offSet=SimpleCodeGet.getOffset(rowSize,page);
		
	
		dto.setKeyword(keyword);//키워드 세팅
		dto.setRowSize(rowSize);//행개수 세팅
		dto.setStartRow(offSet);//offset 세팅 
		
		//Limit rowsize offset offset 형태로 키워드기반 관심사 가져옴 
		List<HobbyDTO> list=hobbyRepository.findHobby(dto);
		
		SearchHobbyListDTO resDto=new SearchHobbyListDTO(); // 전송데이터 객체생성 
		
		
		resDto.setFindList(list); //전송데이터 객체 리스트형태<HobbyDto> 에 세팅 
		
		
		return new ResponseEntity<ResponseDTO<SearchHobbyListDTO>>
		(new ResponseDTO<SearchHobbyListDTO>(resDto),HttpStatus.OK); //성공 
	}

}
