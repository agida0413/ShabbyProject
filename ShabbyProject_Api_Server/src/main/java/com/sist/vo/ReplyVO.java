package com.sist.vo;

import java.sql.Date;

import lombok.Data;
@Data
public class ReplyVO {
private int reply_num;
private int post_num;
private int root;
private int gId;
private int ordId;
private int depth;
private int id_num;
private String content;
private Date create_date;
private Date edit_date;
private String isDel;
private String isFixed;
private String isAdminDel;


}
