package com.sist.dto.follow;

import lombok.Data;

@Data
public class FollowInformDTO {
private String nickname; //닉네임 
private String profile; //프로필 사진 url 
private String approve; // 해당 유저피드에서 바라본 팔로우 상태 ( 승인된 상태 , 요청중이지만 아직 거절 혹은 승인을 안한상태 , 아무 연관도 없는상태 ) 
private String myApprove;//해당 유저피드에서 바라보지않고 , 나 기준(로그인한 회원기준) 팔로우 연관상태 
private String locked; //비공개 계정인지, 공개 계정인지 
private boolean itsMe; //이 항목이 내 계정인지 = > 팔로우,팔로워 버튼 숨김
private int relateCount;//연관 관심사 개수
}
