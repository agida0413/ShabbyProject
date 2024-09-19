package com.sist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.api.ResponseDTO;
import com.sist.service.member.RefreshService;
import com.sist.service.member.impl.MybatisRefreshService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class ReissueContoreller {

	private final RefreshService service;
	
	//refresh토큰 재발행 api
	//서비스에서 검증
	@PostMapping("/reissue")
	public ResponseEntity<ResponseDTO<Void>> reisue(HttpServletRequest request, HttpServletResponse response){
		
		return service.reissue(request,response);
	}
}
