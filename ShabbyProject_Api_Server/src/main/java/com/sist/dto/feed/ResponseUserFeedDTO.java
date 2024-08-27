package com.sist.dto.feed;

import java.util.List;

import com.sist.dto.hobby.HobbyDTO;


import lombok.Data;
@Data
public class ResponseUserFeedDTO {
private String profile;
private String nickname;
private String introduce;
private String locked;
private int postAmount;
private int followerAmount;
private int followingAmount;
private List<HobbyDTO> hobbies;
private boolean itsMe;
}
