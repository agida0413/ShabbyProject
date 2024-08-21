package com.sist.service.security;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RefreshService {
	//리프레시토큰 데이터베이스 제거 서비스
	public	void deleteRefresh(String refresh);
	//리프레시 토큰 데이터베이스 추가 서비스
	public void addRefreshEntity(int idNum, String refresh, Long expiredMs);
	//리프레시 토큰 데이터베이스 존재여부 확인 서비스
	public Boolean	isExist(String refresh);
	//액세스 토큰 재발급 서비스
	public ResponseEntity<ResponseDTO<Void>> reissue(HttpServletRequest request, HttpServletResponse response);
	
	
}
