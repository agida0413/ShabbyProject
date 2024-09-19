package com.sist.repository.impl.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.sist.dto.common.GlobalSearchDTO;
import com.sist.dto.common.GlobalSearchResultDTO;
import com.sist.dto.common.SearchResultMemberDTO;
import com.sist.mapper.GlobalSearchMapper;
import com.sist.repository.GlobalSearchRepository;
import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MybatisGlobalSearchRepository implements GlobalSearchRepository{

	private final GlobalSearchMapper globalSearchMapper;
	 // 글로벌 서치 자동완성 검색결과 
	@Override
	public List<GlobalSearchResultDTO> globalSearchList(GlobalSearchDTO dto) {
		// TODO Auto-generated method stub
		return globalSearchMapper.globalSearchList(dto);
	}
	// 글로벌 서치 회원정보 결과 
	@Override
	public List<SearchResultMemberDTO> globalSearchMember(GlobalSearchDTO dto) {
		// TODO Auto-generated method stub
		return globalSearchMapper.globalSearchMember(dto);
	}
	//글로벌 서치 회원정보 총 페이지 
	@Override
	public int searchMemberTotalPage(GlobalSearchDTO dto) {
		// TODO Auto-generated method stub
		return globalSearchMapper.searchMemberTotalPage(dto);
	}
	
	
}
