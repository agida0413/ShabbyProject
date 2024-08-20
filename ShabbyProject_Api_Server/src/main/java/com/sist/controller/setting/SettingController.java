package com.sist.controller.setting;

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

import com.sist.dto.member.MemberDTO;
import com.sist.service.setting.ChangeInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SettingController {

	private final ChangeInfoService changeInfoService;
	
	
	//회원정보(공개/비공개 여부 )를 가져오는 api
	@GetMapping
	public ResponseEntity<?> getInfo(){
		
		return changeInfoService.getLockedInfo();
	}
	
	//닉네임 변경하는 api
	@PutMapping("/nickChange")
	public  ResponseEntity<?> nicknameChange(@RequestBody MemberDTO dto){
		return changeInfoService.nickNameUpdate(dto);
	}
	
	//비밀번호 변경 api
	@PutMapping("/pwdChange")
	public  ResponseEntity<?> pwdChange( String password,String newPassword){ // formdata 
	
	
		return changeInfoService.passwordUpdate(password,newPassword);
	}
	
	//핸드폰 번호 변경 api
	@PutMapping("/phoneChange")
	public ResponseEntity<?> phoneChange(@RequestBody MemberDTO dto){
		
		return changeInfoService.phoneChange(dto);
		
	}
}
