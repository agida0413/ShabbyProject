package com.sist.service.common;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.AlarmListDTO;
import com.sist.dto.common.AlarmResultDTO;

public interface AlarmService {
	//사용자 알람 획득 서비스 
	public ResponseEntity<ResponseDTO<AlarmResultDTO>> getAlarm(int page);
	public ResponseEntity<ResponseDTO<Integer>> alarmCount();
}
