package com.sist.dto.common;

import java.util.List;

import lombok.Data;
@Data
public class SearchResultMemberListDTO {

	private List<SearchResultMemberDTO> list;
	private int totalPage;
}
