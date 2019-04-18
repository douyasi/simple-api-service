package com.douyasi.example.spring_demo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.douyasi.example.spring_demo.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthUser implements UserDetails {

    private static final long serialVersionUID = 4340949739173978615L;

    private final Long id;

    private final String username;  // equal com.douyasi.example.spring_demo.domain.User nickname

    private final String email;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public static AuthUser create(User user) {
        List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
        SimpleGrantedAuthority userRole = new SimpleGrantedAuthority("USER");  // ROLE for normal user
        // SimpleGrantedAuthority adminRole = new SimpleGrantedAuthority("ADMIN");  // ROLE for administrator
        roles.add(userRole);
        // roles.add(adminRole);
        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .collect(Collectors.toList());
        return new AuthUser(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getPassword(),
                authorities
                );
    }

    public AuthUser(Long id,
            String username,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities
            ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
