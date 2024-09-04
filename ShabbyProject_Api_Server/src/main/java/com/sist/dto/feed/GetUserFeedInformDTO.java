package com.sist.dto.feed;


import lombok.Data;

@Data
public class GetUserFeedInformDTO {

private int idNum;//고유번호
private String nickname;//닉네임 
private int rowSize;//행개수 
private int startRow;//시작행
private boolean itsMe;//본인 확인 여부
private String feedState;//글로벌 피드 or 메인피드
private int sessionIdNum;//현재 로그인한 회원의 고유번호 
private String type; //유저피드의 내 게시물(일반)인지  해당 유저가 태그당한 게시물인지
}
