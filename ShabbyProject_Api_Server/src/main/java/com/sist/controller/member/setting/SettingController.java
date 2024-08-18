package com.sist.controller.member.setting;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.service.member.setting.ChangeInfoService;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SettingController {

	private final ChangeInfoService changeInfoService;
	
	@GetMapping
	public ResponseEntity<?> getInfo(){
		
		return changeInfoService.getInfo();
	}
	
	@PostMapping("/nickChange")
	public final ResponseEntity<?> nicknameChange(@RequestBody MemberVO vo){
		return changeInfoService.nickNameUpdate(vo);
	}
	
}
