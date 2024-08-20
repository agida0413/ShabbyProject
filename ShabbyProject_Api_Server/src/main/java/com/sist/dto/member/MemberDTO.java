package com.sist.dto.member;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.Getter;

@Data
public class MemberDTO {
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





public String getVueCreateDate() { //생성일 문자형
    return formatDate(createDate);
}

public String getVueEditDate() {//수정일  문자형
    return formatDate(editDate);
}

private String formatDate(Date date) {
    if (date == null) {
        return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
}
}
