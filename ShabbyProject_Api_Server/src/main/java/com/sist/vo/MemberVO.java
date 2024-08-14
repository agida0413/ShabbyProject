package com.sist.vo;

import java.sql.Date;

import lombok.Data;
@Data
public class MemberVO {
private int id_num;
private String email;
private String nickname;
private String password;
private String name;
private String phone;
private String profile;
private String introduce;
private String role;
private Date create_date;
private Date edit_date;
private String  locked;
}
