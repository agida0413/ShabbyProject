package com.sist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.common.AlarmChangeDTO;
import com.sist.dto.common.AlarmDTO;
import com.sist.dto.common.AlarmListDTO;
import com.sist.dto.common.GlobalSearchDTO;
import com.sist.dto.common.GlobalSearchResultDTO;

import com.sist.dto.common.SearchResultMemberDTO;
@Mapper
public interface AlarmMapper {
 
	//알람 삽입 
	public void alarmInsert(AlarmDTO dto);
	//좋아요 알람 삽입 시에 receiver를 받아옴 (post_num 기준 아이디번호 획득)
	public int chooseReceiver(int postNum);
	//게시물 수정시 기존알람은 냅두고, 새로운 알람만 삽입하기위함
	public List<String> originalMemberTag(int postNum);
	//알람 삭제 
	public void alarmDelete(AlarmDTO dto);
	//알람 조회
	public List<AlarmListDTO> getAlarm(AlarmDTO dto);
	
	//알람 총 페이지
	public int getAlarmTotalPage(AlarmDTO dto);
	
	//비공개 계정에 대한 팔로우 요청이 존재하고 , 아직 받지않은 상태에서 공개전환 시 기존 요청들을 수락상태로 변경 
	//and 팔로우 요청 수락시 알람 상태변경
	public void changeAlarmStatus(AlarmChangeDTO dto);

	//읽음 상태 변경
	public void updateIsread(List<AlarmListDTO> list);
	//팔로우 요청 거절시 알람 상태 변경 
	public void refuseReqAlarmStatus(AlarmChangeDTO dto);
	public int alarmCount(int idNum);
}
