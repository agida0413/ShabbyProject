package com.sist.dto.feed;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestUserFeedDTO {


private String nickname;
private int rowSize;
private int startRow;
private boolean itsMe;
private int sessionIdNum;
}
