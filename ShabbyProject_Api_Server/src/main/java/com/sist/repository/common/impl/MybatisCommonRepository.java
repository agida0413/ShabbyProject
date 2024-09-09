package com.sist.repository.common.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.dto.common.GlobalSearchDTO;
import com.sist.dto.common.GlobalSearchResultDTO;

import com.sist.dto.common.SearchResultMemberDTO;
import com.sist.mapper.common.CommonMapper;
import com.sist.repository.common.CommonRepository;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MybatisCommonRepository implements CommonRepository{

	private final CommonMapper commonMapper;
	 // 글로벌 서치 자동완성 검색결과 
	@Override
	public List<GlobalSearchResultDTO> globalSearchList(GlobalSearchDTO dto) {
		// TODO Auto-generated method stub
		return commonMapper.globalSearchList(dto);
	}
	// 글로벌 서치 회원정보 결과 
	@Override
	public List<SearchResultMemberDTO> globalSearchMember(GlobalSearchDTO dto) {
		// TODO Auto-generated method stub
		return commonMapper.globalSearchMember(dto);
	}
	//글로벌 서치 회원정보 총 페이지 
	@Override
	public int searchMemberTotalPage(GlobalSearchDTO dto) {
		// TODO Auto-generated method stub
		return commonMapper.searchMemberTotalPage(dto);
	}
	
	
	
}
