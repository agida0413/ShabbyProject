package com.sist.repository.common.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.dto.common.AlarmDTO;
import com.sist.dto.common.AlarmListDTO;
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
	//알람 삽입 
	@Override
	public void alarmInsert(AlarmDTO dto) {
		
		commonMapper.alarmInsert(dto);
		// TODO Auto-generated method stub		
	}
	//알람 삭제
	@Override
	public void alarmDelete(AlarmDTO dto) {
		// TODO Auto-generated method stub
		
		commonMapper.alarmDelete(dto);
	}
	//좋아요 알람 삽입 시에 receiver를 받아옴 (post_num 기준 아이디번호 획득)
	@Override
	public int chooseReceiver(int postNum) {
		// TODO Auto-generated method stub
		return commonMapper.chooseReceiver(postNum);
	}
	//게시물 수정시 기존알람은 냅두고, 새로운 알람만 삽입하기위함
	@Override
	public List<String> originalMemberTag(int postNum) {
		// TODO Auto-generated method stub
		return commonMapper.originalMemberTag(postNum);
	}
	@Override
	public List<AlarmListDTO> getAlarm(int idNum) {
		// TODO Auto-generated method stub
		return commonMapper.getAlarm(idNum);
	}
	
	
	
}
