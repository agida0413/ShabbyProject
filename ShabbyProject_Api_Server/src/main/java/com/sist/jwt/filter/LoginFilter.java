package com.sist.jwt.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
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
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sist.common.util.CookieUtil;
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
    //공통 모듈함수
    private final SimpleCodeGet simpleCodeGet;
    //jackson objectmapper
    private final ObjectMapper objectMapper;
    //쿠키관련 제어 함수 
    private final CookieUtil cookieUtil;
   
    
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,RefreshService refreshService,SimpleCodeGet simpleCodeGet,ObjectMapper objectMapper,CookieUtil cookieUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshService=refreshService;
        this.simpleCodeGet=simpleCodeGet;
        this.objectMapper=objectMapper;
        this.cookieUtil=cookieUtil;
        
        //로그인 api url
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    
    	
    	
      String email=obtainUsername(request); //프론트엔드에서 username으로 formdata로 준값 읽기
      String password=obtainPassword(request); //프론트엔드에서 password로 formdata로 준값 읽기
    

      	//로그인을 위해  UsernamePasswordAuthenticationToken 에 정보를 담고 authenticate= > userdetailservice = > 인가 
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authToken);
    }

    
    //로그인 인증 성공시 
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws  IOException {
    	
    	
    	
    	//다중토큰발급 시작
    	String email = authentication.getName();
    	
    	// 권한 값 읽어오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); 
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        //authentication 객체에서 아이디 고유번호값 읽어오기 
        int idNum=simpleCodeGet.getIdNum(authentication);
        //문자열로 변환 
        String strIdNuM=String.valueOf(idNum);
     
        //토큰 생성( 각토큰이름 + email+role+strIdNum + 유효기간 + 시크릿키(sha))
        String access = jwtUtil.createJwt("access", email, role,strIdNuM, 10000L);//엑세스 토큰 
        String refresh = jwtUtil.createJwt("refresh", email, role,strIdNuM, 86400000L); //리프레시 토큰 
        
  
        //refresh토큰 데이터베이스에 저장 = > 서버에서 제어권을 가지려고 ( 나중에 탈취당했을때에 대비하여)
        refreshService.addRefreshEntity(idNum, refresh, 86400000L);
        
        //응답 설정
        response.setHeader("access", access);//엑세스 토큰은 헤더에 
        response.addCookie(cookieUtil.createCookie("refresh", refresh));//리프레시 토큰은 쿠키에
        
        //성공시 응답
        ResponseDTO<Void> succesResponseApi = new ResponseDTO<Void>();
    	succesResponseApi.setOkesponse(response, succesResponseApi, objectMapper);
        return;
    	
       
    }
    
    
    //실패시 
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws JsonProcessingException, IOException {
    	
    	ResponseDTO<Void> responseApi = new ResponseDTO<Void>(
    			 HttpStatus.METHOD_NOT_ALLOWED.value(),
                 "입력정보가 일치하지 않습니다."
             );
    	
    	responseApi.set405Response(response, responseApi, objectMapper);
    }
    
   
    
    
}