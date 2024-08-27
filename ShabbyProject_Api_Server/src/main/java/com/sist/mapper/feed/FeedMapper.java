package com.sist.mapper.feed;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.feed.RequestUserFeedDTO;
import com.sist.dto.feed.ResponsePostListDTO;
import com.sist.dto.feed.ResponseUserFeedDTO;

@Mapper
public interface FeedMapper {

	public ResponseUserFeedDTO userFeedInfoFromMember(RequestUserFeedDTO dto);
	public List<ResponsePostListDTO> userFeedPostList(RequestUserFeedDTO dto);
	
}
