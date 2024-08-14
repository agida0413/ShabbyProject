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



	public	void deleteRefresh(String refresh) {
			
			repository.deleteRefresh(refresh);
		}
		
		
		public void addRefreshEntity(int id_num, String refresh, Long expiredMs) {
	
		    Date date = new Date(System.currentTimeMillis() + expiredMs);
	
		 TokenStoreVO vo= new TokenStoreVO();
		    vo.setId_num(id_num);
		    vo.setRefresh(refresh);
		    vo.setExpiration(date.toString());
	
		    repository.save(vo);
		}
	
	public Boolean	isExist(String refresh) {
		int count=repository.findRefresh(refresh);
		boolean result=false;
		if(count>0) {
			result=true;
		}
		return result;
	}
	
	
	 public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
	    
	     
	        String refresh = null;
	        Cookie[] cookies = request.getCookies();
	        for (Cookie cookie : cookies) {

	            if (cookie.getName().equals("refresh")) {

	                refresh = cookie.getValue();
	            }
	        }

	        if (refresh == null) {

	           
	            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
	        }

	        //expired check
	        try {
	            jwtUtil.isExpired(refresh);
	        } catch (ExpiredJwtException e) {

	            //response status code
	            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
	        }

	        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
	        String category = jwtUtil.getCategory(refresh);

	        if (!category.equals("refresh")) {

	            //response status code
	            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
	        }
	        
	        //DB에 저장되어 있는지 확인
			Boolean isExist = isExist(refresh);
			if (!isExist) {
			
			    //response body
			    return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
			}
	        

	        String username = jwtUtil.getUsername(refresh);
	        String role = jwtUtil.getRole(refresh);

	        
	        int id_num=repository.findId_num(username);
	      //make new JWT
	        String newAccess = jwtUtil.createJwt("access", username, role, 60000000L);
	        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

	        
	      //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
	        
			deleteRefresh(refresh);
			addRefreshEntity(id_num, newRefresh, 86400000L);
	        //response
	        response.setHeader("access", newAccess);
	        response.addCookie(createCookie("refresh", newRefresh));

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
