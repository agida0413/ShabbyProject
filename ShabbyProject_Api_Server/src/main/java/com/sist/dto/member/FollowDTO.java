package com.sist.dto.member;

import java.sql.Date;

import lombok.Data;
import lombok.Getter;
@Data
public class FollowDTO {
private int followNum;
private int idNum;
private int followingId;
private Date followDate;
}
