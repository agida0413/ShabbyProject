package com.sist.dto.follow;

import java.util.List;

import com.sist.dto.member.MemberDTO;

import lombok.Data;
@Data
public class FollowSearchResultDTO {
	
private List<MemberDTO> followList;//멤버 dto에 팔로우/팔로워 목록 리스트 형태로 전송
}
