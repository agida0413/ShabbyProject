package com.sist.repository.common;

import java.util.List;

import com.sist.dto.common.GlobalSearchDTO;
import com.sist.dto.common.GlobalSearchResultDTO;

import com.sist.dto.common.SearchResultMemberDTO;

public interface CommonRepository {
	public List<GlobalSearchResultDTO> globalSearchList(GlobalSearchDTO dto);
	public List<SearchResultMemberDTO> globalSearchMember(GlobalSearchDTO dto);
	public int searchMemberTotalPage(GlobalSearchDTO dto);
}
