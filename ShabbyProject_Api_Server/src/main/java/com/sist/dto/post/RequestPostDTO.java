package com.sist.dto.post;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class RequestPostDTO {

private List<MultipartFile> imgList;
private List<String> hobbyList;
private List<String> followTagList;
private List<String> imgUrlList;
@Size(max = 150, message = "게시글 내용은 150자리이하야합니다.")
private String content;
private boolean canReply;
private boolean onlyMe;
public String canReplyState;
public String onlyMeState;
private int idNum;
private int postNum;

}
