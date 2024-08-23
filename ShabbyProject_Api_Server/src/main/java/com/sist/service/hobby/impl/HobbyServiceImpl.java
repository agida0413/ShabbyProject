package com.sist.service.hobby.impl;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.hobby.HobbyDTO;
import com.sist.dto.hobby.ResponseHobby;import com.sist.mapper.hobby.hobbyMapper;
import com.sist.repository.hobby.HobbyRepository;
import com.sist.service.hobby.HobbyService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class HobbyServiceImpl implements HobbyService {

	private final HobbyRepository hobbyRepository;
	
	@Override
	public ResponseEntity<ResponseDTO<HobbyDTO>> allHobby() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	
	public ResponseEntity<ResponseDTO<ResponseHobby>> findHobby(String keyword) {
		// TODO Auto-generated method stub
		
	
		List<HobbyDTO> list=hobbyRepository.findHobby(keyword);
		System.out.println(list.size());
		ResponseHobby resDto=new ResponseHobby();
		if(list.size()==0) {
			HobbyDTO noDto= new HobbyDTO();
			noDto.setHobby("noData");
			list.add(noDto);
		}
		
		resDto.setFindList(list);
		
		
		return new ResponseEntity<ResponseDTO<ResponseHobby>>
		(new ResponseDTO<ResponseHobby>(resDto),HttpStatus.OK); //성공 
	}

}
