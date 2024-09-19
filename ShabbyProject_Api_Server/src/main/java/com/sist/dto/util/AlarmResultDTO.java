package com.sist.dto.util;

import java.util.List;

import lombok.Data;
@Data
public class AlarmResultDTO {

private	List<AlarmListDTO> list;
private int totalPage;
}
