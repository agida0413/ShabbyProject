package com.sist.dto.setting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDTO {
	@NotBlank(message = "공백일 수 없습니다.")
    @Size(min = 9, max = 20, message = "비밀번호는 9~20자리")
    @Pattern(regexp = "^[^\s][^\s]*$", message = "공백으로 시작하거나 공백을 포함할 수 없음")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$", message = "적어도 하나문자,숫자,특수문자 포함")
	private String password; //패스워드 
	
	@NotBlank(message = "공백일 수 없습니다.")
    @Size(min = 9, max = 20, message = "비밀번호는 9~20자리")
    @Pattern(regexp = "^[^\s][^\s]*$", message = "공백으로 시작하거나 공백을 포함할 수 없음")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$", message = "적어도 하나문자,숫자,특수문자 포함")
	private String newPassword; //새 패스워드
}
