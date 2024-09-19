package com.sist.service.common.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.AlarmDTO;
import com.sist.dto.common.AlarmListDTO;
import com.sist.dto.common.AlarmResultDTO;
import com.sist.repository.AlarmRepository;
import com.sist.service.common.AlarmService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

	private final AlarmRepository alarmRepository;
	//알람 획득 서비스 
	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<AlarmResultDTO>> getAlarm(int page) {
		// TODO Auto-generated method stub
		//회원 고유번호 
		int idNum=SimpleCodeGet.getIdNum();
		//행개수
		int rowSize=10;
		//offset
		int offSet=SimpleCodeGet.getOffset(rowSize, page);
		//알람 객체 생성 
		AlarmDTO dto= new AlarmDTO();
		//값 세팅 
		dto.setIdNum(idNum);
		dto.setStartRow(offSet);
		dto.setRowSize(rowSize);
		
		//조건에 해당하는 알람 리스트를 가져옴 
		List<AlarmListDTO> list = alarmRepository.getAlarm(dto);
		//전송객체 생성 
		AlarmResultDTO resultDTO=new AlarmResultDTO();
		if(list.size()!=0) {
			//조건에 해당하는 알람 리스트의 총페이지 
			int totalPage=alarmRepository.getAlarmTotalPage(dto);	
			//리스트 담기 
			resultDTO.setList(list);
			//총페이지 담기 
			resultDTO.setTotalPage(totalPage);
			//읽음 상태 업데이트
			alarmRepository.updateIsread(list);
		}
		
		
		
		return new ResponseEntity<ResponseDTO<AlarmResultDTO>>
		(new ResponseDTO<AlarmResultDTO>(resultDTO),HttpStatus.OK); //성공 
	}
	
	
	@Override
	public ResponseEntity<ResponseDTO<Integer>> alarmCount() {
		// TODO Auto-generated method stub
		int idNum=SimpleCodeGet.getIdNum();
		int alarmCount=alarmRepository.alarmCount(idNum);
		return new ResponseEntity<ResponseDTO<Integer>>
		(new ResponseDTO<Integer>(alarmCount),HttpStatus.OK); //성공 
	}
}
