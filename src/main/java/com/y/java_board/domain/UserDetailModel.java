package com.y.java_board.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailModel implements UserDetails {
    private final String userEmail;
    private final String password;
    private final String nickname;
    private final String name;
    // private List<GrantedAuthority> authorities;

    public UserDetailModel(User user) {
        this.userEmail = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.name = user.getName();
        // this.authorities = Stream.of(user.getRoles().split(","))
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return this.authorities;
        return null;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
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
