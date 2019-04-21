package com.douyasi.example.spring_demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class ExceptionEntity implements Serializable {

    private static final long serialVersionUID = 9031619656203165441L;

    private String message;

    private String code;
    
    private String path;
    
    private String error;
    
    private Date timestamp = new Date();
}
