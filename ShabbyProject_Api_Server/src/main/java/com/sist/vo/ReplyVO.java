package com.sist.vo;

import java.sql.Date;

import lombok.Data;
@Data
public class ReplyVO {
private int replyNum;
private int postNum;
private int root;
private int gId;
private int ordId;
private int depth;
private int idNum;
private String content;
private Date createDate;
private Date editDate;
private String isDel;
private String isFixed;
private String isAdminDel;


}
