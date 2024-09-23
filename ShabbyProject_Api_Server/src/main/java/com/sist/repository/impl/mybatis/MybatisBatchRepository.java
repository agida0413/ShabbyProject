package com.sist.repository.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.sist.mapper.BatchMapper;
import com.sist.repository.BatchRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Repository
public class MybatisBatchRepository implements BatchRepository{

	private final BatchMapper batchMapper;
	
	@Override
	public void batchEmailAuth() {
		// TODO Auto-generated method stub
		batchMapper.batchEmailAuth();
	}

	@Override
	public void batchTokenExpir() {
		// TODO Auto-generated method stub
	    batchMapper.batchTokenExpir();	
	}

	@Override
	public void batchTokenDupl() {
		// TODO Auto-generated method stub
		batchMapper.batchTokenDupl();	
	}

}
