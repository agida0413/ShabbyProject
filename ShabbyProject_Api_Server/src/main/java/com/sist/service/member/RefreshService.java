package com.sist.service.member;

import java.sql.Date;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sist.jwt.JWTUtil;
import com.sist.repository.member.JwtStoreRepository;
import com.sist.vo.MemberVO;
import com.sist.vo.TokenStoreVO;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshService {
private final JwtStoreRepository repository;
private final JWTUtil jwtUtil;



	public	void deleteRefresh(String refresh) { // 데이터베이스에서 리프레시 토큰을 지움 매개변수는 토큰 
			
			repository.deleteRefresh(refresh);
		}
		
		
		public void addRefreshEntity(int idNum, String refresh, Long expiredMs) {// 리프레시토큰을 데이터베이스에 저장
				
		    Date date = new Date(System.currentTimeMillis() + expiredMs); //현재시간 + 매개변수로 받은 유효기간 
	
		 TokenStoreVO vo= new TokenStoreVO();
		 
		    vo.setIdNum(idNum); //매개변수로 받은 아이디고유번호
		    vo.setRefresh(refresh); // 매개변수로 받은 토큰 
		    vo.setExpiration(date.toString()); // 유효기간
	
		    repository.save(vo);//데이터에 저장
		}
	
	public Boolean	isExist(String refresh) { //리프레시 토큰이 데이터베이스에 실존하는지 검증
		
		int count=repository.findRefresh(refresh); //리프레시 토큰을 기준으로 데이터베이스의 행의개수
		boolean result=false;//초기값 false
		if(count>0) {//검색결과가 있을 시
			result=true; //true로 초기화
		}
		return result;
	}
	
	//최종 refresh 토큰 발급 서비스
	 public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) { 
	    
	     
	        String refresh = null;
	        Cookie[] cookies = request.getCookies();  
	        for (Cookie cookie : cookies) {

	            if (cookie.getName().equals("refresh")) {//쿠키를 읽어서 키값이 refresh 인것을 가져옴

	                refresh = cookie.getValue(); // 그것에 대한 value를 읽음
	            }
	        }

	        if (refresh == null) {//만약 refresh가 없다면 

	           
	            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);// 메시지와 400 응답코드 리턴 
	        }

	        //expired check
	        try {
	            jwtUtil.isExpired(refresh);// 유효기간 검증 
	        } catch (ExpiredJwtException e) {

	            //response status code
	            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);//유효기간이 지났다면 400응답코드 리턴 
	        }

	      
	        String category = jwtUtil.getCategory(refresh);   // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)

	        if (!category.equals("refresh")) {//refresh 토큰이 아니면 

	         
	            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST); //400응답코드 
	        }
	        
	       
			Boolean isExist = isExist(refresh); //DB에 저장되어 있는지 확인
			if (!isExist) {//없다면 
			
			  
			    return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);//400응답코드 
			}
	        

	        String username = jwtUtil.getUsername(refresh); //토큰에서 이메일을 읽어옴
	        String role = jwtUtil.getRole(refresh);// 권한을 읽어옴 

	
	      
	        //새로운 jwt 토큰 발급 
	        String newAccess = jwtUtil.createJwt("access", username, role, 60000000L);
	        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

	        
	     
	        
			deleteRefresh(refresh); //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
			
			 int idNum=repository.findId_num(username);// 리프레시 테이블에 회원고유번호를 저장하기 위해 
			addRefreshEntity(idNum, newRefresh, 86400000L);//새 토큰 데이터에 저장
	        
	        response.setHeader("access", newAccess); //새로운 토큰을 헤더에 추가 
	        response.addCookie(createCookie("refresh", newRefresh)); // 쿠키생성 메서드

	        return new ResponseEntity<>(HttpStatus.OK);
	    }
	
	private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true); https
        //cookie.setPath("/");
        cookie.setHttpOnly(true); //xss 공격방지 

        return cookie;
    }

}
