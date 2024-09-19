package com.sist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.common.util.PathVariableValidation;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.util.GlobalSearchResultDTO;
import com.sist.dto.util.SearchResultMemberDTO;
import com.sist.dto.util.SearchResultMemberListDTO;
import com.sist.service.minor.GlobalSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class GlobalSearchController {
	
	private final GlobalSearchService globalSearchService;
	
	//자동완성 검색어 결과 
	@GetMapping("/{page}")
	public ResponseEntity<ResponseDTO<List<GlobalSearchResultDTO>>> globalSearchReult(@RequestParam String keyword,@PathVariable int page){
		//_ 키워드 데이터베이스 변환 필요
		
		
		//키워드 , 페이지에 대한 검증 
		if (!PathVariableValidation.pageValidation(page) || !PathVariableValidation.keyWordValService(keyword)) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		keyword=keyword.replace("_", "\\_");
		return globalSearchService.globalSearchResult(keyword, page);
	}
	// 검색피드에서의 회원 정보 
	@GetMapping("/member/{page}")
	public ResponseEntity<ResponseDTO<SearchResultMemberListDTO>> globalSearchMemberList(@RequestParam String keyword,@PathVariable int page){
	
		
		//페이지와 , 키워드에 대한 검증 
		if (!PathVariableValidation.pageValidation(page) || !PathVariableValidation.keyWordValService(keyword)) {

			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		//_ 키워드 데이터베이스 변환 필요
		keyword=keyword.replace("_", "\\_");
		return globalSearchService.globalSearchMemberList(keyword, page);
	}
}
