package com.sist.controller.search;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.GlobalSearchResultDTO;
import com.sist.dto.common.SearchResultMemberDTO;
import com.sist.service.common.GlobalSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class GlobalSearchController {
	
	private final GlobalSearchService globalSearchService;
	
	@GetMapping("/{page}")
	public ResponseEntity<ResponseDTO<List<GlobalSearchResultDTO>>> globalSearchReult(@RequestParam String keyword,@PathVariable int page){
		//_ 키워드 데이터베이스 변환 필요
		keyword=keyword.replace("_", "\\_");
		return globalSearchService.globalSearchResult(keyword, page);
	}
	
	@GetMapping("/member/{page}")
	public ResponseEntity<ResponseDTO<List<SearchResultMemberDTO>>> globalSearchMemberList(@RequestParam String keyword,@PathVariable int page){
		//_ 키워드 데이터베이스 변환 필요
		keyword=keyword.replace("_", "\\_");
		return globalSearchService.globalSearchMemberList(keyword, page);
	}
}
