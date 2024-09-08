package com.sist.service.common;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.GlobalSearchResultDTO;
import com.sist.dto.common.SearchResultMemberDTO;
import com.sist.dto.common.SearchResultMemberListDTO;

public interface GlobalSearchService {

	public ResponseEntity<ResponseDTO<List<GlobalSearchResultDTO>>> globalSearchResult(String keyword,int page);
	public ResponseEntity<ResponseDTO<SearchResultMemberListDTO>> globalSearchMemberList(String keyword,int page);
 }
