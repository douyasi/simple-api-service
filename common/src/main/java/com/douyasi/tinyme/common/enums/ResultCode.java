package com.douyasi.tinyme.common.enums;

/**
 * Http Status Code
 * 
 * @author raoyc
 */
public enum ResultCode {
    /**
     * http status from 200 - 500
     */
    SUCCESS(200),
    REDIRECT(301),
    TEMP_REDIRECT(302),
    FAIL(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);
    
    public int code;

    ResultCode(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
