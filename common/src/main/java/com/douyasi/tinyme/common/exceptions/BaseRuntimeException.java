package com.douyasi.tinyme.common.exceptions;

import com.douyasi.tinyme.common.interfaces.IMessage;

/**
 * BaseRuntimeException
 * 
 * @author raoyc
 */
public class BaseRuntimeException extends RuntimeException implements IMessage {

    private static final long serialVersionUID = 410892693882117962L;
    private String errorCode;

    protected BaseRuntimeException(IMessage iMessage){
        super(iMessage.getMessage());
        this.errorCode = iMessage.getCode();
    }

    protected BaseRuntimeException(IMessage iMessage, String message){
        super(message);
        this.errorCode = iMessage.getCode();
    }


    protected BaseRuntimeException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }


    @Override
    public String getCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
