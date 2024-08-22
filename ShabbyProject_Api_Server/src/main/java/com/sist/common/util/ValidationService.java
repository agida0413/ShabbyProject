package com.sist.common.util;

import org.springframework.stereotype.Component;

import com.sist.dto.member.MemberDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidationService {

	private final ValidationUtil validationUtil;
	
	//회원가입 validation
	public boolean joinValService(MemberDTO dto) {
		
		//이메일 검증
		if(!emailValService(dto)) {
			return false;
		}
		//비밀번호 검증
		if(!pwdValService(dto)) {
			return false;
		}
		
		//닉네임 검증
		if(!nickNameValService(dto)) {
			return false;
		}
		
		//휴대폰 검증 
		if (!phoneValService(dto)) {
			return false;
		}
		//이름 검증
		if(!nameValSevice(dto)) {
			return false;
		}
		
		return true;
	}
	
	public boolean emailValService(MemberDTO dto) {
		//이메일 입력 null 불가
		if (!validationUtil.cantNull(dto.getEmail())) {
			return false;
		}

		// 이메일 형식이 아닐경우 불가
		if (!validationUtil.mustEmailType(dto.getEmail())) {

			return false;
		}
		// 이메일 첫 문자 공백 불가
		if (!validationUtil.cantFirstWhiteSapce(dto.getEmail())) {
			return false;
		}

		// 이메일 공백포함 불가
		if (!validationUtil.cantWhiteSpace(dto.getEmail())) {
			return false;
		}
		
		return true;
	}
	
	public boolean nickNameValService(MemberDTO dto) {
		//닉네임 null 불가
    	if(!validationUtil.cantNull(dto.getNickname())) {
			return false;
		}
		//닉네임 공백으로 시작 불가 
		if(!validationUtil.cantFirstWhiteSapce(dto.getNickname())) {
			return false;
		} 
		//닉네임 공백 포함 불가
		if(!validationUtil.cantWhiteSpace(dto.getNickname())) {
			return false;
		}
		//닉네임 한글포함 불가
		if(!validationUtil.cantKorean(dto.getNickname())) {
			return false;
		}
		//닉네임 _를 제외한 특수문자 포함 불가
		if(!validationUtil.cantSpecialChar(dto.getNickname())) {
			return false;
		}
		//닉네임 15자 이상 불가
		if(!validationUtil.cantOverLength(dto.getNickname(), 15)) {
			return false;
		}
			
	 return true;
	}
	
	public boolean phoneValService(MemberDTO dto) {
		//반드시11자리
		if(!validationUtil.mustSameLength(dto.getPhone(),11)) {
			return false;
		}
		//한국어를 포함 할수 없음
		if(!validationUtil.cantKorean(dto.getPhone())) {
			return false;
		}
		//첫글자 공백일수 없음
		if(!validationUtil.cantFirstWhiteSapce(dto.getPhone())) {
			return false;
		}
		//공백을 포함할수없음
		if(!validationUtil.cantWhiteSpace(dto.getPhone())) {
			return false;
		}
		//숫자로만 이루어져야함
		if(!validationUtil.mustOnlyNum(dto.getPhone())) {
			return false;
		}
		
		return true;
	}
	
	public boolean pwdValService(MemberDTO dto) {
		//비밀번호 널 불가
		if(!validationUtil.cantNull(dto.getPassword())) {
			return false;
		}
		//비밀번호 첫글자 공백 불가
		if(!validationUtil.cantFirstWhiteSapce(dto.getPassword())) {
			return false;
		}
		//비밀번호 공백포함 불가
		if(!validationUtil.cantWhiteSpace(dto.getPassword())) {
			return false;
		}
		// 비밀번호 20글자 초과 불가
		if (!validationUtil.cantOverLength(dto.getPassword(), 20)) {
			return false;
		}
		// 비밀번호 9자 미만 불가
		if (!validationUtil.cantUnderLength(dto.getPassword(), 9)) {
			return false;
		}
		// 비밀번호 최소 하나의 문자 포함 불가
		if (!validationUtil.mustContainEng(dto.getPassword())) {
			return false;
		}
		// 비밀번호 최소하나의 숫자 포함 불가
		if (!validationUtil.mustContainNum(dto.getPassword())) {
			return false;
		}
		// 비밀번호 최소하나의 특수문자 포함 불가
		if (!validationUtil.mustContainSpecialChar(dto.getPassword())) {
			return false;
		}
		
		return true;
	}
	
	public boolean nameValSevice(MemberDTO dto) {
		//이름 널 불가
		if(!validationUtil.cantNull(dto.getName())) {
			return false;
		}
		//이름 공백포함 불가
		if(!validationUtil.cantWhiteSpace(dto.getName())) {
			return false;
		}
		//이름 공백 시작 불가
		if(!validationUtil.cantFirstWhiteSapce(dto.getName())) {
			return false;
		}
		
		
		return true;
	}
}
