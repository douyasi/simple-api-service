package com.douyasi.example.jersey_jetty_demo.security;

import java.security.Principal;

public final class AuthUser implements Principal {

    private final String username;
    private final Long uid;
    
    public AuthUser(String username, Long uid) {
        this.username = username;
        this.uid = uid;
    }
    
    @Override
    public String getName() {
        return username;
    }
    
    public Long getUserId() {
        return uid;
    }

}
