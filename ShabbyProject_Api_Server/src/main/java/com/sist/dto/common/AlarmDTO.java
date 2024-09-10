package com.sist.dto.common;

import java.util.List;

import lombok.Data;

@Data
public class AlarmDTO {
private int alarmNum; //알람 고유번호 
private int sender; //보내는 사람 
private int postNum;//게시물 번호 
private List<String> receivers; //받는 사람  ---> 좋아요를 제외한 알람 작업시
private String alarmType; //알람 유형 
private String createDate; //생성일 
private boolean isread; //읽음 여부

private int receiver;//받는사람 ---> 좋아요 에서 작업시
private boolean isUpdate;//게시물 업데이트 임을 동적쿼리에게 알리기위함

}
