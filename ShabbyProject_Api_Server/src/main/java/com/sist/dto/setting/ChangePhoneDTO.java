package com.sist.dto.setting;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePhoneDTO {
	
@Pattern(regexp = "^\\d{11}$", message = "핸드폰번호는 11자리 정수 ")	
private String phone;
private String password;
}
