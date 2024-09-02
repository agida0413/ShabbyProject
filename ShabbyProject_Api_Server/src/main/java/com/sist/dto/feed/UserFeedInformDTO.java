package com.sist.dto.feed;

import java.util.List;
import com.sist.dto.hobby.HobbyDTO;


import lombok.Data;

// 서버 => 클라이언트 에게보낼 피드에 대한 정보 
@Data
public class UserFeedInformDTO {
private String profile;//프로필 사진 
private String nickname;//닉네임 
private String introduce; //자기소개 
private String locked;//비공개, 공개 여부
private int postAmount;//게시글수 
private int followerAmount;//팔로워 수 
private int followingAmount;//팔로잉수 
private List<HobbyDTO> hobbies; //해당 피드의 관심사 릭스트 
private boolean itsMe; // 내피드인지 , 다른유저의 피드인지에 대한 정보 
private int followOKCount; // 상대방 피드에서의 비공개 계정일 시 해당 계정이 세션계정의 팔로우 요청을 승인했는지(OK) 여부를 체크 하기위한 행개수
private int followNOCount;// 상대방 피드에서의 비공개 계정일 시 해당 계정이 세션계정의 팔로우 요청은 하였지만 승인 상태가 NO인 개수
private int followAllCount; // FOLLOW 테이블에서 세션 아이디와 , 조회할 피드의 회원 정보의 팔로우 레코드 개수를 가져온다 . 
private String followState; // 현재 세션 정보와 , 조회할 대상 간의 최종적인 팔로우 상태를 내보내기 위한 변수



}
