package com.douyasi.example.spring_demo.domain.dao;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.douyasi.example.spring_demo.domain.User;

@Mapper
@Component
public interface UserDao {
    User getUser(Long id);
    
    User getUserByCredentials(@NotNull @Param("email") String email, @NotNull @Param("password") String password);
}
