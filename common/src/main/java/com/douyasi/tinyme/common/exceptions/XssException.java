package com.douyasi.tinyme.common.exceptions;

/**
 * XssException
 * 
 * @author raoyc
 */
public class XssException extends RuntimeException {

    private static final long serialVersionUID = -7594523657270930696L;

    public XssException(String message){
        super(message);
    }
}
