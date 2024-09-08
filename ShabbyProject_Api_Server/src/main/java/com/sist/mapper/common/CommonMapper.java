package com.sist.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.common.GlobalSearchDTO;
import com.sist.dto.common.GlobalSearchResultDTO;

import com.sist.dto.common.SearchResultMemberDTO;
@Mapper
public interface CommonMapper {

	public List<GlobalSearchResultDTO> globalSearchList(GlobalSearchDTO dto);
	
	public List<SearchResultMemberDTO> globalSearchMember(GlobalSearchDTO dto);
}
