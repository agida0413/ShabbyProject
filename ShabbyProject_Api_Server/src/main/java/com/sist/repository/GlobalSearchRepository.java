package com.sist.repository;

import java.util.List;

import com.sist.dto.common.AlarmChangeDTO;
import com.sist.dto.common.AlarmDTO;
import com.sist.dto.common.AlarmListDTO;
import com.sist.dto.common.GlobalSearchDTO;
import com.sist.dto.common.GlobalSearchResultDTO;

import com.sist.dto.common.SearchResultMemberDTO;

public interface GlobalSearchRepository {
	    // 글로벌 서치 자동완성 검색결과 
		public List<GlobalSearchResultDTO> globalSearchList(GlobalSearchDTO dto);
		// 글로벌 서치 회원정보 결과 
		public List<SearchResultMemberDTO> globalSearchMember(GlobalSearchDTO dto);
		//글로벌 서치 회원정보 총 페이지 
		public int searchMemberTotalPage(GlobalSearchDTO dto);
	
}
