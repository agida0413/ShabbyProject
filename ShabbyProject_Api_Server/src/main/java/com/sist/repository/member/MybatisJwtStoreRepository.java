package com.sist.repository.member;

import java.sql.Date;

import org.springframework.stereotype.Repository;

import com.sist.mapper.member.jwtStoreMapper;
import com.sist.vo.MemberVO;
import com.sist.vo.TokenStoreVO;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Repository
public class MybatisJwtStoreRepository implements JwtStoreRepository {
	private final jwtStoreMapper mapper;
	
	//현재 전달받은 토큰을 데이터베이스에서 찾아서 있으면 반환해줌
	public int	findRefresh(String refresh) {
		return mapper.findRefresh(refresh);
	}
	// 해당 토큰을 데이터베이스에서 찾아서 삭제해줌
	public	void deleteRefresh(String refresh) {
		mapper.deleteRefresh(refresh);
	}
		// 리프레시 토근정보를 저장함
	public	void save(TokenStoreVO vo) {
		mapper.save(vo);
	}
	
	//회원 고유아이디 번호 리턴
	public int findId_num(String email){
	return	mapper.findId_num(email);
	}
	

	
	
	
}
