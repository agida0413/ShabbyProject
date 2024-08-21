package com.sist.service.security.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sist.dto.member.MemberDTO;


public class CustomUserDetails implements UserDetails {

    private final MemberDTO dto;

    public CustomUserDetails(MemberDTO dto) {

        this.dto = dto;
    }

    //회원 권한 정보 읽어오기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>(); 

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return dto.getRole();
            }
        });

        return collection;
    }

    //패스워드 
    @Override
    public String getPassword() {

        return dto.getPassword();
    }
    
    //고유번호 리턴
    public int getIdNum()
    {
    	return dto.getIdNum();
    }
    
    //이메일을 사용하니 이메일 리턴
    @Override
    public String getUsername() {
    
        return dto.getEmail(); 
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}