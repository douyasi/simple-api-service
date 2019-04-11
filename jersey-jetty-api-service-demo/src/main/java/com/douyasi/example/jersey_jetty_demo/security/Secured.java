package com.douyasi.example.jersey_jetty_demo.security;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {
    
}
