package com.sist.dto.feed;



import lombok.Data;

@Data
public class ResponsePostListDTO {
private int postNum;
private String postImgUrl;
private String onlyMe;
private int likeCount;
private int replyCount;

}
