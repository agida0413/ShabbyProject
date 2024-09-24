package com.sist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.common.util.PathVariableValidation;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.util.AlarmListDTO;
import com.sist.dto.util.AlarmResultDTO;
import com.sist.service.minor.AlarmService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {

	private final AlarmService alarmService;
	
	
	//알람 획득
	@GetMapping("/{page}")
	public ResponseEntity<ResponseDTO<AlarmResultDTO>> getAlarm(@PathVariable int page){
		
		if(!PathVariableValidation.pageValidation(page)) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		
		return alarmService.getAlarm(page);
	}
	//안읽은 알람의 개수 
	@GetMapping
	public ResponseEntity<ResponseDTO<Integer>> alarmCount(){
		return alarmService.alarmCount();
	}
}
