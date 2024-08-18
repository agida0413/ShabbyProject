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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.sist.common.SimpleCodeGet;
import com.sist.repository.member.MemberAccountRepository;
import com.sist.repository.member.MybatisMemberAccountRepository;
import com.sist.service.member.security.CustomUserDetails;
import com.sist.service.member.security.MybatisRefreshService;
import com.sist.vo.MemberVO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final MybatisRefreshService refreshService;
    private final SimpleCodeGet simpleCodeGet;
   
    
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,MybatisRefreshService refreshService,SimpleCodeGet simpleCodeGet) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshService=refreshService;
        this.simpleCodeGet=simpleCodeGet;
       
    
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
        
        //토큰 생성
        String access = jwtUtil.createJwt("access", email, role, 1L);//엑세스 토큰 
        String refresh = jwtUtil.createJwt("refresh", email, role, 86400000L); //리프레시 토큰 

  
        //refresh토큰 저장
        int idNum=simpleCodeGet.getIdNum(authentication);
      
        refreshService.addRefreshEntity(idNum, refresh, 86400000L);
        
        //응답 설정
        response.setHeader("access", access);//엑세스 토큰은 헤더에 
        response.addCookie(createCookie("refresh", refresh));//리프레시 토큰은 쿠키에
        response.setStatus(HttpStatus.OK.value());
    	
       
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    
        response.setStatus(400); //로그인 실패시 400
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