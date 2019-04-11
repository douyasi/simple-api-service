package com.douyasi.tinyme.common.enums;

/**
 * Http Status Code
 * 
 * @author raoyc
 */
public enum ResultCode {
    
    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    REDIRECT(301),
    TEMP_REDIRECT(302);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
