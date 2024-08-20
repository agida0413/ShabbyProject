package com.sist.repository.member;

import com.sist.dto.security.TokenStoreDTO;


public interface JwtStoreRepository {
	//클라이언트로 받은 refresh 토큰이 진짜 인지 검증하기 위해  데이터베이스의 row 값 리턴 
	public int	findRefresh(String refresh);
	
	// 해당 토큰을 데이터베이스에서 찾아서 삭제해줌
	public	void deleteRefresh(String refresh);
		// 리프레시 토근정보를 저장함
	public	void save(TokenStoreDTO dto);
	
	
}
