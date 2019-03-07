package com.douyasi.tinyme.common.model;

import java.io.Serializable;

/**
 * BaseResult
 * 
 * @author raoyc
 */
public abstract class BaseResult<T> implements Serializable{

    /**
     * success ?
     */
    private boolean success = false;

    /**
     * message to response
     */
    private String message;

    /**
     * data to response
     */
    private T data;

    /**
     * error code
     */
    private String code;

    /**
     * current timestamp
     */
    private String timestamp;

    /**
     * isSuccess
     * 
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * setSuccess
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * getMessage
     * 
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * setMessage
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * getData
     * 
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * setData
     * 
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * getCode
     * 
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * setCode
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * getTimestamp
     * 
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * setTimestamp
     * 
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
