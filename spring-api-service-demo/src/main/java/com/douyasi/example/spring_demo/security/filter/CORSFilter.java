package com.douyasi.example.spring_demo.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cross-Origin Resource Sharing filter.
 * 
 * ref:
 * - http://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#CORS_Filter
 * - https://github.com/signalarun/CORSFilter
 * - https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
 * - https://github.com/RobJinman/CorsFilter
 * - https://github.com/taoxy1993/Spring4-SpringMVC4-Mybatis4-Quartz
 */
@Component
public class CORSFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);
    
    private static Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(
        "http://localhost:18080", "http://127.0.0.1:18080"));

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String ori = req.getHeader("Origin");
        logger.info("origin domain: " + ori);
        if (ori != null && allowedOrigins.contains(ori)) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Access-Control-Allow-Origin", ori);
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, "
                    + "X-Requested-With, "
                    + "Content-Type, "
                    + "Accept, "
                    + "Origin, "
                    + "User-Agent, "
                    + "Access-Control-Allow-Origin, "
                    + "Access-Token");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Expose-Headers", "Cache-Control, "
                    + "Content-Language, "
                    + "Content-Type, "
                    + "Expires, "
                    + "Last-Modified, "
                    + "Pragma");
        }
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {}

}