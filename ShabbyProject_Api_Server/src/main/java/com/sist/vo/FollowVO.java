package com.sist.vo;

import java.sql.Date;

import lombok.Data;
@Data
public class FollowVO {
private int follow_num;
private int id_num;
private int following_id;
private Date follow_date;
}
