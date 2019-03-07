package com.douyasi.tinyme.common.exceptions;

/**
 * IpException
 * 
 * @author raoyc
 */
public class IpException extends RuntimeException {

    private static final long serialVersionUID = -8649864605110886623L;

    public IpException(String message){
        super(message);
    }

    public IpException(String message, Throwable cause) {
        super(message, cause);
    }

    public IpException(Throwable cause) {
        super(cause);
    }
}
