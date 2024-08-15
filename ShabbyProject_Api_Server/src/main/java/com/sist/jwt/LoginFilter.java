package com.sist.jwt;

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
import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.sist.repository.member.memberAccountRepository;
import com.sist.service.member.CustomUserDetails;
import com.sist.service.member.RefreshService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshService refreshService;
    
    
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,RefreshService refreshService) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshService=refreshService;
    
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    
    	
    	
      String email=obtainUsername(request); //프론트에서 username으로 줘야함
      String password=obtainPassword(request);
    

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
    	
    	CustomUserDetails userDetails= (CustomUserDetails)authentication.getPrincipal();
    	int idNum =userDetails.getIdNum();
    	
    	//다중토큰발급 시작
    	String email = authentication.getName();
    
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        
        //토큰 생성
        String access = jwtUtil.createJwt("access", email, role, 1L);
        String refresh = jwtUtil.createJwt("refresh", email, role, 86400000L);


        
        //refresh토큰 저장
        refreshService.addRefreshEntity(idNum, refresh, 86400000L);
        
        //응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    	
       
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    
        response.setStatus(400);
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