package com.sist.service.post.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	//게시물 repository
    private final PostRepository postRepository;
    //공통 함수모듈
    private final SimpleCodeGet simpleCodeGet;
    //이미지업로드 서비스
    private final ImageService imageService;
 // 최대 파일 크기 5MB 변수
    private static final long MAX_FILE_SIZE = 5* 1024 * 1024; 

    //게시물 업로드 서비스 
    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<Void>> postInsertTransaction(RequestPostDTO dto) {
        List<MultipartFile> imgList = dto.getImgList(); // 업로드할 이미지 리스트
        List<String> imgUrList = new ArrayList<>(); // 업로드된 이미지의 URL을 저장할 리스트

        // S3 업로드 시작
        try {
        		// request로 받은 img파일 리스트
            for (MultipartFile image : imgList) {
                validateFileSize(image); // 파일 크기 검증
                String imgUrl = imageService.upload(image); //하나씩 꺼내와서 이미지 업로드==>s3에서 반환받은 url 값 저장
                imgUrList.add(imgUrl); // 업로드된 이미지를 url리스트에 URL 추가
            }
        } 
        catch (BadRequestException e) {
			throw new BadRequestException(e.getMessage());//사진파일용량 검증 에러 캐치
		}
        catch (Exception e) {
            throw new BadRequestException("사진 업로드 중 서버 오류가 발생하였습니다."); // 파일 업로드 오류 처리
        }

        // 데이터베이스 저장
        try {
            int idNum = simpleCodeGet.getIdNum(); // 현재 세션의 ID 가져오기
            dto.setIdNum(idNum); // 게시물 DTO에 ID 설정
            setPostState(dto); // 게시물 상태 설정

            // 게시물 테이블에 데이터 저장
            postRepository.postInsert(dto);

            // 관심사 및 인물 태그 인서트 //postnum+hobby  유니크키 
           insertHobbies(dto);
           insertFollowTags(dto);

            // 게시물 이미지 URL 리스트를 DTO에 설정하고 이미지 테이블에 저장
            dto.setImgUrlList(imgUrList);
            postRepository.postImgInsert(dto);

        } catch (Exception e) {
            removeImageToRollback(imgUrList); // 데이터베이스 오류 발생 시 이미지 삭제 및 롤백
        }

        // 성공적으로 처리된 경우 HTTP 200 OK 응답 반환
        return new ResponseEntity<>(new ResponseDTO<>(), HttpStatus.OK);
    }

    
    
    //파일크기 서버측 검증 (한번더 ) 1차 : 클라이언트 , 2차 : 게시물서비스 
    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
        	
            throw new BadRequestException("사진 파일의 용량이 너무 큽니다."); // 파일 크기 초과 오류 처리
        }
    }

    //게시물의 나만보기, 댓글기능해제 상태를 설정
    private void setPostState(RequestPostDTO dto) {
        dto.setCanReplyState(dto.isCanReply() ? "NOREPLY" : "USEREPLY"); // 댓글 기능 설정
        dto.setOnlyMeState(dto.isOnlyMe() ? "ONLYME" : "NOTONLYME"); // 나만 보기 설정
        if (dto.getContent().isEmpty()) {
            dto.setContent(null); // 게시물 내용이 비어있으면 null로 설정
        }
    }

   
    //게시물과 연관된 관심사테이블에 데이터  삽입 
    private void insertHobbies(RequestPostDTO dto) {
    	  if (dto.getHobbyList() != null && !dto.getHobbyList().isEmpty()) {
              postRepository.hobbyInsert(dto); // 관심사 테이블에 인서트
          }
    }
    //게시물과 연관된 사람태그 테이블에 데이터  삽입 
    private void insertFollowTags(RequestPostDTO dto) {
    	 if (dto.getFollowTagList() != null && !dto.getFollowTagList().isEmpty()) {
             postRepository.followTagInsert(dto); // 인물 태그 테이블에 인서트
         }
  }
     
     
  //데이터베이스 작업 중 오류 발생 시, 업로드된 이미지를 삭제하고 예외를 던짐
    private void removeImageToRollback(List<String> imgUrList) {
        try {
            for (String imgUrl : imgUrList) {
                imageService.deleteImage(imgUrl); // S3에서 이미지 삭제
            }
        } catch (Exception e) {
            throw new InternerException("사진 업로드 중 서버 내부 오류가 발생했습니다."); // 이미지 삭제 오류 처리
        }
        throw new InternerException("서버 내부 오류입니다."); // 데이터베이스 오류 처리
    }
}
