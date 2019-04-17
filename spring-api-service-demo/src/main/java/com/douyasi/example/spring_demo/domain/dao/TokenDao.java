package com.douyasi.example.spring_demo.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.douyasi.example.spring_demo.domain.Token;

@Mapper
@Component
public interface TokenDao {
    Token getTokenByToken(String token);
    
    int insert(Token record);
}
