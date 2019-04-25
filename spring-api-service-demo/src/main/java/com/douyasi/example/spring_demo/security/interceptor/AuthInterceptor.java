package com.douyasi.example.spring_demo.security.interceptor;

import com.douyasi.example.spring_demo.domain.Token;
import com.douyasi.example.spring_demo.domain.User;
import com.douyasi.example.spring_demo.domain.dao.TokenDao;
import com.douyasi.example.spring_demo.domain.dao.UserDao;
import com.douyasi.example.spring_demo.exception.AppException;
import com.douyasi.example.spring_demo.security.AuthContext;
import com.douyasi.example.spring_demo.security.AuthUser;
import com.douyasi.example.spring_demo.util.RespUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthInterceptor
 *
 * @author raoyc
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String AUTHENTICATION_SCHEME = "Bearer";
    
    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        // Get the Authorization header from the request
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // Validate the Authorization header
        try {
            if (!isTokenBasedAuthentication(authorizationHeader)) {
                throw new AppException("403", "fail to authenticate access token!");
            }
            // Extract the token from the Authorization header
            String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
            // Validate the token
            validateToken(token, request);
        } catch (AppException e) {
            RespUtil.respException(response, e);
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
            .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void validateToken(String token, HttpServletRequest req) throws AppException {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid

        // It is not compelling necessary to load the use details from the database. You could also store the information
        // in the token and read it from it. It's up to you ;)
        // chk twice
        AuthUser authUser = loadUserByToken(token);
        if (authUser != null) {
            AuthContext.addUser(authUser);
            logger.info("authenticated user [" + authUser.getId() + ":" + authUser.getUsername() + "], setting security context");
            return;
        }
        throw new AppException("401", "access token already expired, please recall `/api/login` !");
    }
    
    private AuthUser loadUserByToken(String token) throws AppException {
        Token oToken = tokenDao.getTokenByToken(token);
        if (oToken == null) {
            throw new AppException("403", "fail to authenticate access token!");
        } else {
            Long uid = oToken.getUid();
            Long expiredAt = oToken.getExpiredAt();
            Long time = System.currentTimeMillis();
            System.out.println("time: " + time + "expiredAt: " + expiredAt);
            // not expired
            if (time < expiredAt*1000) {
                User user = userDao.getUser(uid);
                if (user == null) {
                    return null;
                }
                AuthUser authUser = AuthUser.create(user);
                return authUser;
            } else {
                throw new AppException("401", "access token already expired, please recall `/api/login` !");
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object object, Exception ex) throws Exception {
        AuthContext.removeUser();
    }
}
