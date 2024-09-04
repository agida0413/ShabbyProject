package com.sist.dto.post;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class WritePostDTO {

private List<MultipartFile> imgList; //파일 형태의 이미지 리스트 
private List<String> hobbyList; //관심사 목록 
private List<String> followTagList; //태그한 인물들  리스트 
private List<String> imgUrlList; //데이터베이스에 저장할 s3로 부터 반환받은 url리스트 
@Size(max = 150, message = "게시글 내용은 150자리이하야합니다.")
private String content;//게시물 내용 
private boolean onlyMe; //나만보기 기능
public String onlyMeState;  //나만보기 상태 
private int idNum; //회원고유번호 
private int postNum; //게시물 고유번호

}
