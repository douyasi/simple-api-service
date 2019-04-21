package com.douyasi.example.spring_demo.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douyasi.example.spring_demo.util.RespUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.douyasi.example.spring_demo.exception.AppException;
import com.douyasi.example.spring_demo.security.TokenUserDetailsService;


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
            validateToken(token, request);
        } catch (AppException e) {
            RespUtil.respException(response, e);
            return;
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

    private void validateToken(String token, HttpServletRequest req) throws AppException {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid

        // It is not compelling necessary to load the use details from the database. You could also store the information
        // in the token and read it from it. It's up to you ;)
        // chk twice
        UserDetails userDetails = userDetailsService.loadUserByToken(token);
        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            logger.info("authenticated user " + userDetails.getUsername() + "], setting security context");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return;
        }
        throw new AppException("401", "access token already expired, please recall `/api/login` !");
    }
}
