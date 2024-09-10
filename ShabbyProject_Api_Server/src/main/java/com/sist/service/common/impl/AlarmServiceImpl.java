package com.sist.service.common.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.AlarmListDTO;
import com.sist.repository.common.CommonRepository;
import com.sist.service.common.AlarmService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

	private final CommonRepository commonRepository;
	@Override
	public ResponseEntity<ResponseDTO<List<AlarmListDTO>>> getAlarm() {
		// TODO Auto-generated method stub
		int idNum=SimpleCodeGet.getIdNum();
		
		List<AlarmListDTO> list = commonRepository.getAlarm(idNum);
		
		return new ResponseEntity<ResponseDTO<List<AlarmListDTO>>>
		(new ResponseDTO<List<AlarmListDTO>>(list),HttpStatus.OK); //성공 
	}
	
}
