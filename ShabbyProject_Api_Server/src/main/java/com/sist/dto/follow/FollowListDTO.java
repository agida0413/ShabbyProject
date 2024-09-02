package com.sist.dto.follow;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class FollowListDTO {
private List<FollowInformDTO> followList;
private String myNickname;
}
