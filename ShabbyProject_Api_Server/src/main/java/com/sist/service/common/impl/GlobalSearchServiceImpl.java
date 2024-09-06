package com.sist.service.common.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sist.common.exception.BadRequestException;
import com.sist.common.util.PathVariableValidation;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.common.GlobalSearchDTO;
import com.sist.dto.common.GlobalSearchResultDTO;
import com.sist.repository.common.CommonRepository;
import com.sist.service.common.GlobalSearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GlobalSearchServiceImpl implements GlobalSearchService{

	private final CommonRepository commonRepository;
	
	@Override
	public ResponseEntity<ResponseDTO<List<GlobalSearchResultDTO>>> globalSearchResult(String keyword,int page) {
		// TODO Auto-generated method stub
		
		if(!PathVariableValidation.pageValidation(page)) {
			throw new BadRequestException("잘못된 페이지 요청입니다.");
		}
		
		
		GlobalSearchDTO reqDto= new GlobalSearchDTO();
		reqDto.setKeyword(keyword);
		
		int rowSize=15;
		int offSet=SimpleCodeGet.getOffset(rowSize, page);
		reqDto.setRowSize(rowSize);
		reqDto.setStartRow(offSet);
		
		List<GlobalSearchResultDTO> list =commonRepository.globalSearchList(reqDto);
		
		
		return new ResponseEntity<ResponseDTO<List<GlobalSearchResultDTO>>>
		(new ResponseDTO<List<GlobalSearchResultDTO>>(list),HttpStatus.OK); //성공 
	}

}
