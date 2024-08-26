package com.sist.service.post.impl;

import java.util.ArrayList;
import java.util.List;


import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.post.RequestPostDTO;
import com.sist.repository.post.PostRepository;
import com.sist.service.image.ImageService;
import com.sist.service.post.PostService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final SimpleCodeGet simpleCodeGet;
	private final ImageService imageService;
	
	
	
	
	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<Void>> postInsertTransaction(RequestPostDTO dto) {
		// TODO Auto-generated method stub
		
		// s3 업로드 시작= ==============================================
		
		List<MultipartFile> imgList = dto.getImgList();
		List<String>imgUrList= new ArrayList<String>();
		for (MultipartFile image : imgList) {
			try {
			String imgurl=	imageService.upload(image);
				imgUrList.add(imgurl);
			} catch (Exception e) {
				// TODO: handle exception
				throw new BadRequestException("사진 업로드중 서버오류가 발생하였습니다.");
			}
			
		}
		
		
		// s3 업로드 끝 ====================
		
		//데이터베이스 저장 
		try {
			//현재세션 아이디값
			int idNum=simpleCodeGet.getIdNum();
			//아이디 값 세팅 
			dto.setIdNum(idNum);
			
			//성공시 일단 체크박스 값을 읽어 게시물 상태 세팅(나만보기, 댓글 기능해제여부)
			if(dto.isCanReply()) {
				dto.setCanReplyState("NOREPLY");
			}else {
				dto.setCanReplyState("USEREPLY");
			}
			
			if(dto.isOnlyMe()) {
				dto.setOnlyMeState("ONLYME");
			}else {
				dto.setOnlyMeState("NOTONLYME");
			}
			
			//게시물 내용 공백일시 널로 설정
			if(dto.getContent().equals("")) {
				dto.setContent(null);
			}
			
			//게시물 테이블 인서트 
			postRepository.postInsert(dto);
			//게시물관련 관심사 테이블 인서트 /동적쿼리로 인서트 ==> postNum은 게시물 테이블 인서트와 동시에 generatedkey로 받았다.
			if(dto.getHobbyList().size()!=0 && dto.getHobbyList()!=null) {
				postRepository.hobbyInsert(dto);
			}
			if(dto.getFollowTagList().size()!=0&&dto.getHobbyList()!=null) {
				//게시물관련 인물태그 인서트 /동적쿼리 
				postRepository.followTagInsert(dto);
			}
			
			
			//s3로 반환받은 url리스트 세팅 로직 ....
			
			dto.setImgUrlList(imgUrList);
			//url리스트를 세팅한후 게시물 사진 테이블 인서트
			postRepository.postImgInsert(dto);
			
		} catch (Exception e) {
			// TODO: handle exception
			//에러 발생시 s3 사진업로드 된것 삭제 
		
			try {
				for (String imgUrl : imgUrList) {
				imageService.deleteImage(imgUrl);
			}
				
			} catch (Exception e2) {
				// TODO: handle exception
				throw new InternerException("사진 업로드 중 서버 내부오류가 발생했습니다.");
			}
			//다시 던져서 트랜잭션 롤백
			throw  new InternerException("서버 내부오류입니다.");
		}
		
		//s3 업로드 실패시 모든 로직 수행 x 
		
		return new ResponseEntity<ResponseDTO<Void>>
		(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}

}
