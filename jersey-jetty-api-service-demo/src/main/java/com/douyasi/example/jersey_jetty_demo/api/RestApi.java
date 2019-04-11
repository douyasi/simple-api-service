package com.douyasi.example.jersey_jetty_demo.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
// import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.douyasi.example.jersey_jetty_demo.api.bean.PageBean;
import com.douyasi.example.jersey_jetty_demo.api.model.CreateOrUpdatePage;
import com.douyasi.example.jersey_jetty_demo.api.model.UserCredentials;
import com.douyasi.example.jersey_jetty_demo.domain.Page;
import com.douyasi.example.jersey_jetty_demo.domain.Token;
import com.douyasi.example.jersey_jetty_demo.domain.User;
import com.douyasi.example.jersey_jetty_demo.domain.dao.PageDao;
import com.douyasi.example.jersey_jetty_demo.domain.model.ListData;
import com.douyasi.example.jersey_jetty_demo.exception.AppException;
import com.douyasi.example.jersey_jetty_demo.security.AuthUser;
import com.douyasi.example.jersey_jetty_demo.security.Secured;
import com.douyasi.example.jersey_jetty_demo.service.AuthService;
import com.douyasi.example.jersey_jetty_demo.service.MyBatisUtil;
import com.douyasi.tinyme.common.constants.BaseErrorCode;
import com.douyasi.tinyme.common.model.CommonResult;
import com.douyasi.tinyme.common.util.ResultUtil;
import com.douyasi.tinyme.common.util.ValidateUtil;


@Path("/api")
public class RestApi {
    static SqlSessionFactory sqlSessionFactory = null;

    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    
    /**
     * post login
     * @return
     */
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public CommonResult<?> postLogin(UserCredentials userCredentials)
    {
        try {
            String email = userCredentials.getEmail();
            String password = userCredentials.getPassword();
            AuthService authService = new AuthService();
            User user = authService.validateCredentials(email, password);
            Token token = authService.generateToken(user);
            return ResultUtil.returnSuccess(token);
        } catch (AppException e) {
            return ResultUtil.returnError(e.getMessage(), e.getCode());
        }
    }
    
    /**
     * get all pages
     * @return
     */
    @GET
    @Path("pages")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResult<?> getPages(@BeanParam PageBean pageBean, @Context SecurityContext securityContext)
    {
        AuthUser authUser = (AuthUser) securityContext.getUserPrincipal();
        Long uid = authUser.getUserId();
        pageBean.setUid(uid);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PageDao pageDao = sqlSession.getMapper(PageDao.class);
        List<Page> pages = pageDao.getPagesByCondition(pageBean);
        int pagesCount = pageDao.getPagesCountByCondition(pageBean);
        ListData<Page> listData = new ListData<Page>(pagesCount, pageBean.getPerPage(), pageBean.getPage(), pages);
        return ResultUtil.returnSuccess(listData);
    }
    
    
    /**
     * create new page
     * @return
     */
    @POST
    @Path("page")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResult<?> postPage(CreateOrUpdatePage createPage, @Context SecurityContext securityContext)
    {
        AuthUser authUser = (AuthUser) securityContext.getUserPrincipal();
        Long uid = authUser.getUserId();
        
        String content = createPage.getContent();
        if (ValidateUtil.isEmpty(content)) {
            return ResultUtil.returnError("param illegal or empty!", BaseErrorCode.Common.PARAM_EMPTY.getCode());
        }
        
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int row = 0;
        try {
            Page page = new Page();
            page.setUid(uid);
            page.setContent(content);
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
            page.setCreatedAt(now);
            page.setUpdatedAt(now);
            PageDao pageDao = sqlSession.getMapper(PageDao.class);
            row = pageDao.insert(page);
            sqlSession.commit();
        } catch (Exception e) {
            return ResultUtil.returnError(e.getMessage(), null);
        } finally {
            sqlSession.close();
        }
        if (row == 1) {
            return ResultUtil.returnSuccess(null);
        } else {
            return ResultUtil.returnError("insert fail!", null);
        }
        
    }
    
    
    /**
     * get specific page
     * @return
     */
    @GET
    @Path("page/{id:\\d+}")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResult<?> getSpecificPage(@PathParam("id") Long id, @Context SecurityContext securityContext)
    {
        AuthUser authUser = (AuthUser) securityContext.getUserPrincipal();
        Long uid = authUser.getUserId();
        
        SqlSession sqlSession = sqlSessionFactory.openSession();
        
        PageDao pageDao = sqlSession.getMapper(PageDao.class);
        Page page = pageDao.getPage(id, uid);
        
        if (page == null) {
            return ResultUtil.returnError("record not found!", "404");
        } else {
            return ResultUtil.returnSuccess(page);
        }
    }
    
    /**
     * update specific page
     * @return
     */
    @PUT
    @Path("page/{id:\\d+}")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResult<?> putSpecificPage(@PathParam("id") Long id, CreateOrUpdatePage updatePage, @Context SecurityContext securityContext)
    {
        AuthUser authUser = (AuthUser) securityContext.getUserPrincipal();
        Long uid = authUser.getUserId();
        
        String content = updatePage.getContent();
        if (ValidateUtil.isEmpty(content)) {
            return ResultUtil.returnError("param illegal or empty!", BaseErrorCode.Common.PARAM_EMPTY.getCode());
        }
        
        SqlSession sqlSession = sqlSessionFactory.openSession();
        
        PageDao pageDao = sqlSession.getMapper(PageDao.class);
        Page page = pageDao.getPage(id, uid);
        int row = 0;
        if (page == null) {
            return ResultUtil.returnError("record not found!", "404");
        } else {
            try {
                row = pageDao.update(content, id);
                sqlSession.commit();
            } catch (Exception e) {
                
            } finally {
                sqlSession.close();
            }
            if (row == 1) {
                return ResultUtil.returnSuccess(null);
            } else {
                return ResultUtil.returnError("update fail!", null);
            }
        }
    }
    
    /**
     * delete specific page
     * @return
     */
    @DELETE
    @Path("page/{id:\\d+}")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonResult<?> deleteSpecificPage(@PathParam("id") Long id, @Context SecurityContext securityContext)
    {
        AuthUser authUser = (AuthUser) securityContext.getUserPrincipal();
        Long uid = authUser.getUserId();
        
        SqlSession sqlSession = sqlSessionFactory.openSession();
        
        PageDao pageDao = sqlSession.getMapper(PageDao.class);
        Page page = pageDao.getPage(id, uid);
        int row = 0;
        if (page == null) {
            return ResultUtil.returnError("record not found!", "404");
        } else {
            try {
                row = pageDao.delete(id);
                sqlSession.commit();
            } catch (Exception e) {
                
            } finally {
                sqlSession.close();
            }
        }
        if (row == 1) {
            return ResultUtil.returnSuccess(null);
        } else {
            return ResultUtil.returnError("delete fail!", null);
        }
    }
}
