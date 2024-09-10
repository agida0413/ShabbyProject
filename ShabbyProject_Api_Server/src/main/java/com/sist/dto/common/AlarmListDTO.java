package com.sist.dto.common;

import lombok.Data;


@Data
public class AlarmListDTO {
private String receiver;
private String sender;
private String senderProfile;
private int postNum;
private String alarmType;
private String createDate;

}
