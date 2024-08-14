package com.sist.vo;

import java.sql.Date;

import lombok.Data;
@Data
public class PostVO {
private int post_num;
private int id_num;
private String content;
private Date create_date;
private Date edit_date;
private String canReply;
private String onlyMe;

}
