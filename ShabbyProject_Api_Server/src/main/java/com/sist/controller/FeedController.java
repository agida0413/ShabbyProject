package com.sist.controller;

import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.common.util.PathVariableValidation;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.feed.UserFeedInformDTO;
import com.sist.dto.follow.DoFollowDTO;
import com.sist.dto.follow.FollowInformDTO;
import com.sist.dto.follow.FollowListDTO;
import com.sist.dto.follow.FollowSearchResultDTO;
import com.sist.dto.follow.UnFollowDTO;
import com.sist.dto.feed.UpdateProfileDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.post.PostListDTO;
import com.sist.service.primary.FeedService;
import com.sist.service.primary.FollowService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

	//유저피드 관련 API 시작 
	private final FeedService feedService;
	private final FollowService followService;
	// 유저피드 접근시 해당유저에 대한 정보를 불러오는 api
	@GetMapping("/userfeed/{nickname}")
	public ResponseEntity<ResponseDTO<UserFeedInformDTO>> loadUserfeedInfo(@PathVariable String nickname){
		//닉네임 validation
		if (!PathVariableValidation.nickNameValService(nickname)) {

			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}

		return feedService.loadUserFeedInfo(nickname);
	}
	
	//유저피드 접근시 해당유저에 대한 정보 외 해당유저의 게시물정보를 불러오는 api
	@GetMapping("/userfeed/post/{type}/{nickname}/{page}")
	public ResponseEntity<ResponseDTO<List<PostListDTO>>> loadUserfeedPostList(@PathVariable String type, @PathVariable String nickname,@PathVariable int page){
		
		if(!PathVariableValidation.nickNameValService(nickname)||!PathVariableValidation.pageValidation(page)||!(type.equals("NORMAL")||type.equals("TAGGED"))) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		return feedService.loadUserFeedPostList(type, nickname, page);
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
	
	//유저피드에서 팔로우,팔로잉 목록을 불러온다   /해당피드의 닉네임 / 팔로우인지 팔로워인지 / 페이지
	@GetMapping("/userfeed/{nickname}/{flwType}/{page}")
	public ResponseEntity<ResponseDTO<FollowListDTO>> getFollowInFeed(@PathVariable String nickname, @PathVariable String flwType, @PathVariable int page){
		//validation
		if(!PathVariableValidation.pageValidation(page)||(!"FOLLOWING".equals(flwType) && !"FOLLOWER".equals(flwType))||!PathVariableValidation.nickNameValService(nickname)) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		
		return followService.getFollowInFeed(nickname, flwType, page);
	}
	//팔로우 인서트 (팔로잉 작업) ==> 비공개 계정 or 공개 계정에 따라 다른작업 
	@PostMapping("/follow")
	public ResponseEntity<ResponseDTO<String>> doFollow(@RequestBody @Valid DoFollowDTO dto){
		
		return followService.doFollow(dto);
	}
	//팔로우 delete (언팔로우 작업) == > 비공개,공개여부 상관없이 레코드 삭제 작업 
	@DeleteMapping("/follow")
	public ResponseEntity<ResponseDTO<Void>> unFollow(@RequestBody @Valid UnFollowDTO dto){
		return followService.unFollow(dto);
	}
	
	//유저피드에서의 자기소개 변경api
	@PutMapping("/introduce")
	public ResponseEntity<ResponseDTO<Void>> updateIntroduce(@RequestBody @Valid UpdateProfileDTO dto) {
		return feedService.updateIntroduce(dto);
	}
	
	//유저피드 관련 API 종료 
	
	
	//메인피드 관련 API 시작
	
	@GetMapping("/mainfeed/{page}")
	public ResponseEntity<ResponseDTO<List<PostListDTO>>> loadMainFeedPostList(@PathVariable int page){
		//validation
		if (!PathVariableValidation.pageValidation(page)) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		
		return feedService.loadMainFeedPostList(page);
	}
	
	//메인피드 관련 종료
	
	//글로벌피드 관련시작
	@GetMapping("/globalfeed/{page}")
	public ResponseEntity<ResponseDTO<List<PostListDTO>>> loadGlobalPostList(@PathVariable int page){
		
		// validation
		if (!PathVariableValidation.pageValidation(page)) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		return feedService.loadGlobalFeedPostList(page);
	}
	@GetMapping("/search/{page}")
	public ResponseEntity<ResponseDTO<List<PostListDTO>>> loadSearchPostList(@PathVariable int page,@RequestParam String keyword){
		//validation
		if (!PathVariableValidation.pageValidation(page) || !PathVariableValidation.keyWordValService(keyword)) {
			throw new InternerException("유효하지 않은 입력입니다.","validation 실패");
		}
		return feedService.loadSearchFeedPostList(keyword, page);
	}
	
}
