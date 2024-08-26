package com.sist.dto.post;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class RequestPostDTO {

private List<MultipartFile> imgList;
private List<String> hobbyList;
private List<String> followTagList;
private List<String> imgUrlList;
private String content;
private boolean canReply;
private boolean onlyMe;
public String canReplyState;
public String onlyMeState;
private int idNum;
private int postNum;

}
