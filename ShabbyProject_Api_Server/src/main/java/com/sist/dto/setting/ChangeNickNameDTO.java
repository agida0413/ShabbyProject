package com.sist.dto.setting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangeNickNameDTO {
	
	private String password;
	
	@NotBlank(message = "공백일 수 없습니다.")
    @Pattern(regexp = "^[^\s][^\s]*$", message = "공백으로 시작하거나 공백을 포함할 수 없음")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "닉네임은 영문자, 숫자 _ 만 허용")
    @Size(max = 14, message = "닉네임은 15자리 이상 불가")
	private String nickname;
}
