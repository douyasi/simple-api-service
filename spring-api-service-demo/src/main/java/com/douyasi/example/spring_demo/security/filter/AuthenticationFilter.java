package com.douyasi.example.spring_demo.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import com.douyasi.example.spring_demo.exception.AppException;
import com.douyasi.tinyme.common.model.CommonResult;
import com.douyasi.tinyme.common.util.ResultUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * AuthenticationFilter
 * 
 * ref:
 * - https://www.programcreek.com/java-api-examples/?class=javax.servlet.http.HttpServletRequest&method=getHeader
 * 
 * @author raoyc
 */
public class AuthenticationFilter extends GenericFilterBean {
    private static final String REALM = "Access to spring_demo site";
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    
    @Override
    public void doFilter(
      ServletRequest request, 
      ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse)response;
        // Get the Authorization header from the request
        String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(resp);
            return;
        }
        
        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        try {
            // Validate the token
            validateToken(token, resp);
        } catch (AppException e) {
            handleAppException(resp, e);
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

    private void abortWithUnauthorized(HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        resp.setHeader(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"");
    }

    private void validateToken(String token, HttpServletResponse resp) throws AppException {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
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
        String jsonInString = "Error or exception occurs !";
        try {
            jsonInString = mapper.writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            // ex.printStackTrace();
        }
        resp.getWriter().write(jsonInString);
    }
}
