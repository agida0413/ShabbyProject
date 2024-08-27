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
	private final SimpleCodeGet simpleCodeGet;
	
	@Override
	public ResponseEntity<ResponseDTO<ResponseUserFeedDTO>> loadUserFeedInfo(String nickname) {
		// TODO Auto-generated method stub
		
		RequestUserFeedDTO reqDTO = new RequestUserFeedDTO();
		reqDTO.setNickname(nickname);
		ResponseUserFeedDTO dto = feedRepository.userFeedInfoFromMember(reqDTO);
		
		List<HobbyDTO> list = hobbyRepository.findHobbyByNickname(nickname);
		
		dto.setHobbies(list);
		
		
		if(dto.getNickname().equals(simpleCodeGet.getNickname())) {
			dto.setItsMe(true);
		}
		
		return new ResponseEntity<ResponseDTO<ResponseUserFeedDTO>>
		(new ResponseDTO<ResponseUserFeedDTO>(dto),HttpStatus.OK); //성공 
	}
	
	@Override
	public ResponseEntity<ResponseDTO<List<ResponsePostListDTO>>> loadUserFeedPostList(String nickname,int page) {
		// TODO Auto-generated method stub
		
		int rowSize=6;
		
		int offSet=simpleCodeGet.getOffset(rowSize, page);
		
		RequestUserFeedDTO dto= new RequestUserFeedDTO();
		dto.setNickname(nickname);
		dto.setRowSize(rowSize);//행개수 세팅
		dto.setStartRow(offSet);//offset 세팅 
		
		List<ResponsePostListDTO> list  = feedRepository.userFeedPostList(dto);
		
		

		return new ResponseEntity<ResponseDTO<List<ResponsePostListDTO>>>
		(new ResponseDTO<List<ResponsePostListDTO>>(list),HttpStatus.OK); //성공 
	}

}
