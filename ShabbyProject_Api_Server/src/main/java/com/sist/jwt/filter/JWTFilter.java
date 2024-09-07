package com.sist.jwt.filter;

import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.jwt.JWTUtil;
import com.sist.service.security.impl.CustomUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter{

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;
   


    @Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// TODO Auto-generated method stub
    	
    	 String requestURI = request.getRequestURI();//자원을 가져옴 
    	
    	  return requestURI.equals("/api/reissue"); //재발급시에는 필터를 수행하지않음
	}


	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, java.io.IOException {
				
	    	// 헤더에서 access키에 담긴 토큰을 꺼냄
	    	String accessToken = request.getHeader("access");
	
	    	// 토큰이 없다면 다음 필터로 넘김
	    	if (accessToken == null) {
	    	
	    	    filterChain.doFilter(request, response);
	    	  
	    	    return;
	    	}
	
	    	// 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
	    	try {
	    	    jwtUtil.isExpired(accessToken);
	    	} catch (ExpiredJwtException e) {
	    		
	    		// 클라이언트 측에 410 에러전송 ,410 에러는 현재 서버내 유일하고 , 클라이언트 측에서는 응답 인터셉트로 받아 액세스토큰 재발급 진행 
	    	
	    	
	    		ResponseDTO<Void> responseApi = new ResponseDTO<Void>(
	         			 HttpStatus.GONE.value(),
	                      "만료된 인증입니다."
	                  );
	          	responseApi.set410Response(response, responseApi, objectMapper);
	          	return;
	    	}
	
	    	// 토큰이 access인지 확인 (발급시 페이로드에 명시)
	    	String category = jwtUtil.getCategory(accessToken);
	
	    	if (!category.equals("access")) {
	    		//액세스토큰이 아닐시 
	    		ResponseDTO<Void> responseApi = new ResponseDTO<Void>(
	         			 HttpStatus.UNAUTHORIZED.value(),
	                      "비정상적인 접근입니다"
	                  );
	          	responseApi.set401Response(response, responseApi, objectMapper);
	          	return;
	    	}
	
	    	// username, role,idNum 값을 획득
	    	String username = jwtUtil.getUsername(accessToken);//이메일
//	    	String role = jwtUtil.getRole(accessToken);//권한
	    	int idNum = jwtUtil.getIdNum(accessToken);//고유번호
	    	String nickname=jwtUtil.getNickname(accessToken);//닉네임
	    	
	    	//맴버dto 생성후 담기 
	    	MemberDTO dto = new MemberDTO();
	    	
	    	dto.setEmail(username);
//	    	dto.setRole(role);
	    	dto.setIdNum(idNum);
	    	dto.setNickname(nickname);
	    	
	    	CustomUserDetails customUserDetails = new CustomUserDetails(dto);//ueserDetails에 dto객체 전달
	    	//일시적으로 세션에 담기위해 (SecurityContextHolder)
	    	Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, null);
	    	SecurityContextHolder.getContext().setAuthentication(authToken);
	
	    	filterChain.doFilter(request, response);
    }
}