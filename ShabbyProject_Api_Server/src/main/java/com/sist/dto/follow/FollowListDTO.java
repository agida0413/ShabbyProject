package com.sist.dto.follow;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class FollowListDTO {
	
private List<FollowInformDTO> followList;// 팔로우 정보 dto를 리스트 형태로 담음 
private String myNickname; // 현재 로그인한 회원의 닉네임 
}
