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

	@Override
	public List<GlobalSearchResultDTO> globalSearchList(GlobalSearchDTO dto) {
		// TODO Auto-generated method stub
		return commonMapper.globalSearchList(dto);
	}

	@Override
	public List<SearchResultMemberDTO> globalSearchMember(GlobalSearchDTO dto) {
		// TODO Auto-generated method stub
		return commonMapper.globalSearchMember(dto);
	}
	
	
	
}
