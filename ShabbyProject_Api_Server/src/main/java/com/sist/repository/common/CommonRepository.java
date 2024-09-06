package com.sist.repository.common;

import java.util.List;

import com.sist.dto.common.GlobalSearchDTO;
import com.sist.dto.common.GlobalSearchResultDTO;

public interface CommonRepository {
	public List<GlobalSearchResultDTO> globalSearchList(GlobalSearchDTO dto);
}
