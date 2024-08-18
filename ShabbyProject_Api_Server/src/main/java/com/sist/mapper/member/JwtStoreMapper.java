package com.sist.mapper.member;

import org.apache.ibatis.annotations.Mapper;

import com.sist.vo.MemberVO;
import com.sist.vo.TokenStoreVO;

@Mapper
public interface JwtStoreMapper {

	public int	findRefresh(String refresh);
	
	public	void deleteRefresh(String refresh);
		
	public	void save(TokenStoreVO vo);
	
	public int findId_num(String email);
}
