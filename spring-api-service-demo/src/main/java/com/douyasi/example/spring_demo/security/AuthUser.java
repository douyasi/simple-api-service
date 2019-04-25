package com.douyasi.example.spring_demo.security;

import com.douyasi.example.spring_demo.domain.User;

import java.io.Serializable;

public class AuthUser implements Serializable {

    private static final long serialVersionUID = 4340949739173978615L;

    private final Long id;

    // equal com.douyasi.example.spring_demo.domain.User nickname
    private final String username;  

    private final String email;

    public static AuthUser create(User user) {
        return new AuthUser(
                user.getId(),
                user.getNickname(),
                user.getEmail()
                );
    }

    public AuthUser(Long id,
            String username,
            String email
            ) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
