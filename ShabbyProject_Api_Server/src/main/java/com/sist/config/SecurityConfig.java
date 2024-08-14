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

import com.sist.jwt.CustomLogoutFilter;
import com.sist.jwt.JWTFilter;
import com.sist.jwt.JWTUtil;
import com.sist.jwt.LoginFilter;
import com.sist.service.member.RefreshService;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

		private final AuthenticationConfiguration authenticationConfiguration;
		//JWTUtil 주입
		private final JWTUtil jwtUtil;
		
		private RefreshService refreshService;
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil,RefreshService refreshService) {

        this.authenticationConfiguration = authenticationConfiguration;
				this.jwtUtil = jwtUtil;
				this.refreshService=refreshService;
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
        .csrf((auth) -> auth.disable());

http
        .formLogin((auth) -> auth.disable());

http
        .httpBasic((auth) -> auth.disable());

http
        .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/login", "/", "/api/join","/api/reissue").permitAll()
                .requestMatchers("/api/test").hasRole("ADMIN")
                .anyRequest().authenticated());
		
		//JWTFilter 등록
http
        .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);


http
.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil,refreshService), UsernamePasswordAuthenticationFilter.class);
http.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshService), LogoutFilter.class);
http
        .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

return http.build();
}
}