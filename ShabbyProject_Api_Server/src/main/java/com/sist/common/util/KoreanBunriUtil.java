package com.sist.common.util;

import java.util.ArrayList;
import java.util.List;

import com.sist.dto.hobby.HobbySaveDTO;

public class KoreanBunriUtil {
	
		public static List<HobbySaveDTO> getKeyword(List<String> keywordList){
			List<HobbySaveDTO> list =new ArrayList<>();
			for (String keyword : keywordList) {
				HobbySaveDTO dto = new HobbySaveDTO();
				String result="";
				  for(int i=0; i < keyword.length();i++) {

					    String k = keyword.substring(i, i+1);
					    result += getInitialSound(k);
					   
					   
					  }
				  dto.setHobbyKoreanBunri(result);
				  dto.setHobby(keyword);
				  list.add(dto);
			}
			
		
		  
		
		  
		  return list;
		}
		
		
		public static String getKeyword(String keyword){
			
			
				HobbySaveDTO dto = new HobbySaveDTO();
				String result="";
				  for(int i=0; i < keyword.length();i++) {

					    String k = keyword.substring(i, i+1);
					    result += getInitialSound(k);
					   
					   
					  }
				
				  
			
			
		  
		  return result;
		}

		/**
		  * 초성 추출, 중성, 종성 구하는 방법 추가
		  * @param text
		  * @return
		  */
		public static final String getInitialSound(String text) {
			String result="";
		  // 초성 19자
		  final String[] initialChs = {
		    "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
		    "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
		    "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
		    "ㅋ", "ㅌ", "ㅍ", "ㅎ"
		  };

		  // 중성 21자
		  final String[] medialChs = {
		    "ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ",
		    "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ",
		    "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ",
		    "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ",
		    "ㅣ"
		  };

		  // 종성 없는 경우 포함하여 28자
		  final String[] finalChs = {
		    " ",  "ㄱ", "ㄲ", "ㄳ", "ㄴ",
		    "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ",
		    "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ",
		    "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ",
		    "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ",
		    "ㅌ", "ㅍ", "ㅎ"
		  };

		  // 19: 초성
		  // 21: 중성
		  // 28: 종성
		  if(text.length() > 0) {
		    char chName = text.charAt(0);
		    if(chName >= 0xAC00 && chName <= 0xD7A3) {  // 0xAC00(가) ~ 0xD7A3(힣)

		      int uniVal = chName - 0xAC00;
		      int initialCh = ((uniVal) / (21 * 28)); // 초성 index
		   
		      // 중성
		      int medialCh = ((uniVal % (28 * 21)) / 28);
		 

		      // 종성
		      int finalCh = ((uniVal % 28));
		      
		      result += initialChs[initialCh] +medialChs[medialCh] + finalChs[finalCh];
		      return result;
		    } else {
		      return ""+chName;
		    }
		  }

		  return "";
		}
}
