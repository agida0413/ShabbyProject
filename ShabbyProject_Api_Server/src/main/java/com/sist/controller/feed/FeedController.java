package com.sist.controller.feed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.feed.DoFollowDTO;
import com.sist.dto.feed.FollowListDTO;
import com.sist.dto.feed.UserFeedInformDTO;
import com.sist.dto.feed.UpdateProfileDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.post.PostListDTO;
import com.sist.dto.member.FollowSearchResultDTO;
import com.sist.service.feed.FeedService;
import com.sist.service.member.FollowService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

	
	private final FeedService feedService;
	private final FollowService followService;
	// 유저피드 접근시 해당유저에 대한 정보를 불러오는 api
	@GetMapping("/userfeed/{nickname}")
	public ResponseEntity<ResponseDTO<UserFeedInformDTO>> loadUserfeedInfo(@PathVariable String nickname){
		
		return feedService.loadUserFeedInfo(nickname);
	}
	
	//유저피드 접근시 해당유저에 대한 정보 외 해당유저의 게시물정보를 불러오는 api
	@GetMapping("/userfeed/{nickname}/{page}")
	public ResponseEntity<ResponseDTO<List<PostListDTO>>> loadUserfeedPostList(@PathVariable String nickname,@PathVariable int page){
	
		return feedService.loadUserFeedPostList(nickname, page);
	}
	
	//유저피드에서의 프로필 사진 변경 api
	//이미지 서비스에서 형식, 용량 검증 
	@PutMapping("/userfeed")
	public ResponseEntity<ResponseDTO<Void>> updateProfileImg(@RequestParam(value = "profileImgFile", required = false) MultipartFile file){
			
		return feedService.updateProfileImg(file);
	}
	//유저피드에서의 자기소개 변경시 원래 자기소개정보를 가져오는 api
	@GetMapping("/introduce")
	public ResponseEntity<ResponseDTO<MemberDTO>> getOriginalIntroduce(){
		
		return feedService.getOriginalIntroduce();
	}
	
	//유저피드에서의 자기소개 변경api
	@PutMapping("/introduce")
	public ResponseEntity<ResponseDTO<Void>> updateIntroduce(@RequestBody @Valid UpdateProfileDTO dto){
		return feedService.updateIntroduce(dto);
	}
	
	@GetMapping("/userfeed/{nickname}/{flwType}/{page}")
	public ResponseEntity<ResponseDTO<List<FollowListDTO>>> getFollowInFeed(@PathVariable String nickname, @PathVariable String flwType, @PathVariable int page){
	
		return feedService.getFollowInFeed(nickname, flwType, page);
	}
	
	@PostMapping("/follow")
	public ResponseEntity<ResponseDTO<Void>> doFollow(@RequestBody DoFollowDTO dto){
		
		return followService.doFollow(dto);
	}
	
}
