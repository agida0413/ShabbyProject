package com.sist.repository.hobby.impl;

import org.springframework.stereotype.Repository;

import com.sist.dto.hobby.HobbyDTO;
import com.sist.dto.hobby.SearchHobbyDTO;
import com.sist.mapper.hobby.hobbyMapper;
import com.sist.repository.hobby.HobbyRepository;
import java.util.*;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class MybatisHobbyRepository implements HobbyRepository{
	
	//전체 관심사 조회 
	private final hobbyMapper hobbyMapper;
	@Override
	public HobbyDTO allHobby() {
		// TODO Auto-generated method stub
		return hobbyMapper.allHobby();
	}

	//특정 관심사 조회
	@Override
	public List<HobbyDTO> findHobby(SearchHobbyDTO dto) {
		// TODO Auto-generated method stub
		return hobbyMapper.findHobby(dto);
	}
	//닉네임 기반 관심사 리스트
	@Override
	public List<HobbyDTO> findHobbyByNickname(String nickname) {
		// TODO Auto-generated method stub
		return hobbyMapper.findHobbyByNickname(nickname);
	}

}
