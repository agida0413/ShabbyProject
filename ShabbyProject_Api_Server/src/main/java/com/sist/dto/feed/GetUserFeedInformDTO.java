package com.sist.dto.feed;


import lombok.Data;

@Data
public class GetUserFeedInformDTO {


private String nickname;
private int rowSize;
private int startRow;
private boolean itsMe;
private String feedState;//글로벌 피드 or 메인피드
private int sessionIdNum;
}
