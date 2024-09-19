package com.sist.repository.impl.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.dto.util.AlarmChangeDTO;
import com.sist.dto.util.AlarmDTO;
import com.sist.dto.util.AlarmListDTO;
import com.sist.mapper.AlarmMapper;
import com.sist.mapper.GlobalSearchMapper;
import com.sist.repository.AlarmRepository;


import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MybatisAlarmRepository implements AlarmRepository{

	private final AlarmMapper alarmMapper;
	 // 글로벌 서치 자동완성 검색결과 

	//알람 삽입 
	@Override
	public void alarmInsert(AlarmDTO dto) {
		
		alarmMapper.alarmInsert(dto);
		// TODO Auto-generated method stub		
	}
	//알람 삭제
	@Override
	public void alarmDelete(AlarmDTO dto) {
		// TODO Auto-generated method stub
		
		alarmMapper.alarmDelete(dto);
	}
	//좋아요 알람 삽입 시에 receiver를 받아옴 (post_num 기준 아이디번호 획득)
	@Override
	public int chooseReceiver(int postNum) {
		// TODO Auto-generated method stub
		return alarmMapper.chooseReceiver(postNum);
	}
	//게시물 수정시 기존알람은 냅두고, 새로운 알람만 삽입하기위함
	@Override
	public List<String> originalMemberTag(int postNum) {
		// TODO Auto-generated method stub
		return alarmMapper.originalMemberTag(postNum);
	}
	@Override
	public List<AlarmListDTO> getAlarm(AlarmDTO dto) {
		// TODO Auto-generated method stub
		return alarmMapper.getAlarm(dto);
	}
	@Override
	public int getAlarmTotalPage(AlarmDTO dto) {
		// TODO Auto-generated method stub
		return alarmMapper.getAlarmTotalPage(dto);
	}
	@Override
	public void changeAlarmStatus(AlarmChangeDTO dto) {
		// TODO Auto-generated method stub
		alarmMapper.changeAlarmStatus(dto);
	}
	
	@Override
	public void refuseReqAlarmStatus(AlarmChangeDTO dto) {
		// TODO Auto-generated method stub
		alarmMapper.refuseReqAlarmStatus(dto);
	}
	@Override
	public void updateIsread(List<AlarmListDTO> list) {
		// TODO Auto-generated method stub
		alarmMapper.updateIsread(list);
	}
	@Override
	public int alarmCount(int idNum) {
		// TODO Auto-generated method stub
		return alarmMapper.alarmCount(idNum);
	}
	
	
}
