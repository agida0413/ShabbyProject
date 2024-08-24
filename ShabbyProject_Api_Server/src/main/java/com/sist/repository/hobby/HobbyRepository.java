package com.sist.repository.hobby;

import com.sist.dto.hobby.HobbyDTO;
import com.sist.dto.hobby.RequestHobbyDTO;

import java.util.*;

public interface HobbyRepository {

	//전체 관심사 조회	
	public HobbyDTO allHobby();
	//특정 관심사 조회
	public List<HobbyDTO> findHobby(RequestHobbyDTO dto);
}
