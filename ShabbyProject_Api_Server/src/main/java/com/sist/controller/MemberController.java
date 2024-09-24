package com.sist.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.common.util.PathVariableValidation;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.follow.FollowInformDTO;
import com.sist.dto.follow.FollowSearchResultDTO;
import com.sist.dto.follow.HandleFollowReqDTO;
import com.sist.dto.member.EmailAuthDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.service.member.JoinService;
import com.sist.service.member.MemberFindService;
import com.sist.service.primary.FollowService;
import com.sist.service.util.impl.MailServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
private final JoinService joinService;//회원가입 서비스 	
private final MemberFindService memberFindService;// 회원정보 찾기 서비스 
private final FollowService followService;
	
	//회원가입 
	@PostMapping
	public ResponseEntity<ResponseDTO<Void>> join(@Valid MemberDTO dto){	
		return joinService.join(dto);
	}
	//닉네임 중복검증 
	//서비스에서 검증
	@PostMapping("/nickValidate")
	public ResponseEntity<ResponseDTO<Void>> nickNameValidation(@RequestBody MemberDTO dto){
		if(!PathVariableValidation.nickNameValService(dto.getNickname())) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		return joinService.nickNameValidation(dto.getNickname());
	}

	//회원가입시 이메일 인증코드 받는 api
	//서비스에서 검증 
	@PostMapping("/emailAuth")
	public ResponseEntity<ResponseDTO<Void>> emailAuth(@RequestBody MemberDTO dto){
		
		if(!PathVariableValidation.emailValService(dto.getEmail())) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		return joinService.emailAuth(dto);
		
	}
	// 회원가입시 인증번호 검증하는 api
	//서비스에서 검증 
	@PostMapping("/emailValidate")
	public ResponseEntity<ResponseDTO<Void>> emailValidation(@RequestBody EmailAuthDTO dto){
		if(!PathVariableValidation.authCodeValidation(dto.getCode()) || !PathVariableValidation.emailValService(dto.getEmail())) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		return joinService.emailValidation(dto);
	}
	
	//아이디 찾기 
	//서비스에서 검증
	@PostMapping("/findEmail")
	public ResponseEntity<ResponseDTO<MemberDTO>> findId(@RequestBody MemberDTO dto){
		
		if (!PathVariableValidation.nameValSevice(dto.getName())|| !PathVariableValidation.nickNameValService(dto.getNickname())
			||!PathVariableValidation.phoneValService(dto.getPhone())) {	
				
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		return memberFindService.findEmail(dto);
	}
	
	//패스워드 찾기/초기화 = > 이메일 서비스
	//서비스에서 검증
	@PostMapping("/findPassword")
	public ResponseEntity<ResponseDTO<Void>> findPassword(@RequestBody MemberDTO dto){
		// validation
		if (!PathVariableValidation.nameValSevice(dto.getName())|| !PathVariableValidation.emailValService(dto.getEmail())
			|| !PathVariableValidation.phoneValService(dto.getPhone())) {			
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		return memberFindService.passwordFind(dto);
	}
	
	
	//페이지 , 행개수 를 pathvariable로 받아 현재 세션(로그인한 아이디) 기반 팔로잉 리스트 
	//키워드 -- 파람
	@GetMapping("/following/{page}/{rowSize}")
	public ResponseEntity<ResponseDTO<FollowSearchResultDTO>> followingByKeyword(@RequestParam String keyword,
	@PathVariable int page,@PathVariable int rowSize){
		
		//페이지, 행개수에 대한 validation 
		if (!PathVariableValidation.pageValidation(page) || !PathVariableValidation.pageValidation(rowSize)
			|| !PathVariableValidation.keyWordValService(keyword)) {

			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		
		return  followService.followingBykeyword(keyword, page, rowSize);
	}
	
	//팔로우 수락, 거절
	@PostMapping("/following")
	public ResponseEntity<ResponseDTO<Void>> handleFollowRequest(@RequestBody HandleFollowReqDTO dto){
		
		if(!PathVariableValidation.nickNameValService(dto.getNickname())|| (!"ACCEPT".equals(dto.getType()) && !"REFUSE".equals(dto.getType())) ) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		return followService.handleFollowRequest(dto);
	}
	
	//팔로우 추천
	@GetMapping("/recommend")
	public ResponseEntity<ResponseDTO<List<FollowInformDTO>>> followRecommend(){
		return followService.followRecommend();
	}
	
	
}
