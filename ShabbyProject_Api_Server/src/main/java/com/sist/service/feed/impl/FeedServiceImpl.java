package com.sist.service.feed.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.feed.RequestUserFeedDTO;
import com.sist.dto.feed.ResponsePostListDTO;
import com.sist.dto.feed.ResponseUserFeedDTO;
import com.sist.dto.hobby.HobbyDTO;
import com.sist.dto.hobby.ResponseHobbyDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.repository.feed.FeedRepository;
import com.sist.repository.hobby.HobbyRepository;
import com.sist.service.feed.FeedService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService{
	
	private final HobbyRepository hobbyRepository;
	private final FeedRepository feedRepository;
	
	
	//게시물 리스트를 제외한 사용자피드에서의 피드정보를 불러오는  서비스 
	@Override
	public ResponseEntity<ResponseDTO<ResponseUserFeedDTO>> loadUserFeedInfo(String nickname) {
		// TODO Auto-generated method stub
		
		//데이터베이스 매핑 객체 생성 
		RequestUserFeedDTO reqDTO = new RequestUserFeedDTO();
		//닉네임 세팅 
		reqDTO.setNickname(nickname);
		//클라이언트에게 전송할 객체에 피드관련 정보를 데이터베이스에서 가져온다.
		ResponseUserFeedDTO dto = feedRepository.userFeedInfoFromMember(reqDTO);
		//닉네임에 해당하는 관심사 리스트를 불러온다.
		List<HobbyDTO> list = hobbyRepository.findHobbyByNickname(nickname);
		
		//위에서 생성한 객체에 관심사 리스트를 추가한다.
		dto.setHobbies(list);
		
		//만약 매개변수로 받은 닉네임과 현재 세션정보에 담긴 닉네임과 일치한다면 
		//itsMe 변수를 true로 ( 자신의 피드인지 다른 회원의 피드인지 확인하기 위함) 세팅한다.
		if(dto.getNickname().equals(SimpleCodeGet.getNickname())) {
			dto.setItsMe(true);
		}
		
		
		return new ResponseEntity<ResponseDTO<ResponseUserFeedDTO>>
		(new ResponseDTO<ResponseUserFeedDTO>(dto),HttpStatus.OK); //성공 
	}
	
	// 사용자 피드에서 게시물 정보를 읽어온다 . 
	@Override
	public ResponseEntity<ResponseDTO<List<ResponsePostListDTO>>> loadUserFeedPostList(String nickname,int page) {
		// TODO Auto-generated method stub
		//행의 개수 
		int rowSize=6;
		//offset(시작위치)
		int offSet=SimpleCodeGet.getOffset(rowSize, page);
		//데이터베이스 전송 객체 생성
		RequestUserFeedDTO dto= new RequestUserFeedDTO();
		dto.setNickname(nickname);//닉네임 세팅
		dto.setRowSize(rowSize);//행개수 세팅
		dto.setStartRow(offSet);//offset 세팅 
		
		//닉네임 기반으로 게시물 정보를 읽어서 리스트에 담는다 .
		List<ResponsePostListDTO> list  = feedRepository.userFeedPostList(dto);
		
			
		
		return new ResponseEntity<ResponseDTO<List<ResponsePostListDTO>>>
		(new ResponseDTO<List<ResponsePostListDTO>>(list),HttpStatus.OK); //성공 
	}

}
