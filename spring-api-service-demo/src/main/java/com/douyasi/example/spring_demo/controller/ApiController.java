package com.douyasi.example.spring_demo.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;

// import java.util.List;

import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.RequestMapping;

import com.douyasi.example.spring_demo.domain.Token;
import com.douyasi.example.spring_demo.domain.User;
import com.douyasi.example.spring_demo.exception.AppException;
import com.douyasi.example.spring_demo.model.dto.UserCredentials;
import com.douyasi.example.spring_demo.service.AuthService;
import com.douyasi.tinyme.common.model.CommonResult;
import com.douyasi.tinyme.common.util.ResultUtil;

/**
 * @author raoyc
 */
@RestController
// @RequestMapping("/api")
public class ApiController {

    private final AuthService authService;
    
    @Autowired
    public ApiController(AuthService authService) {
        this.authService = authService;
    }
    
    
    /**
     * index
     * 
     * @return 
     */
    @GetMapping("/")
    public CommonResult<?> getIndex() {
        return ResultUtil.returnSuccess();
    }

    @GetMapping("/err1")
    public void getError() {
        throw new AppException("500", "异常错误");
    }

    /**
     * ping
     * 
     * @return
     */
    @GetMapping(value = "/ping", produces="text/plain")
    public String getPing() {
        return "pong";
    }

    /**
     * login
     * 
     * @return
     */
    @PostMapping("/login")
    public CommonResult<?> postLogin(@RequestBody UserCredentials userCredentials) {
        try {
            String email = userCredentials.getEmail();
            String password = userCredentials.getPassword();
            User user = authService.validateCredentials(email, password);
            Token token = authService.generateToken(user);
            return ResultUtil.returnSuccess(token);
        } catch (AppException e) {
            return ResultUtil.returnError(e.getMessage(), e.getCode());
        }
    }
    
    /**
     * get all pages
     *
     * @return
     */
    @GetMapping("/page")
    public CommonResult<?> getPages() {
        Token token = new Token();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        token.setCreatedAt(now);
        token.setUpdatedAt(now);
        token.setUid(1L).setExpiredAt(133126712L).setToken("sdaghsdajhsdaadasda");
        return ResultUtil.returnSuccess(token);
    }
}
