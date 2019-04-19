package com.douyasi.example.spring_demo;


import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.douyasi.example.spring_demo.security.filter.CORSFilter;

@SpringBootApplication
public class SpringDemoApplication {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter corsFilter = new CORSFilter();
        registration.setFilter(corsFilter);
        // In case you want the filter to apply to specific URL patterns only
        registration.addUrlPatterns("/*");  // don't recognize servlet.context-path=/api
        // registration.setOrder(-100);
        return registration;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoApplication.class, args);
    }
}