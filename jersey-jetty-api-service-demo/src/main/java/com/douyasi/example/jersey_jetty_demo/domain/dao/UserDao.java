package com.douyasi.example.jersey_jetty_demo.domain.dao;

import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Param;

import com.douyasi.example.jersey_jetty_demo.domain.User;

public interface UserDao {
    User getUser(Long id);
    
    User getUserByCredentials(@NotNull @Param("email") String email, @NotNull @Param("password") String password);
}
