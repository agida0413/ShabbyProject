package com.sist.repository.feed.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.dto.feed.RequestUserFeedDTO;
import com.sist.dto.feed.ResponsePostListDTO;
import com.sist.dto.feed.ResponseUserFeedDTO;
import com.sist.mapper.feed.FeedMapper;
import com.sist.repository.feed.FeedRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MybatisFeedRepository implements FeedRepository{
	private final FeedMapper feedMapper;
	
	@Override
	public ResponseUserFeedDTO userFeedInfoFromMember(RequestUserFeedDTO dto) {
		// TODO Auto-generated method stub
		return feedMapper.userFeedInfoFromMember(dto);
	}

	@Override
	public List<ResponsePostListDTO> userFeedPostList(RequestUserFeedDTO dto) {
		// TODO Auto-generated method stub
		return feedMapper.userFeedPostList(dto);
	}

}
