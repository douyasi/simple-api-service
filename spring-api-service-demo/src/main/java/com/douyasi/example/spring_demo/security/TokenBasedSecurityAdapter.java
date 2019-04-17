package com.douyasi.example.spring_demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@EnableWebSecurity
public class TokenBasedSecurityAdapter extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    // roles admin allow to access /admin/**
    // roles user allow to access /page/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/**")
                    .authorizeRequests() // #1 All requests are protected by default
                    .antMatchers("/", "/api/login", "/error").permitAll()  // #2 The home and login api/endpoints are explicitly excluded
                    .antMatchers("/api/page/**").hasAnyRole("USER")  // #3 The page required `USER` role
                    //.antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()  // #4 All other endpoints require an authenticated user
                .and().exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);  // #5 Define access denied Handler
    }
    

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        /*
        UserDetails user =
             User.withDefaultPasswordEncoder()
                .username("user")
                .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
                .roles("USER")
                .build();
        */
        UserDetails user = User.withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build();
        /*
        UserDetails admin = User.withUsername("admin")
                .password("{noop}password")
                .roles("ADMIN")
                .build();
        */

        return new InMemoryUserDetailsManager(user/*, admin*/);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**");
    }
}