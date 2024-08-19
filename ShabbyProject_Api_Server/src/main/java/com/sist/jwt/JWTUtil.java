package com.sist.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {

    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {

    	//application.properties에 저장된 secretkey 암호 알고리즘 통해 생성사를 통해 secretkey 생성
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // 하단부터 jwt 토큰 파싱,해석해서 메서드 이름에 해당하는 값 리턴 
    public String getUsername(String token) {
    
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String getCategory(String token) {
    	return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category",String.class);
    }
    public int getIdNum(String token) {
    	return Integer.parseInt(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("idNum",String.class)); 
    			
    }
    
    //토큰을 만듬 
    public String createJwt(String category,String username, String role,String idNum, Long expiredMs) {

        return Jwts.builder()
        		.claim("category", category) //refresh토큰인지 access 토큰인지 
                .claim("username", username) //이메일
                .claim("role", role) //권한 
                .claim("idNum",idNum)//고유번호
                .issuedAt(new Date(System.currentTimeMillis()))//만든날
                .expiration(new Date(System.currentTimeMillis() + expiredMs))//유효기간
                .signWith(secretKey)//시크릿키
                .compact();
    }
}
