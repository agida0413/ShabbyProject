package com.sist.dto.member;

import jakarta.validation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.text.SimpleDateFormat;

import lombok.Data;

@Data
public class MemberDTO {
    private int idNum;

    @NotBlank(message = "공백일 수 없습니다.")
    @Pattern(regexp = "^[^\s][^\s]*$", message = "공백으로 시작하거나 공백을 포함할 수 없음")
    @Email(message = "이메일 형식이여야 합니다.")
    private String email;
    
    @NotBlank(message = "공백일 수 없습니다.")
    @Pattern(regexp = "^[^\s][^\s]*$", message = "공백으로 시작하거나 공백을 포함할 수 없음")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "닉네임은 영문자, 숫자 _ 만 허용")
    @Size(max = 14, message = "닉네임은 15자리 이상 불가")
    private String nickname;
    
    @NotBlank(message = "공백일 수 없습니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리")
    @Pattern(regexp = "^[^\s][^\s]*$", message = "공백으로 시작하거나 공백을 포함할 수 없음")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$", message = "적어도 하나문자,숫자,특수문자 포함")
    private String password;

    @NotBlank(message = "공백일 수 없습니다.")
    @Pattern(regexp = "^[^\s][^\s]*$", message = "공백으로 시작하거나 공백을 포함할 수 없음")
    private String name;
    
  
    @Pattern(regexp = "^\\d{11}$", message = "핸드폰번호는 11자리 정수 ")
    private String phone;

    private String profile;
    
    @Size(max = 100, message = "자기소개는 100자리이하야합니다.")
    private String introduce;

    private String role;
    private Date createDate;
    private Date editDate;
    private String locked;
    private String vueCreateDate;
    private String code;

    public String getFormatVueCreateDate() { // 생성일 문자형
        return formatDate(createDate);
    }

    public String getFormatVueEditDate() { // 수정일 문자형
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
