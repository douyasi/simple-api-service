package com.douyasi.example.spring_demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.douyasi.example.spring_demo.exception.AppException;

public interface TokenUserDetailsService extends UserDetailsService {

    UserDetails loadUserByToken(String token) throws AppException;

}
