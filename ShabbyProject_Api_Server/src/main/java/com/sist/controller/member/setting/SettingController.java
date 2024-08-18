package com.sist.controller.member.setting;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.service.member.setting.ChangeInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SettingController {

	private final ChangeInfoService changeInfoService;
	
	@GetMapping
	public ResponseEntity<?> getInfo(Authentication authentication){
		System.out.println("ss");
		return changeInfoService.getInfo(authentication);
	}
	
}
