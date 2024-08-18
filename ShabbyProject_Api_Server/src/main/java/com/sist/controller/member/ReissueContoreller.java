package com.sist.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.service.member.security.MybatisRefreshService;
import com.sist.service.member.security.RefreshService;

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
	
	@PostMapping("/reissue")
	public ResponseEntity<?> reisue(HttpServletRequest request, HttpServletResponse response){
		
		return service.reissue(request,response);
	}
}
