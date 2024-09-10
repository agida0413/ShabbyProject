package com.sist.service.common;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.AlarmListDTO;

public interface AlarmService {
	
	public ResponseEntity<ResponseDTO<List<AlarmListDTO>>> getAlarm();

}
