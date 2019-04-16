package com.douyasi.example.spring_demo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douyasi.example.spring_demo.service.AuthService;
import com.douyasi.tinyme.common.model.CommonResult;
import com.douyasi.tinyme.common.util.ResultUtil;

/**
 * @author raoyc
 */
@RestController
// @RequestMapping("/api")
public class ApiController {
    /*
    private AuthService authService;
    
    @Autowired
    public ApiController(AuthService authService) {
        this.authService = authService;
    }
    */
    
    
    /**
     * get all pages
     * @return
     */
    @GetMapping("/page")
    public CommonResult<?> getPages() {
        return ResultUtil.returnSuccess();
    }
}
