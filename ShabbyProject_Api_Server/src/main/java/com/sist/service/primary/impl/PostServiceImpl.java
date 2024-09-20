package com.sist.service.primary.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.common.util.PathVariableValidation;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.follow.UnFollowDTO;
import com.sist.dto.post.DoPostLikeDTO;
import com.sist.dto.post.GetPostDetailDTO;
import com.sist.dto.post.PostDelDTO;
import com.sist.dto.post.PostDetailDTO;
import com.sist.dto.post.TagInformDTO;
import com.sist.dto.post.WritePostDTO;
import com.sist.dto.util.AlarmDTO;
import com.sist.repository.AlarmRepository;

import com.sist.repository.PostRepository;
import com.sist.service.primary.PostService;
import com.sist.service.util.ImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	//게시물 repository
    private final PostRepository postRepository;
    //이미지업로드 서비스
    private final ImageService imageService;
    private final AlarmRepository alarmRepository;

    //게시물 업로드 서비스 
    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<Void>> postInsertTransaction(WritePostDTO dto)  {
    	    	
    	List<MultipartFile> imgList = dto.getImgList(); // 업로드할 이미지 리스트
       
    	List<String> imgUrList = uploadImage(imgList); // 업로드된 이미지의 URL을 저장할 리스트
     
    	 
        // 데이터베이스 저장
        try {
            int idNum = SimpleCodeGet.getIdNum(); // 현재 세션의 ID 가져오기
            dto.setIdNum(idNum); // 게시물 DTO에 ID 설정
            //만약 공백제거 ""면 null
          
            setPostState(dto); // 게시물 상태 설정

            // 게시물 테이블에 데이터 저장
            postRepository.postInsert(dto);
            
            // 관심사 및 인물 태그 인서트 //postnum+hobby  유니크키 
           insertHobbies(dto);
           insertFollowTags(dto);
           
           //태그한 멤버에게 알람 전송위해 알람삽입 ==> 태그한 멤버가 있다면 , 나만보기가 아니라면 
            if(dto.getFollowTagList().size()!=0&&!dto.isOnlyMe()) {
            		//알람 객체 생성 
            		AlarmDTO alarmDTO= new AlarmDTO();
                	//알람 객체 가공 및 세팅 
            		alarmSetting(alarmDTO, dto, idNum,"TAG",false);
            		//알람 삽입 
                	alarmRepository.alarmInsert(alarmDTO);             	        			
            
            }

            // 게시물 이미지 URL 리스트를 DTO에 설정하고 이미지 테이블에 저장
            dto.setImgUrlList(imgUrList);
            postRepository.postImgInsert(dto);

        } 
       
        catch (Exception e) {
        	
            removeImage(imgUrList); // 데이터베이스 오류 발생 시 이미지 삭제 및 롤백
            throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.", "게시물 삽입중 에러발생");
        }

        // 성공적으로 처리된 경우 HTTP 200 OK 응답 반환
        return new ResponseEntity<>(new ResponseDTO<>(), HttpStatus.OK);
    }

   


    //게시물 상세보기 서비스
	@Override
	public ResponseEntity<ResponseDTO<PostDetailDTO>> postDetail(int postNum) {
		// TODO Auto-generated method stub
		
		//현재 로그인한 회원의 고유번호
		int idNum=SimpleCodeGet.getIdNum();
		
		//데이터베이스 전송객체
		GetPostDetailDTO reqDto= new GetPostDetailDTO();
		reqDto.setPostNum(postNum); //게시물번호
		reqDto.setIdNum(idNum);//회원번호 
		
		//게시물 고유번호 기반 정보 가져오기 
		PostDetailDTO dto = postRepository.postDetail(reqDto);
		//정보가 없을 시
		if(dto==null) {
			throw new InternerException("존재하지 않는 게시물 입니다.","존재하지 않는 게시물 접근");
		}
		//현재 로그인한 회원 기반 , 현재 조회하려는 게시물의 좋아요 상태 체크 
		if(dto.getLikeCheck()!=0) {
			//조인한 컬럼(게시물 좋아요테이블) 의 카운트가 0이아니면 like === true
			dto.setLiked(true);
		}
		//만약 내 게시물이라면 
		if(dto.getIdNum()==idNum) {
			dto.setItsMe(true);
		}
		
	
		//데이터베이스에서 , 를 붙혀 스트링 타입으로 받아온 데이터들을 다시 리스트 형태로 변환하고 , 보낼 객체에 담는다 .
		//관심사리스트 생성 
		List<String> hbList = new ArrayList<>();
		//사람태그 리스트 생성
		List<TagInformDTO> tgList=new ArrayList<>();
		//이미지 리스트 생성
		List<String> imgList = new ArrayList<>();
		//태그한 멤버 닉네임 리스트
		List<String> tagNickList=new ArrayList<>();
		
		//데이터베이스에서 가져온 관심사정보가 null이 아닐시 
		if(dto.getStrHobbyList()!=null) {
			//관심사 리스트 배열로 변환 
			String [] handleHobbyList=dto.getStrHobbyList().split(",");
			//리스트로 변환 
			for (String hobby : handleHobbyList) {
				//하나씩 add
				hbList.add(hobby);
			}
			//객체에 담기 
			dto.setHobbyList(hbList);
		}
		//데이터베이스에서 가져온 이미지 정보가 null이 아닐시 
		if(dto.getStrImgList()!=null) {
			// 이미지 리스트 배열로 변환
			String[] handleImgList = dto.getStrImgList().split(",");
			// 리스트로 변환
			for (String img : handleImgList) {
				// 하나씩 add
				imgList.add(img);
			}
			dto.setImgList(imgList);
		}
		//데이터베이스에서 가져온 회원 태그정보가 null이 아닐시 
		if(dto.getStrTagList()!=null) {
			// 태그 리스트 배열로 변환
			String[] handleTagList = dto.getStrTagList().split(",");
			// 리스트로 변환
			for (String tag : handleTagList) {
				// 하나씩 add
				tagNickList.add(tag);
			}
			dto.setTagNickList(tagNickList);
		}
		//데이터베이스에서 가져온 회원 태그닉네임 정보가 null이 아닐시 
		if(dto.getStrTagList()!=null) {
			
			
			//태그 닉네임 정보 배열로변환  
			String[] handleTagList = dto.getStrTagList().split(",");
			// 프로필이미지 정보 닉네임 수만큼 크기의 배열 생성 
			String[] handleTagProfileList=new String[handleTagList.length];
			
			//만약 조회한 회원태그들의 프로필이미지가 전부 null일경우 데이터베이스는 null을반환 
			if(dto.getStrTagProfiles()==null) {
				//전송할 배열에 닉네임 수만큼 for문을 순회하며 null을 대입 
				for (int i=0; i<handleTagList.length;i++) {
					handleTagProfileList[i]=null;
				}
			}//프로필 이미지가 하나라도 존재하면 "예시이미지,NOPROFILE,NOPROFILE" 형태 
			else {
				//,로 잘라서 배열로 변환 
				handleTagProfileList= dto.getStrTagProfiles().split(",");;
			}
					
			
			// 닉네임 배열의 길이만큼 순회
			for (int i=0; i<handleTagList.length;i++) {
				// 닉네임,프로필 이미지를 가진 객체 생성 -->리스트로 담아전송 
				TagInformDTO handleDto = new TagInformDTO();
				//닉네임 세팅
				handleDto.setNickname(handleTagList[i]);
				//만약 프로필이 없는 상태이면 데이터베이스에서 NOPROFILE로 반환 
				//만약 해당배열 인덱스 밸류가 NOPROFILE이면
				if(handleTagProfileList[i].equals("NOPROFILE")) {
					//null을 세팅 
					handleDto.setProfile(null);
				}else {
					//아니라면 기존 값 세팅 
					handleDto.setProfile(handleTagProfileList[i]);
				}
			
				//클라이언트에 전송할 리스트에 객체 하나씩 add			
				tgList.add(handleDto);
			}
			//최종적으로 전송할 객체에 리스트를 세팅 
			dto.setTagList(tgList);;
		}
		
		//클라이언트로 보낼때 회원고유번호 감추기 위함 
		dto.setIdNum(0);
		
		// 성공적으로 처리된 경우 HTTP 200 OK 응답 반환
        return new ResponseEntity<ResponseDTO<PostDetailDTO>>
        (new ResponseDTO<PostDetailDTO>(dto), HttpStatus.OK);
	}
	
	//게시물 삭제 
	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<Void>> postDelete(PostDelDTO dto) {
		// TODO Auto-generated method stub
		//현재 로그인 회원 고유번호 
		int idNum=SimpleCodeGet.getIdNum();
		dto.setIdNum(idNum);
		//게시물 번호
		int postNum=dto.getPostNum();
		//해당 게시물에 해당하는 사진 리스트를 가져옴
		List<String> imgUrlList=postRepository.postImgListByPostNum(postNum);
		//게시물 등록시 필히 사진을 1장이상 등록하기 때문에 만약 사진리스트가 비어있으면 예외처리
		if(imgUrlList.size()==0) {
			throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.", "게시물 삭제중 이미지 리스트가 비어있음");
		}
		
		//단순히 게시물 번호를 받아 레코드를 삭제할 수 있지만 , 혹시모를 위험에 대비하여 where절에 id넘버를 줘서 조건을 주면
		//본인이 작성한 게시물만 삭제할 수 있게 제한을 둘수 있고 , 이상현상을 막을 수 있다 판단하여 회원고유번호를 준다.
		postRepository.postDelete(dto);
		
		//게시물 삭제 시 관련 회원 알람 삭제 
		//알람 객체 생성 
		AlarmDTO alarmDTO=new AlarmDTO();
		//알람 객체 가공 및 세팅 
		alarmSetting(alarmDTO, dto, idNum, "TAG",false);
		//해당 게시물과 관련된 알람 삭제 
		alarmRepository.alarmDelete(alarmDTO);
		
		//s3 이미지 삭제 == > 삭제 실패시 예외발생으로 어차피 데이터베이스는 롤백된다. 
		removeImage(imgUrlList);
		
		// 성공적으로 처리된 경우 HTTP 200 OK 응답 반환
        return new ResponseEntity<ResponseDTO<Void>>
        (new ResponseDTO<Void>(), HttpStatus.OK);
	}
	
	//게시물 수정 서비스 
	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<Void>> postUpdate(WritePostDTO dto) {
		// TODO Auto-generated method stub
		
		
	//회원 고유번호 	
	int idNum=SimpleCodeGet.getIdNum();
	dto.setIdNum(idNum);
	
	//만약 수정작업시 삭제할 이미지 리스트가 존재하면==>클라이언트에서 기존 이미지를 x버튼을 통해 지운상태로 넘겼다 .
	//removeImgList는 클라이언트에서 삭제한 이미지들의 리스트 
	if(dto.getRemoveImgList().size()!=0) {
		//기존 데이터베이스에서 해당하는 게시물번호와 URL 이름으로 삭제 (URL은 고유하다)
		postRepository.deleteOriginalImg(dto);
		//s3 이미지 삭제 
		removeImage(dto.getRemoveImgList());
	}
	
	//만약 수정시에 새롭게 추가한 이미지가 있다면 
	if(dto.getImgList()!=null&&dto.getImgList().size()!=0) {
		//변경,추가 된사진 s3 업로드 
		List<MultipartFile> imgList = dto.getImgList(); // 업로드할 이미지 리스트
    	List<String> imgUrList = uploadImage(imgList); // 업로드된 이미지의 URL을 저장할 리스트
    	
    	//반환받은 url 리스트 세팅 
    	dto.setImgUrlList(imgUrList);
    	
    	try {
    		//새롭게 추가된 이미지 데이터베이스 저장 
			postRepository.postImgInsert(dto);
		} catch (Exception e) {
			// TODO: handle exception
			//데이터베이스 저장중 예외 발생시 s3 다시 삭제 
			removeImage(imgUrList);
			 throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.", "게시물 삭제도중 이미지 재 삭제중 에러발생");
		}
	}
	//기존 회원 태그 리스트를 갖고옴 
	List<String> originalMemList=
			alarmRepository.originalMemberTag(dto.getPostNum());
	
	//기존 관심사 삭제 
	postRepository.deleteOriginalHobby(dto);
	//기존 회원태그 삭제
	postRepository.deleteOriginalMemTag(dto);

	
	//새로운 태그 리스트 
    List<String> newMemList=dto.getFollowTagList();

	//중복 제거후 알람 삭제,삽입 과정을 위해 
	 Set<String> originalSet = new HashSet<>(originalMemList);
     Set<String> newSet = new HashSet<>(newMemList);
     
     // 제거할 태그 리스트 (originalSet - newSet)
     List<String> removeList = new ArrayList<>();
     for (String mem : originalSet) {
         if (!newSet.contains(mem)) {
             removeList.add(mem);
         }
     }
     

     // 추가할 태그 리스트 (newSet - originalSet)
     List<String> addList = new ArrayList<>();
     for (String mem : newSet) {
         if (!originalSet.contains(mem)) {
             addList.add(mem);
         }
     }
    
	
	
	// 태그 회원 수정 발생시 ---> 태그 관련 알람 재설정 필요 
	//알람 객체 생성 
	AlarmDTO alarmDTO= new AlarmDTO();
	//알람 객체 가공 및 세팅 
	alarmSetting(alarmDTO, dto, idNum,"TAG",true);
	
	//제거할 태그리스트 가 있으면 
	if(removeList.size()!=0) {
		//기존 태그관련 알람 삭제 
		alarmDTO.setReceivers(removeList);
		alarmRepository.alarmDelete(alarmDTO);
	}
	
	
	//만약 클라이언트로부터 새롭게 받은 관심사리스트가 비어있지않다면
	//새로운 관심사 인서트 
	if(dto.getHobbyList().size()!=0) {
		postRepository.hobbyInsert(dto);
	}
	//만약 클라이언트로부터 새롭게 받은 맴버태그 리스트가 비어있지않다면
		
	//새로운 맴버태그 인서트 
	if(dto.getFollowTagList().size()!=0) {
		postRepository.followTagInsert(dto);	
		//만약 새로운 맴버태그가 있으면 
		if(addList.size()!=0 ) {
			//알람 삽입 
			alarmDTO.setReceivers(addList);
			alarmRepository.alarmInsert(alarmDTO);           
		} 
	
		
	}

	

	//게시물 테이블을 업데이트 할 정보 세팅 
	// 내용 null처리 , boolean값으로 받은(체크박스로 받은) 나만보기 여부 String타입으로 전환 
	setPostState(dto);
	
	//게시물  테이블 업데이트 
	postRepository.postInformUpdate(dto);
	
	
		// 성공적으로 처리된 경우 HTTP 200 OK 응답 반환
	    return new ResponseEntity<ResponseDTO<Void>>
	    (new ResponseDTO<Void>(), HttpStatus.OK);
	}

	
	//게시물 좋아요 작업 
	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<DoPostLikeDTO>> doPostLike(DoPostLikeDTO dto) {
		// TODO Auto-generated method stub
		
		//회원 고유번호를 가져옴 
		int idNum=SimpleCodeGet.getIdNum();
		//dto에 세팅 
		dto.setIdNum(idNum);
		
		//알람 객체 생성 
		AlarmDTO alarmDTO= new AlarmDTO();
		//알람 객체 가공 및 세팅 
		alarmSetting(alarmDTO, dto, idNum, "LIKE",false);
		//좋아요 알람 데이터 가공을 위한 게시물의 주인 아이디 고유번호를 갖고와 receiver에 세팅 
		int receiver=alarmRepository.chooseReceiver(dto.getPostNum());
		alarmDTO.setReceiver(receiver);
		
		//만약 클라이언트로 부터 받은 현재 특정 게시물에 대한 내 좋아요 상태가 
		//true라면 (누른 상태라면)
		if(dto.isLiked()) {
			//좋아요 테이블에서 제거작업 
			postRepository.postLikeDelete(dto);
			//만약 내 게시물에 대한 좋아요 작업이 아니라면 
			if(alarmDTO.getReceiver()!=idNum) 
			{   //기존 좋아요 알람 제거 
				alarmRepository.alarmDelete(alarmDTO);
			}
			//클라이언트에게 보낼 바뀐 상태값
			dto.setLiked(false);
		}//false라면 
		else {
			//좋아요 테이블에 삽입 작업 
			postRepository.postLikeInsert(dto);
			//만약 내 게시물에 대한 좋아요 작업이 아니라면 
			if(alarmDTO.getReceiver()!=idNum) {
				//알람 삽입 
				alarmRepository.alarmInsert(alarmDTO);
			}
			
			//클라이언트에게 보낼 바뀐 상태값
		    dto.setLiked(true);
		}
		// 회원고유번호 + 게시물 고유번호의 유니크 키 제약조건 == > 이상현상 validation
		
		
		//삽입 후 좋아요 개수를 데이터베이스에서 가져옴 클라이언트에서 단순히 ++, -- 작업을 하면 이상현상이 있을 수 있다 판단 
		int likeCount=postRepository.afterDoPostLike(dto.getPostNum());
		dto.setLikeCount(likeCount);
		
		return new ResponseEntity<ResponseDTO<DoPostLikeDTO>>
		(new ResponseDTO<DoPostLikeDTO>(dto),HttpStatus.OK); //성공 ;
	}
	
	
	
    //게시물의 나만보기, 댓글기능해제 상태를 설정
    private void setPostState(WritePostDTO dto) {
     
        dto.setOnlyMeState(dto.isOnlyMe() ? "ONLYME" : "NOTONLYME"); // 나만 보기 설정
        if (dto.getContent().trim().isEmpty()) {
            dto.setContent(null); // 게시물 내용이 비어있으면 null로 설정
        }
    }

   
    //게시물과 연관된 관심사테이블에 데이터  삽입 
    private void insertHobbies(WritePostDTO dto) {
    	  if (dto.getHobbyList() != null && !dto.getHobbyList().isEmpty()) {
              postRepository.hobbyInsert(dto); // 관심사 테이블에 인서트
          }
    }
    //게시물과 연관된 사람태그 테이블에 데이터  삽입 
    private void insertFollowTags(WritePostDTO dto) {
    	 if (dto.getFollowTagList() != null && !dto.getFollowTagList().isEmpty()) {
             postRepository.followTagInsert(dto); // 인물 태그 테이블에 인서트
         }
  }
     
     
  //s3이미지 삭제
    private void removeImage(List<String> imgUrList) {
    		
    	try {
    		 for (String imgUrl : imgUrList) {
    			
    			 imageService.deleteImage(imgUrl); // S3에서 이미지 삭제
                 
             }
		} catch (InternerException e) {
			// TODO: handle exception
			throw new InternerException(e.getMessage(), e.getServerErrorMsg());
		}
    	 catch (Exception e) {
 			// TODO: handle exception
 			throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.", "이미지 업로드 예외발생으로 인한 사진 롤백삭제중 예외발생");
 		}
           
      
    }
    
    private List<String>  uploadImage(List<MultipartFile> imgList) {
    	 List<String> imgUrList = new ArrayList<>(); // 업로드된 이미지의 URL을 저장할 리스트
    	 
    	  // S3 업로드 시작
         try {
         	   for (MultipartFile image : imgList) {
                  
                    String imgUrl = imageService.upload(image); //하나씩 꺼내와서 이미지 업로드==>s3에서 반환받은 url 값 저장
                    imgUrList.add(imgUrl); // 업로드된 이미지를 url리스트에 URL 추가
                }
         	  
 		} catch (BadRequestException e) {
 			// TODO: handle exception
 			 if (!imgUrList.isEmpty()) {
 			        // 업로드된 이미지가 있을 경우, 필요한 추가 처리 (이미지 삭제)
 			        removeImage(imgUrList);
 			    }
 			throw new BadRequestException(e.getMessage());
 		}
         catch (InternerException e) {
 			// TODO: handle exception
         	 if (!imgUrList.isEmpty()) {
         	        // 업로드된 이미지가 있을 경우, 필요한 추가 처리 ( 이미지 삭제)
         	        removeImage(imgUrList);
         	    }
         	throw new InternerException(e.getMessage(), e.getServerErrorMsg());
 		}
         catch (Exception e) {
 			// TODO: handle exception
         	 if (!imgUrList.isEmpty()) {
         	        // 업로드된 이미지가 있을 경우, 필요한 추가 처리 (예: 이미지 삭제)
         	        removeImage(imgUrList);
         	    }
         	 throw new InternerException("서버 내부 오류입니다. 잠시 뒤 이용해 주세요.","이미지 삭제중오류 ");
         	
 		}
        
         
         return imgUrList;
    }


    //알람 객체 가공 및 세팅 , 오버라이딩 
    private void alarmSetting(AlarmDTO alarmDTO,WritePostDTO dto, int idNum ,String type,boolean isUpdate) {
		alarmDTO.setUpdate(isUpdate);
		alarmDTO.setSender(idNum); //보내는이를 현 로그인 회원으로 
		alarmDTO.setAlarmType(type); //매개변수로 알람 타입 받음 
		alarmDTO.setReceivers(dto.getFollowTagList()); //receivers라는 리스트 형태에 회원 태그 리스트 세팅 
		alarmDTO.setPostNum(dto.getPostNum()); //게시물 번호 
	}
    
    private void alarmSetting(AlarmDTO alarmDTO,DoPostLikeDTO dto, int idNum ,String type,boolean isUpdate) {
    	alarmDTO.setUpdate(isUpdate);
		alarmDTO.setSender(idNum);//보내는이를 현 로그인 회원으로 
		alarmDTO.setAlarmType(type);//매개변수로 알람 타입 받음 
		alarmDTO.setPostNum(dto.getPostNum());//게시물 번호 
	}
    
    private void alarmSetting(AlarmDTO alarmDTO,PostDelDTO dto, int idNum,String type ,boolean isUpdate) {
    	alarmDTO.setUpdate(isUpdate);
		alarmDTO.setAlarmType(type);//매개변수로 알람 타입 받음		
		alarmDTO.setPostNum(dto.getPostNum());//게시물 번호 
	}
	



	
	

}
