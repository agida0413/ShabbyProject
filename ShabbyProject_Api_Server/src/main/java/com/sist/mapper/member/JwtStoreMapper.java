package com.sist.mapper.member;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.security.TokenStoreDTO;

@Mapper
public interface JwtStoreMapper {
	
	//매개변수로 받은 refresh토큰이 실제로 데이터베이스에 존재하는지 확인
	public int	findRefresh(String refresh);
	//리프레시 토큰 데이터베이스에서 삭제
	public	void deleteRefresh(String refresh);
	//리프레시 토큰 데이터베이스에 저장 	
	public	void save(TokenStoreDTO dto);

	
}
