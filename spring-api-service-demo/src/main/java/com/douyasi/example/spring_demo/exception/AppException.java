package com.douyasi.example.spring_demo.exception;

public class AppException extends RuntimeException {

    private static final long serialVersionUID = 9071608656003995940L;

    private String code;
    private String message;
    
    public AppException() {
        super();
    }
 
    public AppException(String message) {
        super(message);
        this.message = message;
    }
 
    public AppException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
 
    public String getCode() {
        return code;
    }
 
    public String getMessage() {
        return message;
    }
}

