package com.douyasi.example.spring_demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.douyasi.example.spring_demo.security.filter.AuthenticationFilter;
import com.douyasi.example.spring_demo.security.filter.CORSFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@EnableWebSecurity
public class TokenBasedSecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenUserDetailsService userDetailsService;
    
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService);
    }

    // roles admin allow to access /admin/**
    // roles user allow to access /page/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // don't need CSRF
            .csrf().disable()
            // don't create session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .authorizeRequests() // #1 All requests are protected by default
                    .antMatchers("/", "/api/login", "/error", "/api/page").permitAll()  // #2 The home and login api/endpoints are explicitly excluded
                    //.antMatchers("/api/page", "/api/page/**").hasAnyRole("USER")  // #3 The page required `USER` role
                    //.antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .antMatchers("/api/page**", "page", "page**")
                    .authenticated();  // #4 All other endpoints require an authenticated user
        // http.addFilter(CORSFilter.class);
        // http.addFilterBefore(authenticationFilterBean(), CORSFilter.class);
    }
    
    /*
    @Bean
    public AuthenticationFilter authenticationFilterBean() throws Exception {
        return new AuthenticationFilter();
    }
    */

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(HttpMethod.GET, "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**");
    }
}