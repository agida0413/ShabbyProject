package com.sist.service.primary.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.common.util.PathVariableValidation;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.feed.GetUserFeedInformDTO;
import com.sist.dto.feed.UserFeedInformDTO;
import com.sist.dto.follow.FollowInFeedDTO;
import com.sist.dto.follow.FollowListDTO;
import com.sist.dto.follow.FollowSearchDTO;
import com.sist.dto.feed.UpdateProfileDTO;
import com.sist.dto.hobby.HobbyDTO;
import com.sist.dto.hobby.SearchHobbyListDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.post.DoPostLikeDTO;
import com.sist.dto.post.PostListDTO;
import com.sist.dto.util.AlarmDTO;
import com.sist.repository.FeedRepository;
import com.sist.repository.FollowRepository;
import com.sist.repository.HobbyRepository;
import com.sist.repository.MemberAccountRepository;
import com.sist.repository.PostRepository;
import com.sist.service.primary.FeedService;
import com.sist.service.util.ImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService{
	
	private final HobbyRepository hobbyRepository;
	private final FeedRepository feedRepository;
	private final PostRepository postRepository;
	private final ImageService imageService;
	private final MemberAccountRepository memberAccountRepository;
	
	
	
	//게시물 리스트를 제외한 사용자 피드에서의 피드정보를 불러오는  서비스 
	@Override
	public ResponseEntity<ResponseDTO<UserFeedInformDTO>> loadUserFeedInfo(String nickname) {
		// TODO Auto-generated method stub
		//정지 사용자 피드
		String forbiddenCheck= memberAccountRepository.getLock(nickname);
		if(forbiddenCheck.equals("FORBIDDEN")) {
			throw new BadRequestException("정지된 사용자입니다.");
		}
		
		//데이터베이스 매핑 객체 생성 
		GetUserFeedInformDTO reqDTO = new GetUserFeedInformDTO();
		//닉네임 세팅 
		reqDTO.setNickname(nickname);
		//만약 현재 스레드에서의 세션 닉네임과 , 요청 닉네임이 같을경우 이 요청은 자신의 피드(내피드) 요청이다.
		SimpleCodeGet.getNickname();
		if(nickname.equals(SimpleCodeGet.getNickname())) {
			//내 피드인지에 대한 정보 TRUE
			reqDTO.setItsMe(true);
		
			//내피드가 아니라 다른유저의 피드를 요청한 것이라면 
		}else {
			//나(현재 세션유저) 와 조회할 유저의 팔로우 상관관계를 조회해야한다.
			//팔로우 유무, 비공개 유무 , 등 다양한 정보에 따라 프론트 영역에서 보여질 정보가 달라진다.
			
			//현재 세션 유저의 고유 IDNUM을 갖고온다.
			int idNum=SimpleCodeGet.getIdNum();
			//데이터베이스에 전송할 객체의 idnum 에 값을 세팅한다.
			reqDTO.setSessionIdNum(idNum);
		}
		
		//클라이언트에게 전송할 객체에 데이터베이스에서 조회한 값을 넣는다.
		UserFeedInformDTO dto = feedRepository.userFeedInfoFromMember(reqDTO);
	
		//만약 매개변수로 받은 닉네임과 현재 세션정보에 담긴 닉네임과 일치한다면 
		//itsMe 변수를 true로 ( 자신의 피드인지 다른 회원의 피드인지 확인하기 위함) 세팅한다. ==== > 여기서 세팅은 클라이언트에게 보낼객체에 대한 세팅이다.
		if(nickname.equals(SimpleCodeGet.getNickname())) {
			//클라이언트에게 보낼 객체에 자신의 피드임을 세팅
			dto.setItsMe(true);
		
			
		}//자신이 아닌 다른유저의 피드를 조회하는 경우
		else {
			
			//해당계정이 비공개 계정인 경우
			if(dto.getLocked().equals("LOCKED")) {
				
				// 현재 세션 유저와 , 조회할 대상의 유저의 상관관계에서 
				//FOLLOW 테이블 컬럼명 approve(요청승인상태)의 값이 FOLLOWOK 인경우 
				if(dto.getFollowOKCount()>0) {
					//팔로우 요청승인 상태(팔로우상태)
					dto.setFollowState("alreadyFollow");
				}
				// 현재 세션 유저와 , 조회할 대상의 유저의 상관관계에서 
				//FOLLOW 테이블 컬럼명 approve(요청승인상태)의 값이 FOLLOWNO 인경우 
				else if (dto.getFollowNOCount()>0) {
					//팔로우 요청 대기상태(공개계정의 FOLLOW 테이블 C 작업의 경우 디폴트가 FOLLOWOK이기때문에 FOLLOWNO인 상태면 무조건 비공개 계정에서의 팔로우 요청승인대기 상태이다)
					dto.setFollowState("alreadyRequest");
				}
				//현재 세션유저와 , 조회할 대상의 유정의 상관관계가 어떠한 팔로우관계도 일어나지않은경우
				else if (dto.getFollowAllCount()==0) {
					//팔로우 하지않은 상태
					dto.setFollowState("isNotFollow");
				}		
				
			}
			//해당계정이 공개계정인 경우 
			else {
				
				//팔로우 상태인경우 
				if(dto.getFollowOKCount()>0) {
					dto.setFollowState("alreadyFollow");
				}
				//팔로우 상태가 아닌경우 
				else if (dto.getFollowAllCount()==0) {
					dto.setFollowState("isNotFollow");
				}
				
				//공개 계정은  FOLLOWNO 상태가 없다.
			}
			
			
		}
		
		
		//닉네임에 해당하는 관심사 리스트를 불러온다.
		List<HobbyDTO> list = hobbyRepository.findHobbyByNickname(nickname);
		
		//위에서 생성한 객체에 관심사 리스트를 추가한다.
		dto.setHobbies(list);
					
		
		return new ResponseEntity<ResponseDTO<UserFeedInformDTO>>
		(new ResponseDTO<UserFeedInformDTO>(dto),HttpStatus.OK); //성공 
	}
	
	// 사용자 피드에서 게시물 정보를 읽어온다 . 
	@Override
	public ResponseEntity<ResponseDTO<List<PostListDTO>>> loadUserFeedPostList(String type,String nickname,int page) {
		// TODO Auto-generated method stub
		
			
		//행의 개수 
		int rowSize=6;
		//offset(시작위치)
		int offSet=SimpleCodeGet.getOffset(rowSize, page);
		//데이터베이스 전송 객체 생성
		GetUserFeedInformDTO dto= new GetUserFeedInformDTO();
		//내 피드이면 , itsMe true == > 나만 보기게시물을 볼수 있게 
		if(nickname.equals(SimpleCodeGet.getNickname())) {
			dto.setItsMe(true);
		}
		
		//읽고자하는 피드가 유저 피드인지 세팅한다 ===> 동적쿼리를 통해 유저피드 ,메인피드, 글로벌 피드 나누기위함 		
		dto.setFeedState("USERFEED");
		
		dto.setType(type);
		dto.setNickname(nickname);//닉네임 세팅
		dto.setRowSize(rowSize);//행개수 세팅
		dto.setStartRow(offSet);//offset 세팅 
	
		//닉네임 기반으로 게시물 정보를 읽어서 리스트에 담는다 .
		List<PostListDTO> list  = postRepository.postList(dto);
		
		
			
		
		return new ResponseEntity<ResponseDTO<List<PostListDTO>>>
		(new ResponseDTO<List<PostListDTO>>(list),HttpStatus.OK); //성공 
	}
	
	
	//메인피드 게시물 리스트 
	@Override
	public ResponseEntity<ResponseDTO<List<PostListDTO>>> loadMainFeedPostList(int page) {
		// TODO Auto-generated method stub
		
		
		int idNum=SimpleCodeGet.getIdNum();
		
		//행의 개수 
		int rowSize=6;
		//offset(시작위치)
		int offSet=SimpleCodeGet.getOffset(rowSize, page);
		//데이터베이스 전송 객체 생성
		GetUserFeedInformDTO dto= new GetUserFeedInformDTO();
		//메인피드 동적쿼리위해 전달
		dto.setFeedState("MAINFEED");
		dto.setIdNum(idNum);//아이디 고유번호 세팅
		dto.setRowSize(rowSize);//행개수 세팅
		dto.setStartRow(offSet);//offset 세팅 
		
		//닉네임 기반으로 게시물 정보를 읽어서 리스트에 담는다 .
		List<PostListDTO> list  = postRepository.postList(dto);
		
		return new ResponseEntity<ResponseDTO<List<PostListDTO>>>
		(new ResponseDTO<List<PostListDTO>>(list),HttpStatus.OK); //성공 
	}
	
	//글로벌 피드 게시물 리스트 
	@Override
	public ResponseEntity<ResponseDTO<List<PostListDTO>>> loadGlobalFeedPostList(int page) {
	// TODO Auto-generated method stub
		
		
		int idNum=SimpleCodeGet.getIdNum();
		
		//행의 개수 
		int rowSize=6;
		//offset(시작위치)
		int offSet=SimpleCodeGet.getOffset(rowSize, page);
		//데이터베이스 전송 객체 생성
		GetUserFeedInformDTO dto= new GetUserFeedInformDTO();
		//글로벌 피드 동적쿼리 위해 전달
		dto.setFeedState("GLOBALFEED");
		dto.setIdNum(idNum);//아이디 고유번호 세팅
		dto.setRowSize(rowSize);//행개수 세팅
		dto.setStartRow(offSet);//offset 세팅 
		
		//닉네임 기반으로 게시물 정보를 읽어서 리스트에 담는다 .
		List<PostListDTO> list  = postRepository.postList(dto);
		
		return new ResponseEntity<ResponseDTO<List<PostListDTO>>>
		(new ResponseDTO<List<PostListDTO>>(list),HttpStatus.OK); //성공 
	}
	
	//검색 피드 게시물 리스트 
		@Override
		public ResponseEntity<ResponseDTO<List<PostListDTO>>> loadSearchFeedPostList(String keyword,int page) {
		// TODO Auto-generated method stub
			
						
			int idNum=SimpleCodeGet.getIdNum();
			
			//행의 개수 
			int rowSize=6;
			//offset(시작위치)
			int offSet=SimpleCodeGet.getOffset(rowSize, page);
			//데이터베이스 전송 객체 생성
			GetUserFeedInformDTO dto= new GetUserFeedInformDTO();
			//글로벌 피드 동적쿼리 위해 전달
			dto.setFeedState("SEARCHFEED");
			dto.setIdNum(idNum);//아이디 고유번호 세팅
			dto.setRowSize(rowSize);//행개수 세팅
			dto.setStartRow(offSet);//offset 세팅 
			dto.setKeyword(keyword);//키워드 세팅
			//닉네임 기반으로 게시물 정보를 읽어서 리스트에 담는다 .
			List<PostListDTO> list  = postRepository.postList(dto);
			
			return new ResponseEntity<ResponseDTO<List<PostListDTO>>>
			(new ResponseDTO<List<PostListDTO>>(list),HttpStatus.OK); //성공 
		}
		
	
	//프로필 이미지 변경 
	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<Void>> updateProfileImg(MultipartFile file) {
		// TODO Auto-generated method stub
		
		//현재 세션 회원고유번호 갖고오기
		int idNum=SimpleCodeGet.getIdNum();
		//데이터베이스 전송객체
		UpdateProfileDTO dto= new UpdateProfileDTO();
		//파일 세팅
		dto.setProfileImgFile(file);
			
		//기존 프로필 이미지 url을 데이터베이스에서 가져옴
		String originalImg= feedRepository.profileImgGet(idNum);
		
		//s3업로드 로직 시작 
		
		//만약 기존 이미지가 존재한다면 
		if(originalImg!=null) {
			//s3에서 삭제
			imageService.deleteImage(originalImg);
		}
		
		//클라이언트로 받은 파일이 존재한다면 
		if(dto.getProfileImgFile()!=null) {
			//s3 이미지 업로드 url 반환 
			String profile=	imageService.upload(dto.getProfileImgFile());
			//dto에 반환받은 url 세팅
			dto.setProfile(profile);
		}
		//회원 고유번호 세팅 		
		dto.setIdNum(idNum);
		
		
		//만약 파일이 null 이면 null인상태로 저장 ==> 기본이미지 
		//데이터베이스 변경로직 시작 
		try {
			
			//프로필 이미지 url 데이터베이스 업데이트 시도 
			feedRepository.profileImgUpdate(dto);
		} catch (Exception e) {
			//에러 발생시
			// TODO: handle exception
			//만약 null인상태 (기본이미지로 변경하려는 상태) 가 아니라면 
			if(dto.getProfile()!=null) {
				//위에서 s3에 업로드한 이미지 재삭제
				imageService.deleteImage(dto.getProfile());
			}
			
			//프로필을 기본이미지로 세팅
			dto.setProfile(null);
			
			try {
				//프로필 null로 업데이트 시도
			
				feedRepository.profileImgUpdate(dto);
			} catch (Exception e2) {
				// TODO: handle exception
				//에러시 예외 서버내부오류 던짐 
			
				throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.", "프로필 사진변경중 오류발생하여 롤백 후 기본이미지 변경중 오류발생");
			}
			//롤백후 기본이미지로 변경 성공
			throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.", "프로필 사진변경중 오류발생/ 기본이미지로 업데이트 ");
		}
		
		
		
		return new ResponseEntity<ResponseDTO<Void>>
		(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}
	
	
	//자기소개 변경 서비스 
	@Override
	public ResponseEntity<ResponseDTO<Void>> updateIntroduce(UpdateProfileDTO dto) {
		// TODO Auto-generated method stub
		
		//회원고유번호 갖고오기 
		int idNum=SimpleCodeGet.getIdNum();
		//값 세팅 
		dto.setIdNum(idNum);
		//자기소개 데이터베이스 변경 
		feedRepository.introduceUpdate(dto);
		

		return new ResponseEntity<ResponseDTO<Void>>
		(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}

	
	//자기소개 변경 시 해당 컴포넌트에서 디폴트값으로 현재 자기소개를 보여주기 위한 서비스 
	@Override
	public ResponseEntity<ResponseDTO<MemberDTO>> getOriginalIntroduce() {
		// TODO Auto-generated method stub
		//회원고유번호
		int idNum= SimpleCodeGet.getIdNum();
		
		//현재 자기소개 갖고오기
		MemberDTO dto=memberAccountRepository.introduceByUserIdNum(idNum);
		
		return new ResponseEntity<ResponseDTO<MemberDTO>>
		(new ResponseDTO<MemberDTO>(dto),HttpStatus.OK); //성공 
	}

	
	

	
	
	

}
