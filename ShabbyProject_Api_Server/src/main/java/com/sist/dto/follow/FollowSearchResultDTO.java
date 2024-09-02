package com.sist.dto.follow;

import java.util.List;

import com.sist.dto.member.MemberDTO;

import lombok.Data;
@Data
public class FollowSearchResultDTO {
private List<MemberDTO> followList;
}
