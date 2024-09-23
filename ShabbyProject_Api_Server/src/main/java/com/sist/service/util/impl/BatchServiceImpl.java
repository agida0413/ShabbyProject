package com.sist.service.util.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.repository.BatchRepository;
import com.sist.service.util.BatchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService{
	private final BatchRepository batchRepository;
	
	//애플리케이션 전체 배치
	@Transactional
	@Scheduled(cron = "0 0 4 * * ?") // 매일 자정 4시 실행
	public void batch() {
		//이메일 인증 배치
		emailAuthBatch();
		//리프레시 토큰 배치
		tokenBatch();
	}
	
	//이메일 인증 관련 배치
	private void emailAuthBatch() {
		// TODO Auto-generated method stub
		//유효기간 만료 레코드 삭제
		batchRepository.batchEmailAuth();
		
	}
	//토큰 관련 배치
	private void tokenBatch() {
		// TODO Auto-generated method stub
		//아이디가 중복된 리프레시토큰 삭제
		batchRepository.batchTokenDupl();
		//유효기간 만료 리프레시토큰 삭제
		batchRepository.batchTokenExpir();
	}

}
