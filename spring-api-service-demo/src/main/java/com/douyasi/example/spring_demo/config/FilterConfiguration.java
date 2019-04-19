package com.douyasi.example.spring_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.douyasi.example.spring_demo.security.filter.AuthenticationFilter;
import com.douyasi.example.spring_demo.security.filter.CORSFilter;

@Configuration
public class FilterConfiguration {

    @Autowired
    private CORSFilter corsFilter;


    @Bean
    public FilterRegistrationBean<CORSFilter> corsFilter() {
        FilterRegistrationBean<CORSFilter> registration = new FilterRegistrationBean<CORSFilter>();
        registration.setFilter(corsFilter);
        // In case you want the filter to apply to specific URL patterns only
        registration.addUrlPatterns("/*");  // don't recognize servlet.context-path=/api
        registration.setOrder(200);
        return registration;
    }
    
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<AuthenticationFilter>();
        AuthenticationFilter authFilter = new AuthenticationFilter();
        registration.setFilter(authFilter);
        registration.addUrlPatterns("/page", "/page/**");  // don't recognize servlet.context-path=/api
        registration.setOrder(100);
        return registration;
    }
}
