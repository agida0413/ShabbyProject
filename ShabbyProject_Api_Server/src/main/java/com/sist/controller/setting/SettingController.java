package com.sist.controller.setting;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.setting.ChangeNickNameDTO;
import com.sist.dto.setting.ChangePasswordDTO;
import com.sist.dto.setting.ChangePhoneDTO;
import com.sist.service.setting.ChangeInfoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SettingController {

	private final ChangeInfoService changeInfoService;
	
	
	//회원정보(공개/비공개 여부 )를 가져오는 api
	@GetMapping
	public ResponseEntity<ResponseDTO<MemberDTO>> getInfo(){
		
		return changeInfoService.getLockedInfo();
	}
	
	//회원탈퇴 api
	@DeleteMapping
	public ResponseEntity<ResponseDTO<Void>> memberDelete(@RequestBody MemberDTO dto){
		return changeInfoService.deleteMember(dto);
	}
	
	//닉네임 변경하는 apis
	@PutMapping("/nickChange")
	public  ResponseEntity<ResponseDTO<Void>> nicknameChange(@RequestBody @Valid ChangeNickNameDTO dto){
		return changeInfoService.nickNameUpdate(dto);
	}
	
	//비밀번호 변경 api
	@PutMapping("/pwdChange")
	public  ResponseEntity<ResponseDTO<Void>> pwdChange(@Valid ChangePasswordDTO dto){ // formdata 
	
	
		return changeInfoService.passwordUpdate(dto);
	}
	
	//핸드폰 번호 변경 api
	@PutMapping("/phoneChange")
	public ResponseEntity<ResponseDTO<Void>> phoneChange(@RequestBody @Valid ChangePhoneDTO dto){
		
		return changeInfoService.phoneChange(dto);
		
	}
	
	@PutMapping("/lockStateChange")
	public ResponseEntity<ResponseDTO<MemberDTO>> lockStateChange(@RequestBody MemberDTO dto){
		
		return changeInfoService.updateLockedState(dto);
		
	}
	
	
}
