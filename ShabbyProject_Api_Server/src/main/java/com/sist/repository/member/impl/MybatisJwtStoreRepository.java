package com.sist.repository.member.impl;

import java.sql.Date;

import org.springframework.stereotype.Repository;

import com.sist.dto.security.TokenStoreDTO;
import com.sist.mapper.member.JwtStoreMapper;
import com.sist.repository.member.JwtStoreRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Repository
public class MybatisJwtStoreRepository implements JwtStoreRepository {
	private final JwtStoreMapper mapper;
	
	//현재 전달받은 토큰을 데이터베이스에서 찾아서 있으면 반환해줌
	public int	findRefresh(String refresh) {
		return mapper.findRefresh(refresh);
	}
	// 해당 토큰을 데이터베이스에서 찾아서 삭제해줌
	public	void deleteRefresh(String refresh) {
		mapper.deleteRefresh(refresh);
	}
		// 리프레시 토근정보를 저장함
	public	void save(TokenStoreDTO dto) {
		mapper.save(dto);
	}
	
	
	

	
	
	
}
