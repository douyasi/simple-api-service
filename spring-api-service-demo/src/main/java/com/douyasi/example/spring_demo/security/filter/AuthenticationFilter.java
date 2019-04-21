package com.douyasi.example.spring_demo.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
// import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import com.douyasi.example.spring_demo.exception.AppException;
import com.douyasi.example.spring_demo.security.TokenUserDetailsService;
import com.douyasi.tinyme.common.model.CommonResult;
import com.douyasi.tinyme.common.util.ResultUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * AuthenticationFilter
 * 
 * ref:
 * - https://www.programcreek.com/java-api-examples/?class=javax.servlet.http.HttpServletRequest&method=getHeader
 * - https://github.com/liumapp/spring-security-mybatis-demo
 * - https://www.cnblogs.com/bndong/p/10275430.html
 * @author raoyc
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static final String REALM = "Access to spring_demo site";
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    
    @Autowired
    private TokenUserDetailsService userDetailsService;

    @Override
    public void doFilterInternal(
      HttpServletRequest request, 
      HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
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
            validateToken(token, response, request);
        } catch (AppException e) {
            handleAppException(response, e);
        } catch (Exception e) {
            
        }
        chain.doFilter(request, response);
    }
    
    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void validateToken(String token, HttpServletResponse resp, HttpServletRequest req) throws AppException {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            // chk twice
            UserDetails userDetails = null;
            userDetails = userDetailsService.loadUserByToken(token);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + userDetails.getUsername() + "], setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return;
            }
        }
        throw new AppException("401", "access token already expired, please recall `/api/login` !");
    }

    /**
     * handle AppException
     * 
     * @param resp
     * @param e
     * @throws IOException
     */
    private void handleAppException(HttpServletResponse resp, AppException e) throws IOException {
        Integer code = Integer.parseInt(e.getCode());
        if (code > 300 && code < 500) {
            resp.setStatus(code);
        } else {
            resp.setStatus(500);
        }
        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        CommonResult<Object> result = ResultUtil.returnError(e.getMessage(), e.getCode());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonInString = "Error or exception occurs !";
        try {
            jsonInString = mapper.writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            // ex.printStackTrace();
        }
        resp.getWriter().write(jsonInString);
    }
}
