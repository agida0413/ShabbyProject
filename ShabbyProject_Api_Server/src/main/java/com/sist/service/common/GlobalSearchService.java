package com.sist.service.common;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.GlobalSearchResultDTO;
import com.sist.dto.common.SearchResultMemberDTO;
import com.sist.dto.common.SearchResultMemberListDTO;

public interface GlobalSearchService {
	//글로벌 서치 자동완성 검색결과 
	public ResponseEntity<ResponseDTO<List<GlobalSearchResultDTO>>> globalSearchResult(String keyword,int page);
	//글로벌 서치 회원정보 
	public ResponseEntity<ResponseDTO<SearchResultMemberListDTO>> globalSearchMemberList(String keyword,int page);
 }
