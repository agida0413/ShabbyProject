package com.sist.dto.hobby;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class ResponseHobby {
private List<HobbyDTO> findList=new ArrayList<HobbyDTO>();
}
