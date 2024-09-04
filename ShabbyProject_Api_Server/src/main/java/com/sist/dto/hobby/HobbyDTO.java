package com.sist.dto.hobby;


import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class HobbyDTO {

private int hbNum; //관심사 고유번호 
private int idNum; //회원 고유번호 
private int postNum; //게시물 고유번호 
private String hobby; //관심사 
private String isFromJoin; 


}
