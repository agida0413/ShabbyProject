package com.sist.dto.follow;

import lombok.Data;

@Data
public class FollowInformDTO {
private String nickname;
private String profile;
private String approve;
private String myApprove;
private String locked;
private boolean itsMe;
}
