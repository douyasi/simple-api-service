package com.douyasi.example.spring_demo.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.douyasi.example.spring_demo.domain.Page;
import com.douyasi.example.spring_demo.domain.dao.PageDao;
import com.douyasi.example.spring_demo.domain.model.ListData;
import com.douyasi.example.spring_demo.model.dto.CreateOrUpdatePage;
import com.douyasi.example.spring_demo.model.dto.PageBean;
import com.douyasi.example.spring_demo.security.AuthContext;
import com.douyasi.example.spring_demo.security.AuthUser;
import com.douyasi.tinyme.common.constants.BaseErrorCode;
import com.douyasi.tinyme.common.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.RequestMapping;

import com.douyasi.example.spring_demo.domain.Token;
import com.douyasi.example.spring_demo.domain.User;
import com.douyasi.example.spring_demo.exception.AppException;
import com.douyasi.example.spring_demo.model.dto.UserCredentials;
import com.douyasi.example.spring_demo.service.AuthService;
import com.douyasi.tinyme.common.model.CommonResult;
import com.douyasi.tinyme.common.util.ResultUtil;

import javax.validation.constraints.NotNull;

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
            System.out.println("email:" + email + " | password: " + password);
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
    public CommonResult<?> getPages(PageBean pageBean) {
        Long uid = getAuthUserId();
        pageBean.setUid(uid);
        List<Page> pages = pageDao.getPagesByCondition(pageBean);
        int pagesCount = pageDao.getPagesCountByCondition(pageBean);
        ListData<Page> listData = new ListData<Page>(pagesCount, pageBean.getPerPage(), pageBean.getPage(), pages);
        return ResultUtil.returnSuccess(listData);
    }

    /**
     * create new page
     * 
     * @return
     */
    @PostMapping("/page")
    public CommonResult<?> postPage(@RequestBody CreateOrUpdatePage createPage) {
        Long uid = getAuthUserId();
        String content = createPage.getContent();
        System.out.println(createPage + content);
        if (ValidateUtil.isEmpty(content)) {
            return ResultUtil.returnError("param illegal or empty!", BaseErrorCode.Common.PARAM_EMPTY.getCode());
        }
        int row = 0;
        try {
            Page page = new Page();
            page.setUid(uid);
            page.setContent(content);
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
            page.setCreatedAt(now);
            page.setUpdatedAt(now);
            row = pageDao.insert(page);
        } catch (Exception e) {
            return ResultUtil.returnError(e.getMessage(), null);
        }
        if (row == 1) {
            return ResultUtil.returnSuccess();
        } else {
            return ResultUtil.returnError("insert fail!", null);
        }
    }


    /**
     * get specific page
     * 
     * @return
     */
    @GetMapping("/page/{id}")
    public CommonResult<?> getSpecificPage(@NotNull @PathVariable("id") Long id) {
        Long uid = getAuthUserId();
        Page page = pageDao.getPage(id, uid);
        if (page == null) {
            return ResultUtil.returnError("record not found!", "404");
        } else {
            return ResultUtil.returnSuccess(page);
        }
    }

    /**
     * update specific page
     * 
     * @return
     */
    @PutMapping("/page/{id}")
    public CommonResult<?> putSpecificPage(@RequestBody CreateOrUpdatePage updatePage, @NotNull @PathVariable("id") Long id) {
        Long uid = getAuthUserId();
        String content = updatePage.getContent();
        if (ValidateUtil.isEmpty(content)) {
            return ResultUtil.returnError("param illegal or empty!", BaseErrorCode.Common.PARAM_EMPTY.getCode());
        }
        Page page = pageDao.getPage(id, uid);
        int row = 0;
        if (page == null) {
            return ResultUtil.returnError("record not found!", "404");
        } else {
            try {
                row = pageDao.update(content, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (row == 1) {
                return ResultUtil.returnSuccess();
            } else {
                return ResultUtil.returnError("update fail!", null);
            }
        }
    }

    /**
     * delete specific page
     * 
     * @return
     */
    @DeleteMapping("/page/{id}")
    public CommonResult<?> deleteSpecificPage(@NotNull @PathVariable("id") Long id) {
        Long uid = getAuthUserId();
        Page page = pageDao.getPage(id, uid);
        int row = 0;
        if (page == null) {
            return ResultUtil.returnError("record not found!", "404");
        } else {
            try {
                row = pageDao.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (row == 1) {
            return ResultUtil.returnSuccess();
        } else {
            return ResultUtil.returnError("delete fail!", null);
        }
    }
    
    private Long getAuthUserId() {
        AuthUser user = AuthContext.getUser();
        if (user != null) {
            return user.getId();
        } else {
            throw new AppException("403", "illegal or incorrect credentials");
        }
    }
}
