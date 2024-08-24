package com.sist.service.hobby.impl;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.hobby.HobbyDTO;
import com.sist.dto.hobby.RequestHobbyDTO;
import com.sist.dto.hobby.ResponseHobbyDTO;import com.sist.mapper.hobby.hobbyMapper;
import com.sist.repository.hobby.HobbyRepository;
import com.sist.service.hobby.HobbyService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class HobbyServiceImpl implements HobbyService {
	private final int rowSize=5;
	private final HobbyRepository hobbyRepository;
	private final SimpleCodeGet simpleCodeGet;
	
	@Override
	public ResponseEntity<ResponseDTO<HobbyDTO>> allHobby() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	
	public ResponseEntity<ResponseDTO<ResponseHobbyDTO>> findHobby(String keyword,int page) {
		// TODO Auto-generated method stub
		RequestHobbyDTO dto = new RequestHobbyDTO();
		
		int offSet=simpleCodeGet.getOffset(rowSize,page);
				
		dto.setKeyword(keyword);
		dto.setRowSize(rowSize);
		dto.setStartRow(offSet);
		
		List<HobbyDTO> list=hobbyRepository.findHobby(dto);
		
		ResponseHobbyDTO resDto=new ResponseHobbyDTO();
		
		
		resDto.setFindList(list);
		
		
		return new ResponseEntity<ResponseDTO<ResponseHobbyDTO>>
		(new ResponseDTO<ResponseHobbyDTO>(resDto),HttpStatus.OK); //성공 
	}

}
