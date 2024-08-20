package com.sist.jwt.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.http.HttpStatus;
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
import com.sist.common.utill.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.jwt.JWTUtil;
import com.sist.service.security.RefreshService;

import jakarta.servlet.FilterChain;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshService refreshService;
    private final SimpleCodeGet simpleCodeGet;
    private final ObjectMapper objectMapper;
   
    
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,RefreshService refreshService,SimpleCodeGet simpleCodeGet,ObjectMapper objectMapper) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshService=refreshService;
        this.simpleCodeGet=simpleCodeGet;
       this.objectMapper=objectMapper;
    
        setFilterProcessesUrl("/api/login");//로그인 api url
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    
    	
    	
      String email=obtainUsername(request); //프론트에서 username으로 줘야함
      String password=obtainPassword(request); 
    

      	//로그인을 위해  UsernamePasswordAuthenticationToken 에 정보를 담고 authenticate= > userdetailservice = > 인가 
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authToken);
    }

    
    //로그인 인증 성공시 
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
    	
    	
    	
    	//다중토큰발급 시작
    	String email = authentication.getName();
    
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        int idNum=simpleCodeGet.getIdNum(authentication);
        String strIdNuM=String.valueOf(idNum);
     
        //토큰 생성
        String access = jwtUtil.createJwt("access", email, role,strIdNuM, 10000L);//엑세스 토큰 
        String refresh = jwtUtil.createJwt("refresh", email, role,strIdNuM, 86400000L); //리프레시 토큰 
        
  
        //refresh토큰 저장
       
        
        refreshService.addRefreshEntity(idNum, refresh, 86400000L);
        
        //응답 설정
        response.setHeader("access", access);//엑세스 토큰은 헤더에 
        response.addCookie(createCookie("refresh", refresh));//리프레시 토큰은 쿠키에
        response.setStatus(HttpStatus.OK.value());
    	
       
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws JsonProcessingException, IOException {
    	
    	ResponseDTO<Void> responseApi = new ResponseDTO<Void>(
    			 HttpStatus.METHOD_NOT_ALLOWED.value(),
                 "입력정보가 일치하지 않습니다."
             );
    	responseApi.set405Response(response, responseApi, objectMapper);
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