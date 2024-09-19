package com.sist.dto.util;

import lombok.Data;

@Data
public class SearchResultMemberDTO {

	private String profile;
	private String nickname;
	private int postCount;
	private int followerAmount;
	private int followingAmount;
	private String hobby;

}
