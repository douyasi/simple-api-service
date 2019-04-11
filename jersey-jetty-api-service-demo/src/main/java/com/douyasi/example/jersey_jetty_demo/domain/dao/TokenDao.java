package com.douyasi.example.jersey_jetty_demo.domain.dao;

import com.douyasi.example.jersey_jetty_demo.domain.Token;


public interface TokenDao {
    Token getTokenByToken(String token);
    
    int insert(Token record);
}
