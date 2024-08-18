package com.sist.repository.member;

import com.sist.vo.TokenStoreVO;

public interface JwtStoreRepository {
	public int	findRefresh(String refresh);
	
	// 해당 토큰을 데이터베이스에서 찾아서 삭제해줌
	public	void deleteRefresh(String refresh);
		// 리프레시 토근정보를 저장함
	public	void save(TokenStoreVO vo);
	
	//회원 고유아이디 번호 리턴
	public int findId_num(String email);
}
