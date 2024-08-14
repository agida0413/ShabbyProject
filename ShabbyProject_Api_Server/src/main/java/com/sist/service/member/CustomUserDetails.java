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
    
    public int getId_num()
    {
    	return vo.getId_num();
    }
    @Override
    public String getUsername() {
    	System.out.println(vo.getEmail());
        return vo.getEmail();
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