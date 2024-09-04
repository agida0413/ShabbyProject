package com.sist.dto.hobby;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class SearchHobbyListDTO {
	
private List<HobbyDTO> findList; //관심사 dto 를 리스트 형태로 전송
}
