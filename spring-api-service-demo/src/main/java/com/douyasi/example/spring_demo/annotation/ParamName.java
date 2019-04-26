package com.douyasi.example.spring_demo.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamName {
    /**
     * The name of the request parameter to bind to.
     */
    String value();
}
