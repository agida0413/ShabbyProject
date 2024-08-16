package com.sist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.service.member.RefreshService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReissueContoreller {

	private final RefreshService service;
	
	@PostMapping("/reissue")
	public ResponseEntity<?> reisue(HttpServletRequest request, HttpServletResponse response){
		
		return service.reissue(request,response);
	}
}
