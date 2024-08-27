package com.sist.repository.feed;

import java.util.List;

import com.sist.dto.feed.RequestUserFeedDTO;
import com.sist.dto.feed.ResponsePostListDTO;
import com.sist.dto.feed.ResponseUserFeedDTO;

public interface FeedRepository {
	public ResponseUserFeedDTO userFeedInfoFromMember(RequestUserFeedDTO dto);
	public List<ResponsePostListDTO> userFeedPostList(RequestUserFeedDTO dto);
}
