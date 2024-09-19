package com.sist.repository;

import java.util.List;

import com.sist.dto.util.AlarmChangeDTO;
import com.sist.dto.util.AlarmDTO;
import com.sist.dto.util.AlarmListDTO;
import com.sist.dto.util.GlobalSearchDTO;
import com.sist.dto.util.GlobalSearchResultDTO;
import com.sist.dto.util.SearchResultMemberDTO;

public interface GlobalSearchRepository {
	    // 글로벌 서치 자동완성 검색결과 
		public List<GlobalSearchResultDTO> globalSearchList(GlobalSearchDTO dto);
		// 글로벌 서치 회원정보 결과 
		public List<SearchResultMemberDTO> globalSearchMember(GlobalSearchDTO dto);
		//글로벌 서치 회원정보 총 페이지 
		public int searchMemberTotalPage(GlobalSearchDTO dto);
	
}
