package com.sist.dto.post;

import java.sql.Date;

import lombok.Data;
import lombok.Getter;
@Data
public class PostDTO {
private int postNum;
private int idNum;
private String content;
private Date createDate;
private Date editDate;
private String canReply;
private String onlyMe;

}
