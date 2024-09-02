package com.sist.dto.feed;

import lombok.Data;

@Data
public class FollowListDTO {
private String nickname;
private String profile;
private String approve;
private String myApprove;
private String locked;
private boolean itsMe;
}
