package com.sist.service.minor;

import org.springframework.http.ResponseEntity;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.util.AlarmResultDTO;

public interface AlarmService {
	//사용자 알람 획득 서비스 
	public ResponseEntity<ResponseDTO<AlarmResultDTO>> getAlarm(int page);
	public ResponseEntity<ResponseDTO<Integer>> alarmCount();
}
