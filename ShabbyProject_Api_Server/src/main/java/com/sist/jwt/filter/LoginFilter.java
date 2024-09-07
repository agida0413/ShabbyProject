package com.sist.jwt.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sist.common.util.CookieUtil;
import com.sist.common.util.PathVariableValidation;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.jwt.JWTUtil;
import com.sist.service.security.RefreshService;

import jakarta.servlet.FilterChain;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    //jwt
    private final JWTUtil jwtUtil;
    //액세스 토큰 재발급 서비스 
    private final RefreshService refreshService;
  
    //jackson objectmapper
    private final ObjectMapper objectMapper;
 
   
    
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,RefreshService refreshService,ObjectMapper objectMapper) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshService=refreshService;
        this.objectMapper=objectMapper;
    
        
        //로그인 api url
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    
    	
    	
      String email=obtainUsername(request); //프론트엔드에서 username으로 formdata로 준값 읽기
      String password=obtainPassword(request); //프론트엔드에서 password로 formdata로 준값 읽기
      
      //이메일 형식 VALIDATION
      if(!PathVariableValidation.emailValService(email)) {
    		ResponseDTO<Void> responseApi = new ResponseDTO<Void>(
 	       			 HttpStatus.METHOD_NOT_ALLOWED.value(),
 	                    "이메일 형식이 아닙니다."
 	                );
 	        	
					try {
						responseApi.set405Response(response, responseApi, objectMapper);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
 	       
      }

      	//로그인을 위해  UsernamePasswordAuthenticationToken 에 정보를 담고 authenticate= > userdetailservice = > 인가 
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList());

        return authenticationManager.authenticate(authToken);
    }

    
    //로그인 인증 성공시 
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws  IOException {
    	
    	
    	
    	//다중토큰발급 시작
    	String email = authentication.getName();
    	

    	// 권한 값 읽어오기
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); 
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//        String role = auth.getAuthority();
        //authentication 객체에서 아이디 고유번호값 읽어오기 
        int idNum=SimpleCodeGet.getIdNum(authentication);
        //문자열로 변환 
        String strIdNuM=String.valueOf(idNum);
        
        //닉네임 가져오기
        String nickname=SimpleCodeGet.getNickname(authentication);
        
        //토큰 생성( 각토큰이름 + email+role+strIdNum + 유효기간 + 시크릿키(sha))
        String access = jwtUtil.createJwt("access", email, strIdNuM,nickname, 10000L);//엑세스 토큰 
        String refresh = jwtUtil.createJwt("refresh", email, strIdNuM,nickname ,86400000L); //리프레시 토큰 
        
  
        //refresh토큰 데이터베이스에 저장 = > 서버에서 제어권을 가지려고 ( 나중에 탈취당했을때에 대비하여)
        refreshService.addRefreshEntity(idNum, refresh, 86400000L);
        
        //응답 설정
        response.setHeader("access", access);//엑세스 토큰은 헤더에 
        response.addCookie(CookieUtil.createCookie("refresh", refresh));//리프레시 토큰은 쿠키에
        
        //성공시 응답
        ResponseDTO<Void> succesResponseApi = new ResponseDTO<Void>();
    	succesResponseApi.setOkResponse(response, succesResponseApi, objectMapper);
        return;
    	
       
    }
    
    
    //실패시 
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws JsonProcessingException, IOException {
    		
    	//실패 응답 포멧전송
    	ResponseDTO<Void> responseApi = new ResponseDTO<Void>(
    			 HttpStatus.METHOD_NOT_ALLOWED.value(),
                 "입력정보가 일치하지 않습니다."
             );
    	//405 사용자 정의 메소드
    	responseApi.set405Response(response, responseApi, objectMapper);
    }
    
   
    
    
}