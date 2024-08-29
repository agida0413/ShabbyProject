package com.sist.controller.feed;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.feed.ResponsePostListDTO;
import com.sist.dto.feed.ResponseUserFeedDTO;
import com.sist.dto.feed.UpdateProfileDTO;
import com.sist.dto.member.ResponseFollowDTO;
import com.sist.service.feed.FeedService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

	
	private final FeedService feedService;
	@GetMapping("/userfeed/{nickname}")
	public ResponseEntity<ResponseDTO<ResponseUserFeedDTO>> loadUserfeedInfo(@PathVariable String nickname){
		
		return feedService.loadUserFeedInfo(nickname);
	}
	
	
	@GetMapping("/userfeed/{nickname}/{page}")
	public ResponseEntity<ResponseDTO<List<ResponsePostListDTO>>> loadUserfeedPostList(@PathVariable String nickname,@PathVariable int page){
	
		return feedService.loadUserFeedPostList(nickname, page);
	}
	
	@PutMapping("/userfeed")
	public ResponseEntity<ResponseDTO<Void>> updateProfileImg(  @RequestParam(value = "profileImgFile", required = false) MultipartFile file){
		
		UpdateProfileDTO dto = new UpdateProfileDTO();
		dto.setProfileImgFile(file);
		return feedService.updateProfileImg(dto);
	}
}
