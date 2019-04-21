package com.douyasi.example.spring_demo.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douyasi.example.spring_demo.exception.AppException;
import com.douyasi.example.spring_demo.util.RespUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


@Component("apiAccessDeniedHandler")
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, 
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {
        AppException e = new AppException("403", "Forbidden");
        RespUtil.respException(response, e);
    }
}