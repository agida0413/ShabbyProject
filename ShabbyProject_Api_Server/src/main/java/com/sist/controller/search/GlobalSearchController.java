package com.sist.controller.search;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.GlobalSearchResultDTO;
import com.sist.service.common.GlobalSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class GlobalSearchController {
	
	private final GlobalSearchService globalSearchService;
	
	@GetMapping("/{keyword}/{page}")
	public ResponseEntity<ResponseDTO<List<GlobalSearchResultDTO>>> globalSearchReult(@PathVariable String keyword,@PathVariable int page){
		
		return globalSearchService.globalSearchResult(keyword, page);
	}
}
