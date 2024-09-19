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
import com.sist.dto.common.SearchResultMemberDTO;
import com.sist.dto.common.SearchResultMemberListDTO;
import com.sist.repository.GlobalSearchRepository;
import com.sist.service.common.GlobalSearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GlobalSearchServiceImpl implements GlobalSearchService{

	private final GlobalSearchRepository globalSearchRepository;
	
	//글로벌 서치 자동완성 검색결과 
	@Override
	public ResponseEntity<ResponseDTO<List<GlobalSearchResultDTO>>> globalSearchResult(String keyword,int page) {
		// TODO Auto-generated method stub
		
		
		//데이터베이스 전송 객체 생성 
		GlobalSearchDTO reqDto= new GlobalSearchDTO();
		
		//회원 고유번호 
		int idNum=SimpleCodeGet.getIdNum();
		//가져올 행 개수 
		int rowSize=15;
		//OFFSET
		int offSet=SimpleCodeGet.getOffset(rowSize, page);
		//전송객체에 값 세팅 
		reqDto.setKeyword(keyword);
		reqDto.setRowSize(rowSize);
		reqDto.setStartRow(offSet);
		reqDto.setIdNum(idNum);
		//데이터 베이스에서 결과값 LIST 타입으로 받음 
		List<GlobalSearchResultDTO> list =globalSearchRepository.globalSearchList(reqDto);
		
		
		return new ResponseEntity<ResponseDTO<List<GlobalSearchResultDTO>>>
		(new ResponseDTO<List<GlobalSearchResultDTO>>(list),HttpStatus.OK); //성공 
	}

	//글로벌 서치 회원 정보 가져오기 
	@Override
	public ResponseEntity<ResponseDTO<SearchResultMemberListDTO>> globalSearchMemberList(String keyword, int page) {
		// TODO Auto-generated method stub
					
		//데이터 베이스 전송 객체 생성 
		GlobalSearchDTO reqDto= new GlobalSearchDTO();
	
		//회원 고유번호
		int idNum=SimpleCodeGet.getIdNum();
		//행 개수 
		int rowSize=10;
		//OFFEST
		int offSet=SimpleCodeGet.getOffset(rowSize, page);
		//전송 객체 값 세팅 
		reqDto.setKeyword(keyword);
		reqDto.setIdNum(idNum);
		reqDto.setStartRow(offSet);
		reqDto.setRowSize(rowSize);
		
		//클라이언트 전송 객체 생성 
		SearchResultMemberListDTO resDto= new SearchResultMemberListDTO();
		//총페이지 구하기 
		int totalPage=globalSearchRepository.searchMemberTotalPage(reqDto);
		//회원 리스트 
		List<SearchResultMemberDTO> list = globalSearchRepository.globalSearchMember(reqDto);
		
		//클라이언트 전송객체에 세팅 
		resDto.setList(list);
		resDto.setTotalPage(totalPage);
		
		return new ResponseEntity<ResponseDTO<SearchResultMemberListDTO>>
		(new ResponseDTO<SearchResultMemberListDTO>(resDto),HttpStatus.OK); //성공 
	}

}
