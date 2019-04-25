package com.douyasi.example.spring_demo.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.douyasi.example.spring_demo.domain.Page;
import com.douyasi.example.spring_demo.domain.dao.PageDao;
import com.douyasi.example.spring_demo.domain.model.ListData;
import com.douyasi.example.spring_demo.model.dto.PageBean;
import com.douyasi.example.spring_demo.security.AuthContext;
import com.douyasi.example.spring_demo.security.AuthUser;
import org.apache.ibatis.session.SqlSession;
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
    
    private final PageDao pageDao;
    
    @Autowired
    public ApiController(AuthService authService, PageDao pageDao) {
        this.authService = authService;
        this.pageDao = pageDao;
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
    public CommonResult<?> getPages(@ModelAttribute PageBean pageBean) {
        Long uid = getAuthUserId();
        List<Page> pages = pageDao.getPagesByCondition(pageBean);
        int pagesCount = pageDao.getPagesCountByCondition(pageBean);
        ListData<Page> listData = new ListData<Page>(pagesCount, pageBean.getPerPage(), pageBean.getPage(), pages);
        return ResultUtil.returnSuccess(listData);
    }
    
    private Long getAuthUserId() {
        return 1L;
        /*
        AuthUser user = AuthContext.getUser();
        if (user != null) {
            return user.getId();
        } else {
            throw new AppException("403", "illegal or incorrect credentials");
        }
        */
    }
}
