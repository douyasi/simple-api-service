package com.douyasi.example.jersey_jetty_demo.security.filter;

import java.io.IOException;


import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.SecurityContext;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.douyasi.example.jersey_jetty_demo.domain.Token;
import com.douyasi.example.jersey_jetty_demo.domain.dao.TokenDao;
import com.douyasi.example.jersey_jetty_demo.exception.AppException;
import com.douyasi.example.jersey_jetty_demo.security.AuthUser;
import com.douyasi.example.jersey_jetty_demo.security.Secured;
import com.douyasi.example.jersey_jetty_demo.security.TokenBasedSecurityContext;
import com.douyasi.example.jersey_jetty_demo.service.MyBatisUtil;
import com.douyasi.tinyme.common.util.ResultUtil;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String REALM = "Access to jersey_jetty_demo site";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get the Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }
        
        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        try {
            // Validate the token
            validateToken(token, requestContext);
        } catch (AppException e) {
            handleAppException(requestContext, e);
        }

    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        /*
        Response.Status.UNAUTHORIZED;  // 401
        Response.Status.FORBIDDEN;  // 403
        Response.Status.OK;  // 200
        Response.Status.NOT_FOUND;  // 404
        Response.Status.INTERNAL_SERVER_ERROR;  // 500
        */
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, 
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }

    private void validateToken(String token, ContainerRequestContext requestContext) throws AppException {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TokenDao tokenDao = sqlSession.getMapper(TokenDao.class);
        Token oToken = tokenDao.getTokenByToken(token);
        if (oToken == null) {
            throw new AppException("403", "fail to authenticate access token!");
        } else {
            Long uid = oToken.getUid();
            Long expiredAt = oToken.getExpiredAt();
            Long time = System.currentTimeMillis();
            if (time < expiredAt*1000) {  // not expired
                boolean isSecure = requestContext.getSecurityContext().isSecure();
                AuthUser authUser = new AuthUser("jersey_jetty_u" + uid, uid);
                SecurityContext securityContext = new TokenBasedSecurityContext(authUser, isSecure);
                requestContext.setSecurityContext(securityContext);
            } else {
                throw new AppException("401", "access token already expired, please recall `/api/login` !");
            }
        }
    }
    
    /**
     * handle AppException
     * 
     * @param requestContext
     * @param e
     */
    private void handleAppException(ContainerRequestContext requestContext, AppException e) {
        Integer code = Integer.parseInt(e.getCode());
        requestContext.abortWith(
                Response.status(code)
                        .entity(ResultUtil.returnError(e.getMessage(), e.getCode()))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + "; charset=utf-8")
                        .build());
    }
}
