package com.sist.vo;

import java.sql.Date;

import lombok.Data;
@Data
public class MemberVO {
private int idNum;
private String email;
private String nickname;
private String password;
private String name;
private String phone;
private String profile;
private String introduce;
private String role;
private Date createDate;
private Date editDate;
private String  locked;
}
