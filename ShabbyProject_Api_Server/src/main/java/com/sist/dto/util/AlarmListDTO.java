package com.sist.dto.util;

import lombok.Data;


@Data
public class AlarmListDTO {
private int alarmNum;
private String receiver;
private String sender;
private String senderProfile;
private int postNum;
private String alarmType;
private String createDate;
private int isread;

}
