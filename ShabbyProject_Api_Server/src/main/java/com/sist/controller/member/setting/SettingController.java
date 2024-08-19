package com.sist.controller.member.setting;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.MemberDTO;
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
		
		return changeInfoService.getLockedInfo();
	}
	
	@PutMapping("/nickChange")
	public  ResponseEntity<?> nicknameChange(@RequestBody MemberDTO dto){
		return changeInfoService.nickNameUpdate(dto);
	}
	
	@PutMapping("/pwdChange")
	public  ResponseEntity<?> pwdChange( String password,String newPassword){ // formdata 
	
	
		return changeInfoService.passwordUpdate(password,newPassword);
	}
	
	@PutMapping("/phoneChange")
	public ResponseEntity<?> phoneChange(@RequestBody MemberDTO dto){
		
		return changeInfoService.phoneChange(dto);
		
	}
}
