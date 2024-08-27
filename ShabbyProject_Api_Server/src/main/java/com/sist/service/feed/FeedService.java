package com.sist.service.feed;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sist.dto.api.ResponseDTO;

import com.sist.dto.feed.ResponsePostListDTO;
import com.sist.dto.feed.ResponseUserFeedDTO;

public interface FeedService {

	public ResponseEntity<ResponseDTO<ResponseUserFeedDTO>> loadUserFeedInfo(String nickname);
	public ResponseEntity<ResponseDTO<List<ResponsePostListDTO>>> loadUserFeedPostList(String nickname,int page);
}
