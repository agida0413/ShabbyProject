package com.sist.dto.common;

import java.util.List;

import lombok.Data;
@Data
public class AlarmResultDTO {

private	List<AlarmListDTO> list;
private int totalPage;
}
