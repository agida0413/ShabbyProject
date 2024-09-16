package com.sist.controller.alarm;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.AlarmListDTO;
import com.sist.dto.common.AlarmResultDTO;
import com.sist.service.common.AlarmService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {

	private final AlarmService alarmService;
	
	
	//알람 획득
	@GetMapping("/{page}")
	public ResponseEntity<ResponseDTO<AlarmResultDTO>> getAlarm(@PathVariable int page){
		return alarmService.getAlarm(page);
	}
	@GetMapping
	public ResponseEntity<ResponseDTO<Integer>> alarmCount(){
		return alarmService.alarmCount();
	}
}
