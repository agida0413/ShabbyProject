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
	
	
	
	//검색어,행개수, 페이지 기반 현재 본인의 팔로잉 리스트를 가져옴 
	@Override
	public ResponseEntity<ResponseDTO<FollowSearchResultDTO>> followingBykeyword(String keyword,int page,int rowSize) {
		// TODO Auto-generated method stub
		//페이지, 행개수에 대한 validation 
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
		// 리스트 형태 변수에 값 세팅  
		resDto.setFollowList(list);
		
		return new ResponseEntity<ResponseDTO<FollowSearchResultDTO>>
		(new ResponseDTO<FollowSearchResultDTO>(resDto),HttpStatus.OK); //성공 
	}

		//사용자 피드에서 팔로우,팔로워 목록 가져오기 
		@Override
		public ResponseEntity<ResponseDTO<FollowListDTO>> getFollowInFeed(String nickname,String flwType,int page) {
			// TODO Auto-generated method stub
			
			//고유번호를 가져옴 
			int idNum=SimpleCodeGet.getIdNum();
			//현재 로그인한 유저의 닉네임 
			String myNickname= SimpleCodeGet.getNickname();
			
				//validation
				if(!PathVariableValidation.pageValidation(page)) {
					throw new BadRequestException("유효하지 않은 입력입니다.");
				}
				if(!"FOLLOWING".equals(flwType) && !"FOLLOWER".equals(flwType)) {
					throw new BadRequestException("유효하지 않은 입력입니다.");
				}
				if(!PathVariableValidation.nickNameValService(nickname)) {
					throw new BadRequestException("유효하지 않은 입력입니다.");
				}
				//행의 개수 
				int rowSize=10;
				
				//offset(시작위치)
				int offSet=SimpleCodeGet.getOffset(rowSize, page);
				
				//데이터베이스 전송객체 생성 
				FollowInFeedDTO dto=new FollowInFeedDTO();
				//닉네임
				dto.setNickname(nickname);
				//팔로우 or 팔로워
				dto.setFlwType(flwType);
				//행 개수 
				dto.setRowSize(rowSize);
				//offset
				dto.setStartRow(offSet);
				//고유 번호 
				dto.setIdNum(idNum);
				//FollowInformDTO형태(팔로우,팔로워들의 정보) 리스트로 담기
				List<FollowInformDTO> list = followRepository.getFollowInFeed(dto);
				//전송 객체 생성 
				FollowListDTO resDto = new FollowListDTO();
				//전송객체 내 변수에 리스트 세팅 
				resDto.setFollowList(list);
				//현재 사용자 닉네임도 전송==> 자신은 팔로우,팔로워 할수 없다 . 
				resDto.setMyNickname(myNickname);
				
			return new ResponseEntity<ResponseDTO<FollowListDTO>>
			(new ResponseDTO<FollowListDTO>(resDto),HttpStatus.OK); //성공 
		}

		// 인서트 팔로우(팔로잉)
	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<String>> doFollow(DoFollowDTO dto) {
		// TODO Auto-generated method stub
		String myNickName=SimpleCodeGet.getNickname();
		if(myNickName.equals(dto.getNickname())) {
			throw new BadRequestException("본인을 팔로우 할 수 없습니다.");
		}

		//회원 고유번호 
		int idNum=SimpleCodeGet.getIdNum();
		dto.setIdNum(idNum);
		//dto엔 팔로우할 유저의 정보가 들어있다 ===> 현재 로그인한 유저 기반 팔로우 인서트 진행 
		followRepository.doFollow(dto);
		//반환값으로 generatekey를 통해 생성된 팔로우 고유번호에서 approve 컬럼을 가져온다 .==> 팔로우상태정보
		String changeState= followRepository.afterFollow(dto.getFollowNum());
		
		return new ResponseEntity<ResponseDTO<String>>
		(new ResponseDTO<String>(changeState),HttpStatus.OK); //성공 
	}

	@Override
	public ResponseEntity<ResponseDTO<Void>> unFollow(UnFollowDTO dto) {
		// TODO Auto-generated method stub
		//회원 고유정보 
		int idNum=SimpleCodeGet.getIdNum();
		dto.setIdNum(idNum);
		//현재 로그인 유저기반 전달받은 회원을 언팔로우한다 . = > 레코드 삭제 
		followRepository.unFollow(dto);
		// unfollow상태는 단순 행의개수가 없는것으로 판단이 가능하기 때문에 (팔로우는 비공개,공개 여부에 따라 요청승인대기중인지 팔로우상태인지 나뉜다.)
		//상태값을 클라이언트쪽에서 바꾼다.
		
		return new ResponseEntity<ResponseDTO<Void>>
		(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}
	
	
	

}
