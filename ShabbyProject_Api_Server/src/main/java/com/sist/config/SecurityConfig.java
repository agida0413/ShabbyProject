package com.sist.config;

import java.util.Collections;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.sist.common.SimpleCodeGet;
import com.sist.jwt.CustomLogoutFilter;
import com.sist.jwt.JWTFilter;
import com.sist.jwt.JWTUtil;
import com.sist.jwt.LoginFilter;
import com.sist.service.member.security.RefreshService;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

		private final AuthenticationConfiguration authenticationConfiguration;
		//JWTUtil 주입
		private final JWTUtil jwtUtil;
		
		private RefreshService refreshService;
		private final SimpleCodeGet simpleCodeGet;
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil,RefreshService refreshService,SimpleCodeGet simpleCodeGet) {

        this.authenticationConfiguration = authenticationConfiguration;
				this.jwtUtil = jwtUtil;
				this.refreshService=refreshService;
				this.simpleCodeGet=simpleCodeGet;
    }
   
		@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    		
    	http
        .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setMaxAge(3600L);

									configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                return configuration;
            }
        })));
    	
    	http
        .csrf((auth) -> auth.disable()); //jwt 사용으로 인한 disable 

		http
		        .formLogin((auth) -> auth.disable()); //jwt사용으로 인한 기본로그인폼  x
		
		http
		        .httpBasic((auth) -> auth.disable());
		
		http
		        .authorizeHttpRequests((auth) -> auth
		                .requestMatchers("/api/login", "/api/join","/api/reissue",
		                		"/api/members/emailAuth","/api/members/emailValidate"
		                		,"api/members/nickValidate","/api/members","/api/members/findEmail"
		                		,"/api/members/findPassword").permitAll() //로그인 ,회원가입 , 토큰 재발급,이메일인증 api는 권한 필요없음 
		                .anyRequest().authenticated());
				
				
		http
		        .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);//JWTFilter 등록 = > 로그인 필터 전에 수행 
		
		
		//  로그인필터를  UsernamePasswordAuthenticationFilter 위치에 
		http
		.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil,refreshService,simpleCodeGet), UsernamePasswordAuthenticationFilter.class);
		
		//커스텀한 로그아웃 필터를 등록 =>기존 필터위치에 
		http
		.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshService), LogoutFilter.class);
		
		http
        .sessionManagement((session) -> session // 세션방식 미사용
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

			return http.build();
}
}