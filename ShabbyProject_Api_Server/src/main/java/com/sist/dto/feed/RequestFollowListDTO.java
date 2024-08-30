package com.sist.dto.feed;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RequestFollowListDTO {
	
	private int rowSize;
	private int page;
	private int startRow;
	private int idNum;
	private String flwType;
}
