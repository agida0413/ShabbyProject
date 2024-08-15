package com.sist.service.member;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.sist.vo.MemberVO;

public class CustomUserDetails implements UserDetails {

    private final MemberVO vo;

    public CustomUserDetails(MemberVO vo) {

        this.vo = vo;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>(); 

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return vo.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return vo.getPassword();
    }
    
    public int getIdNum()
    {
    	return vo.getIdNum();//고유번호 리턴
    }
    @Override
    public String getUsername() {
    
        return vo.getEmail(); //이메일을 사용하니 이메일 리턴
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