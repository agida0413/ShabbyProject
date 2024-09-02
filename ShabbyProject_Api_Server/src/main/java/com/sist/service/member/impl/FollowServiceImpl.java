package com.sist.service.member.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.common.exception.BadRequestException;
import com.sist.common.util.PathVariableValidation;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.follow.DoFollowDTO;
import com.sist.dto.follow.FollowInFeedDTO;
import com.sist.dto.follow.FollowInformDTO;
import com.sist.dto.follow.FollowListDTO;
import com.sist.dto.follow.FollowSearchDTO;
import com.sist.dto.follow.FollowSearchResultDTO;
import com.sist.dto.follow.UnFollowDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.repository.member.FollowRepository;
import com.sist.service.member.FollowService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
	
	
	private final FollowRepository followRepository;
	
	
	
	
	@Override
	public ResponseEntity<ResponseDTO<FollowSearchResultDTO>> followingBykeyword(String keyword,int page,int rowSize) {
		// TODO Auto-generated method stub
		
		if(!PathVariableValidation.pageValidation(page)||!PathVariableValidation.pageValidation(rowSize)) {
			throw new BadRequestException("유효하지 않은 입력입니다.");
		}
		
		//공통모듈에서 offset 계산과 , 현재 securityContextHolder에 담긴  idNum가져옴
		int offSet=SimpleCodeGet.getOffset(rowSize,page);
		int idNum=SimpleCodeGet.getIdNum();
		
		//데이터베이스 전송객체 생성 및 값 세팅 
		FollowSearchDTO dto = new FollowSearchDTO();
		dto.setIdNum(idNum);
		dto.setKeyword(keyword);	
		dto.setRowSize(rowSize);
		dto.setStartRow(offSet);
		
		//MemberDto 리스트 형태로 결과 반환
		List<MemberDTO> list= followRepository.followingByKeyword(dto);
		//클라이언트 전송 객체 생성
		FollowSearchResultDTO resDto= new FollowSearchResultDTO();
		//MemberDto 리스트 형태 변수에 값 세팅  
		resDto.setFollowList(list);
		
		return new ResponseEntity<ResponseDTO<FollowSearchResultDTO>>
		(new ResponseDTO<FollowSearchResultDTO>(resDto),HttpStatus.OK); //성공 
	}

	//사용자 피드에서 팔로우,팔로워 목록 가져오기 
		@Override
		public ResponseEntity<ResponseDTO<FollowListDTO>> getFollowInFeed(String nickname,String flwType,int page) {
			// TODO Auto-generated method stub
			int idNum=SimpleCodeGet.getIdNum();
			String myNickname= SimpleCodeGet.getNickname();
			
			//validation
				if(!PathVariableValidation.pageValidation(page)) {
					throw new BadRequestException("유효하지 않은 입력입니다.");
				}
				if(!"FOLLOWING".equals(flwType) && !"FOLLOWER".equals(flwType)) {
					throw new BadRequestException("유효하지 않은 입력입니다.");
				}
				//행의 개수 
				int rowSize=10;
				
				//offset(시작위치)
				int offSet=SimpleCodeGet.getOffset(rowSize, page);
				
				FollowInFeedDTO dto=new FollowInFeedDTO();
				
				
				dto.setNickname(nickname);
				dto.setFlwType(flwType);
				dto.setRowSize(rowSize);
				dto.setStartRow(offSet);
				dto.setIdNum(idNum);
				
				List<FollowInformDTO> list = followRepository.getFollowInFeed(dto);
				
				FollowListDTO resDto = new FollowListDTO();
				resDto.setFollowList(list);
				resDto.setMyNickname(myNickname);
				
			return new ResponseEntity<ResponseDTO<FollowListDTO>>
			(new ResponseDTO<FollowListDTO>(resDto),HttpStatus.OK); //성공 
		}


	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<String>> doFollow(DoFollowDTO dto) {
		// TODO Auto-generated method stub
		
		int idNum=SimpleCodeGet.getIdNum();
		dto.setIdNum(idNum);
		followRepository.doFollow(dto);
		String changeState= followRepository.afterFollow(dto.getFollowNum());
		
		return new ResponseEntity<ResponseDTO<String>>
		(new ResponseDTO<String>(changeState),HttpStatus.OK); //성공 
	}

	@Override
	public ResponseEntity<ResponseDTO<Void>> unFollow(UnFollowDTO dto) {
		// TODO Auto-generated method stub
		
		int idNum=SimpleCodeGet.getIdNum();
		dto.setIdNum(idNum);
		followRepository.unFollow(dto);
		
		return new ResponseEntity<ResponseDTO<Void>>
		(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}
	
	
	

}
