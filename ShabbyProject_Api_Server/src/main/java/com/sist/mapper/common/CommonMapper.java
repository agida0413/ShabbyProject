package com.sist.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.common.AlarmDTO;
import com.sist.dto.common.AlarmListDTO;
import com.sist.dto.common.GlobalSearchDTO;
import com.sist.dto.common.GlobalSearchResultDTO;

import com.sist.dto.common.SearchResultMemberDTO;
@Mapper
public interface CommonMapper {
    // 글로벌 서치 자동완성 검색결과 
	public List<GlobalSearchResultDTO> globalSearchList(GlobalSearchDTO dto);
	// 글로벌 서치 회원정보 결과 
	public List<SearchResultMemberDTO> globalSearchMember(GlobalSearchDTO dto);
	//글로벌 서치 회원정보 총 페이지 
	public int searchMemberTotalPage(GlobalSearchDTO dto);
	
	//알람 삽입 
	public void alarmInsert(AlarmDTO dto);
	//좋아요 알람 삽입 시에 receiver를 받아옴 (post_num 기준 아이디번호 획득)
	public int chooseReceiver(int postNum);
	//게시물 수정시 기존알람은 냅두고, 새로운 알람만 삽입하기위함
	public List<String> originalMemberTag(int postNum);
	//알람 삭제 
	public void alarmDelete(AlarmDTO dto);
	
	//알람 조회
	public List<AlarmListDTO> getAlarm(int idNum);
	
}
